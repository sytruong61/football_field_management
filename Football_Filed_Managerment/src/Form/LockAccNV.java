
package Form;

import DataBase.ConnectDB;
import com.formdev.flatlaf.FlatLightLaf;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;


public class LockAccNV extends javax.swing.JFrame {
    ConnectDB cn = new ConnectDB();
    Connection conn;
    String maNV = null;

    public String GetTenNVfromID(String maNV){
        String tenNV = null;
        String select_NV = "Select * from NhanVien where MaNV = ?";
        try{
            conn = cn.getConnection();
            PreparedStatement pst = conn.prepareStatement(select_NV);
            pst.setString(1, maNV);
            ResultSet rs = pst.executeQuery();
            if(rs.next()){
                tenNV = rs.getString("HoTen");
            }
        } catch(Exception e){
            e.printStackTrace();
        }
        return tenNV;
    }
    
    public boolean GetUserFromUsername(String maNV){
        boolean lockedOut = false;
        try {
            conn = cn.getConnection();
            String select_User = "Select * from [TaiKhoanNhanVien] where MaNV = ?";
            PreparedStatement pst = conn.prepareStatement(select_User);
            pst.setString(1, maNV );
            ResultSet user = pst.executeQuery();
            while(user.next()){
                if(user.getString("BiKhoa").equals("1")){
                    lockedOut = true;
                }
                else {
                    lockedOut = false; 
                }       
            }     
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lockedOut;
    }
    
    public LockAccNV(String maNV) {
        initComponents();
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        ImageIcon img = new ImageIcon("D:\\Football_Filed_Managerment\\src\\images\\icons/—Pngtree—football field plan_4710125 (1).png");
        this.setIconImage(img.getImage());
        this.maNV = maNV;
        maNVTxt.setText(maNV);
        tenNVTxt.setText(GetTenNVfromID(maNV));
        boolean lockedOut = GetUserFromUsername(maNV);
        if(lockedOut == true){
            statusAccBox.setSelectedItem("Khóa");
        }else{
            statusAccBox.setSelectedItem("Mở");
        }
    }
    
    public void updateAccNV(String value, String maNV){
        try{
            String insert = "UPDATE [TaiKhoanNhanVien] SET BiKhoa = ? WHERE MANV = ?";
            conn = cn.getConnection();
            PreparedStatement pst2 = conn.prepareStatement(insert);
            pst2.setString(1, value);
            pst2.setString(2, maNV);
            pst2.execute();
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public LockAccNV() {
        initComponents();
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        maNVTxt = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        tenNVTxt = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        statusAccBox = new javax.swing.JComboBox<>();
        saveAccNV = new javax.swing.JButton();
        closeButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Mở / Khóa Tài Khoản Nhân Viên");

        jLabel1.setText("Mã Nhân Viên");

        jLabel2.setText("Tên Nhân Viên");

        jLabel3.setText("Tình Trạng Tài Khoản");

        statusAccBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Mở", "Khóa" }));

        saveAccNV.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/022-save.png"))); // NOI18N
        saveAccNV.setText("Lưu");
        saveAccNV.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                saveAccNVMouseClicked(evt);
            }
        });

        closeButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/058-error.png"))); // NOI18N
        closeButton.setText("Đóng");
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
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel1))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(maNVTxt)
                            .addComponent(tenNVTxt, javax.swing.GroupLayout.DEFAULT_SIZE, 190, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(saveAccNV)
                            .addComponent(jLabel3))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(72, 72, 72)
                                .addComponent(closeButton))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 16, Short.MAX_VALUE)
                                .addComponent(statusAccBox, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(22, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(maNVTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(tenNVTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(statusAccBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(saveAccNV)
                    .addComponent(closeButton))
                .addContainerGap(23, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void saveAccNVMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_saveAccNVMouseClicked
        if(statusAccBox.getSelectedItem().toString().equals("Mở")){
            updateAccNV("0", maNV);
            JOptionPane.showMessageDialog(this, "Đã Mở Khóa Tài Khoản Nhân Viên Thành Công");
            this.dispose();
        }else{
            updateAccNV("1", maNV);
            JOptionPane.showMessageDialog(this, "Đã Khóa Tài Khoản Nhân Viên Thành Công");
            this.dispose();
        }
    }//GEN-LAST:event_saveAccNVMouseClicked

    private void closeButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_closeButtonMouseClicked
        this.dispose();
    }//GEN-LAST:event_closeButtonMouseClicked


    public static void main(String args[]) {
        
        FlatLightLaf.setup();
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LockAccNV().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton closeButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JTextField maNVTxt;
    private javax.swing.JButton saveAccNV;
    private javax.swing.JComboBox<String> statusAccBox;
    private javax.swing.JTextField tenNVTxt;
    // End of variables declaration//GEN-END:variables
}
