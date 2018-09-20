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

import nl.bzk.brp.model.algemeen.attribuuttype.kern.Datum;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamEnumeratiewaarde;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Nationaliteitcode;
import nl.bzk.brp.model.basis.AbstractStatischObjectType;
import nl.bzk.brp.model.basis.StamgegevenEntityListener;


/**
 * De aanduiding van de staat waarmee een persoon een juridische band kan hebben zoals bedoeld in het Europees verdrag
 * inzake nationaliteit, Straatsburg 06-11-1997.
 *
 * Er is een nuanceverschil tussen de lijst van Landen en de lijst van Nationaliteiten.
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
public abstract class AbstractNationaliteit extends AbstractStatischObjectType {

    @Id
    private Integer              iD;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "Code"))
    private Nationaliteitcode    code;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "Naam"))
    private NaamEnumeratiewaarde naam;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "dataanvgel"))
    private Datum                datumAanvangGeldigheid;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "dateindegel"))
    private Datum                datumEindeGeldigheid;

    /**
     * Constructor is protected, want de BRP zal geen stamgegevens beheren.
     *
     */
    protected AbstractNationaliteit() {
    }

    /**
     * Constructor die direct alle attributen instantieert.
     *
     * @param code code van Nationaliteit.
     * @param naam naam van Nationaliteit.
     * @param datumAanvangGeldigheid datumAanvangGeldigheid van Nationaliteit.
     * @param datumEindeGeldigheid datumEindeGeldigheid van Nationaliteit.
     */
    protected AbstractNationaliteit(final Nationaliteitcode code, final NaamEnumeratiewaarde naam,
            final Datum datumAanvangGeldigheid, final Datum datumEindeGeldigheid)
    {
        this.code = code;
        this.naam = naam;
        this.datumAanvangGeldigheid = datumAanvangGeldigheid;
        this.datumEindeGeldigheid = datumEindeGeldigheid;

    }

    /**
     * Retourneert ID van Nationaliteit.
     *
     * @return ID.
     */
    protected Integer getID() {
        return iD;
    }

    /**
     * Retourneert Code van Nationaliteit.
     *
     * @return Code.
     */
    public Nationaliteitcode getCode() {
        return code;
    }

    /**
     * Retourneert Naam van Nationaliteit.
     *
     * @return Naam.
     */
    public NaamEnumeratiewaarde getNaam() {
        return naam;
    }

    /**
     * Retourneert de datum aanvang geldigheid voor Nationaliteit.
     *
     * @return datum aanvang geldigheid voor Nationaliteit.
     */
    public Datum getDatumAanvangGeldigheid() {
        return datumAanvangGeldigheid;
    }

    /**
     * Retourneert de datum einde geldigheid voor Nationaliteit.
     *
     * @return datum einde geldigheid voor Nationaliteit.
     */
    public Datum getDatumEindeGeldigheid() {
        return datumEindeGeldigheid;
    }

}
