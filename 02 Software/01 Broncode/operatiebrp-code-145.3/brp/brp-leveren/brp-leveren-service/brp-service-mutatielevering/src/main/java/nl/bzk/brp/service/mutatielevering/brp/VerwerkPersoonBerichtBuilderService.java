/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.mutatielevering.brp;

import java.util.List;
import java.util.Map;
import nl.bzk.brp.domain.algemeen.Autorisatiebundel;
import nl.bzk.brp.domain.algemeen.Populatie;
import nl.bzk.brp.domain.berichtmodel.BijgehoudenPersoon;
import nl.bzk.brp.domain.berichtmodel.VerwerkPersoonBericht;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;

/**
 * VerwerkPersoonBerichtBuildService. Maakt berichten vanuit een map met bijgehouden personen gemaakt door maakbericht.
 */
@FunctionalInterface
interface VerwerkPersoonBerichtBuilderService {
    /**
     * @param administratieveHandeling administratieveHandeling
     * @param bijgehoudenPersonen bijgehoudenPersonen
     * @param teLeverenPersonenMap teLeverenPersonenMap
     * @return verwerk persoon berichten
     */
    List<VerwerkPersoonBericht> maakBerichten(long administratieveHandeling,
                                              Map<Autorisatiebundel, List<BijgehoudenPersoon>> bijgehoudenPersonen,
                                              Map<Autorisatiebundel, Map<Persoonslijst, Populatie>> teLeverenPersonenMap);
}
