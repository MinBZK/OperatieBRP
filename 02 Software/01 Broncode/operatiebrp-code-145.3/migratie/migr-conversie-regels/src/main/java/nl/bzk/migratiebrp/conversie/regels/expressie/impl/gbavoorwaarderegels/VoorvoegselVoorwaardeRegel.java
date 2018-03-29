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
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.VoorvoegselScheidingstekenPaar;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.factory.ConversietabelFactory;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3String;
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
import nl.bzk.migratiebrp.conversie.regels.expressie.impl.criteria.OfWaarde;
import nl.bzk.migratiebrp.conversie.regels.expressie.impl.criteria.OngelijkAlleOperator;
import nl.bzk.migratiebrp.conversie.regels.expressie.impl.criteria.OngelijkEenOperator;
import nl.bzk.migratiebrp.conversie.regels.expressie.impl.criteria.Operator;
import org.springframework.stereotype.Component;

/**
 * Conversie van het voorvoegsel naar brp expressie.
 */
@Component
public final class VoorvoegselVoorwaardeRegel extends AbstractGbaVoorwaardeRegel {

    /**
     * Regex expressie voor selectie van voorwaarderegels die door deze class worden behandeld.
     */
    private static final String REGEX_PATROON = "(01|02|03|05|09|51|52|53|55|59)\\.02\\.30.*";
    private static final int VOLGORDE = 500;
    private static final int AANTAL_ELEMENTEN_VOOR_VOORVOEGSEL = 2;
    private static final String SCHEIDINGSTEKEN_FORMAAT = "\"%s\"";
    private final ConversietabelFactory conversieTabelFactory;
    private final GbaRubriekNaarBrpTypeVertaler gbaRubriekNaarBrpTypeVertaler;

