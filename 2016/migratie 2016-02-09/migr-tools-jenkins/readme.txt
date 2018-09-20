Backup van jenkins configuratie files

cd /var/lib/jenkins/jobs
zip -r /tmp/backupJenkins.zip * -i */config.xml
