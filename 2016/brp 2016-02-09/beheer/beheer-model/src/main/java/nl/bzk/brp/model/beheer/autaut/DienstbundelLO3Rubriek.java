/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.beheer.autaut;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.HashSet;
import java.util.Set;
import javax.annotation.Generated;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import nl.bzk.brp.model.beheer.conv.ConversieLO3Rubriek;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

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
@Entity(name = "beheer.DienstbundelLO3Rubriek")
@Table(schema = "AutAut", name = "DienstbundelLO3Rubriek")
@Generated(value = "nl.bzk.brp.generatoren.java.BeheerGenerator")
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public class DienstbundelLO3Rubriek {

    @Id
    @SequenceGenerator(name = "DIENSTBUNDELLO3RUBRIEK", sequenceName = "AutAut.seq_DienstbundelLO3Rubriek")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "DIENSTBUNDELLO3RUBRIEK")
    @JsonProperty
    private Integer iD;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Dienstbundel")
    private Dienstbundel dienstbundel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Rubr")
    private ConversieLO3Rubriek rubriek;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "dienstbundelLO3Rubriek", cascade = CascadeType.ALL, orphanRemoval = true)
    @Fetch(value = FetchMode.SELECT)
    private Set<HisDienstbundelLO3Rubriek> hisDienstbundelLO3RubriekLijst = new HashSet<>();

    /**
     * Retourneert ID van Dienstbundel \ LO3 Rubriek.
     *
     * @return ID.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public Integer getID() {
        return iD;
    }

    /**
     * Retourneert Dienstbundel van Dienstbundel \ LO3 Rubriek.
     *
     * @return Dienstbundel.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public Dienstbundel getDienstbundel() {
        return dienstbundel;
    }

    /**
     * Retourneert Rubriek van Dienstbundel \ LO3 Rubriek.
     *
     * @return Rubriek.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public ConversieLO3Rubriek getRubriek() {
        return rubriek;
    }

    /**
     * Retourneert Standaard van Dienstbundel \ LO3 Rubriek.
     *
     * @return Standaard.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public Set<HisDienstbundelLO3Rubriek> getHisDienstbundelLO3RubriekLijst() {
        return hisDienstbundelLO3RubriekLijst;
    }

    /**
     * Zet ID van Dienstbundel \ LO3 Rubriek.
     *
     * @param pID ID.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setID(final Integer pID) {
        this.iD = pID;
    }

    /**
     * Zet Dienstbundel van Dienstbundel \ LO3 Rubriek.
     *
     * @param pDienstbundel Dienstbundel.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setDienstbundel(final Dienstbundel pDienstbundel) {
        this.dienstbundel = pDienstbundel;
    }

    /**
     * Zet Rubriek van Dienstbundel \ LO3 Rubriek.
     *
     * @param pRubriek Rubriek.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setRubriek(final ConversieLO3Rubriek pRubriek) {
        this.rubriek = pRubriek;
    }

}
