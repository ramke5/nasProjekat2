use nalaz;
INSERT INTO `nalaz`.`role` (`id`, `description`, `name`, `systemRole`) VALUES ('1', '0', 'ROLE_SUPER_ADMIN', '1');
INSERT INTO `nalaz`.`role` (`id`, `description`, `name`, `systemRole`) VALUES ('2', '0', 'ROLE_ADMIN_KOMPANIJE', '1');
INSERT INTO `nalaz`.`role` (`id`, `description`, `name`, `systemRole`) VALUES ('3', '0', 'ROLE_KORISNIK_KOMPANIJE', '1');
INSERT INTO `nalaz`.`role` (`id`, `description`, `name`, `systemRole`) VALUES ('4', '0', 'ROLE_KORISNIK', '1');

INSERT INTO `nalaz`.`usertype` (`id`, `name`, `mainRole_id`) VALUES ('1', 'Super admin', '1');
INSERT INTO `nalaz`.`usertype` (`id`, `name`, `mainRole_id`) VALUES ('2', 'Admin kompanije', '2');
INSERT INTO `nalaz`.`usertype` (`id`, `name`, `mainRole_id`) VALUES ('3', 'Korisnik kompanije', '3');
INSERT INTO `nalaz`.`usertype` (`id`, `name`, `mainRole_id`) VALUES ('4', 'Korisnik', '4');
