hdfs dfs -mkdir -p pig;
hdfs dfs -put /home/orienit/other/employee1.json /user/orienit/pig/employee1

A = LOAD '/user/orienit/pig/employee1.json' using JsonLoader ('empid:int, name:chararray, salary:int, dept:chararray');
dump A;

B = filter A BY empid > 2 AND dept == 'dev';

dump B;



=========================================================================================
1. Load XML to HDFS using:
> hadoop fs -put /home/orienit/other/employee1.xml /user/orienit/pig/employee1_xml
2. Register the piggybank.jar by:
register /usr/lib/pig/piggybank.jar

grunt> A = load '/user/orienit/pig/employee1_xml' USING org.apache.pig.piggybank.storage.XMLLoader('employee') as(doc:chararray);

grunt> B = foreach A GENERATE FLATTEN(REGEX_EXTRACT_ALL(doc,'<employee>\\s*<empid>(.*)</empid>\\s*<name>(.*)</name>\\s*<salary>(.*)</salary>\\s*<dept>(.*)</dept>\\s*</employee>')) AS (empid:int, name:chararray, salary:float, dept:chararray);

grunt> dump B;