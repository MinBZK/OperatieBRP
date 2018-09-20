/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.util;

import nl.bzk.brp.model.algemeen.stamgegeven.kern.NadereBijhoudingsaard;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.operationeel.kern.HisPersoonBijhoudingModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonIdentificatienummersModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonNummerverwijzingModel;

/**
 * Utility methodes voor PersoonHisVolledig.
 */
public final class PersoonUtil {

    private PersoonUtil() {
        // Niet instantieerbaar
    }

    /**
     * Geef het actuele a-nummer.
     *
     * @param persoon persoon
     * @return het actuele a-nummer
     */
    public static Long getAnummer(final PersoonHisVolledig persoon) {
        final Long result;
        if (persoon.getPersoonIdentificatienummersHistorie() == null) {
            result = null;
        } else {
            final HisPersoonIdentificatienummersModel idnrs = HistorieSetUtil.getActueleRecord(persoon.getPersoonIdentificatienummersHistorie());
            if (idnrs == null || idnrs.getAdministratienummer() == null) {
                result = null;
            } else {
                result = idnrs.getAdministratienummer().getWaarde();
            }
        }
        return result;
    }

    /**
     * Geef het actuele vorige a-nummer.
     *
     * @param persoon persoon
     * @return het actuele vorige a-nummer
     */
    public static Long getVorigeAnummer(final PersoonHisVolledig persoon) {
        final Long result;
        if (persoon.getPersoonNummerverwijzingHistorie() == null) {
            result = null;
        } else {
            final HisPersoonNummerverwijzingModel verwijzing = HistorieSetUtil.getActueleRecord(persoon.getPersoonNummerverwijzingHistorie());
            if (verwijzing == null || verwijzing.getVorigeAdministratienummer() == null) {
                result = null;
            } else {
                result = verwijzing.getVorigeAdministratienummer().getWaarde();
            }
        }
        return result;
    }

    /**
     * Geef het actuele volgende a-nummer.
     *
     * @param persoon persoon
     * @return het actuele volgende a-nummer
     */
    public static Long getVolgendeAnummer(final PersoonHisVolledig persoon) {
        final Long result;
        if (persoon.getPersoonNummerverwijzingHistorie() == null) {
            result = null;
        } else {
            final HisPersoonNummerverwijzingModel verwijzing = HistorieSetUtil.getActueleRecord(persoon.getPersoonNummerverwijzingHistorie());
            if (verwijzing == null || verwijzing.getVolgendeAdministratienummer() == null) {
                result = null;
            } else {
                result = verwijzing.getVolgendeAdministratienummer().getWaarde();
            }
        }
        return result;
    }

    /**
     * Geeft aan of persoon een nadere bijhoudingsaard FOUT heeft.
     *
     * @param persoon persoon
     * @return true als persoon een nadere bijhoudingsaard FOUT heeft, anders false
     */
    public static boolean isAfgevoerd(final PersoonHisVolledig persoon) {
        final boolean result;
        if (persoon.getPersoonBijhoudingHistorie() == null) {
            result = false;
        } else {
            final HisPersoonBijhoudingModel bijhouding = HistorieSetUtil.getActueleRecord(persoon.getPersoonBijhoudingHistorie());
            if (bijhouding == null || bijhouding.getNadereBijhoudingsaard() == null) {
                result = false;
            } else {
                result = NadereBijhoudingsaard.FOUT.equals(bijhouding.getNadereBijhoudingsaard().getWaarde());
            }
        }
        return result;
    }
}
