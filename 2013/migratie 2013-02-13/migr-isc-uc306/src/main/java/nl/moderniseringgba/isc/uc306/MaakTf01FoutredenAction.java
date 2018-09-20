/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.uc306;

import java.util.HashMap;
import java.util.Map;

import nl.moderniseringgba.isc.esb.message.lo3.impl.Tf01Bericht;
import nl.moderniseringgba.isc.jbpm.spring.SpringAction;
import nl.moderniseringgba.migratie.logging.Logger;
import nl.moderniseringgba.migratie.logging.LoggerFactory;

import org.springframework.stereotype.Component;

/**
 * 
 * Logt op basis van de foutreden in het Tf01-bericht een melding.
 * 
 */
@Component("uc306MaakTf01FoutredenAction")
public final class MaakTf01FoutredenAction implements SpringAction {

    /** Foutreden. */
    public static final String FOUT_REDEN = "Tf01 bericht ontvangen met foutcode: %s  - %s";
    /** Tf01Foutreden variabele. */
    public static final String TF01_FOUT_REDEN = "Tf01Foutreden";

    /** Reden emigratie. */
    public static final String REDEN_EMIGRATIE = "Emigratie";
    /** Reden ministrieel besluit. */
    public static final String REDEN_MIN_BESLUIT = "Ministrieel besluit";
    /** Reden overleden. */
    public static final String REDEN_OVERLEDEN = "Overleden";
    /** Reden verhuisd. */
    public static final String REDEN_VERHUISD = "Verhuisd";
    /** Reden al aanwezig. */
    public static final String REDEN_AL_AANWEZIG = "Al aanwezig";
    /** Reden geblokkeerd. */
    public static final String REDEN_GEBLOKKEERD = "Geblokkeerd";
    /** Reden niet gevonden. */
    public static final String REDEN_NIET_GEVONDEN = "Niet gevonden";
    /** Reden niet uniek. */
    public static final String REDEN_NIET_UNIEK = "Niet uniek";

    private static final Logger LOG = LoggerFactory.getLogger();

    @Override
    public Map<String, Object> execute(final Map<String, Object> parameters) {
        LOG.info("execute(parameters={})", parameters);

        final Map<String, Object> result = new HashMap<String, Object>();

        final Tf01Bericht tf01Bericht = (Tf01Bericht) parameters.get("tf01Bericht");

        final Tf01Bericht.Foutreden foutreden = tf01Bericht.getFoutreden();

        switch (foutreden) {
            case E:
                result.put(TF01_FOUT_REDEN, String.format(FOUT_REDEN, foutreden, REDEN_EMIGRATIE));
                break;
            case M:
                result.put(TF01_FOUT_REDEN, String.format(FOUT_REDEN, foutreden, REDEN_MIN_BESLUIT));
                break;
            case O:
                result.put(TF01_FOUT_REDEN, String.format(FOUT_REDEN, foutreden, REDEN_OVERLEDEN));
                break;
            case V:
                result.put(TF01_FOUT_REDEN, String.format(FOUT_REDEN, foutreden, REDEN_VERHUISD));
                break;
            case A:
                result.put(TF01_FOUT_REDEN, String.format(FOUT_REDEN, foutreden, REDEN_AL_AANWEZIG));
                break;
            case B:
                result.put(TF01_FOUT_REDEN, String.format(FOUT_REDEN, foutreden, REDEN_GEBLOKKEERD));
                break;
            case G:
                result.put(TF01_FOUT_REDEN, String.format(FOUT_REDEN, foutreden, REDEN_NIET_GEVONDEN));
                break;
            case U:
                result.put(TF01_FOUT_REDEN, String.format(FOUT_REDEN, foutreden, REDEN_NIET_UNIEK));
                break;
            default:
                LOG.error("Ongeldige foutreden meegegeven in Tf01 bericht.");
        }

        return result;
    }
}
