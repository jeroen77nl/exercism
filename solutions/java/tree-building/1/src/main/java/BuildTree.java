import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

class BuildTree {

    TreeNode buildTree(ArrayList<Record> records) throws InvalidRecordsException {
        if (records.isEmpty()) {
            return null;
        }

        List<Record> orderedRecords =
                records.stream()
                        .sorted(Comparator.comparing(Record::getParentId)
                                .thenComparing(Record::getRecordId))
                        .toList();

        if (orderedRecords.getFirst().getRecordId() != 0
                || orderedRecords.getFirst().getParentId() != 0) {
            throw new InvalidRecordsException("Invalid Records");
        }

        TreeNode root = new TreeNode(0);

        for (int i = 1; i < orderedRecords.size(); i++) {

            if (orderedRecords.get(i - 1).getRecordId() + 1
                    != orderedRecords.get(i).getRecordId()) {
                throw new InvalidRecordsException("Invalid Records");
            }

            if (!insert(orderedRecords.get(i), root)) {
                throw new InvalidRecordsException("Invalid Records");
            }
        }

        return root;
    }

    private boolean insert(Record record, TreeNode treeNode) {
        if (treeNode.getNodeId() == record.getParentId()) {
            TreeNode newTreeNode = new TreeNode(record.getRecordId());
            treeNode.getChildren().add(newTreeNode);
            return true;
        }

        for (TreeNode child : treeNode.getChildren()) {
            boolean inserted = insert(record, child);
            if (inserted)
                return inserted;
        }

        return false;
    }
}
