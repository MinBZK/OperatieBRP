/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import nl.bzk.brp.model.annotaties.GroepAccessor;
import nl.bzk.brp.model.hisvolledig.kern.PersoonVerstrekkingsbeperkingHisVolledig;
import nl.bzk.brp.model.logisch.kern.HisPersoonVerstrekkingsbeperkingIdentiteitGroep;


/**
 *
 *
 */
@Entity
@Table(schema = "Kern", name = "His_PersVerstrbeperking")
@GroepAccessor(dbObjectId = 9347)
public class HisPersoonVerstrekkingsbeperkingModel extends AbstractHisPersoonVerstrekkingsbeperkingModel implements
    HisPersoonVerstrekkingsbeperkingIdentiteitGroep {

    /**
     * Default constructor t.b.v. JPA
     */
    protected HisPersoonVerstrekkingsbeperkingModel() {
        super();
    }

    /**
     * Copy Constructor die op basis van een interface een C/D laag klasse construeert.
     *
     * @param persoonVerstrekkingsbeperkingHisVolledig
     *                    instantie van A-laag klasse.
     * @param actieInhoud Actie inhoud; de actie die leidt tot dit nieuwe record.
     */
    public HisPersoonVerstrekkingsbeperkingModel(
        final PersoonVerstrekkingsbeperkingHisVolledig persoonVerstrekkingsbeperkingHisVolledig,
        final ActieModel actieInhoud)
    {
        super(persoonVerstrekkingsbeperkingHisVolledig, actieInhoud);
    }

    /**
     * Copy Constructor die op basis van een C/D-laag klasse een C/D-laag klasse construeert.
     *
     * @param kopie backrefence instantie.
     */
    public HisPersoonVerstrekkingsbeperkingModel(final AbstractHisPersoonVerstrekkingsbeperkingModel kopie) {
        super(kopie);
    }

    /**
     * @return de objectsleutel
     */
    @Transient
    public final String getObjectSleutel() {
        return getPersoonVerstrekkingsbeperking().getID().toString();
    }

}
