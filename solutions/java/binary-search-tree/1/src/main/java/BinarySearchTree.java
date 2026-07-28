import java.util.ArrayList;
import java.util.List;

class BinarySearchTree<T extends Comparable<T>> {

    private Node<T> root = null;

    void insert(T value) {
        if (root == null) {
            root = new Node<>(value);
            return;
        }

        insert(root, value);
    }

    private void insert(Node<T> node, T value) {
        if (node == null) {
            return;
        }
        if (value.compareTo(node.getData()) <= 0) {
            if (node.left == null) {
                node.left = new Node<>(value);
            } else {
                insert(node.left, value);
            }
        } else {
            if (node.right == null) {
                node.right = new Node<>(value);
            } else {
                insert(node.right, value);
            }
        }
    }

    List<T> getAsSortedList() {
        List<T> result = new ArrayList<>();
        sortedList(root, result);
        return result;
    }

    private void sortedList(Node<T> node, List<T> values) {
        if (node == null)
            return;

        if (node.left != null) {
            sortedList(node.left, values);
        }

        values.add(node.getData());

        if (node.right != null) {
            sortedList(node.right, values);
        }
    }

    List<T> getAsLevelOrderList() {
        List<T> result = new ArrayList<>();

        if (root == null)
            return result;

        result.add(root.getData());
        levelOrderList(root, result);
        return result;
    }

    private void levelOrderList(Node<T> node, List<T> values) {
        if (node == null)
            return;

        if (node.left != null) {
            values.add(node.left.getData());
        }
        if (node.right != null) {
            values.add(node.right.getData());
        }
        if (node.left != null) {
            levelOrderList(node.left, values);
        }
        if (node.right != null) {
            levelOrderList(node.right, values);
        }
    }

    Node<T> getRoot() {
        return root;
    }

    static class Node<T> {

        private Node<T> left;
        private Node<T> right;
        private final T data;

        Node(T data) {
            this.data = data;
        }

        Node<T> getLeft() {
            return left;
        }

        Node<T> getRight() {
            return right;
        }

        T getData() {
            return data;
        }

    }
}
