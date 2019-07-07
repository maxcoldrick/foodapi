package Food;

import com.google.gson.*;

import java.lang.reflect.Type;

public class FoodSerializer implements JsonSerializer<Food> {
    public JsonElement serialize(final Food food, final Type type, final JsonSerializationContext context) {

        JsonObject result = new JsonObject();

        result.add("dish", new JsonPrimitive(food.getDish()));
        result.add("measurement", new JsonPrimitive(food.getMeasurement()));
        result.add("description", new JsonPrimitive(food.getDescription()));
        result.add("spice", new JsonPrimitive(food.getSpice()));
        result.add("ingredient", new JsonPrimitive(food.getIngredient()));

        return result;
    }
}
