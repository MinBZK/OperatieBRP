/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.selectie.schrijver;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Selectietaak;
import nl.bzk.brp.domain.internbericht.selectie.MaakSelectieResultaatTaak;
import nl.bzk.brp.domain.internbericht.selectie.SelectieFragmentSchrijfBericht;

/**
 * SelectieFileService.
 */
public interface SelectieFileService {

    /**
     * Opschonen selectie resultaat directory.
     * @param maakSelectieResultaatTaak maakSelectieResultaatTaak
     */
    void schoonResultaatDirectory(MaakSelectieResultaatTaak maakSelectieResultaatTaak);

    /**
     * Geeft lijst met paden naar selectie fragment bestanden.
     * @param maakSelectieResultaatTaak maakSelectieResultaatTaak
     * @return fragment files
     * @throws IOException fout bij lezen
     */
    List<Path> geefFragmentFiles(MaakSelectieResultaatTaak maakSelectieResultaatTaak) throws IOException;

    /**
     * Leest regels uit selectie fragment bestand.
     * @param path path
     * @return fragment regels
     * @throws IOException fout bij lezen
     */
    Stream<String> leesFragmentRegels(final Path path) throws IOException;

    /**
     * Initialiseert folder structuur voor selectieresultaat sets.
     * @param berichtSchrijfTaak berichtSchrijfTaak
     * @return het opslag pad
     */
    Path initSchrijfOpslag(SelectieFragmentSchrijfBericht berichtSchrijfTaak);

    /**
     * Schrijft een reeks berichten welke behoren tot een selectietaak naar bestand (een deel fragment).
     * @param berichtenEncoded berichtenEncoded
     * @param selectieFragmentSchrijfBericht selectieFragmentSchrijfTaak
     * @throws IOException fout bij schrijven
     */
    void schrijfDeelFragment(List<byte[]> berichtenEncoded, SelectieFragmentSchrijfBericht selectieFragmentSchrijfBericht) throws IOException;

    /**
     * Voegt resterende deel fragment bestanden samen.
     * @param maakSelectieResultaatTaak maakSelectieResultaatTaak
     * @throws IOException io fout
     */
    void concatLaatsteDeelFragmenten(final MaakSelectieResultaatTaak maakSelectieResultaatTaak) throws IOException;

    /**
     * Voegt deel fragmenten bestanden samen.
     * @param selectieFragmentSchrijfBericht selectieFragmentSchrijfTaak
     * @throws IOException io fout
     */
    void concatDeelFragmenten(SelectieFragmentSchrijfBericht selectieFragmentSchrijfBericht) throws IOException;

    /**
     * Schrijft de te protocolleren personen
     * @param selectieBerichtSchrijfTaak selectieBerichtSchrijfTaak
     * @param regels regels
     * @throws IOException fout bij schrijven
     */
    void schijfProtocolleringPersonen(SelectieFragmentSchrijfBericht selectieBerichtSchrijfTaak, List<String> regels) throws IOException;

    /**
     * Schrijft het steekproefbestand.
     * @param taak de {@link MaakSelectieResultaatTaak}
     * @param regelsInSteekproef de persoonregels van de steekproef
     * @throws IOException fout bij schrijven
     */
    void schrijfSteekproefBestand(final MaakSelectieResultaatTaak taak, List<String> regelsInSteekproef) throws IOException;

    /**
     * @return de root folder waar de selectieresultaten komen te staan.
     */
    Path getBerichtResultaatFolder();

    /**
     * Geeft folder waarin selectieresultaat sets komen te staan.
     * @param selectietaakId id van {@link Selectietaak}
     * @param selectieDatumUitvoer uitvoerdatum van een {@link Selectietaak}
     * @return path
     */
    default Path getSelectietaakResultaatPath(final Integer selectietaakId, final Integer selectieDatumUitvoer) {
        final String folderNaam = String.format("selectietaak_%d_%d", selectietaakId, selectieDatumUitvoer);
        return getBerichtResultaatFolder().resolve(folderNaam);
    }

    /**
     * @return geeft het path voor het protocolleringbestand relatief tov het {@link SelectieFileService#getSelectietaakResultaatPath}
     */
    default Path geefProtocolleringBestand() {
        return Paths.get("protocollering", "protocolleringbestand.txt");
    }
}
