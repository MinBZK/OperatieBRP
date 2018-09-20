/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.business.handlers;

import javax.inject.Inject;

import nl.bzk.brp.bevraging.business.dto.BerichtContext;
import nl.bzk.brp.bevraging.business.dto.antwoord.BerichtAntwoord;
import nl.bzk.brp.bevraging.business.dto.verzoek.BerichtVerzoek;
import nl.bzk.brp.bevraging.business.service.BsnLocker;


/**
 * BSN Locker service stap, gebruikt de bsnLocker service om een logisch lock op BSN's te verkwijgen en te releasen.
 */
public class LockStap implements BerichtVerwerkingsStap {

    @Inject
    private BsnLocker bsnLocker;

    /**
     * Plaatst locks op de BSNs die in het bericht bevraagd/gemuteerd worden. Het resultaat geeft aan of de locking
     * succesvol was en dus de volgende stap kan worden genomen, of dat de stap faalde en dus de verdere verwerking
     * gestopt dient te worden. <br/>
     * <br/>
     * <b>Let op:</b> Deze stap plaatst de locks en voegt de betreffende connectie toe aan de ThreadLocal om verder
     * in het systeem nog gebruikt te worden en om in de navwerwerking van deze stap netjes deze connectie te
     * kunnen sluiten. In de naverwerking dient de connectie dan ook gesloten te worden en van de ThreadLocal
     * verwijderd te worden.
     *
     * @param verzoek het berichtverzoek waarvoor de verwerkingsstap dient te worden uitgevoerd.
     * @param context de context waarbinnen het berichtverzoek dient te worden uitgevoerd.
     * @param antwoord het antwoordbericht dat in de stap kan worden (aan)gevuld.
     * @param <T> Type van het antwoordbericht behorende bij het verzoek.
     * @return een boolean die aangeeft of de stap succesvol is uitgevoerd (true) of niet (false).
     */
    @Override
    public <T extends BerichtAntwoord> boolean voerVerwerkingsStapUitVoorBericht(final BerichtVerzoek<T> verzoek,
            final BerichtContext context, final T antwoord)
    {
        return bsnLocker.getLocks(context.getIngaandBerichtId(), verzoek.getReadBsnLocks(), verzoek.getWriteBsnLocks());
    }


    /**
     * De navwerking van deze stap dient er voor te zorgen dat de connectie (en de locks) die tijdens de
     * verwerking van de stap zijn aangemaakt, netjes worden opgeruimd. Hiervoor roept het de {@link
     * BsnLocker#unLock()} methode aan, daar deze correct de connectie uit de ThreadLocal haalt en netjes
     * afsluit (inclusief commit of rollback in het geval van fouten).
     * <br/>
     * Deze methode wordt na uitvoering van deze stap (en alle volgende stappen in het proces) uitgevoerd.
     *
     * @param verzoek het berichtverzoek waarvoor de verwerkingsstap dient te worden uitgevoerd.
     * @param context de context waarbinnen het berichtverzoek dient te worden uitgevoerd.
     */
    @Override
    public void naVerwerkingsStapVoorBericht(final BerichtVerzoek<? extends BerichtAntwoord> verzoek,
            final BerichtContext context)
    {
        bsnLocker.unLock();
    }

}
