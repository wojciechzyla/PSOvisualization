package pl.wzyla;

import java.util.ArrayList;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import pso_algorithm.PSO;
import pso_algorithm.Particle;

public class Simulation {
  private static Simulation firstInstance = null;
  private boolean running = false;

  Simulation(){};

  public static Simulation getInstance(){
    if (firstInstance == null){
      firstInstance = new Simulation();
    }
    return firstInstance;
  }

  public void run (){
    running = true;
  }

  public void stop (){
    running = false;
  }

  public boolean isRunning(){
    return running;
  }

  public void initial (PSO pso, AnchorPane animationField){
    ArrayList<Circle> result = new ArrayList<Circle>();
    pso.createSwarm();
    for (Particle p : PSO.getInstance().getSwarm()){
      Circle c = new Circle(5);
      c.setLayoutX(p.getCoordinate().get(0));
      c.setLayoutY(p.getCoordinate().get(1));
      result.add(c);
    }
    animationField.getChildren().addAll(result);
  }
}
