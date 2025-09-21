## EN_US

### 1️⃣ `tool_rarities` — Tool Rarity Definition

Each file defines a rarity. The file name is the rarity ID (e.g. `artifact.json` → `lookinsharp:artifact`).

**Example: `data/lookinsharp/tool_rarities/artifact.json`**

```jsonc
{
  "weight": 1,        // Selection weight, higher = more common
  "multiplier": 1.5,  // Scales all amounts in traits
  "color": 15447092,  // Display color (decimal RGB)
  "vanillaRarity": "rare" // (Optional) Vanilla rarity: common/uncommon/rare/epic
}
```

| Field           | Type      | Description                                             |
| --------------- | --------- | ------------------------------------------------------- |
| `weight`        | `int`     | Weight for random selection                             |
| `multiplier`    | `double`  | Global multiplier for all modifiers                     |
| `color`         | `int`     | Display color as decimal RGB (convert `#hex` → decimal) |
| `vanillaRarity` | `string?` | *(Optional)* Vanilla rarity name                        |

---

### 2️⃣ `tool_traits` — Tool Trait Definition

Each file defines a trait. The file name is the trait ID (e.g. `heavy.json` → `lookinsharp:heavy`).

**Example: `data/lookinsharp/tool_traits/heavy.json`**

```jsonc
{
  "weight": 1,
  "attributeModifiers": {
    "mainhand": [
      {
        "attribute": "minecraft:generic.movement_speed",
        "modifier": {
          "id": "lookinsharp:trait_movement_speed",
          "amount": -0.1,
          "operation": "add_multiplied_total"
        }
      },
      {
        "attribute": "minecraft:generic.attack_damage",
        "modifier": {
          "id": "lookinsharp:trait_attack_damage",
          "amount": 0.2,
          "operation": "add_value"
        }
      }
    ]
  }
}
```

| Field                | Type                | Description                                                                                                                     |
| -------------------- | ------------------- | ------------------------------------------------------------------------------------------------------------------------------- |
| `weight`             | `int`               | Weight for random selection                                                                                                     |
| `attributeModifiers` | `map<string, list>` | Key = equipment slot name, Value = list of modifiers                                                                            |
| `attribute`          | `string`            | Attribute to modify (e.g. `minecraft:generic.attack_damage`)                                                                    |
| `modifier.id`        | `string`            | Unique ID of this modifier                                                                                                      |
| `modifier.amount`    | `double`            | Value, can be positive or negative                                                                                              |
| `modifier.operation` | `string`            | Operation: <br>`add_value` (flat add)<br>`add_multiplied_base` (scale base value)<br>`add_multiplied_total` (scale final value) |

---

### 3️⃣ Valid Equipment Slots

| Slot Name  | `EquipmentSlot` | When It Applies                              |
| ---------- | --------------- | -------------------------------------------- |
| `mainhand` | `MAINHAND`      | When item is held in main hand               |
| `offhand`  | `OFFHAND`       | When item is held in off hand                |
| `head`     | `HEAD`          | When worn in helmet slot                     |
| `chest`    | `CHEST`         | When worn in chestplate slot                 |
| `legs`     | `LEGS`          | When worn in leggings slot                   |
| `feet`     | `FEET`          | When worn in boots slot                      |
| `body`     | `BODY`          | When worn as animal armor (horse/wolf armor) |

---

### 4️⃣ Notes

* Higher `weight` = higher chance to be picked.
* Use decimal RGB for `color` (convert from `#RRGGBB`).
* `modifier.id` must be globally unique to avoid stacking issues.
* If the item is in the wrong slot, the trait will not apply.
