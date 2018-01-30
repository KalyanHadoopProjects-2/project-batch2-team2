sqoop export \
--options-file /home/orienit/sqoop/db2.properties \
--table categories1 \
--input-fields-terminated-by ':' \
--update-key category_id \
--update-mode allowinsert \
--export-dir=hdfs://quickstart.cloudera:8020/user/orienit/sqoop/categories2 \
-m 2