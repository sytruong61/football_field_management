package Form;

import DataBase.ConnectDB;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.Locale;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

public class Maintenance extends javax.swing.JInternalFrame {
    ConnectDB cn = new ConnectDB();
    Connection conn;
    Locale locale = new Locale("vn", "VN");      
    NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);
    String CODE = "INSERT", maBT, maFBF, maNV;
    
    public String GetSBfromID(String maSB){
        String tenSB = null;
        String select_SBfromID = "Select * from SanBong where MaSan = ?";
        try{
            conn = cn.getConnection();
            PreparedStatement pst = conn.prepareStatement(select_SBfromID);
            pst.setString(1, maSB);
            ResultSet rs = pst.executeQuery();
            if(rs.next()){
                tenSB = rs.getString("TenSan");
            }
        } catch(Exception e){
            e.printStackTrace();
        }
        return tenSB;
    }
    
    public String GetIDSBfromTen(String tenSB){
        String ID = null;
        String select_IDSBfromTen = "Select * from SanBong where TenSan = ?";
        try{
            conn = cn.getConnection();
            PreparedStatement pst = conn.prepareStatement(select_IDSBfromTen);
            pst.setString(1, tenSB);
            ResultSet rs = pst.executeQuery();
            if(rs.next()){
                ID = rs.getString("MaSan");
            }
        } catch(Exception e){
            System.out.println(e.toString() + " 105 ");
        }
        return ID;
    }
    
    public final void SetFBFBox(){
        fbfBox.removeAllItems();
        try {
            String select_FBF = "Select * from SanBong";
            conn = cn.getConnection();
            PreparedStatement pst = conn.prepareStatement(select_FBF);
            ResultSet fbf = pst.executeQuery();
            while(fbf.next()){
                fbfBox.addItem(fbf.getString("TenSan"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void SelectAllBT(){
        try {
            String select_BT = "Select * from BaoTri";
            conn = cn.getConnection();
            PreparedStatement pst = conn.prepareStatement(select_BT);
            ResultSet bt = pst.executeQuery();
            DefaultTableModel model = (DefaultTableModel) btTable.getModel();
            String MaBaoTri, FBF, NDBT, DonGia, NGAYBD, NGAYKT, STATUS, GhiChu;
            while(bt.next()){
                MaBaoTri = bt.getString("MaBaoTri");
                FBF = GetSBfromID(bt.getString("MaSan"));
                NDBT = bt.getString("NoiDungBaoTri");
                DonGia = currencyFormatter.format(Double.parseDouble(bt.getString("ChiPhiBaoTri")));
                NGAYBD = bt.getString("NgayBatDau");
                if(bt.getString("NgayKetThuc") != null){
                    NGAYKT = bt.getString("NgayKetThuc");
                }else{
                    NGAYKT = null;
                }
                STATUS = bt.getString("TinhTrang");
                GhiChu = bt.getString("GhiChu");
                String[] btr = {MaBaoTri, FBF, NDBT, DonGia, NGAYBD, NGAYKT, STATUS, GhiChu};
                model.addRow(btr);               
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public final void Reset(){
        DefaultTableModel dm = (DefaultTableModel)btTable.getModel();
        dm.getDataVector().removeAllElements();
        dm.fireTableDataChanged(); 
        SelectAllBT();
        ColumnsAutoSizer.sizeColumnsToFit(btTable);
        SetFBFBox();
        fbfBox.setSelectedIndex(-1);
        btND.setText("");
        btPrice.setText("");
        ngayBD.setDate(java.sql.Date.valueOf(java.time.LocalDate.now()));
        ngayKT.setDate(java.sql.Date.valueOf(java.time.LocalDate.now()));
        statusBox.setSelectedIndex(0);
        ghichuTxt.setText("");
        moneyLable.setText("");
        statusBox.setEnabled(false);
    }
    
    public Maintenance() {
        initComponents();
        ImageIcon img = new ImageIcon("D:\\Football_Filed_Managerment\\src\\images\\icons/—Pngtree—football field plan_4710125 (1).png");
        this.setFrameIcon(img);
        Reset();
        if(CODE.equals("Insert")){
            statusBox.setEnabled(false);
        }
        btTable.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent mouseEvent) {
                JTable table =(JTable) mouseEvent.getSource();
                java.awt.Point point = mouseEvent.getPoint();
                int row = table.rowAtPoint(point);
                if(row == -1){
                    return;
                }
                maBT = btTable.getModel().getValueAt(row, 0).toString();
                maFBF = btTable.getModel().getValueAt(row, 1).toString();
                if (mouseEvent.getClickCount() == 1 && table.getSelectedRow() != -1) {
                    try {
                        conn = cn.getConnection();
                        String select_1BT = "Select * from BaoTri where MaBaoTri = ?";
                        PreparedStatement pst = conn.prepareStatement(select_1BT);
                        pst.setString(1, maBT);
                        ResultSet bt = pst.executeQuery();
                        if(bt.next()){
                            fbfBox.setSelectedItem(GetSBfromID(bt.getString("MaSan")));
                            btND.setText(bt.getString("NoiDungBaoTri"));
                            btPrice.setText(bt.getString("ChiPhiBaoTri"));
                            moneyLable.setText(currencyFormatter.format(Double.parseDouble(btPrice.getText().trim())));
                            ngayBD.setDate(java.sql.Date.valueOf(bt.getString("NgayBatDau")));
                            if(bt.getString("NgayKetThuc") != null){
                                ngayKT.setDate(java.sql.Date.valueOf(bt.getString("NgayKetThuc")));
                            }
                            statusBox.setSelectedItem(bt.getString("TinhTrang"));
                            ghichuTxt.setText(bt.getString("GhiChu"));
                        }
                        CODE = "UPDATE";
                        statusBox.setEnabled(true);
                        fbfBox.setEnabled(false);
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                }  
            }
        });
    }
    
    public void filter(String query){
        DefaultTableModel dm = (DefaultTableModel) btTable.getModel();
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<DefaultTableModel>(dm);
        btTable.setRowSorter(tr);
        tr.setRowFilter(RowFilter.regexFilter(query));
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel3 = new javax.swing.JLabel();
        btPrice = new javax.swing.JTextField();
        moneyLable = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        fbfBox = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        btND = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        ghichuTxt = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        ngayBD = new com.toedter.calendar.JDateChooser();
        jLabel8 = new javax.swing.JLabel();
        ngayKT = new com.toedter.calendar.JDateChooser();
        statusBox = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        searchTxt = new javax.swing.JTextField();
        addPC = new javax.swing.JButton();
        savePC = new javax.swing.JButton();
        closeButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        btTable = new javax.swing.JTable();

        setTitle("Bảo Trì Sân Bóng");

        jLabel3.setForeground(new java.awt.Color(41, 128, 185));
        jLabel3.setText("Chi Phí");

        btPrice.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(41, 128, 185)));
        btPrice.setPreferredSize(new java.awt.Dimension(0, 21));
        btPrice.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                btPriceKeyReleased(evt);
            }
        });

        moneyLable.setText("                      ");
        moneyLable.setToolTipText("");

        jLabel4.setBackground(new java.awt.Color(255, 255, 255));
        jLabel4.setForeground(new java.awt.Color(41, 128, 185));
        jLabel4.setText("Sân Bóng");

        fbfBox.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(41, 128, 185)));
        fbfBox.setPreferredSize(new java.awt.Dimension(0, 21));

        jLabel5.setForeground(new java.awt.Color(41, 128, 185));
        jLabel5.setText("Nội Dung Bảo Trì");
        jLabel5.setToolTipText("");

        btND.setPreferredSize(new java.awt.Dimension(0, 21));

        jLabel6.setForeground(new java.awt.Color(41, 128, 185));
        jLabel6.setText("Ghi Chú");
        jLabel6.setToolTipText("");

        ghichuTxt.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(41, 128, 185)));
        ghichuTxt.setPreferredSize(new java.awt.Dimension(0, 21));

        jLabel7.setForeground(new java.awt.Color(41, 128, 185));
        jLabel7.setText("Ngày Bắt Đầu");

        ngayBD.setPreferredSize(new java.awt.Dimension(74, 21));

        jLabel8.setForeground(new java.awt.Color(41, 128, 185));
        jLabel8.setText("Ngày Kết Thúc");

        ngayKT.setPreferredSize(new java.awt.Dimension(74, 21));

        statusBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Đang Chờ", "Đang Thực Hiện", "Đã Hoàn Thành" }));
        statusBox.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(41, 128, 185)));
        statusBox.setPreferredSize(new java.awt.Dimension(0, 21));

        jLabel9.setBackground(new java.awt.Color(255, 255, 255));
        jLabel9.setForeground(new java.awt.Color(41, 128, 185));
        jLabel9.setText("Tình Trạng");

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

        addPC.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/056-plus.png"))); // NOI18N
        addPC.setText("Thêm");
        addPC.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(41, 128, 185)));
        addPC.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                addPCMouseClicked(evt);
            }
        });

        savePC.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/022-save.png"))); // NOI18N
        savePC.setText("Lưu");
        savePC.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(41, 128, 185)));
        savePC.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                savePCMouseClicked(evt);
            }
        });

        closeButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/058-error.png"))); // NOI18N
        closeButton.setText("Đóng");
        closeButton.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(41, 128, 185)));
        closeButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                closeButtonMouseClicked(evt);
            }
        });

        btTable.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(41, 128, 185), 1, true));
        btTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã Bảo Trì", "Sân Bóng", "Nội Dung Bảo Trì", "Đơn Giá", "Ngày Bắt Đầu", "Ngày Kết Thúc", "Tình Trạng", "Ghi Chú"
            }
        ));
        jScrollPane1.setViewportView(btTable);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(searchTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(36, 36, 36)
                                .addComponent(addPC, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(68, 68, 68)
                                .addComponent(savePC, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addGap(19, 19, 19))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(closeButton, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addGap(0, 556, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(btPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(26, 26, 26)
                                        .addComponent(moneyLable)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(ngayBD, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(statusBox, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(27, 27, 27)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel8)
                                            .addComponent(jLabel6))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(ngayKT, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(ghichuTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(85, 85, 85)
                                        .addComponent(fbfBox, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel5)
                                .addGap(18, 18, 18)
                                .addComponent(btND, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(210, 210, 210))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(21, 21, 21)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3)
                            .addComponent(moneyLable)))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btND, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel5)
                        .addComponent(fbfBox, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(ngayBD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ngayKT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(statusBox, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel9)
                        .addComponent(jLabel6))
                    .addComponent(ghichuTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(searchTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(addPC, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(savePC, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(closeButton, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(17, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btPriceKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btPriceKeyReleased
        String DonGia = null;
        if(Validate.notNumber(btPrice.getText()) == true){
            JOptionPane.showMessageDialog(this, "Đơn giá phải là số");
            return;
        }
        if(btPrice.getText().length() <= 0){
            moneyLable.setText("");
            return;
        }
        DonGia = currencyFormatter.format(Double.parseDouble(btPrice.getText()));
        moneyLable.setText(String.valueOf(DonGia));
    }//GEN-LAST:event_btPriceKeyReleased

    private void searchTxtMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_searchTxtMouseClicked
        searchTxt.setText("");
    }//GEN-LAST:event_searchTxtMouseClicked

    private void searchTxtKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchTxtKeyReleased
        String query = searchTxt.getText().trim();
        filter(query);
    }//GEN-LAST:event_searchTxtKeyReleased

    private void addPCMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addPCMouseClicked
        Reset();
        CODE = "INSERT";
        fbfBox.setEnabled(true);
    }//GEN-LAST:event_addPCMouseClicked

    public void insertBT(String maSAN, String ndBT, String cpBT, String ngayBD, String ngayKT, String status, String ghiChu){
        try{
            String insert = "INSERT INTO BaoTri(MaSan, NoiDungBaoTri, ChiPhiBaoTri, NgayBatDau, NgayKetThuc, TinhTrang, GhiChu) VALUES(?,?,?,?,?,?,?)";
            conn = cn.getConnection();
            PreparedStatement pst = conn.prepareStatement(insert);
            pst.setString(1, maSAN);
            pst.setString(2, ndBT);
            pst.setString(3, cpBT);
            pst.setString(4, ngayBD);
            pst.setString(5, ngayKT);
            pst.setString(6, status);
            pst.setString(7, ghiChu);
            pst.execute();
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void updateBT(String ndBT, String cpBT, String ngayBD, String ngayKT, String status, String ghiChu, String maBT){
        try{
            String update = "UPDATE BaoTri SET NoiDungBaoTri = ?, ChiPhiBaoTri = ?, NgayBatDau = ?, NgayKetThuc = ?, TinhTrang = ?, GhiChu = ? WHERE MaBaoTri = ?";
            conn = cn.getConnection();
            PreparedStatement pst = conn.prepareStatement(update);
            pst.setString(1, ndBT);
            pst.setString(2, cpBT);
            pst.setString(3, ngayBD);
            pst.setString(4, ngayKT);
            pst.setString(5, status);
            pst.setString(6, ghiChu);
            pst.setString(7, maBT);
            pst.execute();
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void updateFBF(String status, String maSan){
        try{
            String update = "UPDATE SanBong SET TinhTrang = ? WHERE MaSan = ?";
            conn = cn.getConnection();
            PreparedStatement pst = conn.prepareStatement(update);
            pst.setString(1, status);
            pst.setString(2, maSan);
            pst.execute();
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    
    private void savePCMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_savePCMouseClicked
        maFBF = GetIDSBfromTen(fbfBox.getSelectedItem().toString().trim());
        String ndBT = btND.getText().toString().trim();
        String cpBT = btPrice.getText().toString().trim();
        java.sql.Date ngayBDv = new java.sql.Date(ngayBD.getDate().getTime());
        java.sql.Date ngayKTv = new java.sql.Date(ngayKT.getDate().getTime());
        String ghichu = ghichuTxt.getText().toString().trim();
        String tenFBF = null, status = null;
        if(fbfBox.getSelectedIndex() != -1 && statusBox.getSelectedIndex() != -1){
            tenFBF = fbfBox.getSelectedItem().toString().trim();
            status = statusBox.getSelectedItem().toString().trim();
        }
        StringBuffer sb = new StringBuffer();        
        if (ndBT.length() == 0 || cpBT.length() == 0 || tenFBF.length() == 0 || status.length() == 0){
            sb.append("Không được để trống thông tin");
        }
        if(ngayBDv.after(ngayKTv)){
            sb.append("Ngày Kết Thúc Phải Sau Ngày Bắt Đầu");
        }
        if(sb.length()>0){
            JOptionPane.showMessageDialog(this, sb.toString());
            return;
        }
        String sql = "SELECT * FROM PhieuDatSan WHERE MaSan = ? AND CAST(GioBD AS DATE) >= ? AND CAST(GioBD AS DATE) <= ? AND (TrangThai = ? OR TrangThai = ?)";
        try {
            conn = cn.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, maFBF);
            stmt.setDate(2, ngayBDv);
            stmt.setDate(3, ngayKTv);
            stmt.setString(4, "Đang Đặt");
            stmt.setString(5, "Đã Nhận Sân");
            ResultSet rs = stmt.executeQuery();
            if(rs.next()) {
                JOptionPane.showMessageDialog(this, "Trong khoảng thời gian bảo trì có lịch đặt, hãy bảo trì vào buổi tối hoặc vào lúc lịch của sân đã được hoàn thiện");
                return;
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch(Exception e){
            e.printStackTrace();
        }
        if( CODE.equals("INSERT") == true){
            insertBT(maFBF, ndBT, cpBT, String.valueOf(ngayBDv), String.valueOf(ngayKTv), status, ghichu);
            JOptionPane.showMessageDialog(this, "Đã Thêm Lịch Bảo Trì Thành Công");
            Reset();
        }
        if( CODE.equals("UPDATE") == true){            
            if(status.equals("Đang Chờ")){
                updateFBF("Đang Trống", maFBF);
                updateBT(ndBT, cpBT, String.valueOf(ngayBDv), String.valueOf(ngayKTv), status, ghichu, maBT);
            }
            if(status.equals("Đang Thực Hiện")){
                updateFBF("Đang Bảo Trì", maFBF);
                updateBT(ndBT, cpBT, String.valueOf(ngayBDv), String.valueOf(ngayKTv), status, ghichu, maBT);
            }
            if(status.equals("Đã Hoàn Thành")){
                updateFBF("Đang Trống", maFBF);
                updateBT(ndBT, cpBT, String.valueOf(ngayBDv), String.valueOf(ngayKTv), status, ghichu, maBT);
            }
            JOptionPane.showMessageDialog(this, "Chỉnh Sửa Thông Tin Bảo Trì Thành Công");
            Reset();
        }
    }//GEN-LAST:event_savePCMouseClicked

    private void closeButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_closeButtonMouseClicked
        this.dispose();
    }//GEN-LAST:event_closeButtonMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addPC;
    private javax.swing.JTextField btND;
    private javax.swing.JTextField btPrice;
    private javax.swing.JTable btTable;
    private javax.swing.JButton closeButton;
    private javax.swing.JComboBox<String> fbfBox;
    private javax.swing.JTextField ghichuTxt;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel moneyLable;
    private com.toedter.calendar.JDateChooser ngayBD;
    private com.toedter.calendar.JDateChooser ngayKT;
    private javax.swing.JButton savePC;
    private javax.swing.JTextField searchTxt;
    private javax.swing.JComboBox<String> statusBox;
    // End of variables declaration//GEN-END:variables
}
