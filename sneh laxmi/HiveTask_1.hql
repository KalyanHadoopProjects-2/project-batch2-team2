create database kalyan;

use kalyan;

set hive.cli.print.current.db=true;

set hive.cli.print.header=true;




create table employeejson(json string);


load data local inpath '${env:HOME}/work/hive_inputs/employee1.json' into table employeejson;

select * from employeejson;


create external table employee1_json( empid int, name string, salary int , dept string)
row format delimited
fields terminated by '\t';


insert overwrite table employee1_json
select x.* from employeejson
lateral view json_tuple(json, 'empid' , 'name' , 'salary' , 'dept' )
x as empid, name,salary,dept;

select * from employee1_json;

create external table employee1_op( name string , dept string)
row format delimited
fields terminated by '\t';

insert overwrite table employee1_op
select name , dept from employee1_json where empid>2 and dept='dev';

select * from employee1_op;






create table employeexml( line string);

load data local inpath  '/home/orienit/work/hive_inputs/employee1.xml' into table employeexml;

select * from employeexml;

create table employee1_xml( empid int, name string, salary int, dept string)
row format delimited 
fields terminated by '\t';

insert into table employee1_xml
select xpath_int(line,'employee/empid'),xpath_string(line,'employee/name'),xpath_int(line,'employee/salary'),xpath_string(line,'employee/dept')from employeexml;


select * from employee1_xml;;


insert into table employee1_op 
select name , dept from employee1_xml where empid>2 and dept='dev';

select * from employee1_op;
	   
