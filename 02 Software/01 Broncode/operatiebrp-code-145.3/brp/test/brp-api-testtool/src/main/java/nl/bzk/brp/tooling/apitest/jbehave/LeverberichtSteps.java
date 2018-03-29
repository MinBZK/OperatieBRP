/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.tooling.apitest.jbehave;


import java.util.List;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortSynchronisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Verwerkingssoort;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.test.common.jbehave.VerwerkingssoortRegel;
import nl.bzk.brp.test.common.jbehave.VoorkomenAttribuutAanwezigRegel;
import nl.bzk.brp.test.common.jbehave.VoorkomenAttribuutRegel;
import nl.bzk.brp.test.common.jbehave.WaarOnwaar;
import nl.bzk.brp.tooling.apitest.service.basis.BerichtControleService;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.jbehave.core.steps.Steps;

/**
 * LeverberichtSteps.
 */
public final class LeverberichtSteps extends Steps {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    /**
     * Assert dat een volledigbericht is ontvangen voor de gegeven autorisatie.
     * @param leveringsautorisatieNaam naam van de leveringsautorisatie
     */
    @When("het volledigbericht voor leveringsautorisatie $leveringsautorisatieNaam is ontvangen en wordt bekeken")
    public void assertVolledigBerichtIsOntvangen(final String leveringsautorisatieNaam) {
        StoryController.getOmgeving().getLeverberichtStubService().assertBerichtIsOntvangen(leveringsautorisatieNaam, SoortSynchronisatie
                .VOLLEDIG_BERICHT);
    }

    /**
     * Assert dat een volledigbericht is ontvangen voor de gegeven autorisatie en partij.
     * @param leveringsautorisatieNaam de abonnementnaam dat voorkomt in een volledigbericht
     * @param afnemerPartijNaam de partijNaam die voorkomt in een volledigbericht
     */
    @When("volledigbericht voor partij $afnemerPartijNaam en leveringsautorisatie $leveringsautorisatieNaam wordt bekeken")
    public void assertVolledigBerichtIsOntvangen(final String leveringsautorisatieNaam, final String afnemerPartijNaam) {
        StoryController.getOmgeving().getLeverberichtStubService()
                .assertBerichtIsOntvangen(leveringsautorisatieNaam, SoortSynchronisatie.VOLLEDIG_BERICHT, afnemerPartijNaam);
    }

    /**
     * Assert dat een volledigbericht is ontvangen voor de gegeven autorisatie en partij.
     * @param partijNaam partijNaam
     * @param leveringsautorisatieNaam leveringsautorisatieNaam
     * @deprecated duplicate
     */
    @Deprecated
    @When("het volledigbericht voor partij $partijNaam en leveringsautorisatie $leveringsautorisatieNaam is ontvangen en wordt bekeken")
    public void wachtEnHaalVolledigberichtOpZonderDienst(final String partijNaam, final String leveringsautorisatieNaam) {
        assertVolledigBerichtIsOntvangen(leveringsautorisatieNaam, partijNaam);
    }

    /**
     * @param leveringsautorisatie leveringsautorisatie
     * @deprecated deprecated duplicate
     */
    @Deprecated
    @When("volledigbericht voor leveringsautorisatie $leveringsautorisatie wordt bekeken")
    public void haalVolledigberichtOp(final String leveringsautorisatie) {
        assertVolledigBerichtIsOntvangen(leveringsautorisatie);
    }

    /**
     * Assert dat een mutatiebericht is ontvangen voor de gegeven autorisatie.
     * @param leveringsautorisatieNaam naam van de leveringsautorisatie
     */
    @When("het mutatiebericht voor leveringsautorisatie $leveringsautorisatieNaam is ontvangen en wordt bekeken")
    public void assertMutatieBerichtIsOntvangen(final String leveringsautorisatieNaam) {
        StoryController.getOmgeving().getLeverberichtStubService().assertBerichtIsOntvangen(leveringsautorisatieNaam, SoortSynchronisatie.MUTATIE_BERICHT);
    }

