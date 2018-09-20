/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.impl.verconv;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.annotation.Generated;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;
import nl.bzk.brp.model.algemeen.attribuuttype.ist.LO3CategorieAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.VolgnummerAttribuut;
import nl.bzk.brp.model.basis.HisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.verconv.LO3VoorkomenHisVolledigBasis;
import nl.bzk.brp.model.operationeel.verconv.LO3VoorkomenMappingGroepModel;

/**
 * HisVolledig klasse voor LO3 Voorkomen.
 *
 */
@Access(value = AccessType.FIELD)
@Generated(value = "nl.bzk.brp.generatoren.java.HisVolledigModelGenerator")
@MappedSuperclass
public abstract class AbstractLO3VoorkomenHisVolledigImpl implements HisVolledigImpl, LO3VoorkomenHisVolledigBasis {

    @Transient
    @JsonProperty
    private Long iD;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LO3Ber")
    @JsonProperty
    private LO3BerichtHisVolledigImpl lO3Bericht;

    @Embedded
    @AttributeOverride(name = LO3CategorieAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "LO3Categorie"))
    @JsonProperty
    private LO3CategorieAttribuut lO3Categorie;

    @Embedded
    @AttributeOverride(name = VolgnummerAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "LO3Stapelvolgnr"))
    @JsonProperty
    private VolgnummerAttribuut lO3Stapelvolgnummer;

    @Embedded
    @AttributeOverride(name = VolgnummerAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "LO3Voorkomenvolgnr"))
    @JsonProperty
    private VolgnummerAttribuut lO3Voorkomenvolgnummer;

    @Embedded
    private LO3VoorkomenMappingGroepModel mapping;

    /**
     * Default contructor voor JPA.
     *
     */
    protected AbstractLO3VoorkomenHisVolledigImpl() {
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param lO3Bericht lO3Bericht van LO3 Voorkomen.
     * @param lO3Categorie lO3Categorie van LO3 Voorkomen.
     * @param lO3Stapelvolgnummer lO3Stapelvolgnummer van LO3 Voorkomen.
     * @param lO3Voorkomenvolgnummer lO3Voorkomenvolgnummer van LO3 Voorkomen.
     */
    public AbstractLO3VoorkomenHisVolledigImpl(
        final LO3BerichtHisVolledigImpl lO3Bericht,
        final LO3CategorieAttribuut lO3Categorie,
        final VolgnummerAttribuut lO3Stapelvolgnummer,
        final VolgnummerAttribuut lO3Voorkomenvolgnummer)
    {
        this();
        this.lO3Bericht = lO3Bericht;
        this.lO3Categorie = lO3Categorie;
        this.lO3Stapelvolgnummer = lO3Stapelvolgnummer;
        this.lO3Voorkomenvolgnummer = lO3Voorkomenvolgnummer;

    }

    /**
     * Retourneert ID van LO3 Voorkomen.
     *
     * @return ID.
     */
    @Id
    @SequenceGenerator(name = "LO3VOORKOMEN", sequenceName = "VerConv.seq_LO3Voorkomen")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "LO3VOORKOMEN")
    @Access(value = AccessType.PROPERTY)
    public Long getID() {
        return iD;
    }

    /**
     * Retourneert LO3 Bericht van LO3 Voorkomen.
     *
     * @return LO3 Bericht.
     */
    public LO3BerichtHisVolledigImpl getLO3Bericht() {
        return lO3Bericht;
    }

    /**
     * Retourneert LO3 categorie van LO3 Voorkomen.
     *
     * @return LO3 categorie.
     */
    public LO3CategorieAttribuut getLO3Categorie() {
        return lO3Categorie;
    }

    /**
     * Retourneert LO3 stapelvolgnummer van LO3 Voorkomen.
     *
     * @return LO3 stapelvolgnummer.
     */
    public VolgnummerAttribuut getLO3Stapelvolgnummer() {
        return lO3Stapelvolgnummer;
    }

    /**
     * Retourneert LO3 voorkomenvolgnummer van LO3 Voorkomen.
     *
     * @return LO3 voorkomenvolgnummer.
     */
    public VolgnummerAttribuut getLO3Voorkomenvolgnummer() {
        return lO3Voorkomenvolgnummer;
    }

    /**
     * Setter is verplicht voor JPA, omdat de Id annotatie op de getter zit. We maken de functie private voor de
     * zekerheid.
     *
     * @param id Id
     */
    private void setID(final Long id) {
        this.iD = id;
    }

    /**
     * Zet Mapping van LO3 Voorkomen.
     *
     * @param mapping Mapping.
     */
    public void setMapping(final LO3VoorkomenMappingGroepModel mapping) {
        this.mapping = mapping;
    }

}
