USE employee_management;

 TRUNCATE TABLE insurance_scheme;

INSERT INTO insurance_scheme (id, name, valid_from_date, valid_to_date, scheme_amount, scheme_type, share, commission, brokerage) VALUES
(1,'Full Family Coverage','2024-07-20', '2034-01-08', 50000, 'ANNUAL', 10.00, 10.00, 10.00),
(2,'Happiness Unlimited','2024-07-19', '2034-01-08', 60000, 'MONTHLY', 10.00, 10.00, 10.00),
(3,'Freedom Package','2024-07-08', '2034-01-08', 70000, 'QUARTERLY', 10.00, 10.00, 10.00),
(4,'Independence Package','2024-01-20', '2034-01-08', 70000, 'QUARTERLY', 10.00, 10.00, 10.00),
(5,'Liberty Package','2034-01-08', '2034-01-08', 70000, 'QUARTERLY', 10.00, 10.00, 10.00),
(6,'Freedom Ultimate','2024-01-08', '1989-01-08', 70000, 'QUARTERLY', 10.00, 10.00, 10.00)
;