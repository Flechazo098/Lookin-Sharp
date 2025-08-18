package earth.terrarium.lookinsharp.api.types;

import com.google.common.collect.ImmutableMultimap;
import earth.terrarium.lookinsharp.LookinSharp;
import earth.terrarium.lookinsharp.util.Utils;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public enum BuiltInSwordTypes implements SwordType {
    SWORD,
    BROADSWORD {
        @Override
        public void modifyAttributeModifiers(ImmutableMultimap.Builder<Holder<Attribute>, AttributeModifier> builder) {
            builder.put(Attributes.ARMOR_TOUGHNESS, new AttributeModifier(LookinSharp.BASE_TOUGHNESS, .2, AttributeModifier.Operation.ADD_VALUE));
            builder.put(Attributes.KNOCKBACK_RESISTANCE, new AttributeModifier(LookinSharp.BASE_KNOCKBACK, .2, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));
        }
    },
    SHORTSWORD {
        @Override
        public void modifyAttributeModifiers(ImmutableMultimap.Builder<Holder<Attribute>, AttributeModifier> builder) {
            builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_ID, -0.1f, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
            builder.put(Attributes.MOVEMENT_SPEED, new AttributeModifier(LookinSharp.BASE_MOVEMENT, .1, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));
        }
    },
    CUTLASS {
        @Override
        public void modifyAttributeModifiers(ImmutableMultimap.Builder<Holder<Attribute>, AttributeModifier> builder) {
            builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_ID, .4f, AttributeModifier.Operation.ADD_VALUE));
        }
    },
    HONED_SWORD {
        @Override
        public void modifyAttributeModifiers(ImmutableMultimap.Builder<Holder<Attribute>, AttributeModifier> builder) {
            builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_ID, .1f, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));
        }
    },
    GLADIUS {
        @Override
        public void modifyAttributeModifiers(ImmutableMultimap.Builder<Holder<Attribute>, AttributeModifier> builder) {
            builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_ID, .2f, AttributeModifier.Operation.ADD_VALUE));
            builder.put(Attributes.ARMOR_TOUGHNESS, new AttributeModifier(LookinSharp.BASE_TOUGHNESS, 2, AttributeModifier.Operation.ADD_VALUE));
        }
    },
    CLEAVER {
        @Override
        public void modifyAttributeModifiers(ImmutableMultimap.Builder<Holder<Attribute>, AttributeModifier> builder) {
            builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_ID, 3, AttributeModifier.Operation.ADD_VALUE));
            builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_ID, -.8f, AttributeModifier.Operation.ADD_VALUE));
            builder.put(Attributes.ATTACK_KNOCKBACK, new AttributeModifier(LookinSharp.BASE_KNOCKBACK, .3, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));
            builder.put(Attributes.MOVEMENT_SPEED, new AttributeModifier(LookinSharp.BASE_MOVEMENT, -.1, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));
        }
    },
    GREATSWORD {
        @Override
        public void modifyAttributeModifiers(ImmutableMultimap.Builder<Holder<Attribute>, AttributeModifier> builder) {
            builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_ID, .2f, AttributeModifier.Operation.ADD_VALUE));
            builder.put(Utils.getAttackRangeAttribute(), new AttributeModifier(LookinSharp.BASE_RANGE, 1, AttributeModifier.Operation.ADD_VALUE));
        }
    },
    SCYTHE {
        @Override
        public void modifyAttributeModifiers(ImmutableMultimap.Builder<Holder<Attribute>, AttributeModifier> builder) {
            builder.put(Attributes.ATTACK_KNOCKBACK, new AttributeModifier(LookinSharp.BASE_KNOCKBACK, .2, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));
        }
    },
    SPEAR {
        @Override
        public void modifyAttributeModifiers(ImmutableMultimap.Builder<Holder<Attribute>, AttributeModifier> builder) {
            builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_ID, -.1, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));
            builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_ID, -.2f, AttributeModifier.Operation.ADD_VALUE));
            builder.put(Utils.getAttackRangeAttribute(), new AttributeModifier(LookinSharp.BASE_RANGE, 2, AttributeModifier.Operation.ADD_VALUE));
            builder.put(Utils.getReachAttribute(), new AttributeModifier(LookinSharp.BASE_REACH, 0.5, AttributeModifier.Operation.ADD_VALUE));
        }
    };

    public static final ResourceLocation BASE_ATTACK_DAMAGE_ID = ResourceLocation.fromNamespaceAndPath("lookinsharp", "base_attack_damage");
    public static final ResourceLocation BASE_ATTACK_SPEED_ID = ResourceLocation.fromNamespaceAndPath("lookinsharp", "base_attack_speed");
    public static final BuiltInSwordTypes[] VALUES = values();
}