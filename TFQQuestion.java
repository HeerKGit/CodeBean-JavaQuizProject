package ass;
import java.util.Arrays;

/**
 * TFQQuestion class that has getter, setters, toString and equals methods.
 */
public class TFQQuestion implements Question {

    // members
    private String question;
    private String[] answers;
    private String correctAnswer;


    /**
     * Constructor
     * @param question
     * @param answers
     * @param correctAnswer
     */
    public TFQQuestion(String question, String[] answers, String correctAnswer){
        this.question = question;
        this.answers = answers;
        this.correctAnswer = correctAnswer;
    }

    /**
     * getter method for question.
     */
    public String getQuestion() {
        return question;
    }

    /**
     * setter method for question.
     * @param question
     */
    public void setQuestion(String question) {
        this.question = question;
    }

    /**
     * getter method for answer choices.
     */
    public String[] getAllAnswers() {
        return answers;
    }

    /**
     * setter method to set answer choices.
     * @param answers
     */
    public void setAnswers(String[] answers) {
        this.answers = answers;
    }

    /**
     * getter method for correct answer.
     */
    public String getCorrectAnswer() {
        return correctAnswer;
    }

    /**
     * setter method for correct answer. 
     * @param correctAnswer
     */
    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;

    }


    /**
     * equals method to check if one object instance is equal to the another object's instance.
     * @param obj
     */
    public boolean equals(Object obj) 
        {
            if (obj instanceof TFQQuestion)
            {
                TFQQuestion other = (TFQQuestion)obj;
                return (this.question.equals(other.getQuestion())) &&
                (this.answers.equals(other.getAllAnswers())) &&
                (this.correctAnswer.equals(other.getCorrectAnswer()));

            
            }
            return false;
        }

    /**
     * toString method
     */
    @Override
    public String toString() { 
        return "TFQQuestion [question=" + question + ", answers=" + Arrays.toString(answers) + ", correctAnswer="
                + correctAnswer + "]";
    }


        

    }   
