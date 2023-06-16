--Users aanmaken + authorities
  INSERT INTO users (employee_Id, first_Name, last_Name, password, dob, enabled, apikey) VALUES (123456, 'TestAdmin', 'Test', '$2y$10$5AiMvCASciUQktAUZl/RfelglEU35n3pnVCFgmlkMybDfENSbXpXO', DATE '1900-05-01', true, '7847493');
  INSERT INTO authorities (employee_Id, authority) VALUES (123456, 'ADMIN');
  INSERT INTO authorities (employee_Id, authority) VALUES (123456, 'USER');
  INSERT INTO users (employee_Id, first_Name, last_Name, password, dob, enabled, apikey) VALUES (123457, 'TestUser', 'Test', '$2y$10$5AiMvCASciUQktAUZl/RfelglEU35n3pnVCFgmlkMybDfENSbXpXO', DATE '2000-01-01', true, '3947487');
  INSERT INTO authorities (employee_Id, authority) VALUES (123457, 'USER');

--Customers aanmaken
  INSERT INTO customers (id, first_Name, last_Name, address, zip_Code, city, phone_Number, email) VALUES (10000, 'Bart', 'Ook Klant', 'Schoolstraat 1', '1234 AB', 'Grote Stad', 0987123456, 'bart@testklant.nl');
  INSERT INTO customers (id, first_Name, last_Name, address, zip_Code, city, phone_Number, email) VALUES (10001, 'Piet', 'De Derde', 'Het Plein 101', '1029 AS', 'Het Meer', 0213987654, 'piet@dederde.nl');

--Hearing aids aanmaken
  INSERT INTO hearingaids (productcode, brand, type, colour, price, customer_id) VALUES ('dj20rg4', 'Phonak', 'Audéo Lumity L90-R', 'Champagne', 3000.00, 10000);
  INSERT INTO hearingaids (productcode, brand, type, colour, price, customer_id) VALUES ('h389dg4i', 'AudioNova', 'DX 90 R Li T', 'Silver', 500.00, 10001);
  INSERT INTO hearingaids (productcode, brand, type, colour, price) VALUES ('497wrgf9', 'Phonak', 'Slim P90-R', 'Graphite', 2245.00);

--Earpieces aanmaken
  INSERT INTO earpieces (id, type, colour, size, price, hearing_aid_productcode) VALUES (20000, 'Open Dome', 'Transparent', 'Large', 0.15, 'dj20rg4');
  INSERT INTO earpieces (id, type, colour, size, price, hearing_aid_productcode) VALUES (20001, 'Open', 'Pink', 'Custom', 40, 'h389dg4i');

--Receipts aanmaken
  INSERT INTO receipts (id, customer_id) VALUES (30000, 10000);
  INSERT INTO receipts (id, customer_id) VALUES (30001, 10001);
  INSERT INTO receipts_ear_piece_list (receipt_id, ear_piece_list_id) VALUES (30000, 20000);
  INSERT INTO receipts_ear_piece_list (receipt_id, ear_piece_list_id) VALUES (30001, 20001);
  INSERT INTO receipts_hearing_aid_list (receipt_id, hearing_aid_list_productcode) VALUES (30000, 'dj20rg4');
  INSERT INTO receipts_hearing_aid_list (receipt_id, hearing_aid_list_productcode) VALUES (30001, 'h389dg4i');

--Receipts toevoegen aan earpieces en hearing aids
  UPDATE hearingaids SET receipt_id = 30000 WHERE productcode = 'dj20rg4';
  UPDATE hearingaids SET receipt_id = 30001 WHERE productcode = 'h389dg4i';
  UPDATE earpieces SET receipt_id = 30000 WHERE id = 20000;
  UPDATE earpieces SET receipt_id = 30001 WHERE id = 20001;


