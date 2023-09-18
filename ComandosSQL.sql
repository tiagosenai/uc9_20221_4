create database uc9_20221_4;

create table usuario(
 id int not null auto_increment primary key,
 usuario varchar(100),
 senha varchar(255)
 );
 
create table cliente(
  matricula int not null auto_increment primary key,
  nome varchar(200),
  endereco varchar(200),
  modalidade varchar(200)
);