package ass;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class ViewIncorrectEventHandler implements EventHandler<ActionEvent>{


    private Stage stage;
    private String send;
    

    /**
     * Constructor for the ViewIncorrectEventHandler class.
     * @param stage  stage to set the scene on.
     * @param send  name, retrieved from this string, to be displayed in the feedback label.
     */

    public ViewIncorrectEventHandler(Stage stage, String send){
        this.stage = stage;
        this.send = send;
    }

    /**
     * handle event method to display the feedback window with the incorrect questions.
     * @param arg0
     */
    @Override
    public void handle(ActionEvent arg0) {

        String window1color = "#D8B28D";
        // creating a new VBox to hold the feedback and incorrect questions vbox.
        VBox incorrectQuestions = new VBox(30);
        incorrectQuestions.setAlignment(Pos.CENTER);
        incorrectQuestions.setStyle("-fx-background-color: #D8B28D;");

    
        Label title = new Label("Feedback");
        title.setFont(new Font("Stencil", 60));
        title.setTextFill(Color.valueOf("#58181F"));;
        
        title.setBackground(new Background(new BackgroundFill(Color.valueOf(window1color), null, null)));
        title.setAlignment(Pos.CENTER);

        // creating an image view for the bear image to be displayed next to the title.
        Image bear = new Image(getClass().getResourceAsStream("/beanie.png")); // adjust path if needed
        ImageView bearView = new ImageView(bear);
        bearView.setFitWidth(60);  
        bearView.setFitHeight(60);

        HBox some_hbox = new HBox(20); // spacing between image and text
        some_hbox.setAlignment(Pos.CENTER);
        some_hbox.setStyle("-fx-background-color: #D8B28D;");
        some_hbox.getChildren().addAll(title, bearView);

        incorrectQuestions.getChildren().add(some_hbox);


        Label feedback = new Label(send.split(":")[0] + ", you got " + NextEventHandler.wrong_questions.size() + " question(s) wrong.");
        feedback.setPrefWidth(300); // or any size
        feedback.setMinWidth(300);  // to center text
        feedback.setAlignment(Pos.CENTER); // to also center text
        feedback.setStyle("-fx-font-family: 'Bodoni MT'; -fx-font-size: 16px; -fx-text-fill: #000000; -fx-font-weight: bold;");
        

        // creating a new VBox to hold the incorrect questions
        VBox new_vb = new VBox(15);
        new_vb.setAlignment(Pos.CENTER);
        new_vb.setStyle("-fx-background-color: #A25F38;");
        new_vb.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, new CornerRadii(0), new BorderWidths(3) )));
        new_vb.setMaxWidth(700);
        new_vb.setMaxHeight(280);


        incorrectQuestions.getChildren().addAll(feedback,new_vb);


        int count = NextEventHandler.wrong_questions.size();
        System.out.println("Number of wrong questions: " + count); // debug statement to check the number of wrong questions.
         // get the number of wrong questions

        // loop to add the incorrect questions to the VBox
        for(int i = 0; i < count; i++) { 
            Label new_label = new Label(i+1 + ". " + NextEventHandler.wrong_questions.get(i));
            System.out.println(NextEventHandler.wrong_questions.get(i));
            new_label.setStyle("-fx-font-family: 'Bodoni MT'; -fx-font-size: 16px; -fx-text-fill: #000000;");
            new_vb.getChildren().add(new_label);
        }


        // custom cursor
        ImageCursor cursor = Quizz.cursor_method();
        Scene quiz_scene = new Scene(incorrectQuestions,800,715);
        quiz_scene.setCursor(cursor); // this sets it to the coffee bean cursor.
        this.stage.setScene(quiz_scene);
        this.stage.setTitle("Quiz GUI");
        this.stage.show();

        
        
        
    }
    
}
