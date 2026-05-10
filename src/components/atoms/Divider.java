package src.components.atoms;

import javafx.scene.layout.Region;

public class Divider {
    public static Region create() {
        Region divider = new Region();
        divider.setPrefSize(952, 1);
        divider.setStyle("-fx-background-color: #C9CED6;");
        return divider;
    }
}
