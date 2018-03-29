/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.archivering.domain.algemeen;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.BijhoudingResultaat;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Richting;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortBericht;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortMelding;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortSynchronisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.VerwerkingsResultaat;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Verwerkingswijze;

/**
 * Archivering opdracht.
 */
@JsonAutoDetect
public final class ArchiveringOpdracht {

    private Long administratieveHandelingId;
    private BijhoudingResultaat bijhoudingResultaat;
    private String crossReferentienummer;
    private String data;
    private Integer dienstId;
    private SoortMelding hoogsteMeldingsNiveau;
    private Integer leveringsAutorisatieId;
    private Short ontvangendePartijId;
    private String referentienummer;
    private Richting richting;
    private Integer rolId;
    private SoortBericht soortBericht;
    private SoortSynchronisatie soortSynchronisatie;
    private Set<Long> teArchiverenPersonen = new HashSet<>();
    private ZonedDateTime tijdstipVerzending;
    private ZonedDateTime tijdstipOntvangst;
    private VerwerkingsResultaat verwerkingsresultaat;
    private Short zendendePartijId;
    private String zendendeSysteem;
    private Verwerkingswijze verwerkingswijze;
    private ZonedDateTime tijdstipRegistratie;

    /**
     * Constructor.
     * @param richting richting van het bericht
     * @param tsReg tijdstip registratie
     */
    @JsonCreator
    public ArchiveringOpdracht(@JsonProperty("richting") final Richting richting, @JsonProperty("tijdstipRegistratie") final ZonedDateTime tsReg) {
        this.richting = richting;
        this.tijdstipRegistratie = tsReg;
    }

    /**
     * Geef administratieve handeling id.
     * @return administratieve handeling id
     */
    public Long getAdministratieveHandelingId() {
        return administratieveHandelingId;
    }

    /**
     * Zet administratieve handeling id.
     * @param administratieveHandelingId administratieve handeling id
     */
    public void setAdministratieveHandelingId(final Long administratieveHandelingId) {
        this.administratieveHandelingId = administratieveHandelingId;
    }

    /**
     * Geeft de {@link BijhoudingResultaat} terug.
     * @return de bijhoudingResultaat
     */
    public BijhoudingResultaat getBijhoudingResultaat() {
        return bijhoudingResultaat;
    }

    /**
     * Zet de {@link BijhoudingResultaat} voor deze archivering, mag null zijn.
     * @param bijhoudingResultaat de {@link BijhoudingResultaat}
     */
    public void setBijhoudingResultaat(final BijhoudingResultaat bijhoudingResultaat) {
        this.bijhoudingResultaat = bijhoudingResultaat;
    }

    /**
     * Geef cross referentienummer.
     * @return the cross referentienummer
     */
    public String getCrossReferentienummer() {
        return crossReferentienummer;
    }

    /**
     * Zet cross referentienummer.
     * @param crossReferentienummer cross referentienummer
     */
    public void setCrossReferentienummer(final String crossReferentienummer) {
        this.crossReferentienummer = crossReferentienummer;
    }

    /**
     * Geef data.
     * @return the data
     */
    public String getData() {
        return data;
    }

    /**
     * Zet data.
     * @param data data
     */
    public void setData(final String data) {
        this.data = data;
    }

    /**
     * Geef dienst id.
     * @return dienst id.
     */
    public Integer getDienstId() {
        return dienstId;
    }

    /**
     * Zet dienst id.
     * @param dienstId dienst id
     */
    public void setDienstId(final Integer dienstId) {
        this.dienstId = dienstId;
    }

    /**
     * Geef hoogste meldings niveau.
     * @return hoogste meldings niveau
     */
    public SoortMelding getHoogsteMeldingsNiveau() {
        return hoogsteMeldingsNiveau;
    }

    /**
     * Zet hoogste meldings niveau.
     * @param hoogsteMeldingsNiveau hoogste meldings niveau
     */
    public void setHoogsteMeldingsNiveau(final SoortMelding hoogsteMeldingsNiveau) {
        this.hoogsteMeldingsNiveau = hoogsteMeldingsNiveau;
    }

    /**
     * Geef leverings autorisatie id.
     * @return leverings autorisatie id
     */
    public Integer getLeveringsAutorisatieId() {
        return leveringsAutorisatieId;
    }

    /**
     * Zet leverings autorisatie id.
     * @param leveringsAutorisatieId leverings autorisatie id
     */
    public void setLeveringsAutorisatieId(final Integer leveringsAutorisatieId) {
        this.leveringsAutorisatieId = leveringsAutorisatieId;
    }

    /**
     * Geef ontvangende partij id.
     * @return the ontvangende partij id
     */
    public Short getOntvangendePartijId() {
        return ontvangendePartijId;
    }

    /**
     * Zet ontvangende partij id.
     * @param ontvangendePartijId ontvangende partij id
     */
    public void setOntvangendePartijId(final Short ontvangendePartijId) {
        this.ontvangendePartijId = ontvangendePartijId;
    }

    /**
     * Geef referentienummer.
     * @return the referentienummer
     */
    public String getReferentienummer() {
        return referentienummer;
    }

