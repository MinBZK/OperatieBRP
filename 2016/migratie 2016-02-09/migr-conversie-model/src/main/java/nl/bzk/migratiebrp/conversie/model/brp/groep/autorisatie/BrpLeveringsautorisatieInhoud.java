/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.brp.groep.autorisatie;

import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatum;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.autorisatie.BrpProtocolleringsniveauCode;
import nl.bzk.migratiebrp.conversie.model.brp.groep.AbstractBrpGroepInhoud;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.simpleframework.xml.Element;

/**
 * Deze class representeert de inhoud van de groep BRP LeveringsAutorisatie.
 *
 * Deze class is immutable en threadsafe.
 */
public final class BrpLeveringsautorisatieInhoud extends AbstractBrpGroepInhoud {

    @Element(name = "naam", required = false)
    private final String naam;
    @Element(name = "protocolleringsniveau", required = false)
    private final BrpProtocolleringsniveauCode protocolleringsniveau;
    @Element(name = "indicatieAliasSoortAdministratieHandelingLeveren", required = false)
    private final Boolean indicatieAliasSoortAdministratieHandelingLeveren;
    @Element(name = "indicatieGeblokkeerd", required = false)
    private final Boolean indicatieGeblokkeerd;
    @Element(name = "indicatiePopulatiebeperkingVolledigGeconverteerd", required = false)
    private final Boolean indicatiePopulatiebeperkingVolledigGeconverteerd;
    @Element(name = "populatiebeperking", required = false)
    private final String populatiebeperking;
    @Element(name = "datumIngang", required = false)
    private final BrpDatum datumIngang;
    @Element(name = "datumEinde", required = false)
    private final BrpDatum datumEinde;
    @Element(name = "toelichting", required = false)
    private final String toelichting;

    /**
     * Maak een nieuw BrpLeveringsAutorisatieInhoud object.
     *
     * @param naam
     *            naam
     * @param protocolleringsniveau
     *            protocolleringsniveau
     * @param indicatieAliasSoortAdministratieHandelingLeveren
     *            indicatieAliasSoortAdministratieHandelingLeveren
     * @param indicatieGeblokkeerd
     *            indicatieGeblokkeerd
     * @param indicatiePopulatiebeperkingVolledigGeconverteerd
     *            indicatiePopulatiebeperkingVolledigGeconverteerd
     * @param populatiebeperking
     *            populatiebeperking
     * @param datumIngang
     *            datumIngang
     * @param datumEinde
     *            datumEinde
     * @param toelichting
     *            toelichting
     */
    public BrpLeveringsautorisatieInhoud(
        @Element(name = "naam", required = false) final String naam,
        @Element(name = "protocolleringsniveau", required = false) final BrpProtocolleringsniveauCode protocolleringsniveau,
        @Element(name = "indicatieAliasSoortAdministratieHandelingLeveren", required = false) final Boolean indicatieAliasSoortAdministratieHandelingLeveren,
        @Element(name = "indicatieGeblokkeerd", required = false) final Boolean indicatieGeblokkeerd,
        @Element(name = "indicatiePopulatiebeperkingVolledigGeconverteerd", required = false) final Boolean indicatiePopulatiebeperkingVolledigGeconverteerd,
        @Element(name = "populatiebeperking", required = false) final String populatiebeperking,
        @Element(name = "datumIngang", required = false) final BrpDatum datumIngang,
        @Element(name = "datumEinde", required = false) final BrpDatum datumEinde,
        @Element(name = "toelichting", required = false) final String toelichting)
    {
        super();
        this.naam = naam;
        this.protocolleringsniveau = protocolleringsniveau;
        this.indicatieAliasSoortAdministratieHandelingLeveren = indicatieAliasSoortAdministratieHandelingLeveren;
        this.indicatieGeblokkeerd = indicatieGeblokkeerd;
        this.indicatiePopulatiebeperkingVolledigGeconverteerd = indicatiePopulatiebeperkingVolledigGeconverteerd;
        this.populatiebeperking = populatiebeperking;
        this.datumIngang = datumIngang;
        this.datumEinde = datumEinde;
        this.toelichting = toelichting;
    }

