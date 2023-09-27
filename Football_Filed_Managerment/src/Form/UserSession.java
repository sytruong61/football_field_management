package Form;

public class UserSession {
    private static UserSession instance;
    private String username;
    private String userID;
   
    private UserSession() {
      // Khởi tạo một số giá trị mặc định
    }

    public static synchronized UserSession getInstance() {
      if (instance == null) {
         instance = new UserSession();
      }
      return instance;
    }

    public void setUsername(String username) {
      this.username = username;
    }

    public String getUsername() {
      return username;
    }
    
    public void setUserID(String userID) {
      this.userID = userID;
    }

    public String getUserID() {
      return userID;
    }
}
