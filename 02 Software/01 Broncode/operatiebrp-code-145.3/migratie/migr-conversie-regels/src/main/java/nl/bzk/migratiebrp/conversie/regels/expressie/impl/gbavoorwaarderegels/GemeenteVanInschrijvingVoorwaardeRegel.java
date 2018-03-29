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
import nl.bzk.migratiebrp.conversie.regels.expressie.impl.GbaRubriekOnbekendExceptie;
import nl.bzk.migratiebrp.conversie.regels.expressie.impl.GbaVoorwaardeOnvertaalbaarExceptie;
import nl.bzk.migratiebrp.conversie.regels.expressie.impl.RubriekWaarde;
import nl.bzk.migratiebrp.conversie.regels.expressie.impl.criteria.AantalOperator;
import nl.bzk.migratiebrp.conversie.regels.expressie.impl.criteria.Criterium;
import nl.bzk.migratiebrp.conversie.regels.expressie.impl.criteria.ElementWaarde;
import nl.bzk.migratiebrp.conversie.regels.expressie.impl.criteria.EnWaarde;
import nl.bzk.migratiebrp.conversie.regels.expressie.impl.criteria.Expressie;
import nl.bzk.migratiebrp.conversie.regels.expressie.impl.criteria.GelijkAlleOperator;
import nl.bzk.migratiebrp.conversie.regels.expressie.impl.criteria.KNVOperator;
import nl.bzk.migratiebrp.conversie.regels.expressie.impl.criteria.NietWaarde;
import nl.bzk.migratiebrp.conversie.regels.expressie.impl.criteria.OfWaarde;
import org.springframework.stereotype.Component;

/**
 * Voor voorwaarden waarvan de waarde van een rubriek een partij betreft.
 */
@Component
public class GemeenteVanInschrijvingVoorwaardeRegel extends AbstractGbaVoorwaardeRegel {

    private static final String REGEX_PATROON = ".*(08|58)\\.09\\.10.*";
    private static final int VOLGORDE = 100;

    private static final String RNI_LO3_CODE = "1999";
    private static final String HISTORISCHE_CATEGORIE = "58";

    private final StandaardVoorwaardeRegelUtil standaardVoorwaardeRegelUtil;
    private final ConversietabelFactory conversieTabelFactory;
    private final GbaRubriekNaarBrpTypeVertaler gbaRubriekNaarBrpTypeVertaler;
    private final KomtVoorVoorwaardeRegel komtVoorVoorwaardeRegel;

    /**
     * Maakt nieuwe voorwaarderegel aan.
     * @param conversieTabelFactory conversie tabel factory
     * @param gbaRubriekNaarBrpTypeVertaler rubriekvertaler
     * @param komtVoorVoorwaardeRegel vertaler voor KV en KNV regels
     */
    @Inject
    public GemeenteVanInschrijvingVoorwaardeRegel(final ConversietabelFactory conversieTabelFactory,
                                                  final GbaRubriekNaarBrpTypeVertaler gbaRubriekNaarBrpTypeVertaler,
                                                  final KomtVoorVoorwaardeRegel komtVoorVoorwaardeRegel) {
        super(VOLGORDE, REGEX_PATROON);
        this.conversieTabelFactory = conversieTabelFactory;
        this.gbaRubriekNaarBrpTypeVertaler = gbaRubriekNaarBrpTypeVertaler;
        this.komtVoorVoorwaardeRegel = komtVoorVoorwaardeRegel;
        standaardVoorwaardeRegelUtil = new StandaardVoorwaardeRegelUtil(gbaRubriekNaarBrpTypeVertaler,
                waarde -> conversieTabelFactory.createPartijConversietabel().converteerNaarBrp(new Lo3GemeenteCode(waarde)).getWaarde(),
                waarde -> conversieTabelFactory.createGemeenteConversietabel().converteerNaarBrp(new Lo3GemeenteCode(waarde)).getWaarde());
    }

    @Override
    public Expressie getBrpExpressie(final RubriekWaarde voorwaardeRegel) throws GbaVoorwaardeOnvertaalbaarExceptie {
        final Expressie resultaat;
        final String[] delen = voorwaardeRegel.getLo3Expressie().split(GbaVoorwaardeConstanten.SPLIT_CHARACTER, GbaVoorwaardeConstanten.DEEL_AANTAL);
        if (komtVoorVoorwaardeRegel.filter(voorwaardeRegel.getLo3Expressie())) {
            if (betreftHistorischeRubriek(delen)) {
                final Expressie deelExpressie = komtVoorVoorwaardeRegel.getBrpExpressie(voorwaardeRegel);
                try {
                    resultaat = new ElementWaarde(new Criterium(haalRNIElementOpVoorKomtVoorVoorwaardeRegel(delen[GbaVoorwaardeConstanten.DEEL_OPERATOR]),
                            deelExpressie.getCriteria().get(0).getOperator(), null));
                } catch (GbaRubriekOnbekendExceptie gbaRubriekOnbekendExceptie) {
                    throw new GbaVoorwaardeOnvertaalbaarExceptie("Rubriek kan niet vertaald worden", gbaRubriekOnbekendExceptie);
                }
            } else {
                resultaat = komtVoorVoorwaardeRegel.getBrpExpressie(voorwaardeRegel);
            }
        } else {
            if (betreftRNIInEenHistorischeRubriek(delen)) {
                resultaat = voegExtraRNIVoorwaardeToe(voorwaardeRegel, delen[GbaVoorwaardeConstanten.DEEL_RUBRIEK]);
            } else {
                resultaat = standaardVoorwaardeRegelUtil.getBrpExpressie(voorwaardeRegel);
            }
        }

        return resultaat;
    }

