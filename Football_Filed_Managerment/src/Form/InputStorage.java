package Form;

import DataBase.ConnectDB;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.Locale;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import javax.xml.namespace.QName;

public class InputStorage extends javax.swing.JInternalFrame {
    ConnectDB cn = new ConnectDB();
    Connection conn;
    Locale locale = new Locale("vn", "VN");      
    NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);
    String CODE = "INSERT", maDNH = null;
    UserSession userSession = UserSession.getInstance();
    String IDNV = userSession.getUserID();
    
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
    
    public final void SelectAllDNH(){
        String select_DNH = "Select * from DonNhapHang";
        try{
            conn = cn.getConnection();
            PreparedStatement pst = conn.prepareStatement(select_DNH);
            ResultSet rs = pst.executeQuery();
            DefaultTableModel model = (DefaultTableModel) dnhTable.getModel();
            String ID, TenNCC, TenNV, NgayNhap, TongTien, GhiChu;
            while(rs.next()){
               ID = rs.getString("MaDNH");
               TenNCC = GetNCCfromID(rs.getString("MaNCC"));
               TenNV = GetNVfromID(rs.getString("MaNV"));
               NgayNhap = rs.getString("NgayNhap");
               TongTien = String.valueOf(currencyFormatter.format(Double.parseDouble(rs.getString("TongTien"))));
               if(!rs.getString("GhiChu").equals("PAUSED")){
                    GhiChu = rs.getString("GhiChu");
                    String[] dnh = {ID, TenNCC, TenNV, NgayNhap, TongTien, GhiChu};
                    model.addRow(dnh);
               }
            }
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public final void SetNCCBox(){
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
    }
    
    public final void SetNVBox(){
        nvBox.removeAllItems();
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
        DefaultTableModel dm = (DefaultTableModel)dnhTable.getModel();
        dm.getDataVector().removeAllElements();
        dm.fireTableDataChanged(); 
        SelectAllDNH();
        SetNCCBox();
        SetNVBox();
        nccBox.setSelectedIndex(-1);
        nvBox.setSelectedItem(GetNVfromID(IDNV));
        nvBox.setEnabled(false);
        ngaynhapBox.setDate(java.sql.Date.valueOf(java.time.LocalDate.now()));
        moneyTxt.setText("0");
        ghichuTxt.setText("");
        CODE = "INSERT";
        ColumnsAutoSizer.sizeColumnsToFit(dnhTable);
    }
    
    public InputStorage() {
        initComponents();
        ImageIcon img = new ImageIcon("D:\\Football_Filed_Managerment\\src\\images\\icons/—Pngtree—football field plan_4710125 (1).png");
        this.setFrameIcon(img);
        Reset();
        dnhTable.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent mouseEvent) {
                JTable table =(JTable) mouseEvent.getSource();
                java.awt.Point point = mouseEvent.getPoint();
                int row = table.rowAtPoint(point);
                if(row == -1){
                    return;
                }
                String dnhID = dnhTable.getModel().getValueAt(row, 0).toString();
                String tenNCC = dnhTable.getModel().getValueAt(row, 1).toString();
                String tenNV = dnhTable.getModel().getValueAt(row, 2).toString();
                if (mouseEvent.getClickCount() == 1 && table.getSelectedRow() != -1) {
                    try {
                        conn = cn.getConnection();
                        String select_1DNH = "Select * from DonNhapHang where MaDNH = ?";
                        PreparedStatement pst3 = conn.prepareStatement(select_1DNH);
                        pst3.setString(1, dnhID);
                        ResultSet dnh = pst3.executeQuery();
                        if(dnh.next()){
                            nccBox.setSelectedItem(GetNCCfromID(dnh.getString("MaNCC")));
                            nvBox.setSelectedItem(GetNVfromID(dnh.getString("MaNV")));
                            ngaynhapBox.setDate(java.sql.Date.valueOf(dnh.getString("NgayNhap")));
                            moneyTxt.setText(dnh.getString("TongTien"));
                            moneyLable.setText(currencyFormatter.format(Double.parseDouble(moneyTxt.getText())));
                            ghichuTxt.setText(dnh.getString("GhiChu"));
                        }
                        nccBox.setEditable(false);
                        nvBox.setEditable(false);
                        moneyTxt.setEditable(false);
                        ngaynhapBox.setEnabled(false);
                        CODE = "UPDATE";
                        maDNH = dnhID;
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                } 
                if (mouseEvent.getClickCount() == 2 && table.getSelectedRow() != -1) {
                    new DetailInputStorage("VIEW",tenNCC, tenNV, dnhID).setVisible(true);
                } 
            }
        });
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        nccBox = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        ghichuTxt = new javax.swing.JTextField();
        searchTxt = new javax.swing.JTextField();
        addDNH = new javax.swing.JButton();
        saveDNH = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        dnhTable = new javax.swing.JTable();
        jLabel7 = new javax.swing.JLabel();
        nvBox = new javax.swing.JComboBox<>();
        ngaynhapBox = new com.toedter.calendar.JDateChooser();
        moneyTxt = new javax.swing.JTextField();
        moneyLable = new javax.swing.JLabel();
        reloadButton = new javax.swing.JButton();

        setTitle("Quản Lý Đơn Nhập");

        nccBox.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(41, 128, 185), 1, true));
        nccBox.setPreferredSize(new java.awt.Dimension(0, 21));

        jLabel2.setForeground(new java.awt.Color(41, 128, 185));
        jLabel2.setText("Nhà Cung Cấp");

        jLabel5.setForeground(new java.awt.Color(41, 128, 185));
        jLabel5.setText("Ngày Nhập");

        jLabel4.setForeground(new java.awt.Color(41, 128, 185));
        jLabel4.setText("Tổng Tiền");

        jLabel6.setForeground(new java.awt.Color(41, 128, 185));
        jLabel6.setText("Ghi Chú");

        ghichuTxt.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(41, 128, 185), 1, true));
        ghichuTxt.setPreferredSize(new java.awt.Dimension(0, 21));

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

        dnhTable.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(41, 128, 185)));
        dnhTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã Đơn Nhập", "Nhà Cung Cấp", "Nhân Viên Nhập", "Ngày Nhập", "Tổng Tiền", "Ghi Chú"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(dnhTable);

        jLabel7.setForeground(new java.awt.Color(41, 128, 185));
        jLabel7.setText("Nhân Viên");

        nvBox.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(41, 128, 185), 1, true));
        nvBox.setPreferredSize(new java.awt.Dimension(0, 21));

        ngaynhapBox.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(41, 128, 185), 1, true));

        moneyTxt.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(41, 128, 185), 1, true));
        moneyTxt.setPreferredSize(new java.awt.Dimension(0, 21));

        moneyLable.setText("                    ");

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
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 654, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(searchTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(addDNH)
                                .addGap(41, 41, 41)
                                .addComponent(saveDNH)
                                .addGap(29, 29, 29)
                                .addComponent(reloadButton))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel5))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(ngaynhapBox, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(nccBox, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel7)
                                        .addGap(18, 18, 18)
                                        .addComponent(nvBox, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addGap(54, 54, 54)
                                .addComponent(ghichuTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel4)
                                .addGap(18, 18, 18)
                                .addComponent(moneyTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(moneyLable)
                            .addComponent(cancelButton))))
                .addContainerGap(22, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel7)
                    .addComponent(nvBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(nccBox, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(ngaynhapBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 27, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(ghichuTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(moneyTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(moneyLable))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(searchTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(addDNH)
                    .addComponent(saveDNH)
                    .addComponent(cancelButton)
                    .addComponent(reloadButton))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 357, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void searchTxtMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_searchTxtMouseClicked
        searchTxt.setText("");
    }//GEN-LAST:event_searchTxtMouseClicked
    
    public void filter(String query){
        DefaultTableModel dm = (DefaultTableModel) dnhTable.getModel();
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<DefaultTableModel>(dm);
        dnhTable.setRowSorter(tr);
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
        } catch(Exception e){
            e.printStackTrace();
        }
    }//GEN-LAST:event_addDNHMouseClicked

    public void insertDNH(String maNCC, String maNV, String ngayNhap, String tongTien, String ghichu){
        try{
            String insert = "INSERT INTO DonNhapHang(MaNCC, MaNV, NgayNhap, TongTien, GhiChu) VALUES(?,?,?,?,?)";
            conn = cn.getConnection();
            PreparedStatement pst2 = conn.prepareStatement(insert);
            pst2.setString(1, maNCC);
            pst2.setString(2, maNV);
            pst2.setString(3, ngayNhap);
            pst2.setString(4, tongTien);
            pst2.setString(5, ghichu);
            pst2.execute();
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void updateDNH(String maNCC, String maNV, String ngayNhap, String ghichu, String maDNH){
        try{
            String update = "UPDATE DonNhapHang SET MaNCC = ?, MaNV = ?, NgayNhap = ?, GhiChu = ? WHERE MaDNH = ?";
            conn = cn.getConnection();
            PreparedStatement pst2 = conn.prepareStatement(update);
            pst2.setString(1, maNCC);
            pst2.setString(2, maNV);
            pst2.setString(3, ngayNhap);
            pst2.setString(4, ghichu);
            pst2.setString(5, maDNH);
            pst2.execute();
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    
    private void saveDNHMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_saveDNHMouseClicked
        String tenNCC = nccBox.getSelectedItem().toString().trim();
        String tenNV = nvBox.getSelectedItem().toString().trim();
        java.sql.Date ngayNhap = new java.sql.Date(ngaynhapBox.getDate().getTime());
        String ghichu = ghichuTxt.getText().trim();
        StringBuffer sb = new StringBuffer();
        if(tenNCC.length() == 0 || tenNV.length() == 0){
            sb.append("Không được để trống thông tin!\n");
        }
        if(sb.length()>0){
            JOptionPane.showMessageDialog(this, sb.toString());
            return;
        }
        if( CODE.equals("INSERT") == true){
            String select_DNH = "Select * from DonNhapHang";
            try{
                conn = cn.getConnection();
                PreparedStatement pst = conn.prepareStatement(select_DNH);
                ResultSet dnh = pst.executeQuery();
                while(dnh.next()){
                    int count = 0;
                    if(dnh.getString("MaNCC").trim().equals(GetIDNCCfromTen(tenNCC)) == true){
                        count++;
                    }
                    if(dnh.getString("MaNV").trim().equals(GetIDNVfromTen(tenNV)) == true){
                        count++;
                    }
                    if(dnh.getString("NgayNhap").trim().equals(String.valueOf(ngayNhap)) == true){
                        count++;
                    }
                    if(count == 3){
                        sb.append("Đơn Nhập Hàng này có thể đã tồn tại trong hôm nay hãy kiểm tra lại và cập nhật");
                    }
                }
            } catch(Exception e){
                e.printStackTrace();
            }
            if(sb.length()>0){
                JOptionPane.showMessageDialog(this, sb.toString());
                return;
            }
            insertDNH(GetIDNCCfromTen(tenNCC), GetIDNVfromTen(tenNV), String.valueOf(ngayNhap), "0" , ghichu);
            JOptionPane.showMessageDialog(this, "Đã Thêm Đơn Nhập Hàng Thành Công");
            Reset();
        }
        if( CODE.equals("UPDATE") == true){
            updateDNH(GetIDNCCfromTen(tenNCC), GetIDNVfromTen(tenNV),String.valueOf(ngayNhap),ghichu,maDNH);
            JOptionPane.showMessageDialog(this, "Chỉnh Sửa Thông Tin Đơn Nhập Hàng Thành Công");
            Reset();
        }
    }//GEN-LAST:event_saveDNHMouseClicked

    private void cancelButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cancelButtonMouseClicked
        this.dispose();
    }//GEN-LAST:event_cancelButtonMouseClicked

    private void reloadButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_reloadButtonMouseClicked
        Reset();
    }//GEN-LAST:event_reloadButtonMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addDNH;
    private javax.swing.JButton cancelButton;
    private javax.swing.JTable dnhTable;
    private javax.swing.JTextField ghichuTxt;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel moneyLable;
    private javax.swing.JTextField moneyTxt;
    private javax.swing.JComboBox<String> nccBox;
    private com.toedter.calendar.JDateChooser ngaynhapBox;
    private javax.swing.JComboBox<String> nvBox;
    private javax.swing.JButton reloadButton;
    private javax.swing.JButton saveDNH;
    private javax.swing.JTextField searchTxt;
    // End of variables declaration//GEN-END:variables
}
