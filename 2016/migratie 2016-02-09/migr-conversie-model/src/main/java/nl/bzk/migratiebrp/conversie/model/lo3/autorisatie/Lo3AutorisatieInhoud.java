/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.lo3.autorisatie;

import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3CategorieInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3IndicatieGeheimCode;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.simpleframework.xml.Element;

/**
 * De autorisatie tabel 35 van GBA.
 */
public class Lo3AutorisatieInhoud implements Lo3CategorieInhoud {

    /*
     * 95.10 Afnemersindicatie
     */
    @Element(name = "afnemersindicatie", required = false)
    private Integer afnemersindicatie;

    /*
     * 95.20 Afnemernaam
     */
    @Element(name = "afnemernaam", required = false)
    private String afnemernaam;

    /*
     * 95.12 Indicatie geheimhouding
     */
    @Element(name = "indicatieGeheimhouding", required = false)
    private Lo3IndicatieGeheimCode indicatieGeheimhouding;

    /*
     * 95.13 Verstrekkingsbeperking
     */
    @Element(name = "verstrekkingsbeperking", required = false)
    private Integer verstrekkingsbeperking;

    /*
     * 95.40 Rubrieknummer spontaan
     */
    @Element(name = "rubrieknummerSpontaan", required = false)
    private String rubrieknummerSpontaan;

    /*
     * 95.41 Voorwaarderegel spontaan
     */
    @Element(name = "voorwaarderegelSpontaan", required = false)
    private String voorwaarderegelSpontaan;

    /*
     * 95.42 Sleutelrubriek
     */
    @Element(name = "sleutelrubriek", required = false)
    private String sleutelrubriek;

    /*
     * 95.43 Conditionele verstrekking
     */
    @Element(name = "conditioneleVerstrekking", required = false)
    private Integer conditioneleVerstrekking;

    /*
     * 95.44 Medium spontaan
     */
    @Element(name = "mediumSpontaan", required = false)
    private String mediumSpontaan;

    /*
     * 95.50 Rubrieknummer selectie
     */
    @Element(name = "rubrieknummerSelectie", required = false)
    private String rubrieknummerSelectie;

    /*
     * 95.51 Voorwaarderegel selectie
     */
    @Element(name = "voorwaarderegelSelectie", required = false)
    private String voorwaarderegelSelectie;

    /*
     * 95.52 Selectiesoort
     */
    @Element(name = "selectiesoort", required = false)
    private Integer selectiesoort;

    /*
     * 95.53 Berichtaanduiding
     */
    @Element(name = "berichtaanduiding", required = false)
    private Integer berichtaanduiding;

    /*
     * 95.54 Eerste selectiedatum
     */
    @Element(name = "eersteSelectiedatum", required = false)
    private Lo3Datum eersteSelectiedatum;

    /*
     * 95.55 Selectieperiode
     */
    @Element(name = "selectieperiode", required = false)
    private Integer selectieperiode;

    /*
     * 95.56 Medium selectie
     */
    @Element(name = "mediumSelectie", required = false)
    private String mediumSelectie;

    /*
     * 95.60 Rubrieknummer ad hoc
     */
    @Element(name = "rubrieknummerAdHoc", required = false)
    private String rubrieknummerAdHoc;

    /*
     * 95.61 Voorwaarderegel ad hoc
     */
    @Element(name = "voorwaarderegelAdHoc", required = false)
    private String voorwaarderegelAdHoc;

    /*
     * 95.62 Plaatsingsbevoegdheid persoonslijst
     */
    @Element(name = "plaatsingsbevoegdheidPersoonslijst", required = false)
    private Integer plaatsingsbevoegdheidPersoonslijst;

    /*
     * 95.63 Afnemersverstrekking
     */
    @Element(name = "afnemersverstrekking", required = false)
    private String afnemersverstrekking;

    /*
     * 95.66 Adresvraag bevoegdheid
     */
    @Element(name = "adresvraagBevoegdheid", required = false)
    private Integer adresvraagBevoegdheid;

    /*
     * 95.67 Medium ad hoc
     */
    @Element(name = "mediumAdHoc", required = false)
    private String mediumAdHoc;

    /*
     * 95.70 Rubrieknummer adresgeoriënteerd
     */
    @Element(name = "rubrieknummerAdresgeorienteerd", required = false)
    private String rubrieknummerAdresgeorienteerd;

