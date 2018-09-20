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
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;
import nl.bzk.brp.model.algemeen.attribuuttype.ber.MeldingtekstAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RegelAttribuut;
import nl.bzk.brp.model.basis.AbstractDynamischObject;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.basis.Groep;
import nl.bzk.brp.model.basis.ModelIdentificeerbaar;
import nl.bzk.brp.model.logisch.kern.GedeblokkeerdeMelding;
import nl.bzk.brp.model.logisch.kern.GedeblokkeerdeMeldingBasis;

/**
 * Een melding die gedeblokkeerd is.
 *
 * Bij het controleren van een bijhoudingsbericht kunnen er één of meer meldingen zijn die gedeblokkeerd dienen te
 * worden opdat de bijhouding ook daadwerkelijk verricht kan worden. De gedeblokkeerde meldingen worden geadministreerd.
 *
 *
 *
 */
@Access(value = AccessType.FIELD)
@Generated(value = "nl.bzk.brp.generatoren.java.OperationeelModelGenerator")
@MappedSuperclass
public abstract class AbstractGedeblokkeerdeMeldingModel extends AbstractDynamischObject implements GedeblokkeerdeMeldingBasis,
        ModelIdentificeerbaar<Long>
{

    @Transient
    @JsonProperty
    private Long iD;

    @Embedded
    @AttributeOverride(name = RegelAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Regel"))
    @JsonProperty
    private RegelAttribuut regel;

    @Embedded
    @AttributeOverride(name = MeldingtekstAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Melding"))
    @JsonProperty
    private MeldingtekstAttribuut melding;

    /**
     * Default lege constructor t.b.v. Hibernate / JPA
     *
     */
    protected AbstractGedeblokkeerdeMeldingModel() {
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param regel regel van Gedeblokkeerde melding.
     * @param melding melding van Gedeblokkeerde melding.
     */
    public AbstractGedeblokkeerdeMeldingModel(final RegelAttribuut regel, final MeldingtekstAttribuut melding) {
        this();
        this.regel = regel;
        this.melding = melding;

    }

    /**
     * Copy constructor om vanuit het bericht model een instantie van het operationeel model te maken.
     *
     * @param gedeblokkeerdeMelding Te kopieren object type.
     */
    public AbstractGedeblokkeerdeMeldingModel(final GedeblokkeerdeMelding gedeblokkeerdeMelding) {
        this();
        this.regel = gedeblokkeerdeMelding.getRegel();
        this.melding = gedeblokkeerdeMelding.getMelding();

    }

    /**
     * Retourneert ID van Gedeblokkeerde melding.
     *
     * @return ID.
     */
    @Id
    @SequenceGenerator(name = "GEDEBLOKKEERDEMELDING", sequenceName = "Kern.seq_GedeblokkeerdeMelding")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "GEDEBLOKKEERDEMELDING")
    @Access(value = AccessType.PROPERTY)
    public Long getID() {
        return iD;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RegelAttribuut getRegel() {
        return regel;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MeldingtekstAttribuut getMelding() {
        return melding;
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
        if (regel != null) {
            attributen.add(regel);
        }
        if (melding != null) {
            attributen.add(melding);
        }
        return attributen;
    }

}
