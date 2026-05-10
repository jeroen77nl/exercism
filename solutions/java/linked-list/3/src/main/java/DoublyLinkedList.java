import java.util.NoSuchElementException;

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

    private final Element<T> head;
    private final Element<T> tail;
    private int size = 0;

    DoublyLinkedList() {
        head =  new Element<>(null, null, null);
        tail = new Element<>(null, head, null);
        head.next = tail;
    }

    void push(T value) {
        Element<T> previous = tail.prev;
        Element<T> newNode = new Element<>(value, previous, tail);
        previous.next = newNode;
        tail.prev = newNode;
        size++;
    }

    T pop() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        Element<T> popped = tail.prev;
        popped.prev.next = tail;
        tail.prev = popped.prev;
        size--;
        return popped.value;
    }

    void unshift(T value) {
        Element<T> oldFirst = head.next;
        Element<T> element = new Element<>(value, head, oldFirst);
        oldFirst.prev = element;
        head.next = element;
        size++;
    }

    T shift() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        Element<T> shifted = head.next;
        shifted.next.prev = head;
        head.next = shifted.next;
        size--;
        return shifted.value;
    }

    int size() {
        return size;
    }
    private boolean isEmpty() {
        return head.next == tail;
    }
}
