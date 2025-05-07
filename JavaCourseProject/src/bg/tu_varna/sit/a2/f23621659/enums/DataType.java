package bg.tu_varna.sit.a2.f23621659.enums;

public enum DataType {
    INT {
        public boolean isValid(String value) {
            return value.matches("-?\\d+");
        }
    },
    DOUBLE {
        public boolean isValid(String value) {
            return value.matches("-?\\d+(\\.\\d+)?");
        }
    },
    STRING {
        public boolean isValid(String value) {
            return true;
        }
    };

    public abstract boolean isValid(String value);

    public static DataType fromString(String type) {
        return switch (type.toUpperCase()) {
            case "INT" -> DataType.INT;
            case "DOUBLE" -> DataType.DOUBLE;
            case "STRING" -> DataType.STRING;
            default -> throw new IllegalArgumentException("Unsupported data type: " + type);
        };
    }
}
