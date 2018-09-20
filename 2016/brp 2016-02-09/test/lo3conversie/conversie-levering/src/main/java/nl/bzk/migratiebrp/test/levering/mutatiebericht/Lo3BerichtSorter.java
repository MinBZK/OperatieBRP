/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.levering.mutatiebericht;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import nl.bzk.migratiebrp.bericht.model.BerichtSyntaxException;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3Header;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3Inhoud;
import nl.bzk.migratiebrp.bericht.model.lo3.factory.Lo3BerichtFactory;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;

/**
 * Util-class voor het sorteren van de inhoud van LO3-berichten.
 */
public final class Lo3BerichtSorter {

    private static final Logger LOG = LoggerFactory.getLogger();

    private Lo3BerichtSorter() {
        // Niet instantieerbaar
    }

    /**
     * Sorteer het bericht.
     *
     * @param bericht
     *            Het te sorteren bericht.
     * @return Het gesorteerde bericht.
     */
    public static String sorteer(final String bericht) {
        if (bericht == null || "".equals(bericht)) {
            return bericht;
        }

        try {
            final Lo3BerichtFactory lo3BerichtFactory = new Lo3BerichtFactory();
            final Lo3Bericht lo3Bericht = lo3BerichtFactory.getBericht(bericht);
            final Lo3Header header = lo3Bericht.getHeader();
            final int headerSize = getTotalHeaderSize(header, bericht);
            final String inhoud = bericht.substring(headerSize);

            List<Lo3CategorieWaarde> categorieen;
            categorieen = Lo3Inhoud.parseInhoud(inhoud);

            // Categorieen uiteenpluizen naar Stapels
            final List<Lo3Stapel> stapels = parseStapels(categorieen);
            Collections.sort(stapels, new Lo3StapelComparator());
            final List<Lo3CategorieWaarde> gesorteerdeCategorieen = new ArrayList<>();
            for (final Lo3Stapel stapel : stapels) {
                gesorteerdeCategorieen.addAll(stapel.getCategorieen());
            }
            final String gesorteerdeInhoud = Lo3Inhoud.formatInhoud(gesorteerdeCategorieen);
            return bericht.substring(0, headerSize) + gesorteerdeInhoud;
        } catch (final BerichtSyntaxException e) {
            LOG.warn("Bericht parse exception voor bericht: " + bericht);
            return bericht;
        }
    }

    /**
     * Geef de waarde van total header size.
     *
     * @param lo3Header
     *            bevat de informatie over welke headers in het bericht zitten.
     * @param lo3
     *            het bericht als string waaruit de headers worden geparsed.
     *
     * @return total header size
     * @throws BerichtSyntaxException
     *             In het geval dat de headers niet geparsed kunnen worden
     */
    public static int getTotalHeaderSize(final Lo3Header lo3Header, final String lo3) throws BerichtSyntaxException {
        int totalHeaderSize = 0;
        for (final String header : lo3Header.parseHeaders(lo3)) {
            totalHeaderSize += header.length();
        }
        return totalHeaderSize;
    }

    private static List<Lo3Stapel> parseStapels(final List<Lo3CategorieWaarde> categorieen) {
        final List<Lo3Stapel> result = new ArrayList<>();
        Lo3Stapel huidigeStapel = null;

        for (final Lo3CategorieWaarde categorie : categorieen) {
            if (categorie.getCategorie().isActueel()) {
                huidigeStapel = new Lo3Stapel(categorie);
                result.add(huidigeStapel);
            } else {
                huidigeStapel.addCategorie(categorie);
            }
        }

        return result;
    }

    /**
     * Implementatie van de LO3-stapel.
     */
    private static final class Lo3Stapel {
        private final Lo3CategorieWaarde actueleCategorie;
        private final List<Lo3CategorieWaarde> categorieen = new ArrayList<>();

        /**
         * Default constructor.
         *
         * @param actueleCategorie
         *            De actuele categorie van de stapel.
         */
        public Lo3Stapel(final Lo3CategorieWaarde actueleCategorie) {
            this.actueleCategorie = actueleCategorie;
            categorieen.add(actueleCategorie);
        }

        /**
         * Voegt een categorie toe aan de categorieën.
         *
         * @param categorie
         *            De toe te voegen categorie.
         */
        public void addCategorie(final Lo3CategorieWaarde categorie) {
            categorieen.add(categorie);
        }

        /**
         * Geeft alle categorieën terug.
         *
         * @return Alle categorieën.
         */
        public List<Lo3CategorieWaarde> getCategorieen() {
            return categorieen;
        }

        /**
         * Geeft de actuele categorie terug.
         *
         * @return De actuele categorie.
         */
        public Lo3CategorieEnum getActueleCategorie() {
            return actueleCategorie.getCategorie();
        }

        /**
         * Geeft de waarde om op te vergleijken terug.
         *
         * @return De waarde om op te vergelijken.
         */
        public String getVergelijkingsElementen() {
            final StringBuilder result = new StringBuilder();
            for (final Lo3CategorieWaarde categorie : categorieen) {
                for (final String value : categorie.getElementen().values()) {
                    result.append(value);
                }
            }

            return result.toString();
        }
    }

    /**
     * Implementatie van de een vergelijker voor LO3-stapels.
     */
    private static final class Lo3StapelComparator implements Comparator<Lo3Stapel>, Serializable {
        private static final long serialVersionUID = 1L;

        @Override
        public int compare(final Lo3Stapel deze, final Lo3Stapel die) {

            int result = deze.getActueleCategorie().compareTo(die.getActueleCategorie());

            if (result == 0) {
                final String dezeWaarde = deze.getVergelijkingsElementen();
                final String dieWaarde = die.getVergelijkingsElementen();
                result = dezeWaarde.compareTo(dieWaarde);
            }
            return result;

        }

    }

}
