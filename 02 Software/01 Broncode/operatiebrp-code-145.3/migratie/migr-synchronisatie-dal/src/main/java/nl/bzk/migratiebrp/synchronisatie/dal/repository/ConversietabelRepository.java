/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.repository;

import java.util.List;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.AanduidingInhoudingVermissingReisdocument;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.AangifteAdreshouding;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.AdellijkeTitelPredikaat;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.RNIDeelnemer;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.RedenOntbindingHuwelijkPartnerschap;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.RedenOpschorting;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.SoortNlReisdocument;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.VoorvoegselConversie;

/**
 * Levert alle methoden op gegevens uit conversietabellen op te zoeken a.d.h.v. hun unieke code(s).
 */
public interface ConversietabelRepository {

    /**
     * @return de lijst met alle adellijke titels en predikaten in de conversietabel
     */
    List<AdellijkeTitelPredikaat> findAllAdellijkeTitelPredikaat();

    /**
     * @return de lijst met alle AangifteAdreshouding entries in de conversietabel
     */
    List<AangifteAdreshouding> findAllAangifteAdreshouding();

    /**
     * @return de lijst met alle AanduidingInhoudingVermissingReisdocument entries in de conversietabel
     */
    List<AanduidingInhoudingVermissingReisdocument> findAllAanduidingInhoudingVermissingReisdocument();

    /**
     * @return de lijst met alle RedenOntbindingHuwelijkPartnerschap entries in de conversietabel
     */
    List<RedenOntbindingHuwelijkPartnerschap> findAllRedenOntbindingHuwelijkPartnerschap();

    /**
     * @return de lijst met alle RedenOpschorting entries in de conversietabel
     */
    List<RedenOpschorting> findAllRedenOpschorting();

    /**
     * @return de lijst met alle RNIDeelnemer entries in de conversietabel
     */
    List<RNIDeelnemer> findAllRNIDeelnemer();

    /**
     * @return de lijst met alle SoortNlReisdocument entries in de conversietabel
     */
    List<SoortNlReisdocument> findAllSoortNlReisdocument();

    /**
     * @return de lijst met alle VoorvoegselConversie entries in de conversietabel
     */
    List<VoorvoegselConversie> findAllVoorvoegselConversie();

    /**
     * @return de lijst met alle Lo3 rubrieken
     */
    List<String> findAllLo3Rubrieken();
}
