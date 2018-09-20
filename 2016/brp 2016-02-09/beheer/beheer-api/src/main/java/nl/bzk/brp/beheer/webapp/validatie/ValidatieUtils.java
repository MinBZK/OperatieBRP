/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.validatie;

import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.basis.Attribuut;
import org.springframework.validation.Errors;

/**
 * Validatie utils.
 */
public final class ValidatieUtils {

    private static final Logger LOG = LoggerFactory.getLogger();
    private static final String VALIDEER_DATUM_LIGT_NA_DATUM_EERSTE_DATUM_TWEEDE_DATUM = "Valideer datum ligt na datum(eersteDatum={},tweedeDatum={})";
    private static final String VELD_MOETLATERZIJN = "veld.moetlaterzijn";

    private ValidatieUtils() {
        // Niet instantieerbaar
    }

    /**
     * Voer een validatie uit.
     *
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
     *
     * @param errors errors object
     * @param waarde te controleren waarde
     * @param veld veld code (voor errors object)
     */
    public static void valideerVerplichtVeld(final Errors errors, final Object waarde, final String veld) {
        valideer(errors, waarde != null && !"".equals(waarde), veld, "veld.verplicht", veld);
    }

    /**
     * Geef de waarde van een attribuut.
     *
     * @param attribuut attribuut
     * @param <T> attribuut waarde type
     * @return attribuut waarde, of null als attribuut null is
     */
    public static <T> T getAttribuutWaarde(final Attribuut<T> attribuut) {
        return attribuut == null ? null : attribuut.getWaarde();
    }

    /**
     * Valideer dat een attribuut gevuld is (niet null).
     *
     * @param errors errors object
     * @param attribuut te controleren attribuut
     * @param veld veld code (voor errors object)
     */
    public static void valideerVerplichtVeldAttribuut(final Errors errors, final Attribuut<?> attribuut, final String veld) {
        valideerVerplichtVeld(errors, getAttribuutWaarde(attribuut), veld);
    }

    /**
     * Valideer dat een datum (tweedeDatum) na een andere datum (eersteDatum) ligt. Indien een van de datums niet gevuld
     * is, wordt de validatie niet uitgevoerd.
     *
     * @param errors errors object
     * @param eersteDatum eerste datum
     * @param tweedeDatum tweede datum (die na de eerste datum moet liggen)
     * @param veldEersteDatum veld code (voor errors object)
     * @param veldTweedeDatum veld code (voor errors object)
     */
    public static void valideerDatumLigtNaDatum(
        final Errors errors,
        final DatumEvtDeelsOnbekendAttribuut eersteDatum,
        final DatumEvtDeelsOnbekendAttribuut tweedeDatum,
        final String veldEersteDatum,
        final String veldTweedeDatum)
    {
        final Integer eersteDatumInteger = getAttribuutWaarde(eersteDatum);
        final Integer tweedeDatumInteger = getAttribuutWaarde(tweedeDatum);

        if (eersteDatum != null && tweedeDatum != null) {
            LOG.info(VALIDEER_DATUM_LIGT_NA_DATUM_EERSTE_DATUM_TWEEDE_DATUM, eersteDatumInteger, tweedeDatumInteger);
            valideer(
                errors,
                vulOnbekendeDelenEindDatumAan(tweedeDatumInteger) > eersteDatumInteger,
                veldTweedeDatum,
                    VELD_MOETLATERZIJN,
                veldEersteDatum,
                veldTweedeDatum);
        }
    }

    /**
     * Valideer dat een datum (tweedeDatum) na een andere datum (eersteDatum) ligt. Indien een van de datums niet gevuld
     * is, wordt de validatie niet uitgevoerd.
     *
     * @param errors errors object
     * @param eersteDatum eerste datum
     * @param tweedeDatum tweede datum (die na de eerste datum moet liggen)
     * @param veldEersteDatum veld code (voor errors object)
     * @param veldTweedeDatum veld code (voor errors object)
     */
    public static void valideerDatumLigtNaDatum(
            final Errors errors,
            final DatumAttribuut eersteDatum,
            final DatumAttribuut tweedeDatum,
            final String veldEersteDatum,
            final String veldTweedeDatum)
    {
        final Integer eersteDatumInteger = getAttribuutWaarde(eersteDatum);
        final Integer tweedeDatumInteger = getAttribuutWaarde(tweedeDatum);

        if (eersteDatum != null && tweedeDatum != null) {
            LOG.info(VALIDEER_DATUM_LIGT_NA_DATUM_EERSTE_DATUM_TWEEDE_DATUM, eersteDatumInteger, tweedeDatumInteger);
            valideer(
                    errors,
                    vulOnbekendeDelenEindDatumAan(tweedeDatumInteger) > eersteDatumInteger,
                    veldTweedeDatum,
                    VELD_MOETLATERZIJN,
                    veldEersteDatum,
                    veldTweedeDatum);
        }
    }

    private static int vulOnbekendeDelenEindDatumAan(final Integer datumInteger) {
        assert datumInteger != null;
        String datum = String.valueOf(datumInteger);
        datum = datum.replaceAll("^0$", "99999999").replaceAll("0000$", "9999").replaceAll("00$", "99");
        return Integer.parseInt(datum);
    }
}
