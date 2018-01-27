CREATE DATABASE kalyan;

create json table:::
--------------------
ADD jar /home/orienit/hive-json.jar;

add jar /home/orienit/other/hive-hcatalog-core-1.2.1.jar;
create database if not exists kalyan;
create table if not exists employee1_json(empid int,name string,salary int,dept string) row format serde 'org.apache.hive.hcatalog.data.JsonSerDe' stored as textfile;

LOAD DATA LOCAL INPATH '/home/orienit/other/employee1.json' INTO TABLE employee1_json;

 select * from employee1_json where empid>2 and dept='dev';