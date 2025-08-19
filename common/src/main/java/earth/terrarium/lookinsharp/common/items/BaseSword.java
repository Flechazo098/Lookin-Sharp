package earth.terrarium.lookinsharp.common.items;

import com.google.common.collect.ImmutableMultimap;
import earth.terrarium.lookinsharp.api.abilities.ToolAbility;
import earth.terrarium.lookinsharp.api.abilities.ToolAbilityManager;
import earth.terrarium.lookinsharp.api.rarities.ToolRarity;
import earth.terrarium.lookinsharp.api.rarities.ToolRarityApi;
import earth.terrarium.lookinsharp.api.traits.ToolTrait;
import earth.terrarium.lookinsharp.api.traits.ToolTraitApi;
import earth.terrarium.lookinsharp.api.types.SwordType;
import earth.terrarium.lookinsharp.common.registry.ModDataComponents;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.component.Tool;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BaseSword extends SwordItem {
    public final SwordType type;
    private final int baseAttackDamage;
    private final float baseAttackSpeed;
    private final Tier baseTier;

    public BaseSword(Tier tier, SwordType type, int attackDamage, float attackSpeed, Properties properties) {
        super(tier, properties.component(DataComponents.TOOL, createToolProperties()).attributes(createAttributes(tier, type, attackDamage, attackSpeed)));
        this.type = type;
        this.baseAttackDamage = attackDamage;
        this.baseAttackSpeed = attackSpeed;
        this.baseTier = tier;
    }

    private static Tool createToolProperties() {
        return new Tool(List.of(Tool.Rule.minesAndDrops(List.of(Blocks.COBWEB), 15.0F), Tool.Rule.overrideSpeed(BlockTags.SWORD_EFFICIENT, 1.5F)), 1.0F, 2);
    }

    public static ItemAttributeModifiers createAttributes(Tier tier, SwordType type, int attackDamage, float attackSpeed) {
        ItemAttributeModifiers.Builder builder = ItemAttributeModifiers.builder()
                .add(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_ID, (float) attackDamage + tier.getAttackDamageBonus(), AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
                .add(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_ID, attackSpeed, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND);

        addSwordTypeModifiers(builder, type);

        return builder.build();
    }

    private static void addSwordTypeModifiers(ItemAttributeModifiers.Builder target, SwordType type) {
        ImmutableMultimap.Builder<Holder<Attribute>, AttributeModifier> temp = ImmutableMultimap.builder();
        type.modifyAttributeModifiers(temp);
        var multimap = temp.build();
        for (var entry : multimap.entries()) {
            target.add(entry.getKey(), entry.getValue(), EquipmentSlotGroup.MAINHAND);
        }
    }

    @Override
    public boolean canAttackBlock(BlockState blockState, Level level, BlockPos blockPos, Player player) {
        return !player.isCreative();
    }

    @Override
    public boolean hurtEnemy(ItemStack itemStack, LivingEntity target, LivingEntity attacker) {
        return true;
    }

    @Override
    public void postHurtEnemy(ItemStack itemStack, LivingEntity target, LivingEntity attacker) {
        itemStack.hurtAndBreak(1, attacker, EquipmentSlot.MAINHAND);
    }

    @Override
    public void onCraftedBy(ItemStack itemStack, Level level, Player player) {
        super.onCraftedBy(itemStack, level, player);
        if (level.isClientSide()) {
            return;
        }

        if (getTraitId(itemStack) == null) {
            ToolTrait trait = ToolTraitApi.rollTrait();
            if (trait != null) {
                ToolTraitApi.setTrait(itemStack, trait);
            }
        }

        if (ToolRarityApi.fromItem(itemStack) == null) {
            ToolRarity rarity = ToolRarityApi.rollRarity();
            if (rarity != null) {
                ToolRarityApi.setRarity(itemStack, rarity);
            }
        }

        recalcAttributeComponents(itemStack);
    }

    private void recalcAttributeComponents(ItemStack stack) {
        ItemAttributeModifiers.Builder builder = ItemAttributeModifiers.builder()
                .add(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_ID, (float) baseAttackDamage + baseTier.getAttackDamageBonus(), AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
                .add(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_ID, baseAttackSpeed, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND);

        addSwordTypeModifiers(builder, this.type);

        ToolTrait trait = getTrait(stack);
        ToolRarity rarity = ToolRarityApi.fromItem(stack);

        if (trait != null && rarity != null) {
            trait.modifyAttributes(stack, EquipmentSlot.MAINHAND, (attribute, modifier) ->
                    builder.add(attribute, modifier, EquipmentSlotGroup.MAINHAND), rarity);
        }

        stack.set(DataComponents.ATTRIBUTE_MODIFIERS, builder.build());
    }

    @Override
    public void appendHoverText(ItemStack itemStack, TooltipContext tooltipContext, List<Component> list, TooltipFlag tooltipFlag) {
        super.appendHoverText(itemStack, tooltipContext, list, tooltipFlag);

        ToolRarity rarity = ToolRarityApi.fromItem(itemStack);
        if (rarity != null) {
            ResourceLocation rarityId = ToolRarityApi.getRarityId(rarity);
            list.add(Component.translatable(rarityId.toLanguageKey("rarity")).withStyle(ChatFormatting.BOLD).withStyle(Style.EMPTY.withColor(rarity.getColor())));
        }

        ToolAbility ability = getAbility(itemStack);
        if (ability != null) {
            ResourceLocation resourceLocation = ResourceLocation.tryParse(ToolAbilityManager.getName(ability));
            if (resourceLocation == null) return;
            list.add(Component.translatable(resourceLocation.toLanguageKey("ability")));
            list.add(Component.translatable(resourceLocation.toLanguageKey("ability").concat(".desc")).withStyle(ChatFormatting.GRAY));
        }
    }

    @Override
    public @NotNull Component getName(ItemStack itemStack) {
        ToolTrait toolTrait = getTrait(itemStack);
        if (toolTrait != null) {
            ResourceLocation traitId = ToolTraitApi.getTraitId(toolTrait);
            return Component.translatable(traitId.toLanguageKey("trait")).append(Component.literal(" ")).append(super.getName(itemStack));
        }
        return super.getName(itemStack);
    }

    public SwordType getType() {
        return this.type;
    }

    @Nullable
    public ToolAbility getAbility(ItemStack stack) {
        return stack.get(ModDataComponents.TOOL_ABILITY.get());
    }

    public void setAbility(ItemStack stack, ToolAbility ability) {
        stack.set(ModDataComponents.TOOL_ABILITY.get(), ability);
    }

    @Nullable
    public ToolTrait getTrait(ItemStack stack) {
        ResourceLocation id = getTraitId(stack);
        return id != null ? ToolTraitApi.getTrait(id) : null;
    }

    @Nullable
    public ResourceLocation getTraitId(ItemStack stack) {
        return stack.get(ModDataComponents.TOOL_TRAIT_ID.get());
    }
}