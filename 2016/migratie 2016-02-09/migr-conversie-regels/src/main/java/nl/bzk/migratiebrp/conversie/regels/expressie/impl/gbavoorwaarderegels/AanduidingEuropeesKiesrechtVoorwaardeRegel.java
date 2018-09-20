/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.expressie.impl.gbavoorwaarderegels;

import javax.inject.Inject;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.factory.ConversietabelFactory;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AanduidingEuropeesKiesrecht;
import org.springframework.stereotype.Component;

/**
 * Vertaald GBA voorwaarde regels van type aanduiding europees kiesrecht; hiervoor wordt een conversie tabel gebruikt.
 */
@Component
public final class AanduidingEuropeesKiesrechtVoorwaardeRegel extends AbstractStandaardVoorwaardeRegel {

    /**
     * Regex expressie voor selectie van voorwaarderegels die door deze class worden behandeld.
     */
    public static final String REGEX_PATROON = "^13\\.31\\.10.*";
    private static final int VOLGORDE = 500;

    @Inject
    private ConversietabelFactory conversieTabelFactory;

    /**
     * Maakt nieuwe voorwaarderegel aan.
     */
    public AanduidingEuropeesKiesrechtVoorwaardeRegel() {
        super(VOLGORDE, REGEX_PATROON);
    }

    @Override
    public String vertaalWaardeVanRubriek(final String ruweWaarde) {
        final String zonderAanhalingstekens = ruweWaarde.replaceAll("\"", "");
        final Boolean vertaaldeWaarde =
                conversieTabelFactory.createAanduidingEuropeesKiesrechtConversietabel()
                                     .converteerNaarBrp(new Lo3AanduidingEuropeesKiesrecht(zonderAanhalingstekens, null))
                                     .getWaarde();

        return vertaaldeWaarde ? "WAAR" : "ONWAAR";
    }
}
