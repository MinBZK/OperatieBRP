/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.expressie.impl.gbavoorwaarderegels;

import javax.inject.Inject;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.factory.ConversietabelFactory;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.bzk.migratiebrp.conversie.regels.expressie.impl.GbaRubriekNaarBrpTypeVertaler;
import nl.bzk.migratiebrp.conversie.regels.expressie.impl.GbaVoorwaardeOnvertaalbaarExceptie;
import nl.bzk.migratiebrp.conversie.regels.expressie.impl.RubriekWaarde;
import nl.bzk.migratiebrp.conversie.regels.expressie.impl.criteria.Expressie;
import org.springframework.stereotype.Component;

/**
 * Voor voorwaarden waarvan de waarde van een rubriek een partij betreft.
 */
@Component
public class PartijVoorwaardeRegel extends AbstractGbaVoorwaardeRegel {

    private static final String REGEX_PATROON = "07\\.69\\.10.*";
    private static final int VOLGORDE = 100;

    private final StandaardVoorwaardeRegelUtil standaardVoorwaardeRegelUtil;

    /**
     * Maakt nieuwe voorwaarderegel aan.
     * @param conversieTabelFactory conversie tabel factory
     * @param gbaRubriekNaarBrpTypeVertaler rubriekvertaler
     */
    @Inject
    public PartijVoorwaardeRegel(final ConversietabelFactory conversieTabelFactory,
                                 final GbaRubriekNaarBrpTypeVertaler gbaRubriekNaarBrpTypeVertaler) {
        super(VOLGORDE, REGEX_PATROON);
        standaardVoorwaardeRegelUtil = new StandaardVoorwaardeRegelUtil(gbaRubriekNaarBrpTypeVertaler, waarde ->
                conversieTabelFactory.createPartijConversietabel().converteerNaarBrp(new Lo3GemeenteCode(waarde)).getWaarde());
    }

    @Override
    public Expressie getBrpExpressie(final RubriekWaarde voorwaardeRegel) throws GbaVoorwaardeOnvertaalbaarExceptie {
        return standaardVoorwaardeRegelUtil.getBrpExpressie(voorwaardeRegel);
    }
}
