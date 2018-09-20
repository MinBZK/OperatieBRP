/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern;

import javax.persistence.Entity;
import javax.persistence.Table;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.VersienummerAttribuut;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.logisch.kern.HisPersoonInschrijvingGroep;
import nl.bzk.brp.model.logisch.kern.PersoonInschrijvingGroep;


/**
 *
 *
 */
@Entity
@Table(schema = "Kern", name = "His_PersInschr")
public class HisPersoonInschrijvingModel extends AbstractHisPersoonInschrijvingModel implements
    HisPersoonInschrijvingGroep
{

    /**
     * Default constructor t.b.v. JPA
     */
    protected HisPersoonInschrijvingModel() {
        super();
    }

    /**
     * Copy Constructor die op basis van een interface een C/D laag klasse construeert.
     *
     * @param persoonHisVolledig instantie van A-laag klasse.
     * @param groep              groep
     * @param actieInhoud        actie inhoud
     */
    public HisPersoonInschrijvingModel(final PersoonHisVolledig persoonHisVolledig,
        final PersoonInschrijvingGroep groep, final ActieModel actieInhoud)
    {
        super(persoonHisVolledig, groep, actieInhoud);
    }

    /**
     * Copy Constructor die op basis van een C/D-laag klasse een C/D-laag klasse construeert.
     *
     * @param kopie instantie van A-laag klasse.
     */
    public HisPersoonInschrijvingModel(final AbstractHisPersoonInschrijvingModel kopie) {
        super(kopie);
    }

    /**
     * @param persoon           persoon van HisPersoonInschrijvingModel
     * @param datumInschrijving datumInschrijving van HisPersoonInschrijvingModel
     * @param versienummer      versienummer van HisPersoonInschrijvingModel
     * @param actieInhoud       Actie inhoud; de actie die leidt tot dit nieuwe record.
     */
    public HisPersoonInschrijvingModel(final PersoonHisVolledig persoon,
        final DatumEvtDeelsOnbekendAttribuut datumInschrijving, final VersienummerAttribuut versienummer,
        final DatumTijdAttribuut datumtijdstempel, final ActieModel actieInhoud)
    {
        super(persoon, datumInschrijving, versienummer, datumtijdstempel, actieInhoud);
    }

}
