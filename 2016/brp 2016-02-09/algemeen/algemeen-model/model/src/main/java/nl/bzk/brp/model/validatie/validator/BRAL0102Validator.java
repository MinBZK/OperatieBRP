/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.validatie.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.validatie.constraint.BRAL0102;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;


/**
 * Datum deels onbekend voorschrift.
 * <p/>
 * Indien datum (datum formaat wordt aangeduid 'jjjjmmdd') deels onbekend is dan: indien dag onbekend is dan moet voor dag 'dd' de waarde 0 ingevuld zijn;
 * indien maand onbekend is dan moet voor zowel dag 'dd' als maand 'mm' 0 ingevuld zijn; indien jaar onbekend is dan moet zowel dag 'dd', maand 'mm' als
 * jaar 'jjjj' met nullen gevuld zijn.
 *
 * @brp.bedrijfsregel BRAL0102
 */
public class BRAL0102Validator implements ConstraintValidator<BRAL0102, DatumEvtDeelsOnbekendAttribuut> {

    /**
     * Logger voor informeren naar log.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger();

    /**
     * De maximale integer waarde voor een maand.
     */
    private static final int MAAND_MAX = 12;

    /**
     * De maximale integer waarde voor een dag.
     */
    private static final int DAG_MAX = 31;

    /**
     * Rekenkundige waarde voor laatste twee cijfers die nul zijn.
     */
    private static final int MOD_LAATSTE_2_CIJFERS_NUL = 100;

    @Override
    public final void initialize(final BRAL0102 constraintAnnotation) {
        // Niets om te initialiseren.
    }

    @Override
    public final boolean isValid(final DatumEvtDeelsOnbekendAttribuut datum, final ConstraintValidatorContext context) {
        final boolean resultaat;
        resultaat = datum == null || isGeldigeDeelsOnbekendeDatum(datum);
        if (!resultaat) {
            LOGGER.debug("Datum is ongeldig voor deels onbekend [" + datum + "]");
        }
        return resultaat;
    }

    /**
     * Controleert of de waarde een geldige datum is (met evt. onbekende maand/dag).
     *
     * @param datum de datum
     * @return true als het een geldige datum is
     */
    private boolean isGeldigeDeelsOnbekendeDatum(final DatumEvtDeelsOnbekendAttribuut datum) {
        boolean resultaat = true;
        // We gaan ervanuit dat de xsd controleert dat de (textuele) lengte van de datum 8 is inclusief eventuele
        // voorloop nullen.

        final int jaar = datum.getWaarde() / (MOD_LAATSTE_2_CIJFERS_NUL * MOD_LAATSTE_2_CIJFERS_NUL);
        final int leftover = datum.getWaarde() - jaar * MOD_LAATSTE_2_CIJFERS_NUL * MOD_LAATSTE_2_CIJFERS_NUL;
        final int maand = leftover / MOD_LAATSTE_2_CIJFERS_NUL;
        final int dag = leftover % MOD_LAATSTE_2_CIJFERS_NUL;

        // Als een van de velden 0 dan is het deels onbekend
        if (jaar == 0 || maand == 0 || dag == 0) {
            if ((maand == 0 && dag != 0) || (jaar == 0 && maand != 0)) {
                resultaat = false;
            } else {
                resultaat = maand <= MAAND_MAX && dag <= DAG_MAX;
            }
        }

        return resultaat;
    }
}
