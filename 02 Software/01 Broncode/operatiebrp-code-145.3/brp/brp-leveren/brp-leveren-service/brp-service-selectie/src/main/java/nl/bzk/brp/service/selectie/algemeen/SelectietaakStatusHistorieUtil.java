/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.selectie.algemeen;

import java.sql.Timestamp;
import java.util.Set;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Selectietaak;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.SelectietaakStatusHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SelectietaakStatus;

/**
 * SelectietaakStatusHistorieUtil.
 */
public final class SelectietaakStatusHistorieUtil {
    private static final String GEWIJZIGD_DOOR_SYSTEEM = "Systeem";

    private SelectietaakStatusHistorieUtil() {
    }

    /**
     * @param selectietaak selectietaak
     * @param nuTijd nuTijd
     * @param statusNieuw statusNieuw
     * @param statusToelichting optionele statusToelichting
     */
    public static void updateTaakStatus(Selectietaak selectietaak, Timestamp nuTijd, SelectietaakStatus statusNieuw, final String statusToelichting) {
        final SelectietaakStatusHistorie hisTaakStatusVerval = vervalSelectietaakStatus(nuTijd, selectietaak.getSelectietaakStatusHistorieSet());
        final SelectietaakStatusHistorie hisTaakStatusNieuw = new SelectietaakStatusHistorie(hisTaakStatusVerval);
        hisTaakStatusNieuw.setStatusGewijzigdDoor(GEWIJZIGD_DOOR_SYSTEEM);
        hisTaakStatusNieuw.setDatumTijdRegistratie(nuTijd);
        hisTaakStatusNieuw.setStatus((short) statusNieuw.getId());
        hisTaakStatusNieuw.setStatusToelichting(statusToelichting);
        selectietaak.addSelectietaakStatusHistorieSet(hisTaakStatusNieuw);
        selectietaak.setStatus((short) statusNieuw.getId());
    }

    private static SelectietaakStatusHistorie vervalSelectietaakStatus(Timestamp nu, Set<SelectietaakStatusHistorie> selectietaakStatusHistorieSet) {
        //zoek niet vervallen his en verval
        for (SelectietaakStatusHistorie selectietaakStatusHistorie : selectietaakStatusHistorieSet) {
            if (selectietaakStatusHistorie.getDatumTijdVerval() == null) {
                selectietaakStatusHistorie.setDatumTijdVerval(nu);
                return selectietaakStatusHistorie;
            }
        }
        throw new NullPointerException("er moet een actueel selectie taak status his record zijn");
    }

}
