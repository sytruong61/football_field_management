package Form;

import DataBase.ConnectDB;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

public class Customer extends javax.swing.JInternalFrame {
    ConnectDB cn = new ConnectDB();
    Connection conn;
    Locale locale = new Locale("vn", "VN");      
    NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);
    String CODE = "VIEW", maNV, maKH = null;
    
    public void SetHKBox(){
        hkBox.removeAllItems();
        String select_HK = "Select * from HangKhachHang";
        try{
            conn = cn.getConnection();
            PreparedStatement pst = conn.prepareStatement(select_HK);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                hkBox.addItem(rs.getString("TenHK"));
            }
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public String GetTenHKfromID(String maHK){
        String tenHK = null;
        String select_HK = "Select * from HangKhachHang where MAHK = ?";
        try{
            conn = cn.getConnection();
            PreparedStatement pst = conn.prepareStatement(select_HK);
            pst.setString(1, maHK);
            ResultSet rs = pst.executeQuery();
            if(rs.next()){
                tenHK = rs.getString("TenHK");
            }
        } catch(Exception e){
            e.printStackTrace();
        }
        return tenHK;
    }
    
    public void SelectAllKH(){
        try {
            String select_KH = "Select * from KhachHang";
            conn = cn.getConnection();
            PreparedStatement pst = conn.prepareStatement(select_KH);
            ResultSet kh = pst.executeQuery();
            DefaultTableModel model = (DefaultTableModel) khTable.getModel();
            String HoTen, GioiTinh, DiaChi, SOCMND = null, SDT, NgaySinh, Email, TichLuy = null, HANGKHACH, STATUS, GhiChu;
            while(kh.next()){
                HoTen = kh.getString("HoTen");
                GioiTinh = kh.getString("GioiTinh");
                DiaChi = kh.getString("DiaChi");
                SDT = kh.getString("SDT");
                NgaySinh = kh.getString("NgaySinh");
                Email = kh.getString("Email");
                if(kh.getString("SOCMND") != null ){
                    SOCMND = Crypto.AES.decrypt(kh.getString("SOCMND"));
                }
                if(kh.getString("TichLuy") != null){
                    TichLuy = currencyFormatter.format(Double.parseDouble(kh.getString("TichLuy")));
                }
                HANGKHACH = GetTenHKfromID(kh.getString("MAHK"));
                STATUS = kh.getString("TinhTrang");
                GhiChu = kh.getString("GhiChu");
                String[] khr = {HoTen, GioiTinh, DiaChi, SOCMND, SDT, NgaySinh, Email, TichLuy, HANGKHACH, STATUS, GhiChu};
                model.addRow(khr);
                SOCMND = null;
                TichLuy = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            e.printStackTrace();
        }
    }
    
    public final void Reset(){
        DefaultTableModel dm = (DefaultTableModel)khTable.getModel();
        dm.getDataVector().removeAllElements();
        dm.fireTableDataChanged(); 
        SelectAllKH();
        ColumnsAutoSizer.sizeColumnsToFit(khTable);
        tenKH.setText("");
        gtKH.setSelectedIndex(-1);
        bdKH.setDate(java.sql.Date.valueOf(java.time.LocalDate.now()));
        diachiKH.setText("");
        sdtKH.setText("");
        cmndKH.setText("");
        emailKH.setText("");
        statusKH.setSelectedIndex(-1);
        moneyLable.setText(currencyFormatter.format(Double.parseDouble("0")));
        hkBox.setSelectedIndex(-1);
        ghichuTxt.setText("");
        hkBox.setEnabled(false);
        sdtKH.setEditable(false);
    }
    
    public Customer() {
        initComponents();
        ImageIcon img = new ImageIcon("D:\\Football_Filed_Managerment\\src\\images\\icons/—Pngtree—football field plan_4710125 (1).png");
        this.setFrameIcon(img);
        SetHKBox();
        Reset();
        khTable.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent mouseEvent) {
                JTable table =(JTable) mouseEvent.getSource();
                java.awt.Point point = mouseEvent.getPoint();
                int row = table.rowAtPoint(point);
                if(row == -1){
                    return;
                }
                maKH = khTable.getModel().getValueAt(row, 4).toString();
                if (mouseEvent.getClickCount() == 1 && table.getSelectedRow() != -1) {
                    try {
                        conn = cn.getConnection();
                        String select_1KH = "Select * from KhachHang where SDT = ?";
                        PreparedStatement pst = conn.prepareStatement(select_1KH);
                        pst.setString(1, maKH);
                        ResultSet kh = pst.executeQuery();
                        if(kh.next()){
                            tenKH.setText(kh.getString("HoTen"));
                            gtKH.setSelectedItem(kh.getString("GioiTinh"));
                            diachiKH.setText(kh.getString("DiaChi"));
                            sdtKH.setText(kh.getString("SDT"));
                            emailKH.setText(kh.getString("Email"));
                            statusKH.setSelectedItem(kh.getString("TinhTrang"));
                            if(kh.getString("NgaySinh") != null && kh.getString("SoCMND") != null ){
                               bdKH.setDate(java.sql.Date.valueOf(kh.getString("NgaySinh")));
                               cmndKH.setText(Crypto.AES.decrypt(kh.getString("SoCMND")));
                            }
                            if(kh.getString("TichLuy") != null){
                                moneyLable.setText(currencyFormatter.format(Double.parseDouble(kh.getString("TichLuy"))));
                            }
                            hkBox.setSelectedItem(GetTenHKfromID(kh.getString("MAHK")));
                            ghichuTxt.setText(kh.getString("GhiChu"));
                        }
                        CODE = "UPDATE";
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

        jDialog1 = new javax.swing.JDialog();
        jLabel1 = new javax.swing.JLabel();
        tenKH = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        gtKH = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        diachiKH = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        cmndKH = new javax.swing.JTextField();
        emailKH = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        sdtKH = new javax.swing.JTextField();
        bdKH = new com.toedter.calendar.JDateChooser();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        moneyLable = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        hkBox = new javax.swing.JComboBox<>();
        jLabel11 = new javax.swing.JLabel();
        ghichuTxt = new javax.swing.JTextField();
        scroll = new javax.swing.JScrollPane();
        khTable = new javax.swing.JTable();
        saveKH = new javax.swing.JButton();
        closeButton = new javax.swing.JButton();
        searchTxt = new javax.swing.JTextField();
        statusKH = new javax.swing.JComboBox<>();

        javax.swing.GroupLayout jDialog1Layout = new javax.swing.GroupLayout(jDialog1.getContentPane());
        jDialog1.getContentPane().setLayout(jDialog1Layout);
        jDialog1Layout.setHorizontalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jDialog1Layout.setVerticalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        setTitle("Quản Lý Khách Hàng");

        jLabel1.setForeground(new java.awt.Color(41, 128, 185));
        jLabel1.setText("Họ Tên");

        tenKH.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(41, 128, 185), 1, true));
        tenKH.setPreferredSize(new java.awt.Dimension(0, 21));

        jLabel2.setForeground(new java.awt.Color(41, 128, 185));
        jLabel2.setText("Giới Tính");

        gtKH.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Nam", "Nữ", "Khác" }));
        gtKH.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(41, 128, 185), 1, true));
        gtKH.setPreferredSize(new java.awt.Dimension(0, 21));

        jLabel3.setForeground(new java.awt.Color(41, 128, 185));
        jLabel3.setText("Địa Chỉ");

        diachiKH.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(41, 128, 185), 1, true));
        diachiKH.setPreferredSize(new java.awt.Dimension(0, 21));

        jLabel4.setForeground(new java.awt.Color(41, 128, 185));
        jLabel4.setText("Số CCCD/CMND");

        cmndKH.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(41, 128, 185), 1, true));
        cmndKH.setPreferredSize(new java.awt.Dimension(0, 21));

        emailKH.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(41, 128, 185), 1, true));
        emailKH.setPreferredSize(new java.awt.Dimension(0, 21));

        jLabel5.setForeground(new java.awt.Color(41, 128, 185));
        jLabel5.setText("Email");

        jLabel6.setForeground(new java.awt.Color(41, 128, 185));
        jLabel6.setText("Số ĐT");

        sdtKH.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(41, 128, 185), 1, true));
        sdtKH.setPreferredSize(new java.awt.Dimension(0, 21));

        jLabel7.setForeground(new java.awt.Color(41, 128, 185));
        jLabel7.setText("Ngày Sinh");

        jLabel8.setForeground(new java.awt.Color(41, 128, 185));
        jLabel8.setText("Tình Trạng TK");

        jLabel9.setForeground(new java.awt.Color(41, 128, 185));
        jLabel9.setText("Tổng Tích Lũy ");

        moneyLable.setForeground(new java.awt.Color(41, 128, 185));
        moneyLable.setText("                                   ");
        moneyLable.setPreferredSize(new java.awt.Dimension(105, 21));

        jLabel10.setForeground(new java.awt.Color(41, 128, 185));
        jLabel10.setText("Hạng Khách");

        hkBox.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(41, 128, 185), 1, true));
        hkBox.setPreferredSize(new java.awt.Dimension(0, 21));

        jLabel11.setForeground(new java.awt.Color(41, 128, 185));
        jLabel11.setText("Ghi Chú");

        ghichuTxt.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(41, 128, 185), 1, true));
        ghichuTxt.setPreferredSize(new java.awt.Dimension(0, 21));

        khTable.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(41, 128, 185), 1, true));
        khTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Họ Tên", "Giới Tính", "Địa Chỉ", "Số CMND", "Số ĐT", "Ngày Sinh", "Email", "Tổng Tích Lũy", "Hạng Khách", "Tình Trạng TK", "Ghi Chú"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        scroll.setViewportView(khTable);

        saveKH.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/022-save.png"))); // NOI18N
        saveKH.setText("Lưu");
        saveKH.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                saveKHMouseClicked(evt);
            }
        });

        closeButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/058-error.png"))); // NOI18N
        closeButton.setText("Đóng");
        closeButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                closeButtonMouseClicked(evt);
            }
        });

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

        statusKH.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Còn Sử Dụng", "Đã Ngừng" }));
        statusKH.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(41, 128, 185), 1, true));
        statusKH.setPreferredSize(new java.awt.Dimension(0, 21));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jLabel1)
                                    .addGap(24, 24, 24)
                                    .addComponent(tenKH, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel6)
                                        .addComponent(jLabel7))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(sdtKH, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(bdKH, javax.swing.GroupLayout.DEFAULT_SIZE, 190, Short.MAX_VALUE)))
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jLabel8)
                                    .addGap(18, 18, 18)
                                    .addComponent(statusKH, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addComponent(searchTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel2))
                                .addGap(54, 54, 54)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(gtKH, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(diachiKH, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel11))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(ghichuTxt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(saveKH, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 14, Short.MAX_VALUE)
                                        .addComponent(closeButton))
                                    .addComponent(cmndKH, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addGap(54, 54, 54)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel10)
                            .addComponent(jLabel9)
                            .addComponent(jLabel5))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(hkBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(moneyLable, javax.swing.GroupLayout.DEFAULT_SIZE, 192, Short.MAX_VALUE)
                            .addComponent(emailKH, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(1, 1, 1))
                    .addComponent(scroll))
                .addGap(21, 21, 21))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(gtKH, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1)
                        .addComponent(tenKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel2)
                        .addComponent(jLabel5)
                        .addComponent(emailKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(diachiKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9)
                            .addComponent(moneyLable, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jLabel7))
                    .addComponent(bdKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel10)
                        .addComponent(hkBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel6)
                        .addComponent(sdtKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel4))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(cmndKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(statusKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11)
                    .addComponent(ghichuTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(searchTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(saveKH)
                    .addComponent(closeButton))
                .addGap(18, 18, 18)
                .addComponent(scroll, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(19, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void searchTxtMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_searchTxtMouseClicked
        searchTxt.setText("");
    }//GEN-LAST:event_searchTxtMouseClicked

    public void filter(String query){
        DefaultTableModel dm = (DefaultTableModel) khTable.getModel();
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<DefaultTableModel>(dm);
        khTable.setRowSorter(tr);
        tr.setRowFilter(RowFilter.regexFilter(query));
    }
    private void searchTxtKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchTxtKeyReleased
        String query = searchTxt.getText().trim();
        filter(query);
    }//GEN-LAST:event_searchTxtKeyReleased
    
    public void updateKH(String tenKH, String gtKH, String bdKH, String diachiKH, String cmndKH, String emailKH, String statusKH, String ghiChu, String maKH){
        try{
            String update = "UPDATE KhachHang SET HoTen = ?, GioiTinh = ?, NgaySinh = ?, DiaChi = ?, SOCMND = ?, Email = ?, TinhTrang = ?, GhiChu = ? WHERE SDT = ?";
            conn = cn.getConnection();
            PreparedStatement pst = conn.prepareStatement(update);
            pst.setString(1, tenKH);
            pst.setString(2, gtKH);
            pst.setString(3, bdKH);
            pst.setString(4, diachiKH);
            pst.setString(5, cmndKH);
            pst.setString(6, emailKH);
            pst.setString(7, statusKH);
            pst.setString(8, ghiChu);
            pst.setString(9, maKH);
            pst.execute();
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    
    private void saveKHMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_saveKHMouseClicked
        try {
            if(CODE.equals("VIEW")){
                JOptionPane.showMessageDialog(this, "Bạn đang ở chế độ xem, vui lòng chọn dòng để chỉnh sửa.");
                return;
            }   
            String tenKHv = tenKH.getText().trim();
            String gtKHv =  gtKH.getSelectedItem().toString().trim();
            Date bdKHv = bdKH.getDate();
            SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
            String ngaysinh = outputFormat.format(bdKHv);
            String diachiKHv = diachiKH.getText().trim();
            String cmndKHv = cmndKH.getText().trim();
            String cmndKHen = Crypto.AES.encrypt(cmndKHv);
            String emailKHv = emailKH.getText().trim();
            String statusKHv = statusKH.getSelectedItem().toString().trim();
            String ghiChu = ghichuTxt.getText().trim();
            String select_KH = "Select * from KhachHang where SDT != ?";
            conn = cn.getConnection();
            PreparedStatement pst = conn.prepareStatement(select_KH);
            pst.setString(1, maKH);
            ResultSet kh = pst.executeQuery();
            while(kh.next()){
                String Email = kh.getString("Email");
                if(Email != null && Email.equals(emailKHv)){
                    JOptionPane.showMessageDialog(this, "Email Đã Trùng.");
                    return; 
                }
                if(kh.getString("SOCMND") != null ){
                    String SOCMND = Crypto.AES.decrypt(kh.getString("SOCMND"));
                    if( SOCMND != null && SOCMND.equals(cmndKHv)){
                    JOptionPane.showMessageDialog(this, "Số CMND Đã Trùng.");
                    return; 
                }
                }
            }
            if(tenKHv.length() == 0 || gtKHv.length() == 0 || diachiKHv.length() == 0 || emailKHv.length() == 0 || statusKHv.length() == 0){
                JOptionPane.showMessageDialog(this, "Không được để trống thông tin.");
                return;                
            }
            if((cmndKHv.length() !=9 & cmndKHv.length() != 12) || Validate.notNumber(cmndKHv) == true){
                JOptionPane.showMessageDialog(this, "CMND / CCCD chỉ được chứa số và phải có độ dài là 9 hoặc 12 số.");
                return;
            }
            if(Validate.validate(emailKHv) == false){
                JOptionPane.showMessageDialog(this, "Địa chỉ Email phải đúng định dạng. ");
                return;
            }
            if(CODE.equals("UPDATE")){ 
                updateKH(tenKHv, gtKHv, ngaysinh, diachiKHv, cmndKHen, emailKHv, statusKHv, ghiChu, maKH);
                JOptionPane.showMessageDialog(this, "Đã cập nhật thông tin khách hàng thành công.");
                Reset();
                return;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }//GEN-LAST:event_saveKHMouseClicked

    private void closeButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_closeButtonMouseClicked
        this.dispose();
    }//GEN-LAST:event_closeButtonMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.toedter.calendar.JDateChooser bdKH;
    private javax.swing.JButton closeButton;
    private javax.swing.JTextField cmndKH;
    private javax.swing.JTextField diachiKH;
    private javax.swing.JTextField emailKH;
    private javax.swing.JTextField ghichuTxt;
    private javax.swing.JComboBox<String> gtKH;
    private javax.swing.JComboBox<String> hkBox;
    private javax.swing.JDialog jDialog1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JTable khTable;
    private javax.swing.JLabel moneyLable;
    private javax.swing.JButton saveKH;
    private javax.swing.JScrollPane scroll;
    private javax.swing.JTextField sdtKH;
    private javax.swing.JTextField searchTxt;
    private javax.swing.JComboBox<String> statusKH;
    private javax.swing.JTextField tenKH;
    // End of variables declaration//GEN-END:variables
}
