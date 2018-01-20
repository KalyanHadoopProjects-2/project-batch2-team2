create database kalyan;

use kalyan;

set hive.cli.print.current.db=true;
set hive.cli.print.header=true;
 
 
CREATE TABLE IF NOT EXISTS student( name string, id int , course string, year int ) 
STORED AS AVRO 
location '/task';
 
select * from student;
 
 
CREATE TABLE IF NOT EXISTS kalyan.student_avro
( name string, id int , course string, year int )
ROW FORMAT SERDE 'org.apache.hadoop.hive.serde2.avro.AvroSerDe'
STORED AS 
INPUTFORMAT 'org.apache.hadoop.hive.ql.io.avro.AvroContainerInputFormat'
OUTPUTFORMAT 'org.apache.hadoop.hive.ql.io.avro.AvroContainerOutputFormat';
 
 
 
INSERT OVERWRITE TABLE kalyan.student_avro SELECT * from student;
 
select * from kalyan.student_avro;
 

 
create table if not exists kalyan.student_avro_op as select * from kalyan.student_avro where id>2 and course='spark';
 
select * from kalyan.student_avro_op;
 
 