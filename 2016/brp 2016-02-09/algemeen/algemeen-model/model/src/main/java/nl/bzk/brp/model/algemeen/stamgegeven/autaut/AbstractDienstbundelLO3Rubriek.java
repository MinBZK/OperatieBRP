/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.autaut;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.annotation.Generated;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import nl.bzk.brp.model.algemeen.stamgegeven.conv.ConversieLO3Rubriek;
import nl.bzk.brp.model.basis.StamgegevenEntityListener;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Immutable;

/**
 * Het van toepassing zijn van een LO3 Rubriek bij een abonnement.
 *
 * Tijdens de duale periode wordt bij LO3 afnemers een Abonnement aangemaakt waarbij de LO3 Rubrieken geautoriseerd
 * zijn. Zodra een afnemer over is naar de BRP, wordt de inhoud van deze tabel genegeerd.
 *
 * Er is voor gekozen om deze tabel in het AutAut schema te plaatsen ondanks dat deze tabel niet 100% tot het schema
 * behoort, het is immers een tijdelijke toestand. Maar vanwege de samenhang met de Abonnementen, is de tabel in
 * hetzelfde schema geplaatst.
 *
 *
 *
 */
@Access(value = AccessType.FIELD)
@EntityListeners(value = StamgegevenEntityListener.class)
@Immutable
@Generated(value = "nl.bzk.brp.generatoren.java.DynamischeStamgegevensGenerator")
@MappedSuperclass
public abstract class AbstractDienstbundelLO3Rubriek {

    @Id
    @JsonProperty
    private Integer iD;

    @ManyToOne
    @JoinColumn(name = "Dienstbundel")
    @Fetch(value = FetchMode.JOIN)
    private Dienstbundel dienstbundel;

    @ManyToOne
    @JoinColumn(name = "Rubr")
    @Fetch(value = FetchMode.JOIN)
    private ConversieLO3Rubriek rubriek;

    /**
     * Constructor is protected, want de BRP zal geen stamgegevens beheren.
     *
     */
    protected AbstractDienstbundelLO3Rubriek() {
    }

    /**
     * Constructor die direct alle attributen instantieert.
     *
     * @param dienstbundel dienstbundel van DienstbundelLO3Rubriek.
     * @param rubriek rubriek van DienstbundelLO3Rubriek.
     */
    protected AbstractDienstbundelLO3Rubriek(final Dienstbundel dienstbundel, final ConversieLO3Rubriek rubriek) {
        this.dienstbundel = dienstbundel;
        this.rubriek = rubriek;

    }

    /**
     * Retourneert ID van Dienstbundel \ LO3 Rubriek.
     *
     * @return ID.
     */
    protected Integer getID() {
        return iD;
    }

    /**
     * Retourneert Dienstbundel van Dienstbundel \ LO3 Rubriek.
     *
     * @return Dienstbundel.
     */
    public final Dienstbundel getDienstbundel() {
        return dienstbundel;
    }

    /**
     * Retourneert Rubriek van Dienstbundel \ LO3 Rubriek.
     *
     * @return Rubriek.
     */
    public final ConversieLO3Rubriek getRubriek() {
        return rubriek;
    }

}
