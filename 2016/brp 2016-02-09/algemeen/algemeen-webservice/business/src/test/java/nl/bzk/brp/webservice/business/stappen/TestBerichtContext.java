/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.webservice.business.stappen;

import java.util.Arrays;
import java.util.Collection;
import nl.bzk.brp.locking.LockingElement;
import nl.bzk.brp.locking.LockingMode;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PartijAttribuut;
import nl.bzk.brp.model.basis.CommunicatieIdMap;

/**
 * Test context, om gebruik in de verwerker e.d. te kunnen testen met een implementatie van de context.
 */
public class TestBerichtContext extends AbstractBerichtContext {

    /**
     * Constructor die de basis van de bericht context direct initialiseert.
     *
     * @param berichtIds          de ids van inkomende en daaraan gerelateerd uitgaande bericht.
     * @param partij              de partij die de bericht verwerking heeft aangeroepen.
     * @param berichtReferentieNr Referentie nummer uit de stuurgegevens van het binnengekomen bericht.
     * @param identificeerbareObj map van identificeerbare objecten die zijn gevonden in het bericht.
     */
    public TestBerichtContext(final BerichtenIds berichtIds, final Partij partij, final String berichtReferentieNr,
                              final CommunicatieIdMap identificeerbareObj)
    {
        super(berichtIds, new PartijAttribuut(partij), berichtReferentieNr, identificeerbareObj);
    }

    /**
     * Een eigen methode toegevoegd om te kijken hoe we die kunnen gebruiken bij de verwerker...
     *
     * @return
     */
    public boolean heeftEigenMethode() {
        return true;
    }

    @Override
    public LockingElement getLockingElement() {
        return LockingElement.PERSOON;
    }

    @Override
    public LockingMode getLockingMode() {
        return LockingMode.EXCLUSIVE;
    }

    @Override
    public Collection<Integer> getLockingIds() {
        return Arrays.asList(1, 2);
    }
}
