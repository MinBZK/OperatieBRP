/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.validatie.validator;

import java.text.ParseException;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import nl.bzk.brp.model.validatie.constraint.Datum;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Burgerservicenummer voorschrift.
 * <p/>
 * Indien datum (datum formaat wordt aangeduid 'jjjjmmdd') deels onbekend is
 * dan: indien dag onbekend is dan moet voor dag 'dd' de waarde 0 ingevuld zijn;
 * indien maand onbekend is dan moet voor zowel dag 'dd' als maand 'mm' 0
 * ingevuld zijn; indien jaar onbekend is dan moet zowel dag 'dd', maand 'mm'
 * als jaar 'jjjj' met nullen gevuld zijn.
 *
 * @brp.bedrijfsregel BRAL0102
 */
public class DatumValidator implements ConstraintValidator<Datum, nl.bzk.brp.model.attribuuttype.Datum> {

    private static final int    DATUM_LENGTE              = 8;
    private static final int    MAAND_MAX                 = 12;
    private static final int    MOD_LAATSTE_2_CIJFERS_NUL = 100;
    private static final String DATUM_FORMAAT             = "yyyyMMdd";
    private static final Logger LOGGER                    = LoggerFactory.getLogger(DatumValidator.class);
    private boolean magDeelsOnbekendZijn;

    @Override
    public void initialize(final Datum constraintAnnotation) {
        // Niets om te initialiseren
        magDeelsOnbekendZijn = constraintAnnotation.magDeelsOnbekendZijn();
    }

    @Override
    public boolean isValid(final nl.bzk.brp.model.attribuuttype.Datum datum,
        final ConstraintValidatorContext context)
    {
        boolean resultaat;
        if (datum == null) {
            resultaat = true;
        } else {
            resultaat = isGeldigeDatum(datum);
        }
        if (!resultaat) {
            LOGGER.debug("Datum is ongeldig voor [" + datum + "]");
        }
        return resultaat;
    }

    /**
     * Controlleert of de waarde een geldige datum is (met evt. onbekende
     * maan/dag).
     *
     * @param datum de datum
     * @return true als het een geldige datum
     */
    private boolean isGeldigeDatum(final nl.bzk.brp.model.attribuuttype.Datum datum) {
        boolean resultaat = false;

        // we gaan vanuit dat de xsd voor gezorgd heeft dat de (textuele) lente 8 cijfers waren.
        // dus met voorloop nullen.
        // De waarde moeten we nu aanvullen met voorloop nullen.
        String waarde = StringUtils.leftPad(datum.getWaarde().toString(), DATUM_LENGTE, '0');

        // test dat er 8 cijfers in staan; kan geen vorloop nullen bevatten omdat we van Integer zijn begonnen.
        if ((datum.getWaarde() % MOD_LAATSTE_2_CIJFERS_NUL) != 0) {
            // als laatste 2 cijfers zijn geen '00', dan moet het een volledig datum zijn.
            // hiermee is ook 20120015 gedekt omdat geldige maand begint met '01'
            resultaat = isGeldigeEchteDatum(waarde);
        } else {
            if (!magDeelsOnbekendZijn) {
                resultaat = false;
            } else {
                int jaar = (datum.getWaarde() / (MOD_LAATSTE_2_CIJFERS_NUL * MOD_LAATSTE_2_CIJFERS_NUL));
                int leftover = (datum.getWaarde() - (jaar * MOD_LAATSTE_2_CIJFERS_NUL * MOD_LAATSTE_2_CIJFERS_NUL));
                int maand = (leftover / MOD_LAATSTE_2_CIJFERS_NUL);

                if (jaar == 0 && maand == 0) {
                    //Een onbekende datum in zijn geheel is prima
                    resultaat = true;
                } else {
                    // Als we hier komen, dan weten we dat de dag gelijk is aan 00.
                    // toegestaan 20120000, 20120100 ... ,20121200
                    // minimale eis: jaar moet > 0 zijn,

                    // Geldige maand 0-12
                    resultaat = (maand <= MAAND_MAX);

                }
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
    private boolean isGeldigeEchteDatum(final String datum) {
        try {
            // 32 jan. wordt dan NIET 1 feb.
            DateUtils.parseDateStrictly(datum, new String[]{ DATUM_FORMAAT });
            return true;
        } catch (ParseException ex) {
            // log de fout als ongedig datum (print de invoerwaarde ?)
            return false;
        }
    }

}
