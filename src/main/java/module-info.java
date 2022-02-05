module pl.wzyla {
    requires javafx.controls;
    requires javafx.fxml;
  requires java.desktop;

  opens pl.wzyla to javafx.fxml;
    exports pl.wzyla;
}