CREATE TABLE IF NOT EXISTS "user" (
  "id" INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  "email" VARCHAR(50) NOT NULL UNIQUE,
  "last_name" VARCHAR(50) DEFAULT '',
  "first_name" VARCHAR(25) DEFAULT '',
  "password" VARCHAR(255) NOT NULL,
  "token" VARCHAR(255),
  "verified" BOOLEAN DEFAULT FALSE,
  "created_at" TIMESTAMPTZ DEFAULT now() NOT NULL,
  "updated_at" TIMESTAMPTZ
);

CREATE TABLE IF NOT EXISTS "portfolio" (
    "id" int GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    "name" VARCHAR(50) NOT NULL,
    "user_id" INT REFERENCES "user"("id"),
    "created_at" TIMESTAMPTZ DEFAULT now() NOT NULL,
    "updated_at" TIMESTAMPTZ
);

CREATE TABLE IF NOT EXISTS "trading_operation_type" (
    "id" int GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    "name" VARCHAR(25) NOT NULL UNIQUE,
    "created_at" TIMESTAMPTZ DEFAULT now() NOT NULL,
    "updated_at" TIMESTAMPTZ
);

CREATE TABLE IF NOT EXISTS "category" (
    "id" int GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    "name" VARCHAR(25) NOT NULL UNIQUE,
    "created_at" TIMESTAMPTZ DEFAULT now() NOT NULL,
    "updated_at" TIMESTAMPTZ
);

CREATE TABLE IF NOT EXISTS "asset" (
    "id" int GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    "name" VARCHAR(50) NOT NULL UNIQUE,
    "symbol" VARCHAR(10) NOT NULL UNIQUE,
    "price" NUMERIC(20,8) NOT NULL DEFAULT 0,
    "local" VARCHAR(8),
    "category_id" INT REFERENCES "category"("id"),
    "created_at" TIMESTAMPTZ DEFAULT now() NOT NULL,
    "updated_at" TIMESTAMPTZ
);

CREATE TABLE IF NOT EXISTS "invest_line" (
  "id" INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  "price" NUMERIC(20,8) NOT NULL,
  "date" DATE NOT NULL,
  "fees" NUMERIC(4,2) NOT NULL,
  "asset_number" NUMERIC(20,8) NOT NULL,
  "asset_id" INT REFERENCES "asset"("id"),
  "portfolio_id" INT REFERENCES "portfolio"("id"),
  "trading_operation_type_id" INT REFERENCES "trading_operation_type"("id"),
  "created_at" TIMESTAMPTZ DEFAULT now() NOT NULL,
  "updated_at" TIMESTAMPTZ
);