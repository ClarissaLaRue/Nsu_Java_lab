DROP TABLE IF EXISTS node;
DROP TABLE IF EXISTS tag;

CREATE TABLE node
(
    id BIGINT NOT NULL CONSTRAINT node_pkey PRIMARY KEY,
    user_name TEXT
);

CREATE TABLE tag
(
    key TEXT,
    value   TEXT,
    node_id BIGINT NOT NULL CONSTRAINT tag_node_id_fkey REFERENCES node
);