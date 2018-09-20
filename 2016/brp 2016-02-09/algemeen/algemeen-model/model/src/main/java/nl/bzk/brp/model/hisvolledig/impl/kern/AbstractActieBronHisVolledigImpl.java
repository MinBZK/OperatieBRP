/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.impl.kern;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
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
import nl.bzk.brp.model.algemeen.stamgegeven.kern.ElementEnum;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RechtsgrondAttribuut;
import nl.bzk.brp.model.basis.ElementIdentificeerbaar;
import nl.bzk.brp.model.basis.HisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.kern.ActieBronHisVolledigBasis;

/**
 * HisVolledig klasse voor Actie \ Bron.
 *
 */
@Access(value = AccessType.FIELD)
@Generated(value = "nl.bzk.brp.generatoren.java.HisVolledigModelGenerator")
@MappedSuperclass
public abstract class AbstractActieBronHisVolledigImpl implements HisVolledigImpl, ActieBronHisVolledigBasis, ElementIdentificeerbaar {

    @Transient
    @JsonProperty
    private Long iD;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Actie")
    @JsonBackReference
    private ActieHisVolledigImpl actie;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Doc")
    @JsonProperty
    private DocumentHisVolledigImpl document;

    @Embedded
    @AssociationOverride(name = RechtsgrondAttribuut.WAARDE_VELD_NAAM, joinColumns = @JoinColumn(name = "Rechtsgrond"))
    @JsonProperty
    private RechtsgrondAttribuut rechtsgrond;

    @Embedded
    @AttributeOverride(name = OmschrijvingEnumeratiewaardeAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Rechtsgrondoms"))
    @JsonProperty
    private OmschrijvingEnumeratiewaardeAttribuut rechtsgrondomschrijving;

    /**
     * Default contructor voor JPA.
     *
     */
    protected AbstractActieBronHisVolledigImpl() {
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param actie actie van Actie \ Bron.
     * @param document document van Actie \ Bron.
     * @param rechtsgrond rechtsgrond van Actie \ Bron.
     * @param rechtsgrondomschrijving rechtsgrondomschrijving van Actie \ Bron.
     */
    public AbstractActieBronHisVolledigImpl(
        final ActieHisVolledigImpl actie,
        final DocumentHisVolledigImpl document,
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
     * Retourneert Actie van Actie \ Bron.
     *
     * @return Actie.
     */
    public ActieHisVolledigImpl getActie() {
        return actie;
    }

    /**
     * Retourneert Document van Actie \ Bron.
     *
     * @return Document.
     */
    public DocumentHisVolledigImpl getDocument() {
        return document;
    }

    /**
     * Retourneert Rechtsgrond van Actie \ Bron.
     *
     * @return Rechtsgrond.
     */
    public RechtsgrondAttribuut getRechtsgrond() {
        return rechtsgrond;
    }

    /**
     * Retourneert Rechtsgrondomschrijving van Actie \ Bron.
     *
     * @return Rechtsgrondomschrijving.
     */
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
     * Retourneert het Element behorende bij dit objecttype.
     *
     * @return Element enum instantie behorende bij dit objecttype.
     */
    public final ElementEnum getElementIdentificatie() {
        return ElementEnum.ACTIEBRON;
    }

}
