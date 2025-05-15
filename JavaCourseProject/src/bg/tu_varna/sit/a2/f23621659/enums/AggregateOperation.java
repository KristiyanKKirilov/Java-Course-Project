package bg.tu_varna.sit.a2.f23621659.enums;

public enum AggregateOperation {
    SUM,
    PRODUCT,
    MAXIMUM,
    MINIMUM;

    public static AggregateOperation fromString(String value) {
        return switch (value.toLowerCase()) {
            case "sum" -> SUM;
            case "product" -> PRODUCT;
            case "maximum" -> MAXIMUM;
            case "minimum" -> MINIMUM;
            default -> throw new IllegalArgumentException("Unsupported aggregate operation: " + value);
        };
    }
}
