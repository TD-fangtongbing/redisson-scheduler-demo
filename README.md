# redisson-scheduler-demo
# startup application
- docker-compose up -d redis rabbitmq
- run RedissonSchedulerDemoApplication.main()
# test
use postman GET localhost:8080/sendMsg
it will send an event to mq and consume it, then submit a task to Redisson.
