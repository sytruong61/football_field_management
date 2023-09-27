package Form;

import DataBase.ConnectDB;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;


public class FootballFiled extends javax.swing.JInternalFrame {
    ConnectDB cn = new ConnectDB();
    Connection conn;
    String CODE = "INSERT", maFBF;
    Locale locale = new Locale("vn", "VN");      
    NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);
     
    public final void SelectSan(String ID){
        try{
            conn = cn.getConnection();
            String select_1FBF = "Select * from SanBong where MaSan = ?";
            PreparedStatement pst3 = conn.prepareStatement(select_1FBF);
            pst3.setString(1, ID);
            ResultSet fbf = pst3.executeQuery();
            if(fbf.next()){
                tensanTxt.setText(fbf.getString("TenSan").trim());
                loaisanBox.setSelectedItem(fbf.getString("LoaiSan").trim());
                statusBox.setSelectedItem(fbf.getString("TinhTrang").trim());
                ghichuTxt.setText(fbf.getString("GhiChu").trim());                
                ampriceTxt.setText(fbf.getString("GiaTruoc17H"));
                ampriceLbl.setText(currencyFormatter.format(Double.parseDouble(fbf.getString("GiaTruoc17H"))));
                pmpriceTxt.setText(fbf.getString("GiaSau17H"));
                pmpriceLbl.setText(currencyFormatter.format(Double.parseDouble(fbf.getString("GiaSau17H"))));
                wkpriceTxt.setText(fbf.getString("GiaCuoiTuan"));
                wkpriceLbl.setText(currencyFormatter.format(Double.parseDouble(fbf.getString("GiaCuoiTuan"))));
                hldpriceTxt.setText(fbf.getString("GiaNgayLe"));
                hldpriceLbl.setText(currencyFormatter.format(Double.parseDouble(fbf.getString("GiaNgayLe"))));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
        
    public final int selectSanwithtenSan(String tenSan){
        try {
            int ID = 0;
            String select_1FBF = "Select * from SanBong where TenSan = ?";
            PreparedStatement pst3 = conn.prepareStatement(select_1FBF);
            pst3.setString(1, tenSan);
            ResultSet fbf = pst3.executeQuery();
            if(fbf.next()){
                ID = Integer.parseInt(fbf.getString("MaSan"));
                return ID;
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    public void SelectAllFBF(){
        DefaultTableModel dm = (DefaultTableModel)fbfTable.getModel();
        dm.getDataVector().removeAllElements();
        dm.fireTableDataChanged();
        String select_FBF = "Select * from SanBong";
        try{
            conn = cn.getConnection();
            PreparedStatement pst = conn.prepareStatement(select_FBF);
            ResultSet rs = pst.executeQuery();
            DefaultTableModel model = (DefaultTableModel) fbfTable.getModel();
            String ID, TenSan, LoaiSan, TinhTrang, AMPRICE = null, PMPRICE = null, WKPRICE = null, HLDPRICE = null, GhiChu;
            while(rs.next()){
                ID = rs.getString("MaSan");
                TenSan = rs.getString("TenSan");
                LoaiSan = rs.getString("LoaiSan");
                TinhTrang = rs.getString("TinhTrang");
                Locale locale = new Locale("vn", "VN"); 
                NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);                    
                AMPRICE = currencyFormatter.format(Double.parseDouble(rs.getString("GiaTruoc17H")));
                PMPRICE = currencyFormatter.format(Double.parseDouble(rs.getString("GiaSau17H")));
                WKPRICE = currencyFormatter.format(Double.parseDouble(rs.getString("GiaCuoiTuan")));
                HLDPRICE = currencyFormatter.format(Double.parseDouble(rs.getString("GiaNgayLe")));  
                GhiChu = rs.getString("GhiChu");
                String[] fbf = {ID, TenSan, LoaiSan, TinhTrang, AMPRICE, PMPRICE, WKPRICE, HLDPRICE, GhiChu};
                model.addRow(fbf);
            }
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void Reset(){
        SelectAllFBF();
        ColumnsAutoSizer.sizeColumnsToFit(fbfTable);
        CODE = "INSERT";
        tensanTxt.setText("");
        loaisanBox.setSelectedItem("Sân 5 Người");
        statusBox.setSelectedItem("Đang Trống");
        ampriceTxt.setText("");
        pmpriceTxt.setText("");
        wkpriceTxt.setText("");
        hldpriceTxt.setText("");
        ghichuTxt.setText("");
        ampriceLbl.setText("");
        pmpriceLbl.setText("");
        wkpriceLbl.setText("");
        hldpriceLbl.setText("");
    }
    
    public FootballFiled() {
        initComponents();
        ImageIcon img = new ImageIcon("D:\\Football_Filed_Managerment\\src\\images\\icons/—Pngtree—football field plan_4710125 (1).png");
        this.setFrameIcon(img);
        SelectAllFBF();
        statusBox.setEnabled(false);
        ColumnsAutoSizer.sizeColumnsToFit(fbfTable);
        fbfTable.getColumnModel().getColumn(0).setWidth(0);
        fbfTable.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent mouseEvent) {
                JTable table =(JTable) mouseEvent.getSource();
                Point point = mouseEvent.getPoint();
                int row = table.rowAtPoint(point);
                if(row == -1){
                    return;
                }
                maFBF = fbfTable.getModel().getValueAt(row, 0).toString();
                if (mouseEvent.getClickCount() == 1 && table.getSelectedRow() != -1) {
                    try {
                        SelectSan(maFBF);
                        CODE = "UPDATE";
                    } catch (Exception ex) {
                        System.out.println(ex.toString());
                    }
                }   
            }
        });
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        searchTxt = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        fbfTable = new javax.swing.JTable();
        addFBF = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        tensanTxt = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        loaisanBox = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        statusBox = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        ampriceTxt = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        pmpriceTxt = new javax.swing.JTextField();
        ampriceLbl = new javax.swing.JLabel();
        wkpriceTxt = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        pmpriceLbl = new javax.swing.JLabel();
        hldpriceTxt = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        wkpriceLbl = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        ghichuTxt = new javax.swing.JTextField();
        hldpriceLbl = new javax.swing.JLabel();
        savefbfButton = new javax.swing.JButton();
        cancelButton1 = new javax.swing.JButton();

        setTitle("Quản Lý Sân Bóng");

        searchTxt.setText("Nhập để tìm kiếm");
        searchTxt.setToolTipText("");
        searchTxt.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(41, 128, 185), 1, true));
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

        fbfTable.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(41, 128, 185), 1, true));
        fbfTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã Sân", "Tên Sân", "Loại Sân", "Tình trạng", "Giá Buổi Sáng", "Giá Buổi Chiều", "Giá Cuối Tuần ", "Giá Ngày Lễ", "Ghi Chú"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, true, true, true, true, true, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(fbfTable);

        addFBF.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/056-plus.png"))); // NOI18N
        addFBF.setText("Thêm Sân Bóng");
        addFBF.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                addFBFMouseClicked(evt);
            }
        });

        jLabel1.setForeground(new java.awt.Color(41, 128, 185));
        jLabel1.setText(" Giá Buổi Chiều ( từ 17h đến 22h )");

        jLabel2.setForeground(new java.awt.Color(41, 128, 185));
        jLabel2.setText("Giá Buổi Sáng ( từ 7h đến 17h )");

        jLabel3.setForeground(new java.awt.Color(41, 128, 185));
        jLabel3.setText("Tên Sân");

        tensanTxt.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(41, 128, 185), 1, true));
        tensanTxt.setPreferredSize(new java.awt.Dimension(0, 21));

        jLabel4.setForeground(new java.awt.Color(41, 128, 185));
        jLabel4.setText("Loại Sân");

        loaisanBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Sân 5 Người", "Sân 7 Người", "Sân 11 Người" }));
        loaisanBox.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(41, 128, 185), 1, true));
        loaisanBox.setPreferredSize(new java.awt.Dimension(0, 21));

        jLabel5.setForeground(new java.awt.Color(41, 128, 185));
        jLabel5.setText("Tình Trạng");

        statusBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Đang Trống", "Đang Có Người Thuê", "Đang Bảo Trì" }));
        statusBox.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(41, 128, 185), 1, true));
        statusBox.setPreferredSize(new java.awt.Dimension(0, 21));

        jLabel6.setForeground(new java.awt.Color(41, 128, 185));
        jLabel6.setText("Giá Buổi Sáng");

        ampriceTxt.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(41, 128, 185), 1, true));
        ampriceTxt.setPreferredSize(new java.awt.Dimension(0, 21));
        ampriceTxt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                ampriceTxtKeyReleased(evt);
            }
        });

        jLabel7.setForeground(new java.awt.Color(41, 128, 185));
        jLabel7.setText("Giá Buổi Tối ");

        pmpriceTxt.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(41, 128, 185), 1, true));
        pmpriceTxt.setPreferredSize(new java.awt.Dimension(0, 21));
        pmpriceTxt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                pmpriceTxtKeyReleased(evt);
            }
        });

        ampriceLbl.setText("                                   ");

        wkpriceTxt.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(41, 128, 185), 1, true));
        wkpriceTxt.setPreferredSize(new java.awt.Dimension(0, 21));
        wkpriceTxt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                wkpriceTxtKeyReleased(evt);
            }
        });

        jLabel8.setForeground(new java.awt.Color(41, 128, 185));
        jLabel8.setText("Giá Cuối Tuần");

        pmpriceLbl.setText("                                   ");

        hldpriceTxt.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(41, 128, 185), 1, true));
        hldpriceTxt.setPreferredSize(new java.awt.Dimension(0, 21));
        hldpriceTxt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                hldpriceTxtKeyReleased(evt);
            }
        });

        jLabel9.setForeground(new java.awt.Color(41, 128, 185));
        jLabel9.setText("Giá Ngày Lễ");

        wkpriceLbl.setText("                                   ");

        jLabel10.setForeground(new java.awt.Color(41, 128, 185));
        jLabel10.setText("Ghi Chú");

        ghichuTxt.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(41, 128, 185), 1, true));
        ghichuTxt.setPreferredSize(new java.awt.Dimension(0, 21));

        hldpriceLbl.setText("                                   ");

        savefbfButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/022-save.png"))); // NOI18N
        savefbfButton.setText("Lưu");
        savefbfButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                savefbfButtonMouseClicked(evt);
            }
        });

        cancelButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/058-error.png"))); // NOI18N
        cancelButton1.setText("Hủy");
        cancelButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cancelButton1MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addComponent(jLabel1)
                .addGap(28, 28, 28))
            .addGroup(layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel10)
                                .addGap(39, 39, 39)
                                .addComponent(ghichuTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING))
                                .addGap(24, 24, 24)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(loaisanBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(statusBox, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(tensanTxt, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel8)
                                    .addComponent(jLabel9))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(wkpriceTxt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(hldpriceTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(22, 22, 22)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(wkpriceLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(hldpriceLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addGap(26, 26, 26)
                                .addComponent(pmpriceTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(pmpriceLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addGap(18, 18, 18)
                                .addComponent(ampriceTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(ampriceLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(searchTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(addFBF)
                        .addGap(34, 34, 34)
                        .addComponent(savefbfButton)
                        .addGap(34, 34, 34)
                        .addComponent(cancelButton1)
                        .addGap(289, 289, 289))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 855, Short.MAX_VALUE)
                        .addGap(19, 19, 19))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(tensanTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(loaisanBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(ampriceTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ampriceLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(pmpriceTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(pmpriceLbl))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel5)
                        .addComponent(statusBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(wkpriceTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(wkpriceLbl))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(hldpriceTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(hldpriceLbl)
                            .addComponent(jLabel10)
                            .addComponent(ghichuTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(searchTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(addFBF)
                    .addComponent(savefbfButton)
                    .addComponent(cancelButton1))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(13, Short.MAX_VALUE))
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

    private void addFBFMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addFBFMouseClicked
        try {
            tensanTxt.setText("");
            loaisanBox.setSelectedItem("Sân 5 Người");
            statusBox.setSelectedItem("Đang Trống");
            ampriceTxt.setText("");
            pmpriceTxt.setText("");
            wkpriceTxt.setText("");
            hldpriceTxt.setText("");
            ghichuTxt.setText("");
            ampriceLbl.setText("");
            pmpriceLbl.setText("");
            wkpriceLbl.setText("");
            hldpriceLbl.setText("");
            CODE = "INSERT";
        } catch(Exception e){
            e.printStackTrace();
        }
    }//GEN-LAST:event_addFBFMouseClicked
    
    public void filter(String query){
        DefaultTableModel dm = (DefaultTableModel) fbfTable.getModel();
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<DefaultTableModel>(dm);
        fbfTable.setRowSorter(tr);
        tr.setRowFilter(RowFilter.regexFilter(query));
    }
    
    private void ampriceTxtKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ampriceTxtKeyReleased
        String AMPRICE = null;
        if(Validate.notNumber(ampriceTxt.getText()) == true){
            JOptionPane.showMessageDialog(this, "Giá buổi sáng phải là số");
            return;
        }
        if(ampriceTxt.getText().length() <= 0){
            ampriceLbl.setText("");
            return;
        }
        AMPRICE = currencyFormatter.format(Double.parseDouble(ampriceTxt.getText()));
        ampriceLbl.setText(String.valueOf(AMPRICE));
    }//GEN-LAST:event_ampriceTxtKeyReleased

    private void pmpriceTxtKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_pmpriceTxtKeyReleased
        String PMPRICE = null;
        if(Validate.notNumber(pmpriceTxt.getText()) == true){
            JOptionPane.showMessageDialog(this, "Giá buổi tối phải là số");
            return;
        }
        if(pmpriceTxt.getText().length() <= 0){
            pmpriceLbl.setText("");
            return;
        }
        PMPRICE = currencyFormatter.format(Double.parseDouble(pmpriceTxt.getText()));
        pmpriceLbl.setText(String.valueOf(PMPRICE));
    }//GEN-LAST:event_pmpriceTxtKeyReleased

    private void wkpriceTxtKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_wkpriceTxtKeyReleased
        String WKPRICE = null;
        if(Validate.notNumber(wkpriceTxt.getText()) == true){
            JOptionPane.showMessageDialog(this, "Giá cuối tuần phải là số");
            return;
        }
        if(wkpriceTxt.getText().length() <= 0){
            wkpriceLbl.setText("");
            return;
        }
        WKPRICE = currencyFormatter.format(Double.parseDouble(wkpriceTxt.getText()));
        wkpriceLbl.setText(String.valueOf(WKPRICE));
    }//GEN-LAST:event_wkpriceTxtKeyReleased

    private void hldpriceTxtKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_hldpriceTxtKeyReleased
        String HLDPRICE = null;
        if(Validate.notNumber(hldpriceTxt.getText()) == true){
            JOptionPane.showMessageDialog(this, "Giá ngày lễ phải là số");
            return;
        }
        if(hldpriceTxt.getText().length() <= 0){
            hldpriceLbl.setText("");
            return;
        }
        HLDPRICE = currencyFormatter.format(Double.parseDouble(hldpriceTxt.getText()));
        hldpriceLbl.setText(String.valueOf(HLDPRICE));
    }//GEN-LAST:event_hldpriceTxtKeyReleased
    
    public void insertFBF(String tenSan, String loaiSan, String statusSan, String giaTruoc17H, String giaSau17H, String giaCuoiTuan, String giaNgayLe, String ghichu){
        try{
            String insert1 = "INSERT INTO SanBong(TenSan, LoaiSan, TinhTrang, GiaTruoc17H, GiaSau17H, GiaCuoiTuan, GiaNgayLe, GhiChu) VALUES(?,?,?,?,?,?,?,?)";
            conn = cn.getConnection();
            PreparedStatement pst2 = conn.prepareStatement(insert1);
            pst2.setString(1, tenSan);
            pst2.setString(2, loaiSan);
            pst2.setString(3, statusSan);
            pst2.setString(4, giaTruoc17H);
            pst2.setString(5, giaSau17H);
            pst2.setString(6, giaCuoiTuan);
            pst2.setString(7, giaNgayLe);
            pst2.setString(8, ghichu);
            pst2.execute();
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void updateFBF(String tenSan, String loaiSan, String statusSan, String giaTruoc17H, String giaSau17H, String giaCuoiTuan, String giaNgayLe, String ghichu, String maSan){
        try{
            String update = "UPDATE SanBong SET TenSan = ?, LoaiSan = ?, TinhTrang = ?, GiaTruoc17H = ?, GiaSau17H = ?,  GiaCuoiTuan = ?, GiaNgayLe  = ?, GhiChu = ? WHERE MaSan = ?";
            conn = cn.getConnection();
            PreparedStatement pst2 = conn.prepareStatement(update);
            pst2.setString(1, tenSan);
            pst2.setString(2, loaiSan);
            pst2.setString(3, statusSan);            
            pst2.setString(4, giaTruoc17H);
            pst2.setString(5, giaSau17H);
            pst2.setString(6, giaCuoiTuan);
            pst2.setString(7, giaNgayLe);
            pst2.setString(8, ghichu);
            pst2.setString(9, maSan);
            pst2.execute();
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void validateGiaSan(String giaSan, StringBuffer sb){
        if(giaSan.length() == 0){
            sb.append("Giá sân không được để trống!\n");
            return;
        }
        if(Integer.parseInt(giaSan) < 1000){
            sb.append("Giá sân không được bé hơn 1000đ!\n");
        }
        if(Validate.notNumber(giaSan) == true){
            sb.append("Giá sân không được chứa chữ!\n");
        }
    }
    
    private void savefbfButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_savefbfButtonMouseClicked
        String tenSan = tensanTxt.getText().trim();
        String loaiSan = loaisanBox.getSelectedItem().toString().trim();
        String statusSan = statusBox.getSelectedItem().toString().trim();
        String amPrice = ampriceTxt.getText().trim();
        String pmPrice = pmpriceTxt.getText().trim();
        String wkPrice = wkpriceTxt.getText().trim();
        String hldPrice = hldpriceTxt.getText().trim();
        String ghichu = ghichuTxt.getText().trim();
        StringBuffer sb = new StringBuffer();
        if(tenSan.length() == 0){
            sb.append("Tên sân không được để trống!\n");
        }
        validateGiaSan(amPrice, sb);
        validateGiaSan(pmPrice, sb);
        validateGiaSan(wkPrice, sb);
        validateGiaSan(hldPrice, sb);
        if(sb.length()>0 ){
            JOptionPane.showMessageDialog(this, sb.toString());
            return;
        }
        if( CODE.equals("INSERT") == true){
            String select_FBF = "Select * from SanBong";
            try{
                conn = cn.getConnection();
                PreparedStatement pst = conn.prepareStatement(select_FBF);
                ResultSet fbf = pst.executeQuery();
                while(fbf.next()){
                    if(fbf.getString("TenSan").trim().equals(tenSan) == true){
                        sb.append("Tên Sân đã tồn tại!\n");
                    }
                }
            } catch(Exception e){
                e.printStackTrace();
            }
            if(sb.length()>0){
                JOptionPane.showMessageDialog(this, sb.toString());
                return;
            }
            insertFBF(tenSan,loaiSan,statusSan,amPrice, pmPrice, wkPrice, hldPrice, ghichu);
            JOptionPane.showMessageDialog(this, "Đã Thêm Sân Thành Công");
            Reset();
            return;
        }
        if( CODE.equals("UPDATE") == true){
            updateFBF(tenSan, loaiSan, statusSan, amPrice, pmPrice, wkPrice, hldPrice, ghichu,  maFBF);
            JOptionPane.showMessageDialog(this, "Chỉnh Sửa Thông Tin Sân Thành Công");
            Reset();
            return;
        }
    }//GEN-LAST:event_savefbfButtonMouseClicked

    private void cancelButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cancelButton1MouseClicked
        this.dispose();
    }//GEN-LAST:event_cancelButton1MouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addFBF;
    private javax.swing.JLabel ampriceLbl;
    private javax.swing.JTextField ampriceTxt;
    private javax.swing.JButton cancelButton1;
    private javax.swing.JTable fbfTable;
    private javax.swing.JTextField ghichuTxt;
    private javax.swing.JLabel hldpriceLbl;
    private javax.swing.JTextField hldpriceTxt;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JComboBox<String> loaisanBox;
    private javax.swing.JLabel pmpriceLbl;
    private javax.swing.JTextField pmpriceTxt;
    private javax.swing.JButton savefbfButton;
    private javax.swing.JTextField searchTxt;
    private javax.swing.JComboBox<String> statusBox;
    private javax.swing.JTextField tensanTxt;
    private javax.swing.JLabel wkpriceLbl;
    private javax.swing.JTextField wkpriceTxt;
    // End of variables declaration//GEN-END:variables
}