    /**
     * Assert dat een mutatiebericht is ontvangen voor de gegeven autorisatie en partij.
     * @param leveringsautorisatieNaam de abonnementnaam dat voorkomt in een mutatiebericht
     * @param afnemerPartijNaam de partijCode die voorkomt in een mutatiebericht
     */
    @When("mutatiebericht voor partij $afnemerPartijNaam en leveringsautorisatie $leveringsautorisatieNaam wordt bekeken")
    public void assertMutatieBerichtIsOntvangen(final String leveringsautorisatieNaam, final String afnemerPartijNaam) {
        StoryController.getOmgeving().getLeverberichtStubService()
                .assertBerichtIsOntvangen(leveringsautorisatieNaam, SoortSynchronisatie.MUTATIE_BERICHT, afnemerPartijNaam);
    }

    /**
     * @param leveringsautorisatie leveringsautorisatie
     * @deprecated duplicaat
     */
    @Deprecated
    @When("mutatiebericht voor leveringsautorisatie $leveringsautorisatie wordt bekeken")
    public void haalMutatieberichtOp(final String leveringsautorisatie) {
        assertMutatieBerichtIsOntvangen(leveringsautorisatie);

    }

    /**
     * @param partijNaam partijnaam
     * @param leveringsautorisatie leveringsautorisatie
     * @deprecated duplicaat
     */
    @Deprecated
    @When("het mutatiebericht voor partij $partijNaam en leveringsautorisatie $leveringsautorisatie is ontvangen en wordt bekeken")
    public void wachtEnHaalMutatieberichtOp(final String partijNaam, final String leveringsautorisatie) {
        assertMutatieBerichtIsOntvangen(leveringsautorisatie, partijNaam);
    }


    /**
     * Assert dat het aantal ontvangen berichten gelijk is aan het opgegeven aantal.
     * @param count het aantal berichten waarvan is verwacht dat ze zijn geleverd
     */
    @Then("{zijn|worden|is|wordt} er $count bericht{en|} geleverd")
    public void aantalOntvangenBerichtenIs(final int count) {
        valideerHetAantalOntvangenBerichten(count);
    }

    /**
     * Assert dat het geen ontvangen berichten zijn.
     */
    @Then("is er geen synchronisatiebericht gevonden")
    public void erIsGeenLeverBericht() {
        valideerHetAantalOntvangenBerichten(0);
    }

    /**
     * Assert dat het geen ontvangen berichten zijn voor de gegeven autorisatie.
     * @param leveringsautorisatieNaam leveringsautorisatieNaam
     */
    @Then("is er geen synchronisatiebericht voor leveringsautorisatie $leveringsautorisatieNaam")
    public void erIsGeenLeverBerichtVoorLeveringsautorisatie(final String leveringsautorisatieNaam) {
        StoryController.getOmgeving().getLeverberichtStubService().assertGeenLevering(leveringsautorisatieNaam);
    }

    /**
     * Assert dat het aantal ontvangen berichten gelijk is aan het opgegeven aantal.
     * @param count het aantal berichten waarvan is verwacht dat ze zijn geleverd
     */
    @Then("is het aantal ontvangen berichten $count")
    public void valideerHetAantalOntvangenBerichten(final int count) {
        StoryController.getOmgeving().getLeverberichtStubService().assertAantalOntvangenBerichten(count);
    }

    /**
     * Assert dat het synchronisatiebericht (dat eerder is klaargezet) gelijk is aan het opgegeven expected bestand.
     * @param expected het verwachte antwoord
     * @param regex xpath expressie voor het vergelijken van synchronisatiebericht en verwacht bestand
     * @throws Exception fout
     * @deprecated deprecated
     */
    @Then("is het synchronisatiebericht gelijk aan $expected voor expressie $regex")
    @Deprecated
    public void thenIsHetSynchronisatieberichtGelijkAan(final String expected, final String regex) throws Exception {
        StoryController.getOmgeving().getBerichtControleService()
                .assertBerichtGelijkAanExpected(BerichtControleService.TypeBericht.LEVERBERICHT, expected, null);
    }

