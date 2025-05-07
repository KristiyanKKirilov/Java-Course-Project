package bg.tu_varna.sit.a2.f23621659.models;

import bg.tu_varna.sit.a2.f23621659.enums.DataType;

import java.util.ArrayList;
import java.util.List;

public class Table {
    private List<String> headers;
    private List<DataType> types;
    private List<List<String>> rows;

    public Table(List<String> headers, List<DataType> types) {
        this.headers = headers;
        this.types = types;
        this.rows = new ArrayList<>();
    }

    public List<String> getHeaders() {
        return headers;
    }

    public List<String> getTypeStrings() {
        return types.stream().map(DataType::name).toList();
    }

    public List<List<String>> getRows() {
        return rows;
    }

    public void addRow(List<String> row) {
        if(row.size() != headers.size()) {
            throw new IllegalArgumentException("Row size does not match number of columns");
        }

        for (int i = 0; i < row.size(); i++) {
            DataType type = types.get(i);
            String value = row.get(i);

            if (!type.isValid(value)) {
                throw new IllegalArgumentException("Invalid value '" + value + "' for type '" + type + "' at column " + headers.get(i));
            }
        }

        rows.add(row);
    }

    public void addColumn(String columnName, DataType columnType) {
        headers.add(columnName);
        types.add(columnType);

        for (List<String> row : rows) {
            row.add("NULL");
        }
    }

    public List<String> getTableData() {
        List<List<String>> table = new ArrayList<>();
        table.add(headers);
        table.add(getTypeStrings());
        table.addAll(rows);

        return formatData(table);
    }

    public String getColumnDescription() {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < headers.size(); i++) {
            String header = headers.get(i);
            String type = getTypeStrings().get(i);
            sb.append(header + " ").append(type);

            if(i != headers.size() - 1)
                sb.append("\n");
        }

        return sb.toString();
    }

    public Table selectRowsByColumnValue(int columnIndex, String value) {
        List<List<String>> matchedRows = new ArrayList<>();


        for (List<String> row : rows) {
            if (columnIndex < row.size() && row.get(columnIndex).equals(value)) {
                matchedRows.add(row);
            }
        }

        Table filteredTable = new Table(headers, types);
        filteredTable.getRows().addAll(matchedRows);
        return filteredTable;
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
