package model;

import model.tasks.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Board {

    private FlightPlan flightPlan = new FlightPlan();
    private int coffee;
    private final List<Task> tasks;
    private int maxSpeedBorder;
    private int minSpeedBorder;
    private int brakeStrenght;
    private int currentPosition;
    private int currentAxis;
    private int currentSpeed;

    public Board(Plan plan) throws IOException {
        importFlightPlan(plan);
        tasks = new ArrayList<>();
        this.maxSpeedBorder = 8;
        this.minSpeedBorder = 5;
        this.brakeStrenght = 0;
        this.currentPosition = 0;
        this.currentAxis = 0;

        SpeedTask speedTaskPilot = new SpeedTask(new Role[]{Role.PILOT});
        SpeedTask speedTaskCopilot = new SpeedTask(new Role[]{Role.COPILOT});

        AxisTask axisTaskPilot = new AxisTask(new Role[]{Role.PILOT});
        AxisTask axisTaskCopilot = new AxisTask(new Role[]{Role.COPILOT});

        RadioTask radioTaskPilot = new RadioTask(new Role[]{Role.PILOT});
        RadioTask radioTaskCopilot1 = new RadioTask(new Role[]{Role.COPILOT});
        RadioTask radioTaskCopilot2 = new RadioTask(new Role[]{Role.COPILOT});

        BrakesTask brakesTaskPilot2 = new BrakesTask(2);
        BrakesTask brakesTaskPilot4 = new BrakesTask(4);
        BrakesTask brakesTaskPilot6 = new BrakesTask(6);

        LandingGearTask landingGearTaskPilot12 = new LandingGearTask(1, 2);
        LandingGearTask landingGearTaskPilot34 = new LandingGearTask(3, 4);
        LandingGearTask landingGearTaskPilot56 = new LandingGearTask(5, 6);

        FlapsTask flapsTaskCopilot12 = new FlapsTask(1, 2);
        FlapsTask flapsTaskCopilot23 = new FlapsTask(2, 3);
        FlapsTask flapsTaskCopilot45 = new FlapsTask(4, 5);
        FlapsTask flapsTaskCopilot56 = new FlapsTask(5, 6);

        CoffeeTask coffeeTask1 = new CoffeeTask();
        CoffeeTask coffeeTask2 = new CoffeeTask();
        CoffeeTask coffeeTask3 = new CoffeeTask();

        tasks.add(speedTaskPilot);
        tasks.add(speedTaskCopilot);
        tasks.add(axisTaskPilot);
        tasks.add(axisTaskCopilot);
        tasks.add(radioTaskPilot);
        tasks.add(radioTaskCopilot1);
        tasks.add(radioTaskCopilot2);
        tasks.add(brakesTaskPilot2);
        tasks.add(brakesTaskPilot4);
        tasks.add(brakesTaskPilot6);
        tasks.add(landingGearTaskPilot12);
        tasks.add(landingGearTaskPilot34);
        tasks.add(landingGearTaskPilot56);
        tasks.add(flapsTaskCopilot12);
        tasks.add(flapsTaskCopilot23);
        tasks.add(flapsTaskCopilot45);
        tasks.add(flapsTaskCopilot56);
        tasks.add(coffeeTask1);
        tasks.add(coffeeTask2);
        tasks.add(coffeeTask3);
    }

    private void importFlightPlan(Plan plan) throws IOException {
        flightPlan.importFlightPlan(plan);
    }

    public int getCoffee() {
        return coffee;
    }

    public void setCoffee(int coffee) {
        this.coffee = coffee;
    }

    public Task[] getAvailableTasks(Role... roles) { //liste zurückgeben
        return tasks.stream()
                .filter(task -> task.isRoleAllowed(roles))
                .filter(task -> !task.isUsed())
                .toArray(Task[]::new);
    }

    public boolean contributeToTask(int diceValue, Task task, Player player) {
        if (task.isRoleAllowed(new Role[]{player.getRole()}) && task.getDiceValue()==null && task.isDiceValueValid(diceValue) && player.getDice().getRolledValues().contains(diceValue)){
            task.setDiceValue(diceValue);
            task.setUsed(true);
            task.triggerAction(this);
            player.getDice().getRolledValues().remove((Integer) diceValue);
            return true;
        }else {
            return false;
        }
    }

    public int getBrakeStrenght() {
        return brakeStrenght;
    }

    public void setBrakeStrenght(int brakeStrenght) {
        this.brakeStrenght = brakeStrenght;
    }

    public int getMinSpeedBorder() {
        return minSpeedBorder;
    }

    public void setMinSpeedBorder(int minSpeedBorder) {
        this.minSpeedBorder = minSpeedBorder;
    }

    public int getMaxSpeedBorder() {
        return maxSpeedBorder;
    }

    public void setMaxSpeedBorder(int maxSpeedBorder) {
        this.maxSpeedBorder = maxSpeedBorder;
    }

    public int getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(int currentPosition) {
        this.currentPosition = currentPosition;
    }

    public FlightPlan getFlightPlan() {
        return flightPlan;
    }

    public int getCurrentAxis() {
        return currentAxis;
    }

    public void setCurrentAxis(int currentAxis) {
        this.currentAxis = currentAxis;
    }

    public void setCurrentSpeed(int totalSpeed) {
        this.currentSpeed=totalSpeed;
    }

    public int getCurrentSpeed() {
        return currentSpeed;
    }
}
