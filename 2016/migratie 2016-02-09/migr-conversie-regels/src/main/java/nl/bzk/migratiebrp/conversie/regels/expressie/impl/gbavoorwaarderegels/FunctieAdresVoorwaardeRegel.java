/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.expressie.impl.gbavoorwaarderegels;

import javax.inject.Inject;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.factory.ConversietabelFactory;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3FunctieAdres;
import org.springframework.stereotype.Component;

/**
 * Vertaald GBA voorwaarde regels van type functie adres; hiervoor wordt een conversie tabel gebruikt.
 */
@Component
public class FunctieAdresVoorwaardeRegel extends AbstractStandaardVoorwaardeRegel {

    /**
     * Regex expressie voor selectie van voorwaarderegels die door deze class worden behandeld.
     */
    public static final String REGEX_PATROON = "^(08|58)\\.10\\.10.*";
    private static final int VOLGORDE = 500;

    @Inject
    private ConversietabelFactory conversieTabelFactory;

    /**
     * Maakt nieuwe voorwaarderegel aan.
     */
    public FunctieAdresVoorwaardeRegel() {
        super(VOLGORDE, REGEX_PATROON);
    }

    @Override
    public final String vertaalWaardeVanRubriek(final String ruweWaarde) {
        final String zonderAanhalingstekens = ruweWaarde.replaceAll("\"", "");
        final String vertaaldeWaarde =
                conversieTabelFactory.createFunctieAdresConversietabel().converteerNaarBrp(new Lo3FunctieAdres(zonderAanhalingstekens)).getWaarde();
        return String.format("\"%s\"", vertaaldeWaarde);
    }
}
