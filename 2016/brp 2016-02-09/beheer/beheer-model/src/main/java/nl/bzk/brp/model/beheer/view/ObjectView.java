/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.beheer.view;

import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.ElementEnum;
import nl.bzk.brp.model.basis.ModelIdentificeerbaar;

/**
 * Basis object type view.
 *
 * @param <T> basis object type
 */
public class ObjectView<T extends ModelIdentificeerbaar<?>> {

    private final T basisObject;
    private final Map<ElementEnum, GegevensView<?>> gegevens = new TreeMap<>();

    /**
     * Constructor.
     *
     * @param basisObject basis object
     */
    public ObjectView(final T basisObject) {
        this.basisObject = basisObject;
    }

    /**
     * Bepaal of deze view de view is voor een gegeven basis object.
     *
     * @param object basis object
     * @return true, als deze view de view voor het gegeven basis object is, anders false
     */
    public final boolean isViewVoor(final T object) {
        return basisObject.getID().equals(object.getID());
    }

    /**
     * Geef de gegevens view voor een gegeven element.
     *
     * @param element element
     * @return gegevens view
     */
    public final GegevensView<?> geefGegevensViewVoor(final ElementEnum element) {
        if (gegevens.containsKey(element)) {
            return gegevens.get(element);
        }

        final GegevensView<?> gegevensView = new GegevensView<>(element);
        gegevens.put(element, gegevensView);
        return gegevensView;
    }

    /**
     * Geef het basis object.
     *
     * @return basis object
     */
    public final T getBasisObject() {
        return basisObject;
    }

    /**
     * Geef de gegevens views.
     *
     * @return gegevens views
     */
    public final Collection<GegevensView<?>> getGegevensViews() {
        return gegevens.values();
    }
}
