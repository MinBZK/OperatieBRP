/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.uc307;

import java.util.Map;

import nl.moderniseringgba.isc.esb.message.brp.generated.FoutCode;
import nl.moderniseringgba.isc.esb.message.brp.generated.StatusType;
import nl.moderniseringgba.isc.esb.message.brp.impl.MvGeboorteAntwoordBericht;
import nl.moderniseringgba.isc.jbpm.spring.SpringDecision;
import nl.moderniseringgba.migratie.logging.Logger;
import nl.moderniseringgba.migratie.logging.LoggerFactory;

import org.springframework.stereotype.Component;

//@formatter:off
/**
 * Controleert of de status van het ontvangen antwoord op het MvGeboorteVerzoek OK is.
 * 
 *      geen antwoord (null) --> "Fout"
 *      StatusType.OK --> null;
 *      StatusType.WAARSCHUWING --> "Fout"
 *      StatusType.FOUT --> 
 *          FoutCode.A --> "12c. PL bestaat reeds"
 *          FoutCode.G --> "12d. Identificatie persoon (moeder) niet gevonden"
 *          FoutCode.U --> "12e. Identificatie persoon (moeder) niet uniek"
 *          FoutCode.B --> "12f. Identificatie persoon (moeder) gemarkeerd 'geblokkeerd'"
 *          FoutCode.O --> "12g. Identificatie persoon (moeder) gemarkeerd als opgeschort 'overleden'"
 *          FoutCode.E --> "12h. Identificatie persoon (moeder) gemarkeerd als opgeschort 'geëmigreerd'"
 *          FoutCode.M --> "12i. Identificatie persoon (moeder) gemarkeerd als opgeschort 'ministerieel besluit'"
 *      StatusType.FOUT_GEBLOKKEERD --> "Fout"
 */
//@formatter:on
@Component("uc307ControleerMVGeboorteAntwoordDecision")
public final class ControleerMVGeboorteAntwoordDecision implements SpringDecision {

    /**
     * Naam van de transitie in geval van algemene 'Fout'.
     */
    public static final String FOUT = "Fout";

    /**
     * Naam van de transitie indien de PL reeds bestaat.
     */
    public static final String FOUT_CODE_A = "12c. PL bestaat reeds";

    /**
     * Naam van de transitie indien de identificatie persoon (moeder) niet gevonden is.
     */
    public static final String FOUT_CODE_G = "12d. Identificatie persoon (moeder) niet gevonden";

    /**
     * Naam van de transitie indien de identificatie persoon (moeder) niet uniek is.
     */
    public static final String FOUT_CODE_U = "12e. Identificatie persoon (moeder) niet uniek";

    /**
     * Naam van de transitie indien de identificatie persoon (moeder) als 'geblokkeerd' gemarkeerd is.
     */
    public static final String FOUT_CODE_B = "12f. Identificatie persoon (moeder) gemarkeerd 'geblokkeerd'";

    /**
     * Naam van de transitie indien de identificatie persoon (moeder) als 'overleden' is opgeschort.
     */
    public static final String FOUT_CODE_O =
            "12g. Identificatie persoon (moeder) gemarkeerd als opgeschort 'overleden'";

    /**
     * Naam van de transitie indien de identificatie persoon (moeder) als 'geëmigreerd' is opgeschort.
     */
    public static final String FOUT_CODE_E =
            "12h. Identificatie persoon (moeder) gemarkeerd als opgeschort 'geëmigreerd'";

    /**
     * Naam van de transitie indien de identificatie persoon (moeder) als 'ministerieel besluit' is opgeschort.
     */
    public static final String FOUT_CODE_M =
            "12i. Identificatie persoon (moeder) gemarkeerd als opgeschort 'ministerieel besluit'";

    /**
     * Naam van de transitie indien de foutcode niet herkend wordt.
     */
    public static final String FOUT_CODE_OVERIG = "12k. Overige reden van afwijzing";

    private static final Logger LOG = LoggerFactory.getLogger();

    // CHECKSTYLE:OFF Door het aantal mogelijke foutcode komt de cyclomatic complexity te hoog uit (>10).
    @Override
    public String execute(final Map<String, Object> parameters) {
        // CHECKSTYLE:ON
        LOG.info("execute(parameters={})", parameters);

        final MvGeboorteAntwoordBericht geboorteAntwoord = (MvGeboorteAntwoordBericht) parameters.get("brpBericht");
        LOG.info("geboorteAntwoord: {}", geboorteAntwoord);

        final String transitie;

        if (geboorteAntwoord == null || geboorteAntwoord.getStatus() == null
                || StatusType.WAARSCHUWING.equals(geboorteAntwoord.getStatus())) {
            transitie = FOUT;
        } else if (StatusType.OK.equals(geboorteAntwoord.getStatus())) {
            transitie = null;
        } else {
            if (FoutCode.A.equals(geboorteAntwoord.getFoutCode())) {
                transitie = FOUT_CODE_A;
            } else if (FoutCode.G.equals(geboorteAntwoord.getFoutCode())) {
                transitie = FOUT_CODE_G;
            } else if (FoutCode.U.equals(geboorteAntwoord.getFoutCode())) {
                transitie = FOUT_CODE_U;
            } else if (FoutCode.B.equals(geboorteAntwoord.getFoutCode())) {
                transitie = FOUT_CODE_B;
            } else if (FoutCode.O.equals(geboorteAntwoord.getFoutCode())) {
                transitie = FOUT_CODE_O;
            } else if (FoutCode.E.equals(geboorteAntwoord.getFoutCode())) {
                transitie = FOUT_CODE_E;
            } else if (FoutCode.M.equals(geboorteAntwoord.getFoutCode())) {
                transitie = FOUT_CODE_M;
            } else {
                transitie = FOUT_CODE_OVERIG;
            }
        }

        return transitie;
    }
}
