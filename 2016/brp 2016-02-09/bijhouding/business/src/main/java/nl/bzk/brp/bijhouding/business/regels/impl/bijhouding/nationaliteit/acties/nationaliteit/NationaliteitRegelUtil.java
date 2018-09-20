/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.bijhouding.nationaliteit.acties.nationaliteit;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamEnumeratiewaardeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NationaliteitcodeAttribuut;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.logisch.kern.Actie;
import nl.bzk.brp.model.logisch.kern.ActieBron;

/**
 * Util klasse voor gedeelde functionaliteit van de nationaliteit regels.
 */
public final class NationaliteitRegelUtil {

    /**
     * Private constructor, util klasse.
     */
    private NationaliteitRegelUtil() {
    }

    /**
     * Geeft aan of de persoon in dit bericht (nieuwe situatie) de Nederlandse nationaliteit verkrijgt.
     *
     * @param nieuweSituatie de persoon uit het bericht
     * @return of de Nederlandse nationaliteit verkregen wordt
     */
    public static boolean verkrijgtNederlandseNationaliteit(final PersoonBericht nieuweSituatie) {
        return nieuweSituatie.getNationaliteiten() != null
                && nieuweSituatie.getNationaliteiten().size() == 1
                && nieuweSituatie.getNationaliteiten().get(0).getNationaliteit().getWaarde().getCode()
                    .equals(NationaliteitcodeAttribuut.NL_NATIONALITEIT_CODE);
    }

    /**
     * Geeft aan of deze actie een bron van het type koninklijk besluit (naturalisatie) bevat.
     * Dat wil zeggen: of er een bron is die een document heeft van het soort 'Koninklijk besluit'.
     *
     * @param actie de actie
     * @return of de actie een koninklijk besluit bron heeft
     */
    public static boolean heeftKoninklijkbesluitBron(final Actie actie) {
        boolean koninklijkBesluitBron = false;
        if (actie.getBronnen() != null) {
            for (ActieBron bron : actie.getBronnen()) {
                if (isKoninklijkBesluitBron(bron)) {
                    koninklijkBesluitBron = true;
                }
            }
        }
        return koninklijkBesluitBron;
    }

    /**
     * Geeft aan of deze bron een koninklijk besluit bron is, dwz: een document heeft van het soort
     * koninklijk besluit (gecheckt op de naam van het document).
     *
     * @param bron de bron
     * @return of het een koniklijk besluit bron is
     */
    private static boolean isKoninklijkBesluitBron(final ActieBron bron) {
        return bron.getDocument() != null && NaamEnumeratiewaardeAttribuut.DOCUMENT_NAAM_KONINKLIJK_BESLUIT.equals(
                bron.getDocument().getSoort().getWaarde().getNaam());
    }

}
