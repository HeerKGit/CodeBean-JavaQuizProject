package ass;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class RandomClient {

    String send;
    Stage stage;

    public RandomClient(String send, Stage stage){
        this.send = send;
        this.stage = stage;
    }

    public void start_client() throws IOException, UnknownHostException{
        Socket client_socket = new Socket("localhost",12345);
        PrintWriter out = new PrintWriter(client_socket.getOutputStream());
        BufferedReader in = new BufferedReader(new InputStreamReader(client_socket.getInputStream()));

        System.out.println("Received from NextEventHandler"+send);
        out.println(send); // sends the initial string to the server in the format name:score:time
        out.flush();

        String server_response = in.readLine(); // reading server's response.
        System.out.println("Server sent back: "+server_response); 

        // storing each record of the top 3 in a seperate string
        String first_record = server_response.split(",")[0]; // 1st place
        String second_record = server_response.split(",")[1]; // 2nd place
        String third_record = server_response.split(",")[2]; // 3rd place

        // creatin array of size 3 each to add the name, score and time of each player.
        String [] arr1 = new String[3];
        String[] arr2 = new String[3];
        String[] arr3 = new String[3];


        // splitting name, score and time and storing elements in an array.
        for (int z = 0; z < 3;z++){
            arr1[z] = first_record.split(":")[z];
            arr2[z] = second_record.split(":")[z];
            arr3[z] = third_record.split(":")[z];
        }
        // creating the leaderboard GUI.
        VBox lBox = new VBox(15);
        lBox.setAlignment(Pos.CENTER);
        lBox.setStyle("-fx-background-color: #D8B28D;");
        Label leaderboard_label = new Label("Leaderboard");
        leaderboard_label.setFont(Font.font("Stencil", 60));
        leaderboard_label.setTextFill(Color.valueOf("#58181F"));

        

        // vBox to display rank/scores.
        VBox rankvb = new VBox(15);
        rankvb.setAlignment(Pos.CENTER);
        rankvb.setStyle("-fx-background-color: #A25F38;");
        rankvb.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, new CornerRadii(0), new BorderWidths(3) )));
        rankvb.setMaxWidth(700);
        rankvb.setMaxHeight(280);
        rankvb.setPadding(new Insets(10));

        // bear image
        Image bear = new Image(getClass().getResourceAsStream("/beanie.png")); // adjust path if needed
        ImageView bearView = new ImageView(bear);
        bearView.setFitWidth(60);  
        bearView.setFitHeight(60);

        
        HBox some_hbox = new HBox(20); // spacing between image and text
        some_hbox.setAlignment(Pos.CENTER);
        some_hbox.setStyle("-fx-background-color: #D8B28D;");
        some_hbox.getChildren().addAll(leaderboard_label, bearView);
        
        lBox.getChildren().add(some_hbox);


        Label new_label = new Label(arr1[0] + " : " + arr1[1] + " points in " + arr1[2] + "s");
        new_label.setStyle("-fx-font-family: 'Bodoni MT'; -fx-font-size: 16px; -fx-text-fill: #000000; -fx-font-weight: bold;");

        Label new_label2 = new Label(arr2[0] + " : " + arr2[1] + " points in " + arr2[2] + "s");
        new_label2.setStyle("-fx-font-family: 'Bodoni MT'; -fx-font-size: 16px; -fx-text-fill: #000000; -fx-font-weight: bold;");

        Label new_label3 = new Label(arr3[0] + " : " + arr3[1] + " points in " + arr3[2] + "s");
        new_label3.setStyle("-fx-font-family: 'Bodoni MT'; -fx-font-size: 16px; -fx-text-fill: #000000; -fx-font-weight: bold;");

        Button view_wrong_questions = new Button("View Wrong Questions");
        view_wrong_questions.setTextFill(Color.valueOf("#000000"));
        view_wrong_questions.setFont(Font.font("Bodoni MT", FontWeight.BOLD,18));
        view_wrong_questions.setBackground(new Background(new BackgroundFill(Color.valueOf("#A25F38"),CornerRadii.EMPTY, Insets.EMPTY)));
        view_wrong_questions.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, new CornerRadii(0), BorderStroke.THIN)));
        view_wrong_questions.setOnAction(new ViewIncorrectEventHandler(stage,send));

        VBox empty_vbox = new VBox(30);
        empty_vbox.setAlignment(Pos.CENTER);
        empty_vbox.setStyle("-fx-background-color: #D8B28D;");

        
        rankvb.getChildren().addAll(new_label,new_label2,new_label3,empty_vbox,view_wrong_questions);
        lBox.getChildren().addAll(rankvb,empty_vbox,view_wrong_questions);

        
        ImageCursor cursor = Quizz.cursor_method();

        // setting the scene.
        Scene leaderboard_scene = new Scene(lBox,800,715);
        leaderboard_scene.setCursor(cursor); // setting the cursor to the custom cursor.
        stage.setScene(leaderboard_scene);
        stage.setTitle("Quiz GUI");
        stage.show();

        in.close();
        out.close();
        client_socket.close(); 
    }












}
    


    

