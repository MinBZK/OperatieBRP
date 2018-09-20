/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.domein.lev;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import nl.bzk.brp.bevraging.domein.StatusHistorie;
import nl.bzk.brp.bevraging.domein.aut.DoelBinding;
import nl.bzk.brp.bevraging.domein.ber.SoortBericht;


/**
 * Abonnement.
 */
@Entity
@Table(name = "Abonnement", schema = "Lev")
@Access(AccessType.FIELD)
public class Abonnement implements Serializable {

    @SequenceGenerator(name = "ABONNEMENT_SEQUENCE_GENERATOR", sequenceName = "Lev.seq_Abonnement")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ABONNEMENT_SEQUENCE_GENERATOR")
    private Long                        id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "Doelbinding")
    private DoelBinding                 doelBinding;

    @Column(name = "SrtAbonnement")
    private SoortAbonnement             soort;

    @OneToMany(mappedBy = "abonnement", fetch = FetchType.EAGER)
    private Set<AbonnementSoortBericht> soortBerichten;

    @OneToMany(mappedBy = "abonnement", fetch = FetchType.EAGER)
    private Set<AbonnementGegevensElement> abonnementGegevensElementen;

    @Column(name = "Populatiecriterium")
    private String                      populatieCriterium;

    @Column(name = "AbonnementStatusHis")
    private StatusHistorie              statusHistorie;

    /**
     * No-arg constructor voor JPA.
     */
    protected Abonnement() {
    }

    /**
     * Constructor voor nieuwe instanties.
     *
     * @param doelBinding de doelBinding waar dit abonnement op gebaseerd is.
     * @param soort het soort persoon dat wordt gecreeerd.
     */
    public Abonnement(final DoelBinding doelBinding, final SoortAbonnement soort) {
        this.doelBinding = doelBinding;
        this.soort = soort;
        soortBerichten = new HashSet<AbonnementSoortBericht>();
    }

    public Long getId() {
        return id;
    }

    public SoortAbonnement getSoort() {
        return soort;
    }

    public DoelBinding getDoelBinding() {
        return doelBinding;
    }

    public StatusHistorie getStatusHistorie() {
        return statusHistorie;
    }

    public Set<AbonnementSoortBericht> getSoortBerichten() {
        return Collections.unmodifiableSet(soortBerichten);
    }

    public Set<AbonnementGegevensElement> getGegevensElementen() {
        return Collections.unmodifiableSet(abonnementGegevensElementen);
    }

    /**
     * Voegt een {@link SoortBericht} toe aan het abonnement, waardoor de partij behorend bij dit abonnement gerechtigd
     * is dat soort berichten te versturen.
     *
     * @param soortBericht het soort bericht waarvoor dit abonnement geldt.
     */
    public void addSoortBericht(final SoortBericht soortBericht) {
        AbonnementSoortBericht abonnementSoortBericht = new AbonnementSoortBericht(this, soortBericht);
        soortBerichten.add(abonnementSoortBericht);
    }

    /**
     * Het criterium dat éénduidige bepaalt van welke ingeschrevenen de Geautoriseerde partij gegevens via dit
     * Abonnement mag inzien.
     *
     * @return het criterium dat bepaalt van welke ingeschrevenen de Geautoriseerde partij gegevens mag inzien.
     */
    public String getPopulatieCriterium() {
        return populatieCriterium;
    }

}
