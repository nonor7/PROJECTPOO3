// src/combat/Damage.java
package combat;

import java.util.concurrent.ThreadLocalRandom;

public final class Damage {
    private Damage(){}

    public static int compute(int atk, int def) {
        int variance = ThreadLocalRandom.current().nextInt(-2, 3);
        return Math.max(0, atk - def + variance);
    }
}

