/**
 * @author Dino Cajic
 */
public class Item {

    private String location, itemNumber;
    private int qty;

    Item(String location, String itemNumber, int qty) {
        this.setLocation( location );
        this.setItemNumber( itemNumber );
        this.setQty( qty );
    }

    private void setLocation(String location) {
        this.location = location;
    }

    private void setItemNumber(String itemNumber) {
        this.itemNumber = itemNumber;
    }

    private void setQty(int qty) {
        this.qty = qty;
    }

    private String getLocation() {
        return this.location;
    }

    private String getItemNumber() {
        return this.itemNumber
                .replace("-", "x")
                .replace("+", "_");
    }

    private int getQty() {
        return this.qty;
    }

    @Override
    public String toString() {
        return this.getItemNumber() +
                this.getLocation() +
                "=" +
                this.getQty() +
                ",";
    }
}
