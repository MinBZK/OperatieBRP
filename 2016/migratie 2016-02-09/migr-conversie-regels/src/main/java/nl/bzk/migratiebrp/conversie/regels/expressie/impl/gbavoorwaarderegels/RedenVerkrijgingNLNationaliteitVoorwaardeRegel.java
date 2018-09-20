/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.expressie.impl.gbavoorwaarderegels;

import javax.inject.Inject;

import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.factory.ConversietabelFactory;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3RedenNederlandschapCode;

import org.springframework.stereotype.Component;

/**
 * voor rubriek met waarde die een reden verkrijging NL nationaliteit is.
 */
@Component
public class RedenVerkrijgingNLNationaliteitVoorwaardeRegel extends AbstractStandaardVoorwaardeRegel {

    private static final String REGEX_PATROON = "^(04|54)\\.63\\.10.*";
    private static final int VOLGORDE = 500;

    @Inject
    private ConversietabelFactory conversieTabelFactory;

    /**
     * Maakt nieuwe voorwaarderegel aan.
     */
    public RedenVerkrijgingNLNationaliteitVoorwaardeRegel() {
        super(VOLGORDE, REGEX_PATROON);
    }

    @Override
    protected final String vertaalWaardeVanRubriek(final String ruweWaarde) {
        return Short.toString(
            conversieTabelFactory.createRedenOpnameNationaliteitConversietabel()
                                                   .converteerNaarBrp(new Lo3RedenNederlandschapCode(ruweWaarde))
                                                   .getWaarde());
    }
}
