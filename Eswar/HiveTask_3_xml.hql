add jar /home/orienit/other/hivexmlserde-1.0.5.3.jar;


CREATE TABLE employee3_xml(empid int, name string, salary float, dept string,address string,pincode bigint,address1 string,pincode1 bigint )
ROW FORMAT SERDE 'com.ibm.spss.hive.serde2.xml.XmlSerDe'
WITH SERDEPROPERTIES (
"column.xpath.empid"="/employee/empid/text()",
"column.xpath.name"="/employee/name/text()",
"column.xpath.salary"="/employee/salary/text()",
"column.xpath.dept"="/employee/dept/text()",
"column.xpath.address"="/employee/details[1]/address[1]/text()",
"column.xpath.pincode"="/employee/details[1]/pincode[1]/text()",
"column.xpath.address1"="employee/details[2]/address[1]/text()",
"column.xpath.pincode1"="employee/details[2]/pincode[1]/text()"
)
STORED AS
INPUTFORMAT 'com.ibm.spss.hive.serde2.xml.XmlInputFormat'
OUTPUTFORMAT 'org.apache.hadoop.hive.ql.io.IgnoreKeyTextOutputFormat'
TBLPROPERTIES (
"xmlinput.start"="<employee>",
"xmlinput.end"="</employee>"
);


LOAD DATA LOCAL INPATH '/home/orienit/other/employee3.xml'OVERWRITE INTO TABLE employee3_xml;

select *  from employee3_xml ;