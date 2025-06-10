package ass;
/*
 * This is the Question interface.
 * This interface will be implemented by the MCQQuestion class.
 * 
 */

public interface Question { 

    /**
     * method for getting question.
     * @return
     */
    String getQuestion();


    /**
     * method for getting all answer options.
     * @return
     */
    String[] getAllAnswers();


    /**
     * method for getting the correct answer.
     * @return
     */
    String getCorrectAnswer(); 


    
}
