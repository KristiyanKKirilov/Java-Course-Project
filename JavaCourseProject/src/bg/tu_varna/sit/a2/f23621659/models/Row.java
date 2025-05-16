package bg.tu_varna.sit.a2.f23621659.models;

import java.util.ArrayList;
import java.util.List;

public class Row {
    private final List<String> values;

    public Row(List<String> values) {
        this.values = new ArrayList<>(values);
    }

    public String get(int index) {
        return values.get(index);
    }

    public void set(int index, String value) {
        values.set(index, value);
    }

    public void add(String value) {
        values.add(value);
    }

    public int size() {
        return values.size();
    }

    public List<String> getValues() {
        return values;
    }
}
