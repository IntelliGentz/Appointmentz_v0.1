/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  ndine
 * Created: 13-Nov-2016
 */
drop database db_bro;
create database db_bro;
use db_bro;

CREATE TABLE `hospital` (
  `id` int NOT NULL UNIQUE,
  `hospital_id` varchar(255)  NOT NULL,
  `hospital_name` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  PRIMARY KEY (`hospital_id`)
);

CREATE TABLE `room` (
  `room_id` int NOT NULL UNIQUE AUTO_INCREMENT,
  `room_number` varchar(255) NOT NULL,
  `hospital_id` varchar(255) NOT NULL,
  PRIMARY KEY (`room_id`),
  FOREIGN KEY(`hospital_id`) REFERENCES hospital (`hospital_id`) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE `rpi` (
  `room_id` int NOT NULL UNIQUE,
  `auth` varchar(255) NOT NULL,
  `serial` varchar(255) NOT NULL,
  `last_number` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`serial`),
  FOREIGN KEY(`room_id`) REFERENCES room (`room_id`) ON DELETE CASCADE ON UPDATE CASCADE
);

/*CREATE TABLE `rpi` (
  `room_id` int(10) unsigned NOT NULL,
  `auth` varchar(255) NOT NULL,
  `serial` varchar(255) NOT NULL,
  PRIMARY KEY (`serial`),
  FOREIGN KEY(`room_id`) REFERENCES room (`room_id`) ON DELETE CASCADE
);

CREATE TABLE `doctor` (
  `doctor_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `hospital_id` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`doctor_id`),
  FOREIGN KEY(`hospital_id`) REFERENCES hospital (`hospital_id`) ON DELETE CASCADE
);

CREATE TABLE `button` (
  `doctor_id` int(10) unsigned NOT NULL,
  `auth` varchar(255) NOT NULL,
  `serial` varchar(255) NOT NULL,
  PRIMARY KEY (`serial`),
  FOREIGN KEY(`doctor_id`) REFERENCES doctor (`doctor_id`) ON DELETE CASCADE
);

CREATE TABLE `session` (
  `session_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `doctor_id` int(10) unsigned NOT NULL,
  `room_id` int(10) unsigned NOT NULL,
  `current_no` int(10) unsigned NOT NULL DEFAULT "0",
  `date` date NOT NULL,
  `start_time` time NOT NULL,
  PRIMARY KEY (`session_id`),
  FOREIGN KEY(`doctor_id`) REFERENCES doctor (`doctor_id`) ON DELETE CASCADE,
  FOREIGN KEY(`room_id`) REFERENCES room (`room_id`) ON DELETE CASCADE
);
*/
