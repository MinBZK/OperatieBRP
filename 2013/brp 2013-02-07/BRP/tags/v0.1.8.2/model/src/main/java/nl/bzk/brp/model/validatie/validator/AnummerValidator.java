/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.validatie.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import nl.bzk.brp.model.attribuuttype.Administratienummer;
import nl.bzk.brp.model.validatie.constraint.Anummer;
import org.apache.commons.lang.StringUtils;


/**
 * Administratienummer voorschrift.
 *
 * Als de cijfers van het A-nummer van links naar rechts worden aangegeven met a[0] t/m a[9], dan gelden de volgende
 * condities:
 * 1) a[0] <> 0
 * 2) a[i] <> a[i+1]
 * 3) a[0]+a[1]+...+a[9] is deelbaar door 11
 * of a[0]+a[1]+...+a[9]+6 is deelbaar door 11
 * 4) (1*a[0])+(2*a[1])+(4*a[2])+...+(512*a[9]) is deelbaar door 11
 *
 *
 * @brp.bedrijfsregel BRAL0001
 */
public class AnummerValidator implements ConstraintValidator<Anummer, Administratienummer> {

    private static final int ANUMMER_LENGTE    = 10;
    private static final int ELF = 11;
    private static final int ZES = 6;

    @Override
    public void initialize(final Anummer constraintAnnotation) {
        // Niets om te initialiseren
    }

    @Override
    public boolean isValid(final Administratienummer aNummer, final ConstraintValidatorContext context) {
        boolean resultaat;

        if (aNummer == null || aNummer.getWaarde() == null) {
            resultaat = true;
        } else {
            resultaat = isGeldigeAnummer(aNummer);
        }

        return resultaat;
    }

    /**
     * Controlleert of de waarde een geldige BSN nummer is.
     *
     * @param aNummer de bsn
     * @return true als het een geldige bsn
     */
    private boolean isGeldigeAnummer(final Administratienummer aNummer) {
        boolean resultaat;
        final String waarde = aNummer.getWaarde().toString();
        if (waarde.length() == ANUMMER_LENGTE && StringUtils.isNumeric(waarde)) {
            resultaat =
                voldoetAanRegel1(waarde) && voldoetAanRegel2(waarde) && voldoetAanRegel3(waarde)
                    && voldoetAanRegel4(waarde);
        } else {
            resultaat = false;
        }

        return resultaat;
    }

    /**
     * 1) a[0] <> 0.
     *
     * @param waarde de A-nummer
     *
     * @return true als aan de regel voldoet
     */
    private boolean voldoetAanRegel1(final String waarde) {
        return (Character.getNumericValue(waarde.charAt(0)) != 0);
    }

    /**
     * 2) a[i] <> a[i+1].
     *
     * @param waarde de A-nummer
     *
     * @return true als aan de regel voldoet
     */
    private boolean voldoetAanRegel2(final String waarde) {
        boolean resultaat = true;

        for (int i = 0; i < waarde.length() - 1; i++) {
            if (Character.getNumericValue(waarde.charAt(i)) == Character.getNumericValue(waarde.charAt(i + 1))) {
                resultaat = false;
                break;
            }
        }

        return resultaat;
    }

    /**
     * 3) a[0]+a[1]+...+a[9] is deelbaar door 11 of a[0]+a[1]+...+a[9]+6 is deelbaar door 11.
     *
     * @param waarde de A-nummer
     *
     * @return true als aan de regel voldoet
     */
    private boolean voldoetAanRegel3(final String waarde) {
        boolean resultaat;

        int som = 0;

        // a[0]+a[1]+...+a[9] is deelbaar door 11
        for (int i = 0; i < waarde.length(); i++) {
            som += (Character.getNumericValue(waarde.charAt(i)));
        }

        if (som % ELF == 0) {
            resultaat = true;
        } else {
            som += ZES;

            if (som % ELF == 0) {
                resultaat = true;
            } else {
                resultaat = false;
            }
        }

        return resultaat;
    }

    /**
     * 4) (1*a[0])+(2*a[1])+(4*a[2])+...+(512*a[9]) is deelbaar door 11.
     *
     * @param waarde de A-nummer
     *
     * @return true als aan de regel voldoet
     */
    private boolean voldoetAanRegel4(final String waarde) {
        boolean resultaat;

        int som = 0;
        int factor = 1;
        for (int i = 0; i < waarde.length(); i++) {
            som += (Character.getNumericValue(waarde.charAt(i)) * factor);

            factor *= 2;
        }

        if (som % ELF == 0) {
            resultaat = true;
        } else {
            resultaat = false;
        }

        return resultaat;
    }
}
