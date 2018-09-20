/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.ist;

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
import nl.bzk.brp.model.algemeen.attribuuttype.ist.LO3AfnemersverstrekkingAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.ist.LO3BerichtaanduidingAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.ist.LO3SelectiesoortAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.PartijCodeAttribuut;
import nl.bzk.brp.model.basis.StamgegevenEntityListener;
import org.hibernate.annotations.Immutable;

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
@Access(value = AccessType.FIELD)
@EntityListeners(value = StamgegevenEntityListener.class)
@Immutable
@Generated(value = "nl.bzk.brp.generatoren.java.DynamischeStamgegevensGenerator")
@MappedSuperclass
public abstract class AbstractAutorisatietabel {

    @Id
    @JsonProperty
    private Integer iD;

    @Embedded
    @AttributeOverride(name = PartijCodeAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Partijcode"))
    @JsonProperty
    private PartijCodeAttribuut partijcode;

    @Embedded
    @AttributeOverride(name = DatumEvtDeelsOnbekendAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "DatIngangTabelregel"))
    @JsonProperty
    private DatumEvtDeelsOnbekendAttribuut datumIngangTabelregel;

    @Embedded
    @AttributeOverride(name = DatumEvtDeelsOnbekendAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "DatEindeTabelregel"))
    @JsonProperty
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
     * Constructor is protected, want de BRP zal geen stamgegevens beheren.
     *
     */
    protected AbstractAutorisatietabel() {
    }

    /**
     * Constructor die direct alle attributen instantieert.
     *
     * @param partijcode partijcode van Autorisatietabel.
     * @param datumIngangTabelregel datumIngangTabelregel van Autorisatietabel.
     * @param datumEindeTabelregel datumEindeTabelregel van Autorisatietabel.
     * @param selectiesoort selectiesoort van Autorisatietabel.
     * @param berichtaanduiding berichtaanduiding van Autorisatietabel.
     * @param afnemersverstrekking afnemersverstrekking van Autorisatietabel.
     */
    protected AbstractAutorisatietabel(
        final PartijCodeAttribuut partijcode,
        final DatumEvtDeelsOnbekendAttribuut datumIngangTabelregel,
        final DatumEvtDeelsOnbekendAttribuut datumEindeTabelregel,
        final LO3SelectiesoortAttribuut selectiesoort,
        final LO3BerichtaanduidingAttribuut berichtaanduiding,
        final LO3AfnemersverstrekkingAttribuut afnemersverstrekking)
    {
        this.partijcode = partijcode;
        this.datumIngangTabelregel = datumIngangTabelregel;
        this.datumEindeTabelregel = datumEindeTabelregel;
        this.selectiesoort = selectiesoort;
        this.berichtaanduiding = berichtaanduiding;
        this.afnemersverstrekking = afnemersverstrekking;

    }

    /**
     * Retourneert ID van Autorisatietabel.
     *
     * @return ID.
     */
    protected Integer getID() {
        return iD;
    }

    /**
     * Retourneert Partijcode van Autorisatietabel.
     *
     * @return Partijcode.
     */
    public final PartijCodeAttribuut getPartijcode() {
        return partijcode;
    }

    /**
     * Retourneert Datum ingang tabelregel van Autorisatietabel.
     *
     * @return Datum ingang tabelregel.
     */
    public final DatumEvtDeelsOnbekendAttribuut getDatumIngangTabelregel() {
        return datumIngangTabelregel;
    }

    /**
     * Retourneert Datum einde tabelregel van Autorisatietabel.
     *
     * @return Datum einde tabelregel.
     */
    public final DatumEvtDeelsOnbekendAttribuut getDatumEindeTabelregel() {
        return datumEindeTabelregel;
    }

    /**
     * Retourneert Selectiesoort van Autorisatietabel.
     *
     * @return Selectiesoort.
     */
    public final LO3SelectiesoortAttribuut getSelectiesoort() {
        return selectiesoort;
    }

    /**
     * Retourneert Berichtaanduiding van Autorisatietabel.
     *
     * @return Berichtaanduiding.
     */
    public final LO3BerichtaanduidingAttribuut getBerichtaanduiding() {
        return berichtaanduiding;
    }

    /**
     * Retourneert Afnemersverstrekking van Autorisatietabel.
     *
     * @return Afnemersverstrekking.
     */
    public final LO3AfnemersverstrekkingAttribuut getAfnemersverstrekking() {
        return afnemersverstrekking;
    }

}
