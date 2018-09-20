/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.jpa.usertypes;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.usertype.UserType;


/**
 * Abstracte superklasse met een user type implementatie voor enums. Hierdoor kan een enum gebruik maken van zijn eigen getId() als id in de database, in
 * plaats van de standaard opties ordinal en naam. Een concrete persistent enum user type hoeft slechts returnedClass() te implementeren.
 * <p/>
 * Overgenomen van: http://www.gabiaxel.com/2011/01/better-enum-mapping-with-hibernate.html
 *
 * @param <T> het concrete persistent enum type
 */
public abstract class AbstractPersistentEnumUserType<T extends PersistentEnum> implements UserType {

    @Override
    public Object assemble(final Serializable cached, final Object owner) {
        return cached;
    }

    @Override
    public Object deepCopy(final Object value) {
        return value;
    }

    @Override
    public Serializable disassemble(final Object value) {
        return (Serializable) value;
    }

    /**
     * {@inheritDoc}
     * <p/>
     * Methode benodigd voor implementeren van door Hibernate vereiste interface.
     */
    @SuppressWarnings("PMD.SuspiciousEqualsMethodName")
    @Override
    public boolean equals(final Object x, final Object y) {
        return x.equals(y);
    }

    @Override
    public int hashCode(final Object x) {
        int hashCode = 0;
        if (x != null) {
            hashCode = x.hashCode();
        }
        return hashCode;
    }

    @Override
    public boolean isMutable() {
        return false;
    }

    @Override
    public Object nullSafeGet(final ResultSet rs, final String[] names, final SessionImplementor session,
        final Object owner) throws SQLException
    {
        final int id = rs.getInt(names[0]);
        if (rs.wasNull()) {
            return null;
        }
        for (final PersistentEnum value : returnedClass().getEnumConstants()) {
            if (id == value.getId()) {
                return value;
            }
        }
        throw new IllegalStateException("Unknown " + returnedClass().getSimpleName() + " id");
    }

    @Override
    public void nullSafeSet(final PreparedStatement st, final Object value, final int index,
        final SessionImplementor session) throws SQLException
    {
        if (value == null) {
            st.setNull(index, Types.INTEGER);
        } else {
            st.setInt(index, ((PersistentEnum) value).getId());
        }
    }

    @Override
    public Object replace(final Object original, final Object target, final Object owner) {
        return original;
    }

    @Override
    public abstract Class<T> returnedClass();

    @Override
    public int[] sqlTypes() {
        return new int[]{ Types.INTEGER };
    }

}
