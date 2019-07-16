package Auth;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;

public class CreateUser {
    public static void testUser() throws IOException {
        String payload = "{\"email\": \"pete@pete.pete\",\"password\": \"petepete\",\"password_confirmation\": \"petepete\"}";
        StringEntity entity = new StringEntity(payload,
                ContentType.APPLICATION_JSON);
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost request = new HttpPost("http://mcfoodapi.azurewebsites.net/auth");

        request.setEntity(entity);

        HttpResponse response = httpClient.execute(request);
        System.out.println(response.getStatusLine().getStatusCode());
    }
}