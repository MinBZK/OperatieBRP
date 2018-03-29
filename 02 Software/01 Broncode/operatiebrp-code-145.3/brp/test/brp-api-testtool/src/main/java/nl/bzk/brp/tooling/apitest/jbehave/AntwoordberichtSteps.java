/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.tooling.apitest.jbehave;

import com.google.common.collect.Sets;
import java.util.List;
import java.util.Set;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.test.common.jbehave.MeldingRow;
import nl.bzk.brp.test.common.jbehave.SorteringRegel;
import nl.bzk.brp.test.common.jbehave.VoorkomenAttribuutAanwezigRegel;
import nl.bzk.brp.test.common.jbehave.WaarOnwaar;
import nl.bzk.brp.tooling.apitest.service.basis.BerichtControleService;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.steps.Steps;

/**
 * Steps voor het controleren van antwoordberichten.
 */
public final class AntwoordberichtSteps extends Steps {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    /**
     * Assert dat het antwoordbericht de gegeven verwerking heeft.
     *
     * @param verwerking de verwerking die wordt verwacht. Mogelijke waardes zijn: `Geslaagd` en `Foutief`
     */
    @Then("heeft het antwoordbericht verwerking $verwerking")
    public void assertAntwoordberichtHeeftVerwerking(final String verwerking) {
        StoryController.getOmgeving().getBerichtControleService()
            .assertAntwoordberichtHeeftVerwerking(verwerking);
    }

    /**
     * Assert dat het antwoordbericht gelijk is aan de gegeven expectedFile.
     *
     * @param expectedFile een filepad met het verwachte antwoord
     * @param expr         een xPath expressie voor een deelvergelijking
     * @throws Exception als de
     */
    @Then("is het antwoordbericht gelijk aan $expectedFile voor expressie $epx")
    public void assertAntwoordberichtGelijkAanExpected(final String expectedFile, final String expr) throws Exception {
        StoryController.getOmgeving().getBerichtControleService()
            .assertBerichtGelijkAanExpected(BerichtControleService.TypeBericht.ANTWOORDBERICHT, expectedFile, null);
    }

    /**
     * Assert dat het antwoord (response) bericht XSD valide is.
     * @deprecated deprecated
     */
    @Then("is het antwoordbericht xsd-valide")
    @Deprecated
    public void assertAntwoordBerichtVoldoetAanXsd() {
        LOGGER.warn("Step 'is het antwoordbericht xsd-valide' is nu deprecated omdat het automatisch gebeurt");
    }

    /**
     * ==== Expected met SOAP Response vergelijken Valideert een (synchroon) SOAP response met een expected bestand. Het expected bestand wordt als
     * template behandeld, zodat bijvoorbeeld datums variabel kunnen zijn. De validatie wordt gedaan door te kijken of de opgegeven xpath expressie in het
     * resultaat hetzelfde is als in het expected bestand.
     *
     * @param expected het verwachte antwoord
     * @param regex    xpath expressie voor het vergelijken van soap-resultaat en verwacht bestand
     * @throws Exception fout
     */
    @Then("is het antwoordbericht gelijk aan $expected voor expressie $regex")
    public void responseVoldoetAanExpected(final String expected, final String regex) throws Exception {
        assertAntwoordberichtGelijkAanExpected(expected, regex);
    }

    /**
     * ==== Controleert xpath Valideert of het bericht de opgegeven waarde heeft als content voor de opgegeven xpath expressie.
     *
     * @param xpathExpressie een xPath expressie
     * @param waarde         de te controleren waarde
     */
    @Then("heeft het antwoordbericht voor xpath $xpathExpressie de waarde $waarde")
    public void antwoordberichtHeeftXPathWaarde(final String xpathExpressie, final String waarde) {
        StoryController.getOmgeving().getBerichtControleService().assertEvaluatieGelijkAanWaarde(BerichtControleService.TypeBericht.ANTWOORDBERICHT, xpathExpressie, waarde);
    }

