package Food;

import com.github.javafaker.Faker;

public class FoodFactory {
    private Faker f = new Faker();
    public Object[] FoodJect() {
        Object[] p = new Object[100];
        int d = 0;
        for (int j = 0; j < p.length-1; j++) {

            Food i = new Food(
                    f.food().ingredient(),
                    f.food().measurement(),
                    f.lorem().sentence(),
                    f.food().spice(),
                    f.food().ingredient()
            );
            d++;
            p[d] = i;
        }
        return p;
    }
}
