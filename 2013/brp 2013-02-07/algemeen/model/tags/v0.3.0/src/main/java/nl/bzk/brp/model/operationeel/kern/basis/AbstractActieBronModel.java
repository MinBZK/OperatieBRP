/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern.basis;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;

import nl.bzk.brp.model.algemeen.stamgegeven.kern.Rechtsgrond;
import nl.bzk.brp.model.basis.AbstractDynamischObjectType;
import nl.bzk.brp.model.logisch.kern.ActieBron;
import nl.bzk.brp.model.logisch.kern.basis.ActieBronBasis;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.model.operationeel.kern.DocumentModel;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;


/**
 * De Verantwoording van een Actie door een bron, hetzij een Document hetzij een Rechtsgrond.
 *
 * Een BRP Actie wordt verantwoord door nul, ��n of meer Documenten en nul, ��n of meer Rechtsgronden. Elke combinatie
 * van de Actie enerzijds en een bron (een Document of een Rechtsgrond) anderzijds, wordt vastgelegd.
 *
 * 1. De ontleningstoelicting is gemodelleerd als eigenschap van de BRP Actie. Voordeel hiervan is dat de beperking tot
 * ��n toelichting per Actie eenvoudig is af te dwingen. Het nadeel is dat voor het begrijpen van de Actie er zowel naar
 * de ontleningstoelichting moet worden gekeken, als naar de Verantwoording. Dit maakt echter dat het begrip van
 * "Verantwoording" iets wordt versimpeld: een verantwoording betreft altijd of een Document, of een Rechtsgrond.
 * RvdP 24-9-2012
 * 2. De naam is een tijdje 'verantwoording' geweest. Het is echter niet meer dan een koppeltabel tussen een actie
 * enerzijds, en een document of rechtsgrond anderzijds. Een generalisatie van document en rechtsgrond zou 'bron' zijn.
 * Passend in het BMR toegepaste patroon is dan om de koppeltabel - die actie enerzijds en bron anderzijds koppelt - dan
 * de naam Actie/Bron te noemen. Hiervoor is uiteindelijk gekozen.
 * RvdP 26 november 2012, aangepast 29 november.
 *
 *
 *
 */
@Access(value = AccessType.FIELD)
@MappedSuperclass
public abstract class AbstractActieBronModel extends AbstractDynamischObjectType implements ActieBronBasis {

    @Id
    @SequenceGenerator(name = "ACTIEBRON", sequenceName = "Kern.seq_ActieBron")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "ACTIEBRON")
    @JsonProperty
    private Long          iD;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Actie")
    private ActieModel    actie;

    @ManyToOne
    @JoinColumn(name = "Doc")
    @JsonProperty
    private DocumentModel document;

    @ManyToOne
    @JoinColumn(name = "Rechtsgrond")
    @Fetch(value = FetchMode.JOIN)
    @JsonProperty
    private Rechtsgrond   rechtsgrond;

    /**
     * Standaard constructor (t.b.v. Hibernate/JPA).
     *
     */
    protected AbstractActieBronModel() {
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param actie actie van Actie/Bron.
     * @param document document van Actie/Bron.
     * @param rechtsgrond rechtsgrond van Actie/Bron.
     */
    public AbstractActieBronModel(final ActieModel actie, final DocumentModel document, final Rechtsgrond rechtsgrond) {
        this();
        this.actie = actie;
        this.document = document;
        this.rechtsgrond = rechtsgrond;

    }

    /**
     * Copy constructor om vanuit het bericht model een instantie van het operationeel model te maken.
     *
     * @param actieBron Te kopieren object type.
     * @param actie Bijbehorende Actie.
     * @param document Bijbehorende Document.
     */
    public AbstractActieBronModel(final ActieBron actieBron, final ActieModel actie, final DocumentModel document) {
        this();
        this.actie = actie;
        this.document = document;
        this.rechtsgrond = actieBron.getRechtsgrond();

    }

    /**
     * Retourneert ID van Actie/Bron.
     *
     * @return ID.
     */
    public Long getID() {
        return iD;
    }

    /**
     * Retourneert Actie van Actie/Bron.
     *
     * @return Actie.
     */
    public ActieModel getActie() {
        return actie;
    }

    /**
     * Retourneert Document van Actie/Bron.
     *
     * @return Document.
     */
    public DocumentModel getDocument() {
        return document;
    }

    /**
     * Retourneert Rechtsgrond van Actie/Bron.
     *
     * @return Rechtsgrond.
     */
    public Rechtsgrond getRechtsgrond() {
        return rechtsgrond;
    }

}
