CREATE DATABASE IF NOT EXISTS ride_auth_db;
CREATE DATABASE IF NOT EXISTS ride_passenger_db;
CREATE DATABASE IF NOT EXISTS ride_driver_db;
CREATE DATABASE IF NOT EXISTS ride_trip_db;
CREATE DATABASE IF NOT EXISTS ride_notification_db;

GRANT ALL PRIVILEGES ON ride_auth_db.* TO 'rideshare'@'%';
GRANT ALL PRIVILEGES ON ride_passenger_db.* TO 'rideshare'@'%';
GRANT ALL PRIVILEGES ON ride_driver_db.* TO 'rideshare'@'%';
GRANT ALL PRIVILEGES ON ride_trip_db.* TO 'rideshare'@'%';
GRANT ALL PRIVILEGES ON ride_notification_db.* TO 'rideshare'@'%';
FLUSH PRIVILEGES;
