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
import nl.bzk.brp.model.hisvolledig.kern.PersoonNationaliteitHisVolledig;
import nl.bzk.brp.model.logisch.kern.HisPersoonNationaliteitStandaardGroep;
import nl.bzk.brp.model.logisch.kern.PersoonNationaliteitStandaardGroep;


/**
 *
 *
 */
@Entity
@Table(schema = "Kern", name = "His_PersNation")
public class HisPersoonNationaliteitModel extends AbstractHisPersoonNationaliteitModel implements
    HisPersoonNationaliteitStandaardGroep
{

    /**
     * Default constructor t.b.v. JPA
     */
    protected HisPersoonNationaliteitModel() {
        super();
    }

    /**
     * Copy Constructor die op basis van een interface een C/D laag klasse construeert.
     *
     * @param persoonNationaliteitHisVolledig
     *                    instantie van A-laag klasse.
     * @param groep       groep
     * @param historie    historie
     * @param actieInhoud actie inhoud
     */
    public HisPersoonNationaliteitModel(final PersoonNationaliteitHisVolledig persoonNationaliteitHisVolledig,
        final PersoonNationaliteitStandaardGroep groep, final MaterieleHistorie historie,
        final ActieModel actieInhoud)
    {
        super(persoonNationaliteitHisVolledig, groep, historie, actieInhoud);
    }

    /**
     * Copy Constructor die op basis van een C/D-laag klasse een C/D-laag klasse construeert.
     *
     * @param kopie instantie van A-laag klasse.
     */
    public HisPersoonNationaliteitModel(final AbstractHisPersoonNationaliteitModel kopie) {
        super(kopie);
    }

    /**
     * Toegevoegd omdat er anders alleen nationaliteiten getoond zouden worden met reden verkrijging / reden verlies.
     */
    @Override
    public boolean isMagGeleverdWorden() {
        return super.isMagGeleverdWorden()
            || (getPersoonNationaliteit().getNationaliteit() != null && getPersoonNationaliteit().getNationaliteit()
            .isMagGeleverdWorden());
    }


    /**
     * @return de objectsleutel
     */
    @Transient
    public final String getObjectSleutel() {
        return getPersoonNationaliteit().getID().toString();
    }
}
