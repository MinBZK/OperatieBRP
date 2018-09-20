/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern;

import javax.persistence.Embeddable;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPartijOnderzoekAttribuut;
import nl.bzk.brp.model.logisch.kern.PartijOnderzoekStandaardGroep;


/**
 *
 *
 */
@Embeddable
public class PartijOnderzoekStandaardGroepModel extends AbstractPartijOnderzoekStandaardGroepModel implements
    PartijOnderzoekStandaardGroep
{

    /**
     * Standaard constructor (t.b.v. Hibernate/JPA).
     */
    protected PartijOnderzoekStandaardGroepModel() {
        super();
    }

    /**
     * Basis constructor die direct alle velden instantieert.
     *
     * @param rol rol van Standaard.
     */
    public PartijOnderzoekStandaardGroepModel(final SoortPartijOnderzoekAttribuut rol) {
        super(rol);
    }

    /**
     * Copy constructor om vanuit het bericht model een instantie van het operationeel model te maken.
     *
     * @param partijOnderzoekStandaardGroep te kopieren groep.
     */
    public PartijOnderzoekStandaardGroepModel(final PartijOnderzoekStandaardGroep partijOnderzoekStandaardGroep) {
        super(partijOnderzoekStandaardGroep);
    }

}