    /**
     * Assert dat het synchronisatiebericht (dat eerder is klaargezet) gelijk is aan het opgegeven expected bestand.
     * @param expected het verwachte antwoord
     * @param regex xpath expressie voor het vergelijken van synchronisatiebericht en verwacht bestand
     * @throws Exception fout
     * @deprecated deprecated
     */
    @Then("is het synchronisatiebericht ongeacht elementvolgorde voor $attributen gelijk aan $expected voor expressie $regex")
    @Deprecated
    public void thenIsHetSynchronisatieberichtGelijkAanOngeachtVolgorde(final List<String> attributen, final String expected, final String regex)
            throws Exception {
        StoryController.getOmgeving().getBerichtControleService()
                .assertBerichtGelijkAanExpectedOngeachtVolgorde(BerichtControleService.TypeBericht.LEVERBERICHT, expected, attributen);
    }


    /**
     * Assert dat het antwoordbericht (dat eerder is klaargezet) gelijk is aan het opgegeven expected bestand.
     * @param expected het verwachte antwoord
     * @param regex xpath expressie voor het vergelijken van antwoordbericht en verwacht bestand
     * @throws Exception fout
     * @deprecated deprecated
     */
    @Then("is het antwoordbericht ongeacht elementvolgorde voor $attributen gelijk aan $expected voor expressie $regex")
    @Deprecated
    public void thenIsHetAntwoordberichtGelijkAanOngeachtVolgorde(final List<String> attributen, final String expected, final String regex)
            throws Exception {
        StoryController.getOmgeving().getBerichtControleService()
                .assertBerichtGelijkAanExpectedOngeachtVolgorde(BerichtControleService.TypeBericht.ANTWOORDBERICHT, expected, attributen);
    }

    /**
     * Assert dat het ontvangen bericht XSD valide is.
     * @deprecated deprecated
     */
    @Then("is het bericht xsd-valide")
    @Deprecated
    public void assertBerichtXsdValide() {
        LOGGER.warn("Step 'is het bericht xsd-valide' is nu deprecated omdat het automatisch gebeurt");
    }

    /**
     * ==== Attribuut in (synchronisatie)bericht controleren Valideert of in het synchronisatiebericht een attribuut van een groep een verwachte waarde
     * heeft. Deze step faciliteert het valideren van waardes in meerdere voorkomens, vandaar dat er meerdere waardes kunnen worden opgegeven, gescheiden
     * door een komma.
     * <p>
     * Bijvoorbeeld, *huisnummer in adres*, voor het volgende deel in een synchronisatiebericht:
     * <p>
     * [source,xml] ---- &lt;adres&gt; &lt;huisnummer&gt;14&lt;/huisnummer&gt; ... &lt;/adres&gt; ----
     * @param attribuut het attribuut (in XML een element) waarvan de waardes worden gevraagd
     * @param groep de groep waaronder het gegeven attribuut is geplaatst in het bericht
     * @param verwachteWaardes de verwachte waardes van het gegeven attribuut. Indien een attribuut meer dan een keer voorkomt, geef dan de waardes in de
     * volgorde waarin ze in het bericht staan.
     */
    @Then("heeft '$attribuut' in '$groep' de waarde{s|} '$verwachteWaardes'")
    public void heeftGroepenGesorteerd(final String attribuut, final String groep, final List<String> verwachteWaardes) {
        StoryController.getOmgeving().getBerichtControleService()
                .assertBerichtHeeftWaardes(BerichtControleService.TypeBericht.LEVERBERICHT, groep, attribuut, verwachteWaardes);
    }

    /**
     * ==== Controleert xpath Valideert of het bericht de opgegeven waarde heeft als content voor de opgegeven xpath expressie.
     * @param xpathExpressie xpath
     */
    @Then("heeft het bericht geen kinderen voor xpath $xpathExpressie")
    public void synchronisatieberichtHeeftGeenKinderenVoorXPath(final String xpathExpressie) {
        StoryController.getOmgeving().getBerichtControleService().assertBevatLeafNode(BerichtControleService.TypeBericht.LEVERBERICHT, xpathExpressie);
    }

    /**
     * ==== Controleert xpath Valideert of het bericht de opgegeven waarde heeft als content voor de opgegeven xpath expressie.
     * @param xpathExpressie xpath
     * @param waarde waarde
     */
    @Then("heeft het bericht voor xpath $xpathExpressie de waarde $waarde")
    public void synchronisatieberichtHeeftXPathWaarde(final String xpathExpressie, final String waarde) {
        StoryController.getOmgeving().getBerichtControleService()
                .assertEvaluatieGelijkAanWaarde(BerichtControleService.TypeBericht.LEVERBERICHT, xpathExpressie, waarde);
    }

