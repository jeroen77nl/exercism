import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
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
        if (value.compareTo(node.getData()) <= 0) {
            if (node.getLeft() == null) {
                node.left = new Node<>(value);
            } else {
                insert(node.getLeft(), value);
            }
        } else {
            if (node.getRight() == null) {
                node.right = new Node<>(value);
            } else {
                insert(node.getRight(), value);
            }
        }
    }

    List<T> getAsSortedList() {
        List<T> result = new ArrayList<>();
        sortedListRecur(root, result);
        return result;
    }

    private void sortedListRecur(Node<T> node, List<T> values) {
        if (node == null)
            return;

        sortedListRecur(node.getLeft(), values);
        values.add(node.getData());
        sortedListRecur(node.getRight(), values);
    }

    List<T> getAsLevelOrderList() {
        List<T> result = new ArrayList<>();
        Deque<Node<T>> queue = new ArrayDeque<>();

        if (root == null)
            return result;

        queue.addLast(root);
        while (!queue.isEmpty()) {
            Node<T> node = queue.removeFirst();
            result.addLast(node.getData());

            if (node.getLeft() != null) {
                queue.addLast(node.getLeft());
            }
            if (node.getRight() != null) {
                queue.addLast(node.getRight());
            }
        }

        return result;
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
