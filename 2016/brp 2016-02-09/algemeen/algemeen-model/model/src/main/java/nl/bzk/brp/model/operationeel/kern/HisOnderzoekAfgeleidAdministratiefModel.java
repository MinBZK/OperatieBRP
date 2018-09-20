/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern;

import javax.persistence.Entity;
import javax.persistence.Table;
import nl.bzk.brp.model.annotaties.GroepAccessor;
import nl.bzk.brp.model.basis.ModelIdentificeerbaar;
import nl.bzk.brp.model.hisvolledig.kern.OnderzoekHisVolledig;
import nl.bzk.brp.model.logisch.kern.HisOnderzoekAfgeleidAdministratiefGroep;
import nl.bzk.brp.model.logisch.kern.OnderzoekAfgeleidAdministratiefGroep;


/**
 *
 *
 */
@Entity
@Table(schema = "Kern", name = "His_OnderzoekAfgeleidAdminis")
@GroepAccessor(dbObjectId = 10841)
public class HisOnderzoekAfgeleidAdministratiefModel extends AbstractHisOnderzoekAfgeleidAdministratiefModel implements
    ModelIdentificeerbaar<Integer>, HisOnderzoekAfgeleidAdministratiefGroep
{

    /**
     * Default constructor t.b.v. JPA
     */
    protected HisOnderzoekAfgeleidAdministratiefModel() {
        super();
    }

    /**
     * Copy Constructor die op basis van een interface een C/D laag klasse construeert.
     *
     * @param onderzoekHisVolledig instantie van A-laag klasse.
     * @param groep                groep
     * @param actieInhoud          Actie inhoud; de actie die leidt tot dit nieuwe record.
     */
    public HisOnderzoekAfgeleidAdministratiefModel(final OnderzoekHisVolledig onderzoekHisVolledig,
        final OnderzoekAfgeleidAdministratiefGroep groep,
        final ActieModel actieInhoud)
    {
        super(onderzoekHisVolledig, groep, actieInhoud);
    }

    /**
     * Copy Constructor die op basis van een C/D-laag klasse een C/D-laag klasse construeert.
     *
     * @param kopie backrefence instantie.
     */
    public HisOnderzoekAfgeleidAdministratiefModel(final AbstractHisOnderzoekAfgeleidAdministratiefModel kopie) {
        super(kopie);
    }

    /**
     *
     *
     * @param onderzoek onderzoek van HisOnderzoekAfgeleidAdministratiefModel
     * @param administratieveHandeling administratieveHandeling van HisOnderzoekAfgeleidAdministratiefModel
     * @param actieInhoud Actie inhoud; de actie die leidt tot dit nieuwe record.
     */
    /*
     * ermul: handmatige wijzging
     * public HisOnderzoekAfgeleidAdministratiefModel(final OnderzoekHisVolledig onderzoek,
     * final AdministratieveHandeling administratieveHandeling, final ActieModel actieInhoud)
     * {
     * super(onderzoek, administratieveHandeling, actieInhoud);
     * }
     */

}
