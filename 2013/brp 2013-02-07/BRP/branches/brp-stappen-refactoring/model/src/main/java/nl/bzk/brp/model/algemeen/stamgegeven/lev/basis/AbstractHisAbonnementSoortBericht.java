/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.lev.basis;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;

import nl.bzk.brp.model.algemeen.stamgegeven.lev.AbonnementSoortBericht;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;


/**
 *
 *
 * Generator: nl.bzk.brp.generatoren.java.OperationeelModelGenerator.
 * Metaregister versie: 1.6.0.
 * Generator versie: 1.0-SNAPSHOT.
 * Generator gebouwd op: 2013-01-21 13:38:20.
 * Gegenereerd op: Mon Jan 21 13:42:15 CET 2013.
 */
@Access(value = AccessType.FIELD)
@MappedSuperclass
public abstract class AbstractHisAbonnementSoortBericht {

    @Id
    @SequenceGenerator(name = "HIS_ABONNEMENTSOORTBERICHT", sequenceName = "Lev.seq_His_AbonnementSrtBer")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "HIS_ABONNEMENTSOORTBERICHT")
    @JsonProperty
    private Integer                iD;

    @ManyToOne
    @JoinColumn(name = "AbonnementSrtBer")
    @Fetch(value = FetchMode.JOIN)
    private AbonnementSoortBericht abonnementSoortBericht;

    /**
     * Default constructor t.b.v. JPA
     *
     */
    protected AbstractHisAbonnementSoortBericht() {
    }

    /**
     * Retourneert ID van His Abonnement \ Soort bericht.
     *
     * @return ID.
     */
    public Integer getID() {
        return iD;
    }

    /**
     * Retourneert Abonnement \ Soort bericht van His Abonnement \ Soort bericht.
     *
     * @return Abonnement \ Soort bericht.
     */
    public AbonnementSoortBericht getAbonnementSoortBericht() {
        return abonnementSoortBericht;
    }

}
