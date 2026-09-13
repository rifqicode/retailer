package Services.Transactions;

import Services.Products.Product;

public class TransactionItem {
    private Product product;
    private int quantity;
    private double priceAtPurchase;

    public TransactionItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
        this.priceAtPurchase = product.getPrice();
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPriceAtPurchase() {
        return priceAtPurchase;
    }

    public double getSubtotal() {
        return priceAtPurchase * quantity;
    }

    @Override
    public String toString() {
        return String.format("%-20s %3d x Rp%-10.0f = Rp%-10.0f",
                product.getName(), quantity, priceAtPurchase, getSubtotal());
    }
}
