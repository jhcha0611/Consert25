package dto;

import java.util.Date;

public class CashReceiptDTO {
    private Date reservationDate;
    private int reservationNumber;
    private String productName;
    private double issuedAmount;
    private double feeAmount;

    public CashReceiptDTO(Date reservationDate, int reservationNumber, String productName, double issuedAmount, double feeAmount) {
        this.reservationDate = reservationDate;
        this.reservationNumber = reservationNumber;
        this.productName = productName;
        this.issuedAmount = issuedAmount;
        this.feeAmount = feeAmount;
    }

    public Date getReservationDate() {
        return reservationDate;
    }

    public int getReservationNumber() {
        return reservationNumber;
    }

    public String getProductName() {
        return productName;
    }

    public double getIssuedAmount() {
        return issuedAmount;
    }

    public double getFeeAmount() {
        return feeAmount;
    }
}
