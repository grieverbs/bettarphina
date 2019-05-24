package org.dnd.discord.utility;

import org.dnd.discord.DDEnum;
import org.dnd.discord.Constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
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

    public static int randomAbilityScore3d6() {
        final DDEnum.Dice myD6 = DDEnum.Dice.D6;
        return IntStream.of(myD6.roll(), myD6.roll(), myD6.roll()).sum();
    }

    public static List<Integer> randomStatBlock(final boolean fourthDice) {
        final List<Integer> block = new ArrayList<>();
        for (int count = 0; count < 6; ++count)
            block.add(fourthDice ? randomAbilityScore() : randomAbilityScore3d6());

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

    public static Map<Integer, AtomicInteger> getD4Statistics(final int sampleSize, final boolean fourthDice) {
        final Map<Integer, AtomicInteger> map = new HashMap<>();
        for (int count = 0; count < sampleSize; ++count) {
            final var key = fourthDice ? randomAbilityScore() : randomAbilityScore3d6();
            map.putIfAbsent(key, new AtomicInteger(0));
            map.get(key).incrementAndGet();
        }
        return map;
    }

    public static String toStringStatistic(final Map<Integer, AtomicInteger> map, final int sampleSize) {
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(String.format("Sample size of %d:\n", sampleSize));
        for (final Integer key : map.keySet()) {
            stringBuilder.append(
                    String.format(
                            "%d:\t%f\t%d times",
                            key,
                            (map.get(key).floatValue() / sampleSize),
                            map.get(key).intValue()
                    )
            );
        }
        return stringBuilder.toString();
    }
}
