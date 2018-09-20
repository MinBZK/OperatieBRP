/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.synchronisatie.repository;

import java.util.List;

import nl.moderniseringgba.migratie.synchronisatie.domein.conversietabel.entity.AangifteAdreshouding;
import nl.moderniseringgba.migratie.synchronisatie.domein.conversietabel.entity.AdellijkeTitelPredikaat;
import nl.moderniseringgba.migratie.synchronisatie.domein.conversietabel.entity.AutoriteitVanAfgifte;
import nl.moderniseringgba.migratie.synchronisatie.domein.conversietabel.entity.RedenInhoudingVermissingReisdocument;
import nl.moderniseringgba.migratie.synchronisatie.domein.conversietabel.entity.RedenOntbindingHuwelijkPartnerschap;
import nl.moderniseringgba.migratie.synchronisatie.domein.conversietabel.entity.RedenOpschorting;
import nl.moderniseringgba.migratie.synchronisatie.domein.conversietabel.entity.RedenVerkrijgingVerliesNlSchap;
import nl.moderniseringgba.migratie.synchronisatie.domein.conversietabel.entity.SoortNlReisdocument;
import nl.moderniseringgba.migratie.synchronisatie.domein.conversietabel.entity.Verblijfstitel;
import nl.moderniseringgba.migratie.synchronisatie.domein.conversietabel.entity.Voorvoegsel;

/**
 * Levert alle methoden op gegevens uit conversietabellen op te zoeken a.d.h.v. hun unieke code(s).
 * 
 */
public interface ConversietabelRepository {

    /**
     * @return de lijst met alle Voorvoegsels in de conversietabel
     */
    List<Voorvoegsel> findAllVoorvoegsels();

    /**
     * @return de lijst met alle adellijke titels en predikaten in de conversietabel
     */
    List<AdellijkeTitelPredikaat> findAllAdellijkeTitelPredikaat();

    /**
     * @return de lijst met alle AangifteAdreshouding entries in de conversietabel
     */
    List<AangifteAdreshouding> findAllAangifteAdreshouding();

    /**
     * @return de lijst met alle AutoriteitVanAfgifte entries in de conversietabel
     */
    List<AutoriteitVanAfgifte> findAllAutoriteitVanAfgifte();

    /**
     * @return de lijst met alle RedenInhoudingVermissingReisdocument entries in de conversietabel
     */
    List<RedenInhoudingVermissingReisdocument> findAllRedenInhoudingVermissingReisdocument();

    /**
     * @return de lijst met alle RedenOntbindingHuwelijkPartnerschap entries in de conversietabel
     */
    List<RedenOntbindingHuwelijkPartnerschap> findAllRedenOntbindingHuwelijkPartnerschap();

    /**
     * @return de lijst met alle RedenOpschorting entries in de conversietabel
     */
    List<RedenOpschorting> findAllRedenOpschorting();

    /**
     * @return de lijst met alle RedenVerkrijgingNlSchap entries in de conversietabel
     */
    List<RedenVerkrijgingVerliesNlSchap> findAllRedenVerkrijgingNlSchap();

    /**
     * @return de lijst met alle RedenVerliesNlSchap entries in de conversietabel
     */
    List<RedenVerkrijgingVerliesNlSchap> findAllRedenVerliesNlSchap();

    /**
     * @return de lijst met alle SoortNlReisdocument entries in de conversietabel
     */
    List<SoortNlReisdocument> findAllSoortNlReisdocument();

    /**
     * @return de lijst met alle Verblijfstitel entries in de conversietabel
     */
    List<Verblijfstitel> findAllVerblijfstitel();
}
