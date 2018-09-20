/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern.basis;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.StatusHistorie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.basis.AbstractDynamischObjectType;
import nl.bzk.brp.model.logisch.kern.basis.VerstrekkingDerdeBasis;
import nl.bzk.brp.model.operationeel.kern.PersoonModel;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Type;


/**
 * De expliciete toestemming van betrokkene voor het verstrekken van diens persoonsgegevens aan een derde.
 *
 * Ingeschreven personen in de BRP hebben de mogelijkheid om bepaalde categorie�n van afnemers uit te sluiten van het
 * verkrijgen van diens persoonsgegevens. Naast deze 'uitsluiting' is expliciete 'insluiting' ook mogelijk: hierdoor kan
 * een partij die uit oogpunt van de verstrekkingsbeperking uitgesloten zou zijn, alsnog gegevens ontvangen over de
 * persoon.
 *
 *
 *
 */
@Access(value = AccessType.FIELD)
@MappedSuperclass
public abstract class AbstractVerstrekkingDerdeModel extends AbstractDynamischObjectType implements
        VerstrekkingDerdeBasis
{

    @Id
    @SequenceGenerator(name = "VERSTREKKINGDERDE", sequenceName = "Kern.seq_VerstrDerde")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "VERSTREKKINGDERDE")
    @JsonProperty
    private Integer        iD;

    @ManyToOne
    @JoinColumn(name = "Pers")
    @JsonProperty
    private PersoonModel   persoon;

    @ManyToOne
    @JoinColumn(name = "Derde")
    @Fetch(value = FetchMode.JOIN)
    @JsonProperty
    private Partij         derde;

    @Type(type = "StatusHistorie")
    @Column(name = "VerstrDerdeStatusHis")
    @JsonProperty
    private StatusHistorie verstrekkingDerdeStatusHis;

    /**
     * Standaard constructor (t.b.v. Hibernate/JPA).
     *
     */
    protected AbstractVerstrekkingDerdeModel() {
        this.verstrekkingDerdeStatusHis = StatusHistorie.X;

    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param persoon persoon van Verstrekking derde.
     * @param derde derde van Verstrekking derde.
     */
    public AbstractVerstrekkingDerdeModel(final PersoonModel persoon, final Partij derde) {
        this();
        this.persoon = persoon;
        this.derde = derde;

    }

    /**
     * Retourneert ID van Verstrekking derde.
     *
     * @return ID.
     */
    public Integer getID() {
        return iD;
    }

    /**
     * Retourneert Persoon van Verstrekking derde.
     *
     * @return Persoon.
     */
    public PersoonModel getPersoon() {
        return persoon;
    }

    /**
     * Retourneert Derde van Verstrekking derde.
     *
     * @return Derde.
     */
    public Partij getDerde() {
        return derde;
    }

    /**
     * Retourneert Verstrekking derde StatusHis van Verstrekking derde.
     *
     * @return Verstrekking derde StatusHis.
     */
    public StatusHistorie getVerstrekkingDerdeStatusHis() {
        return verstrekkingDerdeStatusHis;
    }

    /**
     * Zet Verstrekking derde StatusHis van Verstrekking derde.
     *
     * @param verstrekkingDerdeStatusHis Verstrekking derde StatusHis.
     */
    public void setVerstrekkingDerdeStatusHis(final StatusHistorie verstrekkingDerdeStatusHis) {
        this.verstrekkingDerdeStatusHis = verstrekkingDerdeStatusHis;
    }

}
