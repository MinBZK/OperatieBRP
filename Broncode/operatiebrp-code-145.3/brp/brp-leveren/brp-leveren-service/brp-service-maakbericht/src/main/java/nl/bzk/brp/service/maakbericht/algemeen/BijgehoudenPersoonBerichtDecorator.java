/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.maakbericht.algemeen;

import java.util.List;
import java.util.Map;
import nl.bzk.brp.domain.algemeen.Autorisatiebundel;
import nl.bzk.brp.domain.berichtmodel.BijgehoudenPersoon;
import nl.bzk.brp.domain.berichtmodel.VerwerkPersoonBericht;

/**
 * Callback interface voor het bouwen van het dienst-specifiek bericht. Internals voor het maken van bericht (specifieke persoon sortering oid) worden
 * zodoende niet exposed in de generieke maakbericht module.
 */
@FunctionalInterface
public interface BijgehoudenPersoonBerichtDecorator {

    /**
     * Maakt een lijst van {@link VerwerkPersoonBericht}en obv een lijst {@link BijgehoudenPersoon}en.
     * @param bijgehoudenPersoonList lijst bijgehouden personen.
     * @return lijst verwerk persoon berichten.
     */
    List<VerwerkPersoonBericht> build(Map<Autorisatiebundel, List<BijgehoudenPersoon>> bijgehoudenPersoonList);

}
