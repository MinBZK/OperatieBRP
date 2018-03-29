#!/bin/bash

list=$( ./ps-a | grep brp | awk '{print $1,$(NF-1)}' | tr ' ' '#' );

for i in ${list};
do
	values=(${i//#/ });
	context=$( docker exec -u root ${values[0]} ls webapps | grep -v war | grep -v alg-webapp );
	port=$( echo ${values[1]} | sed 's/.*:/:/' | sed 's/->.*//' );
	path=$( docker exec -u root ${values[0]} cat webapps/${context}/WEB-INF/web.xml | grep url-pattern | sed 's/.*>[*\/]\?\([^<]*\)<.*/\1/' );


	echo http://sit-dok04.modernodam.nl$port/$context/$path;
done
