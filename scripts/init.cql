create keyspace test3241 with replication = {'class': 'SimpleStrategy', 'replication_factor': 1};

CREATE TABLE test3241.users
(
    username TEXT primary key,
    password TEXT,
    email    TEXT,
    role     TEXT
);

CREATE TYPE test3241.song
(
    id   TIMEUUID,
    name TEXT,
    description TEXT,
    owner TEXT,
    duration INT
);

CREATE TYPE test3241.album
(
    name TEXT,
    genre TEXT,
    owner TEXT,
    releaseDate TIMESTAMP,
    songs SET<FROZEN<test3241.song>>
);

CREATE TABLE test3241.signer_by_name
(
    name       TEXT PRIMARY KEY,
    biography  TEXT,
    email      TEXT,
    password   TEXT,
    albums     SET<FROZEN<test3241.album>>
);

/*INSERT INTO test321.signer_by_name (name, biography, email, password, albums)
VALUES ('Eminem', 'Eminem biography', 'signer@gmail.com', 'qwerty', {{name: 'Kamikaze', genre: 'Rap', owner: 'Enimen', releaseDate: '2018-08-31',
        songs: {{id: 1, name: 'The Ringer', description: 'The Ringer description', owner: 'Enimen', duration: 337}}}});*/
SELECT * FROM test3241.signer_by_name;

CREATE TABLE test3241.album_by_signer
(
    signer_name TEXT,
    genre      TEXT,
    album_name  TEXT,
    release_date TIMESTAMP,
    songs       SET<FROZEN<test3241.song>>,
    PRIMARY KEY ((signer_name), album_name)
) WITH CLUSTERING ORDER BY (album_name ASC);

/*INSERT INTO test321.album_by_signer (signer_name, album_name, genre, release_date, songs)
VALUES ('Eminem', 'Kamikaze', 'Rap', '2018-08-31', {{id: 1, name: 'The Ringer', description: 'The Ringer description',
    owner: 'Enimen', duration: 337}});*/

SELECT * FROM test3241.album_by_signer;

CREATE TABLE test3241.album_by_genre
(
    genre      TEXT,
    album_name TEXT,
    signer_name TEXT,
    release_date TIMESTAMP,
    songs       SET<FROZEN<test3241.song>>,
    PRIMARY KEY ((genre), signer_name)
) WITH CLUSTERING ORDER BY (signer_name ASC);


SELECT * FROM test3241.users;

INSERT INTO test3241.users(username, password, email, role)VALUES ('admin', '$2a$10$4yJUAtU9wzpMiPHs89/35Otpg7ltbQiPCBDuVN1EHEOe1zbrigBe6', 'admin@gmail.com', 'ADMIN');

SELECT * FROM test3241.signer_by_name;
SELECT * FROM test3241.album_by_signer;
SELECT * FROM test3241.album_by_genre;
