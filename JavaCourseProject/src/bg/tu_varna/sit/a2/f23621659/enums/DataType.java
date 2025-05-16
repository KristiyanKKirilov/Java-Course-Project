package bg.tu_varna.sit.a2.f23621659.enums;

public enum DataType {
    INT {
        public boolean isValid(String value) {
            return value.equals("NULL") || value.matches("[+-]?\\d+");
        }
    },
    DOUBLE {
        public boolean isValid(String value) {
            return value.equals("NULL") || value.matches("[+-]?\\d+") || value.matches("[+-]?\\d+\\.\\d+");
        }
    },
    STRING {
        public boolean isValid(String value) {
            return value.equals("NULL") || !value.isBlank();
//                    || value.matches("\"(\\\\.|[^\"\\\\])*\"");
        }
    };

    public abstract boolean isValid(String value);

    protected boolean isNull(String value) {
        return "NULL".equalsIgnoreCase(value);
    }

    public static DataType fromString(String type) {
        return switch (type.toUpperCase()) {
            case "INT" -> DataType.INT;
            case "DOUBLE" -> DataType.DOUBLE;
            case "STRING" -> DataType.STRING;
            default -> throw new IllegalArgumentException("Unsupported data type: " + type);
        };
    }
}
