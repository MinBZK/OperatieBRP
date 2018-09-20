/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.autaut.basis;

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

import nl.bzk.brp.model.algemeen.attribuuttype.autaut.TekstUitBesluit;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Datum;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaNee;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.StatusHistorie;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Autorisatiebesluit;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.SoortAutorisatiebesluit;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Toestand;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.basis.AbstractStatischObjectType;
import nl.bzk.brp.model.basis.StamgegevenEntityListener;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Type;


/**
 * Een besluit van de minister van BZK, van een College van Burgemeester en Wethouders, of van de Staat der Nederlanden,
 * dat de juridische grondslag vormt voor het autoriseren van Partijen.
 *
 * Alle bijhoudingsacties op de BRP dienen expliciet toegestaan te worden doordat er een Autorisatiebesluit van het
 * soort 'bijhouden' aanwezig is. Naast de initi�le Autorisatie aan gemeenten en aan minister - die als een soort
 * 'bootstrap' is verricht door 'de Staat der Nederlanden' - kunnen gemeenten en minister andere partijen autoriseren
 * voor bepaalde soorten bijhoudingen.
 *
 * 1. De constructie met Model Autorisatiebesluit & Gebaseerd op is wat technisch van aard. Deze is bedacht om de
 * beheerder van Autorisatiebesluiten te ondersteunen in het efficient vastleggen van Autorisatiebesluiten en wat
 * daaraan vasthangt (tot en met Abonnementen).
 * De constructie is beperkt gehouden: zo is de keten 'Autorisatiebesluit (gebaseerd op) Autorisatiebesluit (gebaseerd
 * op) Autorisatiebesluit gelimiteerd tot ��n diep:
 * een Autorisatiebesluit kan alleen gebaseerd zijn op een Autorisatiebesluit die zelf een Model is, en een
 * Autorisatiebesluit dat een Model is, is niet gebaseerd op een ander Autorisatiebesluit.
 * Zo wordt de 'boom' beperkt. Dit is gedaan om complete boomstructuren te vermijden, die op termijn de complexiteit van
 * het beheer zou verhogen.
 * RvdP 14 oktober 2011.
 *
 *
 *
 */
@Access(value = AccessType.FIELD)
@EntityListeners(value = StamgegevenEntityListener.class)
@MappedSuperclass
public abstract class AbstractAutorisatiebesluit extends AbstractStatischObjectType {

    @Id
    @JsonProperty
    private Integer                 iD;

