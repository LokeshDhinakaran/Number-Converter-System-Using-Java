package Controllers;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Random;



import Models.User;

public class AuthController {
    public static ArrayList<User> UserList; 
    static{
        AuthController.LoadUserList();
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
              if(!AuthController.SaveUserList()){
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
    
    }

