/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.builder;

import java.util.Collections;
import java.util.List;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.brp.levering.lo3.bericht.Bericht;
import nl.bzk.brp.levering.lo3.bericht.BerichtFactory;
import nl.bzk.brp.levering.lo3.conversie.ConversieCache;

/**
 * Bouwt een Ha01 string bericht.
 */
public class Ha01Builder {
    private final BerichtFactory berichtFactory;

    /**
     * Constructor.
     * @param berichtFactory bericht factory
     */
    public Ha01Builder(final BerichtFactory berichtFactory) {
        this.berichtFactory = berichtFactory;
    }

    /**
     * Bouwt een Ha01 string bericht op basis van een persoonslijst.
     * @param persoonslijsten lijst van persoonslijsten (moet maar één bevatten)
     * @param gevraagdeRubrieken gevraagde rubrieken
     * @return geformatteerd Ha01 bericht
     */
    public String build(final List<Persoonslijst> persoonslijsten, List<String> gevraagdeRubrieken) {
        return build(
                persoonslijsten.stream()
                        .findFirst()
                        .orElseThrow(() -> new IllegalArgumentException("Kan geen meerdere persoonslijsten formatteren naar een Ha01 bericht.")),
                gevraagdeRubrieken);
    }

    private String build(final Persoonslijst persoonslijst, List<String> gevraagdeRubrieken) {
        Bericht bericht = berichtFactory.maakHa01Bericht(persoonslijst);
        bericht.converteerNaarLo3(new ConversieCache());
        bericht.filterRubrieken(gevraagdeRubrieken == null ? Collections.emptyList() : gevraagdeRubrieken);
        return bericht.maakUitgaandBericht();
    }
}
