/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.validatie;


/**
 * Model class voor een melding in een antwoordbericht. Merk op dat een Melding grotendeels Immutable is. Alleen de
 * omschrijving kan eventueel nog worden aangepast na initialisatie.
 */
public class Melding {

    private SoortMelding soort;
    private final MeldingCode code;
    private String omschrijving;
    private String attribuutNaam;
    private String verzendendId;


    /**
     * Standaard lege constructor t.b.v. JiBX en andere frameworks.
     */
    private Melding() {
        this(SoortMelding.FOUT_ONOVERRULEBAAR, null, null);
    }

    /**
     * Standaard constructor die direct alle waardes initialiseert, waarbij de omschrijving wordt gezet op de standaard
     * omschrijving horende bij de meldingcode.
     * @param soort de soort melding.
     * @param code de code van de melding (mag niet <code>null</code> zijn).
     */
    public Melding(final SoortMelding soort, final MeldingCode code) {
        this(soort, code, code.getOmschrijving());
    }

    /**
     * Standaard constructor die direct alle waardes initialiseert.
     * @param soort de soort melding.
     * @param code de code van de melding.
     * @param omschrijving de omschrijving van de melding.
     */
    public Melding(final SoortMelding soort, final MeldingCode code, final String omschrijving) {
        this.soort = soort;
        this.code = code;
        this.omschrijving = omschrijving;
    }

    /**
     * Retourneert de soort melding; de soort geeft aan of het een melding is ter info of bijvoorbeeld een echte fout.
     * @return de soort melding
     */
    public SoortMelding getSoort() {
        return soort;
    }

    /**
     * Retourneert de code van de melding; de code geeft het type van de melding/fout aan.
     * @return de code van de melding.
     */
    public MeldingCode getCode() {
        return code;
    }

    /**
     * Overrule het niveau van de melding. De implementatie van overruled is op dit ogenblik door dit object zelf bepaald.
     */
    public void overrule() {
        if (soort == SoortMelding.FOUT_OVERRULEBAAR) {
            soort = SoortMelding.WAARSCHUWING;
        }
        System.out.println("Nu gedowngrade naar " + soort);
    }


    /**
     * Retourneert een omschrijving van de melding.
     * @return een omschrijving van de melding.
     */
    public String getOmschrijving() {
        return omschrijving;
    }

    /**
     * Zet een omschrijving van de melding.
     * @param omschrijving een omschrijving van de melding.
     */
    public void setOmschrijving(final String omschrijving) {
        this.omschrijving = omschrijving;
    }

    public String getAttribuutNaam() {
        return attribuutNaam;
    }

    public void setAttribuutNaam(final String attribuutNaam) {
        this.attribuutNaam = attribuutNaam;
    }

    public String getVerzendendId() {
        return verzendendId;
    }

    public void setVerzendendId(final String verzendendId) {
        this.verzendendId = verzendendId;
    }
}
