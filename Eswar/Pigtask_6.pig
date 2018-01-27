REGISTER /usr/lib/pig/piggybank.jar;
REGISTER /usr/lib/avro/avro.jar;
REGISTER /home/orienit/other/json-simple-1.1.jar;
REGISTER /home/orienit/other/snappy-java-1.0.4.1.jar;

A = LOAD '/user/orienit/pig/avro/student.avro' USING org.apache.pig.piggybank.storage.avro.AvroStorage();


filtered = FILTER A BY id>2 AND course=='spark';

store filtered into '/user/orienit/pig/student_avro_op' USING org.apache.pig.piggybank.storage.avro.AvroStorage();