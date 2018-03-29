/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dockertest.jbehave;

import java.util.List;
import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPathExpressionException;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.dockertest.component.BrpOmgeving;
import nl.bzk.brp.dockertest.service.AsynchroonberichtHelper;
import nl.bzk.brp.dockertest.util.ResourceUtils;
import nl.bzk.brp.test.common.jbehave.MeldingRow;
import nl.bzk.brp.test.common.jbehave.VoorkomenAttribuutRegel;
import nl.bzk.brp.test.common.xml.XmlUtils;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.jbehave.core.model.ExamplesTable;
import org.jbehave.core.steps.Steps;
import org.springframework.util.Assert;

/**
 * JBehave steps tbv het controleren van XML berichten.
 */
public class BerichtControleSteps extends Steps {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    /**
     * ==== Verwerking controleren
     * Valideert of het (synchrone) antwoordbericht de opgegeven verwerking heeft.
     * @param verwerking de verwerking die wordt verwacht. Mogelijke waardes zijn: `Geslaagd` en `Foutief`
     */
    @Then("heeft het antwoordbericht verwerking $verwerking")
    public void berichtHeeftVerwerking(String verwerking) {
        LOGGER.debug("Assert dat response verwerking '{}' heeft", verwerking);
        final String response = JBehaveState.get().verzoekService().getResponse();
        JBehaveState.get().getxPathHelper().assertWaardeGelijk(response, "//brp:resultaat/brp:verwerking", verwerking);
    }

    /**
     * ==== Attribuut in bericht controleren
     * Valideert of in het (synchroon) antwoordbericht een attribuut van een groep een verwachte waarde
     * heeft. Deze step faciliteert het valideren van waardes in meerdere voorkomens, vandaar dat
     * er meerdere waardes kunnen worden opgegeven, gescheiden door een komma.
     *
     * Bijvoorbeeld, *huisnummer in adres*, voor het volgende deel in een bevraging antwoordbericht:
     *
     * [source,xml]
     * ----
     * &lt;adres&gt;
     * &lt;huisnummer&gt;14&lt;/huisnummer&gt;
     * ...
     * &lt;/adres&gt;
     * ----
     * @param attribuut het attribuut (in XML een element) waarvan de waardes worden gevraagd
     * @param groep de groep waaronder het gegeven attribuut is geplaatst in het bericht
     * @param verwachteWaardes de verwachte waardes van het gegeven attribuut. Indien een attribuut meer dan een keer voorkomt, geef dan de waardes in de
     * volgorde waarin ze in het bericht staan.
     */
    @Then("heeft in het antwoordbericht '$attribuut' in '$groep' de waarde{s|} '$verwachteWaardes'")
    public void heeftGroepenGesorteerdInResponse(final String attribuut, final String groep, final List<String> verwachteWaardes) {
        final String response = JBehaveState.get().verzoekService().getResponse();
        JBehaveState.get().getxPathHelper().assertBerichtHeeftWaardes(response, groep, attribuut, verwachteWaardes);
    }

    /**
     * ==== Attribuut aanwezigheid in (synchronisatie)bericht controleren
     * Valideert of in het synchronisatiebericht een attribuut van een groep aanwezig is.
     * Deze step faciliteert het valideren van een specifiek voorkomen van een groep,
     * vandaar dat het nummer van het voorkomen kan worden opgegeven.
     *
     * Bijvoorbeeld, *huisnummer in adres*, voor het volgende deel in een synchronisatiebericht:
     *
     * [source,xml]
     * ----
     * &lt;adres&gt;
     * &lt;huisnummer&gt;14&lt;/huisnummer&gt;
     * ...
     * &lt;/adres&gt;
     * ----
     * @param attribuut het attribuut (in XML een element) waarvan de aanwezigheid worden gevraagd
     * @param voorkomen de groep waaronder het gegeven attribuut is geplaatst in het bericht
     * @param nummer geeft aan welk voorkomen gevalideerd moet worden
     * @param aanwezig de verwachte aanwezigheid van het gegeven attribuut
     */
    @Then("is in antwoordbericht de aanwezigheid van '$attribuut' in '$voorkomen' nummer $nummer $aanwezig")
    public void isAttribuutInVookomenAanwezig(final String attribuut, final String voorkomen, final Integer nummer, final String aanwezig) {
        final String response = JBehaveState.get().verzoekService().getResponse();
        JBehaveState.get().getxPathHelper().assertBerichtHeeftAttribuutAanwezigheid(response, voorkomen, nummer, attribuut, "ja".equals(aanwezig));
    }

