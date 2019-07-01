import com.google.gson.Gson;

public class Sender {
    public static void main(String[] args) {
        Object j = create();
        Gson g = MakeJson.toJson(j);
        System.out.println(g.toJson(j));
    }

    private static Object create(){
        Food i = new Food();

        i.setDish("Eggs");
        i.setMeasurement("Five");

        return i;

    }
}
