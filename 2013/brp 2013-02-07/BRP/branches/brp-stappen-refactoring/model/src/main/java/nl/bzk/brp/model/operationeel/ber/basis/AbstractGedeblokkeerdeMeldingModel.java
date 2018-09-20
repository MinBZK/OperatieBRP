/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.ber.basis;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;

import nl.bzk.brp.model.algemeen.attribuuttype.ber.Meldingtekst;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Element;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.basis.AbstractDynamischObjectType;
import nl.bzk.brp.model.logisch.ber.GedeblokkeerdeMelding;
import nl.bzk.brp.model.logisch.ber.basis.GedeblokkeerdeMeldingBasis;


/**
 * Een melding die gedeblokkeerd is.
 *
 * Bij het controleren van een bijhoudingsbericht kunnen er ��n of meer meldingen zijn die gedeblokkeerd dienen te
 * worden opdat de bijhouding ook daadwerkelijk verricht kan worden. De gedeblokkeerde meldingen worden geadministreerd.
 *
 *
 *
 * Generator: nl.bzk.brp.generatoren.java.OperationeelModelGenerator.
 * Metaregister versie: 1.6.0.
 * Generator versie: 1.0-SNAPSHOT.
 * Generator gebouwd op: 2013-01-21 13:38:20.
 * Gegenereerd op: Mon Jan 21 13:42:15 CET 2013.
 */
@Access(value = AccessType.FIELD)
@MappedSuperclass
public abstract class AbstractGedeblokkeerdeMeldingModel extends AbstractDynamischObjectType implements
        GedeblokkeerdeMeldingBasis
{

    @Id
    @SequenceGenerator(name = "GEDEBLOKKEERDEMELDING", sequenceName = "Ber.seq_GedeblokkeerdeMelding")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "GEDEBLOKKEERDEMELDING")
    @JsonProperty
    private Long         iD;

    @Enumerated
    @Column(name = "Regel")
    @JsonProperty
    private Regel        regel;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "Melding"))
    @JsonProperty
    private Meldingtekst melding;

    @Enumerated
    @Column(name = "Attribuut")
    @JsonProperty
    private Element      attribuut;

    /**
     * Standaard constructor (t.b.v. Hibernate/JPA).
     *
     */
    protected AbstractGedeblokkeerdeMeldingModel() {
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param regel regel van Gedeblokkeerde melding.
     * @param melding melding van Gedeblokkeerde melding.
     * @param attribuut attribuut van Gedeblokkeerde melding.
     */
    public AbstractGedeblokkeerdeMeldingModel(final Regel regel, final Meldingtekst melding, final Element attribuut) {
        this();
        this.regel = regel;
        this.melding = melding;
        this.attribuut = attribuut;

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
        this.attribuut = gedeblokkeerdeMelding.getAttribuut();

    }

    /**
     * Retourneert ID van Gedeblokkeerde melding.
     *
     * @return ID.
     */
    public Long getID() {
        return iD;
    }

    /**
     * Retourneert Regel van Gedeblokkeerde melding.
     *
     * @return Regel.
     */
    public Regel getRegel() {
        return regel;
    }

    /**
     * Retourneert Melding van Gedeblokkeerde melding.
     *
     * @return Melding.
     */
    public Meldingtekst getMelding() {
        return melding;
    }

    /**
     * Retourneert Attribuut van Gedeblokkeerde melding.
     *
     * @return Attribuut.
     */
    public Element getAttribuut() {
        return attribuut;
    }

}
