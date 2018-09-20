/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern;

import javax.persistence.Entity;
import javax.persistence.Table;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaAttribuut;
import nl.bzk.brp.model.annotaties.GroepAccessor;
import nl.bzk.brp.model.basis.ModelIdentificeerbaar;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.logisch.kern.HisPersoonUitsluitingKiesrechtGroep;
import nl.bzk.brp.model.logisch.kern.PersoonUitsluitingKiesrechtGroep;


/**
 *
 *
 */
@Entity
@Table(schema = "Kern", name = "His_PersUitslKiesr")
@GroepAccessor(dbObjectId = 3519)
public class HisPersoonUitsluitingKiesrechtModel extends AbstractHisPersoonUitsluitingKiesrechtModel implements
    ModelIdentificeerbaar<Integer>, HisPersoonUitsluitingKiesrechtGroep
{

    /**
     * Default constructor t.b.v. JPA
     */
    protected HisPersoonUitsluitingKiesrechtModel() {
        super();
    }

    /**
     * Copy Constructor die op basis van een interface een C/D laag klasse construeert.
     *
     * @param persoonHisVolledig instantie van A-laag klasse.
     * @param groep              groep
     * @param actieInhoud        Actie inhoud; de actie die leidt tot dit nieuwe record.
     */
    public HisPersoonUitsluitingKiesrechtModel(final PersoonHisVolledig persoonHisVolledig,
        final PersoonUitsluitingKiesrechtGroep groep, final ActieModel actieInhoud)
    {
        super(persoonHisVolledig, groep, actieInhoud);
    }

    /**
     * Copy Constructor die op basis van een C/D-laag klasse een C/D-laag klasse construeert.
     *
     * @param kopie backrefence instantie.
     */
    public HisPersoonUitsluitingKiesrechtModel(final AbstractHisPersoonUitsluitingKiesrechtModel kopie) {
        super(kopie);
    }

    /**
     * @param persoon                       persoon van HisPersoonUitsluitingKiesrechtModel
     * @param indicatieUitsluitingKiesrecht indicatieUitsluitingKiesrecht van HisPersoonUitsluitingKiesrechtModel
     * @param datumVoorzienEindeUitsluitingKiesrecht
     *                                      datumVoorzienEindeUitsluitingKiesrecht van HisPersoonUitsluitingKiesrechtModel
     * @param actieInhoud                   Actie inhoud; de actie die leidt tot dit nieuwe record.
     */
    public HisPersoonUitsluitingKiesrechtModel(final PersoonHisVolledig persoon,
        final JaAttribuut indicatieUitsluitingKiesrecht,
        final DatumEvtDeelsOnbekendAttribuut datumVoorzienEindeUitsluitingKiesrecht, final ActieModel actieInhoud)
    {
        super(persoon, indicatieUitsluitingKiesrecht, datumVoorzienEindeUitsluitingKiesrecht, actieInhoud);
    }

}
