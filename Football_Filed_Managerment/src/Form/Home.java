package Form;

import DataBase.ConnectDB;
import com.formdev.flatlaf.FlatLightLaf;
import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.BorderLayout;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;

public class Home extends javax.swing.JFrame {
    UserSession userSession = UserSession.getInstance();
    String username = userSession.getUsername();
    String idNV = userSession.getUserID();
    ConnectDB cn = new ConnectDB();
    Connection conn;
    String maCV = null, maBP = null;
    
    
    public Home() {
        if(idNV == null){
            JOptionPane.showMessageDialog(this, " Vui Lòng Đăng Nhập ");
            this.setVisible(false);
            new Login().setVisible(true);
            repaint();
            revalidate();
            return;
        }
        initComponents();
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        ImageIcon img = new ImageIcon("D:\\Football_Filed_Managerment\\src\\images\\icons/—Pngtree—football field plan_4710125 (1).png");
        this.setIconImage(img.getImage());
        GetNhanVienFromID(idNV);
        
        String tenCV = GetChucVuFromID(maCV);
        String tenBP = GetBoPhanFromID(maBP);
        if(tenCV.equals("Quản Lý") && tenBP.equals("Quản Lý")){
//            jTabbedPane1.remove(1);
           return;
        }
        if(!tenBP.equals("Kho")){
            khoPanel.setVisible(false);
        }
        if(!tenBP.equals("Quản Lý")){
            staffPanel.setVisible(false);
            generalPanel.setVisible(false);
            dichvuInfoPanel.setVisible(false);
        }
        if(!tenBP.equals("Thu Ngân")){
            datsanPanel.setVisible(false);
            khachhangPanel.setVisible(false);
            sellDVpanel.setVisible(false);
        }
        if(!tenBP.equals("Quản Lý")){
            sanBongPanel.setVisible(false);
        }
        if(tenBP.equals("Thu Ngân")){
            jTabbedPane1.remove(0);
            jTabbedPane1.remove(1);
            jTabbedPane1.remove(0);
            jTabbedPane1.revalidate(); 
            jTabbedPane1.repaint();
        }
        if(tenBP.equals("Kho")){
            jTabbedPane1.remove(0);
            jTabbedPane1.remove(0);
            jTabbedPane1.revalidate(); 
            jTabbedPane1.repaint();
        }
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // Reset tất cả các user sessions khi đóng frame home
                userSession.setUserID(null);
                userSession.setUsername(null);
                new Login().setVisible(true);
            }
        });
    }
    
    public void GetNhanVienFromID(String idNV){
        try {
            conn = cn.getConnection();
            String select_NV = "Select * from NhanVien where MaNV = ?";
            PreparedStatement pst4 = conn.prepareStatement(select_NV);
            pst4.setString(1, idNV);
            ResultSet nv = pst4.executeQuery();
            while(nv.next()){
                maCV = nv.getString("ChucVu");
                maBP = nv.getString("BoPhan");
            }            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public String GetChucVuFromID(String maCV){
        String tenCV = null;
        try {
            conn = cn.getConnection();
            String select_CV = "Select * from ChucVu where MaCV = ?";
            PreparedStatement pst4 = conn.prepareStatement(select_CV);
            pst4.setString(1, maCV);
            ResultSet cv = pst4.executeQuery();
            while(cv.next()){
                tenCV = cv.getString("TenChucVu");
            }            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tenCV;
    }
    
    public String GetBoPhanFromID(String maBP){
        String tenBP = null;
        try {
            conn = cn.getConnection();
            String select_BP = "Select * from BoPhan where MaBP = ?";
            PreparedStatement pst4 = conn.prepareStatement(select_BP);
            pst4.setString(1, maBP);
            ResultSet bp = pst4.executeQuery();
            if(bp.next()){
                tenBP = bp.getString("TenBoPhan");
            }            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tenBP;
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        systemPanel = new javax.swing.JPanel();
        menuPanel = new javax.swing.JPanel();
        staffPanel = new javax.swing.JPanel();
        staffButton = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        pstButton = new javax.swing.JButton();
        dpmButton1 = new javax.swing.JButton();
        sanBongPanel = new javax.swing.JPanel();
        fbfButton = new javax.swing.JButton();
        btButton = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        fbfInfoButton = new javax.swing.JButton();
        jSeparator3 = new javax.swing.JSeparator();
        jSeparator4 = new javax.swing.JSeparator();
        jSeparator11 = new javax.swing.JSeparator();
        generalPanel = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        discountCodeButton = new javax.swing.JButton();
        guestrateButton = new javax.swing.JButton();
        jSeparator6 = new javax.swing.JSeparator();
        inputPanel = new javax.swing.JPanel();
        managerPanel1 = new javax.swing.JPanel();
        inputPanel1 = new javax.swing.JPanel();
        jSeparator7 = new javax.swing.JSeparator();
        reportPanel1 = new javax.swing.JPanel();
        fbfReportDayBtn = new javax.swing.JButton();
        fbfReportMonthBtn = new javax.swing.JButton();
        fbfReportYearBtn = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        reportPanel2 = new javax.swing.JPanel();
        dvReportDayBtn = new javax.swing.JButton();
        dvReportMonthBtn = new javax.swing.JButton();
        dvReportYearBtn = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        systemPanel1 = new javax.swing.JPanel();
        dichVuPanel = new javax.swing.JPanel();
        khoPanel = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        storageButton = new javax.swing.JButton();
        dnhButton = new javax.swing.JButton();
        nccButton = new javax.swing.JButton();
        jSeparator8 = new javax.swing.JSeparator();
        jSeparator9 = new javax.swing.JSeparator();
        dichvuInfoPanel = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        foodButton = new javax.swing.JButton();
        chargeButton = new javax.swing.JButton();
        rentButton = new javax.swing.JButton();
        jSeparator10 = new javax.swing.JSeparator();
        dvPanel = new javax.swing.JPanel();
        managerPanel = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        changepassButton = new javax.swing.JButton();
        logoutButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        infomationButton = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        datsanPanel = new javax.swing.JPanel();
        BookFBF = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        hoadonButton = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JSeparator();
        khachhangPanel = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        hkButton = new javax.swing.JButton();
        jSeparator5 = new javax.swing.JSeparator();
        sellDVpanel = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        fbButton = new javax.swing.JButton();
        rentalButton = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jSeparator12 = new javax.swing.JSeparator();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Trang Chủ");
        setBackground(new java.awt.Color(46, 204, 113));

        jTabbedPane1.setBackground(new java.awt.Color(46, 204, 113));

        systemPanel.setBackground(new java.awt.Color(46, 204, 113));

        menuPanel.setBackground(new java.awt.Color(26, 32, 40));

        staffPanel.setBackground(new java.awt.Color(26, 32, 40));

        staffButton.setBackground(new java.awt.Color(26, 32, 40));
        staffButton.setForeground(new java.awt.Color(255, 255, 255));
        staffButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/359-users.png"))); // NOI18N
        staffButton.setText("QUẢN LÝ NHÂN SỰ");
        staffButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        staffButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        staffButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                staffButtonMouseClicked(evt);
            }
        });

        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("QUẢN LÝ NHÂN SỰ");

        pstButton.setBackground(new java.awt.Color(26, 32, 40));
        pstButton.setForeground(new java.awt.Color(255, 255, 255));
        pstButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/220-layers.png"))); // NOI18N
        pstButton.setText("QUẢN LÝ CHỨC VỤ");
        pstButton.setToolTipText("");
        pstButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        pstButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        pstButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pstButtonMouseClicked(evt);
            }
        });

        dpmButton1.setBackground(new java.awt.Color(26, 32, 40));
        dpmButton1.setForeground(new java.awt.Color(255, 255, 255));
        dpmButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/368-house.png"))); // NOI18N
        dpmButton1.setText("QUẢN LÝ BỘ PHẬN");
        dpmButton1.setToolTipText("");
        dpmButton1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        dpmButton1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        dpmButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                dpmButton1MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout staffPanelLayout = new javax.swing.GroupLayout(staffPanel);
        staffPanel.setLayout(staffPanelLayout);
        staffPanelLayout.setHorizontalGroup(
            staffPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(staffPanelLayout.createSequentialGroup()
                .addGroup(staffPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(staffPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(pstButton, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(dpmButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(staffButton, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(staffPanelLayout.createSequentialGroup()
                        .addGap(187, 187, 187)
                        .addComponent(jLabel4)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        staffPanelLayout.setVerticalGroup(
            staffPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(staffPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(staffPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(dpmButton1)
                    .addComponent(pstButton)
                    .addComponent(staffButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel4)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        sanBongPanel.setBackground(new java.awt.Color(26, 32, 40));

        fbfButton.setBackground(new java.awt.Color(26, 32, 40));
        fbfButton.setForeground(new java.awt.Color(255, 255, 255));
        fbfButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/—Pngtree—football field plan_4710125 (1).png"))); // NOI18N
        fbfButton.setText("QUẢN LÝ SÂN BÓNG");
        fbfButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        fbfButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        fbfButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                fbfButtonMouseClicked(evt);
            }
        });

        btButton.setBackground(new java.awt.Color(26, 32, 40));
        btButton.setForeground(new java.awt.Color(255, 255, 255));
        btButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/—Pngtree—football field plan_4710125 (1).png"))); // NOI18N
        btButton.setText("QUẢN LÝ BẢO TRÌ SÂN");
        btButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btButtonMouseClicked(evt);
            }
        });

        jLabel5.setText("QUẢN LÝ CHUNG");

        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("QUẢN LÝ SÂN BÓNG");

        fbfInfoButton.setBackground(new java.awt.Color(26, 32, 40));
        fbfInfoButton.setForeground(new java.awt.Color(255, 255, 255));
        fbfInfoButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/—Pngtree—football field plan_4710125 (1).png"))); // NOI18N
        fbfInfoButton.setText("THÔNG TIN SÂN BÓNG");
        fbfInfoButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        fbfInfoButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        fbfInfoButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                fbfInfoButtonMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout sanBongPanelLayout = new javax.swing.GroupLayout(sanBongPanel);
        sanBongPanel.setLayout(sanBongPanelLayout);
        sanBongPanelLayout.setHorizontalGroup(
            sanBongPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sanBongPanelLayout.createSequentialGroup()
                .addGap(180, 180, 180)
                .addComponent(jLabel5)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(sanBongPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(fbfInfoButton, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(sanBongPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(sanBongPanelLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel11))
                    .addGroup(sanBongPanelLayout.createSequentialGroup()
                        .addComponent(fbfButton, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btButton)))
                .addGap(0, 18, Short.MAX_VALUE))
        );
        sanBongPanelLayout.setVerticalGroup(
            sanBongPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sanBongPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(sanBongPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(fbfButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(fbfInfoButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel11)
                .addGap(39, 39, 39)
                .addComponent(jLabel5)
                .addContainerGap())
        );

        jSeparator3.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jSeparator4.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jSeparator11.setOrientation(javax.swing.SwingConstants.VERTICAL);

        generalPanel.setBackground(new java.awt.Color(26, 32, 40));

        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("QUẢN LÝ CHUNG");

        discountCodeButton.setBackground(new java.awt.Color(26, 32, 40));
        discountCodeButton.setForeground(new java.awt.Color(255, 255, 255));
        discountCodeButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/369-user.png"))); // NOI18N
        discountCodeButton.setText("QUẢN LÝ MÃ KHUYẾN MÃI");
        discountCodeButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        discountCodeButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        discountCodeButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                discountCodeButtonMouseClicked(evt);
            }
        });

        guestrateButton.setBackground(new java.awt.Color(26, 32, 40));
        guestrateButton.setForeground(new java.awt.Color(255, 255, 255));
        guestrateButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/369-user.png"))); // NOI18N
        guestrateButton.setText("QUẢN LÝ HẠNG KHÁCH");
        guestrateButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        guestrateButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        guestrateButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                guestrateButtonMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout generalPanelLayout = new javax.swing.GroupLayout(generalPanel);
        generalPanel.setLayout(generalPanelLayout);
        generalPanelLayout.setHorizontalGroup(
            generalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, generalPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(discountCodeButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(guestrateButton)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, generalPanelLayout.createSequentialGroup()
                .addContainerGap(132, Short.MAX_VALUE)
                .addComponent(jLabel10)
                .addGap(121, 121, 121))
        );
        generalPanelLayout.setVerticalGroup(
            generalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(generalPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(generalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(discountCodeButton, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(guestrateButton, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel10)
                .addContainerGap())
        );

        javax.swing.GroupLayout menuPanelLayout = new javax.swing.GroupLayout(menuPanel);
        menuPanel.setLayout(menuPanelLayout);
        menuPanelLayout.setHorizontalGroup(
            menuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menuPanelLayout.createSequentialGroup()
                .addComponent(staffPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(sanBongPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(generalPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(138, Short.MAX_VALUE))
        );
        menuPanelLayout.setVerticalGroup(
            menuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator3)
            .addComponent(jSeparator4, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(jSeparator11)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, menuPanelLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(sanBongPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(staffPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(generalPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        inputPanel.setBackground(new java.awt.Color(46, 204, 113));

        javax.swing.GroupLayout inputPanelLayout = new javax.swing.GroupLayout(inputPanel);
        inputPanel.setLayout(inputPanelLayout);
        inputPanelLayout.setHorizontalGroup(
            inputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1500, Short.MAX_VALUE)
        );
        inputPanelLayout.setVerticalGroup(
            inputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 843, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout systemPanelLayout = new javax.swing.GroupLayout(systemPanel);
        systemPanel.setLayout(systemPanelLayout);
        systemPanelLayout.setHorizontalGroup(
            systemPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator6, javax.swing.GroupLayout.DEFAULT_SIZE, 1500, Short.MAX_VALUE)
            .addComponent(inputPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(systemPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(menuPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        systemPanelLayout.setVerticalGroup(
            systemPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(systemPanelLayout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(menuPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, 11, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(inputPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("HỆ THỐNG", systemPanel);

        managerPanel1.setBackground(new java.awt.Color(26, 32, 40));

        inputPanel1.setBackground(new java.awt.Color(46, 204, 113));

        javax.swing.GroupLayout inputPanel1Layout = new javax.swing.GroupLayout(inputPanel1);
        inputPanel1.setLayout(inputPanel1Layout);
        inputPanel1Layout.setHorizontalGroup(
            inputPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(inputPanel1Layout.createSequentialGroup()
                .addComponent(jSeparator7, javax.swing.GroupLayout.PREFERRED_SIZE, 894, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 606, Short.MAX_VALUE))
        );
        inputPanel1Layout.setVerticalGroup(
            inputPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(inputPanel1Layout.createSequentialGroup()
                .addComponent(jSeparator7, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(875, Short.MAX_VALUE))
        );

        reportPanel1.setBackground(new java.awt.Color(26, 32, 40));

        fbfReportDayBtn.setBackground(new java.awt.Color(26, 32, 40));
        fbfReportDayBtn.setForeground(new java.awt.Color(255, 255, 255));
        fbfReportDayBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/dateIcon.png"))); // NOI18N
        fbfReportDayBtn.setText("THEO NGÀY");
        fbfReportDayBtn.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        fbfReportDayBtn.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        fbfReportDayBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                fbfReportDayBtnMouseClicked(evt);
            }
        });

        fbfReportMonthBtn.setBackground(new java.awt.Color(26, 32, 40));
        fbfReportMonthBtn.setForeground(new java.awt.Color(255, 255, 255));
        fbfReportMonthBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/monthIcon.png"))); // NOI18N
        fbfReportMonthBtn.setText("THEO THÁNG");
        fbfReportMonthBtn.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        fbfReportMonthBtn.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        fbfReportMonthBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                fbfReportMonthBtnMouseClicked(evt);
            }
        });

        fbfReportYearBtn.setBackground(new java.awt.Color(26, 32, 40));
        fbfReportYearBtn.setForeground(new java.awt.Color(255, 255, 255));
        fbfReportYearBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/yearIcon.png"))); // NOI18N
        fbfReportYearBtn.setText("THEO NĂM");
        fbfReportYearBtn.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        fbfReportYearBtn.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        fbfReportYearBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fbfReportYearBtnActionPerformed(evt);
            }
        });

        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("BÁO CÁO DOANH THU SÂN BÓNG");

        javax.swing.GroupLayout reportPanel1Layout = new javax.swing.GroupLayout(reportPanel1);
        reportPanel1.setLayout(reportPanel1Layout);
        reportPanel1Layout.setHorizontalGroup(
            reportPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(reportPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(fbfReportDayBtn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(fbfReportMonthBtn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(fbfReportYearBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, reportPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel12)
                .addGap(71, 71, 71))
        );
        reportPanel1Layout.setVerticalGroup(
            reportPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(reportPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(reportPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(fbfReportYearBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(fbfReportMonthBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(fbfReportDayBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        reportPanel2.setBackground(new java.awt.Color(26, 32, 40));

        dvReportDayBtn.setBackground(new java.awt.Color(26, 32, 40));
        dvReportDayBtn.setForeground(new java.awt.Color(255, 255, 255));
        dvReportDayBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/dateIcon.png"))); // NOI18N
        dvReportDayBtn.setText("THEO NGÀY");
        dvReportDayBtn.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        dvReportDayBtn.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        dvReportDayBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                dvReportDayBtnMouseClicked(evt);
            }
        });

        dvReportMonthBtn.setBackground(new java.awt.Color(26, 32, 40));
        dvReportMonthBtn.setForeground(new java.awt.Color(255, 255, 255));
        dvReportMonthBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/monthIcon.png"))); // NOI18N
        dvReportMonthBtn.setText("THEO THÁNG");
        dvReportMonthBtn.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        dvReportMonthBtn.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        dvReportMonthBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                dvReportMonthBtnMouseClicked(evt);
            }
        });

        dvReportYearBtn.setBackground(new java.awt.Color(26, 32, 40));
        dvReportYearBtn.setForeground(new java.awt.Color(255, 255, 255));
        dvReportYearBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/yearIcon.png"))); // NOI18N
        dvReportYearBtn.setText("THEO NĂM");
        dvReportYearBtn.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        dvReportYearBtn.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        dvReportYearBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dvReportYearBtnActionPerformed(evt);
            }
        });

        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("BÁO CÁO DOANH THU DỊCH VỤ");

        javax.swing.GroupLayout reportPanel2Layout = new javax.swing.GroupLayout(reportPanel2);
        reportPanel2.setLayout(reportPanel2Layout);
        reportPanel2Layout.setHorizontalGroup(
            reportPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(reportPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(dvReportDayBtn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(dvReportMonthBtn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(dvReportYearBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, reportPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel13)
                .addGap(71, 71, 71))
        );
        reportPanel2Layout.setVerticalGroup(
            reportPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(reportPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(reportPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(dvReportYearBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(dvReportMonthBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(dvReportDayBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout managerPanel1Layout = new javax.swing.GroupLayout(managerPanel1);
        managerPanel1.setLayout(managerPanel1Layout);
        managerPanel1Layout.setHorizontalGroup(
            managerPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(inputPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(managerPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(reportPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(reportPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        managerPanel1Layout.setVerticalGroup(
            managerPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(managerPanel1Layout.createSequentialGroup()
                .addGroup(managerPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(reportPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(reportPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(inputPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("THỐNG KÊ", managerPanel1);

        systemPanel1.setBackground(new java.awt.Color(26, 32, 40));

        dichVuPanel.setBackground(new java.awt.Color(26, 32, 40));

        khoPanel.setBackground(new java.awt.Color(26, 32, 40));

        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("QUẢN LÝ KHO VÀ NHÀ CUNG CẤP");

        storageButton.setBackground(new java.awt.Color(26, 32, 40));
        storageButton.setForeground(new java.awt.Color(255, 255, 255));
        storageButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/304-archive.png"))); // NOI18N
        storageButton.setText("KHO");
        storageButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        storageButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        storageButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                storageButtonMouseClicked(evt);
            }
        });

        dnhButton.setBackground(new java.awt.Color(26, 32, 40));
        dnhButton.setForeground(new java.awt.Color(255, 255, 255));
        dnhButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/099-incoming.png"))); // NOI18N
        dnhButton.setText("NHẬP HÀNG");
        dnhButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        dnhButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        dnhButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                dnhButtonMouseClicked(evt);
            }
        });

        nccButton.setBackground(new java.awt.Color(26, 32, 40));
        nccButton.setForeground(new java.awt.Color(255, 255, 255));
        nccButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/368-house.png"))); // NOI18N
        nccButton.setText("NHÀ CUNG CẤP");
        nccButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        nccButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        nccButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                nccButtonMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout khoPanelLayout = new javax.swing.GroupLayout(khoPanel);
        khoPanel.setLayout(khoPanelLayout);
        khoPanelLayout.setHorizontalGroup(
            khoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(khoPanelLayout.createSequentialGroup()
                .addGroup(khoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(khoPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(storageButton, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(nccButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(dnhButton))
                    .addGroup(khoPanelLayout.createSequentialGroup()
                        .addGap(64, 64, 64)
                        .addComponent(jLabel8)))
                .addContainerGap(12, Short.MAX_VALUE))
        );
        khoPanelLayout.setVerticalGroup(
            khoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(khoPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(khoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(storageButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(dnhButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(nccButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel8))
        );

        dnhButton.getAccessibleContext().setAccessibleName("QUẢN LÝ ");

        jSeparator8.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jSeparator9.setOrientation(javax.swing.SwingConstants.VERTICAL);

        dichvuInfoPanel.setBackground(new java.awt.Color(26, 32, 40));

        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("QUẢN LÝ MẶT HÀNG VÀ DỊCH VỤ");

        foodButton.setBackground(new java.awt.Color(26, 32, 40));
        foodButton.setForeground(new java.awt.Color(255, 255, 255));
        foodButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/food.png"))); // NOI18N
        foodButton.setText("ĐỒ ĂN NƯỚC UỐNG");
        foodButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        foodButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        foodButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                foodButtonMouseClicked(evt);
            }
        });

        chargeButton.setBackground(new java.awt.Color(26, 32, 40));
        chargeButton.setForeground(new java.awt.Color(255, 255, 255));
        chargeButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/170-flag.png"))); // NOI18N
        chargeButton.setText("PHỤ PHÍ");
        chargeButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        chargeButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        chargeButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                chargeButtonMouseClicked(evt);
            }
        });

        rentButton.setBackground(new java.awt.Color(26, 32, 40));
        rentButton.setForeground(new java.awt.Color(255, 255, 255));
        rentButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/044-repeat.png"))); // NOI18N
        rentButton.setText("CHO THUÊ");
        rentButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        rentButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        rentButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rentButtonMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout dichvuInfoPanelLayout = new javax.swing.GroupLayout(dichvuInfoPanel);
        dichvuInfoPanel.setLayout(dichvuInfoPanelLayout);
        dichvuInfoPanelLayout.setHorizontalGroup(
            dichvuInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dichvuInfoPanelLayout.createSequentialGroup()
                .addGroup(dichvuInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel9)
                    .addGroup(dichvuInfoPanelLayout.createSequentialGroup()
                        .addComponent(foodButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(rentButton)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(chargeButton)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        dichvuInfoPanelLayout.setVerticalGroup(
            dichvuInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dichvuInfoPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(dichvuInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(foodButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(chargeButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(rentButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout dichVuPanelLayout = new javax.swing.GroupLayout(dichVuPanel);
        dichVuPanel.setLayout(dichVuPanelLayout);
        dichVuPanelLayout.setHorizontalGroup(
            dichVuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dichVuPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(khoPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(dichvuInfoPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        dichVuPanelLayout.setVerticalGroup(
            dichVuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator8)
            .addComponent(jSeparator9, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(dichVuPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(dichVuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(khoPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dichvuInfoPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        dvPanel.setBackground(new java.awt.Color(46, 204, 113));

        javax.swing.GroupLayout dvPanelLayout = new javax.swing.GroupLayout(dvPanel);
        dvPanel.setLayout(dvPanelLayout);
        dvPanelLayout.setHorizontalGroup(
            dvPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        dvPanelLayout.setVerticalGroup(
            dvPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 860, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout systemPanel1Layout = new javax.swing.GroupLayout(systemPanel1);
        systemPanel1.setLayout(systemPanel1Layout);
        systemPanel1Layout.setHorizontalGroup(
            systemPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator10)
            .addComponent(dvPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(systemPanel1Layout.createSequentialGroup()
                .addComponent(dichVuPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 846, Short.MAX_VALUE))
        );
        systemPanel1Layout.setVerticalGroup(
            systemPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(systemPanel1Layout.createSequentialGroup()
                .addComponent(dichVuPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator10, javax.swing.GroupLayout.PREFERRED_SIZE, 6, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(dvPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("QUẢN LÝ DỊCH VỤ VÀ KHO", systemPanel1);

        managerPanel.setBackground(new java.awt.Color(46, 204, 113));

        jPanel3.setBackground(new java.awt.Color(26, 32, 40));

        jPanel4.setBackground(new java.awt.Color(26, 32, 40));

        changepassButton.setBackground(new java.awt.Color(26, 32, 40));
        changepassButton.setForeground(new java.awt.Color(255, 255, 255));
        changepassButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/378-locked.png"))); // NOI18N
        changepassButton.setText("Đổi mật khẩu");
        changepassButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                changepassButtonMouseClicked(evt);
            }
        });

        logoutButton.setBackground(new java.awt.Color(26, 32, 40));
        logoutButton.setForeground(new java.awt.Color(255, 255, 255));
        logoutButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/321-exit.png"))); // NOI18N
        logoutButton.setText("Đăng xuất");
        logoutButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                logoutButtonMouseClicked(evt);
            }
        });

        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("TÀI KHOẢN");

        infomationButton.setBackground(new java.awt.Color(26, 32, 40));
        infomationButton.setForeground(new java.awt.Color(255, 255, 255));
        infomationButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/367-user.png"))); // NOI18N
        infomationButton.setText("Thông tin tài khoản");
        infomationButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                infomationButtonMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(changepassButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(logoutButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(infomationButton)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(logoutButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(changepassButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(infomationButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1))
        );

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);

        datsanPanel.setBackground(new java.awt.Color(26, 32, 40));

        BookFBF.setBackground(new java.awt.Color(26, 32, 40));
        BookFBF.setForeground(new java.awt.Color(255, 255, 255));
        BookFBF.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/—Pngtree—football field plan_4710125 (1).png"))); // NOI18N
        BookFBF.setText("ĐẶT SÂN");
        BookFBF.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        BookFBF.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        BookFBF.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BookFBFMouseClicked(evt);
            }
        });

        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("ĐẶT SÂN VÀ THANH TOÁN");

        jButton4.setBackground(new java.awt.Color(26, 32, 40));
        jButton4.setForeground(new java.awt.Color(255, 255, 255));
        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/259-document.png"))); // NOI18N
        jButton4.setText("THANH TOÁN");
        jButton4.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton4.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton4MouseClicked(evt);
            }
        });

        hoadonButton.setBackground(new java.awt.Color(26, 32, 40));
        hoadonButton.setForeground(new java.awt.Color(255, 255, 255));
        hoadonButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/259-document.png"))); // NOI18N
        hoadonButton.setText("Quản Lý Hóa Đơn");
        hoadonButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        hoadonButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        hoadonButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                hoadonButtonMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout datsanPanelLayout = new javax.swing.GroupLayout(datsanPanel);
        datsanPanel.setLayout(datsanPanelLayout);
        datsanPanelLayout.setHorizontalGroup(
            datsanPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(datsanPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(BookFBF)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(datsanPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(datsanPanelLayout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(datsanPanelLayout.createSequentialGroup()
                        .addComponent(jButton4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(hoadonButton, javax.swing.GroupLayout.DEFAULT_SIZE, 125, Short.MAX_VALUE)))
                .addContainerGap())
        );
        datsanPanelLayout.setVerticalGroup(
            datsanPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(datsanPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(datsanPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(datsanPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(BookFBF)
                        .addComponent(jButton4, javax.swing.GroupLayout.Alignment.TRAILING))
                    .addComponent(hoadonButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 43, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addContainerGap())
        );

        jSeparator2.setOrientation(javax.swing.SwingConstants.VERTICAL);

        khachhangPanel.setBackground(new java.awt.Color(26, 32, 40));

        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("KHÁCH HÀNG");

        hkButton.setBackground(new java.awt.Color(26, 32, 40));
        hkButton.setForeground(new java.awt.Color(255, 255, 255));
        hkButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/360-users.png"))); // NOI18N
        hkButton.setText("Thông tin khách hàng");
        hkButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        hkButton.setInheritsPopupMenu(true);
        hkButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        hkButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                hkButtonMouseClicked(evt);
            }
        });
        hkButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hkButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout khachhangPanelLayout = new javax.swing.GroupLayout(khachhangPanel);
        khachhangPanel.setLayout(khachhangPanelLayout);
        khachhangPanelLayout.setHorizontalGroup(
            khachhangPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, khachhangPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(hkButton)
                .addContainerGap())
            .addGroup(khachhangPanelLayout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addComponent(jLabel3)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        khachhangPanelLayout.setVerticalGroup(
            khachhangPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(khachhangPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(hkButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 44, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addContainerGap())
        );

        jSeparator5.setOrientation(javax.swing.SwingConstants.VERTICAL);

        sellDVpanel.setBackground(new java.awt.Color(26, 32, 40));

        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("DỊCH VỤ");

        fbButton.setBackground(new java.awt.Color(26, 32, 40));
        fbButton.setForeground(new java.awt.Color(255, 255, 255));
        fbButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/food.png"))); // NOI18N
        fbButton.setText("Đồ Ăn Nước Uống");
        fbButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        fbButton.setInheritsPopupMenu(true);
        fbButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        fbButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                fbButtonMouseClicked(evt);
            }
        });
        fbButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fbButtonActionPerformed(evt);
            }
        });

        rentalButton.setBackground(new java.awt.Color(26, 32, 40));
        rentalButton.setForeground(new java.awt.Color(255, 255, 255));
        rentalButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/044-repeat.png"))); // NOI18N
        rentalButton.setText("Cho Thuê");
        rentalButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        rentalButton.setInheritsPopupMenu(true);
        rentalButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        rentalButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rentalButtonMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout sellDVpanelLayout = new javax.swing.GroupLayout(sellDVpanel);
        sellDVpanel.setLayout(sellDVpanelLayout);
        sellDVpanelLayout.setHorizontalGroup(
            sellDVpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sellDVpanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(sellDVpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel7)
                    .addComponent(fbButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(rentalButton)
                .addGap(95, 95, 95))
        );
        sellDVpanelLayout.setVerticalGroup(
            sellDVpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sellDVpanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(sellDVpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(rentalButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(fbButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 40, Short.MAX_VALUE)
                .addComponent(jLabel7)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addComponent(datsanPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(khachhangPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(sellDVpanel, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 579, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator2, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(datsanPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jSeparator5, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(khachhangPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sellDVpanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel1.setBackground(new java.awt.Color(46, 204, 113));
        jPanel1.setForeground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1500, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 847, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout managerPanelLayout = new javax.swing.GroupLayout(managerPanel);
        managerPanel.setLayout(managerPanelLayout);
        managerPanelLayout.setHorizontalGroup(
            managerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jSeparator12)
        );
        managerPanelLayout.setVerticalGroup(
            managerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(managerPanelLayout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator12, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("ĐẶT SÂN VÀ BÁN HÀNG", managerPanel);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.Alignment.TRAILING)
        );

        jTabbedPane1.getAccessibleContext().setAccessibleName("THỐNG KÊ");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void dnhButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dnhButtonMouseClicked
        InputStorage inputStorage = new InputStorage();
        dvPanel.removeAll();
        revalidate();
        repaint();
        dvPanel.add(inputStorage).setVisible(true);
    }//GEN-LAST:event_dnhButtonMouseClicked

    private void storageButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_storageButtonMouseClicked
        Storage storage = new Storage();
        dvPanel.removeAll();
        revalidate();
        repaint();
        dvPanel.add(storage).setVisible(true);
    }//GEN-LAST:event_storageButtonMouseClicked

    private void nccButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_nccButtonMouseClicked
        Provider provider = new Provider();
        dvPanel.removeAll();
        revalidate();
        repaint();
        dvPanel.add(provider).setVisible(true);
    }//GEN-LAST:event_nccButtonMouseClicked

    private void foodButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_foodButtonMouseClicked
        Substance substance = new Substance();
        dvPanel.removeAll();
        revalidate();
        repaint();
        dvPanel.add(substance).setVisible(true);
    }//GEN-LAST:event_foodButtonMouseClicked

    private void chargeButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_chargeButtonMouseClicked
        PlusCharge plusCharge = new PlusCharge();
        dvPanel.removeAll();
        revalidate();
        repaint();
        dvPanel.add(plusCharge).setVisible(true);
    }//GEN-LAST:event_chargeButtonMouseClicked

    private void rentButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rentButtonMouseClicked
        Rental rental = new Rental();
        dvPanel.removeAll();
        revalidate();
        repaint();
        dvPanel.add(rental).setVisible(true);
    }//GEN-LAST:event_rentButtonMouseClicked

    private void btButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btButtonMouseClicked
        Maintenance maintenance = new Maintenance();
        inputPanel.removeAll();
        revalidate();
        repaint();
        inputPanel.add(maintenance).setVisible(true);
    }//GEN-LAST:event_btButtonMouseClicked

    private void fbfButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_fbfButtonMouseClicked
        FootballFiled fbf = new FootballFiled();
        inputPanel.removeAll();
        revalidate();
        repaint();
        inputPanel.add(fbf).setVisible(true);
    }//GEN-LAST:event_fbfButtonMouseClicked

    private void dpmButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dpmButton1MouseClicked
        Department department = new Department();
        inputPanel.removeAll();
        revalidate();
        repaint();
        inputPanel.add(department).setVisible(true);
    }//GEN-LAST:event_dpmButton1MouseClicked

    private void pstButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pstButtonMouseClicked
        Position position = new Position();
        inputPanel.removeAll();
        revalidate();
        repaint();
        inputPanel.add(position).setVisible(true);
    }//GEN-LAST:event_pstButtonMouseClicked

    private void staffButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_staffButtonMouseClicked
        Staff staff = new Staff();
        inputPanel.removeAll();
        revalidate();
        repaint();
        inputPanel.add(staff).setVisible(true);
    }//GEN-LAST:event_staffButtonMouseClicked

    private void guestrateButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_guestrateButtonMouseClicked
        HangKhach hangkhach = new HangKhach();
        inputPanel.removeAll();
        revalidate();
        repaint();
        inputPanel.add(hangkhach).setVisible(true);
    }//GEN-LAST:event_guestrateButtonMouseClicked

    private void BookFBFMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BookFBFMouseClicked
        BookFBF bookFBF = new BookFBF();
        jPanel1.removeAll();
        revalidate();
        repaint();
        jPanel1.add(bookFBF).setVisible(true);
    }//GEN-LAST:event_BookFBFMouseClicked

    private void hkButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_hkButtonMouseClicked
        Customer customer = new Customer();
        jPanel1.removeAll();
        revalidate();
        repaint();
        jPanel1.add(customer).setVisible(true);
    }//GEN-LAST:event_hkButtonMouseClicked

    private void hkButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hkButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_hkButtonActionPerformed

    private void fbButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_fbButtonMouseClicked
        SellFB sellFB = new SellFB();
        jPanel1.removeAll();
        revalidate();
        repaint();
        jPanel1.add(sellFB).setVisible(true);
    }//GEN-LAST:event_fbButtonMouseClicked

    private void fbButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fbButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_fbButtonActionPerformed

    private void rentalButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rentalButtonMouseClicked
        RentStuff rentTSTB = new RentStuff();
        jPanel1.removeAll();
        revalidate();
        repaint();
        jPanel1.add(rentTSTB).setVisible(true);
    }//GEN-LAST:event_rentalButtonMouseClicked

    private void jButton4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton4MouseClicked
        Pay pay = new Pay();
        jPanel1.removeAll();
        revalidate();
        repaint();
        jPanel1.add(pay).setVisible(true);
    }//GEN-LAST:event_jButton4MouseClicked

    private void discountCodeButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_discountCodeButtonMouseClicked
        DiscountCode discountCode = new DiscountCode();
        inputPanel.removeAll();
        revalidate();
        repaint();
        inputPanel.add(discountCode).setVisible(true);
    }//GEN-LAST:event_discountCodeButtonMouseClicked

    private void logoutButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_logoutButtonMouseClicked
        userSession.setUserID(null);
        userSession.setUsername(null);
        this.dispose();
        new Login().setVisible(true);
    }//GEN-LAST:event_logoutButtonMouseClicked

    private void hoadonButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_hoadonButtonMouseClicked
        HoaDon hoaDon = new HoaDon();
        jPanel1.removeAll();
        revalidate();
        repaint();
        jPanel1.add(hoaDon).setVisible(true);
    }//GEN-LAST:event_hoadonButtonMouseClicked

    private void changepassButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_changepassButtonMouseClicked
        new ChangePassword().setVisible(true);
    }//GEN-LAST:event_changepassButtonMouseClicked

    private void infomationButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_infomationButtonMouseClicked
        try {
            new Staff_Infomation("UPDATE MS", idNV).setVisible(true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }//GEN-LAST:event_infomationButtonMouseClicked

    private void fbfInfoButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_fbfInfoButtonMouseClicked
        FBF_Infomation fBF_Infomation = new FBF_Infomation();
        inputPanel.removeAll();
        revalidate();
        repaint();
        inputPanel.add(fBF_Infomation).setVisible(true);
    }//GEN-LAST:event_fbfInfoButtonMouseClicked

    private void fbfReportMonthBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_fbfReportMonthBtnMouseClicked
        FBFReportMonth fBFReportMonth = new FBFReportMonth();
        inputPanel1.removeAll();
        revalidate();
        repaint();
        inputPanel1.add(fBFReportMonth).setVisible(true);
    }//GEN-LAST:event_fbfReportMonthBtnMouseClicked

    private void fbfReportYearBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fbfReportYearBtnActionPerformed
        FBFReportYear fBFReportYear = new FBFReportYear();
        inputPanel1.removeAll();
        revalidate();
        repaint();
        inputPanel1.add(fBFReportYear).setVisible(true);
    }//GEN-LAST:event_fbfReportYearBtnActionPerformed

    private void dvReportMonthBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dvReportMonthBtnMouseClicked
        DVReportMonth dVReportMonth = new DVReportMonth();
        inputPanel1.removeAll();
        revalidate();
        repaint();
        inputPanel1.add(dVReportMonth).setVisible(true);
    }//GEN-LAST:event_dvReportMonthBtnMouseClicked

    private void dvReportYearBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dvReportYearBtnActionPerformed
        DVReportYear dVReportYear = new DVReportYear();
        inputPanel1.removeAll();
        revalidate();
        repaint();
        inputPanel1.add(dVReportYear).setVisible(true);
    }//GEN-LAST:event_dvReportYearBtnActionPerformed

    private void fbfReportDayBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_fbfReportDayBtnMouseClicked
        FBFReportDay fBFReportDay = new FBFReportDay();
        inputPanel1.removeAll();
        revalidate();
        repaint();
        inputPanel1.add(fBFReportDay).setVisible(true);
    }//GEN-LAST:event_fbfReportDayBtnMouseClicked

    private void dvReportDayBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dvReportDayBtnMouseClicked
        DVReportDay dVReportDay = new DVReportDay();
        inputPanel1.removeAll();
        revalidate();
        repaint();
        inputPanel1.add(dVReportDay).setVisible(true);
    }//GEN-LAST:event_dvReportDayBtnMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        FlatLightLaf.setup();
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Home().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton BookFBF;
    public javax.swing.JButton btButton;
    public javax.swing.JButton changepassButton;
    public javax.swing.JButton chargeButton;
    public javax.swing.JPanel datsanPanel;
    public javax.swing.JPanel dichVuPanel;
    public javax.swing.JPanel dichvuInfoPanel;
    public javax.swing.JButton discountCodeButton;
    public javax.swing.JButton dnhButton;
    public javax.swing.JButton dpmButton1;
    public javax.swing.JPanel dvPanel;
    public javax.swing.JButton dvReportDayBtn;
    public javax.swing.JButton dvReportMonthBtn;
    public javax.swing.JButton dvReportYearBtn;
    public javax.swing.JButton fbButton;
    public javax.swing.JButton fbfButton;
    public javax.swing.JButton fbfInfoButton;
    public javax.swing.JButton fbfReportDayBtn;
    public javax.swing.JButton fbfReportMonthBtn;
    public javax.swing.JButton fbfReportYearBtn;
    public javax.swing.JButton foodButton;
    public javax.swing.JPanel generalPanel;
    public javax.swing.JButton guestrateButton;
    public javax.swing.JButton hkButton;
    public javax.swing.JButton hoadonButton;
    public javax.swing.JButton infomationButton;
    public javax.swing.JPanel inputPanel;
    public javax.swing.JPanel inputPanel1;
    public javax.swing.JButton jButton4;
    public javax.swing.JLabel jLabel1;
    public javax.swing.JLabel jLabel10;
    public javax.swing.JLabel jLabel11;
    public javax.swing.JLabel jLabel12;
    public javax.swing.JLabel jLabel13;
    public javax.swing.JLabel jLabel2;
    public javax.swing.JLabel jLabel3;
    public javax.swing.JLabel jLabel4;
    public javax.swing.JLabel jLabel5;
    public javax.swing.JLabel jLabel7;
    public javax.swing.JLabel jLabel8;
    public javax.swing.JLabel jLabel9;
    public javax.swing.JPanel jPanel1;
    public javax.swing.JPanel jPanel3;
    public javax.swing.JPanel jPanel4;
    public javax.swing.JSeparator jSeparator1;
    public javax.swing.JSeparator jSeparator10;
    public javax.swing.JSeparator jSeparator11;
    public javax.swing.JSeparator jSeparator12;
    public javax.swing.JSeparator jSeparator2;
    public javax.swing.JSeparator jSeparator3;
    public javax.swing.JSeparator jSeparator4;
    public javax.swing.JSeparator jSeparator5;
    public javax.swing.JSeparator jSeparator6;
    public javax.swing.JSeparator jSeparator7;
    public javax.swing.JSeparator jSeparator8;
    public javax.swing.JSeparator jSeparator9;
    public javax.swing.JTabbedPane jTabbedPane1;
    public javax.swing.JPanel khachhangPanel;
    public javax.swing.JPanel khoPanel;
    public javax.swing.JButton logoutButton;
    public javax.swing.JPanel managerPanel;
    public javax.swing.JPanel managerPanel1;
    public javax.swing.JPanel menuPanel;
    public javax.swing.JButton nccButton;
    public javax.swing.JButton pstButton;
    public javax.swing.JButton rentButton;
    public javax.swing.JButton rentalButton;
    public javax.swing.JPanel reportPanel1;
    public javax.swing.JPanel reportPanel2;
    public javax.swing.JPanel sanBongPanel;
    public javax.swing.JPanel sellDVpanel;
    public javax.swing.JButton staffButton;
    public javax.swing.JPanel staffPanel;
    public javax.swing.JButton storageButton;
    public javax.swing.JPanel systemPanel;
    public javax.swing.JPanel systemPanel1;
    // End of variables declaration//GEN-END:variables
}
