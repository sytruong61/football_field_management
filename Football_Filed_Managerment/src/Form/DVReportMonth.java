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

/**
 *
 * @author Sytru
 */
public class DVReportMonth extends javax.swing.JInternalFrame {

    ConnectDB cn = new ConnectDB();
    Connection conn;
    public DVReportMonth() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        thangBaoCao = new com.toedter.calendar.JMonthChooser();
        namBaoCao = new com.toedter.calendar.JYearChooser();
        reportButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        reportTable = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();

        setTitle("Báo Cáo Doanh Thu Dịch Vụ Theo Tháng");
        setToolTipText("");

        reportButton.setText("Xác Nhận");
        reportButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                reportButtonMouseClicked(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(41, 128, 185));
        jLabel1.setText("BÁO CÁO DOANH THU DỊCH VỤ THEO THÁNG");

        reportTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Dịch Vụ", "Số Lượng", "Doanh Thu"
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
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 664, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(thangBaoCao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(namBaoCao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(reportButton)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(142, 142, 142)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(thangBaoCao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(namBaoCao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel2))
                    .addComponent(reportButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 181, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private static DefaultCategoryDataset extractDataFromTable(JTable table) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        int rowCount = table.getRowCount();
        for (int i = 0; i < rowCount - 1; i++) {
            Object dichVu = table.getValueAt(i, 0);
            Object soLuotBanString = table.getValueAt(i, 1);
            double doanhThu = Double.parseDouble(table.getValueAt(i, 2).toString());
            Number soLanBan = Integer.parseInt((String) soLuotBanString);
            dataset.addValue(doanhThu, "Doanh thu", (String) dichVu);
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
        String thangBaoCao = String.valueOf(this.thangBaoCao.getMonth() + 1);
        String namBaoCao = String.valueOf(this.namBaoCao.getYear());
        Integer tongSL = 0;
        Integer tongDT = 0;
        String query = "SELECT D.TenDichVu, C.SoLuong, C.TongTien " +
                       "FROM DichVu D, HoaDon H, ChiTietHoaDon C " +
                       "WHERE D.MaDV = C.MaDV " +
                       "AND H.MaHD = C.MaHD " +
                       "AND Month(NgayLapHD) = ? AND Year(NgayLapHD) = ?";
        try {
            conn = cn.getConnection();
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setString(1, thangBaoCao);
            pst.setString(2, namBaoCao);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                String tenDichVu = rs.getString("TenDichVu");
                int soLuong = rs.getInt("SoLuong");
                int doanhThu = rs.getInt("TongTien");
                String[] line = { tenDichVu, String.valueOf(soLuong), String.valueOf(doanhThu) };
                model.addRow(line);
                tongDT = tongDT + doanhThu;
                tongSL = tongSL + soLuong;
            }
            String[] line = { "Tổng" , String.valueOf(tongSL), String.valueOf(tongDT) };
            model.addRow(line);
            conn.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        DefaultCategoryDataset dataset = extractDataFromTable(reportTable);
        // Tạo biểu đồ cột
        JFreeChart chart = ChartFactory.createBarChart(
                "Biểu đồ doanh thu dịch vụ theo tháng", // Tiêu đề biểu đồ
                "Dịch Vụ", // Nhãn trục x
                "Doanh Thu", // Nhãn trục y
                dataset, // Dataset
                PlotOrientation.VERTICAL, // Hướng biểu đồ
                true, // Hiển thị legent
                true, // Hiển thị tooltips
                false // Không hiển thị URLs
        );
        ChartFrame frame = new ChartFrame("Biểu đồ doanh thu dịch vụ theo tháng", chart);
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
