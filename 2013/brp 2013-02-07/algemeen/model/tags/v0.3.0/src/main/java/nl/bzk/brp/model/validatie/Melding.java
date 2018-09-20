/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.validatie;

import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortMelding;
import nl.bzk.brp.model.basis.AbstractObjectTypeBericht;
import nl.bzk.brp.model.basis.Identificeerbaar;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;


/**
 * Model class voor een melding in een antwoordbericht. Merk op dat een Melding grotendeels Immutable is. Alleen de
 * omschrijving kan eventueel nog worden aangepast na initialisatie.
 */
public class Melding extends AbstractObjectTypeBericht implements Comparable<Melding> {

    private static final int HASHCODE_ONEVEN_GETAL1 = 37;
    private static final int HASHCODE_ONEVEN_GETAL2 = 17;

    private       SoortMelding soort;
    private final MeldingCode  code;
    private       String       omschrijving;
    private       String       attribuutNaam;

    /** Standaard lege constructor t.b.v. JiBX en andere frameworks. */
    private Melding() {
        this(SoortMelding.FOUT, null, null);
    }

    /**
     * Standaard constructor die direct alle waardes initialiseert, waarbij de omschrijving wordt gezet op de standaard
     * omschrijving horende bij de meldingcode.
     *
     * @param soort de soort melding.
     * @param code de code van de melding (mag niet <code>null</code> zijn).
     * @deprecated gebruik de constructor (SoortMelding, MeldingCode, Identificeerbaar, String).
     */
    @Deprecated
    public Melding(final SoortMelding soort, final MeldingCode code) {
        this(soort, code, code.getOmschrijving());
    }

    /**
     * Standaard constructor die direct alle waardes initialiseert, waarbij de omschrijving wordt gezet op de standaard
     * omschrijving horende bij de meldingcode.
     *
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
     *
     * @param soort de soort melding.
     * @param code de code van de melding.
     * @param omschrijving de omschrijving van de melding.
     * @deprecated gebruik de constructor (SoortMelding, MeldingCode, String, Identificeerbaar, String).
     */
    @Deprecated
    public Melding(final SoortMelding soort, final MeldingCode code, final String omschrijving) {
        this.soort = soort;
        this.code = code;
        this.omschrijving = omschrijving;
    }

    /**
     * Standaard constructor die direct alle waardes initialiseert.
     *
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
        attribuutNaam = atttribuutNaam;
        if (groep != null) {
            setCommunicatieID(groep.getCommunicatieID());
        }
    }

    /**
     * Retourneert de soort melding; de soort geeft aan of het een melding is ter info of bijvoorbeeld een echte fout.
     *
     * @return de soort melding
     */
    public SoortMelding getSoort() {
        return soort;
    }

    /**
     * Retourneert de code van de melding; de code geeft het type van de melding/fout aan.
     *
     * @return de code van de melding.
     */
    public MeldingCode getCode() {
        return code;
    }

    /**
     * Overrule het niveau van de melding. De implementatie van overruled is op dit ogenblik door dit object zelf
     * bepaald.
     */
    public void overrule() {
        if (soort == SoortMelding.DEBLOKKEERBAAR) {
            soort = SoortMelding.WAARSCHUWING;
        }
    }

    /**
     * Retourneert een omschrijving van de melding.
     *
     * @return een omschrijving van de melding.
     */
    public String getOmschrijving() {
        return omschrijving;
    }

    /**
     * Zet een omschrijving van de melding.
     *
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

    @Override
    public int compareTo(final Melding that) {
        final int isGelijk = 0;

        // pointer vergelijken.
        if (this == that) {
            return isGelijk;
        }

        int volgorder = that.getSoort().compareTo(getSoort());

        if (volgorder == isGelijk) {
            volgorder = getCode().name().compareTo(that.getCode().name());
        }
        if (volgorder == isGelijk) {
            if (StringUtils.isBlank(getCommunicatieID()) && StringUtils.isBlank(that.getCommunicatieID())) {
                volgorder = isGelijk;
            } else {
                if (getCommunicatieID() != null) {
                    volgorder = getCommunicatieID().compareTo(that.getCommunicatieID());
                } else {
                    volgorder = that.getCommunicatieID().compareTo(getCommunicatieID());
                }
            }
        }
        if (volgorder == isGelijk) {
            if (StringUtils.isBlank(getAttribuutNaam()) && StringUtils.isBlank(that.getAttribuutNaam())) {
                volgorder = isGelijk;
            } else {
                if (getAttribuutNaam() != null) {
                    volgorder = getAttribuutNaam().compareTo(that.getAttribuutNaam());
                } else {
                    volgorder = that.getAttribuutNaam().compareTo(getAttribuutNaam());
                }
            }
        }

        return volgorder;
    }

    /** {@inheritDoc} */
    @Override
    public boolean equals(final Object obj) {
        final boolean resultaat;

        if (obj == null || obj.getClass() != getClass()) {
            resultaat = false;
        } else if (obj == this) {
            resultaat = true;
        } else {
            Melding rhs = (Melding) obj;
            resultaat = new EqualsBuilder().append(getSoort(), rhs.getSoort())
                                           .append(getCode().getNaam(), rhs.getCode().getNaam())
                                           .append(getCommunicatieID(), rhs.getCommunicatieID())
                                           .append(getAttribuutNaam(), rhs.getAttribuutNaam())
                                           .isEquals();
        }
        return resultaat;
    }

    /** {@inheritDoc} */
    @Override
    public int hashCode() {
        return new HashCodeBuilder(HASHCODE_ONEVEN_GETAL1, HASHCODE_ONEVEN_GETAL2).append(getSoort()).append(
            getCode().getNaam()).append(getCommunicatieID()).append(getAttribuutNaam()).toHashCode();
    }
}
