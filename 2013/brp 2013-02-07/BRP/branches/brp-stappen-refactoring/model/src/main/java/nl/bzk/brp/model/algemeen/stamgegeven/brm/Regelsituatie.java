/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.brm;

import javax.persistence.Entity;
import javax.persistence.Table;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaNee;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.StatusHistorie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Bijhoudingsaard;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RedenOpschorting;
import nl.bzk.brp.model.algemeen.stamgegeven.brm.basis.AbstractRegelsituatie;


/**
 * De situaties waarin de Regelimplementatie van toepassing is.
 *
 * 1. De runtime beheerde gegevens zijn in een aparte entiteit ondergebracht: Deze. Hierdoor is regel zelf release time
 * beheerd geworden. RvdP 20 december 2011, aangepast 24 januari 2012 en 16 april 2012.
 *
 * 2. Bij de naamgeving van deze entiteit is aangesloten bij het patroon bij bijhoudingssituaties: deze entiteit
 * beschrijft namelijk de situaties waarin een bepaalde regel/soort bericht 'van toepassing' is. In tegenstelling tot de
 * situatie bij 'bijhoudingsautorisatie', echter, is het 'effect' nu vastgelegd bij de "...situatie" entiteit, in plaats
 * van bij de "ouder" entiteit. Reden is dat, in tegenstelling tot bijhoudingssituatie, het effect (weigeren,
 * waarschuwen, ...) op het niveau van een individuele situatie is, en niet voor het 'geheel'.
 * Het geheel doortrekken van de naam zou 'regel/soortbericht situatie' hebben opgeleverd; deze term is echter minder
 * begrijpelijk dan 'regelsituatie'. Om die reden is voor regelsituatie gekozen.
 * RvdP 24 januari 2012, aangepast 16 april 2012.
 *
 *
 *
 * Generator: nl.bzk.brp.generatoren.java.DynamischStamgegevensModelGenerator.
 * Metaregister versie: 1.1.15.
 * Generator versie: 1.0-SNAPSHOT.
 * Generator gebouwd op: 2012-11-28 16:34:59.
 * Gegenereerd op: Wed Nov 28 16:38:31 CET 2012.
 */
@Entity
@Table(schema = "BRM", name = "Regelsituatie")
public class Regelsituatie extends AbstractRegelsituatie {

    /**
     * Constructor is protected, want de BRP zal geen stamgegevens beheren.
     *
     */
    protected Regelsituatie() {
        super();
    }

    /**
     * Constructor die direct alle attributen instantieert.
     *
     * @param regelimplementatie regelimplementatie van Regelsituatie.
     * @param bijhoudingsaard bijhoudingsaard van Regelsituatie.
     * @param indicatieOpgeschort indicatieOpgeschort van Regelsituatie.
     * @param redenOpschorting redenOpschorting van Regelsituatie.
     * @param effect effect van Regelsituatie.
     * @param indicatieActief indicatieActief van Regelsituatie.
     * @param regelsituatieStatusHis regelsituatieStatusHis van Regelsituatie.
     */
    protected Regelsituatie(final RegelSoortBericht regelimplementatie,
            final Bijhoudingsaard bijhoudingsaard, final JaNee indicatieOpgeschort,
            final RedenOpschorting redenOpschorting, final Regeleffect effect, final JaNee indicatieActief,
            final StatusHistorie regelsituatieStatusHis)
    {
        super(regelimplementatie, bijhoudingsaard, indicatieOpgeschort, redenOpschorting, effect,
                indicatieActief, regelsituatieStatusHis);
    }

}
