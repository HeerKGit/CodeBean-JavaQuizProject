package ass;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;


/**
 * This class contains a load() method to load questions from a file and selectRandomQuestion() method to 
 * select random question objects (either TFQ or MCQ) from the array that stores the question objects. 
 * 
 */
public class QuizQuestions {

    ArrayList<Question> allQuestions = new ArrayList<>();
    ArrayList<Question> selectedQuestions = new ArrayList<>();

    /**
     * Method for loading question, choices of answers and correct answer from questionBase.txt file.
     * First it initialises  FileReader and BufferedReader to actually connect to the file and do the reading part. 
     * Next it initialises line which would hold a String value and a String array of choices which will be used later on
     * to determine if the choices belong to mcq questions or true/false questions. 
     * Based on that, object of either MCQQuestion or TFQQuestion will be created respectfully. 
     * 
     * @param filename or filepath
     * @throws IOException
     * 
     */
    void load(String filename) throws IOException{

        
        FileReader f_reader = new FileReader(filename);
        BufferedReader b_reader = new BufferedReader(f_reader);

        String line;
        String[] choices;


        while((line = b_reader.readLine()) != null){

            if(line.isBlank() == true) continue; // if line is blank, don't do anything.

            String question = line;

            String answers = b_reader.readLine();
            choices=answers.split(","); // splits the answers based on the comma 
            String corrAnswer = b_reader.readLine();
            //System.out.println(corrAnswer);
            

            if(choices.length > 2){ // checks the length of the choices array. if it is greater than 2 it is for the mcq question.
                MCQQuestion mcq = new MCQQuestion(question, choices, corrAnswer);
                allQuestions.add(mcq); // added the object to the allQuestions array.
            }

            else if(choices.length == 2){ // if length is 2 then it belongs to the true/false question.
                TFQQuestion tfq = new TFQQuestion(question, choices, corrAnswer);
                allQuestions.add(tfq); // added the object to the allQuestions array.
            }

            }

        b_reader.close();
        f_reader.close();
        }

    
    
        /**
         * In this method we pass in the number of random questions we want to generate.
         * We run a while loop until we select the number of questions we want. 
         * The while loop generates a random number which is in the range of the size of allQuestions (currently 10)
         * this is so that we do not get an index out of range error.
         * we then get that object of Question which is on that index.
         * Before we add it to the arraylist of selectedQuestions, we check if the Question object already exists in it to avoid 
         * the same question from being selected again.
         * If it does not exists already, we add it to the selectedQuestions. 
         * @param numberOfQuestions number of random questions user wants to generate. 
         */
        
        void selectRandomQuestions(int numberOfQuestions){

        Random random = new Random();

        while(selectedQuestions.size() < numberOfQuestions){
            int random_index = random.nextInt(allQuestions.size());
            Question random_question = allQuestions.get(random_index);
            

            if (selectedQuestions.contains(random_question) == false){
                selectedQuestions.add(random_question);
            }
        }
       

    }

    } 



