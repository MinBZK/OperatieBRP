/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.algemeen;

import nl.bzk.brp.domain.berichtmodel.VerwerkPersoonBericht;

/**
 * MaakPersoonBerichtService.
 */
@FunctionalInterface
public interface MaakPersoonBerichtService {

    /**
     * @param verwerkPersoonBericht verwerkPersoonBericht
     * @return het bericht.
     * @throws StapException fout bij maken bericht
     */
    String maakPersoonBericht(VerwerkPersoonBericht verwerkPersoonBericht) throws StapException;
}