    /**
     * ==== Controleert xpath Controleert of de waarde binnen de response gevonden kan worden via de opgegeven xpath expressie.
     * @param xpathExpressie de xPath expressie
     * @param waarde waarde
     */
    @Then("heeft het bericht voor xpath $xpathExpressie de platgeslagen waarde $waarde")
    public void synchronisatieberichtAlsPlatteTekstVanafXPath(final String xpathExpressie, final String waarde) {
        StoryController.getOmgeving().getBerichtControleService().assertPlatteWaardeGelijk(BerichtControleService.TypeBericht.LEVERBERICHT,
                xpathExpressie, waarde);
    }

    /**
     * === Controleert xpath Controleert of gegeven xpath evalueert naar een node uit het xml leveringbericht.
     * @param xpath de xPath expressie
     */
    @Then("is er voor xpath $xpath een node aanwezig in het levering bericht")
    public void antwoordberichtHeeftGegevenNodeVoorXpath(final String xpath) {
        StoryController.getOmgeving().getBerichtControleService().assertNodeBestaat(BerichtControleService.TypeBericht.LEVERBERICHT, xpath);
    }

    /**
     * === Controleert xpath Controleert of er voor gegeven xpath geen node aanwezig is in het xml leveringbericht.
     * @param xpath de xpath expressie
     */
    @Then("is er voor xpath $xpath geen node aanwezig in het levering bericht")
    public void antwoordberichtHeeftGeenNodeVoorXpath(final String xpath) {
        StoryController.getOmgeving().getBerichtControleService().assertNodeBestaatNiet(BerichtControleService.TypeBericht.LEVERBERICHT, xpath);
    }

    /**
     * ==== Groepen in een bericht tellen Valideert of een bepaald XML element x keer voorkomt.
     * @param aantal het aantal keer dat een element wordt verwacht
     * @param groep de naam van de 'groep' of XML element dat wordt verwacht
     */
    @Then("heeft het bericht $aantal groep{en|} '$groep'")
    public void heeftAantalVoorkomensVanEenGroepInLevering(final Integer aantal, final String groep) {
        StoryController.getOmgeving().getBerichtControleService().assertElementAantal(BerichtControleService.TypeBericht.LEVERBERICHT, groep, aantal);
    }

    /**
     * ==== Groepen in een antwoordbericht tellen Valideert of een bepaald XML element x keer voorkomt.
     * @param groep de naam van de 'groep' of XML element dat wordt verwacht
     * @param aanwezig de aanwezigheid van de groep
     */
    @Then("heeft het antwoordbericht '$groep' $aanwezig")
    public void isGroepInResponseAanwezig(final String groep, final String aanwezig) {
        StoryController.getOmgeving().getBerichtControleService()
                .assertElementAanwezig(BerichtControleService.TypeBericht.LEVERBERICHT, groep, WaarOnwaar.isWaar(aanwezig));
    }

    /**
     * ==== Attribuut waardes in (synchronisatie)bericht controleren Valideert of in het synchronisatiebericht een attribuut van een groep een verwachte
     * waarde heeft. Deze step faciliteert het valideren van een specifiek voorkomen van een groep, vandaar dat het nummer van het voorkomen kan worden
     * opgegeven.
     * <p>
     * Bijvoorbeeld, *huisnummer in adres*, voor het volgende deel in een synchronisatiebericht:
     * <p>
     * [source,xml] ---- &lt;adres&gt; &lt;huisnummer&gt;14&lt;/huisnummer&gt; ... &lt;/adres&gt; ----
     * @param attribuut het attribuut (in XML een element) waarvan de waardes worden gevraagd
     * @param voorkomen de groep waaronder het gegeven attribuut is geplaatst in het bericht
     * @param voorkomenIndex geeft aan welk voorkomen gevalideerd moet worden
     * @param verwachteWaarde de verwachte waardes van het gegeven attribuut. Indien een attribuut meer dan een keer voorkomt, geef dan de waardes in de
     * volgorde waarin ze in het bericht staan.
     */
    @Then("heeft '$attribuut' in '$voorkomen' nummer $voorkomenIndex de waarde '$verwachteWaarde'")
    public void heeftAttribuutInVoorkomenWaarde(final String attribuut, final String voorkomen, final Integer voorkomenIndex,
                                                final String verwachteWaarde) {
        StoryController.getOmgeving().getBerichtControleService()
                .assertBerichtHeeftWaarde(BerichtControleService.TypeBericht.LEVERBERICHT, voorkomen, voorkomenIndex, attribuut,
                        verwachteWaarde);
    }