    /**
     * Controleert xpath Controleert of de waarde binnen de response gevonden kan worden via de opgegeven xpath expressie.
     *
     * @param xpathExpressie een xPath expressie
     * @param waarde         de te controleren waarde
     */
    @Then("heeft het antwoordbericht voor xpath $xpathExpressie de platgeslagen waarde $waarde")
    public void antwoordberichtAlsPlatteTekstVanafXPath(final String xpathExpressie, final String waarde) {
        StoryController.getOmgeving().getBerichtControleService().assertPlatteWaardeGelijk(BerichtControleService.TypeBericht.ANTWOORDBERICHT, xpathExpressie, waarde);
    }

    /**
     * Controleert xpath Valideert of het bericht de opgegeven waarde heeft als content voor de opgegeven xpath expressie.
     *
     * @param xpathExpressie een xPath expressie
     */
    @Then("heeft het antwoordbericht geen kinderen voor xpath $xpathExpressie")
    public void antwoordberichtHeeftGeenKinderenVoorXPath(final String xpathExpressie) {
        StoryController.getOmgeving().getBerichtControleService().assertBevatLeafNode(BerichtControleService.TypeBericht.ANTWOORDBERICHT, xpathExpressie);
    }

    /**
     * Controleert xpath Controleert of gegeven xpath evalueert naar een node uit het xml antwoordbericht.
     *
     * @param xpathExpressie een xPath expressie
     */
    @Then("is er voor xpath $xpathExpressie een node aanwezig in het antwoord bericht")
    public void antwoordberichtHeeftGegevenNodeVoorXpath(final String xpathExpressie) {
        StoryController.getOmgeving().getBerichtControleService().assertNodeBestaat(BerichtControleService.TypeBericht.ANTWOORDBERICHT, xpathExpressie);
    }

    /**
     * Controleert xpath Controleert of er voor gegeven xpath geen node aanwezig is in het xml antwoordbericht.
     *
     * @param xpathExpressie een xPath expressie
     */
    @Then("is er voor xpath $xpathExpressie geen node aanwezig in het antwoord bericht")
    public void antwoordberichtHeeftGeenNodeVoorXpath(final String xpathExpressie) {
        StoryController.getOmgeving().getBerichtControleService().assertNodeBestaatNiet(BerichtControleService.TypeBericht.ANTWOORDBERICHT, xpathExpressie);
    }

    /**
     * Aantal meldingen controleren Valideert of het (synchrone) antwoordbericht het aantal opgegeven meldingen bevat.
     *
     * @param count het aantal verwachte meldingen in het bericht
     */
    @Then("heeft het antwoordbericht $count melding{en|}")
    public void berichtHeeftAantalMeldingen(final int count) {
        StoryController.getOmgeving().getBerichtControleService().assertHeeftBerichtAantalMeldingen(BerichtControleService.TypeBericht.ANTWOORDBERICHT, count);
    }

    /**
     * Controleert of meldingen v bepaald regelnummer referen naar juiste personen
     * @param count aantal meldingen v bepaald regelnummer
     * @param regelnummer regelnummer vd melding
     * @param bsnLijst lijst met burgerservicenummers
     */
    @Then("heeft het antwoordbericht $count meldingen voor regel '$regelnummer' die betrekking hebben op personen met bsn '$bsnLijst'")
    public void berichtHeeftMeldingVoorRegelVoorPersonen(final int count, final String regelnummer, final List<String> bsnLijst) {
        StoryController.getOmgeving().getBerichtControleService()
                .assertHeeftBerichtAantalMeldingenVoorPersonen(BerichtControleService.TypeBericht.ANTWOORDBERICHT, count, regelnummer, bsnLijst);
    }

