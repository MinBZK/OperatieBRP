/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testdatageneratie.helper;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Properties;

import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.usertype.ParameterizedType;
import org.hibernate.usertype.UserType;


/**
 * Enum user type by name.
 *
 * @param <E> enum
 */
public class EnumUserTypeByName<E extends Enum<E>> implements UserType, ParameterizedType {

    private Class<E> clazz = null;

    @Override
    public void setParameterValues(final Properties params) {
        String enumClassName = params.getProperty("enumClassName");
        if (enumClassName == null) {
            throw new MappingException("enumClassName parameter not specified");
        }

        try {
            @SuppressWarnings("unchecked")
            Class<E> enumClass = (Class<E>) Class.forName(enumClassName);
            this.clazz = enumClass;
        } catch (ClassNotFoundException e) {
            throw new MappingException("enumClass " + enumClassName + " not found", e);
        }
    }

    private static final int[] SQL_TYPES = { Types.VARCHAR };

    @Override
    public int[] sqlTypes() {
        return SQL_TYPES;
    }

    @Override
    public Class<E> returnedClass() {
        return clazz;
    }

    @Override
    public Object nullSafeGet(final ResultSet resultSet, final String[] names,
                              final SessionImplementor sessionImplementor, final Object owner)
            throws HibernateException, SQLException
    {
        String name = resultSet.getString(names[0]);
        Object result = null;
        if (!resultSet.wasNull()) {
            result = Enum.valueOf(clazz, name);
        }
        return result;
    }

    @Override
    public void nullSafeSet(final PreparedStatement preparedStatement, final Object value, final int index,
                            final SessionImplementor sessionImplementor)
            throws HibernateException, SQLException
    {
        if (null == value) {
            preparedStatement.setNull(index, Types.VARCHAR);
        } else {
            @SuppressWarnings("unchecked")
            E enumValue = (E) value;
            preparedStatement.setString(index, enumValue.name());
        }
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
    public Object assemble(final Serializable cached, final Object owner) {
        return cached;
    }

    @Override
    public Serializable disassemble(final Object value) {
        return (Serializable) value;
    }

    @Override
    public Object replace(final Object original, final Object target, final Object owner) {
        return original;
    }

    @Override
    public int hashCode(final Object x) {
        return x.hashCode();
    }

    @Override
    public boolean equals(final Object x, final Object y) {
        boolean isEqual;

        if (x == y) {
            isEqual = true;
        } else if (null == x || null == y) {
            isEqual = false;
        } else {
            isEqual = x.equals(y);
        }

        return isEqual;
    }
}
