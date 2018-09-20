/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.ist;

import javax.persistence.Embeddable;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.logisch.ist.StapelVoorkomenCategorieOudersGroep;


/**
 *
 *
 */
@Embeddable
public class StapelVoorkomenCategorieOudersGroepModel extends AbstractStapelVoorkomenCategorieOudersGroepModel
    implements StapelVoorkomenCategorieOudersGroep
{

    /**
     * Standaard constructor (t.b.v. Hibernate/JPA).
     */
    protected StapelVoorkomenCategorieOudersGroepModel() {
        super();
    }

    /**
     * Basis constructor die direct alle velden instantieert.
     *
     * @param rubriek6210DatumIngangFamilierechtelijkeBetrekking
     *         rubriek6210DatumIngangFamilierechtelijkeBetrekking van Categorie ouders.
     */
    public StapelVoorkomenCategorieOudersGroepModel(
        final DatumEvtDeelsOnbekendAttribuut rubriek6210DatumIngangFamilierechtelijkeBetrekking)
    {
        super(rubriek6210DatumIngangFamilierechtelijkeBetrekking);
    }

}
