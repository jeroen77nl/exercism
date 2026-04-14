class Fighter {

    boolean isVulnerable() {
        return true;
    }

    int getDamagePoints(Fighter fighter) {
        return 1;
    }
}

class Warrior extends Fighter {

    @Override
    int getDamagePoints(Fighter fighter) {
        if (fighter.isVulnerable())
            return 10;
        else
            return 6;
    }

    @Override
    public String toString() {
        return "Fighter is a Warrior";
    }

    @Override
    public boolean isVulnerable() {
        return false;
    }
}

class Wizard extends Fighter {
    private boolean prepares = false;

    @Override
    public String toString() {
        return "Fighter is a Wizard";
    }

    public void prepareSpell() {
        this.prepares = true;
    }

    @Override
    public boolean isVulnerable() {
        return !this.prepares;
    }

    @Override
    int getDamagePoints(Fighter fighter) {
        if (this.prepares)
            return 12;
        else
            return 3;
    }

}