    /**
     * ==== Expected met SOAP Response vergelijken
     * Valideert een (synchroon) SOAP response met een expected bestand. Het expected
     * bestand wordt als template behandeld, zodat bijvoorbeeld datums variabel kunnen zijn.
     * De validatie wordt gedaan door te kijken of de opgegeven xpath
     * expressie in het resultaat hetzelfde is als in het expected bestand.
     * @param expected het verwachte antwoord
     * @param regex xpath expressie voor het vergelijken van soap-resultaat en verwacht bestand
     */
    @Then("is het antwoordbericht gelijk aan $expected voor expressie $regex")
    public void responseVoldoetAanExpected(String expected, String regex) {
        final String response = JBehaveState.get().verzoekService().getResponse();
        final String expectedXml = ResourceUtils.classPathFileToString(expected);
        schrijfExpectedEnActualWeg(expectedXml, response, "response");
        XmlUtils.assertGelijk(regex, expectedXml, response);
    }

    @Then("is het ontvangen bericht voor $regex gelijk aan $expected")
    public void isHetOntvangenBerichtVoorExpressieGelijkAan(final String regex, final String expected) {
        final String bericht = JBehaveState.get().asynchroonBericht().getGeselecteerdeBericht();
        final String expectedXml = ResourceUtils.classPathFileToString(expected);
        schrijfExpectedEnActualWeg(expectedXml, bericht, "levering");
        XmlUtils.assertGelijk(regex, expectedXml, bericht);
    }

    private static void schrijfExpectedEnActualWeg(final String expected, final String actual, final String classifier) {
        ResourceUtils
                .schrijfBerichtNaarBestand(expected, JBehaveState.getScenarioState().getCurrentStory().getPath(),
                        JBehaveState.getScenarioState().getCurrectScenario(),
                        String.format("expected-%s.xml", classifier));
        ResourceUtils
                .schrijfBerichtNaarBestand(actual, JBehaveState.getScenarioState().getCurrentStory().getPath(),
                        JBehaveState.getScenarioState().getCurrectScenario(),
                        String.format("actual-%s.xml", classifier));
    }

    /**
     * ==== Controleert xpath
     * Controleert of gegeven xpath evalueert naar een node uit het xml antwoordbericht
     */
    @Then("is er voor xpath $xpath een node aanwezig in het antwoord bericht")
    public void thenIsNodeAanwezigInAntwoordbericht(String xpath) {
        final String response = JBehaveState.get().verzoekService().getResponse();
        JBehaveState.get().getxPathHelper().assertNodeBestaat(response, xpath);
    }

    /**
     * === Controleert xpath
     * Controleert of er voor gegeven xpath geen node aanwezig is in het xml antwoordbericht
     */
    @Then("is er voor xpath $xpath geen node aanwezig in het antwoord bericht")
    public void thenIsNodeNietAanwezigInAntwoordbericht(String xpath) {
        final String response = JBehaveState.get().verzoekService().getResponse();
        JBehaveState.get().getxPathHelper().assertNodeBestaatNiet(response, xpath);
    }

    /**
     * ==== xsd validatie van het antwoordbericht
     * Valideert of het (synchrone) antwoordbericht voldoet aan het xsd schema.
     */
    @Then("is het antwoordbericht xsd-valide")
    public void antwoordBerichtVoldoetAanXsd() {
        JBehaveState.get().verzoekService().assertResponseXsdValide();
    }

