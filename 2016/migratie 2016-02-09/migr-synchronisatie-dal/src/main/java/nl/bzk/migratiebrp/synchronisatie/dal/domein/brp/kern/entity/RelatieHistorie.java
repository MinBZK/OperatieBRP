/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import nl.bzk.migratiebrp.conversie.model.brp.attribuut.Validatie;
import nl.bzk.migratiebrp.conversie.model.validatie.ValidationUtils;

/**
 * The persistent class for the his_relatie database table.
 *
 */
@Entity
@EntityListeners(GegevenInOnderzoekListener.class)
@Table(name = "his_relatie", schema = "kern", uniqueConstraints = @UniqueConstraint(columnNames = {"relatie", "tsreg" }))
@SuppressWarnings("checkstyle:designforextension")
public class RelatieHistorie extends AbstractFormeleHistorie implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "his_relatie_id_generator", sequenceName = "kern.seq_his_relatie", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "his_relatie_id_generator")
    @Column(nullable = false)
    /**
     * Het veld zou Integer moeten zijn maar is Long i.v.m. de link naar GegevenInOnderzoek.
     */
    private Long id;

    @Column(name = "blplaatsaanv", length = 40)
    private String buitenlandsePlaatsAanvang;

    @Column(name = "blplaatseinde", length = 40)
    private String buitenlandsePlaatsEinde;

    @Column(name = "blregioaanv", length = 35)
    private String buitenlandseRegioAanvang;

    @Column(name = "blregioeinde", length = 35)
    private String buitenlandseRegioEinde;

    @Column(name = "dataanv")
    private Integer datumAanvang;

    @Column(name = "dateinde")
    private Integer datumEinde;

    @Column(name = "omslocaanv", length = 40)
    private String omschrijvingLocatieAanvang;

    @Column(name = "omsloceinde", length = 40)
    private String omschrijvingLocatieEinde;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "landgebiedaanv")
    private LandOfGebied landOfGebiedAanvang;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "landgebiedeinde")
    private LandOfGebied landOfGebiedEinde;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "gemaanv")
    private Gemeente gemeenteAanvang;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "gemeinde")
    private Gemeente gemeenteEinde;

    @Column(name = "wplnaamaanv")
    private String woonplaatsnaamAanvang;

    @Column(name = "wplnaameinde")
    private String woonplaatsnaamEinde;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "rdneinde")
    private RedenBeeindigingRelatie redenBeeindigingRelatie;

    // bi-directional many-to-one association to Relatie
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "relatie", nullable = false)
    private Relatie relatie;

    /**
     * JPA default constructor.
     */
    protected RelatieHistorie() {
    }

    /**
     * Maak een nieuwe huwelijk geregistreerd partnerschap historie.
     *
     * @param relatie
     *            relatie
     */
    public RelatieHistorie(final Relatie relatie) {
        setRelatie(relatie);
    }

    /**
     * Geef de Id waarde voor de entiteit. Intern is de Id een Long voor integratie met GegevenInOnderzoek. De waarde
     * wordt geconverteerd naar een Integer.
     *
     * @return de Id waarde.
     */
    public Integer getId() {
        return convertLongNaarInteger(id);
    }

    /**
     * Zet de Id waarde voor de entiteit. Intern wordt de Id waarde geconverteert naar een Long voor integratie met
     * GegevenInOnderzoek.
     *
     * @param id
     *            de Id waarde.
     */
    public void setId(final Integer id) {
        this.id = convertIntegerNaarLong(id);
    }

    /**
     * Geef de waarde van buitenlandse plaats aanvang.
     *
     * @return buitenlandse plaats aanvang
     */
    public String getBuitenlandsePlaatsAanvang() {
        return buitenlandsePlaatsAanvang;
    }

    /**
     * Zet de waarde van buitenlandse plaats aanvang.
     *
     * @param buitenlandsePlaatsAanvang
     *            buitenlandse plaats aanvang
     */
    public void setBuitenlandsePlaatsAanvang(final String buitenlandsePlaatsAanvang) {
        Validatie.controleerOpLegeWaarden("buitenlandsePlaatsAanvang mag geen lege string zijn", buitenlandsePlaatsAanvang);
        this.buitenlandsePlaatsAanvang = buitenlandsePlaatsAanvang;
    }

    /**
     * Geef de waarde van buitenlandse plaats einde.
     *
     * @return buitenlandse plaats einde
     */
    public String getBuitenlandsePlaatsEinde() {
        return buitenlandsePlaatsEinde;
    }

    /**
     * Zet de waarde van buitenlandse plaats einde.
     *
     * @param buitenlandsePlaatsEinde
     *            buitenlandse plaats einde
     */
    public void setBuitenlandsePlaatsEinde(final String buitenlandsePlaatsEinde) {
        Validatie.controleerOpLegeWaarden("buitenlandsePlaatsEinde mag geen lege string zijn", buitenlandsePlaatsEinde);
        this.buitenlandsePlaatsEinde = buitenlandsePlaatsEinde;
    }

    /**
     * Geef de waarde van buitenlandse regio aanvang.
     *
     * @return buitenlandse regio aanvang
     */
    public String getBuitenlandseRegioAanvang() {
        return buitenlandseRegioAanvang;
    }

    /**
     * Zet de waarde van buitenlandse regio aanvang.
     *
     * @param buitenlandseRegioAanvang
     *            buitenlandse regio aanvang
     */
    public void setBuitenlandseRegioAanvang(final String buitenlandseRegioAanvang) {
        Validatie.controleerOpLegeWaarden("buitenlandseRegioAanvang mag geen lege string zijn", buitenlandseRegioAanvang);
        this.buitenlandseRegioAanvang = buitenlandseRegioAanvang;
    }

    /**
     * Geef de waarde van buitenlandse regio einde.
     *
     * @return buitenlandse regio einde
     */
    public String getBuitenlandseRegioEinde() {
        return buitenlandseRegioEinde;
    }

    /**
     * Zet de waarde van buitenlandse regio einde.
     *
     * @param buitenlandseRegioEinde
     *            buitenlandse regio einde
     */
    public void setBuitenlandseRegioEinde(final String buitenlandseRegioEinde) {
        Validatie.controleerOpLegeWaarden("buitenlandseRegioEinde mag geen lege string zijn", buitenlandseRegioEinde);
        this.buitenlandseRegioEinde = buitenlandseRegioEinde;
    }

    /**
     * Geef de waarde van datum aanvang.
     *
     * @return datum aanvang
     */
    public Integer getDatumAanvang() {
        return datumAanvang;
    }

    /**
     * Zet de waarde van datum aanvang.
     *
     * @param datumAanvang
     *            datum aanvang
     */
    public void setDatumAanvang(final Integer datumAanvang) {
        this.datumAanvang = datumAanvang;
    }

    /**
     * Geef de waarde van datum einde.
     *
     * @return datum einde
     */
    public Integer getDatumEinde() {
        return datumEinde;
    }

    /**
     * Zet de waarde van datum einde.
     *
     * @param datumEinde
     *            datum einde
     */
    public void setDatumEinde(final Integer datumEinde) {
        this.datumEinde = datumEinde;
    }

    /**
     * Geef de waarde van omschrijving locatie aanvang.
     *
     * @return omschrijving locatie aanvang
     */
    public String getOmschrijvingLocatieAanvang() {
        return omschrijvingLocatieAanvang;
    }

    /**
     * Zet de waarde van omschrijving locatie aanvang.
     *
     * @param omschrijvingLocatieAanvang
     *            omschrijving locatie aanvang
     */
    public void setOmschrijvingLocatieAanvang(final String omschrijvingLocatieAanvang) {
        Validatie.controleerOpLegeWaarden("omschrijvingLocatieAanvang mag geen lege string zijn", omschrijvingLocatieAanvang);
        this.omschrijvingLocatieAanvang = omschrijvingLocatieAanvang;
    }

    /**
     * Geef de waarde van omschrijving locatie einde.
     *
     * @return omschrijving locatie einde
     */
    public String getOmschrijvingLocatieEinde() {
        return omschrijvingLocatieEinde;
    }

    /**
     * Zet de waarde van omschrijving locatie einde.
     *
     * @param omschrijvingLocatieEinde
     *            omschrijving locatie einde
     */
    public void setOmschrijvingLocatieEinde(final String omschrijvingLocatieEinde) {
        Validatie.controleerOpLegeWaarden("omschrijvingLocatieEinde mag geen lege string zijn", omschrijvingLocatieEinde);
        this.omschrijvingLocatieEinde = omschrijvingLocatieEinde;
    }

    /**
     * Geef de waarde van land of gebied aanvang.
     *
     * @return land of gebied aanvang
     */
    public LandOfGebied getLandOfGebiedAanvang() {
        return landOfGebiedAanvang;
    }

    /**
     * Zet de waarde van land of gebied aanvang.
     *
     * @param landOfGebiedAanvang
     *            land of gebied aanvang
     */
    public void setLandOfGebiedAanvang(final LandOfGebied landOfGebiedAanvang) {
        this.landOfGebiedAanvang = landOfGebiedAanvang;
    }

    /**
     * Geef de waarde van land of gebied einde.
     *
     * @return land of gebied einde
     */
    public LandOfGebied getLandOfGebiedEinde() {
        return landOfGebiedEinde;
    }

    /**
     * Zet de waarde van land of gebied einde.
     *
     * @param landOfGebiedEinde
     *            land of gebied einde
     */
    public void setLandOfGebiedEinde(final LandOfGebied landOfGebiedEinde) {
        this.landOfGebiedEinde = landOfGebiedEinde;
    }

    /**
     * Geef de waarde van gemeente aanvang.
     *
     * @return gemeente aanvang
     */
    public Gemeente getGemeenteAanvang() {
        return gemeenteAanvang;
    }

    /**
     * Zet de waarde van gemeente aanvang.
     *
     * @param gemeenteAanvang
     *            gemeente aanvang
     */
    public void setGemeenteAanvang(final Gemeente gemeenteAanvang) {
        this.gemeenteAanvang = gemeenteAanvang;
    }

    /**
     * Geef de waarde van gemeente einde.
     *
     * @return gemeente einde
     */
    public Gemeente getGemeenteEinde() {
        return gemeenteEinde;
    }

    /**
     * Zet de waarde van gemeente einde.
     *
     * @param gemeenteEinde
     *            gemeente einde
     */
    public void setGemeenteEinde(final Gemeente gemeenteEinde) {
        this.gemeenteEinde = gemeenteEinde;
    }

    /**
     * Geef de waarde van woonplaatsnaam aanvang.
     *
     * @return woonplaatsnaam aanvang
     */
    public String getWoonplaatsnaamAanvang() {
        return woonplaatsnaamAanvang;
    }

    /**
     * Zet de waarde van woonplaatsnaam aanvang.
     *
     * @param woonplaatsnaamAanvang
     *            woonplaatsnaam aanvang
     */
    public void setWoonplaatsnaamAanvang(final String woonplaatsnaamAanvang) {
        this.woonplaatsnaamAanvang = woonplaatsnaamAanvang;
    }

    /**
     * Geef de waarde van woonplaatsnaam einde.
     *
     * @return woonplaatsnaam einde
     */
    public String getWoonplaatsnaamEinde() {
        return woonplaatsnaamEinde;
    }

    /**
     * Zet de waarde van woonplaatsnaam einde.
     *
     * @param woonplaatsnaamEinde
     *            woonplaatsnaam einde
     */
    public void setWoonplaatsnaamEinde(final String woonplaatsnaamEinde) {
        this.woonplaatsnaamEinde = woonplaatsnaamEinde;
    }

    /**
     * Geef de waarde van reden beeindiging relatie.
     *
     * @return reden beeindiging relatie
     */
    public RedenBeeindigingRelatie getRedenBeeindigingRelatie() {
        return redenBeeindigingRelatie;
    }

    /**
     * Zet de waarde van reden beeindiging relatie.
     *
     * @param redenBeeindigingRelatie
     *            reden beeindiging relatie
     */
    public void setRedenBeeindigingRelatie(final RedenBeeindigingRelatie redenBeeindigingRelatie) {
        this.redenBeeindigingRelatie = redenBeeindigingRelatie;
    }

    /**
     * Geef de waarde van relatie.
     *
     * @return relatie
     */
    public Relatie getRelatie() {
        return relatie;
    }

    /**
     * Zet de waarde van relatie.
     *
     * @param relatie
     *            relatie
     */
    public void setRelatie(final Relatie relatie) {
        ValidationUtils.controleerOpNullWaarden("relatie mag niet null zijn", relatie);
        this.relatie = relatie;
    }
}
