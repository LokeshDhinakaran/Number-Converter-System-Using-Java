package Controllers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Map;

import Models.Conversion;
import Models.User;

public class FileController {
    public static void BatchProcessingThroughFile(int userid , String filepath){
        try (BufferedReader br = new BufferedReader(new FileReader(filepath))) {
            while(br.ready()){
                String[] valueforconversions = br.readLine().split(",");
                String value = valueforconversions[0];
                int sourcebase = Integer.parseInt(valueforconversions[1]);
                int targetbase = Integer.parseInt(valueforconversions[2]);
                System.out.printf("\n%s",ConverterController.convert(userid,value,sourcebase,targetbase));
            }
            
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("There was an problem while reading the file");
        }
    }
    public static boolean ExportCurrentSessionConversions(int userid){
        User user = UserController.GetUserById(userid);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("D:\\JAVA and DSA\\Codes\\Practice\\Longcoding\\NumberConverterSystem\\files\\TestingFiles\\TestingExport.txt"));) {
            for(Conversion c : user.CurrentSession){
                bw.append(c.toString());
            }
            return true;
            
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("There was an problem while exporting to the file" + e.getMessage());
            return false;
        }
    }
     public static boolean ExportHistoryConversionsBydate(int userid){
        User user = UserController.GetUserById(userid);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("D:\\JAVA and DSA\\Codes\\Practice\\Longcoding\\NumberConverterSystem\\files\\TestingHistoryExport.txt"));) {
            for(Map.Entry<LocalDate,ArrayList<Conversion>> entry : user.ConversionHistory.entrySet()){
                bw.append(String.format("%s\n",entry.getKey().toString())); 
                for(Conversion c : entry.getValue()){
                bw.append(c.toString());
            }   
            bw.append("\n");
            }
            
            return true;
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("There was an problem while exporting to the file"+e.getMessage());
            return false;
        }
     }
}
