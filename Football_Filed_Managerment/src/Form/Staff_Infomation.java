package Form;

import DataBase.ConnectDB;
import Form.Staff;
import com.formdev.flatlaf.FlatLightLaf;
import java.awt.Image;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class Staff_Infomation extends javax.swing.JFrame {
    String CODE,ID;
    ConnectDB cn = new ConnectDB();
    Connection conn;
    byte[] person_image=null;
 
    public String getBoPhanfromID(String ID) throws SQLException, ClassNotFoundException{
        String bophan = null;
        conn = cn.getConnection();
        String select_1BP = "Select * from BoPhan where MaBP = ?";
        PreparedStatement pst3 = conn.prepareStatement(select_1BP);        
        pst3.setString(1, ID);
        ResultSet bophanlist = pst3.executeQuery();
        while(bophanlist.next()){
            bophan = bophanlist.getString("TenBoPhan");
        }
        return bophan;
    }
    public String getChucVufromID(String ID) throws SQLException, ClassNotFoundException{
        String chucvu = null;
        conn = cn.getConnection();
        String select_1CV = "Select * from ChucVu where MaCV = ?";
        PreparedStatement pst3 = conn.prepareStatement(select_1CV);        
        pst3.setString(1, ID);
        ResultSet chucvulist = pst3.executeQuery();
        while(chucvulist.next()){
            chucvu = chucvulist.getString("TenChucVu");
        }
        return chucvu;
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
    
    public Staff_Infomation(String CODE, String ID) throws ClassNotFoundException, SQLException, ParseException, Exception {
        initComponents();
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        ImageIcon icon = new ImageIcon("D:\\Football_Filed_Managerment\\src\\images\\icons/—Pngtree—football field plan_4710125 (1).png");
        this.setIconImage(icon.getImage());
        try{
            conn = cn.getConnection();
            String select_BP = "Select * from BoPhan";
            PreparedStatement pst3 = conn.prepareStatement(select_BP);
            ResultSet bophan = pst3.executeQuery();
            while(bophan.next()){
                String bpName = bophan.getString("TenBoPhan");
                bophanBox.addItem(bpName);
            }
            String select_CV = "Select * from ChucVu";
            PreparedStatement pst4 = conn.prepareStatement(select_CV);
            ResultSet chucvu = pst4.executeQuery();
            while(chucvu.next()){
                String cvName = chucvu.getString("TenChucVu");
                chucvuBox.addItem(cvName);
            }
        } catch(Exception e){
            e.printStackTrace();
        }
        this.CODE = CODE;
        this.ID = ID;
        if(CODE.equals("VIEW") == true){
            conn = cn.getConnection();
            String select_1NV = "Select * from NhanVien where MaNV = ?";
            PreparedStatement pst3 = conn.prepareStatement(select_1NV);
            pst3.setString(1, this.ID);
            ResultSet nhanvien = pst3.executeQuery();
            System.out.println("Start");
            if(nhanvien.next()){
                hotenTxt.setText(nhanvien.getString("HoTen").trim());
                diachiTxt.setText(nhanvien.getString("DiaChi").trim());
                if(nhanvien.getString("CMND") != null){
                    cmndTxt.setText(Crypto.AES.decrypt(nhanvien.getString("CMND")));
                }
                sdtTxt.setText(nhanvien.getString("SDT").trim());
                emailTxt.setText(nhanvien.getString("Email").trim());
                ngaysinhTxt.setDate(java.sql.Date.valueOf(nhanvien.getString("NgaySinh")));
                gioitinhBox.setSelectedItem(nhanvien.getString("GioiTinh").trim());
                bophanBox.setSelectedItem(getBoPhanfromID(nhanvien.getString("BoPhan").trim()));
                chucvuBox.setSelectedItem(getChucVufromID(nhanvien.getString("ChucVu").trim()));
                statusBox.setSelectedItem(nhanvien.getString("TinhTrang").trim());
                ghichuTxt.setText(nhanvien.getString("GhiChu").trim());
                byte[] img = nhanvien.getBytes("Hinh");
                if(img != null){
                    ImageIcon imageicon = new ImageIcon( new ImageIcon(img).getImage().getScaledInstance(lbl_image.getWidth(), lbl_image.getHeight(), Image.SCALE_SMOOTH));
                    lbl_image.setIcon(imageicon); 
                }   
            }
            choosePicNV.setVisible(false);
            saveNV.setVisible(false);
            cancelButton.setText("Back");
        }
        if( CODE.equals("INSERT") == true){
            hotenTxt.setText("");
            diachiTxt.setText("");
            cmndTxt.setText("");
            sdtTxt.setText("");
            emailTxt.setText("");
            ngaysinhTxt.setDate(java.sql.Date.valueOf(java.time.LocalDate.now()));
        }
        if( CODE.equals("UPDATE") == true){
            conn = cn.getConnection();
            String select_1NV = "Select * from NhanVien where MaNV = ?";
            PreparedStatement pst3 = conn.prepareStatement(select_1NV);
            pst3.setString(1, this.ID);
            ResultSet nhanvien = pst3.executeQuery();
            System.out.println("Start");
            if(nhanvien.next()){
                hotenTxt.setText(nhanvien.getString("HoTen").trim());
                diachiTxt.setText(nhanvien.getString("DiaChi").trim());
                if(nhanvien.getString("CMND") != null){
                    cmndTxt.setText(Crypto.AES.decrypt(nhanvien.getString("CMND")));
                }
                sdtTxt.setText(nhanvien.getString("SDT").trim());
                emailTxt.setText(nhanvien.getString("Email").trim());
                ngaysinhTxt.setDate(java.sql.Date.valueOf(nhanvien.getString("NgaySinh")));
                gioitinhBox.setSelectedItem(nhanvien.getString("GioiTinh").trim());
                bophanBox.setSelectedItem(getBoPhanfromID(nhanvien.getString("BoPhan").trim()));
                chucvuBox.setSelectedItem(getChucVufromID(nhanvien.getString("ChucVu").trim()));
                statusBox.setSelectedItem(nhanvien.getString("TinhTrang").trim());
                if(nhanvien.getString("GhiChu") != null){
                    ghichuTxt.setText(nhanvien.getString("GhiChu").trim());
                }
                byte[] img = nhanvien.getBytes("Hinh");
                if(img != null){
                    ImageIcon imageicon = new ImageIcon( new ImageIcon(img).getImage().getScaledInstance(lbl_image.getWidth(), lbl_image.getHeight(), Image.SCALE_SMOOTH));
                    lbl_image.setIcon(imageicon); 
                    person_image = img;
                }   
            }
        }
        if( CODE.equals("UPDATE MS") == true){
            conn = cn.getConnection();
            String select_1NV = "Select * from NhanVien where MaNV = ?";
            PreparedStatement pst3 = conn.prepareStatement(select_1NV);
            pst3.setString(1, this.ID);
            ResultSet nhanvien = pst3.executeQuery();
            System.out.println("Start");
            if(nhanvien.next()){
                hotenTxt.setText(nhanvien.getString("HoTen").trim());
                diachiTxt.setText(nhanvien.getString("DiaChi").trim());
                if(nhanvien.getString("CMND") != null){
                    cmndTxt.setText(Crypto.AES.decrypt(nhanvien.getString("CMND")));
                }
                sdtTxt.setText(nhanvien.getString("SDT").trim());
                emailTxt.setText(nhanvien.getString("Email").trim());
                ngaysinhTxt.setDate(java.sql.Date.valueOf(nhanvien.getString("NgaySinh")));
                gioitinhBox.setSelectedItem(nhanvien.getString("GioiTinh").trim());
                bophanBox.setSelectedItem(getBoPhanfromID(nhanvien.getString("BoPhan").trim()));
                bophanBox.setEnabled(false);
                chucvuBox.setSelectedItem(getChucVufromID(nhanvien.getString("ChucVu").trim()));
                chucvuBox.setEnabled(false);
                statusBox.setVisible(false);
                ghichuTxt.setText(nhanvien.getString("GhiChu"));
                ghichuTxt.setVisible(false);
                jLabel12.setVisible(false);
                jLabel13.setVisible(false);
                byte[] img = nhanvien.getBytes("Hinh");
                if(img != null){
                    ImageIcon imageicon = new ImageIcon( new ImageIcon(img).getImage().getScaledInstance(lbl_image.getWidth(), lbl_image.getHeight(), Image.SCALE_SMOOTH));
                    lbl_image.setIcon(imageicon); 
                    person_image = img;
                }   
            }
        }
        
    }
    public Staff_Infomation() throws ClassNotFoundException, SQLException {
        initComponents();

    }

    public void insertNV(String hoten, String sdt, java.sql.Date ngaysinh, String gioitinh, String bophan, String chucvu, String diachi, String cmnd, String email, byte[] hinh, String tinhtrang, String ghichu){
        try{
            String insert = "INSERT INTO NhanVien(HoTen, NgaySinh, GioiTinh, BoPhan, ChucVu, DiaChi, CMND, SDT, Email, Hinh, TinhTrang, GhiChu) VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";
            conn = cn.getConnection();
            PreparedStatement pst2 = conn.prepareStatement(insert);
            pst2.setString(1, hoten);
            pst2.setDate(2, ngaysinh);
            pst2.setString(3, gioitinh);
            pst2.setString(4, bophan);
            pst2.setString(5, chucvu);
            pst2.setString(6, diachi);
            pst2.setString(7, cmnd);
            pst2.setString(8, sdt);
            pst2.setString(9, email);
            pst2.setBytes(10, hinh);
            pst2.setString(11, tinhtrang);
            pst2.setString(12, ghichu);
            pst2.execute();
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    public void updateNV(String hoten, String sdt, java.sql.Date ngaysinh, String gioitinh, String bophan, String chucvu, String diachi, String cmnd, String email, byte[] hinh, String tinhtrang, String ghichu, String maNV){
        try{
            String insert = "UPDATE NhanVien SET HoTen = ?, NgaySinh = ?, GioiTinh = ?, BoPhan = ?, ChucVu = ?, DiaChi = ?, CMND = ?, SDT = ?, Email = ?, Hinh = ?, TinhTrang = ?, GhiChu = ? WHERE MaNV = ?";
            conn = cn.getConnection();
            PreparedStatement pst2 = conn.prepareStatement(insert);
            pst2.setString(1, hoten);
            pst2.setDate(2, ngaysinh);
            pst2.setString(3, gioitinh);
            pst2.setString(4, bophan);
            pst2.setString(5, chucvu);
            pst2.setString(6, diachi);
            pst2.setString(7, cmnd);
            pst2.setString(8, sdt);
            pst2.setString(9, email);
            pst2.setBytes(10, hinh);
            pst2.setString(11, tinhtrang);
            pst2.setString(12, ghichu);
            pst2.setString(13, maNV);
            pst2.execute();
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void updateStatusAccNV(String maNV){
        try{
            String updateStatusAccNV = "UPDATE [TaiKhoanNhanVien] SET BiKhoa = ? WHERE MaNV = ?";
            conn = cn.getConnection();
            PreparedStatement pst2 = conn.prepareStatement(updateStatusAccNV);
            pst2.setString(1, "1");
            pst2.setString(2, maNV);
            pst2.execute();
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void updateEmailAcc(String email, String maNV){
        try{
            String updateStatusAccNV = "UPDATE [TaiKhoanNhanVien] SET Email = ? WHERE MaNV = ?";
            conn = cn.getConnection();
            PreparedStatement pst2 = conn.prepareStatement(updateStatusAccNV);
            pst2.setString(1, email);
            pst2.setString(2, maNV);
            pst2.execute();
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        hotenTxt = new javax.swing.JTextField();
        diachiTxt = new javax.swing.JTextField();
        cmndTxt = new javax.swing.JTextField();
        sdtTxt = new javax.swing.JTextField();
        emailTxt = new javax.swing.JTextField();
        saveNV = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        lbl_image = new javax.swing.JLabel();
        choosePicNV = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        ghichuTxt = new javax.swing.JTextField();
        ngaysinhTxt = new com.toedter.calendar.JDateChooser();
        statusBox = new javax.swing.JComboBox<>();
        chucvuBox = new javax.swing.JComboBox<>();
        bophanBox = new javax.swing.JComboBox<>();
        gioitinhBox = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Thông Tin Nhân Viên");

        jPanel1.setForeground(new java.awt.Color(41, 128, 185));
        jPanel1.setToolTipText("");

        jLabel2.setForeground(new java.awt.Color(41, 128, 185));
        jLabel2.setText("Họ Tên");

        jLabel3.setForeground(new java.awt.Color(41, 128, 185));
        jLabel3.setText("Địa chỉ");

        jLabel4.setForeground(new java.awt.Color(41, 128, 185));
        jLabel4.setText("Số CMND");

        jLabel5.setForeground(new java.awt.Color(41, 128, 185));
        jLabel5.setText("Số điện thoại");

        jLabel6.setForeground(new java.awt.Color(41, 128, 185));
        jLabel6.setText("Email");

        jLabel7.setForeground(new java.awt.Color(41, 128, 185));
        jLabel7.setText("Ngày sinh");

        hotenTxt.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(41, 128, 185), 1, true));
        hotenTxt.setPreferredSize(new java.awt.Dimension(3, 21));

        diachiTxt.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(41, 128, 185), 1, true));
        diachiTxt.setPreferredSize(new java.awt.Dimension(3, 21));

        cmndTxt.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(41, 128, 185), 1, true));
        cmndTxt.setPreferredSize(new java.awt.Dimension(3, 21));

        sdtTxt.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(41, 128, 185), 1, true));
        sdtTxt.setPreferredSize(new java.awt.Dimension(3, 21));

        emailTxt.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(41, 128, 185), 1, true));
        emailTxt.setPreferredSize(new java.awt.Dimension(3, 21));

        saveNV.setBackground(new java.awt.Color(41, 128, 185));
        saveNV.setForeground(new java.awt.Color(255, 255, 255));
        saveNV.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/022-save.png"))); // NOI18N
        saveNV.setText("Lưu");
        saveNV.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                saveNVMouseClicked(evt);
            }
        });

        cancelButton.setBackground(new java.awt.Color(41, 128, 185));
        cancelButton.setForeground(new java.awt.Color(255, 255, 255));
        cancelButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/058-error.png"))); // NOI18N
        cancelButton.setText("Hủy");
        cancelButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cancelButtonMouseClicked(evt);
            }
        });

        jLabel9.setForeground(new java.awt.Color(41, 128, 185));
        jLabel9.setText("Bộ phận");

        jLabel10.setForeground(new java.awt.Color(41, 128, 185));
        jLabel10.setText("Chức vụ");

        jLabel11.setForeground(new java.awt.Color(41, 128, 185));
        jLabel11.setText("Giới tính");

        lbl_image.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_image.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/364-user.png"))); // NOI18N
        lbl_image.setText("Hình");
        lbl_image.setToolTipText("");
        lbl_image.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(41, 128, 185), 1, true));

        choosePicNV.setBackground(new java.awt.Color(41, 128, 185));
        choosePicNV.setForeground(new java.awt.Color(255, 255, 255));
        choosePicNV.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/011-photo camera.png"))); // NOI18N
        choosePicNV.setText("Thay đổi hình");
        choosePicNV.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                choosePicNVMouseClicked(evt);
            }
        });

        jLabel12.setForeground(new java.awt.Color(41, 128, 185));
        jLabel12.setText("Tình trạng");

        jLabel13.setForeground(new java.awt.Color(41, 128, 185));
        jLabel13.setText("Ghi chú");

        ghichuTxt.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(41, 128, 185), 1, true));
        ghichuTxt.setPreferredSize(new java.awt.Dimension(3, 21));

        ngaysinhTxt.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(41, 128, 185), 1, true));
        ngaysinhTxt.setPreferredSize(new java.awt.Dimension(76, 21));

        statusBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Đang Làm", "Đã Nghỉ" }));
        statusBox.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(41, 128, 185), 1, true));

        chucvuBox.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(41, 128, 185), 1, true));

        bophanBox.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(41, 128, 185), 1, true));

        gioitinhBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Nam", "Nữ", "Khác", " " }));
        gioitinhBox.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(41, 128, 185), 1, true));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addComponent(choosePicNV)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lbl_image, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(58, 58, 58))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel11)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel9)
                                .addComponent(jLabel10)))
                        .addGap(40, 40, 40)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(chucvuBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(bophanBox, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(gioitinhBox, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7)
                            .addComponent(jLabel12)
                            .addComponent(jLabel13)
                            .addComponent(jLabel5))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(ngaysinhTxt, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(emailTxt, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 340, Short.MAX_VALUE)
                                    .addComponent(ghichuTxt, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 339, Short.MAX_VALUE)
                                    .addComponent(statusBox, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(sdtTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel3)
                            .addComponent(jLabel2))
                        .addGap(36, 36, 36)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(diachiTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmndTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(hotenTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(107, 107, 107)
                        .addComponent(saveNV)
                        .addGap(76, 76, 76)
                        .addComponent(cancelButton)))
                .addContainerGap(15, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_image, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(choosePicNV))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(hotenTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(diachiTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(cmndTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(sdtTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(emailTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel7)
                    .addComponent(ngaysinhTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(gioitinhBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(chucvuBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(bophanBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(statusBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(ghichuTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(saveNV)
                    .addComponent(cancelButton)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(19, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(17, 17, 17))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    @SuppressWarnings("empty-statement")
    private void saveNVMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_saveNVMouseClicked
        String hoten, sdt, gioitinh, bophan, chucvu, diachi, cmnd, cmndEn = "", email, tinhtrang, ghichu;
        hoten = hotenTxt.getText().toString().trim();
        sdt = sdtTxt.getText().toString().trim();
        java.sql.Date ngaysinh = new java.sql.Date(ngaysinhTxt.getDate().getTime());
        gioitinh = gioitinhBox.getSelectedItem().toString();
        bophan = bophanBox.getSelectedItem().toString();
        chucvu = chucvuBox.getSelectedItem().toString();
        cmnd = cmndTxt.getText().toString().trim();
        email = emailTxt.getText().toString().trim();
        diachi = diachiTxt.getText().toString().trim();
        tinhtrang = statusBox.getSelectedItem().toString();
        ghichu = ghichuTxt.getText().toString().trim();
        try{
            conn = cn.getConnection();
            String select_BP = "Select * from BoPhan";
            PreparedStatement pst3 = conn.prepareStatement(select_BP);
            ResultSet bophanlist = pst3.executeQuery();
            while(bophanlist.next()){
                String bpName = bophanlist.getString("TenBoPhan");
                if(bpName.equals(bophan)){
                    bophan = bophanlist.getString("MaBP");
                }
            }
            String select_CV = "Select * from ChucVu";
            PreparedStatement pst4 = conn.prepareStatement(select_CV);
            ResultSet chucvulist = pst4.executeQuery();
            while(chucvulist.next()){
                String cvName = chucvulist.getString("TenChucVu");
                if(cvName.equals(chucvu)){
                    chucvu = chucvulist.getString("MaCV");
                }
            }
        } catch(Exception e){
            e.printStackTrace();
        }        
        // Validate thông tin nhân viên
        StringBuffer sb = new StringBuffer();
        if(hoten.length() == 0){
            sb.append("Họ tên không được để trống!\n");
        }
        if(Validate.isNumber(hoten) == true || Validate.notAlpha(hoten) == true){
            sb.append("Họ tên không được có số hoặc kí tự đặc biệt!\n");
        }
        if(diachi.length() == 0){
            sb.append("Địa chỉ không được để trống!\n");
        }
        if(cmnd.length() != 9 && cmnd.length() != 12){
            sb.append("CMND phải có độ dài là 9 hoặc 12!\n");
        }
        if(Validate.notNumber(cmnd) == true){
            sb.append("CMND chỉ được chứa số!\n");
        }
        if(sdt.length() != 10){
            sb.append("Số điện thoại phải có độ dài là 10!\n");
        }
        if(Validate.notNumber(sdt) == true){
            sb.append("Số điện thoại chỉ được chứa số!\n");
        }
        if(email.length() == 0){
            sb.append("Email không được để trống!\n");
        }
        if(Validate.validate(email) == false){
            sb.append("Định dạng email không đúng!\n");
        }
        java.util.Date birthDate = ngaysinhTxt.getDate();
        Instant instant = birthDate.toInstant();
        LocalDate selectedLocalDate = instant.atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate currentDate = LocalDate.now();
        if(selectedLocalDate.isAfter(currentDate.minusYears(18))){
            sb.append("Nhân viên chưa đủ tuổi!\n");
        }
        String select_NV = "Select * from NhanVien where MaNV != ?";
            try{
            conn = cn.getConnection();
            PreparedStatement pst = conn.prepareStatement(select_NV);
            pst.setString(1, this.ID);
            ResultSet staff = pst.executeQuery();
            while(staff.next()){
                if(Crypto.AES.decrypt(staff.getString("CMND")).equals(cmnd) == true){
                    sb.append("CMND đã tồn tại!\n");
                }
                if(staff.getString("SDT").trim().equals(sdt) == true){
                    sb.append("Số điện thoại đã tồn tại!\n");
                }
                if(staff.getString("Email").trim().equals(email) == true){
                    sb.append("Email đã tồn tại!\n");
                }
            }
        } catch(Exception e){
            e.printStackTrace();
        }
        if(sb.length()>0){    
            JOptionPane.showMessageDialog(this, sb.toString());
            return;
        }
        try {
                cmndEn = Crypto.AES.encrypt(cmnd);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if(this.CODE.equals("INSERT") == true){
            String hashPwd = Validate.md5("123456789");
            insertNV(hoten, sdt, ngaysinh, gioitinh, bophan, chucvu, diachi, cmndEn, email, person_image, tinhtrang, ghichu);
            insertAccNV(email,hashPwd);
            JOptionPane.showMessageDialog(this, "Đã Thêm Nhân Viên Thành Công. Mật khẩu mặc định là: 123456789");
            this.dispose();
        }
        if(this.CODE.equals("UPDATE") == true){
            updateNV(hoten, sdt, ngaysinh, gioitinh, bophan, chucvu, diachi, cmndEn, email, person_image, tinhtrang, ghichu, this.ID);
            updateEmailAcc(email, this.ID);
            if(tinhtrang.trim().equals("Đã Nghỉ") == true){
                updateStatusAccNV(this.ID);
            }
            JOptionPane.showMessageDialog(this, "Đã Cập Nhật Thông Tin Nhân Viên Thành Công");
            this.dispose();
        }
        if(this.CODE.equals("UPDATE MS") == true){
            updateNV(hoten, sdt, ngaysinh, gioitinh, bophan, chucvu, diachi, cmndEn, email, person_image, tinhtrang, ghichu, this.ID);
            if(tinhtrang.trim().equals("Đã Nghỉ") == true){
                updateStatusAccNV(this.ID);
            }
            JOptionPane.showMessageDialog(this, "Đã Cập Nhật Thông Tin Nhân Viên Thành Công");
            this.dispose();
        }
        
    }//GEN-LAST:event_saveNVMouseClicked

    public void insertAccNV( String username, String password ){
        try{
            String insert = "INSERT INTO [TaiKhoanNhanVien](Email, Password, SoLanSaiMatKhau, BiKhoa, XacThuc) VALUES(?,?,?,?,?)";
            conn = cn.getConnection();
            PreparedStatement pst2 = conn.prepareStatement(insert);
            pst2.setString(1, username);
            pst2.setString(2, password);
            pst2.setString(3, "0");
            pst2.setString(4, "0");
            pst2.setString(5, "0");
            pst2.execute();
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    
    private void cancelButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cancelButtonMouseClicked
        this.dispose();
    }//GEN-LAST:event_cancelButtonMouseClicked

    private void choosePicNVMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_choosePicNVMouseClicked
        JFileChooser chooser = new JFileChooser();
        chooser.showOpenDialog(null);
        File f = chooser.getSelectedFile();
        if(f == null){
            return;
        }
        String filename = f.getAbsolutePath();
        ImageIcon imageicon = new ImageIcon( new ImageIcon(filename).getImage().getScaledInstance(lbl_image.getWidth(), lbl_image.getHeight(), Image.SCALE_SMOOTH));
        lbl_image.setIcon(imageicon);
        try{
            File image = new File(filename);
            FileInputStream fis = new FileInputStream(image);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];
            for(int readNum;(readNum=fis.read(buf))!=-1;) {
                bos.write(buf,0,readNum);
            }
            person_image = bos.toByteArray();
        }catch(Exception e){
            e.printStackTrace();
        }
    }//GEN-LAST:event_choosePicNVMouseClicked

    public static void main(String args[]) {
        FlatLightLaf.setup();
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new Staff_Infomation().setVisible(true);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(Staff_Infomation.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    Logger.getLogger(Staff_Infomation.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> bophanBox;
    private javax.swing.JButton cancelButton;
    private javax.swing.JButton choosePicNV;
    private javax.swing.JComboBox<String> chucvuBox;
    private javax.swing.JTextField cmndTxt;
    private javax.swing.JTextField diachiTxt;
    private javax.swing.JTextField emailTxt;
    private javax.swing.JTextField ghichuTxt;
    private javax.swing.JComboBox<String> gioitinhBox;
    private javax.swing.JTextField hotenTxt;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lbl_image;
    private com.toedter.calendar.JDateChooser ngaysinhTxt;
    private javax.swing.JButton saveNV;
    private javax.swing.JTextField sdtTxt;
    private javax.swing.JComboBox<String> statusBox;
    // End of variables declaration//GEN-END:variables
}
