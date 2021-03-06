package RabbitMQ;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;

public class Connector {
    private final static String QUEUE_NAME = "readinessChecker";

    public static void waitForMessages() throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("rabbitmq");

        // Set port for k8s
        factory.setPort(7001);

        boolean repeat=true;
        while(repeat)
        {
            try {
                Connection connection = factory.newConnection();
                Channel channel = connection.createChannel();

                channel.queueDeclare(QUEUE_NAME, false, false, false, null);
                System.out.println("Waiting for API to be ready");

                DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                    String message = new String(delivery.getBody(), "UTF-8");
                    System.out.println(" [x] Received: '" + message + "'");
                };
                channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> { });

                repeat=false;
            }
            catch (IOException e) {
                System.out.println("Failed to connect. Retrying in 10secs...maybe....");
                Thread.sleep(10000);
            }
        }
    }
}
