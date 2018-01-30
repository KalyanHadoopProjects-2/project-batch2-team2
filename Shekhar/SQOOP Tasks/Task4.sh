sqoop import \
--options-file /home/orienit/sqoop/db3.properties \
--table categories \
--as-sequencefile \
--compression-codec bzip2 \
--fields-terminated-by ':' \
--target-dir=hdfs://quickstart.cloudera:8020/user/orienit/sqoop/categories5 \
-m 2


sqoop job --create myjob2 \
-- import \
--options-file /home/orienit/sqoop/db3.properties \
--table categories \
--as-sequencefile \
--compress \
--compression-codec org.apache.hadoop.io.compress.BZip2codec \
--fields-terminated-by ':' \
--target-dir=hdfs://quickstart.cloudera:8020/user/orienit/sqoop/categories6 \
-m 2

sqoop job --list

sqoop job --show myjob1

sqoop job --exec myjob1