  INSERT INTO users (employee_Id, first_Name, last_Name, password, dob, enabled, apikey) VALUES (123456, 'Tester', 'Test', '$2y$10$5AiMvCASciUQktAUZl/RfelglEU35n3pnVCFgmlkMybDfENSbXpXO', DATE '1900-05-01', true, '7847493');
  INSERT INTO authorities (employee_Id, authority) VALUES (123456, 'ADMIN');
  INSERT INTO authorities (employee_Id, authority) VALUES (123456, 'USER');