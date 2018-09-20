/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.expressie.impl.gbavoorwaarderegels;

import javax.inject.Inject;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.factory.ConversietabelFactory;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3SoortNederlandsReisdocument;
import org.springframework.stereotype.Component;

/**
 * Voor voorwaarden waarvan de waarde van een rubriek een reisdocument soort betreft.
 */
@Component
public class SoortReisdocumentVoorwaardeRegel extends AbstractStandaardVoorwaardeRegel {

    private static final String REGEX_PATROON = "^12\\.35\\.10.*";
    private static final int VOLGORDE = 500;

    @Inject
    private ConversietabelFactory conversieTabelFactory;

    /**
     * Maakt nieuwe voorwaarderegel aan.
     */
    public SoortReisdocumentVoorwaardeRegel() {
        super(VOLGORDE, REGEX_PATROON);
    }

    @Override
    protected final String vertaalWaardeVanRubriek(final String ruweWaarde) {
        final String zonderAanhalingstekens = ruweWaarde.replaceAll("\"", "");
        final String vertaaldeWaarde = conversieTabelFactory
                .createSoortReisdocumentConversietabel()
                .converteerNaarBrp(new Lo3SoortNederlandsReisdocument(zonderAanhalingstekens))
                .getWaarde();
        return String.format("\"%s\"", vertaaldeWaarde);
    }
}
