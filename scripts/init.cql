create keyspace template with replication = {'class': 'SimpleStrategy', 'replication_factor': 1};

CREATE TABLE template.users
(
    username TEXT primary key,
    password TEXT,
    roles    SET<TEXT>
);