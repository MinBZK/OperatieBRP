/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.jpa.usertypes;

import javax.persistence.MappedSuperclass;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.DatabaseObjectKern;
import org.hibernate.annotations.TypeDef;


/**
 * De custom user type mapping voor de DatabaseObject enum. Maakt gebruik van de generieke persistent enum user type.
 */
@MappedSuperclass
@TypeDef(name = "DatabaseObject", defaultForType = DatabaseObjectKern.class,
    typeClass = DatabaseObjectHibernateType.class)
public class DatabaseObjectHibernateType extends AbstractPersistentEnumUserType<DatabaseObjectKern> {

    @Override
    public Class<DatabaseObjectKern> returnedClass() {
        return DatabaseObjectKern.class;
    }

}
