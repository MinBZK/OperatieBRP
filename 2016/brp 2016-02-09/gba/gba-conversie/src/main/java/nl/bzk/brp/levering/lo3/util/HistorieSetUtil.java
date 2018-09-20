/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.util;

import nl.bzk.brp.model.FormeleHistorieSet;
import nl.bzk.brp.model.MaterieleHistorieSet;
import nl.bzk.brp.model.basis.FormeelHistorisch;
import nl.bzk.brp.model.basis.FormeelVerantwoordbaar;
import nl.bzk.brp.model.basis.MaterieelHistorisch;
import nl.bzk.brp.model.basis.MaterieelVerantwoordbaar;
import nl.bzk.brp.model.operationeel.kern.HisOnderzoekModel;

/**
 * Historie set utilities.
 */
public final class HistorieSetUtil {

    private HistorieSetUtil() {
        // Niet instantieerbaar
    }

    /**
     * Retourneert het het actuele record. In een formele historie set is dit het record wat niet is vervallen.
     *
     * @param historie
     *            formele historie set
     * @param <T>
     *            historie type
     * @return het record wat niet is vervallen; null als er geen actueel record is
     */
    public static <T extends FormeelHistorisch & FormeelVerantwoordbaar<?>> T getActueleRecord(final FormeleHistorieSet<T> historie) {
        for (final T record : historie) {
            if (record.getVerantwoordingVerval() == null) {
                return record;
            }
        }
        return null;
    }

    /**
     * Retourneert het het actuele record. In een materiele historie set is dit het record wat niet is vervallen en dat
     * geen datum einde geldigheid heeft.
     *
     * @param historie
     *            materiele historie set
     * @param <T>
     *            historie type
     * @return het record wat niet is vervallen; null als er geen actueel record is
     */
    public static <T extends MaterieelHistorisch & MaterieelVerantwoordbaar<?>> T getActueleRecord(final MaterieleHistorieSet<T> historie) {
        for (final T record : historie) {
            if (record.getVerantwoordingVerval() == null
                && (record.getMaterieleHistorie() == null
                    || record.getMaterieleHistorie().getDatumEindeGeldigheid() == null
                    || record.getMaterieleHistorie().getDatumEindeGeldigheid().getWaarde() == null))
            {
                return record;
            }
        }
        return null;
    }

    /**
     * Geef het laatste record.
     *
     * @param onderzoekHistorie
     *            historie
     * @return laatste record, kan null zijn
     */
    public static HisOnderzoekModel getLaatsteRecord(final FormeleHistorieSet<HisOnderzoekModel> onderzoekHistorie) {
        HisOnderzoekModel laatste = null;

        for (final HisOnderzoekModel onderzoek : onderzoekHistorie) {
            if (laatste == null || onderzoek.getID() > laatste.getID()) {
                laatste = onderzoek;
            }
        }
        return laatste;
    }

}
