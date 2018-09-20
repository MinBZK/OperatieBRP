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
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import nl.bzk.brp.model.operationeel.StatusHistorie;


/**
 * Een actuele Voornaam van een Persoon in het operationele model.
 */
@Entity
@Access(AccessType.FIELD)
@Table(name = "PersVoornaam", schema = "Kern")
public class PersistentPersoonVoornaam {

    @Id
    @SequenceGenerator(name = "PersVoornaam", sequenceName = "Kern.seq_PersVoornaam")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PersVoornaam")
    private Long                    id;

    @ManyToOne(targetEntity = PersistentPersoon.class)
    @JoinColumn(name = "Pers")
    private PersistentPersoon       persoon;

    @Column(name = "Volgnr")
    private Integer                 volgnummer;

    @Column(name = "Naam")
    private String                  naam;

    @Column(name = "PersVoornaamStatusHis")
    @Enumerated(value = EnumType.STRING)
    private StatusHistorie          persoonVoornaamStatusHis;

    public PersistentPersoon getPersoon() {
        return persoon;
    }

    public void setPersoon(final PersistentPersoon persoon) {
        this.persoon = persoon;
    }

    public Integer getVolgnummer() {
        return volgnummer;
    }

    public void setVolgnummer(final Integer volgnummer) {
        this.volgnummer = volgnummer;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(final String naam) {
        this.naam = naam;
    }

    public Long getId() {
        return id;
    }

    public StatusHistorie getPersoonVoornaamStatusHis() {
        return persoonVoornaamStatusHis;
    }

    public void setPersoonVoornaamStatusHis(final StatusHistorie persoonVoornaamStatusHis) {
        this.persoonVoornaamStatusHis = persoonVoornaamStatusHis;
    }
}
