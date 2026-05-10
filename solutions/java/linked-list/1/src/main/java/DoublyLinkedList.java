class DoublyLinkedList<T> {

    private static final class Element<T> {
        private final T value;
        private Element<T> prev;
        private Element<T> next;

        Element(T value, Element<T> prev, Element<T> next) {
            this.value = value;
            this.prev = prev;
            this.next = next;
        }
    }

    private Element<T> head;
    private Element<T> tail;

    DoublyLinkedList() {
        head =  new Element<>(null, null, null);
        tail = new Element<>(null, head, null);
        head.next = tail;
    }

    void push(T value) {
        Element<T> newNode = new Element<>(value, tail.prev, tail);
        tail.prev.next = newNode;
        tail.prev = newNode;
    }

    T pop() {
        Element<T> popped = tail.prev;
        popped.prev.next = tail;
        tail.prev = tail.prev.prev;
        return popped.value;
    }

    void unshift(T value) {
        Element<T> newNode = new Element<>(value, head, head.next);
        newNode.next.prev = newNode;
        head.next = newNode;
    }

    T shift() {
        Element<T> shifted = head.next;
        shifted.next.prev = head;
        head.next = head.next.next;
        return shifted.value;
    }
}
