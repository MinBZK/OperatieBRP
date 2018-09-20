/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern;

import javax.persistence.Entity;
import javax.persistence.Table;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.AdministratienummerAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.BurgerservicenummerAttribuut;
import nl.bzk.brp.model.annotaties.GroepAccessor;
import nl.bzk.brp.model.basis.MaterieleHistorie;
import nl.bzk.brp.model.basis.ModelIdentificeerbaar;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.logisch.kern.HisPersoonNummerverwijzingGroep;
import nl.bzk.brp.model.logisch.kern.PersoonNummerverwijzingGroep;


/**
 *
 *
 */
@Entity
@Table(schema = "Kern", name = "His_PersNrverwijzing")
@GroepAccessor(dbObjectId = 10900)
public class HisPersoonNummerverwijzingModel extends AbstractHisPersoonNummerverwijzingModel implements
    ModelIdentificeerbaar<Integer>, HisPersoonNummerverwijzingGroep
{

    /**
     * Default constructor t.b.v. JPA
     */
    protected HisPersoonNummerverwijzingModel() {
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
    public HisPersoonNummerverwijzingModel(final PersoonHisVolledig persoonHisVolledig,
        final PersoonNummerverwijzingGroep groep, final MaterieleHistorie historie, final ActieModel actieInhoud)
    {
        super(persoonHisVolledig, groep, historie, actieInhoud);
    }

    /**
     * Copy Constructor die op basis van een C/D-laag klasse een C/D-laag klasse construeert.
     *
     * @param kopie backrefence instantie.
     */
    public HisPersoonNummerverwijzingModel(final AbstractHisPersoonNummerverwijzingModel kopie) {
        super(kopie);
    }

    /**
     * @param persoon                     persoon van HisPersoonNummerverwijzingModel
     * @param vorigeBurgerservicenummer   vorigeBurgerservicenummer van HisPersoonNummerverwijzingModel
     * @param volgendeBurgerservicenummer volgendeBurgerservicenummer van HisPersoonNummerverwijzingModel
     * @param vorigeAdministratienummer   vorigeAdministratienummer van HisPersoonNummerverwijzingModel
     * @param volgendeAdministratienummer volgendeAdministratienummer van HisPersoonNummerverwijzingModel
     * @param actieInhoud                 Actie inhoud; de actie die leidt tot dit nieuwe record.
     * @param historie                    De groep uit een bericht
     */
    public HisPersoonNummerverwijzingModel(final PersoonHisVolledig persoon,
        final BurgerservicenummerAttribuut vorigeBurgerservicenummer,
        final BurgerservicenummerAttribuut volgendeBurgerservicenummer,
        final AdministratienummerAttribuut vorigeAdministratienummer,
        final AdministratienummerAttribuut volgendeAdministratienummer, final ActieModel actieInhoud,
        final MaterieleHistorie historie)
    {
        super(persoon, vorigeBurgerservicenummer, volgendeBurgerservicenummer, vorigeAdministratienummer,
            volgendeAdministratienummer, actieInhoud, historie);
    }

}
