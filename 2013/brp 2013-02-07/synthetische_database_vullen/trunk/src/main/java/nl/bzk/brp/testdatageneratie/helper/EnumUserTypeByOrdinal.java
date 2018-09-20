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
import org.hibernate.usertype.ParameterizedType;
import org.hibernate.usertype.UserType;


public class EnumUserTypeByOrdinal<E extends Enum<E>> implements UserType, ParameterizedType {

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

    private static final int[] SQL_TYPES = { Types.SMALLINT };

    @Override
    public int[] sqlTypes() {
        return SQL_TYPES;
    }

    @Override
    public Class<E> returnedClass() {
        return clazz;
    }

    @Override
    public Object nullSafeGet(final ResultSet resultSet, final String[] names, final Object owner)
            throws HibernateException, SQLException
    {
        Short ordinal = resultSet.getShort(names[0]);
        Object result = null;
        if (!resultSet.wasNull()) {
            try {
                @SuppressWarnings("unchecked")
                E[] values = (E[]) clazz.getMethod("values").invoke(null);
                result = values[ordinal];
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return result;
    }

    @Override
    public void nullSafeSet(final PreparedStatement preparedStatement, final Object value, final int index)
            throws HibernateException, SQLException
    {
        if (null == value) {
            preparedStatement.setNull(index, Types.SMALLINT);
        } else {
            @SuppressWarnings("unchecked")
            E enumValue = (E) value;
            preparedStatement.setShort(index, (short) enumValue.ordinal());
        }
    }

    @Override
    public Object deepCopy(final Object value) throws HibernateException {
        return value;
    }

    @Override
    public boolean isMutable() {
        return false;
    }

    @Override
    public Object assemble(final Serializable cached, final Object owner) throws HibernateException {
        return cached;
    }

    @Override
    public Serializable disassemble(final Object value) throws HibernateException {
        return (Serializable) value;
    }

    @Override
    public Object replace(final Object original, final Object target, final Object owner) throws HibernateException {
        return original;
    }

    @Override
    public int hashCode(final Object x) throws HibernateException {
        return x.hashCode();
    }

    @Override
    public boolean equals(final Object x, final Object y) throws HibernateException {
        if (x == y)
            return true;
        if (null == x || null == y)
            return false;
        return x.equals(y);
    }
}