    private boolean betreftHistorischeRubriek(final String[] delen) {
        return delen[GbaVoorwaardeConstanten.DEEL_OPERATOR].startsWith(HISTORISCHE_CATEGORIE);
    }

    private Expressie voegExtraRNIVoorwaardeToe(final RubriekWaarde voorwaardeRegel, final String rubriek) throws GbaVoorwaardeOnvertaalbaarExceptie {
        final Expressie resultaat;
        try {
            //OGA1/OGAA OF (HISM(Persoon.Bijhouding.PartijCode) A= 199901 EN AANTAL(HISM(Persoon.Bijhouding.PartijCode)) = 1 EN KNV(HISM(Persoon.Adres
            // .BuitenlandsAdresRegel1))))
            //GA1       EN NIET((HISM(Persoon.Bijhouding.PartijCode) A= 199901 EN AANTAL(HISM(Persoon.Bijhouding.PartijCode)) = 1 EN KNV(HISM(Persoon.Adres
            // .BuitenlandsAdresRegel1)))))
            final String
                    operator =
                    voorwaardeRegel.getLo3Expressie()
                            .split(GbaVoorwaardeConstanten.SPLIT_CHARACTER, GbaVoorwaardeConstanten.DEEL_AANTAL)[GbaVoorwaardeConstanten.DEEL_OPERATOR];
            switch (operator) {
                case "OGA1":
                case "OGAA":
                    resultaat = maakOGAExpressie(voorwaardeRegel, rubriek);
                    break;
                case "GA1":
                case "GAA":
                    resultaat = maakGAExpressie(voorwaardeRegel, rubriek);
                    break;
                default:
                    throw new GbaVoorwaardeOnvertaalbaarExceptie("Onbekende operator " + operator);
            }
        } catch (GbaRubriekOnbekendExceptie gbaRubriekOnbekendExceptie) {
            throw new GbaVoorwaardeOnvertaalbaarExceptie("Rubriek kan niet vertaald worden", gbaRubriekOnbekendExceptie);
        }
        return resultaat;
    }

    private Expressie maakGAExpressie(final RubriekWaarde voorwaardeRegel, final String rubriek)
            throws GbaVoorwaardeOnvertaalbaarExceptie, GbaRubriekOnbekendExceptie {
        final Expressie resultaat;
        resultaat = new EnWaarde(
                standaardVoorwaardeRegelUtil.getBrpExpressie(voorwaardeRegel),
                new NietWaarde(
                        new EnWaarde(
                                new ElementWaarde(
                                        new Criterium(haalElementOp(rubriek),
                                                new GelijkAlleOperator(),
                                                WaardeFormatter.format(
                                                        conversieTabelFactory.createPartijConversietabel()
                                                                .converteerNaarBrp(new Lo3GemeenteCode(RNI_LO3_CODE))
                                                                .getWaarde()))),
                                new ElementWaarde(new Criterium(haalElementOp(rubriek), new AantalOperator(), "1")),
                                new ElementWaarde(new Criterium(haalRNIElementOp(rubriek), new KNVOperator(), null)))));
        return resultaat;
    }

    private Expressie maakOGAExpressie(final RubriekWaarde voorwaardeRegel, final String rubriek)
            throws GbaVoorwaardeOnvertaalbaarExceptie, GbaRubriekOnbekendExceptie {
        final Expressie resultaat;
        resultaat = new OfWaarde(
                standaardVoorwaardeRegelUtil.getBrpExpressie(voorwaardeRegel),
                new EnWaarde(
                        new ElementWaarde(
                                new Criterium(haalElementOp(rubriek),
                                        new GelijkAlleOperator(),
                                        WaardeFormatter.format(
                                                conversieTabelFactory.createPartijConversietabel()
                                                        .converteerNaarBrp(new Lo3GemeenteCode(RNI_LO3_CODE))
                                                        .getWaarde()))),
                        new ElementWaarde(new Criterium(haalElementOp(rubriek), new AantalOperator(), "1")),
                        new ElementWaarde(new Criterium(haalRNIElementOp(rubriek), new KNVOperator(), null))));
        return resultaat;
    }

    private String haalElementOp(final String rubriek) throws GbaRubriekOnbekendExceptie {
        return gbaRubriekNaarBrpTypeVertaler.vertaalGbaRubriekNaarBrpType(rubriek)[0].getType();
    }

    private String haalRNIElementOp(final String rubriek) throws GbaRubriekOnbekendExceptie {
        return gbaRubriekNaarBrpTypeVertaler.vertaalGbaRubriekNaarBrpType(rubriek + "." + RNI_LO3_CODE)[0].getType();
    }

    private String haalRNIElementOpVoorKomtVoorVoorwaardeRegel(final String rubriek) throws GbaRubriekOnbekendExceptie {
        return gbaRubriekNaarBrpTypeVertaler.vertaalGbaRubriekNaarBrpType(rubriek + ".kv")[0].getType();
    }

    private boolean betreftRNIInEenHistorischeRubriek(final String[] delen) {
        final String rubriekWaarde = delen[GbaVoorwaardeConstanten.DEEL_REST];
        return RNI_LO3_CODE.equals(rubriekWaarde) && delen[GbaVoorwaardeConstanten.DEEL_RUBRIEK].startsWith(HISTORISCHE_CATEGORIE);
    }

}
