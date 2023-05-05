  --Users aanmaken + authorities
  INSERT INTO users (employee_Id, first_Name, last_Name, password, dob, enabled, apikey) VALUES (123456, 'TestAdmin', 'Test', '$2y$10$5AiMvCASciUQktAUZl/RfelglEU35n3pnVCFgmlkMybDfENSbXpXO', DATE '1900-05-01', true, '7847493');
  INSERT INTO authorities (employee_Id, authority) VALUES (123456, 'ADMIN');
  INSERT INTO authorities (employee_Id, authority) VALUES (123456, 'USER');
  INSERT INTO users (employee_Id, first_Name, last_Name, password, dob, enabled, apikey) VALUES (123457, 'TestUser', 'Test', '$2y$10$5AiMvCASciUQktAUZl/RfelglEU35n3pnVCFgmlkMybDfENSbXpXO', DATE '2000-01-01', true, '3947487');
  INSERT INTO authorities (employee_Id, authority) VALUES (123457, 'USER');

  --Customers aanmaken
  INSERT INTO customers (id, first_Name, last_Name, address, zip_Code, city, phone_Number, email) VALUES (10000, 'Bart', 'Ook Klant', 'Schoolstraat 1', '1234 AB', 'Grote Stad', 0987123456, 'bart@testklant.nl');
  INSERT INTO customers (id, first_Name, last_Name, address, zip_Code, city, phone_Number, email) VALUES (10001, 'Piet', 'De Derde', 'Het Plein 101', '1029 AS', 'Het Meer', 0213987654, 'piet@dederde.nl');

--Earpieces aanmaken
  INSERT INTO earpieces (id, type, colour, size, price) VALUES (10000, 'Open Dome', 'Transparent', 'Large', 0.15);
  INSERT INTO earpieces (id, type, colour, size, price) VALUES (10001, 'Open', 'Pink', 'Custom', 40);

--Hearing aids aanmaken
  INSERT INTO hearingaids (productcode, brand, type, colour, price) VALUES ('dj20rg4', 'Phonak', 'Audéo Lumity L90-R', 'Champagne', 3000.00);
  INSERT INTO hearingaids (productcode, brand, type, colour, price) VALUES ('h389dg4i', 'AudioNova', 'DX 90 R Li T', 'Silver', 500.00);

--Receipts aanmaken
  INSERT INTO receipts (id, sale_Date) VALUES (10000, '2023-01-02');
  INSERT INTO receipts (id, sale_Date) VALUES (10001, '2023-01-03');
