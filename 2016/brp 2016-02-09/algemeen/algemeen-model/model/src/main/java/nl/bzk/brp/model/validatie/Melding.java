/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.validatie;

import java.util.Collections;
import java.util.List;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortMelding;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.basis.AbstractBerichtEntiteit;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.basis.BerichtEntiteitGroep;
import nl.bzk.brp.model.basis.BerichtIdentificeerbaar;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;


/**
 * Model class voor een melding in een antwoordbericht. Merk op dat een Melding grotendeels Immutable is. Alleen de omschrijving kan eventueel nog worden
 * aangepast na initialisatie.
 */
public class Melding extends AbstractBerichtEntiteit implements Comparable<Melding> {

    private static final int HASHCODE_ONEVEN_GETAL1 = 37;
    private static final int HASHCODE_ONEVEN_GETAL2 = 17;

    private       SoortMelding soort;
    private final Regel        regel;
    private       String       meldingTekst;
    private       String       attribuutNaam;

    /**
     * Standaard lege constructor t.b.v. JiBX en andere frameworks.
     */
    private Melding() {
        this(SoortMelding.FOUT, (Regel) null, null, null, null);
    }

    /**
     * Standaard constructor die direct alle waardes initialiseert, waarbij de omschrijving wordt gezet op de standaard omschrijving horende bij de
     * meldingcode.
     *
     * @param soort de soort melding.
     * @param regel de regel voor de melding (mag niet <code>null</code> zijn).
     */
    public Melding(final SoortMelding soort, final Regel regel) {
        this(soort, regel, regel.getOmschrijving(), null, null);
    }

    /**
     * Instantieert een nieuwe Melding.
     *
     * @param soort          de soort
     * @param regel          de regel
     * @param groep          de groep
     * @param attribuutNaam  de attribuut naam
     */
    public Melding(final SoortMelding soort, final Regel regel, final BerichtIdentificeerbaar groep,
        final String attribuutNaam)
    {
        this(soort, regel, regel.getOmschrijving(), groep, attribuutNaam);
    }

    /**
     * Standaard constructor die direct alle waardes initialiseert.
     *
     * @param soort        de soort melding.
     * @param regel        de regel van de melding.
     * @param meldingTekst de omschrijving van de melding.
     */
    public Melding(final SoortMelding soort, final Regel regel, final String meldingTekst) {
        this(soort, regel, meldingTekst, null, null);
    }

    /**
     * Standaard constructor die direct alle waardes initialiseert.
     *
     * @param soort          de soort melding.
     * @param regel          de regel van de melding.
     * @param meldingTekst   de omschrijving van de melding.
     * @param groep          het groep dat een zendendId bevat
     * @param attribuutNaam de attribuutnaam dat niet correct is.
     */
    public Melding(final SoortMelding soort, final Regel regel, final String meldingTekst,
        final BerichtIdentificeerbaar groep, final String attribuutNaam)
    {
        this.soort = soort;
        this.regel = regel;
        this.meldingTekst = meldingTekst;
        this.attribuutNaam = attribuutNaam;
        if (groep != null) {
            setReferentieID(groep.getCommunicatieID());
        }
    }

    /**
     * Retourneert de soort melding; de soort geeft aan of het een melding is ter info of bijvoorbeeld een echte fout.
     *
     * @return de soort melding
     */
    public SoortMelding getSoort() {
        return this.soort;
    }

    /**
     * Retourneert de code van de melding; de code geeft het type van de melding/fout aan.
     *
     * @return de code van de melding.
     */
    public Regel getRegel() {
        return this.regel;
    }

    /**
     * Overrule het niveau van de melding. De implementatie van overruled is op dit ogenblik door dit object zelf bepaald.
     */
    public void overrule() {
        if (this.soort == SoortMelding.DEBLOKKEERBAAR) {
            this.soort = SoortMelding.WAARSCHUWING;
        }
    }

    /**
     * Retourneert een omschrijving van de melding.
     *
     * @return een omschrijving van de melding.
     */
    public String getMeldingTekst() {
        return this.meldingTekst;
    }

    /**
     * Zet een omschrijving van de melding.
     *
     * @param meldingTekst een omschrijving van de melding.
     */
    public void setMeldingTekst(final String meldingTekst) {
        this.meldingTekst = meldingTekst;
    }

    /**
     * Geef de attribuutNaam waarover deze melding gaat.
     *
     * @return de attribuutnaam waarover de melding gaat
     */
    public String getAttribuutNaam() {
        return this.attribuutNaam;
    }

    /**
     * Zet de attribuutnaam waarover de melding gaat.
     *
     * @param attribuutNaam de attribuutnaam
     */
    public void setAttribuutNaam(final String attribuutNaam) {
        this.attribuutNaam = attribuutNaam;
    }

    @Override
    public int compareTo(final Melding that) {
        // pointer vergelijken.
        if (this == that) {
            return 0;
        }

        final int isGelijk = 0;

        int vergelijkResultaat = that.getSoort().compareTo(getSoort());

        if (vergelijkResultaat == isGelijk) {
            vergelijkResultaat = getRegel().getCode().compareTo(that.getRegel().getCode());
        }

        if (vergelijkResultaat == isGelijk) {
            if (StringUtils.isBlank(getReferentieID()) && StringUtils.isBlank(that.getReferentieID())) {
                vergelijkResultaat = isGelijk;
            } else {
                if (getReferentieID() != null && that.getReferentieID() != null) {
                    vergelijkResultaat = getReferentieID().compareTo(that.getReferentieID());
                } else if (getReferentieID() == null) {
                    // sorteer null altijd onder niet null
                    vergelijkResultaat = -1;
                } else {
                    // sorteer niet null altijd boven null
                    vergelijkResultaat = 1;
                }
            }
        }
        if (vergelijkResultaat == isGelijk) {
            if (StringUtils.isBlank(getAttribuutNaam()) && StringUtils.isBlank(that.getAttribuutNaam())) {
                vergelijkResultaat = isGelijk;
            } else {
                if (getAttribuutNaam() != null && that.getAttribuutNaam() != null) {
                    vergelijkResultaat = getAttribuutNaam().compareTo(that.getAttribuutNaam());
                } else if (getAttribuutNaam() == null) {
                    vergelijkResultaat = -1;
                } else {
                    vergelijkResultaat = 1;
                }
            }
        }

        return vergelijkResultaat;
    }

    @Override
    public boolean equals(final Object obj) {
        final boolean resultaat;

        if (obj == null || obj.getClass() != getClass()) {
            resultaat = false;
        } else if (obj == this) {
            resultaat = true;
        } else {
            final Melding rhs = (Melding) obj;
            resultaat =
                new EqualsBuilder().append(getSoort(), rhs.getSoort())
                    .append(getRegel().getCode(), rhs.getRegel().getCode())
                    .append(getReferentieID(), rhs.getReferentieID())
                    .append(getAttribuutNaam(), rhs.getAttribuutNaam()).isEquals();
        }
        return resultaat;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(HASHCODE_ONEVEN_GETAL1, HASHCODE_ONEVEN_GETAL2).append(getSoort())
            .append(getRegel().getCode()).append(getReferentieID()).append(getAttribuutNaam()).toHashCode();
    }

    @Override
    public Integer getMetaId() {
        return null;
    }

    @Override
    public List<BerichtEntiteit> getBerichtEntiteiten() {
        return Collections.emptyList();
    }

    @Override
    public List<BerichtEntiteitGroep> getBerichtEntiteitGroepen() {
        return Collections.emptyList();
    }
}
