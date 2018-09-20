/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.logisch.kern;

import javax.annotation.Generated;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaNeeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.BijhoudingsaardAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.NadereBijhoudingsaardAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PartijAttribuut;
import nl.bzk.brp.model.basis.Groep;

/**
 * Groep gegevens over de bijhouding
 *
 * De Wet BRP kent twee afdelingen voor bijhouden, afdeling I ('Ingezetenen') en afdeling II ('Niet ingezetenen'). Deze
 * afdelingen bepalen de wijze waarop de bijhouding plaats vindt. Hierbij is het van belang of iemand thans een
 * ingezetene is (en valt onder afdeling I), dat iemand geëmigreerd is, of dat iemand van begin af aan onder afdeling II
 * werd bijgehouden. Dit onderscheid wordt aangegeven met bijhoudingsaard.
 *
 * Verplicht aanwezig bij persoon
 *
 * 1. In geval van een opgeschorte bijhouding, is 'ingezetene' en 'niet-ingezetene' minder goed gedefinieerd. In het LO
 * BRP wordt dit punt uitgewerkt. 2. Vorm van historie: Beiden. De bijhoudingsverantwoordelijkheid kan verschuiven door
 * bijvoorbeeld een verhuizing die om technische redenen later wordt geregistreerd (maar wellicht op tijd is
 * doorgegeven). De datum ingang geldigheid van de (nieuwe) bijhoudingsverantwoordelijkheid ligt dus vaak vóór de
 * datumtijd registratie. Een formele tijdslijn alleen is dus onvoldoende. Omdat, in geval van verschuivingen van
 * verantwoordelijkheid tussen Minister en College B&W, ook van belang kan zijn wie over een periode in het verleden 'de
 * verantwoordelijke' was (voor het wijzigen van historische gegevens), is de materiële tijdslijn ook relevant, en dus
 * wordt deze vastgelegd. 3. De naam is aangepast naar bijhoudingsaard, tegen de term bijhoudingsverantwoordelijkheid
 * was teveel weerstand.
 *
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.LogischModelGenerator")
public interface PersoonBijhoudingGroepBasis extends Groep {

    /**
     * Retourneert Bijhoudingspartij van Bijhouding.
     *
     * @return Bijhoudingspartij.
     */
    PartijAttribuut getBijhoudingspartij();

    /**
     * Retourneert Bijhoudingsaard van Bijhouding.
     *
     * @return Bijhoudingsaard.
     */
    BijhoudingsaardAttribuut getBijhoudingsaard();

    /**
     * Retourneert Nadere bijhoudingsaard van Bijhouding.
     *
     * @return Nadere bijhoudingsaard.
     */
    NadereBijhoudingsaardAttribuut getNadereBijhoudingsaard();

    /**
     * Retourneert Onverwerkt document aanwezig? van Bijhouding.
     *
     * @return Onverwerkt document aanwezig?.
     */
    JaNeeAttribuut getIndicatieOnverwerktDocumentAanwezig();

}
