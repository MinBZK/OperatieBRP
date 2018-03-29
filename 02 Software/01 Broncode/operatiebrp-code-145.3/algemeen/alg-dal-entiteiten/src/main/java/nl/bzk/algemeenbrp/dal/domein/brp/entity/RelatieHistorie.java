/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.dal.domein.brp.entity;

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
import nl.bzk.algemeenbrp.dal.domein.brp.util.ValidationUtils;

/**
 * The persistent class for the his_relatie database table.
 *
 */
@Entity
@EntityListeners(GegevenInOnderzoekListener.class)
@Table(name = "his_relatie", schema = "kern", uniqueConstraints = @UniqueConstraint(columnNames = {"relatie", "tsreg"}))
public class RelatieHistorie extends AbstractFormeleHistorie implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "his_relatie_id_generator", sequenceName = "kern.seq_his_relatie", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "his_relatie_id_generator")
    @Column(nullable = false)
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "landgebiedaanv")
    private LandOfGebied landOfGebiedAanvang;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "landgebiedeinde")
    private LandOfGebied landOfGebiedEinde;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gemaanv")
    private Gemeente gemeenteAanvang;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gemeinde")
    private Gemeente gemeenteEinde;

    @Column(name = "wplnaamaanv")
    private String woonplaatsnaamAanvang;

    @Column(name = "wplnaameinde")
    private String woonplaatsnaamEinde;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rdneinde")
    private RedenBeeindigingRelatie redenBeeindigingRelatie;

    // bi-directional many-to-one association to Relatie
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "relatie", nullable = false)
    private Relatie relatie;

    /**
     * JPA default constructor.
     */
    protected RelatieHistorie() {}

    /**
     * Maak een nieuwe huwelijk geregistreerd partnerschap historie.
     *
     * @param relatie relatie
     */
    public RelatieHistorie(final Relatie relatie) {
        setRelatie(relatie);
    }

    /*
     * (non-Javadoc)
     * 
     * @see nl.bzk.algemeen.dal.domein.brp.kern.entity.DeltaEntiteit#getId()
     */
    @Override
    public Long getId() {
        return id;
    }

    /**
     * Zet de waarden voor id van RelatieHistorie.
     *
     * @param id de nieuwe waarde voor id van RelatieHistorie
     */
    public void setId(final Long id) {
        this.id = id;
    }

    /**
     * Geef de waarde van buitenlandse plaats aanvang van RelatieHistorie.
     *
     * @return de waarde van buitenlandse plaats aanvang van RelatieHistorie
     */
    public String getBuitenlandsePlaatsAanvang() {
        return buitenlandsePlaatsAanvang;
    }

    /**
     * Zet de waarden voor buitenlandse plaats aanvang van RelatieHistorie.
     *
     * @param buitenlandsePlaatsAanvang de nieuwe waarde voor buitenlandse plaats aanvang van
     *        RelatieHistorie
     */
    public void setBuitenlandsePlaatsAanvang(final String buitenlandsePlaatsAanvang) {
        ValidationUtils.controleerOpLegeWaarden("buitenlandsePlaatsAanvang mag geen lege string zijn", buitenlandsePlaatsAanvang);
        this.buitenlandsePlaatsAanvang = buitenlandsePlaatsAanvang;
    }

    /**
     * Geef de waarde van buitenlandse plaats einde van RelatieHistorie.
     *
     * @return de waarde van buitenlandse plaats einde van RelatieHistorie
     */
    public String getBuitenlandsePlaatsEinde() {
        return buitenlandsePlaatsEinde;
    }

    /**
     * Zet de waarden voor buitenlandse plaats einde van RelatieHistorie.
     *
     * @param buitenlandsePlaatsEinde de nieuwe waarde voor buitenlandse plaats einde van
     *        RelatieHistorie
     */
    public void setBuitenlandsePlaatsEinde(final String buitenlandsePlaatsEinde) {
        ValidationUtils.controleerOpLegeWaarden("buitenlandsePlaatsEinde mag geen lege string zijn", buitenlandsePlaatsEinde);
        this.buitenlandsePlaatsEinde = buitenlandsePlaatsEinde;
    }

    /**
     * Geef de waarde van buitenlandse regio aanvang van RelatieHistorie.
     *
     * @return de waarde van buitenlandse regio aanvang van RelatieHistorie
     */
    public String getBuitenlandseRegioAanvang() {
        return buitenlandseRegioAanvang;
    }

    /**
     * Zet de waarden voor buitenlandse regio aanvang van RelatieHistorie.
     *
     * @param buitenlandseRegioAanvang de nieuwe waarde voor buitenlandse regio aanvang van
     *        RelatieHistorie
     */
    public void setBuitenlandseRegioAanvang(final String buitenlandseRegioAanvang) {
        ValidationUtils.controleerOpLegeWaarden("buitenlandseRegioAanvang mag geen lege string zijn", buitenlandseRegioAanvang);
        this.buitenlandseRegioAanvang = buitenlandseRegioAanvang;
    }

    /**
     * Geef de waarde van buitenlandse regio einde van RelatieHistorie.
     *
     * @return de waarde van buitenlandse regio einde van RelatieHistorie
     */
    public String getBuitenlandseRegioEinde() {
        return buitenlandseRegioEinde;
    }

    /**
     * Zet de waarden voor buitenlandse regio einde van RelatieHistorie.
     *
     * @param buitenlandseRegioEinde de nieuwe waarde voor buitenlandse regio einde van
     *        RelatieHistorie
     */
    public void setBuitenlandseRegioEinde(final String buitenlandseRegioEinde) {
        ValidationUtils.controleerOpLegeWaarden("buitenlandseRegioEinde mag geen lege string zijn", buitenlandseRegioEinde);
        this.buitenlandseRegioEinde = buitenlandseRegioEinde;
    }

    /**
     * Geef de waarde van datum aanvang van RelatieHistorie.
     *
     * @return de waarde van datum aanvang van RelatieHistorie
     */
    public Integer getDatumAanvang() {
        return datumAanvang;
    }

    /**
     * Zet de waarden voor datum aanvang van RelatieHistorie.
     *
     * @param datumAanvang de nieuwe waarde voor datum aanvang van RelatieHistorie
     */
    public void setDatumAanvang(final Integer datumAanvang) {
        this.datumAanvang = datumAanvang;
    }

    /**
     * Geef de waarde van datum einde van RelatieHistorie.
     *
     * @return de waarde van datum einde van RelatieHistorie
     */
    public Integer getDatumEinde() {
        return datumEinde;
    }

    /**
     * Zet de waarden voor datum einde van RelatieHistorie.
     *
     * @param datumEinde de nieuwe waarde voor datum einde van RelatieHistorie
     */
    public void setDatumEinde(final Integer datumEinde) {
        this.datumEinde = datumEinde;
    }

    /**
     * Geef de waarde van omschrijving locatie aanvang van RelatieHistorie.
     *
     * @return de waarde van omschrijving locatie aanvang van RelatieHistorie
     */
    public String getOmschrijvingLocatieAanvang() {
        return omschrijvingLocatieAanvang;
    }

    /**
     * Zet de waarden voor omschrijving locatie aanvang van RelatieHistorie.
     *
     * @param omschrijvingLocatieAanvang de nieuwe waarde voor omschrijving locatie aanvang van
     *        RelatieHistorie
     */
    public void setOmschrijvingLocatieAanvang(final String omschrijvingLocatieAanvang) {
        ValidationUtils.controleerOpLegeWaarden("omschrijvingLocatieAanvang mag geen lege string zijn", omschrijvingLocatieAanvang);
        this.omschrijvingLocatieAanvang = omschrijvingLocatieAanvang;
    }

    /**
     * Geef de waarde van omschrijving locatie einde van RelatieHistorie.
     *
     * @return de waarde van omschrijving locatie einde van RelatieHistorie
     */
    public String getOmschrijvingLocatieEinde() {
        return omschrijvingLocatieEinde;
    }

    /**
     * Zet de waarden voor omschrijving locatie einde van RelatieHistorie.
     *
     * @param omschrijvingLocatieEinde de nieuwe waarde voor omschrijving locatie einde van
     *        RelatieHistorie
     */
    public void setOmschrijvingLocatieEinde(final String omschrijvingLocatieEinde) {
        ValidationUtils.controleerOpLegeWaarden("omschrijvingLocatieEinde mag geen lege string zijn", omschrijvingLocatieEinde);
        this.omschrijvingLocatieEinde = omschrijvingLocatieEinde;
    }

    /**
     * Geef de waarde van land of gebied aanvang van RelatieHistorie.
     *
     * @return de waarde van land of gebied aanvang van RelatieHistorie
     */
    public LandOfGebied getLandOfGebiedAanvang() {
        return landOfGebiedAanvang;
    }

    /**
     * Zet de waarden voor land of gebied aanvang van RelatieHistorie.
     *
     * @param landOfGebiedAanvang de nieuwe waarde voor land of gebied aanvang van RelatieHistorie
     */
    public void setLandOfGebiedAanvang(final LandOfGebied landOfGebiedAanvang) {
        this.landOfGebiedAanvang = landOfGebiedAanvang;
    }

    /**
     * Geef de waarde van land of gebied einde van RelatieHistorie.
     *
     * @return de waarde van land of gebied einde van RelatieHistorie
     */
    public LandOfGebied getLandOfGebiedEinde() {
        return landOfGebiedEinde;
    }

    /**
     * Zet de waarden voor land of gebied einde van RelatieHistorie.
     *
     * @param landOfGebiedEinde de nieuwe waarde voor land of gebied einde van RelatieHistorie
     */
    public void setLandOfGebiedEinde(final LandOfGebied landOfGebiedEinde) {
        this.landOfGebiedEinde = landOfGebiedEinde;
    }

    /**
     * Geef de waarde van gemeente aanvang van RelatieHistorie.
     *
     * @return de waarde van gemeente aanvang van RelatieHistorie
     */
    public Gemeente getGemeenteAanvang() {
        return gemeenteAanvang;
    }

    /**
     * Zet de waarden voor gemeente aanvang van RelatieHistorie.
     *
     * @param gemeenteAanvang de nieuwe waarde voor gemeente aanvang van RelatieHistorie
     */
    public void setGemeenteAanvang(final Gemeente gemeenteAanvang) {
        this.gemeenteAanvang = gemeenteAanvang;
    }

    /**
     * Geef de waarde van gemeente einde van RelatieHistorie.
     *
     * @return de waarde van gemeente einde van RelatieHistorie
     */
    public Gemeente getGemeenteEinde() {
        return gemeenteEinde;
    }

    /**
     * Zet de waarden voor gemeente einde van RelatieHistorie.
     *
     * @param gemeenteEinde de nieuwe waarde voor gemeente einde van RelatieHistorie
     */
    public void setGemeenteEinde(final Gemeente gemeenteEinde) {
        this.gemeenteEinde = gemeenteEinde;
    }

    /**
     * Geef de waarde van woonplaatsnaam aanvang van RelatieHistorie.
     *
     * @return de waarde van woonplaatsnaam aanvang van RelatieHistorie
     */
    public String getWoonplaatsnaamAanvang() {
        return woonplaatsnaamAanvang;
    }

    /**
     * Zet de waarden voor woonplaatsnaam aanvang van RelatieHistorie.
     *
     * @param woonplaatsnaamAanvang de nieuwe waarde voor woonplaatsnaam aanvang van RelatieHistorie
     */
    public void setWoonplaatsnaamAanvang(final String woonplaatsnaamAanvang) {
        this.woonplaatsnaamAanvang = woonplaatsnaamAanvang;
    }

    /**
     * Geef de waarde van woonplaatsnaam einde van RelatieHistorie.
     *
     * @return de waarde van woonplaatsnaam einde van RelatieHistorie
     */
    public String getWoonplaatsnaamEinde() {
        return woonplaatsnaamEinde;
    }

    /**
     * Zet de waarden voor woonplaatsnaam einde van RelatieHistorie.
     *
     * @param woonplaatsnaamEinde de nieuwe waarde voor woonplaatsnaam einde van RelatieHistorie
     */
    public void setWoonplaatsnaamEinde(final String woonplaatsnaamEinde) {
        this.woonplaatsnaamEinde = woonplaatsnaamEinde;
    }

    /**
     * Geef de waarde van reden beeindiging relatie van RelatieHistorie.
     *
     * @return de waarde van reden beeindiging relatie van RelatieHistorie
     */
    public RedenBeeindigingRelatie getRedenBeeindigingRelatie() {
        return redenBeeindigingRelatie;
    }

    /**
     * Zet de waarden voor reden beeindiging relatie van RelatieHistorie.
     *
     * @param redenBeeindigingRelatie de nieuwe waarde voor reden beeindiging relatie van
     *        RelatieHistorie
     */
    public void setRedenBeeindigingRelatie(final RedenBeeindigingRelatie redenBeeindigingRelatie) {
        this.redenBeeindigingRelatie = redenBeeindigingRelatie;
    }

    /**
     * Geef de waarde van relatie van RelatieHistorie.
     *
     * @return de waarde van relatie van RelatieHistorie
     */
    public Relatie getRelatie() {
        return relatie;
    }

    /**
     * Zet de waarden voor relatie van RelatieHistorie.
     *
     * @param relatie de nieuwe waarde voor relatie van RelatieHistorie
     */
    public void setRelatie(final Relatie relatie) {
        ValidationUtils.controleerOpNullWaarden("relatie mag niet null zijn", relatie);
        this.relatie = relatie;
    }

    /**
     * Maakt een kopie van deze relatie historie met daarin alleen de gegevens die betrekking hebben
     * op de aanvang.
     *
     * @return een nieuw relatie voorkomen met de aanvang gegevens van dit voorkomen
     */
    public final RelatieHistorie maakKopieVanAanvang() {
        final RelatieHistorie result = new RelatieHistorie(relatie);
        kopierAanvangAttributen(result);
        return result;
    }

    /**
     * Maakt een kopie van deze relatie historie.
     *
     * @return een nieuw relatie voorkomen met de gegevens van dit voorkomen
     */
    public final RelatieHistorie maakKopie() {
        final RelatieHistorie result = new RelatieHistorie(relatie);
        kopierAanvangAttributen(result);
        kopierEindeAttributen(result);
        return result;
    }

    private void kopierAanvangAttributen(final RelatieHistorie doel) {
        doel.setBuitenlandsePlaatsAanvang(buitenlandsePlaatsAanvang);
        doel.setBuitenlandseRegioAanvang(buitenlandseRegioAanvang);
        doel.setDatumAanvang(datumAanvang);
        doel.setOmschrijvingLocatieAanvang(omschrijvingLocatieAanvang);
        doel.setLandOfGebiedAanvang(landOfGebiedAanvang);
        doel.setGemeenteAanvang(gemeenteAanvang);
        doel.setWoonplaatsnaamAanvang(woonplaatsnaamAanvang);
    }

    private void kopierEindeAttributen(final RelatieHistorie doel) {
        doel.setBuitenlandsePlaatsEinde(buitenlandsePlaatsEinde);
        doel.setBuitenlandseRegioEinde(buitenlandseRegioEinde);
        doel.setDatumEinde(datumEinde);
        doel.setOmschrijvingLocatieEinde(omschrijvingLocatieEinde);
        doel.setLandOfGebiedEinde(landOfGebiedEinde);
        doel.setGemeenteEinde(gemeenteEinde);
        doel.setWoonplaatsnaamEinde(woonplaatsnaamEinde);
        doel.setRedenBeeindigingRelatie(redenBeeindigingRelatie);
    }
}
