/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.business.toegang.leveringsautorisatie.cache;

import java.util.List;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Dienst;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Leveringsautorisatie;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.ToegangLeveringsautorisatie;

/**
 *
 */
public interface LeveringAutorisatieCache {

    /**
     * (Her)laad de cache gescheduled d.m.v spring cron expressie. Ook benaderbaar via jmx operatie.
     */
    void herlaad();

    /**
     * Geef alle geldige toegangLeveringsautorisaties uit cache.
     *
     * @return geldige leveringsautorisaties
     */
    List<ToegangLeveringsautorisatie> geefGeldigeToegangleveringsautorisaties();

    /**
     * Geef het leveringsautorisatie met naam.
     *
     * @param leveringautorisatieId de leveringsautorisatie naam
     * @return abonnnement het leveringsautorisatie met de gegeven naam
     */
    Leveringsautorisatie geefLeveringsautorisatie(final int leveringautorisatieId);

    /**
     * Geef het leveringsautorisatie met naam.
     *
     * @param leveringautorisatieId de leveringsautorisatie naam
     * @return abonnnement het leveringsautorisatie met de gegeven naam
     */
    Leveringsautorisatie geefLeveringsautorisatieZonderControle(final int leveringautorisatieId);

    /**
     * Geef toegangLeveringsautorisaties met leveringsautorisatie naam en partijcode.
     *
     * @param leveringautorisatieId het leveringautorisatieId
     * @param partijCode            de partij code
     * @return het toegang leveringsautorisatie
     */
    ToegangLeveringsautorisatie geefToegangleveringautorisatieZonderControle(final int leveringautorisatieId, final int partijCode);

    /**
     * Geef geldige toegangLeveringsautorisaties met leveringsautorisatie naam en partijcode.
     *
     * @param leveringautorisatieId het leveringautorisatieId
     * @param partijCode            de partij code
     * @return het toegang leveringsautorisatie
     */
    ToegangLeveringsautorisatie geefToegangleveringsautorisatie(final int leveringautorisatieId, final int partijCode);

    /**
     * Geef toegangLeveringsautorisatie voor ID.
     *
     * @param iD id van toegangLeveringsautorisatie
     * @return het toegang leveringsautorisatie
     */
    ToegangLeveringsautorisatie geefToegangleveringautorisatieZonderControle(Integer iD);

    /**
     * Geef de dienst op basis van dienstId
     *
     * @param dienstId id van de dienst
     * @return de dienst
     */
    Dienst geefDienst(final int dienstId);

    /**
     * herlaad via jmx.
     */
    void herlaadViaJmx();

}
