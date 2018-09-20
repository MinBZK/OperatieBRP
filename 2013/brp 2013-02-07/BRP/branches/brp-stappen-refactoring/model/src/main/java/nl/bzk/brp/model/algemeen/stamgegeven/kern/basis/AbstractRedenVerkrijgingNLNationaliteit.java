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
import nl.bzk.brp.model.algemeen.attribuuttype.kern.OmschrijvingEnumeratiewaarde;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.RedenVerkrijgingCode;
import nl.bzk.brp.model.basis.AbstractStatischObjectType;
import nl.bzk.brp.model.basis.StamgegevenEntityListener;


/**
 * De mogelijke reden voor het verkrijgen van de Nederlandse nationaliteit.
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
public abstract class AbstractRedenVerkrijgingNLNationaliteit extends AbstractStatischObjectType {

    @Id
    private Short                        iD;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "Code"))
    private RedenVerkrijgingCode         code;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "Oms"))
    private OmschrijvingEnumeratiewaarde omschrijving;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "dataanvgel"))
    private Datum                        datumAanvangGeldigheid;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "dateindegel"))
    private Datum                        datumEindeGeldigheid;

    /**
     * Constructor is protected, want de BRP zal geen stamgegevens beheren.
     *
     */
    protected AbstractRedenVerkrijgingNLNationaliteit() {
    }

    /**
     * Constructor die direct alle attributen instantieert.
     *
     * @param code code van RedenVerkrijgingNLNationaliteit.
     * @param omschrijving omschrijving van RedenVerkrijgingNLNationaliteit.
     * @param datumAanvangGeldigheid datumAanvangGeldigheid van RedenVerkrijgingNLNationaliteit.
     * @param datumEindeGeldigheid datumEindeGeldigheid van RedenVerkrijgingNLNationaliteit.
     */
    protected AbstractRedenVerkrijgingNLNationaliteit(final RedenVerkrijgingCode code,
            final OmschrijvingEnumeratiewaarde omschrijving, final Datum datumAanvangGeldigheid,
            final Datum datumEindeGeldigheid)
    {
        this.code = code;
        this.omschrijving = omschrijving;
        this.datumAanvangGeldigheid = datumAanvangGeldigheid;
        this.datumEindeGeldigheid = datumEindeGeldigheid;

    }

    /**
     * Retourneert ID van Reden verkrijging NL nationaliteit.
     *
     * @return ID.
     */
    protected Short getID() {
        return iD;
    }

    /**
     * Retourneert Code van Reden verkrijging NL nationaliteit.
     *
     * @return Code.
     */
    public RedenVerkrijgingCode getCode() {
        return code;
    }

    /**
     * Retourneert Omschrijving van Reden verkrijging NL nationaliteit.
     *
     * @return Omschrijving.
     */
    public OmschrijvingEnumeratiewaarde getOmschrijving() {
        return omschrijving;
    }

    /**
     * Retourneert de datum aanvang geldigheid voor Reden verkrijging NL nationaliteit.
     *
     * @return datum aanvang geldigheid voor Reden verkrijging NL nationaliteit.
     */
    public Datum getDatumAanvangGeldigheid() {
        return datumAanvangGeldigheid;
    }

    /**
     * Retourneert de datum einde geldigheid voor Reden verkrijging NL nationaliteit.
     *
     * @return datum einde geldigheid voor Reden verkrijging NL nationaliteit.
     */
    public Datum getDatumEindeGeldigheid() {
        return datumEindeGeldigheid;
    }

}
