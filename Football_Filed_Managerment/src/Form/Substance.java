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

public class Substance extends javax.swing.JInternalFrame {
    ConnectDB cn = new ConnectDB();
    Connection conn;
    Locale locale = new Locale("vn", "VN");      
    NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);
    String CODE = "INSERT", maFB, maNV, maTSTB;
    
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
    
    public void SelectAllFB(){
        try {
            String select_FB = "Select * from DichVu where LoaiDV = ?";
            conn = cn.getConnection();
            PreparedStatement pst = conn.prepareStatement(select_FB);
            pst.setString(1, "F&B");
            ResultSet fb = pst.executeQuery();
            DefaultTableModel model = (DefaultTableModel) fbTable.getModel();
            String MAFB, TENFB, DonGia, DVT, GhiChu;
            while(fb.next()){
                MAFB = fb.getString("MaDV");
                TENFB = fb.getString("TenDichVu");
                DonGia = currencyFormatter.format(Double.parseDouble(fb.getString("DonGia")));
                DVT = fb.getString("DVT");
                GhiChu = fb.getString("GhiChu");
                String[] fbr = {MAFB, TENFB, DonGia, DVT, GhiChu};
                model.addRow(fbr);               
            }
        } catch (Exception e) {
            e.printStackTrace();
            e.printStackTrace();
        }
    }
    
    public String GetTenTSTBfromID(String maTSTB){
        String tenTSTB = null;
        try {
            conn = cn.getConnection();
            String select_TSTB = "Select * from TaiSanThietBi where MaTSTB = ?";
            PreparedStatement pst = conn.prepareStatement(select_TSTB);
            pst.setString(1, maTSTB);
            ResultSet tstb = pst.executeQuery();
            if(tstb.next()){
                tenTSTB = tstb.getString("TenTSTB");            
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tenTSTB;
    }
    
    public final void SetTSTBBox(){
        tstbBox.removeAllItems();
        try {
            String select_TSTB = "Select * from TaiSanThietBi where LoaiTSTB = ?";
            conn = cn.getConnection();
            PreparedStatement pst = conn.prepareStatement(select_TSTB);
            pst.setString(1, "F&B");
            ResultSet tstb = pst.executeQuery();
            while(tstb.next()){
                tstbBox.addItem(tstb.getString("TenTSTB"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
        
    public final void Reset(){
        DefaultTableModel dm = (DefaultTableModel)fbTable.getModel();
        dm.getDataVector().removeAllElements();
        dm.fireTableDataChanged(); 
        SelectAllFB();
        SetTSTBBox();
        ColumnsAutoSizer.sizeColumnsToFit(fbTable);
        tstbBox.setSelectedIndex(-1);
        fbName.setText("");
        fbDVT.setText("");
        fbPrice.setText("");
        ghichuTxt.setText("");
        moneyLable.setText("");
        fbName.setEnabled(false);
        fbDVT.setEnabled(false);
        
    }
    
    public Substance() {
        initComponents();
        ImageIcon img = new ImageIcon("D:\\Football_Filed_Managerment\\src\\images\\icons/—Pngtree—football field plan_4710125 (1).png");
        this.setFrameIcon(img);
        Reset();
        fbTable.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent mouseEvent) {
                JTable table =(JTable) mouseEvent.getSource();
                java.awt.Point point = mouseEvent.getPoint();
                int row = table.rowAtPoint(point);
                if(row == -1){
                    return;
                }
                maFB = fbTable.getModel().getValueAt(row, 0).toString();
                if (mouseEvent.getClickCount() == 1 && table.getSelectedRow() != -1) {
                    try {
                        conn = cn.getConnection();
                        String select_1FB = "Select * from DichVu where MaDV = ?";
                        PreparedStatement pst = conn.prepareStatement(select_1FB);
                        pst.setString(1, maFB);
                        ResultSet fb = pst.executeQuery();
                        if(fb.next()){
                            tstbBox.setSelectedItem(GetTenTSTBfromID(fb.getString("MaTSTB")));
                            fbName.setText(fb.getString("TenDichVu"));
                            fbDVT.setText(fb.getString("DVT"));
                            fbPrice.setText(fb.getString("DonGia"));
                            moneyLable.setText(currencyFormatter.format(Double.parseDouble(fbPrice.getText())));
                            ghichuTxt.setText(fb.getString("GhiChu"));
                            maTSTB = fb.getString("MaTSTB");
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

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        fbName = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        fbPrice = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        fbDVT = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        ghichuTxt = new javax.swing.JTextField();
        moneyLable = new javax.swing.JLabel();
        searchTxt = new javax.swing.JTextField();
        addFB = new javax.swing.JButton();
        saveFB = new javax.swing.JButton();
        closeButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        fbTable = new javax.swing.JTable();
        tstbBox = new javax.swing.JComboBox<>();

        setTitle("Quản Lý Đồ Ăn Nước Uống");

        jLabel1.setForeground(new java.awt.Color(41, 128, 185));
        jLabel1.setText("Tên Đồ Ăn - Nước Uống");

        jLabel2.setForeground(new java.awt.Color(41, 128, 185));
        jLabel2.setText("Tên Tài Sản Thiết Bị");

        fbName.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(41, 128, 185), 1, true));
        fbName.setPreferredSize(new java.awt.Dimension(0, 21));

        jLabel3.setForeground(new java.awt.Color(41, 128, 185));
        jLabel3.setText("Đơn Giá");

        fbPrice.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(41, 128, 185), 1, true));
        fbPrice.setPreferredSize(new java.awt.Dimension(0, 21));
        fbPrice.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                fbPriceKeyReleased(evt);
            }
        });

        jLabel4.setForeground(new java.awt.Color(41, 128, 185));
        jLabel4.setText("Đơn Vị Tính");
        jLabel4.setToolTipText("");

        fbDVT.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(41, 128, 185), 1, true));
        fbDVT.setPreferredSize(new java.awt.Dimension(0, 21));

        jLabel5.setForeground(new java.awt.Color(41, 128, 185));
        jLabel5.setText("Ghi Chú");
        jLabel5.setToolTipText("");

        ghichuTxt.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(41, 128, 185), 1, true));
        ghichuTxt.setPreferredSize(new java.awt.Dimension(0, 21));

        moneyLable.setText("                      ");
        moneyLable.setToolTipText("");

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

        addFB.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/056-plus.png"))); // NOI18N
        addFB.setText("Thêm");
        addFB.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                addFBMouseClicked(evt);
            }
        });

        saveFB.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/022-save.png"))); // NOI18N
        saveFB.setText("Lưu");
        saveFB.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                saveFBMouseClicked(evt);
            }
        });

        closeButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/058-error.png"))); // NOI18N
        closeButton.setText("Đóng");
        closeButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                closeButtonMouseClicked(evt);
            }
        });

        fbTable.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(41, 128, 185), 1, true));
        fbTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã Đồ Ăn - Nước Uống", "Tên Đồ Ăn - Nước Uống", "Đơn Giá", "Đơn Vị Tính ", "Ghi Chú"
            }
        ));
        jScrollPane1.setViewportView(fbTable);

        tstbBox.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(41, 128, 185), 1, true));
        tstbBox.setPreferredSize(new java.awt.Dimension(0, 21));
        tstbBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tstbBoxActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel1)
                            .addComponent(jLabel3))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(moneyLable)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(tstbBox, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(fbName, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 190, Short.MAX_VALUE))
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel4))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(fbPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel5)))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(ghichuTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(fbDVT, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(searchTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(addFB)
                        .addGap(18, 18, 18)
                        .addComponent(saveFB)
                        .addGap(18, 18, 18)
                        .addComponent(closeButton)
                        .addGap(0, 0, Short.MAX_VALUE))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(25, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 585, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(tstbBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(fbName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(fbDVT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(fbPrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel3))
                    .addComponent(ghichuTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(moneyLable)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(searchTxt, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(addFB)
                        .addComponent(saveFB)
                        .addComponent(closeButton)))
                .addGap(18, 18, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void searchTxtMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_searchTxtMouseClicked
        searchTxt.setText("");
    }//GEN-LAST:event_searchTxtMouseClicked

    public void filter(String query){
        DefaultTableModel dm = (DefaultTableModel) fbTable.getModel();
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<DefaultTableModel>(dm);
        fbTable.setRowSorter(tr);
        tr.setRowFilter(RowFilter.regexFilter(query));
    }
    
    private void searchTxtKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchTxtKeyReleased
        String query = searchTxt.getText().trim();
        filter(query);
    }//GEN-LAST:event_searchTxtKeyReleased

    private void fbPriceKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_fbPriceKeyReleased
        String DonGia = null;
        if(Validate.notNumber(fbPrice.getText()) == true){
            JOptionPane.showMessageDialog(this, "Đơn giá phải là số");
            return;
        }
        if(fbPrice.getText().length() <= 0){
            moneyLable.setText("");
            return;
        }
        DonGia = currencyFormatter.format(Double.parseDouble(fbPrice.getText()));
        moneyLable.setText(String.valueOf(DonGia));  
    }//GEN-LAST:event_fbPriceKeyReleased

    private void addFBMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addFBMouseClicked
        Reset();
        CODE = "INSERT";
    }//GEN-LAST:event_addFBMouseClicked

    private void closeButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_closeButtonMouseClicked
        this.dispose();
    }//GEN-LAST:event_closeButtonMouseClicked

    public void insertFB(String tenFB, String dongia, String dvt, String ghichu, String loaiDV, String maTSTB){
        try{
            String insert = "INSERT INTO DichVu(TenDichVu, DonGia, DVT, GhiChu, LoaiDV, MaTSTB) VALUES(?,?,?,?,?,?)";
            conn = cn.getConnection();
            PreparedStatement pst = conn.prepareStatement(insert);
            pst.setString(1, tenFB);
            pst.setString(2, dongia);
            pst.setString(3, dvt);
            pst.setString(4, ghichu);
            pst.setString(5, loaiDV);
            pst.setString(6, maTSTB);
            pst.execute();
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void updateFB(String dongia, String ghichu, String maFB){
        try{
            String update = "UPDATE DichVu SET DonGia = ?, GhiChu = ? WHERE MaDV = ?";
            conn = cn.getConnection();
            PreparedStatement pst = conn.prepareStatement(update);
            pst.setString(1, dongia);
            pst.setString(2, ghichu);
            pst.setString(3, maFB);
            pst.execute();
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    
    private void saveFBMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_saveFBMouseClicked
        String tenFB = fbName.getText().toString().trim();
        String dongia = fbPrice.getText().toString().trim();
        String dvt = fbDVT.getText().toString().trim();
        String ghichu = ghichuTxt.getText().toString().trim();
        String loaiDV = "F&B";
        if(tenFB.length() == 0 || dongia.length() == 0 || dvt.length() == 0 || maTSTB.length() == 0 ){
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
                    if(dv.getString("TenDichVu").trim().equals(tenFB) == true){
                        count++;
                    }
                    if(dv.getString("MaTSTB").trim().equals(maTSTB) == true){
                        count++;
                    }
                    if(count > 0){
                        sb.append("Food & Beverage này có thể đã tồn tại");
                    }
                }
            } catch(Exception e){
                e.printStackTrace();
            }
            if(sb.length()>0){
                JOptionPane.showMessageDialog(this, sb.toString());
                return;
            }
            insertFB(tenFB, dongia, dvt, ghichu, loaiDV, maTSTB);
            JOptionPane.showMessageDialog(this, "Đã Thêm Đồ Ăn - Nước Uống Thành Công");
            Reset();
        }
        if( CODE.equals("UPDATE") == true){
            updateFB(dongia, ghichu, maFB);
            JOptionPane.showMessageDialog(this, "Chỉnh Sửa Thông Tin Đồ Ăn - Nước Uống Thành Công");
            Reset();
        }
    }//GEN-LAST:event_saveFBMouseClicked

    private void tstbBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tstbBoxActionPerformed
        if(tstbBox.getSelectedIndex() != -1){
                try {
                String select_TSTB = "Select * from TaiSanThietBi where MaTSTB = ?";
                conn = cn.getConnection();
                PreparedStatement pst = conn.prepareStatement(select_TSTB);
                maTSTB = GetIDTSTBfromTen(tstbBox.getSelectedItem().toString());
                pst.setString(1, maTSTB );
                ResultSet tstb = pst.executeQuery();
                if(tstb.next()){
                    fbName.setText(tstb.getString("TenTSTB"));
                    fbDVT.setText(tstb.getString("DVT"));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }//GEN-LAST:event_tstbBoxActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addFB;
    private javax.swing.JButton closeButton;
    private javax.swing.JTextField fbDVT;
    private javax.swing.JTextField fbName;
    private javax.swing.JTextField fbPrice;
    private javax.swing.JTable fbTable;
    private javax.swing.JTextField ghichuTxt;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel moneyLable;
    private javax.swing.JButton saveFB;
    private javax.swing.JTextField searchTxt;
    private javax.swing.JComboBox<String> tstbBox;
    // End of variables declaration//GEN-END:variables
}
