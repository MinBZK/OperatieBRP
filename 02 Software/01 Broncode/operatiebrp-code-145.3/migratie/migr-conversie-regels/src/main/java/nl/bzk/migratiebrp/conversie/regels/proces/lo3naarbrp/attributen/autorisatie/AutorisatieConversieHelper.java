/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.lo3naarbrp.attributen.autorisatie;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Categorie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
import nl.bzk.migratiebrp.conversie.model.lo3.autorisatie.Lo3AutorisatieInhoud;
import org.springframework.stereotype.Component;

/**
 * Helper voor autorisatie conversie.
 */
@Component
public final class AutorisatieConversieHelper {

    /**
     * Bepaal de meeste recente niet lege inhoud op basis van datum ingang van een stapel.
     * @param stapel stapel
     * @return inhoud
     */
    public Lo3AutorisatieInhoud bepaalMeesteRecenteNietLegeInhoud(final Lo3Stapel<Lo3AutorisatieInhoud> stapel) {
        Lo3Categorie<Lo3AutorisatieInhoud> result = null;

        if (stapel != null) {
            for (final Lo3Categorie<Lo3AutorisatieInhoud> categorie : stapel) {
                if (categorie.getInhoud().isLeeg()) {
                    continue;
                }

                if (result == null
                        || result.getHistorie().getIngangsdatumGeldigheid().compareTo(categorie.getHistorie().getIngangsdatumGeldigheid()) < 0) {
                    result = categorie;
                }
            }
        }
        return result == null ? null : result.getInhoud();
    }

    /**
     * Maakt van rubrieken uit een autorisatie (filter/sleutel) een lijst van rubrieknummers.
     * @param autorisatieRubrieken Een String van rubrieken gescheiden door een '#'.
     * @return lo3Rubrieken Een lijst van string rubrieknummers.
     */
    public Set<String> bepaalRubrieken(final String autorisatieRubrieken) {
        if (autorisatieRubrieken == null || "".equals(autorisatieRubrieken)) {
            return Collections.emptySet();
        }

        final String[] rubrieknummers = autorisatieRubrieken.split("#");
        return new HashSet<>(Arrays.asList(rubrieknummers));
    }

    /**
     * Maakt een unieke String op basis van de gegeven afnemerindicatie en het versieNr. Indien gevuld word ook de
     * suffix gebruikt.
     * @param afnemerindicatie de afnemer code
     * @param versieNr het versienummer van de autorisatietabelregel
     * @param suffix de eventuele suffix
     * @return uniekeNaam 'afnemerindicatie(versieNr) suffix'.
     */
    public String maakNaamUniek(final String afnemerindicatie, final Integer versieNr, final String suffix) {
        String afnemerCode = afnemerindicatie;
        if (versieNr - 1 > 0) {
            afnemerCode = afnemerCode + "(" + (versieNr - 1) + ")";
        }

        String uniekeNaam = afnemerCode;
        if (suffix != null && !"".equals(suffix)) {
            uniekeNaam = uniekeNaam + " " + suffix;
        }
        return uniekeNaam;
    }

}
