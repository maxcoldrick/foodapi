require './rabbitmq/connector.rb'
# Puma can serve each request in a thread from an internal thread pool.
# The `threads` method setting takes two numbers: a minimum and maximum.
# Any libraries that use thread pools should be configured to match
# the maximum value specified for Puma. 
threads_count = ENV.fetch("RAILS_MAX_THREADS") { 5 }
threads threads_count, threads_count

# Specifies the `port` that Puma will listen on to receive requests; default is 3000.
port        ENV.fetch("PORT") { 3000 }

# Specifies the `environment` that Puma will run in.
environment ENV.fetch("RAILS_ENV") { "development" }

# How many worker processes to run.  Typically this is set to
# to the number of available cores.
workers 1

# Code to run immediately before the master starts workers.
before_fork do
  puts "Starting workers..."
end

# Code to run in a worker before it starts serving requests.
#
# This is called everytime a worker is to be started.
on_worker_boot do
  attempt_rabbitmq_connection()
end

# Allow puma to be restarted by `rails restart` command.
plugin :tmp_restart
