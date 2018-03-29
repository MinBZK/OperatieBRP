/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.tooling.apitest.service.dataaccess;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienst;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Leveringsautorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ToegangLeveringsAutorisatie;
import nl.bzk.brp.test.common.DienstSleutel;
import nl.bzk.brp.tooling.apitest.autorisatie.AutorisatieData;

/**
 * Service om autorisaties te laden.
 */
public interface AutorisatieStubService {

    /**
     * Zet de autorisatie data.
     *
     * @param autorisatieData data object.
     */
    void setData(AutorisatieData autorisatieData);

    /**
     * Geeft de leveringsautorisatie obv de naam.
     *
     * @param leveringsautorisatieNaam naam van de leveringsautorisatie
     * @return de leveringsautorisatie.
     */
    Leveringsautorisatie getLeveringsautorisatie(final String leveringsautorisatieNaam);

    /**
     * Geeft de dienst ahv de dienst bevattende leveringsautorisatie en het dienstId.
     *
     * @param leveringsAutorisatie leveringsautorisatie
     * @param dienstId             dienstId
     * @return dienst
     */
    Dienst getDienstUitLeveringsautorisatie(final Leveringsautorisatie leveringsAutorisatie, final int dienstId);

    Dienst getDienst(DienstSleutel dienstSleutel);

    ToegangLeveringsAutorisatie getToegangleveringsautorisatie(DienstSleutel dienstSleutel);
}
