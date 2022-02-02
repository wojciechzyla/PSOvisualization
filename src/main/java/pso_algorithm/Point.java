package pso_algorithm;

import java.util.ArrayList;
import java.util.Arrays;

public class Point {
  protected ArrayList<Double> coordinate = new ArrayList<>();

  Point(Double x, Double y){
    coordinate.add(x);
    coordinate.add(y);
  }

  public ArrayList<Double> getCoordinate() {
    return coordinate;
  }

  public void setCoordinate(Double x, Double y) {
    this.coordinate = new ArrayList<>(Arrays.asList(x, y));
  }
}
