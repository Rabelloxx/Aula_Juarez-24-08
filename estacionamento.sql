-- Tutorial Parte I - Passos 3, 4 e 5
create database if not exists estacionamento;
use estacionamento;
create table if not exists carro(
  placa char(7) not null primary key,
  cor varchar(20),
  descricao varchar(100)
);
