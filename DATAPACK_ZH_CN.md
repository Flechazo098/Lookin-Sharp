## ZH_CN

### 1️⃣ `tool_rarities` —— 工具稀有度定义

每个文件定义一种稀有度，文件名即稀有度 ID（例：`artifact.json` → `lookinsharp:artifact`）。

**示例：`data/lookinsharp/tool_rarities/artifact.json`**

```jsonc
{
  "weight": 1,        // 抽取权重，数值越大越常见
  "multiplier": 1.5,  // 属性倍率，对 Trait 中所有 amount 生效
  "color": 15447092,  // 用于显示名称的颜色（十进制RGB值）
  "vanillaRarity": "rare" // (可选) 关联原版稀有度：common/uncommon/rare/epic
}
```

| 字段名             | 类型        | 说明                                 |
| --------------- | --------- | ---------------------------------- |
| `weight`        | `int`     | 用于随机选择稀有度的权重                       |
| `multiplier`    | `double`  | 稀有度对属性的全局倍率                        |
| `color`         | `int`     | 显示用颜色（整数RGB），可用在线工具将 `#hex` 转换为十进制 |
| `vanillaRarity` | `string?` | *(可选)* 对应原版物品稀有度，影响物品名字颜色          |

---

### 2️⃣ `tool_traits` —— 工具特性定义

每个文件定义一种特性，文件名即特性 ID（例：`heavy.json` → `lookinsharp:heavy`）。

**示例：`data/lookinsharp/tool_traits/heavy.json`**

```jsonc
{
  "weight": 1,              // 抽取权重，决定特性出现概率
  "attributeModifiers": {   // 按槽位分组的属性修改
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

| 字段名                  | 类型                  | 说明                                                                                                     |
| -------------------- | ------------------- | ------------------------------------------------------------------------------------------------------ |
| `weight`             | `int`               | 用于随机选择特性的权重                                                                                            |
| `attributeModifiers` | `map<string, list>` | 键是装备槽位名，值是属性修改列表                                                                                       |
| `attribute`          | `string`            | 目标属性的资源路径，如 `minecraft:generic.attack_damage`                                                          |
| `modifier.id`        | `string`            | 修饰符唯一 ID，用于区分多个修改器                                                                                     |
| `modifier.amount`    | `double`            | 数值，负数表示削弱                                                                                              |
| `modifier.operation` | `string`            | 计算方式，可选值：<br>`add_value`（直接加数值）<br>`add_multiplied_base`（按基础值加百分比）<br>`add_multiplied_total`（按最终值加百分比） |

---

### 3️⃣ 合法槽位及生效位置

| 槽位名        | 对应 `EquipmentSlot` | 生效位置说明           |
| ---------- | ------------------ | ---------------- |
| `mainhand` | `MAINHAND`         | 主手拿着物品时生效        |
| `offhand`  | `OFFHAND`          | 副手拿着物品时生效        |
| `head`     | `HEAD`             | 头盔位置生效           |
| `chest`    | `CHEST`            | 胸甲位置生效           |
| `legs`     | `LEGS`             | 护腿位置生效           |
| `feet`     | `FEET`             | 靴子位置生效           |
| `body`     | `BODY`             | 动物盔甲位置（马铠、狼铠等）生效 |

---

### 4️⃣ 重要提示

* `weight` 越大，该条目的出现概率越高，可以用来调节稀有/常见程度。
* `color` 使用十进制整数，推荐用在线工具将 `#RRGGBB` 转为十进制。
* `modifier.id` 必须全局唯一，否则属性叠加可能冲突。
* 如果物品被放在错误槽位，Trait 将不会生效。