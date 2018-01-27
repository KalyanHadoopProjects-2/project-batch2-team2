1. Load XML to HDFS using:
> hadoop fs -put /home/orienit/other/employee2.xml /user/orienit/pig/employee2?employee2_xml
2. Register the piggybank.jar by:
register /usr/lib/pig/piggybank.jar

DEFINE Xpath org.apache.pig.piggybank.evaluation.xml.XPath();
A = load '/user/orienit/pig/employee2/employee2_xml' USING org.apache.pig.piggybank.storage.XMLLoader('employee') as(doc:chararray);

B= foreach A GENERATE FLATTEN(REGEX_EXTRACT_ALL(doc,'(?s)<employee>.*?<empid>([^>]*?)</empid>.*?<name>([^>]*?)</name>.*?<salary>([^>]*?)</salary>.*?<dept>([^>]*?)</dept>.*?</employee>')) AS (empid:int, name:chararray, salary:float, dept:chararray);
C = filter B BY empid > 2 AND dept == 'dev';
dump B;