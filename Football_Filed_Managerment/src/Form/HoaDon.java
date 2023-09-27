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
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class HoaDon extends javax.swing.JInternalFrame {
    ConnectDB cn = new ConnectDB();
    Connection conn;
    Locale locale = new Locale("vn", "VN");      
    NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);
    
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
    
    public String GetKHfromID(String maKH){
        String tenKH = null;
        try{    
            String select_KH = "Select * from KhachHang where SDT = ?";
            conn = cn.getConnection();
            PreparedStatement pst = conn.prepareStatement(select_KH);
            pst.setString(1, maKH);
            ResultSet kh = pst.executeQuery();
            if(kh.next()){
                tenKH = kh.getString("HoTen");         
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tenKH;
    }
    
    public void SelectAllfromHoaDon(){
        DefaultTableModel dm = (DefaultTableModel) hoaDonTable.getModel();
        dm.getDataVector().removeAllElements();
        dm.fireTableDataChanged();
        DefaultTableCellRenderer stringRenderer = (DefaultTableCellRenderer)
        hoaDonTable.getDefaultRenderer(String.class);
        stringRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        String select_HD = "Select * from HoaDon";
        try {
            conn = cn.getConnection();
            PreparedStatement pst = conn.prepareStatement(select_HD);
            ResultSet rs = pst.executeQuery();
            DefaultTableModel model = (DefaultTableModel) hoaDonTable.getModel();
            String MAPDS, HoTenKHACH = null, SanBong = null, NGAYLAP = null, NVLAP = null, TongTien = null;
            while(rs.next()){
                MAPDS = rs.getString("MaPhieuDat");
                SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
                SimpleDateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy");
                if(rs.getString("NgayLapHD") != null){
                    Date date = inputFormat.parse(rs.getString("NgayLapHD"));
                    NGAYLAP = outputFormat.format(date);
                    if(rs.getString("TongTien") != null){
                        TongTien = currencyFormatter.format(Double.parseDouble(rs.getString("TongTien")));
                    }
                    NVLAP = GetNVfromID(rs.getString("MaNV"));
                }
                conn = cn.getConnection();
                String select_PDS = "Select * from PhieuDatSan where MaPhieuDat = ?";
                PreparedStatement pst2 = conn.prepareStatement(select_PDS);
                pst2.setString(1, MAPDS);
                ResultSet pds = pst2.executeQuery();
                if(pds.next()){
                    HoTenKHACH = GetKHfromID(pds.getString("MAKH"));
                    SanBong = GetFBFfromID(pds.getString("MaSan"));
                }
                if(pds.getString("TrangThai").equals("Đã Hoàn Thành")){
                    String[] hdr = { MAPDS, HoTenKHACH, SanBong, NGAYLAP, NVLAP, TongTien };
                    model.addRow(hdr);
                }
                MAPDS = null; HoTenKHACH = null; SanBong = null; NGAYLAP = null; NVLAP = null; TongTien = null;
            }
            rs.close();
            pst.close();
            conn.close();
       } catch (Exception ex) {
          ex.printStackTrace();
       }
    }
        
    public HoaDon() {
        initComponents();
        SelectAllfromHoaDon();
        ColumnsAutoSizer.sizeColumnsToFit(hoaDonTable);
        hoaDonTable.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent mouseEvent) {
                JTable table =(JTable) mouseEvent.getSource();
                java.awt.Point point = mouseEvent.getPoint();
                int row = table.rowAtPoint(point);
                if(row == -1){
                    return;
                }
                if (mouseEvent.getClickCount() == 1 && table.getSelectedRow() != -1) {
                    maPDS.setText(hoaDonTable.getModel().getValueAt(row, 0).toString());
                    hotenKhach.setText(hoaDonTable.getModel().getValueAt(row, 1).toString());
                    tenSan.setText(hoaDonTable.getModel().getValueAt(row, 2).toString());
                    ngayLap.setText(hoaDonTable.getModel().getValueAt(row, 3).toString());
                    nhanVienLap.setText(hoaDonTable.getModel().getValueAt(row, 4).toString());
                    tongTien.setText(hoaDonTable.getModel().getValueAt(row, 5).toString());
                } 
                if (mouseEvent.getClickCount() == 2 && table.getSelectedRow() != -1) {
                    new Bill(hoaDonTable.getModel().getValueAt(row, 0).toString()).setVisible(true);
                } 
            }
        });
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        maPDS = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        hotenKhach = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        tenSan = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        ngayLap = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        nhanVienLap = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        tongTien = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        hoaDonTable = new javax.swing.JTable();
        closeButton = new javax.swing.JButton();

        setTitle("Quản Lý Hóa Đơn");

        jLabel1.setForeground(new java.awt.Color(41, 128, 185));
        jLabel1.setText("Mã Phiếu Đặt");

        maPDS.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(41, 128, 185), 1, true));
        maPDS.setPreferredSize(new java.awt.Dimension(0, 21));

        jLabel2.setForeground(new java.awt.Color(41, 128, 185));
        jLabel2.setText("Họ Tên Khách");

        hotenKhach.setText("                ");
        hotenKhach.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(41, 128, 185), 1, true));
        hotenKhach.setPreferredSize(new java.awt.Dimension(0, 21));

        jLabel3.setForeground(new java.awt.Color(41, 128, 185));
        jLabel3.setText("Sân Bóng");

        tenSan.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(41, 128, 185), 1, true));
        tenSan.setPreferredSize(new java.awt.Dimension(0, 21));

        jLabel4.setForeground(new java.awt.Color(41, 128, 185));
        jLabel4.setText("Ngày Lập Hóa Đơn");

        ngayLap.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(41, 128, 185), 1, true));
        ngayLap.setPreferredSize(new java.awt.Dimension(0, 21));

        jLabel5.setForeground(new java.awt.Color(41, 128, 185));
        jLabel5.setText("Nhân Viên Lập");

        nhanVienLap.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(41, 128, 185), 1, true));
        nhanVienLap.setPreferredSize(new java.awt.Dimension(0, 21));

        jLabel6.setForeground(new java.awt.Color(41, 128, 185));
        jLabel6.setText("Tổng Tiền");

        tongTien.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(41, 128, 185), 1, true));
        tongTien.setPreferredSize(new java.awt.Dimension(0, 21));

        hoaDonTable.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(41, 128, 185), 1, true));
        hoaDonTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã Phiếu Đặt", "Họ Tên Khách", "Sân Bóng", "Ngày Lập Hóa Đơn", "Nhân Viên Lập", "Tổng Tiền"
            }
        ));
        jScrollPane1.setViewportView(hoaDonTable);

        closeButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/058-error.png"))); // NOI18N
        closeButton.setText("Đóng");
        closeButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                closeButtonMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 832, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                    .addComponent(jLabel2)
                                    .addGap(18, 18, 18))
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jLabel1)
                                    .addGap(20, 20, 20)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addGap(39, 39, 39)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(maPDS, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(hotenKhach, javax.swing.GroupLayout.DEFAULT_SIZE, 188, Short.MAX_VALUE)
                            .addComponent(tenSan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(92, 92, 92)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(ngayLap, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(nhanVienLap, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(tongTien, javax.swing.GroupLayout.DEFAULT_SIZE, 190, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(closeButton)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(maPDS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(ngayLap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(hotenKhach, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(nhanVienLap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(tenSan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(tongTien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(closeButton))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void closeButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_closeButtonMouseClicked
        this.dispose();
    }//GEN-LAST:event_closeButtonMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton closeButton;
    private javax.swing.JTable hoaDonTable;
    private javax.swing.JTextField hotenKhach;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField maPDS;
    private javax.swing.JTextField ngayLap;
    private javax.swing.JTextField nhanVienLap;
    private javax.swing.JTextField tenSan;
    private javax.swing.JTextField tongTien;
    // End of variables declaration//GEN-END:variables
}
