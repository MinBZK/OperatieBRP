/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bmr.generator.utils;

/**
 * Een soort CharSequence speciaal voor geformatteerde broncode, met indentatie en regels.
 */
public class ArtifactBuilder implements CharSequence {

    private static final int          INDENTATION      = 4;
    private static final CharSequence REGELEINDE       = System.getProperty("line.separator");
    private int                       indentatieNiveau = 0;
    private StringBuilder             content          = new StringBuilder();

    /**
     * Voeg tekst toe aan dit artifact.
     *
     * @param inhoud de tekst om toe te voegen.
     */
    public ArtifactBuilder append(final CharSequence inhoud) {
        this.content.append(inhoud);
        return this;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public char charAt(final int index) {
        return content.charAt(index);
    }

    /**
     * Verlaag indentatieniveau.
     *
     * @return this.
     */
    public ArtifactBuilder decr() {
        indentatieNiveau -= INDENTATION;
        return this;
    }

    /**
     * Verhoog indentatieniveau.
     *
     * @return this.
     */
    public ArtifactBuilder incr() {
        indentatieNiveau += INDENTATION;
        return this;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public int length() {
        return content.length();
    }

    /**
     * Schrijf een hele regel naar het resultaat met de juiste indentatie en regeleinde.
     *
     * @param regelFragment de regelfragmenten waaruit de regel gevormd wordt.
     * @return this.
     */
    public ArtifactBuilder regel(final CharSequence... regelFragment) {
        if (regelFragment.length > 0) {
            indent();
        }
        for (CharSequence regel : regelFragment) {
            content.append(regel);
        }
        content.append(REGELEINDE);
        return this;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public CharSequence subSequence(final int start, final int end) {
        return content.subSequence(start, end);
    }

    /**
     * Geeft de inhoud van dit object terug als String. Delegeert aan de StringBuilder die de inhoud bevat.
     *
     * @return de inhoud van de StringBuilder.
     */
    @Override
    public String toString() {
        return content.toString();
    }

    /**
     * Indenteer de vereiste hoeveelheid.
     */
    private void indent() {
        content.append(getIndentatie());
    }

    /**
     * Geef de benodigde indentatie.
     *
     * @return De benodigde indentatie.
     */
    public CharSequence getIndentatie() {
        StringBuilder indentatie = new StringBuilder();
        for (int i = 0; i < indentatieNiveau; i++) {
            indentatie.append(" ");
        }
        return indentatie;
    }

    /**
     * @return the indentatieNiveau
     */
    public int getIndentatieNiveau() {
        return indentatieNiveau;
    }

    public CharSequence getNieuweRegel() {
        StringBuilder resultaat = new StringBuilder();
        resultaat.append(getRegeleinde());
        resultaat.append(getIndentatie());
        return resultaat;
    }

    /**
     * @return the regeleinde
     */
    public static CharSequence getRegeleinde() {
        return REGELEINDE;
    }
}
