class ArmstrongNumbers {

    boolean isArmstrongNumber(int numberToCheck) {

        // 153 = 1^3 + 5^3 + 3^3 = 1 + 125 + 27 = 153

        int[] arr = s.chars()
                .map(c -> c - '0')
                .toArray();

        int sum = 0;
        for (int i = 1; i <= arr.length ; i++) {
            sum += (int) Math.pow(arr[i], arr.length);
        }

        int value = Integer.parseInt(numberToCheck);

        return sum == value;
    }
}
