package utilities;

import java.time.LocalDate;

public class Report {
    private int reportId;
    private String branchCode;
    private String rangeTag;
    private LocalDate startDate;
    private LocalDate endDate;
    private double sales;
    private String remainingStock;
    private double profit;

    public Report(int reportId, String branchCode, String rangeTag, LocalDate startDate, LocalDate endDate, double sales, String remainingStock, double profit) {
        this.reportId = reportId;
        this.branchCode = branchCode;
        this.rangeTag = rangeTag;
        this.startDate = startDate;
        this.endDate = endDate;
        this.sales = sales;
        this.remainingStock = remainingStock;
        this.profit = profit;
    }

    public int getReportId() {
        return reportId;
    }

    public void setReportId(int reportId) {
        this.reportId = reportId;
    }

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public String getRangeTag() {
        return rangeTag;
    }

    public void setRangeTag(String rangeTag) {
        this.rangeTag = rangeTag;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public double getSales() {
        return sales;
    }

    public void setSales(double sales) {
        this.sales = sales;
    }

    public String getRemainingStock() {
        return remainingStock;
    }

    public double getProfit() {
        return profit;
    }

    public void setProfit(double profit) {
        this.profit = profit;
    }
}