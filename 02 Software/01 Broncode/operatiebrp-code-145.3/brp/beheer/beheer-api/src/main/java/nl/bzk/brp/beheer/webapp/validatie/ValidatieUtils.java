/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.validatie;

import java.time.LocalDate;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import org.springframework.validation.Errors;

/**
 * Validatie utils.
 */
public final class ValidatieUtils {

    private static final Logger LOG = LoggerFactory.getLogger();
    private static final String VALIDEER_DATUM_LIGT_NA_DATUM_EERSTE_DATUM_TWEEDE_DATUM = "Datum {} moet na datum {} liggen";
    private static final String VALIDEER_DATUM_LIGT_OP_OF_NA_DATUM_EERSTE_DATUM_TWEEDE_DATUM = "Datum {} moet op of na datum {} liggen";
    private static final String VELD_MOETLATERZIJN = "veld.moetlaterzijn";
    private static final String VELD_MOETGELIJKOFLATERZIJN = "veld.moetgelijkoflaterzijn";
    private static final String VALIDEER_DATUM_BEVAT_GEEN_ONBEKENDE_DELEN = "Valideer datum {} is niet deels onbekend.";
    private static final String VELD_MAGNIETDEELSONBEKENDZIJN = "veld.magnietdeelsonbekendzijn";
    private static final String PATROON_ONBEKEND_JAAR = "0000";
    private static final String PATROON_ONBEKEND_MAAND_OF_DAG = "00";
    private static final int START_INDEX_JAAR = 0;
    private static final int START_INDEX_MAAND = 4;
    private static final int START_INDEX_DAG = 6;
    private static final int EIND_INDEX_JAAR = 4;
    private static final int EIND_INDEX_MAAND = 6;

    private ValidatieUtils() {
        // Niet instantieerbaar
    }

    /**
     * Voer een validatie uit.
     * @param errors errors object
     * @param validatie true als validatie is geslaagd, anders false
     * @param veld veld code (voor errors object)
     * @param foutcode fout code (voor errors object)
     * @param argumenten argumenten (voor errors object)
     */
    public static void valideer(final Errors errors, final boolean validatie, final String veld, final String foutcode, final String... argumenten) {
        if (!validatie) {
            if (veld == null || "".equals(veld)) {
                errors.reject(foutcode, argumenten, foutcode);
            } else {
                errors.rejectValue(veld, foutcode, argumenten, foutcode);
            }
        }
    }

    /**
     * Valideer dat een waarde gevuld is (niet null).
     * @param errors errors object
     * @param waarde te controleren waarde
     * @param veld veld code (voor errors object)
     */
    public static void valideerVerplichtVeld(final Errors errors, final Object waarde, final String veld) {
        valideer(errors, waarde != null && !"".equals(waarde), veld, "veld.verplicht", veld);
    }

    /**
     * Valideer of te valideren datum op of na de huidige datum ligt.
     * @param errors validatie error map
     * @param teValiderenDatum datum welke gevalideerd moet worden
     * @param teValiderenDatumVeld naam van het veld, moet overeenkomen met property binnen object
     */
    public static void valideerDatumLigtNaHuidigeDatum(final Errors errors, final Integer teValiderenDatum, final String teValiderenDatumVeld) {
        valideerDatumLigtNaHuidigeDatum(errors, teValiderenDatum, teValiderenDatumVeld, teValiderenDatumVeld);
    }

    /**
     * Valideer of te valideren datum op of na de huidige datum ligt.
     * @param errors validatie error map
     * @param teValiderenDatum datum welke gevalideerd moet worden
     * @param teValiderenDatumVeld naam van het veld, moet overeenkomen met property binnen object
     * @param teValiderenDatumVeldNaam naam om te tonen in berichten
     */
    public static void valideerDatumLigtNaHuidigeDatum(final Errors errors, final Integer teValiderenDatum, final String teValiderenDatumVeld,
                                                       final String teValiderenDatumVeldNaam) {
        final Integer huidigeDatum = DatumUtil.vanDatumNaarInteger(LocalDate.now());
        LOG.info(VALIDEER_DATUM_LIGT_OP_OF_NA_DATUM_EERSTE_DATUM_TWEEDE_DATUM, teValiderenDatum, huidigeDatum);
        if (teValiderenDatum != null) {
            valideer(
                    errors,
                    vulOnbekendeDelenEindDatumAan(teValiderenDatum) > huidigeDatum,
                    teValiderenDatumVeld,
                    VELD_MOETLATERZIJN,
                    "Vandaag",
                    teValiderenDatumVeldNaam);
        }
    }

