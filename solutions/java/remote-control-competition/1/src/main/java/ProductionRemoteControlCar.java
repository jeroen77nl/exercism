class ProductionRemoteControlCar implements RemoteControlCar, Comparable<ProductionRemoteControlCar> {

    private int distanceTravelled = 0;
    private int numberOfVictories;;

    public void drive() {
        distanceTravelled += 10;        
    }

    public int getDistanceTravelled() {
        return this.distanceTravelled;
    }

    public int getNumberOfVictories() {
        return this.numberOfVictories;
    }

    public void setNumberOfVictories(int numberOfVictories) {
        this.numberOfVictories = numberOfVictories;
    }

    public int compareTo(ProductionRemoteControlCar otherCar) {
        return - Integer.valueOf(this.getNumberOfVictories()).compareTo(otherCar.getNumberOfVictories());
    }
}
