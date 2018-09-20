/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern;

import javax.persistence.Entity;
import javax.persistence.Table;

import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.logisch.kern.VerstrekkingDerde;
import nl.bzk.brp.model.operationeel.kern.basis.AbstractVerstrekkingDerdeModel;


/**
 * De expliciete toestemming van betrokkene voor het verstrekken van diens persoonsgegevens aan een derde.
 *
 * Ingeschreven personen in de BRP hebben de mogelijkheid om bepaalde categorie�n van afnemers uit te sluiten van het
 * verkrijgen van diens persoonsgegevens. Naast deze 'uitsluiting' is expliciete 'insluiting' ook mogelijk: hierdoor kan
 * een partij die uit oogpunt van de verstrekkingsbeperking uitgesloten zou zijn, alsnog gegevens ontvangen over de
 * persoon.
 *
 *
 *
 */
@Entity
@Table(schema = "Kern", name = "VerstrDerde")
public class VerstrekkingDerdeModel extends AbstractVerstrekkingDerdeModel implements VerstrekkingDerde {

    /**
     * Standaard constructor (t.b.v. Hibernate/JPA).
     *
     */
    protected VerstrekkingDerdeModel() {
        super();
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param persoon persoon van Verstrekking derde.
     * @param derde derde van Verstrekking derde.
     */
    public VerstrekkingDerdeModel(final PersoonModel persoon, final Partij derde) {
        super(persoon, derde);
    }

}
