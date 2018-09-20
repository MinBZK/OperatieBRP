/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.kern.basis;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.Datum;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.ISO31661Alpha2;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Landcode;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamEnumeratiewaarde;
import nl.bzk.brp.model.basis.AbstractStatischObjectType;
import nl.bzk.brp.model.basis.StamgegevenEntityListener;


/**
 * Onafhankelijke staat, zoals door Nederland erkend.
 *
 * De erkenning van de ene staat door de andere, is een eenzijdige rechtshandeling van die andere staat, die daarmee te
 * kennen geeft dat hij de nieuw erkende staat als lid van het internationale statensysteem aanvaardt en alle gevolgen
 * van die erkenning wil accepteren. De voorkomens van "Land" zijn hier beperkt tot die staten die door de Staat der
 * Nederlanden erkend zijn, dan wel (in geval van historische gegevens) erkend zijn geweest.
 *
 * Bij de uitbreiding met ISO codes is een cruciale keuze: voegen we de tweeletterige code ('ISO 3166-1 alpha 2') toe,
 * of gaan we voor de drieletterige code.
 * Omdat de tweeletterige iets vaker wordt gebruikt, en omdat deze ook (gratis) kan worden gebruikt zonder dat daarvoor
 * iets hoeft te worden aangeschaft, is de keus op de tweeletterige code gevallen.
 * RvdP 5 september 2011.
 *
 *
 *
 */
@Access(value = AccessType.FIELD)
@EntityListeners(value = StamgegevenEntityListener.class)
@MappedSuperclass
public abstract class AbstractLand extends AbstractStatischObjectType {

    @Id
    @JsonProperty
    private Integer              iD;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "Code"))
    private Landcode             code;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "Naam"))
    private NaamEnumeratiewaarde naam;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "ISO31661Alpha2"))
    private ISO31661Alpha2       iSO31661Alpha2;

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
    protected AbstractLand() {
    }

    /**
     * Constructor die direct alle attributen instantieert.
     *
     * @param code code van Land.
     * @param naam naam van Land.
     * @param iSO31661Alpha2 iSO31661Alpha2 van Land.
     * @param datumAanvangGeldigheid datumAanvangGeldigheid van Land.
     * @param datumEindeGeldigheid datumEindeGeldigheid van Land.
     */
    protected AbstractLand(final Landcode code, final NaamEnumeratiewaarde naam, final ISO31661Alpha2 iSO31661Alpha2,
            final Datum datumAanvangGeldigheid, final Datum datumEindeGeldigheid)
    {
        this.code = code;
        this.naam = naam;
        this.iSO31661Alpha2 = iSO31661Alpha2;
        this.datumAanvangGeldigheid = datumAanvangGeldigheid;
        this.datumEindeGeldigheid = datumEindeGeldigheid;

    }

    /**
     * Retourneert ID van Land.
     *
     * @return ID.
     */
    protected Integer getID() {
        return iD;
    }

    /**
     * Retourneert Code van Land.
     *
     * @return Code.
     */
    public Landcode getCode() {
        return code;
    }

    /**
     * Retourneert Naam van Land.
     *
     * @return Naam.
     */
    public NaamEnumeratiewaarde getNaam() {
        return naam;
    }

    /**
     * Retourneert ISO 3166-1 alpha 2 van Land.
     *
     * @return ISO 3166-1 alpha 2.
     */
    public ISO31661Alpha2 getISO31661Alpha2() {
        return iSO31661Alpha2;
    }

    /**
     * Retourneert de datum aanvang geldigheid voor Land.
     *
     * @return datum aanvang geldigheid voor Land.
     */
    public Datum getDatumAanvangGeldigheid() {
        return datumAanvangGeldigheid;
    }

    /**
     * Retourneert de datum einde geldigheid voor Land.
     *
     * @return datum einde geldigheid voor Land.
     */
    public Datum getDatumEindeGeldigheid() {
        return datumEindeGeldigheid;
    }

}
