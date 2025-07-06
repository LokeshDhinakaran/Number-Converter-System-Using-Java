package Controllers;

import Models.Conversion;
import Models.User;

public class ConverterController {
    public static String errorMessage;
    public static boolean isInteger(String value){
        return !value.contains(".");
    }
    public static String IntegerConversionToTargetBase(String UserValue, int UserSourceBase,int TargetBase){
        int ConvertedBase10Value=0;
        String errorMessage = "";
        
        try{
             ConvertedBase10Value = Integer.parseInt(UserValue,UserSourceBase);
            
        }
        catch (NumberFormatException nfe){
            errorMessage = String.format("Error - Please give a valid value for the given base\n %s", nfe.getMessage());
        }
        String ConertedTargetValue = Integer.toString(ConvertedBase10Value,TargetBase);
        return errorMessage.isEmpty() ? ConertedTargetValue : errorMessage;
    }

    public static String DecimalConversionHelper(String UserValue, int UserSourceBase,int TargetBase){
        // .1101
        //2^-1 .... 
        // Source -> base 10
        double ResultBase10Converted = 0.0;
        for (int i = 0; i < UserValue.length(); i++) {
            char c = UserValue.charAt(i);
            int digit = Character.digit(c, UserSourceBase);
            ResultBase10Converted = ResultBase10Converted + digit / Math.pow(UserSourceBase, i+1);

        } 

        //base 10 -> TargetBase
        int precision = 5;
        double frac = ResultBase10Converted;
        String ResultTargetBaseConverted = new String();
        while (frac!=0.0 && precision !=0) {
        //0.3*2 = 0.6
        //0.6*2 = 1.2
        //0.2*2 = 0.4
        frac = frac* TargetBase;
        int digit = (int)frac;
        ResultTargetBaseConverted = ResultTargetBaseConverted + Character.forDigit(digit, TargetBase);
        frac = frac - digit;
        precision--;
        }
        return ResultTargetBaseConverted;
      
    }

    public static String DecimalConversionToTargetBase(String UserValue , int UserSourceBase , int TargetBase){
        String ValueParts[] = UserValue.split("\\.");
        String IntegerPart = IntegerConversionToTargetBase(ValueParts[0], UserSourceBase, TargetBase);
        String FracPart = DecimalConversionHelper(UserValue, UserSourceBase, TargetBase);
        return String.format("%s.%s",IntegerPart, FracPart);

    }
     
    public static String convert(int UserId, String UserValue, int UserSourceBase,int TargetBase) {
        Conversion c = new Conversion(UserValue, UserSourceBase, TargetBase);
        User user = UserController.GetUserById(UserId);
        if(user == null){
            System.out.println("User not found");
            return "";
        }
        user.CurrentSession.add(c);
        if(ConverterController.isInteger(UserValue)){
            //Integer conversion logic
            String res =  ConverterController.IntegerConversionToTargetBase(UserValue, UserSourceBase, TargetBase);
            if(res.contains("error")){
                c.errorMessage = res;
            }
            else{
                c.result= res;
            }
            return res;
        }
        else{ 
            // Integer + fractional logic
           String res = ConverterController.DecimalConversionToTargetBase(UserValue, UserSourceBase, TargetBase);
            if(res.contains("error")){
                c.errorMessage = res;
            }
            else{
                c.result= res;
            }
            return res;
        }
       
    }
    public static boolean undoLastConversion(int userId) {
        User currentUser = UserController.GetUserById(userId);
        if (currentUser.CurrentSession.size() >= 1) {
            currentUser.CurrentSession.remove(currentUser.CurrentSession.size() - 1);
            currentUser.UndoCountInCurrentConversion += 1;
            return true;
        } else {
            return false;
        }
    }
    
}
