/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.datataal.leveringautorisatie;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.PatternSyntaxException;

/**
 * De autautDSL bestaat uit secties (blokken) met
 * key/value paren. Deze low-level parser maakt deze blokken
 * en groepering op regels.
 */
final class DslSectieParser {

    private List<String> regels;
    private List<DslSectie> secties = new LinkedList<>();

    private DslSectieParser(final List<String> regels) {
        this.regels = regels;
    }

    /**
     *
     * @param resource
     * @return
     * @throws IOException
     */
    static List<DslSectie> parse(final Resource resource) throws IOException {
        final DslSectieParser parser;
        try (InputStream inputStream = resource.getInputStream()) {
            parser = new DslSectieParser(IOUtils.readLines(inputStream));
        }
        return parser.parseRegels();
    }

    /**
     *
     * @param regels
     * @return
     * @throws IOException
     */
    static List<DslSectie> parse(final List<String> regels) throws IOException {
        return new DslSectieParser(regels).parseRegels();
    }

    private void leesSectie(final Iterator<String> regelIterator) {

        DslSectie sectie = null;
        while (regelIterator.hasNext()) {
            final String regel = StringUtils.trimToNull(regelIterator.next());
            if (regel == null) {
                if (sectie != null) {
                    //einde sectie bereikt
                    secties.add(sectie);
                    sectie = null;
                }
                continue;
            }

            if (regel.startsWith("#")) {
                continue;
            }

            if (sectie == null) {
                sectie = new DslSectie(regel);
            }

            final String[] keyValuePaar = maakKeyValuePaar(regel);
            if (keyValuePaar != null) {
                sectie.addRegel(keyValuePaar[0], keyValuePaar[1]);
            }
        }
        if (sectie != null) {
            secties.add(sectie);
        }

    }

    private List<DslSectie> parseRegels() {
        leesSectie(regels.iterator());
        return secties;
    }

    private String[] maakKeyValuePaar(final String regel) {
        String[] keyValuePaar;
        try {
            keyValuePaar = StringUtils.split(regel, ":");
        } catch (PatternSyntaxException e) {
            keyValuePaar = new String[0];
        }
        if (keyValuePaar.length != 2) {
            return null;
        }

        keyValuePaar[0] = StringUtils.trimToNull(keyValuePaar[0]);
        keyValuePaar[1] = StringUtils.trimToNull(keyValuePaar[1]);
        if (keyValuePaar[0] == null) {
            throw new IllegalStateException(String.format("Ongeldig key/value paar: %s ", regel));
        }
        return keyValuePaar;
    }
}
