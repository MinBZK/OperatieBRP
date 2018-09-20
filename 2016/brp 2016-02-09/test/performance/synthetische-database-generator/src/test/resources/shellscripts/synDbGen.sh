nohup java -cp cp:./../../../../target/TestDataGeneratie-6.0.2-SNAPSHOT-jar-with-dependencies.jar nl.bzk.brp.testdatageneratie.SynDbGen &
sleep 3
tail -f syndbgen-debug.log
