/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.esb.message.lo3.impl;

import java.util.List;

import nl.moderniseringgba.isc.esb.message.lo3.format.Lo3CategorieWaardeFormatter;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Datum;
import nl.moderniseringgba.migratie.conversie.model.lo3.syntax.Lo3CategorieWaarde;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Tb02OntkenningVaderschapDoorMoeder.
 */
public final class Tb02OntkenningVaderschapDoorMoederBericht extends Tb02Bericht {
    private static final long serialVersionUID = 1L;

    private static final String LO3_PERSOONSLIJST_ELEMENT = "lo3Persoonslijst";

    /**
     * Convenient constructor.
     * 
     * @param registratieGemeente
     *            De registratiegemeente van de erkenning.
     * @param aktenummer
     *            Het aktenummer van de erkenning.
     * @param ingangsdatumGeldigheid
     *            De ingangsdatum van de erkenning.
     */
    public Tb02OntkenningVaderschapDoorMoederBericht(
            final String registratieGemeente,
            final String aktenummer,
            final Lo3Datum ingangsdatumGeldigheid) {
        super();
        setRegistratieGemeente(registratieGemeente);
        setAktenummer(aktenummer);
        setIngangsdatumGeldigheid(ingangsdatumGeldigheid);
    }

    /* ************************************************************************************************************* */

    // CHECKSTYLE:OFF Omdat we veel informatie uit de Lo3 persoonslijst moeten halen is het executable statement count
    // te hoog.
    @Override
    protected List<Lo3CategorieWaarde> formatInhoud() {
        // CHECKSTYLE:ON
        if (getLo3Persoonslijst() == null || getRegistratieGemeente() == null || getAktenummer() == null
                || getIngangsdatumGeldigheid() == null) {
            throw new IllegalStateException("De Lo3Persoonslijst en aktegegevens mogen niet leeg zijn.");
        }

        final Lo3CategorieWaardeFormatter formatter = new Lo3CategorieWaardeFormatter();
        voegPersoonsCategorieenToe(formatter);
        voegOuder1CategorieenToe(formatter);

        return formatter.getList();
    }

    /* ************************************************************************************************************* */

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Tb02Bericht)) {
            return false;
        }

        return new EqualsBuilder().appendSuper(super.equals(other)).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().appendSuper(super.hashCode()).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString())
                .append(LO3_PERSOONSLIJST_ELEMENT, getLo3Persoonslijst()).toString();
    }

}
