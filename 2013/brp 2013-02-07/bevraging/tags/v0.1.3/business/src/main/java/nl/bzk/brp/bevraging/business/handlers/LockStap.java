/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.business.handlers;

import javax.inject.Inject;

import nl.bzk.brp.bevraging.business.berichtcmds.BrpBerichtCommand;
import nl.bzk.brp.bevraging.business.dto.antwoord.BRPAntwoord;
import nl.bzk.brp.bevraging.business.dto.verzoek.BRPVerzoek;
import nl.bzk.brp.bevraging.business.service.BsnLocker;


/**
 * BSN Locker service stap, gebruikt de bsnLocker service om een logisch lock op BSN's te verkwijgen en te releases.
 *
 * {@inheritDoc}
 */
public class LockStap implements BerichtVerwerkingsStap {

    @Inject
    private BsnLocker bsnLocker;

    @Override
    public boolean voerVerwerkingsStapUitVoorBericht(
            final BrpBerichtCommand<? extends BRPVerzoek, ? extends BRPAntwoord> bericht)
    {
        return bsnLocker.getLocks(bericht.getContext().getBerichtId()
                , bericht.getVerzoek().getReadBSNLocks()
                , bericht.getVerzoek().getWriteBSNLocks());
    }

    @Override
    public void naVerwerkingsStapVoorBericht(
            final BrpBerichtCommand<? extends BRPVerzoek, ? extends BRPAntwoord> bericht)
    {
        bsnLocker.unLock();
    }

}
