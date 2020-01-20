delete from operation;
delete from account;

insert into account(id, balance) values
(1, 100.01);

insert into operation(id, amount, type_operation, account_id) values
(1, 100.01, 'ADD', 1);

alter sequence hibernate_sequence restart with 1;