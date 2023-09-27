package Form;

import DataBase.ConnectDB;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.NumberFormat;
import java.util.Locale;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

public class PlusCharge extends javax.swing.JInternalFrame {
    ConnectDB cn = new ConnectDB();
    Connection conn;
    Locale locale = new Locale("vn", "VN");      
    NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);
    String CODE = "INSERT", maPC, maNV;
    
    public void SelectAllPC(){
        try {
            String select_PC = "Select * from DichVu where LoaiDV = ?";
            conn = cn.getConnection();
            PreparedStatement pst = conn.prepareStatement(select_PC);
            pst.setString(1, "Phụ Phí");
            ResultSet pc = pst.executeQuery();
            DefaultTableModel model = (DefaultTableModel) pcTable.getModel();
            String MAPC, TENPC, DonGia, DVT, GhiChu;
            while(pc.next()){
                MAPC = pc.getString("MaDV");
                TENPC = pc.getString("TenDichVu");
                DonGia = currencyFormatter.format(Double.parseDouble(pc.getString("DonGia")));
                DVT = pc.getString("DVT");
                GhiChu = pc.getString("GhiChu");
                String[] rtr = {MAPC, TENPC, DonGia, DVT, GhiChu};
                model.addRow(rtr);               
            }
        } catch (Exception e) {
            e.printStackTrace();
            e.printStackTrace();
        }
    }
    
    public final void Reset(){
        DefaultTableModel dm = (DefaultTableModel)pcTable.getModel();
        dm.getDataVector().removeAllElements();
        dm.fireTableDataChanged(); 
        SelectAllPC();
        ColumnsAutoSizer.sizeColumnsToFit(pcTable);
        pcName.setText("");
        pcDVT.setText("");
        pcPrice.setText("");
        ghichuTxt.setText("");
        moneyLable.setText("");
        
    }
    
    public PlusCharge() {
        initComponents();
        ImageIcon img = new ImageIcon("D:\\Football_Filed_Managerment\\src\\images\\icons/—Pngtree—football field plan_4710125 (1).png");
        this.setFrameIcon(img);
        Reset();
        pcTable.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent mouseEvent) {
                JTable table =(JTable) mouseEvent.getSource();
                java.awt.Point point = mouseEvent.getPoint();
                int row = table.rowAtPoint(point);
                if(row == -1){
                    return;
                }
                maPC = pcTable.getModel().getValueAt(row, 0).toString();
                if (mouseEvent.getClickCount() == 1 && table.getSelectedRow() != -1) {
                    try {
                        conn = cn.getConnection();
                        String select_1PC = "Select * from DichVu where MaDV = ?";
                        PreparedStatement pst = conn.prepareStatement(select_1PC);
                        pst.setString(1, maPC);
                        ResultSet pc = pst.executeQuery();
                        if(pc.next()){
                            pcName.setText(pc.getString("TenDichVu"));
                            pcDVT.setText(pc.getString("DVT"));
                            pcPrice.setText(pc.getString("DonGia"));
                            moneyLable.setText(currencyFormatter.format(Double.parseDouble(pcPrice.getText())));
                            ghichuTxt.setText(pc.getString("GhiChu"));
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

        jLabel1 = new javax.swing.JLabel();
        pcName = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        pcDVT = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        pcPrice = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        ghichuTxt = new javax.swing.JTextField();
        searchTxt = new javax.swing.JTextField();
        addPC = new javax.swing.JButton();
        savePC = new javax.swing.JButton();
        closeButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        pcTable = new javax.swing.JTable();
        moneyLable = new javax.swing.JLabel();

        setTitle("Quản Lý Phụ Phí");

        jLabel1.setForeground(new java.awt.Color(41, 128, 185));
        jLabel1.setText("Tên Phụ Phí");

        pcName.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(41, 128, 185)));

        jLabel4.setForeground(new java.awt.Color(41, 128, 185));
        jLabel4.setText("Đơn Vị Tính");
        jLabel4.setToolTipText("");

        jLabel3.setForeground(new java.awt.Color(41, 128, 185));
        jLabel3.setText("Đơn Giá");

        pcPrice.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(41, 128, 185)));
        pcPrice.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                pcPriceKeyReleased(evt);
            }
        });

        jLabel5.setForeground(new java.awt.Color(41, 128, 185));
        jLabel5.setText("Ghi Chú");
        jLabel5.setToolTipText("");

        ghichuTxt.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(41, 128, 185)));

        searchTxt.setText("Nhập để tìm kiếm");
        searchTxt.setToolTipText("");
        searchTxt.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(41, 128, 185)));
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

        addPC.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/056-plus.png"))); // NOI18N
        addPC.setText("Thêm");
        addPC.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(41, 128, 185)));
        addPC.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                addPCMouseClicked(evt);
            }
        });

        savePC.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/022-save.png"))); // NOI18N
        savePC.setText("Lưu");
        savePC.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(41, 128, 185)));
        savePC.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                savePCMouseClicked(evt);
            }
        });

        closeButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/058-error.png"))); // NOI18N
        closeButton.setText("Đóng");
        closeButton.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(41, 128, 185)));
        closeButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                closeButtonMouseClicked(evt);
            }
        });

        pcTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã Phụ Phí", "Tên Phụ Phí", "Đơn Giá", "Đơn Vị Tính ", "Ghi Chú"
            }
        ));
        jScrollPane1.setViewportView(pcTable);

        moneyLable.setText("                      ");
        moneyLable.setToolTipText("");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1)
                        .addGap(22, 22, 22))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel3))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(moneyLable)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(pcPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel5)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 47, Short.MAX_VALUE)
                                        .addComponent(ghichuTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(pcName, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel4)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(pcDVT, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap(35, Short.MAX_VALUE))))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(searchTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(addPC, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(savePC, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(closeButton, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel4)
                        .addComponent(pcDVT))
                    .addComponent(pcName, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel3)
                    .addComponent(jLabel5)
                    .addComponent(pcPrice, javax.swing.GroupLayout.DEFAULT_SIZE, 20, Short.MAX_VALUE)
                    .addComponent(ghichuTxt))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(moneyLable)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(searchTxt, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(addPC, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(savePC, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(closeButton, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(20, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void pcPriceKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_pcPriceKeyReleased
        String DonGia = null;
        if(Validate.notNumber(pcPrice.getText()) == true){
            JOptionPane.showMessageDialog(this, "Đơn giá phải là số");
            return;
        }
        if(pcPrice.getText().length() <= 0){
            moneyLable.setText("");
            return;
        }
        DonGia = currencyFormatter.format(Double.parseDouble(pcPrice.getText()));
        moneyLable.setText(String.valueOf(DonGia));
    }//GEN-LAST:event_pcPriceKeyReleased

    private void searchTxtMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_searchTxtMouseClicked
        searchTxt.setText("");
    }//GEN-LAST:event_searchTxtMouseClicked

    public void filter(String query){
        DefaultTableModel dm = (DefaultTableModel) pcTable.getModel();
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<DefaultTableModel>(dm);
        pcTable.setRowSorter(tr);
        tr.setRowFilter(RowFilter.regexFilter(query));
    }
        
    private void searchTxtKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchTxtKeyReleased
        String query = searchTxt.getText().trim();
        filter(query);
    }//GEN-LAST:event_searchTxtKeyReleased

    private void addPCMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addPCMouseClicked
        Reset();
        CODE = "INSERT";
    }//GEN-LAST:event_addPCMouseClicked

    public void insertPC( String tenPC, String dongia, String dvt, String ghichu, String loaiDV ){
        try{
            String insert = "INSERT INTO DichVu(TenDichVu, DonGia, DVT, GhiChu, LoaiDV) VALUES(?,?,?,?,?)";
            conn = cn.getConnection();
            PreparedStatement pst = conn.prepareStatement(insert);
            pst.setString(1, tenPC);
            pst.setString(2, dongia);
            pst.setString(3, dvt);
            pst.setString(4, ghichu);
            pst.setString(5, loaiDV);
            pst.execute();
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void updatePC(String tenPC, String dongia, String dvt, String ghichu, String maPC){
        try{
            String update = "UPDATE DichVu SET TenDichVu = ?, DonGia = ?, DVT = ?, GhiChu = ? WHERE MaDV = ?";
            conn = cn.getConnection();
            PreparedStatement pst = conn.prepareStatement(update);
            pst.setString(1, tenPC);
            pst.setString(2, dongia);
            pst.setString(3, dvt);
            pst.setString(4, ghichu);
            pst.setString(5, maPC);
            pst.execute();
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    
    private void savePCMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_savePCMouseClicked
        String tenPC = pcName.getText().toString().trim();
        String dongia = pcPrice.getText().toString().trim();
        String dvt = pcDVT.getText().toString().trim();
        String ghichu = ghichuTxt.getText().toString().trim();
        String loaiDV = "Phụ Phí";
        if(tenPC.length() == 0 || dongia.length() == 0 || dvt.length() == 0 ){
            JOptionPane.showMessageDialog(this, "Không được để trống thông tin");
            return;
        }
        StringBuffer sb = new StringBuffer();
        if(sb.length()>0){
            JOptionPane.showMessageDialog(this, sb.toString());
            return;
        }
        if( CODE.equals("INSERT") == true){
            String select_DV = "Select * from DichVu";
            try{
                conn = cn.getConnection();
                PreparedStatement pst = conn.prepareStatement(select_DV);
                ResultSet dv = pst.executeQuery();
                while(dv.next()){
                    if(dv.getString("TenDichVu").trim().equals(tenPC) == true){
                        sb.append("Phụ Phí này có thể đã tồn tại");
                    }
                }
            } catch(Exception e){
                e.printStackTrace();
            }
            if(sb.length()>0){
                JOptionPane.showMessageDialog(this, sb.toString());
                return;
            }
            insertPC(tenPC, dongia, dvt, ghichu, loaiDV);
            JOptionPane.showMessageDialog(this, "Đã Thêm Phụ Phí Thành Công");
            Reset();
        }
        if( CODE.equals("UPDATE") == true){
            updatePC(tenPC, dongia, dvt, ghichu, maPC);
            JOptionPane.showMessageDialog(this, "Chỉnh Sửa Thông Tin Phụ Phí Thành Công");
            Reset();
        }
    }//GEN-LAST:event_savePCMouseClicked

    private void closeButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_closeButtonMouseClicked
        this.dispose();
    }//GEN-LAST:event_closeButtonMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addPC;
    private javax.swing.JButton closeButton;
    private javax.swing.JTextField ghichuTxt;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel moneyLable;
    private javax.swing.JTextField pcDVT;
    private javax.swing.JTextField pcName;
    private javax.swing.JTextField pcPrice;
    private javax.swing.JTable pcTable;
    private javax.swing.JButton savePC;
    private javax.swing.JTextField searchTxt;
    // End of variables declaration//GEN-END:variables

}
