/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.expressie;

import java.util.Objects;
import nl.bzk.brp.domain.leveringmodel.MetaObject;

/**
 * Een literal expressie voor een {@link MetaObject}.
 */
public final class MetaObjectLiteral implements Literal {

    private final ExpressieType expressieType;
    private final MetaObject metaObject;

    /**
     * Constructor.
     *
     * @param metaObject    een {@link MetaObject}
     * @param expressieType Type van het rootobject.
     */
    public MetaObjectLiteral(final MetaObject metaObject, final ExpressieType expressieType) {
        this.metaObject = metaObject;
        this.expressieType = expressieType;
    }

    public MetaObject getMetaObject() {
        return metaObject;
    }

    @Override
    public ExpressieType getType(final Context context) {
        return expressieType;
    }

    @Override
    public String toString() {
        return "@" + metaObject.getObjectElement().getNaam();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final MetaObjectLiteral that = (MetaObjectLiteral) o;
        return Objects.equals(metaObject, that.metaObject);
    }

    @Override
    public int hashCode() {
        return Objects.hash(metaObject);
    }
}
