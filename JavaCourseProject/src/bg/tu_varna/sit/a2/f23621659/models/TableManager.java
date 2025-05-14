package bg.tu_varna.sit.a2.f23621659.models;

import bg.tu_varna.sit.a2.f23621659.enums.DataType;

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
        table.getRows().addAll(rows);
        return table;
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
