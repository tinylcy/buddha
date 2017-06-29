package org.tinylcy;

import java.util.Random;

/**
 * Created by chenyangli.
 */
public class RandomGenerator {

    /**
     * @param min minimum value
     * @param max maximum value must be greater than min
     * @return Integer between min and max, inclusive.
     */
    public static int randInt(int min, int max) {
        Random random = new Random();
        int randomNum = random.nextInt((max - min) + 1) + min;
        return randomNum;
    }
}
