/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.ggo.viewer.service;

import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3RNIDeelnemerCode;

/**
 * Deze service bevraagt de (statische/dynamische)stamtabellen om de omschrijving bij de code (Lo3 domein) op te halen
 * en geeft deze informatie terug als formatted string. De bron voor deze stamtabellen is Lo3.
 */
public interface Lo3StamtabelService extends StamtabelService {

    /**
     * Zet de waarde om in een voor de viewer geschikt formaat.
     * @param aanduiding String.
     * @return Omgezette waarde.
     */
    String getAanduidingBijzonderNederlandschap(final String aanduiding);

    /**
     * Zet de waarde om in een voor de viewer geschikt formaat.
     * @param aanduiding String.
     * @return Omgezette waarde.
     */
    String getAanduidingEuropeesKiesrecht(final String aanduiding);

    /**
     * Zet de waarde om in een voor de viewer geschikt formaat.
     * @param aanduiding String.
     * @return Omgezette waarde.
     */
    String getAanduidingHuisnummer(final String aanduiding);

    /**
     * Zet de waarde om in een voor de viewer geschikt formaat.
     * @param aanduiding String.
     * @return Omgezette waarde.
     */
    String getAanduidingUitgeslotenKiesrecht(final String aanduiding);

    /**
     * Zet de waarde om in een voor de viewer geschikt formaat.
     * @param indicatieGeheim String.
     * @return Omgezette waarde.
     */
    String getIndicatieGeheim(final String indicatieGeheim);

    /**
     * Zet de waarde om in een voor de viewer geschikt formaat.
     * @param code String.
     * @return Omgezette waarde.
     */
    String getIndicatiePKVolledigGeconverteerdCode(final String code);

    /**
     * Zet de waarde om in een voor de viewer geschikt formaat.
     * @param verbintenis String.
     * @return Omgezette waarde.
     */
    String getSoortVerbintenis(final String verbintenis);

    /**
     * Zet de waarde om in een voor de viewer geschikt formaat.
     * @param indicatie String.
     * @return Omgezette waarde.
     */
    String getIndicatieCurateleRegister(final String indicatie);

    /**
     * Zet de waarde om in een voor de viewer geschikt formaat.
     * @param indicatie String.
     * @return Omgezette waarde.
     */
    String getIndicatieGezagMinderjarige(final String indicatie);

    /**
     * Zet de waarde om in een voor de viewer geschikt formaat.
     * @param indicatie String.
     * @return Omgezette waarde.
     */
    String getIndicatieOnjuist(final String indicatie);

    /**
     * Zet de waarde om in een voor de viewer geschikt formaat.
     * @param indicatie String.
     * @return Omgezette waarde.
     */
    String getIndicatieDocument(final String indicatie);

    /**
     * Zet de waarde om in een voor de viewer geschikt formaat.
     * @param signalering String.
     * @return Omgezette waarde.
     */
    String getSignalering(final String signalering);

    /**
     * Haalt de stamgegevens van Lo3 Aanduiding inhouding/vermissing NL reisdocument op.
     * @param loAanduidingInhVermNlReisdoc String
     * @return formatted String
     */
    String getAanduidingInhoudingVermissingNlReisdocument(final String loAanduidingInhVermNlReisdoc);

    /**
     * Haalt de stamgegevens van Lo3 Aangifte Adreshouding op.
     * @param lo3AangifteAdreshoudingCode String
     * @return formatted String
     */
    String getAangifteAdreshouding(final String lo3AangifteAdreshoudingCode);

    /**
     * Haalt de stamgegevens van Tabel 38 Adellijke Titel Predikaat op.
     * @param lo3AdellijkeTitelPredikaatCode String
     * @return formatted String
     */
    String getAdellijkeTitelPredikaat(final String lo3AdellijkeTitelPredikaatCode);

    /**
     * Haalt de stamgegevens van Tabel 49 Autoritieit van afgifte Nederlands reisdocument op.
     * @param lo3ReisdocumentAutoriteitVanAfgifte String
     * @return formatted String
     */
    @Override
    String getAutoriteitVanAfgifteReisdocument(final String lo3ReisdocumentAutoriteitVanAfgifte);

    /**
     * Haalt de stamgegevens van Lo3 Reden Opschorting op.
     * @param lo3RedenOpschortingBijhoudingCode String
     * @return formatted String
     */
    String getRedenOpschorting(final String lo3RedenOpschortingBijhoudingCode);

    /**
     * Haalt de stamgegevens van Tabel 48 Nederlands reisdocument op.
     * @param lo3ReisdocumentSoort String
     * @return formatted String
     */
    @Override
    String getSoortNlReisdocument(final String lo3ReisdocumentSoort);

    /**
     * Haalt de stamgegevens van Tabel 60 RNI-deelnemer op. Deze zijn niet beschikbaar dus de omschrijving van de
     * RNI-deelnemer zal die van de gekoppelde BrpPartij zijn.
     * @param lo3RniDeelnemerCode Lo3RNIDeelnemerCode
     * @return formatted String
     */
    String getRNIDeelnemer(final Lo3RNIDeelnemerCode lo3RniDeelnemerCode);
}
