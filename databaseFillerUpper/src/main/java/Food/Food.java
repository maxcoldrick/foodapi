package Food;

public class Food
{
    Food(String dish, String measurement, String description, String spice, String ingredient) {
        this.dish = dish;
        this.measurement = measurement;
        this.description = description;
        this.spice = spice;
        this.ingredient = ingredient;
    }

    private String dish;

    private String measurement;

    private String description;

    private String spice;

    private String ingredient;

    String getIngredient() {
        return ingredient;
    }

    void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    String getDescription() {
        return description;
    }

    void setDescription(String description) {
        this.description = description;
    }

    String getSpice() {
        return spice;
    }

    void setSpice(String spice) {
        this.spice = spice;
    }

    String getDish() {
        return dish;
    }

    void setDish(String dish) {
        this.dish = dish;
    }

    String getMeasurement() {
        return measurement;
    }

    void setMeasurement(String measurement) {
        this.measurement = measurement;
    }
}
