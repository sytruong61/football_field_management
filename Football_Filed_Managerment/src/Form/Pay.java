package Form;

import DataBase.ConnectDB;
import com.sun.source.tree.ContinueTree;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.security.interfaces.RSAKey;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class Pay extends javax.swing.JInternalFrame {
    ConnectDB cn = new ConnectDB();
    Connection conn;
    Locale locale = new Locale("vn", "VN");      
    NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);
    String CODE = "PAY", maPDS = null, maKH = null, maHD = null, maSan = null, maKMConst = null;
    UserSession userSession = UserSession.getInstance();
    String maNV = userSession.getUserID();
    
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
    
    public static boolean isHoliday(LocalDate date) {
        int day = date.getDayOfMonth();
        int month = date.getMonthValue();
        int year = date.getYear();
        if ((day == 1 && month == 1) // New Year's Day
            || (day == 30 && month == 4 && year >= 1975) // Reunification Day
            || (day == 1 && month == 5) // Labor Day
            || (day == 2 && month == 9) // National Day
            || (day == 25 && month == 12) // Christmas Day
            ) {
                return true;
        } else {
            return false;
        }
  }
    
     public static boolean isWeekend(LocalDate date) {
    // Check if the date is a weekend in Vietnam (Saturday or Sunday)
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        if (dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY) {
            return true;
        } else {
            return false;
        }
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
            LocalTime currentTime = LocalTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
            gioRaTxt.setText(currentTime.format(formatter));
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
            tiensanTxt.setText(String.valueOf(Math.round(tongTien)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void GetThongTinKH(String maKH){
        try{    
            String select_KH = "Select * from KhachHang where SDT = ?";
            conn = cn.getConnection();
            PreparedStatement pst = conn.prepareStatement(select_KH);
            pst.setString(1, maKH);
            ResultSet kh = pst.executeQuery();
            if(kh.next()){
                sdtKH.setText(kh.getString("SDT"));
                tenKH.setText(kh.getString("HoTen"));
                String maHK = kh.getString("MAHK");
                String select_HK = "Select * from HangKhachHang where MAHK = ?";
                conn = cn.getConnection();
                PreparedStatement pst2 = conn.prepareStatement(select_HK);
                pst2.setString(1, maHK);
                ResultSet rs = pst2.executeQuery();
                if(rs.next()){
                    hangHK.setText(rs.getString("TenHK"));
                    ptkmKH.setText(rs.getString("PTKM"));
                    ptkmKH1.setText(rs.getString("PTKM"));
                }
            }
            Integer tongPTKM = 0;
            if(!ptkmMaTxt.getText().equals("")){
                tongPTKM = Integer.parseInt(ptkmKH.getText().trim()) + Integer.parseInt(ptkmMaTxt.getText().trim());
                tongPTKMTxt.setText(String.valueOf(tongPTKM));
            }else{
                tongPTKMTxt.setText(ptkmKH.getText().trim());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void SelectAllFB(String maHD){
        DefaultTableModel dm = (DefaultTableModel)fbTable.getModel();
        dm.getDataVector().removeAllElements();
        dm.fireTableDataChanged();
        DefaultTableCellRenderer stringRenderer = (DefaultTableCellRenderer)
        fbTable.getDefaultRenderer(String.class);
        stringRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        String select_CTHD = "Select * from ChiTietHoaDon WHERE MaHD = ?";
        try {
            conn = cn.getConnection();
            PreparedStatement pst = conn.prepareStatement(select_CTHD);
            pst.setString(1, maHD);
            ResultSet rs = pst.executeQuery();
            DefaultTableModel model = (DefaultTableModel) fbTable.getModel();
            String TENFB, SOLUONG, THANHTIEN;
            Integer tongTien = 0;
            while(rs.next()){
                if(GetLoaiDVfromID(rs.getString("MaDV")).equals("F&B")){
                    TENFB = GetDVfromID(rs.getString("MaDV"));
                    SOLUONG = rs.getString("SoLuong");
                    THANHTIEN = currencyFormatter.format(Double.parseDouble(rs.getString("TongTien")));
                    tongTien = tongTien + Integer.parseInt(rs.getString("TongTien"));
                    tienFBTxt.setText(String.valueOf(tongTien));
                    String[] cthd = { TENFB, SOLUONG, THANHTIEN };
                    model.addRow(cthd);
                }
            }
            tienFBTxt.setText(String.valueOf(tongTien));
            moneyLable2.setText(currencyFormatter.format(Double.parseDouble(tongTien.toString())));
            rs.close();
            pst.close();
            conn.close();
       } catch (Exception ex) {
          ex.printStackTrace();
       }
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
            String TEN, SOLUONG, SOLUONGDATRA, THANHTIEN, TienCoc, TienCocDaTra = null, SoLuongLamHong, TrangThai;
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
            tienthueTxt.setText(String.valueOf(tongTien));
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
            phuthuTxt.setText(String.valueOf(tongTien));
            moneyLable1.setText(currencyFormatter.format(Double.parseDouble(tongTien.toString())));
            rs.close();
            pst.close();
            conn.close();
       } catch (Exception ex) {
          ex.printStackTrace();
       }
    }
    
    public Pay() {
        initComponents();
        ImageIcon img = new ImageIcon("D:\\Football_Filed_Managerment\\src\\images\\icons/—Pngtree—football field plan_4710125 (1).png");
        this.setFrameIcon(img);
        SelectAllPDS();
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
                        SelectAllFB(maHD);
                        SelectAllCTPT(maHD);
                        SelectAllPlusCharge(maHD);
                        GetTongTienSan(maPDS);
                        GetThongTinKH(maKH);
                        Integer tongTienApDCKM = 0;
                        Float tienSan = Float.parseFloat(tiensanTxt.getText());
                        tongTienApDCKM = Math.round(tienSan) + Integer.parseInt(tienthueTxt.getText()) + Integer.parseInt(tienFBTxt.getText());
                        Integer phuThu = Integer.parseInt(phuthuTxt.getText());
                        Integer sumMoney = tongTienApDCKM + phuThu;
                        apdcKM.setText(String.valueOf(tongTienApDCKM));
                        tongTien.setText(String.valueOf(sumMoney));
                        Integer tienKM = tongTienApDCKM * Integer.parseInt(tongPTKMTxt.getText()) / 100;
                        tienKMTxt.setText(String.valueOf(tienKM));
                        remainTxt.setText(String.valueOf((Integer.parseInt(tongTien.getText())) - tienKM));
                    }
                    CODE = "INSERT";
                    maKMConst = null;
                    rs.close();
                    pst.close();
                    conn.close();
               } catch (Exception ex) {
                  ex.printStackTrace();
               }
            }
            
        });
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel19 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        pdsTable = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        moneyLable = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        ctptTable = new javax.swing.JTable();
        jLabel9 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        moneyLable1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        phuthuTable = new javax.swing.JTable();
        moneyLable2 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        fbTable = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
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
        jLabel4 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        fbfNameTxt = new javax.swing.JTextField();
        loaiSanTxt = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        sdtKH = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        tenKH = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        hangHK = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        ptkmKH = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        tiensanTxt = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        tienFBTxt = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        tienthueTxt = new javax.swing.JTextField();
        phuthuTxt = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        maKMtxt = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        ptkmKH1 = new javax.swing.JTextField();
        apMaKM = new javax.swing.JButton();
        ptkmMaTxt = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        tongPTKMTxt = new javax.swing.JTextField();
        jLabel27 = new javax.swing.JLabel();
        tienKMTxt = new javax.swing.JTextField();
        remain = new javax.swing.JLabel();
        remainTxt = new javax.swing.JTextField();
        PayBill = new javax.swing.JButton();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        apdcKM = new javax.swing.JTextField();
        jLabel30 = new javax.swing.JLabel();
        tongTien = new javax.swing.JTextField();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();

        setResizable(true);
        setTitle("Thanh Toán");

        jLabel19.setForeground(new java.awt.Color(41, 128, 185));
        jLabel19.setText("Sân Đang Hoạt Động");

        pdsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã Đặt Sân", "Số Điện Thoại KH", "Sân Bóng"
            }
        ));
        jScrollPane1.setViewportView(pdsTable);

        jLabel1.setForeground(new java.awt.Color(41, 128, 185));
        jLabel1.setText("Hóa Đơn Thuê");

        jLabel6.setForeground(new java.awt.Color(41, 128, 185));
        jLabel6.setText("Tổng Hóa Đơn Thuê");

        moneyLable.setForeground(new java.awt.Color(41, 128, 185));
        moneyLable.setText("                                    ");

        ctptTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Tên", "Số Lượng", "Số Lượng Đã Trả", "Thành Tiền", "Tiền Cọc", "Tiền Cọc Đã Trả", "Số Lượng Làm Hỏng / Mất", "Tình Trạng"
            }
        ));
        jScrollPane3.setViewportView(ctptTable);

        jLabel9.setForeground(new java.awt.Color(41, 128, 185));
        jLabel9.setText("Phụ Thu");

        jLabel20.setForeground(new java.awt.Color(41, 128, 185));
        jLabel20.setText("Tổng Phụ Thu");

        moneyLable1.setForeground(new java.awt.Color(41, 128, 185));
        moneyLable1.setText("                                    ");

        phuthuTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Tên Phụ Thu", "Số Lượng", "Thành Tiền"
            }
        ));
        jScrollPane2.setViewportView(phuthuTable);

        moneyLable2.setForeground(new java.awt.Color(41, 128, 185));
        moneyLable2.setText("                                    ");

        jLabel7.setForeground(new java.awt.Color(41, 128, 185));
        jLabel7.setText("Tổng Hóa Đơn F&B");

        fbTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Tên F&B", "Số Lượng", "Thành Tiền"
            }
        ));
        jScrollPane4.setViewportView(fbTable);

        jLabel2.setForeground(new java.awt.Color(41, 128, 185));
        jLabel2.setText("Hóa Đơn F&B");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Hóa Đơn Sân", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(41, 128, 185))); // NOI18N

        jLabel12.setForeground(new java.awt.Color(41, 128, 185));
        jLabel12.setText("Tổng Tiền Sân");

        jLabel10.setForeground(new java.awt.Color(41, 128, 185));
        jLabel10.setText("Chi Tiết Giá");

        chitietGia.setColumns(20);
        chitietGia.setRows(3);
        jScrollPane5.setViewportView(chitietGia);

        jLabel8.setForeground(new java.awt.Color(41, 128, 185));
        jLabel8.setText("Giờ Ra");

        jLabel5.setForeground(new java.awt.Color(41, 128, 185));
        jLabel5.setText("Giờ Vào");

        jLabel11.setForeground(new java.awt.Color(41, 128, 185));
        jLabel11.setText("Ngày");

        jLabel4.setForeground(new java.awt.Color(41, 128, 185));
        jLabel4.setText("Loại Sân");

        jLabel3.setForeground(new java.awt.Color(41, 128, 185));
        jLabel3.setText("Tên Sân");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(jLabel8)
                    .addComponent(jLabel10)
                    .addComponent(jLabel3)
                    .addComponent(jLabel11)
                    .addComponent(jLabel12))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(fbfNameTxt)
                    .addComponent(loaiSanTxt)
                    .addComponent(giovaoTxt)
                    .addComponent(gioRaTxt)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 175, Short.MAX_VALUE)
                    .addComponent(ngayTxt)
                    .addComponent(tongtienTxt))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(fbfNameTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
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
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(tongtienTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Thông Tin Khách Hàng", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(41, 128, 185))); // NOI18N

        jLabel13.setForeground(new java.awt.Color(41, 128, 185));
        jLabel13.setText("Số Điện Thoại KH");

        jLabel14.setForeground(new java.awt.Color(41, 128, 185));
        jLabel14.setText("Họ và Tên");

        jLabel15.setForeground(new java.awt.Color(41, 128, 185));
        jLabel15.setText("Hạng Khách");

        jLabel16.setForeground(new java.awt.Color(41, 128, 185));
        jLabel16.setText("% Khuyến Mãi Theo Hạng");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel16)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(ptkmKH))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel13)
                            .addComponent(jLabel14)
                            .addComponent(jLabel15))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(sdtKH, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(tenKH, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(hangHK, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(sdtKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(tenKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(hangHK, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(11, 11, 11)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(ptkmKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel17.setForeground(new java.awt.Color(41, 128, 185));
        jLabel17.setText("Tiền Sân");

        jLabel18.setForeground(new java.awt.Color(41, 128, 185));
        jLabel18.setText("Tiền F&B");

        jLabel21.setForeground(new java.awt.Color(41, 128, 185));
        jLabel21.setText("Tiền Thuê");

        jLabel22.setForeground(new java.awt.Color(41, 128, 185));
        jLabel22.setText("Phụ Thu");

        jLabel23.setForeground(new java.awt.Color(41, 128, 185));
        jLabel23.setText("Mã Khuyến Mại");

        jLabel24.setForeground(new java.awt.Color(41, 128, 185));
        jLabel24.setText("% Khuyến Mãi Theo Hạng");

        apMaKM.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/088-percent.png"))); // NOI18N
        apMaKM.setText("Áp Mã");
        apMaKM.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                apMaKMMouseClicked(evt);
            }
        });

        jLabel25.setForeground(new java.awt.Color(41, 128, 185));
        jLabel25.setText("% Khuyến Mại Mã");

        jLabel26.setForeground(new java.awt.Color(41, 128, 185));
        jLabel26.setText("Tổng % Khuyến Mại");

        jLabel27.setForeground(new java.awt.Color(41, 128, 185));
        jLabel27.setText("Tổng Tiền Khuyến Mại");

        remain.setForeground(new java.awt.Color(41, 128, 185));
        remain.setText("Còn Lại");

        PayBill.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/080-calculator.png"))); // NOI18N
        PayBill.setText("Thanh Toán");
        PayBill.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                PayBillMouseClicked(evt);
            }
        });

        jLabel29.setForeground(new java.awt.Color(41, 128, 185));
        jLabel29.setText("Tổng Tiền Áp Được KM");

        jLabel30.setForeground(new java.awt.Color(41, 128, 185));
        jLabel30.setText("Tổng Tiền ");

        jMenu1.setText("File");
        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");
        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel19)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel6)
                                .addGap(18, 18, 18)
                                .addComponent(moneyLable, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 922, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                        .addComponent(jLabel9)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel20)
                                        .addGap(18, 18, 18)
                                        .addComponent(moneyLable1, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 22, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel2)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel7)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(moneyLable2))
                                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabel21)
                                            .addComponent(jLabel22)
                                            .addComponent(jLabel18)
                                            .addComponent(jLabel17))
                                        .addGap(18, 18, 18)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(tiensanTxt, javax.swing.GroupLayout.DEFAULT_SIZE, 173, Short.MAX_VALUE)
                                            .addComponent(tienFBTxt)
                                            .addComponent(tienthueTxt)
                                            .addComponent(phuthuTxt)))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabel24)
                                            .addComponent(jLabel23)
                                            .addComponent(jLabel25)
                                            .addComponent(jLabel26)
                                            .addComponent(jLabel27)
                                            .addComponent(remain))
                                        .addGap(18, 18, 18)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(ptkmMaTxt)
                                            .addComponent(maKMtxt)
                                            .addComponent(ptkmKH1)
                                            .addComponent(tongPTKMTxt)
                                            .addComponent(tienKMTxt)
                                            .addComponent(remainTxt, javax.swing.GroupLayout.DEFAULT_SIZE, 173, Short.MAX_VALUE))))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addGap(0, 0, Short.MAX_VALUE)
                                                .addComponent(jLabel30)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(tongTien, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                                .addComponent(jLabel29)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(apdcKM)))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel28))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(apMaKM)
                                            .addComponent(PayBill))
                                        .addGap(0, 0, Short.MAX_VALUE)))))))
                .addContainerGap(34, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1)
                        .addComponent(jLabel6)
                        .addComponent(moneyLable))
                    .addComponent(jLabel19))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel7)
                                    .addComponent(moneyLable2))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel9)
                                    .addComponent(jLabel20)
                                    .addComponent(moneyLable1)
                                    .addComponent(jLabel2))
                                .addGap(11, 11, 11)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel17)
                            .addComponent(tiensanTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel18)
                            .addComponent(tienFBTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel21)
                            .addComponent(tienthueTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel29)
                            .addComponent(apdcKM, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel30)
                                .addComponent(tongTien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel22)
                                .addComponent(phuthuTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel28)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel24)
                            .addComponent(ptkmKH1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel23)
                            .addComponent(maKMtxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(apMaKM))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(ptkmMaTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel25))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel26)
                            .addComponent(tongPTKMTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel27)
                            .addComponent(tienKMTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(remain)
                            .addComponent(remainTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(PayBill))))
                .addGap(23, 23, 23))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void apMaKMMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_apMaKMMouseClicked
        try{    
            String select_KM = "Select * from MaKhuyenMai where MaKM = ?";
            conn = cn.getConnection();
            PreparedStatement pst = conn.prepareStatement(select_KM);
            pst.setString(1, maKMtxt.getText().trim());
            ResultSet km = pst.executeQuery();
            if(km.next()){
                LocalDate now = LocalDate.now();
                String dateString = km.getString("HanKM");
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");
                LocalDate date = LocalDate.parse(dateString, formatter);
                if (now.isAfter(date)) {
                    JOptionPane.showMessageDialog(this, "Mã Khuyến Mại Đã Hết Hạn");
                    return;
                } else{
                    ptkmMaTxt.setText(km.getString("PTKM"));
                    maKMConst = maKMtxt.getText().trim();
                }
            }else if (maKMConst!= null && maKMtxt.getText().equals("")){
                UpdateMaKM(null, null , maHD);
                JOptionPane.showMessageDialog(this, "Đã Hủy Áp Mã Khuyến Mãi"); 
                ptkmMaTxt.setText("");
                Integer tongPTKM = 0 + Integer.parseInt(ptkmKH1.getText().trim());
                tongPTKMTxt.setText(String.valueOf(tongPTKM));
                Integer tongTienApDCKM = Integer.parseInt(apdcKM.getText());
                Integer tienKM = tongTienApDCKM * Integer.parseInt(tongPTKMTxt.getText()) / 100;
                tienKMTxt.setText(String.valueOf(tienKM));
                remainTxt.setText(String.valueOf((Integer.parseInt(tongTien.getText())) - tienKM));
                return;
            }else{
                JOptionPane.showMessageDialog(this, "Không tìm thấy mã khuyến mại");
                return;
            }
            Integer tongPTKM = 0;
            tongPTKM = Integer.parseInt(km.getString("PTKM")) + Integer.parseInt(ptkmKH1.getText().trim());
            tongPTKMTxt.setText(String.valueOf(tongPTKM));
            Integer tongTienApDCKM = Integer.parseInt(apdcKM.getText());
            Integer tienKM = tongTienApDCKM * Integer.parseInt(tongPTKMTxt.getText()) / 100;
            tienKMTxt.setText(String.valueOf(tienKM));
            remainTxt.setText(String.valueOf((Integer.parseInt(tongTien.getText())) - tienKM));
            UpdateMaKM(maKMtxt.getText().trim(), String.valueOf(java.sql.Date.valueOf(java.time.LocalDate.now())) , maHD);
            JOptionPane.showMessageDialog(this, "Đã áp mã khuyến mại thành công");
            return;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_apMaKMMouseClicked

    public void UpdateMaKM(String maKM, String ngayLap, String maHD){
        try {
            String Update_KM = "Update HoaDon SET MaKM = ?, NgayLapHD = ? where MaHD = ?";
            conn = cn.getConnection();
            PreparedStatement pst = conn.prepareStatement(Update_KM);
            pst.setString(1, maKM);
            pst.setString(2, ngayLap);
            pst.setString(3, maHD);
            pst.execute();
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }
    
    public void UpdateFBF(String maSan){
        try {
            String Update_FBF = "Update SanBong SET TinhTrang = ? Where MaSan = ?";
            conn = cn.getConnection();
            PreparedStatement pst = conn.prepareStatement(Update_FBF);
            pst.setString(1, "Đang Trống");
            pst.setString(2, maSan);
            pst.execute();
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }
    
    public void UpdatePDS(String gioRa, String maPDS){
        try {
            String Update_PDS = "Update PhieuDatSan SET TrangThai = ?, GioRa = ? Where MaPhieuDat = ?";
            conn = cn.getConnection();
            PreparedStatement pst = conn.prepareStatement(Update_PDS);
            pst.setString(1, "Đã Hoàn Thành");
            pst.setString(2, gioRa);
            pst.setString(3, maPDS);
            pst.execute();
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }
    
    public void UpdateKH(Integer tichLuy, String maKH){
        try {
            Integer tichLuyNow = 0;
            String Select_KH = "Select * from KhachHang where SDT = ?";
            conn = cn.getConnection();
            PreparedStatement pst = conn.prepareStatement(Select_KH);
            pst.setString(1, maKH);
            ResultSet rs = pst.executeQuery();
            if(rs.next()){
                tichLuyNow = rs.getInt("TichLuy");
            }
            tichLuyNow = tichLuyNow + tichLuy;
            String Select_HK = "Select * from HangKhachHang where GhiChu != ?";
            conn = cn.getConnection();
            PreparedStatement pst1 = conn.prepareStatement(Select_HK);
            pst1.setString(1, "PAUSED");
            ResultSet hk = pst1.executeQuery();
            while(hk.next()){
                if(tichLuyNow >= hk.getInt("ChiTieu")){
                    String Update_KH = "Update KhachHang SET MAHK = ?, TichLuy = ? where SDT = ?";
                    conn = cn.getConnection();
                    PreparedStatement pst2 = conn.prepareStatement(Update_KH);
                    pst2.setString(1, hk.getString("MAHK"));
                    pst2.setInt(2, tichLuyNow);
                    pst2.setString(3, maKH);
                    pst2.execute();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }
    

    public void UpdateHD(LocalDateTime ngayLapHD, Integer tienSan, Integer tienDV, Integer phuPhi, Integer PTKMHK, Integer tongTien, String maNV, String maHD){
        try {
            String Update_HD = "Update HoaDon SET NgayLapHD = ?, TienSan = ?, TienDV = ?, PhuPhi = ?, PTKMHK = ?, TongTien = ?, MaNV = ? Where MaHD = ?";
            conn = cn.getConnection();
            PreparedStatement pst = conn.prepareStatement(Update_HD);
            pst.setTimestamp(1, Timestamp.valueOf(ngayLapHD));
            pst.setInt(2, tienSan);
            pst.setInt(3, tienDV);
            pst.setInt(4, phuPhi);
            pst.setInt(5, PTKMHK);
            pst.setInt(6, tongTien);
            pst.setString(7, maNV);
            pst.setString(8, maHD);
            pst.execute();
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }
        
    private void PayBillMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PayBillMouseClicked
        for(int i = 0; i < ctptTable.getRowCount(); i++){
            if(!ctptTable.getModel().getValueAt(i, 7).toString().equals("Đã Hoàn Thành")){
                JOptionPane.showMessageDialog(this, "Chưa Hoàn Thành Việc Trả Đồ Đã Thuê");
                return;
            }
        }
        UpdateFBF(maSan);
        UpdatePDS(gioRaTxt.getText().trim(),maPDS);
        LocalDateTime ngayLapHD = LocalDateTime.now();
        Integer tienDV = Integer.parseInt(tienFBTxt.getText().trim()) + Integer.parseInt(tienthueTxt.getText().trim());
        UpdateHD(ngayLapHD, Integer.parseInt(tiensanTxt.getText().trim()), tienDV, Integer.parseInt(phuthuTxt.getText().trim()), Integer.parseInt(ptkmKH1.getText().trim()), Integer.parseInt(remainTxt.getText().trim())  , maNV, maHD);
        JOptionPane.showMessageDialog(this, "Thanh Toán và Cập Nhật Thông Tin Thành Công");
        new Bill(maPDS).setVisible(true);
        UpdateKH(Integer.parseInt(remainTxt.getText().trim()), maKH);
        this.dispose();
        return;
    }//GEN-LAST:event_PayBillMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton PayBill;
    private javax.swing.JButton apMaKM;
    private javax.swing.JTextField apdcKM;
    private javax.swing.JTextArea chitietGia;
    private javax.swing.JTable ctptTable;
    private javax.swing.JTable fbTable;
    private javax.swing.JTextField fbfNameTxt;
    private javax.swing.JTextField gioRaTxt;
    private javax.swing.JTextField giovaoTxt;
    private javax.swing.JTextField hangHK;
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
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTextField loaiSanTxt;
    private javax.swing.JTextField maKMtxt;
    private javax.swing.JLabel moneyLable;
    private javax.swing.JLabel moneyLable1;
    private javax.swing.JLabel moneyLable2;
    private javax.swing.JTextField ngayTxt;
    private javax.swing.JTable pdsTable;
    private javax.swing.JTable phuthuTable;
    private javax.swing.JTextField phuthuTxt;
    private javax.swing.JTextField ptkmKH;
    private javax.swing.JTextField ptkmKH1;
    private javax.swing.JTextField ptkmMaTxt;
    private javax.swing.JLabel remain;
    private javax.swing.JTextField remainTxt;
    private javax.swing.JTextField sdtKH;
    private javax.swing.JTextField tenKH;
    private javax.swing.JTextField tienFBTxt;
    private javax.swing.JTextField tienKMTxt;
    private javax.swing.JTextField tiensanTxt;
    private javax.swing.JTextField tienthueTxt;
    private javax.swing.JTextField tongPTKMTxt;
    private javax.swing.JTextField tongTien;
    private javax.swing.JTextField tongtienTxt;
    // End of variables declaration//GEN-END:variables
}
