/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.domein.aut;

import java.util.Set;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import nl.bzk.brp.bevraging.domein.Partij;
import nl.bzk.brp.bevraging.domein.SoortPartij;
import nl.bzk.brp.bevraging.domein.StatusHistorie;
import nl.bzk.brp.bevraging.domein.Verantwoordelijke;
import nl.bzk.brp.bevraging.domein.aut.AutorisatieBesluit;
import nl.bzk.brp.bevraging.domein.aut.Toestand;
import org.hibernate.annotations.Where;


/**
 * Een bijhouding in de BRP vindt plaats doordat een Partij expliciet geautoriseerd is om een bepaalde soort bijhouding
 * te doen, voor een bepaalde soort bijhoudingspopulatie. De vastlegging hiervan gebeurt middels de
 * Bijhoudingsautorisatie. De autorisatie kan zijn doordat de Partij zelf geautoriseerd is, doordat de Partij van een
 * Soort partij is die geautoriseerd is. Hierbij dient de Autoriseerderende Partij zelf ook geautoriseerd te zijn.
 */
@Entity
@Table(name = "bijhautorisatie", schema = "autaut")
@Access(value = AccessType.FIELD)
public class BijhoudingsAutorisatie {

    @SequenceGenerator(name = "BIJHAUTORISATIE_SEQUENCE_GENERATOR", sequenceName = "AutAut.seq_Bijhautorisatie")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BIJHAUTORISATIE_SEQUENCE_GENERATOR")
    private Long                     id;
    @ManyToOne
    @JoinColumn(name = "bijhautorisatiebesluit")
    private AutorisatieBesluit       autorisatieBesluit;
    @Column(name = "verantwoordelijke")
    private Verantwoordelijke        verantwoordelijke;
    @Column(name = "srtbijhouding")
    private SoortBijhouding          soortBijhouding;
    @Column(name = "geautoriseerdesrtpartij")
    private SoortPartij              geautoriseerdeSoortPartij;
    @ManyToOne
    @JoinColumn(name = "geautoriseerdepartij")
    private Partij                   geautoriseerdePartij;
    @Column(name = "beperkingpopulatie")
    private BeperkingPopulatie       beperkingPopulatie;
    @Column(name = "toestand")
    private Toestand                 toestand;
    @Column(name = "oms")
    private String                   omschrijving;
    @Column(name = "bijhautorisatiestatushis")
    @Enumerated(EnumType.STRING)
    private StatusHistorie           statusHistorie;
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "bijhautorisatie")
    @Where(clause = "uitgeslotenestatushis='A'")
    private Set<Uitgeslotene>        uitgeslotenen;
    @OneToMany(fetch = FetchType.LAZY)
    @Where(clause = "bijhSituatieStatusHis='A'")
    @JoinColumn(name = "bijhautorisatie")
    private Set<BijhoudingsSituatie> bijhoudingsSituaties;

    /**
     * No-arg constructor.
     */
    public BijhoudingsAutorisatie() {
    }

    public Long getId() {
        return id;
    }

    /**
     * Het autorisatiebesluit wat geleid heeft tot deze Bijhoudings autorisatie.
     *
     * @return Autorisatie besluit dat hoort bij deze bijhoudings autorisatie.
     */
    public AutorisatieBesluit getAutorisatieBesluit() {
        return autorisatieBesluit;
    }

    /**
     * Soort bijhouding waarop deze bijhoudings autorisatie is gebaseerd.
     *
     * @return Soort bijhouding waarvoor deze autorisatie bijhouding geldt.
     */
    public SoortBijhouding getSoortBijhouding() {
        return soortBijhouding;
    }

    /**
     * Het soort partij dat geautoriseerd is door deze bijhoudings autorisatie.
     *
     * @return Soort partij dat geautoriseerd is.
     */
    public SoortPartij getGeautoriseerdeSoortPartij() {
        return geautoriseerdeSoortPartij;
    }

    /**
     * Specifiek Partij wat geautoriseerd is door deze bijhoudings autorisatie.
     *
     * @return Partij dat geautoriseerd is.
     */
    public Partij getGeautoriseerdePartij() {
        return geautoriseerdePartij;
    }

    /**
     * Toestand van deze bijhoudings autorisatie.
     *
     * @return De toestand van deze bijhoudings autorisatie.
     */
    public Toestand getToestand() {
        return toestand;
    }

    /**
     * Korte omschrijving voor deze bijhoudings autorisatie.
     *
     * @return Een omschrijving van deze bijhoudings autorisatie.
     */
    public String getOmschrijving() {
        return omschrijving;
    }

    public StatusHistorie getStatusHistorie() {
        return statusHistorie;
    }

    /**
     * De partijen die uitgesloten zijn van deze Bijhoudings autorisatie.
     *
     * @return Uitgeslotenen van deze bijhoudings autorisatie.
     */
    public Set<Uitgeslotene> getUitgeslotenen() {
        return uitgeslotenen;
    }

    /**
     * De bijhoudingsautorisatie is van toepassing voor bijhoudingen van personen waarvan de verantwoordelijkheid
     * valt onder de verantwoordelijke.
     *
     * @return De voor de bijhouding verantwoordelijke.
     */
    public Verantwoordelijke getVerantwoordelijke() {
        return verantwoordelijke;
    }

    /**
     * De beperking op de populatie waarvoor de Bijhoudingsautorisatie van toepassing is.
     *
     * @return De van toepassing zijnde beperking populatie.
     */
    public BeperkingPopulatie getBeperkingPopulatie() {
        return beperkingPopulatie;
    }

    /**
     * BijhoudingsSituaties die van toepassing zijn op deze BijhoudingsAutorisatie.
     *
     * @return Bijhoudings situaties.
     */
    public Set<BijhoudingsSituatie> getBijhoudingsSituaties() {
        return bijhoudingsSituaties;
    }
}
