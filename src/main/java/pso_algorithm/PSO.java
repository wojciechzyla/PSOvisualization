package pso_algorithm;

import java.util.ArrayList;
import java.util.Random;

public class PSO {
  private static PSO firstInstance = null;
  private TargetPoint target;
  private double inertiaCoefficient;
  private double selfEsteem;
  private double socialEsteem;
  private ArrayList<Particle> swarm = new ArrayList<Particle>();
  private double windowHeight;
  private double windowWidth;
  private double maxSpeed;
  private int swarmCount;

  PSO(){};

  public static PSO getInstance(){
    if (firstInstance == null){
      firstInstance = new PSO();
      firstInstance.inertiaCoefficient = 1;
      firstInstance.selfEsteem = 1;
      firstInstance.socialEsteem = 1;
    }
    return firstInstance;
  }

  public void setSwarmCount(int swarmCount) {
    if (swarmCount < 10) swarmCount = 10;
    this.swarmCount = swarmCount;
  }

  public int getSwarmCount() {
    return swarmCount;
  }

  public void setMaxSpeed(double maxSpeed) {
    this.maxSpeed = Math.abs(maxSpeed);;
  }

  public double getMaxSpeed() {
    return maxSpeed;
  }

  public void setWindowHeight(double windowHeight) {
    this.windowHeight = windowHeight;
  }

  public void setWindowWidth(double windowWidth) {
    this.windowWidth = windowWidth;
  }

  public void setInertiaCoefficient(double inertiaCoefficient) {
    if (inertiaCoefficient <= 0) inertiaCoefficient = 1;
    this.inertiaCoefficient = inertiaCoefficient;
  }

  public double getInertiaCoefficient() {
    return inertiaCoefficient;
  }

  public void setSelfEsteem(double selfEsteem) {
    if (selfEsteem <= 0) selfEsteem = 1;
    this.selfEsteem = selfEsteem;
  }

  public double getSelfEsteem() {
    return selfEsteem;
  }

  public void setSocialEsteem(double socialEsteem) {
    if (socialEsteem <= 0) socialEsteem = 1;
    this.socialEsteem = socialEsteem;
  }

  public double getSocialEsteem() {
    return socialEsteem;
  }

  public void setTarget(Double x, Double y) {
    Particle part;
    for (int i = 0; i< swarm.size(); i++){
      part = swarm.get(i);
      part.resetBestPosition();
      swarm.set(i, part);
    }
    target = new TargetPoint(x, y);
  }

  public TargetPoint getTarget(){
    return target;
  }

  public void resetTarget() {
    target = null;
  }

  public void createSwarm(){
    Random random = new Random();
    double x;
    double y;
    double vx;
    double vy;
    for (int i=0; i< swarmCount; i++) {
      x = windowWidth * random.nextDouble();
      y = windowHeight * random.nextDouble();
      vx = -maxSpeed + 2 * maxSpeed * random.nextDouble();
      vy = -maxSpeed + 2 * maxSpeed * random.nextDouble();
      Particle part = new Particle(x, y, vx, vy);
      swarm.add(part);
    }
  }

  public void resetSwarm(){
    swarm.clear();
  }

  public void setSwarm(ArrayList<Particle> swarm){
    this.swarm = swarm;
  }

  public void setSwarm(int i, Particle part){
    this.swarm.set(i, part);
  }

  public ArrayList<Particle> getSwarm(){
    return swarm;
  }

  public void spreadSwarm() {
    double vx, vy, x, y;
    Particle part;
    int[][] variation = {{-1,-1},{-1,1},{1,1},{1,-1}};
    int j;
    for (int i=0; i< swarmCount; i++) {
      j = i%3;
      part = swarm.get(i);
      vx = variation[j][0] * maxSpeed * 10;
      vy = variation[j][1] * maxSpeed * 10;
      x = part.getCoordinate().get(0)+vx;
      y = part.getCoordinate().get(1)+vy;
      part.setCoordinate(x, y);
      part.setVelocity(vx, vy);
      swarm.set(i, part);
    }
  }

  private double objectiveFunction(Particle particle) {
    double result;
    double squaredX;
    double squaredY;
    squaredX = Math.pow((particle.getCoordinate().get(0) - target.getCoordinate().get(0)), 2);
    squaredY = Math.pow((particle.getCoordinate().get(1) - target.getCoordinate().get(1)), 2);
    result = Math.sqrt(squaredX+squaredY);
    return result;
  }

  public void algorithmStep(){
    if (target != null && !swarm.isEmpty()){
      // Execute algorithm step only if swarm and target exist
      Random random = new Random();
      ArrayList<Particle> helperSwarm = new ArrayList<Particle>();

      Particle part;
      if (swarm.get(0).getCost() == Double.MAX_VALUE){
        // First step after swarm reset
        for (int i=0; i< swarm.size(); i++){
          part = swarm.get(i);
          part.setCost(objectiveFunction(part));
          swarm.set(i, part);
        }
      }

      double functionVal;
      Particle randPart;

      for (int i=0; i< swarm.size(); i++){
        part = swarm.get(i);
        int randomParticle = random.nextInt(swarm.size());
        while (randomParticle == i ){
          randomParticle = random.nextInt(swarm.size());
        }
        randPart = swarm.get(randomParticle);
        double newXV;
        double newYV;
        double newX;
        double newY;
        double r1;
        double r2;
        ArrayList<Double> position = part.getCoordinate();
        ArrayList<Double> bestPosition = part.getBestCoordinate();
        ArrayList<Double> velocity = part.getVelocity();
        ArrayList<Double> bestPosition2 = randPart.getBestCoordinate();
        r1 = random.nextDouble();
        r2 = random.nextDouble();

        // Calculate new velocity in 'x' direction
        newXV = inertiaCoefficient * velocity.get(0) + selfEsteem * r1 * (bestPosition.get(0) - position.get(0))+
            socialEsteem * r2 * (bestPosition2.get(0) - position.get(0));
        if (newXV > maxSpeed) newXV = maxSpeed;
        if (newXV < -maxSpeed) newXV = -maxSpeed;
        // Calculate new velocity in 'y' direction
        newYV = inertiaCoefficient * velocity.get(1) + selfEsteem * r1 * (bestPosition.get(1) - position.get(1))+
            socialEsteem * r2 * (bestPosition2.get(1) - position.get(1));
        if (newYV > maxSpeed) newYV = maxSpeed;
        if (newYV < -maxSpeed) newYV = -maxSpeed;
        part.setVelocity(newXV, newYV);

        // Calculate new 'x' coordinate
        newX = position.get(0) + part.getVelocity().get(0);
        if (newX < 0){
          newX = 0;
        }else if (newX > windowWidth){
          newX = windowWidth;
        }
        // Calculate new 'y' coordinate
        newY = position.get(1) + part.getVelocity().get(1);
        if (newY < 0){
          newY = 0;
        }else if (newY > windowHeight){
          newY = windowHeight;
        }
        part.setCoordinate(newX, newY);
        // Calculate new value of objective function
        functionVal = objectiveFunction(part);
        part.setCost(functionVal);
        if (functionVal < part.getBestCost()){
          // Update best position of particle
          part.setBestCoordinate(newX, newY);
          part.setBestCost(functionVal);
        }
        helperSwarm.add(part);
      }
      swarm = helperSwarm;
    }
  }
}
