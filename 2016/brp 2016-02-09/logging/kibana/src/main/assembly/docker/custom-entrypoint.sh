#!/bin/bash

if [ ! -s "/opt/kibana/config/.customSetup" ]; then
	echo >&2 'Custom setup kibana.yml'
    # Set default dashboard
    sed -ri "s!^(\#\s*)?(kibana\.defaultAppId:).*!\2 'dashboard/Default-dashboard'!" /opt/kibana/config/kibana.yml
	
	checkKibana=$(curl -XHEAD -i 'http://elasticsearch:9200/.kibana')
	if [[ $checkKibana == *"HTTP/1.1 404"* ]]
	then
		echo >&2 'Custom setup kibana in elastic search'
	
		# Setup kibana
		curl -XPUT 'http://elasticsearch:9200/.kibana/' -d @setup/kibana.json
		curl -XPUT 'http://elasticsearch:9200/.kibana/config/4.3.0' -d @setup/config.json
		curl -XPUT 'http://elasticsearch:9200/.kibana/index-pattern/logstash-*' -d @setup/index-pattern-logstash.json
		
		# Setup searches
		curl -XPUT 'http://elasticsearch:9200/.kibana/search/Waarschuwingen' -d @setup/search-waarschuwingen.json
		
		# Setup visualizations
		curl -XPUT 'http://elasticsearch:9200/.kibana/visualization/ISC-Gestart' -d @setup/visualization-isc-gestart.json
		curl -XPUT 'http://elasticsearch:9200/.kibana/visualization/ISC-Jbpm-Gestart' -d @setup/visualization-isc-jbpm-gestart.json
		curl -XPUT 'http://elasticsearch:9200/.kibana/visualization/ISC-Levering-Gestart' -d @setup/visualization-isc-levering-gestart.json
		curl -XPUT 'http://elasticsearch:9200/.kibana/visualization/ISC-Sync-Gestart' -d @setup/visualization-isc-sync-gestart.json
		curl -XPUT 'http://elasticsearch:9200/.kibana/visualization/ISC-Voisc-Gestart' -d @setup/visualization-isc-voisc-gestart.json
		curl -XPUT 'http://elasticsearch:9200/.kibana/visualization/Queues' -d @setup/visualization-queues.json
		curl -XPUT 'http://elasticsearch:9200/.kibana/visualization/ROUTE-Gestart' -d @setup/visualization-route-gestart.json
		curl -XPUT 'http://elasticsearch:9200/.kibana/visualization/SYNC-Afnemer-Gestart' -d @setup/visualization-sync-afnemer-gestart.json
		curl -XPUT 'http://elasticsearch:9200/.kibana/visualization/SYNC-Afnemersindicaties-Gestart' -d @setup/visualization-sync-afnemersindicaties-gestart.json
		curl -XPUT 'http://elasticsearch:9200/.kibana/visualization/SYNC-Archivering-Gestart' -d @setup/visualization-sync-archivering-gestart.json
		curl -XPUT 'http://elasticsearch:9200/.kibana/visualization/SYNC-Gemeente-Gestart' -d @setup/visualization-sync-gemeente-gestart.json
		curl -XPUT 'http://elasticsearch:9200/.kibana/visualization/SYNC-Gestart' -d @setup/visualization-sync-gestart.json
		curl -XPUT 'http://elasticsearch:9200/.kibana/visualization/SYNC-Sync-Gestart' -d @setup/visualization-sync-sync-gestart.json
		curl -XPUT 'http://elasticsearch:9200/.kibana/visualization/SYNC-ToevalligeGebeurtenissen-Gestart' -d @setup/visualization-sync-toevalligegebeurtenissen-gestart.json
		curl -XPUT 'http://elasticsearch:9200/.kibana/visualization/VOISC-Gestart' -d @setup/visualization-voisc-gestart.json
		curl -XPUT 'http://elasticsearch:9200/.kibana/visualization/VOISC-Isc-Ontvangen-Gestart' -d @setup/visualization-voisc-isc-ontvangen-gestart.json
		curl -XPUT 'http://elasticsearch:9200/.kibana/visualization/VOISC-Isc-Versturen-Gestart' -d @setup/visualization-voisc-isc-versturen-gestart.json
		curl -XPUT 'http://elasticsearch:9200/.kibana/visualization/VOISC-Mailbox-Gestart' -d @setup/visualization-voisc-mailbox-gestart.json
		curl -XPUT 'http://elasticsearch:9200/.kibana/visualization/Waarschuwingen-per-applicatie' -d @setup/visualization-waarschuwingen-per-applicatie.json
		
		# Setup dashboards
		curl -XPUT 'http://elasticsearch:9200/.kibana/dashboard/Default-dashboard' -d @setup/dashboard-default.json
		curl -XPUT 'http://elasticsearch:9200/.kibana/dashboard/Migratie-Status' -d @setup/dashboard-migratie-status.json
	fi
	echo >&2 'Custom setup kibana done.'
	
    echo >/opt/kibana/config/.customSetup 'done'
fi

./docker-entrypoint.sh $@
