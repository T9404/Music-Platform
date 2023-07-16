create keyspace test_old with replication = {'class': 'SimpleStrategy', 'replication_factor': 1};

CREATE TABLE test_old.users
(
    username TEXT primary key,
    password TEXT,
    email    TEXT,
    roles    SET<TEXT>
);


SELECT * FROM test_old.users;
