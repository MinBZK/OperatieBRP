/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.relateren.service;

import javax.inject.Inject;
import nl.bzk.brp.relateren.business.RelateerPersoon;
import nl.bzk.brp.relateren.service.bericht.RelateerPersoonBericht;
import org.springframework.stereotype.Component;

/**
 * De implementatie van de {@link RelateerService}.
 */
@Component("relateerService")
public final class RelateerServiceImpl implements RelateerService {

    private final RelateerPersoon relateerPersoon;

    /**
     * constructor
     * @param relateerPersoon
     */
    @Inject
    public RelateerServiceImpl(final RelateerPersoon relateerPersoon) {
        this.relateerPersoon = relateerPersoon;
    }

    @Override
    public void verwerkPersoonRelateerBericht(final RelateerPersoonBericht bericht) {
        if (bericht == null) {
            throw new NullPointerException("bericht mag niet null zijn");
        }
    }

    /**
     * geef relateer persoon terug.
     * @return RelateerPersoon
     */
    public RelateerPersoon getRelateerPersoon(){
        return relateerPersoon;
    }
}
