/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.controllers.query;

import nl.bzk.brp.beheer.webapp.util.AttribuutUtils;
import nl.bzk.brp.model.basis.Attribuut;

/**
 * Atribuut value adapter.
 *
 * @param <T> attribuut type
 * @param <V> value type
 */
public final class AttribuutValueAdapter<T extends Attribuut<V>, V> implements ValueAdapter<T> {

    private final ValueAdapter<V> baseValueAdapter;
    private final Class<T> attribuutClazz;

    /**
     * Constructor.
     *
     * @param baseValueAdapter basis value adapter
     * @param attribuutClazz attribuut klasse
     */
    public AttribuutValueAdapter(final ValueAdapter<V> baseValueAdapter, final Class<T> attribuutClazz) {
        this.baseValueAdapter = baseValueAdapter;
        this.attribuutClazz = attribuutClazz;
    }

    @Override
    public T adaptValue(final String value) {
        return AttribuutUtils.getAsAttribuut(attribuutClazz, baseValueAdapter.adaptValue(value));
    }
}
