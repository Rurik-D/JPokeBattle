module it.ml.jpokebattle {
    requires javafx.controls;
    requires javafx.fxml;


    opens it.ml.jpokebattle to javafx.fxml;
    exports it.ml.jpokebattle;
}