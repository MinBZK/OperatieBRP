#!/bin/bash
MIGR_LIB_PATH=
TEST_LIB_PATH=lib/

MIGR_LIB_PATH_LISTING=`ls -l $MIGR_LIB_PATH | egrep '.jar$' | awk '{print $9}'`
TEST_LIB_PATH_LISTING=`ls -l $TEST_LIB_PATH | egrep '.jar$' | awk '{print $9}'`

MIG_LIBRARIES=
for LIB_FILE in $MIGR_LIB_PATH_LISTING
do
    case $LIB_FILE in
        migr-synchronisatie-runtime-* )
    ;;
    *)
        MIG_LIBRARIES=$MIG_LIBRARIES":$MIGR_LIB_PATH/$LIB_FILE"
    ;;
    esac
done

TEST_LIBRARIES=
for TEST_FILE in $TEST_LIB_PATH_LISTING
do
    case $TEST_FILE in
        migr-synchronisatie-dal-*-tests.jar | migr-conversie-model-*-tests.jar)
            TEST_LIBRARIES=$TEST_LIBRARIES":$TEST_LIB_PATH/$TEST_FILE"
        ;;
        migr-synchronisatie-dal-*.jar | migr-conversie-model-*.jar)
        ;;
        *)
            TEST_LIBRARIES=$TEST_LIBRARIES":$TEST_LIB_PATH/$TEST_FILE"
        ;;
    esac
done

java -Dtest.directory=regressie/ -Dtest.thema=$1 -Dtest.casus=$2 -cp $MIG_LIBRARIES$TEST_LIBRARIES org.junit.runner.JUnitCore  nl.bzk.migratiebrp.test.lo3naarbrp.ParameterizedTest
