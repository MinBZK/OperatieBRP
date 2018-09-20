/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern;

import javax.persistence.Embeddable;

import nl.bzk.brp.model.algemeen.stamgegeven.kern.Geslachtsaanduiding;
import nl.bzk.brp.model.logisch.kern.PersoonGeslachtsaanduidingGroep;
import nl.bzk.brp.model.operationeel.kern.basis.AbstractPersoonGeslachtsaanduidingGroepModel;


/**
 * Gegevens over het geslacht van een Persoon.
 *
 * Verplicht aanwezig bij persoon
 *
 * Beide vormen van historie: geslacht(saanduiding) kan in de werkelijkheid veranderen, dus materieel bovenop de formele
 * historie.
 *
 *
 *
 */
@Embeddable
public class PersoonGeslachtsaanduidingGroepModel extends AbstractPersoonGeslachtsaanduidingGroepModel implements
        PersoonGeslachtsaanduidingGroep
{

    /**
     * Standaard constructor (t.b.v. Hibernate/JPA).
     *
     */
    protected PersoonGeslachtsaanduidingGroepModel() {
        super();
    }

    /**
     * Basis constructor die direct alle velden instantieert.
     *
     * @param geslachtsaanduiding geslachtsaanduiding van Geslachtsaanduiding.
     */
    public PersoonGeslachtsaanduidingGroepModel(final Geslachtsaanduiding geslachtsaanduiding) {
        super(geslachtsaanduiding);
    }

    /**
     * Copy constructor om vanuit het bericht model een instantie van het operationeel model te maken.
     *
     * @param persoonGeslachtsaanduidingGroep te kopieren groep.
     */
    public PersoonGeslachtsaanduidingGroepModel(final PersoonGeslachtsaanduidingGroep persoonGeslachtsaanduidingGroep) {
        super(persoonGeslachtsaanduidingGroep);
    }

}
