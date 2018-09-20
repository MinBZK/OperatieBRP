/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import nl.bzk.brp.model.basis.MaterieleHistorie;
import nl.bzk.brp.model.hisvolledig.kern.PersoonVoornaamHisVolledig;
import nl.bzk.brp.model.logisch.kern.HisPersoonVoornaamStandaardGroep;
import nl.bzk.brp.model.logisch.kern.PersoonVoornaamStandaardGroep;


/**
 *
 *
 */
@Entity
@Table(schema = "Kern", name = "His_PersVoornaam")
public class HisPersoonVoornaamModel extends AbstractHisPersoonVoornaamModel implements HisPersoonVoornaamStandaardGroep {

    /**
     * Default constructor t.b.v. JPA
     */
    protected HisPersoonVoornaamModel() {
        super();
    }

    /**
     * Copy Constructor die op basis van een interface een C/D laag klasse construeert.
     *
     * @param persoonVoornaamHisVolledig instantie van A-laag klasse.
     * @param groep                      groep
     * @param historie                   historie
     * @param actieInhoud                actie inhoud
     */
    public HisPersoonVoornaamModel(final PersoonVoornaamHisVolledig persoonVoornaamHisVolledig,
        final PersoonVoornaamStandaardGroep groep, final MaterieleHistorie historie, final ActieModel actieInhoud)
    {
        super(persoonVoornaamHisVolledig, groep, historie, actieInhoud);
    }

    /**
     * Copy Constructor die op basis van een C/D-laag klasse een C/D-laag klasse construeert.
     *
     * @param kopie instantie van A-laag klasse.
     */
    public HisPersoonVoornaamModel(final AbstractHisPersoonVoornaamModel kopie) {
        super(kopie);
    }

    /**
     * @return de objectsleutel
     */
    @Transient
    public final String getObjectSleutel() {
        return getPersoonVoornaam().getID().toString();
    }

}
