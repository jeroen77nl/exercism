import java.util.ArrayList;
import java.util.List;

class Flattener {

    List<Object> flatten(List<?> list) {
        List<Object> result = new ArrayList<>();
        flattenInto(list, result);
        return result;
    }

    private void flattenInto(List<?> list, List<Object> result) {
        for (var object : list) {
            if (object == null) continue;

            if (object instanceof List<?> nestedList) {
                flattenInto(nestedList, result);
            } else {
                result.add(object);
            }
        }
    }
}