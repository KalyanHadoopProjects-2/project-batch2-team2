non partition Table:::
---------------------------
create table RickFactor_nonPartition(YearStart STRING, YearEnd INT, LocationAbbr STRING, LocationDesc STRING, Datasource STRING, Class STRING, Topic STRING, Question STRING, Data_Value_Unit STRING, Data_Value_Type STRING, Data_Value FLOAT, Data_Value_Alt FLOAT, Data_Value_Footnote_Symbol STRING, Data_Value_Footnote STRING, Low_Confidence_Limit FLOAT, High_Confidence_Limit FLOAT, Sample_Size STRING, Total STRING, Age_years STRING, Education STRING, Gender STRING, Income STRING, RaceorEthnicity STRING, GeoLocation STRING, ClassID STRING, TopicID STRING, QuestionID STRING, DataValueTypeID STRING, LocationID INT, StratificationCategory1 STRING, Stratification1 STRING, StratificationCategoryId1 STRING, StratificationID1 STRING)
row format delimited 
fields terminated by ','
lines terminated by '\n'
tblproperties("skip.header.line.count"="1");


LOAD DATA LOCAL INPATH '/home/orienit/other/Behavioral_Risk_Factor_Surveillance_System.csv'OVERWRITE INTO TABLE RickFactor_nonPartition;


create Parttition table::
-------------------------
create table RickFactor(YearEnd INT, LocationAbbr STRING, Datasource STRING, Class STRING, Topic STRING, Question STRING, Data_Value_Unit STRING, Data_Value_Type STRING, Data_Value FLOAT, Data_Value_Alt FLOAT, Data_Value_Footnote_Symbol STRING, Data_Value_Footnote STRING, Low_Confidence_Limit FLOAT, High_Confidence_Limit FLOAT, Sample_Size STRING, Total STRING, Age_years STRING, Education STRING, Gender STRING, Income STRING, RaceorEthnicity STRING, ClassID STRING, TopicID STRING, QuestionID STRING, DataValueTypeID STRING, LocationID INT, StratificationCategory1 STRING, Stratification1 STRING, StratificationCategoryId1 STRING, StratificationID1 STRING)
partitioned by (YearStart INT, LocationDesc STRING, GeoLocation STRING)
row format delimited 
fields terminated by ','
lines terminated by '\n';


loading:::
set hive.exec.dynamic.partition.mode=nonstrict;    --for dynamic mode

INSERT OVERWRITE TABLE RickFactor PARTITION(YearStart=2011,LocationDesc='Alabama',GeoLocation='(32.84057112200048, -86.63186076199969)') select YearEnd, LocationAbbr, Datasource, Class, Topic, Question, Data_Value_Unit, Data_Value_Type, Data_Value, Data_Value_Alt, Data_Value_Footnote_Symbol, Data_Value_Footnote, Low_Confidence_Limit, High_Confidence_Limit, Sample_Size, Total, Age_years, Education, Gender, Income, RaceorEthnicity, ClassID, TopicID, QuestionID, DataValueTypeID, LocationID, StratificationCategory1, Stratification1, StratificationCategoryId1, StratificationID1  from RickFactor_nonPartition;

select *  from RickFactor where  Sample_size>1000 AND 40<Age_years<50 limit 5;
select *  from RickFactor where Sample_size>1000  limit 5;