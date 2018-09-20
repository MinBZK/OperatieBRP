/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern;

import javax.persistence.Entity;
import javax.persistence.Table;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.AanduidingVerblijfsrechtAttribuut;
import nl.bzk.brp.model.annotaties.GroepAccessor;
import nl.bzk.brp.model.basis.MaterieleHistorie;
import nl.bzk.brp.model.basis.ModelIdentificeerbaar;
import nl.bzk.brp.model.bericht.kern.PersoonVerblijfsrechtGroepBericht;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.logisch.kern.HisPersoonVerblijfsrechtGroep;
import nl.bzk.brp.model.logisch.kern.PersoonVerblijfsrechtGroep;


/**
 *
 *
 */
@Entity
@Table(schema = "Kern", name = "His_PersVerblijfsr")
@GroepAccessor(dbObjectId = 3517)
public class HisPersoonVerblijfsrechtModel extends AbstractHisPersoonVerblijfsrechtModel implements
    ModelIdentificeerbaar<Integer>, HisPersoonVerblijfsrechtGroep
{

    /**
     * Default constructor t.b.v. JPA
     */
    protected HisPersoonVerblijfsrechtModel() {
        super();
    }

    /**
     * Copy Constructor die op basis van een interface een C/D laag klasse construeert.
     *
     * @param persoonHisVolledig instantie van A-laag klasse.
     * @param groep              groep
     * @param historie           historie
     * @param actieInhoud        Actie inhoud; de actie die leidt tot dit nieuwe record.
     */
    public HisPersoonVerblijfsrechtModel(final PersoonHisVolledig persoonHisVolledig,
        final PersoonVerblijfsrechtGroep groep, final MaterieleHistorie historie, final ActieModel actieInhoud)
    {
        super(persoonHisVolledig, groep, actieInhoud);
    }

    /**
     * Copy Constructor die op basis van een C/D-laag klasse een C/D-laag klasse construeert.
     *
     * @param kopie backrefence instantie.
     */
    public HisPersoonVerblijfsrechtModel(final AbstractHisPersoonVerblijfsrechtModel kopie) {
        super(kopie);
    }

    /**
     * @param persoon                          persoon van HisPersoonVerblijfsrechtModel
     * @param aanduidingVerblijfsrecht         aanduidingVerblijfsrecht van HisPersoonVerblijfsrechtModel
     * @param datumAanvangVerblijfsrecht       datumAanvangVerblijfsrecht van HisPersoonVerblijfsrechtModel
     * @param datumMededelingVerblijfsrecht    datumMededelingVerblijfsrecht van HisPersoonVerblijfsrechtModel
     * @param datumVoorzienEindeVerblijfsrecht datumVoorzienEindeVerblijfsrecht van HisPersoonVerblijfsrechtModel
     * @param actieInhoud                      Actie inhoud; de actie die leidt tot dit nieuwe record.
     */
    public HisPersoonVerblijfsrechtModel(final PersoonHisVolledig persoon,
        final AanduidingVerblijfsrechtAttribuut aanduidingVerblijfsrecht,
        final DatumEvtDeelsOnbekendAttribuut datumAanvangVerblijfsrecht,
        final DatumEvtDeelsOnbekendAttribuut datumMededelingVerblijfsrecht,
        final DatumEvtDeelsOnbekendAttribuut datumVoorzienEindeVerblijfsrecht, final ActieModel actieInhoud)
    {
        super(persoon, aanduidingVerblijfsrecht, datumAanvangVerblijfsrecht, datumMededelingVerblijfsrecht, datumVoorzienEindeVerblijfsrecht,
            actieInhoud);
    }

    /**
     * @param persoon     persoon van HisPersoonVerblijfsrechtModel
     * @param bericht     het bericht
     * @param actieInhoud Actie inhoud; de actie die leidt tot dit nieuwe record.
     */
    public HisPersoonVerblijfsrechtModel(final PersoonHisVolledigImpl persoon, final PersoonVerblijfsrechtGroepBericht bericht,
        final ActieModel actieInhoud)
    {
        this(persoon, bericht.getAanduidingVerblijfsrecht(),
            bericht.getDatumAanvangVerblijfsrecht(), bericht.getDatumMededelingVerblijfsrecht(), bericht.getDatumVoorzienEindeVerblijfsrecht(),
            actieInhoud);
    }
}
