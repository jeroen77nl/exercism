import java.util.ArrayList;
import java.util.List;

class VariableLengthQuantity {

    private static final int MSB_ZERO_MASK = 0b01111111;
    private static final int MSB_SET_ONE_MASK = 0b10000000;

    List<String> encode(List<Long> numbers) {
        List<String> result = new ArrayList<>();
        for (long number : numbers) {
            result.addAll(encodeNumber(number));
        }
        return result;
    }

    List<String> encodeNumber(long number) {
        List<String> resultList = new ArrayList<>();

        long byteValue = number & MSB_ZERO_MASK;
        resultList.add("0x" + Long.toHexString(byteValue));
        number >>>= 7;

        while (number > 0) {
            byteValue = number & MSB_ZERO_MASK;
            byteValue |= MSB_SET_ONE_MASK;
            resultList.add("0x" + Long.toHexString(byteValue));
            number >>>= 7;
        }

        return resultList.reversed();
    }

    List<String> decode(List<Long> bytes) {
        List<String> result = new ArrayList<>();

        long decodedValue = 0;
        boolean buildingSequence = false;

        for (long byteValue : bytes) {
            decodedValue <<= 7;
            decodedValue |= (byteValue & MSB_ZERO_MASK);

            buildingSequence = !isTerminalByte(byteValue);

            if (!buildingSequence) {
                result.add("0x" + Long.toHexString(decodedValue));
                decodedValue = 0;
            }
        }
        if (buildingSequence) {
            throw new IllegalArgumentException("Invalid variable-length quantity encoding");
        }

        return result;
    }

    private boolean isTerminalByte(long byteValue) {
        return (byteValue & MSB_SET_ONE_MASK) == 0;
    }
}
