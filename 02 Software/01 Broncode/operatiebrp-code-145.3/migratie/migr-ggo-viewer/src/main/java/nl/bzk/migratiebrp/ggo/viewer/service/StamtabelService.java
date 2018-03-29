/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.ggo.viewer.service;

/**
 * Deze service bevraagt de (statische/dynamische)stamtabellen om de omschrijving bij de code op te halen en geeft deze
 * informatie terug als formatted string. Dit betreft gegevens uit de stamtabellen welke gelijk zijn in zowel het Lo3
 * als Brp domein. De bron is voor deze stamtabellen Brp.
 */
public interface StamtabelService {

    /**
     * Haalt de stamgegevens van SoortAdres (BRP) op.
     * @param functieAdresCode String
     * @return formatted String
     */
    String getFunctieAdres(String functieAdresCode);

    /**
     * Haalt de stamgegevens van Partij (BRP) op.
     * @param gemeenteCode String
     * @return formatted String
     */
    String getGemeente(String gemeenteCode);

    /**
     * Haalt de stamgegevens van Geslachtsaanduiding (BRP) op.
     * @param geslachtsaanduidingCode String
     * @return formatted String
     */
    String getGeslachtsaanduiding(String geslachtsaanduidingCode);

    /**
     * Haalt de stamgegevens van LandOfGebied (BRP) op.
     * @param landCode String
     * @return formatted String
     */
    String getLandOfGebied(String landCode);

    /**
     * Haalt de stamgegevens van Nationaliteit (BRP) op.
     * @param nationaliteitCode String
     * @return formatted String
     */
    String getNationaliteit(String nationaliteitCode);

    /**
     * Haalt de stamgegevens van RedenVerkrijgingNLNationaliteit (BRP) op.
     * @param redenVerkrijgingNederlandschapCode String
     * @return formatted String
     */
    String getRedenVerkrijgingNederlandschap(String redenVerkrijgingNederlandschapCode);

    /**
     * Haalt de stamgegevens van RedenVerliesNLNationaliteit (BRP) op.
     * @param redenVerliesNederlandschapCode String
     * @return formatted String
     */

    String getRedenVerliesNederlandschap(String redenVerliesNederlandschapCode);

    /**
     * Haalt de stamgegevens van Verblijfsrecht (BRP) op.
     * @param verblijfstitelCode String
     * @return formatted String
     */
    String getVerblijfstitel(String verblijfstitelCode);

    /**
     * Haalt de stamgegevens van Naamgebruik (BRP) op.
     * @param naamgebruikCode String
     * @return formatted String
     */
    String getNaamgebruik(String naamgebruikCode);

    /**
     * Haalt de stamgegevens van AutoriteitVanAfgifteReisdocument op.
     * @param brpReisdocumentAutoriteitVanAfgifte String
     * @return formatted String
     */
    String getAutoriteitVanAfgifteReisdocument(String brpReisdocumentAutoriteitVanAfgifte);

    /**
     * Haalt de stamgegevens van SoortNlReisdocument op.
     * @param brpReisdocumentSoort String
     * @return formatted String
     */
    String getSoortNlReisdocument(String brpReisdocumentSoort);

    /**
     * Haalt de stamgegevens van RedenEindeRelatie op.
     * @param brpRedenEindeRelatieCode BrpRedenEindeRelatieCode
     * @return formatted String
     */
    String getRedenEindeRelatie(String brpRedenEindeRelatieCode);

}