    /**
     * Geef de leeg.
     *
     * @return false
     */
    @Override
    public boolean isLeeg() {
        return datumIngang == null;
    }

    /**
     * Geef de waarde van naam.
     *
     * @return naam.
     */
    public String getNaam() {
        return naam;
    }

    /**
     * Geef de waarde van protocolleringsniveau.
     *
     * @return protocolleringsniveau.
     */
    public BrpProtocolleringsniveauCode getProtocolleringsniveau() {
        return protocolleringsniveau;
    }

    /**
     * Geef de waarde van indicatieAliasSoortAdministratieHandelingLeveren.
     *
     * @return indicatieAliasSoortAdministratieHandelingLeveren.
     */
    public Boolean getIndicatieAliasSoortAdministratieHandelingLeveren() {
        return indicatieAliasSoortAdministratieHandelingLeveren;
    }

    /**
     * Geef de waarde van indicatieGeblokkeerd.
     *
     * @return indicatieGeblokkeerd.
     */
    public Boolean getIndicatieGeblokkeerd() {
        return indicatieGeblokkeerd;
    }

    /**
     * Geef de waarde van indicatiePopulatiebeperkingVolledigGeconverteerd.
     *
     * @return indicatiePopulatiebeperkingVolledigGeconverteerd.
     */
    public Boolean getIndicatiePopulatiebeperkingVolledigGeconverteerd() {
        return indicatiePopulatiebeperkingVolledigGeconverteerd;
    }

    /**
     * Geef de waarde van populatiebeperking.
     *
     * @return populatiebeperking.
     */
    public String getPopulatiebeperking() {
        return populatiebeperking;
    }

    /**
     * Geef de waarde van datumIngang.
     *
     * @return datumIngang.
     */
    public BrpDatum getDatumIngang() {
        return datumIngang;
    }

    /**
     * Geef de waarde van datumEinde.
     *
     * @return datumEinde.
     */
    public BrpDatum getDatumEinde() {
        return datumEinde;
    }

    /**
     * Geef de waarde van toelichting.
     *
     * @return toelichting.
     */
    public String getToelichting() {
        return toelichting;
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof BrpLeveringsautorisatieInhoud)) {
            return false;
        }
        final BrpLeveringsautorisatieInhoud castOther = (BrpLeveringsautorisatieInhoud) other;
        return new EqualsBuilder().append(naam, castOther.naam)
                                  .append(protocolleringsniveau, castOther.protocolleringsniveau)
                                  .append(indicatieAliasSoortAdministratieHandelingLeveren, castOther.indicatieAliasSoortAdministratieHandelingLeveren)
                                  .append(indicatieGeblokkeerd, castOther.indicatieGeblokkeerd)
                                  .append(indicatiePopulatiebeperkingVolledigGeconverteerd, castOther.indicatiePopulatiebeperkingVolledigGeconverteerd)
                                  .append(populatiebeperking, castOther.populatiebeperking)
                                  .append(datumIngang, castOther.datumIngang)
                                  .append(datumEinde, castOther.datumEinde)
                                  .append(toelichting, castOther.toelichting)
                                  .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(naam)
                .append(protocolleringsniveau)
                .append(indicatieAliasSoortAdministratieHandelingLeveren)
                .append(indicatieGeblokkeerd)
                .append(indicatiePopulatiebeperkingVolledigGeconverteerd)
                .append(populatiebeperking)
                .append(datumIngang)
                .append(datumEinde)
                .append(toelichting)
                                    .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("naam", naam)
                                                                          .append("protocolleringsniveau", protocolleringsniveau)
                                                                          .append(
                                                                              "indicatieAliasSoortAdministratieHandelingLeveren",
                                                                              indicatieAliasSoortAdministratieHandelingLeveren)
                                                                          .append("indicatieGeblokkeerd", indicatieGeblokkeerd)
                                                                          .append(
                                                                              "indicatiePopulatiebeperkingVolledigGeconverteerd",
                                                                              indicatiePopulatiebeperkingVolledigGeconverteerd)
                                                                          .append("populatiebeperking", populatiebeperking)
                                                                          .append("datumIngang", datumIngang)
                                                                          .append("datumEinde", datumEinde)
                                                                          .append("toelichting", toelichting)
                                                                          .toString();
    }
}
