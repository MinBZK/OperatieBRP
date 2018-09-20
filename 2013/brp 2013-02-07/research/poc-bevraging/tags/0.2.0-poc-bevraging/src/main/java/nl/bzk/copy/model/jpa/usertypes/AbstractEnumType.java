/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.copy.model.jpa.usertypes;

import java.io.Serializable;

import org.hibernate.usertype.UserType;


/**
 * User type voor enum.
 */
public abstract class AbstractEnumType implements UserType {

    @Override
    public boolean equals(final Object x, final Object y) {
        return x == y;
    }

    @Override
    public int hashCode(final Object x) {
        return 0;
    }

    @Override
    public Object deepCopy(final Object value) {
        return value;
    }

    @Override
    public boolean isMutable() {
        return false;
    }

    @Override
    public Serializable disassemble(final Object value) {
        return (Serializable) value;
    }

    @Override
    public Object assemble(final Serializable cached, final Object owner) {
        return cached;
    }

    @Override
    public Object replace(final Object original, final Object target, final Object owner) {
        return original;
    }
}
