package Auth;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.http.HttpEntity;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class LogIn {
    public AuthedUser authMe(String username, String password) throws IOException {
        User pete = new User();
        pete.setUsername(username);
        pete.setPassword(password);

        Gson g = new GsonBuilder().registerTypeAdapter(User.class, new UserSerializer())
                .create();
        String payload = g.toJson(pete);
        StringEntity entity = new StringEntity(payload,
                ContentType.APPLICATION_JSON);

        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost request = new HttpPost("http://web:3000/auth/sign_in");

        request.setEntity(entity);

        CloseableHttpResponse response = (CloseableHttpResponse) httpClient.execute(request);
        System.out.println(response.getStatusLine());
        HttpEntity entity1 = response.getEntity();

        String token = response.getFirstHeader("access-token").getValue();
        String client = response.getFirstHeader("client").getValue();
        String expiry = response.getFirstHeader("expiry").getValue();

        EntityUtils.consume(entity1);

        return new AuthedUser(token, client, expiry);

    }
}
