/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.lev.basis;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.StatusHistorie;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortBericht;
import nl.bzk.brp.model.algemeen.stamgegeven.lev.Abonnement;
import nl.bzk.brp.model.basis.AbstractStatischObjectType;
import nl.bzk.brp.model.basis.StamgegevenEntityListener;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Type;


/**
 * Toegestaan berichtsoort in kader van een Abonnement.
 *
 * Door middel van het opsommen van de Soorten bericht die mogelijk zijn bij een Abonnement, wordt invulling gegeven aan
 * de behoefte om de relevante services die worden gestart naar aanleiding van een Abonnement.
 *
 *
 *
 */
@Access(value = AccessType.FIELD)
@EntityListeners(value = StamgegevenEntityListener.class)
@MappedSuperclass
public abstract class AbstractAbonnementSoortBericht extends AbstractStatischObjectType {

    @Id
    @JsonProperty
    private Integer        iD;

    @ManyToOne
    @JoinColumn(name = "Abonnement")
    @Fetch(value = FetchMode.JOIN)
    private Abonnement     abonnement;

    @Column(name = "SrtBer")
    private SoortBericht   soortBericht;

    @Type(type = "StatusHistorie")
    @Column(name = "AbonnementSrtBerStatusHis")
    private StatusHistorie abonnementSoortBerichtStatusHis;

    /**
     * Constructor is protected, want de BRP zal geen stamgegevens beheren.
     *
     */
    protected AbstractAbonnementSoortBericht() {
    }

    /**
     * Constructor die direct alle attributen instantieert.
     *
     * @param abonnement abonnement van AbonnementSoortBericht.
     * @param soortBericht soortBericht van AbonnementSoortBericht.
     * @param abonnementSoortBerichtStatusHis abonnementSoortBerichtStatusHis van AbonnementSoortBericht.
     */
    protected AbstractAbonnementSoortBericht(final Abonnement abonnement, final SoortBericht soortBericht,
            final StatusHistorie abonnementSoortBerichtStatusHis)
    {
        this.abonnement = abonnement;
        this.soortBericht = soortBericht;
        this.abonnementSoortBerichtStatusHis = abonnementSoortBerichtStatusHis;

    }

    /**
     * Retourneert ID van Abonnement \ Soort bericht.
     *
     * @return ID.
     */
    protected Integer getID() {
        return iD;
    }

    /**
     * Retourneert Abonnement van Abonnement \ Soort bericht.
     *
     * @return Abonnement.
     */
    public Abonnement getAbonnement() {
        return abonnement;
    }

    /**
     * Retourneert Soort bericht van Abonnement \ Soort bericht.
     *
     * @return Soort bericht.
     */
    public SoortBericht getSoortBericht() {
        return soortBericht;
    }

    /**
     * Retourneert Abonnement \ Soort bericht StatusHis van Abonnement \ Soort bericht.
     *
     * @return Abonnement \ Soort bericht StatusHis.
     */
    public StatusHistorie getAbonnementSoortBerichtStatusHis() {
        return abonnementSoortBerichtStatusHis;
    }

}
