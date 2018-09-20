/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.kern;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.annotation.Generated;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.ISO31661Alpha2Attribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.LandGebiedCodeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamEnumeratiewaardeAttribuut;
import nl.bzk.brp.model.basis.BestaansPeriodeStamgegeven;
import nl.bzk.brp.model.basis.ElementIdentificeerbaar;
import nl.bzk.brp.model.basis.StamgegevenEntityListener;
import nl.bzk.brp.model.basis.SynchroniseerbaarStamgegeven;
import org.hibernate.annotations.Immutable;

/**
 * Onafhankelijke staat, zoals door Nederland erkend, of een gebied.
 *
 * De erkenning van de ene staat door de andere, is een eenzijdige rechtshandeling van die andere staat, die daarmee te
 * kennen geeft dat hij de nieuw erkende staat als lid van het internationale statensysteem aanvaardt en alle gevolgen
 * van die erkenning wil accepteren. Naast staten bevat de tabel ook incidenteel gebieden voor die gevallen waarin
 * erkenning door de Nederlandse staat niet aan de orde is (geweest).
 *
 * 1. Bij de uitbreiding met ISO codes is een cruciale keuze: voegen we de tweeletterige code ('ISO 3166-1 alpha 2')
 * toe, of gaan we voor de drieletterige code. Omdat de tweeletterige iets vaker wordt gebruikt, en omdat deze ook
 * (gratis) kan worden gebruikt zonder dat daarvoor iets hoeft te worden aangeschaft, is de keus op de tweeletterige
 * code gevallen.
 *
 * 2. Naam is aangepast naar Land of gebied, conform overeenkomstige wijzigingen uit afstemming gegevensset.
 *
 *
 *
 */
@Access(value = AccessType.FIELD)
@EntityListeners(value = StamgegevenEntityListener.class)
@Immutable
@Generated(value = "nl.bzk.brp.generatoren.java.DynamischeStamgegevensGenerator")
@MappedSuperclass
public abstract class AbstractLandGebied implements SynchroniseerbaarStamgegeven, BestaansPeriodeStamgegeven, ElementIdentificeerbaar {

    @Id
    @JsonProperty
    private Integer iD;

    @Embedded
    @AttributeOverride(name = LandGebiedCodeAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Code"))
    @JsonProperty
    private LandGebiedCodeAttribuut code;

    @Embedded
    @AttributeOverride(name = NaamEnumeratiewaardeAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Naam"))
    private NaamEnumeratiewaardeAttribuut naam;

    @Embedded
    @AttributeOverride(name = ISO31661Alpha2Attribuut.WAARDE_VELD_NAAM, column = @Column(name = "ISO31661Alpha2"))
    private ISO31661Alpha2Attribuut iSO31661Alpha2;

    @Embedded
    @AttributeOverride(name = DatumEvtDeelsOnbekendAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "dataanvgel"))
    private DatumEvtDeelsOnbekendAttribuut datumAanvangGeldigheid;

    @Embedded
    @AttributeOverride(name = DatumEvtDeelsOnbekendAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "dateindegel"))
    private DatumEvtDeelsOnbekendAttribuut datumEindeGeldigheid;

    /**
     * Constructor is protected, want de BRP zal geen stamgegevens beheren.
     *
     */
    protected AbstractLandGebied() {
    }

    /**
     * Constructor die direct alle attributen instantieert.
     *
     * @param code code van LandGebied.
     * @param naam naam van LandGebied.
     * @param iSO31661Alpha2 iSO31661Alpha2 van LandGebied.
     * @param datumAanvangGeldigheid datumAanvangGeldigheid van LandGebied.
     * @param datumEindeGeldigheid datumEindeGeldigheid van LandGebied.
     */
    protected AbstractLandGebied(
        final LandGebiedCodeAttribuut code,
        final NaamEnumeratiewaardeAttribuut naam,
        final ISO31661Alpha2Attribuut iSO31661Alpha2,
        final DatumEvtDeelsOnbekendAttribuut datumAanvangGeldigheid,
        final DatumEvtDeelsOnbekendAttribuut datumEindeGeldigheid)
    {
        this.code = code;
        this.naam = naam;
        this.iSO31661Alpha2 = iSO31661Alpha2;
        this.datumAanvangGeldigheid = datumAanvangGeldigheid;
        this.datumEindeGeldigheid = datumEindeGeldigheid;

    }

    /**
     * Retourneert ID van Land/gebied.
     *
     * @return ID.
     */
    protected Integer getID() {
        return iD;
    }

    /**
     * Retourneert Code van Land/gebied.
     *
     * @return Code.
     */
    public final LandGebiedCodeAttribuut getCode() {
        return code;
    }

    /**
     * Retourneert Naam van Land/gebied.
     *
     * @return Naam.
     */
    public final NaamEnumeratiewaardeAttribuut getNaam() {
        return naam;
    }

    /**
     * Retourneert ISO 3166-1 alpha 2 van Land/gebied.
     *
     * @return ISO 3166-1 alpha 2.
     */
    public final ISO31661Alpha2Attribuut getISO31661Alpha2() {
        return iSO31661Alpha2;
    }

    /**
     * Retourneert de datum aanvang geldigheid voor Land/gebied.
     *
     * @return Datum aanvang geldigheid voor Land/gebied
     */
    public final DatumEvtDeelsOnbekendAttribuut getDatumAanvangGeldigheid() {
        return datumAanvangGeldigheid;
    }

    /**
     * Retourneert de datum einde geldigheid voor Land/gebied.
     *
     * @return Datum einde geldigheid voor Land/gebied
     */
    public final DatumEvtDeelsOnbekendAttribuut getDatumEindeGeldigheid() {
        return datumEindeGeldigheid;
    }

    /**
     * Retourneert het Element behorende bij dit objecttype.
     *
     * @return Element enum instantie behorende bij dit objecttype.
     */
    public final ElementEnum getElementIdentificatie() {
        return ElementEnum.LANDGEBIED;
    }

}
