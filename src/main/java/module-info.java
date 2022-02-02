module pl.wzyla {
    requires javafx.controls;
    requires javafx.fxml;

    opens pl.wzyla to javafx.fxml;
    exports pl.wzyla;
}