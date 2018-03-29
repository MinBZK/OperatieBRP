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
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.dal.domein.brp.util.ValidationUtils;

/**
 * The persistent class for the admhndgedeblokkeerderegel database table.
 */
@Entity
@Table(name = "admhndgedeblokkeerderegel", schema = "kern")
public class AdministratieveHandelingGedeblokkeerdeRegel extends AbstractEntiteit implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "admhndgedeblokkeerderegel_id_generator", sequenceName = "kern.seq_admhndgedeblokkeerderegel", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "admhndgedeblokkeerderegel_id_generator")
    @Column(nullable = false)
    private Long id;

    // bi-directional many-to-one association to AdministratieveHandeling
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "admhnd", nullable = false)
    private AdministratieveHandeling administratieveHandeling;

    @Column(name = "regel", updatable = false, nullable = false)
    private int regelId;

    /**
     * JPA default constructor.
     */
    protected AdministratieveHandelingGedeblokkeerdeRegel() {}

    /**
     * Maak een nieuwe administratieve handeling gedeblokkeerde regel.
     *
     * @param administratieveHandeling de administratieve handeling, mag niet null zijn
     * @param regel de nieuwe waarde voor regel, mag niet null zijn
     */
    public AdministratieveHandelingGedeblokkeerdeRegel(final AdministratieveHandeling administratieveHandeling, final Regel regel) {
        setAdministratieveHandeling(administratieveHandeling);
        setRegel(regel);
    }

    /**
     * Geef de waarde van id van AdministratieveHandelingGedeblokkeerdeRegel.
     *
     * @return de waarde van id van AdministratieveHandelingGedeblokkeerdeRegel
     */
    @Override
    public Long getId() {
        return id;
    }

    /**
     * Zet de waarden voor id van AdministratieveHandelingGedeblokkeerdeRegel.
     *
     * @param id de nieuwe waarde voor id van AdministratieveHandelingGedeblokkeerdeRegel
     */
    public void setId(final Long id) {
        this.id = id;
    }

    /**
     * Geef de waarde van administratieve handeling van AdministratieveHandelingGedeblokkeerdeRegel.
     *
     * @return de waarde van administratieve handeling van
     *         AdministratieveHandelingGedeblokkeerdeRegel
     */
    public AdministratieveHandeling getAdministratieveHandeling() {
        return administratieveHandeling;
    }

    /**
     * Zet de waarden voor administratieve handeling van
     * AdministratieveHandelingGedeblokkeerdeRegel.
     *
     * @param administratieveHandeling de nieuwe waarde voor administratieve handeling van
     *        AdministratieveHandelingGedeblokkeerdeRegel
     */
    public void setAdministratieveHandeling(final AdministratieveHandeling administratieveHandeling) {
        ValidationUtils.controleerOpNullWaarden("administratieve handeling mag niet null zijn", administratieveHandeling);
        this.administratieveHandeling = administratieveHandeling;
    }

    /**
     * Geef de waarde van regel.
     *
     * @return de waarde van regel
     */
    public Regel getRegel() {
        return Regel.parseId(regelId);
    }

    /**
     * Zet de waarden voor regel.
     *
     * @param regel de nieuwe waarde voor regel
     */
    public void setRegel(final Regel regel) {
        ValidationUtils.controleerOpNullWaarden("regel mag niet null zijn", regel);
        regelId = regel.getId();
    }
}
