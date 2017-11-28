/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.builder;

import java.util.List;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.brp.levering.lo3.bericht.BerichtFactory;
import nl.bzk.migratiebrp.util.gbav.GBACharacterSet;

/**
 * Bouwt een Xa01 string bericht.
 */
public class Xa01Builder {
    private static final int MAX_BERICHT_LENGTH = 19_000;
    private final BerichtFactory berichtFactory;

    /**
     * Constructor.
     * @param berichtFactory bericht factory
     */
    public Xa01Builder(final BerichtFactory berichtFactory) {
        this.berichtFactory = berichtFactory;
    }

    /**
     * Bouwt een Xa01 string bericht op basis van een persoonslijst.
     * @param persoonslijsten lijst van persoonslijsten
     * @param gevraagdeRubrieken gevraagde rubrieken
     * @return geformatteerd Xa01 bericht
     */
    public String build(final List<Persoonslijst> persoonslijsten, List<String> gevraagdeRubrieken) {
        return berichtFactory.maakXa01Bericht(persoonslijsten).maakUitgaandBericht(gevraagdeRubrieken);
    }

    /**
     * Checkt of het geproduceerde Xa01 binnen de gestelde limiet valt.
     * @param persoonslijsten persoonslijsten
     * @param gevraagdeRubrieken gevraagde rubrieken
     * @return boolean die aangeeft of het geproduceerde Xa01 binnen de gestelde limiet valt
     */
    public boolean isTooLong(final List<Persoonslijst> persoonslijsten, List<String> gevraagdeRubrieken) {
        return GBACharacterSet.convertTeletexStringToByteArray(build(persoonslijsten, gevraagdeRubrieken)).length > MAX_BERICHT_LENGTH;
    }
}
