/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testrunner.jbehave;

import java.util.Arrays;
import java.util.List;
import org.jbehave.core.annotations.AsParameters;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Parameter;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.jbehave.core.model.ExamplesTable;

/**
 * Created by dennis on 11/5/15.
 */
public class MijnStappen {


    @Given("de persoon beschrijvingen: $dsl")
    public void givenPersoonDSL(String dsl) {
        System.err.println("xxxxxxx");
    }

    @Given("de standaardpersoon $pers met bsn $bsn en anr $anr zonder extra gebeurtenissen")
    public void givenStandaardPersoonZonderExtraGebeurtenissen(String pers, int bsn, int anr) {

    }

    @Given("de personen $ids zijn verwijderd")
    public void givenPersonenZijnVerwijderd(List<Integer> ids) {}

    @Then("is het bericht xsd-valide")
    public void berichtValide() {
        System.err.println("xxxxxxx");
    }

    @When("voor persoon $id wordt de laatste handeling geleverd")
    public void persoonLeverLaatsteHandeling(int id) {

    }

    @Then("hebben attributen in voorkomens de volgende waarde{s|}: $attribuutRegels")
    public void hebbenAttributenWaardesInTabel(final List<VoorkomenAttribuutRegel> attribuutRegels) {

    }

    @Then("hebben attributen in voorkomens de volgende aanwezigheid: $attribuutRegels")
    public void hebbenAttributenAanwezigheid(final List<VoorkomenAttribuutAanwezigRegel> attribuutRegels) {

    }
    @When("het bericht wordt verstuurd")
    public void whenHetBerichtWordtVerstuurd() {
        // PENDING
    }

    @Then("heeft het antwoordbericht verwerking Geslaagd")
    public void thenHeeftHetAntwoordberichtVerwerkingGeslaagd() {
        // PENDING
    }

    @Given("verzoek van type $type")
    public void givenVerzoekVanTypeGeefSynchronisatiePersoon(String type) {
        // PENDING
    }

    @Then("heeft het bericht $aantal groepen 'gegevenInOnderzoek'")
    public void thenHeeftHetBericht7GroepengegevenInOnderzoek(int aantal) {
        // PENDING
    }


    @Given("een sync uit bestand $file")
    public void givenEenSyncUitBestand(String file) {
    }

    @Given("alle personen zijn verwijderd")
    public void givenAllePersonenZijnVerwijderd() {
        // PENDING
    }

    @Then("is er voor xpath $xpath een node aanwezig in het levering bericht")
    public void thenIsErVoorXpathbrpreisdocumentbrpvoorkomenSleutelbrpvoorkomenSleutelGegeventextEenNodeAanwezigInHetLeveringBericht(String xpath) {
        // PENDING
    }



    @When("het volledigbericht voor leveringsautorisatie $abo is ontvangen en wordt bekeken")
    public void whenHetVolledigberichtVoorAbonnementGeenPopbepLeveringOpBasisVanDoelbindingIsOntvangenEnWordtBekeken(String abo) {
        // PENDING
    }


    @Then("is er geen synchronisatiebericht gevonden")
    public void thenIsErGeenSynchronisatieberichtGevonden() {
    }

    @When("het mutatiebericht voor leveringsautorisatie $abo is ontvangen en wordt bekeken")
    public void whenMutatieberichtWordtBekeken(String abo) {

    }

    @Given("extra waardes: $table")
    public void deExtraWaardes(ExamplesTable table) {

    }


    /**
     * Klasse voor vertaling van {@link org.jbehave.core.model.ExamplesTable} met voorkomen / attribuut aanduiding.
     */
    @AsParameters
    public static class VoorkomenAttribuutRegel {
        @Parameter(name = "groep")
        String  groep;
        @Parameter(name = "nummer")
        Integer nummer;
        @Parameter(name = "attribuut")
        String  attribuut;
        @Parameter(name = "verwachteWaarde")
        String  verwachteWaarde;
    }

    /**
     * Klasse voor vertaling van {@link org.jbehave.core.model.ExamplesTable} met voorkomen / attribuut aanwezig.
     */
    @AsParameters
    public static class VoorkomenAttribuutAanwezigRegel {
        @Parameter(name = "groep")
        String  groep;
        @Parameter(name = "nummer")
        Integer nummer;
        @Parameter(name = "attribuut")
        String  attribuut;
        @Parameter(name = "aanwezig")
        String  aanwezig;

        boolean isAanwezig() {
            return true;//WaarOnwaar.isWaar(aanwezig)
        }
    }

    /**
     * Klasse voor vertaling van {@link org.jbehave.core.model.ExamplesTable} met sorteringen.
     */
    @AsParameters
    public static class SorteringRegel {
        @Parameter(name = "groep")
        String groep;
        @Parameter(name = "attribuut")
        String attribuut;
        @Parameter(name = "verwachteWaardes")
        String verwachteWaardes;

        List<String> getVerwachteWaardes() {
            return Arrays.asList(verwachteWaardes.split(","));
        }
    }

    /**
     * Klasse voor vertaling van {@link org.jbehave.core.model.ExamplesTable} met verwerkingssoort aanduiding.
     */
    @AsParameters
    public static class VerwerkingssoortRegel {
        @Parameter(name = "groep")
        String  groep;
        @Parameter(name = "nummer")
        Integer nummer;
        @Parameter(name = "verwerkingssoort")
        String  verwerkingssoort;
    }

//    final class WaarOnwaar {
//        private static final def JA_likes = ['ja', 'wel', 'j', 'y', 'waar', 'true', 'ok']
//        private static final def NEE_likes = ['nee', 'niet', 'n', 'onwaar', 'false', 'nok']
//
//        private WaarOnwaar() {
//            // alleen static methods op deze util klasse
//        }
//
//        static boolean isWaar(String input) {
//            if (JA_likes.contains(input.toLowerCase().trim())) { return true }
//            if (NEE_likes.contains(input.toLowerCase().trim())) { return false }
//
//            throw new IllegalArgumentException("De waarde '${input}' wordt niet herkend als ja/nee waarde.")
//        }
//
//        static boolean isOnwaar(String input) {
//            !isWaar(input)
//        }
//    }

}
