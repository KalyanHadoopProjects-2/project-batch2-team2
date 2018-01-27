add jar /home/orienit/Downloads/hivexmlserde-1.0.5.3.jar;


CREATE EXTERNAL TABLE emp2_xml(empid int,name string,salary int,dept string)
ROW FORMAT SERDE 'com.ibm.spss.hive.serde2.xml.XmlSerDe'
WITH SERDEPROPERTIES (
"column.xpath.empid"="/employee/empid/text()",
"column.xpath.name"="/employee/name/text()",
"column.xpath.salary"="/employee/salary/text()",
"column.xpath.dept"="/employee/dept/text()"
)

STORED AS
INPUTFORMAT 'com.ibm.spss.hive.serde2.xml.XmlInputFormat'
OUTPUTFORMAT 'org.apache.hadoop.hive.ql.io.IgnoreKeyTextOutputFormat'

LOCATION '/home/orienit/workspace/hive.input/employee2.xml'

TBLPROPERTIES (
    "xmlinput.start"="<employee>",
    "xmlinput.end"="</employee>"
); 

load data local inpath '/home/orienit/workspace/hive.input/employee2.xml' overwrite into table emp2_xml;


select * from emp2_xml;
create table employee2_op(name string,dept string)
row format delimited fields terminated by '\t';

insert into table employee2_op 
select name , dept from emp2_xml where empid>2 and dept='dev';

select * from employee2_op;


