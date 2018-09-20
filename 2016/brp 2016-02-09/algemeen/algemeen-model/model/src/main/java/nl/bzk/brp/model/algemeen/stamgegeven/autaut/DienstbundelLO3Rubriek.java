/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.autaut;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;
import nl.bzk.brp.model.algemeen.stamgegeven.conv.ConversieLO3Rubriek;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
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
@Table(schema = "AutAut", name = "DienstbundelLO3Rubriek")
@Access(value = AccessType.FIELD)
@Entity
//@EntityListeners(value = StamgegevenEntityListener.class) handmatige wijziging
@Immutable
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class DienstbundelLO3Rubriek extends AbstractDienstbundelLO3Rubriek {

    /**
     * Constructor is protected, want de BRP zal geen stamgegevens beheren.
     *
     */
    protected DienstbundelLO3Rubriek() {
        super();
    }

    /**
     * Constructor die direct alle attributen instantieert.
     *
     * @param dienstbundel dienstbundel van DienstbundelLO3Rubriek.
     * @param rubriek rubriek van DienstbundelLO3Rubriek.
     */
    protected DienstbundelLO3Rubriek(final Dienstbundel dienstbundel, final ConversieLO3Rubriek rubriek) {
        super(dienstbundel, rubriek);
    }

}
