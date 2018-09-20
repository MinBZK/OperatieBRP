/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.autaut;

import javax.persistence.Embeddable;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.logisch.autaut.PersoonAfnemerindicatieStandaardGroep;


/**
 *
 *
 */
@Embeddable
public class PersoonAfnemerindicatieStandaardGroepModel extends AbstractPersoonAfnemerindicatieStandaardGroepModel
    implements PersoonAfnemerindicatieStandaardGroep
{

    /**
     * Standaard constructor (t.b.v. Hibernate/JPA).
     */
    protected PersoonAfnemerindicatieStandaardGroepModel() {
        super();
    }

    /**
     * Basis constructor die direct alle velden instantieert.
     *
     * @param datumAanvangMaterielePeriode datumAanvangMaterielePeriode van Standaard
     * @param datumEindeVolgen             datumEindeVolgen van Standaard
     */
    public PersoonAfnemerindicatieStandaardGroepModel(
        final DatumEvtDeelsOnbekendAttribuut datumAanvangMaterielePeriode,
        final DatumAttribuut datumEindeVolgen)
    {
        super(datumAanvangMaterielePeriode, datumEindeVolgen);
    }

    /**
     * Copy constructor om vanuit het bericht model een instantie van het operationeel model te maken.
     *
     * @param persoonAfnemerindicatieStandaardGroep
     *         te kopieren groep.
     */
    public PersoonAfnemerindicatieStandaardGroepModel(
        final PersoonAfnemerindicatieStandaardGroep persoonAfnemerindicatieStandaardGroep)
    {
        super(persoonAfnemerindicatieStandaardGroep);
    }

}
