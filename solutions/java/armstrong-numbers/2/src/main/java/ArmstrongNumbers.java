class ArmstrongNumbers {

    boolean isArmstrongNumber(int numberToCheck) {

        int[] arr = Integer.toString(numberToCheck).chars()
                .map(c -> c - '0')
                .toArray();

        int sum = 0;
        for (int i = 0; i < arr.length ; i++) {
            sum += (int) Math.pow(arr[i], arr.length);
        }

        return sum == numberToCheck;
    }
}
