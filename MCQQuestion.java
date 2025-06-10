package ass;
import java.util.Arrays;


/**
 * MCQQuestion class that has getter, setters, toString and equals methods.
 */
public class MCQQuestion implements Question {
    

    // members
    private String question;
    private String[] answers;
    private String correctAnswer;



    // constructor
    public MCQQuestion(String question, String[] choices, String correctAnswer){

        this.question = question;
        this.answers = choices;
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
     * getter method for answer choices array.
     */
    public String[] getAllAnswers() { 
        return answers;
    }

    /**
     * setter method for setting answer choices.
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
     * setter method for setting correct answer.
     * @param correctAnswer
     */
    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    /**
     * toString method
     */
    @Override
    public String toString() { 
        return "MCQQuestion [question=" + question + ", answers=" + Arrays.toString(answers) + ", correctAnswer="
                + correctAnswer + "]";
    }

    /**
     * equals method to check if one object instance is equal to the another object's instance.
     * @param obj
     */
    public boolean equals(Object obj) 
        {
            if (obj instanceof MCQQuestion)
            {
                MCQQuestion other = (MCQQuestion)obj;
                return (this.question.equals(other.getQuestion())) &&
                (this.answers.equals(other.getAllAnswers())) &&
                (this.correctAnswer.equals(other.getCorrectAnswer()));

            
            }
            return false;
        }



    }

    


    

