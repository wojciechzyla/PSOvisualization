package pso_algorithm;

import java.util.ArrayList;
import java.util.Arrays;

public class Particle extends Point{
  private ArrayList<Double> velocity = new ArrayList<>();
  private ArrayList<Double> bestCoordinate = new ArrayList<>();
  private double cost;
  private double bestCost;

  Particle(Double x, Double y, Double vx, Double vy) {
    super(x, y);
    velocity.add(vx);
    velocity.add(vy);
    bestCoordinate.add(x);
    bestCoordinate.add(y);
    cost = Double.MAX_VALUE;
    bestCost = Double.MAX_VALUE;
  }

  public ArrayList<Double> getVelocity() {
    return velocity;
  }

  public void setVelocity(Double vx, Double vy) {
    this.velocity = new ArrayList<>(Arrays.asList(vx, vy));
  }

  public ArrayList<Double> getBestCoordinate() {
    return bestCoordinate;
  }

  public void setBestCoordinate(Double x, Double y) {
    this.bestCoordinate = new ArrayList<>(Arrays.asList(x, y));
  }

  public double getCost() {
    return cost;
  }

  public void setCost(double cost) {
    this.cost = cost;
  }

  public void setBestCost(double bestCost) {
    this.bestCost = bestCost;
  }

  public double getBestCost() {
    return bestCost;
  }
}

