/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.service.selectie;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Optional;
import javax.validation.constraints.NotNull;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienst;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Selectietaak;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ToegangLeveringsAutorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.EenheidSelectieInterval;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SelectietaakStatus;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortSelectie;
import nl.bzk.algemeenbrp.util.common.DatumUtil;

/**
 * DTO voor selectietaken.
 */
@JsonAutoDetect
public final class SelectieTaakDTO {

    private Integer id;
    private Integer dienstId;
    private Integer selectieInterval;
    private String eenheidSelectieInterval;
    private String stelsel;
    private String afnemerCode;
    private String afnemerNaam;
    private LocalDate eersteSelectieDatum;
    private String selectieSoort;
    private LocalDate berekendeSelectieDatum;
    private Integer volgnummer;
    private Integer toegangLeveringsautorisatieId;
    private LocalDate peilmomentMaterieelResultaat;
    private ZonedDateTime peilmomentFormeelResultaat;
    private Short status;
    private LocalDate datumPlanning;
    private Boolean opnieuwPlannen;
    private String statusToelichting;
    @JsonIgnore
    private Dienst dienst;
    @JsonIgnore
    private ToegangLeveringsAutorisatie toegangLeveringsautorisatie;

    /**
     * Default constructor.
     */
    public SelectieTaakDTO() {

    }

    /**
     * Constructor met een {@link Selectietaak} om de DTO mee te vullen.
     * @param selectietaak de {@link Selectietaak}
     */
    SelectieTaakDTO(Selectietaak selectietaak) {
        this.id = selectietaak.getId();
        this.dienstId = selectietaak.getDienst().getId();
        this.selectieInterval = selectietaak.getDienst().getSelectieInterval();
        Optional.ofNullable(selectietaak.getDienst().getEenheidSelectieInterval())
                .ifPresent(
                        e -> this.setEenheidSelectieInterval(EenheidSelectieInterval.parseId(selectietaak.getDienst().getEenheidSelectieInterval()).getNaam()));
        this.stelsel = selectietaak.getToegangLeveringsAutorisatie().getLeveringsautorisatie().getStelsel().getNaam();
        this.afnemerCode = selectietaak.getToegangLeveringsAutorisatie().getGeautoriseerde().getPartij().getCode();
        this.afnemerNaam = selectietaak.getToegangLeveringsAutorisatie().getGeautoriseerde().getPartij().getNaam();
        this.eersteSelectieDatum = DatumUtil.vanIntegerNaarLocalDate(selectietaak.getDienst().getEersteSelectieDatum());
        this.selectieSoort = SoortSelectie.parseId(selectietaak.getDienst().getSoortSelectie()).getNaam();
        this.toegangLeveringsautorisatieId = selectietaak.getToegangLeveringsAutorisatie().getId();
        Optional.ofNullable(selectietaak.getPeilmomentMaterieelResultaat())
                .ifPresent(p -> this.setPeilmomentMaterieelResultaat(DatumUtil.vanIntegerNaarLocalDate(p)));
        Optional.ofNullable(selectietaak.getPeilmomentFormeelResultaat())
                .ifPresent(p -> this.setPeilmomentFormeelResultaat(DatumUtil.vanTimestampNaarZonedDateTime(p)));
        this.status = selectietaak.getStatus();
        this.datumPlanning = DatumUtil.vanIntegerNaarLocalDate(selectietaak.getDatumPlanning());
        this.volgnummer = selectietaak.getVolgnummer();
        this.statusToelichting = selectietaak.getStatusToelichting();
        this.dienst = selectietaak.getDienst();
        this.toegangLeveringsautorisatie = selectietaak.getToegangLeveringsAutorisatie();
        if (this.selectieInterval != null) {
            this.berekendeSelectieDatum =
                    eersteSelectieDatum.plus((long) (volgnummer - 1) * selectieInterval, SelectieTaakServiceUtil.EENHEID_SELECTIE_INTERVAL_CHRONO_UNIT_MAP.get(
                            EenheidSelectieInterval.parseId(selectietaak.getDienst().getEenheidSelectieInterval())));
        } else {
            this.berekendeSelectieDatum = eersteSelectieDatum;
        }
    }

