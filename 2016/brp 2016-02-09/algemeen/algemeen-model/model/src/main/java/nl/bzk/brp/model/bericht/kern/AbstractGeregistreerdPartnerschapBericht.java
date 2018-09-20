/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.bericht.kern;

import javax.annotation.Generated;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortRelatie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortRelatieAttribuut;
import nl.bzk.brp.model.basis.BrpObject;
import nl.bzk.brp.model.logisch.kern.GeregistreerdPartnerschapBasis;

/**
 * Het (aangaan van het en beëindigen van het) geregistreerd partnerschap zoals beschreven in Titel 5A van het
 * Burgerlijk Wetboek Boek 1.
 *
 * Zie voor verdere toelichting de definitie en toelichting bij Huwelijk/Geregistreerd partnerschap, en bij Relatie.
 *
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.BerichtModelGenerator")
public abstract class AbstractGeregistreerdPartnerschapBericht extends HuwelijkGeregistreerdPartnerschapBericht implements BrpObject,
        GeregistreerdPartnerschapBasis
{

    /**
     * Constructor die het discriminator attribuut zet of doorgeeft.
     *
     */
    public AbstractGeregistreerdPartnerschapBericht() {
        super(new SoortRelatieAttribuut(SoortRelatie.GEREGISTREERD_PARTNERSCHAP));
    }

}
