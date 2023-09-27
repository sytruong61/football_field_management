package Form;

import DataBase.ConnectDB;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.Locale;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public final class SellFB extends javax.swing.JInternalFrame {
    ConnectDB cn = new ConnectDB();
    Connection conn;
    Locale locale = new Locale("vn", "VN");      
    NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);
    String CODE = "INSERT", maPDS = null, maKH = null, maHD = null, maNV = null, maTSTB = null, maDV = null;
    Integer countTSTB = 0, remainTSTB = 0, countConst = 0;
    
    public String GetFBFfromID(String maSan){
        String tenSan = null;
        String select_FBFfromID = "Select * from SanBong where MaSan = ?";
        try{
            conn = cn.getConnection();
            PreparedStatement pst = conn.prepareStatement(select_FBFfromID);
            pst.setString(1, maSan);
            ResultSet rs = pst.executeQuery();
            if(rs.next()){
               tenSan = rs.getString("TenSan");
            }
            rs.close();
            pst.close();
            conn.close();
        } catch(Exception ex){
            ex.printStackTrace();
        }
        return tenSan;
    }
    
    public String GetDVfromID(String maDV){
        String tenDV = "";
        try{    
            String select_DV = "Select * from DichVu where MaDV = ?";
            conn = cn.getConnection();
            PreparedStatement pst = conn.prepareStatement(select_DV);
            pst.setString(1, maDV);
            ResultSet dv = pst.executeQuery();
            if(dv.next()){
                tenDV = dv.getString("TenDichVu");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tenDV;
    }
    
    public String GetLoaiDVfromID(String maDV){
        String loaiDV = "";
        try{    
            String select_DV = "Select * from DichVu where MaDV = ?";
            conn = cn.getConnection();
            PreparedStatement pst = conn.prepareStatement(select_DV);
            pst.setString(1, maDV);
            ResultSet dv = pst.executeQuery();
            if(dv.next()){
                loaiDV = dv.getString("LoaiDV");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return loaiDV;
    } 
    
    public void SelectAllPDS(){
        ImageIcon img = new ImageIcon("D:\\Football_Filed_Managerment\\src\\images\\icons/—Pngtree—football field plan_4710125 (1).png");
        this.setFrameIcon(img);
        DefaultTableModel dm = (DefaultTableModel) pdsTable.getModel();
        dm.getDataVector().removeAllElements();
        dm.fireTableDataChanged();
        DefaultTableCellRenderer stringRenderer = (DefaultTableCellRenderer)
        pdsTable.getDefaultRenderer(String.class);
        stringRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        String select_PDS = "Select * from PhieuDatSan WHERE TrangThai = ?";
        try {
            conn = cn.getConnection();
            PreparedStatement pst = conn.prepareStatement(select_PDS);
            pst.setString(1, "Đã Nhận Sân");
            ResultSet rs = pst.executeQuery();
            DefaultTableModel model = (DefaultTableModel) pdsTable.getModel();
            String MAPDS, SDTKH, SanBong;
            while(rs.next()){
                MAPDS = rs.getString("MaPhieuDat");
                SDTKH = rs.getString("MAKH");
                SanBong = GetFBFfromID(rs.getString("MaSan"));
                String[] pds = { MAPDS, SDTKH, SanBong };
                model.addRow(pds);
            }
            rs.close();
            pst.close();
            conn.close();
       } catch (Exception ex) {
          ex.printStackTrace();
       }
    }
    
    public void Reset(){
        SelectAllPDS();
        DefaultTableModel dm = (DefaultTableModel)hddvTable.getModel();
        dm.getDataVector().removeAllElements();
        dm.fireTableDataChanged(); 
        SetFBBox();
        fbBox.setEnabled(true);
        CODE = "INSERT";
        maPDS = null;
        moneyLable.setText("");
    }
    
    public void SelectAllCTHD(String maHD){
        DefaultTableModel dm = (DefaultTableModel)hddvTable.getModel();
        dm.getDataVector().removeAllElements();
        dm.fireTableDataChanged();
        DefaultTableCellRenderer stringRenderer = (DefaultTableCellRenderer)
        hddvTable.getDefaultRenderer(String.class);
        stringRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        String select_CTHD = "Select * from ChiTietHoaDon WHERE MaHD = ?";
        Integer tongTien = 0;
        try {
            conn = cn.getConnection();
            PreparedStatement pst = conn.prepareStatement(select_CTHD);
            pst.setString(1, maHD);
            ResultSet rs = pst.executeQuery();
            DefaultTableModel model = (DefaultTableModel) hddvTable.getModel();
            String TENFB, SOLUONG, THANHTIEN;
            while(rs.next()){
                if(GetLoaiDVfromID(rs.getString("MaDV")).equals("F&B")){
                    TENFB = GetDVfromID(rs.getString("MaDV"));
                    SOLUONG = rs.getString("SoLuong");
                    THANHTIEN = currencyFormatter.format(Double.parseDouble(rs.getString("TongTien")));
                    tongTien = tongTien + Integer.parseInt(rs.getString("TongTien"));
                    String[] cthd = { TENFB, SOLUONG, THANHTIEN };
                    model.addRow(cthd);
                }
            }
            moneyLable.setText(currencyFormatter.format(Double.parseDouble(tongTien.toString())));
            rs.close();
            pst.close();
            conn.close();
       } catch (Exception ex) {
          ex.printStackTrace();
       }
    }
    
    public void SetFBBox(){
        fbBox.removeAllItems();
        String select_DV = "Select * from DichVu where LoaiDV = ?";
        try{
            conn = cn.getConnection();
            PreparedStatement pst = conn.prepareStatement(select_DV);
            pst.setString(1, "F&B");
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                fbBox.addItem(rs.getString("TenDichVu"));
            }
            rs.close();
            pst.close();
            conn.close();
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public SellFB() {
        initComponents();
        Reset();
        pdsTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent mouseEvent) {
                JTable table =(JTable) mouseEvent.getSource();
                java.awt.Point point = mouseEvent.getPoint();
                int row = table.rowAtPoint(point);
                if(row == -1){
                    return;
                }
                maPDS = pdsTable.getModel().getValueAt(row, 0).toString();
                maKH = pdsTable.getModel().getValueAt(row, 1).toString();
                String select_HD = "Select * from HoaDon WHERE MaPhieuDat = ?";
                try {
                    conn = cn.getConnection();
                    PreparedStatement pst = conn.prepareStatement(select_HD);
                    pst.setString(1, maPDS);
                    ResultSet rs = pst.executeQuery();
                    if(rs.next()){
                        maHD = rs.getString("MaHD");
                        SelectAllCTHD(maHD);
                        if(hddvTable.getRowCount() != 0){
                            maTSTB = hddvTable.getModel().getValueAt(0, 0).toString();
                            fbBox.setSelectedItem(maTSTB);
                        }
                    }
                    CODE = "INSERT";
                    rs.close();
                    pst.close();
                    conn.close();
               } catch (Exception ex) {
                  ex.printStackTrace();
               }
            }
        });
        hddvTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent mouseEvent) {
                JTable table =(JTable) mouseEvent.getSource();
                java.awt.Point point = mouseEvent.getPoint();
                int row = table.rowAtPoint(point);
                if(row == -1){
                    return;
                }
                fbBox.setSelectedItem(hddvTable.getModel().getValueAt(row, 0).toString());
                fbBox.setEnabled(false);
                countSpinner.setValue(Integer.parseInt(hddvTable.getModel().getValueAt(row, 1).toString()));
                countConst = Integer.parseInt(hddvTable.getModel().getValueAt(row, 1).toString());
                CODE = "UPDATE";
            }
        });
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        pdsTable = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        hddvTable = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        fbBox = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        dvtTxt = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        dongiaTxt = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        ghichuTxt = new javax.swing.JTextField();
        saveFB = new javax.swing.JButton();
        closeButton = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        moneyLable = new javax.swing.JLabel();
        dongiaLable = new javax.swing.JLabel();
        countLable = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        countSpinner = new javax.swing.JSpinner();

        setTitle("Đồ Ăn Nước Uống");
        setToolTipText("");

        pdsTable.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(41, 128, 185)));
        pdsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã Đặt Sân", "Số Điện Thoại KH", "Sân Bóng"
            }
        ));
        jScrollPane1.setViewportView(pdsTable);

        jLabel1.setForeground(new java.awt.Color(41, 128, 185));
        jLabel1.setText("Hóa Đơn Hiện Tại");

        hddvTable.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(41, 128, 185)));
        hddvTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Tên F&B", "Số Lượng", "Thành Tiền"
            }
        ));
        jScrollPane3.setViewportView(hddvTable);

        jLabel2.setForeground(new java.awt.Color(41, 128, 185));
        jLabel2.setText("Tên F&B");

        fbBox.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(41, 128, 185), 1, true));
        fbBox.setPreferredSize(new java.awt.Dimension(0, 21));
        fbBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fbBoxActionPerformed(evt);
            }
        });

        jLabel3.setForeground(new java.awt.Color(41, 128, 185));
        jLabel3.setText("Đơn Vị Tính");

        dvtTxt.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(41, 128, 185), 1, true));
        dvtTxt.setEnabled(false);
        dvtTxt.setPreferredSize(new java.awt.Dimension(0, 21));

        jLabel4.setForeground(new java.awt.Color(41, 128, 185));
        jLabel4.setText("Đơn Giá");

        dongiaTxt.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(41, 128, 185), 1, true));
        dongiaTxt.setEnabled(false);
        dongiaTxt.setPreferredSize(new java.awt.Dimension(0, 21));

        jLabel5.setForeground(new java.awt.Color(41, 128, 185));
        jLabel5.setText("Ghi Chú");

        ghichuTxt.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(41, 128, 185), 1, true));
        ghichuTxt.setEnabled(false);
        ghichuTxt.setPreferredSize(new java.awt.Dimension(0, 21));

        saveFB.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/022-save.png"))); // NOI18N
        saveFB.setText("Lưu");
        saveFB.setToolTipText("");
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

        jLabel6.setForeground(new java.awt.Color(41, 128, 185));
        jLabel6.setText("Tổng Hóa Đơn Hiện Tại");

        moneyLable.setForeground(new java.awt.Color(41, 128, 185));
        moneyLable.setText("                                    ");
        moneyLable.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(41, 128, 185), 1, true));

        dongiaLable.setForeground(new java.awt.Color(41, 128, 185));
        dongiaLable.setText("                                  ");
        dongiaLable.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(41, 128, 185), 1, true));
        dongiaLable.setPreferredSize(new java.awt.Dimension(0, 21));

        countLable.setForeground(new java.awt.Color(41, 128, 185));
        countLable.setText("                                                                 ");
        countLable.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(41, 128, 185), 1, true));
        countLable.setPreferredSize(new java.awt.Dimension(0, 21));

        jLabel7.setForeground(new java.awt.Color(41, 128, 185));
        jLabel7.setText("Số lượng còn lại: ");

        jLabel8.setForeground(new java.awt.Color(41, 128, 185));
        jLabel8.setText("Số Lượng");

        countSpinner.setModel(new javax.swing.SpinnerNumberModel(0, 0, 999, 1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 385, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel2)
                    .addComponent(jLabel5)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(moneyLable)
                            .addComponent(jLabel6)))
                    .addComponent(fbBox, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(dvtTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(countLable, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(dongiaTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(dongiaLable, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE))
                    .addComponent(ghichuTxt, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(saveFB)
                            .addComponent(jLabel8))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(countSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(closeButton))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addContainerGap(25, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(fbBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(countLable, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel7))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel3)
                                    .addComponent(dvtTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(dongiaTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(dongiaLable, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(ghichuTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel8)
                                    .addComponent(countSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(saveFB)
                                    .addComponent(closeButton))
                                .addGap(18, 18, 18)
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(moneyLable)
                                .addGap(15, 15, 15)))))
                .addContainerGap(23, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public void InsertCTDV(String maHD, String maDV, String soLuong, String tongTien){
        try{
            String insert = "INSERT INTO ChiTietHoaDon(MaDV,SoLuong,TongTien, MaHD) VALUES(?,?,?,?)";
            conn = cn.getConnection();
            PreparedStatement pst = conn.prepareStatement(insert);
            pst.setString(1, maDV);
            pst.setString(2, soLuong);
            pst.setString(3, tongTien);
            pst.setString(4, maHD);
            pst.execute();
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void updateCTDV(String maHD, String maDV, String soLuong, String tongTien){
        try{
            String update = "UPDATE ChiTietHoaDon SET SoLuong = ?, TongTien = ? WHERE MaDV = ? AND MaHD = ?";
            conn = cn.getConnection();
            PreparedStatement pst = conn.prepareStatement(update);
            pst.setString(1, soLuong);
            pst.setString(2, tongTien);
            pst.setString(3, maDV);
            pst.setString(4, maHD);
            pst.execute();
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void delectCTDV(String maHD, String maDV){
        try{
            String delete = "DELETE ChiTietHoaDon WHERE MaDV = ? AND MaHD = ?";
            conn = cn.getConnection();
            PreparedStatement pst = conn.prepareStatement(delete);
            pst.setString(1, maDV);
            pst.setString(2, maHD);
            pst.execute();
        } catch(Exception e){
            e.printStackTrace();
        }
    }
        
    public void updateTSTB(String soLuong, String status, String maTSTB){
        try{
            String update = "UPDATE TaiSanThietBi SET SOLUONGTSTB = ?, TinhTrangTSTB = ? WHERE MaTSTB = ?";
            conn = cn.getConnection();
            PreparedStatement pst = conn.prepareStatement(update);
            pst.setString(1, soLuong);
            pst.setString(2, status);
            pst.setString(3, maTSTB);
            pst.execute();
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    
    private void fbBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fbBoxActionPerformed
        if(fbBox.getSelectedIndex() == -1){
            return;
        }
        String select_DV = "Select * from DichVu where TenDichVu = ?";
        try{
            conn = cn.getConnection();
            PreparedStatement pst = conn.prepareStatement(select_DV);
            pst.setString(1, fbBox.getSelectedItem().toString());
            ResultSet rs = pst.executeQuery();
            if(rs.next()){
                maDV = rs.getString("MaDV");
                dvtTxt.setText(rs.getString("DVT"));
                dongiaTxt.setText(rs.getString("DonGia"));
                if(rs.getString("DonGia") != null){
                    dongiaLable.setText(currencyFormatter.format(Double.parseDouble(dongiaTxt.getText().trim())));
                }
                ghichuTxt.setText(rs.getString("GhiChu"));
                maTSTB = rs.getString("MaTSTB");
            }
            String select_TSTB = "Select * from TaiSanThietBi where MaTSTB = ?";
            PreparedStatement pst1 = conn.prepareStatement(select_TSTB);
            pst1.setString(1, maTSTB);
            ResultSet tstb = pst1.executeQuery();
            if(tstb.next()){
                countTSTB = Integer.parseInt(tstb.getString("SOLUONGTSTB"));
                countLable.setText(tstb.getString("SOLUONGTSTB"));
            }
            countSpinner.setValue(0);
            CODE = "INSERT";
            rs.close();
            pst.close();
            conn.close();
        } catch(Exception e){
            e.printStackTrace();
        }
    }//GEN-LAST:event_fbBoxActionPerformed

    private void saveFBMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_saveFBMouseClicked
        if(maPDS == null || maDV == null || maTSTB == null){
            JOptionPane.showMessageDialog(this, "Hãy chọn đủ giá trị mà bạn muốn thêm");
            return;
        }
        String soLuong = countSpinner.getValue().toString();
        String donGia = dongiaTxt.getText().trim();
        String tongTien = String.valueOf(Integer.parseInt(soLuong) * Integer.parseInt(donGia));
        String soLuongconlai = null;
        if(CODE.equals("INSERT")){
            soLuongconlai = String.valueOf(countTSTB - Integer.parseInt(soLuong));
            if(countSpinner.getValue().toString().equals("0")){
                JOptionPane.showMessageDialog(this, "Số lượng phải khác 0");
                return;
            }
            if(countTSTB == 0 || Integer.parseInt(countSpinner.getValue().toString()) > countTSTB){
                JOptionPane.showMessageDialog(this, "Số lượng còn lại không đủ");
                return;
            }
            try{
                String select_CTHD = "SELECT * FROM ChiTietHoaDon WHERE MaDV = ? AND MaHD = ?";
                conn = cn.getConnection();
                PreparedStatement pst = conn.prepareStatement(select_CTHD);
                pst.setString(1, maDV);
                pst.setString(2, maHD);
                pst.execute();
                ResultSet rs = pst.executeQuery();
                if(rs.next()){
                    JOptionPane.showMessageDialog(this, "Dịch vụ này đã được thêm vào hóa đơn trước đó. Hãy chỉnh sửa dòng bạn muốn.");
                    return;
                }
            } catch(Exception e){
                e.printStackTrace();
            }
            InsertCTDV(maHD, maDV, soLuong, tongTien);
            JOptionPane.showMessageDialog(this, "Đã thêm vào hóa đơn và cập nhật số lượng thành công");
        }
        if(CODE.equals("UPDATE")){
            if(soLuong.equals("0")){
                delectCTDV(maHD, maDV);
                JOptionPane.showMessageDialog(this, "Đã xóa chi tiết hóa đơn và cập nhật số lượng thành công");
            }
            else{
                updateCTDV(maHD, maDV, soLuong, tongTien);
                JOptionPane.showMessageDialog(this, "Đã chỉnh sửa vào hóa đơn và cập nhật số lượng thành công");
            }
            soLuongconlai = String.valueOf(countTSTB - (Integer.parseInt(soLuong) - countConst));
        }            
        if(soLuongconlai.equals("0")){
            updateTSTB(soLuongconlai, "Hết Hàng", maTSTB);
        }else{
            updateTSTB(soLuongconlai, "Còn Hàng", maTSTB);
        }            
        Reset();
    }//GEN-LAST:event_saveFBMouseClicked

    private void closeButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_closeButtonMouseClicked
        this.dispose();
    }//GEN-LAST:event_closeButtonMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton closeButton;
    private javax.swing.JLabel countLable;
    private javax.swing.JSpinner countSpinner;
    private javax.swing.JLabel dongiaLable;
    private javax.swing.JTextField dongiaTxt;
    private javax.swing.JTextField dvtTxt;
    private javax.swing.JComboBox<String> fbBox;
    private javax.swing.JTextField ghichuTxt;
    private javax.swing.JTable hddvTable;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel moneyLable;
    private javax.swing.JTable pdsTable;
    private javax.swing.JButton saveFB;
    // End of variables declaration//GEN-END:variables
}
