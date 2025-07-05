package Controllers;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Random;

import Models.Conversion;
import Models.Statistics;
import Models.User;

public class UserController {
    public static ArrayList<User> UserList; 
    static{
        UserController.LoadUserList();
    }
    public static boolean SaveUserList(){
        try (  ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("D:\\JAVA and DSA\\Codes\\Practice\\Longcoding\\NumberConverterSystem\\files\\User.txt"))) {
          
            oos.writeObject(UserList);
            return true;
        
            
        } catch (Exception e) {
            // TODO: handle exception
            UserList = new ArrayList<>();
            System.out.println("There was an problem while saving the file"+e.getMessage());
            return false;
        }
        
    }
    public static void LoadUserList(){
        try (  ObjectInputStream ois = new ObjectInputStream(new FileInputStream("D:\\JAVA and DSA\\Codes\\Practice\\Longcoding\\NumberConverterSystem\\files\\User.txt"))) {
            UserList = (ArrayList<User>) ois.readObject();
            
        } catch (Exception e) {
            // TODO: handle exception
            UserList = new ArrayList<>();
            System.out.println("there was a problem while loading a user file");
        }
    }
    public static int CreateUser(String UserName , String Password){
       
         if(UserName !=null && Password !=null && !UserName.trim().equals("") && !Password.trim().equals("")){
             int id = new Random().nextInt();
              UserList.add(new User(id ,UserName,Password));
              if(!UserController.SaveUserList()){
                UserList.removeIf(User -> User.UserName.equals(UserName));
              }
              return id;
        }
      return -1;
    }
    public static int verifyUser(String UserName , String Password){
        for(User u : UserList){
            if(u.UserName.equals(UserName) && u.Password.equals(Password)){
                return u.id;
            }
        }
        return -1;
    }
    public static User GetUserById(int userId){
        for (User user : UserList) {
            if(user.id == userId){
                return user;
            }
        }
        return null;
    }
    public static void SaveCurrentSessionConversions(int UserId){
        // Hashmap -> 29/06 key exists means -> current session data add
        //if not create a key and assign values
        User u = GetUserById(UserId);
        if(u == null){
            System.out.println("User does not exist");
            return;
        }
        ArrayList<Conversion> ConversionToday = u.ConversionHistory.getOrDefault(LocalDate.now(), new ArrayList<>());
        ConversionToday.addAll(u.CurrentSession);
        u.ConversionHistory.put(LocalDate.now(), ConversionToday);
    }
    public static Statistics getCureeentSessionStats(int UserId){
        User u = UserController.GetUserById(UserId);
        int TotalUndoCount = u.UndoCountInCurrentConversion; 
        int TotalConversions = u.CurrentSession.size();
        int FailedConversion = 0;
        for (Conversion c : u.CurrentSession){
            if(c.ErrorMessage != null){
                FailedConversion++;
            }
        }
        
        return new Statistics(UserId, UserId, UserId, UserId, UserId, UserId);
    }
    
    }

