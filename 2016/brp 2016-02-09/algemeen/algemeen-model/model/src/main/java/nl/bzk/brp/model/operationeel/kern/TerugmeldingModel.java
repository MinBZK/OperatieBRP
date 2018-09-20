/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern;

import javax.persistence.Entity;
import javax.persistence.Table;
import nl.bzk.brp.model.basis.ModelIdentificeerbaar;
import nl.bzk.brp.model.logisch.kern.Terugmelding;


/**
 *
 *
 */
@Entity
@Table(schema = "Kern", name = "Terugmelding")
public class TerugmeldingModel extends AbstractTerugmeldingModel implements Terugmelding,
    ModelIdentificeerbaar<Integer>
{

    /**
     * Standaard constructor (t.b.v. Hibernate/JPA).
     */
    protected TerugmeldingModel() {
        super();
    }

}
