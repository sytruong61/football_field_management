package Crypto;

import java.io.FileInputStream;
import java.util.Properties;

public class Config {
    private static final String CONFIG_FILE = Config.class.getResource("config.properties").getPath();
    private static final String SECRET_KEY_PROPERTY = "secretKey";
    private static final String PASSWORD_PROPERTY = "password";
    private static String secretKey;
    private static String password;

    static {
        try {
            Properties prop = new Properties();
            FileInputStream fis = new FileInputStream(CONFIG_FILE);
            prop.load(fis);
            secretKey = prop.getProperty(SECRET_KEY_PROPERTY);
            password = prop.getProperty(PASSWORD_PROPERTY);
            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getSecretKey() {
        return secretKey;
    }
    
    public static String getPassword() {
        return password;
    }
}