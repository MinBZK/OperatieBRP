/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.dal.domein.brp.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.BijhoudingResultaat;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Richting;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortBericht;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortMelding;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortSynchronisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.VerwerkingsResultaat;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Verwerkingswijze;
import nl.bzk.algemeenbrp.dal.domein.brp.util.ValidationUtils;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLInsert;

/**
 * Bericht entiteit uit het ber-schema.
 */
@Entity
@Table(name = "ber", schema = "ber")
@SQLInsert(sql = "INSERT INTO ber.ber (admhnd, bijhouding, crossreferentienr, data, tsontv, tsreg, tsverzending, dienst, "
        + "hoogstemeldingsniveau, levsautorisatie, ontvangendepartij, referentienr, richting, rol, srt, srtsynchronisatie, "
        + "verwerking, verwerkingswijze, zendendepartij, zendendesysteem, id) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "
        + "?)", check = ResultCheckStyle.NONE)
public class Bericht implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "ber_id_generator", sequenceName = "ber.seq_ber", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ber_id_generator")
    @Column(updatable = false)
    private Long id;

    @Column(name = "tsreg", nullable = false)
    private Timestamp datumTijdRegistratie;
    @Column(name = "srt")
    private Integer soortBerichtId;
    @Column(name = "richting", nullable = false)
    private int richtingId;
    @Column(name = "zendendepartij")
    private Short zendendePartij;
    @Column(name = "zendendesysteem", length = 50)
    private String zendendeSysteem;
    @Column(name = "ontvangendepartij")
    private Short ontvangendePartij;
    @Column(name = "referentienr", length = 36)
    private String referentieNummer;
    @Column(name = "crossreferentienr", length = 36)
    private String crossReferentieNummer;
    @Column(name = "tsverzending")
    private Timestamp datumTijdVerzending;
    @Column(name = "tsontv")
    private Timestamp datumTijdOntvangst;
    @Column(name = "verwerkingswijze")
    private Integer verwerkingswijzeId;
    @Column(name = "rol")
    private Integer rol;
    @Column(name = "srtsynchronisatie")
    private Integer soortSynchronisatieId;
    @Column(name = "levsautorisatie")
    private Integer leveringsAutorisatie;
    @Column(name = "dienst")
    private Integer dienst;
    @Column(name = "verwerking")
    private Integer verwerkingsResultaatId;
    @Column(name = "bijhouding")
    private Integer bijhoudingResultaatId;
    @Column(name = "hoogstemeldingsniveau")
    private Integer hoogsteMeldingsNiveauId;
    @Column(name = "admhnd")
    private Long administratieveHandeling;
    @Column(name = "data")
    private String data;
    @OneToMany(mappedBy = "bericht", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private final Set<BerichtPersoon> personen = new LinkedHashSet<>(0);

    /**
     * JPA Default constructor.
     */
    protected Bericht() {
    }

    /**
     * Maak een nieuw bericht aan met verplichte velden.
     * @param richting De {@link Richting} van het bericht
     * @param datumTijdRegistratie datumTijdRegistratie
     */
    public Bericht(final Richting richting, final Timestamp datumTijdRegistratie) {
        setRichting(richting);
        setDatumTijdRegistratie(datumTijdRegistratie);
    }

    /**
     * @return het ID van het bericht
     */
    public Long getId() {
        return id;
    }

    /**
     * Zet het ID van het bericht.
     * @param id het ID
     */
    public void setId(final Long id) {
        this.id = id;
    }

    /**
     * Geeft tijdstip registratie van het bericht.
     * @return tijdstip registratie
     */
    public Timestamp getDatumTijdRegistratie() {
        return datumTijdRegistratie;
    }

    /**
     * Zet tijdstip registratie van het bericht.
     * @param datumTijdRegistratie tijdstipregistratie
     */
    public void setDatumTijdRegistratie(Timestamp datumTijdRegistratie) {
        ValidationUtils.controleerOpNullWaarden("tijdstip registratie mag niet null zijn", datumTijdRegistratie);
        this.datumTijdRegistratie = datumTijdRegistratie;
    }

    /**
     * @return het soort bericht
     */
    public SoortBericht getSoortBericht() {
        return SoortBericht.parseId(soortBerichtId);
    }

    /**
     * Zet het soort bericht.
     * @param soortBericht het soort bericht
     */
    public void setSoortBericht(final SoortBericht soortBericht) {
        soortBerichtId = soortBericht.getId();
    }

    /**
     * @return de richting van het bericht
     */
    public Richting getRichting() {
        return Richting.parseId(richtingId);
    }

    /**
     * Zet de richting van het bericht.
     * @param richting de richting van het bericht
     */
    public void setRichting(final Richting richting) {
        ValidationUtils.controleerOpNullWaarden("Richting mag niet null zijn", richting);
        richtingId = richting.getId();
    }

    /**
     * @return Geeft de zendende partij terug
     */
    public Short getZendendePartij() {
        return zendendePartij;
    }

    /**
     * Zet de zendende partij.
     * @param zendendePartij de zendende partij
     */
    public void setZendendePartij(final Short zendendePartij) {
        this.zendendePartij = zendendePartij;
    }

    /**
     * @return het zendende systeem.
     */
    public String getZendendeSysteem() {
        return zendendeSysteem;
    }

    /**
     * Zet het zendende systeem.
     * @param zendendeSysteem het zendende systeem.
     */
    public void setZendendeSysteem(final String zendendeSysteem) {
        this.zendendeSysteem = zendendeSysteem;
    }

    /**
     * @return de ontvangende partij
     */
    public Short getOntvangendePartij() {
        return ontvangendePartij;
    }

    /**
     * Zet de ontvangende partij.
     * @param ontvangendePartij de ontvangende partij
     */
    public void setOntvangendePartij(final Short ontvangendePartij) {
        this.ontvangendePartij = ontvangendePartij;
    }

    /**
     * @return het referentie nummer
     */
    public String getReferentieNummer() {
        return referentieNummer;
    }

    /**
     * Zet het referentie nummer.
     * @param referentieNummer het referentie nummer
     */
    public void setReferentieNummer(final String referentieNummer) {
        this.referentieNummer = referentieNummer;
    }

    /**
     * @return het cross-referentie nummer
     */
    public String getCrossReferentieNummer() {
        return crossReferentieNummer;
    }

    /**
     * Zet het cross-referentie nummer.
     * @param crossReferentieNummer het cross-referentie nummer
     */
    public void setCrossReferentieNummer(final String crossReferentieNummer) {
        this.crossReferentieNummer = crossReferentieNummer;
    }

    /**
     * @return de datum/tijd verzending
     */
    public Timestamp getDatumTijdVerzending() {
        return Entiteit.timestamp(datumTijdVerzending);
    }

    /**
     * Zet de datum/tijd verzending.
     * @param datumTijdVerzending de datum/tijd verzending
     */
    public void setDatumTijdVerzending(final Timestamp datumTijdVerzending) {
        this.datumTijdVerzending = Entiteit.timestamp(datumTijdVerzending);
    }

    /**
     * @return de datum/tijd ontvangst.
     */
    public Timestamp getDatumTijdOntvangst() {
        return Entiteit.timestamp(datumTijdOntvangst);
    }

    /**
     * Zet de datum/tijd ontvangst.
     * @param datumTijdOntvangst de datum/tijd ontvangst
     */
    public void setDatumTijdOntvangst(final Timestamp datumTijdOntvangst) {
        this.datumTijdOntvangst = Entiteit.timestamp(datumTijdOntvangst);
    }

    /**
     * @return de verwerkingswijze
     */
    public Verwerkingswijze getVerwerkingswijze() {
        return Verwerkingswijze.parseId(verwerkingswijzeId);
    }

    /**
     * Zet de verwerkingswijze.
     * @param verwerkingswijze de verwerkingswijze
     */
    public void setVerwerkingswijze(final Verwerkingswijze verwerkingswijze) {
        if (verwerkingswijze != null) {
            verwerkingswijzeId = verwerkingswijze.getId();
        }
    }

    /**
     * @return de rol
     */
    public Integer getRol() {
        return rol;
    }

    /**
     * Zet de rol.
     * @param rol de rol
     */
    public void setRol(final Integer rol) {
        this.rol = rol;
    }

    /**
     * @return het soort synchronisatie
     */
    public SoortSynchronisatie getSoortSynchronisatie() {
        return SoortSynchronisatie.parseId(soortSynchronisatieId);
    }

    /**
     * Zet het soort synchronisatie.
     * @param soortSynchronisatie het soort synchronisatie
     */
    public void setSoortSynchronisatie(final SoortSynchronisatie soortSynchronisatie) {
        soortSynchronisatieId = soortSynchronisatie.getId();
    }

    /**
     * @return de levering autorisatie
     */
    public Integer getLeveringsAutorisatie() {
        return leveringsAutorisatie;
    }

    /**
     * Zet de levering autorisatie.
     * @param leveringsAutorisatie de levering autorisatie
     */
    public void setLeveringsAutorisatie(final Integer leveringsAutorisatie) {
        this.leveringsAutorisatie = leveringsAutorisatie;
    }

    /**
     * @return de dienst
     */
    public Integer getDienst() {
        return dienst;
    }

    /**
     * Zet de dienst.
     * @param dienst de dienst
     */
    public void setDienst(final Integer dienst) {
        this.dienst = dienst;
    }

    /**
     * @return het verwerkings resultaat
     */
    public VerwerkingsResultaat getVerwerkingsResultaat() {
        return VerwerkingsResultaat.parseId(verwerkingsResultaatId);
    }

    /**
     * Zet het verwerkings resultaat.
     * @param verwerkingsResultaat het verwerkings resultaat.
     */
    public void setVerwerkingsResultaat(final VerwerkingsResultaat verwerkingsResultaat) {
        if (verwerkingsResultaat != null) {
            verwerkingsResultaatId = verwerkingsResultaat.getId();
        }
    }

    /**
     * @return het bijhoudings resultaat
     */
    public BijhoudingResultaat getBijhoudingResultaat() {
        return BijhoudingResultaat.parseId(bijhoudingResultaatId);
    }

    /**
     * Zet het bijhoudings resultaat.
     * @param bijhoudingResultaat het bijhoudings resultaat
     */
    public void setBijhoudingResultaat(final BijhoudingResultaat bijhoudingResultaat) {
        if (bijhoudingResultaat != null) {
            bijhoudingResultaatId = bijhoudingResultaat.getId();
        }
    }

    /**
     * @return het hoogste meldings niveau
     */
    public SoortMelding getHoogsteMeldingsNiveau() {
        return SoortMelding.parseId(hoogsteMeldingsNiveauId);
    }

    /**
     * Zet het hoogste meldings niveau.
     * @param hoogsteMeldingsNiveau het hoogste meldings niveau
     */
    public void setHoogsteMeldingsNiveau(final SoortMelding hoogsteMeldingsNiveau) {
        if (hoogsteMeldingsNiveau != null) {
            hoogsteMeldingsNiveauId = hoogsteMeldingsNiveau.getId();
        }
    }

    /**
     * @return de administratieve handeling
     */
    public Long getAdministratieveHandeling() {
        return administratieveHandeling;
    }

    /**
     * Zet de administratieve handeling.
     * @param administratieveHandeling de administratieve handeling
     */
    public void setAdministratieveHandeling(final Long administratieveHandeling) {
        this.administratieveHandeling = administratieveHandeling;
    }

    /**
     * @return de data
     */
    public String getData() {
        return data;
    }

    /**
     * Zet de data.
     * @param data de data.
     */
    public void setData(final String data) {
        this.data = data;
    }

    /**
     * @return geeft de personen terug waar dit bericht voor is
     */
    public Set<BerichtPersoon> getPersonen() {
        return personen;
    }

    /**
     * Voegt de persoon toe aan het bericht.
     * @param persoon de persoon
     */
    public void addPersoon(final BerichtPersoon persoon) {
        personen.add(persoon);
    }
}
