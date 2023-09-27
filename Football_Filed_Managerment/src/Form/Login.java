package Form;

import DataBase.ConnectDB;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.BorderFactory;
import java.awt.*;
import java.sql.*;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

public class Login extends javax.swing.JFrame {
    ConnectDB cn = new ConnectDB();
    Connection conn;
    
    public Login() {
        initComponents();
        this.getRootPane().setDefaultButton(loginButton);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        ImageIcon img = new ImageIcon("D:\\Football_Filed_Managerment\\src\\images\\icons/—Pngtree—football field plan_4710125 (1).png");
        this.setIconImage(img.getImage());
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        loginPassword = new javax.swing.JPasswordField();
        loginUsername = new javax.swing.JTextField();
        loginButton = new javax.swing.JButton();
        exitButton = new javax.swing.JButton();
        quenMK = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Đăng Nhập");
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(41, 128, 185));
        jLabel1.setText("ĐĂNG NHẬP HỆ THỐNG");

        jLabel2.setForeground(new java.awt.Color(41, 128, 185));
        jLabel2.setText("Email");

        jLabel3.setForeground(new java.awt.Color(41, 128, 185));
        jLabel3.setText("Password");

        loginPassword.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(41, 128, 185), 1, true));
        loginPassword.setPreferredSize(new java.awt.Dimension(0, 21));

        loginUsername.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(41, 128, 185), 1, true));
        loginUsername.setPreferredSize(new java.awt.Dimension(0, 21));

        loginButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/360-users.png"))); // NOI18N
        loginButton.setText("Đăng Nhập");
        loginButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                loginButtonMouseClicked(evt);
            }
        });

        exitButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/058-error.png"))); // NOI18N
        exitButton.setText("Exit");
        exitButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                exitButtonMouseClicked(evt);
            }
        });

        quenMK.setForeground(new java.awt.Color(255, 0, 0));
        quenMK.setText("Quên mật khẩu?");
        quenMK.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                quenMKMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(loginPassword, javax.swing.GroupLayout.DEFAULT_SIZE, 216, Short.MAX_VALUE)
                            .addComponent(loginUsername, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(13, 13, 13))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addComponent(loginButton)
                        .addGap(35, 35, 35)
                        .addComponent(exitButton)
                        .addContainerGap(35, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(quenMK)
                        .addGap(16, 16, 16))))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(51, 51, 51)
                .addComponent(jLabel1)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(loginUsername, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(loginPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(quenMK)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(loginButton)
                    .addComponent(exitButton))
                .addContainerGap(13, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 130, -1, 200));

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/0b7966df0b6f61f5a35cdd151ac23a2c.jpg"))); // NOI18N
        jLabel6.setText("jLabel6");
        jLabel6.setPreferredSize(new java.awt.Dimension(0, 21));
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 610, 444));

        pack();
    }// </editor-fold>//GEN-END:initComponents
    public void updateAcc(String username, String code){
        try{
            String select_1USer = "Select * from [TaiKhoanNhanVien] where Email =?";
            conn = cn.getConnection();
            PreparedStatement pst = conn.prepareStatement(select_1USer);
            pst.setString(1, username);
            ResultSet rs = pst.executeQuery();
            if(rs.next()){
                int count = rs.getInt("SoLanSaiMatKhau");
                String update_User  = "UPDATE [TaiKhoanNhanVien] SET SoLanSaiMatKhau = ?, BiKhoa = ? WHERE Email = ?";
                conn = cn.getConnection();
                PreparedStatement pst2 = conn.prepareStatement(update_User);
                if(code.equals("Failed") == true){
                    pst2.setInt(1, count+1 );
                    if(count >= 5){
                        pst2.setString(2, "1");
                    }else{
                        pst2.setString(2, "0");
                    }
                }
                if(code.equals("Passed") == true){
                    pst2.setInt(1, 0);
                    pst2.setString(2, "0"); 
                }
                pst2.setString(3, username);
                pst2.execute();
            }
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    
    private void loginButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_loginButtonMouseClicked
        String username = loginUsername.getText().toString().trim();
        String password = loginPassword.getText().toString().trim();
        String hashPwd = Validate.md5(password);
        String idNV;
        StringBuffer sb = new StringBuffer();
        if(username.length()==0){    
            sb.append("Tên đăng nhập không được để trống!\n");
        }
        if(password.length()==0){    
            sb.append("Mật khẩu không được để trống!");
        }        
        if(sb.length()>0){    
            JOptionPane.showMessageDialog(this, sb.toString());
            return;
        }
        String sql_login = "Select * from [TaiKhoanNhanVien] where Email =?";
        try{
            conn = cn.getConnection();
            PreparedStatement pst = conn.prepareStatement(sql_login);
            pst.setString(1, username);
            ResultSet rs = pst.executeQuery();
            if(rs.next()){
                String check = rs.getString("BiKhoa");
                if(check.equals("1") == true){
                    JOptionPane.showMessageDialog(this, "Tài khoản của bạn đã bị khóa");
                    loginUsername.setText("");
                    loginPassword.setText("");
                    return;
                }
                String confirmed = rs.getString("XacThuc");
                if(confirmed.equals("0")){
                    JOptionPane.showMessageDialog(this, "Tài khoản của bạn chưa được xác nhận");
                    UserSession userSession = UserSession.getInstance();
                    userSession.setUsername(username);
                    new ConfirmAcc().setVisible(true);
                    return;
                }
                if(check.equals("1") == true){
                    JOptionPane.showMessageDialog(this, "Tài khoản của bạn đã bị khóa");
                    loginUsername.setText("");
                    loginPassword.setText("");
                    return;
                }
                String userPassword = rs.getString("Password");
                if(userPassword.equals(hashPwd) == true){
                    idNV = rs.getString("MaNV");
                    JOptionPane.showMessageDialog(this, "Đăng nhập thành công");
                    updateAcc(username, "Passed");
                    UserSession userSession = UserSession.getInstance();
                    userSession.setUsername(username);
                    userSession.setUserID(idNV);
                    new Home().setVisible(true);
                    this.setVisible(false);
                }else{
                    updateAcc(username, "Failed");
                    JOptionPane.showMessageDialog(this, "Sai tài khoản hoặc mật khẩu!");
                }
            }
        } catch(Exception e){
            e.printStackTrace();
        }
    }//GEN-LAST:event_loginButtonMouseClicked

    private void quenMKMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_quenMKMouseClicked
        new ChangePassword().setVisible(true);
    }//GEN-LAST:event_quenMKMouseClicked

    private void exitButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_exitButtonMouseClicked
        this.dispose();
    }//GEN-LAST:event_exitButtonMouseClicked

    public static void main(String args[]) {
        FlatLightLaf.setup();
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Login().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton exitButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JButton loginButton;
    private javax.swing.JPasswordField loginPassword;
    private javax.swing.JTextField loginUsername;
    private javax.swing.JLabel quenMK;
    // End of variables declaration//GEN-END:variables
}
