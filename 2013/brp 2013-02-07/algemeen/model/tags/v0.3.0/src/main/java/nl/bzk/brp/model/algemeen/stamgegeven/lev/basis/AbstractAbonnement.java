/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.lev.basis;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import nl.bzk.brp.model.algemeen.attribuuttype.autaut.Populatiecriterium;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.StatusHistorie;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Doelbinding;
import nl.bzk.brp.model.algemeen.stamgegeven.lev.SoortAbonnement;
import nl.bzk.brp.model.basis.AbstractStatischObjectType;
import nl.bzk.brp.model.basis.StamgegevenEntityListener;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Type;


/**
 * Een overeenkomst tussen Afnemers van de BRP enerzijds, en de eigenaar van de BRP anderzijds, op basis waarvan de
 * Afnemer persoonsgegevens vanuit de BRP mag ontvangen.
 *
 * Een Abonnement is uiteindelijk gebaseerd op een Autorisatiebesluit, die bepaalt dat de Afnemer gegevens mag
 * ontvangen. Gegevens over de wijze waarop de Afnemer deze gegevens mag ontvangen, welke persoonsgegevens het precies
 * betreft (welk deel van de populatie maar ook wel deel van de gegevens) wordt vastgelegd bij het Abonnement.
 *
 *
 *
 */
@Access(value = AccessType.FIELD)
@EntityListeners(value = StamgegevenEntityListener.class)
@MappedSuperclass
public abstract class AbstractAbonnement extends AbstractStatischObjectType {

    @Id
    @JsonProperty
    private Integer            iD;

    @ManyToOne
    @JoinColumn(name = "Doelbinding")
    @Fetch(value = FetchMode.JOIN)
    private Doelbinding        doelbinding;

    @Column(name = "SrtAbonnement")
    private SoortAbonnement    soortAbonnement;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "Populatiecriterium"))
    private Populatiecriterium populatiecriterium;

    @Type(type = "StatusHistorie")
    @Column(name = "AbonnementStatusHis")
    private StatusHistorie     abonnementStatusHis;

    /**
     * Constructor is protected, want de BRP zal geen stamgegevens beheren.
     *
     */
    protected AbstractAbonnement() {
    }

    /**
     * Constructor die direct alle attributen instantieert.
     *
     * @param doelbinding doelbinding van Abonnement.
     * @param soortAbonnement soortAbonnement van Abonnement.
     * @param populatiecriterium populatiecriterium van Abonnement.
     * @param abonnementStatusHis abonnementStatusHis van Abonnement.
     */
    protected AbstractAbonnement(final Doelbinding doelbinding, final SoortAbonnement soortAbonnement,
            final Populatiecriterium populatiecriterium, final StatusHistorie abonnementStatusHis)
    {
        this.doelbinding = doelbinding;
        this.soortAbonnement = soortAbonnement;
        this.populatiecriterium = populatiecriterium;
        this.abonnementStatusHis = abonnementStatusHis;

    }

    /**
     * Retourneert ID van Abonnement.
     *
     * @return ID.
     */
    protected Integer getID() {
        return iD;
    }

    /**
     * Retourneert Doelbinding van Abonnement.
     *
     * @return Doelbinding.
     */
    public Doelbinding getDoelbinding() {
        return doelbinding;
    }

    /**
     * Retourneert Soort abonnement van Abonnement.
     *
     * @return Soort abonnement.
     */
    public SoortAbonnement getSoortAbonnement() {
        return soortAbonnement;
    }

    /**
     * Retourneert Populatiecriterium van Abonnement.
     *
     * @return Populatiecriterium.
     */
    public Populatiecriterium getPopulatiecriterium() {
        return populatiecriterium;
    }

    /**
     * Retourneert Abonnement StatusHis van Abonnement.
     *
     * @return Abonnement StatusHis.
     */
    public StatusHistorie getAbonnementStatusHis() {
        return abonnementStatusHis;
    }

}
