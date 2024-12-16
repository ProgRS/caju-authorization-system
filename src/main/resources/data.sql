-- Inserir Contas
INSERT INTO account (id, foodBalance, mealBalance, cashBalance) VALUES ('123', 100.00, 50.00, 20.00);
INSERT INTO account (id, foodBalance, mealBalance, cashBalance) VALUES ('456', 200.00, 100.00, 30.00);
INSERT INTO account (id, foodBalance, mealBalance, cashBalance) VALUES ('789', 300.00, 150.00, 40.00);

-- Inserir Mapeamentos de Comerciantes
INSERT INTO merchant_mapping (merchantName, mcc) VALUES ('UBER EATS                   SAO PAULO BR', '5812');
INSERT INTO merchant_mapping (merchantName, mcc) VALUES ('UBER TRIP                   SAO PAULO BR', '5811');
INSERT INTO merchant_mapping (merchantName, mcc) VALUES ('PAG*JoseDaSilva          RIO DE JANEI BR', '5411');
INSERT INTO merchant_mapping (merchantName, mcc) VALUES ('PICPAY*BILHETEUNICO           GOIANIA BR', '5811');
