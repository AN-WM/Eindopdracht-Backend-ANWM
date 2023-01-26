  INSERT INTO users (employeeId, firstName, lastName, password, dob, enabled, apikey) VALUES (123456, 'Tester', 'Test', '$2y$10$5AiMvCASciUQktAUZl/RfelglEU35n3pnVCFgmlkMybDfENSbXpXO', DATE '1900-05-01', true, '7847493');
  INSERT INTO authorities (username, authority) VALUES ('henk', 'ADMIN');
  INSERT INTO authorities (username, authority) VALUES ('henk', 'USER');