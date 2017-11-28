/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.bevraging.gba.generiek;

import java.util.List;
import nl.bzk.brp.service.bevraging.zoekpersoongeneriek.ZoekPersoonGeneriekVerzoek;

/**
 * Generiek verzoek voor ZoekPersoon voor GBA.
 */
public interface ZoekPersoonGeneriekGbaVerzoek extends ZoekPersoonGeneriekVerzoek {

    /**
     * Geef de rubrieken waarop gezocht wordt. Formaat is aantal zoekrubrieken * 6.
     * @return rubrieken waarop gezocht wordt
     */
    List<String> getZoekRubrieken();

    /**
     * Geef de gevraagde rubrieken.
     * @return gevraagde rubrieken
     */
    List<String> getGevraagdeRubrieken();
}
