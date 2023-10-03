CREATE TABLE IF NOT EXISTS `%prefix%all_players%suffix%` (
`id` INT NOT NULL AUTO_INCREMENT ,
`player_uuid` CHAR(36) NOT NULL ,
`player_name` VARCHAR(32) NOT NULL ,
`first_join` INT NOT NULL DEFAULT '0' ,
`last_join` INT NOT NULL DEFAULT '0' ,
`join_count` INT NOT NULL DEFAULT '0' ,
`join_time` INT NOT NULL DEFAULT '0' ,
PRIMARY KEY (`id`));