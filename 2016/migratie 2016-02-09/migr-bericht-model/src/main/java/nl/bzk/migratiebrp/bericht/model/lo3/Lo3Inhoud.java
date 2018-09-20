/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.lo3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import nl.bzk.migratiebrp.bericht.model.BerichtSyntaxException;
import nl.bzk.migratiebrp.conversie.model.exceptions.Lo3SyntaxException;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;
import nl.bzk.migratiebrp.util.common.EncodingConstants;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.util.gbav.GBACharacterSet;

/**
 * LO3 Inhoud (helper).
 */
public final class Lo3Inhoud {

    private static final Logger LOG = LoggerFactory.getLogger();

    private static final int CATEGORIE_VELDLENGTE = 2;
    private static final int CATEGORIEGROOTTE_VELDLENGTE = 3;
    private static final int ELEMENTGROOTTE_VELDLENGTE = 3;
    private static final int ELEMENTNUMMER_VELDLENGTE = 4;
    private static final int BERICHTGROOTTE_VELDLENGTE = 5;
    private static final int DEFAULT_WAARDE_STAPEL = 999;
    private static final int DEFAULT_WAARDE_VOORKOMEN = 999;
    private static final int HISTORIE_CATEGORIE_VERSCHIL = 50;

    private Lo3Inhoud() {
        throw new AssertionError("Niet instantieerbaar");
    }

    /**
     * Format de inhoud van een bericht (naar Teletex string formaat) van een lijst met Categorie waarden.
     *
     * @param input
     *            categorieen
     * @return een bericht
     */
    public static String formatInhoud(final List<Lo3CategorieWaarde> input) {
        final List<Lo3InhoudCategorie> categorieen = new ArrayList<>();
        if (input != null) {
            for (final Lo3CategorieWaarde categorieWaarde : input) {
                final Lo3InhoudCategorie categorie = new Lo3InhoudCategorie(categorieWaarde.getCategorie().getCategorie());

                // sorteer de keys
                final List<Lo3ElementEnum> sorted = new ArrayList<>(categorieWaarde.getElementen().keySet());
                Collections.sort(sorted);
                for (final Lo3ElementEnum key : sorted) {
                    categorie.addElement(new Lo3InhoudElement(key, categorieWaarde.getElementen().get(key)));
                }

                categorieen.add(categorie);
            }
        }

        int size = 0;
        for (final Lo3InhoudCategorie categorie : categorieen) {
            size = size + categorie.getBytes().length;
        }

        final byte[] data = new byte[BERICHTGROOTTE_VELDLENGTE + size];
        System.arraycopy(String.format("%1$05d", size).getBytes(EncodingConstants.CHARSET), 0, data, 0, BERICHTGROOTTE_VELDLENGTE);

        int index = BERICHTGROOTTE_VELDLENGTE;
        for (final Lo3InhoudCategorie categorie : categorieen) {
            System.arraycopy(categorie.getBytes(), 0, data, index, categorie.getBytes().length);
            index = index + categorie.getBytes().length;
        }

        return GBACharacterSet.convertTeletexByteArrayToString(data);
    }

