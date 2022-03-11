# 🐱 Url Catter

Cat ate your url and make it shorter...

# 🧰 Database Configuration

### Create database

```sql
CREATE DATABASE url_catter;
```

### Create user

```sql
CREATE USER 'catter'@'localhost' IDENTIFIED BY 'password';
GRANT ALL PRIVILEGES ON url_catter . * TO 'catter'@'localhost';
```

### Enable events

```sql
SET GLOBAL event_scheduler=ON;
```

