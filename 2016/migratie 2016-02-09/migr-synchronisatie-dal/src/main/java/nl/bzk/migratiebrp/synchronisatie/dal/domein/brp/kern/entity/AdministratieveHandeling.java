/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity;

import java.io.Serializable;
import java.sql.Timestamp;
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

import nl.bzk.migratiebrp.conversie.model.validatie.ValidationUtils;
import nl.bzk.migratiebrp.util.common.Kopieer;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * The persistent class for the admhnd database table.
 *
 */
@Entity
@EntityListeners(GegevenInOnderzoekListener.class)
@Table(name = "admhnd", schema = "kern")
@SuppressWarnings({"checkstyle:designforextension", "multiplestringliterals" })
public class AdministratieveHandeling extends AbstractDeltaEntiteit implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "admhnd_id_generator", sequenceName = "kern.seq_admhnd", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "admhnd_id_generator")
    @Column(updatable = false, nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "partij", nullable = false)
    private Partij partij;

    @Column(name = "srt", nullable = false)
    private short soort;

    @Column(updatable = false, length = 400)
    private String toelichtingOntlening;

    @Column(name = "tsreg")
    private Timestamp datumTijdRegistratie;

    @Column(name = "tslev")
    private Timestamp datumTijdLevering;

    /**
     * JPA default constructor.
     */
    protected AdministratieveHandeling() {
    }

    /**
     * Maakt een AdministratieveHandeling object.
     *
     * @param partij
     *            de partij, mag niet null zijn
     * @param soort
     *            het soort administratieve handeling, mag niet null zijn
     */
    public AdministratieveHandeling(final Partij partij, final SoortAdministratieveHandeling soort) {
        ValidationUtils.controleerOpNullWaarden("partij mag niet null zijn", partij);
        this.partij = partij;
        ValidationUtils.controleerOpNullWaarden("soort mag niet null zijn", soort);
        this.soort = soort.getId();
    }

    /**
     * Geef de waarde van id.
     *
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * Zet de waarde van id.
     *
     * @param id
     *            id
     */
    public void setId(final Long id) {
        this.id = id;
    }

    /**
     * Geef de waarde van partij.
     *
     * @return partij
     */
    public Partij getPartij() {
        return partij;
    }

    /**
     * Geef de waarde van soort.
     *
     * @return soort
     */
    public SoortAdministratieveHandeling getSoort() {
        return SoortAdministratieveHandeling.parseId(soort);
    }

    /**
     * Zet de waarde van soort.
     *
     * @param soort
     *            soort
     */
    public void setSoort(final SoortAdministratieveHandeling soort) {
        ValidationUtils.controleerOpNullWaarden("soort mag niet null zijn", soort);
        this.soort = soort.getId();
    }

    /**
     * Geef de waarde van toelichting ontlening.
     *
     * @return toelichting ontlening
     */
    public String getToelichtingOntlening() {
        return toelichtingOntlening;
    }

    /**
     * Zet de waarde van toelichting ontlening.
     *
     * @param toelichtingOntlening
     *            toelichting ontlening
     */
    public void setToelichtingOntlening(final String toelichtingOntlening) {
        this.toelichtingOntlening = toelichtingOntlening;
    }

    /**
     * Geef de waarde van datum tijd registratie.
     *
     * @return datum tijd registratie
     */
    public Timestamp getDatumTijdRegistratie() {
        return Kopieer.timestamp(datumTijdRegistratie);
    }

    /**
     * Zet de waarde van datum tijd registratie.
     *
     * @param datumTijdRegistratie
     *            datum tijd registratie
     */
    public void setDatumTijdRegistratie(final Timestamp datumTijdRegistratie) {
        this.datumTijdRegistratie = Kopieer.timestamp(datumTijdRegistratie);
    }

    /**
     * Geef de waarde van datum tijd levering.
     *
     * @return datum tijd levering
     */
    public Timestamp getDatumTijdLevering() {
        return Kopieer.timestamp(datumTijdLevering);
    }

    /**
     * Zet de waarde van datum tijd levering.
     *
     * @param datumTijdLevering
     *            datum tijd levering
     */
    public void setDatumTijdLevering(final Timestamp datumTijdLevering) {
        this.datumTijdLevering = Kopieer.timestamp(datumTijdLevering);
    }

    /**
     * Toevoegen van een actie.
     *
     * @param actie
     *            actie
     */
    public final void addActie(final BRPActie actie) {
        actie.setAdministratieveHandeling(this);
    }

    @Override
    public final String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString())
                                                                          .append("id", id)
                                                                          .append("soort", getSoort())
                                                                          .toString();
    }
}