    /**
     * Constructor met parameters om de DTO mee te vullen.
     * @param dienst {@link Dienst}
     * @param toegangLeveringsAutorisatie de {@link ToegangLeveringsAutorisatie}
     * @param berekendeSelectieDatum de berekende selectiedatum
     * @param peilMomentMaterieelResultaat het berekende peilmoment materieel resultaat
     * @param peilMomentFormeelResultaat het berekende peilmoment formeel resultaat
     */
    SelectieTaakDTO(Dienst dienst, ToegangLeveringsAutorisatie toegangLeveringsAutorisatie, LocalDate berekendeSelectieDatum,
                    LocalDate peilMomentMaterieelResultaat, ZonedDateTime peilMomentFormeelResultaat, Integer volgnummer) {
        this.dienstId = dienst.getId();
        this.selectieInterval = dienst.getSelectieInterval();
        Optional.ofNullable(dienst.getEenheidSelectieInterval())
                .ifPresent(e -> this.setEenheidSelectieInterval(EenheidSelectieInterval.parseId(dienst.getEenheidSelectieInterval()).getNaam()));
        this.stelsel = toegangLeveringsAutorisatie.getLeveringsautorisatie().getStelsel().getNaam();
        final Partij partij = toegangLeveringsAutorisatie.getGeautoriseerde().getPartij();
        this.afnemerCode = partij.getCode();
        this.afnemerNaam = partij.getNaam();
        this.eersteSelectieDatum = DatumUtil.vanIntegerNaarLocalDate(dienst.getEersteSelectieDatum());
        this.selectieSoort = SoortSelectie.parseId(dienst.getSoortSelectie()).getNaam();
        this.berekendeSelectieDatum = berekendeSelectieDatum;
        this.volgnummer = volgnummer;
        this.toegangLeveringsautorisatieId = toegangLeveringsAutorisatie.getId();
        this.peilmomentMaterieelResultaat = peilMomentMaterieelResultaat;
        this.peilmomentFormeelResultaat = peilMomentFormeelResultaat;
        this.status = (short) SelectietaakStatus.IN_TE_PLANNEN.getId();
        this.dienst = dienst;
        this.toegangLeveringsautorisatie = toegangLeveringsAutorisatie;
    }

    /**
     * Geef het ID.
     * @return het ID
     */
    @NotNull
    public Integer getId() {
        return id;
    }

    /**
     * Zet het ID.
     * @param id het ID
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Geef het dienst ID.
     * @return het ID
     */
    public Integer getDienstId() {
        return dienstId;
    }

    /**
     * Zet het dienst ID.
     * @param dienstId het ID
     */
    public void setDienstId(Integer dienstId) {
        this.dienstId = dienstId;
    }

    /**
     * Geef de afnemercode.
     * @return de afnemercode
     */
    public String getAfnemerCode() {
        return afnemerCode;
    }

    /**
     * Zet de afnemercode.
     * @param afnemerCode de afnemercode
     */
    public void setAfnemerCode(String afnemerCode) {
        this.afnemerCode = afnemerCode;
    }

    /**
     * Geef de afnemernaam.
     * @return de afnemernaam
     */
    public String getAfnemerNaam() {
        return afnemerNaam;
    }

    /**
     * Zet de afnemernaam.
     * @param afnemerNaam de afnemernaam
     */
    public void setAfnemerNaam(String afnemerNaam) {
        this.afnemerNaam = afnemerNaam;
    }

    /**
     * Geef de eerste selectiedatum.
     * @return de eerste selectiedatum
     */
    public LocalDate getEersteSelectieDatum() {
        return eersteSelectieDatum;
    }

    /**
     * Zet de eerste selectiedatum.
     * @param eersteSelectieDatum de eerste selectiedatum
     */
    public void setEersteSelectieDatum(LocalDate eersteSelectieDatum) {
        this.eersteSelectieDatum = eersteSelectieDatum;
    }

    /**
     * Geef de selectiesoort.
     * @return de selectiesoort
     */
    public String getSelectieSoort() {
        return selectieSoort;
    }

    /**
     * Zet de selectiesoort.
     * @param selectieSoort de selectiesoort
     */
    public void setSelectieSoort(String selectieSoort) {
        this.selectieSoort = selectieSoort;
    }

    /**
     * Geef de berekende selectiedatum.
     * @return de berekende selectiedatum
     */
    public LocalDate getBerekendeSelectieDatum() {
        return berekendeSelectieDatum;
    }

    /**
     * Zet de berekende selectiedatum.
     * @param berekendeSelectieDatum de berekende selectiedatum
     */
    public void setBerekendeSelectieDatum(LocalDate berekendeSelectieDatum) {
        this.berekendeSelectieDatum = berekendeSelectieDatum;
    }

    /**
     * Geef het volgnummer.
     * @return het volgnummer
     */
    public Integer getVolgnummer() {
        return volgnummer;
    }

    /**
     * Zet het volgnummer.
     * @param volgnummer het volgnummer
     */
    public void setVolgnummer(Integer volgnummer) {
        this.volgnummer = volgnummer;
    }

