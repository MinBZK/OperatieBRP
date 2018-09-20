/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.esb.message.lo3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import nl.ictu.spg.common.util.conversion.GBACharacterSet;
import nl.moderniseringgba.isc.esb.message.BerichtSyntaxException;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3CategorieEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3ElementEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.syntax.Lo3CategorieWaarde;
import nl.moderniseringgba.migratie.logging.Logger;
import nl.moderniseringgba.migratie.logging.LoggerFactory;

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
        final List<Lo3InhoudCategorie> categorieen = new ArrayList<Lo3InhoudCategorie>();
        if (input != null) {
            for (final Lo3CategorieWaarde categorieWaarde : input) {
                final Lo3InhoudCategorie categorie =
                        new Lo3InhoudCategorie(categorieWaarde.getCategorie().getCategorie());

                // sorteer de keys
                final List<Lo3ElementEnum> sorted =
                        new ArrayList<Lo3ElementEnum>(categorieWaarde.getElementen().keySet());
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
        System.arraycopy(String.format("%1$05d", size).getBytes(), 0, data, 0, BERICHTGROOTTE_VELDLENGTE);

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
    // CHECKSTYLE:OFF Refactoren zodat executable statements mindert van 33 naar 30 geeft problemen.
    public static List<Lo3CategorieWaarde> parseInhoud(final String inhoud) throws BerichtSyntaxException {
        // CHECKSTYLE:ON
        if (inhoud == null || !GBACharacterSet.isValidTeletex(inhoud)) {
            LOG.info("Bericht is niet een valide teletex string:\n" + inhoud);

            throw new BerichtSyntaxException("Bericht is niet een valide teletex string");
        }

        final int berichtLengte = bepaalBerichtLengte(inhoud);

        final List<Lo3CategorieWaarde> categorieen = new ArrayList<Lo3CategorieWaarde>();
        int cursor = BERICHTGROOTTE_VELDLENGTE;

        Lo3CategorieEnum vorigeCategorie = null;
        int stapel = DEFAULT_WAARDE_STAPEL;
        int voorkomen = DEFAULT_WAARDE_VOORKOMEN;

        while (cursor < berichtLengte) {
            final String categorieString = inhoud.substring(cursor, cursor + CATEGORIE_VELDLENGTE);
            final Lo3CategorieEnum cat = Lo3CategorieEnum.getLO3Categorie(categorieString);

            voorkomen++;
            if (Integer.valueOf(cat.getCategorie()) < HISTORIE_CATEGORIE_VERSCHIL) {
                stapel++;
                voorkomen = 0;
                if (vorigeCategorie == null
                        || Integer.valueOf(cat.getCategorie()) % HISTORIE_CATEGORIE_VERSCHIL != Integer
                                .valueOf(vorigeCategorie.getCategorie()) % HISTORIE_CATEGORIE_VERSCHIL) {
                    stapel = 0;
                }
            }

            final Lo3CategorieWaarde c = new Lo3CategorieWaarde(cat, stapel, voorkomen);
            int categorieLengte = bepaalCategorieLengte(inhoud, cursor);
            cursor = cursor + CATEGORIE_VELDLENGTE + CATEGORIEGROOTTE_VELDLENGTE;
            categorieLengte = categorieLengte + cursor;
            while (cursor < categorieLengte) {
                final String elementNummer = inhoud.substring(cursor, cursor + ELEMENTNUMMER_VELDLENGTE);
                cursor = cursor + ELEMENTNUMMER_VELDLENGTE;
                final int l = Integer.parseInt(inhoud.substring(cursor, cursor + ELEMENTGROOTTE_VELDLENGTE));
                cursor = cursor + ELEMENTGROOTTE_VELDLENGTE;
                final String teletexWaarde = inhoud.substring(cursor, cursor + l);
                final String waarde = GBACharacterSet.convertTeletex2Unicode(teletexWaarde);
                cursor = cursor + l;
                final Lo3ElementEnum element = Lo3ElementEnum.getLO3Element(elementNummer);
                c.addElement(element, waarde);
            }
            categorieen.add(c);

            vorigeCategorie = cat;
        }

        return categorieen;
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
            throw new BerichtSyntaxException("Berichtlengte klopt niet. Genoemde lengte=" + berichtLengte
                    + ", daadwerkelijke lengte=" + gemetenLengte);
        }
        return berichtLengte;
    }

}
