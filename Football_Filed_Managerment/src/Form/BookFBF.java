package Form;

import DataBase.ConnectDB;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.SpinnerListModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import jdk.dynalink.linker.GuardingTypeConverterFactory;

public class BookFBF extends javax.swing.JInternalFrame {
    ConnectDB cn = new ConnectDB();
    Connection conn;
    Locale locale = new Locale("vn", "VN");      
    NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);
    String CODE = "INSERT", maKH = null, maPDS = null, gioBDconst, gioKTconst, maFBF;
    UserSession userSession = UserSession.getInstance();
    String maNV = userSession.getUserID();
    Boolean khOK = true;
    LocalTime OPEN_TIME;
    LocalTime CLOSE_TIME;
    private static final Duration BOOKING_DURATION = Duration.ofMinutes(15);
    
    public void getOpenTime(){
        try {
            conn = cn.getConnection();
            String sql = "SELECT * FROM ThongTinSanBong";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()) {
                String gioMoCua = rs.getString("gioMoCua");
                String gioDongCua = rs.getString("gioDongCua");
                DateTimeFormatter inputFormat = DateTimeFormatter.ofPattern("HH:mm");
                OPEN_TIME = LocalTime.parse(gioMoCua, inputFormat);
                CLOSE_TIME = LocalTime.parse(gioDongCua, inputFormat);
            }
            // Đóng kết nối
            rs.close();
            stmt.close();
            conn.close();
       } catch (Exception ex) {
          ex.printStackTrace();
       }
    }
    
    public List<LocalTime> getEmptyTimeSlots(Connection conn, String maSan, String ngayDat) throws SQLException {
        List<LocalTime> emptyTimeSlots = new ArrayList<>();
        LocalTime startTime = OPEN_TIME;
        while (startTime.plus(BOOKING_DURATION).isBefore(CLOSE_TIME)) {
            emptyTimeSlots.add(startTime);
            startTime = startTime.plusMinutes(15);
        }
        String sql = "SELECT GioBD, GioKT FROM PhieuDatSan WHERE MaSan = ? AND CAST(GioBD AS DATE) = ? AND TrangThai != ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, maSan);
            stmt.setString(2, ngayDat);
            stmt.setString(3, "Đã Hủy");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                LocalTime bookedStartTime = rs.getTime("GioBD").toLocalTime();
                LocalTime bookedEndTime = rs.getTime("GioKT").toLocalTime();
                LocalDate bookedDate = rs.getDate("GioBD").toLocalDate();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate ngayCanDat = LocalDate.parse(ngayDat, formatter);
                Iterator<LocalTime> iterator = emptyTimeSlots.iterator();
                LocalTime currentTime = LocalTime.now();
                LocalDate today = LocalDate.now();
                while (iterator.hasNext()) {
                    LocalTime emptyStartTime = iterator.next();
                    if(bookedDate.equals(today) && emptyStartTime.isBefore(currentTime.minusMinutes(15))){
                        iterator.remove();
                        continue;
                    }
                    if (emptyStartTime.isAfter(bookedStartTime) && emptyStartTime.plus(BOOKING_DURATION).isBefore(bookedEndTime.plusMinutes(1))) {
                        iterator.remove();
                    }
                    if(emptyStartTime.equals(bookedStartTime) && emptyStartTime.plus(BOOKING_DURATION).isBefore(bookedEndTime.plusMinutes(1))){
                        iterator.remove();
                    }
                    if(emptyStartTime.isBefore(bookedStartTime) && emptyStartTime.plus(BOOKING_DURATION).isAfter(bookedStartTime) && emptyStartTime.plus(BOOKING_DURATION).isBefore(bookedEndTime)){
                        iterator.remove();
                    }
                    if(emptyStartTime.isBefore(bookedStartTime) && emptyStartTime.plusMinutes(60).isAfter(bookedStartTime) && emptyStartTime.plusMinutes(60).isBefore(bookedEndTime)){
                        iterator.remove();
                    }
                    if(emptyStartTime.plusMinutes(60).isAfter(CLOSE_TIME)){
                        iterator.remove();
                    }
                }
            }
            rs.close();
            stmt.close();
            conn.close();
        }
        
        return emptyTimeSlots;
    }
    
    public List<String> getBookedTimeSlots(String maSan, String ngayDat){
        List<String> bookedSlots = new ArrayList<>();
        try {
            conn = cn.getConnection();
            String sql = "SELECT GioBD, GioKT FROM PhieuDatSan WHERE MaSan = ? AND CAST(GioBD AS DATE) = ? AND TrangThai != ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, maSan); 
            stmt.setString(2, ngayDat);
            stmt.setString(3, "Đã Hủy");
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                String startTime = rs.getString("GioBD");
                String endTime = rs.getString("GioKT");
                bookedSlots.add(startTime + " - " + endTime); 
                System.out.println(startTime + " - " + endTime);
            }
            // Đóng kết nối
            rs.close();
            stmt.close();
            conn.close();
       } catch (Exception ex) {
          System.out.println("Error: " + ex.getMessage());
       }
       return bookedSlots;
    }
    
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
    
    public String GetIDHKfromTen(String tenHK){
        String maHK = null;
        String select_HK = "Select * from HangKhachHang where TenHK = ?";
        try{
            conn = cn.getConnection();
            PreparedStatement pst = conn.prepareStatement(select_HK);
            pst.setString(1, tenHK);
            ResultSet rs = pst.executeQuery();
            if(rs.next()){
                maHK = rs.getString("MAHK");
            }
        } catch(Exception e){
            e.printStackTrace();
        }
        return maHK;
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
    
    public void SetFBFBox(){
        fbfBox.removeAllItems();
        String select_FBF = "Select * from SanBong where TinhTrang != ?";
        try{
            conn = cn.getConnection();
            PreparedStatement pst = conn.prepareStatement(select_FBF);
            pst.setString(1, "Đang Bảo Trì");
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                fbfBox.addItem(rs.getString("TenSan"));
            }
            rs.close();
            pst.close();
            conn.close();
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public String GetIDFBFfromTen(String tenSan){
        String idSan = null;
        String select_FBFfromTen = "Select * from SanBong where TenSan = ?";
        try{
            conn = cn.getConnection();
            PreparedStatement pst = conn.prepareStatement(select_FBFfromTen);
            pst.setString(1, tenSan);
            ResultSet rs = pst.executeQuery();
            if(rs.next()){
               idSan = rs.getString("MaSan");
            }
            rs.close();
            pst.close();
            conn.close();
        } catch(Exception ex){
            ex.printStackTrace();
        }
        return idSan;
    }
    
    public String GetTrangThaiPDS(String maPDS){
        String status = null;
        String select_SatusFBF = "Select * from PhieuDatSan where MaPhieuDat = ?";
        try{
            conn = cn.getConnection();
            PreparedStatement pst = conn.prepareStatement(select_SatusFBF);
            pst.setString(1, maPDS);
            ResultSet rs = pst.executeQuery();
            if(rs.next()){
               status = rs.getString("TrangThai");
            }
            rs.close();
            pst.close();
            conn.close();
        } catch(Exception ex){
            ex.printStackTrace();
        }
        return status;
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
    
    public String covertTime(String date){
        String converted = null;
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");
        LocalTime time = LocalTime.parse(date, inputFormatter).withSecond(0).withNano(0);
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("HH:mm");
        converted = time.format(outputFormatter);
        return converted;
    }
    
    public static String convertToLocalTime(LocalTime localTime) {
        // Tạo đối tượng DateTimeFormatter với định dạng HH:mm
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        // Chuyển đổi thành string
        String timeString = localTime.format(formatter);
        return timeString;
    }
    
    public String covertDate(String dateSQL){
        String converted = null;
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");
        LocalDate date = LocalDate.parse(dateSQL, inputFormatter);
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        converted = date.format(outputFormatter);
        return converted;
    }
    
    public String dateToSQLDate(String ndate){
        String outputDate = null;
        String inputDate = ndate;
        SimpleDateFormat inputDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        SimpleDateFormat outputDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        try {
            Date date = inputDateFormat.parse(inputDate);
            outputDate = outputDateFormat.format(date);
            
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return outputDate;
    }
    
    public String randomPDS(){
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
    
    public void SelectALLPDS(){
        String select_PDS = "Select * from PhieuDatSan WHERE TrangThai = ? OR TrangThai = ? ";
        try {
            conn = cn.getConnection();
            PreparedStatement pst = conn.prepareStatement(select_PDS);
            pst.setString(1, "Đang Đặt");
            pst.setString(2, "Đã Nhận Sân");
            ResultSet rs = pst.executeQuery();
            DefaultTableModel model = (DefaultTableModel) pdsTable.getModel();
            String ID, SDTKH, SanBong, NGAYDAT, GioBD, GioKT,  NVLAP = null, TinhTrang, GioVao = null, GioRa = null;
            while(rs.next()){
                ID = rs.getString("MaPhieuDat");
                SDTKH = rs.getString("MAKH");
                SanBong = GetFBFfromID(rs.getString("MaSan"));
                NGAYDAT = covertDate(rs.getString("GioBD"));
                GioBD = covertTime(rs.getString("GioBD"));
                GioKT = covertTime(rs.getString("GioKT"));                    
                GioVao = rs.getString("GioVao");
                GioRa = rs.getString("GioRa");
                NVLAP = GetTenNVfromID(rs.getString("MaNV")); 
                TinhTrang = rs.getString("TrangThai");
                String[] pds = {ID, SDTKH, SanBong, NGAYDAT, GioBD, GioKT,  NVLAP, TinhTrang, GioVao, GioRa };
                model.addRow(pds);
            }
            rs.close();
            pst.close();
            conn.close();
       } catch (Exception ex) {
          System.out.println("Error: " + ex.getMessage());
       }
    }
    
    public void HuyPDSDaQuaHan(){
        String select_PDS = "Select * from PhieuDatSan WHERE TrangThai = ?";
        try {
            conn = cn.getConnection();
            PreparedStatement pst = conn.prepareStatement(select_PDS);
            pst.setString(1, "Đang Đặt");
            ResultSet rs = pst.executeQuery();
            String ID = null, NGAYDAT = null, GioBD = null;
            while(rs.next()){
                ID = rs.getString("MaPhieuDat");
                NGAYDAT = covertDate(rs.getString("GioBD"));
                GioBD = covertTime(rs.getString("GioBD"));
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                LocalDate bookDate = LocalDate.parse(NGAYDAT, formatter);
                DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("HH:mm");
                LocalTime bookTime = LocalTime.parse(GioBD, formatter2);
                LocalDate today = LocalDate.now();
                LocalTime now = LocalTime.now();
                if(bookDate.isBefore(today)){
                    huyPDS(ID);
                    continue;
                }else if ( bookDate.isEqual(today) && now.isAfter(bookTime.plusHours(2)) ){
                    huyPDS(ID);
                    continue;
                }
            }
            rs.close();
            pst.close();
            conn.close();
       } catch (Exception ex) {
          System.out.println("Error: " + ex.getMessage());
       }
    }
    
    public BookFBF() {
        initComponents();     
        getOpenTime();
        SetHKBox();
        SetFBFBox();
        HuyPDSDaQuaHan();
        SelectALLPDS();
        hkBox.setEnabled(false);
        ImageIcon img = new ImageIcon("D:\\Football_Filed_Managerment\\src\\images\\icons/—Pngtree—football field plan_4710125 (1).png");
        this.setFrameIcon(img);
        ColumnsAutoSizer.sizeColumnsToFit(pdsTable);
        fbfBox.setSelectedIndex(-1);
        ngayDat.setDate(java.sql.Date.valueOf(java.time.LocalDate.now()));
        hkBox.setSelectedIndex(-1);
        khgtBox.setSelectedIndex(-1);
        hkBox.setEnabled(false);
        pdsTable.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent mouseEvent) {
                JTable table =(JTable) mouseEvent.getSource();
                java.awt.Point point = mouseEvent.getPoint();
                int row = table.rowAtPoint(point);
                if(row == -1){
                    return;
                }
                maPDS = pdsTable.getModel().getValueAt(row, 0).toString();
                maKH = pdsTable.getModel().getValueAt(row, 1).toString();
                String sanBong = pdsTable.getModel().getValueAt(row, 2).toString();
                String bookDate = pdsTable.getModel().getValueAt(row, 3).toString();
                String gioBD = pdsTable.getModel().getValueAt(row, 4).toString();
                gioBDconst = gioBD;
                String gioKT = pdsTable.getModel().getValueAt(row, 5).toString();
                gioKTconst = gioKT;
                if (mouseEvent.getClickCount() == 1 && table.getSelectedRow() != -1) {
                    try {
                        conn = cn.getConnection();
                        String select_1KH = "Select * from KhachHang where SDT = ?";
                        PreparedStatement pst3 = conn.prepareStatement(select_1KH);
                        pst3.setString(1, maKH);
                        ResultSet kh = pst3.executeQuery();
                        if(kh.next()){
                            sdtkhTxt.setText(maKH);
                            khnameTxt.setText(kh.getString("HoTen"));
                            khgtBox.setSelectedItem(kh.getString("GioiTinh"));
                            hkBox.setSelectedItem(GetTenHKfromID(kh.getString("MAHK")));
                            ghichuTxt.setText(kh.getString("GhiChu"));
                        }
                        fbfBox.setSelectedItem(sanBong);
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                        Date date = dateFormat.parse(bookDate);
                        ngayDat.setDate(date);
                        LocalTime timeBD = LocalTime.parse(gioBD);
                        if(timeBD.getHour() < 10){
                            bdHour.setValue("0" +String.valueOf(timeBD.getHour()));
                        }else{
                            bdHour.setValue(String.valueOf(timeBD.getHour()));
                        }
                        if(timeBD.getMinute() == 15){
                            bdMinute.setValue("15");
                        }else if(timeBD.getMinute() == 30 ){
                            bdMinute.setValue("30");
                        }else if(timeBD.getMinute() == 45){
                            bdMinute.setValue("45");
                        }else{
                            bdMinute.setValue("00");
                        }
                        LocalTime timeKT = LocalTime.parse(gioKT);
                        if(timeKT.getHour() < 10){
                            ktHour.setValue("0" +String.valueOf(timeKT.getHour()));
                        }else{
                            ktHour.setValue(String.valueOf(timeKT.getHour()));
                        }
                        if(timeKT.getMinute() == 15){
                            ktMinute.setValue("15");
                        }else if(timeKT.getMinute() == 30){
                            ktMinute.setValue("30");
                        } else if(timeKT.getMinute() == 45){
                            ktMinute.setValue("45");
                        } else {
                            ktMinute.setValue("00");
                        }
                        CODE = "UPDATE";
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                } 
            }
        });
    }
    
    public void Reset2(){
        SetHKBox();
        SetFBFBox();
        DefaultTableModel dm = (DefaultTableModel)pdsTable.getModel();
        dm.getDataVector().removeAllElements();
        dm.fireTableDataChanged(); 
        SelectALLPDS();
        ColumnsAutoSizer.sizeColumnsToFit(pdsTable);
        sdtkhTxt.setText("");
        khnameTxt.setText("");
        hkBox.setSelectedIndex(0);
        khgtBox.setSelectedIndex(-1); 
        ghichuTxt.setText("");
        fbfBox.setSelectedIndex(-1);
        loaisanBox.setText("");
        giaBS.setText("");
        giaBT.setText("");
        giaCT.setText("");
        giaNL.setText("");
        gioTrong.setText("");
        ngayDat.setDate(java.sql.Date.valueOf(java.time.LocalDate.now()));
        bdHour.setValue("00");
        bdMinute.setValue("00");
        ktHour.setValue("00");
        ktMinute.setValue("00");
        hkBox.setEnabled(false);
        CODE = "INSERT";
        maKH = null;
        maPDS = null;
    }
    
    public void Reset(){ 
        String maSan = null;
        try{
            String select_FBFfromTen = "Select * from SanBong where TenSan = ? AND TinhTrang != ?";
            conn = cn.getConnection();
            PreparedStatement pst = conn.prepareStatement(select_FBFfromTen);
            pst.setString(1, fbfBox.getSelectedItem().toString().trim());
            pst.setString(2, "Đang Bảo Trì");
            ResultSet rs = pst.executeQuery();
            if(rs.next()){
                maSan = rs.getString("MaSan");
                loaisanBox.setText(rs.getString("LoaiSan"));
                giaBS.setText(currencyFormatter.format(Double.parseDouble(rs.getString("GiaTruoc17H"))));
                giaBT.setText(currencyFormatter.format(Double.parseDouble(rs.getString("GiaSau17H"))));
                giaCT.setText(currencyFormatter.format(Double.parseDouble(rs.getString("GiaNgayLe"))));
                giaNL.setText(currencyFormatter.format(Double.parseDouble(rs.getString("GiaCuoiTuan"))));
            }
            // Cập nhật Slot còn trống
            Date ngaydattemp = ngayDat.getDate();
            SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
            String ngaydat = outputFormat.format(ngaydattemp);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate ngayCanDat = LocalDate.parse(ngaydat, formatter);
            List<LocalTime> emptyTimeSlots = getEmptyTimeSlots(conn, maSan, ngaydat);
            String gioTrong = "";
            int count = 1;
            LocalTime currentTime = LocalTime.now();
            LocalDate today = LocalDate.now();
            Iterator<LocalTime> itr = emptyTimeSlots.iterator();
            while (itr.hasNext()) {
                LocalTime startTime = itr.next();
                if(ngayCanDat.equals(today) && startTime.isBefore(currentTime.minusMinutes(15))){
                    itr.remove();
                    continue;
                }
                gioTrong = gioTrong + startTime.format(DateTimeFormatter.ofPattern("HH:mm ")) + "  -   ";
                count++;
                if(count == 8){
                    gioTrong = gioTrong + "\n";
                    count = 1;
                }
            }
            this.gioTrong.setText(gioTrong);
            emptyTimeSlots.clear();
            rs.close();
            pst.close();
            conn.close();
        } catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        khnameTxt = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        khgtBox = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        sdtkhTxt = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        hkBox = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        ghichuTxt = new javax.swing.JTextField();
        searchKH = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        fbfBox = new javax.swing.JComboBox<>();
        ktMinute = new javax.swing.JSpinner();
        ktHour = new javax.swing.JSpinner();
        jLabel7 = new javax.swing.JLabel();
        bdHour = new javax.swing.JSpinner();
        jLabel8 = new javax.swing.JLabel();
        bdMinute = new javax.swing.JSpinner();
        ngayDat = new com.toedter.calendar.JDateChooser();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        loaisanBox = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        gioTrong = new javax.swing.JTextArea();
        jLabel14 = new javax.swing.JLabel();
        giaBS = new javax.swing.JLabel();
        giaBT = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        giaCT = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        giaNL = new javax.swing.JLabel();
        bookButton = new javax.swing.JButton();
        closeButton = new javax.swing.JButton();
        savePDS = new javax.swing.JButton();
        huyPDS = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        pdsTable = new javax.swing.JTable();
        searchTxt = new javax.swing.JTextField();
        reciveFBF = new javax.swing.JButton();

        setTitle("Đặt Sân");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Thông Tin Khách Hàng", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(41, 128, 185))); // NOI18N

        jLabel2.setForeground(new java.awt.Color(41, 128, 185));
        jLabel2.setText("Họ Và Tên");

        khnameTxt.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(41, 128, 185), 1, true));
        khnameTxt.setPreferredSize(new java.awt.Dimension(0, 21));

        jLabel3.setForeground(new java.awt.Color(41, 128, 185));
        jLabel3.setText("Giới Tính");

        khgtBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Nam", "Nữ", "Khác" }));
        khgtBox.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(41, 128, 185), 1, true));
        khgtBox.setPreferredSize(new java.awt.Dimension(0, 21));

        jLabel4.setForeground(new java.awt.Color(41, 128, 185));
        jLabel4.setText("SDT");

        sdtkhTxt.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(41, 128, 185), 1, true));
        sdtkhTxt.setPreferredSize(new java.awt.Dimension(0, 21));

        jLabel5.setForeground(new java.awt.Color(41, 128, 185));
        jLabel5.setText("Hạng Khách");

        hkBox.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(41, 128, 185), 1, true));
        hkBox.setPreferredSize(new java.awt.Dimension(0, 21));

        jLabel6.setForeground(new java.awt.Color(41, 128, 185));
        jLabel6.setText("Ghi Chú");

        ghichuTxt.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(41, 128, 185), 1, true));
        ghichuTxt.setPreferredSize(new java.awt.Dimension(0, 21));

        searchKH.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/324-search.png"))); // NOI18N
        searchKH.setText("Tìm Kiếm");
        searchKH.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                searchKHMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(hkBox, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel6)
                        .addGap(18, 18, 18)
                        .addComponent(ghichuTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel4))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(khnameTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(sdtkhTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(searchKH)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(khgtBox, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(32, 32, 32))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(sdtkhTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(searchKH))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(khnameTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(khgtBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(hkBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(ghichuTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Thông Tin Sân Bóng", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(41, 128, 185))); // NOI18N

        jLabel1.setForeground(new java.awt.Color(41, 128, 185));
        jLabel1.setText("Sân Bóng");

        fbfBox.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(41, 128, 185), 1, true));
        fbfBox.setPreferredSize(new java.awt.Dimension(0, 21));
        fbfBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fbfBoxActionPerformed(evt);
            }
        });

        ktMinute.setModel(new javax.swing.SpinnerListModel(new String[] {"00", "15", "30", "45"}));

        ktHour.setModel(new javax.swing.SpinnerListModel(new String[] {"00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23"}));

        jLabel7.setText(" : ");

        bdHour.setModel(new javax.swing.SpinnerListModel(new String[] {"00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23"}));

        jLabel8.setText(" : ");

        bdMinute.setModel(new javax.swing.SpinnerListModel(new String[] {"00", "15", "30", "45"}));
        bdMinute.setToolTipText("");

        ngayDat.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(41, 128, 185), 1, true));
        ngayDat.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                ngayDatPropertyChange(evt);
            }
        });

        jLabel9.setForeground(new java.awt.Color(41, 128, 185));
        jLabel9.setText("Ngày ");

        jLabel10.setForeground(new java.awt.Color(41, 128, 185));
        jLabel10.setText("Giờ Bắt Đầu");

        jLabel11.setForeground(new java.awt.Color(41, 128, 185));
        jLabel11.setText("Giờ Kết Thúc");

        jLabel12.setForeground(new java.awt.Color(41, 128, 185));
        jLabel12.setText("Loại Sân");

        loaisanBox.setToolTipText("");
        loaisanBox.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(41, 128, 185), 1, true));
        loaisanBox.setPreferredSize(new java.awt.Dimension(0, 21));

        jLabel13.setForeground(new java.awt.Color(41, 128, 185));
        jLabel13.setText("Giờ Phù Hợp");

        gioTrong.setColumns(20);
        gioTrong.setRows(5);
        jScrollPane1.setViewportView(gioTrong);

        jLabel14.setForeground(new java.awt.Color(41, 128, 185));
        jLabel14.setText("Giá Trước 17H");

        giaBS.setText("   ");

        giaBT.setText("   ");

        jLabel15.setForeground(new java.awt.Color(41, 128, 185));
        jLabel15.setText("Giá Sau 17H");

        jLabel16.setForeground(new java.awt.Color(41, 128, 185));
        jLabel16.setText("Giá Cuối Tuần");

        giaCT.setText("   ");

        jLabel17.setForeground(new java.awt.Color(41, 128, 185));
        jLabel17.setText("Giá Ngày Lễ");

        giaNL.setText("   ");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel10)
                .addGap(11, 11, 11)
                .addComponent(bdHour, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel8)
                .addGap(14, 14, 14)
                .addComponent(bdMinute, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 29, Short.MAX_VALUE)
                .addComponent(jLabel11)
                .addGap(8, 8, 8)
                .addComponent(ktHour, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel7)
                .addGap(14, 14, 14)
                .addComponent(ktMinute, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel16)
                        .addGap(18, 18, 18)
                        .addComponent(giaCT, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel17)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(giaNL, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel13)
                        .addGap(21, 21, 21)
                        .addComponent(jScrollPane1))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel14)
                        .addGap(18, 18, 18)
                        .addComponent(giaBS, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel15)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(giaBT, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel9))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(ngayDat, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(fbfBox, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel12)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(loaisanBox, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(ngayDat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(fbfBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel12)
                    .addComponent(loaisanBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(giaBS)
                    .addComponent(jLabel15)
                    .addComponent(giaBT)
                    .addComponent(jLabel14))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(giaCT, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(giaNL)
                        .addComponent(jLabel16)
                        .addComponent(jLabel17)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bdMinute, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bdHour, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addComponent(jLabel10)
                    .addComponent(ktMinute, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ktHour, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(jLabel11))
                .addContainerGap())
        );

        bookButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/073-add.png"))); // NOI18N
        bookButton.setText("Đặt Sân");
        bookButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bookButtonMouseClicked(evt);
            }
        });

        closeButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/058-error.png"))); // NOI18N
        closeButton.setText("Đóng");
        closeButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                closeButtonMouseClicked(evt);
            }
        });

        savePDS.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/022-save.png"))); // NOI18N
        savePDS.setText("Lưu");
        savePDS.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                savePDSMouseClicked(evt);
            }
        });

        huyPDS.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/243-garbage.png"))); // NOI18N
        huyPDS.setText("Hủy Lịch");
        huyPDS.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                huyPDSMouseClicked(evt);
            }
        });

        pdsTable.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(41, 128, 185), 1, true));
        pdsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã Đặt Sân", "SDT Khách Đặt", "Sân Bóng", "Ngày Đặt", "Giờ Bắt Đầu ( Đặt)", "Giờ Kết Thúc ( Đặt)", "Nhân Viên Lập", "Tình Trạng", "Giờ Vào", "Giờ Ra"
            }
        ));
        jScrollPane2.setViewportView(pdsTable);

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

        reciveFBF.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/—Pngtree—football field plan_4710125 (1).png"))); // NOI18N
        reciveFBF.setText("Nhận Sân");
        reciveFBF.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                reciveFBFMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 855, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(searchTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(reciveFBF)
                        .addGap(60, 60, 60)
                        .addComponent(huyPDS)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(bookButton)
                                .addGap(120, 120, 120)
                                .addComponent(savePDS)
                                .addGap(105, 105, 105)
                                .addComponent(closeButton))
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(22, 22, 22))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(bookButton)
                            .addComponent(savePDS)
                            .addComponent(closeButton)))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(searchTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(reciveFBF)
                            .addComponent(huyPDS))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(31, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void searchKHMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_searchKHMouseClicked
        if(Validate.notNumber(sdtkhTxt.getText().trim()) == true){
            JOptionPane.showMessageDialog(this, "Số điện thoại chỉ được chứa số");
            return;
        }
        String select_KHfromID = "Select * from KhachHang where SDT = ?";
        try{
            conn = cn.getConnection();
            PreparedStatement pst = conn.prepareStatement(select_KHfromID);
            pst.setString(1, sdtkhTxt.getText().trim());
            ResultSet rs = pst.executeQuery();
            if(rs.next()){
                maKH = sdtkhTxt.getText().trim();
                khnameTxt.setText(rs.getString("HoTen"));
                khgtBox.setSelectedItem(rs.getString("GioiTinh"));
                hkBox.setSelectedItem(GetTenHKfromID(rs.getString("MAHK")));
                ghichuTxt.setText(rs.getString("GhiChu"));
                khOK = true;
                rs.close();
                pst.close();
                conn.close();
            }else{
                JOptionPane.showMessageDialog(this, "Không tìm thấy khách hàng có số điện thoại này");
                khOK = false;
                khnameTxt.setText("");
                khgtBox.setSelectedItem(-1);
                ghichuTxt.setText("");
                hkBox.setSelectedItem("Đồng");
            }
        } catch(Exception ex){
            ex.printStackTrace();
        }
    }//GEN-LAST:event_searchKHMouseClicked
    
    public boolean isBookingTimeValid(LocalTime startTime, LocalTime endTime, String maSan, String ngayDat, LocalTime startCST, LocalTime endCST) throws ClassNotFoundException {
        List<String> bookedTimeSlots = getBookedTimeSlots(maSan, ngayDat);
        for (String bookedTimeSlot : bookedTimeSlots) {        
            String[] timeSlot = bookedTimeSlot.split(" - ");
            LocalTime bookedStartTime = LocalDateTime.parse(timeSlot[0], DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S")).toLocalTime();
            LocalTime bookedEndTime = LocalDateTime.parse(timeSlot[1], DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S")).toLocalTime();        
            if(CODE.equals("UPDATE")){
                if (bookedStartTime.equals(startCST) && bookedEndTime.equals(endCST)) {
                    continue;
                }
            }
            // Kiểm tra trường hợp 1: Thời gian người dùng nhập vào không nằm hoàn toàn trong khoảng thời gian đã đặt.
            if (startTime.equals(bookedStartTime) || endTime.equals(bookedEndTime)) {
                return false;
            } else if (startTime.isAfter(bookedStartTime) && endTime.isBefore(bookedEndTime)) {
                return false;
            } else if (startTime.isBefore(bookedStartTime) && endTime.isAfter(bookedStartTime) && endTime.isBefore(bookedEndTime)) {
                return false;
            } else if (startTime.isAfter(bookedStartTime) && startTime.isBefore(bookedEndTime) && endTime.isAfter(bookedEndTime)) {
                return false;
            }
        }
        
        return true; // Giờ đặt hợp lệ
     }
    
    private void fbfBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fbfBoxActionPerformed
        if(fbfBox.getSelectedIndex() != -1 && ngayDat.getDate()!= null){
            Reset();
        }
    }//GEN-LAST:event_fbfBoxActionPerformed

    public void insertPDS(String maPDS, String sdtKH, String maNV, String maSan, String gioBD, String gioKT, String status ){
        try{
            String insert = "INSERT INTO PhieuDatSan(MaPhieuDat, MAKH, MaNV, MaSan, GioBD, GioKT, TrangThai) VALUES(?,?,?,?,?,?,?)";
            conn = cn.getConnection();
            PreparedStatement pst2 = conn.prepareStatement(insert);
            pst2.setString(1, maPDS);
            pst2.setString(2, sdtKH);
            pst2.setString(3, maNV);
            pst2.setString(4, maSan);
            pst2.setString(5, gioBD);
            pst2.setString(6, gioKT);
            pst2.setString(7, status);
            pst2.execute();
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void updatePDS(String maPDS, String maSan, String gioBD, String gioKT, String status ){
        try{
            String update = "UPDATE PhieuDatSan SET MaSan = ?, GioBD = ?, GioKT = ?, TrangThai = ? WHERE MaPhieuDat = ?";
            conn = cn.getConnection();
            PreparedStatement pst2 = conn.prepareStatement(update);
            pst2.setString(1, maSan);
            pst2.setString(2, gioBD);
            pst2.setString(3, gioKT);
            pst2.setString(4, status);
            pst2.setString(5, maPDS);
            pst2.execute();
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void huyPDS(String maPDS){
        try{
            String update = "UPDATE PhieuDatSan SET TrangThai = ? WHERE MaPhieuDat = ?";
            conn = cn.getConnection();
            PreparedStatement pst2 = conn.prepareStatement(update);
            pst2.setString(1, "Đã Hủy");
            pst2.setString(2, maPDS);
            pst2.execute();
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void recivePDS(String maPDS, String gioVao){
        try{
            String update = "UPDATE PhieuDatSan SET TrangThai = ?, GioVao = ? WHERE MaPhieuDat = ?";
            conn = cn.getConnection();
            PreparedStatement pst2 = conn.prepareStatement(update);
            pst2.setString(1, "Đã Nhận Sân");
            pst2.setString(2, gioVao);
            pst2.setString(3, maPDS);
            pst2.execute();
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void insertKH(String maKH, String hoTen, String gioiTinh, String maHK, String tichLuy, String tinhtrang, String ghiChu){
        try{
            String insert = "INSERT INTO KhachHang(SDT, HoTen, GioiTinh, MAHK, TichLuy, TinhTrang, GhiChu) VALUES(?,?,?,?,?,?,?)";
            conn = cn.getConnection();
            PreparedStatement pst2 = conn.prepareStatement(insert);
            pst2.setString(1, maKH);
            pst2.setString(2, hoTen);
            pst2.setString(3, gioiTinh);
            pst2.setString(4, maHK);
            pst2.setString(5, tichLuy);
            pst2.setString(6, tinhtrang);
            pst2.setString(7, ghiChu);
            pst2.execute();
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void updateDNH(String maNCC, String maNV, String ngayNhap, String ghichu, String maDNH){
        try{
            String update = "UPDATE DonNhapHang SET MaNCC = ?, MaNV = ?, NgayNhap = ?, GhiChu = ? WHERE MaDNH = ?";
            conn = cn.getConnection();
            PreparedStatement pst2 = conn.prepareStatement(update);
            pst2.setString(1, maNCC);
            pst2.setString(2, maNV);
            pst2.setString(3, ngayNhap);
            pst2.setString(4, ghichu);
            pst2.setString(5, maDNH);
            pst2.execute();
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void insertHD(String maPDS){
        try{
            String insert = "INSERT INTO HoaDon(MaPhieuDat) VALUES(?)";
            conn = cn.getConnection();
            PreparedStatement pst2 = conn.prepareStatement(insert);
            pst2.setString(1, maPDS);
            pst2.execute();
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    
    private void bookButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bookButtonMouseClicked
        Reset2();
    }//GEN-LAST:event_bookButtonMouseClicked

    private void ngayDatPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_ngayDatPropertyChange
        Date date = ngayDat.getDate();
        LocalDate currentDate = LocalDate.now();
        // Lấy ngày từ JDateChooser
        Instant instant = date.toInstant();
        LocalDate selectedDate = instant.atZone(ZoneId.systemDefault()).toLocalDate();
        // Kiểm tra xem ngày lấy có nằm trong tương lai hay không
        if (selectedDate.isBefore(currentDate)) {
            JOptionPane.showMessageDialog(this, "Ngày đã chọn nằm trong quá khứ!");
            ngayDat.setDate(java.sql.Date.valueOf(java.time.LocalDate.now()));
            return;
        }
        if (selectedDate.isAfter(currentDate.plusDays(7))) {
            JOptionPane.showMessageDialog(this, "Chỉ được đặt trước 7 ngày!");
            ngayDat.setDate(java.sql.Date.valueOf(java.time.LocalDate.now()));
            return;
        }
        SetFBFBox();
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
        String ngaydat = outputFormat.format(date);
        String select_FBF = "Select * from BaoTri where NgayBatDau <= ? AND NgayKetThuc >= ?";
        try{
            conn = cn.getConnection();
            PreparedStatement pst = conn.prepareStatement(select_FBF);
            pst.setString(1, ngaydat);
            pst.setString(2, ngaydat);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
               fbfBox.removeItem(GetFBFfromID(rs.getString("MaSan")));
            }
            rs.close();
            pst.close();
            conn.close();
        } catch(Exception ex){
            ex.printStackTrace();
        }
        if(fbfBox.getSelectedIndex() != -1 && ngayDat.getDate()!= null){
            Reset();
        }
    }//GEN-LAST:event_ngayDatPropertyChange
 
    private void savePDSMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_savePDSMouseClicked
        String statusPDS = "Đang Đặt";
        try {
            if(maPDS != null){
                if(GetTrangThaiPDS(maPDS).equals("Đã Nhận Sân")){
                    int result = JOptionPane.showConfirmDialog(null, "Phiếu này đã nhận sân", "Xác nhận chỉnh sửa lịch đặt", JOptionPane.YES_NO_OPTION);
                    if (result == JOptionPane.YES_OPTION) {
                        CODE = "UPDATE";
                        statusPDS = "Đã Nhận Sân";
                    } else {
                        JOptionPane.showMessageDialog(this, "Lịch đặt vẫn được giữ nguyên.");
                        return;
                    }
                }
            }
            String sdtKH = sdtkhTxt.getText().trim();
            String tenKH = khnameTxt.getText().trim();
            if(khgtBox.getSelectedIndex() == -1 || hkBox.getSelectedIndex() == -1 || fbfBox.getSelectedIndex() == -1 || sdtKH.length() == 0 || tenKH.length() == 0){
                JOptionPane.showMessageDialog(this, "Thông tin không được để trống \n");
                return;
            }
            String gtKH = khgtBox.getSelectedItem().toString().trim();
            String hangKH = hkBox.getSelectedItem().toString().trim();
            String tenSan = fbfBox.getSelectedItem().toString().trim();
            String ghichu = ghichuTxt.getText().trim();
            Date ngaydattemp = ngayDat.getDate();
            SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
            String ngaydat = outputFormat.format(ngaydattemp);
            
            String sBookTime = ngaydat + " " + bdHour.getValue().toString() + ":" + bdMinute.getValue().toString();
            String eBookTime = ngaydat + " " + ktHour.getValue().toString() + ":" + ktMinute.getValue().toString();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm", locale);
            LocalTime startBT = LocalTime.parse(sBookTime, formatter).truncatedTo(ChronoUnit.MINUTES);
            LocalTime endBT = LocalTime.parse(eBookTime, formatter).truncatedTo(ChronoUnit.MINUTES);
            LocalDate localDate = ngaydattemp.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDateTime localDateTime = LocalDateTime.of(localDate, startBT);
            if(CODE.equals("UPDATE")){
                String sBTconst = ngaydat + " " + gioBDconst;
                String sKTconst = ngaydat + " " + gioKTconst;
                LocalTime startCST = LocalTime.parse(sBTconst, formatter).truncatedTo(ChronoUnit.MINUTES);
                LocalTime endCST = LocalTime.parse(sKTconst, formatter).truncatedTo(ChronoUnit.MINUTES);            
                if(isBookingTimeValid(startBT, endBT, GetIDFBFfromTen(fbfBox.getSelectedItem().toString()), ngaydat, startCST, endCST) == false){
                    JOptionPane.showMessageDialog(this, "Khung giờ này đã được đặt vui lòng kiểm tra lại \n");
                    return;
                }
            }
            StringBuffer sb = new StringBuffer();
            if(startBT.isAfter(endBT) || endBT.isBefore(startBT)){
                JOptionPane.showMessageDialog(this, "Giờ bắt đầu phải trước giờ kết thúc \n");
                return;
            }
            if(localDateTime.isBefore(LocalDateTime.now().minusMinutes(15))){
                JOptionPane.showMessageDialog(this, "Ngày và giờ đặt không được nằm ở quá khứ \n" );
                return;
            }
            if(startBT.isBefore(OPEN_TIME) || endBT.isAfter(CLOSE_TIME) || Duration.between(startBT, CLOSE_TIME).toHours() < 1){
                JOptionPane.showMessageDialog(this, "Giờ đặt phải sau giờ mở cửa và trước giờ đóng cửa ít nhất 1 tiếng \n" );
                return;
            }
            if(CODE.equals("INSERT")) {
                if(isBookingTimeValid(startBT, endBT, GetIDFBFfromTen(fbfBox.getSelectedItem().toString()), ngaydat, null, null) == false){
                    JOptionPane.showMessageDialog(this, "Khung giờ này đã được đặt vui lòng kiểm tra lại \n");
                    return;
                }
            }
            if(Duration.between(startBT, endBT).toHours() < 1){
                JOptionPane.showMessageDialog(this, "Khoảng thời gian đặt phải ít nhất là 1 tiếng \n");
                return;
            }                
            String gioBDSQL = dateToSQLDate(sBookTime);
            String gioKTSQL = dateToSQLDate(eBookTime);
            if(CODE.equals("INSERT")){
                if(khOK == false){
                    insertKH( sdtKH.trim(), tenKH, gtKH, GetIDHKfromTen(hangKH), "0", "Còn Sử Dụng", ghichu);
                    hkBox.setEnabled(true);
                }
                String maPDS = randomPDS();
                insertPDS(maPDS, sdtKH, maNV, GetIDFBFfromTen(tenSan), gioBDSQL, gioKTSQL , "Đang Đặt");
            }
            if(CODE.equals("UPDATE")){
                updatePDS(maPDS, GetIDFBFfromTen(tenSan), gioBDSQL, gioKTSQL, statusPDS);

            }                
            SelectALLPDS();
            Reset2();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }//GEN-LAST:event_savePDSMouseClicked

    private void searchTxtMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_searchTxtMouseClicked
        searchTxt.setText("");
    }//GEN-LAST:event_searchTxtMouseClicked

    public void filter(String query){
        DefaultTableModel dm = (DefaultTableModel) pdsTable.getModel();
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<DefaultTableModel>(dm);
        pdsTable.setRowSorter(tr);
        tr.setRowFilter(RowFilter.regexFilter(query));
    }
    
    private void searchTxtKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchTxtKeyReleased
        String query = searchTxt.getText().trim();
        filter(query);
    }//GEN-LAST:event_searchTxtKeyReleased

    private void closeButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_closeButtonMouseClicked
        this.dispose();
    }//GEN-LAST:event_closeButtonMouseClicked

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
        
    private void reciveFBFMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_reciveFBFMouseClicked
        if(pdsTable.getSelectionModel().isSelectionEmpty()){
            JOptionPane.showMessageDialog(this, "Vui Lòng chọn phiếu đặt sân cần nhận");
            return;
        }
        if(maPDS != null){
            if(GetTrangThaiPDS(maPDS).equals("Đã Nhận Sân")){
                JOptionPane.showMessageDialog(this, "Phiếu đặt sân này đã nhận sân \n");
                return;
            }
        }
        String select_PDSfromID = "Select * from PhieuDatSan where MaPhieuDat = ?";
        String NGAYDAT = null, GioBD = null, TinhTrang = null;
        try{
            conn = cn.getConnection();
            PreparedStatement pst = conn.prepareStatement(select_PDSfromID);
            pst.setString(1, maPDS);
            ResultSet rs = pst.executeQuery();
            if(rs.next()){
                maFBF = rs.getString("MaSan");
                NGAYDAT = rs.getString("GioBD");
                GioBD = rs.getString("GioBD");
                TinhTrang = rs.getString("TrangThai");
            }
            rs.close();
            pst.close();
            conn.close();
        } catch(Exception ex){
            ex.printStackTrace();
        }
        LocalTime gioVao = LocalTime.now();
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");
        LocalDateTime localNgayGioDate = LocalDateTime.parse(NGAYDAT, formatter);
        LocalDate dateDat = localNgayGioDate.toLocalDate();
        LocalTime timeDat = localNgayGioDate.toLocalTime();
//        if(GetFBFfromID(maFBF).equals("Đang Trống") == false){
//            JOptionPane.showMessageDialog(this, "Sân Đang Có Người Thuê. \n");
//            return;
//        }
        if(!TinhTrang.equals("Đang Đặt")){
            JOptionPane.showMessageDialog(this, "Phiếu đặt sân có thể đã bị hủy, đã nhận hoặc đã hoàn thành. \n");
            return;
        }
        if(!dateDat.isEqual(today) || timeDat.isAfter(gioVao.plusMinutes(15))){
            JOptionPane.showMessageDialog(this, "Nhận sân quá sớm \n");
            return;
        }
        if( dateDat.isEqual(today) && timeDat.isBefore(gioVao.minusHours(1))){
            JOptionPane.showMessageDialog(this, "Đã quá giờ nhận sân \n");
            return;
        }
        if( dateDat.isBefore(today) ){
            JOptionPane.showMessageDialog(this, "Đã quá giờ nhận sân \n");
            return;
        }
        recivePDS(maPDS, String.valueOf(convertToLocalTime(gioVao)));
        updateFBF("Đang Có Người Thuê", maFBF);
        insertHD(maPDS);
        JOptionPane.showMessageDialog(this, "Đã cập nhật lại phiếu đặt sân và tạo hóa đơn mới\n");
        Reset();
        Reset2();
        return;
    }//GEN-LAST:event_reciveFBFMouseClicked

    public String GetStatusFBF(String maSan){
        String TinhTrang = null;
        String select_FBF = "Select * from SanBong where MaSan = ?";
        try{
            conn = cn.getConnection();
            PreparedStatement pst = conn.prepareStatement(select_FBF);
            pst.setString(1, maSan);
            ResultSet rs = pst.executeQuery();
            if(rs.next()){
                TinhTrang = rs.getString("TinhTrang");
            }
            rs.close();
            pst.close();
            conn.close();
        } catch(Exception ex){
            ex.printStackTrace();
        }
        return TinhTrang;
    }
    
    private void huyPDSMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_huyPDSMouseClicked
        if(pdsTable.getSelectionModel().isSelectionEmpty()){
            JOptionPane.showMessageDialog(this, "Vui Lòng chọn phiếu đặt sân cần hủy");
            return;
        }
        if(maPDS != null){
            if(GetTrangThaiPDS(maPDS).equals("Đã Nhận Sân")){
                JOptionPane.showMessageDialog(this, "Phiếu đặt sân này đã nhận sân \n");
                return;
            }
        }
        int result = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn hủy lịch đặt này?", "Xác nhận hủy lịch đặt", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            huyPDS(maPDS);
            JOptionPane.showMessageDialog(this, "Lịch đặt đã được hủy.");
        } else {
            JOptionPane.showMessageDialog(this, "Lịch đặt vẫn được giữ nguyên.");
        }
        Reset();
        Reset2();
    }//GEN-LAST:event_huyPDSMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JSpinner bdHour;
    private javax.swing.JSpinner bdMinute;
    private javax.swing.JButton bookButton;
    private javax.swing.JButton closeButton;
    private javax.swing.JComboBox<String> fbfBox;
    private javax.swing.JTextField ghichuTxt;
    private javax.swing.JLabel giaBS;
    private javax.swing.JLabel giaBT;
    private javax.swing.JLabel giaCT;
    private javax.swing.JLabel giaNL;
    private javax.swing.JTextArea gioTrong;
    private javax.swing.JComboBox<String> hkBox;
    private javax.swing.JButton huyPDS;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
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
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JComboBox<String> khgtBox;
    private javax.swing.JTextField khnameTxt;
    private javax.swing.JSpinner ktHour;
    private javax.swing.JSpinner ktMinute;
    private javax.swing.JTextField loaisanBox;
    private com.toedter.calendar.JDateChooser ngayDat;
    private javax.swing.JTable pdsTable;
    private javax.swing.JButton reciveFBF;
    private javax.swing.JButton savePDS;
    private javax.swing.JTextField sdtkhTxt;
    private javax.swing.JButton searchKH;
    private javax.swing.JTextField searchTxt;
    // End of variables declaration//GEN-END:variables
}
