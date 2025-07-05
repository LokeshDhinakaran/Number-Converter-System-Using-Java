import java.util.Scanner;

import Controllers.UserController;
import Models.Statistics;
import Controllers.ConverterController;
import Controllers.StatsController;

public class Main {
    private static final Scanner sc = new Scanner(System.in);
        public static void main(String[] args) {
        MainMenu();
    }
    public static void MainMenu(){
        System.out.println("\nWelcome to number converter system");
        while (true) {
        System.out.print("\nEnter 1 for login");
        System.out.print("\nEnter 2 for Register");System.out.print("\nEnter 3 for Exit");
        int UserOption = Integer.parseInt(sc.nextLine());
        switch (UserOption) {
            case 1 -> LoginMenu();
            case 2 -> RegisterMenu();
            case 3 -> {
                System.out.println("Exiting...");
               
                return;
            }
        
            default->{ 
                System.out.println("Enter an valid option");
            }
             
        }
                break;
        }
        }
    public static void RegisterMenu(){
        System.out.println("Enter the username:");
        String UserName = sc.nextLine();
        System.out.println("\nEnter Pssword");
        String Password = sc.nextLine();
        int uid = UserController.CreateUser(UserName, Password);
        if(uid == -1){
            System.out.println("Creation of user is failed");
            return ; 
        }
       
        FeatureMenu(uid);
    }
    public static void LoginMenu(){
        System.out.println("Enter the username:");
        String UserName = sc.nextLine();
        System.out.println("\nEnter Pssword");
        String Password = sc.nextLine();
        int uid = UserController.verifyUser(UserName,Password);
         if(uid == -1){
            System.out.println("Creation of user is failed");
            return ;
        }
        FeatureMenu(uid);
       

    }
    public static void FeatureMenu(int uid) {
         while (true) {
            System.out.print("\nEnter 1 for Converting a number from the source base to target base");
            System.out.print("\nEnter 2 for Conversion Stats of the current session");
            System.out.print("\nEnter 3 for Conversion Stats of the history");
            System.out.print("\nEnter 4 for Undo the last conversion in the current session");
            System.out.print("\nEnter 9 for exit");
            int UserOption = sc.nextInt();
            sc.nextLine();
            switch (UserOption) {
                case 1 -> {
                    System.out.println("Enter number for conversion");
                    String UserValue = sc.nextLine();
                    System.out.println("Enter base Source");
                    int UserSourceBase = sc.nextInt();
                    System.out.println("Enter target Source");
                    int TargetBase = sc.nextInt();
                    String res =ConverterController.convert(uid,UserValue,UserSourceBase,TargetBase);
                    System.out.println(res);
                } 
                case 2 ->{
                    System.out.println("printing current session stats:");
                    StatsController.printCurrentSessionConversionStats(uid);

                }
                case 3->{
                    System.out.println("printing the hisatory stats");
                    StatsController.printhistory(uid);

                }

                case 4 ->{
                    if(ConverterController.UndoLastConversion(uid)){
                        System.out.println("Undo Successfully completed");  
                    }
                    else{
                        System.out.println("undo operations can not be performed");
                    }
                }

                case 9 -> {
                    System.out.println("printing the current session stats beforee logging out");
                     StatsController.printCurrentSessionConversionStats(uid);
                    UserController.SaveCurrentSessionConversions(uid);
                    System.out.println("Program Exitting by saiving the current session conversions to history");
                   
                }
            }
            break;
    }
   
   
    }
     
}
