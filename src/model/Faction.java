// src/model/Faction.java
package model;

public enum Faction {
    RED, BLUE
}

/*
enum = enumeration type.

It’s a special class in Java used to define a fixed set of constants.

Each constant is a unique instance of the enum type.

📂 Your Example
java
package model;

public enum Faction {
    RED, BLUE
}

Step 1: Declaration
public enum Faction → defines an enumeration named Faction.

Step 2: Constants
RED and BLUE are the only possible values of Faction.

They are like predefined objects of type Faction.


🎮 Conceptual Meaning
In your game model:

Faction represents which side/team a player belongs to.

Possible factions are RED or BLUE.

No other values are allowed (you can’t accidentally assign "Green" or "Yellow").

⚔️ Example Usage
java
Faction playerFaction = Faction.RED;

if (playerFaction == Faction.RED) {
    System.out.println("Player is on the Red team!");
}
Faction.RED and Faction.BLUE are constants you can compare directly.

Enums are type-safe: you can’t assign a random string like "RED"; it must be the actual enum constant.

🧠 Why Use Enums?
Safety: Prevents invalid values.

Readability: Code is clearer (Faction.RED vs "red").

Features: Enums can have methods, fields, and constructors if needed.

👉 In short: enum defines a restricted set of named constants. Here, Faction can only ever be RED or BLUE, making it perfect for modeling teams or sides in your game.











📝 Extended Enum Example
java
package model;

public enum Faction {
    RED("Red Team", "#FF0000"),
    BLUE("Blue Team", "#0000FF");

    private final String displayName;
    private final String colorCode;

    // Constructor for enum constants
    Faction(String displayName, String colorCode) {
        this.displayName = displayName;
        this.colorCode = colorCode;
    }

    // Getter methods
    public String getDisplayName() {
        return displayName;
    }

    public String getColorCode() {
        return colorCode;
    }
}




🔎 Explanation
Enum constants with parameters

RED("Red Team", "#FF0000") → has a display name and a hex color code.

BLUE("Blue Team", "#0000FF") → same idea.

Fields

displayName → human-readable name.

colorCode → hex string for UI rendering.

Constructor

Enums can have constructors, but they are private by default.

Each constant calls the constructor with its own data.

Getter methods

Allow other parts of the program to access the extra data safely.

🎮 Example Usage
java
Faction f = Faction.RED;

System.out.println(f.getDisplayName()); // "Red Team"
System.out.println(f.getColorCode());   // "#FF0000"
This makes it easy to:

Show faction names in menus.

Render faction colors in the UI.

Keep all faction-related data centralized.

⚔️ Possible Extensions
You could add:

Symbol/emoji → e.g., ⚔️ for RED, 🛡️ for BLUE.

Starting bonuses → each faction could have different starting resources.

AI behavior type → attach strategy profiles to factions.



*/