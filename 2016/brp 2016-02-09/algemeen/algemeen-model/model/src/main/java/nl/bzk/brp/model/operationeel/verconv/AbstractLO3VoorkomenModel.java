/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.verconv;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;
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
import nl.bzk.brp.model.basis.AbstractDynamischObject;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.basis.Groep;
import nl.bzk.brp.model.basis.ModelIdentificeerbaar;
import nl.bzk.brp.model.logisch.verconv.LO3VoorkomenBasis;

/**
 * Een voorkomen (of het ontbreken daarvan) van een
 *
 * Een LO3 bericht bevat één of meer categorieën, sommige daarvan repeterend (te zien aan LO3 stapelvolgnummer); elke
 * (repetitie van een) categorië bevat één of meer voorkomens. In gevallen waarbij het LO3 bericht een voorkomen hád
 * moeten hebben, maar dit niet heeft, wordt dit ontbrekende voorkomen hier alsnog vastgelegd, zodat een LO3 melding
 * hierover kan worden vastgelegd.
 *
 *
 *
 */
@Access(value = AccessType.FIELD)
@Generated(value = "nl.bzk.brp.generatoren.java.OperationeelModelGenerator")
@MappedSuperclass
public abstract class AbstractLO3VoorkomenModel extends AbstractDynamischObject implements LO3VoorkomenBasis, ModelIdentificeerbaar<Long> {

    @Transient
    @JsonProperty
    private Long iD;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LO3Ber")
    @JsonProperty
    private LO3BerichtModel lO3Bericht;

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
    @JsonProperty
    private LO3VoorkomenMappingGroepModel mapping;

    /**
     * Default lege constructor t.b.v. Hibernate / JPA
     *
     */
    protected AbstractLO3VoorkomenModel() {
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param lO3Bericht lO3Bericht van LO3 Voorkomen.
     * @param lO3Categorie lO3Categorie van LO3 Voorkomen.
     * @param lO3Stapelvolgnummer lO3Stapelvolgnummer van LO3 Voorkomen.
     * @param lO3Voorkomenvolgnummer lO3Voorkomenvolgnummer van LO3 Voorkomen.
     */
    public AbstractLO3VoorkomenModel(
        final LO3BerichtModel lO3Bericht,
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
     * {@inheritDoc}
     */
    @Override
    public LO3BerichtModel getLO3Bericht() {
        return lO3Bericht;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LO3CategorieAttribuut getLO3Categorie() {
        return lO3Categorie;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VolgnummerAttribuut getLO3Stapelvolgnummer() {
        return lO3Stapelvolgnummer;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VolgnummerAttribuut getLO3Voorkomenvolgnummer() {
        return lO3Voorkomenvolgnummer;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LO3VoorkomenMappingGroepModel getMapping() {
        return mapping;
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

    /**
     * Geeft alle groepen van de object met uitzondering van groepen die null zijn.
     *
     * @return Lijst met groepen.
     */
    public List<Groep> getGroepen() {
        final List<Groep> groepen = new ArrayList<>();
        if (mapping != null) {
            groepen.add(mapping);
        }
        return groepen;
    }

    /**
     * Geeft alle attributen van de groep met uitzondering van attributen die null zijn.
     *
     * @return Lijst met attributen van het object.
     */
    public List<Attribuut> getAttributen() {
        final List<Attribuut> attributen = new ArrayList<>();
        if (lO3Categorie != null) {
            attributen.add(lO3Categorie);
        }
        if (lO3Stapelvolgnummer != null) {
            attributen.add(lO3Stapelvolgnummer);
        }
        if (lO3Voorkomenvolgnummer != null) {
            attributen.add(lO3Voorkomenvolgnummer);
        }
        return attributen;
    }

}
