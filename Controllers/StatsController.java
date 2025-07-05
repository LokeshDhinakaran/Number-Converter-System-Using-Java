package Controllers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Helper.commonHelper;
import Models.Conversion;
import Models.User;

public class StatsController {
    public static int PAirsToBeDisplayed = 5;
    public static void printCurrentSessionConversionStats(int UserID){
       User user = UserController.GetUserById(UserID); 
        int UndoCountInCurrentConversion = StatsController.UndoCount(user);
        int TotalSuccessfulConversions = StatsController.TotalSuccessfulConversions(user.CurrentSession);
        int TotafailConversions = StatsController.FailedConversion(user.CurrentSession);
        int TotalConversions = TotalSuccessfulConversions+TotafailConversions;
        HashMap<String,Integer> mostUsedbasePair =  getMostUsedBasePairs(user.CurrentSession, PAirsToBeDisplayed);
        for(Map.Entry<String, Integer> pair : mostUsedbasePair.entrySet() ){
            System.out.printf("\n %s (frequency - %d)",pair.getKey(),pair.getValue());
        }
        String mostUsedSourcebase = StatsController.getmostusedBase(user.CurrentSession, true);
        String mostUsedTArgetbase = StatsController.getmostusedBase(user.CurrentSession, false);

        System.out.printf("\nUndo Count In Current Conversion: %d", UndoCountInCurrentConversion);
        System.out.printf("\nTotal Successful Conversions: %d", TotalSuccessfulConversions);
        System.out.printf("\nTotal Fail Conversions: %d", TotafailConversions);
        System.out.printf("\nTotal Conversions: %d", TotalConversions);
        System.out.printf("\nMost Used Source Base: %s", mostUsedSourcebase);
        System.out.printf("\nMost Used Target Base: %s", mostUsedTArgetbase);

    }
     
    public static int UndoCount(User user){
        return user.UndoCountInCurrentConversion;
    }
    public static int TotalSuccessfulConversions(ArrayList<Conversion> Conversions){
        int  Countofsuccessconversion =0;
        for(Conversion c: Conversions){
            if(c.Result != null){
                Countofsuccessconversion+=1;
            }
        }
        return  Countofsuccessconversion;
    }
    public static int FailedConversion(ArrayList<Conversion> Conversions){
        int  Countoffailedconversion =0;
        for (Conversion c : Conversions){
            if(c.ErrorMessage != null){
                Countoffailedconversion+=1;
            }
        }
        return  Countoffailedconversion;
    }
  
    public static HashMap<String,Integer> getMostUsedBasePairs(ArrayList<Conversion> Conversions , int PairsTobeDisplayed){
        HashMap <String,Integer> basePairFrequency = new HashMap<>();
        for(Conversion c : Conversions){
            String key = String.format("%d -> %d", c.SourceBase,c.TargetBase);
            basePairFrequency.put(key,basePairFrequency.getOrDefault(key, 0)+1);
        }
        return commonHelper.sortByValue(basePairFrequency,PairsTobeDisplayed); 
    }
    public static String getmostusedBase(ArrayList<Conversion> conversions, boolean flag){
         HashMap <String,Integer> basesourceFrequency = new HashMap<>();
        for(Conversion c : conversions){
            String key = Integer.toString(flag == true ? c.SourceBase :c.TargetBase);
            basesourceFrequency.put(key,basesourceFrequency.getOrDefault(key, 0)+1);
    }
    return commonHelper.sortByValue(basesourceFrequency, 1).entrySet().toString();

}

   public static void printhistory(int UserId){
        User user = UserController.GetUserById(UserId);
        StatsController.printHistorystatsBydate(user.ConversionHistory);
        StatsController.printHistorystatsBydate(user.ConversionHistory);

     }
     public static void printHistorystatsBydate(HashMap<LocalDate,ArrayList<Conversion>> history){
        for (Map.Entry<LocalDate , ArrayList<Conversion>> datecontent : history.entrySet()){
            System.out.printf("Total successful conversion- %d",StatsController.TotalSuccessfulConversions(datecontent.getValue()));
            System.out.printf("Total failed conversion- %d",StatsController.FailedConversion(datecontent.getValue()));
            System.out.printf("most used source base %s",StatsController.getmostusedBase(datecontent.getValue(), true));
            System.out.printf("most used target base %s",StatsController.getmostusedBase(datecontent.getValue(), false));
             HashMap<String,Integer> mostUsedbasePair =  getMostUsedBasePairs(datecontent.getValue(), PAirsToBeDisplayed);
        for(Map.Entry<String, Integer> pair : mostUsedbasePair.entrySet() ){
            System.out.printf("\n %s (frequency - %d)",pair.getKey(),pair.getValue());
        }
        }
     }
}
