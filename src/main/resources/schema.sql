CREATE TABLE IF NOT EXISTS company (
  company_id   INT          NOT NULL AUTO_INCREMENT,
  company_name VARCHAR(100) NOT NULL,
  company_url  VARCHAR(200),
  PRIMARY KEY (company_id)
);

CREATE TABLE IF NOT EXISTS country (
  country_id   INT          NOT NULL AUTO_INCREMENT,
  country_name VARCHAR(100) NOT NULL,
  PRIMARY KEY (country_id)
);

CREATE TABLE IF NOT EXISTS city (
  city_id    INT          NOT NULL AUTO_INCREMENT,
  city_name  VARCHAR(100) NOT NULL,
  country_id INT          NOT NULL,
  PRIMARY KEY (city_id),
  FOREIGN KEY (country_id) REFERENCES country (country_id)
);


CREATE TABLE IF NOT EXISTS destination (
  destination_id   INT NOT NULL AUTO_INCREMENT,
  company_id       INT NOT NULL,
  city_id          INT NOT NULL,
  request_value    VARCHAR(100),
  destination_name VARCHAR(200),
  PRIMARY KEY (destination_id),
  FOREIGN KEY (company_id) REFERENCES company (company_id),
  FOREIGN KEY (city_id) REFERENCES city (city_id)
);

CREATE TABLE IF NOT EXISTS route (
  route_id            INT NOT NULL AUTO_INCREMENT,
  from_destination_id INT NOT NULL,
  to_destination_id   INT NOT NULL,
  company_id          INT NOT NULL,
  is_active           BOOLEAN,
  PRIMARY KEY (route_id),
  FOREIGN KEY (from_destination_id) REFERENCES destination (destination_id),
  FOREIGN KEY (to_destination_id) REFERENCES destination (destination_id),
  FOREIGN KEY (company_id) REFERENCES company (company_id)
);


CREATE TABLE IF NOT EXISTS price (
  route_id       INT      NOT NULL AUTO_INCREMENT,
  departure_date DATE     NOT NULL,
  departure_time TIME     NOT NULL,
  price          NUMERIC  NOT NULL,
  last_update    DATETIME NOT NULL,
  FOREIGN KEY (route_id) REFERENCES route (route_id)
);