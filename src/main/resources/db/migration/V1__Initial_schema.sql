CREATE TABLE orders (
    id BIGSERIAL PRIMARY KEY NOT NULL,
    game_name varchar(255) NOT NULL,
    game_creator varchar(255),
    game_price float8,
    tip float8,
    status varchar(255) NOT NULL,
    created_date timestamp NOT NULL,
    last_modified_date timestamp NOT NULL,
    version integer NOT NULL
)