package Models;
import Models.Conversion;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class User implements Serializable{
    public static Long serialVersionUid = 1L;
    public String UserName,Password;
    public int id;
    public int UndoCountInCurrentConversion;
    public ArrayList<Conversion> CurrentSession;
    public HashMap<LocalDate,ArrayList<Conversion>> ConversionHistory;
    public User(int id , String UserName, String Password){
        this.id = id;
        this.UserName = UserName;
        this.Password = Password;
        this.CurrentSession = new ArrayList<>();
        this.ConversionHistory = new HashMap<>();
        this.UndoCountInCurrentConversion = 0;
    }
}
