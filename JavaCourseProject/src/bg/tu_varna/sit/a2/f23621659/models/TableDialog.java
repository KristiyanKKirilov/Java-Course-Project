package bg.tu_varna.sit.a2.f23621659.models;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class TableDialog {
    public static void showTableInDialogWithPagination(Table table, int rowsPerPage) {
        List<String> headers = table.getHeaders();
        List<List<String>> allRows = table.getRows();
        int totalPages = (int) Math.ceil((double) allRows.size() / rowsPerPage);
        JFrame frame = new JFrame();

        JDialog dialog = new JDialog(frame, "Table", true);
        dialog.setSize(700, 400);
        dialog.setLocationRelativeTo(null);
        dialog.setLayout(new BorderLayout());

        String[] columnNames = headers.toArray(new String[0]);
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        JTable jTable = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(jTable);
        dialog.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton prevBtn = new JButton("<<");
        JButton nextBtn = new JButton(">>");
        JLabel pageLabel = new JLabel();

        buttonPanel.add(prevBtn);
        buttonPanel.add(pageLabel);
        buttonPanel.add(nextBtn);
        dialog.add(buttonPanel, BorderLayout.SOUTH);

        final int[] currentPage = {0};

        Runnable updateTable = () -> {
            model.setRowCount(0);
            int start = currentPage[0] * rowsPerPage;
            int end = Math.min(start + rowsPerPage, allRows.size());

            for (int i = start; i < end; i++) {
                model.addRow(allRows.get(i).toArray());
            }

            pageLabel.setText("Page " + (currentPage[0] + 1) + " of " + totalPages);
            prevBtn.setEnabled(currentPage[0] > 0);
            nextBtn.setEnabled(currentPage[0] < totalPages - 1);
        };

        prevBtn.addActionListener(e -> {
            if (currentPage[0] > 0) {
                currentPage[0]--;
                updateTable.run();
            }
        });

        nextBtn.addActionListener(e -> {
            if (currentPage[0] < totalPages - 1) {
                currentPage[0]++;
                updateTable.run();
            }
        });

        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        updateTable.run();
        dialog.setVisible(true);

    }
}