    /**
     * Geef het toegang leveringsautorisatie ID.
     * @return het toegang leveringsautorisatie ID
     */
    public Integer getToegangLeveringsautorisatieId() {
        return toegangLeveringsautorisatieId;
    }

    /**
     * Zet de toegang leveringsautorisatie ID.
     * @param toegangLeveringsautorisatieId de toegang leveringsautorisatie ID
     */
    public void setToegangLeveringsautorisatieId(Integer toegangLeveringsautorisatieId) {
        this.toegangLeveringsautorisatieId = toegangLeveringsautorisatieId;
    }

    /**
     * Geef het peilmoment materieel resultaat.
     * @return het peilmoment materieel resultaat
     */
    public LocalDate getPeilmomentMaterieelResultaat() {
        return peilmomentMaterieelResultaat;
    }

    /**
     * Zet het peilmoment materieel resultaat.
     * @param peilmomentMaterieelResultaat het peilmoment materieel resultaat
     */
    public void setPeilmomentMaterieelResultaat(LocalDate peilmomentMaterieelResultaat) {
        this.peilmomentMaterieelResultaat = peilmomentMaterieelResultaat;
    }

    /**
     * Geef het peilmoment formeel resultaat
     * @return het peilmoment formeel resultaat
     */
    public ZonedDateTime getPeilmomentFormeelResultaat() {
        return peilmomentFormeelResultaat;
    }

    /**
     * Zet het peilmoment formeel resultaat.
     * @param peilmomentFormeelResultaat het peilmoment formeel resultaat
     */
    public void setPeilmomentFormeelResultaat(ZonedDateTime peilmomentFormeelResultaat) {
        this.peilmomentFormeelResultaat = peilmomentFormeelResultaat;
    }

    /**
     * Geef de status.
     * @return de status
     */
    public Short getStatus() {
        return status;
    }

    /**
     * Zet de status.
     * @param status de status
     */
    public void setStatus(Short status) {
        this.status = status;
    }

    /**
     * Geef de datum planning.
     * @return de datum planning
     */
    public LocalDate getDatumPlanning() {
        return datumPlanning;
    }

    /**
     * Zet de datum planning.
     * @param datumPlanning de datum planning
     */
    public void setDatumPlanning(LocalDate datumPlanning) {
        this.datumPlanning = datumPlanning;
    }

    /**
     * Geef de indicatie of de taak opnieuw gepland dient te worden.
     */
    public Boolean getOpnieuwPlannen() {
        return opnieuwPlannen;
    }

    /**
     * Zet vlag ter indicatie of de taak opnieuw gepland dient te worden.
     * @param opnieuwPlannen true als de taak opnieuw gepland dient te worden, anders false of null
     */
    public void setOpnieuwPlannen(Boolean opnieuwPlannen) {
        this.opnieuwPlannen = opnieuwPlannen;
    }

    /**
     * Geef de statustoelichting.
     * @return de statustoelichting
     */
    public String getStatusToelichting() {
        return statusToelichting;
    }

    /**
     * Zet de statustoelichting.
     * @param statusToelichting statustoelichting
     */
    public void setStatusToelichting(String statusToelichting) {
        this.statusToelichting = statusToelichting;
    }

    /**
     * Geef het selectieinterval van dienst.
     * @return selectieinterval
     */
    public Integer getSelectieInterval() {
        return selectieInterval;
    }

    /**
     * Zet het selectieinterval.
     * @param selectieInterval stelsel
     */
    public void setSelectieInterval(Integer selectieInterval) {
        this.selectieInterval = selectieInterval;
    }

    /**
     * Geef het stelsel.
     * @return stelsel
     */
    public String getStelsel() {
        return stelsel;
    }

    /**
     * Zet het stelsel.
     * @param stelsel stelsel
     */
    public void setStelsel(String stelsel) {
        this.stelsel = stelsel;
    }

    /**
     * Geef eenheid van selectie interval.
     * @return eenheid selectie interval
     */
    public String getEenheidSelectieInterval() {
        return eenheidSelectieInterval;
    }

    /**
     * Zet eenheid van selectie interval.
     * @param eenheidSelectieInterval eenheid selectie interval
     */
    public void setEenheidSelectieInterval(String eenheidSelectieInterval) {
        this.eenheidSelectieInterval = eenheidSelectieInterval;
    }

    /**
     * Geef de dienst.
     * @return de dienst
     */
    Dienst getDienst() {
        return dienst;
    }

    /**
     * Geef de toegang leveringsautorisatie.
     * @return de toegang leveringsautorisatie
     */
    ToegangLeveringsAutorisatie getToegangLeveringsautorisatie() {
        return toegangLeveringsautorisatie;
    }
}
