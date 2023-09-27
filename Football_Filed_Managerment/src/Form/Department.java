package Form;

import DataBase.ConnectDB;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;


public class Department extends javax.swing.JInternalFrame {
    ConnectDB cn = new ConnectDB();
    Connection conn;
    String CODE = "INSERT",maDPM;
    
    public void SelectAllDPM(){
        String select_DPM = "Select * from BoPhan";
        try{
            DefaultTableModel dm = (DefaultTableModel)dpmTable.getModel();
            dm.getDataVector().removeAllElements();
            dm.fireTableDataChanged();
            conn = cn.getConnection();
            PreparedStatement pst = conn.prepareStatement(select_DPM);
            ResultSet rs = pst.executeQuery();
            DefaultTableModel model = (DefaultTableModel) dpmTable.getModel();
            String ID, TenBoPhan, GhiChu;
            while(rs.next()){
               ID = rs.getString("MaBP");
               TenBoPhan = rs.getString("TenBoPhan");
               GhiChu = rs.getString("GhiChu");
               String[] dpm = {ID,TenBoPhan,GhiChu};
               model.addRow(dpm);
            }
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void Reset(){
        SelectAllDPM();
        tenbophanTxt.setText("");
        ghichuTxt.setText("");
        CODE = "INSERT";
    }
    
    public Department(){
        initComponents();
        SelectAllDPM();
        ColumnsAutoSizer.sizeColumnsToFit(dpmTable);
        ImageIcon img = new ImageIcon("D:\\Football_Filed_Managerment\\src\\images\\icons/—Pngtree—football field plan_4710125 (1).png");
        this.setFrameIcon(img);
        dpmTable.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent mouseEvent) {
                JTable table =(JTable) mouseEvent.getSource();
                Point point = mouseEvent.getPoint();
                int row = table.rowAtPoint(point);
                if(row == -1){
                    return;
                }
                maDPM = dpmTable.getModel().getValueAt(row, 0).toString();
                if (mouseEvent.getClickCount() == 1 && table.getSelectedRow() != -1) {
                    try {
                        tenbophanTxt.setText(dpmTable.getModel().getValueAt(row, 1).toString());
                        if(dpmTable.getModel().getValueAt(row, 2) != null){
                            ghichuTxt.setText(dpmTable.getModel().getValueAt(row, 2).toString());
                        }
                        CODE = "UPDATE";
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

        jScrollPane1 = new javax.swing.JScrollPane();
        dpmTable = new javax.swing.JTable();
        searchTxt = new javax.swing.JTextField();
        addDPM = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        ghichuTxt = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        saveDPM = new javax.swing.JButton();
        tenbophanTxt = new javax.swing.JTextField();

        setTitle("Quản Lý Bộ Phận");

        dpmTable.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(41, 128, 185), 1, true));
        dpmTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã Bộ Phận", "Tên Bộ Phận", "Ghi Chú"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(dpmTable);

        searchTxt.setText("Nhập để tìm kiếm");
        searchTxt.setToolTipText("");
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

        addDPM.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/056-plus.png"))); // NOI18N
        addDPM.setText("Thêm Bộ Phận");
        addDPM.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                addDPMMouseClicked(evt);
            }
        });

        cancelButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/058-error.png"))); // NOI18N
        cancelButton.setText("Đóng");
        cancelButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cancelButtonMouseClicked(evt);
            }
        });

        jLabel2.setForeground(new java.awt.Color(41, 128, 185));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Tên Bộ Phận");

        ghichuTxt.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(41, 128, 185), 1, true));
        ghichuTxt.setPreferredSize(new java.awt.Dimension(3, 21));

        jLabel13.setForeground(new java.awt.Color(41, 128, 185));
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setText("Ghi chú");

        saveDPM.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/022-save.png"))); // NOI18N
        saveDPM.setText("Lưu");
        saveDPM.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                saveDPMMouseClicked(evt);
            }
        });

        tenbophanTxt.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(41, 128, 185), 1, true));
        tenbophanTxt.setPreferredSize(new java.awt.Dimension(3, 21));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                            .addComponent(searchTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(addDPM)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(saveDPM)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(cancelButton)))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(tenbophanTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 341, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(ghichuTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(0, 11, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tenbophanTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ghichuTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13))
                .addGap(18, 18, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cancelButton)
                        .addComponent(addDPM)
                        .addComponent(saveDPM))
                    .addComponent(searchTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 357, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(16, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void searchTxtKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchTxtKeyReleased
        String query = searchTxt.getText().trim();
        filter(query);
    }//GEN-LAST:event_searchTxtKeyReleased

    public void insertBP(String tenBP, String ghichu){
        try{
            String insert = "INSERT INTO BoPhan(TenBoPhan, GhiChu) VALUES(?,?)";
            conn = cn.getConnection();
            PreparedStatement pst2 = conn.prepareStatement(insert);
            pst2.setString(1, tenBP);
            pst2.setString(2, ghichu);
            pst2.execute();
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void updateBP(String tenBP, String ghichu, String maBP){
        try{
            String update = "UPDATE BoPhan SET TenBoPhan = ?, GhiChu = ? WHERE MaBP = ?";
            conn = cn.getConnection();
            PreparedStatement pst2 = conn.prepareStatement(update);
            pst2.setString(1, tenBP);
            pst2.setString(2, ghichu);
            pst2.setString(3, maBP);
            pst2.execute();
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    
    private void addDPMMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addDPMMouseClicked
        try {
            tenbophanTxt.setText("");
            ghichuTxt.setText("");
            CODE = "INSERT";
        } catch(Exception e){
            e.printStackTrace();
        }
    }//GEN-LAST:event_addDPMMouseClicked
    
    public void filter(String query){
        DefaultTableModel dm = (DefaultTableModel) dpmTable.getModel();
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<DefaultTableModel>(dm);
        dpmTable.setRowSorter(tr);
        tr.setRowFilter(RowFilter.regexFilter(query));
    }
    private void cancelButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cancelButtonMouseClicked
        this.dispose();
    }//GEN-LAST:event_cancelButtonMouseClicked

    private void searchTxtMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_searchTxtMouseClicked
        searchTxt.setText("");
    }//GEN-LAST:event_searchTxtMouseClicked

    private void saveDPMMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_saveDPMMouseClicked
        String tenBP = tenbophanTxt.getText().trim();
        String ghichu = ghichuTxt.getText().trim();
        StringBuffer sb = new StringBuffer();
        if(tenBP.length() == 0){
            sb.append("Tên bộ phận không được để trống!\n");
        }
        if(sb.length()>0){
            JOptionPane.showMessageDialog(this, sb.toString());
            return;
        }
        if( CODE.equals("INSERT") == true){
            String select_BP = "Select * from BoPhan";
            try{
                conn = cn.getConnection();
                PreparedStatement pst = conn.prepareStatement(select_BP);
                ResultSet bophan = pst.executeQuery();
                while(bophan.next()){
                    if(bophan.getString("TenBoPhan").trim().equals(tenBP) == true){
                        sb.append("Bộ phận đã tồn tại!\n");
                    }
                }
            } catch(Exception e){
                e.printStackTrace();
            }
            if(sb.length()>0){
                JOptionPane.showMessageDialog(this, sb.toString());
                return;
            }
            insertBP(tenBP,ghichu);
            JOptionPane.showMessageDialog(this, "Đã Thêm Bộ Phận Thành Công");
            Reset();
            return;
        }
        if( CODE.equals("UPDATE") == true){
            String select_DPM = "Select * from NhanVien where BoPhan = ?";
            try{
                conn = cn.getConnection();
                PreparedStatement pst = conn.prepareStatement(select_DPM);
                pst.setString(1, maDPM);
                ResultSet rs = pst.executeQuery();
                if(rs.next()){
                    JOptionPane.showMessageDialog(this, "Không được chỉnh sửa.");
                    return;
                }
            } catch(Exception e){
                e.printStackTrace();
            }
            updateBP(tenBP,ghichu, maDPM);
            JOptionPane.showMessageDialog(this, "Chỉnh Sửa Thông Tin Bộ Phận Thành Công");
            Reset();
            return;
        }
    }//GEN-LAST:event_saveDPMMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addDPM;
    private javax.swing.JButton cancelButton;
    private javax.swing.JTable dpmTable;
    private javax.swing.JTextField ghichuTxt;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton saveDPM;
    private javax.swing.JTextField searchTxt;
    private javax.swing.JTextField tenbophanTxt;
    // End of variables declaration//GEN-END:variables
}
