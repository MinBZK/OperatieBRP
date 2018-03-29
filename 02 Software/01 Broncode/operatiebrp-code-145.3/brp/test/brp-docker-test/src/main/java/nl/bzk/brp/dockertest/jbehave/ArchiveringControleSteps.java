/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dockertest.jbehave;

import java.text.ParseException;
import java.util.List;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortBericht;
import nl.bzk.brp.dockertest.component.BrpOmgeving;
import nl.bzk.brp.dockertest.component.DockerNaam;
import org.jbehave.core.annotations.BeforeScenario;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.steps.Steps;

/**
 * JBehave steps tbv archivering.
 */
public class ArchiveringControleSteps extends Steps {

    /**
     * Leegt de ber.ber tabellen.
     */
    @BeforeScenario
    public void leegBerichtenTabel() {
        final BrpOmgeving brpOmgeving = JBehaveState.get();
        if (brpOmgeving != null && brpOmgeving.bevat(DockerNaam.ARCHIVERINGDB)) {
            brpOmgeving.archivering().reset();
        }
    }

    /**
     * Stap om te controleren dat er geen persoon is gearchiveerd voor bericht met referentie en srt
     * @param referentie de referentie om te controleren
     * @param srt de srt om te controleren
     */
    @Then("bestaat er geen voorkomen in berpers tabel voor referentie $referentie en srt $srt")
    public void bestaatErGeenVoorkomenInBerPersVoorReferentieEnSrt(
            final String referentie, final String srt) {
        JBehaveState.get().archivering().assertGeenArchivering(referentie, SoortBericht.parseIdentifier(srt));
    }

    /**
     * Stap om te controleren dat er geen persoon is gearchiveerd voor bericht met crossreferentie en srt
     * @param crossreferentie de crossreferentie om te controleren
     * @param srt de srt om te controleren
     */
    @Then("bestaat er geen voorkomen in berpers tabel voor crossreferentie $crossreferentie en srt $srt")
    public void bestaatErGeenVoorkomenInBerPersVoorCrossReferentieEnSrt(
            final String crossreferentie, final String srt) {
        JBehaveState.get().archivering().assertGeenPersArchivering(crossreferentie, SoortBericht.parseIdentifier(srt));
    }

    /**
     * Stap om te controleren dat de tijdstip verzending in het bericht (zie data kolom ber.ber tabel) gelijk is aan tsverzending kolom in de ber.ber
     * tabel.
     */
    @Then("tijdstipverzending in bericht is correct gearchiveerd")
    public void deTijdstipVerzendingInBerichtIsGelijkAanTijdstipInArchivering() throws ParseException {
        JBehaveState.get().archivering().assertTijdstipVerzendingInBerichtIsGelijkAanTijdstipInArchivering();
    }


    /**
     * Stap om te controleren dat de tijdstip ontvangst binnen redelijke grenzen actueel is. Gezien de mogelijke tijdsverschillen op de servers hanteren we
     * voorlopig een uur.
     */
    @Then("tijdstipontvangst is actueel")
    public void tijdstipOntvangsIsActueel() throws ParseException {
        JBehaveState.get().archivering().assertTijdstipOntvangsIsActueel();
    }

    /**
     * Stap om te controleren dat de leveringautorisatie
     */
    @Then("leveringautorisatie is gelijk in archief")
    public void leveringautorisatieIsGelijk() {
        JBehaveState.get().archivering().assertLeveringautorisatieGelijk();
    }


    /**
     * Stap om te controleren dat de kruisreferentie in het bericht gelijk is aan de kruisreferentie in de ber.ber tabel
     */
    @Then("referentienr is gelijk")
    public void kruisreferentieIsGelijk() {
        JBehaveState.get().archivering().assertKruisreferentieGelijk();
    }

    /**
     * Stap om te controleren dat de dienstid correct is gearchiveerd
     */
    @Then("dienstid is gelijk in archief")
    public void dienstIdIsGelijk() {
        JBehaveState.get().archivering().assertDienstIdGelijk();
    }

    /**
     * Stap om te controleren dat het synchrone request en response bericht correct gearchiveerd zijn.
     */
    @Then("is het synchrone verzoek correct gearchiveerd")
    public void isHetSynchroneVerzoekCorrectGearchiveerd() {
        JBehaveState.get().archivering().assertSynchroonGearchiveerd();
    }

    /**
     *
     * @param gegevensRegels
     */
    @Then("is er gearchiveerd met de volgende gegevens: $gegevensRegels")
    public void thenIsErGearchiveerdMetDeVolgendeGegevens(List<GegevensRegels> gegevensRegels) {
        JBehaveState.get().archivering().assertGearchiveerdMetDeVolgendeGegevens(GegevensRegels.map(gegevensRegels));
    }

    /**
     * Stap om te controleren dat er een uitgaand bericht bestaat als antwoord op het ingaande bericht.
     * @param referentie de referentie om te controleren
     */
    @Then("bestaat er een antwoordbericht voor referentie $referentie")
    public void bestaatErEenAntwoordberichtVoorReferentie(
            final String referentie) {
        JBehaveState.get().archivering().assertErBestaatEenAntwoordberichtVoorReferentie(referentie);
    }

    /**
     *
     * @param bsn
     */
    @Then("is de administratieve handeling voor persoon $bsn correct gearchiveerd")
    public void isErEenAdmHndGearchiveerdVoorBsn(final String bsn) {
        JBehaveState.get().archivering().assertAdministratieveHandelingGearchiveerd(bsn);
    }
}
