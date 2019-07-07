package Auth;

import com.google.gson.*;

import java.lang.reflect.Type;

public class UserSerializer implements JsonSerializer<User> {
        public JsonElement serialize(final User user, final Type type, final JsonSerializationContext context) {

            JsonObject result = new JsonObject();

            result.add("email", new JsonPrimitive(user.getUsername()));
            result.add("password", new JsonPrimitive(user.getPassword()));


            return result;
        }
}
