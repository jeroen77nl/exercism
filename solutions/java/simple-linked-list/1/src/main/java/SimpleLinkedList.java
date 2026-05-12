import java.lang.reflect.Array;
import java.util.NoSuchElementException;

class SimpleLinkedList<T> {

    private static final class Element<T> {
        private final T value;
        private Element<T> next;

        Element(T value, Element<T> next) {
            this.value = value;
            this.next = next;
        }
    }

    private final Element<T> head = new Element<>(null, null);
    private int size = 0;

    SimpleLinkedList() {
    }

    SimpleLinkedList(T[] values) {
        for (T value : values) {
            push(value);
        }
    }

    void push(T value) {
        head.next = new Element<>(value, head.next);
        size++;
    }

    T pop() {
        if (size == 0) throw new NoSuchElementException();

        Element<T> popped = head.next;
        head.next = popped.next;
        size--;
        return popped.value;
    }

    void reverse() {
        Element<T> prev = null;
        Element<T> current = head.next;

        while (current != null) {
            Element<T> next = current.next;
            current.next = prev;
            prev = current;
            current = next;
        }

        head.next = prev;
    }

    T[] asArray(Class<T> clazz) {

        @SuppressWarnings("unchecked")
        T[] result = (T[]) Array.newInstance(clazz, size);

        int i = 0;

        Element<T> element = head.next;

        while (element != null) {
            result[i++] = element.value;
            element = element.next;
        }
        return result;
    }

    int size() {
        return size;
    }
}
