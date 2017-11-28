/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.algemeen.autorisatie;


import java.util.List;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienst;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Leveringsautorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ToegangLeveringsAutorisatie;

/**
 * Service voor het ophalen van entiteiten die gerelateerd zijn aan autorisatie van leveringen : (toegang)leveringautorisaties, diensten etc.
 */
public interface LeveringsautorisatieService {

    /**
     * Haalt alle {@link ToegangLeveringsAutorisatie} objecten op.
     * @return lijst met {@link ToegangLeveringsAutorisatie}s
     */
    List<ToegangLeveringsAutorisatie> geefToegangLeveringsAutorisaties();

    /**
     * Geef de {@link Leveringsautorisatie} obv het gegeven id.
     * @param leveringautorisatieId id van de {@link Leveringsautorisatie}
     * @return de Leveringsautorisatie met de gegeven naam
     */
    Leveringsautorisatie geefLeveringautorisatie(final int leveringautorisatieId);

    /**
     * Haalt geldige {@link ToegangLeveringsAutorisatie} op.
     * @param leveringautorisatieId id van de {@link Leveringsautorisatie}
     * @param partijCode de partij code
     * @return de toegang leveringsautorisatie
     */
    ToegangLeveringsAutorisatie geefToegangLeveringsAutorisatie(final int leveringautorisatieId, final String partijCode);

    /**
     * Geef de {@link ToegangLeveringsAutorisatie} obv het gegeven id.
     * @param toegangLeveringsautorisatieId id van de {@link ToegangLeveringsAutorisatie}
     * @return de ToegangLeveringsAutorisatie
     */
    ToegangLeveringsAutorisatie geefToegangLeveringsAutorisatie(int toegangLeveringsautorisatieId);

    /**
     * Alle {@link ToegangLeveringsAutorisatie}s waarvoor de gegeven {@link Partij} geautoriseerd is.
     * @param partij de {@link Partij}
     * @return een lijst van {@link ToegangLeveringsAutorisatie}s
     */
    List<ToegangLeveringsAutorisatie> geefToegangLeveringAutorisaties(final Partij partij);

    /**
     * Bestaat de {@link Dienst} met id.
     * @param dienstId de dienstId
     * @return true als de {@link Dienst} bestaat, anders false
     */
    boolean bestaatDienst(final int dienstId);
}
