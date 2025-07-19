-- Data insertion for `vendor` table
INSERT INTO `vendor` (`vendor_id`, `name`, `cnic`, `phone_number`) VALUES
(1, 'Traders ABC', '2314563241456', '27654342312'),
(2, 'XYZ Traders', '4857384783421', '34562345678');

-- Data insertion for `product` table
INSERT INTO `product` (`product_id`, `vendor_id`, `name`, `category`, `original_price`, `sale_price`, `price_by_carton`) VALUES
(1, 1, 'Herbal Essences Shampoo', 'Personal Care', 800, 1000, 6000),
(2, 1, 'Safeguard Pure White', 'Personal Care', 20, 40, 200),
(3, 1, 'Nerds', 'Food and Beverages', 200, 250, 1100),
(4, 1, 'Haribo Goldbears', 'Food and Beverages', 350, 400, 2400),
(5, 2, 'Bananas', 'Food and Beverages', 100, 110, 2000),
(6, 1, 'Lotte Choco Pie', 'Food and Beverages', 40, 45, 200),
(7, 2, 'Apples', 'Food and Beverages', 290, 300, 3000),
(8, 1, 'Colgate toothpaste', 'Personal Care', 38, 40, 1000),
(9, 1, 'Adam\'s Mozarella Cheese', 'Food and Beverages', 450, 480, 2000);

-- Data insertion for `product_branch` table
INSERT INTO `product_branch` (`product_id`, `branch_code`, `quantity`) VALUES
(1, '001LHR', 100),
(2, '001LHR', 70),
(3, '001LHR', 100),
(4, '001LHR', 50),
(5, '001LHR', 100),
(6, '001LHR', 100),
(7, '001LHR', 100),
(8, '001LHR', 200),
(9, '001LHR', 50);

INSERT INTO `employees` (`name`, `email`, `password`, `branch_code`, `emp_type`, `salary`) VALUES
('Laiba', 'laiba@gmail.com', '123456', '001LHR', 'C', 50000);
