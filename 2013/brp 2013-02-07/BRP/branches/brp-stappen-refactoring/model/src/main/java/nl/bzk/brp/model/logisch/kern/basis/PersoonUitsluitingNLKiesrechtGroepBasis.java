/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.logisch.kern.basis;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.Datum;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Ja;
import nl.bzk.brp.model.basis.Groep;


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
 * Metaregister versie: 1.6.0.
 * Gegenereerd op: Tue Jan 15 12:53:52 CET 2013.
 */
public interface PersoonUitsluitingNLKiesrechtGroepBasis extends Groep {

    /**
     * Retourneert Uitsluiting NL kiesrecht? van Uitsluiting NL kiesrecht.
     *
     * @return Uitsluiting NL kiesrecht?.
     */
    Ja getIndicatieUitsluitingNLKiesrecht();

    /**
     * Retourneert Datum einde uitsluiting NL kiesrecht van Uitsluiting NL kiesrecht.
     *
     * @return Datum einde uitsluiting NL kiesrecht.
     */
    Datum getDatumEindeUitsluitingNLKiesrecht();

}
