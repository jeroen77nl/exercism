public class CarsAssemble {

    public double productionRatePerHour(int speed) {
        final int PRODUCTION = 221;

        double factor;
        if (speed == 0) {
            factor = 0;
        } else if (speed <= 4) {
            factor = 1;
        } else if (speed <= 8) {
            factor = 0.9;
        } else if (speed <= 9) {
            factor = 0.8;
        } else {
            factor = 0.77;
        }

        return factor * speed * PRODUCTION;
    }

    public int workingItemsPerMinute(int speed) {
        return (int) productionRatePerHour(speed) / 60;
    }
}
