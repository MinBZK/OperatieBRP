/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.jpa.usertypes;

import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.LocatieTenOpzichteVanAdres;
import org.hibernate.annotations.TypeDef;
import org.hibernate.type.AbstractSingleColumnStandardBasicType;
import org.hibernate.type.StringType;


/**
 * Hibernate specifiek user type voor het persisteren van {@link nl.bzk.brp.model.algemeen.attribuuttype.kern.LocatieTenOpzichteVanAdres} klasse
 * instanties, waarbij de waarde van de instantie wordt opgeslagen als {@link String}.
 */
@MappedSuperclass
@TypeDef(name = "LocatieTenOpzichteVanAdres", defaultForType = LocatieTenOpzichteVanAdres.class,
    typeClass = LocatieTenOpzichteVanAdresHibernateType.class)
public class LocatieTenOpzichteVanAdresHibernateType extends
    AbstractVasteWaardeAttribuutTypeHibernateType<LocatieTenOpzichteVanAdres, String>
{

    /**
     * {@inheritDoc}
     */
    @Override
    @Transient
    public AbstractSingleColumnStandardBasicType<String> getBasicTypeInstance() {
        return StringType.INSTANCE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Class returnedClass() {
        return String.class;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transient
    public LocatieTenOpzichteVanAdres[] getAlleExtraWaardes() {
        return LocatieTenOpzichteVanAdres.values();
    }
}
