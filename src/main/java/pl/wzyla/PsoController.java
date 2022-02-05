package pl.wzyla;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import pso_algorithm.PSO;

public class PsoController {
  @FXML
  private Label numSwarmLabel;
  @FXML
  private Slider numSwarmSlider;
  @FXML
  private Label c1Label;
  @FXML
  private Slider c1Slider;
  @FXML
  private Label c2Label;
  @FXML
  private Slider c2Slider;
  @FXML
  private Label wLabel;
  @FXML
  private Slider wSlider;
  @FXML
  private TextField maxSpeedText;
  @FXML
  private Button resetButton;
  @FXML
  private Button startButton;
  @FXML
  private AnchorPane animationField;
  @FXML
  private Button spreadButton;
  @FXML
  private Hyperlink githubLink;
  @FXML
  private Hyperlink linkedinLink;

  private final Circle targetCircle = new Circle(20);
  private final PSO psoAlgorithm = PSO.getInstance();
  private final Simulation simulation = Simulation.getInstance();

  @FXML
  public void initialize(){

    numSwarmSlider.valueProperty().addListener((observable, oldValue, newValue)->{
      numSwarmLabel.setText(Double.toString(newValue.intValue()));
    });
    numSwarmLabel.textProperty().set(Double.toString(numSwarmSlider.valueProperty().get()));

    c1Slider.valueProperty().addListener((observable, oldValue, newValue)->{
      c1Label.setText(Double.toString(newValue.floatValue()));
    });
    c1Label.textProperty().set(Double.toString(c1Slider.valueProperty().get()));

    c2Slider.valueProperty().addListener((observable, oldValue, newValue)->{
      c2Label.setText(Double.toString(newValue.floatValue()));
    });
    c2Label.textProperty().set(Double.toString(c2Slider.valueProperty().get()));

    wSlider.valueProperty().addListener((observable, oldValue, newValue)->{
      wLabel.setText(Double.toString(newValue.floatValue()));
    });
    wLabel.textProperty().set(Double.toString(wSlider.valueProperty().get()));

    startButton.addEventHandler(ActionEvent.ACTION, e -> {
        numSwarmSlider.setDisable(true);
        c1Slider.setDisable(true);
        c2Slider.setDisable(true);
        wSlider.setDisable(true);
        startButton.setDisable(true);
        maxSpeedText.setDisable(true);
        resetButton.setDisable(false);
        spreadButton.setDisable(false);
        psoAlgorithm.setSwarmCount(numSwarmSlider.valueProperty().getValue().intValue());
        psoAlgorithm.setSelfEsteem(c1Slider.valueProperty().getValue().floatValue());
        psoAlgorithm.setSocialEsteem(c2Slider.valueProperty().getValue().floatValue());
        psoAlgorithm.setInertiaCoefficient(wSlider.valueProperty().getValue().floatValue());
        psoAlgorithm.setMaxSpeed(Double.parseDouble(maxSpeedText.getText()));
        psoAlgorithm.setWindowHeight(animationField.getHeight());
        psoAlgorithm.setWindowWidth(animationField.getWidth());
        simulation.engage(psoAlgorithm, animationField);
    });

    resetButton.addEventHandler(ActionEvent.ACTION, e -> {
      numSwarmSlider.setDisable(false);
      c1Slider.setDisable(false);
      c2Slider.setDisable(false);
      wSlider.setDisable(false);
      startButton.setDisable(false);
      maxSpeedText.setDisable(false);
      resetButton.setDisable(true);
      spreadButton.setDisable(true);
      animationField.getChildren().clear();
      simulation.stop();
    });

    spreadButton.addEventHandler(ActionEvent.ACTION, e ->{
      simulation.spread();
    });

    animationField.addEventHandler(MouseEvent.MOUSE_CLICKED, e ->{
      if (simulation.isRunning()){
        targetCircle.setFill(Color.RED);
        targetCircle.setViewOrder(2);
        targetCircle.setLayoutY(e.getY());
        targetCircle.setLayoutX(e.getX());
        if (!animationField.getChildren().contains(targetCircle)){
          animationField.getChildren().add(targetCircle);
          simulation.changeTarget(e.getX(), e.getY());
          simulation.run();
        }
        simulation.changeTarget(e.getX(), e.getY());
      }
    });
  }

  @FXML
  public void goGithub(ActionEvent e) throws URISyntaxException, IOException {
    Desktop.getDesktop().browse(new URI("https://github.com/wojciechzyla/PSOvisualization"));
  }

  @FXML
  public void goLinkedin(ActionEvent e) throws URISyntaxException, IOException {
    Desktop.getDesktop().browse(new URI("https://www.linkedin.com/in/wojciech-zyla/"));
  }
}
