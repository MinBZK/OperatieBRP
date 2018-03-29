/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.expressie.impl.gbavoorwaarderegels;

import javax.inject.Inject;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.factory.ConversietabelFactory;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3IndicatieDocument;
import nl.bzk.migratiebrp.conversie.regels.expressie.impl.GbaRubriekNaarBrpTypeVertaler;
import nl.bzk.migratiebrp.conversie.regels.expressie.impl.GbaVoorwaardeOnvertaalbaarExceptie;
import nl.bzk.migratiebrp.conversie.regels.expressie.impl.RubriekWaarde;
import nl.bzk.migratiebrp.conversie.regels.expressie.impl.criteria.Expressie;
import org.springframework.stereotype.Component;

/**
 * Vertaald GBA voorwaarde regels van type indicatie document; hiervoor wordt een conversie tabel gebruikt.
 */
@Component
public final class IndicatieDocumentVoorwaardeRegel extends AbstractGbaVoorwaardeRegel {

    private static final String REGEX_PATROON = "^(08|58)\\.75\\.10.*";
    private static final int VOLGORDE = 500;

    private final IndicatieVoorwaardeRegelUtil indicatieVoorwaardeRegelUtil;

    /**
     * Maakt nieuwe voorwaarderegel aan.
     * @param conversieTabelFactory conversie tabel factory
     * @param gbaRubriekNaarBrpTypeVertaler rubriekvertaler
     */
    @Inject
    public IndicatieDocumentVoorwaardeRegel(
            final ConversietabelFactory conversieTabelFactory,
            final GbaRubriekNaarBrpTypeVertaler gbaRubriekNaarBrpTypeVertaler) {
        super(VOLGORDE, REGEX_PATROON);
        indicatieVoorwaardeRegelUtil = new IndicatieVoorwaardeRegelUtil(gbaRubriekNaarBrpTypeVertaler, waarde ->
                conversieTabelFactory.createIndicatieDocumentConversietabel()
                        .converteerNaarBrp(new Lo3IndicatieDocument(waarde.replaceAll("\"", ""), null))
                        .getWaarde()
        );
    }

    @Override
    public Expressie getBrpExpressie(final RubriekWaarde voorwaardeRegel) throws GbaVoorwaardeOnvertaalbaarExceptie {
        return indicatieVoorwaardeRegelUtil.getBrpExpressie(voorwaardeRegel);
    }
}
