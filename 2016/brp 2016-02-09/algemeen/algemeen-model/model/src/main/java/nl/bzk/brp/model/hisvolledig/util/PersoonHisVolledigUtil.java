/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.util;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.brp.model.hisvolledig.kern.HuwelijkGeregistreerdPartnerschapHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.OuderHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PartnerHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.operationeel.kern.HisRelatieModel;


/**
 * De utility klasse voor de klasse Persoon his volledig.
 */
public final class PersoonHisVolledigUtil {

    /**
     * Private constructor, want util klasse.
     */
    private PersoonHisVolledigUtil() {

    }

    /**
     * Geef alle huwelijken en geregistreerd partnerschappen terug van deze persoon. Let op: dit zijn dus zowel actuele als beeindigde hgps.
     *
     * @param persoon de persoon
     * @return alle hgps
     */
    public static List<HuwelijkGeregistreerdPartnerschapHisVolledig> getHGPs(final PersoonHisVolledig persoon) {
        final List<HuwelijkGeregistreerdPartnerschapHisVolledig> hgps =  new ArrayList<HuwelijkGeregistreerdPartnerschapHisVolledig>();

        for (PartnerHisVolledig betrokkenheid : persoon.getPartnerBetrokkenheden()) {
            hgps.add((HuwelijkGeregistreerdPartnerschapHisVolledig) betrokkenheid.getRelatie());
        }

        return hgps;
    }

    /**
     * Geef alle ouderschap betrokkenheden terug van deze persoon. Let op: dit zijn dus zowel actieve als vervallen ouderschappen.
     *
     * @param persoon de persoon
     * @return alle ouder betrokkenheden
     */
    public static List<OuderHisVolledig> getOuderBetrokkenheden(final PersoonHisVolledig persoon) {
        final List<OuderHisVolledig> ouderHisVolledigs = new ArrayList<OuderHisVolledig>();
        ouderHisVolledigs.addAll(persoon.getOuderBetrokkenheden());

        return ouderHisVolledigs;
    }

    /**
     * Geef alle actuele huwelijken en geregistreerd partnerschappen terug. Dat wil zeggen: ze zijn niet vervallen en hebben geen datum einde. In NL zou
     * dit er altijd maximaal 1 moeten zijn, maar vanwege de optie dat dit er vanuit buitenlands recht meerdere kunnen zijn is het een lijst.
     *
     * @param persoon de persoon
     * @return de actuele hgp's
     */
    public static List<HuwelijkGeregistreerdPartnerschapHisVolledig> getActueleHGPs(final PersoonHisVolledig persoon) {
        final List<HuwelijkGeregistreerdPartnerschapHisVolledig> actueleHGPs = new ArrayList<HuwelijkGeregistreerdPartnerschapHisVolledig>();

        for (final HuwelijkGeregistreerdPartnerschapHisVolledig hgpVolledig : getHGPs(persoon)) {
            final HisRelatieModel actueelRecord = hgpVolledig.getRelatieHistorie()
                .getActueleRecord();
            if (actueelRecord != null && actueelRecord.getDatumEinde() == null) {
                actueleHGPs.add(hgpVolledig);
            }
        }
        return actueleHGPs;
    }

    /**
     * Haalt de partner (persoon his volledig) uit de verbintenis, die niet de persoon zelf is. Aanname: de persoon zelf komt voor als betrokkene in de
     * relatie.
     *
     * @param persoon de persoon
     * @param hgp     de relatie
     * @return de partner persoon
     */
    public static PersoonHisVolledig getPartnerPersoon(final PersoonHisVolledig persoon,
        final HuwelijkGeregistreerdPartnerschapHisVolledig hgp)
    {
        return hgp.geefPartnerVan(persoon).getPersoon();
    }
}
