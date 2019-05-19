package org.dnd.discord;

import java.util.concurrent.ThreadLocalRandom;

public class DnDDices {
    public enum Die{
        D4(Constants.D4),
        D6(Constants.D6),
        D8(Constants.D8),
        D10(Constants.D10),
        D12(Constants.D12),
        D20(Constants.D20),
        D100(Constants.D100);


        private final int diceValue;

        private Die(final int diceValue)
        {
            this.diceValue = diceValue;
        }

        public int roll()
        {
            return ThreadLocalRandom.current().nextInt(this.diceValue) + 1;
        }
    }
}
