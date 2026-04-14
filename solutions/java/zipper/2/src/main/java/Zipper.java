import java.util.Objects;

class Zipper {
    Zipper up;
    Zipper left;
    Zipper right;
    int value;

    Zipper(int val) {
        this.value = val;
    }

    int getValue() {
        return value;
    }

    void setValue(int val) {
        this.value = val;
    }

    void setLeft(Zipper leftChild) {
        this.left = leftChild;
        if (leftChild != null) {
            left.up = this;
        }
    }

    void setRight(Zipper rightChild) {
        this.right = rightChild;
        if (rightChild != null) {
            right.up = this;
        }
    }

    BinaryTree toTree() {
        return up != null ? up.toTree() : new BinaryTree(this);
    }

    public String toString() {
        return "value: " + this.value + ", "
                + "left: "
                + (this.left == null
                        ? null
                        : ("{ " + this.left + " }"))
                + ", right: "
                + (this.right == null
                        ? null
                        : ("{ " + this.right + " }"))
                ;
    }
}

class BinaryTree {
    private final Zipper root;

    BinaryTree(int value) {
        root = new Zipper(value);
    }

    BinaryTree(Zipper root) {
        this.root = root;
    }

    Zipper getRoot() {
        return root;
    }

    String printTree() {
        return this.root.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof BinaryTree that))
            return false;

        return Objects.equals(this.root, that.root);
    }

}