/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.basis;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embedded;
import javax.persistence.MappedSuperclass;

/**
 * Abstracte implementatie voor historie entiteiten die een materiele historie patroon volgen.
 */
@Access(value = AccessType.FIELD)
@MappedSuperclass
public abstract class AbstractMaterieleHistorieEntiteit implements MaterieleHistorieEntiteit {

    @Embedded
    private MaterieleHistorieImpl materieleHistorie = new MaterieleHistorieImpl();

    protected AbstractMaterieleHistorieEntiteit(final AbstractMaterieleHistorieEntiteit kopie) {
        materieleHistorie = new MaterieleHistorieImpl((MaterieleHistorieImpl) kopie.getMaterieleHistorie());
    }

    protected AbstractMaterieleHistorieEntiteit() {

    }

    @Override
    public MaterieleHistorie getMaterieleHistorie() {
        return materieleHistorie;
    }
}
