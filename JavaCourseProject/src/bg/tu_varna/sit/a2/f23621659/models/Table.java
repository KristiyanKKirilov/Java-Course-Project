package bg.tu_varna.sit.a2.f23621659.models;

import bg.tu_varna.sit.a2.f23621659.enums.AggregateOperation;
import bg.tu_varna.sit.a2.f23621659.enums.DataType;

import java.util.ArrayList;
import java.util.List;

public class Table {
    private List<String> headers;
    private List<DataType> types;
    private List<Row> rows;

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

    public List<DataType> getTypes() {
        return types;
    }

    public List<Row> getRows() {
        return rows;
    }

    public void addRow(List<String> rowValues) throws IllegalArgumentException {
        if(rowValues.size() != headers.size()) {
            throw new IllegalArgumentException("Row size does not match number of columns");
        }

        for (int i = 0; i < rowValues.size(); i++) {
            DataType type = types.get(i);
            String value = rowValues.get(i);

            if (!type.isValid(value)) {
                throw new IllegalArgumentException("Invalid value '" + value + "' for type '" + type + "' at column " + headers.get(i));
            }
        }

        rows.add(new Row(rowValues));
    }

    public void addColumn(String columnName, DataType columnType) {
        headers.add(columnName);
        types.add(columnType);

        for (Row row : rows) {
            row.add("NULL");
        }
    }

    public List<String> getTableData() {
        List<List<String>> table = new ArrayList<>();
        table.add(headers);
        table.add(getTypeStrings());

        for (Row row : rows) {
            table.add(row.getValues());
        }

        return formatData(table);
    }

    public String getColumnDescription() {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < headers.size(); i++) {
            String header = headers.get(i);
            String type = getTypeStrings().get(i);
            sb.append(header).append(" ").append(type);

            if(i != headers.size() - 1)
                sb.append("\n");
        }

        return sb.toString();
    }

    public Table selectRowsByColumnValue(int columnIndex, String value) {
        List<Row> matchedRows = new ArrayList<>();

        for (Row row : rows) {
            if (columnIndex < row.size() && row.get(columnIndex).equals(value)) {
                matchedRows.add(new Row(row.getValues()));
            }
        }

        Table filteredTable = new Table(headers, types);
        filteredTable.getRows().addAll(matchedRows);
        return filteredTable;
    }

    public void deleteRowsByColumnValue(int columnIndex, String value) throws IllegalArgumentException {
        if(columnIndex < 0 || columnIndex >= headers.size()) {
            throw new IllegalArgumentException("Invalid column index");
        }

        rows.removeIf(row -> row.get(columnIndex).equals(value));
    }

    public void updateRowsByColumnValue(int searchColumnIndex, String searchValue, int targetColumnIndex, String targetValue) throws IllegalArgumentException {
        if(searchColumnIndex < 0 || searchColumnIndex >= headers.size()) {
            throw new IllegalArgumentException("Invalid search column index");
        }

        if(targetColumnIndex < 0 || targetColumnIndex >= headers.size()) {
            throw new IllegalArgumentException("Invalid target column index");
        }

        DataType type = types.get(targetColumnIndex);

        if(!"NULL".equals(targetValue) && !type.isValid(targetValue)) {
            throw new IllegalArgumentException("Invalid value " + targetValue + " for type " + type);
        }

        for (Row row : rows) {
            if (row.get(searchColumnIndex).equals(searchValue)) {
                row.set(targetColumnIndex, targetValue);
            }
        }

    }

    public int countRowsByColumnValue(int columnIndex, String value) throws IllegalArgumentException {
        if(columnIndex < 0 || columnIndex >= headers.size()) {
            throw new IllegalArgumentException("Invalid column index");
        }

        int count = 0;

        for (Row row : rows) {
            if (columnIndex < row.size() && row.get(columnIndex).equals(value)) {
                count++;
            }
        }

        return count;
    }

    public double aggregate(int searchColumnIndex, String searchValue, int targetColumnIndex, AggregateOperation operation) {
        if(searchColumnIndex < 0
                || searchColumnIndex >= headers.size()
                || targetColumnIndex < 0
                || targetColumnIndex >= headers.size()
        ) {
            throw new IllegalArgumentException("Invalid column index");
        }

        DataType targetType = types.get(targetColumnIndex);

        if(targetType != DataType.INT && targetType != DataType.DOUBLE) {
            throw new IllegalArgumentException("Only numeric columns allow aggregation operations");
        }

        List<Double> values = new ArrayList<>();

        for(Row row : rows) {
            String searchColumnValue = row.get(searchColumnIndex);
            String targetColumnValue = row.get(targetColumnIndex);

            if(searchColumnValue.equals(searchValue) && !targetColumnValue.equals("NULL")) {
                try {
                    values.add(Double.parseDouble(targetColumnValue));
                } catch(NumberFormatException ex) {
                    throw new IllegalArgumentException("Invalid numeric value in target column: " + targetColumnValue);
                }
            }
        }

        if(values.isEmpty()) {
            throw new IllegalArgumentException("No matching numeric values found for aggregation");
        }

        return switch (operation) {
            case SUM -> values.stream().mapToDouble(Double::doubleValue).sum();
            case PRODUCT -> values.stream().reduce(1.0, (a,b) -> a* b);
            case MAXIMUM -> values.stream().mapToDouble(Double::doubleValue).max().orElseThrow();
            case MINIMUM -> values.stream().mapToDouble(Double::doubleValue).min().orElseThrow();
            default -> throw new IllegalArgumentException("Unsupported operation " + operation.name());
        };
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
