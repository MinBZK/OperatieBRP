/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;
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
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.apache.commons.lang3.builder.EqualsBuilder;

/**
 * The persistent class for the onderzoek database table.
 * 
 */
@Entity
@EntityListeners(GegevenInOnderzoekListener.class)
@Table(name = "onderzoek", schema = "kern")
@SuppressWarnings("checkstyle:designforextension")
public class Onderzoek extends AbstractDeltaEntiteit implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "onderzoek_id_generator", sequenceName = "kern.seq_onderzoek", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "onderzoek_id_generator")
    @Column(nullable = false)
    /**
     * Het veld zou Integer moeten zijn maar is Long i.v.m. de link naar GegevenInOnderzoek.
     */
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "admhnd")
    private AdministratieveHandeling administratieveHandeling;

    @Column(name = "dataanv")
    private Integer datumAanvang;

    @Column(name = "dateinde")
    private Integer datumEinde;

    @Column(name = "oms", length = 2147483647)
    private String omschrijving;

    @Column(name = "status")
    private Short statusOnderzoekId;

    @Column(name = "verwachteafhandeldat")
    private Integer verwachteAfhandelDatum;

    @Transient
    private boolean voortgekomenUitNietActueelVoorkomen;

    // bi-directional many-to-one association to OnderzoekHistorie
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "onderzoek", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE },
            orphanRemoval = true)
    private Set<OnderzoekHistorie> onderzoekHistorieSet = new LinkedHashSet<>(0);

    // bi-directional one-to-many association to PartijOnderzoek
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "onderzoek", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE },
            orphanRemoval = true)
    private Set<PartijOnderzoek> partijOnderzoekSet = new LinkedHashSet<>(0);

    // bi-directional one-to-many association to PersoonOnderzoek
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "onderzoek", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE },
            orphanRemoval = true)
    private Set<PersoonOnderzoek> persoonOnderzoekSet = new LinkedHashSet<>(0);

    /**
     * Onderzoeken moeten EAGER worden geladen ivm uni-directionele koppeling van {@link GegevenInOnderzoek} naar
     * {@link DeltaEntiteit} door {@link GegevenInOnderzoekListener}. Dit wordt gedaan ivm @Any in de
     * {@link GegevenInOnderzoek}.
     */
    // bi-directional one-to-many association to GegevenInOnderzoek
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "onderzoek", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE },
            orphanRemoval = true)
    private Set<GegevenInOnderzoek> gegevenInOnderzoekSet = new LinkedHashSet<>(0);

    // bi-directional one-to-many association to OnderzoekAfgeleidAdministratiefHistorie
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "onderzoek", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE },
            orphanRemoval = true)
    private Set<OnderzoekAfgeleidAdministratiefHistorie> onderzoekAfgeleidAdministratiefHistorieSet = new LinkedHashSet<>(0);

    /**
     * Maak een nieuwe onderzoek.
     */
    public Onderzoek() {
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
     * Geef de waarde van administratieve handeling.
     *
     * @return administratieve handeling
     */
    public AdministratieveHandeling getAdministratieveHandeling() {
        return administratieveHandeling;
    }

    /**
     * Zet de waarde van administratieve handeling.
     *
     * @param administratieveHandeling
     *            administratieve handeling
     */
    public void setAdministratieveHandeling(final AdministratieveHandeling administratieveHandeling) {
        this.administratieveHandeling = administratieveHandeling;
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
     * Geef de waarde van omschrijving.
     *
     * @return omschrijving
     */
    public String getOmschrijving() {
        return omschrijving;
    }

    /**
     * Zet de waarde van omschrijving.
     *
     * @param omschrijving
     *            omschrijving
     */
    public void setOmschrijving(final String omschrijving) {
        this.omschrijving = omschrijving;
    }

    /**
     * Geef de waarde van status onderzoek.
     *
     * @return status onderzoek
     */
    public StatusOnderzoek getStatusOnderzoek() {
        return StatusOnderzoek.parseId(statusOnderzoekId);
    }

    /**
     * Zet de waarde van status onderzoek.
     *
     * @param statusOnderzoek
     *            status onderzoek
     */
    public void setStatusOnderzoek(final StatusOnderzoek statusOnderzoek) {
        statusOnderzoekId = statusOnderzoek.getId();
    }

    /**
     * Geef de waarde van verwachte afhandel datum.
     *
     * @return verwachte afhandel datum
     */
    public Integer getVerwachteAfhandelDatum() {
        return verwachteAfhandelDatum;
    }

    /**
     * Zet de waarde van verwachte afhandel datum.
     *
     * @param verwachteAfhandelDatum
     *            verwachte afhandel datum
     */
    public void setVerwachteAfhandelDatum(final Integer verwachteAfhandelDatum) {
        this.verwachteAfhandelDatum = verwachteAfhandelDatum;
    }

    /**
     * Geef de waarde van onderzoek historie set.
     *
     * @return onderzoek historie set
     */
    public Set<OnderzoekHistorie> getOnderzoekHistorieSet() {
        return onderzoekHistorieSet;
    }

    /**
     * Toevoegen van een onderzoek historie.
     *
     * @param onderzoekHistorie
     *            onderzoek historie
     */
    public void addOnderzoekHistorie(final OnderzoekHistorie onderzoekHistorie) {
        onderzoekHistorie.setOnderzoek(this);
        onderzoekHistorieSet.add(onderzoekHistorie);
    }

    /**
     * Geef de waarde van partij onderzoek set.
     *
     * @return partij onderzoek set
     */
    public Set<PartijOnderzoek> getPartijOnderzoekSet() {
        return partijOnderzoekSet;
    }

    /**
     * Toevoegen van een partij onderzoek.
     *
     * @param partijOnderzoek
     *            partij onderzoek
     */
    public void addPartijOnderzoek(final PartijOnderzoek partijOnderzoek) {
        partijOnderzoek.setOnderzoek(this);
        partijOnderzoekSet.add(partijOnderzoek);
    }

    /**
     * Geef de waarde van persoon onderzoek set.
     *
     * @return persoon onderzoek set
     */
    public Set<PersoonOnderzoek> getPersoonOnderzoekSet() {
        return persoonOnderzoekSet;
    }

    /**
     * Toevoegen van een persoon onderzoek.
     *
     * @param persoonOnderzoek
     *            persoon onderzoek
     */
    public void addPersoonOnderzoek(final PersoonOnderzoek persoonOnderzoek) {
        persoonOnderzoek.setOnderzoek(this);
        persoonOnderzoekSet.add(persoonOnderzoek);
    }

    /**
     * Geef de waarde van gegeven in onderzoek set.
     *
     * @return gegeven in onderzoek set
     */
    public Set<GegevenInOnderzoek> getGegevenInOnderzoekSet() {
        return gegevenInOnderzoekSet;
    }

    /**
     * Toevoegen van een gegeven in onderzoek.
     *
     * @param gegevenInOnderzoek
     *            gegeven in onderzoek
     */
    public void addGegevenInOnderzoek(final GegevenInOnderzoek gegevenInOnderzoek) {
        gegevenInOnderzoek.setOnderzoek(this);
        gegevenInOnderzoekSet.add(gegevenInOnderzoek);
    }

    /**
     * Geef de waarde van onderzoek afgeleid administratief historie set.
     *
     * @return onderzoek afgeleid administratief historie set
     */
    public Set<OnderzoekAfgeleidAdministratiefHistorie> getOnderzoekAfgeleidAdministratiefHistorieSet() {
        return onderzoekAfgeleidAdministratiefHistorieSet;
    }

    /**
     * Toevoegen van een onderzoek afgeleid administratief historie.
     *
     * @param onderzoekAfgeleidAdministratiefHistorie
     *            onderzoek afgeleid administratief historie
     */
    public void addOnderzoekAfgeleidAdministratiefHistorie(final OnderzoekAfgeleidAdministratiefHistorie onderzoekAfgeleidAdministratiefHistorie) {
        onderzoekAfgeleidAdministratiefHistorie.setOnderzoek(this);
        onderzoekAfgeleidAdministratiefHistorieSet.add(onderzoekAfgeleidAdministratiefHistorie);
    }

    /**
     * Controleert of het meegegeven onderzoek inhoudelijk gelijk is aan dit onderzoek.
     * 
     * @param anderOnderzoek
     *            het onderzoek waarmee vergeleken gaat worden
     * @return true als het onderzoek inhoudelijk gelijk is aan dit onderzoek
     */
    public final boolean isInhoudelijkGelijk(final Onderzoek anderOnderzoek) {
        return new EqualsBuilder().append(datumAanvang, anderOnderzoek.datumAanvang)
                                  .append(datumEinde, anderOnderzoek.datumEinde)
                                  .append(omschrijving, anderOnderzoek.omschrijving)
                                  .append(statusOnderzoekId, anderOnderzoek.statusOnderzoekId)
                                  .append(verwachteAfhandelDatum, anderOnderzoek.verwachteAfhandelDatum)
                                  .isEquals();
    }

    /**
     * Update de inhoud van dit onderzoek adhv het meegegeven onderzoek.
     * 
     * @param nieuwOnderzoek
     *            het onderzoek waarmee dit onderzoek wordt bijgewerkt.
     */
    public final void updateInhoud(final Onderzoek nieuwOnderzoek) {
        datumAanvang = nieuwOnderzoek.datumAanvang;
        datumEinde = nieuwOnderzoek.datumEinde;
        omschrijving = nieuwOnderzoek.omschrijving;
        statusOnderzoekId = nieuwOnderzoek.statusOnderzoekId;
        verwachteAfhandelDatum = nieuwOnderzoek.verwachteAfhandelDatum;
    }

    /**
     * geeft true terug als dit onderzoek te herlijden is tot een historish voorkomen in LO3.
     *
     * @return boolean true als te herlijden is uit historisch voorkomen LO3
     */
    public final boolean isVoortgekomenUitNietActueelVoorkomen() {
        return voortgekomenUitNietActueelVoorkomen;
    }

    /**
     * Geeft aan dat dit onderzoek is te herlijden tot een historisch voorkomen in LO3.
     *
     * @param waarde
     *            boolean waarde
     */
    public final void setVoortgekomenUitNietActueelVoorkomen(final boolean waarde) {
        voortgekomenUitNietActueelVoorkomen = waarde;
    }

}
