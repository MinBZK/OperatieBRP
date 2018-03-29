/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.selectie.gba.berichten;

import java.util.Collections;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.brp.levering.lo3.bericht.Bericht;
import nl.bzk.brp.levering.lo3.bericht.BerichtFactory;
import nl.bzk.brp.levering.lo3.conversie.ConversieCache;
import nl.bzk.brp.levering.lo3.util.PersoonUtil;
import org.springframework.stereotype.Component;

/**
 * Factory voor het maken van Sf01 berichten.
 */
@Component
public final class Sf01BerichtFactory {

    private static final Logger LOG = LoggerFactory.getLogger();
    private static final String A_NUMMER_RUBRIEK = "01.01.10";
    private BerichtFactory berichtFactory;

    /**
     * Constructor.
     * @param berichtFactory bericht factory
     */
    @Inject
    public Sf01BerichtFactory(final BerichtFactory berichtFactory) {
        this.berichtFactory = berichtFactory;
    }

    /**
     * Maak een message creator Sf01 bericht.
     * @param persoonslijst persoonsgegevens
     * @param registratieGemeente de gemeente waar de persoon geregistreerd staat bij een blokkering
     * @return Sf01 bericht string
     */
    public String maakSf01Bericht(final Persoonslijst persoonslijst, final String registratieGemeente) {
        // Volg de bericht strategy (ook voor ontkoppeling van migratie code)
        final Bericht bericht = berichtFactory.maakSf01Bericht(persoonslijst);

        String bijhoudingPartijcode = PersoonUtil.getBijhoudingPartijcode(persoonslijst);
        LOG.info("Controle bijhoudingpartij is ook registratiegemeente: {}", registratieGemeente.equalsIgnoreCase(bijhoudingPartijcode));

        // Converteer naar LO3
        bericht.converteerNaarLo3(new ConversieCache());

        // Filter alleen het anummer eruit
        bericht.filterRubrieken(Collections.singletonList(A_NUMMER_RUBRIEK));

        // Message creator
        return bericht.maakUitgaandBericht();
    }
}
