package gr.myaigoprov.ui.milk;

public class DailyMilkProduction {
    private String date;
    private double totalQuantity;

    public DailyMilkProduction(String date, double totalQuantity) {
        this.date = date;
        this.totalQuantity = totalQuantity;
    }

    public String getDate() {
        return date;
    }

    public double getTotalQuantity() {
        return totalQuantity;
    }
}

