/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.prot;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Table;
import nl.bzk.brp.model.basis.ModelIdentificeerbaar;
import nl.bzk.brp.model.logisch.prot.LeveringsaantekeningPersoon;

/**
 * Het betrokken zijn van een Persoon in een Levering.
 *
 * Bij een Levering van Persoonsgegevens, zijn ��n of meer Personen het "onderwerp" van de Levering. Indien een Persoon
 * onderwerp is van een Levering, dan wordt de koppeling tussen deze Levering en de Persoon vastgelegd.
 *
 *
 *
 */
@Entity
@Table(schema = "Prot", name = "LevsaantekPers")
@Access(value = AccessType.FIELD)
public class LeveringsaantekeningPersoonModel extends AbstractLeveringsaantekeningPersoonModel implements LeveringsaantekeningPersoon,
        ModelIdentificeerbaar<Long>
{

    /**
     * Default lege constructor t.b.v. Hibernate / JPA
     *
     */
    protected LeveringsaantekeningPersoonModel() {
        super();
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param leveringsaantekening leveringsaantekening van Leveringsaantekening \ Persoon.
     * @param persoonId persoonId van Leveringsaantekening \ Persoon.
     */
    public LeveringsaantekeningPersoonModel(final LeveringsaantekeningModel leveringsaantekening, final Integer persoonId) {
        super(leveringsaantekening, persoonId);
    }

}
