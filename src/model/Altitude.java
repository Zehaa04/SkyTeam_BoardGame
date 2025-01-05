package model;

public class Altitude {

    private int altitude;

    public Altitude() {
        this.altitude = 6;
    }

    public void decreaseAltitude() {
        this.altitude -= 1;
    }

    public int getAltitude() {
        return altitude*1000;
    }

    public boolean hasRerollToken() { //depends on plan
        if (getAltitude() == 6000) {
            return true;
        }
        return false;
    }
}
