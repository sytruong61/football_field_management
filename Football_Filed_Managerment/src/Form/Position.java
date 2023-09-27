package Form;

import DataBase.ConnectDB;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.ResultSetMetaData;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

public class Position extends javax.swing.JInternalFrame {
    ConnectDB cn = new ConnectDB();
    Connection conn;
    String CODE = "INSERT", pstID;

    public void SelectAllPST(){
        
        String select_PST = "Select * from ChucVu";
        try{
            DefaultTableModel dm = (DefaultTableModel)pstTable.getModel();
            dm.getDataVector().removeAllElements();
            dm.fireTableDataChanged();
            conn = cn.getConnection();
            PreparedStatement pst1 = conn.prepareStatement(select_PST);
            ResultSet rs = pst1.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            DefaultTableModel model = (DefaultTableModel) pstTable.getModel();
            String ID, TenChucVu, GhiChu;
            while(rs.next()){
               ID = rs.getString("MaCV");
               TenChucVu = rs.getString("TenChucVu");
               GhiChu = rs.getString("GhiChu");
               String[] pst = {ID,TenChucVu,GhiChu};
               model.addRow(pst);
            }
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public Position() {
        initComponents();
        ImageIcon img = new ImageIcon("D:\\Football_Filed_Managerment\\src\\images\\icons/—Pngtree—football field plan_4710125 (1).png");
        this.setFrameIcon(img);
        SelectAllPST();
        ColumnsAutoSizer.sizeColumnsToFit(pstTable);
        pstTable.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent mouseEvent) {
                JTable table =(JTable) mouseEvent.getSource();
                Point point = mouseEvent.getPoint();
                int row = table.rowAtPoint(point);
                if(row == -1){
                    return;
                }
                pstID = pstTable.getModel().getValueAt(row, 0).toString();
                if (mouseEvent.getClickCount() == 1 && table.getSelectedRow() != -1) {
                    try {
                        tenchucvuTxt.setText(pstTable.getModel().getValueAt(row, 1).toString());
                        if(pstTable.getModel().getValueAt(row, 2) != null){
                            ghichuTxt.setText(pstTable.getModel().getValueAt(row, 2).toString());
                        }
                    } catch (Exception ex) {
                        System.out.println(ex.toString());
                    }
                    CODE = "UPDATE";
                }   
            }
        });
        
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        cancelButton = new javax.swing.JButton();
        addPST = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        pstTable = new javax.swing.JTable();
        searchTxt = new javax.swing.JTextField();
        savePST = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        ghichuTxt = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        tenchucvuTxt = new javax.swing.JTextField();

        setTitle("Quản Lý Chức Vụ");

        cancelButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/058-error.png"))); // NOI18N
        cancelButton.setText("Đóng");
        cancelButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cancelButtonMouseClicked(evt);
            }
        });

        addPST.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/056-plus.png"))); // NOI18N
        addPST.setText("Thêm Chức Vụ");
        addPST.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                addPSTMouseClicked(evt);
            }
        });

        pstTable.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(41, 128, 185), 1, true));
        pstTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã Chức Vụ", "Tên Chức Vụ", "Ghi Chú"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(pstTable);

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

        savePST.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/022-save.png"))); // NOI18N
        savePST.setText("Lưu");
        savePST.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                savePSTMouseClicked(evt);
            }
        });

        jLabel2.setBackground(new java.awt.Color(255, 255, 255));
        jLabel2.setForeground(new java.awt.Color(41, 128, 185));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Tên Chức Vụ");

        ghichuTxt.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(41, 128, 185), 1, true));

        jLabel13.setBackground(new java.awt.Color(255, 255, 255));
        jLabel13.setForeground(new java.awt.Color(41, 128, 185));
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setText("Ghi chú");

        tenchucvuTxt.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(41, 128, 185), 1, true));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(searchTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(addPST)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(savePST)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(cancelButton))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tenchucvuTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ghichuTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(19, 19, 19))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tenchucvuTxt, javax.swing.GroupLayout.DEFAULT_SIZE, 21, Short.MAX_VALUE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ghichuTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(searchTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(savePST)
                    .addComponent(addPST)
                    .addComponent(cancelButton))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 357, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cancelButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cancelButtonMouseClicked
        this.dispose();
    }//GEN-LAST:event_cancelButtonMouseClicked

    private void addPSTMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addPSTMouseClicked
        try {
            tenchucvuTxt.setText("");
            ghichuTxt.setText("");
            CODE = "INSERT";
        } catch(Exception e){
            e.printStackTrace();
        }
    }//GEN-LAST:event_addPSTMouseClicked

    public void filter(String query){
        DefaultTableModel dm = (DefaultTableModel) pstTable.getModel();
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<DefaultTableModel>(dm);
        pstTable.setRowSorter(tr);
        tr.setRowFilter(RowFilter.regexFilter(query));
    }
    private void searchTxtKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchTxtKeyReleased
        String query = searchTxt.getText().trim();
        filter(query);
    }//GEN-LAST:event_searchTxtKeyReleased

    private void searchTxtMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_searchTxtMouseClicked
        searchTxt.setText("");       
    }//GEN-LAST:event_searchTxtMouseClicked

    public void Reset(){
        CODE = "INSERT";
        SelectAllPST();
        tenchucvuTxt.setText("");
        ghichuTxt.setText("");
    }
    
    public void insertCV(String tenCV, String ghichu){
        try{
            String insert = "INSERT INTO ChucVu(TenChucVu, GhiChu) VALUES(?,?)";
            conn = cn.getConnection();
            PreparedStatement pst2 = conn.prepareStatement(insert);
            pst2.setString(1, tenCV);
            pst2.setString(2, ghichu);
            pst2.execute();
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void updateCV(String tenCV, String ghichu, String maCV){
        try{
            String insert = "UPDATE ChucVu SET TenChucVu = ?, GhiChu = ? WHERE MaCV = ?";
            conn = cn.getConnection();
            PreparedStatement pst2 = conn.prepareStatement(insert);
            pst2.setString(1, tenCV);
            pst2.setString(2, ghichu);
            pst2.setString(3, maCV);
            pst2.execute();
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    
    private void savePSTMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_savePSTMouseClicked
        String tenCV = tenchucvuTxt.getText().trim();
        String ghichu = ghichuTxt.getText().trim();
        StringBuffer sb = new StringBuffer();
        if(tenCV.length() == 0){
            sb.append("Tên chức vụ không được để trống!\n");
        }
        if(sb.length()>0){
            JOptionPane.showMessageDialog(this, sb.toString());
            return;
        }
        if( CODE.equals("INSERT") == true){
            String select_CV = "Select * from ChucVu";
            try{
                conn = cn.getConnection();
                PreparedStatement pst = conn.prepareStatement(select_CV);
                ResultSet chucvu = pst.executeQuery();
                while(chucvu.next()){
                    if(chucvu.getString("TenChucVu").trim().equals(tenCV) == true){
                        sb.append("Chức Vụ đã tồn tại!\n");
                    }
                }
            } catch(Exception e){
                e.printStackTrace();
            }
            insertCV(tenCV,ghichu);
            JOptionPane.showMessageDialog(this, "Đã Thêm Chức Vụ Thành Công");
            Reset();
            return;
        }
        if( CODE.equals("UPDATE") == true){
            String select_PST = "Select * from NhanVien where ChucVu = ?";
            try{
                conn = cn.getConnection();
                PreparedStatement pst = conn.prepareStatement(select_PST);
                pst.setString(1, pstID);
                ResultSet rs = pst.executeQuery();
                if(rs.next()){
                    JOptionPane.showMessageDialog(this, "Không được chỉnh sửa.");
                    return;
                }
            } catch(Exception e){
                e.printStackTrace();
            }
            String select_CV = "Select * from ChucVu where MaCV != ?";
            try{
                conn = cn.getConnection();
                PreparedStatement pst = conn.prepareStatement(select_CV);
                pst.setString(1, pstID);
                ResultSet chucvu = pst.executeQuery();
                while(chucvu.next()){
                    if(chucvu.getString("TenChucVu").trim().equals(tenCV) == true){
                        sb.append("Chức Vụ đã tồn tại!\n");
                    }
                }
            } catch(Exception e){
                e.printStackTrace();
            }
            updateCV(tenCV,ghichu, pstID);
            JOptionPane.showMessageDialog(this, "Chỉnh Sửa Thông Tin Chức Vụ Thành Công");
            Reset();
            return;
        }
    }//GEN-LAST:event_savePSTMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addPST;
    private javax.swing.JButton cancelButton;
    private javax.swing.JTextField ghichuTxt;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable pstTable;
    private javax.swing.JButton savePST;
    private javax.swing.JTextField searchTxt;
    private javax.swing.JTextField tenchucvuTxt;
    // End of variables declaration//GEN-END:variables
}
