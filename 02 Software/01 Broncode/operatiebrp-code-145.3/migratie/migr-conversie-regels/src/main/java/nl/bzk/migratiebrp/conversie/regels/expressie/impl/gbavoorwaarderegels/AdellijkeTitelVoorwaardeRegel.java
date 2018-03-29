/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.expressie.impl.gbavoorwaarderegels;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.AdellijkeTitelPredikaatPaar;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.factory.ConversietabelFactory;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AdellijkeTitelPredikaatCode;
import nl.bzk.migratiebrp.conversie.regels.expressie.impl.BrpType;
import nl.bzk.migratiebrp.conversie.regels.expressie.impl.GbaRubriekNaarBrpTypeVertaler;
import nl.bzk.migratiebrp.conversie.regels.expressie.impl.GbaRubriekOnbekendExceptie;
import nl.bzk.migratiebrp.conversie.regels.expressie.impl.GbaVoorwaardeOnvertaalbaarExceptie;
import nl.bzk.migratiebrp.conversie.regels.expressie.impl.RubriekWaarde;
import nl.bzk.migratiebrp.conversie.regels.expressie.impl.criteria.Criterium;
import nl.bzk.migratiebrp.conversie.regels.expressie.impl.criteria.ElementWaarde;
import nl.bzk.migratiebrp.conversie.regels.expressie.impl.criteria.EnWaarde;
import nl.bzk.migratiebrp.conversie.regels.expressie.impl.criteria.Expressie;
import nl.bzk.migratiebrp.conversie.regels.expressie.impl.criteria.GelijkEenOperator;
import nl.bzk.migratiebrp.conversie.regels.expressie.impl.criteria.KNVOperator;
import nl.bzk.migratiebrp.conversie.regels.expressie.impl.criteria.KVOperator;
import nl.bzk.migratiebrp.conversie.regels.expressie.impl.criteria.OfWaarde;
import nl.bzk.migratiebrp.conversie.regels.expressie.impl.criteria.Operator;
import org.springframework.stereotype.Component;

/**
 * Conversie van de adelijke titel en predicaat naar brp expressie.
 */
@Component
public final class AdellijkeTitelVoorwaardeRegel extends AbstractGbaVoorwaardeRegel {

    private static final int AANTAL_TYPEN_VOOR_ADELLIJKE_TITEL = 3;

    /**
     * Regex expressie voor selectie van voorwaarderegels die door deze class worden behandeld.
     */
    private static final String REGEX_PATROON = ".*(01|02|03|05|09|51|52|53|55|59)\\.02\\.20.*";
    private static final int VOLGORDE = 100;

    private final ConversietabelFactory conversieTabelFactory;
    private final GbaRubriekNaarBrpTypeVertaler gbaRubriekNaarBrpTypeVertaler;

    /**
     * Maakt nieuwe voorwaarderegel aan.
     * @param conversieTabelFactory conversie tabel factory
     * @param gbaRubriekNaarBrpTypeVertaler rubriekvertaler
     */
    @Inject
    public AdellijkeTitelVoorwaardeRegel(
            final ConversietabelFactory conversieTabelFactory,
            final GbaRubriekNaarBrpTypeVertaler gbaRubriekNaarBrpTypeVertaler) {
        super(VOLGORDE, REGEX_PATROON);
        this.conversieTabelFactory = conversieTabelFactory;
        this.gbaRubriekNaarBrpTypeVertaler = gbaRubriekNaarBrpTypeVertaler;
    }

    @Override
    public Expressie getBrpExpressie(final RubriekWaarde voorwaardeRegel) throws GbaVoorwaardeOnvertaalbaarExceptie {
        final Expressie result;
        try {
            final String[] delen = voorwaardeRegel.getLo3Expressie().split(GbaVoorwaardeConstanten.SPLIT_CHARACTER);
            if (voorwaardeRegel.getLo3Expressie().matches("^KV +01\\.02\\.20.*")) {
                BrpType[] brpTypen = gbaRubriekNaarBrpTypeVertaler.vertaalGbaRubriekNaarBrpType(delen[GbaVoorwaardeConstanten.DEEL_OPERATOR]);
                final String expressieAdellijkeTitel = brpTypen[0].getType();
                final String expressiePredicaat = brpTypen[1].getType();
                result = maakExpressieKV(expressieAdellijkeTitel, expressiePredicaat);
            } else if (voorwaardeRegel.getLo3Expressie().matches("^KNV +01\\.02\\.20.*")) {
                BrpType[] brpTypen = gbaRubriekNaarBrpTypeVertaler.vertaalGbaRubriekNaarBrpType(delen[GbaVoorwaardeConstanten.DEEL_OPERATOR]);
                final String expressieAdellijkeTitel = brpTypen[0].getType();
                final String expressiePredicaat = brpTypen[1].getType();
                result = maakExpressieKNV(expressieAdellijkeTitel, expressiePredicaat);
            } else if (voorwaardeRegel.getLo3Expressie().matches("^(01|02|03|05|09|51|52|53|55|59)\\.02\\.20.*")) {
                result = maakExpressieOverig(voorwaardeRegel.getLo3Expressie());
            } else {
                throw new GbaVoorwaardeOnvertaalbaarExceptie(String.format("Kan voorwaarde [%s] niet vertalen", voorwaardeRegel));
            }
        } catch (final GbaRubriekOnbekendExceptie groe) {
            throw new GbaVoorwaardeOnvertaalbaarExceptie(voorwaardeRegel.getLo3Expressie(), groe);
        }
        return result;
    }

