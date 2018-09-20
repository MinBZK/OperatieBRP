/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AssociationOverride;
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
import nl.bzk.brp.model.algemeen.attribuuttype.kern.SleutelwaardeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.ElementAttribuut;
import nl.bzk.brp.model.basis.AbstractDynamischObject;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.basis.Groep;
import nl.bzk.brp.model.basis.ModelIdentificeerbaar;
import nl.bzk.brp.model.logisch.kern.GegevenInOnderzoek;
import nl.bzk.brp.model.logisch.kern.GegevenInOnderzoekBasis;

/**
 * Gegevens waar onderzoek naar gedaan wordt/naar gedaan is.
 *
 * Bij conversie tussen GBA-V en BRP formaat, maakt men graag gebruik gemaakt van bepaalde standaard ORM software voor
 * het schrijven en lezen van objecten uit de database, in dit geval specifiek voor het vastleggen van (tegelijk) een
 * inhoudelijk gegeven EN het registreren van een indicatie dat dit inhoudelijk gegeven in onderzoek is. Vooralsnog is
 * het gebruik van deze ORM mapping functionaliteit nog niet voorzien in de reguliere bijhouding van de BRP. Besloten is
 * om vooralsnog de "do instead" functionaliteit van Postgres te gebruiken. Hierbij ontstaat dus in essentie een
 * verschil tussen het "op basis van patronen gegenereerde Operationeel model", en het fysieke model zoals gegenereerd
 * met de sql DDL. Mocht in de toekomst blijken dat er meer afwijkingen ontstaan tussen operationeel model en fysiek
 * model, dan kan deze keuze herzien worden. (Met als mogelijke oplossingsrichtingen bijvoorbeeld het opnemen van deze
 * extra structuur in het logisch model, of bijvoorbeeld het expliciet onderkennen van een fysiek model dat NIET alleen
 * op basis van patronen wordt gegenereerd uit het logische model. De hiertoe gegeneerde sql code heeft (o.a.) het
 * volgende commentaar: -- Door het gebruikt van standaard ORM software om het opslaan en lezen van objecten uit de
 * database te realiseren, -- is er voor GegevenInOnderzoek behoefte aan een extra kolom 'TblGegeven' die een 1-op-1
 * mapping representeert -- tussen entiteit classen in de software en een waarde in de TblGegeven kolom in de database.
 * -- Deze kolom voegt geen extra informatie toe ten opzichte van de SrtGegeven kolom, en wordt daarom gerealiseerd --
 * als een 'writeable view' gebaseerd op de tabellen GegevenInOnderzoek en DbObject.
 *
 * Formeel zou er een Logische Identiteit zitten op SrtGegeven en de Object Sleutel / Voorkomen Sleutel. Maar aangezien
 * of de Object Sleutel of Voorkomen Sleutel worden ingevuld, is dit een zinloze UC op de database (de NULL zorgt immers
 * dat dubbele gewoon kunnen worden toegevoegd).
 *
 *
 *
 */
@Access(value = AccessType.FIELD)
@Generated(value = "nl.bzk.brp.generatoren.java.OperationeelModelGenerator")
@MappedSuperclass
public abstract class AbstractGegevenInOnderzoekModel extends AbstractDynamischObject implements GegevenInOnderzoekBasis, ModelIdentificeerbaar<Integer> {

    @Transient
    @JsonProperty
    private Integer iD;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Onderzoek")
    private OnderzoekModel onderzoek;

    @Embedded
    @AssociationOverride(name = ElementAttribuut.WAARDE_VELD_NAAM, joinColumns = @JoinColumn(name = "Element"))
    @JsonProperty
    private ElementAttribuut element;

    @Embedded
    @AttributeOverride(name = SleutelwaardeAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "ObjectSleutelGegeven"))
    @JsonProperty
    private SleutelwaardeAttribuut objectSleutelGegeven;

    @Embedded
    @AttributeOverride(name = SleutelwaardeAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "VoorkomenSleutelGegeven"))
    @JsonProperty
    private SleutelwaardeAttribuut voorkomenSleutelGegeven;

    /**
     * Default lege constructor t.b.v. Hibernate / JPA
     *
     */
    protected AbstractGegevenInOnderzoekModel() {
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param onderzoek onderzoek van Gegeven in onderzoek.
     * @param element element van Gegeven in onderzoek.
     * @param objectSleutelGegeven objectSleutelGegeven van Gegeven in onderzoek.
     * @param voorkomenSleutelGegeven voorkomenSleutelGegeven van Gegeven in onderzoek.
     */
    public AbstractGegevenInOnderzoekModel(
        final OnderzoekModel onderzoek,
        final ElementAttribuut element,
        final SleutelwaardeAttribuut objectSleutelGegeven,
        final SleutelwaardeAttribuut voorkomenSleutelGegeven)
    {
        this();
        this.onderzoek = onderzoek;
        this.element = element;
        this.objectSleutelGegeven = objectSleutelGegeven;
        this.voorkomenSleutelGegeven = voorkomenSleutelGegeven;

    }

    /**
     * Copy constructor om vanuit het bericht model een instantie van het operationeel model te maken.
     *
     * @param gegevenInOnderzoek Te kopieren object type.
     * @param onderzoek Bijbehorende Onderzoek.
     */
    public AbstractGegevenInOnderzoekModel(final GegevenInOnderzoek gegevenInOnderzoek, final OnderzoekModel onderzoek) {
        this();
        this.onderzoek = onderzoek;
        this.element = gegevenInOnderzoek.getElement();
        this.objectSleutelGegeven = gegevenInOnderzoek.getObjectSleutelGegeven();
        this.voorkomenSleutelGegeven = gegevenInOnderzoek.getVoorkomenSleutelGegeven();

    }

    /**
     * Retourneert ID van Gegeven in onderzoek.
     *
     * @return ID.
     */
    @Id
    @SequenceGenerator(name = "GEGEVENINONDERZOEK", sequenceName = "Kern.seq_GegevenInOnderzoek")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "GEGEVENINONDERZOEK")
    @Access(value = AccessType.PROPERTY)
    public Integer getID() {
        return iD;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OnderzoekModel getOnderzoek() {
        return onderzoek;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ElementAttribuut getElement() {
        return element;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SleutelwaardeAttribuut getObjectSleutelGegeven() {
        return objectSleutelGegeven;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SleutelwaardeAttribuut getVoorkomenSleutelGegeven() {
        return voorkomenSleutelGegeven;
    }

    /**
     * Setter is verplicht voor JPA, omdat de Id annotatie op de getter zit. We maken de functie private voor de
     * zekerheid.
     *
     * @param id Id
     */
    private void setID(final Integer id) {
        this.iD = id;
    }

    /**
     * Geeft alle groepen van de object met uitzondering van groepen die null zijn.
     *
     * @return Lijst met groepen.
     */
    public List<Groep> getGroepen() {
        final List<Groep> groepen = new ArrayList<>();
        return groepen;
    }

    /**
     * Geeft alle attributen van de groep met uitzondering van attributen die null zijn.
     *
     * @return Lijst met attributen van het object.
     */
    public List<Attribuut> getAttributen() {
        final List<Attribuut> attributen = new ArrayList<>();
        if (element != null) {
            attributen.add(element);
        }
        if (objectSleutelGegeven != null) {
            attributen.add(objectSleutelGegeven);
        }
        if (voorkomenSleutelGegeven != null) {
            attributen.add(voorkomenSleutelGegeven);
        }
        return attributen;
    }

}
