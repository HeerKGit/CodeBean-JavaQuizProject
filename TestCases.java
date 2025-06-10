package ass;

import static org.junit.Assert.assertEquals;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;


public class TestCases {

    @Test
    /**
     * test case for checking if MCQQuestion returns the correct answer correctly. 
     */
    public void test_get_MCQAnswer(){

        // setup
        String question = "What data type is NOT a primitive type?";
        String[] choices = {"String", "int", "Boolean", "double"};
        String correct = "String";
        MCQQuestion mcq = new MCQQuestion(question,choices,correct);

        // invoke
        String expected = correct;
        String actual = mcq.getCorrectAnswer();

        // analyse
        assertEquals(expected,actual);

    }

    @Test
    /**
     * test case for loading questions from file and making sure they are unique. 
     * @throws IOException
     */
    public void test_load_questions() throws IOException{

        // setup
        QuizQuestions q = new QuizQuestions();
        q.load("ass\\questionsBase.txt");
        q.selectRandomQuestions(3);
        Set<Question> to_check = new HashSet<>();

        //invoke
        int expected_size = 3;
        
        for(int i = 0; i < q.selectedQuestions.size(); i++){
            to_check.add(q.selectedQuestions.get(i)); // this must work if the questions are unique.

        }

        int actual_size = to_check.size();

        // analyse
        assertEquals(expected_size, actual_size); // if this passes this means that each question is unique because we are using a set.
    }

    /**
     * test case for checking if the score is being updated when the user
     * selects the right answer.
     */
    @Test
    public void test_score_updates_on_correct_answer() {

        // setup
        String questionText = "Java programs are platform independent because of the java virtual machine.";
        String[] options = {"True", "False"};
        String correct = "True";

        TFQQuestion q = new TFQQuestion(questionText, options, correct);
    
        // invoke
        int score = NextEventHandler.updateScore(q, "True");
    
        // analyse
        assertEquals(10, score); // correct answer
    }
} 


