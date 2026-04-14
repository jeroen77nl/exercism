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

record BinaryTree(Zipper root) {
    BinaryTree(int value) {
        this(new Zipper(value));
    }

    String printTree() {
        return this.root.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof BinaryTree(Zipper rootZipper)))
            return false;

        return Objects.equals(this.root, rootZipper);
    }

}