    /*
     * 95.71 Voorwaarderegel adresgeoriënteerd
     */
    @Element(name = "voorwaarderegelAdresgeorienteerd", required = false)
    private String voorwaarderegelAdresgeorienteerd;

    /*
     * 95.73 Medium adresgeoriënteerd
     */
    @Element(name = "mediumAdresgeorienteerd", required = false)
    private String mediumAdresgeorienteerd;

    /*
     * 99.98 Datum ingang
     */
    @Element(name = "datumIngang", required = false)
    private Lo3Datum datumIngang;

    /*
     * 99.99 Datum ingang
     */
    @Element(name = "datumEinde", required = false)
    private Lo3Datum datumEinde;

    /**
     * Geef de waarde van afnemersindicatie.
     *
     * @return the afnemersindicatie
     */
    public final Integer getAfnemersindicatie() {
        return afnemersindicatie;
    }

    /**
     * Zet de waarde van afnemersindicatie.
     *
     * @param afnemersindicatie
     *            the afnemersindicatie to set
     */
    public final void setAfnemersindicatie(final Integer afnemersindicatie) {
        this.afnemersindicatie = afnemersindicatie;
    }

    /**
     * Geef de waarde van afnemernaam.
     *
     * @return the afnemernaam
     */
    public final String getAfnemernaam() {
        return afnemernaam;
    }

    /**
     * Zet de waarde van afnemernaam.
     *
     * @param afnemernaam
     *            the afnemernaam to set
     */
    public final void setAfnemernaam(final String afnemernaam) {
        this.afnemernaam = afnemernaam;
    }

    /**
     * Geef de waarde van indicatie geheimhouding.
     *
     * @return the indicatieGeheimhouding
     */
    public final Lo3IndicatieGeheimCode getIndicatieGeheimhouding() {
        return indicatieGeheimhouding;
    }

    /**
     * Zet de waarde van indicatie geheimhouding.
     *
     * @param indicatieGeheimhouding
     *            the indicatieGeheimhouding to set
     */
    public final void setIndicatieGeheimhouding(final Lo3IndicatieGeheimCode indicatieGeheimhouding) {
        this.indicatieGeheimhouding = indicatieGeheimhouding;
    }

    /**
     * Geef de waarde van verstrekkingsbeperking.
     *
     * @return the verstrekkingsbeperking
     */
    public final Integer getVerstrekkingsbeperking() {
        return verstrekkingsbeperking;
    }

    /**
     * Zet de waarde van verstrekkingsbeperking.
     *
     * @param verstrekkingsbeperking
     *            the verstrekkingsbeperking to set
     */
    public final void setVerstrekkingsbeperking(final Integer verstrekkingsbeperking) {
        this.verstrekkingsbeperking = verstrekkingsbeperking;
    }

    /**
     * Geef de waarde van rubrieknummer spontaan.
     *
     * @return the rubrieknummerSpontaan
     */
    public final String getRubrieknummerSpontaan() {
        return rubrieknummerSpontaan;
    }

    /**
     * Zet de waarde van rubrieknummer spontaan.
     *
     * @param rubrieknummerSpontaan
     *            the rubrieknummerSpontaan to set
     */
    public final void setRubrieknummerSpontaan(final String rubrieknummerSpontaan) {
        this.rubrieknummerSpontaan = rubrieknummerSpontaan;
    }

    /**
     * Geef de waarde van voorwaarderegel spontaan.
     *
     * @return the voorwaarderegelSpontaan
     */
    public final String getVoorwaarderegelSpontaan() {
        return voorwaarderegelSpontaan;
    }

    /**
     * Zet de waarde van voorwaarderegel spontaan.
     *
     * @param voorwaarderegelSpontaan
     *            the voorwaarderegelSpontaan to set
     */
    public final void setVoorwaarderegelSpontaan(final String voorwaarderegelSpontaan) {
        this.voorwaarderegelSpontaan = voorwaarderegelSpontaan;
    }

    /**
     * Geef de waarde van sleutelrubriek.
     *
     * @return the sleutelrubriek
     */
    public final String getSleutelrubriek() {
        return sleutelrubriek;
    }

    /**
     * Zet de waarde van sleutelrubriek.
     *
     * @param sleutelrubriek
     *            the sleutelrubriek to set
     */
    public final void setSleutelrubriek(final String sleutelrubriek) {
        this.sleutelrubriek = sleutelrubriek;
    }

