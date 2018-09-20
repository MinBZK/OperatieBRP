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
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import nl.bzk.brp.model.algemeen.attribuuttype.autaut.Populatiecriterium;
import nl.bzk.brp.model.algemeen.attribuuttype.autaut.TekstDoelbinding;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaNee;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.StatusHistorie;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Autorisatiebesluit;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Protocolleringsniveau;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.basis.AbstractStatischObjectType;
import nl.bzk.brp.model.basis.StamgegevenEntityListener;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Type;


/**
 * Het verband tussen enerzijds de welbepaalde, uitdrukkelijk omschreven en gerechtvaardigde taak van een Partij en
 * anderzijds de daarvoor door de BRP aan die Partij te verstrekken gegevens.
 *
 *
 *
 */
@Access(value = AccessType.FIELD)
@EntityListeners(value = StamgegevenEntityListener.class)
@MappedSuperclass
public abstract class AbstractDoelbinding extends AbstractStatischObjectType {

    @Id
    @JsonProperty
    private Integer               iD;

    @ManyToOne
    @JoinColumn(name = "Levsautorisatiebesluit")
    @Fetch(value = FetchMode.JOIN)
    private Autorisatiebesluit    leveringsautorisatiebesluit;

    @ManyToOne
    @JoinColumn(name = "Geautoriseerde")
    @Fetch(value = FetchMode.JOIN)
    private Partij                geautoriseerde;

    @Column(name = "Protocolleringsniveau")
    private Protocolleringsniveau protocolleringsniveau;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "TekstDoelbinding"))
    private TekstDoelbinding      tekstDoelbinding;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "Populatiecriterium"))
    private Populatiecriterium    populatiecriterium;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "IndVerstrbeperkingHonoreren"))
    private JaNee                 indicatieVerstrekkingsbeperkingHonoreren;

    @Type(type = "StatusHistorie")
    @Column(name = "DoelbindingStatusHis")
    private StatusHistorie        doelbindingStatusHis;

    /**
     * Constructor is protected, want de BRP zal geen stamgegevens beheren.
     *
     */
    protected AbstractDoelbinding() {
    }

    /**
     * Constructor die direct alle attributen instantieert.
     *
     * @param leveringsautorisatiebesluit leveringsautorisatiebesluit van Doelbinding.
     * @param geautoriseerde geautoriseerde van Doelbinding.
     * @param protocolleringsniveau protocolleringsniveau van Doelbinding.
     * @param tekstDoelbinding tekstDoelbinding van Doelbinding.
     * @param populatiecriterium populatiecriterium van Doelbinding.
     * @param indicatieVerstrekkingsbeperkingHonoreren indicatieVerstrekkingsbeperkingHonoreren van Doelbinding.
     * @param doelbindingStatusHis doelbindingStatusHis van Doelbinding.
     */
    protected AbstractDoelbinding(final Autorisatiebesluit leveringsautorisatiebesluit, final Partij geautoriseerde,
            final Protocolleringsniveau protocolleringsniveau, final TekstDoelbinding tekstDoelbinding,
            final Populatiecriterium populatiecriterium, final JaNee indicatieVerstrekkingsbeperkingHonoreren,
            final StatusHistorie doelbindingStatusHis)
    {
        this.leveringsautorisatiebesluit = leveringsautorisatiebesluit;
        this.geautoriseerde = geautoriseerde;
        this.protocolleringsniveau = protocolleringsniveau;
        this.tekstDoelbinding = tekstDoelbinding;
        this.populatiecriterium = populatiecriterium;
        this.indicatieVerstrekkingsbeperkingHonoreren = indicatieVerstrekkingsbeperkingHonoreren;
        this.doelbindingStatusHis = doelbindingStatusHis;

    }

    /**
     * Retourneert ID van Doelbinding.
     *
     * @return ID.
     */
    protected Integer getID() {
        return iD;
    }

    /**
     * Retourneert Leveringsautorisatiebesluit van Doelbinding.
     *
     * @return Leveringsautorisatiebesluit.
     */
    public Autorisatiebesluit getLeveringsautorisatiebesluit() {
        return leveringsautorisatiebesluit;
    }

    /**
     * Retourneert Geautoriseerde van Doelbinding.
     *
     * @return Geautoriseerde.
     */
    public Partij getGeautoriseerde() {
        return geautoriseerde;
    }

    /**
     * Retourneert Protocolleringsniveau van Doelbinding.
     *
     * @return Protocolleringsniveau.
     */
    public Protocolleringsniveau getProtocolleringsniveau() {
        return protocolleringsniveau;
    }

    /**
     * Retourneert Tekst doelbinding van Doelbinding.
     *
     * @return Tekst doelbinding.
     */
    public TekstDoelbinding getTekstDoelbinding() {
        return tekstDoelbinding;
    }

    /**
     * Retourneert Populatiecriterium van Doelbinding.
     *
     * @return Populatiecriterium.
     */
    public Populatiecriterium getPopulatiecriterium() {
        return populatiecriterium;
    }

    /**
     * Retourneert Verstrekkingsbeperking honoreren? van Doelbinding.
     *
     * @return Verstrekkingsbeperking honoreren?.
     */
    public JaNee getIndicatieVerstrekkingsbeperkingHonoreren() {
        return indicatieVerstrekkingsbeperkingHonoreren;
    }

    /**
     * Retourneert Doelbinding StatusHis van Doelbinding.
     *
     * @return Doelbinding StatusHis.
     */
    public StatusHistorie getDoelbindingStatusHis() {
        return doelbindingStatusHis;
    }

}
