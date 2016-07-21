CREATE TABLE routes( route_to VARCHAR(100) NOT NULL,
                      route_from VARCHAR(100) NOT NULL,
                      min_price numeric(12,2) NOT NULL,
                      date_of_trip date not NULL ,
                      tlm date
  );