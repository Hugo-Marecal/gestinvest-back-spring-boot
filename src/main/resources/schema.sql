CREATE TABLE IF NOT EXISTS "user" (
  "id" UUID PRIMARY KEY DEFAULT gen_random_uuid(),
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
    "id" UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    "name" VARCHAR(50) NOT NULL,
    "user_id" UUID REFERENCES "user"("id"),
    "created_at" TIMESTAMPTZ DEFAULT now() NOT NULL,
    "updated_at" TIMESTAMPTZ
);

CREATE TABLE IF NOT EXISTS "trading_operation_type" (
    "id" UUID PRIMARY KEY DEFAULT gen_random_uuid(),
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
    "id" UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    "name" VARCHAR(50) NOT NULL UNIQUE,
    "symbol" VARCHAR(10) NOT NULL UNIQUE,
    "price" NUMERIC(20,8) NOT NULL DEFAULT 0,
    "local" VARCHAR(8),
    "category_id" INT REFERENCES "category"("id"),
    "created_at" TIMESTAMPTZ DEFAULT now() NOT NULL,
    "updated_at" TIMESTAMPTZ
);

CREATE TABLE IF NOT EXISTS "invest_line" (
  "id" UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  "price" NUMERIC(20,8) NOT NULL,
  "date" DATE NOT NULL,
  "fees" NUMERIC(4,2) NOT NULL,
  "asset_number" NUMERIC(20,8) NOT NULL,
  "asset_id" UUID REFERENCES "asset"("id"),
  "portfolio_id" UUID REFERENCES "portfolio"("id"),
  "trading_operation_type_id" UUID REFERENCES "trading_operation_type"("id"),
  "created_at" TIMESTAMPTZ DEFAULT now() NOT NULL,
  "updated_at" TIMESTAMPTZ
);