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
import nl.bzk.brp.model.algemeen.attribuuttype.kern.OmschrijvingEnumeratiewaardeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RechtsgrondAttribuut;
import nl.bzk.brp.model.basis.AbstractDynamischObject;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.basis.Groep;
import nl.bzk.brp.model.basis.ModelIdentificeerbaar;
import nl.bzk.brp.model.logisch.kern.ActieBron;
import nl.bzk.brp.model.logisch.kern.ActieBronBasis;

/**
 * De Verantwoording van een Actie door een bron, hetzij een Document hetzij een vooraf bekende Rechtsgrond, hetzij de
 * omschrijving van een (niet vooraf bekende) rechtsgrond.
 *
 * Een BRP Actie wordt verantwoord door nul, één of meer Documenten en nul, één of meer Rechtsgronden. Elke combinatie
 * van de Actie enerzijds en een bron (een Document of een Rechtsgrond) anderzijds, wordt vastgelegd.
 *
 * De naam is een tijdje 'verantwoording' geweest. Het is echter niet meer dan een koppeltabel tussen een actie
 * enerzijds, en een document of rechtsgrond anderzijds. Een generalisatie van document en rechtsgrond zou 'bron' zijn.
 * Passend in het BMR toegepaste patroon is dan om de koppeltabel - die actie enerzijds en bron anderzijds koppelt - dan
 * de naam Actie/Bron te noemen. Hiervoor is uiteindelijk gekozen.
 *
 *
 *
 */
@Access(value = AccessType.FIELD)
@Generated(value = "nl.bzk.brp.generatoren.java.OperationeelModelGenerator")
@MappedSuperclass
public abstract class AbstractActieBronModel extends AbstractDynamischObject implements ActieBronBasis, ModelIdentificeerbaar<Long> {

    @Transient
    @JsonProperty
    private Long iD;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Actie")
    private ActieModel actie;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Doc")
    @JsonProperty
    private DocumentModel document;

    @Embedded
    @AssociationOverride(name = RechtsgrondAttribuut.WAARDE_VELD_NAAM, joinColumns = @JoinColumn(name = "Rechtsgrond"))
    @JsonProperty
    private RechtsgrondAttribuut rechtsgrond;

    @Embedded
    @AttributeOverride(name = OmschrijvingEnumeratiewaardeAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Rechtsgrondoms"))
    @JsonProperty
    private OmschrijvingEnumeratiewaardeAttribuut rechtsgrondomschrijving;

    /**
     * Default lege constructor t.b.v. Hibernate / JPA
     *
     */
    protected AbstractActieBronModel() {
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param actie actie van Actie \ Bron.
     * @param document document van Actie \ Bron.
     * @param rechtsgrond rechtsgrond van Actie \ Bron.
     * @param rechtsgrondomschrijving rechtsgrondomschrijving van Actie \ Bron.
     */
    public AbstractActieBronModel(
        final ActieModel actie,
        final DocumentModel document,
        final RechtsgrondAttribuut rechtsgrond,
        final OmschrijvingEnumeratiewaardeAttribuut rechtsgrondomschrijving)
    {
        this();
        this.actie = actie;
        this.document = document;
        this.rechtsgrond = rechtsgrond;
        this.rechtsgrondomschrijving = rechtsgrondomschrijving;

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
        this.rechtsgrondomschrijving = actieBron.getRechtsgrondomschrijving();

    }

    /**
     * Retourneert ID van Actie \ Bron.
     *
     * @return ID.
     */
    @Id
    @SequenceGenerator(name = "ACTIEBRON", sequenceName = "Kern.seq_ActieBron")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "ACTIEBRON")
    @Access(value = AccessType.PROPERTY)
    public Long getID() {
        return iD;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ActieModel getActie() {
        return actie;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DocumentModel getDocument() {
        return document;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RechtsgrondAttribuut getRechtsgrond() {
        return rechtsgrond;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OmschrijvingEnumeratiewaardeAttribuut getRechtsgrondomschrijving() {
        return rechtsgrondomschrijving;
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
        if (rechtsgrond != null) {
            attributen.add(rechtsgrond);
        }
        if (rechtsgrondomschrijving != null) {
            attributen.add(rechtsgrondomschrijving);
        }
        return attributen;
    }

}
