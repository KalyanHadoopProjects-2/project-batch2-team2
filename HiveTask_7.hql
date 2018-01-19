create database kalyan;

use kalyan;

set hive.cli.print.current.db=true;

set hive.cli.print.header=true;

create external table if not exists RiskFactor(YearStart int,YearEnd int,LocationAbbr string,LocationDesc string,Datasource string,Class string,Topic string,Question string,
Data_Value_Unit string,Data_Value_Type string,Data_Value double,Data_Value_Alt double,Data_Value_Footnote_Symbol string,
Data_Value_Footnote string,Low_Confidence_Limit double,High_Confidence_Limit double,Sample_Size int,Total string,Age int,
Education string,Gender string,Income string,RaceEthnicity string,GeoLocation double,ClassID string,TopicID string,QuestionID string,DataValueTypeID string,
LocationID int,StratificationCategory1 string,Stratification1 string,StratificationCategoryId1 string,StratificationID1 string)
row format delimited 
fields terminated by ','
stored as textfile
location '/csv';


select * from RiskFactor;

create external table if not exists RiskFactorPartition(YearStart int,LocationAbbr string,Datasource string,Class string,Topic string,Question string,
Data_Value_Unit string,Data_Value_Type string,Data_Value double,Data_Value_Alt double,Data_Value_Footnote_Symbol string,
Data_Value_Footnote string,Low_Confidence_Limit double,High_Confidence_Limit double,Sample_Size int,Total string,Age int,
Education string,Gender string,Income string,RaceEthnicity string,ClassID string,TopicID string,QuestionID string,DataValueTypeID string,
LocationID int,StratificationCategory1 string,Stratification1 string,StratificationCategoryId1 string,StratificationID1 string)
partitioned by (YearEnd int,LocationDesc string,GeoLocation double)
row format delimited
fields terminated by ','
stored as textfile;


set hive.exec.dynamic.partition.mode=nonstrict;
SET hive.exec.max.dynamic.partitions=100000;
SET hive.exec.max.dynamic.partitions.pernode=100000;


INSERT OVERWRITE TABLE RiskFactorPartition PARTITION(YearEnd ,LocationDesc ,GeoLocation) select * from RiskFactor;


select * from RiskFactorPartition;



CREATE TABLE IF NOT EXISTS RiskFactorFilter(YearStart int,YearEnd int,LocationAbbr string,LocationDesc string,Datasource string,Class string,Topic string,Question string,
Data_Value_Unit string,Data_Value_Type string,Data_Value double,Data_Value_Alt double,Data_Value_Footnote_Symbol string,
Data_Value_Footnote string,Low_Confidence_Limit double,High_Confidence_Limit double,Sample_Size int,Total string,Age int,
Education string,Gender string,Income string,RaceEthnicity string,GeoLocation double,ClassID string,TopicID string,QuestionID string,DataValueTypeID string,
LocationID int,StratificationCategory1 string,Stratification1 string,StratificationCategoryId1 string,StratificationID1 string)
row format delimited
fields terminated by ','
stored as textfile;


INSERT into table RiskFactorFilter select * from RiskFactor where Sample_Size>1000 and Age BETWEEN 40 and 50;


select * from RiskFactorFilter;
 