    /**
     * ==== Antwoord is een soapFault
     * Valideert of het antwoord een SoapFault is.
     */
    @Then("is het antwoordbericht een soapfault")
    public void antwoordIsEenSoapFault() {
        Assert.isTrue(JBehaveState.get().verzoekService().isSoapFoutOpgetreden());
    }

    /**
     * ==== Groepen in een antwoordbericht tellen
     * Valideert of een bepaald XML element x keer voorkomt.
     * @param aantal het aantal keer dat een element wordt verwacht
     * @param groep de naam van de 'groep' of XML element dat wordt verwacht
     */
    @Then("heeft het antwoordbericht $aantal groep{en|} '$groep'")
    public void heeftAantalVoorkomensVanEenGroepInResponse(Integer aantal, String groep) {
        final String response = JBehaveState.get().verzoekService().getResponse();
        JBehaveState.get().getxPathHelper().assertAantal(response, groep, aantal);
    }

    /**
     * ==== Specifiek meldingen controleren
     * Valideert of de opgegeven meldingen in het (synchrone) antwoordbericht voorkomen. Voor de duidelijkheid
     * kan de meldingtekst worden opgegegeven in de tabel, maar deze wordt niet meegenomen in
     * de validatie van het bericht.
     *
     * De tabel heeft de volgende header:
     * ----
     * CODE    | MELDING
     * ALG0001 | De algememe foutmelding
     * ----
     * @param meldingen tabel met meldingcodes en meldingsteksten waarvan wordt gekeken of deze in het bericht staan
     */
    @Then("heeft het antwoordbericht de meldingen: $meldingen")
    public void berichtHeeftMeldingen(List<MeldingRow> meldingen) {
        final String response = JBehaveState.get().verzoekService().getResponse();
        meldingen.forEach(meldingRow -> JBehaveState.get().getxPathHelper()
                .assertMeldingBestaat(response, meldingRow.getCode(), Regel.parseCode(meldingRow.getCode()).getMelding()));
    }

    /**
     *
     * @throws InterruptedException
     */
    @When("alle berichten zijn geleverd")
    public void whenAlleBerichtenZijnGeleverd() throws InterruptedException {
        JBehaveState.get().asynchroonBericht().wachtTotOntvangen(null);
    }

    /**
     *
     * @param aantal
     * @throws InterruptedException
     */
    @When("alle $aantal berichten zijn geleverd")
    public void whenAlleBerichtenZijnGeleverd(int aantal) throws InterruptedException {
        JBehaveState.get().asynchroonBericht().wachtTotOntvangen(aantal);
    }

    /**
     *
     */
    @Then("zijn er geen berichten ontvangen")
    public void erZijnGeenLeveringBerichtenOntvangen() {
        JBehaveState.get().asynchroonBericht().assertGeenBerichtenOntvangen();
    }

    /**
     *
     * @param soortSynchronisatie
     * @param leveringsautorisatie
     * @throws TransformerException
     * @throws XPathExpressionException
     */
    @Then("is er een $soortSynchronisatie ontvangen voor leveringsautorisatie $leveringsautorisatie")
    public void isErEenBerichtOntvangenVoorAutorisatie(final String soortSynchronisatie, final String leveringsautorisatie)
            throws TransformerException, XPathExpressionException {
        isErEenBerichtOntvangenVoorAutorisatieEnPartij(soortSynchronisatie, null, leveringsautorisatie);
    }

    /**
     *
     * @param soortSynchronisatie
     * @param partijNaam
     * @param leveringsautorisatie
     */
    @Then("is er een $soortSynchronisatie ontvangen voor partij $partij en voor leveringsautorisatie $leveringsautorisatie")
    public void isErEenBerichtOntvangenVoorAutorisatieEnPartij(final String soortSynchronisatie, final String partijNaam, final String leveringsautorisatie) {
        JBehaveState.get().asynchroonBericht().
                assertBerichtOntvangenVoorAutorisatieEnPartij(soortSynchronisatie, partijNaam, leveringsautorisatie);
    }

