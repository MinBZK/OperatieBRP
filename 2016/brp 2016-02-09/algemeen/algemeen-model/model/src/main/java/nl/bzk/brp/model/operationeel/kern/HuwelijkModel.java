/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import nl.bzk.brp.model.logisch.kern.Huwelijk;


/**
 * Het (aangaan van het en be�indigen van het) huwelijk zoals beschreven in Titel 5 van het Burgerlijk Wetboek Boek 1.
 * <p/>
 * Zie voor verdere toelichting de definitie en toelichting bij Huwelijk/Geregistreerd partnerschap en bij Relatie.
 */
@Entity
@DiscriminatorValue(value = "1")
public class HuwelijkModel extends AbstractHuwelijkModel implements Huwelijk {

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     */
    public HuwelijkModel() {
        super();
    }

    /**
     * Copy constructor om vanuit het bericht model een instantie van het operationeel model te maken.
     *
     * @param huwelijk Te kopieren object type.
     */
    public HuwelijkModel(final Huwelijk huwelijk) {
        super(huwelijk);
    }

}
