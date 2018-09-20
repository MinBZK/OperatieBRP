/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.validatie.validator;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import nl.bzk.brp.model.validatie.constraint.Datum;
import org.apache.commons.lang.StringUtils;


/**
 * Burgerservicenummer voorschrift.
 *
 * Indien datum (datum formaat wordt aangeduid 'jjjjmmdd') deels onbekend is dan:
 * indien dag onbekend is dan moet voor dag 'dd' de waarde 0 ingevuld zijn;
 * indien maand onbekend is dan moet voor zowel dag 'dd' als maand 'mm' 0 ingevuld zijn;
 * indien jaar onbekend is dan moet zowel dag 'dd', maand 'mm' als jaar 'jjjj' met nullen gevuld zijn.
 *
 *
 * @brp.bedrijfsregel BRAL0102
 */
public class DatumValidator implements ConstraintValidator<Datum, Integer> {

    private static final int    DATUM_LENGTE              = 8;
    private static final int    DATUM_MIN                 = 0;
    private static final int    MAAND_MAX                 = 12;
    private static final int    DAG_MAX                   = 31;

    private static final int    MAAND_INDEX_VAN           = 4;
    private static final int    MAAND_INDEX_TOT           = 6;
    private static final int    DAG_INDEX_VAN             = 6;
    private static final int    DAG_INDEX_TOT             = 8;

    private static final int    MOD_LAATSTE_2_CIJFERS_NUL = 100;
    private static final String DATUM_FORMAAT             = "yyyyMMdd";

    @Override
    public void initialize(final Datum constraintAnnotation) {
        // Niets om te initialiseren
    }

    @Override
    public boolean isValid(final Integer waarde, final ConstraintValidatorContext context) {
        boolean resultaat;

        if (waarde == null || waarde == 0) {
            resultaat = true;
        } else {
            resultaat = isGeldigeDatum(waarde);
        }

        return resultaat;
    }

    /**
     * Controlleert of de waarde een geldige datum is (met evt. onbekende maan/dag).
     *
     * @param datum de datum
     * @return true als het een geldige datum
     */
    private boolean isGeldigeDatum(final Integer datum) {
        boolean resultaat;

        if ((datum % MOD_LAATSTE_2_CIJFERS_NUL) != 0) {
            // als laatste 2 cijfers zijn geen '00', dan moet het een
            // volledig datum zijn.
            resultaat = isGeldigeEchteDatum(datum);
        } else {
            String waarde = datum.toString();

            if (waarde.length() == DATUM_LENGTE && StringUtils.isNumeric(waarde)) {
                int maand = Integer.parseInt(waarde.substring(MAAND_INDEX_VAN, MAAND_INDEX_TOT));
                int dag = Integer.parseInt(waarde.substring(DAG_INDEX_VAN, DAG_INDEX_TOT));

                // Geldige dag 0-31
                resultaat = (dag >= DATUM_MIN && dag <= DAG_MAX);

                // Geldige maand 0-12
                resultaat = resultaat && (maand >= DATUM_MIN && maand <= MAAND_MAX);

                // Als maand 0 is dan moet dag ook 0 zijn
                resultaat = resultaat && !(maand == DATUM_MIN && dag > DATUM_MIN);
            } else {
                resultaat = false;
            }
        }

        return resultaat;
    }

    /**
     * Controlleert of de waarde een echt geldige kalender datum is .
     *
     * @param datum de datum
     * @return true als het een geldige datum
     */
    private boolean isGeldigeEchteDatum(final Integer datum) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATUM_FORMAAT);
        // 32 jan. wordt NIET 1 feb.
        sdf.setLenient(false);
        try {
            sdf.parse(datum.toString());
            return true;
        } catch (ParseException ex) {
            // log de fout als ongedig datum (print de invoerwaarde ?)
            return false;
        }
    }

}
