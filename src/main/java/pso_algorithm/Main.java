package pso_algorithm;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Main {

  private static final DecimalFormat df = new DecimalFormat("0.0000000000");

  public static void printCost(ArrayList<Particle> particles){
    String txt = "";
    for (Particle p : particles){
      txt += "cost: "+p.getCost()+" | coord: "+df.format(p.getCoordinate().get(0))+" "+df.format(p.getCoordinate().get(1))+
          " | bestc: "+df.format(p.getBestCoordinate().get(0))+" "+df.format(p.getBestCoordinate().get(1))+
          " | v:"+df.format(p.getVelocity().get(0))+" "+df.format(p.getVelocity().get(1))+"\n";
    }
    System.out.println(txt);
  }

  public static void main(String[] args) {
    PSO p = PSO.getInstance();
    p.setSelfEsteem(0.6);
    p.setSocialEsteem(0.3);
    p.setInertiaCoefficient(0.3);
    double winHeight = 100;
    double winWidth = 100;
    p.setWindowHeight(winHeight);
    p.setWindowWidth(winWidth);
    p.setTarget(10.0,90.0);
    p.createSwarm(60,  10.0, 1.0);
    ArrayList<Particle> particles;
    particles = p.getSwarm();
    for (int i = 0; i<1000; i++){
      p.algorithmStep();
    }

    Collections.sort(particles, new Comparator<Particle>() {
      @Override
      public int compare(Particle o1, Particle o2) {
        if (o1.getCost() < o2.getCost()){
          return -1;
        }else if (o1.getCost() == o2.getCost()){
          return 0;
        }else{
          return 1;
        }
      }
    });
    printCost(particles);
  }
}

