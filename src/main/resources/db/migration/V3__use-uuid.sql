CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

ALTER TABLE genre
DROP COLUMN id;

ALTER TABLE genre
    ADD COLUMN id UUID PRIMARY KEY DEFAULT uuid_generate_v4();
