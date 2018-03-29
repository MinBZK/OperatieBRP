/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.brp.groep.autorisatie;

import nl.bzk.algemeenbrp.dal.domein.brp.enums.LeverwijzeSelectie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortSelectie;
import nl.bzk.algemeenbrp.util.xml.annotation.Element;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatum;
import nl.bzk.migratiebrp.conversie.model.brp.groep.AbstractBrpGroepInhoud;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Deze class representeert de inhoud van de groep BRP Dienst Selectie.
 *
 * Deze class is immutable en threadsafe.
 */
public final class BrpDienstSelectieInhoud extends AbstractBrpGroepInhoud {

    @Element(name = "soortSelectie", required = false)
    private final SoortSelectie soortSelectie;
    @Element(name = "eersteSelectieDatum", required = false)
    private final BrpDatum eersteSelectieDatum;
    @Element(name = "selectiePeriodeInMaanden", required = false)
    private final Short selectiePeriodeInMaanden;
    @Element(name = "indVerzendenVolledigBerichtBijPlaatsen", required = false)
    private final Boolean indVerzendenVolledigBerichtBijPlaatsen;
    @Element(name = "leverwijzeSelectie", required = false)
    private final LeverwijzeSelectie leverwijzeSelectie;

    /**
     * Maak een BrpDienstSelectieInhoud object.
     * @param soortSelectie soort selectie
     * @param eersteSelectieDatum eerste selectie datum
     * @param selectiePeriodeInMaanden selectie periode in maanden
     * @param indVerzendenVolledigBerichtBijPlaatsen indicatie verzenden volledig bericht bij plaatsen
     * @param leverwijzeSelectie De wijze van leveren van de selectie
     */
    public BrpDienstSelectieInhoud(
            @Element(name = "soortSelectie", required = false) final SoortSelectie soortSelectie,
            @Element(name = "eersteSelectieDatum", required = false) final BrpDatum eersteSelectieDatum,
            @Element(name = "selectiePeriodeInMaanden", required = false) final Short selectiePeriodeInMaanden,
            @Element(name = "indVerzendenVolledigBerichtBijPlaatsen", required = false) final Boolean indVerzendenVolledigBerichtBijPlaatsen,
            @Element(name = "leverwijzeSelectie", required = false) final LeverwijzeSelectie leverwijzeSelectie) {
        super();
        this.soortSelectie = soortSelectie;
        this.eersteSelectieDatum = eersteSelectieDatum;
        this.selectiePeriodeInMaanden = selectiePeriodeInMaanden;
        this.indVerzendenVolledigBerichtBijPlaatsen = indVerzendenVolledigBerichtBijPlaatsen;
        this.leverwijzeSelectie = leverwijzeSelectie;
    }

    /**
     * Geef de waarde van soort selectie van BrpDienstSelectieInhoud.
     * @return de waarde van soort selectie van BrpDienstSelectieInhoud
     */
    public SoortSelectie getSoortSelectie() {
        return soortSelectie;
    }

    /**
     * Geef de waarde van eerste selectie datum van BrpDienstSelectieInhoud.
     * @return de waarde van eerste selectie datum van BrpDienstSelectieInhoud
     */
    public BrpDatum getEersteSelectieDatum() {
        return eersteSelectieDatum;
    }

    /**
     * Geef de waarde van selectie periode in maanden van BrpDienstSelectieInhoud.
     * @return de waarde van selectie periode in maanden van BrpDienstSelectieInhoud
     */
    public Short getSelectiePeriodeInMaanden() {
        return selectiePeriodeInMaanden;
    }

    /**
     * Geef de waarde van indicatie verzenden volledig berichten bij plaatsen van BrpDienstSelectieInhoud.
     * @return de waarde van indicatie verzenden volledig berichten bij plaatsen van BrpDienstSelectieInhoud
     */
    public Boolean getIndVerzendenVolledigBerichtBijPlaatsen() {
        return indVerzendenVolledigBerichtBijPlaatsen;
    }

    /**
     * Geef de waarde van leverwijze selectie van BrpDienstSelectieInhoud.
     * @return de waarde van leverwijze selectie in maanden van BrpDienstSelectieInhoud
     */
    public LeverwijzeSelectie getLeverwijzeSelectie() {
        return leverwijzeSelectie;
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof BrpDienstSelectieInhoud)) {
            return false;
        }
        final BrpDienstSelectieInhoud castOther = (BrpDienstSelectieInhoud) other;
        return new EqualsBuilder().append(soortSelectie, castOther.soortSelectie).append(eersteSelectieDatum, castOther.eersteSelectieDatum)
                .append(selectiePeriodeInMaanden, castOther.selectiePeriodeInMaanden)
                .append(indVerzendenVolledigBerichtBijPlaatsen, castOther.indVerzendenVolledigBerichtBijPlaatsen)
                .append(leverwijzeSelectie, castOther.leverwijzeSelectie)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(soortSelectie).append(eersteSelectieDatum).append(selectiePeriodeInMaanden).append(leverwijzeSelectie).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("soortSelectie", soortSelectie)
                .append("eersteSelectieDatum", eersteSelectieDatum)
                .append("selectiePeriodeInMaanden", selectiePeriodeInMaanden)
                .append("indVerzendenVolledigBerichtBijPlaatsen", indVerzendenVolledigBerichtBijPlaatsen)
                .append("leverwijzeSelectie", leverwijzeSelectie)
                .toString();
    }

    /*
     * (non-Javadoc)
     *
     * @see nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGroepInhoud#isLeeg()
     */
    @Override
    public boolean isLeeg() {
        return soortSelectie == null && selectiePeriodeInMaanden == null;
    }
}
