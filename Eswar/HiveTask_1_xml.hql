CREATE TABLE employee1_xml(empid int, name string, salary float, dept string )
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
TBLPROPERTIES (
"xmlinput.start"="<employee>",
"xmlinput.end"="</employee>"
);

add jar /home/orienit/other/hivexmlserde-1.0.5.3.jar;
LOAD DATA LOCAL INPATH '/home/orienit/other/employee1.xml'OVERWRITE INTO TABLE employee1_xml;

select *  from employee1_xml where empid>2 AND dept='dev';