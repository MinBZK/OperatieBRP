/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.expressie.impl.gbavoorwaarderegels;

import javax.inject.Inject;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.factory.ConversietabelFactory;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3GemeenteCode;
import org.springframework.stereotype.Component;

/**
 * Voor voorwaarden waarvan de waarde van een rubriek een gemeente betreft.
 */
@Component
public class GemeenteVoorwaardeRegel extends AbstractStandaardVoorwaardeRegel {

    private static final String REGEX_PATROON = "^(01|02|03|05|09|51|52|53|55|59)\\.03\\.20.*";
    private static final int VOLGORDE = 500;

    @Inject
    private ConversietabelFactory conversieTabelFactory;

    /**
     * Maakt nieuwe voorwaarderegel aan.
     */
    public GemeenteVoorwaardeRegel() {
        super(VOLGORDE, REGEX_PATROON);
    }

    @Override
    public final String vertaalWaardeVanRubriek(final String ruweWaarde) {
        return Short.toString(conversieTabelFactory
                .createGemeenteConversietabel()
                .converteerNaarBrp(new Lo3GemeenteCode(ruweWaarde))
                .getWaarde());
    }
}
