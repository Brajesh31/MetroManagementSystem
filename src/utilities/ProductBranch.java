package utilities;

public class ProductBranch {
    private int productId;
    private String branchCode;
    private int quantity;

    public ProductBranch(int productId, String branchCode, int quantity) {
        this.productId = productId;
        this.branchCode = branchCode;
        this.quantity = quantity;
    }

    public int getProductId() { return productId; }
    public void setProductId(int productId) { this.productId = productId; }

    public String getBranchCode() { return branchCode; }
    public void setBranchCode(String branchCode) { this.branchCode = branchCode; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
}