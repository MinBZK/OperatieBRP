/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.expressie.impl.gbavoorwaarderegels;

import nl.bzk.migratiebrp.conversie.regels.expressie.impl.BrpType;
import nl.bzk.migratiebrp.conversie.regels.expressie.impl.GbaRubriekOnbekendExceptie;
import nl.bzk.migratiebrp.conversie.regels.expressie.impl.GbaVoorwaardeOnvertaalbaarExceptie;

/**
 * Voorwaarde regel voor indicaties.
 */
public abstract class AbstractIndicatieVoorwaardeRegel extends AbstractGbaVoorwaardeRegel {

    private static final String REGEX_PATROON = "^%s.*";
    private static final int VOLGORDE = 500;

    private static final String HEEFT_INDICATIE_WEL = "NIET IS_NULL(%s)";
    private static final String HEEFT_INDICATIE_NIET = "IS_NULL(%s)";

    /**
     * Maakt de voorwaarde regel.
     *
     * @param rubriek
     *            rubrieknummer
     */
    protected AbstractIndicatieVoorwaardeRegel(final String rubriek) {
        super(VOLGORDE, String.format(REGEX_PATROON, rubriek.replaceAll("\\.", "\\\\.")));
    }

    @Override
    public final String getBrpExpressie(final String voorwaardeRegel) throws GbaVoorwaardeOnvertaalbaarExceptie {
        final String[] delen = voorwaardeRegel.split(SPLIT_CHARACTER);
        final boolean indicatieWaarde;
        try {
            indicatieWaarde = vertaalIndicatieVanWaardes(delen);
        } catch (final InconsistenteIndicatieWaardenException e) {
            throw new GbaVoorwaardeOnvertaalbaarExceptie(voorwaardeRegel, e);
        }

        final BrpType brpType;
        try {
            brpType = getGbaRubriekNaarBrpTypeVertaler().vertaalGbaRubriekNaarBrpType(delen[DEEL_RUBRIEK])[0];
        } catch (final GbaRubriekOnbekendExceptie e) {
            throw new GbaVoorwaardeOnvertaalbaarExceptie(voorwaardeRegel, e);
        }

        final String result;
        switch (delen[DEEL_OPERATOR] + indicatieWaarde) {
            case "GA1false":
            case "GAAfalse":
            case "OGA1true":
            case "OGAAtrue":
                result = String.format(HEEFT_INDICATIE_NIET, brpType.getType());
                break;
            case "GA1true":
            case "GAAtrue":
            case "OGA1false":
            case "OGAAfalse":
                result = String.format(HEEFT_INDICATIE_WEL, brpType.getType());
                break;
            default:
                throw new GbaVoorwaardeOnvertaalbaarExceptie(voorwaardeRegel);
        }

        return result;
    }

    /**
     * Bepaalt of de indicatie wel of niet moet voorkomen obv de rubriek waarden.
     *
     * @param delen
     *            voorwaarde delen
     * @return true, als de indicator moet voorkomen, anders false
     * @throws InconsistenteIndicatieWaardenException
     *             als de rubriek waarden niet consistent zijn (bv indicatie geheim 0 ENVGL 7)
     */
    private boolean vertaalIndicatieVanWaardes(final String[] delen) throws InconsistenteIndicatieWaardenException {
        Boolean resultaat = null;
        for (int index = 2; index < delen.length; index = index + 2) {
            final Boolean deelResultaat = vertaalIndicatieVanWaarde(delen[index]);
            if (resultaat == null) {
                resultaat = deelResultaat;
            } else if (!resultaat.equals(deelResultaat)) {
                throw new InconsistenteIndicatieWaardenException("Indicatie waarden inconsistent (resulteren in beide WAAR en ONWAAR in BRP termen)");
            }
        }
        return resultaat;
    }

    /**
     * Extentie punt om de waarde van een indicatie te vertalen.
     *
     * @param ruweWaarde
     *            de te vertalen rubriekwaarde
     * @return de vertaalde rubriekwaarde als boolean
     */
    protected abstract Boolean vertaalIndicatieVanWaarde(final String ruweWaarde);

    /**
     * Exception om aan te geven dat een lijst met indicatie waarden niet consistent is.
     */
    public static final class InconsistenteIndicatieWaardenException extends Exception {

        private static final long serialVersionUID = 1L;

        /**
         * Constructor.
         *
         * @param message
         *            melding
         */
        public InconsistenteIndicatieWaardenException(final String message) {
            super(message);
        }

    }
}
