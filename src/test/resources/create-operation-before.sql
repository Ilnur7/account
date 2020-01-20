delete from operation;

insert into operation(id, amount, type_operation, account_id) values
(1, 100.01, 'ADD', 1),
(2, 200.01, 'ADD', 2);

alter sequence hibernate_sequence restart with 1;
