/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.ber;

import javax.persistence.Embeddable;
import nl.bzk.brp.model.algemeen.attribuuttype.ber.BerichtdataAttribuut;
import nl.bzk.brp.model.logisch.ber.BerichtStandaardGroep;


/**
 *
 *
 */
@Embeddable
public class BerichtStandaardGroepModel extends AbstractBerichtStandaardGroepModel implements BerichtStandaardGroep {

    /**
     * Default lege constructor t.b.v. Hibernate / JPA
     */
    protected BerichtStandaardGroepModel() {
        super();
    }

    /**
     * Basis constructor die direct alle velden instantieert.
     *
     * @param administratieveHandelingId administratieveHandeling van Standaard.
     * @param data                       data van Standaard.
     * @param antwoordOp                 antwoordOp van Standaard.
     */
    public BerichtStandaardGroepModel(final Long administratieveHandelingId, final BerichtdataAttribuut data,
        final BerichtModel antwoordOp)
    {
        super(administratieveHandelingId, data, antwoordOp);
    }

    /**
     * Copy constructor om vanuit het bericht model een instantie van het operationeel model te maken.
     *
     * @param berichtStandaardGroep te kopieren groep.
     */
    public BerichtStandaardGroepModel(final BerichtStandaardGroep berichtStandaardGroep) {
        super(berichtStandaardGroep);
    }

}
