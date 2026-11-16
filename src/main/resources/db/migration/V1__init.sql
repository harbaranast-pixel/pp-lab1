CREATE TABLE clients (
                         id BIGSERIAL PRIMARY KEY,
                         name VARCHAR(150) NOT NULL,
                         phone VARCHAR(50) NOT NULL
);

CREATE TABLE cars (
                      vin_code VARCHAR(50) PRIMARY KEY,
                      brand VARCHAR(100) NOT NULL,
                      model VARCHAR(100) NOT NULL,
                      manufacturing_year INT NOT NULL,
                      owner_id BIGINT NOT NULL REFERENCES clients(id)
);

CREATE TABLE mechanics (
                           id BIGSERIAL PRIMARY KEY,
                           name VARCHAR(150) NOT NULL,
                           specialization VARCHAR(100) NOT NULL
);

CREATE TABLE repair_orders (
                               id VARCHAR(36) PRIMARY KEY,
                               car_vin VARCHAR(50) NOT NULL REFERENCES cars(vin_code),
                               client_id BIGINT NOT NULL REFERENCES clients(id),
                               mechanic_id BIGINT REFERENCES mechanics(id),
                               status VARCHAR(30) NOT NULL
);

CREATE TABLE tasks (
                       id BIGSERIAL PRIMARY KEY,
                       repair_order_id VARCHAR(36) NOT NULL REFERENCES repair_orders(id) ON DELETE CASCADE,
                       description VARCHAR(255) NOT NULL,
                       detail_price NUMERIC(10, 2) NOT NULL,
                       work_price NUMERIC(10, 2) NOT NULL,
                       time_minutes INT NOT NULL DEFAULT 0,
                       is_completed BOOLEAN NOT NULL DEFAULT FALSE
);