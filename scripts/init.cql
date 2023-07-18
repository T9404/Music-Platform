create keyspace test_new_nn1 with replication = {'class': 'SimpleStrategy', 'replication_factor': 1};

CREATE TABLE test_new_nn1.users
(
    username TEXT primary key,
    password TEXT,
    email    TEXT,
    roles    SET<TEXT>
);

CREATE TYPE test_new_nn1.song
(
    id   INT,
    name TEXT,
    description TEXT,
    owner TEXT,
    duration INT
);

CREATE TYPE test_new_nn1.album
(
    name TEXT,
    genre TEXT,
    owner TEXT,
    releaseDate TEXT,
    songs SET<FROZEN<test_new_nn1.song>> // song name
);

CREATE TABLE test_new_nn1.signer_by_name
(
    name       TEXT PRIMARY KEY,
    biography  TEXT,
    email      TEXT,
    password   TEXT,
    albums     SET<FROZEN<test_new_nn1.album>>
);

INSERT INTO test_new_nn1.signer_by_name (name, biography, email, password, albums)
VALUES ('Eminem', 'Eminem biography', 'signer@gmail.com', 'qwerty', {{name: 'Kamikaze', genre: 'Rap', owner: 'Enimen', releaseDate: '2018-08-31',
        songs: {{id: 1, name: 'The Ringer', description: 'The Ringer description', owner: 'Enimen', duration: 337}}}});
SELECT * FROM test_new_nn1.signer_by_name;

CREATE TABLE test_new_nn1.album_by_signer
(
    signer_name TEXT,
    genre      TEXT,
    album_name  TEXT,
    release_date TEXT,
    songs       SET<FROZEN<test_new_nn1.song>>,
    PRIMARY KEY ((signer_name), album_name)
) WITH CLUSTERING ORDER BY (album_name ASC);

INSERT INTO test_new_nn1.album_by_signer (signer_name, album_name, genre, release_date, songs)
VALUES ('Eminem', 'Kamikaze', 'Rap', '2018-08-31', {{id: 1, name: 'The Ringer', description: 'The Ringer description',
    owner: 'Enimen', duration: 337}});

SELECT * FROM test_new_nn1.album_by_signer;

CREATE TABLE test_new_nn1.album_by_genre
(
    genre      TEXT,
    album_name TEXT,
    signer_name TEXT,
    release_date TEXT,
    songs       SET<FROZEN<test_new_nn1.song>>,
    PRIMARY KEY ((genre), signer_name)
) WITH CLUSTERING ORDER BY (signer_name ASC);


SELECT * FROM test_new_nn1.users;

INSERT INTO test_new_nn1.users(username, password, email,roles) VALUES ('test_admin', '$2a$10$b4LHV4715bKsByhUnc5qJOzUhEkm5/2futNGFe4HyGBa.TJ5SZCXS', 'ok@gmail.com', {'ADMIN'});

SELECT * FROM test_new_nn1.signer_by_name;
SELECT * FROM test_new_nn1.album_by_signer;
SELECT * FROM test_new_nn1.album_by_genre;