    /**
     * Parse de inhoud van een bericht (in Teletex string formaat) naar een lijst met CategorieWaarden.
     *
     * @param inhoud
     *            de inhoud, mag niet null zijn en moet beginnen met de berichtlengte
     * @throws BerichtSyntaxException
     *             bij syntax fouten
     * @return een lijst met CategorieWaarden
     */
    public static List<Lo3CategorieWaarde> parseInhoud(final String inhoud) throws BerichtSyntaxException {
        if (inhoud == null) {
            LOG.debug("Bericht is niet een valide teletex string: <null>");
            throw new BerichtSyntaxException("Bericht is niet een valide teletex string");
        }
        try {
            final int berichtLengte = bepaalBerichtLengte(inhoud);

            final List<Lo3CategorieWaarde> categorieen = new ArrayList<>();
            int cursor = BERICHTGROOTTE_VELDLENGTE;

            Lo3CategorieEnum vorigeCategorie = null;
            int stapel = DEFAULT_WAARDE_STAPEL;
            int voorkomen = DEFAULT_WAARDE_VOORKOMEN;

            while (cursor < berichtLengte) {
                final String categorieString = inhoud.substring(cursor, cursor + CATEGORIE_VELDLENGTE);
                final Lo3CategorieEnum cat = Lo3CategorieEnum.getLO3Categorie(categorieString);

                voorkomen++;
                if (cat.isActueel()) {
                    stapel++;
                    voorkomen = 0;
                    if (vorigeCategorie == null
                        || Integer.parseInt(cat.getCategorie()) % HISTORIE_CATEGORIE_VERSCHIL != Integer.parseInt(vorigeCategorie.getCategorie())
                                                                                                 % HISTORIE_CATEGORIE_VERSCHIL)
                    {
                        stapel = 0;
                    }
                }

                final Lo3CategorieWaarde categorieWaarde = new Lo3CategorieWaarde(cat, stapel, voorkomen);
                int categorieLengte = bepaalCategorieLengte(inhoud, cursor);
                cursor = cursor + CATEGORIE_VELDLENGTE + CATEGORIEGROOTTE_VELDLENGTE;
                categorieLengte = categorieLengte + cursor;
                while (cursor < categorieLengte) {
                    final String elementNummer = inhoud.substring(cursor, cursor + ELEMENTNUMMER_VELDLENGTE);
                    cursor = cursor + ELEMENTNUMMER_VELDLENGTE;
                    final int l = Integer.parseInt(inhoud.substring(cursor, cursor + ELEMENTGROOTTE_VELDLENGTE));
                    cursor = cursor + ELEMENTGROOTTE_VELDLENGTE;
                    final String teletexWaarde = inhoud.substring(cursor, cursor + l);
                    final String waarde = "".equals(teletexWaarde) ? "" : GBACharacterSet.converteerTeletexNaarUnicodeBehoudOnbekend(teletexWaarde);
                    cursor = cursor + l;
                    final Lo3ElementEnum element = Lo3ElementEnum.getLO3Element(elementNummer);
                    categorieWaarde.addElement(element, waarde);
                }
                categorieen.add(categorieWaarde);

                vorigeCategorie = cat;
            }
            return categorieen;
        } catch (final
            Lo3SyntaxException
            | NumberFormatException e)
        {
            LOG.debug("Fout tijdens bericht parsen", e);
            throw new BerichtSyntaxException(e.getMessage(), e);
        }
    }

    private static int bepaalCategorieLengte(final String inhoud, final int cursor) throws BerichtSyntaxException {
        final int startIndex = cursor + CATEGORIE_VELDLENGTE;
        final int eindIndex = startIndex + CATEGORIEGROOTTE_VELDLENGTE;

        if (inhoud == null || inhoud.length() < eindIndex) {
            throw new BerichtSyntaxException("Categorie lengte overschrijdt bericht lengte.");
        }

        final String categorieLengte = inhoud.substring(startIndex, eindIndex);
        try {
            return Integer.parseInt(categorieLengte);
        } catch (final NumberFormatException nfee) {
            throw new BerichtSyntaxException("Categorie lengte moet numeriek zijn, maar is:" + categorieLengte, nfee);
        }
    }

    private static int bepaalBerichtLengte(final String inhoud) throws BerichtSyntaxException {
        if (inhoud == null || inhoud.length() < BERICHTGROOTTE_VELDLENGTE) {
            throw new BerichtSyntaxException("Berichtinhoud is te kort om een berichtlengte te bevatten.");
        }
        final String lengte = inhoud.substring(0, BERICHTGROOTTE_VELDLENGTE);
        final int berichtLengte;
        try {
            berichtLengte = Integer.parseInt(lengte);
        } catch (final NumberFormatException nfee) {
            throw new BerichtSyntaxException("Berichtlengte moet numeriek zijn, maar is:" + lengte, nfee);
        }
        final int gemetenLengte = inhoud.length() - BERICHTGROOTTE_VELDLENGTE;
        if (berichtLengte != gemetenLengte) {
            throw new BerichtSyntaxException("Berichtlengte klopt niet. Genoemde lengte=" + berichtLengte + ", daadwerkelijke lengte=" + gemetenLengte);
        }
        return berichtLengte;
    }

}
