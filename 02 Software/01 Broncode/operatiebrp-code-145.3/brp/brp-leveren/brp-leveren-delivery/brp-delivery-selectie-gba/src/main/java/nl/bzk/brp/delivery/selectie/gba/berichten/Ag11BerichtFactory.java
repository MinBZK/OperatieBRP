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
 * Factory voor het maken van Ag11 berichten.
 */
@Component
public final class Ag11BerichtFactory {

    private BerichtFactory berichtFactory;
    private Lo3FilterRubriekRepository lo3FilterRubriekRepository;

    /**
     * Constructor.
     * @param berichtFactory bericht factory
     * @param lo3FilterRubriekRepository lo3 filterrubriek repository
     */
    @Inject
    public Ag11BerichtFactory(final BerichtFactory berichtFactory, final Lo3FilterRubriekRepository lo3FilterRubriekRepository) {
        this.berichtFactory = berichtFactory;
        this.lo3FilterRubriekRepository = lo3FilterRubriekRepository;
    }

    /**
     * Maak een message creator Sv11 bericht.
     * @param spontaanDienst dienst spontaan
     * @param persoonslijst persoonsgegevens
     * @return Sv11 bericht string
     */
    public String maakAg11Bericht(final Dienst spontaanDienst, final Persoonslijst persoonslijst) {
        // Volg de bericht strategy (ook voor ontkoppeling van migratie code)
        final Bericht bericht = berichtFactory.maakAg11Bericht(persoonslijst);

        // Converteer naar LO3
        bericht.converteerNaarLo3(new ConversieCache());

        // Bepaal filter rubrieken obv de dienstbundel voor spontaan
        bericht.filterRubrieken(lo3FilterRubriekRepository.haalLo3FilterRubriekenVoorDienstbundel(spontaanDienst.getDienstbundel().getId()));

        // Message creator
        return bericht.maakUitgaandBericht();
    }
}
