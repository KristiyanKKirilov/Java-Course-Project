package bg.tu_varna.sit.a2.f23621649.models;

import bg.tu_varna.sit.a2.f23621649.enums.DataType;

import java.util.ArrayList;
import java.util.List;

public class TableManager {
    public static Table createTable(List<String> lines) {
        List<List<String>> extractedData = extractData(lines);

        if(extractedData.size() < 2) {
            throw new IllegalArgumentException("Table must contain at least header and type rows");
        }

        List<String> headers = extractedData.get(0);
        List<String> typesString = extractedData.get(1);
        List<List<String>> rows = extractedData.subList(2, extractedData.size());

        List<DataType> types = new ArrayList<>();
        for(String type: typesString) {
            types.add(DataType.fromString(type));
        }

        Table table = new Table(headers, types);
        for (List<String> rowData : rows) {
            table.addRow(rowData);
        }

        return table;
    }

    public static Table innerJoinTables(Table firstTable, int firstTableColumnIndex, Table secondTable, int secondTableColumnIndex) throws IllegalArgumentException{
        if (firstTableColumnIndex < 0
                || firstTableColumnIndex >= firstTable.getHeaders().size()
                || secondTableColumnIndex < 0
                || secondTableColumnIndex >= secondTable.getHeaders().size()) {
            throw new IllegalArgumentException("Invalid column index for join.");
        }

        if(!firstTable.getHeaders().get(firstTableColumnIndex).equalsIgnoreCase(secondTable.getHeaders().get(secondTableColumnIndex))) {
            throw new IllegalArgumentException("Given indexes do not match PK->FK relation");
        }

        List<String> newHeaders = new ArrayList<>(firstTable.getHeaders());
        List<DataType> newTypes = new ArrayList<>(firstTable.getTypes());

            newHeaders.addAll(secondTable.getHeaders());
            newTypes.addAll(secondTable.getTypes());

        Table result = new Table(newHeaders, newTypes);

        for (Row row1 : firstTable.getRows()) {
            List<String> newRow = new ArrayList<>(row1.getValues());

            Table secondTableValues = secondTable.selectRowsByColumnValue(secondTableColumnIndex, newRow.get(firstTableColumnIndex));

            List<String> rowsToAdd = new ArrayList<>();
            if(!secondTableValues.getRows().isEmpty()) {
                rowsToAdd = secondTableValues.getRows().getFirst().getValues();
            } else {
                List<String> nulls = new ArrayList<>();
                for(int i = 0; i < secondTable.getHeaders().size(); i++) {
                    nulls.add("NULL");
                }

                rowsToAdd = nulls;
            }
            newRow.addAll(rowsToAdd);
            result.addRow(newRow);
        }

        return result;
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
