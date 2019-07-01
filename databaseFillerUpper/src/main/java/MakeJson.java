import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class MakeJson {
    public static Gson toJson(Object p) {
        Gson gson = new GsonBuilder().registerTypeAdapter(Food.class, new FoodSerializer())
                .create();

        return gson;
    }
}
