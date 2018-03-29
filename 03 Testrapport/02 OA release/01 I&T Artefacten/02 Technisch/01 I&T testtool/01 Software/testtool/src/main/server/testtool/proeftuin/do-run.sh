#!/bin/bash
. ./env

rm -fr ${MULTIPLIER_FILES_DIR}/* 2>/dev/null
rm -fr ${MULTIPLIER_OUTPUT_DIR}/* 2>/dev/null

${LO3OTOMY} -m ${MULTIPLIER_GEMEENTE_DIR}/$1 -r $2 -list ${MULTIPLIER_OUTPUT_DIR}/pls.bsn_list.csv -pattern ${MULTIPLIER_SOFTWARE_DIR}/lo3otomy/pattern.dat -a 7777 ${MULTIPLIER_INPUT_DIR}/*.csv >${MULTIPLIER_DATA_FILE}

cut -c39-5039 ${MULTIPLIER_DATA_FILE} | ${LO3OTOMY} -csvs ${MULTIPLIER_FILES_DIR} >${MULTIPLIER_OUTPUT_DIR}/pls.list.csv
zip -qr ${MULTIPLIER_OUTPUT_DIR}/pls.zip ${MULTIPLIER_FILES_DIR}
