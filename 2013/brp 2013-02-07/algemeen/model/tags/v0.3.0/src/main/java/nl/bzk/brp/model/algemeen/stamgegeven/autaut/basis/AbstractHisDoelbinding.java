/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.autaut.basis;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;

import nl.bzk.brp.model.algemeen.attribuuttype.autaut.Populatiecriterium;
import nl.bzk.brp.model.algemeen.attribuuttype.autaut.TekstDoelbinding;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaNee;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Doelbinding;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Protocolleringsniveau;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;


/**
 *
 *
 */
@Access(value = AccessType.FIELD)
@MappedSuperclass
public abstract class AbstractHisDoelbinding {

    @Id
    @SequenceGenerator(name = "HIS_DOELBINDING", sequenceName = "AutAut.seq_His_Doelbinding")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "HIS_DOELBINDING")
    @JsonProperty
    private Integer               iD;

    @ManyToOne
    @JoinColumn(name = "Doelbinding")
    @Fetch(value = FetchMode.JOIN)
    private Doelbinding           doelbinding;

    @Enumerated
    @Column(name = "Protocolleringsniveau")
    @JsonProperty
    private Protocolleringsniveau protocolleringsniveau;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "TekstDoelbinding"))
    @JsonProperty
    private TekstDoelbinding      tekstDoelbinding;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "Populatiecriterium"))
    @JsonProperty
    private Populatiecriterium    populatiecriterium;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "IndVerstrbeperkingHonoreren"))
    @JsonProperty
    private JaNee                 indicatieVerstrekkingsbeperkingHonoreren;

    /**
     * Default constructor t.b.v. JPA
     *
     */
    protected AbstractHisDoelbinding() {
    }

    /**
     * Retourneert ID van His Doelbinding.
     *
     * @return ID.
     */
    public Integer getID() {
        return iD;
    }

    /**
     * Retourneert Doelbinding van His Doelbinding.
     *
     * @return Doelbinding.
     */
    public Doelbinding getDoelbinding() {
        return doelbinding;
    }

    /**
     * Retourneert Protocolleringsniveau van His Doelbinding.
     *
     * @return Protocolleringsniveau.
     */
    public Protocolleringsniveau getProtocolleringsniveau() {
        return protocolleringsniveau;
    }

    /**
     * Retourneert Tekst doelbinding van His Doelbinding.
     *
     * @return Tekst doelbinding.
     */
    public TekstDoelbinding getTekstDoelbinding() {
        return tekstDoelbinding;
    }

    /**
     * Retourneert Populatiecriterium van His Doelbinding.
     *
     * @return Populatiecriterium.
     */
    public Populatiecriterium getPopulatiecriterium() {
        return populatiecriterium;
    }

    /**
     * Retourneert Verstrekkingsbeperking honoreren? van His Doelbinding.
     *
     * @return Verstrekkingsbeperking honoreren?.
     */
    public JaNee getIndicatieVerstrekkingsbeperkingHonoreren() {
        return indicatieVerstrekkingsbeperkingHonoreren;
    }

}
