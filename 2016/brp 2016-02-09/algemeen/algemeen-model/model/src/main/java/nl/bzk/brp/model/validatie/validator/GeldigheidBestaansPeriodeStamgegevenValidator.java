/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.validatie.validator;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.basis.BestaansPeriodeStamgegeven;
import nl.bzk.brp.model.basis.DatumBasisAttribuut;
import nl.bzk.brp.model.validatie.constraint.GeldigheidBestaansPeriodeStamgegeven;
import org.springframework.util.ReflectionUtils;


/**
 * Valideert de geldigheid van stamgegevens van het type BestaansPeriodeStamgegeven.
 */
public final class GeldigheidBestaansPeriodeStamgegevenValidator implements ConstraintValidator<GeldigheidBestaansPeriodeStamgegeven, Object> {

    /**
     * Maximale datum.
     */
    private static final int MAX_DATUM = 99999999;

    /**
     * Minimale datum.
     */
    private static final int MIN_DATUM = 0;

    private String peilDatumVeld;
    private String bestaansPeriodeStamgegevenVeld;
    private Regel  regel;

    @Override
    public void initialize(final GeldigheidBestaansPeriodeStamgegeven constraintAnnotation) {
        peilDatumVeld = constraintAnnotation.peilDatumVeld();
        bestaansPeriodeStamgegevenVeld = constraintAnnotation.bestaansPeriodeStamgegevenVeld();
        regel = constraintAnnotation.code();
    }

    @Override
    public boolean isValid(final Object waarde, final ConstraintValidatorContext context) {
        final boolean isValide;

        final Object peilDatum;
        final Object bestaansPeriodeStamgegeven;
        try {
            peilDatum = ValidatorUtil.haalWaardeOp(waarde, this.peilDatumVeld);
            bestaansPeriodeStamgegeven = ValidatorUtil.haalWaardeOp(waarde, this.bestaansPeriodeStamgegevenVeld);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new IllegalArgumentException(e);
        }

        if (peilDatum == null || bestaansPeriodeStamgegeven == null) {
            isValide = true;
        } else {
            if (peilDatum instanceof DatumBasisAttribuut && bestaansPeriodeStamgegeven instanceof BestaansPeriodeStamgegeven) {
                if (!isBestaansPeriodeStamgegevenGeldigOp((BestaansPeriodeStamgegeven) bestaansPeriodeStamgegeven, (DatumBasisAttribuut) peilDatum)) {
                    final String melding =
                        String.format(regel.getOmschrijving(), haalCodeOp(bestaansPeriodeStamgegeven),
                            ((DatumBasisAttribuut) peilDatum).getWaarde());

                    context.disableDefaultConstraintViolation();
                    context.buildConstraintViolationWithTemplate(melding).addConstraintViolation();
                    isValide = false;
                } else {
                    isValide = true;
                }
            } else {
                throw new IllegalArgumentException("peilDatumVeld is niet van het type Datum of "
                    + "bestaansPeriodeStamgegevenVeld is niet van het type BestaansPeriodeStamgegeven.");
            }
        }
        return isValide;
    }

    /**
     * Controleert of het bestaans periode stamgegeven geldig is op een bepaalde peildatum.
     *
     * @param bestaansPeriodeStamgegeven het bestaans periode stamgegeven
     * @param peilDatum de peil datum
     * @return true als geldig, anders false.
     */
    private boolean isBestaansPeriodeStamgegevenGeldigOp(final BestaansPeriodeStamgegeven bestaansPeriodeStamgegeven,
                                                         final DatumBasisAttribuut peilDatum)
    {
        final int intDatumAanvangGeldigheid;
        if (bestaansPeriodeStamgegeven.getDatumAanvangGeldigheid() != null) {
            intDatumAanvangGeldigheid = bestaansPeriodeStamgegeven.getDatumAanvangGeldigheid().getIntWaardeOfMin();
        } else {
            intDatumAanvangGeldigheid = MIN_DATUM;
        }

        final int intDatumEindeGeldigheid;
        if (bestaansPeriodeStamgegeven.getDatumEindeGeldigheid() != null) {
            intDatumEindeGeldigheid = bestaansPeriodeStamgegeven.getDatumEindeGeldigheid().getIntWaardeOfMax();
        } else {
            intDatumEindeGeldigheid = MAX_DATUM;
        }

        final DatumEvtDeelsOnbekendAttribuut datumAanvangGeldigheid = new DatumEvtDeelsOnbekendAttribuut(intDatumAanvangGeldigheid);
        final DatumEvtDeelsOnbekendAttribuut datumEindeGeldigheid = new DatumEvtDeelsOnbekendAttribuut(intDatumEindeGeldigheid);

        return peilDatum.isGeldigTussen(datumAanvangGeldigheid, datumEindeGeldigheid);
    }

    /**
     * Haalt code op.
     *
     * @param bestaansPeriodeStamgegeven het bestaans periode stamgegeven
     * @return de code als string
     */
    private String haalCodeOp(final Object bestaansPeriodeStamgegeven) {
        String waarde = "";

        // Normaal gesproken heeft een stamgegeven een code
        final Field field = ReflectionUtils.findField(bestaansPeriodeStamgegeven.getClass(), "code");
        if (field != null) {
            final Object id;
            field.setAccessible(true);
            try {
                id = field.get(bestaansPeriodeStamgegeven);
            } catch (IllegalAccessException e) {
                // Zou niet kunnen gebeuren omdat er op voorhand al gecontrolleerd wordt of veld bestaat.
                throw new IllegalArgumentException("code attribuut bestaat niet", e);
            }
            waarde = id.toString();
        }

        return waarde;
    }
}
