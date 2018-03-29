/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dockertest.jbehave;

import org.jbehave.core.annotations.Then;

/**
 * JBehave steps tbv controle afnemerindicaties.
 */
public class AfnemerindicatieControleSteps {

    /**
     * ==== Controleren of de afnemerindicatie bij een persoon is gezet
     * Controleert of er een afnemerindicatie bij de opgegeven persoon is gezet voor de opgegeven leveringsautorisatie
     * @param bsn de bsn van de persoon waarvoor een afnemerindicatie gecontroleert word
     * @param leveringautorisatienaam leveringautorisatie waarvoor een afnemerindicatie is geplaatst
     * @param partijnaam partij waarvoor de afnemerindicatie is geplaatst
     * @param soortDienst soortDienst
     */
    @Then("is er voor persoon met bsn $bsn en leveringautorisatie $leveringautorisatie en partij $partijnaam en soortDienst $soortDienst een afnemerindicatie geplaatst")
    public void isAfnemerIndicatieGeplaatst(String bsn, String leveringautorisatienaam, String partijnaam, String soortDienst) {
        JBehaveState.get().afnemerindicatie().assertAfnemerIndicatieGeplaatst(bsn, leveringautorisatienaam, partijnaam, soortDienst);
    }


    /**
     * ==== Controleren of de afnemerindicatie bij een persoon is verwijderd
     * Controleert of er een afnemerindicatie bij de opgegeven persoon is verwijderd voor het opgegeven leveringsautorisatie
     * @param bsn de bsn van de persoon waarvoor een afnemerindicatie gecontroleert word
     * @param leveringautorisatienaam leveringautorisatie waarvoor een afnemerindicatie is geplaatst
     * @param partijnaam partij waarvoor de afnemerindicatie is geplaatst
     */
    @Then("is er voor persoon met bsn $bsn en leveringautorisatie $leveringautorisatie en partij $partijnaam geen afnemerindicatie aanwezig")
    public void isAfnemerIndicatieVerwijderd(String bsn, String leveringautorisatienaam, String partijnaam) {
        JBehaveState.get().afnemerindicatie().assertAfnemerIndicatieNietGeplaatst(bsn, leveringautorisatienaam, partijnaam);
    }

    /**
     * Controleert of de afnemerindicatie bij een persoon is vervallen Controleert of er een afnemerindicatie bij de opgegeven persoon is vervallen
     * (verwijderd via service tsverval is gevuld) voor het opgegeven leveringsautorisatie
     * @param bsn de bsn van de persoon waarvoor een afnemerindicatie gecontroleert word
     * @param leveringautorisatienaam leveringautorisatie waarvoor een afnemerindicatie is geplaatst
     * @param partijCode partijCode waarvoor de afnemerindicatie is geplaatst
     */
    @Then("is er voor persoon met bsn $bsn en leveringautorisatie $leveringautorisatie en partij $partijCode een vervallen afnemerindicatie")
    public void isAfnemerIndicatieVervallen(String bsn, String leveringautorisatienaam, String partijCode) {
        JBehaveState.get().afnemerindicatie().assertAfnemerIndicatieVervallen(bsn, leveringautorisatienaam, partijCode);
    }

}
