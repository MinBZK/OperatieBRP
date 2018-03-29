/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.funqmachine.jbehave.validatie;

/**
 * Abstractie over een meta annotatie in een .story file.
 */
final class MetaTag {
    private static final String STATUS_META_TAG_NAAM = "@status";
    private String naam;
    private String waarde;

    /**
     * Constructor, splitst de regel in de naam en de waarde van de tag.
     *
     * @param regel
     *            de regel met meta info
     */
    MetaTag(final String regel) {
        if (!regel.startsWith("@")) {
            throw new IllegalArgumentException("Regel is geen meta regel");
        }
        final String space = " ";
        if (regel.contains(space)) {
            this.naam = regel.substring(0, regel.indexOf(space));
            this.waarde = regel.substring(regel.indexOf(space) + 1).trim();
        } else {
            this.naam = regel;
        }
    }

    /**
     * Geeft de naam terug van de {@link MetaTag}.
     * 
     * @return de naam
     */
    public String getNaam() {
        return naam;
    }

    /**
     * Geeft de waarde terug van de {@link MetaTag}.
     * 
     * @return de waardes
     */
    public String getWaarde() {
        return waarde;
    }

    /**
     * Geeft aan of deze {@link MetaTag} een @status-tag is.
     * 
     * @return true als het een @status-tag is
     */
    boolean isStatusTag() {
        return STATUS_META_TAG_NAAM.equals(naam);
    }
}
