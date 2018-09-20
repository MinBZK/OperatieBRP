nohup java -cp cp:TestDataGeneratie-2.0.2-jar-with-dependencies.jar nl.bzk.brp.testdatageneratie.SynDbGen &
sleep 3
tail -f syndbgen-debug.log
