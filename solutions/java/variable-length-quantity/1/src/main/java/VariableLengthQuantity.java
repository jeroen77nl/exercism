import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

class VariableLengthQuantity {

    List<String> encode(List<Long> numbers) {
        List<List<String>> resultList = new ArrayList<>();

        for (Long l : numbers) {
            resultList.add(encodeByte(l.intValue()));
        }
        return resultList.stream().flatMap(Collection::stream).toList();
    }

    List<String> encodeByte(int number) {
        List<String> resultList = new ArrayList<>();

        int bt = number & 0b01111111;
        resultList.add("0x" + Integer.toHexString(bt));
        number >>>= 7;

        while (number > 0) {
            bt = number & 0b01111111;
            bt = bt | 0b10000000;
            resultList.add("0x" + Integer.toHexString(bt));
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

        List<String> resultList = new ArrayList<>();
        int value = 0;
        for (Long encodedByteValue : bytes) {
            value <<= 7;
            value |= encodedByteValue.intValue() & 0b01111111;
        }
        return "0x" + Integer.toHexString(value);
    }

    private void verifyBytes(List<Long> bytes) {
        String msg = "Invalid variable-length quantity encoding";

        if (isMsbOn(bytes.getLast())) {
            throw new IllegalArgumentException(msg);
        }
    }

    private boolean isMsbOn(Long b) {
        return (b.intValue() & 0b10000000) != 0;
    }

    private boolean isMsbOff(Long b) {
        return (b.intValue() & 0b10000000) == 0;
    }
}
