import com.google.gson.Gson;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;

public class Sender {
    public static void main(String[] args) throws IOException, InterruptedException {
        Thread.sleep(5000);
        Object j = create();
        Gson g = MakeJson.toJson(j);
        String payload = g.toJson(j);
        StringEntity entity = new StringEntity(payload,
                ContentType.APPLICATION_JSON);

        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost request = new HttpPost("http://localhost:3000/food");
        request.setEntity(entity);

        HttpResponse response = httpClient.execute(request);
        System.out.println(response.getStatusLine().getStatusCode());
    }

    private static Object create(){
        Food i = new Food();

        i.setDish("Beans");
        i.setMeasurement("One Hundred");

        return i;

    }
}
