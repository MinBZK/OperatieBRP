/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.ist;

import javax.persistence.Entity;
import javax.persistence.Table;
import nl.bzk.brp.model.basis.ModelIdentificeerbaar;
import nl.bzk.brp.model.logisch.ist.StapelRelatie;
import nl.bzk.brp.model.operationeel.kern.RelatieModel;


/**
 * Het relateren van een stapel aan een relatie.
 * <p/>
 * Een stapel heeft normaliter betrekking op ��n relatie. Uitzonderingen hierop zijn omzettingen van huwelijk in een geregistreerd partnerschap (die leiden
 * tot twee relaties) en stapels met alleen onjuiste voorkomens (die leiden tot g��n relaties). Omdat ��n stapel altijd behoort tot (de persoonslijst van)
 * ��n persoon, kunnen meerdere stapels behoren tot ��n relatie.
 */
@Entity
@Table(schema = "IST", name = "StapelRelatie")
public class StapelRelatieModel extends AbstractStapelRelatieModel implements StapelRelatie,
    ModelIdentificeerbaar<Integer>
{

    /**
     * Standaard constructor (t.b.v. Hibernate/JPA).
     */
    protected StapelRelatieModel() {
        super();
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param stapel  stapel van Stapel \ Relatie.
     * @param relatie relatie van Stapel \ Relatie.
     */
    public StapelRelatieModel(final StapelModel stapel, final RelatieModel relatie) {
        super(stapel, relatie);
    }

}
