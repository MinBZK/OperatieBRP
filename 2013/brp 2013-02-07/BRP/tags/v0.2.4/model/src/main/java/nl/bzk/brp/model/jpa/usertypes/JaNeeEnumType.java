/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.jpa.usertypes;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.persistence.MappedSuperclass;

import nl.bzk.brp.model.attribuuttype.JaNee;
import org.hibernate.annotations.TypeDef;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.type.BooleanType;
import org.hibernate.type.descriptor.sql.BooleanTypeDescriptor;


/**
 * User type om boolean om te zetten naar JaNee en andersom.
 *
 * Note: Deze userType is geannoteerd met @MappedSuperClass om gedetecteerd te worden door hibernate.
 */
@MappedSuperclass
@TypeDef(name = "JaNee", defaultForType = JaNee.class, typeClass = JaNeeEnumType.class)
public class JaNeeEnumType extends AbstractEnumType {

    @Override
    public int[] sqlTypes() {
        return new int[] { BooleanTypeDescriptor.INSTANCE.getSqlType() };
    }

    @Override
    public Class<JaNee> returnedClass() {
        return JaNee.class;
    }

    @Override
    public Object nullSafeGet(final ResultSet rs, final String[] names, final SessionImplementor session,
            final Object owner) throws SQLException
    {
        JaNee resultaat;

        Boolean value = (Boolean) BooleanType.INSTANCE.get(rs, names[0], session);
        if (value == null) {
            resultaat = null;
        } else if (value) {
            resultaat = JaNee.Ja;
        } else {
            resultaat = JaNee.Nee;
        }

        return resultaat;
    }

    @Override
    public void nullSafeSet(final PreparedStatement st, final Object value, final int index,
            final SessionImplementor session) throws SQLException
    {
        if (value == null) {
            BooleanType.INSTANCE.set(st, null, index, session);
        } else {
            JaNee jaNee = (JaNee) value;
            if (jaNee == JaNee.Ja) {
                BooleanType.INSTANCE.set(st, true, index, session);
            } else {
                BooleanType.INSTANCE.set(st, false, index, session);
            }
        }
    }
}
