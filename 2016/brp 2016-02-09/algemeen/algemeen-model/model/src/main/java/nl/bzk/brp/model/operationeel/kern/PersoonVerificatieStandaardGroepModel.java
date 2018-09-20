/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern;

import javax.persistence.Embeddable;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.logisch.kern.PersoonVerificatieStandaardGroep;


/**
 *
 *
 */
@Embeddable
public class PersoonVerificatieStandaardGroepModel extends AbstractPersoonVerificatieStandaardGroepModel implements
    PersoonVerificatieStandaardGroep
{

    /**
     * Standaard constructor (t.b.v. Hibernate/JPA).
     */
    protected PersoonVerificatieStandaardGroepModel() {
        super();
    }

    /**
     * Basis constructor die direct alle velden instantieert.
     *
     * @param datum datum van Standaard.
     */
    public PersoonVerificatieStandaardGroepModel(final DatumEvtDeelsOnbekendAttribuut datum) {
        super(datum);
    }

    /**
     * Basis constructor die direct alle velden instantieert.
     *
     * @param standaardgroep standaard groep van persoon verrificatie
     */
    public PersoonVerificatieStandaardGroepModel(final PersoonVerificatieStandaardGroep standaardgroep) {
        super(standaardgroep);
    }

}
