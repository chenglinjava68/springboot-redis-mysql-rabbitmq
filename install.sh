#!/bin/sh
ALARM_DIR=/opt/server/springboot-redis-mysql-rabbitmq
ALARM_LOG_DIR=/var/log/springboot-redis-mysql-rabbitmq/
if [ ! -x "$ALARM_DIR" ]; then
  mkdir -p "$ALARM_DIR"
fi
if [ -d "$ALARM_DIR" ]; then
  rm -rf $ALARM_DIR
fi
#
tar -xzf springboot-redis-mysql-rabbitmq-0.0.1.tar.gz -C /opt/server/
mv /opt/server/springboot-redis-mysql-rabbitmq-0.0.1 $ALARM_DIR
cp `pwd`/springboot-redis-mysql-rabbitmq /etc/init.d/

#make log file
#/var/log/springboot-redis-mysql-rabbitmq/
if [ ! -x "$ALARM_LOG_DIR" ]; then
   mkdir -p "$ALARM_LOG_DIR"
fi
echo "mir-alarm install successful"
echo "Use '/etc/init.d/springboot-redis-mysql-rabbitmq start|stop|restart' " 


