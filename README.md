# 🐱 Url Catter

Cat ate your url and make it shorter...

# 🧰 Database Configuration

### Create database

```roomsql
CREATE DATABASE 'url_catter';
```

### Create user

```roomsql
CREATE USER 'catter'@'localhost' IDENTIFIED BY 'password';
GRANT ALL PRIVILEGES ON url_catter . * TO 'catter'@'localhost';
```

### Enable events

```roomsql
SET GLOBAL event_scheduler=ON;
```