    /**
     * Geef de waarde van conditionele verstrekking.
     *
     * @return the conditioneleVerstrekking
     */
    public final Integer getConditioneleVerstrekking() {
        return conditioneleVerstrekking;
    }

    /**
     * Zet de waarde van conditionele verstrekking.
     *
     * @param conditioneleVerstrekking
     *            the conditioneleVerstrekking to set
     */
    public final void setConditioneleVerstrekking(final Integer conditioneleVerstrekking) {
        this.conditioneleVerstrekking = conditioneleVerstrekking;
    }

    /**
     * Geef de waarde van medium spontaan.
     *
     * @return the mediumSpontaan
     */
    public final String getMediumSpontaan() {
        return mediumSpontaan;
    }

    /**
     * Zet de waarde van medium spontaan.
     *
     * @param mediumSpontaan
     *            the mediumSpontaan to set
     */
    public final void setMediumSpontaan(final String mediumSpontaan) {
        this.mediumSpontaan = mediumSpontaan;
    }

    /**
     * Geef de waarde van rubrieknummer selectie.
     *
     * @return the rubrieknummerSelectie
     */
    public final String getRubrieknummerSelectie() {
        return rubrieknummerSelectie;
    }

    /**
     * Zet de waarde van rubrieknummer selectie.
     *
     * @param rubrieknummerSelectie
     *            the rubrieknummerSelectie to set
     */
    public final void setRubrieknummerSelectie(final String rubrieknummerSelectie) {
        this.rubrieknummerSelectie = rubrieknummerSelectie;
    }

    /**
     * Geef de waarde van voorwaarderegel selectie.
     *
     * @return the voorwaarderegelSelectie
     */
    public final String getVoorwaarderegelSelectie() {
        return voorwaarderegelSelectie;
    }

    /**
     * Zet de waarde van voorwaarderegel selectie.
     *
     * @param voorwaarderegelSelectie
     *            the voorwaarderegelSelectie to set
     */
    public final void setVoorwaarderegelSelectie(final String voorwaarderegelSelectie) {
        this.voorwaarderegelSelectie = voorwaarderegelSelectie;
    }

    /**
     * Geef de waarde van selectiesoort.
     *
     * @return the selectiesoort
     */
    public final Integer getSelectiesoort() {
        return selectiesoort;
    }

    /**
     * Zet de waarde van selectiesoort.
     *
     * @param selectiesoort
     *            the selectiesoort to set
     */
    public final void setSelectiesoort(final Integer selectiesoort) {
        this.selectiesoort = selectiesoort;
    }

    /**
     * Geef de waarde van berichtaanduiding.
     *
     * @return the berichtaanduiding
     */
    public final Integer getBerichtaanduiding() {
        return berichtaanduiding;
    }

    /**
     * Zet de waarde van berichtaanduiding.
     *
     * @param berichtaanduiding
     *            the berichtaanduiding to set
     */
    public final void setBerichtaanduiding(final Integer berichtaanduiding) {
        this.berichtaanduiding = berichtaanduiding;
    }

    /**
     * Geef de waarde van eerste selectiedatum.
     *
     * @return the eersteSelectiedatum
     */
    public final Lo3Datum getEersteSelectiedatum() {
        return eersteSelectiedatum;
    }

    /**
     * Zet de waarde van eerste selectiedatum.
     *
     * @param eersteSelectiedatum
     *            the eersteSelectiedatum to set
     */
    public final void setEersteSelectiedatum(final Lo3Datum eersteSelectiedatum) {
        this.eersteSelectiedatum = eersteSelectiedatum;
    }

    /**
     * Geef de waarde van selectieperiode.
     *
     * @return the selectieperiode
     */
    public final Integer getSelectieperiode() {
        return selectieperiode;
    }

    /**
     * Zet de waarde van selectieperiode.
     *
     * @param selectieperiode
     *            the selectieperiode to set
     */
    public final void setSelectieperiode(final Integer selectieperiode) {
        this.selectieperiode = selectieperiode;
    }

    /**
     * Geef de waarde van medium selectie.
     *
     * @return the mediumSelectie
     */
    public final String getMediumSelectie() {
        return mediumSelectie;
    }

    /**
     * Zet de waarde van medium selectie.
     *
     * @param mediumSelectie
     *            the mediumSelectie to set
     */
    public final void setMediumSelectie(final String mediumSelectie) {
        this.mediumSelectie = mediumSelectie;
    }

