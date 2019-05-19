package org.dnd.discord.utility;

import org.dnd.discord.DDEnum;
import org.dnd.discord.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class DiceUtils {
    public static int randomAbilityScore() {
        final DDEnum.Dice myD6 = DDEnum.Dice.D6;
        final List<Integer> list = IntStream.of(myD6.roll(), myD6.roll(), myD6.roll(), myD6.roll())
                .sorted().boxed().collect(Collectors.toList());

        // Remove the lowest dice out of the 4
        list.remove(0);

        // Sum the 3
        return list.stream().mapToInt(x -> x).sum();
    }

    public static List<Integer> randomStatBlock() {
        final List<Integer> block = new ArrayList<>();
        block.add(randomAbilityScore());
        block.add(randomAbilityScore());
        block.add(randomAbilityScore());
        block.add(randomAbilityScore());
        block.add(randomAbilityScore());
        block.add(randomAbilityScore());
        return block;
    }

    public static String printStatBlock(final List<Integer> list) {
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("(");
        stringBuilder.append(list.stream().map(x -> x.toString())
                .collect(Collectors.joining(", ")));
        stringBuilder.append(")");
        return stringBuilder.toString();
    }
}
