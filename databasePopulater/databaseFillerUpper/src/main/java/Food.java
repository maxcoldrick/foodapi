public class Food
{
    private String dish;

    private String measurement;

    public String getDish() {
        return dish;
    }

    public void setDish(String dish) {
        this.dish = dish;
    }

    public String getMeasurement() {
        return measurement;
    }

    public void setMeasurement(String measurement) {
        this.measurement = measurement;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [dish = "+dish+", measurement = "+measurement+"]";
    }
}
