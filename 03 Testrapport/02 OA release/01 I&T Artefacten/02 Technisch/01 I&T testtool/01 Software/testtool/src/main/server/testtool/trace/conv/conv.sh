#!/bin/bash

OUT_DIR=./output
skip=0

for i in `cat lo.txt | sed 's/ /##/g'`; 
do 
	out=`echo $i | grep -e "^|R[[:digit:]]\+" | sed 's/##//g'`
	line=`echo $i | sed 's/##/ /g'`;

	if [ "${out}" != "" ];
	then
		regel=`echo ${out} | sed 's/^|\(R[[:digit:]]\+\).*/\1/g'`;
		file=`echo "${OUT_DIR}/${regel}.txt"`;
		if [ -e "${file}" ];
		then
			echo "${line}" >> ${file}
		else
			echo "${line}" > ${file}
		fi

		skip=0;
	else 
		if [ "${file}" != "" ];
		then
			echo "${line}" >> ${file}
		fi
	fi
done
