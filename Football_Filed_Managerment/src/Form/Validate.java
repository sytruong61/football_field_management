/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Form;

import java.security.MessageDigest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Sytru
 */
public class Validate {
        public static boolean isNumber(String temp){
        boolean pass = false;
        char[] c = temp.toCharArray();
        int count = 0;
        for(char a : c){
            if(Character.isDigit(a)){
                count++;
            }
        }
        if(count > 0){
            pass = true;
        }
        return pass;
    }
    public static boolean notAlpha(String temp){
        Pattern p = Pattern.compile("[^a-z0-9aAàÀảẢãÃáÁạẠăĂằẰẳẲẵẴắẮặẶâÂầẦẩẨẫẪấẤậẬbBcCdDđĐeEèÈẻẺẽẼéÉẹẸêÊềỀểỂễỄếẾệỆ\n" +
"fFgGhHiIìÌỉỈĩĨíÍịỊjJkKlLmMnNoOòÒỏỎõÕóÓọỌôÔồỒổỔỗỖốỐộỘơƠờỜởỞỡỠớỚợỢpPqQrRsStTu\n" +
"UùÙủỦũŨúÚụỤưƯừỪửỬữỮứỨựỰvVwWxXyYỳỲỷỶỹỸýÝỵỴzZ ]", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(temp);
        boolean b = m.find();
        return b;
    }
    
    public static boolean notNumber(String temp){
        Pattern p = Pattern.compile("[^0-9 ]", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(temp);
        boolean b = m.find();
        return b;
    }
    public static final Pattern VALID_Email_ADDRESS_REGEX = 
    Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public static boolean validate(String emailStr) {
        Matcher matcher = VALID_Email_ADDRESS_REGEX.matcher(emailStr);
        return matcher.matches();
    }
    
    public static StringBuffer checkPwd(String password){
        StringBuffer sb = new StringBuffer();   
        if (password == null){
            sb.append("Mật khẩu không được để trống\n");
        }
        if (password.length() < 8){
            sb.append("Mật khẩu phải có độ dài từ 8 trở lên\n");
        }
        boolean containsUpperCase = false;
        boolean containsLowerCase = false;
        boolean containsDigit = false;
        for(char ch: password.toCharArray()){
            if(Character.isUpperCase(ch)) containsUpperCase = true;
            if(Character.isLowerCase(ch)) containsLowerCase = true;
            if(Character.isDigit(ch)) containsDigit = true;
        }
        if(containsUpperCase == false){
           sb.append("Mật khẩu phải chứa ít nhất một chữ in hoa\n"); 
        }
        if(containsLowerCase == false){
           sb.append("Mật khẩu phải chứa ít nhất một chữ thường\n"); 
        }
        if(containsDigit  == false){
           sb.append("Mật khẩu phải chứa ít nhất một số\n"); 
        }
        return sb;
    }
    
    public static String md5(String msg) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(msg.getBytes());
            byte byteData[] = md.digest();
            //convert the byte to hex format method 1
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < byteData.length; i++) {
                sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
            }
            return  sb.toString();
        } catch (Exception ex) {
            return "";
        }
    }
}
