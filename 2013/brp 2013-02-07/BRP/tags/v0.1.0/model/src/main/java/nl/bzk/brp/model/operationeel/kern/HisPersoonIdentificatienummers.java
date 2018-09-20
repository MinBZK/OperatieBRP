/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import nl.bzk.brp.model.basis.AbstractMaterieleEnFormeleHistorie;


/**
 * Entity voor de historie van de groep "Identificatienummers" van Kern.Persoon.
 */
@Entity
@Table(schema = "kern", name = "His_PersIDs")
@Access(AccessType.FIELD)
public class HisPersoonIdentificatienummers extends AbstractMaterieleEnFormeleHistorie {

    @Id
    @SequenceGenerator(name = "HisPersIdentificatieNrs", sequenceName = "Kern.seq_His_PersIDs")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HisPersIdentificatieNrs")
    @Column(name = "ID")
    private Long              id;

    @ManyToOne
    @JoinColumn(name = "Pers")
    private PersistentPersoon persoon;

    @Column(name = "BSN")
    private String            burgerservicenummer;

    @Column(name = "ANr")
    private String            aNummer;

    public PersistentPersoon getPersoon() {
        return persoon;
    }

    public void setPersoon(final PersistentPersoon persoon) {
        this.persoon = persoon;
    }

    public String getBurgerservicenummer() {
        return burgerservicenummer;
    }

    public void setBurgerservicenummer(final String burgerservicenummer) {
        this.burgerservicenummer = burgerservicenummer;
    }

    public String getANummer() {
        return aNummer;
    }

    public void setANummer(final String aNummer) {
        this.aNummer = aNummer;
    }

    public Long getId() {
        return id;
    }
}
