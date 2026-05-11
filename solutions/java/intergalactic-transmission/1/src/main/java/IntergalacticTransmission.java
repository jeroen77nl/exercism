import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class IntergalacticTransmission {

    public static List<Integer> getTransmitSequence(List<Integer> message) {
        if (message.isEmpty()) return List.of();

        String inputBitString = message.stream()
                .map(IntergalacticTransmission::integerToBitString)
                .collect(Collectors.joining());

        List<String> sevenParts = splitToSevenBitParts(inputBitString);

        return sevenParts.stream()
                .map(IntergalacticTransmission::addParityBit)
                .map(s -> Integer.parseUnsignedInt(s, 2))
                .toList();
    }

    public static List<Integer> decodeSequence(List<Integer> sequence) {
        if (sequence.isEmpty()) return List.of();

        String decodedString = sequence.stream()
                .map(IntergalacticTransmission::integerToBitString)
                .map(IntergalacticTransmission::removeParity)
                .collect(Collectors.joining());

        return splitToEightParts(decodedString).stream()
                .map(s -> Integer.valueOf(s, 2))
                .toList();
    }

    private static String removeParity(String byteString) {
        String sevenPart = byteString.substring(0, 8);
        char parityBit = byteString.charAt(8);

        if ((countOnes(sevenPart) % 2 == 0 && parityBit == '1')
                || (countOnes(sevenPart) % 2 == 1 && parityBit == '0')) {
            throw new IllegalArgumentException("wrong parity bit");
        }

        return sevenPart;
    }

    private static String addParityBit(String sevenPart) {
        int countOnes = countOnes(sevenPart);
        if (countOnes % 2 == 0) {
            return sevenPart + '0';
        }
        return sevenPart + '1';
    }

    private static int countOnes(String sevenPart) {
        int countOnes = 0;
        for (int i = 0; i < sevenPart.length(); i++) {
            if (sevenPart.charAt(i) == '1') {
                countOnes++;
            }
        }
        return countOnes;
    }

    private static String integerToBitString(Integer i) {
        String bits = Integer.toBinaryString(i);
        return String.format("%8s", bits).replace(' ', '0');
    }

    private static List<String> splitToSevenBitParts(String input) {
        List<String> result = new ArrayList<>();

        for (int i = 0; i < input.length(); i += 7) {
            result.add(input.substring(i, Math.min(i + 7, input.length())));
        }

        String s = String.format("%-7s", result.getLast()).replace(' ', '0');
        result.set(result.size() - 1, s);
        return result;
    }

    private static List<String> splitToEightParts(String input) {
        List<String> result = new ArrayList<>();

        for (int i = 0; i < input.length(); i += 8) {
            if (i + 8 <= input.length()) {
                result.add(input.substring(i, i + 8));
            }
        }

        String s = String.format("%-8s", result.getLast()).replace(' ', '0');
        result.set(result.size() - 1, s);

        return result;
    }
}
