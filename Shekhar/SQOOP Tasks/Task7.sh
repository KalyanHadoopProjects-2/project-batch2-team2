sqoop export \
--options-file /home/orienit/sqoop/db2.properties \
--table categories1 \
--input-fields-terminated-by ':' \
--export-dir=hdfs://quickstart.cloudera:8020/user/orienit/sqoop/categories1 \
-m 2