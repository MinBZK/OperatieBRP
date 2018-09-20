/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.lo3.groep;

import java.lang.reflect.Field;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Documentatie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Historie;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3CategorieInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.element.AbstractLo3Element;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Onderzoek;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Elementnummer;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3GroepEnum;

/**
 * Util klasse om bv te controleren of een bepaalde groep aanwezig is in een categorie.
 */
public final class Lo3GroepUtil {

    private Lo3GroepUtil() {

    }

    /**
     * Controleer of de opgegeven groep aanwezig is in de opgegeven categorie inhoud.
     * 
     * @param <T>
     *            de mogelijke inhoud classes
     * @param groep
     *            De groep waar op gecontroleerd wordt of deze aanwezig is
     * @param inhoud
     *            de categorie waarin gecontroleerd moet worden of de opgegeven groep aanwezig is
     * @return true als minimaal 1 element van de opgegeven groep aanwezig is.
     */
    public static <T extends Lo3CategorieInhoud> boolean isGroepAanwezig(final Lo3GroepEnum groep, final T inhoud) {
        final Field[] fields = inhoud.getClass().getDeclaredFields();
        return checkElementenAanwezig(groep, fields, inhoud);
    }

    /**
     * Controleer of de opgegeven groep aanwezig is in de opgegeven historie.
     * 
     * @param groep
     *            De groep waar op gecontroleerd wordt of deze aanwezig is
     * @param historie
     *            de historie waarin gecontroleerd moet worden of de opgegeven groep aanwezig is
     * @return true als minimaal 1 element van de opgegeven groep aanwezig is.
     */
    public static boolean isGroepAanwezig(final Lo3GroepEnum groep, final Lo3Historie historie) {
        boolean result = false;
        if (historie != null) {
            final Field[] fields = historie.getClass().getDeclaredFields();
            result = checkElementenAanwezig(groep, fields, historie);
        }
        return result;
    }

    /**
     * Controleer of de opgegeven groep aanwezig is in de opgegeven documentatie.
     * 
     * @param groep
     *            De groep waar op gecontroleerd wordt of deze aanwezig is
     * @param documentatie
     *            de documentatie waarin gecontroleerd moet worden of de opgegeven groep aanwezig is
     * @return true als minimaal 1 element van de opgegeven groep aanwezig is.
     */
    public static boolean isGroepAanwezig(final Lo3GroepEnum groep, final Lo3Documentatie documentatie) {
        boolean result = false;
        if (documentatie != null) {
            final Field[] fields = documentatie.getClass().getDeclaredFields();
            result = checkElementenAanwezig(groep, fields, documentatie);
        }
        return result;
    }

    /**
     * Controleer of de opgegeven groep aanwezig is in de opgegeven onderzoek.
     * 
     * @param groep
     *            De groep waar op gecontroleerd wordt of deze aanwezig is
     * @param onderzoek
     *            de onderzoek waarin gecontroleerd moet worden of de opgegeven groep aanwezig is
     * @return true als minimaal 1 element van de opgegeven groep aanwezig is.
     */
    public static boolean isGroepAanwezig(final Lo3GroepEnum groep, final Lo3Onderzoek onderzoek) {
        boolean result = false;
        if (onderzoek != null) {
            final Field[] fields = onderzoek.getClass().getDeclaredFields();
            result = checkElementenAanwezig(groep, fields, onderzoek);
        }
        return result;
    }

    private static boolean checkElementenAanwezig(final Lo3GroepEnum groep, final Field[] fields, final Object object) {
        boolean elementGevuldAanwezig = false;

        for (final Field field : fields) {
            final Lo3Elementnummer elementAnnotation = field.getAnnotation(Lo3Elementnummer.class);
            if (elementAnnotation != null && elementAnnotation.value().getGroep().equals(groep)) {
                field.setAccessible(true);
                try {
                    final Object value = field.get(object);
                    if (value != null
                        && (value instanceof AbstractLo3Element && ((AbstractLo3Element) value).isInhoudelijkGevuld() || !(value instanceof AbstractLo3Element)))
                    {
                        elementGevuldAanwezig = true;
                        break;
                    }
                } catch (final IllegalAccessException iae) {
                    throw new IllegalArgumentException(iae);
                }
            }
        }
        return elementGevuldAanwezig;
    }
}