    /**
     * Zet referentienummer.
     * @param referentienummer referentienummer
     */
    public void setReferentienummer(final String referentienummer) {
        this.referentienummer = referentienummer;
    }

    /**
     * Geef richting.
     * @return the richting
     */
    public Richting getRichting() {
        return richting;
    }

    /**
     * Zet richting.
     * @param richting richting
     */
    public void setRichting(final Richting richting) {
        this.richting = richting;
    }

    /**
     * Geef rol id.
     * @return rol id
     */
    public Integer getRolId() {
        return rolId;
    }

    /**
     * Zet rol id.
     * @param rolId rol id
     */
    public void setRolId(final Integer rolId) {
        this.rolId = rolId;
    }

    /**
     * Geef soort bericht.
     * @return the soort bericht
     */
    public SoortBericht getSoortBericht() {
        return soortBericht;
    }

    /**
     * Zet soort bericht.
     * @param soortBericht soort bericht
     */
    public void setSoortBericht(final SoortBericht soortBericht) {
        this.soortBericht = soortBericht;
    }

    /**
     * Geef soort synchronisatie.
     * @return soort synchronisatie
     */
    public SoortSynchronisatie getSoortSynchronisatie() {
        return soortSynchronisatie;
    }

    /**
     * Zet soort synchronisatie.
     * @param soortSynchronisatie soort synchronisatie
     */
    public void setSoortSynchronisatie(final SoortSynchronisatie soortSynchronisatie) {
        this.soortSynchronisatie = soortSynchronisatie;
    }

    /**
     * Geef lijst van te archiveren personen.
     * @return lijst van te archiveren personen
     */
    public Set<Long> getTeArchiverenPersonen() {
        return Collections.unmodifiableSet(new HashSet<>(teArchiverenPersonen));
    }

    /**
     * Voeg te archiveren persoon id toe.
     * @param teArchiverenPersoon te archiveren persoon id
     */
    public void addTeArchiverenPersoon(final Long teArchiverenPersoon) {
        this.teArchiverenPersonen.add(teArchiverenPersoon);
    }

    /**
     * Geef tijdstip verzending.
     * @return the tijdstip verzending
     */
    public ZonedDateTime getTijdstipVerzending() {
        return tijdstipVerzending;
    }

    /**
     * Zet tijdstip verzending.
     * @param tijdstipVerzending tijdstip verzending
     */
    public void setTijdstipVerzending(final ZonedDateTime tijdstipVerzending) {
        this.tijdstipVerzending = tijdstipVerzending;
    }

    /**
     * Geef tijdstip ontvangst.
     * @return the tijdstip ontvangst
     */
    public ZonedDateTime getTijdstipOntvangst() {
        return tijdstipOntvangst;
    }

    /**
     * Zet tijdstip ontvangst.
     * @param tijdstipOntvangst tijdstip ontvangst
     */
    public void setTijdstipOntvangst(final ZonedDateTime tijdstipOntvangst) {
        this.tijdstipOntvangst = tijdstipOntvangst;
    }

    /**
     * Geef verwerkings resultaat.
     * @return verwerkings resultaat
     */
    public VerwerkingsResultaat getVerwerkingsresultaat() {
        return verwerkingsresultaat;
    }

    /**
     * Zet verwerkings resultaat.
     * @param verwerkingsresultaat verwerkings resultaat
     */
    public void setVerwerkingsresultaat(final VerwerkingsResultaat verwerkingsresultaat) {
        this.verwerkingsresultaat = verwerkingsresultaat;
    }

    /**
     * Geef zendende partij code.
     * @return the zendende partij code
     */
    public Short getZendendePartijId() {
        return zendendePartijId;
    }

    /**
     * Zet zendende partij id.
     * @param zendendePartijId zendende partij id
     */
    public void setZendendePartijId(final Short zendendePartijId) {
        this.zendendePartijId = zendendePartijId;
    }

    /**
     * Geef zendende systeem.
     * @return the zendende systeem
     */
    public String getZendendeSysteem() {
        return zendendeSysteem;
    }

    /**
     * Zet zendende systeem.
     * @param zendendeSysteem zendende systeem
     */
    public void setZendendeSysteem(final String zendendeSysteem) {
        this.zendendeSysteem = zendendeSysteem;
    }

    /**
     * Geef verwerkingswijze.
     * @return verwerkingswijze
     */
    public Verwerkingswijze getVerwerkingswijze() {
        return verwerkingswijze;
    }

    /**
     * Zet verwerkingswijze.
     * @param verwerkingswijze verwerkingswijze
     */
    public void setVerwerkingswijze(Verwerkingswijze verwerkingswijze) {
        this.verwerkingswijze = verwerkingswijze;
    }

    /**
     * Geef het tijdstipRegistratie.
     * @return tijdstipRegistratie
     */
    public ZonedDateTime getTijdstipRegistratie() {
        return tijdstipRegistratie;
    }

    /**
     * Zet het tijdstipRegistratie.
     * @param tijdstipRegistratie tijdstipRegistratie
     */
    public void setTijdstipRegistratie(ZonedDateTime tijdstipRegistratie) {
        this.tijdstipRegistratie = tijdstipRegistratie;
    }
}
