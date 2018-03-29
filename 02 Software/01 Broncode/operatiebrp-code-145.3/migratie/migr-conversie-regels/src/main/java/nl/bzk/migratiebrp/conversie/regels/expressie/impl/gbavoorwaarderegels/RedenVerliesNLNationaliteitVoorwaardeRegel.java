/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.expressie.impl.gbavoorwaarderegels;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.factory.ConversietabelFactory;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3RedenNederlandschapCode;
import nl.bzk.migratiebrp.conversie.regels.expressie.impl.BrpType;
import nl.bzk.migratiebrp.conversie.regels.expressie.impl.GbaRubriekNaarBrpTypeVertaler;
import nl.bzk.migratiebrp.conversie.regels.expressie.impl.GbaRubriekOnbekendExceptie;
import nl.bzk.migratiebrp.conversie.regels.expressie.impl.GbaVoorwaardeOnvertaalbaarExceptie;
import nl.bzk.migratiebrp.conversie.regels.expressie.impl.RubriekWaarde;
import nl.bzk.migratiebrp.conversie.regels.expressie.impl.criteria.Criterium;
import nl.bzk.migratiebrp.conversie.regels.expressie.impl.criteria.ElementWaarde;
import nl.bzk.migratiebrp.conversie.regels.expressie.impl.criteria.EnWaarde;
import nl.bzk.migratiebrp.conversie.regels.expressie.impl.criteria.Expressie;
import nl.bzk.migratiebrp.conversie.regels.expressie.impl.criteria.KNVOperator;
import nl.bzk.migratiebrp.conversie.regels.expressie.impl.criteria.OfWaarde;
import org.springframework.stereotype.Component;

/**
 * voor rubriek met waarde die een reden verlies NL nationaliteit is.
 */
@Component
public final class RedenVerliesNLNationaliteitVoorwaardeRegel extends AbstractGbaVoorwaardeRegel {

    private static final String REGEX_PATROON = "^.*(04|54)\\.64\\.10.*";
    private static final int VOLGORDE = 100;
    private static final int AANTAL_RUBRIEKEN_IN_VERTALING = 3;

    private final StandaardVoorwaardeRegelUtil standaardVoorwaardeRegelUtil;
    private final KomtVoorVoorwaardeRegel komtVoorVoorwaardeRegel;
    private final GbaRubriekNaarBrpTypeVertaler vertaler;

    /**
     * Maakt nieuwe voorwaarderegel aan.
     * @param conversieTabelFactory conversie tabel factory
     * @param gbaRubriekNaarBrpTypeVertaler rubriekvertaler
     * @param komtVoorVoorwaardeRegel vertaler voor KV en KNV regels
     */
    @Inject
    public RedenVerliesNLNationaliteitVoorwaardeRegel(
            final ConversietabelFactory conversieTabelFactory,
            final GbaRubriekNaarBrpTypeVertaler gbaRubriekNaarBrpTypeVertaler,
            final KomtVoorVoorwaardeRegel komtVoorVoorwaardeRegel) {
        super(VOLGORDE, REGEX_PATROON);
        this.komtVoorVoorwaardeRegel = komtVoorVoorwaardeRegel;
        vertaler = gbaRubriekNaarBrpTypeVertaler;
        standaardVoorwaardeRegelUtil = new StandaardVoorwaardeRegelUtil(gbaRubriekNaarBrpTypeVertaler, waarde ->
                conversieTabelFactory.createRedenBeeindigingNationaliteitConversietabel()
                        .converteerNaarBrp(new Lo3RedenNederlandschapCode(waarde)).getWaarde());
    }

    @Override
    public Expressie getBrpExpressie(final RubriekWaarde voorwaardeRegel) throws GbaVoorwaardeOnvertaalbaarExceptie {
        final Expressie deelExpressie;
        if (komtVoorVoorwaardeRegel.filter(voorwaardeRegel.getLo3Expressie())) {
            deelExpressie = komtVoorVoorwaardeRegel.getBrpExpressie(voorwaardeRegel);
        } else {
            deelExpressie = standaardVoorwaardeRegelUtil.getBrpExpressie(voorwaardeRegel);
        }
        final List<Expressie> ofExpressies = new ArrayList<>();
        if (deelExpressie.getCriteria().size() != AANTAL_RUBRIEKEN_IN_VERTALING) {
            throw new GbaVoorwaardeOnvertaalbaarExceptie("aantal elementen van 04.64.10 is 3, indien gewijzigd pas implementatie aan");
        } else {
            final BrpType[] brpTypes;
            try {
                brpTypes = vertaler.vertaalGbaRubriekNaarBrpType("04.64.10.knv");
            } catch (GbaRubriekOnbekendExceptie gbaRubriekOnbekendExceptie) {
                throw new GbaVoorwaardeOnvertaalbaarExceptie("Kan 04.64.10.knv rubriek niet vinden", gbaRubriekOnbekendExceptie);
            }
            for (final Criterium criterium : deelExpressie.getCriteria()) {
                if (criterium.getElement().contains("Staatloos")) {
                    ofExpressies
                            .add(new EnWaarde(new ElementWaarde(criterium), new ElementWaarde(new Criterium(brpTypes[1].getType(), new KNVOperator(), null))));
                } else {
                    ofExpressies
                            .add(new EnWaarde(new ElementWaarde(criterium), new ElementWaarde(new Criterium(brpTypes[0].getType(), new KNVOperator(), null))));
                }
            }
        }
        return new OfWaarde(ofExpressies.toArray(new Expressie[ofExpressies.size()]));
    }
}
