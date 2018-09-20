/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.autaut;

import javax.persistence.Entity;
import javax.persistence.Table;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.StatusHistorie;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.basis.AbstractBijhoudingssituatie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortDocument;


/**
 * Situatie waarin de bijhoudingsautorisatie de bijhouding toestaat.
 *
 * Bijhoudingsautorisaties zijn restrictief: alleen door iets expliciets toe te staan is bijhouding mogelijk. Situaties
 * waarin bijhouden is toegestaan, worden gekenmerkt door een soort actie, of een actie en een soort document. Om
 * bijvoorbeeld toe te staan dat de bijhoudingsautorisatie iets toestaat voor acties van 'soort 1' mits onderbouwt met
 * document van 'soort 2', dient er een bijhoudingssituatie te zijn met soort actie =1 en soort document =2.
 * Zijn er bij een bijhoudingsautorisaties g��n bijhoudingssituaties onderkend, dan mogen �lle situaties (c.q.: alle
 * combinaties van soort actie en soort document).
 * Een bijhoudingssituatie met een lege soort document bepaalt dat alle situaties gebaseerd op de soort actie zijn
 * toegestaan.
 *
 *
 *
 */
@Entity
@Table(schema = "AutAut", name = "Bijhsituatie")
public class Bijhoudingssituatie extends AbstractBijhoudingssituatie {

    /**
     * Constructor is protected, want de BRP zal geen stamgegevens beheren.
     *
     */
    protected Bijhoudingssituatie() {
        super();
    }

    /**
     * Constructor die direct alle attributen instantieert.
     *
     * @param bijhoudingsautorisatie bijhoudingsautorisatie van Bijhoudingssituatie.
     * @param soortAdministratieveHandeling soortAdministratieveHandeling van Bijhoudingssituatie.
     * @param soortDocument soortDocument van Bijhoudingssituatie.
     * @param bijhoudingssituatieStatusHis bijhoudingssituatieStatusHis van Bijhoudingssituatie.
     */
    protected Bijhoudingssituatie(final Bijhoudingsautorisatie bijhoudingsautorisatie,
            final SoortAdministratieveHandeling soortAdministratieveHandeling, final SoortDocument soortDocument,
            final StatusHistorie bijhoudingssituatieStatusHis)
    {
        super(bijhoudingsautorisatie, soortAdministratieveHandeling, soortDocument, bijhoudingssituatieStatusHis);
    }

}
