/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.validatie.validator;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.StringTokenizer;

import nl.bzk.brp.model.attribuuttype.Datum;
import nl.bzk.brp.model.attribuuttype.DatumTijd;
import nl.bzk.brp.model.basis.AttribuutType;
import org.springframework.util.ReflectionUtils;


public class ValidatorUtil {
    /**
     * Methode om waarde op te halen waar ook gecontrolleerd wordt op null voor geneste objecten.
     * <p/>
     * Bijvoorbeeld PersoonAdres.Land.code, wanneer land null is moet er niet geprobeerd worden om de code op te halen.
     *
     * @param object het object waarin de waarde opgehaald moet worden
     * @param veld veld naar de atttribuut, dit kan een geneste attribuut zijn, gescheiden door een . in de pad
     * @return waarde of null wanneer de Object null is
     * @throws IllegalAccessException exceptie die gegooid wordt door BeanUtils.getProperty
     * @throws InvocationTargetException exceptie die gegooid wordt door BeanUtils.getProperty
     * @throws NoSuchMethodException exceptie die gegooid wordt door BeanUtils.getProperty
     */
    @SuppressWarnings("rawtypes")
    public static Object haalWaardeOp(final Object object, final String veld) throws IllegalAccessException,
        InvocationTargetException, NoSuchMethodException
    {
        Object resultaat = null;

        StringTokenizer st = new StringTokenizer(veld, ".");

        Object value = object;
        while (st.hasMoreTokens()) {
            String variabel = st.nextToken().toString();

            Field field = ReflectionUtils.findField(value.getClass(), variabel);
            if (field != null) {
                field.setAccessible(true);
                value = field.get(value);

                if (value instanceof DatumTijd || value instanceof Datum) {
                    resultaat = value;
                } else if (value instanceof AttribuutType) {
                    resultaat = ((AttribuutType) value).getWaarde();
                } else {
                    resultaat = value;

                    if (resultaat == null) {
                        //Attribuut is null, hoeft niet meer verder te navigeren
                        break;
                    }
                }
            } else {
                throw new IllegalArgumentException("veld bestaat niet: " + variabel);
            }

        }

        return resultaat;
    }
}
