/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.tooling.apitest.service.selectie;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Selectietaak;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SelectietaakStatus;
import nl.bzk.brp.test.common.dsl.DslSectie;
import nl.bzk.brp.tooling.apitest.service.basis.Stateful;
import org.springframework.core.io.Resource;

/**
 * Service voor het uitvoeren van de selectie.
 */
public interface SelectieAPIService {
    /**
     * Start de selectierun.
     */
    void startRun() throws IOException, InterruptedException;

    /**
     * Start de selectierun single threaded.
     */
    void startRunSingleThreaded() throws IOException, InterruptedException;

    /**
     * @param id selectietaak id
     * @param datumPlanning datumUitvoer
     */
    void assertFilesAanwezig(Integer id, String datumPlanning) throws IOException;

    /**
     * @param id selectietaak id
     * @param datumPlanning datumUitvoer
     * @param aantalPersonen aantalPersonen
     */
    void assertFilesAanwezigMetAantalPersonen(Integer id, String datumPlanning, Integer aantalPersonen) throws IOException;

    /**
     * @param aantalSeconden aantalSeconden
     * @param inError inError
     */
    void wachtAantalSecondenTotSelectieRunKlaar(int aantalSeconden, boolean inError) throws InterruptedException;

    /**
     * @param id selectietaak id
     * @param datumPlanning datumUitvoer
     */
    void assertGeenFilesAanwezig(Integer id, String datumPlanning) throws IOException;

    /**
     * Maak selectietaken obv de gegeven resource.
     * @param resource een resouce
     */
    void maakSelectietaken(Resource resource);

    /**
     * Maak selectietaken obv de sectieList resource.
     * @param sectieList een lijst van {@link DslSectie}s
     */
    void maakSelectietaken(List<DslSectie> sectieList);

    /**
     * Maak selectielijsten obv de sectieList resource.
     * @param sectieList een lijst van {@link DslSectie}s
     */
    void maakSelectielijsten(List<DslSectie> sectieList);

    /**
     *  Verwijder het protocolleringsbestand voor een selectietaak.
     * @param selectietaakId selectietaak id
     * @param datumPlanning datum planning
     */
    void verwijderProtocolleringbestand(int selectietaakId, String datumPlanning);

    /**
     * Maak het protocolleringsbetand path invalid door er een dir i.p.v. file van te maken
     * @param selectietaakId selectietaak id
     * @param datumPlanning datum planning
     */
    void maakProtocolleringsBestandInvalid(int selectietaakId, String datumPlanning);

    /**
     * Controleer of alle selectietaken een id bevatten.
     * @param sectieList een lijst van {@link DslSectie}s
     */
    void assertTakenHebbenEenSelectierunId(List<DslSectie> sectieList);

    /**
     * @param id selectietaak id
     * @param datumPlanning datumPlanning
     * @param aantalPersonen aantalPersonen
     * @param aantalFiles aantalFiles
     */
    void assertFilesAanwezigMetAantalPersonenEnAantalFiles(Integer id, String datumPlanning, Integer aantalPersonen, Integer aantalFiles) throws
            IOException;

    /**
     * Controleer totalenbestand.
     * @param id selectietaak id
     * @param datumPlanning datum uitvoer
     * @param expectedPath pad naar expectation
     * @throws IOException fout bij lezen totalenbestand
     */
    void assertTotalenbestandVoorTaakGelijkAan(Integer id, String datumPlanning, final String expectedPath) throws IOException;

    /**
     * Controleer resultaat bestanden tegen expecations.
     * @param id selectie taak ID
     * @param datumPlanning datum uitvoer
     * @param expectationPaden de paden naar de expectations
     */
    void assertFilesAanwezigMetExpectations(Integer id, String datumPlanning, List<Map<String, String>> expectationPaden) throws IOException;

    /**
     * Assert of een node bestaat in een van de selectieresultaat persoonbestanden.
     * @param id selectie taak ID
     * @param datumPlanning datum uitvoer
     * @param xpathExpressie XPath expressie welke geëvalueerd dient te worden
     */
    void assertNodeBestaatInPersoonBestanden(final Integer id, final String datumPlanning, String xpathExpressie) throws IOException;

    /**
     * Assert of protocollering bestand aanwezig/afwezig
     * @param id id
     * @param datumPlanning datumUitvoer
     * @param aanwezig aanwezig
     */
    void assertProtocolleringBestand(final Integer id, final String datumPlanning, Boolean aanwezig);

    /**
     * Assert dat de gegeven status transities doorlopen zijn.
     * @param list lijst met transitiet per taak
     */
    void assertStatusTransitie(List<Map<String, String>> list) throws IOException;

    /**
     * Assert waarden van peilmomenten van selectietaak
     * @param list waardenlijst
     * @throws IOException
     */
    void assertPeilmomentenSelectietaak(final List<Map<String, String>> list) throws IOException;

    /**
     * Assert dat er geen controlebestand gemaakt is.
     *
     * selectietaakId
     * aanwezig boolean
     * aantal personen
     * inhoud
     * @param map met
     */
    void assertControlebestand(final List<Map<String, String>> map) throws IOException;

    /**
     * Activeert de selectie bulkmodus, zodat eenvoudig een grote selectie gesimuleerd kan worden.
     * @param aantalPersonen totaal aantal personen in de selectie.
     */
    void activeerBulkModus(int aantalPersonen);

    /**
     * Start de Protcollering voor selectie routine.
     */
    void startProtocollering();


    /**
     * Assert dat als volgt geprotocolleerd is.

     */
    void assertGeprotocolleerdVoorSelectie(int aantalLeveringsaantekening, int aanPersonen);

    /**
     * Zet de status van de gegeven taak op {@link SelectietaakStatus#TE_PROTOCOLLEREN}
     * @param selectietaakId id van de taak
     */
    void setStatusOpTeProtocolleren(int selectietaakId);


    /**
     * Testservice voor zetten en getten van de taken.
     */
    interface TaakBeheer {

        Collection<Selectietaak> getTaken();

        void setTaken(Collection<Selectietaak> taken);

        Selectietaak getTaak(Integer id);

    }

    public interface BulkMode extends Stateful {

        void activeerBulkModus(int max);
    }

}
