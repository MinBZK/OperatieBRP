/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.brp.autorisatie;

import java.util.List;

import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatum;
import nl.bzk.migratiebrp.conversie.model.brp.groep.autorisatie.BrpToegangLeveringsautorisatieInhoud;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;

/**
 * Deze class representeert het BRP objecttype BrpToegangLeveringsautorisatie.
 *
 * Deze class heeft een relatie met een doelbinding en een dienst. In de conversie van LO3 naar BRP wordt voor elk type
 * levering (adhoc, spontaan, selectie) een apart abonnement gemaakt met daaronder een dienst en een doelbinding.
 *
 * Deze class is immutable en thread-safe.
 */
public final class BrpToegangLeveringsautorisatie {

    @Element(name = "afleverpunt", required = false)
    private final String afleverpunt;
    @Element(name = "datumIngang", required = false)
    private final BrpDatum datumIngang;
    @Element(name = "datumEinde", required = false)
    private final BrpDatum datumEinde;
    @Element(name = "geautoriseerde", required = false)
    private final BrpPartij geautoriseerde;
    @Element(name = "indicatieGeblokkeerd", required = false)
    private final Boolean indicatieGeblokkeerd;
    @Element(name = "naderePopulatiebeperking", required = false)
    private final String naderePopulatiebeperking;
    @Element(name = "ondertekenaar", required = false)
    private final BrpPartij ondertekenaar;
    @Element(name = "transporteur", required = false)
    private final BrpPartij transporteur;
    @ElementList(name = "leveringsautorisaties", entry = "leveringsautorisatie", type = BrpLeveringsautorisatie.class, required = false)
    private final List<BrpLeveringsautorisatie> leveringsautorisaties;
    @Element(name = "toegangLeveringsautorisatieStapel", required = false)
    private final BrpStapel<BrpToegangLeveringsautorisatieInhoud> toegangLeveringsautorisatieStapel;

    /**
     * Maak een nieuw BrpToegangLeveringsAutorisatie object.
     *
     * @param afleverpunt
     *            afleverpunt
     * @param datumIngang
     *            datumIngang
     * @param datumEinde
     *            datumEinde
     * @param geautoriseerde
     *            geautoriseerde
     * @param indicatieGeblokkeerd
     *            indicatieGeblokkeerd
     * @param naderePopulatiebeperking
     *            naderePopulatiebeperking
     * @param ondertekenaar
     *            ondertekenaar
     * @param transporteur
     *            transporteur
     * @param leveringsautorisaties
     *            leveringsautorisaties
     * @param toegangLeveringsautorisatieStapel
     *            de stapel met toegangLeveringsAutorisatieStapel
     */
    public BrpToegangLeveringsautorisatie(
        @Element(name = "afleverpunt", required = false) final String afleverpunt,
        @Element(name = "datumIngang", required = false) final BrpDatum datumIngang,
        @Element(name = "datumEinde", required = false) final BrpDatum datumEinde,
        @Element(name = "geautoriseerde", required = false) final BrpPartij geautoriseerde,
        @Element(name = "indicatieGeblokkeerd", required = false) final Boolean indicatieGeblokkeerd,
        @Element(name = "naderePopulatiebeperking", required = false) final String naderePopulatiebeperking,
        @Element(name = "ondertekenaar", required = false) final BrpPartij ondertekenaar,
        @Element(name = "transporteur", required = false) final BrpPartij transporteur,
        @ElementList(name = "leveringsautorisaties", entry = "leveringsautorisatie", type = BrpLeveringsautorisatie.class, required = false) final List<BrpLeveringsautorisatie> leveringsautorisaties,
        @Element(name = "toegangLeveringsautorisatieStapel", required = false) final BrpStapel<BrpToegangLeveringsautorisatieInhoud> toegangLeveringsautorisatieStapel)
    {
        super();
        this.afleverpunt = afleverpunt;
        this.datumIngang = datumIngang;
        this.datumEinde = datumEinde;
        this.geautoriseerde = geautoriseerde;
        this.indicatieGeblokkeerd = indicatieGeblokkeerd;
        this.naderePopulatiebeperking = naderePopulatiebeperking;
        this.ondertekenaar = ondertekenaar;
        this.transporteur = transporteur;
        this.leveringsautorisaties = leveringsautorisaties;
        this.toegangLeveringsautorisatieStapel = toegangLeveringsautorisatieStapel;
    }