    /**
     * Specifiek meldingen controleren Valideert of de opgegeven meldingen in het (synchrone) antwoordbericht voorkomen. Voor de duidelijkheid kan de
     * meldingtekst worden opgegegeven in de tabel, maar deze wordt niet meegenomen in de validatie van het bericht.
     * <p>
     * De tabel heeft de volgende header: ---- CODE    | MELDING ALG0001 | De algememe foutmelding ----
     *
     * @param meldingen tabel met meldingcodes en meldingsteksten waarvan wordt gekeken of deze in het bericht staan
     */
    @Then("heeft het antwoordbericht de meldingen: $meldingen")
    public void berichtHeeftMeldingen(final List<MeldingRow> meldingen) {
        final Set<String> meldingMap = Sets.newHashSet();
        for (final MeldingRow row : meldingen) {
            meldingMap.add(row.getCode());
        }
        StoryController.getOmgeving().getBerichtControleService().assertBerichtHeeftMelding(BerichtControleService.TypeBericht.ANTWOORDBERICHT, meldingMap);
    }

    /**
     * Attribuut in bericht controleren Valideert of in het (synchroon) antwoordbericht een attribuut van een groep een verwachte waarde heeft. Deze step
     * faciliteert het valideren van waardes in meerdere voorkomens, vandaar dat er meerdere waardes kunnen worden opgegeven, gescheiden door een komma.
     * <p>
     * Bijvoorbeeld, *huisnummer in adres*, voor het volgende deel in een bevraging antwoordbericht:
     * <p>
     * [source,xml] ---- &lt;adres&gt; &lt;huisnummer&gt;14&lt;/huisnummer&gt; ... &lt;/adres&gt; ----
     *
     * @param attribuut        het attribuut (in XML een element) waarvan de waardes worden gevraagd
     * @param groep            de groep waaronder het gegeven attribuut is geplaatst in het bericht
     * @param verwachteWaardes de verwachte waardes van het gegeven attribuut. Indien een attribuut meer dan een keer voorkomt, geef dan de waardes in de
     *                         volgorde waarin ze in het bericht staan.
     */
    @Then("heeft in het antwoordbericht '$attribuut' in '$groep' de waarde{s|} '$verwachteWaardes'")
    public void heeftGroepenGesorteerdInResponse(final String attribuut, final String groep, final List<String> verwachteWaardes) {
        StoryController.getOmgeving().getBerichtControleService().assertBerichtHeeftWaardes(BerichtControleService.TypeBericht.ANTWOORDBERICHT, groep, attribuut,
            verwachteWaardes);
    }

    /**
     * Attributen in bericht controleren Valideert of in het (synchroon) antwoordbericht een attribuut van een groep een verwachte waarde heeft. Deze step
     * faciliteert het valideren van waardes in meerdere voorkomens, vandaar dat er meerdere waardes kunnen worden opgegeven, gescheiden door een komma.
     * <p>
     * Indien er meerdere attributen gecontroleerd moeten worden, kan deze step met een tabel van attribuut en waardes worden gebruikt in plaats van de
     * step die slechts een attribuut controleert.
     * <p>
     * De tabel voor deze step ziet er als volgt uit:
     * <p>
     * ---- groep | attribuut  | verwachteWaardes adres | huisnummer | 14, 99 ----
     *
     * @param sorteringRegels de tabel met te valideren
     */
    @Then("hebben in het antwoordbericht de attributen in de groepen de volgende waarde{s|}: $sorteringRegels")
    public void heeftGroepenGesorteerdInResponseTabel(final List<SorteringRegel> sorteringRegels) {
        for (final SorteringRegel regel : sorteringRegels) {
            heeftGroepenGesorteerdInResponse(regel.getAttribuut(), regel.getGroep(), regel.getVerwachteWaardes());
        }
    }

    /**
     * Valideert of een bepaald XML element x keer voorkomt.
     *
     * @param aantal het aantal keer dat een element wordt verwacht
     * @param groep  de naam van de 'groep' of XML element dat wordt verwacht
     */
    @Then("heeft het antwoordbericht $aantal groep{en|} '$groep'")
    public void heeftAantalVoorkomensVanEenGroepInResponse(final Integer aantal, final String groep) {
        StoryController.getOmgeving().getBerichtControleService().assertElementAantal(BerichtControleService.TypeBericht.ANTWOORDBERICHT, groep, aantal);
    }

