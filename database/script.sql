CREATE DATABASE invest_robot_db;
USE invest_robot_db;

CREATE TABLE tb_usuario (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE tb_carteira (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    descricao VARCHAR(100) NOT NULL,
    valor_total DECIMAL(15, 2) DEFAULT 0.00,
    id_usuario BIGINT NOT NULL,
    FOREIGN KEY (id_usuario) REFERENCES tb_usuario(id)
);

CREATE TABLE tb_ativo (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    ticker VARCHAR(10) NOT NULL UNIQUE, -- PETR4, AAPL34
    cotacao DECIMAL(15, 2) NOT NULL, 
    data_atualizacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE tb_carteira_item (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    
    -- Relacionamentos
    id_carteira BIGINT NOT NULL,
    id_ativo BIGINT NOT NULL,
    
    quantidade INT NOT NULL DEFAULT 0, 
    percentual_alvo DECIMAL(15, 2) NOT NULL, 

    UNIQUE (id_carteira, id_ativo),
    
    FOREIGN KEY (id_carteira) REFERENCES tb_carteira(id),
    FOREIGN KEY (id_ativo) REFERENCES tb_ativo(id)
);
