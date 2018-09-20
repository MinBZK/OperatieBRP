/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.lev.basis;

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

import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijd;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Authenticatiemiddel;
import nl.bzk.brp.model.algemeen.stamgegeven.lev.Abonnement;
import nl.bzk.brp.model.algemeen.stamgegeven.lev.SoortLevering;
import nl.bzk.brp.model.basis.AbstractDynamischObjectType;
import nl.bzk.brp.model.logisch.lev.Levering;
import nl.bzk.brp.model.logisch.lev.basis.LeveringBasis;
import nl.bzk.brp.model.operationeel.ber.BerichtModel;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;


/**
 * De (voorgenomen) Levering van persoonsgegevens aan een Afnemer.
 *
 * Een Afnemer krijgt gegevens doordat er sprake is van een Abonnement. Vlak voordat de gegevens daadwerkelijk
 * afgeleverd gaan worden, wordt dit geprotocolleerd door een regel weg te schrijven in de Levering tabel.
 *
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
public abstract class AbstractLeveringModel extends AbstractDynamischObjectType implements LeveringBasis {

    @Id
    @SequenceGenerator(name = "LEVERING", sequenceName = "Lev.seq_Lev")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "LEVERING")
    @JsonProperty
    private Long                iD;

    @Enumerated
    @Column(name = "Srt")
    @JsonProperty
    private SoortLevering       soort;

    @ManyToOne
    @JoinColumn(name = "Authenticatiemiddel")
    @Fetch(value = FetchMode.JOIN)
    @JsonProperty
    private Authenticatiemiddel authenticatiemiddel;

    @ManyToOne
    @JoinColumn(name = "Abonnement")
    @Fetch(value = FetchMode.JOIN)
    @JsonProperty
    private Abonnement          abonnement;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "TsBesch"))
    @JsonProperty
    private DatumTijd           datumTijdBeschouwing;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "TsKlaarzettenLev"))
    @JsonProperty
    private DatumTijd           datumTijdKlaarzettenLevering;

    @ManyToOne
    @JoinColumn(name = "GebaseerdOp")
    @JsonProperty
    private BerichtModel        gebaseerdOp;

    /**
     * Standaard constructor (t.b.v. Hibernate/JPA).
     *
     */
    protected AbstractLeveringModel() {
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param soort soort van Levering.
     * @param authenticatiemiddel authenticatiemiddel van Levering.
     * @param abonnement abonnement van Levering.
     * @param datumTijdBeschouwing datumTijdBeschouwing van Levering.
     * @param datumTijdKlaarzettenLevering datumTijdKlaarzettenLevering van Levering.
     * @param gebaseerdOp gebaseerdOp van Levering.
     */
    public AbstractLeveringModel(final SoortLevering soort, final Authenticatiemiddel authenticatiemiddel,
            final Abonnement abonnement, final DatumTijd datumTijdBeschouwing,
            final DatumTijd datumTijdKlaarzettenLevering, final BerichtModel gebaseerdOp)
    {
        this();
        this.soort = soort;
        this.authenticatiemiddel = authenticatiemiddel;
        this.abonnement = abonnement;
        this.datumTijdBeschouwing = datumTijdBeschouwing;
        this.datumTijdKlaarzettenLevering = datumTijdKlaarzettenLevering;
        this.gebaseerdOp = gebaseerdOp;

    }

    /**
     * Retourneert ID van Levering.
     *
     * @return ID.
     */
    public Long getID() {
        return iD;
    }

    /**
     * Retourneert Soort van Levering.
     *
     * @return Soort.
     */
    public SoortLevering getSoort() {
        return soort;
    }

    /**
     * Retourneert Authenticatiemiddel van Levering.
     *
     * @return Authenticatiemiddel.
     */
    public Authenticatiemiddel getAuthenticatiemiddel() {
        return authenticatiemiddel;
    }

    /**
     * Retourneert Abonnement van Levering.
     *
     * @return Abonnement.
     */
    public Abonnement getAbonnement() {
        return abonnement;
    }

    /**
     * Retourneert Datum/tijd beschouwing van Levering.
     *
     * @return Datum/tijd beschouwing.
     */
    public DatumTijd getDatumTijdBeschouwing() {
        return datumTijdBeschouwing;
    }

    /**
     * Retourneert Datum/tijd klaarzetten levering van Levering.
     *
     * @return Datum/tijd klaarzetten levering.
     */
    public DatumTijd getDatumTijdKlaarzettenLevering() {
        return datumTijdKlaarzettenLevering;
    }

    /**
     * Retourneert Gebaseerd op van Levering.
     *
     * @return Gebaseerd op.
     */
    public BerichtModel getGebaseerdOp() {
        return gebaseerdOp;
    }

}
