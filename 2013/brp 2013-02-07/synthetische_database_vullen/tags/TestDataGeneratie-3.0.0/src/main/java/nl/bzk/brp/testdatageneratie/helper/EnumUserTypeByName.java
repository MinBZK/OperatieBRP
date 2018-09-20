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


public class EnumUserTypeByName<E extends Enum<E>> implements UserType, ParameterizedType {

    private Class<E> clazz = null;

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

    public int[] sqlTypes() {
        return SQL_TYPES;
    }

    public Class<E> returnedClass() {
        return clazz;
    }

    public Object nullSafeGet(final ResultSet resultSet, final String[] names, final Object owner)
            throws HibernateException, SQLException
    {
        String name = resultSet.getString(names[0]);
        Object result = null;
        if (!resultSet.wasNull()) {
            result = Enum.valueOf(clazz, name);
        }
        return result;
    }

    public void nullSafeSet(final PreparedStatement preparedStatement, final Object value, final int index)
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

    public Object deepCopy(final Object value) throws HibernateException {
        return value;
    }

    public boolean isMutable() {
        return false;
    }

    public Object assemble(final Serializable cached, final Object owner) throws HibernateException {
        return cached;
    }

    public Serializable disassemble(final Object value) throws HibernateException {
        return (Serializable) value;
    }

    public Object replace(final Object original, final Object target, final Object owner) throws HibernateException {
        return original;
    }

    public int hashCode(final Object x) throws HibernateException {
        return x.hashCode();
    }

    public boolean equals(final Object x, final Object y) throws HibernateException {
        if (x == y)
            return true;
        if (null == x || null == y)
            return false;
        return x.equals(y);
    }
}
