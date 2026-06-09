import java.util.ArrayList;
import java.util.List;

import static java.util.Comparator.comparing;

class BuildTree {

    TreeNode buildTree(ArrayList<Record> records) throws InvalidRecordsException {
        if (records.isEmpty()) {
            return null;
        }

        List<Record> orderedRecords = records.stream()
                .sorted(comparing(Record::getRecordId))
                .toList();

        TreeNode[] nodes = new TreeNode[records.size()];


        for (int i = 0; i < orderedRecords.size(); i++) {

            Record record = orderedRecords.get(i);
            int id = record.getRecordId();
            int parentId = record.getParentId();

            if (i != id) {
                throw new InvalidRecordsException("Invalid Records");
            }

            if (id == 0) {
                if (parentId != 0) {
                    throw new InvalidRecordsException("Invalid Records");
                }
            } else {
                if (id <= parentId) {
                    throw new InvalidRecordsException("Invalid Records");
                }
            }

            TreeNode node = new TreeNode(id);
            nodes[id] = node;

            if (id != 0) {
                nodes[parentId].getChildren().add(node);
            }
        }

        return nodes[0];
    }
}
