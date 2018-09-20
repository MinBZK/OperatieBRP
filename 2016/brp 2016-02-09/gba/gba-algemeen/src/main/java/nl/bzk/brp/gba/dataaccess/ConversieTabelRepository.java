/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.gba.dataaccess;

import java.util.List;
import nl.bzk.brp.model.algemeen.stamgegeven.conv.ConversieAanduidingInhoudingVermissingReisdocument;
import nl.bzk.brp.model.algemeen.stamgegeven.conv.ConversieAangifteAdreshouding;
import nl.bzk.brp.model.algemeen.stamgegeven.conv.ConversieAdellijkeTitelPredikaat;
import nl.bzk.brp.model.algemeen.stamgegeven.conv.ConversieRNIDeelnemer;
import nl.bzk.brp.model.algemeen.stamgegeven.conv.ConversieRedenOntbindingHuwelijkGeregistreerdPartnerschap;
import nl.bzk.brp.model.algemeen.stamgegeven.conv.ConversieRedenOpschorting;
import nl.bzk.brp.model.algemeen.stamgegeven.conv.ConversieSoortNLReisdocument;
import nl.bzk.brp.model.algemeen.stamgegeven.conv.ConversieVoorvoegsel;


/**
 * Interface voor de repository die met conversie tabellen heeft te maken.
 *
 */
public interface ConversieTabelRepository {

    /**
     * @return de lijst met alle aangifte adreshouding entries in de
     * conversietabel
     */
    List<ConversieAangifteAdreshouding> findAllAangifteAdreshouding();

    /**
     * @return de lijst met alle adellijke titels en predikaten in de
     * conversietabel
     */
    List<ConversieAdellijkeTitelPredikaat> findAllAdellijkeTitelPredikaat();

    /**
     * @return de lijst met alle ConversieAanduidingInhoudingVermissingReisdocument entries in
     * de conversietabel
     */
    List<ConversieAanduidingInhoudingVermissingReisdocument> findAllAanduidingInhoudingVermissingReisdocument();

    /**
     * @return de lijst met alle ConversieRedenOntbindingHuwelijkGeregistreerdPartnerschap entries in
     * de conversietabel
     */
    List<ConversieRedenOntbindingHuwelijkGeregistreerdPartnerschap> findAllRedenOntbindingHuwelijkPartnerschap();

    /**
     * @return de lijst met alle ConversieRedenOpschorting entries in de conversietabel
     */
    List<ConversieRedenOpschorting> findAllRedenOpschorting();

    /**
     * @return de lijst met alle ConversieSoortNLReisdocument entries in de
     * conversietabel
     */
    List<ConversieSoortNLReisdocument> findAllSoortNlReisdocument();

    /**
     * @return de lijst met alle ConversieVoorvoegsel entries in de
     * conversietabel
     */
    List<ConversieVoorvoegsel> findAllVoorvoegsel();

    /**
     * @return de lijst met alle ConversieRNIDeelnemer entries in de
     * conversietabel
     */
    List<ConversieRNIDeelnemer> findAllRNIDeelnemer();
}
