import Auth.AuthedUser;
import Auth.CreateUser;
import Auth.LogIn;
import Food.Food;
import Food.FoodFactory;
import Food.FoodSerializer;
import RabbitMQ.Connector;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;


import java.io.IOException;

public class Sender {
    public static void main(String[] args) throws IOException {

        try {
            Connector.waitForMessages();
        } catch (Exception e) {
            e.printStackTrace();
        }

        CreateUser.testUser();

        LogIn li = new LogIn();
        FoodFactory ff = new FoodFactory();
        Object[] j = ff.FoodJect();

        AuthedUser authedPete = li.authMe("pete@pete.pete", "petepete");

        for (Object p : j) {
            Gson g = new GsonBuilder().registerTypeAdapter(Food.class, new FoodSerializer())
                    .create();
            String payload = g.toJson(p);
            StringEntity entity = new StringEntity(payload,
                    ContentType.APPLICATION_JSON);

            System.out.println(payload);

            HttpClient httpClient = HttpClientBuilder.create().build();
            HttpPost request = new HttpPost("http://web:3000/food");
            request.addHeader("access-token", authedPete.getAccessToken());
            request.addHeader("token-type", "Bearer");
            request.addHeader("client", authedPete.getClient());
            request.addHeader("expiry", authedPete.getExpiry());

            // If we extend AuthedUser from User then this might be better
            request.addHeader("uid", "pete@pete.pete");

            // Might not be necessary as this is set in the Entity
            request.addHeader("Content-Type", "application/json");
            request.setEntity(entity);

            HttpResponse response = httpClient.execute(request);
            System.out.println(response.getStatusLine().getStatusCode());

        }
    }
}
