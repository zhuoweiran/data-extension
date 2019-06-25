-- -------------------------------------------------------------
-- TablePlus 2.1(204)
--
-- https://tableplus.com/
--
-- Database: data_extension
-- Generation Time: 2019-06-25 17:14:24.5190
-- -------------------------------------------------------------


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


CREATE TABLE `t_msg_job` (
  `id` varchar(255) NOT NULL,
  `last_success_time` datetime DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `status` bit(1) NOT NULL,
  `template` varchar(3000) DEFAULT NULL,
  `topic` varchar(255) DEFAULT NULL,
  `window` int(11) NOT NULL,
  `msg_type` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

CREATE TABLE `t_msg_rules` (
  `id` varchar(255) NOT NULL,
  `job_id` varchar(255) DEFAULT NULL,
  `rule_key` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `status` bit(1) NOT NULL,
  `rule_value` varchar(255) DEFAULT NULL,
  `value_type` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

INSERT INTO `t_msg_job` (`id`, `last_success_time`, `name`, `status`, `template`, `topic`, `window`, `msg_type`) VALUES ('2c900c186b88c2fe016b88d11a1c0000', '2019-06-24 07:13:01', 'commonpair', b'0', '{\"Header\":{\"DcdGuid\":\"${device.guid}\",\"DevGuid\":\"${device.guid}\",\"Sid\":\"xxxxx\",\"Timestamp\":\"${.now}\"},\"Data\":{\"timestamp\":\"${.now?string(\'yyyy-MM-dd HH:mm:ss\')}\",\"flow_id\":1133032190190584,\"event_type\":\"process\",\"serial_number\":1,\"src_ip\":\"${device.ip}\",\"src_port\":62428,\"dest_ip\":\"${device2.ip}\",\"dest_port\":80,\"proto\":\"TCP\",\"app_proto\":\"http\",\"flow\":{\"pkts_toserver\":7,\"pkts_toclient\":7,\"bytes_toserver\":957,\"bytes_toclient\":5426,\"start\":\"2019-04-29T14:44:07.074744+0800\",\"end\":\"2019-04-29T14:44:07.081644+0800\",\"age\":0,\"state\":\"established\",\"reason\":\"shutdown\",\"alerted\":false},\"tcp\":{\"tcp_flags\":\"1a\",\"tcp_flags_ts\":\"1a\",\"tcp_flags_tc\":\"1a\",\"syn\":true,\"psh\":true,\"ack\":true,\"state\":\"established\"}}}', 'test', '10', 'Json'),
('2c900c186b88c2fe016b88e5d8cd0003', '2019-06-24 04:56:26', 'SecurityPolicyNotSafe', b'0', '{\"Header\":{\"DcdGuid\":\"0000-a2a60498-07f9-4101-ae4b-24feb863413f\",\"DevGuid\":\"0000-a2a60498-06f9-4601-ae4b-263feb86413f\",\"Sid\":\"xxxxx\",\"Timestamp\":\"123213232111\"},\"Data\":{\"AppName\":\"\",\"EventType \":\"01\",\"FunClassTag\":\"Comm-ncpa\",\"DiscoverTime\":\"${.now?string(\'yyyy-MM-dd HH:mm:ss\')}\",\"Details\":{\"ClientIp\":\"10.50.33.33\",\"ClientMac\":\"00-50-56-C0-00-01\",\"ClientPort\":\"7799\",\"ServerIp\":\"10.50.33.122\",\"ServerMac\":\" 00-50-56-C0-00-A4\",\"ServerPort\":\"8001\"}}}', 'event', '1', 'Json'),
('2c900c2c6b88a699016b88a70a850000', '2019-06-24 04:07:32', 'test json', b'0', '{\"Header\":{\"DcdGuid\":\"${device.guid}\",\"DevGuid\":\"${device.guid}\",\"Sid\":\"xxxxx\",\"Timestamp\":\"${.now}\"},\"Data\":{\"AppName\":\"\",\"EventType \":\"01\",\"FunClassTag\":\"CommHPAttack\",\"DiscoverTime\":\"${.now?string(\'yyyy-MM-dd HH:mm:ss\')}\",\"Details\":{\"Type\":\"wordpot\",\"ClientIp\":\"${device.ip}\",\"ClientPort\":\"7799\",\"ServerIp\":\"${device2.ip}\",\"ServerPort\":\"8001\",\"Protocol\":\"DNS\",\"LogsDetails\":\"fyc 123456\"}}}', 'test', '1', 'Json'),
('2c900c2c6b8cac40016b8d516eb60000', '2019-06-25 01:27:33', 'test json object', b'0', 'test-json-object-start,name:${obj.name},age:${obj.age-1},test-json-object-end', 'test', '1', 'EText'),
('402880e66b8d6759016b8d6809670000', '2019-06-25 01:52:14', 'test', b'0', 'test[${array}]test', 'test', '1', 'EText'),
('4028a1816b73ed52016b73ee74280000', '2019-06-23 20:35:54', 'test', b'0', '${guid}^${now?string(\'yyyy-MM-dd HH:mm:ss.SSS\')}^${num*100}', 'test', '100', 'EText');

INSERT INTO `t_msg_rules` (`id`, `job_id`, `rule_key`, `name`, `status`, `rule_value`, `value_type`) VALUES ('2c900c186b88c2fe016b88d20da70001', '2c900c186b88c2fe016b88d11a1c0000', 'device', 'device', b'1', '1212', 'Table_Device'),
('2c900c2c6b88a699016b88a7b9f30001', '2c900c2c6b88a699016b88a70a850000', 'device', 'device', b'1', 'device', 'Table_Device'),
('2c900c2c6b88a699016b88a8194a0002', '2c900c2c6b88a699016b88a70a850000', 'device2', 'device2', b'1', 'device', 'Table_TmpDevice'),
('2c900c2c6b88de64016b88e22c410000', '2c900c186b88c2fe016b88d11a1c0000', 'device2', 'device2', b'1', '', 'Table_TmpDevice'),
('2c900c2c6b8cac40016b8d526bde0001', '2c900c2c6b8cac40016b8d516eb60000', 'obj', 'json object', b'1', '{\"name\":\"alex\",\"age\":26}', 'Object'),
('402880e66b87cec3016b87d028300001', '4028a1816b73ed52016b73ee74280000', 'num', 'num', b'1', '10', 'Int'),
('402880e66b8d6759016b8d69c7330001', '402880e66b8d6759016b8d6809670000', 'array', 'test', b'1', '[1,2,3,4,5,6,7]', 'Array'),
('4028a1816b73ed52016b73ee745c0001', '4028a1816b73ed52016b73ee74280000', 'guid', 'guid1', b'1', 'guidxxxxxxxxxxxxxx', 'String'),
('4028a1816b73ed52016b73ee745f0002', '4028a1816b73ed52016b73ee74280000', 'now', 'now', b'1', 'Today', 'Date'),
('4028a1816b77d228016b77d424510001', '4028a1816b77d228016b77d424260000', 'guid', 'guid', b'1', 'guidxxxxxxxxxxxxxx', 'String'),
('4028a1816b77d228016b77d424550002', '4028a1816b77d228016b77d424260000', 'now', 'now', b'1', 'Today', 'Date'),
('4028a1816b77d228016b77d424580003', '4028a1816b77d228016b77d424260000', 'num', 'num', b'1', '30', 'Int');




/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;