/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.maakbericht;

import java.util.List;
import nl.bzk.brp.domain.berichtmodel.VerwerkPersoonBericht;
import nl.bzk.brp.service.maakbericht.algemeen.MaakBerichtParameters;

/**
 * Definieert de service interface voor het maken van bijgehoudenpersonen.
 */
public interface VerwerkPersoonBerichtFactory {

    /**
     * @param maakBerichtParameters maakBerichtParameters
     * @return de input voor een bericht
     */
    List<VerwerkPersoonBericht> maakBerichten(MaakBerichtParameters maakBerichtParameters);
}
