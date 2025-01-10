package com.skyteam.model.tasks;

import com.skyteam.model.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public abstract class Task {
    protected Set<Role> allowedRoles;
    protected boolean used;
    protected Set<Integer> validDiceValues;
    protected boolean obligatory;
    protected Integer diceValue;

    public Task(Set<Role> allowedRoles, boolean obligatory, Integer... validDiceValues) { //hier kein Array sondern liste nutzen
        this.allowedRoles = allowedRoles;  //OLD: new HashSet<>(Arrays.asList(allowedRoles));
        this.used = false;
        this.validDiceValues = new HashSet<>(Arrays.asList(validDiceValues));
        this.obligatory = obligatory;
        this.diceValue = null;
    }

    public boolean isRoleAllowed(Set<Role> roles) { //Role... roles maybe? Muss dann nicht jedes mal ein Hash bauen
        return allowedRoles.containsAll(roles);
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public boolean isDiceValueValid(int diceValue) {
        return validDiceValues.contains(diceValue);
    }

    public boolean isObligatory() {
        return obligatory;
    }

    public Integer getDiceValue() {
        return diceValue;
    }

    public void setDiceValue(Integer diceValue) {
        this.diceValue = diceValue;
    }

    public abstract void triggerAction(Board board);
}
