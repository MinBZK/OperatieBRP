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
import javax.persistence.Embedded;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortDocumentAttribuut;
import nl.bzk.brp.model.basis.AbstractDynamischObject;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.basis.Groep;
import nl.bzk.brp.model.basis.ModelIdentificeerbaar;
import nl.bzk.brp.model.logisch.kern.Document;
import nl.bzk.brp.model.logisch.kern.DocumentBasis;

/**
 * Het Brondocument op basis waarvan bijhouding heeft plaatsgevonden.
 *
 * Bijhouding in de BRP vindt vaak plaats naar aanleiding van ontlening uit brondocumenten. Dit kunnen akten zijn, maar
 * zijn dat niet altijd. Vanuit een BRP Actie zijn de documenten - waaraan de gegevens zijn ontleend - raadpleegbaar.
 * Hierbij is het mogelijk dat eenzelfde Document later (nogmaals) wordt gebruikt voor de ontlening; er is dat geval in
 * principe sprake van één-en-hetzelfde document.
 *
 * 1. Het omgaan met documenten 'erft' de BRP uit het GBA tijdperk. De richting waarin de BRP opgaat is echter één
 * waarin we het specifieke document willen kennen. Dat betekent dat - in tegenstelling tot de 'definitie' in het LO 3.x
 * - het objecttype Document in essentie overeenkomt met het daadwerkelijke fysieke en/of digitale document waar de
 * gegevens op staan die eraan ontleend zijn. Dat betekent dat indien eenzelfde document is gebruikt voor twee
 * verschillende administratieve handelingen (bijvoorbeeld het registreren van het Huwelijk en het registreren van de
 * Geboorte door twee verschillende ambtenaren bij twee verschillende gemeenten) er vanuit de corresponderende acties
 * verwezen zal worden naar dat ENE document. De toelichting bij Document is hierop aangepast.
 *
 * E-mail conversatie aangaande regelgeving: Voor archiveerbare digitale documenten schrijft het nationaal archief PDF/A
 * voor. Dit is een beperkt PDF formaat die blijkbaar de gewenste toekomstvaste mogelijkheden biedt. Voor afbeeldingen
 * kiezen ze voor TIFF. Uiteraard is alles altijd voorzien van meta-informatie. Op zich komt vanuit de wet- en
 * regelgeving niet een voorgeschreven formaat naar voren, dus OOXML of ODF et cetera zou ook kunnen. Ik kan me
 * herinneren dat de NORA hier ook iets over zegt. Even opgezocht, op pagina 154 van NORA 2.0 staat de beschrijving.
 *
 *
 *
 *
 */
@Access(value = AccessType.FIELD)
@Generated(value = "nl.bzk.brp.generatoren.java.OperationeelModelGenerator")
@MappedSuperclass
public abstract class AbstractDocumentModel extends AbstractDynamischObject implements DocumentBasis, ModelIdentificeerbaar<Long> {

    @Transient
    @JsonProperty
    private Long iD;

    @Embedded
    @AssociationOverride(name = SoortDocumentAttribuut.WAARDE_VELD_NAAM, joinColumns = @JoinColumn(name = "Srt"))
    @JsonProperty
    private SoortDocumentAttribuut soort;

    @Embedded
    @JsonProperty
    private DocumentStandaardGroepModel standaard;

    /**
     * Default lege constructor t.b.v. Hibernate / JPA
     *
     */
    protected AbstractDocumentModel() {
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param soort soort van Document.
     */
    public AbstractDocumentModel(final SoortDocumentAttribuut soort) {
        this();
        this.soort = soort;

    }

    /**
     * Copy constructor om vanuit het bericht model een instantie van het operationeel model te maken.
     *
     * @param document Te kopieren object type.
     */
    public AbstractDocumentModel(final Document document) {
        this();
        this.soort = document.getSoort();
        if (document.getStandaard() != null) {
            this.standaard = new DocumentStandaardGroepModel(document.getStandaard());
        }

    }

    /**
     * Retourneert ID van Document.
     *
     * @return ID.
     */
    @Id
    @SequenceGenerator(name = "DOCUMENT", sequenceName = "Kern.seq_Doc")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "DOCUMENT")
    @Access(value = AccessType.PROPERTY)
    public Long getID() {
        return iD;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SoortDocumentAttribuut getSoort() {
        return soort;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DocumentStandaardGroepModel getStandaard() {
        return standaard;
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
     * Zet Standaard van Document.
     *
     * @param standaard Standaard.
     */
    public void setStandaard(final DocumentStandaardGroepModel standaard) {
        this.standaard = standaard;
    }

    /**
     * Geeft alle groepen van de object met uitzondering van groepen die null zijn.
     *
     * @return Lijst met groepen.
     */
    public List<Groep> getGroepen() {
        final List<Groep> groepen = new ArrayList<>();
        if (standaard != null) {
            groepen.add(standaard);
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
        if (soort != null) {
            attributen.add(soort);
        }
        return attributen;
    }

}
