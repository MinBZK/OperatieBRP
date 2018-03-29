/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.dal.domein.brp.entity;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import org.hibernate.proxy.HibernateProxy;

/**
 * Interface voor de entiteiten van BRP.
 */
public interface Entiteit extends Serializable {

    /**
     * Zet de waarden voor gegeven in onderzoek van DeltaEntiteit.
     *
     * @param gegevenInOnderzoek de nieuwe waarde voor gegeven in onderzoek van DeltaEntiteit
     */
    void setGegevenInOnderzoek(final GegevenInOnderzoek gegevenInOnderzoek);

    /**
     * Geef de waarde van elementen in onderzoek van DeltaEntiteit.
     *
     * @return de waarde van elementen in onderzoek van DeltaEntiteit
     */
    Collection<Element> getElementenInOnderzoek();

    /**
     * Geef de waarde van gegeven in onderzoek per element map van DeltaEntiteit.
     *
     * @return de waarde van gegeven in onderzoek per element map van DeltaEntiteit
     */
    Map<Element, GegevenInOnderzoek> getGegevenInOnderzoekPerElementMap();

    /**
     * Geef de waarde van id van DeltaEntiteit.
     *
     * @return de waarde van id van DeltaEntiteit
     */
    Number getId();

    /**
     * Verwijdert de koppeling tussen de {@link Entiteit} en {@link GegevenInOnderzoek}.
     *
     * @param gegevenInOnderzoek het element waaronder de koppeling is vastgelegd.
     */
    void verwijderGegevenInOnderzoek(Element gegevenInOnderzoek);

    /**
     * Controleert of het meegegeven object een {@link org.hibernate.proxy.HibernateProxy} object
     * is. Als dat het geval is wordt, de implementatie van de proxy opgevraagd en terug geven als
     * {@link java.lang.Object}. Als het al een POJO object is, dan wordt het object gelijk terug
     * gegeven.
     *
     * @param entiteit de entiteit die geconverteerd kan/moet worden naar een POJO
     * @param <T> het type object wat er in gaat, moet er ook uit komen
     * @return object gecast naar Object
     */
    static <T> T convertToPojo(final T entiteit) {
        final T pojo;
        if (entiteit instanceof HibernateProxy) {
            // noinspection unchecked
            pojo = (T) ((HibernateProxy) entiteit).getHibernateLazyInitializer().getImplementation();
        } else {
            pojo = entiteit;
        }
        return pojo;
    }

    /**
     * Bepaal de entiteit klasse voor een concrete entiteit implementatie. De feitelijke
     * implementatie kan een sub-klasse zijn van de echte entiteit klasse.
     *
     * @param concreteClass De concrete entiteit klasse. Kan een sub-klasse van de echte entiteit
     *        klasse zijn.
     * @return De echte entiteit klasse.
     */
    static Class<?> bepaalEntiteitClass(final Class<?> concreteClass) {
        if (HibernateProxy.class.isAssignableFrom(concreteClass)) {
            return concreteClass.getSuperclass();
        } else {
            return concreteClass;
        }
    }

    /**
     * Geeft de attributen terug van alle abstracte historie klassen (bv
     * {@link AbstractFormeleHistorie}), mits deze van toepassing zijn voor de meegegeven entiteit.
     *
     * @param entiteitClass de class van de entiteit waarvoor de historie velden opgevraagd worden.
     * @return een array van historie {@link Field} die van toepassing zijn op de meegegeven
     *         entiteit.
     */
    static Field[] verzamelAlleHistorieAttributen(final Class<?> entiteitClass) {
        final List<Field> result = new ArrayList<>();

        result.addAll(FormeleHistorieZonderVerantwoording.getDeclaredEntityHistoryFields(entiteitClass, AbstractMaterieleHistorie.class));
        result.addAll(FormeleHistorieZonderVerantwoording.getDeclaredEntityHistoryFields(entiteitClass, AbstractFormeleHistorieZonderVerantwoording.class));
        result.addAll(FormeleHistorieZonderVerantwoording.getDeclaredEntityHistoryFields(entiteitClass, AbstractFormeleHistorie.class));
        return result.toArray(new Field[result.size()]);
    }

    /**
     * Controleert of het meegegeven veld een constante is in een class.
     *
     * @param field het veld wat gecontroleerd moet worden
     * @return true als het veld een constante (static final) is.
     */
    static boolean isFieldConstant(final Field field) {
        final int modifiers = field.getModifiers();
        return Modifier.isStatic(modifiers) && Modifier.isFinal(modifiers);
    }

    /**
     * Utility methode om een kopie van een java.sql.Timestamp te maken. Te gebruiken voor het
     * defensieve kopieren van (mutable) Timestamp objecten in getters, setters en constructors. Kan
     * omgaan met <code>null</code>.
     *
     * @param ts De te kopieren Timestamp
     * @return Een kopie van de Timestamp
     */
    static Timestamp timestamp(final Timestamp ts) {
        if (ts == null) {
            return null;
        }

        final Timestamp kopie = new Timestamp(ts.getTime());
        kopie.setNanos(ts.getNanos());
        return kopie;
    }
}
