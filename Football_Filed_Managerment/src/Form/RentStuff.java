package Form;

import DataBase.ConnectDB;
import com.formdev.flatlaf.FlatLightLaf;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.Locale;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class RentStuff extends javax.swing.JInternalFrame {
    ConnectDB cn = new ConnectDB();
    Connection conn;
    Locale locale = new Locale("vn", "VN");      
    NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);
    String CODE = "INSERT", maPDS = null, maKH = null, maHD = null, maTSTB = null, maDV = null, tiencoc = null;
    Integer countTSTB = 0, remainTSTB = 0, countConst = 0, countConLai = 0, countDaTra = 0, countLamHong;
    UserSession session = UserSession.getInstance();
    String maNV = session.getUserID();
    
     
    public void SelectAllPDS(){
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
    
    
    public void Reset(){
        SelectAllPDS();
        loaiDVBox.setSelectedIndex(0);
        DefaultTableModel dm = (DefaultTableModel)ctptTable.getModel();
        dm.getDataVector().removeAllElements();
        dm.fireTableDataChanged(); 
        DefaultTableModel dm2 = (DefaultTableModel)phuthuTable.getModel();
        dm2.getDataVector().removeAllElements();
        dm2.fireTableDataChanged();
        countSpinner.setValue(0);
        maTSTB1.setText("");
        tenTSTB1.setText("");
        countSpinne1.setValue(0);
        maTSTB2.setText("");
        tenTSTB2.setText("");
        countSpiner2.setValue(0);
        lydoBox.setSelectedIndex(-1);
        maTSTB1.setEnabled(false);
        maTSTB2.setEnabled(false);
        tenTSTB1.setEnabled(false);
        tenTSTB2.setEnabled(false);
        loaiDVBox.setEnabled(true);
        dvBox.setEnabled(true);
        CODE = "INSERT";
        maPDS = null;
    }
    
    public void SelectAllCTPT(String maHD){
        DefaultTableModel dm = (DefaultTableModel)ctptTable.getModel();
        dm.getDataVector().removeAllElements();
        dm.fireTableDataChanged();
        DefaultTableCellRenderer stringRenderer = (DefaultTableCellRenderer)
        ctptTable.getDefaultRenderer(String.class);
        stringRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        String select_CTPT = "Select * from ChiTietPhieuThue WHERE MaHD = ?";
        try {
            conn = cn.getConnection();
            PreparedStatement pst = conn.prepareStatement(select_CTPT);
            pst.setString(1, maHD);
            ResultSet rs = pst.executeQuery();
            DefaultTableModel model = (DefaultTableModel) ctptTable.getModel();
            String TEN, SOLUONG, SOLUONGDATRA = " ", THANHTIEN, TienCoc, TienCocDaTra = " ", SoLuongLamHong, TrangThai;
            Integer tongTien = 0;
            while(rs.next()){
                TEN = GetDVfromID(rs.getString("MaDV"));
                SOLUONG = rs.getString("SoLuong");
                THANHTIEN = currencyFormatter.format(Double.parseDouble(rs.getString("TongTien")));
                tongTien = tongTien + Integer.parseInt(rs.getString("TongTien"));
                TienCoc = currencyFormatter.format(Double.parseDouble(rs.getString("TienCoc")));
                SOLUONGDATRA = rs.getString("SoLuongDaTra");
                if(rs.getString("TienCocDaTra") != null ){
                    TienCocDaTra = currencyFormatter.format(Double.parseDouble(rs.getString("TienCocDaTra")));
                }
                SoLuongLamHong = rs.getString("SoLuongLamHong");
                TrangThai = rs.getString("TrangThai");
                String[] cthd = { TEN, SOLUONG, SOLUONGDATRA, THANHTIEN, TienCoc, TienCocDaTra, SoLuongLamHong, TrangThai };
                model.addRow(cthd);
            }
            moneyLable.setText(currencyFormatter.format(Double.parseDouble(tongTien.toString())));
            ColumnsAutoSizer.sizeColumnsToFit(ctptTable);
            rs.close();
            pst.close();
            conn.close();
       } catch (Exception ex) {
          ex.printStackTrace();
       }
    }
    
    public void SelectAllPlusCharge(String maHD){
        DefaultTableModel dm = (DefaultTableModel)phuthuTable.getModel();
        dm.getDataVector().removeAllElements();
        dm.fireTableDataChanged();
        DefaultTableCellRenderer stringRenderer = (DefaultTableCellRenderer)
        phuthuTable.getDefaultRenderer(String.class);
        stringRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        String select_PC = "Select * from ChiTietHoaDon WHERE MaHD = ?";
        Integer tongTien = 0;
        try {
            conn = cn.getConnection();
            PreparedStatement pst = conn.prepareStatement(select_PC);
            pst.setString(1, maHD);
            ResultSet rs = pst.executeQuery();
            DefaultTableModel model = (DefaultTableModel) phuthuTable.getModel();
            String TEN = null, SOLUONG = null, THANHTIEN = null;
            while(rs.next()){
                if(GetLoaiDVfromID(rs.getString("MaDV")).equals("Phụ Phí")){
                    TEN = GetDVfromID(rs.getString("MaDV"));
                    SOLUONG = rs.getString("SoLuong");  
                    if(rs.getString("TongTien") != null ){
                        THANHTIEN = currencyFormatter.format(Double.parseDouble(rs.getString("TongTien")));
                        tongTien = tongTien + Integer.parseInt(rs.getString("TongTien"));
                    }                
                    String[] cthd = { TEN, SOLUONG, THANHTIEN };
                    model.addRow(cthd);
                }
            }
            moneyLable1.setText(currencyFormatter.format(Double.parseDouble(tongTien.toString())));
            rs.close();
            pst.close();
            conn.close();
       } catch (Exception ex) {
          ex.printStackTrace();
       }
    }
   
    public RentStuff() {
        initComponents();
        Reset();
        ImageIcon img = new ImageIcon("D:\\Football_Filed_Managerment\\src\\images\\icons/—Pngtree—football field plan_4710125 (1).png");
        this.setFrameIcon(img);
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
                        SelectAllCTPT(maHD);
                        SelectAllPlusCharge(maHD);
                        if(ctptTable.getRowCount() != 0){
                            dvBox.setSelectedItem(ctptTable.getModel().getValueAt(0, 0).toString());
                            countSpinner.setValue(Integer.parseInt(ctptTable.getModel().getValueAt(0, 1).toString()));
                        }
                        maTSTB1.setText("");
                        tenTSTB1.setText("");
                        countSpinne1.setValue(0);
                        maTSTB2.setText("");
                        tenTSTB2.setText("");
                        countSpiner2.setValue(0);
                        lydoBox.setSelectedIndex(-1);
                        loaiDVBox.setEnabled(true);
                        dvBox.setEnabled(true);
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
        ctptTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent mouseEvent) {
                JTable table =(JTable) mouseEvent.getSource();
                java.awt.Point point = mouseEvent.getPoint();
                int row = table.rowAtPoint(point);
                if(row == -1){
                    return;
                }
                loaiDVBox.setSelectedItem("Cho Thuê");
                dvBox.setSelectedItem(ctptTable.getModel().getValueAt(row, 0).toString());
                countSpinner.setValue(Integer.parseInt(ctptTable.getModel().getValueAt(row, 1).toString()));
                countConst = Integer.parseInt(ctptTable.getModel().getValueAt(row, 1).toString());
                if(ctptTable.getModel().getValueAt(row, 2) != null){
                    countDaTra = Integer.parseInt(ctptTable.getModel().getValueAt(row, 2).toString());
                }else{
                    countDaTra = 0;
                }
                if(ctptTable.getModel().getValueAt(row, 6) != null){
                    countLamHong = Integer.parseInt(ctptTable.getModel().getValueAt(row, 6).toString());
                }else{
                    countLamHong = 0;
                }
                maTSTB1.setText(maTSTB);
                maTSTB2.setText(maTSTB);
                tenTSTB1.setText(GetTenTSTBfromID(maTSTB1.getText().trim()));
                tenTSTB2.setText(GetTenTSTBfromID(maTSTB2.getText().trim()));
                countSpinne1.setValue(countDaTra);
                countSpiner2.setValue(countLamHong);
                loaiDVBox.setEnabled(false);
                dvBox.setEnabled(false);
                CODE = "UPDATE";
            }
        });
        phuthuTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent mouseEvent) {
                JTable table =(JTable) mouseEvent.getSource();
                java.awt.Point point = mouseEvent.getPoint();
                int row = table.rowAtPoint(point);
                if(row == -1){
                    return;
                }
                loaiDVBox.setSelectedItem("Phụ Phí");
                loaiDVBox.setEnabled(false);
                dvBox.setEnabled(false);
                dvBox.setSelectedItem(phuthuTable.getModel().getValueAt(row, 0).toString());
                countSpinner.setValue(Integer.parseInt(phuthuTable.getModel().getValueAt(row, 1).toString()));
                CODE = "UPDATE";
            }
        });
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        pdsTable = new javax.swing.JTable();
        closeButton = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        moneyLable = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        ctptTable = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        phuthuTable = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        dvBox = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        countLable = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        dvtTxt = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        dongiaTxt = new javax.swing.JTextField();
        dongiaLable = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        ghichuTxt = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        countSpinner = new javax.swing.JSpinner();
        saveCT = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        loaiDVBox = new javax.swing.JComboBox<>();
        jLabel16 = new javax.swing.JLabel();
        tiencocTxt = new javax.swing.JTextField();
        tiencocLable = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        tenTSTB1 = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        countSpinne1 = new javax.swing.JSpinner();
        traTSTB = new javax.swing.JButton();
        jLabel17 = new javax.swing.JLabel();
        maTSTB1 = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        tenTSTB2 = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        countSpiner2 = new javax.swing.JSpinner();
        xuatTSTB = new javax.swing.JButton();
        jLabel15 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        maTSTB2 = new javax.swing.JTextField();
        lydoBox = new javax.swing.JComboBox<>();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        moneyLable1 = new javax.swing.JLabel();

        setTitle("Cho Thuê ");

        pdsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã Đặt Sân", "Số Điện Thoại KH", "Sân Bóng"
            }
        ));
        jScrollPane1.setViewportView(pdsTable);

        closeButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/058-error.png"))); // NOI18N
        closeButton.setText("Đóng");
        closeButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                closeButtonMouseClicked(evt);
            }
        });

        jLabel6.setForeground(new java.awt.Color(41, 128, 185));
        jLabel6.setText("Tổng Hóa Đơn Hiện Tại");

        moneyLable.setText("                                    ");

        ctptTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Tên", "Số Lượng", "Đã Trả", "Thành Tiền", "Tiền Cọc", "Tiền Cọc Đã Trả", "Làm Hỏng / Mất", "Tình Trạng"
            }
        ));
        jScrollPane3.setViewportView(ctptTable);

        jLabel1.setForeground(new java.awt.Color(41, 128, 185));
        jLabel1.setText("Hóa Đơn Hiện Tại");

        jLabel9.setForeground(new java.awt.Color(41, 128, 185));
        jLabel9.setText("Phụ Thu");

        phuthuTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Tên Phụ Thu", "Số Lượng", "Thành Tiền"
            }
        ));
        jScrollPane2.setViewportView(phuthuTable);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Dịch Vụ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(41, 128, 185))); // NOI18N

        dvBox.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(41, 128, 185), 1, true));
        dvBox.setPreferredSize(new java.awt.Dimension(0, 21));
        dvBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dvBoxActionPerformed(evt);
            }
        });

        jLabel7.setForeground(new java.awt.Color(41, 128, 185));
        jLabel7.setText("Số lượng còn lại: ");

        countLable.setForeground(new java.awt.Color(41, 128, 185));
        countLable.setText("                                                                 ");
        countLable.setEnabled(false);
        countLable.setPreferredSize(new java.awt.Dimension(0, 21));

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

        dongiaLable.setForeground(new java.awt.Color(41, 128, 185));
        dongiaLable.setText("                                  ");
        dongiaLable.setPreferredSize(new java.awt.Dimension(0, 21));

        jLabel5.setForeground(new java.awt.Color(41, 128, 185));
        jLabel5.setText("Ghi Chú");

        ghichuTxt.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(41, 128, 185), 1, true));
        ghichuTxt.setEnabled(false);
        ghichuTxt.setPreferredSize(new java.awt.Dimension(0, 21));

        jLabel2.setForeground(new java.awt.Color(41, 128, 185));
        jLabel2.setText("Tên Dịch Vụ");

        jLabel8.setForeground(new java.awt.Color(41, 128, 185));
        jLabel8.setText("Số Lượng");

        countSpinner.setModel(new javax.swing.SpinnerNumberModel(0, 0, 999, 1));

        saveCT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/022-save.png"))); // NOI18N
        saveCT.setText("Lưu");
        saveCT.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                saveCTMouseClicked(evt);
            }
        });

        jLabel10.setForeground(new java.awt.Color(41, 128, 185));
        jLabel10.setText("Loại");

        loaiDVBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Cho Thuê", "Phụ Phí" }));
        loaiDVBox.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(41, 128, 185), 1, true));
        loaiDVBox.setPreferredSize(new java.awt.Dimension(0, 21));
        loaiDVBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loaiDVBoxActionPerformed(evt);
            }
        });

        jLabel16.setForeground(new java.awt.Color(41, 128, 185));
        jLabel16.setText("Tiền Cọc");

        tiencocTxt.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(41, 128, 185), 1, true));
        tiencocTxt.setEnabled(false);
        tiencocTxt.setPreferredSize(new java.awt.Dimension(0, 21));

        tiencocLable.setForeground(new java.awt.Color(41, 128, 185));
        tiencocLable.setText("                                  ");
        tiencocLable.setPreferredSize(new java.awt.Dimension(0, 21));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(dongiaTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(dongiaLable, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jLabel4)
                                    .addComponent(dvBox, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel7)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(countLable, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel3)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(dvtTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                            .addComponent(jLabel8)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(countSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jLabel5)
                                            .addComponent(ghichuTxt, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(65, 65, 65)
                                .addComponent(saveCT))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel10)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(loaiDVBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel16, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                        .addComponent(tiencocTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(tiencocLable, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(loaiDVBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2)
                .addGap(7, 7, 7)
                .addComponent(dvBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(countLable, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(dvtTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(dongiaTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dongiaLable, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel16)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tiencocTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tiencocLable, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 17, Short.MAX_VALUE)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ghichuTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(countSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(saveCT)
                .addContainerGap())
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Trả Đồ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(41, 128, 185))); // NOI18N

        jLabel11.setForeground(new java.awt.Color(41, 128, 185));
        jLabel11.setText("Tên Tài Sản Thiết Bị ");

        tenTSTB1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(41, 128, 185), 1, true));
        tenTSTB1.setPreferredSize(new java.awt.Dimension(0, 21));

        jLabel12.setForeground(new java.awt.Color(41, 128, 185));
        jLabel12.setText("Số Lượng Trả");

        countSpinne1.setModel(new javax.swing.SpinnerNumberModel(0, 0, 999, 1));

        traTSTB.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/022-save.png"))); // NOI18N
        traTSTB.setText("Lưu");
        traTSTB.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                traTSTBMouseClicked(evt);
            }
        });

        jLabel17.setForeground(new java.awt.Color(41, 128, 185));
        jLabel17.setText("Mã Tài Sản Thiết Bị");

        maTSTB1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(41, 128, 185), 1, true));
        maTSTB1.setPreferredSize(new java.awt.Dimension(0, 21));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11)
                    .addComponent(jLabel12)
                    .addComponent(jLabel17))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(traTSTB)
                    .addComponent(countSpinne1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tenTSTB1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(maTSTB1, javax.swing.GroupLayout.DEFAULT_SIZE, 190, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(24, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(maTSTB1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(tenTSTB1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(countSpinne1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(traTSTB)
                .addContainerGap())
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Xuất Đồ Do Khách Làm Hỏng Hoặc Làm Mất", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(41, 128, 185))); // NOI18N

        jLabel13.setForeground(new java.awt.Color(41, 128, 185));
        jLabel13.setText("Tên Tài Sản Thiết Bị ");

        tenTSTB2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(41, 128, 185), 1, true));
        tenTSTB2.setPreferredSize(new java.awt.Dimension(0, 21));

        jLabel14.setForeground(new java.awt.Color(41, 128, 185));
        jLabel14.setText("Số Lượng Xuất");

        countSpiner2.setModel(new javax.swing.SpinnerNumberModel(0, 0, 999, 1));

        xuatTSTB.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/022-save.png"))); // NOI18N
        xuatTSTB.setText("Lưu");
        xuatTSTB.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                xuatTSTBMouseClicked(evt);
            }
        });

        jLabel15.setForeground(new java.awt.Color(41, 128, 185));
        jLabel15.setText("Lý Do Xuất");

        jLabel18.setForeground(new java.awt.Color(41, 128, 185));
        jLabel18.setText("Mã Tài Sản Thiết Bị");

        maTSTB2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(41, 128, 185), 1, true));
        maTSTB2.setPreferredSize(new java.awt.Dimension(0, 21));

        lydoBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Khách Làm Hỏng", "Khách Làm Mất" }));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel13)
                    .addComponent(jLabel14)
                    .addComponent(jLabel15)
                    .addComponent(jLabel18))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(xuatTSTB)
                    .addComponent(countSpiner2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tenTSTB2, javax.swing.GroupLayout.DEFAULT_SIZE, 190, Short.MAX_VALUE)
                    .addComponent(maTSTB2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lydoBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(maTSTB2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(tenTSTB2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(countSpiner2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(lydoBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(xuatTSTB)
                .addGap(6, 6, 6))
        );

        jLabel19.setForeground(new java.awt.Color(41, 128, 185));
        jLabel19.setText("Sân Đang Hoạt Động");

        jLabel20.setForeground(new java.awt.Color(41, 128, 185));
        jLabel20.setText("Tổng Phụ Thu");

        moneyLable1.setText("                                    ");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel19)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 281, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(closeButton)
                        .addGap(92, 92, 92)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addGap(416, 416, 416)
                                .addComponent(jLabel20)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(moneyLable1, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(8, 8, 8))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel6)))
                        .addGap(18, 18, 18)
                        .addComponent(moneyLable, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 939, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGap(2, 2, 2)
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.LEADING))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(jLabel6)
                            .addComponent(moneyLable))
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(jLabel20)
                            .addComponent(moneyLable1))
                        .addGap(11, 11, 11)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel19)
                        .addGap(11, 11, 11)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addComponent(closeButton)))
                .addContainerGap(18, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void dvBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dvBoxActionPerformed
        if(dvBox.getSelectedIndex() == -1){
            return;
        }
        String select_DV = "Select * from DichVu where TenDichVu = ?";
        try{
            conn = cn.getConnection();
            PreparedStatement pst = conn.prepareStatement(select_DV);
            pst.setString(1, dvBox.getSelectedItem().toString());
            ResultSet rs = pst.executeQuery();
            if(rs.next()){
                maDV = rs.getString("MaDV");
                tiencocTxt.setText(rs.getString("TienCoc"));
                dvtTxt.setText(rs.getString("DVT"));
                dongiaTxt.setText(rs.getString("DonGia"));
                if(rs.getString("DonGia") != null){ 
                    dongiaLable.setText(currencyFormatter.format(Double.parseDouble(dongiaTxt.getText().trim())));
                }
                if(rs.getString("TienCoc") != null){
                    tiencocLable.setText(currencyFormatter.format(Double.parseDouble(tiencocTxt.getText().trim())));
                }    
                ghichuTxt.setText(rs.getString("GhiChu"));
                maTSTB = rs.getString("MaTSTB");
            }
            if(rs.getString("LoaiDV").equals("Cho Thuê")){
                jLabel7.show();
                countLable.show();
                jLabel16.show();
                tiencocTxt.show();
                tiencocLable.show();
                conn = cn.getConnection();
                String select_TSTB = "Select * from TaiSanThietBi where MaTSTB = ?";
                PreparedStatement pst1 = conn.prepareStatement(select_TSTB);
                pst1.setString(1, maTSTB);
                ResultSet tstb = pst1.executeQuery();
                if(tstb.next()){
                    countTSTB = Integer.parseInt(tstb.getString("SOLUONGTSTB"));
                    countLable.setText(tstb.getString("SOLUONGTSTB"));
                }
            }
            if(rs.getString("LoaiDV").equals("Phụ Phí")){
                jLabel7.hide();
                countLable.hide();
                jLabel16.hide();
                tiencocTxt.hide();
                tiencocLable.hide();
            }
            countSpinner.setValue(0);
            CODE = "INSERT";
            rs.close();
            pst.close();
            conn.close();
        } catch(Exception e){
            e.printStackTrace();
        }
    }//GEN-LAST:event_dvBoxActionPerformed

    private void closeButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_closeButtonMouseClicked
        this.dispose();
    }//GEN-LAST:event_closeButtonMouseClicked

    private void loaiDVBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loaiDVBoxActionPerformed
        dvBox.removeAllItems();
        String select_DV = "Select * from DichVu where LoaiDV = ?";
        try{
            conn = cn.getConnection();
            PreparedStatement pst = conn.prepareStatement(select_DV);
            pst.setString(1, loaiDVBox.getSelectedItem().toString());
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                dvBox.addItem(rs.getString("TenDichVu"));
            }
            rs.close();
            pst.close();
            conn.close();
        } catch(Exception e){
            e.printStackTrace();
        }
    }//GEN-LAST:event_loaiDVBoxActionPerformed

    private void saveCTMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_saveCTMouseClicked
        if(maHD == null){
            JOptionPane.showMessageDialog(this, "Vui Lòng Phiếu Cần Thêm");
            return;
        }
        if(loaiDVBox.getSelectedItem().toString().equals("Cho Thuê")){
            String soLuong = countSpinner.getValue().toString();
            String donGia = dongiaTxt.getText().trim();
            String tienCoc = tiencocTxt.getText().trim();
            String tongTien = String.valueOf(Integer.parseInt(soLuong) * Integer.parseInt(donGia));
            String tongTienCoc = String.valueOf(Integer.parseInt(soLuong) * Integer.parseInt(tienCoc));
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
                    String select_CTPT = "SELECT * FROM ChiTietPhieuThue WHERE MaDV = ? AND MaHD = ?";
                    conn = cn.getConnection();
                    PreparedStatement pst = conn.prepareStatement(select_CTPT);
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
                InsertCTPT(maHD, maDV, soLuong, tongTienCoc, tongTien, "Đang Thuê");
                if(soLuongconlai.equals("0")){
                    updateTSTB(soLuongconlai, "Hết Hàng", maTSTB);
                }else{
                    updateTSTB(soLuongconlai, "Còn Hàng", maTSTB);
                }   
                JOptionPane.showMessageDialog(this, "Đã thêm vào hóa đơn và cập nhật số lượng thành công");
                Reset();
            }
            if(CODE.equals("UPDATE")){
                if(soLuong.equals("0")){
                    delectCTPT(maHD, maDV);
                    JOptionPane.showMessageDialog(this, "Đã xóa chi tiết hóa đơn và cập nhật số lượng thành công");
                }
                else{
                    updateCTPT(maHD, maDV, soLuong, tongTienCoc, tongTien, "Đang Thuê");
                    JOptionPane.showMessageDialog(this, "Đã chỉnh sửa vào hóa đơn và cập nhật số lượng thành công");
                }
                soLuongconlai = String.valueOf(countTSTB - (Integer.parseInt(soLuong) - countConst));
                if(soLuongconlai.equals("0")){
                    updateTSTB(soLuongconlai, "Hết Hàng", maTSTB);
                }else{
                    updateTSTB(soLuongconlai, "Còn Hàng", maTSTB);
                }
                Reset();
            }
        }
        if(loaiDVBox.getSelectedItem().toString().equals("Phụ Phí")){
            String soLuong = countSpinner.getValue().toString();
            String donGia = dongiaTxt.getText().trim();
            String tongTien = String.valueOf(Integer.parseInt(soLuong) * Integer.parseInt(donGia));
            if(CODE.equals("INSERT")){
                if(countSpinner.getValue().toString().equals("0")){
                    JOptionPane.showMessageDialog(this, "Số lượng phải khác 0");
                    return;
                }
                try{
                    String select_CTPT = "SELECT * FROM ChiTietHoaDon WHERE MaDV = ? AND MaHD = ?";
                    conn = cn.getConnection();
                    PreparedStatement pst = conn.prepareStatement(select_CTPT);
                    pst.setString(1, maDV);
                    pst.setString(2, maHD);
                    pst.execute();
                    ResultSet rs = pst.executeQuery();
                    if(rs.next()){
                        JOptionPane.showMessageDialog(this, "Phụ Phí này đã được thêm vào hóa đơn trước đó. Hãy chỉnh sửa dòng bạn muốn.");
                        return;
                    }
                } catch(Exception e){
                    e.printStackTrace();
                }
                InsertCTDV(maHD, maDV, soLuong, tongTien);
                JOptionPane.showMessageDialog(this, "Đã thêm vào hóa đơn và cập nhật số lượng thành công");
                Reset();
            }
            if(CODE.equals("UPDATE")){
                if(soLuong.equals("0")){
                    delectCTDV(maHD, maDV);
                    JOptionPane.showMessageDialog(this, "Đã xóa phụ phí khỏi hóa đơn");
                }
                else{
                    updateCTDV(maHD, maDV, soLuong, tongTien);
                    JOptionPane.showMessageDialog(this, "Đã chỉnh sửa vào hóa đơn và cập nhật số lượng thành công");
                }
                Reset();
            }
        }
    }//GEN-LAST:event_saveCTMouseClicked

    private void traTSTBMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_traTSTBMouseClicked
        String idTSTB = maTSTB1.getText().trim();
        Integer soLuongTra = Integer.parseInt(countSpinne1.getValue().toString()) + countDaTra;
        if(idTSTB == null || soLuongTra.equals("0") || countSpinne1.getValue().toString().equals("0")){
            JOptionPane.showMessageDialog(this, "Bạn chưa chọn thiết bị trả. Số lượng trả phải lớn hơn 0.");
            return;
        }
        Integer soLuongHienTai = Integer.parseInt(countLable.getText()) + Integer.parseInt(countSpinne1.getValue().toString());
        String tienCocTra = String.valueOf(soLuongTra * Integer.parseInt(tiencocTxt.getText().trim()));
        updateTSTB(String.valueOf(soLuongHienTai), "Còn Hàng", maTSTB);
        if(soLuongTra + countLamHong > countConst){
            JOptionPane.showMessageDialog(this, "Số lượng trả nhiều hơn số lượng thuê.");
            return;
        }
        if(countConst - soLuongTra - countLamHong == 0){
            traTSTB(maHD, maDV, String.valueOf(soLuongTra) , tienCocTra, "Đã Hoàn Thành"); 
        }else{
            traTSTB(maHD, maDV, String.valueOf(soLuongTra), tienCocTra, "Đang Thuê"); 
        }
        JOptionPane.showMessageDialog(this, "Đã Trả Tài Sản Thiết Bị Thành Công.");
        Reset();
        return;
    }//GEN-LAST:event_traTSTBMouseClicked

    private void xuatTSTBMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_xuatTSTBMouseClicked
        String idTSTB = maTSTB2.getText().trim();
        LocalDate today = LocalDate.now();
        //String maDXH = GetMaDXH(maHD);
