/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.beheer.kern;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.annotation.Generated;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.ISO31661Alpha2Attribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.LandGebiedCodeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamEnumeratiewaardeAttribuut;

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
@Entity(name = "beheer.LandGebied")
@Table(schema = "Kern", name = "LandGebied")
@Generated(value = "nl.bzk.brp.generatoren.java.BeheerGenerator")
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public class LandGebied {

    @Id
    @SequenceGenerator(name = "LANDGEBIED", sequenceName = "Kern.seq_LandGebied")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "LANDGEBIED")
    @JsonProperty
    private Integer iD;

    @Embedded
    @AttributeOverride(name = LandGebiedCodeAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Code"))
    private LandGebiedCodeAttribuut code;

    @Embedded
    @AttributeOverride(name = NaamEnumeratiewaardeAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Naam"))
    private NaamEnumeratiewaardeAttribuut naam;

    @Embedded
    @AttributeOverride(name = ISO31661Alpha2Attribuut.WAARDE_VELD_NAAM, column = @Column(name = "ISO31661Alpha2"))
    private ISO31661Alpha2Attribuut iSO31661Alpha2;

    @Embedded
    @AttributeOverride(name = DatumEvtDeelsOnbekendAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "DatAanvGel"))
    private DatumEvtDeelsOnbekendAttribuut datumAanvangGeldigheid;

    @Embedded
    @AttributeOverride(name = DatumEvtDeelsOnbekendAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "DatEindeGel"))
    private DatumEvtDeelsOnbekendAttribuut datumEindeGeldigheid;

    /**
     * Retourneert ID van Land/gebied.
     *
     * @return ID.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public Integer getID() {
        return iD;
    }

    /**
     * Retourneert Code van Land/gebied.
     *
     * @return Code.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public LandGebiedCodeAttribuut getCode() {
        return code;
    }

    /**
     * Retourneert Naam van Land/gebied.
     *
     * @return Naam.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public NaamEnumeratiewaardeAttribuut getNaam() {
        return naam;
    }

    /**
     * Retourneert ISO 3166-1 alpha 2 van Land/gebied.
     *
     * @return ISO 3166-1 alpha 2.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public ISO31661Alpha2Attribuut getISO31661Alpha2() {
        return iSO31661Alpha2;
    }

    /**
     * Retourneert Datum aanvang geldigheid van Land/gebied.
     *
     * @return Datum aanvang geldigheid.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public DatumEvtDeelsOnbekendAttribuut getDatumAanvangGeldigheid() {
        return datumAanvangGeldigheid;
    }

    /**
     * Retourneert Datum einde geldigheid van Land/gebied.
     *
     * @return Datum einde geldigheid.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public DatumEvtDeelsOnbekendAttribuut getDatumEindeGeldigheid() {
        return datumEindeGeldigheid;
    }

    /**
     * Zet ID van Land/gebied.
     *
     * @param pID ID.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setID(final Integer pID) {
        this.iD = pID;
    }

    /**
     * Zet Code van Land/gebied.
     *
     * @param pCode Code.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setCode(final LandGebiedCodeAttribuut pCode) {
        this.code = pCode;
    }

    /**
     * Zet Naam van Land/gebied.
     *
     * @param pNaam Naam.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setNaam(final NaamEnumeratiewaardeAttribuut pNaam) {
        this.naam = pNaam;
    }

    /**
     * Zet ISO 3166-1 alpha 2 van Land/gebied.
     *
     * @param pISO31661Alpha2 ISO 3166-1 alpha 2.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setISO31661Alpha2(final ISO31661Alpha2Attribuut pISO31661Alpha2) {
        this.iSO31661Alpha2 = pISO31661Alpha2;
    }

    /**
     * Zet Datum aanvang geldigheid van Land/gebied.
     *
     * @param pDatumAanvangGeldigheid Datum aanvang geldigheid.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setDatumAanvangGeldigheid(final DatumEvtDeelsOnbekendAttribuut pDatumAanvangGeldigheid) {
        this.datumAanvangGeldigheid = pDatumAanvangGeldigheid;
    }

    /**
     * Zet Datum einde geldigheid van Land/gebied.
     *
     * @param pDatumEindeGeldigheid Datum einde geldigheid.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setDatumEindeGeldigheid(final DatumEvtDeelsOnbekendAttribuut pDatumEindeGeldigheid) {
        this.datumEindeGeldigheid = pDatumEindeGeldigheid;
    }

}