    /**
     * ==== Waardes van attributen in (synchronisatie)bericht controleren Valideert of in het geselecteerde synchronisatiebericht een attribuut van een
     * groep een verwachte waarde heeft. Deze step faciliteert het valideren van een specifiek voorkomen van een groep, vandaar dat het nummer van het
     * voorkomen kan worden opgegeven.
     * <p>
     * Indien er meerdere attributen gecontroleerd moeten worden, kan deze step met een tabel van attribuut en waardes worden gebruikt in plaats van de
     * step die slechts een attribuut controleert.
     * <p>
     * De tabel voor deze step ziet er als volgt uit:
     * <p>
     * ---- groep | nummer | attribuut  | verwachteWaarde adres | 2      | huisnummer | 14 ----
     * @param attribuutRegels de tabel met te valideren waardes
     */
    @Then("hebben attributen in voorkomens de volgende waarde{s|}: $attribuutRegels")
    public void hebbenAttributenWaardesInTabel(final List<VoorkomenAttribuutRegel> attribuutRegels) {
        for (final VoorkomenAttribuutRegel attribuutRegel : attribuutRegels) {
            heeftAttribuutInVoorkomenWaarde(attribuutRegel.getAttribuut(), attribuutRegel.getGroep(), attribuutRegel.getNummer(),
                    attribuutRegel.getVerwachteWaarde());
        }
    }

    /**
     * Attribuut aanwezigheid in (synchronisatie)bericht controleren Valideert of in het synchronisatiebericht een attribuut van een groep aanwezig is.
     * Deze step faciliteert het valideren van een specifiek voorkomen van een groep, vandaar dat het nummer van het voorkomen kan worden opgegeven.
     * <p>
     * Bijvoorbeeld, *huisnummer in adres*, voor het volgende deel in een synchronisatiebericht:
     * <p>
     * [source,xml] ---- &lt;adres&gt; &lt;huisnummer&gt;14&lt;/huisnummer&gt; ... &lt;/adres&gt; ----
     * @param attribuut het attribuut (in XML een element) waarvan de aanwezigheid worden gevraagd
     * @param voorkomen de groep waaronder het gegeven attribuut is geplaatst in het bericht
     * @param voorkomenIndex geeft aan welk voorkomen gevalideerd moet worden
     * @param aanwezig de verwachte aanwezigheid van het gegeven attribuut
     */
    @Then("is de aanwezigheid van '$attribuut' in '$voorkomen' nummer $voorkomenIndex $aanwezig")
    public void isAttribuutInVoorkomenAanwezig(final String attribuut, final String voorkomen, final Integer voorkomenIndex, final String aanwezig) {
        StoryController.getOmgeving().getBerichtControleService().assertAttribuutAanwezigheid(
                BerichtControleService.TypeBericht.LEVERBERICHT, voorkomen, voorkomenIndex, attribuut, WaarOnwaar.isWaar(aanwezig)
        );
    }

    /**
     * Aanwezigheid van attributen in (synchronisatie)bericht controleren Valideert of in het geselecteerde synchronisatiebericht een attribuut van een
     * groep aanwezig is. Deze step faciliteert het valideren van een specifiek voorkomen van een groep, vandaar dat het nummer van het voorkomen kan
     * worden opgegeven.
     * <p>
     * Indien er meerdere attributen gecontroleerd moeten worden, kan deze step met een tabel van attribuut en aanwezigheid worden gebruikt in plaats van
     * de step die slechts een attribuut controleert.
     * <p>
     * De tabel voor deze step ziet er als volgt uit:
     * <p>
     * ---- groep | nummer | attribuut  | aanwezig adres | 2      | huisnummer | true adres | 2      | huisletter | false ----
     * @param attribuutRegels de tabel met te valideren aanwezigheid
     */
    @Then("hebben attributen in voorkomens de volgende aanwezigheid: $attribuutRegels")
    public void zijnAttributenAanwezigInTabel(final List<VoorkomenAttribuutAanwezigRegel> attribuutRegels) {
        for (final VoorkomenAttribuutAanwezigRegel attribuutRegel : attribuutRegels) {
            isAttribuutInVoorkomenAanwezig(attribuutRegel.getAttribuut(), attribuutRegel.getGroep(), attribuutRegel.getNummer(),
                    attribuutRegel.getAanwezig());
        }
    }

