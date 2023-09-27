package Form;

import DataBase.ConnectDB;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

public final class Storage extends javax.swing.JInternalFrame {
    ConnectDB cn = new ConnectDB();
    Connection conn;
    String CODE = "INSERT", ID;
    
    public String GetNCCfromID(String ID){
        String tenNCC = null;
        String select_NCCfromID = "Select * from NhaCungCap where MaNCC = ?";
        try{
            conn = cn.getConnection();
            PreparedStatement pst = conn.prepareStatement(select_NCCfromID);
            pst.setString(1, ID);
            ResultSet rs = pst.executeQuery();
            if(rs.next()){
               tenNCC = rs.getString("TenNCC");
            }
        } catch(Exception e){
            e.printStackTrace();
        }
        return tenNCC;
    }
    
    public String GetIDNCCfromTen(String tenNCC){
        String ID = null;
        String select_NCCfromTen = "Select * from NhaCungCap where TenNCC = ?";
        try{
            conn = cn.getConnection();
            PreparedStatement pst = conn.prepareStatement(select_NCCfromTen);
            pst.setString(1, tenNCC);
            ResultSet rs = pst.executeQuery();
            if(rs.next()){
               ID = rs.getString("MaNCC");
            }
        } catch(Exception e){
            e.printStackTrace();
        }
        return ID;
    }

    public Storage(Connection conn, String ID) {
        this.conn = conn;
        this.ID = ID;
    }
    public void SelectAllST(){
        String select_ST = "Select * from TaiSanThietBi";
        try{
            conn = cn.getConnection();
            PreparedStatement pst = conn.prepareStatement(select_ST);
            ResultSet rs = pst.executeQuery();
            DefaultTableModel model = (DefaultTableModel) stTable.getModel();
            String ID, TEN, TenNCC, LOAI, STATUS, SOLUONG, DVT, GhiChu;
            while(rs.next()){
               ID = rs.getString("MaTSTB");
               TEN = rs.getString("TenTSTB");
               TenNCC = GetNCCfromID(rs.getString("MaNCC"));
               LOAI = rs.getString("LoaiTSTB");
               STATUS = rs.getString("TinhTrangTSTB");
               SOLUONG = rs.getString("SOLUONGTSTB");
               DVT = rs.getString("DVT");
               if(!rs.getString("GhiChu").equals("PAUSED")){
                    GhiChu = rs.getString("GhiChu");
                    String[] ncc = {ID, TEN, TenNCC, LOAI, STATUS, SOLUONG, DVT, GhiChu};
                    model.addRow(ncc);
               }
            }
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void SetNCCBox(){
        nccBox.removeAllItems();
        try {
            String select_NCC = "Select * from NhaCungCap";
            PreparedStatement pst4 = conn.prepareStatement(select_NCC);
            ResultSet ncc = pst4.executeQuery();
            while(ncc.next()){
                String nccName = ncc.getString("TenNCC");
                nccBox.addItem(nccName);
            }            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        countTxt.setText("0");
    }
    
    public Storage() {
        initComponents();
        ImageIcon img = new ImageIcon("D:\\Football_Filed_Managerment\\src\\images\\icons/—Pngtree—football field plan_4710125 (1).png");
        this.setFrameIcon(img);
        tentstbTxt.setText("");
        countTxt.setEditable(false);
        SelectAllST();
        SetNCCBox();
        nccBox.setSelectedIndex(-1);
        categoryBox.setSelectedIndex(-1);
        statusBox.setSelectedIndex(1);
        statusBox.setEnabled(false);
        ColumnsAutoSizer.sizeColumnsToFit(stTable);
        stTable.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent mouseEvent) {
                JTable table =(JTable) mouseEvent.getSource();
                java.awt.Point point = mouseEvent.getPoint();
                int row = table.rowAtPoint(point);
                if(row == -1){
                    return;
                }
                String tstbID = stTable.getModel().getValueAt(row, 0).toString();
                if (mouseEvent.getClickCount() == 1 && table.getSelectedRow() != -1) {
                    try {
                        conn = cn.getConnection();
                        String select_1TSTB = "Select * from TaiSanThietBi where MaTSTB = ?";
                        PreparedStatement pst3 = conn.prepareStatement(select_1TSTB);
                        pst3.setString(1, tstbID);
                        ResultSet tstb = pst3.executeQuery();
                        if(tstb.next()){
                            tentstbTxt.setText(tstb.getString("TenTSTB").trim());
                            nccBox.setSelectedItem(GetNCCfromID(tstb.getString("MaNCC")));
                            categoryBox.setSelectedItem(tstb.getString("LoaiTSTB"));
                            statusBox.setSelectedItem(tstb.getString("TinhTrangTSTB"));
                            countTxt.setText(tstb.getString("SOLUONGTSTB"));
                            dvtTxt.setText(tstb.getString("DVT"));
                            ghichuTxt.setText(tstb.getString("GhiChu"));
                        }
                        CODE = "UPDATE";
                        ID = tstbID;
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                }   
            }
        });
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        searchTxt = new javax.swing.JTextField();
        addTSTB = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        cancelButton = new javax.swing.JButton();
        saveTSTB = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        fixTSTB = new javax.swing.JButton();
        tentstbTxt = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        stTable = new javax.swing.JTable();
        nccBox = new javax.swing.JComboBox<>();
        categoryBox = new javax.swing.JComboBox<>();
        statusBox = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        countTxt = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        ghichuTxt = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        dvtTxt = new javax.swing.JTextField();

        setTitle("Quản Lý Kho");

        searchTxt.setText("Nhập để tìm kiếm");
        searchTxt.setToolTipText("");
        searchTxt.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(41, 128, 185), 1, true));
        searchTxt.setMinimumSize(new java.awt.Dimension(3, 21));
        searchTxt.setPreferredSize(new java.awt.Dimension(0, 21));
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

