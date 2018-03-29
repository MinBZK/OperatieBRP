/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.tools.jmxcollector;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.management.AttributeList;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanServerConnection;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.ReflectionException;

/**
 * Een meting.
 */
class Meting {

    private final String naam;
    private final ObjectName objectNaam;
    private final String[] attributen;

    Meting(final String naam, final String objectNaam, final String... attributen) throws MalformedObjectNameException {
        this.naam = naam;
        this.objectNaam = new ObjectName(objectNaam);
        this.attributen = attributen;
    }

    List<MetingResultaat> haalMetingOp(final MBeanServerConnection connectie) throws MetingException {
        try {
            final AttributeList result = connectie.getAttributes(objectNaam, attributen);
            if (result != null) {
                return result
                        .asList()
                        .stream()
                        .map(attribuut -> new MetingResultaat(String.format("%s.%s", naam, attribuut.getName()), Objects.toString(attribuut.getValue(), "0")))
                        .collect(Collectors.toList());
            } else {
                throw new MetingException(String.format("%s gaf geen resultaat", naam));
            }
        } catch (final InstanceNotFoundException
                | ReflectionException
                | IOException e) {
            throw new MetingException(String.format("Meting is niet gelukt voor: %s", naam), e);
        }
    }

    String getNaam() {
        return naam;
    }
}