    /**
     *
     * @param soortSynchronisatie
     * @param partijNaam
     */
    @Then("is er een $soortSynchronisatie ontvangen voor partij $partij")
    public void isErEenBerichtOntvangenVoorPartij(final String soortSynchronisatie, final String partijNaam) {
        JBehaveState.get().asynchroonBericht().
                assertBerichtOntvangenVoorAutorisatieEnPartij(soortSynchronisatie, partijNaam, null);
    }

    /**
     *
     * @param table
     */
    @Then("zijn er per type bericht de volgende aantallen ontvangen: $table")
    public void zijnErPerTypeDezeAantallenOntvangen(final ExamplesTable table) {
        // TODO geen JBehave API onze code in...
        JBehaveState.get().asynchroonBericht().assertBerichtenVanGegevenTypeZijnOntvangen(table);
    }

    /**
     * ==== Groepen in een bericht tellen
     * Valideert of een bepaald XML element x keer voorkomt.
     * @param aantal het aantal keer dat een element wordt verwacht
     * @param groep de naam van de 'groep' of XML element dat wordt verwacht
     */
    @Then("heeft het bericht $aantal groep{en|} '$groep'")
    public void heeftAantalVoorkomensVanEenGroepInLevering(Integer aantal, String groep) {
        JBehaveState.get().asynchroonBericht().assertBekekenBerichtHeeftAantalVoorkomensVanEenGroepInLevering(aantal, groep);
    }

    /**
     * === Controleert xpath
     * Controleert of gegeven xpath evalueert naar een node uit het xml leveringbericht
     */
    @Then("is er voor xpath $xpath een node aanwezig in het levering bericht")
    public void thenIsNodeAanwezigInLeveringbericht(String xpath) {
        JBehaveState.get().getxPathHelper().assertNodeBestaat(JBehaveState.get().asynchroonBericht().getGeselecteerdeBericht(), xpath);
    }

    /**
     * ==== Waardes van attributen in (synchronisatie)bericht controleren
     * Valideert of in het geselecteerde synchronisatiebericht een attribuut van een groep een verwachte waarde
     * heeft. Deze step faciliteert het valideren van een specifiek voorkomen van een groep,
     * vandaar dat het nummer van het voorkomen kan worden opgegeven.
     *
     * Indien er meerdere attributen gecontroleerd moeten worden, kan deze step met een tabel van attribuut
     * en waardes worden gebruikt in plaats van de step die slechts een attribuut controleert.
     *
     * De tabel voor deze step ziet er als volgt uit:
     *
     * ----
     * groep | nummer | attribuut  | verwachteWaarde
     * adres | 2      | huisnummer | 14
     * ----
     * @param attribuutRegels de tabel met te valideren waardes
     */
    @Then("hebben attributen in voorkomens de volgende waarde{s|}: $attribuutRegels")
    public void hebbenAttributenWaardesInTabel(final List<VoorkomenAttribuutRegel> attribuutRegels) {
        final BrpOmgeving brpOmgeving = JBehaveState.get();
        final AsynchroonberichtHelper asynchroonberichtHelper = brpOmgeving.asynchroonBericht();
        asynchroonberichtHelper.assertLeveringen();

        final String bericht = asynchroonberichtHelper.geefBerichten().iterator().next();
        for (VoorkomenAttribuutRegel attribuutRegel : attribuutRegels) {
            JBehaveState.get().getxPathHelper().assertBerichtHeeftAttribuutWaarde(bericht, attribuutRegel.getGroep(), attribuutRegel.getNummer(),
                    attribuutRegel.getAttribuut(), attribuutRegel.getVerwachteWaarde());
        }
    }
}
