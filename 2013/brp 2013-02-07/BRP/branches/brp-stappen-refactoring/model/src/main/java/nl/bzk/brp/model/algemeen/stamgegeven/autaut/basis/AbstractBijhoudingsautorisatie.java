/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.autaut.basis;

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
import nl.bzk.brp.model.algemeen.attribuuttype.kern.OmschrijvingEnumeratiewaarde;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.StatusHistorie;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Autorisatiebesluit;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.SoortBevoegdheid;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Toestand;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.CategoriePersonen;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPartij;
import nl.bzk.brp.model.basis.AbstractStatischObjectType;
import nl.bzk.brp.model.basis.StamgegevenEntityListener;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Type;


/**
 * De aan een Partij - of aan Partijen van een soort - uitgereikte autorisatie om bijhoudingen te doen.
 *
 * Een bijhouding in de BRP vindt plaats doordat een Partij expliciet geautoriseerd is om een bepaalde soort bijhouding
 * te doen, voor een bepaalde soort bijhoudingspopulatie. De vastlegging hiervan gebeurd middels de
 * Bijhoudingsautorisatie.
 * De autorisatie kan zijn doordat de Partij zelf geautoriseerd is, doordat de Partij van een Soort partij is die
 * geautoriseerd is. Hierbij dient de Autoriseerderende Partij zelf ook geautoriseerd te zijn.
 *
 *
 *
 * Generator: nl.bzk.brp.generatoren.java.DynamischeStamgegevensGenerator.
 * Metaregister versie: 1.6.0.
 * Generator versie: 1.0-SNAPSHOT.
 * Generator gebouwd op: 2013-01-15 12:43:16.
 * Gegenereerd op: Tue Jan 15 12:53:51 CET 2013.
 */
@Access(value = AccessType.FIELD)
@EntityListeners(value = StamgegevenEntityListener.class)
@MappedSuperclass
public abstract class AbstractBijhoudingsautorisatie extends AbstractStatischObjectType {

    @Id
    private Short                        iD;

    @ManyToOne
    @JoinColumn(name = "Bijhautorisatiebesluit")
    @Fetch(value = FetchMode.JOIN)
    private Autorisatiebesluit           bijhoudingsautorisatiebesluit;

    @Column(name = "SrtBevoegdheid")
    private SoortBevoegdheid             soortBevoegdheid;

    @Column(name = "GeautoriseerdeSrtPartij")
    private SoortPartij                  geautoriseerdeSoortPartij;

    @ManyToOne
    @JoinColumn(name = "GeautoriseerdePartij")
    @Fetch(value = FetchMode.JOIN)
    private Partij                       geautoriseerdePartij;

    @Column(name = "Toestand")
    private Toestand                     toestand;

