/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern;

import javax.persistence.Entity;
import javax.persistence.Table;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaNeeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.SorteervolgordeAttribuut;
import nl.bzk.brp.model.annotaties.GroepAccessor;
import nl.bzk.brp.model.basis.ModelIdentificeerbaar;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.logisch.kern.HisPersoonAfgeleidAdministratiefGroep;
import nl.bzk.brp.model.logisch.kern.PersoonAfgeleidAdministratiefGroep;
import org.apache.commons.lang.NotImplementedException;


/**
 *
 *
 */
@Entity
@Table(schema = "Kern", name = "His_PersAfgeleidAdministrati")
@GroepAccessor(dbObjectId = 3909)
public class HisPersoonAfgeleidAdministratiefModel extends AbstractHisPersoonAfgeleidAdministratiefModel implements
    ModelIdentificeerbaar<Integer>, HisPersoonAfgeleidAdministratiefGroep
{

    /**
     * Default constructor t.b.v. JPA
     */
    protected HisPersoonAfgeleidAdministratiefModel() {
        super();
    }

    /**
     * Copy Constructor die op basis van een interface een C/D laag klasse construeert.
     *
     * @param persoonHisVolledig instantie van A-laag klasse.
     * @param groep              groep
     * @param actieInhoud        Actie inhoud; de actie die leidt tot dit nieuwe record.
     */
    public HisPersoonAfgeleidAdministratiefModel(final PersoonHisVolledig persoonHisVolledig,
        final PersoonAfgeleidAdministratiefGroep groep,
        final ActieModel actieInhoud)
    {
        super(persoonHisVolledig, groep, actieInhoud);
    }

    /**
     * Copy Constructor die op basis van een C/D-laag klasse een C/D-laag klasse construeert.
     *
     * @param kopie backrefence instantie.
     */
    public HisPersoonAfgeleidAdministratiefModel(final AbstractHisPersoonAfgeleidAdministratiefModel kopie) {
        super(kopie);
    }

    /**
     * @param persoon                  persoon van HisPersoonAfgeleidAdministratiefModel
     * @param administratieveHandeling administratieveHandeling van HisPersoonAfgeleidAdministratiefModel
     * @param tijdstipLaatsteWijziging tijdstipLaatsteWijziging van HisPersoonAfgeleidAdministratiefModel
     * @param sorteervolgorde          sorteervolgorde van HisPersoonAfgeleidAdministratiefModel
     * @param actieInhoud              Actie inhoud; de actie die leidt tot dit nieuwe record.
     */
    public HisPersoonAfgeleidAdministratiefModel(final PersoonHisVolledig persoon,
        final AdministratieveHandelingModel administratieveHandeling,
        final DatumTijdAttribuut tijdstipLaatsteWijziging,
        final SorteervolgordeAttribuut sorteervolgorde,
        final JaNeeAttribuut indicatieOnverwerktBijhoudingsvoorstelNietIngezeteneAanwezig,
        final DatumTijdAttribuut tijdstipLaatsteWijzigingGBASystematiek, final ActieModel actieInhoud)
    {
        super(persoon, administratieveHandeling, tijdstipLaatsteWijziging,
            sorteervolgorde, indicatieOnverwerktBijhoudingsvoorstelNietIngezeteneAanwezig,
            tijdstipLaatsteWijzigingGBASystematiek, actieInhoud);

    }

    // PdJ: handmatige wijziging
    // @Override
    public JaNeeAttribuut getIndicatieGegevensInOnderzoek() {
        throw new NotImplementedException("Handmatig toegevoegd, moet nog iets mee gebeuren.");
    }
}
