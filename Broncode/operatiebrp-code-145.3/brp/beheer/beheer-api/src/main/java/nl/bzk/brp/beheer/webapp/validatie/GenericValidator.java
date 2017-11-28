/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.validatie;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Afleidbaar;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Generieke validator voor bedrijfsregels die gelden over meerdere stamgegevens.
 * @param <T> Type van het stamgegeven
 */
public class GenericValidator<T extends Afleidbaar> implements Validator {

    private static final Logger LOG = LoggerFactory.getLogger();

    private static final String VELD_DATUM_INGANG = "datumIngang";
    private static final String VELD_DATUM_EINDE = "datumEinde";
    private static final String GET_METHODE_DATUM_INGANG = "getDatumIngang";
    private static final String GET_METHODE_DATUM_EINDE = "getDatumEinde";
    private Class<T> genericType;

    /**
     * Default constructor.
     * @param genericType het type van de class
     */
    public GenericValidator(final Class<T> genericType) {
        this.genericType = genericType;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return genericType.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        final T typedObject = (T) target;

        // Controleer op datumIngang/datumEinde.
        if (haalMethodeOpUitObject(typedObject, GET_METHODE_DATUM_INGANG, null) != null
                && haalMethodeOpUitObject(typedObject, GET_METHODE_DATUM_EINDE, null) != null) {
            // Voer datum validatie uit
            Method methodDag = haalMethodeOpUitObject(typedObject, GET_METHODE_DATUM_INGANG, null);
            Method methodDeg = haalMethodeOpUitObject(typedObject, GET_METHODE_DATUM_EINDE, null);
            if (methodDag != null && methodDeg != null) {
                Integer datumIngang = invokeMethodOnObject(typedObject, methodDag, new Object[]{});
                Integer datumEinde = invokeMethodOnObject(typedObject, methodDeg, null);
                validateDatumvelden(datumIngang, datumEinde, errors);
            }
        }
        // Voer .. validatie uit
        // overige generieke validaties.
    }

    private Method haalMethodeOpUitObject(final T typedObject, final String methodName, final Class<?>... parameterTypes) {
        try {
            return typedObject.getClass().getMethod(methodName, parameterTypes);
        } catch (NoSuchMethodException e) {
            // Geen datum validatie
            LOG.debug("Class {} bevat geen {} methode", typedObject.getClass().toString(), methodName);
            return null;
        }
    }

    private void validateDatumvelden(final Integer datumIngang, final Integer datumEinde, final Errors errors) {
        ValidatieUtils.valideerVerplichtVeld(errors, datumIngang, VELD_DATUM_INGANG);
        ValidatieUtils.valideerGeenDeelsOnbekendeDelen(errors, datumIngang, VELD_DATUM_INGANG);
        ValidatieUtils.valideerGeenDeelsOnbekendeDelen(errors, datumEinde, VELD_DATUM_EINDE);
        ValidatieUtils.valideerDatumLigtNaDatum(errors, datumIngang, datumEinde, VELD_DATUM_INGANG, VELD_DATUM_EINDE);
    }

    private <R> R invokeMethodOnObject(final Object invokerObject, final Method method, final Object... invokerArguments) {
        try {
            return (R) method.invoke(invokerObject, invokerArguments);
        } catch (IllegalAccessException | InvocationTargetException e) {
            LOG.error("Exceptie bij invoken methode {} op {}: {}", method.getName(), invokerObject.getClass().toString(), e);
            return null;
        }
    }
}
