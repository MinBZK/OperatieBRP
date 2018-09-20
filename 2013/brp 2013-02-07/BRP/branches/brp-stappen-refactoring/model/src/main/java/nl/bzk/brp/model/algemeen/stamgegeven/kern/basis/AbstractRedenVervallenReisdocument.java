/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.kern.basis;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamEnumeratiewaarde;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.RedenOntbrekenReisdocumentCode;
import nl.bzk.brp.model.basis.AbstractStatischObjectType;
import nl.bzk.brp.model.basis.StamgegevenEntityListener;


/**
 * De (mogelijke) reden voor het vervallen van een reisdocument.
 *
 *
 *
 * Generator: nl.bzk.brp.generatoren.java.DynamischeStamgegevensGenerator.
 * Metaregister versie: 1.6.0.
 * Generator versie: 1.0-SNAPSHOT.
 * Generator gebouwd op: 2013-01-15 12:43:16.
 * Gegenereerd op: Tue Jan 15 12:53:51 CET 2013.
 */
@Access(value = AccessType.FIELD)
@EntityListeners(value = StamgegevenEntityListener.class)
@MappedSuperclass
public abstract class AbstractRedenVervallenReisdocument extends AbstractStatischObjectType {

    @Id
    private Short                          iD;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "Code"))
    private RedenOntbrekenReisdocumentCode code;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "Naam"))
    private NaamEnumeratiewaarde           naam;

    /**
     * Constructor is protected, want de BRP zal geen stamgegevens beheren.
     *
     */
    protected AbstractRedenVervallenReisdocument() {
    }

    /**
     * Constructor die direct alle attributen instantieert.
     *
     * @param code code van RedenVervallenReisdocument.
     * @param naam naam van RedenVervallenReisdocument.
     */
    protected AbstractRedenVervallenReisdocument(final RedenOntbrekenReisdocumentCode code,
            final NaamEnumeratiewaarde naam)
    {
        this.code = code;
        this.naam = naam;

    }

    /**
     * Retourneert ID van Reden vervallen reisdocument.
     *
     * @return ID.
     */
    protected Short getID() {
        return iD;
    }

    /**
     * Retourneert Code van Reden vervallen reisdocument.
     *
     * @return Code.
     */
    public RedenOntbrekenReisdocumentCode getCode() {
        return code;
    }

    /**
     * Retourneert Naam van Reden vervallen reisdocument.
     *
     * @return Naam.
     */
    public NaamEnumeratiewaarde getNaam() {
        return naam;
    }

}
