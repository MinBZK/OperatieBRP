/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.lev.basis;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import nl.bzk.brp.model.algemeen.stamgegeven.kern.DatabaseObject;
import nl.bzk.brp.model.algemeen.stamgegeven.lev.Abonnement;
import nl.bzk.brp.model.basis.AbstractStatischObjectType;
import nl.bzk.brp.model.basis.StamgegevenEntityListener;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;


/**
 * Het voorkomen van een gegevenselement in een Abonnement.
 *
 * Bij het Abonnement wordt vastgelegd welke Gegevenselementen in het kader van het Abonnement geraakt mogen worden. Het
 * betreft een deelverzameling van de Gegevenselement doelbinding.
 *
 * 1. Bij de naamskeuze van het objecttype sluiten we aan bij de standaard, en dus kiezen we niet voor de naam
 * "Gegevenselement [van het] Abonnement".
 *
 * 2. Bij de naam '(Gegevens)Element' is de vraag welke elementen worden bedoeld: zijn dit de LOGISCHE elementen, of de
 * representanten hiervan in de database, de DATABASE OBJECTEN.
 * Voor een aantal objecttypen is de hypothese waaronder gewerkt wordt dat het de DATABASE OBJECTEN zijn. Om deze
 * duidelijk te kunnen scheiden van (andere) Elementen, hebben deze een aparte naam gekregen: Databaseobject.
 * In de verwijzing van het attribuut gebruiken we echter nog de naam 'Element': een Databaseobject zou immers kunnen
 * worden beschouwd als een specialisatie van Element. Alleen is die specialisatie in het model niet uitgemodelleerd.
 * RvdP 16 november 2011.
 *
 *
 *
 * Generator: nl.bzk.brp.generatoren.java.DynamischeStamgegevensGenerator.
 * Metaregister versie: 1.6.0.
 * Generator versie: 1.0-SNAPSHOT.
 * Generator gebouwd op: 2013-01-15 12:43:16.
 * Gegenereerd op: Tue Jan 15 12:53:51 CET 2013.
 */
@Access(value = AccessType.FIELD)
@EntityListeners(value = StamgegevenEntityListener.class)
@MappedSuperclass
public abstract class AbstractAbonnementGegevenselement extends AbstractStatischObjectType {

    @Id
    private Integer        iD;

    @ManyToOne
    @JoinColumn(name = "Abonnement")
    @Fetch(value = FetchMode.JOIN)
    private Abonnement     abonnement;

    @Column(name = "Gegevenselement")
    private DatabaseObject gegevenselement;

    /**
     * Constructor is protected, want de BRP zal geen stamgegevens beheren.
     *
     */
    protected AbstractAbonnementGegevenselement() {
    }

    /**
     * Constructor die direct alle attributen instantieert.
     *
     * @param abonnement abonnement van AbonnementGegevenselement.
     * @param gegevenselement gegevenselement van AbonnementGegevenselement.
     */
    protected AbstractAbonnementGegevenselement(final Abonnement abonnement, final DatabaseObject gegevenselement) {
        this.abonnement = abonnement;
        this.gegevenselement = gegevenselement;

    }

    /**
     * Retourneert ID van Abonnement \ Gegevenselement.
     *
     * @return ID.
     */
    protected Integer getID() {
        return iD;
    }

    /**
     * Retourneert Abonnement van Abonnement \ Gegevenselement.
     *
     * @return Abonnement.
     */
    public Abonnement getAbonnement() {
        return abonnement;
    }

    /**
     * Retourneert Gegevenselement van Abonnement \ Gegevenselement.
     *
     * @return Gegevenselement.
     */
    public DatabaseObject getGegevenselement() {
        return gegevenselement;
    }

}
