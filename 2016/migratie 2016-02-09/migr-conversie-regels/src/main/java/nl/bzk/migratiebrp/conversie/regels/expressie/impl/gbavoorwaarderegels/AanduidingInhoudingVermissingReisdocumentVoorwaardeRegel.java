/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.expressie.impl.gbavoorwaarderegels;

import javax.inject.Inject;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.factory.ConversietabelFactory;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AanduidingInhoudingVermissingNederlandsReisdocument;
import org.springframework.stereotype.Component;

/**
 * Voor voorwaarden waarvan de waarde van een rubriek een aanduiding inhouding of vermissing reisdocument betreft.
 */
@Component
public final class AanduidingInhoudingVermissingReisdocumentVoorwaardeRegel extends AbstractStandaardVoorwaardeRegel {

    private static final String REGEX_PATROON = "^12\\.35\\.70.*";
    private static final int VOLGORDE = 500;

    @Inject
    private ConversietabelFactory conversieTabelFactory;

    /**
     * Maakt nieuwe voorwaarderegel aan.
     */
    public AanduidingInhoudingVermissingReisdocumentVoorwaardeRegel() {
        super(VOLGORDE, REGEX_PATROON);
    }

    @Override
    protected String vertaalWaardeVanRubriek(final String ruweWaarde) {
        final String zonderAanhalingstekens = ruweWaarde.replaceAll("\"", "");
        final String vertaaldeWaarde =
                conversieTabelFactory.createAanduidingInhoudingVermissingReisdocumentConversietabel()
                                     .converteerNaarBrp(new Lo3AanduidingInhoudingVermissingNederlandsReisdocument(zonderAanhalingstekens))
                                     .getWaarde()
                                     .toString();
        return String.format("\"%s\"", vertaaldeWaarde);
    }
}
