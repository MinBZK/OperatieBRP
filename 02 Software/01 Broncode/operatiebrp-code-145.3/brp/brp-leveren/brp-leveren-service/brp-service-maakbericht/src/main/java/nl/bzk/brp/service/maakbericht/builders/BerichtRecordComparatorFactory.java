/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.maakbericht.builders;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Comparator;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.domain.leveringmodel.MetaRecord;
import nl.bzk.brp.service.maakbericht.algemeen.Berichtgegevens;

/**
 * Utilklasse om MetaRecords te vergelijken voor leveren.
 */
final class BerichtRecordComparatorFactory {

    /**
     * Datum representatie voor 'eeuwigheid' of wel de hoogst mogelijke datum in de toekomst.
     */
    private static final Integer EEUWIGHEID_DATUM = 99_991_231;


    /**
     * Datum en tijd representatie voor 'eeuwigheid' of wel de hoogst mogelijk datum en tijd in de toekomst.
     */
    private static final ZonedDateTime EEUWIGHEID_DATUM_TIJD = ZonedDateTime.ofLocal(LocalDateTime.MAX, DatumUtil.BRP_ZONE_ID, null);

    private BerichtRecordComparatorFactory() {

    }

    /**
     * @param berichtgegevens berichtgegevens
     * @return de vergelijker voor berichtrecords
     */
    static Comparator<MetaRecord> maakComparator(final Berichtgegevens berichtgegevens) {
        return new MetaRecordComparator(berichtgegevens);
    }

    /**
     * Geef de datum tijd zelf terug, behalve als die geen waarde heeft, dan de 'eeuwigheid'.
     * @param datumTijd datum tijd
     * @return datum tijd of eeuwigheid
     */
    static ZonedDateTime datumTijdOfEeuwigheid(final ZonedDateTime datumTijd) {
        final ZonedDateTime datumTijdOfEeuwigheid;
        if (datumTijd == null) {
            datumTijdOfEeuwigheid = EEUWIGHEID_DATUM_TIJD;
        } else {
            datumTijdOfEeuwigheid = datumTijd;
        }
        return datumTijdOfEeuwigheid;
    }

    /**
     * Geef de datum zelf terug, behalve als die geen waarde heeft, dan de 'eeuwigheid'.
     * @param datum datum
     * @return datum of eeuwigheid
     */
    static Integer datumOfEeuwigheid(final Integer datum) {
        final Integer datumOfEeuwigheid;
        if (datum == null) {
            datumOfEeuwigheid = EEUWIGHEID_DATUM;
        } else {
            datumOfEeuwigheid = datum;
        }
        return datumOfEeuwigheid;
    }

    /**
     * Vergelijking van twee argumenten, datum of datum tijd. Let op: zowel links als rechts mag niet null zijn!
     * @param links waarde om te vergelijken.
     * @param rechts waarde om te vergelijken.
     * @return waarde die aangeeft of linker verlijkingswaarde groter, gelijk of kleiner is dan rechter waarde.
     */
    static int vergelijk(final Comparable links, final Comparable rechts) {
        if (links == null || rechts == null) {
            return 0;
        }

        return rechts.compareTo(links);
    }

}
