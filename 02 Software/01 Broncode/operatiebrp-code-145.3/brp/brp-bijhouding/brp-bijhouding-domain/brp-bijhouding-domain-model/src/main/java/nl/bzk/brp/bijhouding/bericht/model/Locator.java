/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Betrokkenheid;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortRelatie;

/**
 * Bevat functies en constanties die gedeeld worden door alle locators.
 */
final class Locator {

    static final String GERELATEERDE = "GERELATEERDE";

    private static final String GERELATEERDEGEREGISTREERDEPARTNER = GERELATEERDE + "GEREGISTREERDEPARTNER";
    private static final String GERELATEERDEHUWELIJKSPARTNER = GERELATEERDE + "HUWELIJKSPARTNER";
    private static final String GERELATEERDEKIND = GERELATEERDE + "KIND";
    private static final String GERELATEERDEOUDER = GERELATEERDE + "OUDER";
    private static final String INDICATIE_ELEMENT_PREFIX = "PERSOON_INDICATIE_";

    private Locator() {
        throw new AssertionError("Er mag geen instantie gemaakt worden van deze class");
    }

    static Set<Persoon> zoekGerelateerdePersonen(final Element element, final Persoon persoon) {
        final Set<Persoon> results = new LinkedHashSet<>();
        if (element.name().startsWith(GERELATEERDEGEREGISTREERDEPARTNER)) {
            results.addAll(verzamelPersonen(persoon.getActuelePartners().stream()
                    .filter(betrokkenheid -> SoortRelatie.GEREGISTREERD_PARTNERSCHAP.equals(betrokkenheid.getRelatie().getSoortRelatie())).collect(
                            Collectors.toSet())));
        } else if (element.name().startsWith(GERELATEERDEHUWELIJKSPARTNER)) {
            results.addAll(verzamelPersonen(persoon.getActuelePartners().stream()
                    .filter(betrokkenheid -> SoortRelatie.HUWELIJK.equals(betrokkenheid.getRelatie().getSoortRelatie())).collect(
                            Collectors.toSet())));
        } else if (element.name().startsWith(GERELATEERDEKIND)) {
            results.addAll(verzamelPersonen(persoon.getActueleKinderen()));
        } else if (element.name().startsWith(GERELATEERDEOUDER)) {
            results.addAll(verzamelPersonen(persoon.getActueleOuders()));
        }
        return results;
    }

    static boolean isIndicatie(final Element element) {
        return element.name().startsWith(INDICATIE_ELEMENT_PREFIX);
    }

    private static Set<Persoon> verzamelPersonen(final Set<Betrokkenheid> betrokkenheden) {
        final Set<Persoon> results = new LinkedHashSet<>();
        for (final Betrokkenheid betrokkenheid : betrokkenheden) {
            if (betrokkenheid.getPersoon() != null) {
                results.add(betrokkenheid.getPersoon());
            }
        }
        return results;
    }
}
