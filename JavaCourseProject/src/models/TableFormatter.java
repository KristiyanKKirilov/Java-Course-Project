package models;

import java.util.ArrayList;
import java.util.List;

public class TableFormatter {
    public static List<List<String>> extractData(List<String> lines) {
        List<List<String>> tableData = new ArrayList<>();

        for (String line: lines) {
            line = line.trim();

            if(line.startsWith("+") || line.isEmpty()) {
                continue;
            }

            String[] cells = line.split("\\|");

            List<String> row = new ArrayList<>();

            for (String cell : cells) {
                String trimmed = cell.trim();

                if(!trimmed.isEmpty()) {
                    row.add(trimmed);
                }
            }

            tableData.add(row);
        }

        return tableData;
    }

    public static List<String> formatData(List<List<String>> table) {
        List<String> result = new ArrayList<>();

        if(table.isEmpty())
            return result;

        int columns = table.get(0).size();
        int[] maxWidths = new int[columns];

        for (List<String> row : table) {
            for (int i = 0; i < columns; i ++) {
                int length = row.get(i).length();

                if(length > maxWidths[i])
                {
                    maxWidths[i] = length;
                }
            }
        }

        String border = createBorder(maxWidths);
        result.add(border);

        for (int i = 0; i < table.size(); i++) {
            List<String> row = table.get(i);

            StringBuilder sb = new StringBuilder("|");

            for (int j = 0; j < columns; j++) {
                String content = String.format("%-" + maxWidths[j] + "s", row.get(j));
                sb.append(" ").append(content).append(" |");
            }

            result.add(sb.toString());

            if(i == 0)
                result.add(border);
        }

        result.add(border);
        return result;
    }


    private static String createBorder(int[] widths) {
        StringBuilder border = new StringBuilder("+");
        for (int width : widths) {
            border.append("-".repeat(width + 2)).append("+");
        }
        return border.toString();
    }
}
