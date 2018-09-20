/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern;

import java.util.Set;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import nl.bzk.brp.model.PersistentRootObject;
import nl.bzk.brp.model.gedeeld.GeslachtsAanduiding;
import nl.bzk.brp.model.gedeeld.Partij;

/**
 * Een actueel persoon in het operationeel model.
 */
@Entity
@Access(AccessType.FIELD)
@Table(name = "Pers", schema = "Kern")
public class PersistentPersoon implements PersistentRootObject {

    @Id
    @SequenceGenerator(name = "PERSOON", sequenceName = "Kern.seq_Pers")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PERSOON")
    private Long id;

    @Column(name = "BSN")
    private String burgerservicenummer;

    @Column(name = "anr")
    private String aNummer;

    @Column(name = "datgeboorte")
    private Integer datumGeboorte;

    @Column(name = "geslachtsaand")
    private GeslachtsAanduiding geslachtsAanduiding;

    @Column(name = "geslnaam")
    private String geslachtsNaam;

    @Column(name = "voornamen")
    private String voornaam;

    @ManyToOne
    @JoinColumn(name = "bijhgem")
    private Partij bijhoudingsGemeente;

    @OneToMany
    @JoinColumn(name = "pers")
    private Set<PersistentPersoonAdres> adressen;

    /**
     * No-args constructor, vereist voor JPA.
     */
    public PersistentPersoon() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getBurgerservicenummer() {
        return burgerservicenummer;
    }

    public void setBurgerservicenummer(final String burgerservicenummer) {
        this.burgerservicenummer = burgerservicenummer;
    }

    public Set<PersistentPersoonAdres> getAdressen() {
        return adressen;
    }

    public void setAdressen(final Set<PersistentPersoonAdres> adressen) {
        this.adressen = adressen;
    }

    public Partij getBijhoudingsGemeente() {
        return bijhoudingsGemeente;
    }

    public void setBijhoudingsGemeente(final Partij bijhoudingsGemeente) {
        this.bijhoudingsGemeente = bijhoudingsGemeente;
    }

    public String getANummer() {
        return aNummer;
    }

    public void setANummer(final String aNummer) {
        this.aNummer = aNummer;
    }

    public Integer getDatumGeboorte() {
        return datumGeboorte;
    }

    public void setDatumGeboorte(final Integer datumGeboorte) {
        this.datumGeboorte = datumGeboorte;
    }

    public GeslachtsAanduiding getGeslachtsAanduiding() {
        return geslachtsAanduiding;
    }

    public void setGeslachtsAanduiding(final GeslachtsAanduiding geslachtsAanduiding) {
        this.geslachtsAanduiding = geslachtsAanduiding;
    }

    public String getGeslachtsNaam() {
        return geslachtsNaam;
    }

    public void setGeslachtsNaam(final String geslachtsNaam) {
        this.geslachtsNaam = geslachtsNaam;
    }

    public String getVoornaam() {
        return voornaam;
    }

    public void setVoornaam(final String voornaam) {
        this.voornaam = voornaam;
    }
}
