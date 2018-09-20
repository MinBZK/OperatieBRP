/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.autaut;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Table;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PartijAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PartijRolAttribuut;
import nl.bzk.brp.model.basis.ModelIdentificeerbaar;
import nl.bzk.brp.model.logisch.autaut.ToegangBijhoudingsautorisatie;

/**
 * Vastlegging van autorisaties welke Partijen gerechtigd zijn bijhoudingen in te dienen voor andere Partijen.
 *
 * De Toegang bijhoudingsautorisatie geeft invulling aan de bewerkersconstructie voor bijhouders.
 *
 *
 *
 */
@Entity
@Table(schema = "AutAut", name = "ToegangBijhautorisatie")
@Access(value = AccessType.FIELD)
public class ToegangBijhoudingsautorisatieModel extends AbstractToegangBijhoudingsautorisatieModel implements ToegangBijhoudingsautorisatie,
        ModelIdentificeerbaar<Integer>
{

    /**
     * Default lege constructor t.b.v. Hibernate / JPA
     *
     */
    protected ToegangBijhoudingsautorisatieModel() {
        super();
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param geautoriseerde geautoriseerde van Toegang bijhoudingsautorisatie.
     * @param ondertekenaar ondertekenaar van Toegang bijhoudingsautorisatie.
     * @param transporteur transporteur van Toegang bijhoudingsautorisatie.
     */
    public ToegangBijhoudingsautorisatieModel(
        final PartijRolAttribuut geautoriseerde,
        final PartijAttribuut ondertekenaar,
        final PartijAttribuut transporteur)
    {
        super(geautoriseerde, ondertekenaar, transporteur);
    }

}