        addTSTB.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/056-plus.png"))); // NOI18N
        addTSTB.setText("Thêm");
        addTSTB.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                addTSTBMouseClicked(evt);
            }
        });

        jLabel4.setForeground(new java.awt.Color(41, 128, 185));
        jLabel4.setText("Tình Trạng");

        jLabel1.setForeground(new java.awt.Color(41, 128, 185));
        jLabel1.setText("Tên Tài Sản - Thiết Bị");

        cancelButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/058-error.png"))); // NOI18N
        cancelButton.setText("Hủy");
        cancelButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cancelButtonMouseClicked(evt);
            }
        });

        saveTSTB.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/022-save.png"))); // NOI18N
        saveTSTB.setText("Lưu");
        saveTSTB.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                saveTSTBMouseClicked(evt);
            }
        });

        jLabel3.setForeground(new java.awt.Color(41, 128, 185));
        jLabel3.setText("Loại Tài Sản - Thiết Bị");

        jLabel2.setForeground(new java.awt.Color(41, 128, 185));
        jLabel2.setText("Nhà Cung Cấp");

        fixTSTB.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/218-edit.png"))); // NOI18N
        fixTSTB.setText("Chỉnh sửa ");
        fixTSTB.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                fixTSTBMouseClicked(evt);
            }
        });

        tentstbTxt.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(41, 128, 185), 1, true));
        tentstbTxt.setPreferredSize(new java.awt.Dimension(0, 21));

        stTable.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(41, 128, 185), 1, true));
        stTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã", "Tên ", "Nhà Cung Cấp", "Loại", "Tình Trạng", "Số Lượng", "Đơn Vị Tính", "Ghi Chú"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(stTable);

        nccBox.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(41, 128, 185), 1, true));
        nccBox.setPreferredSize(new java.awt.Dimension(0, 21));

        categoryBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "F&B", "Cho Thuê", "Thiết Bị Sân Bóng" }));
        categoryBox.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(41, 128, 185), 1, true));
        categoryBox.setPreferredSize(new java.awt.Dimension(0, 21));

        statusBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Còn Hàng", "Hết Hàng", "Ngừng Sử Dụng" }));
        statusBox.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(41, 128, 185), 1, true));
        statusBox.setPreferredSize(new java.awt.Dimension(0, 21));

        jLabel5.setForeground(new java.awt.Color(41, 128, 185));
        jLabel5.setText("Số Lượng");

        countTxt.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(41, 128, 185), 1, true));
        countTxt.setPreferredSize(new java.awt.Dimension(0, 21));

        jLabel6.setForeground(new java.awt.Color(41, 128, 185));
        jLabel6.setText("Ghi Chú");

        ghichuTxt.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(41, 128, 185), 1, true));
        ghichuTxt.setPreferredSize(new java.awt.Dimension(0, 21));

        jLabel7.setForeground(new java.awt.Color(41, 128, 185));
        jLabel7.setText("Đơn Vị Tính");

        dvtTxt.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(41, 128, 185), 1, true));
        dvtTxt.setPreferredSize(new java.awt.Dimension(0, 21));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel6))
                                .addGap(25, 25, 25)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(tentstbTxt, javax.swing.GroupLayout.DEFAULT_SIZE, 190, Short.MAX_VALUE)
                                    .addComponent(categoryBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(statusBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(ghichuTxt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(26, 26, 26)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel7))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(countTxt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(nccBox, 0, 190, Short.MAX_VALUE)
                                    .addComponent(dvtTxt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(searchTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(addTSTB)
                                .addGap(18, 18, 18)
                                .addComponent(fixTSTB)
                                .addGap(18, 18, 18)
                                .addComponent(saveTSTB)
                                .addGap(18, 18, 18)
                                .addComponent(cancelButton)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 1020, Short.MAX_VALUE))
                .addGap(22, 22, 22))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(tentstbTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(nccBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(17, 17, 17)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(countTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(categoryBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(17, 17, 17)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel7)
                        .addComponent(dvtTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel4)
                        .addComponent(statusBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(ghichuTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(searchTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(addTSTB)
                    .addComponent(fixTSTB)
                    .addComponent(saveTSTB)
                    .addComponent(cancelButton))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 357, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    public void Reset(){
        DefaultTableModel dm = (DefaultTableModel)stTable.getModel();
        dm.getDataVector().removeAllElements();
        dm.fireTableDataChanged(); 
        SelectAllST();
        SetNCCBox();
        tentstbTxt.setText("");
        nccBox.setSelectedIndex(-1);
        categoryBox.setSelectedIndex(-1);
        statusBox.setSelectedIndex(1);
        countTxt.setText("0");
        dvtTxt.setText("");
        ghichuTxt.setText("");
        CODE = "INSERT";
        ColumnsAutoSizer.sizeColumnsToFit(stTable);
    }
    
    private void searchTxtMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_searchTxtMouseClicked
        searchTxt.setText("");
    }//GEN-LAST:event_searchTxtMouseClicked
    
    public void filter(String query){
        DefaultTableModel dm = (DefaultTableModel) stTable.getModel();
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<DefaultTableModel>(dm);
        stTable.setRowSorter(tr);
        tr.setRowFilter(RowFilter.regexFilter(query));
    }
    
    private void searchTxtKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchTxtKeyReleased
        String query = searchTxt.getText().trim();
        filter(query);
    }//GEN-LAST:event_searchTxtKeyReleased

    private void addTSTBMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addTSTBMouseClicked
        try {
        Reset();
        CODE = "INSERT";
        } catch(Exception e){
            e.printStackTrace();
        }
    }//GEN-LAST:event_addTSTBMouseClicked

    private void cancelButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cancelButtonMouseClicked
        this.dispose();
    }//GEN-LAST:event_cancelButtonMouseClicked

    public void insertTSTB(String maNCC, String tenTSTB, String loaiTSTB, String statusTSTB, String soluongTSTB, String dvt, String ghichu){
        try{
            String insert = "INSERT INTO TaiSanThietBi(MaNCC, TenTSTB, LoaiTSTB, TinhTrangTSTB, SOLUONGTSTB, DVT, GhiChu) VALUES(?,?,?,?,?,?,?)";
            conn = cn.getConnection();
            PreparedStatement pst2 = conn.prepareStatement(insert);
            pst2.setString(1, maNCC);
            pst2.setString(2, tenTSTB);
            pst2.setString(3, loaiTSTB);
            pst2.setString(4, statusTSTB);
            pst2.setString(5, soluongTSTB);
            pst2.setString(6, dvt);
            pst2.setString(7, ghichu);
            pst2.execute();
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void updateTSTB(String statusTSTB,String dvt, String ghichu, String maTSTB){
        try{
            String update = "UPDATE TaiSanThietBi SET TinhTrangTSTB = ?, DVT = ?, GhiChu = ? WHERE MaTSTB = ?";
            conn = cn.getConnection();
            PreparedStatement pst2 = conn.prepareStatement(update);
            pst2.setString(1, statusTSTB);
            pst2.setString(2, dvt);
            pst2.setString(3, ghichu);
            pst2.setString(4, maTSTB);
            pst2.execute();
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    
    private void saveTSTBMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_saveTSTBMouseClicked
        String tenNCC = null, loaiTSTB = null, statusTSTB = null;
        StringBuffer sb = new StringBuffer();
        String tenTSTB = tentstbTxt.getText().trim();
        if(nccBox.getSelectedIndex() != -1 && categoryBox.getSelectedIndex() != -1 && statusBox.getSelectedIndex() != -1 ){
            tenNCC = nccBox.getSelectedItem().toString().trim();
            loaiTSTB = categoryBox.getSelectedItem().toString().trim();
            statusTSTB = statusBox.getSelectedItem().toString().trim();  
        }else{
            sb.append("Thông tin không được để trống!\n");
        }
        String soluongTSTB = countTxt.getText().trim();
        String dvt = dvtTxt.getText().trim();
        String ghichu = ghichuTxt.getText().trim();
        if(tenTSTB.length() == 0 || soluongTSTB.length() == 0 || dvt.length() == 0){
            sb.append("Thông tin không được để trống!\n");
        }
        if(sb.length()>0){
            JOptionPane.showMessageDialog(this, sb.toString());
            return;
        }
        if( CODE.equals("INSERT") == true){
            String select_TSTB = "Select * from TaiSanThietBi";
            try{
                conn = cn.getConnection();
                PreparedStatement pst = conn.prepareStatement(select_TSTB);
                ResultSet tstb = pst.executeQuery();
                while(tstb.next()){
                    if(tstb.getString("TenTSTB").trim().equals(tenTSTB) == true){
                        sb.append("Tài Sản hoặc Thiết Bị đã tồn tại!\n");
                    }
                }
            } catch(Exception e){
                e.printStackTrace();
            }
            if(sb.length()>0){
                JOptionPane.showMessageDialog(this, sb.toString());
                return;
            }
            insertTSTB(GetIDNCCfromTen(tenNCC), tenTSTB, loaiTSTB, statusTSTB, soluongTSTB, dvt, ghichu);
            JOptionPane.showMessageDialog(this, "Đã Thêm Tài Sản Thiết Bị Thành Công");
            Reset();
        }
        if( CODE.equals("UPDATE") == true){
            updateTSTB(statusTSTB, dvt, ghichu,ID);
            JOptionPane.showMessageDialog(this, "Chỉnh Sửa Thông Tin Tài Sản Thiết Bị Thành Công");
            Reset();
        }
    }//GEN-LAST:event_saveTSTBMouseClicked

    private void fixTSTBMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_fixTSTBMouseClicked
        int column = 0;
        if(stTable.getSelectionModel().isSelectionEmpty()){
            JOptionPane.showMessageDialog(this, "Vui Lòng chọn dòng cần chỉnh sửa");
            return;
        }
        int row = stTable.getSelectedRow();
        String tstbID = stTable.getModel().getValueAt(row, column).toString();
        try {
            conn = cn.getConnection();
            String select_1TSTB = "Select * from TaiSanThietBi where MaTSTB = ?";
            PreparedStatement pst3 = conn.prepareStatement(select_1TSTB);
            pst3.setString(1, tstbID);
            ResultSet tstb = pst3.executeQuery();
            if(tstb.next()){
                tentstbTxt.setText(tstb.getString("TenTSTB").trim());
                nccBox.setSelectedItem(GetNCCfromID(tstb.getString("MaNCC")));
                categoryBox.setSelectedItem(tstb.getString("LoaiTSTB"));
                statusBox.setSelectedItem(tstb.getString("TinhTrangTSTB"));
                countTxt.setText(tstb.getString("SOLUONGTSTB"));
                dvtTxt.setText(tstb.getString("DVT"));
                ghichuTxt.setText(tstb.getString("GhiChu"));
            }
            tentstbTxt.setEditable(false);
            nccBox.setEditable(false);
            categoryBox.setEditable(false);
            countTxt.setEditable(false);
            CODE = "UPDATE";
            ID = tstbID;
        } catch (Exception e){
            e.printStackTrace();
        }
    }//GEN-LAST:event_fixTSTBMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addTSTB;
    private javax.swing.JButton cancelButton;
    private javax.swing.JComboBox<String> categoryBox;
    private javax.swing.JTextField countTxt;
    private javax.swing.JTextField dvtTxt;
    private javax.swing.JButton fixTSTB;
    private javax.swing.JTextField ghichuTxt;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JComboBox<String> nccBox;
    private javax.swing.JButton saveTSTB;
    private javax.swing.JTextField searchTxt;
    private javax.swing.JTable stTable;
    private javax.swing.JComboBox<String> statusBox;
    private javax.swing.JTextField tentstbTxt;
    // End of variables declaration//GEN-END:variables
}
