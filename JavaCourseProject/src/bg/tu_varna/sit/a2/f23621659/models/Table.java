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

    public List<String> getHeaders() {
        return headers;
    }

    public List<String> getTypes() {
        return types;
    }

    public List<List<String>> getRows() {
        return rows;
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

    private  List<String> formatData(List<List<String>> table) {
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

    private String createBorder(int[] widths) {
        StringBuilder border = new StringBuilder("+");
        for (int width : widths) {
            border.append("-".repeat(width + 2)).append("+");
        }
        return border.toString();
    }


}
