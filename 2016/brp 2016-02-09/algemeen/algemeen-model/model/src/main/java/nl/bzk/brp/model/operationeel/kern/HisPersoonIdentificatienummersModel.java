/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern;

import javax.persistence.Entity;
import javax.persistence.Table;
import nl.bzk.brp.model.basis.MaterieleHistorie;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.logisch.kern.HisPersoonIdentificatienummersGroep;
import nl.bzk.brp.model.logisch.kern.PersoonIdentificatienummersGroep;


/**
 *
 *
 */
@Entity
@Table(schema = "Kern", name = "His_PersIDs")
public class HisPersoonIdentificatienummersModel extends AbstractHisPersoonIdentificatienummersModel implements
    HisPersoonIdentificatienummersGroep
{

    /**
     * Default constructor t.b.v. JPA
     */
    protected HisPersoonIdentificatienummersModel() {
        super();
    }

    /**
     * Copy Constructor die op basis van een interface een C/D laag klasse construeert.
     *
     * @param persoonHisVolledig instantie van A-laag klasse.
     * @param groep              groep
     * @param historie           historie
     * @param actieInhoud        actie inhoud
     */
    public HisPersoonIdentificatienummersModel(final PersoonHisVolledig persoonHisVolledig,
        final PersoonIdentificatienummersGroep groep, final MaterieleHistorie historie,
        final ActieModel actieInhoud)
    {
        super(persoonHisVolledig, groep, historie, actieInhoud);
    }

    /**
     * Copy Constructor die op basis van een C/D-laag klasse een C/D-laag klasse construeert.
     *
     * @param kopie instantie van A-laag klasse.
     */
    public HisPersoonIdentificatienummersModel(final AbstractHisPersoonIdentificatienummersModel kopie) {
        super(kopie);
    }

}
