/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.repository.util;

import org.hibernate.proxy.HibernateProxy;

/**
 * Utility methoden voor de persistentie implementatie.
 */
public final class PersistenceUtil {

    private static final Class<org.hibernate.proxy.HibernateProxy> HIBERNATE_PROXY_INTERFACE = org.hibernate.proxy.HibernateProxy.class;

    private PersistenceUtil() {
    }

    /**
     * Bepaal de entiteit klasse voor een concrete entiteit implementatie. De feitelijke implementatie kan een
     * sub-klasse zijn van de echte entiteit klasse.
     *
     * @param concreteClass
     *            De concrete entiteit klasse. Kan een sub-klasse van de echte entiteit klasse zijn.
     * @return De echte entiteit klasse.
     */
    public static Class<?> bepaalEntiteitClass(final Class<?> concreteClass) {
        if (HIBERNATE_PROXY_INTERFACE.isAssignableFrom(concreteClass)) {
            return concreteClass.getSuperclass();
        } else {
            return concreteClass;
        }
    }

    /**
     * Controleert of het meegegeven object een {@link org.hibernate.proxy.HibernateProxy} object is. Als dat het geval
     * is wordt, de implementatie van de proxy opgevraagd en terug geven als {@link java.lang.Object}. Als het al een
     * POJO object is, dan wordt het object gelijk terug gegeven.
     *
     * @param object
     *            object of {@link org.hibernate.proxy.HibernateProxy}
     * @param <T>
     *            Het type van het object
     * @return object gecast naar Object
     */
    public static <T> T getPojoFromObject(final T object) {
        final T pojo;
        if (object instanceof HibernateProxy) {
            // noinspection unchecked
            pojo = (T) ((HibernateProxy) object).getHibernateLazyInitializer().getImplementation();
        } else {
            pojo = object;
        }
        return pojo;
    }

}
