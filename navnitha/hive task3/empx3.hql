add jar /home/orienit/Downloads/hivexmlserde-1.0.5.3.jar;

drop table emp3_xml;

CREATE EXTERNAL TABLE emp3_xml(empid int,name string,salary int,dept string,details map<string,string>)
ROW FORMAT SERDE 'com.ibm.spss.hive.serde2.xml.XmlSerDe'
WITH SERDEPROPERTIES (
"column.xpath.empid"="/employee/empid/text()",
"column.xpath.name"="/employee/name/text()",
"column.xpath.salary"="/employee/salary/text()",
"column.xpath.dept"="/employee/dept/text()",
"column.xpath.details"="/employee/details/*"
)

STORED AS
INPUTFORMAT 'com.ibm.spss.hive.serde2.xml.XmlInputFormat'
OUTPUTFORMAT 'org.apache.hadoop.hive.ql.io.IgnoreKeyTextOutputFormat'

LOCATION '/home/orienit/workspace/hive.input/employee3.xml'

TBLPROPERTIES (
    "xmlinput.start"="<employee>",
    "xmlinput.end"="</employee>"
); 
load data local inpath '/home/orienit/workspace/hive.input/employee3.xml' overwrite into table emp3_xml;


select * from emp3_xml;

drop table emp3_op;

create table emp3_op(empid int,details string)
row format delimited fields terminated by '\t';

insert into table emp3_op select empid,details["adress"] from emp3_xml where empid>2 and details["adress"]=hyderabad';

select * from emp3_op;