    /**
     * Geef de waarde van rubrieknummer ad hoc.
     *
     * @return the rubrieknummerAdHoc
     */
    public final String getRubrieknummerAdHoc() {
        return rubrieknummerAdHoc;
    }

    /**
     * Zet de waarde van rubrieknummer ad hoc.
     *
     * @param rubrieknummerAdHoc
     *            the rubrieknummerAdHoc to set
     */
    public final void setRubrieknummerAdHoc(final String rubrieknummerAdHoc) {
        this.rubrieknummerAdHoc = rubrieknummerAdHoc;
    }

    /**
     * Geef de waarde van voorwaarderegel ad hoc.
     *
     * @return the voorwaarderegelAdHoc
     */
    public final String getVoorwaarderegelAdHoc() {
        return voorwaarderegelAdHoc;
    }

    /**
     * Zet de waarde van voorwaarderegel ad hoc.
     *
     * @param voorwaarderegelAdHoc
     *            the voorwaarderegelAdHoc to set
     */
    public final void setVoorwaarderegelAdHoc(final String voorwaarderegelAdHoc) {
        this.voorwaarderegelAdHoc = voorwaarderegelAdHoc;
    }

    /**
     * Geef de waarde van plaatsingsbevoegdheid persoonslijst.
     *
     * @return the plaatsingsbevoegdheidPersoonslijst
     */
    public final Integer getPlaatsingsbevoegdheidPersoonslijst() {
        return plaatsingsbevoegdheidPersoonslijst;
    }

    /**
     * Zet de waarde van plaatsingsbevoegdheid persoonslijst.
     *
     * @param plaatsingsbevoegdheidPersoonslijst
     *            the plaatsingsbevoegdheidPersoonslijst to set
     */
    public final void setPlaatsingsbevoegdheidPersoonslijst(final Integer plaatsingsbevoegdheidPersoonslijst) {
        this.plaatsingsbevoegdheidPersoonslijst = plaatsingsbevoegdheidPersoonslijst;
    }

    /**
     * Geef de waarde van afnemersverstrekking.
     *
     * @return the afnemersverstrekking
     */
    public final String getAfnemersverstrekking() {
        return afnemersverstrekking;
    }

    /**
     * Zet de waarde van afnemersverstrekking.
     *
     * @param afnemersverstrekking
     *            the afnemersverstrekking to set
     */
    public final void setAfnemersverstrekking(final String afnemersverstrekking) {
        this.afnemersverstrekking = afnemersverstrekking;
    }

    /**
     * Geef de waarde van adresvraag bevoegdheid.
     *
     * @return the adresvraagBevoegdheid
     */
    public final Integer getAdresvraagBevoegdheid() {
        return adresvraagBevoegdheid;
    }

    /**
     * Zet de waarde van adresvraag bevoegdheid.
     *
     * @param adresvraagBevoegdheid
     *            the adresvraagBevoegdheid to set
     */
    public final void setAdresvraagBevoegdheid(final Integer adresvraagBevoegdheid) {
        this.adresvraagBevoegdheid = adresvraagBevoegdheid;
    }

    /**
     * Geef de waarde van medium ad hoc.
     *
     * @return the mediumAdHoc
     */
    public final String getMediumAdHoc() {
        return mediumAdHoc;
    }

    /**
     * Zet de waarde van medium ad hoc.
     *
     * @param mediumAdHoc
     *            the mediumAdHoc to set
     */
    public final void setMediumAdHoc(final String mediumAdHoc) {
        this.mediumAdHoc = mediumAdHoc;
    }

    /**
     * Geef de waarde van rubrieknummer adresgeorienteerd.
     *
     * @return the rubrieknummerAdresgeorienteerd
     */
    public final String getRubrieknummerAdresgeorienteerd() {
        return rubrieknummerAdresgeorienteerd;
    }

    /**
     * Zet de waarde van rubrieknummer adresgeorienteerd.
     *
     * @param rubrieknummerAdresgeorienteerd
     *            the rubrieknummerAdresgeorienteerd to set
     */
    public final void setRubrieknummerAdresgeorienteerd(final String rubrieknummerAdresgeorienteerd) {
        this.rubrieknummerAdresgeorienteerd = rubrieknummerAdresgeorienteerd;
    }

    /**
     * Geef de waarde van voorwaarderegel adresgeorienteerd.
     *
     * @return the voorwaarderegelAdresgeorienteerd
     */
    public final String getVoorwaarderegelAdresgeorienteerd() {
        return voorwaarderegelAdresgeorienteerd;
    }

