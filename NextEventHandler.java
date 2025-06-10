package ass;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;


/**
 * Next button event handler.
 */
public class NextEventHandler implements EventHandler<ActionEvent> {

    // members
    private VBox question_vb;
    private VBox answers_vb;
    private VBox all_vb;
    private QuizQuestions quiz_question;
    private int currentIndex;
    private Stage stage;
    public static TextField tf;
    private ToggleGroup tg;
    private Label question_label;
    public static String send;
    public static ArrayList<String> wrong_questions = new ArrayList<>();
;
    

    /**
     * Constructor 
     * @param stage
     * @param currentIndex
     * @param all_vb
     * @param question_vb
     * @param answers_vb
     * @param tg
     * @param quiz_question
     * @param question_label
     * @param tf
     */
    public NextEventHandler(Stage stage, int currentIndex, VBox all_vb, VBox question_vb,VBox answers_vb, 
    ToggleGroup tg,QuizQuestions quiz_question, Label question_label, TextField tf){
        this.currentIndex = currentIndex;
        this.all_vb = all_vb;
        this.question_vb = question_vb;
        this.answers_vb = answers_vb;
        this.stage = stage;
        this.tg = tg;
        this.tf = tf;
        this.quiz_question = quiz_question;
        this.question_label = question_label;
    }


    /**
     * Handler method for the next button.
     * First it calls the get_chosen() method to retrieve which answer option the user has chosen.
     * It compares that answer with the question's actual answer. If it is correct, 10 points are added to the score.
     * Also updates the currentIndex each time the button is clicked. Once that is done, again the currentIndex
     * is compared to the amount of questions we have in the array. If it is smaller, then we create a new object
     * of StartQuizEventHandler and call the display_question() method. If it is not smaller, this means all the questions
     * have been displayed so it calls the end_quiz() function. 
     * @param arg0
     */
    
    @Override
    public void handle(ActionEvent arg0) {
    
        Question current_question = quiz_question.selectedQuestions.get(currentIndex);
        String chosen_option = get_chosen(tg);
    
        if (chosen_option == null) {
            System.out.println("No option selected!");
            wrong_questions.add(current_question.getQuestion());} // add the question to the wrong questions list.
        else {
            //System.out.println("Selected option: " + chosen_option);
            if (current_question.getCorrectAnswer().contains(chosen_option)) {
                StartQuizEventHandler.quiz_score += 10;
                //System.out.println(StartQuizEventHandler.quiz_score);
            }else {
                System.out.println("Wrong answer!");
                //System.out.println("Question:"+current_question.getQuestion());
                wrong_questions.add(current_question.getQuestion());
            }
        }
    
        // incrementing index after checking if answer is correct.
        currentIndex++;
    
        if (currentIndex < quiz_question.selectedQuestions.size()) {
            StartQuizEventHandler startQuizEH = new StartQuizEventHandler(stage, tf);
            startQuizEH.display_question(all_vb, question_vb, answers_vb, question_label, quiz_question, currentIndex, tg);
        } else {
            end_quiz(all_vb, question_label, stage, tf);
        }
    }
     
            

    

    /**
     * Method that clears the VB and adds the label 'Quiz Completed!' and button 'View Leaderboard'
     * @param all_vb
     * @param question_label
     * @param stage
     * @param tf
     */

    public static void end_quiz(VBox all_vb, Label question_label, Stage stage, TextField tf){
        int final_score = StartQuizEventHandler.quiz_score;
        all_vb.getChildren().clear(); // clear the VBox so we can display the quiz completed text and view leaderboard button.
        question_label.setText("Quiz Completed!");
        Button view_leaderboard = new Button("View Leaderboard");
        view_leaderboard.setFont(Font.font("Bodoni MT", 18));
        view_leaderboard.setBackground(new Background(new BackgroundFill(Color.valueOf("#FFF0F1"),CornerRadii.EMPTY, Insets.EMPTY)));
        view_leaderboard.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, new CornerRadii(20), BorderStroke.THIN)));

        int time = Integer.valueOf(TimerThread.getTime());
        int total_time = 120-time;
        System.out.println("Time takenn: "+ total_time);
        send = tf.getText() + ":"+final_score + ":"+total_time;
        System.out.println("To send to server: " +send);

        // creating an object of client and running it when end_quiz is called.
        RandomClient r = new RandomClient(send,stage);
        try {
            r.start_client();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    
    
    /**
     * Method to get the user's selected answer. 
     * @param tg
     * @return
     */
    public String get_chosen(ToggleGroup tg){

    RadioButton selected_button = (RadioButton) tg.getSelectedToggle();
    if(selected_button!= null){
        String chosen = selected_button.getText(); 
        return chosen; // return the text of the chosen answer.
    }
    return null; // otherwise, return null.
        
    }

    public static int updateScore(Question question, String chosenAnswer) {
        if (question.getCorrectAnswer().contains(chosenAnswer)) {
            return 10;
        }
        return 0;
    }

    
}
