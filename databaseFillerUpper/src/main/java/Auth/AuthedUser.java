package Auth;

public class AuthedUser {
    private String accessToken;
    private String client;
    private String expiry;

    AuthedUser(String accessToken, String client, String expiry) {
        this.accessToken = accessToken;
        this.client = client;
        this.expiry = expiry;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getExpiry() {
        return expiry;
    }

    public void setExpiry(String expiry) {
        this.expiry = expiry;
    }
}
