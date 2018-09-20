/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern;

import javax.persistence.Entity;
import javax.persistence.Table;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.GegevenswaardeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.ElementAttribuut;
import nl.bzk.brp.model.basis.ModelIdentificeerbaar;
import nl.bzk.brp.model.logisch.kern.GegevenInTerugmelding;


/**
 *
 *
 */
@Entity
@Table(schema = "Kern", name = "GegevenInTerugmelding")
public class GegevenInTerugmeldingModel extends AbstractGegevenInTerugmeldingModel implements GegevenInTerugmelding,
    ModelIdentificeerbaar<Integer>
{

    /**
     * Standaard constructor (t.b.v. Hibernate/JPA).
     */
    protected GegevenInTerugmeldingModel() {
        super();
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param terugmelding      terugmelding van Gegeven in terugmelding.
     * @param gegevenselement   gegevenselement van Gegeven in terugmelding.
     * @param betwijfeldeWaarde betwijfeldeWaarde van Gegeven in terugmelding.
     * @param verwachteWaarde   verwachteWaarde van Gegeven in terugmelding.
     */
    public GegevenInTerugmeldingModel(
        final TerugmeldingModel terugmelding,
        final ElementAttribuut gegevenselement,
        final GegevenswaardeAttribuut betwijfeldeWaarde,
        final GegevenswaardeAttribuut verwachteWaarde)
    {
        super(terugmelding, gegevenselement, betwijfeldeWaarde, verwachteWaarde);
    }

}
