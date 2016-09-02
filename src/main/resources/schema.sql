CREATE TABLE IF NOT EXISTS company (
  company_id   INT          NOT NULL AUTO_INCREMENT,
  company_name VARCHAR(100) NOT NULL,
  company_url  VARCHAR(200),
  PRIMARY KEY (company_id),
  CONSTRAINT uc_company_name UNIQUE (company_name)
);

CREATE TABLE IF NOT EXISTS country (
  country_id   INT          NOT NULL AUTO_INCREMENT,
  country_name VARCHAR(100) NOT NULL,
  PRIMARY KEY (country_id),
  CONSTRAINT uc_country_name UNIQUE (country_name)
);

CREATE TABLE IF NOT EXISTS city (
  city_id    INT          NOT NULL AUTO_INCREMENT,
  city_name  VARCHAR(100) NOT NULL,
  country_id INT          NOT NULL,
  PRIMARY KEY (city_id),
  FOREIGN KEY (country_id) REFERENCES country (country_id),
  CONSTRAINT uc_city_name UNIQUE (city_name)
);


CREATE TABLE IF NOT EXISTS destination (
  destination_id   INT NOT NULL AUTO_INCREMENT,
  company_id       INT NOT NULL,
  city_id          INT NOT NULL,
  request_value    VARCHAR(100),
  destination_name VARCHAR(200),
  PRIMARY KEY (destination_id),
  FOREIGN KEY (company_id) REFERENCES company (company_id),
  FOREIGN KEY (city_id) REFERENCES city (city_id),
  CONSTRAINT uniq_comp_city_req UNIQUE (company_id, city_id, request_value)

);

CREATE TABLE IF NOT EXISTS route (
  route_id            INT NOT NULL AUTO_INCREMENT,
  from_destination_id INT NOT NULL,
  to_destination_id   INT NOT NULL,
  from_city_id INT NOT NULL,
  to_city_id   INT NOT NULL,
  company_id          INT NOT NULL,
  is_active           BOOLEAN,
  has_changes BOOLEAN,
  PRIMARY KEY (route_id),
  CONSTRAINT uniq_from_to_company UNIQUE (from_destination_id, to_destination_id, company_id),
  FOREIGN KEY (from_destination_id) REFERENCES destination (destination_id),
  FOREIGN KEY (to_destination_id) REFERENCES destination (destination_id),
  FOREIGN KEY (from_city_id) REFERENCES city (city_id),
  FOREIGN KEY (to_city_id) REFERENCES city (city_id),
  FOREIGN KEY (company_id) REFERENCES company (company_id)
);


CREATE TABLE IF NOT EXISTS price (
  route_id       INT      NOT NULL,
  departure_date DATE     NOT NULL,
  departure_time TIME     NOT NULL,
  arrival_time TIME NOT NULL,
  price NUMERIC(7, 2) NOT NULL,
  currency     VARCHAR(10),
  last_update    DATETIME NOT NULL,
  PRIMARY KEY (route_id, departure_date, departure_time),
  FOREIGN KEY (route_id) REFERENCES route (route_id)

);

CREATE TABLE IF NOT EXISTS price_archive
(
  route_id       INT           NOT NULL,
  departure_date DATE          NOT NULL,
  departure_time TIME          NOT NULL,
  price          DECIMAL(7, 2) NOT NULL,
  last_update    DATETIME      NOT NULL,
  arrival_time   TIME          NOT NULL,
  currency       VARCHAR(10),
  PRIMARY KEY (route_id, departure_date, departure_time, last_update),
  FOREIGN KEY (route_id) REFERENCES route (route_id)
);