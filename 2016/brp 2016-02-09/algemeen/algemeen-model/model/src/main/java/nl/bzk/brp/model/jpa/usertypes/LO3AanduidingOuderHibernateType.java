/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.jpa.usertypes;

import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import nl.bzk.brp.model.algemeen.attribuuttype.verconv.LO3AanduidingOuder;
import org.hibernate.annotations.TypeDef;
import org.hibernate.type.AbstractSingleColumnStandardBasicType;
import org.hibernate.type.ShortType;


/**
 * Hibernate specifiek user type voor het persisteren van {@link LO3AanduidingOuder} klasse
 * instanties, waarbij de waarde van de instantie wordt opgeslagen als {@link String}.
 */
@MappedSuperclass
@TypeDef(name = "LO3AanduidingOuder", defaultForType = LO3AanduidingOuder.class,
    typeClass = LO3AanduidingOuderHibernateType.class)
public class LO3AanduidingOuderHibernateType extends AbstractVasteWaardeAttribuutTypeHibernateType<LO3AanduidingOuder, Short> {

    /**
     * {@inheritDoc}
     */
    @Override
    @Transient
    public AbstractSingleColumnStandardBasicType<Short> getBasicTypeInstance() {
        return ShortType.INSTANCE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Class returnedClass() {
        return Short.class;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transient
    public LO3AanduidingOuder[] getAlleExtraWaardes() {
        return LO3AanduidingOuder.values();
    }
}
