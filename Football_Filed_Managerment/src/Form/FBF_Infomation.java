package Form;

import DataBase.ConnectDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;


public class FBF_Infomation extends javax.swing.JInternalFrame {
    ConnectDB cn = new ConnectDB();
    Connection conn;
    String maSanBong = null;
    
    public FBF_Infomation() {
        initComponents();
        try{
            conn = cn.getConnection();
            String select_1FBFInfo = "Select * from ThongTinSanBong";
            PreparedStatement pst3 = conn.prepareStatement(select_1FBFInfo);
            ResultSet fbfInfo = pst3.executeQuery();
            System.out.println("Start");
            if(fbfInfo.next()){
                maSanBong = fbfInfo.getString("MaSanBong");
                tensanTxt.setText(fbfInfo.getString("TenSan"));
                diachisanTxt.setText(fbfInfo.getString("DiaChiSan"));
                if(fbfInfo.getString("GioMoCua").equals("") == true || fbfInfo.getString("GioDongCua").equals("") == true){
                    return;
                }
                LocalTime timeBD = LocalTime.parse(fbfInfo.getString("GioMoCua"));
                if(timeBD.getHour() < 10){
                    gioMCSpinner.setValue("0" +String.valueOf(timeBD.getHour()));
                }else{
                    gioMCSpinner.setValue(String.valueOf(timeBD.getHour()));
                }
                if(timeBD.getMinute() == 15){
                    phutMCSpinner.setValue("15");
                }else if(timeBD.getMinute() == 30 ){
                    phutMCSpinner.setValue("30");
                }else if(timeBD.getMinute() == 45){
                    phutMCSpinner.setValue("45");
                }else{
                    phutMCSpinner.setValue("00");
                }
                LocalTime timeKT = LocalTime.parse(fbfInfo.getString("GioDongCua"));
                if(timeKT.getHour() < 10){
                    gioDCSpinner.setValue("0" +String.valueOf(timeKT.getHour()));
                }else{
                    gioDCSpinner.setValue(String.valueOf(timeKT.getHour()));
                }
                if(timeKT.getMinute() == 15){
                    phutDCSpinner.setValue("15");
                }else if(timeKT.getMinute() == 30){
                    phutDCSpinner.setValue("30");
                } else if(timeKT.getMinute() == 45){
                    phutDCSpinner.setValue("45");
                } else {
                    phutDCSpinner.setValue("00");
                }
            }else{
                maSanBong = randomFBFInfo();
                insertFBFInfo(maSanBong);
            }               
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public String randomFBFInfo(){
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
    
    public void insertFBFInfo(String maSanBong){
        try{
            String insert = "INSERT INTO ThongTinSanBong( MaSanBong, TenSan, DiaChiSan, GioMoCua, GioDongCua ) VALUES(?,?,?,?,?)";
            conn = cn.getConnection();
            PreparedStatement pst2 = conn.prepareStatement(insert);
            pst2.setString(1, maSanBong);
            pst2.setString(2, "");
            pst2.setString(3, "");
            pst2.setString(4, "");
            pst2.setString(5, "");
            pst2.execute();
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void updateFBFInfo(String tenSan, String diaChiSan, String gioMoCua, String gioDongCua, String maSanBong){
        try{
            String insert = "UPDATE ThongTinSanBong SET TenSan =?, DiaChiSan = ?, GioMoCua = ?, GioDongCua = ? where MaSanBong = ?";
            conn = cn.getConnection();
            PreparedStatement pst2 = conn.prepareStatement(insert);
            pst2.setString(1, tenSan);
            pst2.setString(2, diaChiSan);
            pst2.setString(3, gioMoCua);
            pst2.setString(4, gioDongCua);
            pst2.setString(5, maSanBong);
            pst2.execute();
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        tensanTxt = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        diachisanTxt = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        gioMCSpinner = new javax.swing.JSpinner();
        phutMCSpinner = new javax.swing.JSpinner();
        jLabel5 = new javax.swing.JLabel();
        gioDCSpinner = new javax.swing.JSpinner();
        jLabel6 = new javax.swing.JLabel();
        phutDCSpinner = new javax.swing.JSpinner();
        saveFBFInfo = new javax.swing.JButton();
        closeButton = new javax.swing.JButton();

        setTitle("Thông Tin Sân Bóng");

        jLabel1.setText("Tên Sân");

        jLabel2.setText("Địa Chỉ");

        jLabel3.setText("Giờ Mở Cửa");

        jLabel4.setText("Giờ Đóng Cửa");

        gioMCSpinner.setModel(new javax.swing.SpinnerListModel(new String[] {"00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23"}));

        phutMCSpinner.setModel(new javax.swing.SpinnerListModel(new String[] {"00", "15", "30", "45"}));

        jLabel5.setText(" : ");

        gioDCSpinner.setModel(new javax.swing.SpinnerListModel(new String[] {"00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23"}));

        jLabel6.setText(" : ");

        phutDCSpinner.setModel(new javax.swing.SpinnerListModel(new String[] {"00", "15", "30", "45"}));

        saveFBFInfo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/022-save.png"))); // NOI18N
        saveFBFInfo.setText("Lưu");
        saveFBFInfo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                saveFBFInfoMouseClicked(evt);
            }
        });

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
                .addGap(16, 16, 16)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3))
                        .addGap(28, 28, 28)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(diachisanTxt)
                            .addComponent(tensanTxt)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(saveFBFInfo)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel4)
                                        .addGap(18, 18, 18)
                                        .addComponent(gioDCSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel6)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(phutDCSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(77, 77, 77)
                                        .addComponent(closeButton))))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(gioMCSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(phutMCSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(92, 92, 92)))
                        .addGap(0, 73, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(tensanTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(diachisanTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(gioMCSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(phutMCSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(gioDCSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(phutDCSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(saveFBFInfo)
                    .addComponent(closeButton))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void saveFBFInfoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_saveFBFInfoMouseClicked
        String tenSan = tensanTxt.getText().trim();
        String diaChiSan = diachisanTxt.getText().trim();
        String gioMoCua = gioMCSpinner.getValue() + ":" + phutMCSpinner.getValue();
        String gioDongCua = gioDCSpinner.getValue() + ":" + phutDCSpinner.getValue();
        if(tenSan.length() == 0 || diaChiSan.length() == 0){
            JOptionPane.showMessageDialog(this, "Không được để trống thông tin");
            return;
        }
        DateTimeFormatter inputFormat = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime OPEN_TIME = LocalTime.parse(gioMoCua, inputFormat);
        LocalTime CLOSE_TIME = LocalTime.parse(gioDongCua, inputFormat);
        String sql = "SELECT GioBD, GioKT FROM PhieuDatSan WHERE TrangThai = ? OR TrangThai = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "Đang Đặt");
            stmt.setString(2, "Đã Nhận Sân");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                LocalTime bookedStartTime = rs.getTime("GioBD").toLocalTime();
                LocalTime bookedEndTime = rs.getTime("GioKT").toLocalTime();
                if(bookedStartTime.isBefore(OPEN_TIME) || bookedEndTime.isAfter(CLOSE_TIME)){
                    JOptionPane.showMessageDialog(this, "Hiện đang có lịch đặt ngoài khoảng thời gian này. Xin vui lòng kiểm tra lại");
                    return;
                }
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        updateFBFInfo(tenSan, diaChiSan, gioMoCua, gioDongCua, maSanBong);
        JOptionPane.showMessageDialog(this, "Đã thay đổi thông tin sân bóng thành công");
        this.dispose();
    }//GEN-LAST:event_saveFBFInfoMouseClicked

    private void closeButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_closeButtonMouseClicked
        this.dispose();
    }//GEN-LAST:event_closeButtonMouseClicked


    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton closeButton;
    private javax.swing.JTextField diachisanTxt;
    private javax.swing.JSpinner gioDCSpinner;
    private javax.swing.JSpinner gioMCSpinner;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JSpinner phutDCSpinner;
    private javax.swing.JSpinner phutMCSpinner;
    private javax.swing.JButton saveFBFInfo;
    private javax.swing.JTextField tensanTxt;
    // End of variables declaration//GEN-END:variables
}
