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

import nl.bzk.brp.model.basis.VasteWaardeAttribuutType;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.type.AbstractSingleColumnStandardBasicType;
import org.hibernate.usertype.UserType;

/**
 * Abstract basis klasse die een Hibernate {@link UserType} implementeert ten behoeve van de persistentie (middels
 * Hibernate) van {@link VasteWaardeAttribuutType} instanties. Sommige attribuuttypes hebben namelijk een vaste
 * set van waardes (waardebereik) en worden om die reden als Java enumeratie geimplementeerd, waarbij de waarde
 * een attribuut is van de enumeratie. Daar het onderliggende type in de database niet altijd een String of getal is,
 * maar ook omdat de String waarde niet altijd geheel in hoofdletters dient te worden opgeslagen, voldoet het
 * standaard JPA persisteren van enums, namelijk de naam of de ordinal, niet en is dus deze een Hibernate specifiek
 * User type nodig.
 *
 * @param <R> het type vaste waarde attribuut
 * @param <T> het type van de vaste waardes van het attribuut
 */
public abstract class AbstractVasteWaardeAttribuutTypeHibernateType<R extends VasteWaardeAttribuutType<T>, T>
    implements UserType
{

    /**
     * Retourneert het Hibernate Basis type dat gebruikt wordt in de database voor de opslag van de waarde van
     * het vaste waarde attribuut.
     * @return een Hibernate basis type.
     */
    public abstract AbstractSingleColumnStandardBasicType<T> getBasicTypeInstance();

    /**
     * Retourneert alle mogelijke waardes van het vaste waarde attribuut.
     * @return een array van mogelijk waardes voor het vaste waarde attribuut.
     */
    public abstract R[] getAlleExtraWaardes();

    @Override
    public int[] sqlTypes() {
        return new int[]{ getBasicTypeInstance().getSqlTypeDescriptor().getSqlType() };
    }

    @Override
    public Object nullSafeGet(final ResultSet rs, final String[] names, final SessionImplementor session,
        final Object owner) throws SQLException
    {
        assert names.length == 1;

        R resultaat = null;

        T attribuut = (T) getBasicTypeInstance().get(rs, names[0], session);
        for (R waarde : getAlleExtraWaardes()) {
            if (waarde.getWaarde().equals(attribuut)) {
                resultaat = waarde;
                break;
            }
        }
        return resultaat;
    }

    @Override
    public void nullSafeSet(final PreparedStatement st, final Object value, final int index,
        final SessionImplementor session) throws SQLException
    {
        if (value == null) {
            getBasicTypeInstance().set(st, null, index, session);
        } else {
            final R attribuut = (R) value;
            getBasicTypeInstance().set(st, attribuut.getWaarde(), index, session);
        }
    }

    @Override
    public boolean equals(final Object x, final Object y) {
        return x == y;
    }

    @Override
    public int hashCode(final Object x) {
        return ((VasteWaardeAttribuutType) x).getWaarde().hashCode();
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
