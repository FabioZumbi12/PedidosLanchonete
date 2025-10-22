-- Criação da tabela FOOD
CREATE TABLE FOOD (
    ID IDENTITY PRIMARY KEY,
    NAME VARCHAR(100) NOT NULL,
    PRICE DECIMAL(10,2) NOT NULL,
    TYPE VARCHAR(50) NOT NULL,
    INGREDIENTS VARCHAR(1000),
    ADDITIONAL_INGREDIENTS VARCHAR(1000)
);

-- Inserção dos dados
INSERT INTO FOOD (NAME, PRICE, TYPE, INGREDIENTS) VALUES ('X-Tudo', 30.0, 'LANCHE', 'Pão,Hamburguer,Queijo,Bacon,Ovo,Alface,Tomate,Maionese');
INSERT INTO FOOD (NAME, PRICE, TYPE, INGREDIENTS) VALUES ('X-Salada', 25.0, 'LANCHE', 'Pão,Hamburguer,Queijo,Alface,Tomate,Maionese');
INSERT INTO FOOD (NAME, PRICE, TYPE, INGREDIENTS) VALUES ('X-Bacon', 28.0, 'LANCHE', 'Pão,Hamburguer,Queijo,Bacon,Maionese');
INSERT INTO FOOD (NAME, PRICE, TYPE, INGREDIENTS) VALUES ('X-Egg', 27.5, 'LANCHE', 'Pão,Hamburguer,Queijo,Ovo,Maionese');
INSERT INTO FOOD (NAME, PRICE, TYPE, INGREDIENTS) VALUES ('X-Calabresa', 26.0, 'LANCHE', 'Pão,Calabresa,Queijo,Cebola,Maionese');
INSERT INTO FOOD (NAME, PRICE, TYPE, INGREDIENTS) VALUES ('X-Frango', 29.0, 'LANCHE', 'Pão,Frango,Queijo,Alface,Tomate,Maionese');
INSERT INTO FOOD (NAME, PRICE, TYPE, INGREDIENTS) VALUES ('X-Podrão', 35.0, 'LANCHE', 'Pão,Hamburguer,Bacon,Calabresa,Ovo,Queijo,Alface,Tomate');
INSERT INTO FOOD (NAME, PRICE, TYPE, INGREDIENTS) VALUES ('X-DaCasa', 32.0, 'LANCHE', 'Pão,Hamburguer,Queijo,Presunto,Alface,Tomate,Maionese');
INSERT INTO FOOD (NAME, PRICE, TYPE, INGREDIENTS) VALUES ('X-Fome', 34.0, 'LANCHE', 'Pão,Hamburguer,Bacon,Queijo,Ovo,Cebola,Maionese');
INSERT INTO FOOD (NAME, PRICE, TYPE, INGREDIENTS) VALUES ('X-Calota', 33.0, 'LANCHE', 'Pão,Hamburguer,Queijo,Bacon,Ovo,Alface,Tomate');
INSERT INTO FOOD (NAME, PRICE, TYPE, INGREDIENTS) VALUES ('Cheeseburger', 28.5, 'LANCHE', 'Pão,Hamburguer,Queijo,Picles,Mostarda,Catchup');
INSERT INTO FOOD (NAME, PRICE, TYPE, INGREDIENTS) VALUES ('Cachorro-Quente', 22.0, 'LANCHE', 'Pão,Salsicha,Cebola,Catchup,Mostarda,Maionese');
INSERT INTO FOOD (NAME, PRICE, TYPE, INGREDIENTS) VALUES ('Monte o Seu', 16.0, 'LANCHE', 'Pão,Hamburguer');
INSERT INTO FOOD (NAME, PRICE, TYPE, INGREDIENTS) VALUES ('Pastel de Pizza', 10.0, 'LANCHE', 'Massa de Pastel,Queijo,Presunto,Tomate');
INSERT INTO FOOD (NAME, PRICE, TYPE, INGREDIENTS) VALUES ('Pastel de Carne', 10.0, 'LANCHE', 'Massa de Pastel,Carne,Azeitona');
INSERT INTO FOOD (NAME, PRICE, TYPE, INGREDIENTS) VALUES ('Pastel de Vento', 8.0, 'LANCHE', 'Massa de Pastel');
INSERT INTO FOOD (NAME, PRICE, TYPE, INGREDIENTS) VALUES ('Batata Frita', 8.0, 'PORCAO', 'Batata-Palha,Sal');
INSERT INTO FOOD (NAME, PRICE, TYPE, INGREDIENTS) VALUES ('Porção de Calabresa', 15.0, 'PORCAO', 'Calabresa,Cebola');
INSERT INTO FOOD (NAME, PRICE, TYPE, INGREDIENTS) VALUES ('Iscas de Peixe', 20.0, 'PORCAO', 'Peixe,Farinha');
INSERT INTO FOOD (NAME, PRICE, TYPE, INGREDIENTS) VALUES ('Iscas de Frango', 18.0, 'PORCAO', 'Frango,Farinha');
INSERT INTO FOOD (NAME, PRICE, TYPE, INGREDIENTS) VALUES ('Refrigerante', 6.0, 'BEBIDA', 'Coca-Cola');
