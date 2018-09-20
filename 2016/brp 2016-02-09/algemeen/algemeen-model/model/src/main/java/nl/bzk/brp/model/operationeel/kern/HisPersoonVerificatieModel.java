/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import nl.bzk.brp.model.hisvolledig.kern.PersoonVerificatieHisVolledig;
import nl.bzk.brp.model.logisch.kern.HisPersoonVerificatieStandaardGroep;
import nl.bzk.brp.model.logisch.kern.PersoonVerificatieStandaardGroep;


/**
 *
 *
 */
@Entity
@Table(schema = "Kern", name = "His_PersVerificatie")
public class HisPersoonVerificatieModel extends AbstractHisPersoonVerificatieModel implements
    HisPersoonVerificatieStandaardGroep
{

    /**
     * Default constructor t.b.v. JPA
     */
    protected HisPersoonVerificatieModel() {
        super();
    }

    /**
     * Copy Constructor die op basis van een interface een C/D laag klasse construeert.
     *
     * @param persoonVerificatieHisVolledig instantie van A-laag klasse.
     * @param groep                         groep
     * @param actieInhoud                   actie inhoud
     */
    public HisPersoonVerificatieModel(final PersoonVerificatieHisVolledig persoonVerificatieHisVolledig,
        final PersoonVerificatieStandaardGroep groep, final ActieModel actieInhoud)
    {
        super(persoonVerificatieHisVolledig, groep, actieInhoud);
    }

    /**
     * Copy Constructor die op basis van een C/D-laag klasse een C/D-laag klasse construeert.
     *
     * @param kopie instantie van A-laag klasse.
     */
    public HisPersoonVerificatieModel(final AbstractHisPersoonVerificatieModel kopie) {
        super(kopie);
    }

    /**
     * @return de objectsleutel
     */
    @Transient
    public final String getObjectSleutel() {
        return getPersoonVerificatie().getID().toString();
    }

}
