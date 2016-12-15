CREATE TABLE cliente (
	codigo SERIAL PRIMARY KEY,
	nome VARCHAR(100) NOT NULL,
	tipo_pessoa VARCHAR(15) NOT NULL,
	cpf_cnpj VARCHAR(30),
	telefone VARCHAR(20),
	email VARCHAR(60) NOT NULL,
	logradouro VARCHAR(60),
	numero VARCHAR(15),
	complemento VARCHAR(30),
	cep VARCHAR(15),
	codigo_cidade BIGINT
);

ALTER TABLE cliente ADD CONSTRAINT cliente_cidade_fk
	FOREIGN KEY(codigo_cidade) REFERENCES cidade(codigo);