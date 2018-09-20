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
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.OmschrijvingEnumeratiewaardeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.RechtsgrondCodeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortRechtsgrond;

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
@Entity(name = "beheer.Rechtsgrond")
@Table(schema = "Kern", name = "Rechtsgrond")
@Generated(value = "nl.bzk.brp.generatoren.java.BeheerGenerator")
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public class Rechtsgrond {

    @Id
    @SequenceGenerator(name = "RECHTSGROND", sequenceName = "Kern.seq_Rechtsgrond")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "RECHTSGROND")
    @JsonProperty
    private Short iD;

    @Embedded
    @AttributeOverride(name = RechtsgrondCodeAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Code"))
    private RechtsgrondCodeAttribuut code;

    @Column(name = "Srt")
    @Enumerated
    private SoortRechtsgrond soort;

    @Embedded
    @AttributeOverride(name = OmschrijvingEnumeratiewaardeAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Oms"))
    private OmschrijvingEnumeratiewaardeAttribuut omschrijving;

    @Embedded
    @AttributeOverride(name = JaAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "IndLeidtTotStrijdigheid"))
    private JaAttribuut indicatieLeidtTotStrijdigheid;

    @Embedded
    @AttributeOverride(name = DatumEvtDeelsOnbekendAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "DatAanvGel"))
    private DatumEvtDeelsOnbekendAttribuut datumAanvangGeldigheid;

    @Embedded
    @AttributeOverride(name = DatumEvtDeelsOnbekendAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "DatEindeGel"))
    private DatumEvtDeelsOnbekendAttribuut datumEindeGeldigheid;

    /**
     * Retourneert ID van Rechtsgrond.
     *
     * @return ID.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public Short getID() {
        return iD;
    }

    /**
     * Retourneert Code van Rechtsgrond.
     *
     * @return Code.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public RechtsgrondCodeAttribuut getCode() {
        return code;
    }

    /**
     * Retourneert Soort van Rechtsgrond.
     *
     * @return Soort.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public SoortRechtsgrond getSoort() {
        return soort;
    }

    /**
     * Retourneert Omschrijving van Rechtsgrond.
     *
     * @return Omschrijving.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public OmschrijvingEnumeratiewaardeAttribuut getOmschrijving() {
        return omschrijving;
    }

    /**
     * Retourneert Leidt tot strijdigheid? van Rechtsgrond.
     *
     * @return Leidt tot strijdigheid?.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public JaAttribuut getIndicatieLeidtTotStrijdigheid() {
        return indicatieLeidtTotStrijdigheid;
    }

    /**
     * Retourneert Datum aanvang geldigheid van Rechtsgrond.
     *
     * @return Datum aanvang geldigheid.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public DatumEvtDeelsOnbekendAttribuut getDatumAanvangGeldigheid() {
        return datumAanvangGeldigheid;
    }

    /**
     * Retourneert Datum einde geldigheid van Rechtsgrond.
     *
     * @return Datum einde geldigheid.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public DatumEvtDeelsOnbekendAttribuut getDatumEindeGeldigheid() {
        return datumEindeGeldigheid;
    }

    /**
     * Zet ID van Rechtsgrond.
     *
     * @param pID ID.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setID(final Short pID) {
        this.iD = pID;
    }

    /**
     * Zet Code van Rechtsgrond.
     *
     * @param pCode Code.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setCode(final RechtsgrondCodeAttribuut pCode) {
        this.code = pCode;
    }

    /**
     * Zet Soort van Rechtsgrond.
     *
     * @param pSoort Soort.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setSoort(final SoortRechtsgrond pSoort) {
        this.soort = pSoort;
    }

    /**
     * Zet Omschrijving van Rechtsgrond.
     *
     * @param pOmschrijving Omschrijving.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setOmschrijving(final OmschrijvingEnumeratiewaardeAttribuut pOmschrijving) {
        this.omschrijving = pOmschrijving;
    }

    /**
     * Zet Leidt tot strijdigheid? van Rechtsgrond.
     *
     * @param pIndicatieLeidtTotStrijdigheid Leidt tot strijdigheid?.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setIndicatieLeidtTotStrijdigheid(final JaAttribuut pIndicatieLeidtTotStrijdigheid) {
        this.indicatieLeidtTotStrijdigheid = pIndicatieLeidtTotStrijdigheid;
    }

    /**
     * Zet Datum aanvang geldigheid van Rechtsgrond.
     *
     * @param pDatumAanvangGeldigheid Datum aanvang geldigheid.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setDatumAanvangGeldigheid(final DatumEvtDeelsOnbekendAttribuut pDatumAanvangGeldigheid) {
        this.datumAanvangGeldigheid = pDatumAanvangGeldigheid;
    }

    /**
     * Zet Datum einde geldigheid van Rechtsgrond.
     *
     * @param pDatumEindeGeldigheid Datum einde geldigheid.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setDatumEindeGeldigheid(final DatumEvtDeelsOnbekendAttribuut pDatumEindeGeldigheid) {
        this.datumEindeGeldigheid = pDatumEindeGeldigheid;
    }

}
