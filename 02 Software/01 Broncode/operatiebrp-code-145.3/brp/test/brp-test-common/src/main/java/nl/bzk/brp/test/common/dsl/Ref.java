/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.test.common.dsl;

/**
 */
public class Ref<T> {

    private final String ref;
    private final T value;

    public Ref(final String ref, final T value) {
        this.ref = ref;
        this.value = value;
    }

    public String getRef() {
        return ref;
    }

    public T getValue() {
        return value;
    }
}
