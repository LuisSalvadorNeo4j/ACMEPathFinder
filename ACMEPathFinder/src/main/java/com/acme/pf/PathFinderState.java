package com.acme.pf;

import java.time.ZonedDateTime;

public class PathFinderState {

    public double cost;
    public double requiredCapacity;
    public ZonedDateTime startTime;
    public ZonedDateTime endTime;

    public PathFinderState() {
        this.cost = 0.0d;
        this.requiredCapacity = 0.0d;
        this.startTime = null;
        this.endTime = null;
    }

    public PathFinderState(double cost, double requiredCapacity, ZonedDateTime startTime, ZonedDateTime endTIme) {
        this.cost = cost;
        this.requiredCapacity = requiredCapacity;
        this.startTime = startTime;
        this.endTime = endTIme;
    }

    public double getRequiredCapacity(){
        return requiredCapacity;
    }

    public PathFinderState setRequiredCapacity(double requiredCapacity) {
        this.requiredCapacity = requiredCapacity;

        return this;
    }

    public ZonedDateTime getstartTime(){
        return startTime;
    }

    public void setStartTime(ZonedDateTime startTime){
        this.startTime = startTime;
    }

    public ZonedDateTime getendTime(){
        return endTime;
    }

    public void setEndTime(ZonedDateTime endTime){
        this.endTime = endTime;
    }

    public double getCost() {
        return cost;
    }

    public void incrementCost(double incrementBy) {
        cost = cost + incrementBy;
    }

    public PathFinderState copy() {
        double requiredCapacity = getRequiredCapacity();
        double cost = getCost();
        ZonedDateTime startTime = getstartTime();
        ZonedDateTime endTime = getendTime();

        return new PathFinderState (cost, requiredCapacity, startTime, endTime );
    }
}
