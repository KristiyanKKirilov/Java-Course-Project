package bg.tu_varna.sit.a2.f23621659.models;

import java.util.ArrayList;
import java.util.List;

public class Table {
    private List<String> headers;
    private List<String> types;
    private List<List<String>> rows;

    public Table(List<String> headers, List<String> types) {
        this.headers = headers;
        this.types = types;
        this.rows = new ArrayList<>();
    }

    public void addRow(List<String> row) {
        if(row.size() != headers.size()) {
            throw new IllegalArgumentException("Row size does not match number of columns");
        }

        rows.add(row);
    }

    public List<String> getTableData() {
        List<List<String>> table = new ArrayList<>();
        table.add(headers);
        table.add(types);
        table.addAll(rows);

        return formatData(table);
    }

    public static Table createTable(List<String> lines) {
        List<List<String>> extractedData = extractData(lines);

        if(extractedData.size() < 2) {
            throw new IllegalArgumentException("Table must contain at least header and type rows");
        }

        List<String> headers = extractedData.get(0);
        List<String> types = extractedData.get(1);
        List<List<String>> rows = extractedData.subList(2, extractedData.size());

        Table table = new Table(headers, types);
        table.rows.addAll(rows);
        return table;
    }

    public String getColumnDescription() {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < headers.size(); i++) {
            String header = headers.get(i);
            String type = types.get(i);
            sb.append(header + " ").append(type);

            if(i != headers.size() - 1)
                sb.append("\n");
        }

        return sb.toString();
    }

    private static  List<String> formatData(List<List<String>> table) {
        List<String> result = new ArrayList<>();

        if(table.isEmpty())
            return result;

        int columns = table.get(0).size();
        int[] maxWidths = new int[columns];

        for(List<String> row : table) {
            for(int i = 0; i < columns; i++) {
                if(i < row.size()) {
                    int length = row.get(i).length();

                    if(length > maxWidths[i]) {
                        maxWidths[i] = length;
                    }
                }
            }
        }

        String border = createBorder(maxWidths);
        result.add(border);

        for (int i = 0; i < table.size(); i++) {
            List<String> row = table.get(i);
            StringBuilder sb = new StringBuilder("|");

            for (int j = 0; j < columns; j++) {
                String content = j < row.size() ? row.get(j) : "";
                content = String.format("%-" + maxWidths[j] + "s", content);
                sb.append(" ").append(content).append(" |");
            }

            result.add(sb.toString());

            if(i == 0 || i == 1)
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

    private static List<List<String>> extractData(List<String> lines) {
        List<List<String>> tableData = new ArrayList<>();

        for (String line : lines) {
            line = line.trim();
            if (line.startsWith("+") || line.isEmpty()) continue;

            String[] cells = line.split("\\|");
            List<String> row = new ArrayList<>();
            for (String cell : cells) {
                String trimmed = cell.trim();
                if (!trimmed.isEmpty()) row.add(trimmed);
            }

            tableData.add(row);
        }

        return tableData;
    }
}
