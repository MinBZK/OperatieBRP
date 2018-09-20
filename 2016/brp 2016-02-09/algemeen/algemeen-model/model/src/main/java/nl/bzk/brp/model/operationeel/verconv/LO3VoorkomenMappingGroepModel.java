/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.verconv;

import javax.persistence.Embeddable;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.SorteervolgordeAttribuut;
import nl.bzk.brp.model.logisch.verconv.LO3VoorkomenMappingGroep;
import nl.bzk.brp.model.operationeel.kern.ActieModel;


/**
 *
 *
 */
@Embeddable
public class LO3VoorkomenMappingGroepModel extends AbstractLO3VoorkomenMappingGroepModel implements
    LO3VoorkomenMappingGroep
{

    /**
     * Standaard constructor (t.b.v. Hibernate/JPA).
     */
    protected LO3VoorkomenMappingGroepModel() {
        super();
    }

    /**
     * Basis constructor die direct alle velden instantieert.
     *
     * @param actie                 actie van Mapping.
     * @param lO3ConversieSortering lO3ConversieSortering van Mapping.
     */
    public LO3VoorkomenMappingGroepModel(final ActieModel actie, final SorteervolgordeAttribuut lO3ConversieSortering) {
        super(actie, lO3ConversieSortering);
    }

}
