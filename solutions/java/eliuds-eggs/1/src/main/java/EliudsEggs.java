public class EliudsEggs {
    public int eggCount(int number) {
        int eggs = 0;
        while (number > 0) {
            if (number % 2 == 1) {
                eggs++;
            }
            number /= 2;
        }
        return eggs;
    }
}