    /**
     * Valideer of te valideren datum op of na de huidige datum ligt.
     * @param errors validatie error map
     * @param teValiderenDatum datum welke gevalideerd moet worden
     * @param teValiderenDatumVeld naam van het veld
     */
    public static void valideerDatumLigtOpOfNaHuidigeDatum(final Errors errors, final Integer teValiderenDatum, final String teValiderenDatumVeld) {
        final Integer huidigeDatum = DatumUtil.vanDatumNaarInteger(LocalDate.now());
        LOG.info(VALIDEER_DATUM_LIGT_OP_OF_NA_DATUM_EERSTE_DATUM_TWEEDE_DATUM, teValiderenDatum, huidigeDatum);
        if (teValiderenDatum != null) {
            valideer(
                    errors,
                    vulOnbekendeDelenEindDatumAan(teValiderenDatum) >= huidigeDatum,
                    teValiderenDatumVeld,
                    VELD_MOETGELIJKOFLATERZIJN,
                    "Vandaag",
                    teValiderenDatumVeld);
        }
    }

    /**
     * Valideer dat een datum (tweedeDatum) na een andere datum (eersteDatum) ligt. Indien een van de datums niet gevuld
     * is, wordt de validatie niet uitgevoerd.
     * @param errors errors object
     * @param eersteDatum eerste datum
     * @param tweedeDatum tweede datum (die na de eerste datum moet liggen)
     * @param veldEersteDatum veld code (voor errors object)
     * @param veldTweedeDatum veld code (voor errors object)
     */
    public static void valideerDatumLigtNaDatum(
            final Errors errors,
            final Integer eersteDatum,
            final Integer tweedeDatum,
            final String veldEersteDatum,
            final String veldTweedeDatum) {
        if (eersteDatum != null && tweedeDatum != null) {
            LOG.info(VALIDEER_DATUM_LIGT_NA_DATUM_EERSTE_DATUM_TWEEDE_DATUM, tweedeDatum, eersteDatum);
            valideer(
                    errors,
                    vulOnbekendeDelenEindDatumAan(tweedeDatum) > eersteDatum,
                    veldTweedeDatum,
                    VELD_MOETLATERZIJN,
                    veldEersteDatum,
                    veldTweedeDatum);
        }
    }

    /**
     * Valideer dat een datum (tweedeDatum) na een andere datum (eersteDatum) ligt. Indien een van de datums niet gevuld
     * is, wordt de validatie niet uitgevoerd.
     * @param errors errors object
     * @param eersteDatum eerste datum
     * @param tweedeDatum tweede datum (die na de eerste datum moet liggen)
     * @param veldEersteDatum veld code (voor errors object)
     * @param veldTweedeDatum veld code (voor errors object)
     */
    public static void valideerDatumLigtOpOfNaDatum(
            final Errors errors,
            final Integer eersteDatum,
            final Integer tweedeDatum,
            final String veldEersteDatum,
            final String veldTweedeDatum) {
        if (eersteDatum != null && tweedeDatum != null) {
            LOG.info(VALIDEER_DATUM_LIGT_NA_DATUM_EERSTE_DATUM_TWEEDE_DATUM, tweedeDatum, eersteDatum);
            valideer(
                    errors,
                    vulOnbekendeDelenEindDatumAan(tweedeDatum) >= eersteDatum,
                    veldTweedeDatum,
                    VELD_MOETGELIJKOFLATERZIJN,
                    veldEersteDatum,
                    veldTweedeDatum);
        }
    }

    /**
     * Bepaalt of de meegegeven datum geen deels onbekende datumdelen bevat.
     * @param errors errors object
     * @param veldDatum veld code (voor errors object)
     * @param datumInteger De te controleren datum
     */
    public static void valideerGeenDeelsOnbekendeDelen(final Errors errors, final Integer datumInteger, final String veldDatum) {

        if (datumInteger != null) {
            // Valideer datum is niet 0
            boolean result = datumInteger != 0;
            final String datum = String.format("%08d", datumInteger);
            // Valideer jaar is niet 0000
            result &= !datum.substring(START_INDEX_JAAR, EIND_INDEX_JAAR).contains(PATROON_ONBEKEND_JAAR);
            // Valideer maand is niet 00
            result &= !datum.substring(START_INDEX_MAAND, EIND_INDEX_MAAND).contains(PATROON_ONBEKEND_MAAND_OF_DAG);
            // Valideer dag is niet 00
            result &= !datum.substring(START_INDEX_DAG).contains(PATROON_ONBEKEND_MAAND_OF_DAG);
            LOG.info(VALIDEER_DATUM_BEVAT_GEEN_ONBEKENDE_DELEN, datumInteger);
            valideer(errors, result, veldDatum, VELD_MAGNIETDEELSONBEKENDZIJN, veldDatum);
        }

    }

    private static int vulOnbekendeDelenEindDatumAan(final Integer datumInteger) {
        assert datumInteger != null;
        String datum = String.valueOf(datumInteger);
        datum = datum.replaceAll("^0$", "99999999").replaceAll("0000$", "9999").replaceAll("00$", "99");
        return Integer.parseInt(datum);
    }
}
