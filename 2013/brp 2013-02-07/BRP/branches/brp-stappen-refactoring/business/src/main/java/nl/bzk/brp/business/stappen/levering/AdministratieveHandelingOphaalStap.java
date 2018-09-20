/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.stappen.levering;

import javax.inject.Inject;

import nl.bzk.brp.business.stappen.StappenContext;
import nl.bzk.brp.dataaccess.repository.AdministratieveHandelingRepository;

import nl.bzk.brp.model.logisch.kern.Persoon;

/**
 * TODO: Add documentation
 */
public class AdministratieveHandelingOphaalStap extends AbstractLeveringStap<Persoon, LeveringResultaat> {

    @Inject
    private AdministratieveHandelingRepository administratieveHandelingRepository;

    @Override
    public boolean voerStapUit(final Persoon onderwerp, final StappenContext context,
                               final LeveringResultaat resultaat)
    {
        return onderwerp.getGeboorte()!=null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void voerNabewerkingStapUit(final Persoon onderwerp, final StappenContext context,
                                       final LeveringResultaat resultaat)
    {
        return;
    }
}
