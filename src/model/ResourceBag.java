// src/model/ResourceBag.java
package model;

import game.GameConfig;
import java.util.HashMap;
import java.util.Map;

public class ResourceBag {
    private final Map<String,Integer> values = new HashMap<>();

    public ResourceBag() {
        values.put("gold", 0);
        values.put("wood", 0);
        values.put("stone", 0);
        values.put("food", 0);
    }

    public static ResourceBag starting() {
        ResourceBag r = new ResourceBag();
        r.add("gold", GameConfig.START_GOLD);
        r.add("wood", GameConfig.START_WOOD);
        r.add("stone", GameConfig.START_STONE);
        r.add("food", GameConfig.START_FOOD);
        return r;
    }

    public int get(String key) { return values.getOrDefault(key, 0); }
    //getOrDefault(key, 0) → This method tries to retrieve the value associated with the given key.
    //If the key exists in the map, it returns the corresponding Integer value.
    //If the key does not exist, it returns the default value you provided (0 in this case).
    public void add(String key, int v) { values.put(key, get(key) + v); }
    public boolean spend(String key, int v) {
        if (get(key) < v) return false;
        values.put(key, get(key) - v);
        return true;
    }

    public boolean canAfford(Cost c) {
        return get("gold")  >= c.gold &&
               get("wood")  >= c.wood &&
               get("stone") >= c.stone &&
               get("food")  >= c.food;
    }
    public void pay(Cost c) {
        spend("gold", c.gold); spend("wood", c.wood);
        spend("stone", c.stone); spend("food", c.food);
    }

    public static class Cost {
        public final int gold, wood, stone, food;
        public Cost(int g,int w,int s,int f){gold=g;wood=w;stone=s;food=f;}
    }

    @Override public String toString() {
        return "G:" + get("gold") + " W:" + get("wood") + " S:" + get("stone") + " F:" + get("food");
    }
}

/*
private final Map<String,Integer> values = new HashMap<>();
A map that stores resource names (String) mapped to their amounts (Integer).

final → the reference cannot be reassigned, but contents can change.

🏗️ Constructor
java
public ResourceBag() {
    values.put("gold", 0);
    values.put("wood", 0);
    values.put("stone", 0);
    values.put("food", 0);
}
Initializes all resources to 0.

Ensures the bag always has these four keys.

🎮 Starting Resources
java
public static ResourceBag starting() {
    ResourceBag r = new ResourceBag();
    r.add("gold", GameConfig.START_GOLD);
    r.add("wood", GameConfig.START_WOOD);
    r.add("stone", GameConfig.START_STONE);
    r.add("food", GameConfig.START_FOOD);
    return r;
}
Factory method to create a new bag with initial resources from GameConfig.

Example: starting gold = 100, wood = 50, etc.

📊 Core Methods
Get Resource
java
public int get(String key) { return values.getOrDefault(key, 0); }
Returns the amount of a resource.

If the key doesn’t exist, returns 0.

Add Resource
java
public void add(String key, int v) { values.put(key, get(key) + v); }
Increases resource by v.

Example: add("gold", 50) → adds 50 gold.

Spend Resource
java
public boolean spend(String key, int v) {
    if (get(key) < v) return false;
    values.put(key, get(key) - v);
    return true;
}
Tries to subtract v from resource.

If not enough, returns false (transaction fails).

Otherwise subtracts and returns true.

💰 Cost Handling
Check Affordability
java
public boolean canAfford(Cost c) {
    return get("gold")  >= c.gold &&
           get("wood")  >= c.wood &&
           get("stone") >= c.stone &&
           get("food")  >= c.food;
}
Checks if the bag has enough resources to pay a given Cost.

Pay Cost
java
public void pay(Cost c) {
    spend("gold", c.gold); spend("wood", c.wood);
    spend("stone", c.stone); spend("food", c.food);
}
Deducts resources according to the Cost object.

🧾 Inner Class: Cost
java
public static class Cost {
    public final int gold, wood, stone, food;
    public Cost(int g,int w,int s,int f){gold=g;wood=w;stone=s;food=f;}
}
Represents the price of something in terms of resources.

Immutable (fields are final).

🖊️ toString
java
@Override public String toString() {
    return "G:" + get("gold") + " W:" + get("wood") + " S:" + get("stone") + " F:" + get("food");
}
Returns a string summary of resources.

Example: "G:100 W:50 S:20 F:30"

⚔️ Example in Action
java
ResourceBag bag = ResourceBag.starting();
System.out.println(bag); 
// G:100 W:50 S:20 F:30 (example values)

Cost buildingCost = new ResourceBag.Cost(50, 20, 10, 0);

if (bag.canAfford(buildingCost)) {
    bag.pay(buildingCost);
    System.out.println("Building constructed!");
    System.out.println(bag);
    // G:50 W:30 S:10 F:30
}


















🔑 Key Differences
Aspect	MapString, Integer> ages = new HashMap<>();	HashMapString, Double> map = new HashMap<>();
Variable type	Interface (Map) → more flexible	Concrete class (HashMap) → less flexible
Value type	Integer (whole numbers)	Double (decimal numbers)
Polymorphism	Can switch to other Map implementations easily	Locked to HashMap
Use case	Storing ages, counts, discrete values	Storing prices, scores, continuous values
🎯 Practical Insight
Since you’re building strategic games and modular projects, the first style (Map<String, Integer> ages = new HashMap<>();) is usually better. It lets you change the underlying map type later (e.g., if you need ordering with TreeMap or insertion order with LinkedHashMap) without rewriting much code.

Would you like me to show you a game-related example where both styles are used—like one map for player ages (Integer) and another for resource weights (Double)? That way you’ll see how the choice of Integer vs Double affects the logic.
*/