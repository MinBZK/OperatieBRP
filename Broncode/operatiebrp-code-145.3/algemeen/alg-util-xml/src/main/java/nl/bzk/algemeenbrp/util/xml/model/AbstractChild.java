/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.util.xml.model;

/**
 * Kind van een {@link CompositeObject}.
 *
 * @param <T> type van het kind
 */
public abstract class AbstractChild<T> implements Child<T> {

    private final Accessor<T> accessor;
    private final String name;

    /**
     * Constructor.
     *
     * @param accessor toegang tot de waarde
     * @param name naam
     */
    protected AbstractChild(final Accessor<T> accessor, final String name) {
        super();
        this.accessor = accessor;
        this.name = name;
    }

    /**
     * Geef de toegang tot de waarde.
     *
     * @return toegang
     */
    @Override
    public final Accessor<T> getAccessor() {
        return accessor;
    }

    /**
     * Geef de geconfigureerde naam.
     *
     * @return naam
     */
    @Override
    public final String getName() {
        return name;
    }

}
