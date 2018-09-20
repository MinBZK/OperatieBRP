/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.beheer.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.ElementEnum;
import nl.bzk.brp.model.basis.ModelIdentificeerbaar;

/**
 * Gegevens view.
 *
 * @param <T> his record type
 */
public final class GegevensView<T extends ModelIdentificeerbaar<?>> {

    private final T actueelRecord;
    private final ElementEnum element;

    private final Map<ActieType, List<T>> gegevens = new TreeMap<>();

    /**
     * Constructor.
     *
     * @param actueelRecord Actueel record van de gegevensview
     * @param element his record element
     */
    public GegevensView(final T actueelRecord, final ElementEnum element) {
        this.element = element;
        this.actueelRecord = actueelRecord;
    }
    /**
     * Constructor.
     *
     * @param element his record element
     */
    public GegevensView(final ElementEnum element) {
        this(null, element);
    }

    /**
     * Voeg een record toe.
     *
     * @param actieType actie type
     * @param gegeven record
     */
    public void voegGegevenToe(final ActieType actieType, final T gegeven) {
        if (!gegevens.containsKey(actieType)) {
            gegevens.put(actieType, new ArrayList<T>());
        }

        gegevens.get(actieType).add(gegeven);
    }

    /**
     * Geef het element.
     *
     * @return element
     */
    public ElementEnum getElement() {
        return element;
    }

    /**
     * Geef de gegevens.
     *
     * @return gegevens
     */

    public Map<ActieType, List<T>> getGegevens() {
        return gegevens;
    }

    /**
     * Geef actueelrecord terug.
     *
     * @return actueel record
     */
    public T getActueelRecord() {
        return actueelRecord;
    }

}
