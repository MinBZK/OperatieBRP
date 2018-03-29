/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.selectie.gba.berichten;

import javax.inject.Inject;
import nl.bzk.brp.levering.lo3.bericht.Bericht;
import nl.bzk.brp.levering.lo3.bericht.BerichtFactory;
import org.springframework.stereotype.Component;

/**
 * Factory voor het maken van Sv11 berichten.
 */
@Component
public final class Sv11BerichtFactory {

    private BerichtFactory berichtFactory;

    /**
     * Constructor.
     * @param berichtFactory bericht factory
     */
    @Inject
    public Sv11BerichtFactory(final BerichtFactory berichtFactory) {
        this.berichtFactory = berichtFactory;
    }

    /**
     * Maak een message creator Sv11 bericht.
     * @return Sv11 bericht string
     */
    public String maakSv11Bericht() {
        // Volg de bericht strategy (ook voor ontkoppeling van migratie code)
        final Bericht bericht = berichtFactory.maakSv11Bericht();

        // Message creator
        return bericht.maakUitgaandBericht();
    }
}
