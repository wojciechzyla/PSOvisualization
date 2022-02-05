package pl.wzyla;

import java.util.ArrayList;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import pso_algorithm.PSO;
import pso_algorithm.Particle;

public class Simulation {
  private static Simulation firstInstance = null;
  private boolean running = false;
  private final Timeline timeline;
  private PSO pso;
  AnchorPane animationField;

  Simulation(){
    this.timeline = new Timeline(new KeyFrame(Duration.millis(20), this::simulationStep));
    this.timeline.setCycleCount(Timeline.INDEFINITE);
  }

  public static Simulation getInstance(){
    if (firstInstance == null){
      firstInstance = new Simulation();
    }
    return firstInstance;
  }

  private void simulationStep(ActionEvent e) {
    pso.algorithmStep();
    ArrayList<Particle> particles = pso.getSwarm();
    ObservableList<Node> partAnim = animationField.getChildren();
    Node particle;
    for (int i = 0; i < particles.size(); i++){
      particle = partAnim.get(i);
      particle.setLayoutX(particles.get(i).getCoordinate().get(0));
      particle.setLayoutY(particles.get(i).getCoordinate().get(1));
      animationField.getChildren().set(i, particle);
    }
  }

  public void engage(PSO pso, AnchorPane animationField){
    this.pso = pso;
    this.animationField = animationField;
    ArrayList<Circle> result = new ArrayList<Circle>();
    this.pso.createSwarm();
    for (Particle p : this.pso.getSwarm()){
      Circle c = new Circle(5);
      c.setLayoutX(p.getCoordinate().get(0));
      c.setLayoutY(p.getCoordinate().get(1));
      c.setViewOrder(1);
      result.add(c);
    }
    this.animationField.getChildren().addAll(result);
    running = true;
  }

  public void spread(){
    pso.spreadSwarm();
  }

  public void run(){
    timeline.play();
  }

  public void stop (){
    running = false;
    timeline.stop();
    pso.resetTarget();
    pso.resetSwarm();
  }

  public boolean isRunning(){
    return running;
  }

  public void changeTarget(double x, double y){
    pso.setTarget(x, y);
  }
}
