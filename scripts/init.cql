create keyspace T9404 with replication = {'class': 'SimpleStrategy', 'replication_factor': 1};

CREATE TABLE T9404.users
(
    username TEXT primary key,
    password TEXT,
    email    TEXT,
    roles    SET<TEXT>
);


SELECT * FROM T9404.users;
