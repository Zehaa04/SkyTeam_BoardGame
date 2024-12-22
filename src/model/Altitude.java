package model;

public class Altitude {

    private int altitude;

    Altitude() {
        this.altitude = 6000;
    }

    public void decreaseAltitude() {
        this.altitude -= 1000;
    }

    public int getAltitude() {
        return altitude;
    }

    public boolean hasRerollToken() { //depends on plan
        if (getAltitude() == 6000) {
            return true;
        }
        return false;
    }
}
