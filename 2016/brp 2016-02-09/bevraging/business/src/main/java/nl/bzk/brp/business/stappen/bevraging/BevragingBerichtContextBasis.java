/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.stappen.bevraging;

import java.util.Collection;
import nl.bzk.brp.levering.model.Leveringinformatie;
import nl.bzk.brp.locking.LockingMode;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.basis.CommunicatieIdMap;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.webservice.business.stappen.AbstractBerichtContextBasis;
import nl.bzk.brp.webservice.business.stappen.BerichtenIds;

/**
 * De context klasse om gegevens tussen stappen uit te wisselen in de bevraging ten behoeve van bijhouding verwerker.
 */
public class BevragingBerichtContextBasis extends AbstractBerichtContextBasis implements BevragingBerichtContext {

    // TODO naar een specifieke bevraging voor levering context?
    private Leveringinformatie leveringinformatie;
    private PersoonHisVolledigImpl persoonHisVolledigImpl;

    /**

     * Constructor die de basis van de bericht context direct initialiseert.
     *
     * @param berichtIds          de ids van inkomende en daaraan gerelateerd uitgaande bericht.
     * @param afzender            de partij die de bericht verwerking heeft aangeroepen.
     * @param berichtReferentieNr Referentie nummer uit de stuurgegevens van het binnengekomen bericht.
     * @param identificeerbareObj map van identificeerbare objecten die zijn gevonden in het bericht.
     */
    public BevragingBerichtContextBasis(final BerichtenIds berichtIds, final Partij afzender,
        final String berichtReferentieNr, final CommunicatieIdMap identificeerbareObj) {
        super(berichtIds, afzender, berichtReferentieNr, identificeerbareObj);
    }

    @Override
    public final Collection<Integer> getLockingIds() {
        // TODO: Locking voor bevraging moet opnieuw uitgedacht worden
        return null;
    }

    @Override
    public final LockingMode getLockingMode() {
        return LockingMode.SHARED;
    }

    public Leveringinformatie getLeveringinformatie() {
        return leveringinformatie;
    }

    public final void setLeveringinformatie(final Leveringinformatie leveringinformatie) {
        this.leveringinformatie = leveringinformatie;
    }


    public PersoonHisVolledigImpl getPersoonHisVolledigImpl() {
        return persoonHisVolledigImpl;
    }

    public final void setPersoonHisVolledigImpl(final PersoonHisVolledigImpl persoonHisVolledig) {
        this.persoonHisVolledigImpl = persoonHisVolledig;
    }
}
