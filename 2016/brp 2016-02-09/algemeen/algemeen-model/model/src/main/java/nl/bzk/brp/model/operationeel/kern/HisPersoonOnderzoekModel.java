/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoonOnderzoekAttribuut;
import nl.bzk.brp.model.annotaties.GroepAccessor;
import nl.bzk.brp.model.basis.ModelIdentificeerbaar;
import nl.bzk.brp.model.hisvolledig.kern.PersoonOnderzoekHisVolledig;
import nl.bzk.brp.model.logisch.kern.HisPersoonOnderzoekStandaardGroep;
import nl.bzk.brp.model.logisch.kern.PersoonOnderzoekStandaardGroep;


/**
 *
 *
 */
@Entity
@Table(schema = "Kern", name = "His_PersOnderzoek")
@GroepAccessor(dbObjectId = 10763)
public class HisPersoonOnderzoekModel extends AbstractHisPersoonOnderzoekModel implements
    ModelIdentificeerbaar<Integer>, HisPersoonOnderzoekStandaardGroep
{

    /**
     * Default constructor t.b.v. JPA
     */
    protected HisPersoonOnderzoekModel() {
        super();
    }

    /**
     * Copy Constructor die op basis van een interface een C/D laag klasse construeert.
     *
     * @param persoonOnderzoekHisVolledig instantie van A-laag klasse.
     * @param groep                       groep
     * @param actieInhoud                 Actie inhoud; de actie die leidt tot dit nieuwe record.
     */
    public HisPersoonOnderzoekModel(final PersoonOnderzoekHisVolledig persoonOnderzoekHisVolledig,
        final PersoonOnderzoekStandaardGroep groep, final ActieModel actieInhoud)
    {
        super(persoonOnderzoekHisVolledig, groep, actieInhoud);
    }

    /**
     * Copy Constructor die op basis van een C/D-laag klasse een C/D-laag klasse construeert.
     *
     * @param kopie backrefence instantie.
     */
    public HisPersoonOnderzoekModel(final AbstractHisPersoonOnderzoekModel kopie) {
        super(kopie);
    }

    /**
     * @param persoonOnderzoek persoonOnderzoek van HisPersoonOnderzoekModel
     * @param rol              rol van HisPersoonOnderzoekModel
     * @param actieInhoud      Actie inhoud; de actie die leidt tot dit nieuwe record.
     */
    public HisPersoonOnderzoekModel(final PersoonOnderzoekHisVolledig persoonOnderzoek,
        final SoortPersoonOnderzoekAttribuut rol, final ActieModel actieInhoud)
    {
        super(persoonOnderzoek, rol, actieInhoud);
    }

    /**
     * @return de objectsleutel
     */
    @Transient
    public final String getObjectSleutel() {
        return getPersoonOnderzoek().getID().toString();
    }

}