//        if(maDXH == null){
//            maDXH = randomMaDXH();
//            InsertDXH(maDXH, maNV, today.toString());
//            updateHD(maDXH, maHD);
//        }
        if( idTSTB == null || countLamHong == null || countSpiner2.getValue().toString().equals("0")){
            JOptionPane.showMessageDialog(this, "Bạn chưa chọn thiết bị. Số lượng phải lớn hơn 0.");
            return;
        }        
        Integer soLuongLamHong = countLamHong + Integer.parseInt(countSpiner2.getValue().toString());
        if(soLuongLamHong + countDaTra > countConst){
            JOptionPane.showMessageDialog(this, "Số lượng nhiều hơn số lượng thuê.");
            return;
        }
        //InsertCTPXH(maDXH, maTSTB, String.valueOf(soLuongLamHong), lydoTxt.getText().trim());
        if(lydoBox.getSelectedItem().toString().equals("Khách Làm Hỏng")){
            Integer soLuongHienTai = Integer.parseInt(countLable.getText()) + Integer.parseInt(countSpiner2.getValue().toString());
            updateTSTB(String.valueOf(soLuongHienTai), "Còn Hàng", maTSTB);
            String ghiChu = GetGhiChuTSTB(maTSTB) + " | " +  maPDS + " Khách Làm Hỏng " + countSpiner2.getValue().toString();
            UpdateGhiChuTSTB(ghiChu, maTSTB);
        }else if(lydoBox.getSelectedItem().toString().equals("Khách Làm Mất")){
            String ghiChu = GetGhiChuTSTB(maTSTB) + " | " +  maPDS + " Khách Làm Mất " + countSpiner2.getValue().toString();
            UpdateGhiChuTSTB(ghiChu, maTSTB);
        }
        if(countDaTra + soLuongLamHong == countConst ){
            hongTSTB(maHD, maDV, String.valueOf(soLuongLamHong), "Đã Hoàn Thành");
        }else{
            hongTSTB(maHD, maDV, String.valueOf(soLuongLamHong), "Đang Thuê");
        }
        JOptionPane.showMessageDialog(this, "Đã Cập Nhật Phiếu Thuê Thành Công.");
        Reset();
        return;
    }//GEN-LAST:event_xuatTSTBMouseClicked
    
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
    
    public void InsertCTPT(String maHD, String maDV, String soLuong, String tienCoc, String tongTien, String status){
        try{
            String insert = "INSERT INTO ChiTietPhieuThue(MaHD, MaDV, SoLuong, TienCoc, TongTien, TrangThai) VALUES(?,?,?,?,?,?)";
            conn = cn.getConnection();
            PreparedStatement pst = conn.prepareStatement(insert);
            pst.setString(1, maHD);
            pst.setString(2, maDV);
            pst.setString(3, soLuong);
            pst.setString(4, tienCoc);
            pst.setString(5, tongTien);
            pst.setString(6, status);
            pst.execute();
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void updateCTPT(String maHD, String maDV, String soLuong, String tienCoc, String tongTien, String status){
        try{
            String update = "UPDATE ChiTietPhieuThue SET SoLuong = ?, TienCoc = ?, TongTien = ?, TrangThai = ? WHERE MaDV = ? AND MaHD = ?";
            conn = cn.getConnection();
            PreparedStatement pst = conn.prepareStatement(update);
            pst.setString(1, soLuong);
            pst.setString(2, tienCoc);
            pst.setString(3, tongTien);
            pst.setString(4, status);
            pst.setString(5, maDV);
            pst.setString(6, maHD);
            pst.execute();
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void delectCTPT(String maHD, String maDV){
        try{
            String delete = "DELETE ChiTietPhieuThue WHERE MaDV = ? AND MaHD = ?";
            conn = cn.getConnection();
            PreparedStatement pst = conn.prepareStatement(delete);
            pst.setString(1, maDV);
            pst.setString(2, maHD);
            pst.execute();
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void traTSTB(String maHD, String maDV, String soLuongTra, String tienCocTra, String status){
        try{
            String update = "UPDATE ChiTietPhieuThue SET SoLuongDaTra = ?, TienCocDaTra = ?, TrangThai = ? WHERE MaDV = ? AND MaHD = ?";
            conn = cn.getConnection();
            PreparedStatement pst = conn.prepareStatement(update);
            pst.setString(1, soLuongTra);
            pst.setString(2, tienCocTra);
            pst.setString(3, status);
            pst.setString(4, maDV);
            pst.setString(5, maHD);
            pst.execute();
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void hongTSTB(String maHD, String maDV, String soLuongLamHong, String status){
        try{
            String update = "UPDATE ChiTietPhieuThue SET SoLuongLamHong = ?, TrangThai = ? WHERE MaDV = ? AND MaHD = ?";
            conn = cn.getConnection();
            PreparedStatement pst = conn.prepareStatement(update);
            pst.setString(1, soLuongLamHong);
            pst.setString(2, status);
            pst.setString(3, maDV);
            pst.setString(4, maHD);
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
    
    public void updateHD(String maDXH, String maHD){
        try{
            String insert = "UPDATE HoaDon SET MaDXH = ? WHERE MaHD = ?";
            conn = cn.getConnection();
            PreparedStatement pst = conn.prepareStatement(insert);
            pst.setString(1, maDXH);
            pst.setString(2, maHD);
            pst.execute();
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public String GetMaDXH(String maHD){
        String maDXH = null;
        try{
            String select_MaDXH = "SELECT * FROM HoaDon WHERE MaHD = ?";
            conn = cn.getConnection();
            PreparedStatement pst = conn.prepareStatement(select_MaDXH);
            pst.setString(1, maHD);
            ResultSet hd = pst.executeQuery();
            if(hd.next()){
                maDXH = hd.getString("MaDXH");            
            }
        } catch(Exception e){
            e.printStackTrace();
        }
        return maDXH;
    }
    
    public String GetGhiChuTSTB(String maTSTB){
        String ghiChu = null;
        try{
            String select_GhiChu = "SELECT * FROM TaiSanThietBi WHERE MaTSTB = ?";
            conn = cn.getConnection();
            PreparedStatement pst = conn.prepareStatement(select_GhiChu);
            pst.setString(1, maTSTB);
            ResultSet tstb = pst.executeQuery();
            if(tstb.next()){
                ghiChu = tstb.getString("GhiChu");            
            }
        } catch(Exception e){
            e.printStackTrace();
        }
        return ghiChu;
    }
    
    public void UpdateGhiChuTSTB(String ghiChu, String maTSTB){
        try{
            String insert = "UPDATE TaiSanThietBi SET GhiChu = ? WHERE MaTSTB = ?";
            conn = cn.getConnection();
            PreparedStatement pst = conn.prepareStatement(insert);
            pst.setString(1, ghiChu);
            pst.setString(2, maTSTB);
            pst.execute();
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void InsertDXH(String maDXH, String maNV, String ngayXuat ){
        try{
            String insert = "INSERT INTO DONXUATHANG( MaDXH, MaNV, NgayXuat ) VALUES(?,?,?)";
            conn = cn.getConnection();
            PreparedStatement pst = conn.prepareStatement(insert);
            pst.setString(1, maDXH);
            pst.setString(2, maNV);
            pst.setString(3, ngayXuat);
            pst.execute();
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void InsertCTPXH(String maPXH, String maTSTB, String soLuong, String lyDo){
        try{
            String insert = "INSERT INTO ChiTietDonXuatHang( MaDXH, MaTSTB, SOLUONG, LyDoXuat ) VALUES(?,?,?,?)";
            conn = cn.getConnection();
            PreparedStatement pst = conn.prepareStatement(insert);
            pst.setString(1, maPXH);
            pst.setString(2, maTSTB);
            pst.setString(3, soLuong);
            pst.setString(4, lyDo);
            pst.execute();
        } catch(Exception e){
            e.printStackTrace();
        }
    }
   
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton closeButton;
    private javax.swing.JLabel countLable;
    private javax.swing.JSpinner countSpiner2;
    private javax.swing.JSpinner countSpinne1;
    private javax.swing.JSpinner countSpinner;
    private javax.swing.JTable ctptTable;
    private javax.swing.JLabel dongiaLable;
    private javax.swing.JTextField dongiaTxt;
    private javax.swing.JComboBox<String> dvBox;
    private javax.swing.JTextField dvtTxt;
    private javax.swing.JTextField ghichuTxt;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JComboBox<String> loaiDVBox;
    private javax.swing.JComboBox<String> lydoBox;
    private javax.swing.JTextField maTSTB1;
    private javax.swing.JTextField maTSTB2;
    private javax.swing.JLabel moneyLable;
    private javax.swing.JLabel moneyLable1;
    private javax.swing.JTable pdsTable;
    private javax.swing.JTable phuthuTable;
    private javax.swing.JButton saveCT;
    private javax.swing.JTextField tenTSTB1;
    private javax.swing.JTextField tenTSTB2;
    private javax.swing.JLabel tiencocLable;
    private javax.swing.JTextField tiencocTxt;
    private javax.swing.JButton traTSTB;
    private javax.swing.JButton xuatTSTB;
    // End of variables declaration//GEN-END:variables
}
