#!/bin/bash
. ./env

MULTIPLIER_SCRIPT_DIR=${MULTIPLIER_SOFTWARE_DIR}/plc/B
PGPASSWORD=gb4v psql -w -Ugbav -dpostgres < ${MULTIPLIER_DB_SCRIPTS}/reset-gbav.1.psql
PGPASSWORD=gb4v psql -w -Ugbav -dgbav < ${MULTIPLIER_DB_SCRIPTS}/gbav-20151208.sql
PGPASSWORD=gb4v psql -w -Ugbav -dgbav < ${MULTIPLIER_DB_SCRIPTS}/createInitVullingTables.sql
PGPASSWORD=gb4v psql -w -Ugbav -dgbav < ${MULTIPLIER_DB_SCRIPTS}/load-dat-file.psql
PGPASSWORD=gb4v psql -w -Ugbav -dgbav < ${MULTIPLIER_DB_SCRIPTS}/run-function.psql