    /**
     * ==== Waarde van het xml attribuut "verwerkingssoort" in (synchronisatie)bericht controleren Valideert of in het geselecteerde synchronisatiebericht
     * de verwerkingssoort van een groep een verwachte waarde heeft. Deze step faciliteert het valideren van een specifiek voorkomen van een groep, vandaar
     * dat het nummer van het voorkomen kan worden opgegeven.
     * @param groep de groep waaronder het gegeven attribuut is geplaatst in het bericht
     * @param voorkomenIndex geeft aan welk voorkomen gevalideerd moet worden
     * @param verwerkingssoort de verwachte waarde van de verwerkingssoort
     */
    @Then("is de verwerkingssoort van groep $groep in voorkomen $voorkomenIndex, $verwerkingssoort")
    public void heeftGroepVerwerkingssoort(final String groep, final Integer voorkomenIndex, final String verwerkingssoort) {
        StoryController.getOmgeving().getBerichtControleService()
                .assertBerichtVerwerkingssoortCorrect(BerichtControleService.TypeBericht.LEVERBERICHT, groep, voorkomenIndex,
                        Verwerkingssoort.parseNaam(verwerkingssoort));
    }

    /**
     * ==== Waardes van het xml attribuut "verwerkingssoort" in (synchronisatie)bericht controleren Valideert of in het geselecteerde synchronisatiebericht
     * een attribuut van een groep een verwachte waarde heeft. Deze step faciliteert het valideren van een specifiek voorkomen van een groep, vandaar dat
     * het nummer van het voorkomen kan worden opgegeven.
     * <p>
     * Indien er meerdere attributen gecontroleerd moeten worden, kan deze step met een tabel van attribuut en waardes worden gebruikt in plaats van de
     * step die slechts een attribuut controleert.
     * <p>
     * De tabel voor deze step ziet er als volgt uit:
     * <p>
     * ---- groep                | nummer                 | verwerkingssoort persoon              | 1                      | Wijziging ----
     * @param verwerkingssoortRegels de tabel met te valideren
     */
    @Then("hebben verwerkingssoorten in voorkomens de volgende waardes: $verwerkingssoortRegels")
    public void heeftGroepenVerwerkingssoort(final List<VerwerkingssoortRegel> verwerkingssoortRegels) {
        for (final VerwerkingssoortRegel verwerkingssoortRegel : verwerkingssoortRegels) {
            heeftGroepVerwerkingssoort(verwerkingssoortRegel.getGroep(), verwerkingssoortRegel.getNummer(), verwerkingssoortRegel.getVerwerkingssoort());
        }
    }

    /**
     * Verantwoording correct Valideert of de acties in het `&lt;verantwoording&gt;` deel van het bericht ook voorkomen bij de groepen van de persoon(en)
     * in het bericht. Indien er inconsistentie is, dus acties bij een persoon die niet in de verantwoording zijn opgenomen, of vice versa, zal dit worden
     * gemeld.
     */
    @Then("verantwoording acties staan in persoon")
    public void alleVerantwoordingActiesInPersoon() {
        StoryController.getOmgeving().getBerichtControleService().assertVerantwoordingCorrect(BerichtControleService.TypeBericht.LEVERBERICHT);
    }

    /**
     * ==== Expected met synchronisatiebericht vergelijken Valideert of een synchronisatiebericht voldoet aan een verwacht resultaat.
     * @param expected het bestand met het verwachte resultaat
     * @param regex xpath expressie voor het vergelijken van synchronisatiebericht en verwacht bestand
     * @deprecated deprecated
     */
    @Then("is het bericht gelijk aan $expected voor expressie $regex")
    @Deprecated
    public void leveringVoldoetAanExpected(final String expected, final String regex) {
        throw new UnsupportedOperationException("Step wordt niet meer gebruikt");
    }

}
