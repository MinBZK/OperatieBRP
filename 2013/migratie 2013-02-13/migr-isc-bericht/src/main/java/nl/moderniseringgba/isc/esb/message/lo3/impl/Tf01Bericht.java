/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.esb.message.lo3.impl;

import java.io.Serializable;
import java.util.List;

import nl.moderniseringgba.isc.esb.message.BerichtInhoudException;
import nl.moderniseringgba.isc.esb.message.lo3.AbstractLo3Bericht;
import nl.moderniseringgba.isc.esb.message.lo3.Lo3Bericht;
import nl.moderniseringgba.isc.esb.message.lo3.Lo3Header;
import nl.moderniseringgba.isc.esb.message.lo3.Lo3HeaderVeld;
import nl.moderniseringgba.isc.esb.message.lo3.format.Lo3CategorieWaardeFormatter;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3CategorieEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3ElementEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.syntax.Lo3CategorieWaarde;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Tf01. Fout: persoon niet in te schrijven. Indien de persoon niet in te schrijven is, gaat dit bericht terug naar de
 * gemeente van de 'toevallige geboorte'. Te verzenden door: gemeente van eerste inschrijving Te verzenden aan:
 * geboortegemeente
 */
public final class Tf01Bericht extends AbstractLo3Bericht implements Lo3Bericht, Serializable {
    private static final long serialVersionUID = 1L;

    private static final Lo3Header HEADER = new Lo3Header(Lo3HeaderVeld.RANDOM_KEY, Lo3HeaderVeld.BERICHTNUMMER,
            Lo3HeaderVeld.FOUTREDEN, Lo3HeaderVeld.GEMEENTE, Lo3HeaderVeld.A_NUMMER);

    private String akteNummer;

    /**
     * Enum met de mogelijke foutredenen.
     */
    public static enum Foutreden {
        /** Emigratie. */
        E,
        /** Ministrieel besluit. */
        M,
        /** Overleden. */
        O,
        /** Verhuisd. */
        V,
        /** Reeds aanwezig. */
        A,
        /** Geblokkeerd. */
        B,
        /** Niet gevonden. */
        G,
        /** Niet uniek. */
        U
    };

    /**
     * Constructor.
     */
    public Tf01Bericht() {
        super(HEADER);

        setHeader(Lo3HeaderVeld.RANDOM_KEY, null);
        setHeader(Lo3HeaderVeld.BERICHTNUMMER, getBerichtType());
        setHeader(Lo3HeaderVeld.FOUTREDEN, null);
        setHeader(Lo3HeaderVeld.GEMEENTE, null);
        setHeader(Lo3HeaderVeld.A_NUMMER, null);
    }

    /**
     * Convenience constructor.
     * 
     * @param tb01Bericht
     *            Het gerelateerde Tb01Bericht, waar dit Tf01Bericht het antwoord op is.
     * @param foutreden
     *            De foutreden
     */
    public Tf01Bericht(final Tb01Bericht tb01Bericht, final Foutreden foutreden) {
        this();

        akteNummer = tb01Bericht.getHeader(Lo3HeaderVeld.AKTENUMMER);
        setBronGemeente(tb01Bericht.getDoelGemeente());
        setDoelGemeente(tb01Bericht.getBronGemeente());
        setHeader(Lo3HeaderVeld.FOUTREDEN, foutreden.toString());
        setHeader(Lo3HeaderVeld.GEMEENTE, tb01Bericht.getDoelGemeente());
        setHeader(Lo3HeaderVeld.A_NUMMER, tb01Bericht.getHeader(Lo3HeaderVeld.A_NUMMER));
        setCorrelationId(tb01Bericht.getMessageId());
    }

    @Override
    public String getBerichtType() {
        return "Tf01";
    }

    @Override
    public String getStartCyclus() {
        return null;
    }

    /**
     * Geeft de foutreden terug.
     * 
     * @return De foutreden.
     */
    public Foutreden getFoutreden() {
        return Foutreden.valueOf(getHeader(Lo3HeaderVeld.FOUTREDEN));
    }

    /* ************************************************************************************************************* */
    @Override
    protected void parseInhoud(final List<Lo3CategorieWaarde> categorieen) throws BerichtInhoudException {
        // rubriek 01.81.20 Aktenummer uit het Tb01-bericht
        for (final Lo3CategorieWaarde catWaarde : categorieen) {
            if (Lo3CategorieEnum.CATEGORIE_01.equals(catWaarde.getCategorie())) {
                akteNummer = catWaarde.getElement(Lo3ElementEnum.ELEMENT_8120);
                break;
            }
        }
    }

    @Override
    protected List<Lo3CategorieWaarde> formatInhoud() {
        final Lo3CategorieWaardeFormatter formatter = new Lo3CategorieWaardeFormatter();
        formatter.categorie(Lo3CategorieEnum.CATEGORIE_01);
        formatter.element(Lo3ElementEnum.ELEMENT_8120, akteNummer);
        return formatter.getList();
    }

    /* ************************************************************************************************************* */

    public String getAktenummer() {
        return akteNummer;
    }

    /* ************************************************************************************************************* */

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Tf01Bericht)) {
            return false;
        }
        final Tf01Bericht castOther = (Tf01Bericht) other;
        return new EqualsBuilder().appendSuper(super.equals(other)).append(akteNummer, castOther.akteNummer)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().appendSuper(super.hashCode()).append(akteNummer).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString())
                .append("akteNummer", akteNummer).toString();
    }
}
