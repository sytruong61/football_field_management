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

public class DVReportDay extends javax.swing.JInternalFrame {
    
    ConnectDB cn = new ConnectDB();
    Connection conn;
    public DVReportDay() {
        initComponents();
        ngayBaoCao.setDate(java.sql.Date.valueOf(java.time.LocalDate.now()));
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        ngayBaoCao = new com.toedter.calendar.JDateChooser();
        reportButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        reportTable = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();

        setTitle("Báo Cáo Doanh Thu Dịch Vụ Theo Ngày");
        setToolTipText("");

        reportButton.setText("Xác Nhận");
        reportButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                reportButtonMouseClicked(evt);
            }
        });
        reportButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reportButtonActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(41, 128, 185));
        jLabel1.setText("BÁO CÁO DOANH THU DỊCH VỤ THEO NGÀY");

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
        jLabel2.setText("Chọn Ngày");

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
                        .addComponent(ngayBaoCao, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(reportButton)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(131, 131, 131))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(reportButton)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel2)
                        .addComponent(ngayBaoCao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(14, 14, 14)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 178, Short.MAX_VALUE)
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
        Date ngaybaocaotemp = ngayBaoCao.getDate();
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
        String ngayBaoCao = outputFormat.format(ngaybaocaotemp);
        Integer tongSL = 0;
        Integer tongDT = 0;
        String query = "SELECT D.TenDichVu, C.SoLuong, C.TongTien " +
                       "FROM DichVu D, HoaDon H, ChiTietHoaDon C " +
                       "WHERE D.MaDV = C.MaDV " +
                       "AND H.MaHD = C.MaHD " +
                       "AND CAST(H.NgayLapHD AS DATE) = ?";
        try {
            conn = cn.getConnection();
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setString(1, ngayBaoCao);
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
                "Biểu đồ doanh thu dịch vụ theo ngày", // Tiêu đề biểu đồ
                "Dịch Vụ", // Nhãn trục x
                "Doanh Thu", // Nhãn trục y
                dataset, // Dataset
                PlotOrientation.VERTICAL, // Hướng biểu đồ
                true, // Hiển thị legent
                true, // Hiển thị tooltips
                false // Không hiển thị URLs
        );
        ChartFrame frame = new ChartFrame("Biểu đồ doanh thu dịch vụ theo ngày", chart);
        frame.pack();
        frame.setVisible(true);
    }//GEN-LAST:event_reportButtonMouseClicked

    private void reportButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reportButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_reportButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private com.toedter.calendar.JDateChooser ngayBaoCao;
    private javax.swing.JButton reportButton;
    private javax.swing.JTable reportTable;
    // End of variables declaration//GEN-END:variables
}
