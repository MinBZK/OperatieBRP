/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.predikaatview;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.basis.DatumBasisAttribuut;

/**
 * Basis klasse voor comparators die {@link nl.bzk.brp.model.basis.HistorieEntiteit}en kunnen vergelijken.
 */
public abstract class AbstractHistorieEntiteitComparator {

    /**
     * Datum representatie voor 'eeuwigheid' of wel de hoogst mogelijke datum in de toekomst.
     */
    protected static final DatumAttribuut     EEUWIGHEID_DATUM      = new DatumAttribuut(99991231);
    /**
     * Datum en tijd representatie voor 'eeuwigheid' of wel de hoogst mogelijk datum en tijd in de toekomst.
     */
    protected static final DatumTijdAttribuut EEUWIGHEID_DATUM_TIJD = new DatumTijdAttribuut(EEUWIGHEID_DATUM.toDate());

    /**
     * Geef de datum tijd zelf terug, behalve als die geen waarde heeft, dan de 'eeuwigheid'.
     *
     * @param datumTijd datum tijd
     * @return datum tijd of eeuwigheid
     */
    protected final DatumTijdAttribuut datumTijdOfEeuwigheid(final DatumTijdAttribuut datumTijd) {
        final DatumTijdAttribuut datumTijdOfEeuwigheid;
        if (datumTijd == null) {
            datumTijdOfEeuwigheid = EEUWIGHEID_DATUM_TIJD;
        } else {
            datumTijdOfEeuwigheid = datumTijd;
        }
        return datumTijdOfEeuwigheid;
    }

    /**
     * Geef de datum zelf terug, behalve als die geen waarde heeft, dan de 'eeuwigheid'.
     *
     * @param datum datum
     * @return datum of eeuwigheid
     */
    protected final DatumBasisAttribuut datumOfEeuwigheid(final DatumEvtDeelsOnbekendAttribuut datum) {
        final DatumBasisAttribuut datumOfEeuwigheid;
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
    protected final int vergelijk(final Comparable links, final Comparable rechts) {
        if (links == null || rechts == null) {
            return 0;
        }

        return rechts.compareTo(links);
    }
}
