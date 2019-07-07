import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;

public class demo {
    public static void main(String[] args) throws IOException {
        String payload = "{\"email\": \"pete@pete.pete\",\"password\": \"petepete\",\"password_confirmation\": \"petepete\"}";
        StringEntity entity = new StringEntity(payload,
                ContentType.APPLICATION_JSON);
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost request = new HttpPost("http://localhost:3000/auth");
        request.addHeader("Cache-Control", "no-cache");
        request.addHeader("Connection", "keep-alive");
        request.addHeader("Content-Type", "application/json");

        request.setEntity(entity);

        HttpResponse response = httpClient.execute(request);
        System.out.println(response.getStatusLine().getStatusCode());
    }
}

//  -H 'Accept: */*' \
//          -H 'Cache-Control: no-cache' \
//          -H 'Connection: keep-alive' \
//          -H 'Content-Type: application/json' \
//          -H 'Host: localhost:3000' \
//          -H 'Postman-Token: 4fe3b418-cfca-417e-aba3-d977ea553aee,ba5f4583-9802-4e7c-b561-524baacace45' \
//          -H 'User-Agent: PostmanRuntime/7.15.0' \
//          -H 'accept-encoding: gzip, deflate' \
//          -H 'cache-control: no-cache' \
//          -H 'content-length: 86' \
//          -d '{"email": "pete@pete.pete","password": "petepete","password_confirmation": "petepete"}'