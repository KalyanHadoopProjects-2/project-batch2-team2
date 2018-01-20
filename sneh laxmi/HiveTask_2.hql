create table raw( line string);

load data local inpath '${env:HOME}/work/hive_inputs/employee2.json' into table raw;

select * from raw;


create table raw1( json_body string);


INSERT OVERWRITE TABLE raw1
SELECT CONCAT_WS(' ',COLLECT_LIST(line)) 
      FROM (SELECT INPUT__FILE__NAME,BLOCK__OFFSET__INSIDE__FILE, line FROM raw DISTRIBUTE BY INPUT__FILE__NAME SORT BY BLOCK__OFFSET__INSIDE__FILE) x
      GROUP BY INPUT__FILE__NAME;
	  

select * from raw1;

create table jraw2( empid int, name string, salary int, dept string);

insert into table jraw2
select x.* from raw1 
lateral view json_tuple(json_body, 'empid', 'name', 'salary' , 'dept') 
x as empid,name,salary,dept,details;

select * from jraw2;
 