    /**
     * Geef de waarde van de toegangleveringautorisatieinhoud stapel.
     *
     * @return toegangleveringautorisatieinhoud stapel
     */
    public BrpStapel<BrpToegangLeveringsautorisatieInhoud> getToegangLeveringsautorisatieStapel() {
        return toegangLeveringsautorisatieStapel;
    }

    /**
     * Geef de waarde van de leveringsautorisaties.
     *
     * @return leveringsautorisaties
     */
    public List<BrpLeveringsautorisatie> getLeveringsautorisaties() {
        return leveringsautorisaties;
    }

    /**
     * Geef de waarde van het afleverpunt.
     *
     * @return afleverpunt
     */
    public String getAfleverpunt() {
        return afleverpunt;
    }

    /**
     * Geef de waarde van de datumIngang.
     *
     * @return datumIngang
     */
    public BrpDatum getDatumIngang() {
        return datumIngang;
    }

    /**
     * Geef de waarde van de datumEinde.
     *
     * @return datumEinde
     */
    public BrpDatum getDatumEinde() {
        return datumEinde;
    }

    /**
     * Geef de waarde van de indicatieGeblokkeerd.
     *
     * @return indicatieGeblokkeerd
     */
    public BrpPartij getGeautoriseerde() {
        return geautoriseerde;
    }

    /**
     * Geef de waarde van de toegangleveringautorisatieinhoud stapel.
     *
     * @return toegangleveringautorisatieinhoud stapel
     */
    public Boolean getIndicatieGeblokkeerd() {
        return indicatieGeblokkeerd;
    }

    /**
     * Geef de waarde van de naderePopulatiebeperking.
     *
     * @return naderePopulatiebeperking
     */
    public String getNaderePopulatiebeperking() {
        return naderePopulatiebeperking;
    }

    /**
     * Geef de waarde van de ondertekenaar.
     *
     * @return ondertekenaar
     */
    public BrpPartij getOndertekenaar() {
        return ondertekenaar;
    }

    /**
     * Geef de waarde van de transporteur.
     *
     * @return transporteur
     */
    public BrpPartij getTransporteur() {
        return transporteur;
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof BrpToegangLeveringsautorisatie)) {
            return false;
        }
        final BrpToegangLeveringsautorisatie castOther = (BrpToegangLeveringsautorisatie) other;
        return new EqualsBuilder().append(afleverpunt, castOther.afleverpunt)
                                  .append(datumIngang, castOther.datumIngang)
                                  .append(datumEinde, castOther.datumEinde)
                                  .append(geautoriseerde, castOther.geautoriseerde)
                                  .append(indicatieGeblokkeerd, castOther.indicatieGeblokkeerd)
                                  .append(naderePopulatiebeperking, castOther.naderePopulatiebeperking)
                                  .append(ondertekenaar, castOther.ondertekenaar)
                                  .append(transporteur, castOther.transporteur)
                                  .append(leveringsautorisaties, castOther.leveringsautorisaties)
                                  .append(toegangLeveringsautorisatieStapel, castOther.toegangLeveringsautorisatieStapel)
                                  .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(afleverpunt)
                .append(datumIngang)
                .append(datumEinde)
                .append(geautoriseerde)
                .append(indicatieGeblokkeerd)
                .append(naderePopulatiebeperking)
                .append(ondertekenaar)
                .append(transporteur)
                .append(leveringsautorisaties)
                .append(toegangLeveringsautorisatieStapel)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("afleverpunt", afleverpunt)
                .append("datumIngang", datumIngang)
                .append("datumEinde", datumEinde)
                .append("geautoriseerde", geautoriseerde)
                .append("indicatieGeblokkeerd", indicatieGeblokkeerd)
                .append("naderePopulatiebeperking", naderePopulatiebeperking)
                .append("ondertekenaar", ondertekenaar)
                .append("transporteur", transporteur)
                .append("leveringsautorisaties", leveringsautorisaties)
                .append("toegangLeveringsautorisatieStapel", toegangLeveringsautorisatieStapel)
                .toString();
    }
}