    private Expressie maakExpressieKV(final String expressieAdellijkeTitel, final String expressiePredicaat) {
        Expressie result;
        final Criterium criteriumAdellijkeTitel = new Criterium(expressieAdellijkeTitel, new KVOperator(), null);
        final Criterium criteriumPredicaat = new Criterium(expressiePredicaat, new KVOperator(), null);
        result = new OfWaarde(new ElementWaarde(criteriumAdellijkeTitel), new ElementWaarde(criteriumPredicaat));
        return result;
    }

    private Expressie maakExpressieKNV(final String expressieAdellijkeTitel, final String expressiePredicaat) {
        Expressie result;
        final Criterium criteriumAdellijkeTitel = new Criterium(expressieAdellijkeTitel, new KNVOperator(), null);
        final Criterium criteriumPredicaat = new Criterium(expressiePredicaat, new KNVOperator(), null);
        result = new EnWaarde(new ElementWaarde(criteriumAdellijkeTitel), new ElementWaarde(criteriumPredicaat));
        return result;
    }

    private Expressie maakExpressieOverig(final String lo3Expressie) throws GbaRubriekOnbekendExceptie, GbaVoorwaardeOnvertaalbaarExceptie {
        final String[] delen = lo3Expressie.split(GbaVoorwaardeConstanten.SPLIT_CHARACTER);
        final BrpType[] brpTypen = gbaRubriekNaarBrpTypeVertaler.vertaalGbaRubriekNaarBrpType(delen[GbaVoorwaardeConstanten.DEEL_RUBRIEK]);
        final Expressie result;
        if (delen[GbaVoorwaardeConstanten.DEEL_REST].matches("\\d{2}\\.\\d{2}\\.\\d{2}")) {
            result = maakExpressieOverigMetRubrieken(delen, brpTypen);
        } else {
            result = maakExpressieOverigMetWaarden(delen[GbaVoorwaardeConstanten.DEEL_REST], brpTypen);
        }
        return result;
    }

    private Expressie maakExpressieOverigMetRubrieken(final String[] delen, final BrpType[] brpTypen)
            throws GbaRubriekOnbekendExceptie, GbaVoorwaardeOnvertaalbaarExceptie {
        final BrpType[] brpTypenWaarden = gbaRubriekNaarBrpTypeVertaler.vertaalGbaRubriekNaarBrpType(delen[GbaVoorwaardeConstanten.DEEL_REST]);
        final List<Expressie> expressies = new ArrayList<>(3);
        final Operator operator = bepaalOperator(delen[GbaVoorwaardeConstanten.DEEL_OPERATOR]);

        maakDeelExpressies(brpTypen, brpTypenWaarden, expressies, operator);
        return new EnWaarde(expressies.get(0), expressies.get(1));
    }


    private void maakDeelExpressies(final BrpType[] brpTypen, final BrpType[] brpTypenWaarden, final List<Expressie> expressies,
                                    final Operator operator) {
        for (int x = 0; x < brpTypen.length; x++) {
            if (brpTypenWaarden[x].isLijst()) {
                expressies.add(new ElementWaarde(new Criterium(brpTypenWaarden[x].getType(), operator, brpTypen[x].getType())));
            } else {
                expressies.add(new ElementWaarde(new Criterium(brpTypen[x].getType(), operator, brpTypenWaarden[x].getType())));
            }
        }
    }

    private Expressie maakExpressieOverigMetWaarden(final String waarde, final BrpType[] brpTypen) {
        Expressie result;
        final AdellijkeTitelPredikaatPaar geconverteerdeWaarde =
                conversieTabelFactory.createAdellijkeTitelPredikaatConversietabel()
                        .converteerNaarBrp(new Lo3AdellijkeTitelPredikaatCode(waarde));

        if (geconverteerdeWaarde.getAdellijkeTitel() != null) {
            final Criterium criteriumAdellijkeTitel =
                    new Criterium(
                            brpTypen[0].getType(),
                            new GelijkEenOperator(),
                            String.valueOf(geconverteerdeWaarde.getAdellijkeTitel().getWaarde()));
            if (brpTypen.length == AANTAL_TYPEN_VOOR_ADELLIJKE_TITEL) {
                final Criterium criteriumGeslacht =
                        new Criterium(brpTypen[2].getType(), new GelijkEenOperator(), geconverteerdeWaarde.getGeslachtsaanduiding().getWaarde());
                result = new EnWaarde(new ElementWaarde(criteriumAdellijkeTitel), new ElementWaarde(criteriumGeslacht));
            } else {
                result = new ElementWaarde(criteriumAdellijkeTitel);
            }
        } else {
            final Criterium criteriumPredicaat =
                    new Criterium(brpTypen[1].getType(), new GelijkEenOperator(), String.valueOf(geconverteerdeWaarde.getPredikaat().getWaarde()));
            if (brpTypen.length == AANTAL_TYPEN_VOOR_ADELLIJKE_TITEL) {
                final Criterium criteriumGeslacht =
                        new Criterium(brpTypen[2].getType(), new GelijkEenOperator(), geconverteerdeWaarde.getGeslachtsaanduiding().getWaarde());
                result = new EnWaarde(new ElementWaarde(criteriumPredicaat), new ElementWaarde(criteriumGeslacht));
            } else {
                result = new ElementWaarde(criteriumPredicaat);
            }
        }
        return result;
    }
}
