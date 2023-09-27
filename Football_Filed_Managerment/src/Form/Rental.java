package Form;

import DataBase.ConnectDB;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.NumberFormat;
import java.util.Locale;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

public class Rental extends javax.swing.JInternalFrame {
    ConnectDB cn = new ConnectDB();
    Connection conn;
    Locale locale = new Locale("vn", "VN");      
    NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);
    String CODE = "INSERT", maRT, maNV, maTSTB;
    
    public void SelectAllRT(){
        try {
            String select_RT = "Select * from DichVu where LoaiDV = ?";
            conn = cn.getConnection();
            PreparedStatement pst = conn.prepareStatement(select_RT);
            pst.setString(1, "Cho Thuê");
            ResultSet rt = pst.executeQuery();
            DefaultTableModel model = (DefaultTableModel) rtTable.getModel();
            String MART, TENRT, DonGia, TienCoc = " ", DVT, GhiChu;
            while(rt.next()){
                MART = rt.getString("MaDV");
                TENRT = rt.getString("TenDichVu");
                DonGia = currencyFormatter.format(Double.parseDouble(rt.getString("DonGia")));
                if(rt.getString("TienCoc") != null){
                    TienCoc = currencyFormatter.format(Double.parseDouble(rt.getString("TienCoc")));
                }
                DVT = rt.getString("DVT");
                GhiChu = rt.getString("GhiChu");
                String[] rtr = {MART, TENRT, DonGia, TienCoc, DVT, GhiChu};
                model.addRow(rtr);
                TienCoc = " ";
            }
        } catch (Exception e) {
            e.printStackTrace();
            e.printStackTrace();
        }
    }
    
    public String GetTSTBfromID(String maTSTB){
        String tenTSTB = null;
        String select_TSTBfromID = "Select * from TaiSanThietBi where MaTSTB = ?";
        try{
            conn = cn.getConnection();
            PreparedStatement pst = conn.prepareStatement(select_TSTBfromID);
            pst.setString(1, maTSTB);
            ResultSet rs = pst.executeQuery();
            if(rs.next()){
                tenTSTB = rs.getString("TenTSTB");
            }
        } catch(Exception e){
            e.printStackTrace();
        }
        return tenTSTB;
    }
    
    public String GetIDTSTBfromTen(String tenTSTB){
        String ID = null;
        String select_IDTSTBfromTen = "Select * from TaiSanThietBi where TenTSTB = ?";
        try{
            conn = cn.getConnection();
            PreparedStatement pst = conn.prepareStatement(select_IDTSTBfromTen);
            pst.setString(1, tenTSTB);
            ResultSet rs = pst.executeQuery();
            if(rs.next()){
                ID = rs.getString("MaTSTB");
            }
        } catch(Exception e){
            System.out.println(e.toString() + " 105 ");
        }
        return ID;
    }
    
    public final void SetTSTBBox(){
        tstbBox.removeAllItems();
        try {
            String select_TSTB = "Select * from TaiSanThietBi where LoaiTSTB = ?";
            conn = cn.getConnection();
            PreparedStatement pst = conn.prepareStatement(select_TSTB);
            pst.setString(1, "Cho Thuê");
            ResultSet tstb = pst.executeQuery();
            while(tstb.next()){
                tstbBox.addItem(tstb.getString("TenTSTB"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public final void Reset(){
        DefaultTableModel dm = (DefaultTableModel)rtTable.getModel();
        dm.getDataVector().removeAllElements();
        dm.fireTableDataChanged(); 
        SelectAllRT();
        ColumnsAutoSizer.sizeColumnsToFit(rtTable);
        SetTSTBBox();
        tstbBox.setSelectedIndex(-1);
        rtName.setText("");
        rtDVT.setText("");
        rtPrice.setText("");
        ghichuTxt.setText("");
        moneyLable.setText("");
        tienCocTxt.setText(" ");
        tienCocLabel.setText(" ");
    }
    
    public Rental() {
        initComponents();
        ImageIcon img = new ImageIcon("D:\\Football_Filed_Managerment\\src\\images\\icons/—Pngtree—football field plan_4710125 (1).png");
        this.setFrameIcon(img);
        Reset();
        rtTable.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent mouseEvent) {
                JTable table =(JTable) mouseEvent.getSource();
                java.awt.Point point = mouseEvent.getPoint();
                int row = table.rowAtPoint(point);
                if(row == -1){
                    return;
                }
                maRT = rtTable.getModel().getValueAt(row, 0).toString();
                if (mouseEvent.getClickCount() == 1 && table.getSelectedRow() != -1) {
                    try {
                        conn = cn.getConnection();
                        String select_1RT = "Select * from DichVu where MaDV = ?";
                        PreparedStatement pst = conn.prepareStatement(select_1RT);
                        pst.setString(1, maRT);
                        ResultSet rt = pst.executeQuery();
                        if(rt.next()){
                            tstbBox.setSelectedItem(GetTSTBfromID(rt.getString("MaTSTB")));
                            rtName.setText(rt.getString("TenDichVu"));
                            rtDVT.setText(rt.getString("DVT"));
                            rtPrice.setText(rt.getString("DonGia"));
                            moneyLable.setText(currencyFormatter.format(Double.parseDouble(rtPrice.getText())));
                            if(rt.getString("TienCoc") != null){
                                tienCocTxt.setText(rt.getString("TienCoc"));
                                tienCocLabel.setText(currencyFormatter.format(Double.parseDouble(tienCocTxt.getText())));
                            }else{
                                tienCocTxt.setText(" ");
                                tienCocLabel.setText(" ");
                            }
                            ghichuTxt.setText(rt.getString("GhiChu"));
                            maTSTB = rt.getString("MaTSTB");
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

        rtDVT = new javax.swing.JTextField();
        searchTxt = new javax.swing.JTextField();
        rtPrice = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        addRT = new javax.swing.JButton();
        ghichuTxt = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        saveRT = new javax.swing.JButton();
        rtName = new javax.swing.JTextField();
        closeButton = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        moneyLable = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        rtTable = new javax.swing.JTable();
        tstbBox = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        tienCocTxt = new javax.swing.JTextField();
        tienCocLabel = new javax.swing.JLabel();

        setTitle("Quản Lý Cho Thuê");

        rtDVT.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(41, 128, 185), 1, true));
        rtDVT.setPreferredSize(new java.awt.Dimension(0, 21));

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

        rtPrice.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(41, 128, 185), 1, true));
        rtPrice.setPreferredSize(new java.awt.Dimension(0, 21));
        rtPrice.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                rtPriceKeyReleased(evt);
            }
        });

        jLabel1.setForeground(new java.awt.Color(41, 128, 185));
        jLabel1.setText("Tên Dịch Vụ Cho Thuê");

        addRT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/056-plus.png"))); // NOI18N
        addRT.setText("Thêm");
        addRT.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                addRTMouseClicked(evt);
            }
        });

        ghichuTxt.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(41, 128, 185), 1, true));
        ghichuTxt.setPreferredSize(new java.awt.Dimension(0, 21));

        jLabel4.setForeground(new java.awt.Color(41, 128, 185));
        jLabel4.setText("Đơn Vị Tính");
        jLabel4.setToolTipText("");

        jLabel3.setForeground(new java.awt.Color(41, 128, 185));
        jLabel3.setText("Đơn Giá");

        saveRT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/022-save.png"))); // NOI18N
        saveRT.setText("Lưu");
        saveRT.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                saveRTMouseClicked(evt);
            }
        });

        rtName.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(41, 128, 185), 1, true));
        rtName.setPreferredSize(new java.awt.Dimension(0, 21));

        closeButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/058-error.png"))); // NOI18N
        closeButton.setText("Đóng");
        closeButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                closeButtonMouseClicked(evt);
            }
        });

        jLabel2.setForeground(new java.awt.Color(41, 128, 185));
        jLabel2.setText("Tên Tài Sản Thiết Bị");

        jLabel5.setForeground(new java.awt.Color(41, 128, 185));
        jLabel5.setText("Ghi Chú");
        jLabel5.setToolTipText("");

        moneyLable.setText("                      ");
        moneyLable.setToolTipText("");

        rtTable.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(41, 128, 185), 1, true));
        rtTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã Dịch Vụ Cho Thuê", "Tên Dịch Vụ Cho Thuê", "Đơn Giá", "Tiền Cọc", "Đơn Vị Tính ", "Ghi Chú"
            }
        ));
        jScrollPane1.setViewportView(rtTable);

        tstbBox.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(41, 128, 185), 1, true));
        tstbBox.setPreferredSize(new java.awt.Dimension(0, 21));

        jLabel6.setForeground(new java.awt.Color(41, 128, 185));
        jLabel6.setText("Tiền Cọc");

        tienCocTxt.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(41, 128, 185), 1, true));
        tienCocTxt.setPreferredSize(new java.awt.Dimension(3, 21));
        tienCocTxt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tienCocTxtKeyReleased(evt);
            }
        });

        tienCocLabel.setText("                      ");
        tienCocLabel.setToolTipText("");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1)
                        .addGap(22, 22, 22))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel1)
                            .addComponent(jLabel3)
                            .addComponent(jLabel5))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(ghichuTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(tstbBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(rtName, javax.swing.GroupLayout.DEFAULT_SIZE, 190, Short.MAX_VALUE))
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel4))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(moneyLable, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(rtPrice, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 190, Short.MAX_VALUE))
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel6)))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(rtDVT, javax.swing.GroupLayout.DEFAULT_SIZE, 190, Short.MAX_VALUE)
                                    .addComponent(tienCocTxt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(tienCocLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(searchTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(addRT)
                        .addGap(18, 18, 18)
                        .addComponent(saveRT)
                        .addGap(18, 18, 18)
                        .addComponent(closeButton)
                        .addGap(0, 118, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(tstbBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(rtName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(rtDVT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rtPrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel6)
                    .addComponent(tienCocTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(moneyLable)
                    .addComponent(tienCocLabel))
                .addGap(7, 7, 7)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ghichuTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(searchTxt, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(addRT)
                        .addComponent(saveRT)
                        .addComponent(closeButton)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void searchTxtMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_searchTxtMouseClicked
        searchTxt.setText("");
    }//GEN-LAST:event_searchTxtMouseClicked

    public void filter(String query){
        DefaultTableModel dm = (DefaultTableModel) rtTable.getModel();
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<DefaultTableModel>(dm);
        rtTable.setRowSorter(tr);
        tr.setRowFilter(RowFilter.regexFilter(query));
    }
    
    private void searchTxtKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchTxtKeyReleased
        String query = searchTxt.getText().trim();
        filter(query);
    }//GEN-LAST:event_searchTxtKeyReleased

    private void rtPriceKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_rtPriceKeyReleased
        String DonGia = null;
        if(Validate.notNumber(rtPrice.getText()) == true){
            JOptionPane.showMessageDialog(this, "Đơn giá phải là số");
            return;
        }
        if(rtPrice.getText().length() <= 0){
            moneyLable.setText("");
            return;
        }
        DonGia = currencyFormatter.format(Double.parseDouble(rtPrice.getText()));
        moneyLable.setText(String.valueOf(DonGia));
    }//GEN-LAST:event_rtPriceKeyReleased

    private void addRTMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addRTMouseClicked
        Reset();
        CODE = "INSERT";
    }//GEN-LAST:event_addRTMouseClicked

    public void insertRT(String tenRT, String dongia, String dvt, String tienCoc, String ghichu, String loaiDV, String maTSTB){
        try{
            String insert = "INSERT INTO DichVu(TenDichVu, DonGia, DVT, TienCoc, GhiChu, LoaiDV, MaTSTB) VALUES(?,?,?,?,?,?,?)";
            conn = cn.getConnection();
            PreparedStatement pst = conn.prepareStatement(insert);
            pst.setString(1, tenRT);
            pst.setString(2, dongia);
            pst.setString(3, dvt);
            pst.setString(4, tienCoc);
            pst.setString(5, ghichu);
            pst.setString(6, loaiDV);
            pst.setString(7, maTSTB);
            pst.execute();
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void updateRT(String tenRT, String dongia, String dvt, String tienCoc, String ghichu, String maRT){
        try{
            String update = "UPDATE DichVu SET TenDichVu = ?, DonGia = ?, DVT = ?, TienCoc = ?, GhiChu = ? WHERE MaDV = ?";
            conn = cn.getConnection();
            PreparedStatement pst = conn.prepareStatement(update);
            pst.setString(1, tenRT);
            pst.setString(2, dongia);
            pst.setString(3, dvt);
            pst.setString(4, tienCoc);
            pst.setString(5, ghichu);
            pst.setString(6, maRT);
            pst.execute();
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    
    private void saveRTMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_saveRTMouseClicked
        String tenRT = rtName.getText().toString().trim();
        String dongia = rtPrice.getText().toString().trim();
        String dvt = rtDVT.getText().toString().trim();
        String tienCoc = tienCocTxt.getText().toString().trim();
        String ghichu = ghichuTxt.getText().toString().trim();
        maTSTB = GetIDTSTBfromTen(tstbBox.getSelectedItem().toString().trim());
        String loaiDV = "Cho Thuê";
        if(tenRT.length() == 0 || dongia.length() == 0 || dvt.length() == 0 || tienCoc.length() == 0 || maTSTB.length() == 0 ){
            JOptionPane.showMessageDialog(this, "Không được để trống thông tin");
            return;
        }
        StringBuffer sb = new StringBuffer();
        if(sb.length()>0){
            JOptionPane.showMessageDialog(this, sb.toString());
            return;
        }
        if( CODE.equals("INSERT") == true){
            String select_DV = "Select * from DichVu";
            try{
                conn = cn.getConnection();
                PreparedStatement pst = conn.prepareStatement(select_DV);
                ResultSet dv = pst.executeQuery();
                while(dv.next()){
                    int count = 0;
                    if(dv.getString("TenDichVu").trim().equals(tenRT) == true){
                        count++;
                    }
                    if(dv.getString("MaTSTB") != null){
                        if(dv.getString("MaTSTB").trim().equals(maTSTB) == true){
                            count++;
                        }
                    }
                    if(count > 0){
                        sb.append("Dịch vụ cho thuê này có thể đã tồn tại");
                    }
                }
            } catch(Exception e){
                e.printStackTrace();
            }
            if(sb.length()>0){
                JOptionPane.showMessageDialog(this, sb.toString());
                return;
            }
            insertRT(tenRT, dongia, dvt, tienCoc, ghichu, loaiDV, maTSTB);
            JOptionPane.showMessageDialog(this, "Đã Thêm Dịch Vụ Cho Thuê Thành Công");
            Reset();
        }
        if( CODE.equals("UPDATE") == true){
            updateRT(tenRT, dongia, dvt, tienCoc, ghichu, maRT);
            JOptionPane.showMessageDialog(this, "Chỉnh Sửa Thông Tin Dịch Vụ Cho Thuê Thành Công");
            Reset();
        }
    }//GEN-LAST:event_saveRTMouseClicked

    private void closeButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_closeButtonMouseClicked
        this.dispose();
    }//GEN-LAST:event_closeButtonMouseClicked

    private void tienCocTxtKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tienCocTxtKeyReleased
        String TienCoc = null;
        if(Validate.notNumber(tienCocTxt.getText()) == true){
            JOptionPane.showMessageDialog(this, "Tiền Cọc phải là số");
            return;
        }
        if(tienCocTxt.getText().length() <= 0){
            tienCocLabel.setText("");
            return;
        }
        TienCoc = currencyFormatter.format(Double.parseDouble(tienCocTxt.getText()));
        tienCocLabel.setText(String.valueOf(TienCoc));
    }//GEN-LAST:event_tienCocTxtKeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addRT;
    private javax.swing.JButton closeButton;
    private javax.swing.JTextField ghichuTxt;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel moneyLable;
    private javax.swing.JTextField rtDVT;
    private javax.swing.JTextField rtName;
    private javax.swing.JTextField rtPrice;
    private javax.swing.JTable rtTable;
    private javax.swing.JButton saveRT;
    private javax.swing.JTextField searchTxt;
    private javax.swing.JLabel tienCocLabel;
    private javax.swing.JTextField tienCocTxt;
    private javax.swing.JComboBox<String> tstbBox;
    // End of variables declaration//GEN-END:variables
}
