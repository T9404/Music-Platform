CREATE TABLE example.singer_by_name
(
    name       TEXT PRIMARY KEY,
    biography  TEXT,
    email      TEXT,
    password   TEXT,
    albums     SET<FROZEN<example.album>>
);
