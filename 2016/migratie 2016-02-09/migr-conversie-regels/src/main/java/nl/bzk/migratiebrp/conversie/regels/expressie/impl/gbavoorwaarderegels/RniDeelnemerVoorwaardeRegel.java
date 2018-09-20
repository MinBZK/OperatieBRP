/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.expressie.impl.gbavoorwaarderegels;

import javax.inject.Inject;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPartijCode;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.factory.ConversietabelFactory;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3RNIDeelnemerCode;
import org.springframework.stereotype.Component;

/**
 * Voor voorwaarden waarvan de waarde van een rubriek een RNI deelnemer betreft.
 */
@Component
public class RniDeelnemerVoorwaardeRegel extends AbstractStandaardVoorwaardeRegel {

    private static final String REGEX_PATROON = "^(0|5)(1|4|6|7|8)\\.88\\.10.*";
    private static final int VOLGORDE = 500;

    @Inject
    private ConversietabelFactory conversieTabelFactory;

    /**
     * Maakt nieuwe voorwaarderegel aan.
     */
    public RniDeelnemerVoorwaardeRegel() {
        super(VOLGORDE, REGEX_PATROON);
    }

    @Override
    public final String vertaalWaardeVanRubriek(final String ruweWaarde) {
        final Lo3RNIDeelnemerCode lo3Waarde = new Lo3RNIDeelnemerCode(ruweWaarde);
        final BrpPartijCode brpWaarde = conversieTabelFactory.createRNIDeelnemerConversietabel().converteerNaarBrp(lo3Waarde);

        return Integer.toString(brpWaarde.getWaarde());
    }
}
