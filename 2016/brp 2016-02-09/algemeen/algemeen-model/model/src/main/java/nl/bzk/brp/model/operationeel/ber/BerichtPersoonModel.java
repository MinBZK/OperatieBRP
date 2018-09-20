/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.ber;

import javax.persistence.Entity;
import javax.persistence.Table;
import nl.bzk.brp.model.basis.ModelIdentificeerbaar;
import nl.bzk.brp.model.logisch.ber.BerichtPersoon;


/**
 * De opsomming van in een antwoord betrokken personen.
 * <p/>
 * Het betreft een constructie met als doel het genereren van de gewenste structuren in de XSD's.
 */
@Entity
@Table(schema = "Ber", name = "BerPers")
public class BerichtPersoonModel extends AbstractBerichtPersoonModel implements BerichtPersoon,
    ModelIdentificeerbaar<Long>
{

    /**
     * Default lege constructor t.b.v. Hibernate / JPA
     */
    protected BerichtPersoonModel() {
        super();
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param bericht   bericht van Bericht \ Persoon.
     * @param persoonId persoonId van Bericht \ Persoon.
     */
    public BerichtPersoonModel(final BerichtModel bericht, final Integer persoonId) {
        super(bericht, persoonId);
    }

}