    /**
     * Maakt nieuwe voorwaarderegel aan.
     * @param conversieTabelFactory conversie tabel factory
     * @param gbaRubriekNaarBrpTypeVertaler rubriekvertaler
     */
    @Inject
    public VoorvoegselVoorwaardeRegel(
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
            result = maakExpressieVanLo3Expressie(voorwaardeRegel.getLo3Expressie());
        } catch (final GbaRubriekOnbekendExceptie groe) {
            throw new GbaVoorwaardeOnvertaalbaarExceptie(voorwaardeRegel.getLo3Expressie(), groe);
        }
        return result;
    }

    private Expressie maakExpressieVanLo3Expressie(final String lo3Expressie) throws GbaRubriekOnbekendExceptie, GbaVoorwaardeOnvertaalbaarExceptie {
        final String[] delen = lo3Expressie.split(GbaVoorwaardeConstanten.SPLIT_CHARACTER, GbaVoorwaardeConstanten.DEEL_AANTAL);
        final BrpType[] gedefinieerdeBrpTypen = gbaRubriekNaarBrpTypeVertaler.vertaalGbaRubriekNaarBrpType(delen[GbaVoorwaardeConstanten.DEEL_RUBRIEK]);
        final List<Expressie> expressies = new ArrayList<>();
        final List<BrpType[]> brpTypenLijst = new ArrayList<>();
        for (int x = 0; x < gedefinieerdeBrpTypen.length; x = x + AANTAL_ELEMENTEN_VOOR_VOORVOEGSEL) {
            brpTypenLijst.add(Arrays.copyOfRange(gedefinieerdeBrpTypen, x, x + AANTAL_ELEMENTEN_VOOR_VOORVOEGSEL));
        }
        for (final BrpType[] brpTypen : brpTypenLijst) {
            if (delen[GbaVoorwaardeConstanten.DEEL_REST].matches("\\d{2}\\.\\d{2}\\.\\d{2}")) {
                expressies.add(maakExpressieMetRubrieken(delen, brpTypen));
            } else {
                expressies.add(maakExpressieMetWaarden(delen, brpTypen));
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

    private Expressie maakExpressieMetRubrieken(final String[] delen, final BrpType[] brpTypen)
            throws GbaVoorwaardeOnvertaalbaarExceptie, GbaRubriekOnbekendExceptie {
        final Expressie resultaat;
        final BrpType[] brpTypenWaarden = gbaRubriekNaarBrpTypeVertaler.vertaalGbaRubriekNaarBrpType(delen[GbaVoorwaardeConstanten.DEEL_REST]);
        final List<Expressie> expressies = new ArrayList<>(2);
        switch (delen[GbaVoorwaardeConstanten.DEEL_OPERATOR]) {
            case "OGAA":
                maakDeelExpressies(brpTypen, brpTypenWaarden, expressies, new OngelijkAlleOperator());
                resultaat = new OfWaarde(expressies.toArray(new Expressie[expressies.size()]));
                break;
            case "OGA1":
                maakDeelExpressies(brpTypen, brpTypenWaarden, expressies, new OngelijkEenOperator());
                resultaat = new OfWaarde(expressies.toArray(new Expressie[expressies.size()]));
                break;
            case "GA1":
                maakDeelExpressies(brpTypen, brpTypenWaarden, expressies, new GelijkEenOperator());
                resultaat = new EnWaarde(expressies.toArray(new Expressie[expressies.size()]));
                break;
            default:
                throw new GbaVoorwaardeOnvertaalbaarExceptie("Operator wordt niet ondersteund");
        }
        return resultaat;
    }

    private Expressie maakExpressieMetWaarden(final String[] delen, final BrpType[] brpTypen) throws GbaVoorwaardeOnvertaalbaarExceptie {
        final Expressie resultaat;
        final VoorvoegselScheidingstekenPaar voorvoegselScheidingstekenPaar =
                conversieTabelFactory.createVoorvoegselScheidingstekenConversietabel()
                        .converteerNaarBrp(new Lo3String(delen[GbaVoorwaardeConstanten.DEEL_REST]));
        switch (delen[GbaVoorwaardeConstanten.DEEL_OPERATOR]) {
            case "OGAA":
                resultaat = new OfWaarde(new ElementWaarde(
                                new Criterium(brpTypen[0].getType(), new OngelijkAlleOperator(), voorvoegselScheidingstekenPaar.getVoorvoegsel().getWaarde())),
                                new ElementWaarde(new Criterium(brpTypen[1].getType(), new OngelijkAlleOperator(),
                                        String.format(SCHEIDINGSTEKEN_FORMAAT, voorvoegselScheidingstekenPaar.getScheidingsteken().getWaarde().toString()))));
                break;
            case "OGA1":
                resultaat = new OfWaarde(new ElementWaarde(
                                new Criterium(brpTypen[0].getType(), new OngelijkEenOperator(), voorvoegselScheidingstekenPaar.getVoorvoegsel().getWaarde())),
                                new ElementWaarde(new Criterium(brpTypen[1].getType(), new OngelijkEenOperator(),
                                        String.format(SCHEIDINGSTEKEN_FORMAAT, voorvoegselScheidingstekenPaar.getScheidingsteken().getWaarde().toString()))));
                break;
            case "GA1":
                resultaat = new EnWaarde(new ElementWaarde(
                                new Criterium(brpTypen[0].getType(), new GelijkEenOperator(), voorvoegselScheidingstekenPaar.getVoorvoegsel().getWaarde())),
                                new ElementWaarde(new Criterium(brpTypen[1].getType(), new GelijkEenOperator(),
                                        String.format(SCHEIDINGSTEKEN_FORMAAT, voorvoegselScheidingstekenPaar.getScheidingsteken().getWaarde().toString()))));
                break;
            default:
                throw new GbaVoorwaardeOnvertaalbaarExceptie("Operator wordt niet ondersteund");
        }

        return resultaat;
    }

    private void maakDeelExpressies(final BrpType[] brpTypen, final BrpType[] brpTypenWaarden, final List<Expressie> expressies, final Operator operator) {
        for (int x = 0; x < brpTypen.length; x++) {
            if (brpTypenWaarden[x].isLijst()) {
                expressies.add(new ElementWaarde(new Criterium(brpTypenWaarden[x].getType(), operator, brpTypen[x].getType())));
            } else {
                expressies.add(new ElementWaarde(new Criterium(brpTypen[x].getType(), operator, brpTypenWaarden[x].getType())));
            }
        }
    }
}
