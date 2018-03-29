/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.docker.dockerservice.mojo.service;


import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ListPortMappings {

    public static void main(String[] args) throws Exception {
        final int port = 3481;
        final Pattern pattern = Pattern.compile("(\\d+)\\:"+port);

        final Map<String,Integer> mappings = new TreeMap<>();
        for(final String service : ServicesUtil.bepaalAlleServices()) {
            final Properties properties = ServiceUtil.getServiceProperties(service);
            final String ports = properties.getProperty("create.ports", "");
            final Matcher matcher = pattern.matcher(ports);
            if(matcher.find()) {
                mappings.put(service, Integer.valueOf(matcher.group(1)));
            }
        }

        System.out.println("Aantal mappings voor poort " + port + " -> " + mappings.size());
        for(final Map.Entry<String,Integer> mapping : mappings.entrySet()) {
            System.out.println(mapping.getKey() + " -> " + mapping.getValue());
        }
    }
}
