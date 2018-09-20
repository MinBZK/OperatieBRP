/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import javax.persistence.Entity;
import javax.persistence.Table;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortDocumentAttribuut;
import nl.bzk.brp.model.logisch.kern.Document;
import org.apache.commons.lang.builder.EqualsBuilder;


/**
 * Het Brondocument op basis waarvan bijhouding heeft plaatsgevonden.
 * <p/>
 * Bijhouding in de BRP vindt vaak plaats naar aanleiding van ontlening uit brondocumenten. Dit kunnen akten zijn, maar zijn dat niet altijd. Vanuit een
 * BRP Actie zijn de documenten - waaraan de gegevens zijn ontleend - raadpleegbaar. Hierbij is het mogelijk dat eenzelfde Document later (nogmaals) wordt
 * gebruikt voor de ontlening; er is dat geval in principe sprake van ��n-en-hetzelfde document.
 * <p/>
 * 1. Het omgaan met documenten 'erft' de BRP uit het GBA tijdperk. De richting waarin de BRP opgaat is echter ��n waarin we het specifieke document willen
 * kennen. Dat betekent dat - in tegenstelling tot de 'definitie' in het LO 3.x - het objecttype Document in essentie overeenkomt met het daadwerkelijke
 * fysieke en/of digitale document waar de gegevens op staan die eraan ontleent zijn. Dat betekent dat indien eenzelfde document is gebruikt voor twee
 * verschillende administratieve handelingen (bijvoorbeeld het registreren van het Huwelijk en het registreren van de Geboorte door twee verschillende
 * ambtenaren bij twee verschillende gemeenten) er vanuit de corresponderende acties verwezen zal worden naar dat ENE document. De toelichting bij Document
 * is hierop aangepast. RvdP 24-9-2012
 * <p/>
 * Ter verwerken e-mail: --------------------- Hallo J,
 * <p/>
 * Voor archiveerbare digitale documenten schrijft het nationaal archief PDF/A voor. Dit is een beperkt PDF formaat die blijkbaar de gewenste toekomstvaste
 * mogelijkheden biedt. Voor afbeeldingen kiezen ze voor TIFF. Uiteraard is alles altijd voorzien van meta-informatie. Op zich komt vanuit de wet- en
 * regelgeving niet een voorgeschreven formaat naar voren, dus OOXML of ODF et cetera zou ook kunnen. Ik kan me herinneren dat de NORA hier ook iets over
 * zegt. Even opgezocht, op pagina 154 van NORA 2.0 staat de beschrijving.
 * <p/>
 * Met vriendelijke groet, S
 */
@Entity
@Table(schema = "Kern", name = "Doc")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "iD", scope = DocumentModel.class)
public class DocumentModel extends AbstractDocumentModel implements Document {

    /**
     * Standaard constructor (t.b.v. Hibernate/JPA).
     */
    protected DocumentModel() {
        super();
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param soort soort van Document.
     */
    public DocumentModel(final SoortDocumentAttribuut soort) {
        super(soort);
    }

    /**
     * Copy constructor om vanuit het bericht model een instantie van het operationeel model te maken.
     *
     * @param document Te kopieren object type.
     */
    public DocumentModel(final Document document) {
        super(document);
    }

    @Override
    public int hashCode() {
        if (getID() != null) {
            return super.getID().hashCode();
        }
        return super.hashCode();
    }

    @Override
    public boolean equals(final Object obj) {
        final boolean isGelijk;

        if (obj == null || obj.getClass() != getClass()) {
            isGelijk = false;
        } else if (this == obj) {
            isGelijk = true;
        } else {
            final DocumentModel ander = (DocumentModel) obj;
            isGelijk = new EqualsBuilder().append(this.getID(), ander.getID()).isEquals();
        }

        return isGelijk;
    }

}
