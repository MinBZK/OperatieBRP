/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.expressie.impl.gbavoorwaarderegels;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.migratiebrp.conversie.regels.expressie.impl.BrpType;
import nl.bzk.migratiebrp.conversie.regels.expressie.impl.GbaRubriekNaarBrpTypeVertaler;
import nl.bzk.migratiebrp.conversie.regels.expressie.impl.GbaRubriekOnbekendExceptie;
import nl.bzk.migratiebrp.conversie.regels.expressie.impl.GbaVoorwaardeOnvertaalbaarExceptie;
import nl.bzk.migratiebrp.conversie.regels.expressie.impl.RubriekWaarde;
import nl.bzk.migratiebrp.conversie.regels.expressie.impl.criteria.EnWaarde;
import nl.bzk.migratiebrp.conversie.regels.expressie.impl.criteria.Expressie;
import nl.bzk.migratiebrp.conversie.regels.expressie.impl.criteria.OfWaarde;

/**
 * Implementatie van de standaardregel. Aangezien binnen de standaardconversie ook een aantal waarden van de rubrieken
 * door middel van conversietabellen moet worden vertaald, geeft deze util een extentiepunt om de individuele waarde
 * eventueel te vertalen door een conversietabel. Dit extentiepunt wordt doormiddel van een functionele interface
 * doorgegeven.
 */
public class StandaardVoorwaardeRegelUtil {

    private static final String OPERATOR_ONGELIJK_AAN_ALLE = "OGAA";
    private static final String OPERATOR_ONGELIJK_AAN_EEN = "OGA1";
    private static final String OPERATOR_GELIJK_AAN_EEN = "GA1";

    private final RubriekVertaler standaardVertaler;
    private final RubriekVertaler vraagVertaler;

    /**
     * Maakt nieuwe Standaard verwerker aan.
     * @param gbaRubriekNaarBrpTypeVertaler rubriekvertaler
     * @param vertaalWaardeVanRubriek vertaler van de waarde van de rubriek naar BRP
     */
    public StandaardVoorwaardeRegelUtil(
            final GbaRubriekNaarBrpTypeVertaler gbaRubriekNaarBrpTypeVertaler,
            final VertaalWaardeVanRubriek vertaalWaardeVanRubriek) {
       this(gbaRubriekNaarBrpTypeVertaler, vertaalWaardeVanRubriek, vertaalWaardeVanRubriek);
    }

    /**
     * Maakt nieuwe Standaard verwerker aan.
     * @param gbaRubriekNaarBrpTypeVertaler rubriekvertaler
     * @param vertaalWaardeVanRubriek vertaler van de waarde van de rubriek naar BRP
     * @param vraagVertaalWaardeVanRubriek vertaler van de waarde van de rubriek naar BRP speciaal voor vragen
     */
    public StandaardVoorwaardeRegelUtil(
            final GbaRubriekNaarBrpTypeVertaler gbaRubriekNaarBrpTypeVertaler,
            final VertaalWaardeVanRubriek vertaalWaardeVanRubriek,
            final VertaalWaardeVanRubriek vraagVertaalWaardeVanRubriek) {
        this.standaardVertaler = new RubriekVertaler(gbaRubriekNaarBrpTypeVertaler, vertaalWaardeVanRubriek);
        this.vraagVertaler = new RubriekVertaler(gbaRubriekNaarBrpTypeVertaler, vraagVertaalWaardeVanRubriek);
    }

    /**
     * Standaard vertaling van voorwaarde regel naar expressie.
     * @param voorwaardeRegel te vertalen voorwaarde regel
     * @return brp expressie
     * @throws GbaVoorwaardeOnvertaalbaarExceptie indien het mis gaat
     */
    public final Expressie getBrpExpressie(final RubriekWaarde voorwaardeRegel) throws GbaVoorwaardeOnvertaalbaarExceptie {
        final String[] delen = voorwaardeRegel.getLo3Expressie().split(GbaVoorwaardeConstanten.SPLIT_CHARACTER, GbaVoorwaardeConstanten.DEEL_AANTAL);
        final Expressie result;
        final RubriekVertaler vertaler;
        if (voorwaardeRegel.isVoorVragen()) {
            vertaler = vraagVertaler;
        } else {
            vertaler = standaardVertaler;
        }
        try {
            final BrpType[] brpTypen = vertaler.vertaalGbaRubriekNaarBrpType(delen[GbaVoorwaardeConstanten.DEEL_RUBRIEK]);
            final boolean
                    verzameling =
                    delen[GbaVoorwaardeConstanten.DEEL_REST].contains("OFVGL") || delen[GbaVoorwaardeConstanten.DEEL_REST].contains("ENVGL");
            if (brpTypen.length == 1) {
                result = GbaVoorwaardeFactory.geefVertaler(delen, brpTypen[0], verzameling, vertaler).verwerk();
            } else {
                final List<Expressie> expressies = maakDeelExpressies(delen, brpTypen, verzameling, vertaler);
                result = voegDeelExpressiesSamen(voorwaardeRegel, delen[GbaVoorwaardeConstanten.DEEL_OPERATOR], expressies);
            }
        } catch (final GbaRubriekOnbekendExceptie groe) {
            throw new GbaVoorwaardeOnvertaalbaarExceptie(voorwaardeRegel.getLo3Expressie(), groe);
        }
        return result;
    }

    private List<Expressie> maakDeelExpressies(final String[] delen, final BrpType[] brpTypen, final boolean verzameling, final RubriekVertaler vertaler)
            throws GbaVoorwaardeOnvertaalbaarExceptie {
        final List<Expressie> expressies = new ArrayList<>();
        for (final BrpType brpType : brpTypen) {
            if (brpType.isInverse()) {
                final Expressie vorigeExpressie = expressies.remove(expressies.size() - 1);
                expressies.add(new EnWaarde(vorigeExpressie, GbaVoorwaardeFactory.geefVertaler(delen, brpType, verzameling, vertaler).verwerk()));
            } else {
                expressies.add(GbaVoorwaardeFactory.geefVertaler(delen, brpType, verzameling, vertaler).verwerk());
            }
        }
        return expressies;
    }

    private Expressie voegDeelExpressiesSamen(final RubriekWaarde voorwaardeRegel, final String s, final List<Expressie> expressies)
            throws GbaVoorwaardeOnvertaalbaarExceptie {
        final Expressie result;
        switch (s) {
            case OPERATOR_GELIJK_AAN_EEN:
                result = new OfWaarde(expressies.toArray(new Expressie[expressies.size()]));
                break;
            case OPERATOR_ONGELIJK_AAN_EEN:
            case OPERATOR_ONGELIJK_AAN_ALLE:
                result = new EnWaarde(expressies.toArray(new Expressie[expressies.size()]));
                break;
            default:
                throw new GbaVoorwaardeOnvertaalbaarExceptie(voorwaardeRegel.getLo3Expressie());
        }
        return result;
    }
}
