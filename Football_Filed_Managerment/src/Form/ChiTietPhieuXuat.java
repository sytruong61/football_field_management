package Form;

import DataBase.ConnectDB;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

public class ChiTietPhieuXuat extends javax.swing.JFrame {
    ConnectDB cn = new ConnectDB();
    Connection conn;
    Locale locale = new Locale("vn", "VN");      
    NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);
    String CODE, maNV, tenNV, maDXH, soLuongconst;
    
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
    
    public String GetNVfromID(String ID){
        String tenNV = null;
        String select_NVfromID = "Select * from NhanVien where MaNV = ?";
        try{
            conn = cn.getConnection();
            PreparedStatement pst = conn.prepareStatement(select_NVfromID);
            pst.setString(1, ID);
            ResultSet rs = pst.executeQuery();
            if(rs.next()){
               tenNV = rs.getString("HoTen");
            }
        } catch(Exception e){
            e.printStackTrace();
        }
        return tenNV;
    }
    
    public String GetTSTBfromID(String maTSTB){
        String tenTSTB = null;
        String select_TSTBfromID = "Select * from TaiSanThietBi where MaTSTB = ?";
        try{
            conn = cn.getConnection();
            PreparedStatement pst = conn.prepareStatement(select_TSTBfromID);
            pst.setString(1, maTSTB);
            ResultSet rs = pst.executeQuery();
            if(rs.next()){
                tenTSTB = rs.getString("TenTSTB");
            }
        } catch(Exception e){
            e.printStackTrace();
        }
        return tenTSTB;
    }
    
    public final void SetNVBox(){
        try {
            String select_NV = "Select * from NhanVien";
            PreparedStatement pst4 = conn.prepareStatement(select_NV);
            ResultSet nv = pst4.executeQuery();
            while(nv.next()){
                String nvName = nv.getString("HoTen");
                nvBox.addItem(nvName);
            }
            System.out.println(" Done ");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public String GetIDTSTBfromTen(String tenTSTB){
        String ID = null;
        String select_IDTSTBfromTen = "Select * from TaiSanThietBi where TenTSTB = ?";
        try{
            conn = cn.getConnection();
            PreparedStatement pst = conn.prepareStatement(select_IDTSTBfromTen);
            pst.setString(1, tenTSTB);
            ResultSet rs = pst.executeQuery();
            if(rs.next()){
                ID = rs.getString("MaTSTB");
            }
        } catch(Exception e){
            System.out.println(e.toString() + " 105 ");
        }
        return ID;
    }
    
    public String GetSoLuongTSTBfromID(String maTSTB){
        String slTSTB = null;
        String select_slTSTBfromID = "Select * from TaiSanThietBi where MaTSTB = ?";
        try{
            conn = cn.getConnection();
            PreparedStatement pst = conn.prepareStatement(select_slTSTBfromID);
            pst.setString(1, maTSTB);
            ResultSet rs = pst.executeQuery();
            if(rs.next()){
                slTSTB = rs.getString("SOLUONGTSTB");
            }
        } catch(Exception e){
            System.out.println(e.toString() + " 106 ");
        }
        return slTSTB;
    }
     
    public final void SetTSTBfromTen(String tenTSTB, String soLuong, String lyDo){
        try {
            conn = cn.getConnection();
            String select_1TSTB = "Select * from TaiSanThietBi where MaTSTB = ?";
            PreparedStatement pst3 = conn.prepareStatement(select_1TSTB);
            pst3.setString(1, GetIDTSTBfromTen(tenTSTB));
            ResultSet tstb = pst3.executeQuery();
            if(tstb.next()){
                tstbBox.setSelectedItem(tenTSTB);
                categoryBox.setSelectedItem(tstb.getString("LoaiTSTB"));
                //dvtText.setText(tstb.getString("DVT"));
                lydoTxt.setText(lyDo);
            }
                countTxt.setText(soLuong);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    
    public final void SetDXH(String maDXH){
        String select_DXH = "Select * from DONXUATHANG";
        try{
            conn = cn.getConnection();
            PreparedStatement pst = conn.prepareStatement(select_DXH);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                maPXTxt.setText(rs.getString("MaDXH"));
                nvBox.setSelectedItem(GetNVfromID(rs.getString("MaNV")));
                maNV = rs.getString("MaNV");
                tenNV = GetNVfromID(maNV);
                SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date date = inputFormat.parse(rs.getString("NgayXuat"));
                ngayXuatBox.setDate(date);
                ghichuTxt.setText(rs.getString("GhiChu"));
            }
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public final void SetTSTBBox(){
        try {
            String select_TSTB = "Select * from TaiSanThietBi";
            PreparedStatement pst4 = conn.prepareStatement(select_TSTB);
            ResultSet tstb = pst4.executeQuery();
            while(tstb.next()){
                String tstbName = tstb.getString("TenTSTB");
                tstbBox.addItem(tstbName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public final void SetBox(){
        SetNVBox();
        tstbBox.removeAllItems();
        SetTSTBBox();
        SetDXH(maDXH);
        nvBox.setSelectedItem(GetNVfromID(maNV));
        tstbBox.setSelectedIndex(-1);
    }
    
    public ChiTietPhieuXuat(String CODE, String tenNV, String maDXH) {
        initComponents();
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        ImageIcon img = new ImageIcon("D:\\Football_Filed_Managerment\\src\\images\\icons/—Pngtree—football field plan_4710125 (1).png");
        this.setIconImage(img.getImage());
        this.CODE = CODE;
        this.tenNV = tenNV;
        this.maDXH = maDXH;
        nvBox.setEnabled(false);
        ngayXuatBox.setEnabled(false);
        categoryBox.setSelectedIndex(-1);
        tstbBox.setSelectedIndex(-1);
        if(CODE.equals("VIEW") == true){           
            SelectAllChiTietDonXuatHang(maDXH);
        }
        SetBox();
        ColumnsAutoSizer.sizeColumnsToFit(ctdxhTable);
        tstbBox.addActionListener (new ActionListener () {
            public void actionPerformed(ActionEvent e) {
                if(tstbBox.getSelectedIndex() == -1){
                    return;
                }
                String select_TSTB = "Select * from TaiSanThietBi where TenTSTB = ?";
                try{
                    conn = cn.getConnection();
                    PreparedStatement pst = conn.prepareStatement(select_TSTB);
                    pst.setString(1, tstbBox.getSelectedItem().toString().trim());
                    ResultSet rs = pst.executeQuery();
                    if(rs.next()){
                        tstbBox.setSelectedItem(rs.getString("TenTSTB"));
                        categoryBox.setSelectedItem(rs.getString("LoaiTSTB"));
                    }
                } catch(ClassNotFoundException | SQLException ex){
                    ex.printStackTrace();
                }
            }
        });
    }
    
    public ChiTietPhieuXuat() {
        initComponents();
        Reset();
        if(CODE.equals("VIEW") == true){           
            SelectAllChiTietDonXuatHang(maDXH);
        }
        SetBox();
        ColumnsAutoSizer.sizeColumnsToFit(ctdxhTable);
    }

    public final void SelectAllChiTietDonXuatHang(String maDXH){
        DefaultTableModel dm = (DefaultTableModel)ctdxhTable.getModel();
        dm.getDataVector().removeAllElements();
        dm.fireTableDataChanged();
        String select_ChiTietDonXuatHang = "Select * from ChiTietDonXuatHang where MaDXH = ?";
        try{
            conn = cn.getConnection();
            PreparedStatement pst = conn.prepareStatement(select_ChiTietDonXuatHang);
            pst.setString(1, maDXH);
            ResultSet rs = pst.executeQuery();
            DefaultTableModel model = (DefaultTableModel) ctdxhTable.getModel();
            String TenTSTB, SOLUONG, LyDoXuat;
            while(rs.next()){
                TenTSTB = GetTSTBfromID(rs.getString("MaTSTB"));
                SOLUONG = rs.getString("SOLUONG");
                LyDoXuat = rs.getString("LyDoXuat");
                String[] ctdxh = {TenTSTB, SOLUONG, LyDoXuat};
                model.addRow(ctdxh);               
            }
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public final void Reset(){
        SelectAllChiTietDonXuatHang(maDXH);
        SetBox();
        tstbBox.setSelectedIndex(-1);
        categoryBox.setSelectedIndex(-1);
        countTxt.setText("");
        lydoTxt.setText("");
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel11 = new javax.swing.JLabel();
        cancelButton = new javax.swing.JButton();
        fixDXH = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        ngayXuatBox = new com.toedter.calendar.JDateChooser();
        ghichuTxt = new javax.swing.JTextField();
        nvBox = new javax.swing.JComboBox<>();
        categoryBox = new javax.swing.JComboBox<>();
        addDXH = new javax.swing.JButton();
        searchTxt = new javax.swing.JTextField();
        lydoTxt = new javax.swing.JTextField();
        tstbBox = new javax.swing.JComboBox<>();
        saveDXH = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        countTxt = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        ctdxhTable = new javax.swing.JTable();
        jLabel7 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        maPXTxt = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Chi Tiết Phiếu Xuất");

        jLabel11.setText("Lý Do Xuất");

        cancelButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/058-error.png"))); // NOI18N
        cancelButton.setText("Hủy");
        cancelButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cancelButtonMouseClicked(evt);
            }
        });

        fixDXH.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/218-edit.png"))); // NOI18N
        fixDXH.setText("Chỉnh sửa ");
        fixDXH.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                fixDXHMouseClicked(evt);
            }
        });

        jLabel8.setText("Loại Tài Sản - Thiết Bị");

        jLabel10.setText("Số Lượng");

        categoryBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "F&B", "Cho Thuê", "Thiết Bị Sân Bóng", " " }));

        addDXH.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/056-plus.png"))); // NOI18N
        addDXH.setText("Thêm");
        addDXH.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                addDXHMouseClicked(evt);
            }
        });

        searchTxt.setText("Nhập để tìm kiếm");
        searchTxt.setToolTipText("");
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

        saveDXH.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/022-save.png"))); // NOI18N
        saveDXH.setText("Lưu");
        saveDXH.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                saveDXHMouseClicked(evt);
            }
        });

        jLabel3.setText("Tài Sản Thiết Bị");

        ctdxhTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Thiết Bị Tài Sản ", "Số Lượng", "Lý Do Xuất"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(ctdxhTable);

        jLabel7.setText("Nhân Viên");

        jLabel6.setText("Ghi Chú");

        jLabel5.setText("Ngày Nhập");

        jLabel1.setText("Mã Phiếu Xuất");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 685, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(20, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(searchTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(addDXH)
                                .addGap(18, 18, 18)
                                .addComponent(fixDXH)
                                .addGap(18, 18, 18)
                                .addComponent(saveDXH)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(cancelButton))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel10)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(countTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel8)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(categoryBox, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel3)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(tstbBox, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel5)
                                            .addComponent(jLabel1))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(ngayXuatBox, javax.swing.GroupLayout.DEFAULT_SIZE, 190, Short.MAX_VALUE)
                                            .addComponent(maPXTxt))))
                                .addGap(41, 41, 41)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel7)
                                            .addComponent(jLabel6))
                                        .addGap(18, 18, 18)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(nvBox, 0, 190, Short.MAX_VALUE)
                                            .addComponent(ghichuTxt)))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel11)
                                        .addGap(18, 18, 18)
                                        .addComponent(lydoTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGap(71, 71, 71))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(nvBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(maPXTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel5)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(ngayXuatBox, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(ghichuTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(tstbBox, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(categoryBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(lydoTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10)
                    .addComponent(countTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 20, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(searchTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(addDXH)
                    .addComponent(fixDXH)
                    .addComponent(saveDXH)
                    .addComponent(cancelButton))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 357, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cancelButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cancelButtonMouseClicked
        this.dispose();
    }//GEN-LAST:event_cancelButtonMouseClicked

    private void fixDXHMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_fixDXHMouseClicked
        int row = ctdxhTable.getSelectedRow();
        String tenTSTB = ctdxhTable.getModel().getValueAt(row, 0).toString();
        String soLuong = ctdxhTable.getModel().getValueAt(row, 1).toString();
        String lyDo = ctdxhTable.getModel().getValueAt(row, 2).toString();
        soLuongconst = soLuong;
        SetTSTBfromTen(tenTSTB, soLuong, lyDo);
        CODE = "UPDATE";
    }//GEN-LAST:event_fixDXHMouseClicked

    private void addDXHMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addDXHMouseClicked
        try {
            Reset();
            CODE = "INSERT";
        } catch(Exception e){
            e.printStackTrace();
        }
    }//GEN-LAST:event_addDXHMouseClicked

    private void searchTxtMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_searchTxtMouseClicked
        searchTxt.setText("");
    }//GEN-LAST:event_searchTxtMouseClicked
    
    public void filter(String query){
        DefaultTableModel dm = (DefaultTableModel) ctdxhTable.getModel();
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<DefaultTableModel>(dm);
        ctdxhTable.setRowSorter(tr);
        tr.setRowFilter(RowFilter.regexFilter(query));
    }
    
    private void searchTxtKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchTxtKeyReleased
        String query = searchTxt.getText().trim();
        filter(query);
    }//GEN-LAST:event_searchTxtKeyReleased
    
    private void saveDXHMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_saveDXHMouseClicked
        if(CODE.equals("VIEW") == true){
            JOptionPane.showMessageDialog(this, "Bạn đang ở chế độ xem, nhấn nút thêm hoặc chỉnh sửa để chuyển chế độ");
            return;
        }
        String tenNV = nvBox.getSelectedItem().toString().trim();
        java.sql.Date ngayNhap = new java.sql.Date(ngayXuatBox.getDate().getTime());
        String ghichu = ghichuTxt.getText().trim();
        String tenTSTB = tstbBox.getSelectedItem().toString();
        if(categoryBox.getSelectedIndex() == -1){
            return;
        }
        String loaiTSTB = categoryBox.getSelectedItem().toString().trim();
        String soLuong = countTxt.getText().trim();
        String lyDo = lydoTxt.getText().trim();
        StringBuffer sb = new StringBuffer();
        if(tenTSTB.length() == 0 || soLuong.length() == 0 || lyDo.length() == 0){
            sb.append("Không được để trống thông tin!\n");
        }
        if(Validate.notNumber(soLuong) == true){
            sb.append("Số lượng phải là số!\n");
        }
        if(sb.length()>0){
            JOptionPane.showMessageDialog(this, sb.toString());
            return;
        }
        if( CODE.equals("INSERT") == true){
            String select_ChiTietDonXuatHang = "Select * from ChiTietDonXuatHang";
            try{
                conn = cn.getConnection();
                PreparedStatement pst = conn.prepareStatement(select_ChiTietDonXuatHang);
                ResultSet ctdxh = pst.executeQuery();
                while(ctdxh.next()){
                    int count = 0;
                    if(ctdxh.getString("MaDXH").trim().equals(maDXH) == true){
                        count++;
                    }
                    if(ctdxh.getString("MaTSTB").trim().equals(GetIDTSTBfromTen(tenTSTB)) == true){
                        count++;
                    }
                    if(count == 2){
                        sb.append("Chi Tiết Đơn Xuất Hàng này có thể đã tồn tại trong hôm nay hãy kiểm tra lại và cập nhật");
                    }
                }
            } catch(Exception e){
                e.printStackTrace();
            }
            if(sb.length()>0){
                JOptionPane.showMessageDialog(this, sb.toString());
                return;
            }
            String maTSTB = GetIDTSTBfromTen(tenTSTB);
            Integer slSanCo = Integer.parseInt(GetSoLuongTSTBfromID(maTSTB));
            Integer slHienTai = slSanCo - Integer.parseInt(soLuong);
            if(Integer.valueOf(slHienTai) == 0){
                updateTSTB( slHienTai, "Hết Hàng", maTSTB );
            }
            if(Integer.valueOf(slHienTai) > 0){
                updateTSTB( slHienTai, "Còn Hàng", maTSTB );
            }
            if(Integer.valueOf(slHienTai) < 0){
                JOptionPane.showMessageDialog(this, "Không thể cập nhật số lượng");
                return;
            }
            insertChiTietDonXuatHang(maDXH, maTSTB, Integer.parseInt(soLuong), lyDo);
            Reset();
        }
        if( CODE.equals("UPDATE") == true){
            String maTSTB = GetIDTSTBfromTen(tenTSTB);
            String slSanCo = GetSoLuongTSTBfromID(maTSTB);
            Integer slHienTai = Integer.parseInt(slSanCo) - (Integer.parseInt(soLuong) - Integer.parseInt(soLuongconst));
            if(Integer.valueOf(slHienTai) == 0){
                updateTSTB( slHienTai, "Hết Hàng", maTSTB );
            }
            if(Integer.valueOf(slHienTai) > 0){
                updateTSTB( slHienTai, "Còn Hàng", maTSTB );
            }
            if(Integer.valueOf(slHienTai) < 0){
                JOptionPane.showMessageDialog(this, "Không thể cập nhật số lượng");
                return;
            }
            updateChiTietDonXuatHang(maDXH, maTSTB, Integer.parseInt(soLuong), lyDo);
            JOptionPane.showMessageDialog(this, "Chỉnh Sửa Thông Tin Tài Sản Thiết Bị Thành Công");
            Reset();
        }
    }//GEN-LAST:event_saveDXHMouseClicked
    
    public void updateTSTB(Integer soluongTSTB, String statusTSTB, String maTSTB){
        try{
            String update = "UPDATE TaiSanThietBi SET SOLUONGTSTB = ? , TinhTrangTSTB = ? WHERE MaTSTB = ?";
            conn = cn.getConnection();
            PreparedStatement pst2 = conn.prepareStatement(update);
            pst2.setInt(1, soluongTSTB);
            pst2.setString(2, statusTSTB);
            pst2.setString(3, maTSTB);
            pst2.execute();
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void insertChiTietDonXuatHang(String maDXH, String maTSTB, Integer soLuong, String lyDo){
        try{
            String insert = "INSERT INTO ChiTietDonXuatHang(MaDXH, MaTSTB, SOLUONG, LyDoXuat) VALUES(?,?,?,?)";
            conn = cn.getConnection();
            PreparedStatement pst2 = conn.prepareStatement(insert);
            pst2.setString(1, maDXH);
            pst2.setString(2, maTSTB);
            pst2.setInt(3, soLuong);
            pst2.setString(4, lyDo);
            pst2.execute();
        } catch(Exception e){
            e.printStackTrace();
        }
    }
        
    public void updateChiTietDonXuatHang(String maDXH, String maTSTB, Integer soLuong, String lyDo){
        try{
            String update = "UPDATE ChiTietDonXuatHang SET SOLUONG = ?, LyDoXuat = ? WHERE MaDXH = ? AND  MaTSTB = ?";
            conn = cn.getConnection();
            PreparedStatement pst2 = conn.prepareStatement(update);
            pst2.setInt(1, soLuong);
            pst2.setString(2, lyDo);
            pst2.setString(3, maDXH);            
            pst2.setString(4, maTSTB);
            pst2.execute();
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void deleteCTDNH(String maDXH, String maTSTB){
        try{
            String delete = "DELETE FROM ChiTietDonXuatHang WHERE MaDXH = ? AND  MaTSTB = ?";
            conn = cn.getConnection();
            PreparedStatement pst2 = conn.prepareStatement(delete);            
            pst2.setString(1, maDXH);
            pst2.setString(2, maTSTB);
            pst2.execute();
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ChiTietPhieuXuat.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ChiTietPhieuXuat.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ChiTietPhieuXuat.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ChiTietPhieuXuat.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ChiTietPhieuXuat().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addDXH;
    private javax.swing.JButton cancelButton;
    private javax.swing.JComboBox<String> categoryBox;
    private javax.swing.JTextField countTxt;
    private javax.swing.JTable ctdxhTable;
    private javax.swing.JButton fixDXH;
    private javax.swing.JTextField ghichuTxt;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField lydoTxt;
    private javax.swing.JTextField maPXTxt;
    private com.toedter.calendar.JDateChooser ngayXuatBox;
    private javax.swing.JComboBox<String> nvBox;
    private javax.swing.JButton saveDXH;
    private javax.swing.JTextField searchTxt;
    private javax.swing.JComboBox<String> tstbBox;
    // End of variables declaration//GEN-END:variables
}
