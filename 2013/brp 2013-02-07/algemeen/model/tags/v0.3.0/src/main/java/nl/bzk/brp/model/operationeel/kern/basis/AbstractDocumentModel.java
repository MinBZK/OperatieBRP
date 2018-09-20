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
import javax.persistence.Embedded;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.StatusHistorie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortDocument;
import nl.bzk.brp.model.basis.AbstractDynamischObjectType;
import nl.bzk.brp.model.logisch.kern.Document;
import nl.bzk.brp.model.logisch.kern.basis.DocumentBasis;
import nl.bzk.brp.model.operationeel.kern.DocumentStandaardGroepModel;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Type;


/**
 * Het Brondocument op basis waarvan bijhouding heeft plaatsgevonden.
 *
 * Bijhouding in de BRP vindt vaak plaats naar aanleiding van ontlening uit brondocumenten. Dit kunnen akten zijn, maar
 * zijn dat niet altijd. Vanuit een BRP Actie zijn de documenten - waaraan de gegevens zijn ontleend - raadpleegbaar.
 * Hierbij is het mogelijk dat eenzelfde Document later (nogmaals) wordt gebruikt voor de ontlening; er is dat geval in
 * principe sprake van ��n-en-hetzelfde document.
 *
 * 1. Het omgaan met documenten 'erft' de BRP uit het GBA tijdperk. De richting waarin de BRP opgaat is echter ��n
 * waarin we het specifieke document willen kennen. Dat betekent dat - in tegenstelling tot de 'definitie' in het LO 3.x
 * - het objecttype Document in essentie overeenkomt met het daadwerkelijke fysieke en/of digitale document waar de
 * gegevens op staan die eraan ontleent zijn. Dat betekent dat indien eenzelfde document is gebruikt voor twee
 * verschillende administratieve handelingen (bijvoorbeeld het registreren van het Huwelijk en het registreren van de
 * Geboorte door twee verschillende ambtenaren bij twee verschillende gemeenten) er vanuit de corresponderende acties
 * verwezen zal worden naar dat ENE document. De toelichting bij Document is hierop aangepast.
 * RvdP 24-9-2012
 *
 * Ter verwerken e-mail:
 * ---------------------
 * Hallo J,
 *
 * Voor archiveerbare digitale documenten schrijft het nationaal archief PDF/A voor. Dit is een beperkt PDF formaat die
 * blijkbaar de gewenste toekomstvaste mogelijkheden biedt. Voor afbeeldingen kiezen ze voor TIFF.
 * Uiteraard is alles altijd voorzien van meta-informatie.
 * Op zich komt vanuit de wet- en regelgeving niet een voorgeschreven formaat naar voren, dus OOXML of ODF et cetera zou
 * ook kunnen.
 * Ik kan me herinneren dat de NORA hier ook iets over zegt. Even
 * opgezocht, op pagina 154 van NORA 2.0 staat de beschrijving.
 *
 * Met vriendelijke groet, S
 *
 *
 *
 */
@Access(value = AccessType.FIELD)
@MappedSuperclass
public abstract class AbstractDocumentModel extends AbstractDynamischObjectType implements DocumentBasis {

    @Id
    @SequenceGenerator(name = "DOCUMENT", sequenceName = "Kern.seq_Doc")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "DOCUMENT")
    @JsonProperty
    private Long                        iD;

    @ManyToOne
    @JoinColumn(name = "Srt")
    @Fetch(value = FetchMode.JOIN)
    @JsonProperty
    private SoortDocument               soort;

    @Embedded
    @JsonProperty
    private DocumentStandaardGroepModel standaard;

    @Type(type = "StatusHistorie")
    @Column(name = "DocStatusHis")
    @JsonProperty
    private StatusHistorie              documentStatusHis;

    /**
     * Standaard constructor (t.b.v. Hibernate/JPA).
     *
     */
    protected AbstractDocumentModel() {
        this.documentStatusHis = StatusHistorie.X;

    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param soort soort van Document.
     */
    public AbstractDocumentModel(final SoortDocument soort) {
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
    public Long getID() {
        return iD;
    }

    /**
     * Retourneert Soort van Document.
     *
     * @return Soort.
     */
    public SoortDocument getSoort() {
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
     * Retourneert Document StatusHis van Document.
     *
     * @return Document StatusHis.
     */
    public StatusHistorie getDocumentStatusHis() {
        return documentStatusHis;
    }

    /**
     * Zet Standaard van Document. Zet tevens het bijbehorende status his veld op 'A' als het argument niet null is.
     *
     * @param standaard Standaard.
     */
    public void setStandaard(final DocumentStandaardGroepModel standaard) {
        this.standaard = standaard;
        if (standaard != null) {
            documentStatusHis = StatusHistorie.A;
        }

    }

    /**
     * Zet Document StatusHis van Document.
     *
     * @param documentStatusHis Document StatusHis.
     */
    public void setDocumentStatusHis(final StatusHistorie documentStatusHis) {
        this.documentStatusHis = documentStatusHis;
    }

}
