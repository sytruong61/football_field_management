package Form;

import DataBase.ConnectDB;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.text.NumberFormat;
import java.util.Locale;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

public class HangKhach extends javax.swing.JInternalFrame {
    ConnectDB cn = new ConnectDB();
    Connection conn;
    String CODE = "INSERT", maHK;
    Locale locale = new Locale("vn", "VN");      
    NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);
    
    public void Reset(){
        tenhangkhachTxt.setText("");
        ptSpiner.setValue(0);
        chitieuTxt.setText("");
        moneyLable.setText("");
        ghichuTxt.setText("");
        CODE = "INSERT";
        SelectAllHK();
        ColumnsAutoSizer.sizeColumnsToFit(hkTable);
    }

    public void SelectAllHK(){
        DefaultTableModel dm = (DefaultTableModel)hkTable.getModel();
        dm.getDataVector().removeAllElements();
        dm.fireTableDataChanged();
        String select_HK = "Select * from HangKhachHang";
        try{
            conn = cn.getConnection();
            PreparedStatement pst = conn.prepareStatement(select_HK);
            ResultSet rs = pst.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            DefaultTableModel model = (DefaultTableModel) hkTable.getModel();
            String ID, TenHK, ChiTieu, PTKM, GhiChu;
            while(rs.next()){
               ID = rs.getString("MAHK");
               TenHK = rs.getString("TenHK");
               PTKM = rs.getString("PTKM") + "%";
               Locale locale = new Locale("vn", "VN");      
               NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);
               ChiTieu = currencyFormatter.format(Double.parseDouble(rs.getString("ChiTieu")));
               if(!rs.getString("GhiChu").equals("PAUSED")){
                    GhiChu = rs.getString("GhiChu");
                    String[] hk = {ID, TenHK, PTKM, ChiTieu, GhiChu};
                    model.addRow(hk);
               }
            }
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public HangKhach() {
        initComponents();
        ImageIcon img = new ImageIcon("D:\\Football_Filed_Managerment\\src\\images\\icons/—Pngtree—football field plan_4710125 (1).png");
        this.setFrameIcon(img);
        SelectAllHK();
        ColumnsAutoSizer.sizeColumnsToFit(hkTable);
        hkTable.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent mouseEvent) {
                JTable table =(JTable) mouseEvent.getSource();
                java.awt.Point point = mouseEvent.getPoint();
                int row = table.rowAtPoint(point);
                if(row == -1){
                    return;
                }
                maHK = hkTable.getModel().getValueAt(row, 0).toString();
                if (mouseEvent.getClickCount() == 1 && table.getSelectedRow() != -1) {
                    try {
                        conn = cn.getConnection();
                        String select_1HK = "Select * from HangKhachHang where MAHK = ?";
                        PreparedStatement pst3 = conn.prepareStatement(select_1HK);
                        pst3.setString(1, maHK);
                        ResultSet hangkhach = pst3.executeQuery();
                        if(hangkhach.next()){
                            tenhangkhachTxt.setText(hangkhach.getString("TenHK").trim());
                            ptSpiner.setValue(Integer.parseInt(hangkhach.getString("PTKM").trim()));
                            chitieuTxt.setText(hangkhach.getString("ChiTieu").trim());
                            moneyLable.setText(currencyFormatter.format(Double.parseDouble(hangkhach.getString("ChiTieu").trim())));
                            ghichuTxt.setText(hangkhach.getString("GhiChu"));
                        }
                    } catch (Exception ex) {
                        System.out.println(ex.toString());
                    }
                }
                CODE = "UPDATE";
            }
        });
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        searchTxt = new javax.swing.JTextField();
        addHK = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        hkTable = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        tenhangkhachTxt = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        ptSpiner = new javax.swing.JSpinner();
        jLabel1 = new javax.swing.JLabel();
        chitieuTxt = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        moneyLable = new javax.swing.JLabel();
        ghichuTxt = new javax.swing.JTextField();
        lable14 = new javax.swing.JLabel();
        saveHK = new javax.swing.JButton();

        setTitle("Quản Lý Hạng Khách");
        setToolTipText("");

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

        addHK.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/056-plus.png"))); // NOI18N
        addHK.setText("Thêm");
        addHK.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                addHKMouseClicked(evt);
            }
        });

        cancelButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/058-error.png"))); // NOI18N
        cancelButton.setText("Hủy");
        cancelButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cancelButtonMouseClicked(evt);
            }
        });

        hkTable.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(41, 128, 185), 1, true));
        hkTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã Hạng Khách", "Tên Hạng Khách", "Phần Trăm Khuyến Mãi", "Tổng chi tiêu cần đạt", "Ghi Chú"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, true, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(hkTable);

        jLabel2.setForeground(new java.awt.Color(41, 128, 185));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Tên Hạng Khách");

        tenhangkhachTxt.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(41, 128, 185), 1, true));
        tenhangkhachTxt.setPreferredSize(new java.awt.Dimension(0, 21));

        jLabel4.setForeground(new java.awt.Color(41, 128, 185));
        jLabel4.setText("Phần trăm khuyến mãi");

        ptSpiner.setModel(new javax.swing.SpinnerNumberModel(0, 0, 100, 1));
        ptSpiner.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(41, 128, 185)));
        ptSpiner.setPreferredSize(new java.awt.Dimension(26, 21));

        jLabel1.setForeground(new java.awt.Color(41, 128, 185));
        jLabel1.setText("%");

        chitieuTxt.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(41, 128, 185), 1, true));
        chitieuTxt.setPreferredSize(new java.awt.Dimension(0, 21));
        chitieuTxt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                chitieuTxtKeyReleased(evt);
            }
        });

        jLabel13.setForeground(new java.awt.Color(41, 128, 185));
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setText("Chi Tiêu");

        ghichuTxt.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(41, 128, 185), 1, true));
        ghichuTxt.setPreferredSize(new java.awt.Dimension(0, 21));

        lable14.setForeground(new java.awt.Color(41, 128, 185));
        lable14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lable14.setText("Ghi Chú");

        saveHK.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/022-save.png"))); // NOI18N
        saveHK.setText("Lưu");
        saveHK.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                saveHKMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(tenhangkhachTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lable14, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(ghichuTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                        .addComponent(searchTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(addHK)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(chitieuTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(ptSpiner, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(jLabel1)))
                                        .addGap(4, 4, 4)))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(moneyLable, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(48, 48, 48)
                                        .addComponent(saveHK)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 48, Short.MAX_VALUE)
                                        .addComponent(cancelButton)))))
                        .addGap(22, 22, 22))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(tenhangkhachTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ptSpiner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel1))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel13)
                        .addComponent(chitieuTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(moneyLable, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 20, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lable14)
                    .addComponent(ghichuTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(searchTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(saveHK)
                    .addComponent(addHK)
                    .addComponent(cancelButton))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 357, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(21, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void searchTxtMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_searchTxtMouseClicked
        searchTxt.setText("");
    }//GEN-LAST:event_searchTxtMouseClicked

    private void searchTxtKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchTxtKeyReleased
        String query = searchTxt.getText().trim();
        filter(query);
    }//GEN-LAST:event_searchTxtKeyReleased
    
    private void addHKMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addHKMouseClicked
        try {
            tenhangkhachTxt.setText("");
            ptSpiner.setValue(0);
            chitieuTxt.setText("");
            moneyLable.setText("");
            ghichuTxt.setText("");
            CODE = "INSERT";
        } catch(Exception e){
            e.printStackTrace();
        }
    }//GEN-LAST:event_addHKMouseClicked
    
    public void filter(String query){
        DefaultTableModel dm = (DefaultTableModel) hkTable.getModel();
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<DefaultTableModel>(dm);
        hkTable.setRowSorter(tr);
        tr.setRowFilter(RowFilter.regexFilter(query));
    }
    
    private void cancelButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cancelButtonMouseClicked
        this.dispose();
    }//GEN-LAST:event_cancelButtonMouseClicked

    private void chitieuTxtKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_chitieuTxtKeyReleased
        String ChiTieu = null;
        Locale locale = new Locale("vn", "VN");
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);
        if(Validate.notNumber(chitieuTxt.getText()) == true){
            JOptionPane.showMessageDialog(this, "Chi tiêu phải là số");
            return;
        }
        if(chitieuTxt.getText().length() <= 0){
            moneyLable.setText("");
            return;
        }
        ChiTieu = currencyFormatter.format(Double.parseDouble(chitieuTxt.getText()));
        moneyLable.setText(String.valueOf(ChiTieu));
    }//GEN-LAST:event_chitieuTxtKeyReleased
    
    public void insertHK(String tenHK, String ptkm, String chitieu, String ghichu){
        try{
            String insert = "INSERT INTO HangKhachHang(TenHK, PTKM, ChiTieu,GhiChu) VALUES(?,?,?,?)";
            conn = cn.getConnection();
            PreparedStatement pst2 = conn.prepareStatement(insert);
            pst2.setString(1, tenHK);
            pst2.setString(2, ptkm);
            pst2.setString(3, chitieu);
            pst2.setString(4, ghichu);
            pst2.execute();
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void updateHK(String tenHK, String ptkm, String chitieu, String maHK, String ghichu){
        try{
            String update = "UPDATE HangKhachHang SET TenHK = ?, PTKM = ?, ChiTieu = ?, GhiChu = ? WHERE MAHK = ?";
            conn = cn.getConnection();
            PreparedStatement pst2 = conn.prepareStatement(update);
            pst2.setString(1, tenHK);
            pst2.setString(2, ptkm);
            pst2.setString(3, chitieu);
            pst2.setString(4, ghichu);
            pst2.setString(5, maHK);
            pst2.execute();
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    
    private void saveHKMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_saveHKMouseClicked
        String tenHK = tenhangkhachTxt.getText().trim();
        String ptkm = String.valueOf(ptSpiner.getValue());
        String chitieu = chitieuTxt.getText().trim();
        String ghichu = ghichuTxt.getText().trim();
        StringBuffer sb = new StringBuffer();
        if(tenHK.length() == 0){
            sb.append("Tên bộ phận không được để trống!\n");
        }
        if(chitieu.length() == 0){
            sb.append("Tổng chi tiêu không được để trống!\n");
        }
        if( Float.parseFloat(ptkm) < 0 || Float.parseFloat(ptkm) > 100 ){
            sb.append("Phần trăm khuyến mãi phải từ 0% đến 100%!\n");
        }
        if(sb.length()>0){
            JOptionPane.showMessageDialog(this, sb.toString());
            return;
        }
        if( CODE.equals("INSERT") == true){
            String select_HK = "Select * from HangKhachHang";
            try{
                conn = cn.getConnection();
                PreparedStatement pst = conn.prepareStatement(select_HK);
                ResultSet hangkhach = pst.executeQuery();
                while(hangkhach.next()){
                    if(hangkhach.getString("TenHK").trim().equals(tenHK) == true){
                        sb.append("Hạng khách đã tồn tại!\n");
                    }
                }
            } catch(Exception e){
                e.printStackTrace();
            }
            if(sb.length()>0){
                JOptionPane.showMessageDialog(this, sb.toString());
                return;
            }
            insertHK(tenHK,ptkm,chitieu,ghichu);
            JOptionPane.showMessageDialog(this, "Đã Thêm Hạng Khách Thành Công");
            Reset();
            return;
        }
        if( CODE.equals("UPDATE") == true){
            updateHK(tenHK,ptkm,chitieu,maHK,ghichu);
            JOptionPane.showMessageDialog(this, "Chỉnh Sửa Thông Tin Hạng Khách Thành Công");
            Reset();
            return;
        }
    }//GEN-LAST:event_saveHKMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addHK;
    private javax.swing.JButton cancelButton;
    private javax.swing.JTextField chitieuTxt;
    private javax.swing.JTextField ghichuTxt;
    private javax.swing.JTable hkTable;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lable14;
    private javax.swing.JLabel moneyLable;
    private javax.swing.JSpinner ptSpiner;
    private javax.swing.JButton saveHK;
    private javax.swing.JTextField searchTxt;
    private javax.swing.JTextField tenhangkhachTxt;
    // End of variables declaration//GEN-END:variables
}
