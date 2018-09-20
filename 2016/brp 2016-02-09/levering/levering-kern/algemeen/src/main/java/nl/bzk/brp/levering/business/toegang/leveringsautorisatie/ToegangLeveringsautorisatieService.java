/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.business.toegang.leveringsautorisatie;


import java.util.List;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Leveringsautorisatie;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.ToegangLeveringsautorisatie;

/**
 * De interface voor de leveringsautorisaties service.
 */
public interface ToegangLeveringsautorisatieService {

    /**
     * Haalt geldige toegangleveringautorisaties op
     *
     * @return geldige leveringsautorisaties
     */
    List<ToegangLeveringsautorisatie> geefGeldigeLeveringsautorisaties();

    /**
     * Geef het leveringsautorisatie met naam
     *
     * @param leveringautorisatieId de leveringsautorisatie naam
     * @return abonnnement het leveringsautorisatie met de gegeven naam
     */
    Leveringsautorisatie geefLeveringautorisatie(final int leveringautorisatieId);

    /**
     * Geef het leveringsautorisatie met naam (zonder controle)
     *
     * @param leveringautorisatieId de leveringsautorisatie naam
     * @return abonnnement het leveringsautorisatie met de gegeven naam
     */
    Leveringsautorisatie geefLeveringautorisatieZonderControle(final int leveringautorisatieId);

    /**
     * Bestaat het leveringsautorisatie met naam (zonder controle)
     *
     * @param leveringsautorisatieId de leveringsautorisatie naam
     * @return
     */
    boolean bestaatLeveringautorisatie(int leveringsautorisatieId);

    /**
     * Haalt toegang leveringsautorisatie op zonder geldigheids controles
     *
     * @param leveringsautorisatieId het leveringsautorisatieId
     * @param partijCode             de partij code
     * @return
     */
    ToegangLeveringsautorisatie geefToegangLeveringsautorisatieOpZonderControle(final int leveringsautorisatieId,
        final int partijCode);


    /**
     * Haalt geldig toegang leveringsautorisatie op.
     *
     * @param leveringautorisatieId de leveringsautorisatie naam
     * @param partijCode            de partij code
     * @return
     */
    ToegangLeveringsautorisatie geefToegangleveringautorisaties(final int leveringautorisatieId, final int partijCode);

    /**
     * Haalt geldig(?) toegang leveringsautorisatie op.
     *
     * @param toegangLeveringautorisatieId Het id van de toegangleveringautorisatie
     * @return
     */
    ToegangLeveringsautorisatie geefToegangleveringautorisatieZonderControle(Integer toegangLeveringautorisatieId);

    /**
     * Bestaat de dienst met id.
     *
     * @param dienstId de dienstId
     * @return true als de dienst bestaat, anders false
     */
    boolean bestaatDienst(final int dienstId);
}
