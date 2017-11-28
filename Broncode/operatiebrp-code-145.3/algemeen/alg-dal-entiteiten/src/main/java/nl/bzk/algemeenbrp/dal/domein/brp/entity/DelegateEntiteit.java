/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.dal.domein.brp.entity;

import java.util.Collections;
import java.util.List;

/**
 * Definieert de delegate methodes.
 * @param <T> moet van type {@link Entiteit} zijn
 */
public interface DelegateEntiteit<T extends Entiteit> {

    /**
     * Geef de waarde van delegate.
     *
     * @return de betrokkenheid delegate
     */
    T getDelegate();

    /**
     * Geef de waarde van delegates.
     *
     * @return delegates
     */
    default List<T> getDelegates() {
        return Collections.singletonList(getDelegate());
    }


    /**
     * Geef de waarde van isReadOnly.
     *
     * @return true als er geen wijzigingen mogen worden aangebracht aan de onderliggende entiteit,
     *         anders false
     */
    default boolean isReadOnly() {
        return false;
    }
}
