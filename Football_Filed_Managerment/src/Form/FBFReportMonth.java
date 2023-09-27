
package Form;
import DataBase.ConnectDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

public class FBFReportMonth extends javax.swing.JInternalFrame {
    ConnectDB cn = new ConnectDB();
    Connection conn;
    /**
     * Creates new form FBFReportMonth
     */
    public FBFReportMonth() {
        initComponents();
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        reportButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        reportTable = new javax.swing.JTable();
        thangBaoCao = new com.toedter.calendar.JMonthChooser();
        namBaoCao = new com.toedter.calendar.JYearChooser();
        jLabel2 = new javax.swing.JLabel();

        setTitle("Báo Cáo Doanh Thu Sân Bóng Theo Tháng");

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(41, 128, 185));
        jLabel1.setText("BÁO CÁO DOANH THU SÂN BÓNG THEO THÁNG");

        reportButton.setText("Xác Nhận");
        reportButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                reportButtonMouseClicked(evt);
            }
        });

        reportTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Sân Bóng", "Số Lần Đặt", "Doanh Thu"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(reportTable);

        jLabel2.setForeground(new java.awt.Color(41, 128, 185));
        jLabel2.setText("Chọn Tháng");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 664, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(thangBaoCao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(namBaoCao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(reportButton)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addGroup(layout.createSequentialGroup()
                .addGap(128, 128, 128)
                .addComponent(jLabel1)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(9, 9, 9)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(thangBaoCao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(namBaoCao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel2))
                    .addComponent(reportButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 183, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private static DefaultCategoryDataset extractDataFromTable(JTable table) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        int rowCount = table.getRowCount();
        for (int i = 0; i < rowCount - 1; i++) {
            Object sanBong = table.getValueAt(i, 0);
            Object soLanDatString = table.getValueAt(i, 1);
            double doanhThu = Double.parseDouble(table.getValueAt(i, 2).toString());
            Number soLanDat = Integer.parseInt((String) soLanDatString);
            dataset.addValue(doanhThu, "Doanh thu", (String) sanBong);
        }
        return dataset;
    }
        
    private void reportButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_reportButtonMouseClicked
        DefaultTableModel dm = (DefaultTableModel)reportTable.getModel();
        dm.getDataVector().removeAllElements();
        dm.fireTableDataChanged();
        DefaultTableCellRenderer stringRenderer = (DefaultTableCellRenderer)
        reportTable.getDefaultRenderer(String.class);
        stringRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        DefaultTableModel model = (DefaultTableModel) reportTable.getModel();
        String tenSan = "", maSan = "";
        Integer soLanDat = 0;
        Integer doanhThu = 0;
        Integer tongSLD = 0;
        Integer tongDT = 0;
        String thangBaoCao = String.valueOf(this.thangBaoCao.getMonth() + 1);
        String namBaoCao = String.valueOf(this.namBaoCao.getYear());
        String select_FBF = "Select * from SanBong";
        try {
            conn = cn.getConnection();
            PreparedStatement pst = conn.prepareStatement(select_FBF);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                tenSan = rs.getString("TenSan");
                maSan = rs.getString("MaSan");
                String select_PDS = "Select * from PhieuDatSan WHERE MaSan = ? AND Month(GioBD) = ? AND Year(GioBD) = ? AND TrangThai != ?";
                PreparedStatement stmt = conn.prepareStatement(select_PDS);
                stmt.setString(1, maSan);
                stmt.setString(2, thangBaoCao);
                stmt.setString(3, namBaoCao);
                stmt.setString(4, "Đã Hủy");
                ResultSet pds = stmt.executeQuery();
                while(pds.next()){
                    soLanDat = soLanDat + 1;
                    String maPDS = pds.getString("MaPhieuDat");
                    String select_HD = "Select * from HoaDon WHERE MaPhieuDat = ?";
                    PreparedStatement pst2 = conn.prepareStatement(select_HD);
                    pst2.setString(1, maPDS);
                    ResultSet hd = pst2.executeQuery();
                    while(hd.next()){
                        doanhThu = doanhThu + hd.getInt("TienSan");
                    }
                }                    
                String[] line = { tenSan, String.valueOf(soLanDat), String.valueOf(doanhThu) };
                model.addRow(line);
                tongDT = tongDT + doanhThu;
                tongSLD = tongSLD + soLanDat;
                tenSan = ""; maSan = "";
                soLanDat = 0;
                doanhThu = 0;
            }
            String[] line = { "Tổng", String.valueOf(tongSLD), String.valueOf(tongDT) };
            model.addRow(line);
            rs.close();
            pst.close();
            conn.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        DefaultCategoryDataset dataset = extractDataFromTable(reportTable);
        // Tạo biểu đồ cột
        JFreeChart chart = ChartFactory.createBarChart(
                "Biểu đồ doanh thu sân bóng theo tháng", // Tiêu đề biểu đồ
                "Sân bóng", // Nhãn trục x
                "Doanh Thu", // Nhãn trục y
                dataset, // Dataset
                PlotOrientation.VERTICAL, // Hướng biểu đồ
                true, // Hiển thị legent
                true, // Hiển thị tooltips
                false // Không hiển thị URLs
        );
        ChartFrame frame = new ChartFrame("Biểu đồ doanh thu sân bóng theo tháng", chart);
        frame.pack();
        frame.setVisible(true);
    }//GEN-LAST:event_reportButtonMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private com.toedter.calendar.JYearChooser namBaoCao;
    private javax.swing.JButton reportButton;
    private javax.swing.JTable reportTable;
    private com.toedter.calendar.JMonthChooser thangBaoCao;
    // End of variables declaration//GEN-END:variables
}
