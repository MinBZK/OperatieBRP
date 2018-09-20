/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.tussen.autorisatie;

import java.util.List;

import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatum;
import nl.bzk.migratiebrp.conversie.model.brp.groep.autorisatie.BrpToegangLeveringsautorisatieInhoud;
import nl.bzk.migratiebrp.conversie.model.tussen.TussenStapel;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;

/**
 * Migratie representatie voor een toegangleveringsautorisatie (BRP inhoud, LO3 historie).
 *
 * Deze class is immutable en thread-safe.
 */
public final class TussenToegangLeveringsautorisatie {

    @Element(name = "afleverpunt", required = false)
    private final String afleverpunt;
    @Element(name = "datumIngang", required = false)
    private final BrpDatum datumIngang;
    @Element(name = "datumEinde", required = false)
    private final BrpDatum datumEinde;
    @Element(name = "geautoriseerde", required = false)
    private final TussenPartij geautoriseerde;
    @Element(name = "indicatieGeblokkeerd", required = false)
    private final Boolean indicatieGeblokkeerd;
    @Element(name = "naderePopulatiebeperking", required = false)
    private final String naderePopulatiebeperking;
    @Element(name = "ondertekenaar", required = false)
    private final TussenPartij ondertekenaar;
    @Element(name = "transporteur", required = false)
    private final TussenPartij transporteur;
    @ElementList(name = "leveringsautorisaties", entry = "leveringsautorisatie", type = TussenLeveringsautorisatie.class, required = false)
    private final List<TussenLeveringsautorisatie> leveringsautorisaties;
    @Element(name = "toegangLeveringsautorisatieStapel", required = false)
    private final TussenStapel<BrpToegangLeveringsautorisatieInhoud> toegangLeveringsautorisatieStapel;

    /**
     * Maak een nieuw TussenToegangLeveringsAutorisatie object.
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
    public TussenToegangLeveringsautorisatie(
        @Element(name = "afleverpunt", required = false) final String afleverpunt,
        @Element(name = "datumIngang", required = false) final BrpDatum datumIngang,
        @Element(name = "datumEinde", required = false) final BrpDatum datumEinde,
        @Element(name = "geautoriseerde", required = false) final TussenPartij geautoriseerde,
        @Element(name = "indicatieGeblokkeerd", required = false) final Boolean indicatieGeblokkeerd,
        @Element(name = "naderePopulatiebeperking", required = false) final String naderePopulatiebeperking,
        @Element(name = "ondertekenaar", required = false) final TussenPartij ondertekenaar,
        @Element(name = "transporteur", required = false) final TussenPartij transporteur,
        @ElementList(name = "leveringsautorisaties", entry = "leveringsautorisatie", type = TussenLeveringsautorisatie.class, required = false) final List<TussenLeveringsautorisatie> leveringsautorisaties,
        @Element(name = "toegangLeveringsAutorisatieStapel", required = false) final TussenStapel<BrpToegangLeveringsautorisatieInhoud> toegangLeveringsautorisatieStapel)
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
    public TussenStapel<BrpToegangLeveringsautorisatieInhoud> getToegangLeveringsautorisatieStapel() {
        return toegangLeveringsautorisatieStapel;
    }

    /**
     * Geef de waarde van de leveringsautorisaties.
     *
     * @return leveringsautorisaties
     */
    public List<TussenLeveringsautorisatie> getLeveringsautorisaties() {
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
    public TussenPartij getGeautoriseerde() {
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
    public TussenPartij getOndertekenaar() {
        return ondertekenaar;
    }

    /**
     * Geef de waarde van de transporteur.
     *
     * @return transporteur
     */
    public TussenPartij getTransporteur() {
        return transporteur;
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof TussenToegangLeveringsautorisatie)) {
            return false;
        }
        final TussenToegangLeveringsautorisatie castOther = (TussenToegangLeveringsautorisatie) other;
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
