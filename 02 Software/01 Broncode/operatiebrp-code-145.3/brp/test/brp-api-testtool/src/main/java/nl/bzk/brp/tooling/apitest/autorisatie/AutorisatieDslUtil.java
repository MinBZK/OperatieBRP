/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.tooling.apitest.autorisatie;

import com.google.common.collect.Lists;
import java.io.IOException;
import java.util.List;
import java.util.ListIterator;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.test.common.dsl.DslSectie;
import nl.bzk.brp.test.common.dsl.DslSectieParser;
import org.springframework.core.io.Resource;

/**
 * Util klasse voor het parsen van de autorisatie DSL.
 */
final class AutorisatieDslUtil {

    /**
     * sectienaam dienstbundel.
     */
    static final String SECTIE_DIENSTBUNDEL                = "DienstenBundel";
    /**
     * sectienaam dienst.
     */
    static final String SECTIE_DIENST                      = "Dienst";
    /**
     * sectienaam leveringautorisatie.
     */
    static final String SECTIE_LEVERINGAUTORISATIE         = "Levering autorisatie";
    /**
     * sectienaam toegang leveringsautorisatie.
     */
    static final String SECTIE_TOEGANG_LEVERINGAUTORISATIE = "Toegang Levering autorisatie";

    private static final Logger LOGGER = LoggerFactory.getLogger();

    private AutorisatieDslUtil() {

    }

    /**
     * Parsed dsl resource.
     *
     * @param resource    de resource
     * @param idGenerator de id generator
     * @return een parser resultaat
     */
    public static DslParser parse(final Resource resource, final IdGenerator idGenerator) throws IOException {

        final List<DslSectie> list = DslSectieParser.parse(resource);
        final ListIterator<DslSectie> sectieIterator = list.listIterator();
        final List<ToegangLeveringautorisatieParser> toegangLeveringAutorisaties = Lists.newLinkedList();
        LeveringautorisatieParser leveringautorisatieParser = null;
        while (sectieIterator.hasNext()) {
            final DslSectie next = sectieIterator.next();
            if (AutorisatieDslUtil.SECTIE_TOEGANG_LEVERINGAUTORISATIE.equals(next.getSectieNaam())) {
                toegangLeveringAutorisaties.add(new ToegangLeveringautorisatieParser(next, idGenerator));
            } else {
                sectieIterator.previous();
                leveringautorisatieParser = LeveringautorisatieParser.parse(sectieIterator, idGenerator);
            }
        }
        if (leveringautorisatieParser != null) {
            for (final ToegangLeveringautorisatieParser tla : toegangLeveringAutorisaties) {
                tla.setLeveringautorisatie(leveringautorisatieParser);
            }
        }
        return new DslParser(toegangLeveringAutorisaties,
                leveringautorisatieParser);
    }
}
