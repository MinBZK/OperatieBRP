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
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.OmschrijvingEnumeratiewaardeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.RechtsgrondCodeAttribuut;
import nl.bzk.brp.model.basis.BestaansPeriodeStamgegeven;
import nl.bzk.brp.model.basis.ElementIdentificeerbaar;
import nl.bzk.brp.model.basis.StamgegevenEntityListener;
import nl.bzk.brp.model.basis.SynchroniseerbaarStamgegeven;
import org.hibernate.annotations.Immutable;

/**
 * In de Wet BRP genoemde rechtsgrond.
 *
 * 1. Een nette definitie van 'rechtsgrond' is niet snel gevonden. Een definitie uit een proefschrift (
 * https://openaccess.leidenuniv.nl/bitstream/handle/1887/11859/02_02.pdf?sequence=6 ) benoemd het als een 'rechtsnorm'.
 * In de Wet BRP komt het wordt in artikel 2.7 lid b sub 2 gesproken over
 * "...rechtsgrond krachtens welke gegevens over het Nederlanderschap...", wat refereert naar de relevante artikelen die
 * van toepassing zijn op het verlenen van het Nederlanderschap. In paragraaf 5.2.4. van de (voorlopige, niet
 * definitieve) memorie van toelichting wordt gesproken over "... rechtsgrond het gegeven is opgenomen (...) Als de
 * gegevens zijn ontleendaan een opgave van een (buitenlands) zusterorgaan van een aangewezen bestuursorgaan" waarbij
 * klaarblijkelijk het verdrag op basis waarvan de gegevens zijn overgenomen worden aangeduid met rechtsgrond. 2. Zie
 * Objecttype Soort Rechtsgrond voor een uitleg over het registeren van Rechtsgronden.
 *
 *
 *
 */
@Access(value = AccessType.FIELD)
@EntityListeners(value = StamgegevenEntityListener.class)
@Immutable
@Generated(value = "nl.bzk.brp.generatoren.java.DynamischeStamgegevensGenerator")
@MappedSuperclass
public abstract class AbstractRechtsgrond implements SynchroniseerbaarStamgegeven, BestaansPeriodeStamgegeven, ElementIdentificeerbaar {

    @Id
    @JsonProperty
    private Short iD;

    @Embedded
    @AttributeOverride(name = RechtsgrondCodeAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Code"))
    @JsonProperty
    private RechtsgrondCodeAttribuut code;

    @Column(name = "Srt")
    private SoortRechtsgrond soort;

    @Embedded
    @AttributeOverride(name = OmschrijvingEnumeratiewaardeAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Oms"))
    private OmschrijvingEnumeratiewaardeAttribuut omschrijving;

    @Embedded
    @AttributeOverride(name = JaAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "IndLeidtTotStrijdigheid"))
    private JaAttribuut indicatieLeidtTotStrijdigheid;

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
    protected AbstractRechtsgrond() {
    }

    /**
     * Constructor die direct alle attributen instantieert.
     *
     * @param code code van Rechtsgrond.
     * @param soort soort van Rechtsgrond.
     * @param omschrijving omschrijving van Rechtsgrond.
     * @param indicatieLeidtTotStrijdigheid indicatieLeidtTotStrijdigheid van Rechtsgrond.
     * @param datumAanvangGeldigheid datumAanvangGeldigheid van Rechtsgrond.
     * @param datumEindeGeldigheid datumEindeGeldigheid van Rechtsgrond.
     */
    protected AbstractRechtsgrond(
        final RechtsgrondCodeAttribuut code,
        final SoortRechtsgrond soort,
        final OmschrijvingEnumeratiewaardeAttribuut omschrijving,
        final JaAttribuut indicatieLeidtTotStrijdigheid,
        final DatumEvtDeelsOnbekendAttribuut datumAanvangGeldigheid,
        final DatumEvtDeelsOnbekendAttribuut datumEindeGeldigheid)
    {
        this.code = code;
        this.soort = soort;
        this.omschrijving = omschrijving;
        this.indicatieLeidtTotStrijdigheid = indicatieLeidtTotStrijdigheid;
        this.datumAanvangGeldigheid = datumAanvangGeldigheid;
        this.datumEindeGeldigheid = datumEindeGeldigheid;

    }

    /**
     * Retourneert ID van Rechtsgrond.
     *
     * @return ID.
     */
    protected Short getID() {
        return iD;
    }

    /**
     * Retourneert Code van Rechtsgrond.
     *
     * @return Code.
     */
    public final RechtsgrondCodeAttribuut getCode() {
        return code;
    }

    /**
     * Retourneert Soort van Rechtsgrond.
     *
     * @return Soort.
     */
    public final SoortRechtsgrond getSoort() {
        return soort;
    }

    /**
     * Retourneert Omschrijving van Rechtsgrond.
     *
     * @return Omschrijving.
     */
    public final OmschrijvingEnumeratiewaardeAttribuut getOmschrijving() {
        return omschrijving;
    }

    /**
     * Retourneert Leidt tot strijdigheid? van Rechtsgrond.
     *
     * @return Leidt tot strijdigheid?.
     */
    public final JaAttribuut getIndicatieLeidtTotStrijdigheid() {
        return indicatieLeidtTotStrijdigheid;
    }

    /**
     * Retourneert de datum aanvang geldigheid voor Rechtsgrond.
     *
     * @return Datum aanvang geldigheid voor Rechtsgrond
     */
    public final DatumEvtDeelsOnbekendAttribuut getDatumAanvangGeldigheid() {
        return datumAanvangGeldigheid;
    }

    /**
     * Retourneert de datum einde geldigheid voor Rechtsgrond.
     *
     * @return Datum einde geldigheid voor Rechtsgrond
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
        return ElementEnum.RECHTSGROND;
    }

}