    @Column(name = "CategoriePersonen")
    private CategoriePersonen            categoriePersonen;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "Oms"))
    private OmschrijvingEnumeratiewaarde omschrijving;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "DatIngang"))
    private Datum                        datumIngang;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "DatEinde"))
    private Datum                        datumEinde;

    @Type(type = "StatusHistorie")
    @Column(name = "BijhautorisatieStatusHis")
    private StatusHistorie               bijhoudingsautorisatieStatusHis;

    /**
     * Constructor is protected, want de BRP zal geen stamgegevens beheren.
     *
     */
    protected AbstractBijhoudingsautorisatie() {
    }

    /**
     * Constructor die direct alle attributen instantieert.
     *
     * @param bijhoudingsautorisatiebesluit bijhoudingsautorisatiebesluit van Bijhoudingsautorisatie.
     * @param soortBevoegdheid soortBevoegdheid van Bijhoudingsautorisatie.
     * @param geautoriseerdeSoortPartij geautoriseerdeSoortPartij van Bijhoudingsautorisatie.
     * @param geautoriseerdePartij geautoriseerdePartij van Bijhoudingsautorisatie.
     * @param toestand toestand van Bijhoudingsautorisatie.
     * @param categoriePersonen categoriePersonen van Bijhoudingsautorisatie.
     * @param omschrijving omschrijving van Bijhoudingsautorisatie.
     * @param datumIngang datumIngang van Bijhoudingsautorisatie.
     * @param datumEinde datumEinde van Bijhoudingsautorisatie.
     * @param bijhoudingsautorisatieStatusHis bijhoudingsautorisatieStatusHis van Bijhoudingsautorisatie.
     */
    protected AbstractBijhoudingsautorisatie(final Autorisatiebesluit bijhoudingsautorisatiebesluit,
            final SoortBevoegdheid soortBevoegdheid, final SoortPartij geautoriseerdeSoortPartij,
            final Partij geautoriseerdePartij, final Toestand toestand, final CategoriePersonen categoriePersonen,
            final OmschrijvingEnumeratiewaarde omschrijving, final Datum datumIngang, final Datum datumEinde,
            final StatusHistorie bijhoudingsautorisatieStatusHis)
    {
        this.bijhoudingsautorisatiebesluit = bijhoudingsautorisatiebesluit;
        this.soortBevoegdheid = soortBevoegdheid;
        this.geautoriseerdeSoortPartij = geautoriseerdeSoortPartij;
        this.geautoriseerdePartij = geautoriseerdePartij;
        this.toestand = toestand;
        this.categoriePersonen = categoriePersonen;
        this.omschrijving = omschrijving;
        this.datumIngang = datumIngang;
        this.datumEinde = datumEinde;
        this.bijhoudingsautorisatieStatusHis = bijhoudingsautorisatieStatusHis;

    }

    /**
     * Retourneert ID van Bijhoudingsautorisatie.
     *
     * @return ID.
     */
    protected Short getID() {
        return iD;
    }

    /**
     * Retourneert Bijhoudingsautorisatiebesluit van Bijhoudingsautorisatie.
     *
     * @return Bijhoudingsautorisatiebesluit.
     */
    public Autorisatiebesluit getBijhoudingsautorisatiebesluit() {
        return bijhoudingsautorisatiebesluit;
    }

    /**
     * Retourneert Soort bevoegdheid van Bijhoudingsautorisatie.
     *
     * @return Soort bevoegdheid.
     */
    public SoortBevoegdheid getSoortBevoegdheid() {
        return soortBevoegdheid;
    }

    /**
     * Retourneert Geautoriseerde soort partij van Bijhoudingsautorisatie.
     *
     * @return Geautoriseerde soort partij.
     */
    public SoortPartij getGeautoriseerdeSoortPartij() {
        return geautoriseerdeSoortPartij;
    }

    /**
     * Retourneert Geautoriseerde partij van Bijhoudingsautorisatie.
     *
     * @return Geautoriseerde partij.
     */
    public Partij getGeautoriseerdePartij() {
        return geautoriseerdePartij;
    }

    /**
     * Retourneert Toestand van Bijhoudingsautorisatie.
     *
     * @return Toestand.
     */
    public Toestand getToestand() {
        return toestand;
    }

    /**
     * Retourneert Categorie personen van Bijhoudingsautorisatie.
     *
     * @return Categorie personen.
     */
    public CategoriePersonen getCategoriePersonen() {
        return categoriePersonen;
    }

    /**
     * Retourneert Omschrijving van Bijhoudingsautorisatie.
     *
     * @return Omschrijving.
     */
    public OmschrijvingEnumeratiewaarde getOmschrijving() {
        return omschrijving;
    }

    /**
     * Retourneert Datum ingang van Bijhoudingsautorisatie.
     *
     * @return Datum ingang.
     */
    public Datum getDatumIngang() {
        return datumIngang;
    }

    /**
     * Retourneert Datum einde van Bijhoudingsautorisatie.
     *
     * @return Datum einde.
     */
    public Datum getDatumEinde() {
        return datumEinde;
    }

    /**
     * Retourneert Bijhoudingsautorisatie StatusHis van Bijhoudingsautorisatie.
     *
     * @return Bijhoudingsautorisatie StatusHis.
     */
    public StatusHistorie getBijhoudingsautorisatieStatusHis() {
        return bijhoudingsautorisatieStatusHis;
    }

}
