/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.expressie.impl.gbavoorwaarderegels;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.inject.Inject;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.factory.ConversietabelFactory;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.bzk.migratiebrp.conversie.regels.expressie.impl.BrpType;
import nl.bzk.migratiebrp.conversie.regels.expressie.impl.GbaRubriekNaarBrpTypeVertaler;
import nl.bzk.migratiebrp.conversie.regels.expressie.impl.GbaRubriekOnbekendExceptie;
import nl.bzk.migratiebrp.conversie.regels.expressie.impl.GbaVoorwaardeOnvertaalbaarExceptie;
import nl.bzk.migratiebrp.conversie.regels.expressie.impl.RubriekWaarde;
import nl.bzk.migratiebrp.conversie.regels.expressie.impl.criteria.Criterium;
import nl.bzk.migratiebrp.conversie.regels.expressie.impl.criteria.ElementWaarde;
import nl.bzk.migratiebrp.conversie.regels.expressie.impl.criteria.Expressie;
import nl.bzk.migratiebrp.conversie.regels.expressie.impl.criteria.OfWaarde;
import nl.bzk.migratiebrp.conversie.regels.expressie.impl.criteria.Operator;
import org.springframework.stereotype.Component;

/**
 * Voor voorwaarden waarvan de waarde van een rubriek een gemeente betreft.
 */
@Component
public final class GemeenteVoorwaardeRegel extends AbstractGbaVoorwaardeRegel {

    private static final String REGEX_PATROON = "^(01|02|03|05|09|51|52|53|55|59)\\.03\\.20.*";
    private static final int VOLGORDE = 500;
    private static final int GEMEENTE_CODE = 0;
    private static final int BUITENLANDSE_PLAATS = 1;
    private static final int OMSCHRIJVING_LOCATIE = 2;
    private static final int WOONPLAATSNAAM = 3;
    private static final int AANTAL_ELEMENTEN_VOOR_GEMEENTE = 4;

    private final ConversietabelFactory conversieTabelFactory;
    private final GbaRubriekNaarBrpTypeVertaler gbaRubriekNaarBrpTypeVertaler;

    /**
     * Maakt nieuwe voorwaarderegel aan.
     * @param conversieTabelFactory conversie tabel factory
     * @param gbaRubriekNaarBrpTypeVertaler rubriekvertaler
     */
    @Inject
    public GemeenteVoorwaardeRegel(final ConversietabelFactory conversieTabelFactory, final GbaRubriekNaarBrpTypeVertaler gbaRubriekNaarBrpTypeVertaler) {
        super(VOLGORDE, REGEX_PATROON);
        this.conversieTabelFactory = conversieTabelFactory;
        this.gbaRubriekNaarBrpTypeVertaler = gbaRubriekNaarBrpTypeVertaler;
    }

    @Override
    public Expressie getBrpExpressie(final RubriekWaarde voorwaardeRegel) throws GbaVoorwaardeOnvertaalbaarExceptie {
        try {
            return maakExpressie(voorwaardeRegel.getLo3Expressie());
        } catch (GbaRubriekOnbekendExceptie gbaRubriekOnbekendExceptie) {
            throw new GbaVoorwaardeOnvertaalbaarExceptie("Een rubriek kan niet worden vertaald", gbaRubriekOnbekendExceptie);
        }
    }

    private Expressie maakExpressie(final String lo3Expressie) throws GbaRubriekOnbekendExceptie, GbaVoorwaardeOnvertaalbaarExceptie {
        final String[] delen = lo3Expressie.split(GbaVoorwaardeConstanten.SPLIT_CHARACTER, GbaVoorwaardeConstanten.DEEL_AANTAL);
        final BrpType[] gedefinieerdeBrpTypen = gbaRubriekNaarBrpTypeVertaler.vertaalGbaRubriekNaarBrpType(delen[GbaVoorwaardeConstanten.DEEL_RUBRIEK]);
        final List<Expressie> expressies = new ArrayList<>();
        final List<BrpType[]> brpTypenLijst = new ArrayList<>();
        for (int x = 0; x < gedefinieerdeBrpTypen.length; x = x + AANTAL_ELEMENTEN_VOOR_GEMEENTE) {
            brpTypenLijst.add(Arrays.copyOfRange(gedefinieerdeBrpTypen, x, x + AANTAL_ELEMENTEN_VOOR_GEMEENTE));
        }
        for (final BrpType[] brpTypen : brpTypenLijst) {
            if (delen[GbaVoorwaardeConstanten.DEEL_REST].matches("\\d{2}\\.\\d{2}\\.\\d{2}")) {
                expressies.add(maakExpressieOverigMetRubrieken(delen, brpTypen));
            } else {
                expressies.add(maakExpressieOverigMetWaarden(delen, brpTypen));
            }
        }
        final Expressie result;
        if (expressies.size() > 1) {
            result = new OfWaarde(expressies.toArray(new Expressie[expressies.size()]));
        } else {
            result = expressies.get(0);
        }
        return result;
    }

    private Expressie maakExpressieOverigMetRubrieken(final String[] delen, final BrpType[] brpTypen)
            throws GbaRubriekOnbekendExceptie, GbaVoorwaardeOnvertaalbaarExceptie {
        final BrpType[] brpTypenWaarden = gbaRubriekNaarBrpTypeVertaler.vertaalGbaRubriekNaarBrpType(delen[GbaVoorwaardeConstanten.DEEL_REST]);
        final List<Expressie> expressies = new ArrayList<>(3);
        final Operator operator = bepaalOperator(delen[GbaVoorwaardeConstanten.DEEL_OPERATOR]);

        maakDeelExpressies(brpTypen, brpTypenWaarden, expressies, operator);
        return expressies.get(GEMEENTE_CODE);
    }

    private Expressie maakExpressieOverigMetWaarden(final String[] delen, final BrpType[] brpTypen) throws GbaVoorwaardeOnvertaalbaarExceptie {
        final Expressie resultaat;
        final Operator operator = bepaalOperator(delen[GbaVoorwaardeConstanten.DEEL_OPERATOR]);
        final Lo3GemeenteCode gemeenteCode = new Lo3GemeenteCode(delen[GbaVoorwaardeConstanten.DEEL_REST]);
        if (gemeenteCode.isValideNederlandseGemeenteCode()) {
            String gemeenteCodeAlsString = String.valueOf(conversieTabelFactory.createGemeenteConversietabel().converteerNaarBrp(gemeenteCode).getWaarde());
            resultaat = new OfWaarde(new ElementWaarde(new Criterium(brpTypen[BUITENLANDSE_PLAATS].getType(), operator, gemeenteCodeAlsString)),
                    new ElementWaarde(new Criterium(brpTypen[OMSCHRIJVING_LOCATIE].getType(), operator, gemeenteCodeAlsString)),
                    new ElementWaarde(new Criterium(brpTypen[WOONPLAATSNAAM].getType(), operator, gemeenteCodeAlsString)), new ElementWaarde(
                    new Criterium(brpTypen[GEMEENTE_CODE].getType(), operator, gemeenteCodeAlsString)));
        } else {
            resultaat = new OfWaarde(new ElementWaarde(new Criterium(brpTypen[BUITENLANDSE_PLAATS].getType(), operator, gemeenteCode.getWaarde())),
                    new ElementWaarde(new Criterium(brpTypen[OMSCHRIJVING_LOCATIE].getType(), operator, gemeenteCode.getWaarde())),
                    new ElementWaarde(new Criterium(brpTypen[WOONPLAATSNAAM].getType(), operator, gemeenteCode.getWaarde())));
        }
        return resultaat;
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
}
