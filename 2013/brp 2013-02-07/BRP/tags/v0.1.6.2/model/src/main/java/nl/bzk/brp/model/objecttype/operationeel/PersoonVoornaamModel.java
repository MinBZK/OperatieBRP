/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.objecttype.operationeel;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Table;

import nl.bzk.brp.model.objecttype.logisch.PersoonVoornaam;
import nl.bzk.brp.model.objecttype.logisch.basis.PersoonVoornaamBasis;
import nl.bzk.brp.model.objecttype.operationeel.basis.AbstractPersoonVoornaamModel;


/**
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(schema = "Kern", name = "PersVoornaam")
@Access(AccessType.FIELD)
public class PersoonVoornaamModel extends AbstractPersoonVoornaamModel implements PersoonVoornaam {

    public PersoonVoornaamModel(final PersoonVoornaamBasis persVoornaam,
                              final PersoonModel pers)
    {
        super(persVoornaam, pers);
    }

    private PersoonVoornaamModel() {
    }
}
