package Form;

import DataBase.ConnectDB;
import com.formdev.flatlaf.FlatLightLaf;
import java.awt.Component;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableRowSorter;


public class Staff extends javax.swing.JInternalFrame {
    ConnectDB cn = new ConnectDB();
    Connection conn;
    String lockedOut = "0";
    
    public String getBoPhanfromID(String ID) throws SQLException, ClassNotFoundException{
        String bophan = null;
        conn = cn.getConnection();
        String select_1BP = "Select * from BoPhan where MaBP = ?";
        PreparedStatement pst3 = conn.prepareStatement(select_1BP);        
        pst3.setString(1, ID);
        ResultSet bophanlist = pst3.executeQuery();
        while(bophanlist.next()){
            bophan = bophanlist.getString("TenBoPhan");
        }
        return bophan;
    }
    public String getChucVufromID(String ID) throws SQLException, ClassNotFoundException{
        String chucvu = null;
        conn = cn.getConnection();
        String select_1CV = "Select * from ChucVu where MaCV = ?";
        PreparedStatement pst3 = conn.prepareStatement(select_1CV);        
        pst3.setString(1, ID);
        ResultSet chucvulist = pst3.executeQuery();
        while(chucvulist.next()){
            chucvu = chucvulist.getString("TenChucVu");
        }
        return chucvu;
    }
    
