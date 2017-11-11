package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

public class Controller {

    @FXML
    private Button GameButton;

    public void initialize(){
        System.out.println("LOL");
    }

    @FXML
    private void handleButtonClick(ActionEvent event){
//        if(event.getSource() == ){
            System.out.println("OMG");
//        }
    }
}
