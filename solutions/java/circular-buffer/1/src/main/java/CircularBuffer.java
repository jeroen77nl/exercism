class CircularBuffer<T> {

    private final T[] b;

    private final int cap;
    private int size = 0;
    private int lastWritten = 0;
    private int firstWritten = 0;

    @SuppressWarnings("unchecked")
    CircularBuffer(final int size) {
        b = (T[]) new Object[size];
        cap = size;
    }

    T read() throws BufferIOException {
        if (size == 0) {
            throw new BufferIOException("Tried to read from empty buffer");
        }

        T value = b[firstWritten];
        firstWritten = increment(firstWritten);
        size--;
        return value;
    }

    void write(T data) throws BufferIOException {
        if (size == cap) {
            throw new BufferIOException("Tried to write to full buffer");
        }

        if (size == 0) {
            firstWritten = increment(firstWritten);
        }
        lastWritten = increment(lastWritten);
        b[lastWritten] = data;
        size++;
    }

    void overwrite(T data) {
        if (size < cap) {
            try {
                write(data);
            } catch (BufferIOException _) {
            }
        } else {
            b[firstWritten] = data;
            firstWritten = increment(firstWritten);
        }
    }

    void clear() {
        size = 0;
        firstWritten = 0;
        lastWritten = 0;
    }

    private int increment(int index) {
        if (++index > cap - 1) {
            return 0;
        } else {
            return index;
        }
    }

}