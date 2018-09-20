/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern;

import javax.persistence.Entity;
import javax.persistence.Table;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.GeslachtsaanduidingAttribuut;
import nl.bzk.brp.model.basis.MaterieleHistorie;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.logisch.kern.HisPersoonGeslachtsaanduidingGroep;
import nl.bzk.brp.model.logisch.kern.PersoonGeslachtsaanduidingGroep;


/**
 *
 *
 */
@Entity
@Table(schema = "Kern", name = "His_PersGeslachtsaand")
public class HisPersoonGeslachtsaanduidingModel extends AbstractHisPersoonGeslachtsaanduidingModel implements
    HisPersoonGeslachtsaanduidingGroep
{

    /**
     * Default constructor t.b.v. JPA
     */
    protected HisPersoonGeslachtsaanduidingModel() {
        super();
    }

    /**
     * Copy Constructor die op basis van een interface een C/D laag klasse construeert.
     *
     * @param persoonHisVolledig instantie van A-laag klasse.
     * @param groep              groep
     * @param historie           historie
     * @param actieInhoud        actie inhoud
     */
    public HisPersoonGeslachtsaanduidingModel(final PersoonHisVolledig persoonHisVolledig,
        final PersoonGeslachtsaanduidingGroep groep, final MaterieleHistorie historie, final ActieModel actieInhoud)
    {
        super(persoonHisVolledig, groep, historie, actieInhoud);
    }

    /**
     * Copy Constructor die op basis van een C/D-laag klasse een C/D-laag klasse construeert.
     *
     * @param kopie instantie van A-laag klasse.
     */
    public HisPersoonGeslachtsaanduidingModel(final AbstractHisPersoonGeslachtsaanduidingModel kopie) {
        super(kopie);
    }

    /**
     * @param persoon             persoon van HisPersoonGeslachtsaanduidingModel
     * @param geslachtsaanduiding geslachtsaanduiding van HisPersoonGeslachtsaanduidingModel
     * @param actieInhoud         Actie inhoud; de actie die leidt tot dit nieuwe record.
     * @param historie            De groep uit een bericht
     */
    public HisPersoonGeslachtsaanduidingModel(final PersoonHisVolledig persoon,
        final GeslachtsaanduidingAttribuut geslachtsaanduiding, final ActieModel actieInhoud,
        final MaterieleHistorie historie)
    {
        super(persoon, geslachtsaanduiding, actieInhoud, historie);
    }
}
