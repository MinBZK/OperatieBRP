/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.ggo.viewer.service;

/**
 * Deze service bevraagt de (statische/dynamische)stamtabellen om de omschrijving bij de code (Brp domein) op te halen
 * en geeft deze informatie terug als formatted string. De bron voor deze stamtabellen is Brp.
 */
public interface BrpStamtabelService extends StamtabelService {
    /**
     * Haalt de stamgegevens van Aangever op.
     * @param brpAangeverCode Character
     * @return formatted String
     */
    String getAangever(Character brpAangeverCode);

    /**
     * Haalt de stamgegevens van AdellijkeTitel op.
     * @param brpAdellijkeTitelCode String
     * @return formatted String
     */
    String getAdellijkeTitel(String brpAdellijkeTitelCode);

    /**
     * Haalt de stamgegevens van Bijhoudingsaard op.
     * @param brpBijhoudingsaardCode String
     * @return formatted String
     */
    String getBijhoudingsaard(String brpBijhoudingsaardCode);

    /**
     * Haalt de stamgegevens van nadere bijhoudingsaard op.
     * @param brpNadereBijhoudingsaard String
     * @return formatted String
     */
    String getNadereBijhoudingsaard(String brpNadereBijhoudingsaard);

    /**
     * Haalt de stamgegevens van PartijCode op.
     * @param brpPartijCode int
     * @return formatted String
     */
    String getPartij(String brpPartijCode);

    /**
     * Haalt de stamgegevens van Predikaat op.
     * @param brpPredicaatCode String
     * @return formatted String
     */
    String getPredicaat(String brpPredicaatCode);

    /**
     * Haalt de stamgegevens van RedenOpschorting op.
     * @param brpRedenOpschortingBijhoudingCode String
     * @return formatted String
     */
    String getRedenOpschorting(String brpRedenOpschortingBijhoudingCode);

    /**
     * Haalt de stamgegevens van ReisdocumentRedenOntbreken op.
     * @param brpReisdocumentRedenOntbreken String
     * @return formatted String
     */
    String getRedenOntbrekenReisdocument(String brpReisdocumentRedenOntbreken);

    /**
     * Haalt de stamgegevens van RedenWijzigingVerblijf op.
     * @param brpRedenWijzigingVerblijfCode Character
     * @return formatted String
     */
    String getRedenWijzigingVerblijf(Character brpRedenWijzigingVerblijfCode);

    /**
     * Haalt de stamgegevens van Gemeente op adhv een partijCode.
     * @param gemeentePartijCode int
     * @return formatted String
     */
    String getGemeenteByPartij(String gemeentePartijCode);

    /**
     * Haalt de gemeente op adhv de partijcode.
     * @param gemeentePartijCode int
     * @return formatted gemeentecode String
     */
    String getGemeenteCodeByPartij(String gemeentePartijCode);

    /**
     * Haalt de soort migratie op.
     * @param brpSoortMigratieCode String
     * @return formatted String
     */
    String getSoortMigratieCode(String brpSoortMigratieCode);
}
