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
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaNeeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamEnumeratiewaardeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.OINAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.PartijCodeAttribuut;
import nl.bzk.brp.model.basis.ElementIdentificeerbaar;
import nl.bzk.brp.model.basis.StamgegevenEntityListener;
import nl.bzk.brp.model.basis.SynchroniseerbaarStamgegeven;
import org.hibernate.annotations.Immutable;

/**
 * Een voor de BRP relevant overheidsorgaan of derde, zoals bedoeld in de Wet BRP, of onderdeel daarvan, die met een
 * bepaalde gerechtvaardigde doelstelling is aangesloten op de BRP.
 *
 * Dit betreft - onder andere - gemeenten, (overige) overheidsorganen en derden.
 *
 * 1. Er is voor gekozen om gemeenten, overige overheidsorganen etc te zien als soorten partijen, en ze allemaal op te
 * nemen in een partij tabel. Van oudsher voorkomende tabellen zoals 'de gemeentetabel' is daarmee een subtype van de
 * partij tabel geworden.
 *
 *
 *
 */
@Access(value = AccessType.FIELD)
@EntityListeners(value = StamgegevenEntityListener.class)
@Immutable
@Generated(value = "nl.bzk.brp.generatoren.java.DynamischeStamgegevensGenerator")
@MappedSuperclass
public abstract class AbstractPartij implements SynchroniseerbaarStamgegeven, ElementIdentificeerbaar {

    @Id
    @JsonProperty
    private Short iD;

    @Embedded
    @AttributeOverride(name = PartijCodeAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Code"))
    @JsonProperty
    private PartijCodeAttribuut code;

    @Embedded
    @AttributeOverride(name = NaamEnumeratiewaardeAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Naam"))
    private NaamEnumeratiewaardeAttribuut naam;

    @Embedded
    @AttributeOverride(name = DatumEvtDeelsOnbekendAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "DatIngang"))
    private DatumEvtDeelsOnbekendAttribuut datumIngang;

    @Embedded
    @AttributeOverride(name = DatumEvtDeelsOnbekendAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "DatEinde"))
    private DatumEvtDeelsOnbekendAttribuut datumEinde;

    @Embedded
    @AttributeOverride(name = OINAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "OIN"))
    private OINAttribuut oIN;

    //handmatige wijziging
    @Embedded
    @AttributeOverride(name = PartijAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Srt"))
    @JsonProperty
    private SoortPartijAttribuut soort;

    @Embedded
    @AttributeOverride(name = JaNeeAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "IndVerstrbeperkingMogelijk"))
    private JaNeeAttribuut indicatieVerstrekkingsbeperkingMogelijk;

    @Embedded
    @AttributeOverride(name = JaNeeAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "IndAutoFiat"))
    private JaNeeAttribuut indicatieAutomatischFiatteren;

    @Embedded
    @AttributeOverride(name = DatumAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "DatOvergangNaarBRP"))
    private DatumAttribuut datumOvergangNaarBRP;

    /**
     * Constructor is protected, want de BRP zal geen stamgegevens beheren.
     *
     */
    protected AbstractPartij() {
    }

    /**
     * Constructor die direct alle attributen instantieert. CHECKSTYLE-BRP:OFF - MAX PARAMS
     *
     * @param code code van Partij.
     * @param naam naam van Partij.
     * @param datumIngang datumIngang van Partij.
     * @param datumEinde datumEinde van Partij.
     * @param oIN oIN van Partij.
     * @param soort soort van Partij.
     * @param indicatieVerstrekkingsbeperkingMogelijk indicatieVerstrekkingsbeperkingMogelijk van Partij.
     * @param indicatieAutomatischFiatteren indicatieAutomatischFiatteren van Partij.
     * @param datumOvergangNaarBRP datumOvergangNaarBRP van Partij.
     */
    protected AbstractPartij(
        final PartijCodeAttribuut code,
        final NaamEnumeratiewaardeAttribuut naam,
        final DatumEvtDeelsOnbekendAttribuut datumIngang,
        final DatumEvtDeelsOnbekendAttribuut datumEinde,
        final OINAttribuut oIN,
        final SoortPartijAttribuut soort,
        final JaNeeAttribuut indicatieVerstrekkingsbeperkingMogelijk,
        final JaNeeAttribuut indicatieAutomatischFiatteren,
        final DatumAttribuut datumOvergangNaarBRP)
    {
        // CHECKSTYLE-BRP:ON - MAX PARAMS
        this.code = code;
        this.naam = naam;
        this.datumIngang = datumIngang;
        this.datumEinde = datumEinde;
        this.oIN = oIN;
        this.soort = soort;
        this.indicatieVerstrekkingsbeperkingMogelijk = indicatieVerstrekkingsbeperkingMogelijk;
        this.indicatieAutomatischFiatteren = indicatieAutomatischFiatteren;
        this.datumOvergangNaarBRP = datumOvergangNaarBRP;

    }

    /**
     * Retourneert ID van Partij.
     *
     * @return ID.
     */
    protected Short getID() {
        return iD;
    }

    /**
     * Retourneert Code van Partij.
     *
     * @return Code.
     */
    public final PartijCodeAttribuut getCode() {
        return code;
    }

    /**
     * Retourneert Naam van Partij.
     *
     * @return Naam.
     */
    public final NaamEnumeratiewaardeAttribuut getNaam() {
        return naam;
    }

    /**
     * Retourneert Datum ingang van Partij.
     *
     * @return Datum ingang.
     */
    public final DatumEvtDeelsOnbekendAttribuut getDatumIngang() {
        return datumIngang;
    }

    /**
     * Retourneert Datum einde van Partij.
     *
     * @return Datum einde.
     */
    public final DatumEvtDeelsOnbekendAttribuut getDatumEinde() {
        return datumEinde;
    }

    /**
     * Retourneert OIN van Partij.
     *
     * @return OIN.
     */
    public final OINAttribuut getOIN() {
        return oIN;
    }

    /**
     * Retourneert Soort van Partij.
     *
     * @return Soort.
     */
    public final SoortPartijAttribuut getSoort() {
        return soort;
    }

    /**
     * Retourneert Verstrekkingsbeperking mogelijk? van Partij.
     *
     * @return Verstrekkingsbeperking mogelijk?.
     */
    public final JaNeeAttribuut getIndicatieVerstrekkingsbeperkingMogelijk() {
        return indicatieVerstrekkingsbeperkingMogelijk;
    }

    /**
     * Retourneert Automatisch fiatteren? van Partij.
     *
     * @return Automatisch fiatteren?.
     */
    public final JaNeeAttribuut getIndicatieAutomatischFiatteren() {
        return indicatieAutomatischFiatteren;
    }

    /**
     * Retourneert Datum overgang naar BRP van Partij.
     *
     * @return Datum overgang naar BRP.
     */
    public final DatumAttribuut getDatumOvergangNaarBRP() {
        return datumOvergangNaarBRP;
    }

    /**
     * Retourneert het Element behorende bij dit objecttype.
     *
     * @return Element enum instantie behorende bij dit objecttype.
     */
    public final ElementEnum getElementIdentificatie() {
        return ElementEnum.PARTIJ;
    }

}
