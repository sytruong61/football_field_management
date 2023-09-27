package Form;

import DataBase.ConnectDB;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

public class OutStorage extends javax.swing.JInternalFrame {
    ConnectDB cn = new ConnectDB();
    Connection conn;
    Locale locale = new Locale("vn", "VN");      
    NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);
    String CODE = "INSERT", maDXH;
    UserSession userSession = UserSession.getInstance();
    String maNV = userSession.getUserID();
    
    public String GetNVfromID(String ID){
        String tenNV = null;
        String select_NVfromID = "Select * from NhanVien where MaNV = ?";
        try{
            conn = cn.getConnection();
            PreparedStatement pst = conn.prepareStatement(select_NVfromID);
            pst.setString(1, ID);
            ResultSet rs = pst.executeQuery();
            if(rs.next()){
               tenNV = rs.getString("HoTen");
            }
        } catch(Exception e){
            e.printStackTrace();
        }
        return tenNV;
    }
    
    public String GetIDNVfromTen(String tenNV){
        String ID = null;
        String select_IDNVfromTen = "Select * from NhanVien where HoTen = ?";
        try{
            conn = cn.getConnection();
            PreparedStatement pst = conn.prepareStatement(select_IDNVfromTen);
            pst.setString(1, tenNV);
            ResultSet rs = pst.executeQuery();
            if(rs.next()){
               ID = rs.getString("MaNV");
            }
        } catch(Exception e){
            e.printStackTrace();
        }
        return ID;
    }
    
    public String randomMaDXH(){
        String randomString = null;
        int n = 5; // số lượng ký tự trong chuỗi
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
    
    public final void SelectAllDXH(){
        DefaultTableModel dm = (DefaultTableModel)dxhTable.getModel();
        dm.getDataVector().removeAllElements();
        dm.fireTableDataChanged(); 
        ColumnsAutoSizer.sizeColumnsToFit(dxhTable);
        String select_DXH = "Select * from DONXUATHANG";
        try{
            conn = cn.getConnection();
            PreparedStatement pst = conn.prepareStatement(select_DXH);
            ResultSet rs = pst.executeQuery();
            DefaultTableModel model = (DefaultTableModel) dxhTable.getModel();
            String MaDXH, TenNV, NgayXuat, GhiChu;
            while(rs.next()){
                MaDXH = rs.getString("MaDXH");
                TenNV = GetNVfromID(rs.getString("MaNV"));
                SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
                SimpleDateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy");
                Date date = inputFormat.parse(rs.getString("NgayXuat"));
                NgayXuat = outputFormat.format(date);
                GhiChu = rs.getString("GhiChu");
                String[] dxh = {MaDXH, TenNV, NgayXuat, GhiChu};
                model.addRow(dxh);
            }
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public final void SetNVBox(){
        try {
            String select_NV = "Select * from NhanVien";
            PreparedStatement pst4 = conn.prepareStatement(select_NV);
            ResultSet nv = pst4.executeQuery();
            while(nv.next()){
                String nvName = nv.getString("HoTen");
                nvBox.addItem(nvName);
            }            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public final void Reset(){
        SelectAllDXH();
        SetNVBox();
        maPXH.setText("");
        nvBox.setSelectedItem(GetNVfromID(maNV));
        ngayXHBox.setDate(java.sql.Date.valueOf(java.time.LocalDate.now()));
        ghichuTxt.setText("");
        CODE = "INSERT";
    }
    
    public OutStorage() {
        initComponents();
        ImageIcon img = new ImageIcon("D:\\Football_Filed_Managerment\\src\\images\\icons/—Pngtree—football field plan_4710125 (1).png");
        this.setFrameIcon(img);
        Reset();
        dxhTable.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent mouseEvent) {
                JTable table =(JTable) mouseEvent.getSource();
                java.awt.Point point = mouseEvent.getPoint();
                int row = table.rowAtPoint(point);
                if(row == -1){
                    return;
                }
                maDXH =  dxhTable.getModel().getValueAt(row, 0).toString();
                String tenNV = dxhTable.getModel().getValueAt(row, 1).toString();
                String ghiChu = null;
                if(dxhTable.getModel().getValueAt(row, 3) != null){
                    ghiChu = dxhTable.getModel().getValueAt(row, 3).toString();
                }
                String ngayXuat = dxhTable.getModel().getValueAt(row, 2).toString();
                if (mouseEvent.getClickCount() == 1 && table.getSelectedRow() != -1) {
                    try {
                        maPXH.setText(maDXH);
                        nvBox.setSelectedItem(tenNV);
                        nvBox.setEnabled(false);
                        SimpleDateFormat inputFormat = new SimpleDateFormat("dd-MM-yyy");
                        Date date = inputFormat.parse(ngayXuat);
                        ngayXHBox.setDate(date);
                        ghichuTxt.setText(ghiChu);
                        CODE = "UPDATE";
                    } catch (ParseException ex) {
                        Logger.getLogger(OutStorage.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } 
                if (mouseEvent.getClickCount() == 2 && table.getSelectedRow() != -1) {
                    new ChiTietPhieuXuat("VIEW", tenNV, maDXH).setVisible(true);
                } 
            }
        });
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel7 = new javax.swing.JLabel();
        nvBox = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        ngayXHBox = new com.toedter.calendar.JDateChooser();
        jLabel6 = new javax.swing.JLabel();
        ghichuTxt = new javax.swing.JTextField();
        searchTxt = new javax.swing.JTextField();
        addDNH = new javax.swing.JButton();
        saveDNH = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        dxhTable = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        maPXH = new javax.swing.JTextField();

        setTitle("Đơn Xuất Hàng");

        jLabel7.setText("Nhân Viên");

        nvBox.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(41, 128, 185), 1, true));
        nvBox.setPreferredSize(new java.awt.Dimension(0, 21));

        jLabel5.setText("Ngày Xuất");

        ngayXHBox.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(41, 128, 185), 1, true));
        ngayXHBox.setPreferredSize(new java.awt.Dimension(74, 21));

        jLabel6.setText("Ghi Chú");

        ghichuTxt.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(41, 128, 185), 1, true));
        ghichuTxt.setPreferredSize(new java.awt.Dimension(0, 21));

        searchTxt.setText("Nhập để tìm kiếm");
        searchTxt.setToolTipText("");
        searchTxt.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(41, 128, 185), 1, true));
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

        addDNH.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/056-plus.png"))); // NOI18N
        addDNH.setText("Thêm");
        addDNH.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                addDNHMouseClicked(evt);
            }
        });

        saveDNH.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/022-save.png"))); // NOI18N
        saveDNH.setText("Lưu");
        saveDNH.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                saveDNHMouseClicked(evt);
            }
        });

        cancelButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/058-error.png"))); // NOI18N
        cancelButton.setText("Hủy");
        cancelButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cancelButtonMouseClicked(evt);
            }
        });

        dxhTable.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(41, 128, 185), 1, true));
        dxhTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã Đơn Xuất", "Nhân Viên ", "Ngày Xuất", "Ghi Chú"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(dxhTable);

        jLabel1.setText("Mã Phiếu Xuất");

        maPXH.setText(" ");
        maPXH.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(41, 128, 185), 1, true));
        maPXH.setPreferredSize(new java.awt.Dimension(0, 21));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 654, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(23, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(searchTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(addDNH)
                                .addGap(11, 11, 11))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1)
                                    .addComponent(jLabel5))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(maPXH, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(ngayXHBox, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(21, 21, 21)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel6))))
                        .addGap(29, 29, 29)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(saveDNH)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(cancelButton))
                            .addComponent(ghichuTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(nvBox, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(91, 91, 91))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(nvBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1)
                            .addComponent(maPXH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel6)
                                .addComponent(ghichuTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(ngayXHBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jLabel5))
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(saveDNH)
                    .addComponent(addDNH)
                    .addComponent(cancelButton)
                    .addComponent(searchTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 357, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(21, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void searchTxtMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_searchTxtMouseClicked
        searchTxt.setText("");
    }//GEN-LAST:event_searchTxtMouseClicked
    
    public void filter(String query){
        DefaultTableModel dm = (DefaultTableModel) dxhTable.getModel();
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<DefaultTableModel>(dm);
        dxhTable.setRowSorter(tr);
        tr.setRowFilter(RowFilter.regexFilter(query));
    }
    
    private void searchTxtKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchTxtKeyReleased
        String query = searchTxt.getText().trim();
        filter(query);
    }//GEN-LAST:event_searchTxtKeyReleased

    private void addDNHMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addDNHMouseClicked
        try {
            Reset();
            CODE = "INSERT";
            maDXH = randomMaDXH();
            maPXH.setText(maDXH);
        } catch(Exception e){
            e.printStackTrace();
        }
    }//GEN-LAST:event_addDNHMouseClicked

    public void InsertDXH(String maDXH, String maNV, String ngayXuat, String ghiChu ){
        try{
            String insert = "INSERT INTO DONXUATHANG( MaDXH, MaNV, NgayXuat, GhiChu ) VALUES(?,?,?,?)";
            conn = cn.getConnection();
            PreparedStatement pst = conn.prepareStatement(insert);
            pst.setString(1, maDXH);
            pst.setString(2, maNV);
            pst.setString(3, ngayXuat);
            pst.setString(4, ghiChu);
            pst.execute();
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void UpdateDXH(String maDXH, String maNV, String ngayXuat, String ghiChu ){
        try{
            String update = "UPDATE DONXUATHANG SET MaNV = ?, NgayXuat = ?, GhiChu = ? WHERE MaDXH = ? ";
            conn = cn.getConnection();
            PreparedStatement pst = conn.prepareStatement(update);
            pst.setString(1, maNV);
            pst.setString(2, ngayXuat);
            pst.setString(3, ghiChu);
            pst.setString(4, maDXH);
            pst.execute();
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    
    private void saveDNHMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_saveDNHMouseClicked
        String tenNV = nvBox.getSelectedItem().toString().trim();
        java.sql.Date ngayXuat = new java.sql.Date(ngayXHBox.getDate().getTime());
        String ghichu = ghichuTxt.getText().trim();
        StringBuffer sb = new StringBuffer();
        if(maDXH.length() == 0 || tenNV.length() == 0){
            sb.append("Không được để trống thông tin!\n");
        }
        if(sb.length()>0){
            JOptionPane.showMessageDialog(this, sb.toString());
            return;
        }
        if( CODE.equals("INSERT") == true){
            InsertDXH(maDXH, maNV, ngayXuat.toString(), ghichu);
            JOptionPane.showMessageDialog(this, "Đã Thêm Đơn Xuất Hàng Thành Công");
            Reset();
        }
        if( CODE.equals("UPDATE") == true){
            UpdateDXH(maDXH, maNV, ngayXuat.toString(), ghichu);
            JOptionPane.showMessageDialog(this, "Chỉnh Sửa Thông Tin Đơn Xuất Hàng Thành Công");
            Reset();
        }
    }//GEN-LAST:event_saveDNHMouseClicked

    private void cancelButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cancelButtonMouseClicked
        this.dispose();
    }//GEN-LAST:event_cancelButtonMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addDNH;
    private javax.swing.JButton cancelButton;
    private javax.swing.JTable dxhTable;
    private javax.swing.JTextField ghichuTxt;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField maPXH;
    private com.toedter.calendar.JDateChooser ngayXHBox;
    private javax.swing.JComboBox<String> nvBox;
    private javax.swing.JButton saveDNH;
    private javax.swing.JTextField searchTxt;
    // End of variables declaration//GEN-END:variables
}
