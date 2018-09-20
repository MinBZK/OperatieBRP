/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern;

import javax.persistence.Entity;
import javax.persistence.Table;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaNeeAttribuut;
import nl.bzk.brp.model.annotaties.GroepAccessor;
import nl.bzk.brp.model.basis.ModelIdentificeerbaar;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.logisch.kern.HisPersoonDeelnameEUVerkiezingenGroep;
import nl.bzk.brp.model.logisch.kern.PersoonDeelnameEUVerkiezingenGroep;


/**
 *
 *
 */
@Entity
@Table(schema = "Kern", name = "His_PersDeelnEUVerkiezingen")
@GroepAccessor(dbObjectId = 3901)
public class HisPersoonDeelnameEUVerkiezingenModel extends AbstractHisPersoonDeelnameEUVerkiezingenModel implements
    ModelIdentificeerbaar<Integer>, HisPersoonDeelnameEUVerkiezingenGroep
{

    /**
     * Default constructor t.b.v. JPA
     */
    protected HisPersoonDeelnameEUVerkiezingenModel() {
        super();
    }

    /**
     * Copy Constructor die op basis van een interface een C/D laag klasse construeert.
     *
     * @param persoonHisVolledig instantie van A-laag klasse.
     * @param groep              groep
     * @param actieInhoud        Actie inhoud; de actie die leidt tot dit nieuwe record.
     */
    public HisPersoonDeelnameEUVerkiezingenModel(final PersoonHisVolledig persoonHisVolledig,
        final PersoonDeelnameEUVerkiezingenGroep groep,
        final ActieModel actieInhoud)
    {
        super(persoonHisVolledig, groep, actieInhoud);
    }

    /**
     * Copy Constructor die op basis van een C/D-laag klasse een C/D-laag klasse construeert.
     *
     * @param kopie backrefence instantie.
     */
    public HisPersoonDeelnameEUVerkiezingenModel(final AbstractHisPersoonDeelnameEUVerkiezingenModel kopie) {
        super(kopie);
    }

    /**
     * @param persoon     persoon van HisPersoonDeelnameEUVerkiezingenModel
     * @param indicatieDeelnameEUVerkiezingen
     *                    indicatieDeelnameEUVerkiezingen van HisPersoonDeelnameEUVerkiezingenModel
     * @param datumAanleidingAanpassingDeelnameEUVerkiezingen
     *                    datumAanleidingAanpassingDeelnameEUVerkiezingen van HisPersoonDeelnameEUVerkiezingenModel
     * @param datumVoorzienEindeUitsluitingEUVerkiezingen
     *                    datumVoorzienEindeUitsluitingEUVerkiezingen van HisPersoonDeelnameEUVerkiezingenModel
     * @param actieInhoud Actie inhoud; de actie die leidt tot dit nieuwe record.
     */
    public HisPersoonDeelnameEUVerkiezingenModel(final PersoonHisVolledig persoon,
        final JaNeeAttribuut indicatieDeelnameEUVerkiezingen,
        final DatumEvtDeelsOnbekendAttribuut datumAanleidingAanpassingDeelnameEUVerkiezingen,
        final DatumEvtDeelsOnbekendAttribuut datumVoorzienEindeUitsluitingEUVerkiezingen,
        final ActieModel actieInhoud)
    {
        super(persoon, indicatieDeelnameEUVerkiezingen, datumAanleidingAanpassingDeelnameEUVerkiezingen,
            datumVoorzienEindeUitsluitingEUVerkiezingen, actieInhoud);
    }

}
