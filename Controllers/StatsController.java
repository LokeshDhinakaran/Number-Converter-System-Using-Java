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
    public static int TotalSuccessfulConversions(ArrayList<Conversion> conversions) {
    int count = 0;
    for (Conversion c : conversions) {
        if (c.result != null && !c.result.isEmpty()) {
            count++;
        }
    }
    return count;
}
    
public static int FailedConversion(ArrayList<Conversion> conversions) {
    int count = 0;
    for (Conversion c : conversions) {
        if (c.errorMessage != null && !c.errorMessage.isEmpty()) {
            count++;
        }
    }
    return count;
}
  
    public static HashMap<String,Integer> getMostUsedBasePairs(ArrayList<Conversion> Conversions , int PairsTobeDisplayed){
        HashMap <String,Integer> basePairFrequency = new HashMap<>();
        for(Conversion c : Conversions){
            String key = String.format("%d -> %d", c.sourceBase,c.targetBase);
            basePairFrequency.put(key,basePairFrequency.getOrDefault(key, 0)+1);
        }
        return commonHelper.sortByValue(basePairFrequency,PairsTobeDisplayed); 
    }
    public static String getmostusedBase(ArrayList<Conversion> conversions, boolean flag){
         HashMap <String,Integer> basesourceFrequency = new HashMap<>();
        for(Conversion c : conversions){
            String key = Integer.toString(flag == true ? c.sourceBase :c.targetBase);
            basesourceFrequency.put(key,basesourceFrequency.getOrDefault(key, 0)+1);
    }
    return commonHelper.sortByValue(basesourceFrequency, 1).entrySet().toString();

}

   public static void printhistory(int UserId) {
    User user = UserController.GetUserById(UserId);
    StatsController.printHistorystatsBydate(user.ConversionHistory); 
}

public static void printHistorystatsBydate(HashMap<LocalDate, ArrayList<Conversion>> history) {
    for (Map.Entry<LocalDate, ArrayList<Conversion>> datecontent : history.entrySet()) {
        ArrayList<Conversion> conversions = datecontent.getValue();

        System.out.printf("\n\n📅 Date: %s", datecontent.getKey());
        System.out.printf("\nTotal successful conversion: %d", StatsController.TotalSuccessfulConversions(conversions));
        System.out.printf("\nTotal failed conversion: %d", StatsController.FailedConversion(conversions));
        System.out.printf("\nMost used source base: %s", StatsController.getmostusedBase(conversions, true));
        System.out.printf("\nMost used target base: %s", StatsController.getmostusedBase(conversions, false));

        HashMap<String, Integer> mostUsedbasePair = getMostUsedBasePairs(conversions, PAirsToBeDisplayed);
        for (Map.Entry<String, Integer> pair : mostUsedbasePair.entrySet()) {
            System.out.printf("\n %s (frequency - %d)", pair.getKey(), pair.getValue());
        }

        System.out.println("\n🔁 Conversions on this date:");
        for (Conversion c : conversions) {
            System.out.println(c);
        }
    }
}



}

