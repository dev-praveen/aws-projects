package ind.qualitysoft.com.model;

public record CustomerInfo(
    int customerId,
    String name,
    int orderId,
    int quantity,
    double amount,
    String cancelledTimestamp) {}
