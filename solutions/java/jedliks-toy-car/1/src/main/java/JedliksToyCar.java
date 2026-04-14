public class JedliksToyCar {

    private int distance = 0;
    private int remainingBattery = 100;

    public static JedliksToyCar buy() {
        return new JedliksToyCar();
    }

    public String distanceDisplay() {
        return "Driven %d meters".formatted(this.distance);        
    }

    public String batteryDisplay() {
        if (this.remainingBattery > 0)
            return "Battery at %d%%".formatted(this.remainingBattery);
        else
            return "Battery empty";
    }

    public void drive() {
        if (this.remainingBattery > 0) {
            this.distance += 20;
            this.remainingBattery--;
        }
    }
}
