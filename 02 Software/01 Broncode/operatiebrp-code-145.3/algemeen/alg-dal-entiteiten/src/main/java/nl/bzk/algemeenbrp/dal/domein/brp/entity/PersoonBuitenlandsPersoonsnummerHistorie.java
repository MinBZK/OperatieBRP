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
 * The persistent class for the his_persblpersnr database table.
 *
 */
@Entity
@EntityListeners(GegevenInOnderzoekListener.class)
@Table(name = "his_persblpersnr", schema = "kern", uniqueConstraints = @UniqueConstraint(columnNames = {"tsreg"}))
public class PersoonBuitenlandsPersoonsnummerHistorie extends AbstractFormeleHistorie implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "his_persblpersnr_id_generator", sequenceName = "kern.seq_his_persblpersnr", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "his_persblpersnr_id_generator")
    @Column(nullable = false)
    private Long id;

    // bi-directional many-to-one association to Persoon
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    @JoinColumn(name = "persblpersnr", nullable = false)
    private PersoonBuitenlandsPersoonsnummer persoonBuitenlandsPersoonsnummer;

    /**
     * JPA default constructor.
     */
    protected PersoonBuitenlandsPersoonsnummerHistorie() {}

    /**
     * Maak een nieuw PersoonBuitenlandsPersoonsnummerHistorie object.
     *
     * @param persoonBuitenlandsPersoonsnummer persoonBuitenlandsPersoonsnummer, mag niet null zijn
     */
    public PersoonBuitenlandsPersoonsnummerHistorie(final PersoonBuitenlandsPersoonsnummer persoonBuitenlandsPersoonsnummer) {
        setPersoonBuitenlandsPersoonsnummer(persoonBuitenlandsPersoonsnummer);
    }

    @Override
    public Long getId() {
        return id;
    }

    /**
     * Zet de waarden voor id van PersoonBuitenlandsPersoonsnummerHistorie.
     *
     * @param id de nieuwe waarde voor id van PersoonBuitenlandsPersoonsnummerHistorie
     */
    public void setId(final Long id) {
        this.id = id;
    }

    /**
     * Geef de waarde van persoonBuitenlandsPersoonsnummer.
     *
     * @return persoonBuitenlandsPersoonsnummer
     */
    public PersoonBuitenlandsPersoonsnummer getPersoonBuitenlandsPersoonsnummer() {
        return persoonBuitenlandsPersoonsnummer;
    }

    /**
     * Zet de waarde van persoonBuitenlandsPersoonsnummer.
     *
     * @param persoonBuitenlandsPersoonsnummer persoonBuitenlandsPersoonsnummer, mag niet null zijn
     */
    public void setPersoonBuitenlandsPersoonsnummer(final PersoonBuitenlandsPersoonsnummer persoonBuitenlandsPersoonsnummer) {
        ValidationUtils.controleerOpNullWaarden("persoonBuitenlandsPersoonsnummer mag niet null zijn", persoonBuitenlandsPersoonsnummer);
        this.persoonBuitenlandsPersoonsnummer = persoonBuitenlandsPersoonsnummer;
    }
}
