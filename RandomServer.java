package ass;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class RandomServer {

    public static void main(String[] args) throws IOException {
        
        ServerSocket server_socket = new ServerSocket(12345);
        System.out.println("Server started.");
        Socket client = server_socket.accept();
        System.out.println("Client connected!");

        BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
        String to_write = in.readLine(); // String of name:score:time from client

        // writing to file in format name:score:time
        try{
            FileWriter fw = new FileWriter("leaderboard.txt",true); // true allows to append.
            PrintWriter pw = new PrintWriter(fw);
            pw.println(to_write);
            pw.close();
        }
        catch(IOException e){
            System.out.println("Error occurred: " + e);
        }

        // counting the number of lines in the file.
        int count = 0;
        try {
            FileReader fr = new FileReader("leaderboard.txt");
            BufferedReader br = new BufferedReader(fr);
            while(br.readLine() != null) {
                count++;
            }
            br.close();
        }
        catch(IOException e) {
            System.out.println("Error occurred: " + e);
        }

        // creating new arrays of exactly the amount of scores we have, each time. 
        int[] scores = new int[count];
        String[] names = new String[count];
        int[] time_arr = new int[count];
        int index = 0;

        // reading the file again to store the name, score and timer into 3 new arrays. 
        String line;
        String[] arr;
        try{
            FileReader fr = new FileReader("leaderboard.txt");
            BufferedReader br = new BufferedReader(fr);

            while((line=br.readLine())!= null){
                arr = line.split(":");
                names[index] = arr[0];
                scores[index] = Integer.valueOf(arr[1]);
                time_arr[index] = Integer.valueOf(arr[2]);
                index++;
            }
            br.close();
        }
        catch(IOException e){
            System.out.println("Error occurred: " + e);
        }

        // sorting of scores and time.
        for (int i = 0; i < count - 1; i++) {
            for (int j = 0; j < count - 1; j++) {
        
                // if score is lower OR scores are equal AND time is higher, then swap
                if (scores[j] < scores[j + 1] || 
                   (scores[j] == scores[j + 1] && time_arr[j] > time_arr[j + 1])) {
        
                    // swap scores
                    int tempScore = scores[j];
                    scores[j] = scores[j + 1];
                    scores[j + 1] = tempScore;
        
                    // swap times
                    int tempTime = time_arr[j];
                    time_arr[j] = time_arr[j + 1];
                    time_arr[j + 1] = tempTime;
        
                    // swap names
                    String tempName = names[j];
                    names[j] = names[j + 1];
                    names[j + 1] = tempName;
                }
            }
        }

        //creating printwriter to send the string to the client.
        PrintWriter out = new PrintWriter(client.getOutputStream(),true);

        // creating a single string and sending to the client to put in the GUI.
        String server_send = " ";

        for(int j = 0; j < 3; j++){
            server_send += names[j] + ":" + scores[j] + ":" + time_arr[j] + ",";
        }

        out.println(server_send); // sending to client.

        in.close();
        out.close();
        client.close();
        server_socket.close();

        
    }



    
}
