/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.logisch.kern;

import nl.bzk.brp.model.logisch.kern.basis.PersoonUitsluitingNLKiesrechtGroepBasis;


/**
 * Gegevens over een eventuele uitsluiting van Nederlandse verkiezingen
 *
 * Vorm van historie: alleen formeel. Motivatie: weliswaar heeft een materi�le tijdslijn betekenis (over welke periode
 * was er uitsluiting, los van het geregistreerd zijn hiervan); echter er is IN KADER VAN DE BRP g��n behoefte om deze
 * te kennen: het is voldoende om te weten of er 'nu' sprake is van uitsluiting. Om die reden wordt de materi�le
 * tijdslijn onderdrukt, en blijft alleen de formele tijdslijn over. Dit is overigens conform LO GBA 3.x.
 *
 *
 *
 * Generator: nl.bzk.brp.generatoren.java.LogischModelGenerator.
 * Generator versie: 1.0-SNAPSHOT.
 * Metaregister versie: 1.1.8.
 * Gegenereerd op: Tue Nov 27 12:05:00 CET 2012.
 */
public interface PersoonUitsluitingNLKiesrechtGroep extends PersoonUitsluitingNLKiesrechtGroepBasis {

}
