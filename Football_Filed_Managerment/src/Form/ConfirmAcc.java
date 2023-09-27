
package Form;

import DataBase.ConnectDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;
import java.util.Random;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;


public class ConfirmAcc extends javax.swing.JFrame {
    UserSession userSession = UserSession.getInstance();
    String username = userSession.getUsername();
    String idNV = userSession.getUserID();
    String maXN = randomMaXN();
    ConnectDB cn = new ConnectDB();
    Connection conn;
    
    public ConfirmAcc() {
        initComponents();
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        ImageIcon img = new ImageIcon("D:\\Football_Filed_Managerment\\src\\images\\icons/—Pngtree—football field plan_4710125 (1).png");
        this.setIconImage(img.getImage());        
        if(username != null){
            emailUser.setText(username);
            emailUser.setEnabled(false);
        }
        
    }
    
    public String randomMaXN() {
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
        message.setSubject("Xác Nhận Tài Khoản");
        message.setText("Mã xác nhận của bạn là: " + confirmationCode);

        // Gửi email
        Transport.send(message);
    }
    

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        guiMail = new javax.swing.JButton();
        confirmedAcc = new javax.swing.JButton();
        closeButton = new javax.swing.JButton();
        maXNTxt = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        emailUser = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        pwdMoiTxt = new javax.swing.JPasswordField();
        jLabel4 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        cfpwdTxt = new javax.swing.JPasswordField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Xác Nhận Tài Khoản");

        guiMail.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/097-send.png"))); // NOI18N
        guiMail.setText("Gửi Mail Xác Nhận");
        guiMail.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                guiMailMouseClicked(evt);
            }
        });

        confirmedAcc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/378-locked.png"))); // NOI18N
        confirmedAcc.setText("Xác Nhận Tài Khoản");
        confirmedAcc.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                confirmedAccMouseClicked(evt);
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

        jLabel1.setText("Email");

        jLabel2.setText("Mã Xác Nhận");

        jLabel4.setText("Nhập Lại MK Mới");

        jLabel3.setText("Mật Khẩu Mới");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
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
                            .addComponent(emailUser, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(49, 49, 49)
                        .addComponent(confirmedAcc)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(closeButton)
                    .addComponent(guiMail))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(emailUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(guiMail))
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(confirmedAcc)
                    .addComponent(closeButton))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void guiMailMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_guiMailMouseClicked
        username = emailUser.getText().trim();
        if(username.equals("")){
            JOptionPane.showMessageDialog(this, "Không được để trống email");
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
        
    public void updateAccNV(String username){
        try{
            String insert = "UPDATE [TaiKhoanNhanVien] SET XacThuc = ? WHERE Email = ?";
            conn = cn.getConnection();
            PreparedStatement pst2 = conn.prepareStatement(insert);
            pst2.setString(1, "1");
            pst2.setString(2, username);
            pst2.execute();
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    
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
        
    private void confirmedAccMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_confirmedAccMouseClicked
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
        if(maXN == null){
            JOptionPane.showMessageDialog(this, "Bạn chưa gửi mã xác nhận");
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
        updateAccNV(username);
        String hashPwd = Validate.md5(pwdMoiTxt.getText().trim());
        updatePwdAccNV(hashPwd,username);
        JOptionPane.showMessageDialog(this, "Đã cập nhật tài khoản thành công");
        this.dispose();
    }//GEN-LAST:event_confirmedAccMouseClicked

    private void closeButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_closeButtonMouseClicked
        this.dispose();
    }//GEN-LAST:event_closeButtonMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ConfirmAcc.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ConfirmAcc.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ConfirmAcc.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ConfirmAcc.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ConfirmAcc().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPasswordField cfpwdTxt;
    private javax.swing.JButton closeButton;
    private javax.swing.JButton confirmedAcc;
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
