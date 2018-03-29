#!/bin/bash

mv M01-LO3 M01
mv M02-LO3 M02

rm -r *BRP

mv TC/LO3 TC/GBA
mv M01/LO3 M01/GBA
mv M02/LO3 M02/GBA

rm */*/old/*;

for i in `cat BSN.txt | awk '{print $4":"$2}' | tr -d '\r'`;
do
        nrs=(${i//:/ });
        for j in */GBA/*/*${nrs[0]}*;
        do
		path=`echo ${j} | sed 's/\/[^\/]*$//'`;
                if [ -e $j ];
                then
                        name=`basename $j | tr '-' '_'`; # | sed "s/_[[:digit:]]\{10\}_/_${nrs[1]//-/}_/"`;
                        mv $j ${path}/${name};
                fi
        done
done

for i in `cat BSN.txt | awk '{print $4":"$2}' | tr -d '\r'`;
do
        nrs=(${i//:/ });

        for j in */BRP/*/*${nrs[0]}*;
        do
		path=`echo ${j} | sed 's/\/[^\/]*$//'`;
                if [ -e $j ];
                then
                        name=`basename $j | tr '-' '_'`; # | sed "s/_[[:digit:]]\{10\}_/_${nrs[1]//-/}_/"`;
                        mv $j ${path}/${name};
                fi
        done
done

#for i in `cat BSN.txt | awk '{print $4":"$2}' | tr -d '\r'`; 
#do
#        nrs=(${i//:/ });
#
#        for j in */SQL/*/*${nrs[0]}*;
#        do
#                if [ -e $j ];
#                then
#                        name=`echo $j | sed 's/_[[:digit:]]\{10\}.csv$//'`;
#                        mv $j ${name}_${nrs[1]}.csv;
#                fi
#        done
#done
