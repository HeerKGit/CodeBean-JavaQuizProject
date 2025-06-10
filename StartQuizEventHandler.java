package ass;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
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
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;


/**
 * Event handler class for 'Start Quiz!' button.
 */
public class StartQuizEventHandler implements EventHandler<ActionEvent> {

    // members 
    private Stage stage;
    private TextField tf; 
    public static int currentIndex = 0;
    public static int quiz_score = 0;


    /**
     * Constructor
     * @param stage
     * @param tf
     */
    public StartQuizEventHandler(Stage stage, TextField tf){
        this.stage = stage;
        this.tf = tf;
    }


    /**
     * Handler function for the button (called when the button is pressed). 
     * Creates a new VBox for questions, answer choices and a main VB to store both these VBoxes called all_vb.
     * A ToggleGroup object is created to ensure that the user is able to select only one option to answer each question.
     * Creates a QuizQuestions object and calls the load() method and selectRandomQuestions() method to get the array
     * of selected random questions. Calls the display_question() method to display the questions as the start button is pressed.
     * Also creates the next button and sets its action by calling the NextEventHandler.
     * @param arg0
     */
    @Override
    public void handle(ActionEvent arg0) {


        VBox all_vb = new VBox(15); // this will hold the question_vb and answers_vb.
        all_vb.setAlignment(Pos.CENTER);
        all_vb.setStyle("-fx-background-color: #D8B28D;");

        VBox question_vb = new VBox(30);
        question_vb.setAlignment(Pos.CENTER);
        question_vb.setStyle("-fx-background-color: #D8B28D;");


        VBox answers_vb = new VBox(15);
        answers_vb.setAlignment(Pos.CENTER);
        answers_vb.setStyle("-fx-background-color: #A25F38;");
        answers_vb.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, new CornerRadii(0), new BorderWidths(3) )));
        answers_vb.setMaxWidth(400);  // or even 300
        answers_vb.setMaxHeight(200);
        answers_vb.setPadding(new Insets(20)); // instead of 10


        ToggleGroup tg = new ToggleGroup();
        Button next = new Button("Next");
        next.setFont(Font.font("Bodoni MT",FontWeight.EXTRA_BOLD,15));
        next.setTextFill(Color.valueOf("#000000"));
        next.setBackground(new Background(new BackgroundFill(Color.valueOf("#A25F38"),CornerRadii.EMPTY, Insets.EMPTY)));
        next.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, new CornerRadii(0), BorderStroke.THIN)));
        

        all_vb.getChildren().addAll(question_vb, answers_vb,next);

        Label question_label = new Label();
        question_label.setStyle("-fx-font-family: 'Bodoni MT'; -fx-font-size: 18px; -fx-text-fill: #000000;-fx-font-weight: bold;");

        QuizQuestions quiz_question = new QuizQuestions();
        
        try {
            quiz_question.load("Assignment2\\questionsBase.txt");
        } catch (IOException e) {
            System.out.println("File not found: " + e);
        }

        quiz_question.selectRandomQuestions(10);

        display_question(all_vb,question_vb, answers_vb, question_label,quiz_question,currentIndex, tg);
        next.setOnAction(new NextEventHandler(stage, currentIndex, all_vb, question_vb, answers_vb,tg,quiz_question,question_label,tf));

        Label timer_label = new Label();
        timer_label.setStyle("-fx-font-family: 'Bodoni MT'; -fx-font-size: 15px; -fx-text-fill: #000000;");
        all_vb.getChildren().add(timer_label);
        TimerThread timer = new TimerThread(stage, answers_vb, timer_label, tf, timer_label);
        timer.start(stage);


        // setting the cursor to a custom image.
        ImageCursor cursor = Quizz.cursor_method();
        Scene quiz_scene = new Scene(all_vb,800,715);
        quiz_scene.setCursor(cursor); // setting the cursor to the custom cursor.
        this.stage.setScene(quiz_scene);
        this.stage.setTitle("Quiz GUI");
        this.stage.show();}
                
          
    /**
     * Method to display questions and answer choices on the screen. 
     * Keeps track of the current index and compares it to the amount of random questions we have in the selectedQuestions
     * array ensuring that it displays the right amount of questions. 
     * @param all_vb
     * @param question_vb
     * @param answers_vb
     * @param question_label
     * @param quiz_question
     * @param currentIndex
     * @param tg
     */
    public void display_question(VBox all_vb, VBox question_vb, VBox answers_vb, Label question_label, 
    QuizQuestions quiz_question, int currentIndex, ToggleGroup tg){

        // clear the VBs each time so the new question can be displayed as the next button is clicked.
        question_vb.getChildren().clear(); 
        answers_vb.getChildren().clear();
        
        VBox title_vb = new VBox(15);
        title_vb.setAlignment(Pos.CENTER);
        title_vb.setStyle("-fx-background-color: #58181F;");

        Label title = new Label(" ");
        Image bear = new Image(getClass().getResourceAsStream("/beanie.png")); // adjust path if needed
        ImageView bearView = new ImageView(bear);
        bearView.setFitWidth(60);  
        bearView.setFitHeight(60);

        HBox some_hbox = new HBox(20); // spacing between image and text
        some_hbox.setAlignment(Pos.CENTER);
        some_hbox.setStyle("-fx-background-color: #D8B28D;");
        some_hbox.getChildren().addAll(bearView,title);
        


        if(currentIndex < quiz_question.selectedQuestions.size()){
            Question curr_question = quiz_question.selectedQuestions.get(currentIndex); // selecting question object from selectedQuestions
            question_label.setText((currentIndex+1) +". "+ curr_question.getQuestion()); // taking the actual question and displaying it in the specified format.
            title.setText("Question " + (currentIndex+1) + " of " + quiz_question.selectedQuestions.size());
            title.setTextFill(Color.valueOf("#58181F"));
            title.setFont(new Font("Stencil", 30));


            String[] answer_choices = curr_question.getAllAnswers(); 
            question_vb.getChildren().addAll(some_hbox,question_label);

            for(String option:answer_choices){
                RadioButton rb = new RadioButton(option); // creating a new radio button object for all the choices.
                rb.setStyle("-fx-font-family: 'Bodoni MT'; -fx-font-size: 16px; -fx-text-fill: #000000;");
                rb.setToggleGroup(tg); // allows the user to only select one option. 
                answers_vb.getChildren().addAll(rb);} // adding options to answers_vb. 
            
            }

        }
    
    }
