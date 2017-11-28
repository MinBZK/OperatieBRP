/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.format;

import java.util.List;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.brp.levering.lo3.conversie.IdentificatienummerMutatie;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3Header;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3HeaderVeld;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3Inhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;

/**
 * Uitgaand Wa11 bericht: wijziging a-nummer.
 * <p>
 * LO 3.8:
 * <p>
 * Kop bevat: <ul> <li>random key = 8 posities</li> <li>berichtnummer = 4 posities</li> <li>(nieuw) A-nummer = 10 posities</li> <li>datum geldigheid = 8
 * posities</li> </ul> Inhoud bevat: <ul> <li>rubriek 01.01.10 (oude) A-nummer</li> <li>alsmede uit categorie 01 Persoon de groepen: <ul> <li>02 Naam,
 * indien de afnemer hiervoor is geautoriseerd</li> <li>03 Geboorte, indien de afnemer hiervoor is geautoriseerd</li> </ul> </li> </ul>
 */
public abstract class AbstractWa11Formatter implements Formatter {

    private static final Logger LOGGER = LoggerFactory.getLogger();
    private static final Lo3Header HEADER =
            new Lo3Header(Lo3HeaderVeld.RANDOM_KEY, Lo3HeaderVeld.BERICHTNUMMER, Lo3HeaderVeld.A_NUMMER, Lo3HeaderVeld.DATUM);

    @Override
    public final String maakPlatteTekst(
            final Persoonslijst persoon,
            final IdentificatienummerMutatie identificatienummerMutatie,
            final List<Lo3CategorieWaarde> categorieen,
            final List<Lo3CategorieWaarde> categorieenGefilterd) {
        LOGGER.debug("Maak Wa11 bericht");
        final String nieuwAnummer = bepaalNieuwAnummer(categorieen, identificatienummerMutatie);
        final String oudAnummer = bepaalOudAnummer(categorieen, identificatienummerMutatie);
        final String datumIngangGeldigheid = bepaalDatumIngangGeldigheid(categorieen, identificatienummerMutatie);

        // toevoegen *OUDE* a-nummer
        bepaalCategorie01(categorieenGefilterd).addElement(Lo3ElementEnum.ELEMENT_0110, oudAnummer);

        return formatHeader(nieuwAnummer, datumIngangGeldigheid) + Lo3Inhoud.formatInhoud(categorieenGefilterd);
    }

    /**
     * Bepaal datum ingang geldigheid
     * @param categorieen categorieen
     * @param identificatienummerMutatie identificatienummer mutatie resultaat
     */
    protected abstract String bepaalDatumIngangGeldigheid(
            final List<Lo3CategorieWaarde> categorieen,
            final IdentificatienummerMutatie identificatienummerMutatie);

    /**
     * Bepaal het oude a-nummer.
     * @param categorieen categorieen (ongefilterd)
     * @param identificatienummerMutatieResultaat het identificatienummer mutatie resultaat.
     * @return het oude a-nummer
     */
    protected abstract String bepaalOudAnummer(
            final List<Lo3CategorieWaarde> categorieen,
            final IdentificatienummerMutatie identificatienummerMutatieResultaat);

    /**
     * Bepaal het nieuwe a-nummer.
     * @param categorieen categorieen (ongefilterd)
     * @param identificatienummerMutatieResultaat het identificatienummer mutatie resultaat.
     * @return het nieuwe a-nummer
     */
    protected abstract String bepaalNieuwAnummer(
            final List<Lo3CategorieWaarde> categorieen,
            final IdentificatienummerMutatie identificatienummerMutatieResultaat);

    /**
     * Bepaal categorie 01 uit gefilterde categorie; voeg categorie toe als de categorie niet bestaat.
     * @param categorieen categorieen
     * @return categorie 01
     */
    private Lo3CategorieWaarde bepaalCategorie01(final List<Lo3CategorieWaarde> categorieen) {
        for (final Lo3CategorieWaarde categorie : categorieen) {
            if (categorie.getCategorie() == Lo3CategorieEnum.CATEGORIE_01) {
                return categorie;
            }
        }
        final Lo3CategorieWaarde categorie01 = new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_01, 0, 0);
        categorieen.add(categorie01);
        return categorie01;
    }

    /**
     * Format de Wa11 header.
     * @param nieuwAnummer nieuw a-nummer
     * @param datumIngangGeldigheid datum ingang geldigheid
     * @return header
     */
    private String formatHeader(final String nieuwAnummer, final String datumIngangGeldigheid) {
        final String[] headers = new String[]{null, "Wa11", nieuwAnummer, datumIngangGeldigheid};
        return HEADER.formatHeaders(headers);
    }

}
