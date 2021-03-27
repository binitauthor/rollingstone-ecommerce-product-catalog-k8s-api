CREATE TABLE `rollingstone_category` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `category_description` varchar(255) NOT NULL,
  `category_name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;



CREATE TABLE `rollingstone_product` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `candisplay` bit(1) NOT NULL,
  `isautomotive` bit(1) NOT NULL,
  `isdeleted` bit(1) NOT NULL,
  `isinternational` bit(1) NOT NULL,
  `long_description` varchar(255) NOT NULL,
  `pcode` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `short_description` varchar(255) NOT NULL,
  `category_id` bigint(20) DEFAULT NULL,
  `parent_category_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK1273qdxsfg98re216bbtj99t7` (`category_id`),
  KEY `FKltvigl6yjjn6lpq6sywmk835c` (`parent_category_id`),
  CONSTRAINT `FK1273qdxsfg98re216bbtj99t7` FOREIGN KEY (`category_id`) REFERENCES `rollingstone_category` (`id`),
  CONSTRAINT `FKltvigl6yjjn6lpq6sywmk835c` FOREIGN KEY (`parent_category_id`) REFERENCES `rollingstone_category` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;