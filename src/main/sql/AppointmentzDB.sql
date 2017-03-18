/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  ndine
 * Created: 13-Nov-2016
 */
drop database appointmentz;
create database appointmentz;
use appointmentz;

CREATE TABLE `hospital` (
  `hospital_id` varchar(255)  NOT NULL,
  `hospital_name` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  PRIMARY KEY (`hospital_id`)
);

CREATE TABLE `room` (
  `room_number` varchar(255) NOT NULL,
  `room_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `hospital_id` varchar(255) NOT NULL,
  PRIMARY KEY (`room_id`),
  FOREIGN KEY(`hospital_id`) REFERENCES hospital (`hospital_id`) ON DELETE CASCADE
);

CREATE TABLE `rpi` (
  `room_id` int(10) unsigned NOT NULL,
  `auth` varchar(255) NOT NULL,
  `serial` varchar(255) NOT NULL,
  PRIMARY KEY (`serial`),
  FOREIGN KEY(`room_id`) REFERENCES room (`room_id`) ON DELETE CASCADE
);

CREATE TABLE `session` (
  `session_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `room_id` int(10) unsigned NOT NULL,
  `current_no` int(10) unsigned NOT NULL DEFAULT "0",
  `date` date NOT NULL,
  `start_time` time NOT NULL,
  PRIMARY KEY (`session_id`),
  FOREIGN KEY(`doctor_id`) REFERENCES doctor (`doctor_id`) ON DELETE CASCADE,
  FOREIGN KEY(`room_id`) REFERENCES room (`room_id`) ON DELETE CASCADE
);

INSERT INTO `appointmentz`.`hospital`
(`hospital_id`,
`hospital_name`,
`password`)
VALUES ('ni','Nine Wells Hospital','12');


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
