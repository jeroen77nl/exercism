class BankAccount {

    enum Status {UNOPENED, OPENED, CLOSED}

    private Status status = Status.UNOPENED;
    private int balance = 0;

    synchronized void open() throws BankAccountActionInvalidException {
        // re-opening a CLOSED account is allowed
        check(!isOpened(), "Account already open");
        status = Status.OPENED;
    }

    synchronized void close() throws BankAccountActionInvalidException {
        check(isOpened(), "Account not open");
        status = Status.CLOSED;
        balance = 0;
    }

    synchronized int getBalance() throws BankAccountActionInvalidException {
        check(isOpened(), "Account closed");
        return balance;
    }

    synchronized void deposit(int amount) throws BankAccountActionInvalidException {
        check(isOpened(), "Account closed");
        checkNonNegative(amount);
        balance += amount;
    }

    synchronized void withdraw(int amount) throws BankAccountActionInvalidException {
        check(isOpened(), "Account closed");
        checkNonNegative(amount);
        check(amount <= balance, "Cannot withdraw more money than is currently in the account");

        balance -= amount;
    }

    private void check(boolean condition, String message) throws BankAccountActionInvalidException {
        if (!condition) {
            throw new BankAccountActionInvalidException(message);
        }
    }

    private boolean isOpened() {
        return status == Status.OPENED;
    }

    private void checkNonNegative(int amount) throws BankAccountActionInvalidException {
        check(amount >= 0, "Cannot deposit or withdraw negative amount");
    }
}