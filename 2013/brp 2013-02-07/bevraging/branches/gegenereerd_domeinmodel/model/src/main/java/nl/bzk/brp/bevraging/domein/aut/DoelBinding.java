/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.domein.aut;

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

import nl.bzk.brp.bevraging.domein.Partij;
import nl.bzk.brp.bevraging.domein.lev.Abonnement;
import nl.bzk.brp.bevraging.domein.lev.SoortAbonnement;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;


/**
 * Het verband tussen enerzijds de welbepaalde, uitdrukkelijk omschreven en gerechtvaardigde taak van een Partij en
 * anderzijds de daarvoor door de BRP aan die Partij te verstrekken gegevens.
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "Doelbinding", schema = "AutAut")
@Access(AccessType.FIELD)
public class DoelBinding implements Serializable {

    private static final String SEQUENCE_GENERATOR_NAME = "DOELBINDING_SEQUENCE_GENERATOR";

    @SequenceGenerator(name = SEQUENCE_GENERATOR_NAME, sequenceName = "AutAut.seq_Doelbinding")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQUENCE_GENERATOR_NAME)
    private Long                  id;

    @ManyToOne
    @JoinColumn(name = "Levsautorisatiebesluit", insertable = false, updatable = false)
    private AutorisatieBesluit    leveringsAutorisatieBesluit;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "Geautoriseerde", insertable = false, updatable = false)
    private Partij                geautoriseerde;

    @Column(name = "Protocolleringsniveau")
    private ProtocolleringsNiveau protocolleringsNiveau;

    @Column(name = "TekstDoelbinding")
    private String                tekstDoelbinding;

    @Column(name = "Populatiecriterium")
    private String                populatieCriterium;

    @Column(name = "IndVerstrbeperkingHonoreren")
    private Boolean               verstrekkingsBeperkingHonoreren;

    @OneToMany(mappedBy = "doelBinding", fetch = FetchType.EAGER)
    private Set<Abonnement>       abonnementen = new HashSet<Abonnement>();

    /**
     * No-arg constructor voor JPA.
     */
    protected DoelBinding() {
    }

    /**
     * Constructor voor programmatische instantiatie, met verplichte attributen.
     *
     * @param leveringsAutorisatieBesluit het autorisatiebesluit waar deze doelbinding op is gebaseerd.
     * @param geautoriseerde de partij die middels deze doelbinding wordt geautoriseerd.
     */
    public DoelBinding(final AutorisatieBesluit leveringsAutorisatieBesluit, final Partij geautoriseerde) {
        super();
        this.leveringsAutorisatieBesluit = leveringsAutorisatieBesluit;
        this.geautoriseerde = geautoriseerde;
    }

    public Long getId() {
        return id;
    }

    /**
     * Het Autorisatiebesluit waar de Doelbinding uit voortvloeit.
     *
     * Een Autorisatiebesluit ten behoeve van het leveren van gegevens ('Leveringsautorisatiebesluit') leidt tot
     * normaliter tot één of meer Doelbindingen. Het betreft hier de verwijzing waarop de Doelbinding is gebaseerd.
     *
     * @return het Autorisatiebesluit waar de Doelbinding uit voortvloeit.
     */
    public AutorisatieBesluit getLeveringsAutorisatieBesluit() {
        return leveringsAutorisatieBesluit;
    }

    /**
     * De Partij, waarop de Doelbinding van toepassing is.
     * De Doelbinding bepaalt de 'speelruimte' waarbinnen de Partij (lees:afnemer van gegevens van de BRP) dient te
     * blijven.
     *
     * @return de Partij, waarop de Doelbinding van toepassing is.
     */
    public Partij getGeautoriseerde() {
        return geautoriseerde;
    }

    /**
     * Het niveau van protocolleren.
     * Bij het leveren van gegevens aan een afnemer, wordt dit normaliter geprotocolleerd, en krijgt de burger, op
     * verzoek, toegang tot deze gegevens. Zo kan de burger achterhalen wie welke gegevens heeft gekregen uit de BRP.
     * In bepaalde gevallen mag de burger NIET worden geïnformeerd worden over deze gegevens, en/of hebben niet alle
     * gemeentefunctionarissen toegang tot deze informatie. Dit uit zich in verschillende protocolleringsniveaus.
     *
     * @return het niveau van protocolleren.
     */
    public ProtocolleringsNiveau getProtocolleringsNiveau() {
        return protocolleringsNiveau;
    }

    /**
     * De tekst, afgeleid uit het bijbehorende Autorisatiebesluit, waarmee wordt beschreven wat de Doelbinding omhelst.
     * Het betreft een door de mens leesbare tekst, die aanleiding is c.q. aanleiding is geweest om de overige gegevens
     * rondom de Doelbinding vast te leggen. Zo zullen de eventueel aanwezige Populatiecriteria gebaseerd zijn op de
     * Doelbinding tekst.
     *
     * @return de tekst waarmee wordt beschreven wat de Doelbinding omhelst.
     */
    public String getTekstDoelbinding() {
        return tekstDoelbinding;
    }

    /**
     * Het criterium dat éénduidig bepaalt van welke ingeschrevenen de Geautoriseerde partij gegevens mag inzien.
     * Dit kán zo eenvoudig zijn als een opsomming van BSN's, maar mag ook complexer zijn, zoals alle personen van
     * een bepaald geslacht en een bepaalde leeftijd woonachtig in een bepaald regio.
     *
     * @return het criterium dat bepaalt van welke ingeschrevenen de Geautoriseerde partij gegevens mag inzien.
     */
    public String getPopulatieCriterium() {
        return populatieCriterium;
    }

    /**
     * Het antwoord op de vraag of een eventuele verstrekkingsbeperking, zoals geregistreerd bij een persoon, invloed
     * heeft op het verstrekken van gegevens in kader van Abonnementen gebaseerd op het onderhavige Doelbinding.
     *
     * @return het antwoord op bovenbeschreven vraag.
     */
    public Boolean isVerstrekkingsBeperkingHonoreren() {
        return verstrekkingsBeperkingHonoreren;
    }

    /**
     * De abonnementen die gebaseerd zijn op deze doelbinding.
     *
     * @return een {@link Set} van abonnementen die gebaseerd zijn op deze doelbinding.
     */
    public Set<Abonnement> getAbonnementen() {
        if (abonnementen == null) {
            return null;
        }
        return Collections.unmodifiableSet(abonnementen);
    }

    /**
     * Creëer een abonnement bij deze doelbinding.
     *
     * @param soortAbonnement het soort abonnement dat gecreëerd wordt.
     * @return het abonnement dat gecreëerd is.
     */
    public Abonnement createAbonnement(final SoortAbonnement soortAbonnement) {
        if (soortAbonnement == null) {
            throw new IllegalArgumentException("soortAbonnement mag niet <null> zijn");
        }
        Abonnement resultaat = new Abonnement(this, soortAbonnement);
        abonnementen.add(resultaat);
        return resultaat;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("id", id)
                .append("geautoriseerde", geautoriseerde).append("protocolleringsNiveau", protocolleringsNiveau)
                .append("populatieCriterium", populatieCriterium).toString();
    }

}
