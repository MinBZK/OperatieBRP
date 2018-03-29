/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service.toevalligegebeurtenis.verwerker.utils;

import java.text.DecimalFormat;
import nl.bzk.migratiebrp.bericht.model.brp.generated.DatumMetOnzekerheid;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatum;
import org.springframework.stereotype.Component;

/**
 * Utility voor het maken van attributen.
 */
@Component
public class AttribuutMaker {

    private static final DecimalFormat DATUM_MET_ONZEKERHEID_FORMAT = new DecimalFormat("00000000");

    private static final int MODULO_JAAR = 10_000;
    private static final int MODULE_MAAND = 100;

    private static final int START_JAAR = 0;
    private static final int EIND_JAAR = 4;
    private static final int START_MAAND = EIND_JAAR;
    private static final int EIND_MAAND = 6;
    private static final int START_DAG = EIND_MAAND;
    private static final int EIND_DAG = 8;

    private static final String SEPARATOR = "-";

    /**
     * Maak een DatumMetOnzekerheid attribuut.
     * @param brpDatum brp datum
     * @return datum met onzekerheid attribuut
     */
    public final DatumMetOnzekerheid maakDatumMetOnzekerheid(final BrpDatum brpDatum) {
        if (brpDatum == null) {
            return null;
        }

        final DatumMetOnzekerheid result = new DatumMetOnzekerheid();

        final Integer datum = brpDatum.getWaarde();
        final String formatted = DATUM_MET_ONZEKERHEID_FORMAT.format(datum);
        if (datum % MODULO_JAAR == 0) {
            result.setValue(formatted.substring(START_JAAR, EIND_JAAR));
        } else if (datum % MODULE_MAAND == 0) {
            result.setValue(formatted.substring(START_JAAR, EIND_JAAR) + SEPARATOR + formatted.substring(START_MAAND, EIND_MAAND));
        } else {
            result.setValue(
                    formatted.substring(START_JAAR, EIND_JAAR)
                            + SEPARATOR
                            + formatted.substring(START_MAAND, EIND_MAAND)
                            + SEPARATOR
                            + formatted.substring(START_DAG, EIND_DAG));
        }

        return result;
    }
}
