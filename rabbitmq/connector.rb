require 'bunny'

def attempt_rabbitmq_connection()
  # Try to connect to rabbitmq
  begin
    retries ||= 0

    # This doesn't go first time, so print how many it takes
    # TODO backoff strategy
    puts "Attempting to connect, attempt: #{ retries+1 }"
    connection = Bunny.new(hostname: 'rabbitmq')
    connection.start
    channel = connection.create_channel
    queue = channel.queue('hello')

    channel.default_exchange.publish('Hello World!', routing_key: queue.name)
    puts " [x] Sent 'Hello World!'"
    connection.close

  rescue Bunny::TCPConnectionFailedForAllHosts => e
    puts "Host isn't ready. Trying again in 11s..."

    sleep(11)

    # Only retry 3 times, otherwise something went badly wrong
    retry if (retries += 1) < 3
  end
end