    @Column(name = "Srt")
    private SoortAutorisatiebesluit soort;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "Besluittekst"))
    private TekstUitBesluit         besluittekst;

    @ManyToOne
    @JoinColumn(name = "Autoriseerder")
    @Fetch(value = FetchMode.JOIN)
    private Partij                  autoriseerder;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "IndModelBesluit"))
    private JaNee                   indicatieModelBesluit;

    @ManyToOne
    @JoinColumn(name = "GebaseerdOp")
    @Fetch(value = FetchMode.JOIN)
    private Autorisatiebesluit      gebaseerdOp;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "IndIngetrokken"))
    private JaNee                   indicatieIngetrokken;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "DatBesluit"))
    private Datum                   datumBesluit;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "DatIngang"))
    private Datum                   datumIngang;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "DatEinde"))
    private Datum                   datumEinde;

    @Column(name = "Toestand")
    private Toestand                toestand;

    @Type(type = "StatusHistorie")
    @Column(name = "AutorisatiebesluitStatusHis")
    private StatusHistorie          autorisatiebesluitStatusHis;

    @Type(type = "StatusHistorie")
    @Column(name = "BijhautorisatiebesluitStatus")
    private StatusHistorie          bijhoudingsautorisatiebesluitStatusHis;

    /**
     * Constructor is protected, want de BRP zal geen stamgegevens beheren.
     *
     */
    protected AbstractAutorisatiebesluit() {
    }

    /**
     * Constructor die direct alle attributen instantieert.
     * CHECKSTYLE-BRP:OFF - MAX PARAMS
     *
     * @param soort soort van Autorisatiebesluit.
     * @param besluittekst besluittekst van Autorisatiebesluit.
     * @param autoriseerder autoriseerder van Autorisatiebesluit.
     * @param indicatieModelBesluit indicatieModelBesluit van Autorisatiebesluit.
     * @param gebaseerdOp gebaseerdOp van Autorisatiebesluit.
     * @param indicatieIngetrokken indicatieIngetrokken van Autorisatiebesluit.
     * @param datumBesluit datumBesluit van Autorisatiebesluit.
     * @param datumIngang datumIngang van Autorisatiebesluit.
     * @param datumEinde datumEinde van Autorisatiebesluit.
     * @param toestand toestand van Autorisatiebesluit.
     * @param autorisatiebesluitStatusHis autorisatiebesluitStatusHis van Autorisatiebesluit.
     * @param bijhoudingsautorisatiebesluitStatusHis bijhoudingsautorisatiebesluitStatusHis van Autorisatiebesluit.
     */
    protected AbstractAutorisatiebesluit(final SoortAutorisatiebesluit soort, final TekstUitBesluit besluittekst,
            final Partij autoriseerder, final JaNee indicatieModelBesluit, final Autorisatiebesluit gebaseerdOp,
            final JaNee indicatieIngetrokken, final Datum datumBesluit, final Datum datumIngang,
            final Datum datumEinde, final Toestand toestand, final StatusHistorie autorisatiebesluitStatusHis,
            final StatusHistorie bijhoudingsautorisatiebesluitStatusHis)
    {
        // CHECKSTYLE-BRP:ON - MAX PARAMS
        this.soort = soort;
        this.besluittekst = besluittekst;
        this.autoriseerder = autoriseerder;
        this.indicatieModelBesluit = indicatieModelBesluit;
        this.gebaseerdOp = gebaseerdOp;
        this.indicatieIngetrokken = indicatieIngetrokken;
        this.datumBesluit = datumBesluit;
        this.datumIngang = datumIngang;
        this.datumEinde = datumEinde;
        this.toestand = toestand;
        this.autorisatiebesluitStatusHis = autorisatiebesluitStatusHis;
        this.bijhoudingsautorisatiebesluitStatusHis = bijhoudingsautorisatiebesluitStatusHis;

    }

    /**
     * Retourneert ID van Autorisatiebesluit.
     *
     * @return ID.
     */
    protected Integer getID() {
        return iD;
    }

    /**
     * Retourneert Soort van Autorisatiebesluit.
     *
     * @return Soort.
     */
    public SoortAutorisatiebesluit getSoort() {
        return soort;
    }

    /**
     * Retourneert Besluittekst van Autorisatiebesluit.
     *
     * @return Besluittekst.
     */
    public TekstUitBesluit getBesluittekst() {
        return besluittekst;
    }

    /**
     * Retourneert Autoriseerder van Autorisatiebesluit.
     *
     * @return Autoriseerder.
     */
    public Partij getAutoriseerder() {
        return autoriseerder;
    }

    /**
     * Retourneert Model besluit? van Autorisatiebesluit.
     *
     * @return Model besluit?.
     */
    public JaNee getIndicatieModelBesluit() {
        return indicatieModelBesluit;
    }

    /**
     * Retourneert Gebaseerd op van Autorisatiebesluit.
     *
     * @return Gebaseerd op.
     */
    public Autorisatiebesluit getGebaseerdOp() {
        return gebaseerdOp;
    }

    /**
     * Retourneert Ingetrokken? van Autorisatiebesluit.
     *
     * @return Ingetrokken?.
     */
    public JaNee getIndicatieIngetrokken() {
        return indicatieIngetrokken;
    }

    /**
     * Retourneert Datum besluit van Autorisatiebesluit.
     *
     * @return Datum besluit.
     */
    public Datum getDatumBesluit() {
        return datumBesluit;
    }

    /**
     * Retourneert Datum ingang van Autorisatiebesluit.
     *
     * @return Datum ingang.
     */
    public Datum getDatumIngang() {
        return datumIngang;
    }

    /**
     * Retourneert Datum einde van Autorisatiebesluit.
     *
     * @return Datum einde.
     */
    public Datum getDatumEinde() {
        return datumEinde;
    }

    /**
     * Retourneert Toestand van Autorisatiebesluit.
     *
     * @return Toestand.
     */
    public Toestand getToestand() {
        return toestand;
    }

    /**
     * Retourneert Autorisatiebesluit StatusHis van Autorisatiebesluit.
     *
     * @return Autorisatiebesluit StatusHis.
     */
    public StatusHistorie getAutorisatiebesluitStatusHis() {
        return autorisatiebesluitStatusHis;
    }

    /**
     * Retourneert Bijhoudingsautorisatiebesluit StatusHis van Autorisatiebesluit.
     *
     * @return Bijhoudingsautorisatiebesluit StatusHis.
     */
    public StatusHistorie getBijhoudingsautorisatiebesluitStatusHis() {
        return bijhoudingsautorisatiebesluitStatusHis;
    }

}
