/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.tooling.apitest.jbehave;

import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import org.jbehave.core.annotations.When;
import org.jbehave.core.steps.Steps;
import org.springframework.util.Assert;

/**
 * Steps specifiek voor mutatielevering.
 */
public final class MutatieleveringSteps extends Steps {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    /**
     * @param bsn bsn
     */
    @When("voor persoon $bsn wordt de initiele vulling geleverd")
    public void leverSynchronisatieNaInitieleVullingVoorPersoon(final String bsn) {
        Assert.notNull(bsn, "BSN niet gevuld");
        StoryController.getOmgeving().getApiService().getMutatieleveringApiService().leverInitieleVullingVoorPersoon(bsn);
    }

    /**
     * ==== Laatste handeling nogmaals leveren Dit is de verkorte versie van link:#handeling_nogmaals_leveren[handeling nogmaals leveren] om de laatste
     * handeling mogmaals te leveren. Deze step leest functioneler dan `0 na laatste handeling`.
     *
     * @param bsn het burgerservicenummer van de persoon, waarvan een handeling opnieuwe verwerkt worden door de mutatielevering software.
     */
    @When("voor persoon $bsn wordt de laatste handeling geleverd")
    public void leverLaatsteHandelingVoorPersoon(final String bsn) {
        Assert.notNull(bsn, "BSN niet gevuld");
        StoryController.getOmgeving().getApiService().getMutatieleveringApiService().leverLaatsteHandelingVoorPersoon(bsn);
    }

    /**
     * ==== Handeling nogmaals leveren Simuleert het (nogmaals) leveren van een administratieve handeling die voor een wijziging van een persoonslijst
     * heeft gezorgd. Dit houdt in dat de kolom `tslev` van de tabel kern.admhnd wordt leeg gemaakt, en dat de de waarde van de `ID` kolom op de queue voor
     * verwerkte administratieve handelingen wordt geplaatst. De mutatielevering software verwerkt deze administratieve handeling alsof deze zo juist door
     * de bijhouding is verwerkt.
     *
     * @param bsn   het burgerservicenummer van de persoon, waarvan een handeling opnieuwe verwerkt worden door de mutatielevering software.
     * @param index in aanduiding van de hoeveel na laatste handeling opnieuw geleverd gaat worden. `1` is de een na laatste, `2` de twee na laatste, etc.
     *              Technisch gezien kan `0` voor de laatste worden opgegeven, maar je kan ook link:#laatste_handeling_nogmaals_leveren[Laatste handeling
     *              nogmaals leveren] gebruiken
     */
    @When("voor persoon $bsn wordt de $index na laatste handeling geleverd")
    public void resetLeveringVoorPersoon(final String bsn, final int index) {
        throw new UnsupportedOperationException("Leveren handeling X van persoon niet supported");
    }


    /**
     * ==== Eerste handeling nogmaals leveren Dit is de verkorte versie van link:#handeling_nogmaals_leveren[handeling nogmaals leveren] om de eerste
     * handeling mogmaals te leveren. Deze step is functioneler dan nagaan wat de index van de eerste handeling van een persoon is.
     *
     * @param bsn het burgerservicenummer van de persoon, waarvan een handeling opnieuwe verwerkt worden door de mutatielevering software.
     */
    @When("voor persoon $bsn wordt de eerste handeling geleverd")
    public void resetEersteLeveringVoorPersoon(final String bsn) {
        throw new UnsupportedOperationException("Leveren eerste handeling van persoon niet supported");
    }

}
