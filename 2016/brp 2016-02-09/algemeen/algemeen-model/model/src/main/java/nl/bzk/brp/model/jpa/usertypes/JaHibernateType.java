/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.jpa.usertypes;

import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Ja;
import org.hibernate.annotations.TypeDef;
import org.hibernate.type.AbstractSingleColumnStandardBasicType;
import org.hibernate.type.BooleanType;


/**
 * Hibernate specifiek user type voor het persisteren van {@link Ja} klasse instanties, waarbij de waarde van de instantie wordt opgeslagen als {@link
 * Boolean}.
 */
@MappedSuperclass
@TypeDef(name = "Ja", defaultForType = Ja.class, typeClass = JaHibernateType.class)
public class JaHibernateType extends AbstractVasteWaardeAttribuutTypeHibernateType<Ja, Boolean> {

    /**
     * {@inheritDoc}
     */
    @Override
    public Class returnedClass() {
        return Ja.class;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transient
    public AbstractSingleColumnStandardBasicType<Boolean> getBasicTypeInstance() {
        return BooleanType.INSTANCE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transient
    public Ja[] getAlleExtraWaardes() {
        return Ja.values();
    }
}
