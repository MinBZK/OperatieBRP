/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.beheer.ist;

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
import nl.bzk.brp.model.algemeen.attribuuttype.ist.LO3AfnemersverstrekkingAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.ist.LO3BerichtaanduidingAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.ist.LO3SelectiesoortAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.PartijCodeAttribuut;

/**
 * Autorisatietabel (tabel 35) uit LO3.x, bevatte een opsomming van door de Minister geautoriseerde en op het
 * GBA-netwerk aangesloten afnemers, gecombineerd met aangesloten gemeenten, de BV BSN, GBA-V, het agentschap BPR.
 *
 * De gegevens uit de autorisatietabel uit LO3.x zijn grotendeels overgezet naar corresponderende tabellen in de BRP.
 * Niet alle gegevens hebben echter een evenknie in BPR. Deze gegevens worden in de onderhavige tabel vastgelegd.
 *
 *
 *
 */
@Entity(name = "beheer.Autorisatietabel")
@Table(schema = "IST", name = "Autorisatietabel")
@Generated(value = "nl.bzk.brp.generatoren.java.BeheerGenerator")
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public class Autorisatietabel {

    @Id
    @SequenceGenerator(name = "AUTORISATIETABEL", sequenceName = "IST.seq_Autorisatietabel")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "AUTORISATIETABEL")
    @JsonProperty
    private Integer iD;

    @Embedded
    @AttributeOverride(name = PartijCodeAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Partijcode"))
    private PartijCodeAttribuut partijcode;

    @Embedded
    @AttributeOverride(name = DatumEvtDeelsOnbekendAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "DatIngangTabelregel"))
    private DatumEvtDeelsOnbekendAttribuut datumIngangTabelregel;

    @Embedded
    @AttributeOverride(name = DatumEvtDeelsOnbekendAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "DatEindeTabelregel"))
    private DatumEvtDeelsOnbekendAttribuut datumEindeTabelregel;

    @Embedded
    @AttributeOverride(name = LO3SelectiesoortAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Selectiesrt"))
    private LO3SelectiesoortAttribuut selectiesoort;

    @Embedded
    @AttributeOverride(name = LO3BerichtaanduidingAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Beraand"))
    private LO3BerichtaanduidingAttribuut berichtaanduiding;

    @Embedded
    @AttributeOverride(name = LO3AfnemersverstrekkingAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Afnemersverstr"))
    private LO3AfnemersverstrekkingAttribuut afnemersverstrekking;

    /**
     * Retourneert ID van Autorisatietabel.
     *
     * @return ID.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public Integer getID() {
        return iD;
    }

    /**
     * Retourneert Partijcode van Autorisatietabel.
     *
     * @return Partijcode.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public PartijCodeAttribuut getPartijcode() {
        return partijcode;
    }

    /**
     * Retourneert Datum ingang tabelregel van Autorisatietabel.
     *
     * @return Datum ingang tabelregel.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public DatumEvtDeelsOnbekendAttribuut getDatumIngangTabelregel() {
        return datumIngangTabelregel;
    }

    /**
     * Retourneert Datum einde tabelregel van Autorisatietabel.
     *
     * @return Datum einde tabelregel.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public DatumEvtDeelsOnbekendAttribuut getDatumEindeTabelregel() {
        return datumEindeTabelregel;
    }

    /**
     * Retourneert Selectiesoort van Autorisatietabel.
     *
     * @return Selectiesoort.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public LO3SelectiesoortAttribuut getSelectiesoort() {
        return selectiesoort;
    }

    /**
     * Retourneert Berichtaanduiding van Autorisatietabel.
     *
     * @return Berichtaanduiding.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public LO3BerichtaanduidingAttribuut getBerichtaanduiding() {
        return berichtaanduiding;
    }

    /**
     * Retourneert Afnemersverstrekking van Autorisatietabel.
     *
     * @return Afnemersverstrekking.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public LO3AfnemersverstrekkingAttribuut getAfnemersverstrekking() {
        return afnemersverstrekking;
    }

    /**
     * Zet ID van Autorisatietabel.
     *
     * @param pID ID.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setID(final Integer pID) {
        this.iD = pID;
    }

    /**
     * Zet Partijcode van Autorisatietabel.
     *
     * @param pPartijcode Partijcode.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setPartijcode(final PartijCodeAttribuut pPartijcode) {
        this.partijcode = pPartijcode;
    }

    /**
     * Zet Datum ingang tabelregel van Autorisatietabel.
     *
     * @param pDatumIngangTabelregel Datum ingang tabelregel.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setDatumIngangTabelregel(final DatumEvtDeelsOnbekendAttribuut pDatumIngangTabelregel) {
        this.datumIngangTabelregel = pDatumIngangTabelregel;
    }

    /**
     * Zet Datum einde tabelregel van Autorisatietabel.
     *
     * @param pDatumEindeTabelregel Datum einde tabelregel.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setDatumEindeTabelregel(final DatumEvtDeelsOnbekendAttribuut pDatumEindeTabelregel) {
        this.datumEindeTabelregel = pDatumEindeTabelregel;
    }

    /**
     * Zet Selectiesoort van Autorisatietabel.
     *
     * @param pSelectiesoort Selectiesoort.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setSelectiesoort(final LO3SelectiesoortAttribuut pSelectiesoort) {
        this.selectiesoort = pSelectiesoort;
    }

    /**
     * Zet Berichtaanduiding van Autorisatietabel.
     *
     * @param pBerichtaanduiding Berichtaanduiding.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setBerichtaanduiding(final LO3BerichtaanduidingAttribuut pBerichtaanduiding) {
        this.berichtaanduiding = pBerichtaanduiding;
    }

    /**
     * Zet Afnemersverstrekking van Autorisatietabel.
     *
     * @param pAfnemersverstrekking Afnemersverstrekking.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setAfnemersverstrekking(final LO3AfnemersverstrekkingAttribuut pAfnemersverstrekking) {
        this.afnemersverstrekking = pAfnemersverstrekking;
    }

}
