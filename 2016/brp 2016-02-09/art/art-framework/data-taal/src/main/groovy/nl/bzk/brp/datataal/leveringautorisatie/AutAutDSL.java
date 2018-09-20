/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.datataal.leveringautorisatie;

import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

/**
 */
public final class AutAutDSL {

    public static Set<String> IDENTITEIT_GROEPEN;

    public static ToegangLeveringautorisatieDslParseerResultaat voerLeveringautorisatiesOp(final Resource resource) {

        final List<DslSectie> list;
        try {
            list = DslSectieParser.parse(resource);
        } catch (IOException e) {
            throw new RuntimeException("Kan dsl niet parsen", e);
        }
        final ListIterator<DslSectie> sectieIterator = list.listIterator();
        final List<ToegangLeveringautorisatieDsl> toegangLeveringAutorisaties = new LinkedList<>();
        LeveringautorisatieDsl leveringautorisatieDsl = null;
        while (sectieIterator.hasNext()) {
            final DslSectie next = sectieIterator.next();
            if (ToegangLeveringautorisatieDsl.SECTIE.equals(next.getSectieNaam())) {
                toegangLeveringAutorisaties.add(new ToegangLeveringautorisatieDsl(next));
            } else {
                sectieIterator.previous();
                leveringautorisatieDsl = LeveringautorisatieDsl.parse(sectieIterator);
            }
        }
        if (leveringautorisatieDsl != null) {
            for (ToegangLeveringautorisatieDsl tla : toegangLeveringAutorisaties) {
                tla.setLeveringautorisatie(leveringautorisatieDsl);
            }
        }
        return new ToegangLeveringautorisatieDslParseerResultaat(toegangLeveringAutorisaties,
                leveringautorisatieDsl);
    }

    public static ToegangBijhoudingautorisatieDsl voerToegangBijhoudingautorisatieOp(final Resource resource) throws IOException {
        return getToegangBijhoudingautorisatieDsl(DslSectieParser.parse(resource));
    }

    public static ToegangBijhoudingautorisatieDsl voerToegangBijhoudingautorisatieOp(final List<String> regels) throws IOException {
        return getToegangBijhoudingautorisatieDsl(DslSectieParser.parse(regels));
    }

    private static ToegangBijhoudingautorisatieDsl getToegangBijhoudingautorisatieDsl(final List<DslSectie> list) {
        final ListIterator<DslSectie> listIterator = list.listIterator();
        return ToegangBijhoudingautorisatieDsl.parse(listIterator);
    }

}