    /**
     * Zet de waarde van voorwaarderegel adresgeorienteerd.
     *
     * @param voorwaarderegelAdresgeorienteerd
     *            the voorwaarderegelAdresgeorienteerd to set
     */
    public final void setVoorwaarderegelAdresgeorienteerd(final String voorwaarderegelAdresgeorienteerd) {
        this.voorwaarderegelAdresgeorienteerd = voorwaarderegelAdresgeorienteerd;
    }

    /**
     * Geef de waarde van medium adresgeorienteerd.
     *
     * @return the mediumAdresgeorienteerd
     */
    public final String getMediumAdresgeorienteerd() {
        return mediumAdresgeorienteerd;
    }

    /**
     * Zet de waarde van medium adresgeorienteerd.
     *
     * @param mediumAdresgeorienteerd
     *            the mediumAdresgeorienteerd to set
     */
    public final void setMediumAdresgeorienteerd(final String mediumAdresgeorienteerd) {
        this.mediumAdresgeorienteerd = mediumAdresgeorienteerd;
    }

    /**
     * Geef de waarde van datum ingang.
     *
     * @return the datumIngang
     */
    public final Lo3Datum getDatumIngang() {
        return datumIngang;
    }

    /**
     * Zet de waarde van datum ingang.
     *
     * @param datumIngang
     *            the datumIngang to set
     */
    public final void setDatumIngang(final Lo3Datum datumIngang) {
        this.datumIngang = datumIngang;
    }

    /**
     * Geef de waarde van datum einde.
     *
     * @return the datumEinde
     */
    public final Lo3Datum getDatumEinde() {
        return datumEinde;
    }

    /**
     * Zet de waarde van datum einde.
     *
     * @param datumEinde
     *            the datumEinde to set
     */
    public final void setDatumEinde(final Lo3Datum datumEinde) {
        this.datumEinde = datumEinde;
    }

