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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.Datum;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.GemeenteCode;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamEnumeratiewaarde;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.StatusHistorie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Sector;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPartij;
import nl.bzk.brp.model.basis.AbstractStatischObjectType;
import nl.bzk.brp.model.basis.StamgegevenEntityListener;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Type;


/**
 * Een voor de BRP relevant overheidsorgaan of derde, zoals bedoeld in de Wet BRP, of onderdeel daarvan, die met een
 * bepaalde gerechtvaardigde doelstelling is aangesloten op de BRP.
 *
 * Dit betreft - onder andere - gemeenten, (overige) overheidsorganen en derden.
 *
 * 1. Er is voor gekozen om gemeenten, overige overheidsorganen etc te zien als soorten partijen, en ze allemaal op te
 * nemen in een partij tabel. Van oudsher voorkomende tabellen zoals 'de gemeentetabel' is daarmee een subtype van de
 * partij tabel geworden. RvdP 29 augustus 2011
 *
 * Voor Partij, maar ook Partij/Rol worden toekomst-mutaties toegestaan. Dat betekent dat een normale 'update via
 * trigger' methode NIET werkt voor de A laag: het zetten op "Materieel = Nee" gaat NIET via een update trigger op de
 * C(+D) laag tabel.
 * Derhalve suggestie voor de DBA: de 'materieel?' velden niet via een trigger maar via een view c.q. een 'do instead'
 * actie??
 * RvdP 10 oktober 2011
 *
 *
 *
 */
@Access(value = AccessType.FIELD)
@EntityListeners(value = StamgegevenEntityListener.class)
@MappedSuperclass
public abstract class AbstractPartij extends AbstractStatischObjectType {

    @Id
    @JsonProperty
    private Short                iD;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "Naam"))
    private NaamEnumeratiewaarde naam;

    @Column(name = "Srt")
    private SoortPartij          soort;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "Code"))
    private GemeenteCode         code;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "DatEinde"))
    private Datum                datumEinde;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "DatAanv"))
    private Datum                datumAanvang;

    @ManyToOne
    @JoinColumn(name = "Sector")
    @Fetch(value = FetchMode.JOIN)
    private Sector               sector;

    @ManyToOne
    @JoinColumn(name = "VoortzettendeGem")
    @Fetch(value = FetchMode.JOIN)
    private Partij               voortzettendeGemeente;

    @ManyToOne
    @JoinColumn(name = "OnderdeelVan")
    @Fetch(value = FetchMode.JOIN)
    private Partij               onderdeelVan;

    @Type(type = "StatusHistorie")
    @Column(name = "GemStatusHis")
    private StatusHistorie       gemeenteStatusHis;

    @Type(type = "StatusHistorie")
    @Column(name = "PartijStatusHis")
    private StatusHistorie       partijStatusHis;

    /**
     * Constructor is protected, want de BRP zal geen stamgegevens beheren.
     *
     */
    protected AbstractPartij() {
    }

    /**
     * Constructor die direct alle attributen instantieert.
     * CHECKSTYLE-BRP:OFF - MAX PARAMS
     *
     * @param naam naam van Partij.
     * @param soort soort van Partij.
     * @param code code van Partij.
     * @param datumEinde datumEinde van Partij.
     * @param datumAanvang datumAanvang van Partij.
     * @param sector sector van Partij.
     * @param voortzettendeGemeente voortzettendeGemeente van Partij.
     * @param onderdeelVan onderdeelVan van Partij.
     * @param gemeenteStatusHis gemeenteStatusHis van Partij.
     * @param partijStatusHis partijStatusHis van Partij.
     */
    protected AbstractPartij(final NaamEnumeratiewaarde naam, final SoortPartij soort, final GemeenteCode code,
            final Datum datumEinde, final Datum datumAanvang, final Sector sector, final Partij voortzettendeGemeente,
            final Partij onderdeelVan, final StatusHistorie gemeenteStatusHis, final StatusHistorie partijStatusHis)
    {
        // CHECKSTYLE-BRP:ON - MAX PARAMS
        this.naam = naam;
        this.soort = soort;
        this.code = code;
        this.datumEinde = datumEinde;
        this.datumAanvang = datumAanvang;
        this.sector = sector;
        this.voortzettendeGemeente = voortzettendeGemeente;
        this.onderdeelVan = onderdeelVan;
        this.gemeenteStatusHis = gemeenteStatusHis;
        this.partijStatusHis = partijStatusHis;

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
     * Retourneert Naam van Partij.
     *
     * @return Naam.
     */
    public NaamEnumeratiewaarde getNaam() {
        return naam;
    }

    /**
     * Retourneert Soort van Partij.
     *
     * @return Soort.
     */
    public SoortPartij getSoort() {
        return soort;
    }

    /**
     * Retourneert Code van Partij.
     *
     * @return Code.
     */
    public GemeenteCode getCode() {
        return code;
    }

    /**
     * Retourneert Datum einde van Partij.
     *
     * @return Datum einde.
     */
    public Datum getDatumEinde() {
        return datumEinde;
    }

    /**
     * Retourneert Datum aanvang van Partij.
     *
     * @return Datum aanvang.
     */
    public Datum getDatumAanvang() {
        return datumAanvang;
    }

    /**
     * Retourneert Sector van Partij.
     *
     * @return Sector.
     */
    public Sector getSector() {
        return sector;
    }

    /**
     * Retourneert Voortzettende gemeente van Partij.
     *
     * @return Voortzettende gemeente.
     */
    public Partij getVoortzettendeGemeente() {
        return voortzettendeGemeente;
    }

    /**
     * Retourneert Onderdeel van van Partij.
     *
     * @return Onderdeel van.
     */
    public Partij getOnderdeelVan() {
        return onderdeelVan;
    }

    /**
     * Retourneert Gemeente StatusHis van Partij.
     *
     * @return Gemeente StatusHis.
     */
    public StatusHistorie getGemeenteStatusHis() {
        return gemeenteStatusHis;
    }

    /**
     * Retourneert Partij StatusHis van Partij.
     *
     * @return Partij StatusHis.
     */
    public StatusHistorie getPartijStatusHis() {
        return partijStatusHis;
    }

}
