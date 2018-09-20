/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.validatie;

import nl.bzk.brp.model.basis.AbstractIdentificerendeGroep;
import nl.bzk.brp.model.basis.Identificeerbaar;


/**
 * Model class voor een melding in een antwoordbericht. Merk op dat een Melding grotendeels Immutable is. Alleen de
 * omschrijving kan eventueel nog worden aangepast na initialisatie.
 */
public class Melding extends AbstractIdentificerendeGroep {

    private SoortMelding      soort;
    private final MeldingCode code;
    private String            omschrijving;
    private String            attribuutNaam;

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
     * Standaard constructor die direct alle waardes initialiseert, waarbij de omschrijving wordt gezet op de standaard
     * omschrijving horende bij de meldingcode.
     * @param soort de soort melding.
     * @param code de code van de melding (mag niet <code>null</code> zijn).
     * @param groep het groep dat een zendendId bevat
     * @param atttribuutNaam de attribuutnaam dat niet correct is.
     */
    public Melding(final SoortMelding soort, final MeldingCode code, final Identificeerbaar groep,
            final String atttribuutNaam)
    {
        this(soort, code, code.getOmschrijving(), groep, atttribuutNaam);
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
     * Standaard constructor die direct alle waardes initialiseert.
     * @param soort de soort melding.
     * @param code de code van de melding.
     * @param omschrijving de omschrijving van de melding.
     * @param groep het groep dat een zendendId bevat
     * @param atttribuutNaam de attribuutnaam dat niet correct is.
     */
    public Melding(final SoortMelding soort, final MeldingCode code, final String omschrijving,
            final Identificeerbaar groep, final String atttribuutNaam)
    {
        this.soort = soort;
        this.code = code;
        this.omschrijving = omschrijving;
        this.attribuutNaam = atttribuutNaam;
        if (groep != null) {
            setVerzendendId(groep.getVerzendendId());
        }
    }

    /**
     * Retourneert de soort melding; de soort geeft aan of het een melding is ter info of bijvoorbeeld een echte fout.
     * @return de soort melding
     */
    public SoortMelding getSoort() {
        return this.soort;
    }

    /**
     * Retourneert de code van de melding; de code geeft het type van de melding/fout aan.
     * @return de code van de melding.
     */
    public MeldingCode getCode() {
        return this.code;
    }

    /**
     * Overrule het niveau van de melding. De implementatie van overruled is op dit ogenblik door dit object zelf bepaald.
     */
    public void overrule() {
        if (this.soort == SoortMelding.FOUT_OVERRULEBAAR) {
            this.soort = SoortMelding.WAARSCHUWING;
        }
    }

    /**
     * Retourneert een omschrijving van de melding.
     * @return een omschrijving van de melding.
     */
    public String getOmschrijving() {
        return this.omschrijving;
    }

    /**
     * Zet een omschrijving van de melding.
     * @param omschrijving een omschrijving van de melding.
     */
    public void setOmschrijving(final String omschrijving) {
        this.omschrijving = omschrijving;
    }

    public String getAttribuutNaam() {
        return this.attribuutNaam;
    }

    public void setAttribuutNaam(final String attribuutNaam) {
        this.attribuutNaam = attribuutNaam;
    }

}
