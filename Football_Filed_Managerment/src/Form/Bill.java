package Form;

import DataBase.ConnectDB;
import static Form.Pay.isHoliday;
import static Form.Pay.isWeekend;
import com.formdev.flatlaf.FlatLightLaf;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import com.itextpdf.text.Anchor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.text.DecimalFormat;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;



public class Bill extends javax.swing.JFrame {

    ConnectDB cn = new ConnectDB();
    Connection conn;
    Locale locale = new Locale("vn", "VN");      
    NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);
    String maSan = null, maHD = null, maPDS = null, maKH = null;
    Integer tongPTKM = 0, tongTien = 0, tongTienApKM = 0, phuThu = 0;
    
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
    
    public String GetDVTDVfromID(String maDV){
        String dvtDV = "";
        try{    
            String select_DV = "Select * from DichVu where MaDV = ?";
            conn = cn.getConnection();
            PreparedStatement pst = conn.prepareStatement(select_DV);
            pst.setString(1, maDV);
            ResultSet dv = pst.executeQuery();
            if(dv.next()){
                dvtDV = dv.getString("DVT");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dvtDV;
    }
    
    public void GetTongTienSan(String maPDS){
        Integer giaBS = 0, giaBT = 0, giaCT = 0, giaNL = 0;
        String giaBScv = null, giaBTcv = null, giaCTcv = null, giaNLcv = null;
        long minutesBefore5pm, minutesAfter5pm;
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        SimpleDateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy");
        try{    
            String select_FBF = "Select * from PhieuDatSan where MaPhieuDat = ?";
            conn = cn.getConnection();
            PreparedStatement pst = conn.prepareStatement(select_FBF);
            pst.setString(1, maPDS);
            ResultSet fbf = pst.executeQuery();
            if(fbf.next()){
                maSan = fbf.getString("MaSan");
                Date date = inputFormat.parse(fbf.getString("GioBD"));
                ngayTxt.setText(outputFormat.format(date));
                giovaoTxt.setText(fbf.getString("GioVao"));
                gioRaTxt.setText(fbf.getString("GioRa"));
            }
            String select_1FBF = "Select * from SanBong where MaSan = ?";
            conn = cn.getConnection();
            PreparedStatement pst1 = conn.prepareStatement(select_1FBF);
            pst1.setString(1, maSan);
            ResultSet fbfv = pst1.executeQuery();
            if(fbfv.next()){
                fbfNameTxt.setText(fbfv.getString("TenSan"));
                loaiSanTxt.setText(fbfv.getString("LoaiSan"));
            giaBS = Integer.parseInt(fbfv.getString("GiaTruoc17H"));
            giaBScv = currencyFormatter.format(Double.parseDouble(fbfv.getString("GiaTruoc17H")));
            giaBT = Integer.parseInt(fbfv.getString("GiaSau17H"));
            giaBTcv = currencyFormatter.format(Double.parseDouble(fbfv.getString("GiaSau17H")));
            giaCT = Integer.parseInt(fbfv.getString("GiaCuoiTuan"));
            giaCTcv = currencyFormatter.format(Double.parseDouble(fbfv.getString("GiaCuoiTuan")));
            giaNL = Integer.parseInt(fbfv.getString("GiaNgayLe"));
            giaNLcv = currencyFormatter.format(Double.parseDouble(fbfv.getString("GiaNgayLe")));  
            }
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            LocalDate date = LocalDate.parse(ngayTxt.getText(), dateFormatter);
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
            LocalTime startTime = LocalTime.parse(giovaoTxt.getText().trim(), timeFormatter);
            LocalTime endTime = LocalTime.parse(gioRaTxt.getText().trim(), timeFormatter);
            LocalTime fivePm = LocalTime.of(17, 0);
            if(startTime.isBefore(fivePm) && endTime.isAfter(fivePm)){
                minutesBefore5pm = Duration.between(startTime, fivePm).toMinutes();
                minutesAfter5pm = Duration.between(fivePm, endTime).toMinutes();
            }else if(startTime.isBefore(fivePm) && endTime.isBefore(fivePm)){
                minutesBefore5pm = Duration.between(startTime, endTime).toMinutes();
                minutesAfter5pm = 0;
            }else{
                minutesBefore5pm = 0;
                minutesAfter5pm = Duration.between(startTime, endTime).toMinutes();
            }
            Float tongTien;
            DecimalFormat df = new DecimalFormat("#.#");
            String chitietTien = null;
            if(isHoliday(date)){
                tongTien = Float.parseFloat(df.format( Duration.between(startTime, endTime).toMinutes() / 60.0)) * giaNL;
                chitietTien = " Giá Ngày Lễ: " + String.valueOf(Float.parseFloat(df.format(Duration.between(startTime, endTime).toMinutes() / 60.0))) + " * " + giaNLcv + " = " + currencyFormatter.format(Double.parseDouble(String.valueOf(tongTien)));
            }else if(!isHoliday(date) && isWeekend(date)){
                tongTien = Float.parseFloat(df.format( Duration.between(startTime, endTime).toMinutes() / 60.0)) * giaCT;
                chitietTien = " Giá Cuối Tuần: " + String.valueOf(Float.parseFloat(df.format(Duration.between(startTime, endTime).toMinutes() / 60.0))) + " * " + giaCTcv + " = " + currencyFormatter.format(Double.parseDouble(String.valueOf(tongTien)));
            }else{
                tongTien = Float.parseFloat(df.format( minutesBefore5pm / 60.0)) * giaBS + Float.parseFloat(df.format(minutesAfter5pm / 60.0)) * giaCT;
                System.err.println(String.valueOf( tongTien ));
                chitietTien = " Giá Buổi Sáng: " + String.valueOf(Float.parseFloat(df.format(minutesBefore5pm / 60.0))) + " * " + giaBScv + " + " + "\n" 
                              + " Giá Buổi Chiều: " + String.valueOf(Float.parseFloat(df.format(minutesAfter5pm / 60.0))) + " * " + giaBTcv + " = " + "\n"
                              + " Tổng Tiền: " + currencyFormatter.format(Double.parseDouble(String.valueOf(tongTien)));
            }
            chitietGia.setText(chitietTien);
            tongtienTxt.setText(String.valueOf(Math.round(tongTien)));
            tongTienApKM = tongTienApKM + Math.round(tongTien);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public String GetPTKMfromMa(String maKM){
        String ptkm = "";
        try{    
            String select_KM = "Select * from MaKhuyenMai where MaKM = ?";
            conn = cn.getConnection();
            PreparedStatement pst = conn.prepareStatement(select_KM);
            pst.setString(1, maKM);
            ResultSet km = pst.executeQuery();
            if(km.next()){
                ptkm = km.getString("PTKM");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ptkm;
    }
    
    public void GetThongTinKH(String maKH){
        try{    
            String select_KH = "Select * from KhachHang where SDT = ?";
            conn = cn.getConnection();
            PreparedStatement pst = conn.prepareStatement(select_KH);
            pst.setString(1, maKH);
            ResultSet kh = pst.executeQuery();
            if(kh.next()){
                sdtKHLable.setText(kh.getString("SDT"));
                tenKHLabel.setText(kh.getString("HoTen"));
                String maHK = kh.getString("MAHK");
                String select_HK = "Select * from HangKhachHang where MAHK = ?";
                conn = cn.getConnection();
                PreparedStatement pst2 = conn.prepareStatement(select_HK);
                pst2.setString(1, maHK);
                ResultSet rs = pst2.executeQuery();
                if(rs.next()){
                    hangHKLable.setText(rs.getString("TenHK"));
                    PTKMHangLabel.setText(rs.getString("PTKM"));
                }
            }
            if(!PTKMMaLabel.getText().equals("")){
                tongPTKM = Integer.parseInt(PTKMHangLabel.getText().trim()) + Integer.parseInt(PTKMMaLabel.getText().trim());
                
            }else{
                tongPTKM = Integer.parseInt(PTKMHangLabel.getText().trim());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
     
    public void SelectAllFB(String maHD){
        String select_CTHD = "Select * from ChiTietHoaDon WHERE MaHD = ?";
        try {
            conn = cn.getConnection();
            PreparedStatement pst = conn.prepareStatement(select_CTHD);
            pst.setString(1, maHD);
            ResultSet rs = pst.executeQuery();
            DefaultTableModel model = (DefaultTableModel) hoaDonTable.getModel();
            String TENFB, SOLUONG, DVT, THANHTIEN = null;
            while(rs.next()){
                if(GetLoaiDVfromID(rs.getString("MaDV")).equals("F&B")){
                    TENFB = GetDVfromID(rs.getString("MaDV"));
                    SOLUONG = rs.getString("SoLuong");
                    DVT = GetDVTDVfromID(rs.getString("MaDV"));
                    if(rs.getString("TongTien") != null ){
                        THANHTIEN = currencyFormatter.format(Double.parseDouble(rs.getString("TongTien")));
                        tongTien = tongTien + Integer.parseInt(rs.getString("TongTien"));
                        tongTienApKM = tongTienApKM + + Integer.parseInt(rs.getString("TongTien"));
                    } 
                    String[] cthd = { TENFB, SOLUONG, DVT, THANHTIEN };
                    model.addRow(cthd);
                }
            }
            rs.close();
            pst.close();
            conn.close();
       } catch (Exception ex) {
          ex.printStackTrace();
       }
    }
    public String GetTenNVfromID(String maNV){
        String tenNV = null;
        String select_NV = "Select * from NhanVien where MaNV = ?";
        try{
            conn = cn.getConnection();
            PreparedStatement pst = conn.prepareStatement(select_NV);
            pst.setString(1, maNV);
            ResultSet rs = pst.executeQuery();
            if(rs.next()){
                tenNV = rs.getString("HoTen");
            }
        } catch(Exception e){
            e.printStackTrace();
        }
        return tenNV;
    }
    
    public void SelectAllCTPT(String maHD){
        String select_CTPT = "Select * from ChiTietPhieuThue WHERE MaHD = ?";
        try {
            conn = cn.getConnection();
            PreparedStatement pst = conn.prepareStatement(select_CTPT);
            pst.setString(1, maHD);
            ResultSet rs = pst.executeQuery();
            DefaultTableModel model = (DefaultTableModel) hoaDonTable.getModel();
            String TEN, SOLUONG, DVT = null, THANHTIEN = null;
            while(rs.next()){
                TEN = GetDVfromID(rs.getString("MaDV"));
                SOLUONG = rs.getString("SoLuong");
                DVT = GetDVTDVfromID(rs.getString("MaDV"));
                if(rs.getString("TongTien") != null ){
                        THANHTIEN = currencyFormatter.format(Double.parseDouble(rs.getString("TongTien")));
                        tongTien = tongTien + Integer.parseInt(rs.getString("TongTien"));
                        tongTienApKM = tongTienApKM +  Integer.parseInt(rs.getString("TongTien"));
                } 
                String[] cthd = { TEN, SOLUONG, DVT, THANHTIEN };
                model.addRow(cthd);
            }
            rs.close();
            pst.close();
            conn.close();
       } catch (Exception ex) {
          ex.printStackTrace();
       }
    }
    
    public void SelectAllPlusCharge(String maHD){
        String select_PC = "Select * from ChiTietHoaDon WHERE MaHD = ?";
        try {
            conn = cn.getConnection();
            PreparedStatement pst = conn.prepareStatement(select_PC);
            pst.setString(1, maHD);
            ResultSet rs = pst.executeQuery();
            DefaultTableModel model = (DefaultTableModel) hoaDonTable.getModel();
            String TEN = null, DVT, SOLUONG = null, THANHTIEN = null;
            Integer tongPlusCharge = 0;
            while(rs.next()){
                if(GetLoaiDVfromID(rs.getString("MaDV")).equals("Phụ Phí")){
                    TEN = GetDVfromID(rs.getString("MaDV"));
                    DVT = GetDVTDVfromID(rs.getString("MaDV"));
                    SOLUONG = rs.getString("SoLuong");
                    if(rs.getString("TongTien") != null ){
                        THANHTIEN = currencyFormatter.format(Double.parseDouble(rs.getString("TongTien")));
                        tongPlusCharge = tongPlusCharge + Integer.parseInt(rs.getString("TongTien"));
                        tongTien = tongTien + tongPlusCharge;
                    }                
                    String[] cthd = { TEN, SOLUONG, DVT, THANHTIEN };
                    model.addRow(cthd);
                }
            }
            phuthuLabel.setText(String.valueOf(tongPlusCharge));
            rs.close();
            pst.close();
            conn.close();
       } catch (Exception ex) {
          ex.printStackTrace();
       }
    }
    
    public Bill(){
        initComponents();
    }
    
    public Bill(String maPDS) {
        ConnectDB cn = new ConnectDB();
        this.maPDS = maPDS;
        //this.setUndecorated(true);
        //this.setLocationRelativeTo(null);
        initComponents();
        DefaultTableCellRenderer stringRenderer = (DefaultTableCellRenderer)
        hoaDonTable.getDefaultRenderer(String.class);
        stringRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        this.setResizable(false);
        ImageIcon img = new ImageIcon("D:\\Football_Filed_Managerment\\src\\images\\icons/—Pngtree—football field plan_4710125 (1).png");
        this.setIconImage(img.getImage());
        String select_PDS = "Select * from PhieuDatSan where MaPhieuDat = ?";
        try {
            conn = cn.getConnection();
            PreparedStatement pst = conn.prepareStatement(select_PDS);
            pst.setString(1, maPDS);
            ResultSet rs = pst.executeQuery();
            if(rs.next()){
                maKH = rs.getString("MAKH");
            }
            rs.close();
            pst.close();
            conn.close();
       } catch (Exception ex) {
          ex.printStackTrace();
       }
        String select_HD = "Select * from HoaDon WHERE MaPhieuDat = ?";
        try {
            conn = cn.getConnection();
            PreparedStatement pst = conn.prepareStatement(select_HD);
            pst.setString(1, maPDS);
            ResultSet rs = pst.executeQuery();
            if(rs.next()){
                maHD = rs.getString("MaHD");
                maHoaDonLabel.setText(maHD);
                nvLapLable.setText(GetTenNVfromID(rs.getString("MaNV")));
                ngayLapLabel.setText(rs.getString("NgayLapHD"));
                maKMLabel.setText(rs.getString("MaKM"));
                PTKMMaLabel.setText(GetPTKMfromMa(rs.getString("MaKM")));
                SelectAllFB(maHD);
                SelectAllCTPT(maHD);
                SelectAllPlusCharge(maHD);
                GetTongTienSan(maPDS);
                GetThongTinKH(maKH);
                tienDVlabel.setText(String.valueOf(tongTien));
                tienApKMLabel.setText(String.valueOf(tongTienApKM));
                Integer tongTienApDCKM = Integer.parseInt(tienApKMLabel.getText().trim());
                if(phuthuLabel.getText().equals(" ") == false){
                    phuThu = Integer.parseInt(phuthuLabel.getText());
                }
                Integer sumMoney = tongTienApDCKM + phuThu;
                tongTienLabel.setText(String.valueOf(sumMoney));
                Integer tienKM = tongTienApDCKM * tongPTKM / 100;
                tongTienKMLabel.setText(String.valueOf(tienKM));
                thanhToanLabel.setText(String.valueOf(sumMoney - tienKM));
            }
            rs.close();
            pst.close();
            conn.close();
            ColumnsAutoSizer.sizeColumnsToFit(hoaDonTable);
            tongtienTxt.setText(currencyFormatter.format(Double.parseDouble(String.valueOf(tongtienTxt.getText()))));
            if(tienDVlabel.getText() != null){
                tienDVlabel.setText(currencyFormatter.format(Double.parseDouble(String.valueOf(tienDVlabel.getText()))));
            }
            if(phuthuLabel.getText() != null){
                phuthuLabel.setText(currencyFormatter.format(Double.parseDouble(String.valueOf(phuthuLabel.getText()))));
            }
            tienApKMLabel.setText(currencyFormatter.format(Double.parseDouble(String.valueOf(tienApKMLabel.getText()))));
            tongTienLabel.setText(currencyFormatter.format(Double.parseDouble(String.valueOf(tongTienLabel.getText()))));
            tongTienKMLabel.setText(currencyFormatter.format(Double.parseDouble(String.valueOf(tongTienKMLabel.getText()))));
            thanhToanLabel.setText(currencyFormatter.format(Double.parseDouble(String.valueOf(thanhToanLabel.getText()))));
       } catch (Exception ex) {
          ex.printStackTrace();
       }
    }
    
    public void PrintFrameToPDF() {
        try {
            Document document = new Document(PageSize.A3);
            String dirPath = "D:\\Football_Filed_Managerment\\Bill";
            File dir = new File(dirPath);
            dir.mkdirs(); // tạo thư mục đích (nếu chưa tồn tại)
            String filename = dirPath + "\\" + maPDS + ".pdf";
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(filename));
            document.open();
            PdfContentByte cb = writer.getDirectContent();
            PdfTemplate template = cb.createTemplate(PageSize.A3.getWidth(), PageSize.A3.getHeight());
            Graphics2D g2d = template.createGraphicsShapes(PageSize.A3.getWidth(), PageSize.A3.getHeight());;
            this.print(g2d);
            cb.addTemplate(template, 0, 0);
            g2d.dispose();
            document.close();
            writer.close();
        } catch (Exception e) {
            System.out.println("ERROR: " + e.toString());
        }
    }

    public void XuatHoaDon(){
        BufferedImage screenshotImage = new BufferedImage(
        this.getBounds().width, this.getBounds().height,
        BufferedImage.TYPE_INT_RGB);
        this.paint(screenshotImage.getGraphics());
        try {
            String dirPath = "D:\\Football_Filed_Managerment\\Bill";
            File dir = new File(dirPath);
            dir.mkdirs(); // tạo thư mục đích (nếu chưa tồn tại)
            String filename = dirPath + "\\" + maPDS + ".png";;
            ImageIO.write(screenshotImage, "png", new File(filename));
        } catch (IOException ex) {
            System.err.println("ImageIsuues");
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        sdtKHLable = new javax.swing.JLabel();
        tenKHLabel = new javax.swing.JLabel();
        hangHKLable = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        maHoaDonLabel = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        nvLapLable = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        ngayLapLabel = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        tongtienTxt = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        chitietGia = new javax.swing.JTextArea();
        gioRaTxt = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        giovaoTxt = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        ngayTxt = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        fbfNameTxt = new javax.swing.JTextField();
        loaiSanTxt = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        hoaDonTable = new javax.swing.JTable();
        jLabel9 = new javax.swing.JLabel();
        tienDVlabel = new javax.swing.JLabel();
        phuthuLabel = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        tienApKMLabel = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        maKMLabel = new javax.swing.JLabel();
        PTKMMaLabel = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        tongTienKMLabel = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        tongTienLabel = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        thanhToanLabel = new javax.swing.JLabel();
        PTKMHangLabel = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        closeButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Hóa Đơn");
        setBackground(new java.awt.Color(255, 255, 255));
        setResizable(false);

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Thông Tin Khách Hàng", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(41, 128, 185))); // NOI18N

        jLabel13.setForeground(new java.awt.Color(41, 128, 185));
        jLabel13.setText("Số Điện Thoại KH");

        jLabel14.setForeground(new java.awt.Color(41, 128, 185));
        jLabel14.setText("Họ và Tên");

        jLabel15.setForeground(new java.awt.Color(41, 128, 185));
        jLabel15.setText("Hạng Khách");

        sdtKHLable.setText("                                             ");

        tenKHLabel.setText("                                             ");

        hangHKLable.setText("                                             ");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel13)
                    .addComponent(jLabel14)
                    .addComponent(jLabel15))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(sdtKHLable, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tenKHLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(hangHKLable, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(107, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(sdtKHLable))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(tenKHLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(hangHKLable))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(41, 128, 185));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("HÓA ĐƠN");

        jLabel2.setForeground(new java.awt.Color(41, 128, 185));
        jLabel2.setText("Mã Hóa Đơn: ");

        maHoaDonLabel.setForeground(new java.awt.Color(41, 128, 185));
        maHoaDonLabel.setText("                               ");

        jLabel3.setForeground(new java.awt.Color(41, 128, 185));
        jLabel3.setText("Nhân Viên Lập");

        nvLapLable.setForeground(new java.awt.Color(41, 128, 185));
        nvLapLable.setText("                                 ");

        jLabel4.setForeground(new java.awt.Color(41, 128, 185));
        jLabel4.setText("Ngày Lập");

        ngayLapLabel.setForeground(new java.awt.Color(41, 128, 185));
        ngayLapLabel.setText("                                ");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Hóa Đơn Sân", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(41, 128, 185))); // NOI18N

        jLabel12.setForeground(new java.awt.Color(41, 128, 185));
        jLabel12.setText("Tổng Tiền Sân");

        jLabel10.setForeground(new java.awt.Color(41, 128, 185));
        jLabel10.setText("Chi Tiết Giá");

        chitietGia.setColumns(20);
        chitietGia.setRows(5);
        jScrollPane5.setViewportView(chitietGia);

        jLabel8.setForeground(new java.awt.Color(41, 128, 185));
        jLabel8.setText("Giờ Ra");

        jLabel5.setForeground(new java.awt.Color(41, 128, 185));
        jLabel5.setText("Giờ Vào");

        jLabel11.setForeground(new java.awt.Color(41, 128, 185));
        jLabel11.setText("Ngày");

        jLabel6.setForeground(new java.awt.Color(41, 128, 185));
        jLabel6.setText("Loại Sân");

        jLabel7.setForeground(new java.awt.Color(41, 128, 185));
        jLabel7.setText("Tên Sân");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(jLabel5)
                    .addComponent(jLabel8)
                    .addComponent(jLabel10)
                    .addComponent(jLabel7)
                    .addComponent(jLabel11)
                    .addComponent(jLabel12))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(fbfNameTxt)
                    .addComponent(loaiSanTxt)
                    .addComponent(giovaoTxt)
                    .addComponent(gioRaTxt)
                    .addComponent(jScrollPane5)
                    .addComponent(ngayTxt)
                    .addComponent(tongtienTxt))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(fbfNameTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(loaiSanTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(ngayTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(11, 11, 11)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(giovaoTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(gioRaTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 69, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12)
                            .addComponent(tongtienTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        hoaDonTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Tên", "DVT", "Đơn Giá", "Thành Tiền"
            }
        ));
        jScrollPane1.setViewportView(hoaDonTable);

        jLabel9.setForeground(new java.awt.Color(41, 128, 185));
        jLabel9.setText("Tổng Tiền Dịch Vụ");

        tienDVlabel.setText("                                               ");

        phuthuLabel.setText(" ");

        jLabel16.setForeground(new java.awt.Color(41, 128, 185));
        jLabel16.setText("Phụ Thu");

        tienApKMLabel.setText(" ");

        jLabel17.setForeground(new java.awt.Color(41, 128, 185));
        jLabel17.setText("Tổng Tiền Áp Được KM");

        jLabel18.setForeground(new java.awt.Color(41, 128, 185));
        jLabel18.setText("% KM theo Hạng");

        jLabel19.setForeground(new java.awt.Color(41, 128, 185));
        jLabel19.setText("Mã KM");

        maKMLabel.setText(" ");

        PTKMMaLabel.setForeground(new java.awt.Color(41, 128, 185));
        PTKMMaLabel.setText("                     ");

        jLabel20.setForeground(new java.awt.Color(41, 128, 185));
        jLabel20.setText("%");

        jLabel21.setForeground(new java.awt.Color(41, 128, 185));
        jLabel21.setText("Tổng Tiền KM");

        tongTienKMLabel.setText(" ");

        jLabel22.setForeground(new java.awt.Color(41, 128, 185));
        jLabel22.setText("Tổng Tiền");

        tongTienLabel.setText(" ");

        jLabel23.setForeground(new java.awt.Color(41, 128, 185));
        jLabel23.setText("Thanh Toán");

        thanhToanLabel.setText(" ");

        PTKMHangLabel.setForeground(new java.awt.Color(41, 128, 185));
        PTKMHangLabel.setText("     ");

        jLabel24.setForeground(new java.awt.Color(41, 128, 185));
        jLabel24.setText("%");

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
                .addGap(13, 13, 13)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(maHoaDonLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(60, 60, 60)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(nvLapLable, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(63, 63, 63)
                        .addComponent(jLabel4)
                        .addGap(18, 18, 18)
                        .addComponent(ngayLapLabel)
                        .addContainerGap(86, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jLabel18)
                                        .addComponent(jLabel17)
                                        .addComponent(jLabel19)
                                        .addComponent(jLabel21)
                                        .addComponent(jLabel22)
                                        .addComponent(jLabel23))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabel9)
                                            .addComponent(jLabel16))))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(thanhToanLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(tongTienLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(maKMLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(PTKMMaLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(jLabel20))
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(PTKMHangLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(jLabel24))
                                            .addComponent(tongTienKMLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addComponent(tienApKMLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(phuthuLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(tienDVlabel, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(8, 8, 8))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(0, 114, Short.MAX_VALUE)
                                .addComponent(closeButton)
                                .addGap(128, 128, 128))))))
            .addGroup(layout.createSequentialGroup()
                .addGap(329, 329, 329)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(maHoaDonLabel)
                    .addComponent(jLabel3)
                    .addComponent(nvLapLable)
                    .addComponent(jLabel4)
                    .addComponent(ngayLapLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 427, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(tienDVlabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel16)
                            .addComponent(phuthuLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel17)
                            .addComponent(tienApKMLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel18)
                            .addComponent(PTKMHangLabel)
                            .addComponent(jLabel24))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel19)
                            .addComponent(maKMLabel)
                            .addComponent(PTKMMaLabel)
                            .addComponent(jLabel20))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel22)
                            .addComponent(tongTienLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel21)
                            .addComponent(tongTienKMLabel, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel23)
                            .addComponent(thanhToanLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(closeButton)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void closeButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_closeButtonMouseClicked
        closeButton.setVisible(false);
        XuatHoaDon();
        this.dispose();
    }//GEN-LAST:event_closeButtonMouseClicked

    public static void main(String args[]) {
        FlatLightLaf.setup();
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Bill().setVisible(true);
                
            }
        });

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel PTKMHangLabel;
    private javax.swing.JLabel PTKMMaLabel;
    private javax.swing.JTextArea chitietGia;
    private javax.swing.JButton closeButton;
    private javax.swing.JTextField fbfNameTxt;
    private javax.swing.JTextField gioRaTxt;
    private javax.swing.JTextField giovaoTxt;
    private javax.swing.JLabel hangHKLable;
    private javax.swing.JTable hoaDonTable;
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
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTextField loaiSanTxt;
    private javax.swing.JLabel maHoaDonLabel;
    private javax.swing.JLabel maKMLabel;
    private javax.swing.JLabel ngayLapLabel;
    private javax.swing.JTextField ngayTxt;
    private javax.swing.JLabel nvLapLable;
    private javax.swing.JLabel phuthuLabel;
    private javax.swing.JLabel sdtKHLable;
    private javax.swing.JLabel tenKHLabel;
    private javax.swing.JLabel thanhToanLabel;
    private javax.swing.JLabel tienApKMLabel;
    private javax.swing.JLabel tienDVlabel;
    private javax.swing.JLabel tongTienKMLabel;
    private javax.swing.JLabel tongTienLabel;
    private javax.swing.JTextField tongtienTxt;
    // End of variables declaration//GEN-END:variables
}
