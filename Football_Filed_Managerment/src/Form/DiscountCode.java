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
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

public class DiscountCode extends javax.swing.JInternalFrame {
    ConnectDB cn = new ConnectDB();
    Connection conn;
    Locale locale = new Locale("vn", "VN");      
    NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);
    String CODE = "INSERT", maKM = null;
    
    public void SelectAllMaKM(){
        try {
            String select_KM = "Select * from MaKhuyenMai";
            conn = cn.getConnection();
            PreparedStatement pst = conn.prepareStatement(select_KM);
            ResultSet km = pst.executeQuery();
            DefaultTableModel model = (DefaultTableModel) kmTable.getModel();
            String MaKM, PTKM, HAN, NOIDUNG;
            while(km.next()){
                MaKM = km.getString("MaKM");
                PTKM = km.getString("PTKM");
                SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
                SimpleDateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy");
                Date date = inputFormat.parse(km.getString("HanKM"));
                HAN = outputFormat.format(date);
                NOIDUNG = km.getString("NoiDungKM");
                String[] kmr = {MaKM, PTKM, HAN, NOIDUNG};
                model.addRow(kmr);               
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public final void Reset(){
        DefaultTableModel dm = (DefaultTableModel)kmTable.getModel();
        dm.getDataVector().removeAllElements();
        dm.fireTableDataChanged(); 
        SelectAllMaKM();
        ColumnsAutoSizer.sizeColumnsToFit(kmTable);
        maKMTxt.setText(randomMaKM());
        ptKMTxt.setText("");
        hanKMDate.setDate(java.sql.Date.valueOf(java.time.LocalDate.now()));
        noidungKMTxt.setText("");
    }
    
    public String randomMaKM(){
        String randomString = null;
        int n = 6; // số lượng ký tự trong chuỗi
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
        
    public DiscountCode() {
        initComponents();
        ImageIcon img = new ImageIcon("D:\\Football_Filed_Managerment\\src\\images\\icons/—Pngtree—football field plan_4710125 (1).png");
        this.setFrameIcon(img);
        Reset();
        kmTable.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent mouseEvent) {
                JTable table =(JTable) mouseEvent.getSource();
                java.awt.Point point = mouseEvent.getPoint();
                int row = table.rowAtPoint(point);
                if(row == -1){
                    return;
                }
                maKM = kmTable.getModel().getValueAt(row, 0).toString();
                if (mouseEvent.getClickCount() == 1 && table.getSelectedRow() != -1) {
                    try {
                        conn = cn.getConnection();
                        String select_1KM = "Select * from MaKhuyenMai where MaKM = ?";
                        PreparedStatement pst = conn.prepareStatement(select_1KM);
                        pst.setString(1, maKM);
                        ResultSet km = pst.executeQuery();
                        if(km.next()){
                            maKMTxt.setText(maKM);
                            ptKMTxt.setText(km.getString("PTKM"));
                            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
                            SimpleDateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy");
                            Date date = inputFormat.parse(km.getString("HanKM"));
                            hanKMDate.setDate(date);
                            noidungKMTxt.setText(km.getString("NoiDungKM"));
                            
                        }
                        CODE = "UPDATE";
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                }  
            }
        });
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        ptKMTxt = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        addPC = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        savePC = new javax.swing.JButton();
        searchTxt = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        kmTable = new javax.swing.JTable();
        closeButton = new javax.swing.JButton();
        maKMTxt = new javax.swing.JTextField();
        hanKMDate = new com.toedter.calendar.JDateChooser();
        jScrollPane2 = new javax.swing.JScrollPane();
        noidungKMTxt = new javax.swing.JTextArea();

        setForeground(new java.awt.Color(255, 255, 255));
        setTitle("Quản Lý Mã Khuyến Mãi");

        ptKMTxt.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(41, 128, 185)));
        ptKMTxt.setPreferredSize(new java.awt.Dimension(3, 21));

        jLabel3.setForeground(new java.awt.Color(41, 128, 185));
        jLabel3.setText("Hạn Mã");

        jLabel5.setForeground(new java.awt.Color(41, 128, 185));
        jLabel5.setText("Nội Dung ");
        jLabel5.setToolTipText("");

        addPC.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/056-plus.png"))); // NOI18N
        addPC.setText("Thêm");
        addPC.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(41, 128, 185)));
        addPC.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                addPCMouseClicked(evt);
            }
        });

        jLabel2.setBackground(new java.awt.Color(255, 255, 255));
        jLabel2.setForeground(new java.awt.Color(41, 128, 185));
        jLabel2.setText("Mã Khuyến Mãi");

        jLabel1.setForeground(new java.awt.Color(41, 128, 185));
        jLabel1.setText("Phần Trăm Khuyến Mãi");

        savePC.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/022-save.png"))); // NOI18N
        savePC.setText("Lưu");
        savePC.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(41, 128, 185)));
        savePC.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                savePCMouseClicked(evt);
            }
        });

        searchTxt.setText("Nhập để tìm kiếm");
        searchTxt.setToolTipText("");
        searchTxt.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(41, 128, 185)));
        searchTxt.setPreferredSize(new java.awt.Dimension(84, 21));
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

        kmTable.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(41, 128, 185), 1, true));
        kmTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã Khuyến Mãi", "Phần Trăm Khuyến Mãi", "Hạn ", "Nội Dung"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(kmTable);

        closeButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/058-error.png"))); // NOI18N
        closeButton.setText("Đóng");
        closeButton.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(41, 128, 185)));
        closeButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                closeButtonMouseClicked(evt);
            }
        });

        maKMTxt.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(41, 128, 185), 1, true));
        maKMTxt.setPreferredSize(new java.awt.Dimension(7, 21));

        hanKMDate.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(41, 128, 185), 1, true));
        hanKMDate.setPreferredSize(new java.awt.Dimension(74, 21));

        noidungKMTxt.setColumns(20);
        noidungKMTxt.setRows(5);
        noidungKMTxt.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(41, 128, 185), 1, true));
        jScrollPane2.setViewportView(noidungKMTxt);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(searchTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(addPC, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(savePC, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(closeButton, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 165, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel1)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel5))
                                .addGap(20, 20, 20)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(ptKMTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(hanKMDate, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(maKMTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addComponent(jScrollPane2))))
                        .addGap(22, 22, 22))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(maKMTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(ptKMTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(14, 14, 14)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(hanKMDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(searchTxt, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(addPC, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(savePC, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(closeButton, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(21, 21, 21)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(14, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void addPCMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addPCMouseClicked
        Reset();
        CODE = "INSERT";
    }//GEN-LAST:event_addPCMouseClicked
    
    public void insertKM(String maKM, String ptKM, String hanKM, String noidungKM){
        try{
            String insert = "INSERT INTO MaKhuyenMai(MaKM, PTKM, HanKM, NoiDungKM) VALUES(?,?,?,?)";
            conn = cn.getConnection();
            PreparedStatement pst = conn.prepareStatement(insert);
            pst.setString(1, maKM);
            pst.setString(2, ptKM);
            pst.setString(3, hanKM);
            pst.setString(4, noidungKM);
            pst.execute();
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void updateKM(String maKM, String ptKM, String hanKM, String noidungKM){
        try{
            String update = "UPDATE MaKhuyenMai SET PTKM = ?, HanKM = ?, NoiDungKM = ? WHERE MaKM = ?";
            conn = cn.getConnection();
            PreparedStatement pst = conn.prepareStatement(update);
            pst.setString(1, ptKM);
            pst.setString(2, hanKM);
            pst.setString(3, noidungKM);
            pst.setString(4, maKM);
            pst.execute();
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    
    private void savePCMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_savePCMouseClicked
        String maKM = maKMTxt.getText().trim();
        if(maKM == null){
            maKM = randomMaKM();
        }
        String ptKM = ptKMTxt.getText().trim();
        java.sql.Date hanKM = new java.sql.Date(hanKMDate.getDate().getTime());
        String ndKM = noidungKMTxt.getText().trim();
        if(maKM.length() == 0 || ptKM.length() == 0 || ndKM.length() == 0){
            JOptionPane.showMessageDialog(this, "Không được để trống thông tin");
            return;
        }
        if(Validate.notNumber(ptKM) == true){
            JOptionPane.showMessageDialog(this, "Phần trăm khuyến mãi phải là số");
            return;
        }
        if( CODE.equals("INSERT") == true){
            String select_KM = "Select * from MaKhuyenMai where MaKM = ?";
            try{
                conn = cn.getConnection();
                PreparedStatement pst = conn.prepareStatement(select_KM);
                pst.setString(1, maKM);
                ResultSet km = pst.executeQuery();
                while(km.next()){
                    if(km.getString("MaKM").equals(maKM)){
                        JOptionPane.showMessageDialog(this, "Mã Khuyến Mại Đã Tồn Tại");
                        return;
                    }
                }
            } catch(Exception e){
                e.printStackTrace();
            }
            insertKM(maKM, ptKM, String.valueOf(hanKM), ndKM);
            JOptionPane.showMessageDialog(this, "Đã Thêm Mã Khuyến Mại Thành Công");
            Reset();
        }
        if( CODE.equals("UPDATE") == true){
            updateKM(maKM, ptKM, String.valueOf(hanKM), ndKM);
            JOptionPane.showMessageDialog(this, "Chỉnh Sửa Mã Khuyến Mại Thành Công");
            Reset();
        }
    }//GEN-LAST:event_savePCMouseClicked

    private void searchTxtMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_searchTxtMouseClicked
        searchTxt.setText("");
    }//GEN-LAST:event_searchTxtMouseClicked

    public void filter(String query){
        DefaultTableModel dm = (DefaultTableModel) kmTable.getModel();
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<DefaultTableModel>(dm);
        kmTable.setRowSorter(tr);
        tr.setRowFilter(RowFilter.regexFilter(query));
    }
    
    private void searchTxtKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchTxtKeyReleased
        String query = searchTxt.getText().trim();
        filter(query);
    }//GEN-LAST:event_searchTxtKeyReleased

    private void closeButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_closeButtonMouseClicked
        this.dispose();
    }//GEN-LAST:event_closeButtonMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addPC;
    private javax.swing.JButton closeButton;
    private com.toedter.calendar.JDateChooser hanKMDate;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable kmTable;
    private javax.swing.JTextField maKMTxt;
    private javax.swing.JTextArea noidungKMTxt;
    private javax.swing.JTextField ptKMTxt;
    private javax.swing.JButton savePC;
    private javax.swing.JTextField searchTxt;
    // End of variables declaration//GEN-END:variables
}
