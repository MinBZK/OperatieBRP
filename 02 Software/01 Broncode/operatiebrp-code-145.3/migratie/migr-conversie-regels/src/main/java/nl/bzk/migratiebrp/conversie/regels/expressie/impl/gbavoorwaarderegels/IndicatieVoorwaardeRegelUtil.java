/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.expressie.impl.gbavoorwaarderegels;

import nl.bzk.migratiebrp.conversie.regels.expressie.impl.BrpType;
import nl.bzk.migratiebrp.conversie.regels.expressie.impl.GbaRubriekNaarBrpTypeVertaler;
import nl.bzk.migratiebrp.conversie.regels.expressie.impl.GbaRubriekOnbekendExceptie;
import nl.bzk.migratiebrp.conversie.regels.expressie.impl.GbaVoorwaardeOnvertaalbaarExceptie;
import nl.bzk.migratiebrp.conversie.regels.expressie.impl.RubriekWaarde;
import nl.bzk.migratiebrp.conversie.regels.expressie.impl.criteria.Criterium;
import nl.bzk.migratiebrp.conversie.regels.expressie.impl.criteria.ElementWaarde;
import nl.bzk.migratiebrp.conversie.regels.expressie.impl.criteria.Expressie;
import nl.bzk.migratiebrp.conversie.regels.expressie.impl.criteria.KNVOperator;
import nl.bzk.migratiebrp.conversie.regels.expressie.impl.criteria.KVOperator;

/**
 * Voorwaarde regel voor indicaties.
 */
public class IndicatieVoorwaardeRegelUtil {

    public static final int STAP = 2;
    private final VertaalIndicatieVanRubriek vertaler;

    private final GbaRubriekNaarBrpTypeVertaler gbaRubriekNaarBrpTypeVertaler;

    /**
     * Maakt nieuwe Standaard Indicatie verwerker aan.
     * @param gbaRubriekNaarBrpTypeVertaler rubriekvertaler
     * @param vertaler vertaler van de waarde van de rubriek naar BRP
     */
    public IndicatieVoorwaardeRegelUtil(final GbaRubriekNaarBrpTypeVertaler gbaRubriekNaarBrpTypeVertaler, final VertaalIndicatieVanRubriek vertaler) {
        this.gbaRubriekNaarBrpTypeVertaler = gbaRubriekNaarBrpTypeVertaler;
        this.vertaler = vertaler;
    }

    /**
     * Standaard vertaling voor expressies.
     * @param voorwaardeRegel te vertalen voorwaarde regel
     * @return brp expressie
     * @throws GbaVoorwaardeOnvertaalbaarExceptie indien er fouten optreden
     */
    public final Expressie getBrpExpressie(final RubriekWaarde voorwaardeRegel) throws GbaVoorwaardeOnvertaalbaarExceptie {
        final String[] delen = voorwaardeRegel.getLo3Expressie().split(GbaVoorwaardeConstanten.SPLIT_CHARACTER);
        final boolean indicatieWaarde = bepaalIndicatieWaarde(voorwaardeRegel, delen);
        final BrpType brpType = bepaalBrpTypen(voorwaardeRegel, delen);
        return maakBrpExpressie(voorwaardeRegel, delen[GbaVoorwaardeConstanten.DEEL_OPERATOR] + indicatieWaarde, brpType);
    }

    private boolean bepaalIndicatieWaarde(final RubriekWaarde voorwaardeRegel, final String[] delen) throws GbaVoorwaardeOnvertaalbaarExceptie {
        final boolean indicatieWaarde;
        try {
            indicatieWaarde = vertaalIndicatieVanWaardes(delen);
        } catch (final InconsistenteIndicatieWaardenException e) {
            throw new GbaVoorwaardeOnvertaalbaarExceptie(voorwaardeRegel.getLo3Expressie(), e);
        }
        return indicatieWaarde;
    }

    private BrpType bepaalBrpTypen(final RubriekWaarde voorwaardeRegel, final String[] delen) throws GbaVoorwaardeOnvertaalbaarExceptie {
        final BrpType brpType;
        try {
            brpType = gbaRubriekNaarBrpTypeVertaler.vertaalGbaRubriekNaarBrpType(delen[GbaVoorwaardeConstanten.DEEL_RUBRIEK])[0];
        } catch (final GbaRubriekOnbekendExceptie e) {
            throw new GbaVoorwaardeOnvertaalbaarExceptie(voorwaardeRegel.getLo3Expressie(), e);
        }
        return brpType;
    }

    private Expressie maakBrpExpressie(final RubriekWaarde voorwaardeRegel, final String sleutel, final BrpType brpType)
            throws GbaVoorwaardeOnvertaalbaarExceptie {
        final Expressie result;
        switch (sleutel) {
            case "GA1false":
            case "GAAfalse":
            case "OGA1true":
            case "OGAAtrue":
                result = new ElementWaarde(new Criterium(brpType.getType(), new KNVOperator(), null));
                break;
            case "GA1true":
            case "GAAtrue":
            case "OGA1false":
            case "OGAAfalse":
                result = new ElementWaarde(new Criterium(brpType.getType(), new KVOperator(), "J"));
                break;
            default:
                throw new GbaVoorwaardeOnvertaalbaarExceptie(voorwaardeRegel.getLo3Expressie());
        }
        return result;
    }

    /**
     * Bepaalt of de indicatie wel of niet moet voorkomen obv de rubriek waarden.
     * @param delen voorwaarde delen
     * @return true, als de indicator moet voorkomen, anders false
     * @throws InconsistenteIndicatieWaardenException als de rubriek waarden niet consistent zijn (bv indicatie geheim 0 ENVGL 7)
     */
    private boolean vertaalIndicatieVanWaardes(final String[] delen) throws InconsistenteIndicatieWaardenException {
        Boolean resultaat = null;
        for (int index = STAP; index < delen.length; index = index + STAP) {
            final Boolean deelResultaat = vertaler.apply(delen[index]);
            if (resultaat == null) {
                resultaat = deelResultaat;
            } else if (!resultaat.equals(deelResultaat)) {
                throw new InconsistenteIndicatieWaardenException("Indicatie waarden inconsistent (resulteren in beide WAAR en ONWAAR in BRP termen)");
            }
        }
        return resultaat;
    }

    /**
     * Exception om aan te geven dat een lijst met indicatie waarden niet consistent is.
     */
    public static final class InconsistenteIndicatieWaardenException extends Exception {

        private static final long serialVersionUID = 1L;

        /**
         * Constructor.
         * @param message melding
         */
        public InconsistenteIndicatieWaardenException(final String message) {
            super(message);
        }

    }
}
