package Form;

import DataBase.ConnectDB;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.FlatLightLaf;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

public class DetailInputStorage extends javax.swing.JFrame {
    ConnectDB cn = new ConnectDB();
    Connection conn;
    Locale locale = new Locale("vn", "VN");      
    NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);
    String CODE, ID;
    UserSession userSession = UserSession.getInstance();
    String IDNV = userSession.getUserID();
    String tenNCC, tenNV, maDNH, soLuongconst, donGiaconst;
    
    public final void SetNCCBox(){
        try {
            String select_NCC = "Select * from NhaCungCap";
            PreparedStatement pst4 = conn.prepareStatement(select_NCC);
            ResultSet ncc = pst4.executeQuery();
            while(ncc.next()){
                String nccName = ncc.getString("TenNCC");
                nccBox.addItem(nccName);
            } 
            System.out.println(" Done ");
        } catch (SQLException e) {
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
            System.out.println(" Done ");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public final void SetDNH(String maDNH){
        try {
            String select_DNH = "Select * from DonNhapHang where MaDNH = ?";
            PreparedStatement pst4 = conn.prepareStatement(select_DNH);
            pst4.setString(1, maDNH);
            ResultSet dnh = pst4.executeQuery();
            if(dnh.next()){
                ngaynhapBox.setDate(java.sql.Date.valueOf(dnh.getString("NgayNhap")));
                LocalDate date = LocalDate.parse(dnh.getString("NgayNhap"), DateTimeFormatter.ISO_LOCAL_DATE);
                if(date.isBefore(LocalDate.now().minusDays(3))){
                    addDNH.setVisible(false);
                    fixDNH.setText("Xem");
                    saveDNH.setVisible(false);
                }
                moneyTxt.setText(dnh.getString("TongTien"));
                moneyLable.setText(String.valueOf(currencyFormatter.format(Double.parseDouble(moneyTxt.getText()))));  
                ghichuTxt.setText(dnh.getString("GhiChu"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
        
    public final void SetTSTBBox(String tenNCC){
        try {
            String select_TSTB = "Select * from TaiSanThietBi where MaNCC = ?";
            PreparedStatement pst4 = conn.prepareStatement(select_TSTB);
            pst4.setString(1, GetIDNCCfromTen(tenNCC));
            ResultSet tstb = pst4.executeQuery();
            while(tstb.next()){
                String tstbName = tstb.getString("TenTSTB");
                tstbBox.addItem(tstbName);
            }
            System.out.println(" Done ");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public final void SetBox(){
        SetNCCBox();
        SetNVBox();
        tstbBox.removeAllItems();
        SetTSTBBox(tenNCC);
        SetDNH(maDNH);
        nccBox.setSelectedItem(tenNCC);
        nvBox.setSelectedItem(tenNV);
        tstbBox.setSelectedIndex(-1);
    }
    
    public DetailInputStorage(String CODE, String tenNCC,String tenNV, String ID) {
        initComponents();
        this.CODE = CODE;
        this.tenNCC = tenNCC;
        this.tenNV = tenNV;
        this.maDNH = ID;
        this.ID = ID;
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        ImageIcon img = new ImageIcon("D:\\Football_Filed_Managerment\\src\\images\\icons/—Pngtree—football field plan_4710125 (1).png");
        this.setIconImage(img.getImage());
        nccBox.setEnabled(false);
        nvBox.setEnabled(false);
        ngaynhapBox.setEnabled(false);
        categoryBox.setSelectedIndex(-1);
        moneyTxt.setEnabled(false);
        //Reset();
        if(CODE.equals("VIEW") == true){           
            SelectAllCTDNH(ID);
        }
        SetBox();
        ColumnsAutoSizer.sizeColumnsToFit(ctdnhTable);
        tstbBox.addActionListener (new ActionListener () {
            public void actionPerformed(ActionEvent e) {
                if(tstbBox.getSelectedIndex() == -1){
                    return;
                }
                String select_TSTB = "Select * from TaiSanThietBi where TenTSTB = ?";
                try{
                    conn = cn.getConnection();
                    PreparedStatement pst = conn.prepareStatement(select_TSTB);
                    pst.setString(1, tstbBox.getSelectedItem().toString().trim());
                    ResultSet rs = pst.executeQuery();
                    if(rs.next()){
                        tstbText.setText(rs.getString("TenTSTB"));
                        categoryBox.setSelectedItem(rs.getString("LoaiTSTB"));
                        dvtText.setText(rs.getString("DVT"));
                    }
                } catch(ClassNotFoundException | SQLException ex){
                    System.out.println(ex.toString() + " 3 ");
                }
            }
        });
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
    
    public String GetIDNVfromTen(String tenNV){
        String ID = null;
        String select_NVfromTen = "Select * from NhanVien where HoTen = ?";
        try{
            conn = cn.getConnection();
            PreparedStatement pst = conn.prepareStatement(select_NVfromTen);
            pst.setString(1, ID);
            ResultSet rs = pst.executeQuery();
            if(rs.next()){
               ID = rs.getString("MaNV");
            }
        } catch(Exception e){
            e.printStackTrace();
        }
        return ID;
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
    
    public String GetSoLuongTSTBfromID(String maTSTB){
        String slTSTB = null;
        String select_slTSTBfromID = "Select * from TaiSanThietBi where MaTSTB = ?";
        try{
            conn = cn.getConnection();
            PreparedStatement pst = conn.prepareStatement(select_slTSTBfromID);
            pst.setString(1, maTSTB);
            ResultSet rs = pst.executeQuery();
            if(rs.next()){
                slTSTB = rs.getString("SOLUONGTSTB");
            }
        } catch(Exception e){
            System.out.println(e.toString() + " 106 ");
        }
        return slTSTB;
    }
         
    public final void SelectAllCTDNH(String maDNH){
        String select_CTDNH = "Select * from ChiTietDonNhapHang where MaDNH = ?";
        try{
            conn = cn.getConnection();
            PreparedStatement pst = conn.prepareStatement(select_CTDNH);
            pst.setString(1, maDNH);
            ResultSet rs = pst.executeQuery();
            DefaultTableModel model = (DefaultTableModel) ctdnhTable.getModel();
            String TenTSTB, SOLUONG, DVT, DonGia;
            while(rs.next()){
                TenTSTB = GetTSTBfromID(rs.getString("MaTSTB"));
                SOLUONG = rs.getString("SOLUONG");
                DVT = rs.getString("DVT");
                DonGia = rs.getString("DonGia");
                String[] ctdnh = {TenTSTB, SOLUONG, DVT, DonGia};
                model.addRow(ctdnh);               
            }
            ColumnsAutoSizer.sizeColumnsToFit(ctdnhTable);
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public final void SetTSTBfromTen(String tenTSTB, String soLuong, String donGia){
        try {
            conn = cn.getConnection();
            String select_1TSTB = "Select * from TaiSanThietBi where MaTSTB = ?";
            PreparedStatement pst3 = conn.prepareStatement(select_1TSTB);
            pst3.setString(1, GetIDTSTBfromTen(tenTSTB));
            ResultSet tstb = pst3.executeQuery();
            if(tstb.next()){
                tstbBox.setSelectedItem(tenTSTB);
                tstbText.setText(tenTSTB);
                categoryBox.setSelectedItem(tstb.getString("LoaiTSTB"));
                dvtText.setText(tstb.getString("DVT"));
            }
                countTxt.setText(soLuong);
                dongiaTxt.setText(donGia);
                costLable.setText(String.valueOf(currencyFormatter.format(Double.parseDouble(dongiaTxt.getText()))));  
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    
    public final int TongTien(String maDNH){
        int money = 0;
        try {
            conn = cn.getConnection();
            String select_CTDNH = "Select * from ChiTietDonNhapHang where MaDNH = ?";
            PreparedStatement pst3 = conn.prepareStatement(select_CTDNH);
            pst3.setString(1, maDNH);
            ResultSet ctdnh = pst3.executeQuery();
            while(ctdnh.next()){
                money = money + Integer.parseInt(ctdnh.getString("SOLUONG"))*Integer.parseInt(ctdnh.getString("DonGia"));
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return money;
    }
   
    // <editor-fold defaultstate="collapsed" desc="SetNCCBox">
//        public final void SetNCCBox(){
//        try {
//            String select_NCC = "Select * from NhaCungCap";
//            PreparedStatement pst4 = conn.prepareStatement(select_NCC);
//            ResultSet ncc = pst4.executeQuery();
//            while(ncc.next()){
//                String nccName = ncc.getString("TenNCC");
//                nccBox.addItem(nccName);
//            }            
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
// </editor-fold> 
    
    public final void Reset(){
        DefaultTableModel dm = (DefaultTableModel)ctdnhTable.getModel();
        dm.getDataVector().removeAllElements();
        dm.fireTableDataChanged();
        SelectAllCTDNH(maDNH);
        SetBox();
        tstbBox.setSelectedIndex(-1);
        tstbText.setText("");
        categoryBox.setSelectedIndex(-1);
        moneyLable.setText(String.valueOf(currencyFormatter.format(Double.parseDouble(moneyTxt.getText()))));  
        costLable.setText("");
        dvtText.setText("");
        countTxt.setText("");
        dongiaTxt.setText("");
    }
    
    public DetailInputStorage() {
        initComponents();
        Reset();
        if(CODE.equals("VIEW") == true){           
            SelectAllCTDNH(ID);
        }
        SetBox();
        ColumnsAutoSizer.sizeColumnsToFit(ctdnhTable);

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        nccBox = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        ngaynhapBox = new com.toedter.calendar.JDateChooser();
        jLabel4 = new javax.swing.JLabel();
        moneyTxt = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        ghichuTxt = new javax.swing.JTextField();
        searchTxt = new javax.swing.JTextField();
        addDNH = new javax.swing.JButton();
        fixDNH = new javax.swing.JButton();
        saveDNH = new javax.swing.JButton();
        nvBox = new javax.swing.JComboBox<>();
        cancelButton = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        ctdnhTable = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        tstbBox = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        categoryBox = new javax.swing.JComboBox<>();
        tstbText = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        dvtText = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        countTxt = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        dongiaTxt = new javax.swing.JTextField();
        moneyLable = new javax.swing.JLabel();
        costLable = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Chi Tiết Đơn Nhập Hàng");
        setAlwaysOnTop(true);

        jLabel2.setForeground(new java.awt.Color(41, 128, 185));
        jLabel2.setText("Nhà Cung Cấp");

        nccBox.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(41, 128, 185), 1, true));
        nccBox.setPreferredSize(new java.awt.Dimension(0, 21));

        jLabel7.setForeground(new java.awt.Color(41, 128, 185));
        jLabel7.setText("Nhân Viên");

        jLabel5.setForeground(new java.awt.Color(41, 128, 185));
        jLabel5.setText("Ngày Nhập");

        ngaynhapBox.setPreferredSize(new java.awt.Dimension(0, 21));

        jLabel4.setForeground(new java.awt.Color(41, 128, 185));
        jLabel4.setText("Tổng Tiền");

        moneyTxt.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(41, 128, 185), 1, true));
        moneyTxt.setPreferredSize(new java.awt.Dimension(0, 21));
        moneyTxt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                moneyTxtKeyReleased(evt);
            }
        });

        jLabel6.setForeground(new java.awt.Color(41, 128, 185));
        jLabel6.setText("Ghi Chú Đơn Nhập Hàng");

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

        fixDNH.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/218-edit.png"))); // NOI18N
        fixDNH.setText("Chỉnh sửa ");
        fixDNH.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                fixDNHMouseClicked(evt);
            }
        });

        saveDNH.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/022-save.png"))); // NOI18N
        saveDNH.setText("Lưu");
        saveDNH.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                saveDNHMouseClicked(evt);
            }
        });

        nvBox.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(41, 128, 185), 1, true));
        nvBox.setPreferredSize(new java.awt.Dimension(0, 21));

        cancelButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/058-error.png"))); // NOI18N
        cancelButton.setText("Đóng");
        cancelButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cancelButtonMouseClicked(evt);
            }
        });

        ctdnhTable.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(41, 128, 185), 1, true));
        ctdnhTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Thiết Bị Tài Sản ", "Số Lượng", "Đơn Vị Tính", "Đơn Giá"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(ctdnhTable);

        jLabel3.setForeground(new java.awt.Color(41, 128, 185));
        jLabel3.setText("Tài Sản Thiết Bị");

        tstbBox.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(41, 128, 185), 1, true));
        tstbBox.setPreferredSize(new java.awt.Dimension(0, 21));

        jLabel8.setForeground(new java.awt.Color(41, 128, 185));
        jLabel8.setText("Loại Tài Sản - Thiết Bị");

        categoryBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "F&B", "Cho Thuê", "Thiết Bị Sân Bóng", " " }));
        categoryBox.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(41, 128, 185), 1, true));
        categoryBox.setPreferredSize(new java.awt.Dimension(0, 21));

        tstbText.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(41, 128, 185), 1, true));
        tstbText.setPreferredSize(new java.awt.Dimension(0, 21));

        jLabel9.setForeground(new java.awt.Color(41, 128, 185));
        jLabel9.setText("Đơn Vị Tính");

        dvtText.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(41, 128, 185), 1, true));
        dvtText.setPreferredSize(new java.awt.Dimension(0, 21));

        jLabel10.setForeground(new java.awt.Color(41, 128, 185));
        jLabel10.setText("Số Lượng");

        countTxt.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(41, 128, 185), 1, true));
        countTxt.setPreferredSize(new java.awt.Dimension(0, 21));

        jLabel11.setForeground(new java.awt.Color(41, 128, 185));
        jLabel11.setText("Đơn Giá");

        dongiaTxt.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(41, 128, 185), 1, true));
        dongiaTxt.setPreferredSize(new java.awt.Dimension(0, 21));
        dongiaTxt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                dongiaTxtKeyReleased(evt);
            }
        });

        moneyLable.setText("         ");

        costLable.setText("         ");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 649, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel5))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(299, 299, 299)
                                .addComponent(jLabel7)
                                .addGap(18, 18, 18)
                                .addComponent(nvBox, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(300, 300, 300)
                                .addComponent(jLabel4)
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(moneyLable, javax.swing.GroupLayout.DEFAULT_SIZE, 190, Short.MAX_VALUE)
                                    .addComponent(moneyTxt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel6))
                        .addGap(30, 30, 30)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(ghichuTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(tstbBox, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(categoryBox, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(countTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ngaynhapBox, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(nccBox, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(searchTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(addDNH)
                        .addGap(18, 18, 18)
                        .addComponent(fixDNH)
                        .addGap(18, 18, 18)
                        .addComponent(saveDNH)
                        .addGap(18, 18, 18)
                        .addComponent(cancelButton))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(layout.createSequentialGroup()
                            .addGap(434, 434, 434)
                            .addComponent(costLable, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel8)
                                .addComponent(jLabel10))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                    .addGap(267, 267, 267)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(tstbText, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(jLabel9)
                                            .addGap(10, 10, 10)
                                            .addComponent(dvtText, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGroup(layout.createSequentialGroup()
                                    .addGap(268, 268, 268)
                                    .addComponent(jLabel11)
                                    .addGap(26, 26, 26)
                                    .addComponent(dongiaTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addContainerGap(22, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jLabel2))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jLabel7))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(nvBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(nccBox, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel5))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(jLabel4))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(moneyTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(ngaynhapBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(ghichuTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(moneyLable)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(jLabel3))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tstbText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(tstbBox, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel9)
                                .addComponent(dvtText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(categoryBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(jLabel10))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11)
                            .addComponent(countTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addComponent(dongiaTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(costLable)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(searchTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(addDNH)
                    .addComponent(fixDNH)
                    .addComponent(saveDNH)
                    .addComponent(cancelButton, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
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
        DefaultTableModel dm = (DefaultTableModel) ctdnhTable.getModel();
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<DefaultTableModel>(dm);
        ctdnhTable.setRowSorter(tr);
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

    private void fixDNHMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_fixDNHMouseClicked
        int column = 0;
        if(ctdnhTable.getSelectionModel().isSelectionEmpty()){
            JOptionPane.showMessageDialog(this, "Vui Lòng chọn dòng cần chỉnh sửa");
            return;
        }
        int row = ctdnhTable.getSelectedRow();
        String tenTSTB = ctdnhTable.getModel().getValueAt(row, column).toString();
        String soLuong = ctdnhTable.getModel().getValueAt(row, 1).toString();
        String donGia = ctdnhTable.getModel().getValueAt(row, 3).toString();
        soLuongconst = soLuong;
        donGiaconst = donGia;
        String dongia = ctdnhTable.getModel().getValueAt(row, 3).toString();
        SetTSTBfromTen(tenTSTB, soLuong, dongia);
        tstbBox.setEnabled(false);
        tstbText.setEnabled(false);
        categoryBox.setEnabled(false);
        dvtText.setEnabled(false);
        CODE = "UPDATE";
    }//GEN-LAST:event_fixDNHMouseClicked

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
    
    public void insertCTDNH(String maDNH, String maTSTB, String soLuong, String donGia,  String dvt){
        try{
            String insert = "INSERT INTO ChiTietDonNhapHang(MaDNH, MaTSTB, SOLUONG, DonGia, DVT) VALUES(?,?,?,?,?)";
            conn = cn.getConnection();
            PreparedStatement pst2 = conn.prepareStatement(insert);
            pst2.setString(1, maDNH);
            pst2.setString(2, maTSTB);
            pst2.setString(3, soLuong);
            pst2.setString(4, donGia);
            pst2.setString(5, dvt);
            pst2.execute();
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void updateDNH(String maNCC, String maNV, String ngayNhap, String tongTien, String ghichu, String maDNH){
        try{
            String update = "UPDATE DonNhapHang SET MaNCC = ?, MaNV = ?, NgayNhap = ?, TongTien = ?, GhiChu = ? WHERE MaDNH = ?";
            conn = cn.getConnection();
            PreparedStatement pst2 = conn.prepareStatement(update);
            pst2.setString(1, maNCC);
            pst2.setString(2, maNV);
            pst2.setString(3, ngayNhap);
            pst2.setString(4, tongTien);
            pst2.setString(5, ghichu);
            pst2.setString(6, maDNH);
            pst2.execute();
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void updateCTDNH(String maDNH, String maTSTB, String soLuong, String donGia,  String dvt){
        try{
            String update = "UPDATE ChiTietDonNhapHang SET SoLuong = ?, DonGia = ?, DVT = ? WHERE MaDNH = ? AND  MaTSTB = ?";
            conn = cn.getConnection();
            PreparedStatement pst2 = conn.prepareStatement(update);
            pst2.setString(1, soLuong);
            pst2.setString(2, donGia);
            pst2.setString(3, dvt);            
            pst2.setString(4, maDNH);
            pst2.setString(5, maTSTB);
            pst2.execute();
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void deleteCTDNH(String maDNH, String maTSTB){
        try{
            String delete = "DELETE FROM ChiTietDonNhapHang WHERE MaDNH = ? AND  MaTSTB = ?";
            conn = cn.getConnection();
            PreparedStatement pst2 = conn.prepareStatement(delete);            
            pst2.setString(1, maDNH);
            pst2.setString(2, maTSTB);
            pst2.execute();
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void insertTSTB(String maNCC, String tenTSTB, String loaiTSTB, String statusTSTB, String soluongTSTB, String dvt, String ghichu){
        try{
            String insert = "INSERT INTO TaiSanThietBi(MaNCC, TenTSTB, LoaiTSTB, TinhTrangTSTB, SoLuongTSTB, DVT, GhiChu) VALUES(?,?,?,?,?,?,?)";
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
    
    public void updateTSTB(String loaiTSTB, String soluongTSTB, String statusTSTB, String DVT, String maTSTB, String maNCC){
        try{
            String update = "UPDATE TaiSanThietBi SET LoaiTSTB = ?, SoLuongTSTB = ? , TinhTrangTSTB = ?, DVT = ? WHERE MaTSTB = ? AND MaNCC = ?";
            conn = cn.getConnection();
            PreparedStatement pst2 = conn.prepareStatement(update);
            pst2.setString(1, loaiTSTB);
            pst2.setString(2, soluongTSTB);
            pst2.setString(3, statusTSTB);
            pst2.setString(4, DVT);
            pst2.setString(5, maTSTB);
            pst2.setString(6, maNCC);
            pst2.execute();
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    
    private void saveDNHMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_saveDNHMouseClicked
        if(CODE.equals("VIEW") == true){
            JOptionPane.showMessageDialog(this, "Bạn đang ở chế độ xem, nhấn nút thêm hoặc chỉnh sửa để chuyển chế độ");
            return;
        }
        String tenNCC = nccBox.getSelectedItem().toString().trim();
        String tenNV = nvBox.getSelectedItem().toString().trim();
        java.sql.Date ngayNhap = new java.sql.Date(ngaynhapBox.getDate().getTime());
        String ghichu = ghichuTxt.getText().trim();
        String tenTSTB = tstbText.getText().trim();
        if(categoryBox.getSelectedIndex() == -1){
            return;
        }
        String loaiTSTB = categoryBox.getSelectedItem().toString().trim();
        String soLuong = countTxt.getText().trim();
        String dvt = dvtText.getText().trim();
        String donGia = dongiaTxt.getText().trim();
        StringBuffer sb = new StringBuffer();
        if(tenTSTB.length() == 0 || soLuong.length() == 0 || dvt.length() == 0 || donGia.length() == 0){
            sb.append("Không được để trống thông tin!\n");
        }
        if(Validate.notNumber(soLuong) == true){
            sb.append("Số lượng phải là số!\n");
        }
        if(sb.length()>0){
            JOptionPane.showMessageDialog(this, sb.toString());
            return;
        }
        if( CODE.equals("INSERT") == true){
            String select_CTDNH = "Select * from ChiTietDonNhapHang";
            try{
                conn = cn.getConnection();
                PreparedStatement pst = conn.prepareStatement(select_CTDNH);
                ResultSet ctdnh = pst.executeQuery();
                while(ctdnh.next()){
                    int count = 0;
                    if(ctdnh.getString("MaDNH").trim().equals(ID) == true){
                        count++;
                    }
                    if(ctdnh.getString("MaTSTB").trim().equals(GetIDTSTBfromTen(tenTSTB)) == true){
                        count++;
                    }
                    if(count == 2){
                        sb.append("Chi Tiết Đơn Nhập Hàng này có thể đã tồn tại trong hôm nay hãy kiểm tra lại và cập nhật");
                    }
                }
            } catch(Exception e){
                e.printStackTrace();
            }
            if(sb.length()>0){
                JOptionPane.showMessageDialog(this, sb.toString());
                return;
            }
            if(GetIDTSTBfromTen(tenTSTB) == null){
                insertTSTB(GetIDNCCfromTen(tenNCC), tenTSTB, loaiTSTB, "Còn Hàng", soLuong, dvt, ghichu);
            }else{
                String maTSTB = GetIDTSTBfromTen(tenTSTB);
                String slSanCo = GetSoLuongTSTBfromID(maTSTB);
                String slHienTai = String.valueOf(Integer.parseInt(slSanCo) + Integer.parseInt(soLuong));
                updateTSTB( categoryBox.getSelectedItem().toString(), slHienTai, "Còn Hàng", dvt, maTSTB, GetIDNCCfromTen(tenNCC));
            }
            insertCTDNH(ID, GetIDTSTBfromTen(tenTSTB) , soLuong, donGia , dvt);
            int money = TongTien(maDNH);
            updateDNH(GetIDNCCfromTen(tenNCC), IDNV, String.valueOf(ngayNhap), String.valueOf(money), ghichu, ID);
            JOptionPane.showMessageDialog(this, "Đã Thêm Chi Tiết Nhập Hàng và Cập Nhật Số Lượng Thành Công");
            Reset();
        }
        if( CODE.equals("UPDATE") == true){
            if(GetIDTSTBfromTen(tenTSTB) == null){
                insertTSTB(GetIDNCCfromTen(tenNCC), tenTSTB, loaiTSTB, "Còn Hàng", soLuong, dvt, ghichu);
                insertCTDNH(ID, GetIDTSTBfromTen(tenTSTB) , soLuong, donGia , dvt);
            }else{
                String maTSTB = GetIDTSTBfromTen(tenTSTB);
                String slSanCo = GetSoLuongTSTBfromID(maTSTB);
                String slHienTai = String.valueOf(Integer.parseInt(slSanCo) + Integer.parseInt(soLuong) - Integer.parseInt(soLuongconst));
                if(Integer.valueOf(slHienTai) == 0){
                    updateTSTB( categoryBox.getSelectedItem().toString(), slHienTai, "Hết Hàng", dvt, maTSTB, GetIDNCCfromTen(tenNCC));
                }
                if(Integer.valueOf(slHienTai) > 0){
                    updateTSTB( categoryBox.getSelectedItem().toString(), slHienTai, "Còn Hàng",dvt, maTSTB, GetIDNCCfromTen(tenNCC));
                }
                if(Integer.valueOf(slHienTai) < 0){
                    JOptionPane.showMessageDialog(this, "Không thể cập nhật số lượng");
                    return;
                }
            }
            updateCTDNH(ID, GetIDTSTBfromTen(tenTSTB), soLuong, donGia, dvt);
            int money = TongTien(maDNH);
            updateDNH(GetIDNCCfromTen(tenNCC), IDNV, String.valueOf(ngayNhap), String.valueOf(money), ghichu, ID);
            JOptionPane.showMessageDialog(this, "Chỉnh Sửa Thông Tin Tài Sản Thiết Bị Thành Công");
            Reset();
        }
    }//GEN-LAST:event_saveDNHMouseClicked

    private void cancelButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cancelButtonMouseClicked
        this.dispose();
    }//GEN-LAST:event_cancelButtonMouseClicked

    private void moneyTxtKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_moneyTxtKeyReleased
        String TongTien = null;
        if(Validate.notNumber(moneyTxt.getText()) == true){
            JOptionPane.showMessageDialog(this, "Tổng Tiền phải là số");
            return;
        }
        if(moneyTxt.getText().length() <= 0){
            moneyLable.setText("");
            return;
        }
        TongTien = currencyFormatter.format(Double.parseDouble(moneyTxt.getText()));
        moneyLable.setText(String.valueOf(TongTien));  
    }//GEN-LAST:event_moneyTxtKeyReleased

    private void dongiaTxtKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_dongiaTxtKeyReleased
        String DonGia = null;
        Locale locale = new Locale("vn", "VN");      
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);
        if(Validate.notNumber(dongiaTxt.getText()) == true){
            JOptionPane.showMessageDialog(this, "Đơn giá phải là số");
            return;
        }
        if(dongiaTxt.getText().length() <= 0){
            costLable.setText("");
            return;
        }
        DonGia = currencyFormatter.format(Double.parseDouble(dongiaTxt.getText()));
        costLable.setText(String.valueOf(DonGia));  
    }//GEN-LAST:event_dongiaTxtKeyReleased

    public static void main(String args[]) {
        FlatLightLaf.setup();
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DetailInputStorage().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addDNH;
    private javax.swing.JButton cancelButton;
    private javax.swing.JComboBox<String> categoryBox;
    private javax.swing.JLabel costLable;
    private javax.swing.JTextField countTxt;
    private javax.swing.JTable ctdnhTable;
    private javax.swing.JTextField dongiaTxt;
    private javax.swing.JTextField dvtText;
    private javax.swing.JButton fixDNH;
    private javax.swing.JTextField ghichuTxt;
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
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel moneyLable;
    private javax.swing.JTextField moneyTxt;
    private javax.swing.JComboBox<String> nccBox;
    private com.toedter.calendar.JDateChooser ngaynhapBox;
    private javax.swing.JComboBox<String> nvBox;
    private javax.swing.JButton saveDNH;
    private javax.swing.JTextField searchTxt;
    private javax.swing.JComboBox<String> tstbBox;
    private javax.swing.JTextField tstbText;
    // End of variables declaration//GEN-END:variables
}
