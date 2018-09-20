/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.util;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NationaliteitcodeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortBetrokkenheid;
import nl.bzk.brp.model.logisch.kern.Betrokkenheid;
import nl.bzk.brp.model.logisch.kern.HuwelijkGeregistreerdPartnerschap;
import nl.bzk.brp.model.logisch.kern.Persoon;
import nl.bzk.brp.model.logisch.kern.PersoonNationaliteit;


/**
 * Util voor Persoon.
 */
public final class PersoonUtil {

    /**
     * Private constructor, want util klasse.
     */
    private PersoonUtil() {

    }

    /**
     * Retourneert true indien deze persoon een niet vervallen nederlandse nationaliteit heeft.
     *
     * @param persoon de Persoon.
     * @return True indien hij nederlander is.
     */
    public static boolean heeftActueleNederlandseNationaliteit(final Persoon persoon) {
        boolean retval = false;
        for (final PersoonNationaliteit nationaliteit : persoon.getNationaliteiten()) {
            if (nationaliteit.getNationaliteit().getWaarde().getCode().equals(NationaliteitcodeAttribuut.NL_NATIONALITEIT_CODE)) {
                retval = true;
                break;
            }
        }
        return retval;
    }

    /**
     * Controleert of persoon ouder is dan een leeftijd op een specifieke datum.
     *
     * @param persoon  persoon
     * @param leeftijd de leeftijd
     * @param datum    datum
     * @return true als persoon ouder is dan de leeftijd op datum
     */
    public static boolean isLeeftijdOfOuderOpDatum(final Persoon persoon, final int leeftijd, final DatumAttribuut datum) {
        boolean isOpLeeftijdOfOuder = false;
        final DatumAttribuut maximaleGeboorteDatum = new DatumAttribuut(datum.getWaarde());
        maximaleGeboorteDatum.voegJaarToe(-leeftijd);
        final DatumEvtDeelsOnbekendAttribuut geboorteDatum = persoon.getGeboorte().getDatumGeboorte();

        if (geboorteDatum.voorOfOp(maximaleGeboorteDatum)) {
            isOpLeeftijdOfOuder = true;
        }

        return isOpLeeftijdOfOuder;
    }

    /**
     * Geef alle huwelijken en geregistreerd partnerschappen terug van deze persoon. Let op: dit zijn dus zowel actuele als beeindigde hgps.
     *
     * @param persoon de persoon
     * @return alle hgps
     */
    public static List<HuwelijkGeregistreerdPartnerschap> getHGPs(final Persoon persoon) {
        final List<HuwelijkGeregistreerdPartnerschap> hgps = new ArrayList<>();
        if (persoon.getBetrokkenheden() != null) {
            for (final Betrokkenheid betrokkenheid : persoon.getBetrokkenheden()) {
                if (betrokkenheid.getRol().getWaarde() == SoortBetrokkenheid.PARTNER) {
                    final HuwelijkGeregistreerdPartnerschap hgp = (HuwelijkGeregistreerdPartnerschap) betrokkenheid.getRelatie();
                    hgps.add(hgp);
                }
            }
        }
        return hgps;
    }

}