    /**
     * Valideert of een bepaald XML element x keer voorkomt.
     *
     * @param aantal    het aantal keer dat een element wordt verwacht
     * @param attribuut de naam van het 'attribuut' of XML element dat wordt verwacht
     */
    @Then("heeft het antwoordbericht $aantal attribuut{en|} '$attribuut'")
    public void heeftAantalVoorkomensVanEenAttribuutInResponse(final Integer aantal, final String attribuut) {
        StoryController.getOmgeving().getBerichtControleService().assertElementAantal(BerichtControleService.TypeBericht.ANTWOORDBERICHT, attribuut, aantal);
    }

    /**
     * Groepen in een antwoordbericht tellen Valideert of een bepaald XML element x keer voorkomt.
     *
     * @param groep    de naam van de 'groep' of XML element dat wordt verwacht
     * @param aanwezig het aantal keer dat een element wordt verwacht
     */
    @Then("heeft het antwoordbericht '$groep' $aanwezig")
    public void isGroepInResponseAanwezig(final String groep, final String aanwezig) {
        final boolean moetAanwezigZijn = WaarOnwaar.isWaar(aanwezig);
        StoryController.getOmgeving().getBerichtControleService().assertElementAanwezig(BerichtControleService.TypeBericht.ANTWOORDBERICHT, groep, moetAanwezigZijn);
    }

    /**
     * ==== Aanwezigheid van attributen in het antwoordbericht controleren Valideert of in het antwoordbericht een attribuut van een groep aanwezig is.
     * Deze step faciliteert het valideren van een specifiek voorkomen van een groep, vandaar dat het nummer van het voorkomen kan worden opgegeven.
     * <p>
     * Indien er meerdere attributen gecontroleerd moeten worden, kan deze step met een tabel van attribuut en aanwezigheid worden gebruikt in plaats van
     * de step die slechts een attribuut controleert.
     * <p>
     * De tabel voor deze step ziet er als volgt uit:
     * <p>
     * ---- groep | nummer | attribuut  | aanwezig adres | 2      | huisnummer | true adres | 2      | huisletter | false ----
     *
     * @param attribuutRegels de tabel met te valideren aanwezigheid
     */
    @Then("hebben attributen in het antwoordbericht in voorkomens de volgende aanwezigheid: $attribuutRegels")
    public void zijnAttributenAanwezigInTabel(final List<VoorkomenAttribuutAanwezigRegel> attribuutRegels) {
        for (final VoorkomenAttribuutAanwezigRegel regel : attribuutRegels) {
            isAttribuutInVookomenAanwezig(regel.getAttribuut(), regel.getGroep(), regel.getNummer(), regel.getAanwezig());
        }
    }


    /**
     * ==== Attribuut aanwezigheid in (synchronisatie)bericht controleren Valideert of in het synchronisatiebericht een attribuut van een groep aanwezig
     * is. Deze step faciliteert het valideren van een specifiek voorkomen van een groep, vandaar dat het nummer van het voorkomen kan worden opgegeven.
     * <p>
     * Bijvoorbeeld, *huisnummer in adres*, voor het volgende deel in een synchronisatiebericht:
     * <p>
     * [source,xml] ---- &lt;adres&gt; &lt;huisnummer&gt;14&lt;/huisnummer&gt; ... &lt;/adres&gt; ----
     *
     * @param attribuut het attribuut (in XML een element) waarvan de aanwezigheid worden gevraagd
     * @param voorkomen de groep waaronder het gegeven attribuut is geplaatst in het bericht
     * @param nummer    geeft aan welk voorkomen gevalideerd moet worden
     * @param aanwezig  de verwachte aanwezigheid van het gegeven attribuut
     */
    @Then("is in antwoordbericht de aanwezigheid van '$attribuut' in '$voorkomen' nummer $nummer $aanwezig")
    public void isAttribuutInVookomenAanwezig(final String attribuut, final String voorkomen, final Integer nummer, final String aanwezig) {
        StoryController.getOmgeving().getBerichtControleService().assertAttribuutAanwezigheid(BerichtControleService.TypeBericht.ANTWOORDBERICHT, voorkomen, nummer, attribuut,
            WaarOnwaar.isWaar(aanwezig));
    }
}