    public boolean checkAccNV(String nvID){
        boolean checkAccNV = true;
        try {
            conn = cn.getConnection();
            String select_1USER = "Select * from [TaiKhoanNhanVien] where MaNV = ?";
            PreparedStatement pst3 = conn.prepareStatement(select_1USER);
            pst3.setString(1, nvID);
            ResultSet user = pst3.executeQuery();
            if(user.next()){
                checkAccNV = true;            
            }else{
                checkAccNV = false;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return checkAccNV;
    }
    
    public boolean lockAccNV(String nvID){
        boolean checkLockNV = true;
        try {
            conn = cn.getConnection();
            String select_1USER = "Select * from TaiKhoanNhanVien where MaNV = ?";
            PreparedStatement pst3 = conn.prepareStatement(select_1USER);
            pst3.setString(1, nvID);
            ResultSet user = pst3.executeQuery();
            if(user.next()){
                if(user.getString("BiKhoa").equals("1") == true){
                     checkLockNV =  true;
                }else{
                     checkLockNV = false;
                }             
            }else{
                checkLockNV = false;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return checkLockNV;
    }

    public void  SelectAllNV(){
        String select_NV = "Select * from NhanVien";
        try{
            conn = cn.getConnection();
            PreparedStatement pst = conn.prepareStatement(select_NV);
            ResultSet rs = pst.executeQuery();
            DefaultTableModel model = (DefaultTableModel) nvTable.getModel();
            model.getDataVector().removeAllElements();
            model.fireTableDataChanged();
            String ID,HoTen,NgaySinh,GioiTinh,BoPhan,ChucVu,DiaChi,CMND = " ",SDT,Email,TinhTrang,GhiChu,TK;
            while(rs.next()){
               ID = rs.getString("MaNV");
               HoTen = rs.getString("HoTen");
               NgaySinh = rs.getString("NgaySinh");
               GioiTinh = rs.getString("GioiTinh");
               BoPhan = getBoPhanfromID(rs.getString("BoPhan"));
               ChucVu = getChucVufromID(rs.getString("ChucVu"));
               DiaChi = rs.getString("DiaChi");
                if(rs.getString("CMND") != null){
                    CMND = Crypto.AES.decrypt(rs.getString("CMND"));
                }
               SDT = rs.getString("SDT");
               Email = rs.getString("Email");
               TinhTrang = rs.getString("TinhTrang");
               GhiChu = rs.getString("GhiChu");
               if(checkAccNV(ID) == true){
                   TK = "Có";
               }else{
                   TK = "Không";
               }
               if(lockAccNV(ID) == true){
                   TK = "Đã Bị Khóa";
               }
               String[] staff = {ID,HoTen,NgaySinh,GioiTinh,BoPhan,ChucVu,DiaChi,CMND,SDT,Email,TinhTrang,GhiChu, TK};
               model.addRow(staff);
               CMND = " ";
            }
        } catch(Exception e){
            e.printStackTrace();
        }
        ColumnsAutoSizer.sizeColumnsToFit(nvTable);
    }
    
    public Staff() {
        initComponents();
        SelectAllNV();
        ImageIcon img = new ImageIcon("D:\\Football_Filed_Managerment\\src\\images\\icons/—Pngtree—football field plan_4710125 (1).png");
        this.setFrameIcon(img);
        ColumnsAutoSizer.sizeColumnsToFit(nvTable);
        nvTable.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent mouseEvent) {
                JTable table =(JTable) mouseEvent.getSource();
                Point point = mouseEvent.getPoint();
                int row = table.rowAtPoint(point);
                if(row == -1){
                    return;
                }
                String nvID = nvTable.getModel().getValueAt(row, 0).toString();
                if (mouseEvent.getClickCount() == 2 && table.getSelectedRow() != -1) {
                    try {
                        new Staff_Infomation("VIEW",nvID).setVisible(true);
                    } catch (Exception ex) {
                        System.out.println(ex.toString());
                    }
                }   
            }
        });
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        searchTxt = new javax.swing.JTextField();
        cancelButton = new javax.swing.JButton();
        searchNV = new javax.swing.JButton();
        addNV = new javax.swing.JButton();
        fixNV = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        nvTable = new javax.swing.JTable();
        changepwdAccNV = new javax.swing.JButton();
        lockAccNV = new javax.swing.JButton();
        reloadButton = new javax.swing.JButton();

        setTitle("Quản Lý Nhân Viên");

        searchTxt.setText("Nhập để tìm kiếm");
        searchTxt.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(41, 128, 185), 1, true));
        searchTxt.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                searchTxtMouseClicked(evt);
            }
        });
        searchTxt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                searchTxtKeyReleased(evt);
            }
        });

        cancelButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/058-error.png"))); // NOI18N
        cancelButton.setText("Đóng");
        cancelButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cancelButtonMouseClicked(evt);
            }
        });

        searchNV.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/093-binoculars.png"))); // NOI18N
        searchNV.setText("Tìm kiếm");

        addNV.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/056-plus.png"))); // NOI18N
        addNV.setText("Thêm nhân viên");
        addNV.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                addNVMouseClicked(evt);
            }
        });

        fixNV.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/218-edit.png"))); // NOI18N
        fixNV.setText("Chỉnh sửa nhân viên ");
        fixNV.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                fixNVMouseClicked(evt);
            }
        });

        nvTable.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(41, 128, 185), 1, true));
        nvTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã", "Họ Tên", "Ngày Sinh", "Giới Tính", "Bộ Phận", "Chức Vụ", "Địa Chỉ", "Số CMND", "Số Điện Thoại", "Email", "Tình Trạng", "Ghi Chú", "Tài Khoản"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(nvTable);

        changepwdAccNV.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/378-locked.png"))); // NOI18N
        changepwdAccNV.setText("Đổi Mật Khẩu");
        changepwdAccNV.setToolTipText("");
        changepwdAccNV.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                changepwdAccNVMouseClicked(evt);
            }
        });

        lockAccNV.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/216-key.png"))); // NOI18N
        lockAccNV.setText("Mở / Khóa Tài Khoản");
        lockAccNV.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lockAccNVMouseClicked(evt);
            }
        });

        reloadButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/091-share.png"))); // NOI18N
        reloadButton.setText("Reload");
        reloadButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                reloadButtonMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(searchTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(searchNV)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(reloadButton))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(addNV)
                        .addGap(121, 121, 121)
                        .addComponent(fixNV)
                        .addGap(111, 111, 111)
                        .addComponent(changepwdAccNV)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 123, Short.MAX_VALUE)
                        .addComponent(lockAccNV)
                        .addGap(109, 109, 109)
                        .addComponent(cancelButton))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(19, 19, 19))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(searchNV, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(reloadButton))
                    .addComponent(searchTxt))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 357, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(fixNV)
                    .addComponent(addNV)
                    .addComponent(cancelButton)
                    .addComponent(changepwdAccNV)
                    .addComponent(lockAccNV))
                .addContainerGap(36, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void addNVMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addNVMouseClicked
        try {
            new Staff_Infomation("INSERT", " ").setVisible(true);
        } catch(Exception e){
            e.printStackTrace();
        }
    }//GEN-LAST:event_addNVMouseClicked

    private void fixNVMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_fixNVMouseClicked
        int column = 0; 
        if(nvTable.getSelectionModel().isSelectionEmpty()){
            JOptionPane.showMessageDialog(this, "Vui Lòng chọn dòng cần chỉnh sửa");
            return;
        }
        int row = nvTable.getSelectedRow();
        String nvID = nvTable.getModel().getValueAt(row, column).toString();
        try {
            new Staff_Infomation("UPDATE", nvID).setVisible(true);
        } catch (Exception e){
            e.printStackTrace();
        }
    }//GEN-LAST:event_fixNVMouseClicked
    public void filter(String query){
        DefaultTableModel dm = (DefaultTableModel) nvTable.getModel();
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<DefaultTableModel>(dm);
        nvTable.setRowSorter(tr);
        tr.setRowFilter(RowFilter.regexFilter(query));
    }    
    
    private void changepwdAccNVMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_changepwdAccNVMouseClicked
        try {
            int column = 0;
            if(nvTable.getSelectionModel().isSelectionEmpty()){
                JOptionPane.showMessageDialog(this, "Vui Lòng chọn dòng cần đổi mật khẩu");
                return;
            }
            int row = nvTable.getSelectedRow();
            String nvID = nvTable.getModel().getValueAt(row, column).toString();
            conn = cn.getConnection();
            String select_1USER = "Select * from [TaiKhoanNhanVien] where MaNV = ?";
            PreparedStatement pst3 = conn.prepareStatement(select_1USER);
            pst3.setString(1, nvID);
            ResultSet user = pst3.executeQuery();
            if(user.next()){
                new Acc_Infomation("UPDATE",nvID).setVisible(true);
            }else{
                JOptionPane.showMessageDialog(this, "Nhân viên chưa có tài khoản nên không thể đổi mật khẩu");
                return;
            }      
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
    }//GEN-LAST:event_changepwdAccNVMouseClicked

    private void cancelButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cancelButtonMouseClicked
        this.dispose();
    }//GEN-LAST:event_cancelButtonMouseClicked

    private void searchTxtKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchTxtKeyReleased
        String query = searchTxt.getText().trim();
        filter(query);
    }//GEN-LAST:event_searchTxtKeyReleased

    private void searchTxtMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_searchTxtMouseClicked
        searchTxt.setText("");
    }//GEN-LAST:event_searchTxtMouseClicked

    private void lockAccNVMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lockAccNVMouseClicked
        int column = 0;
        if(nvTable.getSelectionModel().isSelectionEmpty()){
            JOptionPane.showMessageDialog(this, "Vui Lòng chọn dòng cần chỉnh sửa");
            return;
        }
        int row = nvTable.getSelectedRow();
        String nvID = nvTable.getModel().getValueAt(row, column).toString();
        new LockAccNV(nvID).setVisible(true);
    }//GEN-LAST:event_lockAccNVMouseClicked

    private void reloadButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_reloadButtonMouseClicked
        SelectAllNV();
    }//GEN-LAST:event_reloadButtonMouseClicked
                                          
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addNV;
    private javax.swing.JButton cancelButton;
    private javax.swing.JButton changepwdAccNV;
    private javax.swing.JButton fixNV;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton lockAccNV;
    private javax.swing.JTable nvTable;
    private javax.swing.JButton reloadButton;
    private javax.swing.JButton searchNV;
    private javax.swing.JTextField searchTxt;
    // End of variables declaration//GEN-END:variables
}
