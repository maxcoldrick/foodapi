require 'bunny'

def attempt_rabbitmq_connection()
  # Try to connect to rabbitmq
  begin
    retries ||= 0

    # This doesn't go first time, so print how many it takes
    # TODO backoff strategy
    puts "Attempting to connect, attempt: #{ retries+1 }"

    # Declared port 7001 for k8s which is 7001:5672
    connection = Bunny.new(hostname: 'rabbitmq', port: 7001)
    connection.start
    channel = connection.create_channel
    queue = channel.queue('readinessChecker')

    channel.default_exchange.publish('Ready', routing_key: queue.name)
    puts "Exposed myself as ready"
    connection.close

  rescue Bunny::TCPConnectionFailedForAllHosts => e
    puts "Host isn't ready. Trying again in 11s..."

    sleep(11)

    # Only retry 5 times, otherwise something went badly wrong
    retry if (retries += 1) < 5
  end
end
