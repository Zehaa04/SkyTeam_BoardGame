package model;

import model.tasks.*;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Board {

    private final FlightPlan flightPlan = new FlightPlan();
    private int coffee;
    private int reroll;
    private final List<Task> tasks;
    private int maxSpeedBorder;
    private int minSpeedBorder;
    private int brakeStrenght;
    private int currentPosition;
    private int currentAxis;
    private int currentSpeed;
    private Altitude altitude;
    private final Set<Role> pilotSet = new HashSet<>(List.of(Role.PILOT));
    private final Set<Role> copilotSet = new HashSet<>(List.of(Role.COPILOT));
    private Player pilot;
    private Player copilot;

    public Board(Plan plan) throws IOException {
        this.pilot = new Player(Role.PILOT,this);
        this.copilot = new Player(Role.COPILOT,this);
        importFlightPlan(plan);
        tasks = new ArrayList<>();
        this.maxSpeedBorder = 8;
        this.minSpeedBorder = 5;
        this.brakeStrenght = 0;
        this.currentPosition = 0;
        this.currentAxis = 0;
        this.altitude = new Altitude();

        SpeedTask speedTaskPilot = new SpeedTask(pilotSet); //Pre coded Sets??
        SpeedTask speedTaskCopilot = new SpeedTask(copilotSet);

        AxisTask axisTaskPilot = new AxisTask(pilotSet);
        AxisTask axisTaskCopilot = new AxisTask(copilotSet);

        RadioTask radioTaskPilot = new RadioTask(pilotSet);
        RadioTask radioTaskCopilot1 = new RadioTask(copilotSet);
        RadioTask radioTaskCopilot2 = new RadioTask(copilotSet);

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

    public ArrayList<Task> getAvailableTasks(Set<Role> roles) {
        return tasks.stream()
                .filter(task -> task.isRoleAllowed(roles))
                .filter(task -> !task.isUsed())
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public boolean contributeToTask(int diceValue, Task task, Player player) {
        boolean isObligatoryTask = task.isObligatory();

        if (mustUseObligatoryTasks(player) && !isObligatoryTask) {
            System.out.println("You must use your remaining dice for obligatory tasks!");
            return false;
        }

        if (isTaskOutOfOrder(task, player)) {
            System.out.println("You must complete tasks in the correct order!");
            return false;
        }

        if (task.isRoleAllowed(new HashSet<>(List.of(player.getRole())))
                && task.getDiceValue() == null
                && task.isDiceValueValid(diceValue)
                && player.getDice().getRolledValues().contains(diceValue)) {
            task.setDiceValue(diceValue);
            task.setUsed(true);
            task.triggerAction(this);
            player.getDice().getRolledValues().remove((Integer) diceValue);
            return true;
        }

        System.out.println("Invalid placement. Please choose again.");
        return false;
    }

    private boolean mustUseObligatoryTasks(Player player) {
        List<Task> obligatoryTasks = tasks.stream()
                .filter(task -> task.isObligatory() && task.isRoleAllowed(new HashSet<>(List.of(player.getRole()))))
                .toList();

        int unusedObligatoryTasks = (int) obligatoryTasks.stream().filter(task -> !task.isUsed()).count();
        int remainingDice = player.getDice().getRolledValues().size();

        return remainingDice <= unusedObligatoryTasks;
    }

    private boolean isTaskOutOfOrder(Task task, Player player) {
        if (task instanceof BrakesTask || task instanceof FlapsTask) {
            List<Task> orderedTasks = getOrderedTasks(task, player);
            if (orderedTasks != null) {
                int taskIndex = orderedTasks.indexOf(task);
                if (taskIndex > 0) {
                    Task previousTask = orderedTasks.get(taskIndex - 1);
                    return !previousTask.isUsed();
                }
            }
        }
        return false;
    }


    private List<Task> getOrderedTasks(Task task, Player player) {
        if (task instanceof BrakesTask) {
            return tasks.stream()
                    .filter(t -> t instanceof BrakesTask && t.isRoleAllowed(new HashSet<>(List.of(player.getRole()))))
                    .collect(Collectors.toList());
        } else if (task instanceof FlapsTask) {
            return tasks.stream()
                    .filter(t -> t instanceof FlapsTask && t.isRoleAllowed(new HashSet<>(List.of(player.getRole()))))
                    .collect(Collectors.toList());
        }
        return null;
    }


    public void resetRoundSpecificTasks() {
        tasks.stream()
                .filter(task -> task instanceof SpeedTask || task instanceof AxisTask || task instanceof RadioTask || task instanceof CoffeeTask)
                .forEach(task -> task.setUsed(false));
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

    public Altitude getAltitude() {
        return altitude;
    }

    public void addRerollToken() {
        this.reroll++;
    }

    public void useRerollToken() {
        this.reroll--;
    }

    public int getReroll() {
        return reroll;
    }

    public Player getPilot() {
        return pilot;
    }

    public Player getCopilot() {
        return copilot;
    }
}
