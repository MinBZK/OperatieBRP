/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.selectie.gba.berichten;

import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienst;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.brp.gba.dataaccess.Lo3FilterRubriekRepository;
import nl.bzk.brp.levering.lo3.bericht.Bericht;
import nl.bzk.brp.levering.lo3.bericht.BerichtFactory;
import nl.bzk.brp.levering.lo3.conversie.ConversieCache;
import org.springframework.stereotype.Component;

/**
 * Factory voor het maken van Sv01 berichten.
 */
@Component
public final class Sv01BerichtFactory {

    private Lo3FilterRubriekRepository lo3FilterRubriekRepository;
    private BerichtFactory berichtFactory;

    /**
     * Constructor.
     * @param berichtFactory bericht factory
     * @param lo3FilterRubriekRepository lo3 filterrubriek repository
     */
    @Inject
    public Sv01BerichtFactory(final BerichtFactory berichtFactory, final Lo3FilterRubriekRepository lo3FilterRubriekRepository) {
        this.berichtFactory = berichtFactory;
        this.lo3FilterRubriekRepository = lo3FilterRubriekRepository;
    }

    /**
     * Maak een message creator Sv01 bericht.
     * @param dienst dienst
     * @param persoonslijst persoonsgegevens
     * @return Sv01 bericht string
     */
    public String maakSv01Bericht(final Dienst dienst, final Persoonslijst persoonslijst) {
        // Volg de bericht strategy (ook voor ontkoppeling van migratie code)
        final Bericht bericht = berichtFactory.maakSv01Bericht(persoonslijst);

        // Converteer naar LO3
        bericht.converteerNaarLo3(new ConversieCache());

        // Bepaal filter rubrieken obv de dienstbundel voor spontaan
        bericht.filterRubrieken(lo3FilterRubriekRepository.haalLo3FilterRubriekenVoorDienstbundel(dienst.getDienstbundel().getId()));

        // Message creator
        return bericht.maakUitgaandBericht();
    }
}
