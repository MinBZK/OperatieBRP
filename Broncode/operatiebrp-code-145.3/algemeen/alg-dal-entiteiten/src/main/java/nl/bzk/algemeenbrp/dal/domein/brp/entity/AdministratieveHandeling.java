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
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortAdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.StatusLeveringAdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.util.ValidationUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * The persistent class for the admhnd database table.
 */
@Entity
@EntityListeners(GegevenInOnderzoekListener.class)
@Table(name = "admhnd", schema = "kern")
public class AdministratieveHandeling extends AbstractEntiteit implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "admhnd_id_generator", sequenceName = "kern.seq_admhnd", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "admhnd_id_generator")
    @Column(updatable = false, nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "partij", nullable = false)
    private Partij partij;

    @Column(name = "srt", nullable = false)
    private int soort;

    @Column(updatable = false, length = 400)
    private String toelichtingOntlening;

    @Column(name = "tsreg", nullable = false)
    private Timestamp datumTijdRegistratie;

    @Column(name = "tslev")
    private Timestamp datumTijdLevering;

    @Column(name = "statuslev", nullable = false)
    private Integer statusLeveringId;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "administratieveHandeling", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE},
            orphanRemoval = true)
    private final Set<AdministratieveHandelingGedeblokkeerdeRegel> administratieveHandelingGedeblokkeerdeRegelSet = new LinkedHashSet<>(0);

    /**
     * JPA default constructor.
     */
    protected AdministratieveHandeling() {}

    /**
     * Maakt een AdministratieveHandeling object.
     *
     * @param partij de partij, mag niet null zijn
     * @param soort het soort administratieve handeling, mag niet null zijn
     * @param datumTijdRegistratie datumTijdRegistratie, mag niet null zijn
     */
    public AdministratieveHandeling(final Partij partij, final SoortAdministratieveHandeling soort, final Timestamp datumTijdRegistratie) {
        ValidationUtils.controleerOpNullWaarden("partij mag niet null zijn", partij);
        this.partij = partij;
        setSoort(soort);
        setDatumTijdRegistratie(datumTijdRegistratie);
        setStatusLevering(soort == SoortAdministratieveHandeling.GBA_INITIELE_VULLING ? StatusLeveringAdministratieveHandeling.NIET_LEVEREN
                : StatusLeveringAdministratieveHandeling.TE_LEVEREN);
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
     * Zet de waarden voor id van AdministratieveHandeling.
     *
     * @param id de nieuwe waarde voor id van AdministratieveHandeling
     */
    public void setId(final Long id) {
        this.id = id;
    }

    /**
     * Geef de waarde van partij van AdministratieveHandeling.
     *
     * @return de waarde van partij van AdministratieveHandeling
     */
    public Partij getPartij() {
        return partij;
    }

    /**
     * Geef de waarde van soort van AdministratieveHandeling.
     *
     * @return de waarde van soort van AdministratieveHandeling
     */
    public SoortAdministratieveHandeling getSoort() {
        return SoortAdministratieveHandeling.parseId(soort);
    }

    /**
     * Zet de waarden voor soort van AdministratieveHandeling.
     *
     * @param soort de nieuwe waarde voor soort van AdministratieveHandeling
     */
    public void setSoort(final SoortAdministratieveHandeling soort) {
        ValidationUtils.controleerOpNullWaarden("soort mag niet null zijn", soort);
        this.soort = soort.getId();
    }

    /**
     * Geef de waarde van toelichting ontlening van AdministratieveHandeling.
     *
     * @return de waarde van toelichting ontlening van AdministratieveHandeling
     */
    public String getToelichtingOntlening() {
        return toelichtingOntlening;
    }

    /**
     * Zet de waarden voor toelichting ontlening van AdministratieveHandeling.
     *
     * @param toelichtingOntlening de nieuwe waarde voor toelichting ontlening van
     *        AdministratieveHandeling
     */
    public void setToelichtingOntlening(final String toelichtingOntlening) {
        this.toelichtingOntlening = toelichtingOntlening;
    }

    /**
     * Geef de waarde van datum tijd registratie van AdministratieveHandeling.
     *
     * @return de waarde van datum tijd registratie van AdministratieveHandeling
     */
    public Timestamp getDatumTijdRegistratie() {
        return Entiteit.timestamp(datumTijdRegistratie);
    }

    /**
     * Zet de waarden voor datum tijd registratie van AdministratieveHandeling.
     *
     * @param datumTijdRegistratie de nieuwe waarde voor datum tijd registratie van
     *        AdministratieveHandeling
     */
    public void setDatumTijdRegistratie(final Timestamp datumTijdRegistratie) {
        ValidationUtils.controleerOpNullWaarden("datumTijdRegistratie mag niet null zijn", datumTijdRegistratie);
        this.datumTijdRegistratie = Entiteit.timestamp(datumTijdRegistratie);
    }

    /**
     * Geef de waarde van statusLevering.
     *
     * @return statusLevering
     */
    public StatusLeveringAdministratieveHandeling getStatusLevering() {
        return StatusLeveringAdministratieveHandeling.parseId(statusLeveringId);
    }

    /**
     * Zet de waarde van statusLevering.
     *
     * @param statusLevering statusLevering
     */
    public void setStatusLevering(final StatusLeveringAdministratieveHandeling statusLevering) {
        if (statusLevering == null) {
            statusLeveringId = null;
        } else {
            statusLeveringId = statusLevering.getId();
        }
    }

    /**
     * Geef de waarde van datum tijd levering van AdministratieveHandeling.
     *
     * @return de waarde van datum tijd levering van AdministratieveHandeling
     */
    public Timestamp getDatumTijdLevering() {
        return Entiteit.timestamp(datumTijdLevering);
    }

    /**
     * Zet de waarden voor datum tijd levering van AdministratieveHandeling.
     *
     * @param datumTijdLevering de nieuwe waarde voor datum tijd levering van
     *        AdministratieveHandeling
     */
    public void setDatumTijdLevering(final Timestamp datumTijdLevering) {
        this.datumTijdLevering = Entiteit.timestamp(datumTijdLevering);
    }

    /**
     * Toevoegen van een actie.
     *
     * @param actie actie
     */
    public final void addActie(final BRPActie actie) {
        actie.setAdministratieveHandeling(this);
    }

    /**
     * Geef de waarde van administratieve handeling gedeblokkeerde regel set van
     * AdministratieveHandeling.
     *
     * @return de waarde van administratieve handeling gedeblokkeerde regel set van
     *         AdministratieveHandeling
     */
    public Set<AdministratieveHandelingGedeblokkeerdeRegel> getAdministratieveHandelingGedeblokkeerdeRegelSet() {
        return administratieveHandelingGedeblokkeerdeRegelSet;
    }

    /**
     * Toevoegen van een administratieve handeling gedeblokkeerde regel.
     *
     * @param administratieveHandelingGedeblokkeerdeRegel administratieve handeling gedeblokkeerde
     *        regel
     */
    public final void addAdministratieveHandelingGedeblokkeerdeRegel(
            final AdministratieveHandelingGedeblokkeerdeRegel administratieveHandelingGedeblokkeerdeRegel) {
        administratieveHandelingGedeblokkeerdeRegel.setAdministratieveHandeling(this);
        administratieveHandelingGedeblokkeerdeRegelSet.add(administratieveHandelingGedeblokkeerdeRegel);
    }

    @Override
    public final String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("id", id).append("soort", getSoort())
                .toString();
    }
}
