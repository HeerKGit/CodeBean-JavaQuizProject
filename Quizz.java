package ass;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.HBox;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;


/**
 * Main class that creates the Quiz GUI with JavaFX and uses the StartQuizEventHandler to begin the quiz. 
 */
public class Quizz extends Application{

    /**
     * This method bascically sets the entire stage and puts the scene on the stage with several graphical elements.
     * Here, we create 2 separate VBoxes: one for the rules and one is the main that carries the rules VBox and other elements
     * like the title, username lable, textfield and start quiz button.
     * @param stage
     */
    @Override
    public void start(Stage stage){
        
        VBox vb= new VBox(10);
        vb.setAlignment(Pos.TOP_CENTER);
        vb.setStyle("-fx-background-color: #D8B28D;");

        Image bear = new Image(getClass().getResourceAsStream("/beanie.png")); // adjust path if needed
        ImageView bearView = new ImageView(bear);
        bearView.setFitWidth(80);  
        bearView.setFitHeight(80);


        Scene scene = new Scene(vb, 800,715);
        scene.setCursor(cursor_method());
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("Quiz GUI");
        stage.show();

        Label title = new Label("Quiz");
        title.setTextFill(Color.valueOf("#58181F"));
        title.setFont(new Font("Stencil", 60));
        String window1color = "#D8B28D";
        String rules_color = "#A25F38";
        title.setBackground(new Background(new BackgroundFill(Color.valueOf(window1color), null, null)));
        title.setAlignment(Pos.CENTER);


        HBox titleBox = new HBox(15); // spacing between image and text
        titleBox.setAlignment(Pos.CENTER);
        titleBox.setPadding(new Insets(20));
        titleBox.getChildren().addAll(title,bearView); // Add the image and text to the HBox


        Label rule_title = labelFactory("Rules:","Stencil", 20,Color.valueOf(rules_color),Pos.BOTTOM_CENTER,15);
        rule_title.setUnderline(true);
        Label first_rule = labelFactory("1. There are 10 questions.", "Bodoni MT", 18, Color.valueOf(rules_color), Pos.CENTER,3);
        Label second_rule = labelFactory("2. The difficulty depends on how much you focus in class!", "Bodoni MT", 18, Color.valueOf(rules_color), Pos.CENTER,5);
        Label third_rule = labelFactory("3. You will have 2 minutes to finish the quiz. ", "Bodoni MT", 18, Color.valueOf(rules_color), Pos.CENTER,3);
        Label fourth_rule = labelFactory("4. You will not be able to go back once you have answered a question.", "Bodoni MT", 18, Color.valueOf(rules_color), Pos.TOP_LEFT,3);
        Label fifth_rule = labelFactory("5. There will be a leaderboard at the end of the quiz.", "Bodoni MT", 18, Color.valueOf(rules_color), Pos.CENTER,3);
        Label good_luck = labelFactory("Good Luck!", "Stencil", 20, Color.valueOf(rules_color), Pos.CENTER,30);

        Label username = labelFactory("Username:", "Stencil", 20, Color.valueOf(window1color), Pos.CENTER, 15);
        TextField user_field = new TextField();
        user_field.setAlignment(Pos.TOP_CENTER);
        user_field.setMaxWidth(200);
        user_field.setFont(Font.font("Bodoni MT", 18));
        user_field.setBackground(new Background(new BackgroundFill(Color.valueOf(window1color),CornerRadii.EMPTY, Insets.EMPTY)));
        user_field.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, new CornerRadii(20), BorderStroke.THICK)));

        Button start_quiz = new Button("Start Quiz!");
        start_quiz.setFont(Font.font("Bodoni MT",FontWeight.EXTRA_BOLD,18));
        start_quiz.setTextFill(Color.valueOf("#000000"));
        start_quiz.setAlignment(Pos.BOTTOM_CENTER);
        start_quiz.setMaxWidth(150);
        start_quiz.setBackground(new Background(new BackgroundFill(Color.valueOf(rules_color),CornerRadii.EMPTY, Insets.EMPTY)));
        start_quiz.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, new CornerRadii(0), BorderStroke.MEDIUM)));

        
        Label inform = new Label("NOTE: A 2-minute timer will begin once you press start quiz.");
        inform.setTextFill(Color.valueOf("#58181F"));
        inform.setFont(Font.font("Bodoni MT",FontWeight.BOLD,14));
        inform.setAlignment(Pos.BOTTOM_CENTER);  
        start_quiz.setOnAction(new StartQuizEventHandler(stage,user_field));
        
    
        VBox rules_vb = new VBox(10);
        rules_vb.setAlignment(Pos.TOP_CENTER);
        rules_vb.getChildren().addAll(rule_title,first_rule,second_rule, third_rule,fourth_rule, fifth_rule,good_luck);
        rules_vb.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, new CornerRadii(5), new BorderWidths(3) )));
        rules_vb.setMaxWidth(700);
        rules_vb.setMaxHeight(280);
        rules_vb.setPadding(new Insets(10));
        rules_vb.setStyle("-fx-background-color: #A25F38;");
        vb.getChildren().addAll(titleBox,rules_vb,username,user_field,start_quiz,inform);
        
    }

    /**
     * Simple method taken from previous lectures to build a label based on paramenters entered.
     * @param text
     * @param font_choice
     * @param font_size
     * @param bg_color
     * @param alignment
     * @param value
     * @return
     */
    public static Label labelFactory(String text,String font_choice, int font_size, Color bg_color, Pos alignment, int value){

        Label label = new Label(text);

        label.setFont(Font.font(font_choice,font_size)); // using font method from Font class as an alternative.
        label.setTextFill(Color.BLACK);

        label.setBackground
        (new Background(new BackgroundFill(bg_color,CornerRadii.EMPTY, Insets.EMPTY)));

        label.setPadding(new Insets(value)); 
        label.setAlignment(alignment);
        return label;
    }    

    public static ImageCursor cursor_method (){
        Image cursor = new Image("cursor.png");
        double hotspotX = cursor.getWidth() / 2;
        double hotspotY = cursor.getHeight() / 2;
        ImageCursor customCursor = new ImageCursor(cursor,hotspotX,hotspotY);
        return customCursor;
    }


    /**
     * Main function that launches the GUI.
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
