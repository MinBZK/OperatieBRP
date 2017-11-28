/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.validatie;

import java.util.Optional;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.DynamischeStamtabelMetGeldigheid;
import nl.bzk.brp.beheer.webapp.repository.ReadonlyRepository;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Generieke validator voor bedrijfsregels die gelden over meerdere stamgegevens.
 * @param <T> Type van het stamgegeven
 * @param <U> Type van sleutelveld
 */
public class GenericGeldigheidStamgegevenValidator<T extends DynamischeStamtabelMetGeldigheid, U extends Number> implements Validator {

    private static final String VELD_DATUM_AANVANG_GELDIGHEID = "datumAanvangGeldigheid";
    private static final String VELD_HUIDIGE_DATUM_AANVANG_GELDIGHEID = "huidige datumAanvangGeldigheid";
    private static final String VELD_DATUM_EINDE_GELDIGHEID = "datumEindeGeldigheid";
    private static final String VELD_HUIDIGE_DATUM_EINDE_GELDIGHEID = "huidige datumEindeGeldigheid";
    private final Class<T> genericType;
    private final ReadonlyRepository<T, U> stamgegevenRepository;

    /**
     * Default constructor.
     * @param genericType het type van de class
     * @param stamgegevenRepository repository van het te valideren stamgegeven
     */
    public GenericGeldigheidStamgegevenValidator(final Class<T> genericType, final ReadonlyRepository<T, U> stamgegevenRepository) {
        this.genericType = genericType;
        this.stamgegevenRepository = stamgegevenRepository;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return genericType.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        final T typedObject = (T) target;

        // Controleer op datumAanvangGeldigheid/datumEindeGeldigheid.
        final Optional<Integer> datumAanvangGeldigheid = Optional.ofNullable(typedObject.getDatumAanvangGeldigheid());
        final Optional<Integer> datumEindeGeldigheid = Optional.ofNullable(typedObject.getDatumEindeGeldigheid());
        validateDatumvelden(datumAanvangGeldigheid.orElse(null), datumEindeGeldigheid.orElse(null), errors);
        if (!errors.hasErrors() && typedObject.getId() != null) {
            final T oudeEntiteit = stamgegevenRepository.findOne((U) typedObject.getId());
            final Optional<Integer> oudeDatumAanvangGeldigheid = Optional.ofNullable(oudeEntiteit.getDatumAanvangGeldigheid());
            if (!oudeDatumAanvangGeldigheid.equals(datumAanvangGeldigheid)) {
                ValidatieUtils.valideerDatumLigtNaHuidigeDatum(errors, oudeDatumAanvangGeldigheid.orElse(null), VELD_DATUM_AANVANG_GELDIGHEID,
                        VELD_HUIDIGE_DATUM_AANVANG_GELDIGHEID);
                ValidatieUtils.valideerDatumLigtOpOfNaHuidigeDatum(errors, datumAanvangGeldigheid.orElse(null), VELD_DATUM_AANVANG_GELDIGHEID);
            }
            final Optional<Integer> oudeDatumEindeGeldigheid = Optional.ofNullable(oudeEntiteit.getDatumEindeGeldigheid());
            if (!oudeDatumEindeGeldigheid.equals(datumEindeGeldigheid)) {
                ValidatieUtils.valideerDatumLigtNaHuidigeDatum(errors, oudeDatumEindeGeldigheid.orElse(null), VELD_DATUM_EINDE_GELDIGHEID,
                        VELD_HUIDIGE_DATUM_EINDE_GELDIGHEID);
                ValidatieUtils.valideerDatumLigtOpOfNaHuidigeDatum(errors, datumEindeGeldigheid.orElse(null), VELD_DATUM_EINDE_GELDIGHEID);
            }
        } else {
            ValidatieUtils.valideerDatumLigtOpOfNaHuidigeDatum(errors, datumAanvangGeldigheid.orElse(null), VELD_DATUM_AANVANG_GELDIGHEID);
            ValidatieUtils.valideerDatumLigtOpOfNaHuidigeDatum(errors, datumEindeGeldigheid.orElse(null), VELD_DATUM_EINDE_GELDIGHEID);
        }
    }

    private void validateDatumvelden(final Integer datumIngang, final Integer datumEinde, final Errors errors) {
        ValidatieUtils.valideerGeenDeelsOnbekendeDelen(errors, datumIngang, VELD_DATUM_AANVANG_GELDIGHEID);
        ValidatieUtils.valideerGeenDeelsOnbekendeDelen(errors, datumEinde, VELD_DATUM_EINDE_GELDIGHEID);
        ValidatieUtils.valideerDatumLigtOpOfNaDatum(errors, datumIngang, datumEinde, VELD_DATUM_AANVANG_GELDIGHEID, VELD_DATUM_EINDE_GELDIGHEID);
    }
}
