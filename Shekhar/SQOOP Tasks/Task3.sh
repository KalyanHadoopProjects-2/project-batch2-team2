touch /home/orienit/sqoop/db2.properties
touch /home/orienit/sqoop/db.password
echo -n "cloudera" > /home/orienit/sqoop/db.password
chmod 400 /home/orienit/sqoop/db.password

hdfs dfs -mkdir -p /usr/orienit/sqoop/db/password
hdfs dfs -put /home/orienit/sqoop/db.password /usr/orienit/sqoop/db/password


sqoop import \
--options-file /home/orienit/sqoop/db2.properties \
--table categories \
--as-parquetfile \
--fields-terminated-by ':' \
--delete-target-dir \
--target-dir=hdfs://quickstart.cloudera:8020/user/orienit/sqoop/categories3 \
-m 2

touch /home/orienit/sqoop/db3.properties
hdfs dfs -mkdir -p /usr/orienit/sqoop/db/password
hdfs dfs -put /home/orienit/sqoop/db.password /usr/orienit/sqoop/db/password


sqoop import \
--options-file /home/orienit/sqoop/db3.properties \
--table categories \
--as-parquetfile \
--fields-terminated-by ':' \
--delete-target-dir \
--target-dir=hdfs://quickstart.cloudera:8020/user/orienit/sqoop/categories4 \
-m 2