    @Override
    public final boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Lo3AutorisatieInhoud)) {
            return false;
        }
        final Lo3AutorisatieInhoud castOther = (Lo3AutorisatieInhoud) other;
        return new EqualsBuilder().append(getAdresvraagBevoegdheid(), castOther.getAdresvraagBevoegdheid())
                                  .append(getAfnemernaam(), castOther.getAfnemernaam())
                                  .append(getAfnemersindicatie(), castOther.getAfnemersindicatie())
                                  .append(getAfnemersverstrekking(), castOther.getAfnemersverstrekking())
                                  .append(getBerichtaanduiding(), castOther.getBerichtaanduiding())
                                  .append(getConditioneleVerstrekking(), castOther.getConditioneleVerstrekking())
                                  .append(getEersteSelectiedatum(), castOther.getEersteSelectiedatum())
                                  .append(getIndicatieGeheimhouding(), castOther.getIndicatieGeheimhouding())
                                  .append(getMediumAdHoc(), castOther.getMediumAdHoc())
                                  .append(getMediumAdresgeorienteerd(), castOther.getMediumAdresgeorienteerd())
                                  .append(getMediumSelectie(), castOther.getMediumSelectie())
                                  .append(getMediumSpontaan(), castOther.getMediumSpontaan())
                                  .append(getPlaatsingsbevoegdheidPersoonslijst(), castOther.getPlaatsingsbevoegdheidPersoonslijst())
                                  .append(getRubrieknummerAdHoc(), castOther.getRubrieknummerAdHoc())
                                  .append(getRubrieknummerAdresgeorienteerd(), castOther.getRubrieknummerAdresgeorienteerd())
                                  .append(getRubrieknummerSelectie(), castOther.getRubrieknummerSelectie())
                                  .append(getRubrieknummerSpontaan(), castOther.getRubrieknummerSpontaan())
                                  .append(getSelectieperiode(), castOther.getSelectieperiode())
                                  .append(getSelectiesoort(), castOther.getSelectiesoort())
                                  .append(getSleutelrubriek(), castOther.getSleutelrubriek())
                                  .append(getVerstrekkingsbeperking(), castOther.getVerstrekkingsbeperking())
                                  .append(getVoorwaarderegelAdHoc(), castOther.getVoorwaarderegelAdHoc())
                                  .append(getVoorwaarderegelAdresgeorienteerd(), castOther.getVoorwaarderegelAdresgeorienteerd())
                                  .append(getVoorwaarderegelSelectie(), castOther.getVoorwaarderegelSelectie())
                                  .append(getVoorwaarderegelSpontaan(), castOther.getVoorwaarderegelSpontaan())
                                  .append(getDatumIngang(), castOther.getDatumIngang())
                                  .append(getDatumEinde(), castOther.getDatumEinde())
                                  .isEquals();

    }

    @Override
    public final int hashCode() {
        return new HashCodeBuilder().append(getAdresvraagBevoegdheid())
                                    .append(getAfnemernaam())
                                    .append(getAfnemersindicatie())
                                    .append(getAfnemersverstrekking())
                                    .append(getBerichtaanduiding())
                                    .append(getConditioneleVerstrekking())
                                    .append(getEersteSelectiedatum())
                                    .append(getIndicatieGeheimhouding())
                                    .append(getMediumAdHoc())
                                    .append(getMediumAdresgeorienteerd())
                                    .append(getMediumSelectie())
                                    .append(getMediumSpontaan())
                                    .append(getPlaatsingsbevoegdheidPersoonslijst())
                                    .append(getRubrieknummerAdHoc())
                                    .append(getRubrieknummerAdresgeorienteerd())
                                    .append(getRubrieknummerSelectie())
                                    .append(getRubrieknummerSpontaan())
                                    .append(getSelectieperiode())
                                    .append(getSelectiesoort())
                                    .append(getSleutelrubriek())
                                    .append(getVerstrekkingsbeperking())
                                    .append(getVoorwaarderegelAdHoc())
                                    .append(getVoorwaarderegelAdresgeorienteerd())
                                    .append(getVoorwaarderegelSelectie())
                                    .append(getVoorwaarderegelSpontaan())
                                    .append(getDatumIngang())
                                    .append(getDatumEinde())
                                    .toHashCode();
    }

    @Override
    public final String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("AdresvraagBevoegdheid", getAdresvraagBevoegdheid())
                                                                          .append("Afnemernaam", getAfnemernaam())
                                                                          .append("Afnemersindicatie", getAfnemersindicatie())
                                                                          .append("Afnemersverstrekking", getAfnemersverstrekking())
                                                                          .append("Berichtaanduiding", getBerichtaanduiding())
                                                                          .append("ConditioneleVerstrekking", getConditioneleVerstrekking())
                                                                          .append("EersteSelectiedatum", getEersteSelectiedatum())
                                                                          .append("IndicatieGeheimhouding", getIndicatieGeheimhouding())
                                                                          .append("MediumAdHoc", getMediumAdHoc())
                                                                          .append("MediumAdresgeorienteerd", getMediumAdresgeorienteerd())
                                                                          .append("MediumSelectie", getMediumSelectie())
                                                                          .append("MediumSpontaan", getMediumSpontaan())
                                                                          .append(
                                                                              "PlaatsingsbevoegdheidPersoonslijst",
                                                                              getPlaatsingsbevoegdheidPersoonslijst())
                                                                          .append("RubrieknummerAdHoc", getRubrieknummerAdHoc())
                                                                          .append("RubrieknummerAdresgeorienteerd", getRubrieknummerAdresgeorienteerd())
                                                                          .append("RubrieknummerSelectie", getRubrieknummerSelectie())
                                                                          .append("RubrieknummerSpontaan", getRubrieknummerSpontaan())
                                                                          .append("Selectieperiode", getSelectieperiode())
                                                                          .append("Selectiesoort", getSelectiesoort())
                                                                          .append("Sleutelrubriek", getSleutelrubriek())
                                                                          .append("Verstrekkingsbeperking", getVerstrekkingsbeperking())
                                                                          .append("VoorwaarderegelAdHoc", getVoorwaarderegelAdHoc())
                                                                          .append(
                                                                              "VoorwaarderegelAdresgeorienteerd",
                                                                              getVoorwaarderegelAdresgeorienteerd())
                                                                          .append("VoorwaarderegelSelectie", getVoorwaarderegelSelectie())
                                                                          .append("VoorwaarderegelSpontaan", getVoorwaarderegelSpontaan())
                                                                          .append("DatumIngang", getDatumIngang())
                                                                          .append("DatumEinde", getDatumEinde())
                                                                          .toString();
    }

    /*
     * (non-Javadoc)
     *
     * @see nl.bzk.migratiebrp.conversie.model.lo3.Lo3CategorieInhoud#isLeeg()
     */
    @Override
    public final boolean isLeeg() {
        return afnemersindicatie == null;
    }

}
