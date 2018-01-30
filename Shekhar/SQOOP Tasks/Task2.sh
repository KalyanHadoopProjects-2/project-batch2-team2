mkdir -p /home/orienit/sqoop
touch /home/orienit/sqoop/db1.properties

sqoop import \
--options-file /home/orienit/sqoop/db1.properties \
--table categories \
--as-avrodatafile \
--fields-terminated-by ':' \
--delete-target-dir \
--target-dir=hdfs://quickstart.cloudera:8020/user/orienit/sqoop/categories2 \
-m 2