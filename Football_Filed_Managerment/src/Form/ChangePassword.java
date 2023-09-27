package Form;

import DataBase.ConnectDB;
import com.formdev.flatlaf.FlatLightLaf;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.JOptionPane;


public class ChangePassword extends javax.swing.JFrame {
    UserSession userSession = UserSession.getInstance();
    String username = userSession.getUsername();
    String idNV = userSession.getUserID();
    String maXN = randomMaXN();
    ConnectDB cn = new ConnectDB();
    Connection conn;
    
    public String randomMaXN(){
        String randomString = null;
        int n = 6; // số lượng ký tự trong chuỗi
        StringBuilder sb = new StringBuilder(n);
        Random random = new Random();
        for (int i = 0; i < n; i++) {
            int randomInt = random.nextInt(26); // tạo số ngẫu nhiên trong khoảng từ 0 đến 25
            char randomChar = (char) (randomInt + 65); // chuyển đổi số ngẫu nhiên thành ký tự in hoa tương ứng trong bảng mã ASCII
            sb.append(randomChar); // thêm ký tự vào chuỗi
        }
        randomString = sb.toString();
        return randomString;
    }
    
    public static void sendEmail(String recipientEmail, String confirmationCode) throws MessagingException {
        // Thiết lập thông tin tài khoản email của bạn
        String username = "sytruong61@gmail.com";
        String password = Crypto.Config.getPassword();

        // Thiết lập các thông tin cần thiết cho phiên gửi email
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        // Khởi tạo phiên gửi email
        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        // Tạo message email
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(username));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
        message.setSubject("Xác nhận đổi mật khẩu");
        message.setText("Mã xác nhận của bạn là: " + confirmationCode);

        // Gửi email
        Transport.send(message);
    }
    
    public boolean GetUserFromUsername(String username){
        boolean check = false;
        try {
            conn = cn.getConnection();
            String select_User = "Select * from [TaiKhoanNhanVien] where Email = ?";
            PreparedStatement pst = conn.prepareStatement(select_User);
            pst.setString(1, username);
            ResultSet user = pst.executeQuery();
            if(user.next()){
                check = true;
            }
            else {
                check = false;
                
            }            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return check;
    }
    
    public ChangePassword() {
        initComponents();
        this.setLocationRelativeTo(null);        
        if(username == null){
            emailUser.setText(username);
        }
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        emailUser = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        maXNTxt = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        pwdMoiTxt = new javax.swing.JPasswordField();
        cfpwdTxt = new javax.swing.JPasswordField();
        jLabel4 = new javax.swing.JLabel();
        guiMail = new javax.swing.JButton();
        changePwd = new javax.swing.JButton();
        closeButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setText("Email");

        jLabel2.setText("Mã Xác Nhận");

        jLabel3.setText("Mật Khẩu Mới");

        jLabel4.setText("Nhập Lại MK Mới");

        guiMail.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/097-send.png"))); // NOI18N
        guiMail.setText("Gửi Mail Xác Nhận");
        guiMail.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                guiMailMouseClicked(evt);
            }
        });

        changePwd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/378-locked.png"))); // NOI18N
        changePwd.setText("Đổi Mật Khẩu");
        changePwd.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                changePwdMouseClicked(evt);
            }
        });

        closeButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/058-error.png"))); // NOI18N
        closeButton.setText("Đóng");
        closeButton.setToolTipText("");
        closeButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                closeButtonMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel4))
                                .addGap(34, 34, 34)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cfpwdTxt)
                                    .addComponent(pwdMoiTxt)))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(jLabel2)
                                        .addGap(30, 30, 30))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel1)
                                        .addGap(56, 56, 56)))
                                .addGap(19, 19, 19)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(maXNTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(emailUser, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(59, 59, 59)
                        .addComponent(changePwd)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(closeButton)
                    .addComponent(guiMail))
                .addContainerGap(21, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(emailUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(guiMail)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(maXNTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(pwdMoiTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cfpwdTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(changePwd)
                    .addComponent(closeButton))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void changePwdMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_changePwdMouseClicked
        username = emailUser.getText().trim();
        if(username == ""){
            JOptionPane.showMessageDialog(this, "Không được để trống email");
            return;
        }
        if(Validate.validate(username) == false){
            JOptionPane.showMessageDialog(this, "Email không đúng định dạng");
            return;
        }
        if(GetUserFromUsername(username) == false){
            JOptionPane.showMessageDialog(this, "Không tìm thấy tài khoản có email này");
            return;
        }
        if(maXNTxt.getText() == null || !maXNTxt.getText().equals(maXN)){
            JOptionPane.showMessageDialog(this, "Mã xác nhận không đúng vui lòng nhập lại");
            return;
        }
        if(pwdMoiTxt.getText().trim().equals("")){
            JOptionPane.showMessageDialog(this, "Mật khẩu không được để trống");
            return;
        }
        if(!pwdMoiTxt.getText().trim().equals(cfpwdTxt.getText().trim())){
            JOptionPane.showMessageDialog(this, "Mật khẩu xác nhận phải trùng với mật khẩu mới");
            return;
        }
        StringBuffer sb = Validate.checkPwd(pwdMoiTxt.getText().trim());
        if(sb.length() > 0){    
            JOptionPane.showMessageDialog(this, sb.toString());
            return;
        }
        if(maXN == null){
            JOptionPane.showMessageDialog(this, "Bạn chưa gửi mã xác nhận");
            return;
        }
        String hashPwd = Validate.md5(pwdMoiTxt.getText().trim());
        updatePwdAccNV(hashPwd,username);
        JOptionPane.showMessageDialog(this, "Đã thay đổi mật khẩu thành công");
        this.dispose();
    }//GEN-LAST:event_changePwdMouseClicked

    public void updatePwdAccNV(String password, String username){
        try{
            String insert = "UPDATE [TaiKhoanNhanVien] SET Password = ? WHERE Email = ?";
            conn = cn.getConnection();
            PreparedStatement pst2 = conn.prepareStatement(insert);
            pst2.setString(1, password);
            pst2.setString(2, username);
            pst2.execute();
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    
    private void guiMailMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_guiMailMouseClicked
        username = emailUser.getText().trim();
        if(username.equals("")){
            JOptionPane.showMessageDialog(this, "Không được để trống email");
            return;
        }
        if(GetUserFromUsername(username) == false){
            JOptionPane.showMessageDialog(this, "Không tìm thấy tài khoản có email này");
            return;
        }
        try {
            sendEmail(username, maXN);
            JOptionPane.showMessageDialog(this, "Gửi mail mã xác nhận thành công");
            return;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }//GEN-LAST:event_guiMailMouseClicked

    private void closeButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_closeButtonMouseClicked
        this.dispose();
    }//GEN-LAST:event_closeButtonMouseClicked

    public static void main(String args[]) {
        FlatLightLaf.setup();
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ChangePassword().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPasswordField cfpwdTxt;
    private javax.swing.JButton changePwd;
    private javax.swing.JButton closeButton;
    private javax.swing.JTextField emailUser;
    private javax.swing.JButton guiMail;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JTextField maXNTxt;
    private javax.swing.JPasswordField pwdMoiTxt;
    // End of variables declaration//GEN-END:variables
}
