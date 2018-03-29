/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.runtime.jbpm.hibernate;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.usertype.UserType;
import org.jbpm.context.exe.Converter;
import org.jbpm.db.hibernate.Converters;

/**
 * is the hibernate UserType for storing converters as a char in the database. The conversion can be found (and
 * customized) in the file jbpm.converter.properties.
 */
public final class ConverterEnumType implements UserType, Serializable {

    private static final long serialVersionUID = 1L;

    @Override
    public boolean equals(final Object o1, final Object o2) {
        return o1 == o2;
    }

    @Override
    public int hashCode(final Object o) {
        return o.hashCode();
    }

    @Override
    public Object deepCopy(final Object o) {
        return o;
    }

    @Override
    public boolean isMutable() {
        return false;
    }

    @Override
    public Serializable disassemble(final Object o) {
        return (Serializable) o;
    }

    @Override
    public Object assemble(final Serializable s, final Object o) {
        return s;
    }

    @Override
    public Object replace(final Object original, final Object target, final Object owner) {
        return target;
    }

    @Override
    public int[] sqlTypes() {
        return new int[]{Types.CHAR};
    }

    @Override
    public Class returnedClass() {
        return Converter.class;
    }

    @Override
    public Object nullSafeGet(final ResultSet rs, final String[] names, final SessionImplementor session, final Object owner) throws SQLException {
        final String converterDatabaseId = rs.getString(names[0]);
        return Converters.getConverterByDatabaseId(converterDatabaseId);
    }

    @Override
    public void nullSafeSet(final PreparedStatement st, final Object value, final int index, final SessionImplementor session) throws SQLException {
        final String converterDatabaseId = Converters.getConverterId((Converter) value);
        st.setString(index, converterDatabaseId);

    }
}
