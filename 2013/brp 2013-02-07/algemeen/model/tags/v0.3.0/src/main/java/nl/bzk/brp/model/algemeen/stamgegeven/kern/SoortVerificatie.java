/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.kern;

import javax.persistence.Entity;
import javax.persistence.Table;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamEnumeratiewaarde;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.basis.AbstractSoortVerificatie;


/**
 * Categorisatie van Verificatie.
 *
 *
 *
 *
 */
@Entity
@Table(schema = "Kern", name = "SrtVerificatie")
public class SoortVerificatie extends AbstractSoortVerificatie {

    /**
     * Constructor is protected, want de BRP zal geen stamgegevens beheren.
     *
     */
    protected SoortVerificatie() {
        super();
    }

    /**
     * Constructor die direct alle attributen instantieert.
     *
     * @param naam naam van SoortVerificatie.
     */
    protected SoortVerificatie(final NaamEnumeratiewaarde naam) {
        super(naam);
    }

}
