package Controllers;

import java.util.Random;
import java.util.Scanner;

import Models.QuizQuestion;

public class QuizController {
    public static int Number_Of_Questions= 5;
    public static Random random = new Random();
    public static Scanner sc = new Scanner(System.in);
    public static void StartQuiz(){
        int Score =0;
        for (int i =0 ; i< Number_Of_Questions;i++){
            QuizQuestion q = QuizController.GenerateQuestion();
            System.out.printf("\n Value : %s  - source base : %d - Targetbasse :%d",q.value,q.sourceBase,q.targetBase);
            String useranswer = sc.nextLine();
            q.verifyAnswer(useranswer);
            if(q.isCorrect){
                System.out.println("The answer was correct");
                Score++;
            }
            else{
                System.out.printf("invalid answer and the actual answer is %s",q.correctAnswer);
            }
        }
        System.out.printf("\nYour Score out of 5  - %d",Score);
    }
    public static QuizQuestion GenerateQuestion(){
        // base -> 2 to 36
        int sourceBase = random.nextInt(2,37);
        int targetBase = random.nextInt(2,37);
        //base 10 -> Source base
        int valueBase10 = random.nextInt(10,1000);
        String value = Integer.toString(valueBase10,sourceBase);
        String answer =  ConverterController.IntegerConversionToTargetBase(value, sourceBase, targetBase);
        return new QuizQuestion(value, answer, sourceBase, targetBase);

    }
    
}