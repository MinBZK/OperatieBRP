/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.domein.lev;

import java.io.Serializable;
import java.util.Calendar;

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

import nl.bzk.brp.bevraging.domein.aut.AuthenticatieMiddel;


/**
 * Representeert een levering.
 */
@Entity
@Table(name = "lev", schema = "lev")
@Access(AccessType.FIELD)
public class Levering implements Serializable {

    @SequenceGenerator(name = "LEV_SEQUENCE_GENERATOR", sequenceName = "lev.seq_lev")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "LEV_SEQUENCE_GENERATOR")
    private Long                id;

    @Column(name = "srt")
    private LeveringSoort       soort;

    @ManyToOne
    @JoinColumn(name = "abonnement")
    private Abonnement          abonnement;

    @ManyToOne
    @JoinColumn(name = "authenticatiemiddel")
    private AuthenticatieMiddel authenticatieMiddel;

    @Column(name = "GebaseerdOp")
    private Long                gebaseerdOp;

    @Column(name = "tsbesch")
    private Calendar            beschouwing;

    @Column(name = "Tsklaarzettenlev")
    private Calendar            levering;

    public LeveringSoort getSoort() {
        return soort;
    }

    public void setSoort(final LeveringSoort soort) {
        this.soort = soort;
    }

    public Abonnement getAbonnement() {
        return abonnement;
    }

    public void setAbonnement(final Abonnement abonnement) {
        this.abonnement = abonnement;
    }

    public AuthenticatieMiddel getAuthenticatieMiddel() {
        return authenticatieMiddel;
    }

    public void setAuthenticatieMiddel(final AuthenticatieMiddel authenticatieMiddel) {
        this.authenticatieMiddel = authenticatieMiddel;
    }

    public Calendar getLevering() {
        return levering;
    }

    public void setLevering(final Calendar levering) {
        this.levering = levering;
    }

    public Long getId() {
        return id;
    }

    public Calendar getBeschouwing() {
        return beschouwing;
    }

    public void setBeschouwing(final Calendar beschouwing) {
        this.beschouwing = beschouwing;
    }

    /**
     * De id van het (inkomende) bericht waarop deze levering betrekking heeft.
     * @return id van het (inkomende) bericht waarop deze levering betrekking heeft.
     */
    public Long getGebaseerdOp() {
        return gebaseerdOp;
    }

    /**
     * Zet de id van het (inkomende) bericht waarop deze levering betrekking heeft.
     * @param gebaseerdOp de id van het (inkomende) bericht waarop deze levering betrekking heeft.
     */
    public void setGebaseerdOp(final Long gebaseerdOp) {
        this.gebaseerdOp = gebaseerdOp;
    }

}
