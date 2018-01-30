sqoop import \
--connect jdbc:mysql://quickstart.cloudera:3306/retail_db \
--username root \
--password cloudera \
--table categories \
--as-textfile \
--fields-terminated-by ':' \
--target-dir=hdfs://quickstart.cloudera:8020/user/orienit/sqoop/categories1 \
-m 2