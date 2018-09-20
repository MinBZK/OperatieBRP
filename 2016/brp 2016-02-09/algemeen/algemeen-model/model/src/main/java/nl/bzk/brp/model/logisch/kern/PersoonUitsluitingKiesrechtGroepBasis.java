/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.logisch.kern;

import javax.annotation.Generated;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaAttribuut;
import nl.bzk.brp.model.basis.Groep;

/**
 * Gegevens over een eventuele uitsluiting van (Nederlandse) verkiezingen
 *
 * Vorm van historie: alleen formeel. Motivatie: weliswaar heeft een materiële tijdslijn betekenis (over welke periode
 * was er uitsluiting, los van het geregistreerd zijn hiervan); echter er is IN KADER VAN DE BRP géén behoefte om deze
 * te kennen: het is voldoende om te weten of er 'nu' sprake is van uitsluiting. Om die reden wordt de materiële
 * tijdslijn onderdrukt, en blijft alleen de formele tijdslijn over. Dit is overigens conform LO GBA 3.x.
 *
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.LogischModelGenerator")
public interface PersoonUitsluitingKiesrechtGroepBasis extends Groep {

    /**
     * Retourneert Uitsluiting kiesrecht? van Uitsluiting kiesrecht.
     *
     * @return Uitsluiting kiesrecht?.
     */
    JaAttribuut getIndicatieUitsluitingKiesrecht();

    /**
     * Retourneert Datum voorzien einde uitsluiting kiesrecht van Uitsluiting kiesrecht.
     *
     * @return Datum voorzien einde uitsluiting kiesrecht.
     */
    DatumEvtDeelsOnbekendAttribuut getDatumVoorzienEindeUitsluitingKiesrecht();

}
