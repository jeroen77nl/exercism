import java.util.ArrayList;
import java.util.List;

class VariableLengthQuantity {

    private static final int MSB_ZERO_MASK = 0b01111111;
    private static final int MSB_SET_ONE_MASK = 0b10000000;

    List<String> encode(List<Long> numbers) {
        List<String> result = new ArrayList<>();
        for (long l : numbers) {
            result.addAll(encodeNumber(l));
        }
        return result;
    }

    List<String> encodeNumber(long number) {
        List<String> resultList = new ArrayList<>();

        long bt = number & MSB_ZERO_MASK;
        resultList.add("0x" + Long.toHexString(bt));
        number >>>= 7;

        while (number > 0) {
            bt = number & MSB_ZERO_MASK;
            bt = bt | MSB_SET_ONE_MASK;
            resultList.add("0x" + Long.toHexString(bt));
            number >>>= 7;
        }

        return resultList.reversed();
    }

    List<String> decode(List<Long> bytes) {

        verifyBytes(bytes);

        List<String> resultList = new ArrayList<>();
        List<Long> sequence = new ArrayList<>();

        for (Long aByte : bytes) {
            sequence.add(aByte);
            if (isMsbOff(aByte)) {
                resultList.add(decodeSequence(sequence));
                sequence.clear();
            }
        }
        return resultList;
    }

    String decodeSequence(List<Long> bytes) {
        verifyBytes(bytes);

        long value = 0;
        for (Long encodedByteValue : bytes) {
            value <<= 7;
            value |= encodedByteValue & MSB_ZERO_MASK;
        }
        return "0x" +  Long.toHexString(value);
    }

    private void verifyBytes(List<Long> bytes) {
        String msg = "Invalid variable-length quantity encoding";

        if (isMsbOn(bytes.getLast())) {
            throw new IllegalArgumentException(msg);
        }
    }

    private boolean isMsbOn(Long b) {
        return (b & MSB_SET_ONE_MASK) != 0;
    }

    private boolean isMsbOff(Long b) {
        return (b & MSB_SET_ONE_MASK) == 0;
    }
}
