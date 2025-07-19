-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Dec 08, 2024 at 04:18 PM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `metrodb`
--

-- --------------------------------------------------------

--
-- Table structure for table `bill`
--

CREATE TABLE `bill` (
  `bill_id` int(11) NOT NULL,
  `branch_code` varchar(6) DEFAULT NULL,
  `date` date DEFAULT NULL,
  `cash` tinyint(1) DEFAULT NULL,
  `total_amount` double DEFAULT NULL,
  `tax` double DEFAULT NULL,
  `net_amount` double DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `bill`
--

INSERT INTO `bill` (`bill_id`, `branch_code`, `date`, `cash`, `total_amount`, `tax`, `net_amount`) VALUES
(2, '123LHE', '2024-12-08', 1, 1075, 537.5, 1612.5),
(3, '123LHE', '2024-12-08', 0, 1340, 670, 2010),
(4, '123LHE', '2024-12-08', 0, 980, 490, 1470);

-- --------------------------------------------------------

--
-- Table structure for table `bill_product`
--

CREATE TABLE `bill_product` (
  `bill_id` int(11) NOT NULL,
  `product_id` int(11) NOT NULL,
  `quantity` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `bill_product`
--

INSERT INTO `bill_product` (`bill_id`, `product_id`, `quantity`) VALUES
(2, 2, 2),
(2, 3, 1),
(2, 4, 1),
(2, 6, 1),
(2, 7, 1),
(3, 2, 1),
(3, 3, 2),
(3, 4, 2),
(4, 2, 2),
(4, 3, 2),
(4, 4, 1);

-- --------------------------------------------------------

--
-- Table structure for table `branch`
--

CREATE TABLE `branch` (
  `branch_code` varchar(6) NOT NULL,
  `city` varchar(55) DEFAULT NULL,
  `address` varchar(100) DEFAULT NULL,
  `phone` varchar(11) DEFAULT NULL,
  `n_employees` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `branch`
--

INSERT INTO `branch` (`branch_code`, `city`, `address`, `phone`, `n_employees`) VALUES
('123LHE', 'Lahore', 'temporary', '23948349532', 0);

-- --------------------------------------------------------

--
-- Table structure for table `employees`
--

CREATE TABLE `employees` (
  `emp_no` int(11) NOT NULL,
  `name` varchar(55) NOT NULL,
  `email` varchar(55) NOT NULL,
  `password` varchar(55) NOT NULL,
  `branch_code` varchar(6) DEFAULT NULL,
  `emp_type` varchar(1) NOT NULL,
  `salary` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `employees`
--

INSERT INTO `employees` (`emp_no`, `name`, `email`, `password`, `branch_code`, `emp_type`, `salary`) VALUES
(2, 'Laiba', 'laiba@gmail.com', '1234567', '123LHE', 'C', 50000);

-- --------------------------------------------------------

--
-- Table structure for table `product`
--

CREATE TABLE `product` (
  `product_id` int(11) NOT NULL,
  `vendor_id` int(11) DEFAULT NULL,
  `name` varchar(100) DEFAULT NULL,
  `category` varchar(100) DEFAULT NULL,
  `original_price` double DEFAULT NULL,
  `sale_price` double DEFAULT NULL,
  `price_by_carton` double DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `product`
--

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

-- --------------------------------------------------------

--
-- Table structure for table `product_branch`
--

CREATE TABLE `product_branch` (
  `product_id` int(11) NOT NULL,
  `branch_code` varchar(50) NOT NULL,
  `quantity` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `product_branch`
--

INSERT INTO `product_branch` (`product_id`, `branch_code`, `quantity`) VALUES
(1, '123LHE', 100),
(2, '123LHE', 65),
(3, '123LHE', 95),
(4, '123LHE', 46),
(5, '123LHE', 100),
(6, '123LHE', 99),
(7, '123LHE', 99),
(8, '123LHE', 200),
(9, '123LHE', 50);

-- --------------------------------------------------------

--
-- Table structure for table `report`
--

CREATE TABLE `report` (
  `reportid` int(11) NOT NULL,
  `branchCode` varchar(50) DEFAULT NULL,
  `rangeTag` varchar(50) DEFAULT NULL,
  `startDate` date DEFAULT NULL,
  `endDate` date DEFAULT NULL,
  `sales` double DEFAULT NULL,
  `remainingStock` int(11) DEFAULT NULL,
  `profit` double DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `super_admin`
--

CREATE TABLE `super_admin` (
  `username` varchar(55) NOT NULL,
  `password` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `super_admin`
--

INSERT INTO `super_admin` (`username`, `password`) VALUES
('admin-user', '245362');

-- --------------------------------------------------------

--
-- Table structure for table `vendor`
--

CREATE TABLE `vendor` (
  `vendor_id` int(11) NOT NULL,
  `name` varchar(100) DEFAULT NULL,
  `cnic` char(13) DEFAULT NULL,
  `phone_number` char(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `vendor`
--

INSERT INTO `vendor` (`vendor_id`, `name`, `cnic`, `phone_number`) VALUES
(1, 'Traders ABC', '2314563241456', '27654342312'),
(2, 'XYZ Traders', '4857384783421', '34562345678');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `bill`
--
ALTER TABLE `bill`
  ADD PRIMARY KEY (`bill_id`),
  ADD KEY `fk_bill_branch` (`branch_code`);

--
-- Indexes for table `bill_product`
--
ALTER TABLE `bill_product`
  ADD PRIMARY KEY (`bill_id`,`product_id`),
  ADD KEY `product_id` (`product_id`);

--
-- Indexes for table `branch`
--
ALTER TABLE `branch`
  ADD PRIMARY KEY (`branch_code`);

--
-- Indexes for table `employees`
--
ALTER TABLE `employees`
  ADD PRIMARY KEY (`emp_no`),
  ADD KEY `branch_code` (`branch_code`);

--
-- Indexes for table `product`
--
ALTER TABLE `product`
  ADD PRIMARY KEY (`product_id`),
  ADD KEY `vendor_id` (`vendor_id`);

--
-- Indexes for table `product_branch`
--
ALTER TABLE `product_branch`
  ADD PRIMARY KEY (`product_id`,`branch_code`),
  ADD KEY `branch_code` (`branch_code`);

--
-- Indexes for table `report`
--
ALTER TABLE `report`
  ADD PRIMARY KEY (`reportid`),
  ADD KEY `branchCode` (`branchCode`);

--
-- Indexes for table `super_admin`
--
ALTER TABLE `super_admin`
  ADD PRIMARY KEY (`username`);

--
-- Indexes for table `vendor`
--
ALTER TABLE `vendor`
  ADD PRIMARY KEY (`vendor_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `bill`
--
ALTER TABLE `bill`
  MODIFY `bill_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `employees`
--
ALTER TABLE `employees`
  MODIFY `emp_no` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `product`
--
ALTER TABLE `product`
  MODIFY `product_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT for table `report`
--
ALTER TABLE `report`
  MODIFY `reportid` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `vendor`
--
ALTER TABLE `vendor`
  MODIFY `vendor_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `bill`
--
ALTER TABLE `bill`
  ADD CONSTRAINT `fk_bill_branch` FOREIGN KEY (`branch_code`) REFERENCES `branch` (`branch_code`) ON DELETE SET NULL ON UPDATE CASCADE;

--
-- Constraints for table `bill_product`
--
ALTER TABLE `bill_product`
  ADD CONSTRAINT `bill_product_ibfk_1` FOREIGN KEY (`bill_id`) REFERENCES `bill` (`bill_id`) ON DELETE CASCADE,
  ADD CONSTRAINT `bill_product_ibfk_2` FOREIGN KEY (`product_id`) REFERENCES `product` (`product_id`) ON DELETE CASCADE;

--
-- Constraints for table `employees`
--
ALTER TABLE `employees`
  ADD CONSTRAINT `employees_ibfk_1` FOREIGN KEY (`branch_code`) REFERENCES `branch` (`branch_code`) ON DELETE SET NULL ON UPDATE CASCADE;

--
-- Constraints for table `product`
--
ALTER TABLE `product`
  ADD CONSTRAINT `product_ibfk_1` FOREIGN KEY (`vendor_id`) REFERENCES `vendor` (`vendor_id`) ON DELETE SET NULL;

--
-- Constraints for table `product_branch`
--
ALTER TABLE `product_branch`
  ADD CONSTRAINT `product_branch_ibfk_1` FOREIGN KEY (`product_id`) REFERENCES `product` (`product_id`),
  ADD CONSTRAINT `product_branch_ibfk_2` FOREIGN KEY (`branch_code`) REFERENCES `branch` (`branch_code`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
