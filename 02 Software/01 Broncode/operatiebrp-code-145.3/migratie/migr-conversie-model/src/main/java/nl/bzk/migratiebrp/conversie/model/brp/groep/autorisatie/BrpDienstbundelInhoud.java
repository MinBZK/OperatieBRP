/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.brp.groep.autorisatie;

import nl.bzk.algemeenbrp.util.xml.annotation.Element;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatum;
import nl.bzk.migratiebrp.conversie.model.brp.groep.AbstractBrpGroepInhoud;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Deze class representeert de inhoud van de groep BRP DienstBundelInhoud.
 *
 * Deze class is immutable en threadsafe.
 */
public final class BrpDienstbundelInhoud extends AbstractBrpGroepInhoud {

    @Element(name = "naam", required = false)
    private final String naam;
    @Element(name = "datumIngang", required = false)
    private final BrpDatum datumIngang;
    @Element(name = "datumEinde", required = false)
    private final BrpDatum datumEinde;
    @Element(name = "naderePopulatiebeperking", required = false)
    private final String naderePopulatiebeperking;
    @Element(name = "indicatieNaderePopulatiebeperkingVolledigGeconverteerd", required = false)
    private final Boolean indicatieNaderePopulatiebeperkingVolledigGeconverteerd;
    @Element(name = "toelichting", required = false)
    private final String toelichting;
    @Element(name = "geblokkeerd", required = false)
    private final Boolean geblokkeerd;

    /**
     * Maak een BrpDienstBundelInhoud object.
     * @param naam naam
     * @param datumIngang datum ingang
     * @param datumEinde datum einde
     * @param naderePopulatiebeperking nadere populatie beperking
     * @param indicatieNaderePopulatiebeperkingVolledigGeconverteerd indicatie nadere populatie beperking volledig geconverteerd
     * @param toelichting toelichting
     * @param geblokkeerd geblokkeerd
     */
    public BrpDienstbundelInhoud(
            @Element(name = "naam", required = false) final String naam,
            @Element(name = "datumIngang", required = false) final BrpDatum datumIngang,
            @Element(name = "datumEinde", required = false) final BrpDatum datumEinde,
            @Element(name = "naderePopulatiebeperking", required = false) final String naderePopulatiebeperking,
            @Element(name = "indicatieNaderePopulatiebeperkingVolledigGeconverteerd",
                    required = false) final Boolean indicatieNaderePopulatiebeperkingVolledigGeconverteerd,
            @Element(name = "toelichting", required = false) final String toelichting,
            @Element(name = "geblokkeerd", required = false) final Boolean geblokkeerd) {
        super();
        this.naam = naam;
        this.datumIngang = datumIngang;
        this.datumEinde = datumEinde;
        this.naderePopulatiebeperking = naderePopulatiebeperking;
        this.indicatieNaderePopulatiebeperkingVolledigGeconverteerd = indicatieNaderePopulatiebeperkingVolledigGeconverteerd;
        this.toelichting = toelichting;
        this.geblokkeerd = geblokkeerd;
    }

    /**
     * Geef de waarde van naam van BrpDienstbundelInhoud.
     * @return de waarde van naam van BrpDienstbundelInhoud
     */
    public String getNaam() {
        return naam;
    }

    /**
     * Geef de waarde van datum ingang van BrpDienstbundelInhoud.
     * @return de waarde van datum ingang van BrpDienstbundelInhoud
     */
    public BrpDatum getDatumIngang() {
        return datumIngang;
    }

    /**
     * Geef de waarde van datum einde van BrpDienstbundelInhoud.
     * @return de waarde van datum einde van BrpDienstbundelInhoud
     */
    public BrpDatum getDatumEinde() {
        return datumEinde;
    }

    /**
     * Geef de waarde van nadere populatiebeperking van BrpDienstbundelInhoud.
     * @return de waarde van nadere populatiebeperking van BrpDienstbundelInhoud
     */
    public String getNaderePopulatiebeperking() {
        return naderePopulatiebeperking;
    }

    /**
     * Geef de waarde van geblokkeerd van BrpDienstbundelInhoud.
     * @return de waarde van geblokkeerd van BrpDienstbundelInhoud
     */
    public Boolean getGeblokkeerd() {
        return geblokkeerd;
    }

    /**
     * Geef de waarde van indicatie nadere populatiebeperking volledig geconverteerd van BrpDienstbundelInhoud.
     * @return de waarde van indicatie nadere populatiebeperking volledig geconverteerd van BrpDienstbundelInhoud
     */
    public Boolean getIndicatieNaderePopulatiebeperkingVolledigGeconverteerd() {
        return indicatieNaderePopulatiebeperkingVolledigGeconverteerd;
    }

    /**
     * Geef de waarde van toelichting van BrpDienstbundelInhoud.
     * @return de waarde van toelichting van BrpDienstbundelInhoud
     */
    public String getToelichting() {
        return toelichting;
    }

    /*
     * (non-Javadoc)
     * 
     * @see nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGroepInhoud#isLeeg()
     */
    @Override
    public boolean isLeeg() {
        return datumIngang == null;
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof BrpDienstbundelInhoud)) {
            return false;
        }
        final BrpDienstbundelInhoud castOther = (BrpDienstbundelInhoud) other;
        return new EqualsBuilder().append(naam, castOther.naam)
                .append(datumIngang, castOther.datumIngang)
                .append(datumEinde, castOther.datumEinde)
                .append(naderePopulatiebeperking, castOther.naderePopulatiebeperking)
                .append(
                        indicatieNaderePopulatiebeperkingVolledigGeconverteerd,
                        castOther.indicatieNaderePopulatiebeperkingVolledigGeconverteerd)
                .append(toelichting, castOther.toelichting)
                .append(geblokkeerd, castOther.geblokkeerd)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(naam)
                .append(datumIngang)
                .append(datumEinde)
                .append(naderePopulatiebeperking)
                .append(indicatieNaderePopulatiebeperkingVolledigGeconverteerd)
                .append(toelichting)
                .append(geblokkeerd)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("naam", naam)
                .append("datumIngang", datumIngang)
                .append("datumEinde", datumEinde)
                .append("naderePopulatiebeperking", naderePopulatiebeperking)
                .append(
                        "indnaderepopbeperkingvolconv",
                        indicatieNaderePopulatiebeperkingVolledigGeconverteerd)
                .append("toelichting", toelichting)
                .append("geblokkeerd", geblokkeerd)
                .toString();
    }
}
