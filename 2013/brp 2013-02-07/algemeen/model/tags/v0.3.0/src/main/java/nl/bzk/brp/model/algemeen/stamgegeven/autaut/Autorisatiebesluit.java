/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.autaut;

import javax.persistence.Entity;
import javax.persistence.Table;

import nl.bzk.brp.model.algemeen.attribuuttype.autaut.TekstUitBesluit;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Datum;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaNee;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.StatusHistorie;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.basis.AbstractAutorisatiebesluit;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;


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
@Entity
@Table(schema = "AutAut", name = "Autorisatiebesluit")
public class Autorisatiebesluit extends AbstractAutorisatiebesluit {

    /**
     * Constructor is protected, want de BRP zal geen stamgegevens beheren.
     *
     */
    protected Autorisatiebesluit() {
        super();
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
    protected Autorisatiebesluit(final SoortAutorisatiebesluit soort, final TekstUitBesluit besluittekst,
            // CHECKSTYLE-BRP:ON - MAX PARAMS
            final Partij autoriseerder, final JaNee indicatieModelBesluit, final Autorisatiebesluit gebaseerdOp,
            final JaNee indicatieIngetrokken, final Datum datumBesluit, final Datum datumIngang,
            final Datum datumEinde, final Toestand toestand, final StatusHistorie autorisatiebesluitStatusHis,
            final StatusHistorie bijhoudingsautorisatiebesluitStatusHis)
    {
        super(soort, besluittekst, autoriseerder, indicatieModelBesluit, gebaseerdOp, indicatieIngetrokken,
                datumBesluit, datumIngang, datumEinde, toestand, autorisatiebesluitStatusHis,
                bijhoudingsautorisatiebesluitStatusHis);
    }

}
