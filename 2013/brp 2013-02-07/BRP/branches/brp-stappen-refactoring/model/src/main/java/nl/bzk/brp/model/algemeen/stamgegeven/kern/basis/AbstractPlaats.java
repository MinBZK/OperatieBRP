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
import nl.bzk.brp.model.algemeen.attribuuttype.kern.LangeNaamEnumeratiewaarde;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Woonplaatscode;
import nl.bzk.brp.model.basis.AbstractStatischObjectType;
import nl.bzk.brp.model.basis.StamgegevenEntityListener;


/**
 * De woonplaatsen, zoals onderhouden vanuit de BAG.
 *
 * De inhoud van de woonplaatsentabel wordt overgenomen vanuit de BAG. Qua vorm wijkt deze wel af, zo wordt er apart
 * bijgehouden welke gemeenten er zijn, terwijl de BAG deze in ��n en dezelfde tabel heeft gestopt.
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
public abstract class AbstractPlaats extends AbstractStatischObjectType {

    @Id
    private Integer                   iD;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "Code"))
    private Woonplaatscode            code;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "Naam"))
    private LangeNaamEnumeratiewaarde naam;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "dataanvgel"))
    private Datum                     datumAanvangGeldigheid;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "dateindegel"))
    private Datum                     datumEindeGeldigheid;

    /**
     * Constructor is protected, want de BRP zal geen stamgegevens beheren.
     *
     */
    protected AbstractPlaats() {
    }

    /**
     * Constructor die direct alle attributen instantieert.
     *
     * @param code code van Plaats.
     * @param naam naam van Plaats.
     * @param datumAanvangGeldigheid datumAanvangGeldigheid van Plaats.
     * @param datumEindeGeldigheid datumEindeGeldigheid van Plaats.
     */
    protected AbstractPlaats(final Woonplaatscode code, final LangeNaamEnumeratiewaarde naam,
            final Datum datumAanvangGeldigheid, final Datum datumEindeGeldigheid)
    {
        this.code = code;
        this.naam = naam;
        this.datumAanvangGeldigheid = datumAanvangGeldigheid;
        this.datumEindeGeldigheid = datumEindeGeldigheid;

    }

    /**
     * Retourneert ID van Plaats.
     *
     * @return ID.
     */
    protected Integer getID() {
        return iD;
    }

    /**
     * Retourneert Code van Plaats.
     *
     * @return Code.
     */
    public Woonplaatscode getCode() {
        return code;
    }

    /**
     * Retourneert Naam van Plaats.
     *
     * @return Naam.
     */
    public LangeNaamEnumeratiewaarde getNaam() {
        return naam;
    }

    /**
     * Retourneert de datum aanvang geldigheid voor Plaats.
     *
     * @return datum aanvang geldigheid voor Plaats.
     */
    public Datum getDatumAanvangGeldigheid() {
        return datumAanvangGeldigheid;
    }

    /**
     * Retourneert de datum einde geldigheid voor Plaats.
     *
     * @return datum einde geldigheid voor Plaats.
     */
    public Datum getDatumEindeGeldigheid() {
        return datumEindeGeldigheid;
    }

}
