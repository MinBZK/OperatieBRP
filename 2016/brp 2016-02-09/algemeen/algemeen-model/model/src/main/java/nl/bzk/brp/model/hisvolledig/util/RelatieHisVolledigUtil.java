/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.util;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.RedenEindeRelatieCodeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortRelatie;
import nl.bzk.brp.model.hisvolledig.impl.kern.BetrokkenheidHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.FamilierechtelijkeBetrekkingHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.OuderHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PartnerHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.kern.BetrokkenheidHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.GeregistreerdPartnerschapHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.HuwelijkGeregistreerdPartnerschapHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.HuwelijkHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.RelatieHisVolledig;
import nl.bzk.brp.model.operationeel.kern.HisRelatieModel;


/**
 * De utility klasse voor de verschillende Relatie his volledig klassen.
 */
public final class RelatieHisVolledigUtil {

    /**
     * Private constructor, want util klasse.
     */
    private RelatieHisVolledigUtil()
    {

    }

    /**
     * Haalt een kind uit een familierechtelijke betrekking. Indien er geen kind gevonden wordt, wordt <code>null</code> geretourneerd.
     *
     * @param relatie de familierechtelijke betrekking die gecontroleerd dient te worden.
     * @return het kind of <code>null</code> indien geen kind kan worden gevonden.
     */
    public static PersoonHisVolledigImpl haalKindUitFamilierechtelijkeBetrekking(final FamilierechtelijkeBetrekkingHisVolledigImpl relatie)
    {
        return relatie.getKindBetrokkenheid().getPersoon();
    }

    /**
     * Haalt alle ouder betrokkenheden uit een kind.
     *
     * @param kind het kind waarvan de ouders gezocht moeten worden.
     * @return de lijst met alle gevonden ouder betrokkenheden (kan leeg zijn).
     */
    public static List<OuderHisVolledigImpl> haalOuderBetrokkenhedenUitKind(final PersoonHisVolledigImpl kind) {
        final List<OuderHisVolledigImpl> ouderBetrokkenheden = new ArrayList<OuderHisVolledigImpl>();

        ouderBetrokkenheden.addAll(kind.getKindBetrokkenheid().getRelatie().getOuderBetrokkenheden());

        return ouderBetrokkenheden;
    }

    /**
     * Haalt alle partner betrokkenheden (van de andere kant) uit een persoon.
     *
     * @param persoon de persoon waarvan de partners gezocht worden.
     * @return de lijst met alle gevonden partner betrokkenheden (kan leeg zijn).
     */
    public static List<PartnerHisVolledigImpl> haalPartnerBetrokkenhedenUitPersoon(final PersoonHisVolledigImpl persoon) {
        final List<PartnerHisVolledigImpl> partnerBetrokkenheden = new ArrayList<>();
        for (PartnerHisVolledigImpl betrokkenheid : persoon.getPartnerBetrokkenheden()) {
            for (BetrokkenheidHisVolledigImpl betrokkenheid2 : betrokkenheid.getRelatie().getBetrokkenheden()) {
                if (!betrokkenheid2.getID().equals(betrokkenheid.getID())) {
                    partnerBetrokkenheden.add((PartnerHisVolledigImpl) betrokkenheid2);
                }
            }
        }
        return partnerBetrokkenheden;
    }

    /**
     * Bepaalt (en retourneert) de betrokkenheid van de persoon waarvan de relatie gelijk is aan de opgegeven relatie. Let op: de relatie wordt op ID
     * gecheckt (en op directe object referentie), dus moet een bestaande relatie in de DB zijn (of object identiek). Indien er geen betrokkenheid van de
     * persoon met de opgegeven relatie is, dan zal <code>null</code> geretourneerd worden.
     *
     * @param persoon de persoon
     * @param relatie de relatie
     * @return de betrokkenheid van de persoon met opgegeven relatie, of <code>null</code> indien
     */
    public static BetrokkenheidHisVolledig bepaalBetrokkenheidVanPersoonInRelatie(final PersoonHisVolledig persoon, final RelatieHisVolledig relatie) {
        BetrokkenheidHisVolledig betrokkenheidResultaat = null;
        for (BetrokkenheidHisVolledig betrokkenheid : persoon.getBetrokkenheden()) {
            if (betrokkenheid.getRelatie().getID() != null
                && betrokkenheid.getRelatie().getID().equals(relatie.getID())
                || betrokkenheid.getRelatie() == relatie)
            {
                betrokkenheidResultaat = betrokkenheid;
                break;
            }
        }
        return betrokkenheidResultaat;
    }

    /**
     * Checkt of de persoon een betrokkenheid heeft naar de relatie. Let op: de relatie wordt op ID gecheckt, dus moet een bestaande relatie in de DB
     * zijn.
     *
     * @param persoon de persoon
     * @param relatie de relatie
     * @return of er een dergelijke betrokkenheid is (true) of niet (false)
     */
    public static boolean heeftBetrokkenheidNaarRelatie(final PersoonHisVolledig persoon, final RelatieHisVolledig relatie) {
        return bepaalBetrokkenheidVanPersoonInRelatie(persoon, relatie) != null;
    }

    /**
     * Bepaal het partnerschap dat aan dit huwelijk vooraf ging (is omgezet in dit huwelijk). Dit gebeurt met de volgende criteria: - Voor een van de
     * partners van het huwelijk moet voor een van zijn andere verbintenissen gelden dat: - Het is een beeindigd partnerschap is - Het partnerschap
     * beeindigd is met de reden 'omzetting soort verbintenis' - Het partnerschap is beeindigd op de datum aanvang van het huwelijk - Zowel het huwelijk
     * als het partnerschap dezelfde 2 betrokken personen bevatten.
     *
     * @param huwelijk
     * @return
     */
    public static GeregistreerdPartnerschapHisVolledig getVoorgaandPartnerschap(final HuwelijkHisVolledig huwelijk) {
        GeregistreerdPartnerschapHisVolledig omgezetPartnerschap = null;
        final PersoonHisVolledig eenPartner = huwelijk.getBetrokkenheden().iterator().next().getPersoon();
        for (HuwelijkGeregistreerdPartnerschapHisVolledig hgp : eenPartner.getHuwelijkGeregistreerdPartnerschappen()) {
            if (hgp.getSoort().getWaarde() == SoortRelatie.GEREGISTREERD_PARTNERSCHAP) {
                final HisRelatieModel actueelRecord = hgp.getRelatieHistorie().getActueleRecord();
                if (actueelRecord != null
                    && actueelRecord.getRedenEinde() != null
                    && actueelRecord.getRedenEinde().getWaarde().getCode().equals(
                    RedenEindeRelatieCodeAttribuut.REDEN_EINDE_RELATIE_OMZETTING_SOORT_VERBINTENIS_CODE)
                    && actueelRecord.getDatumEinde().op(huwelijk.getRelatieHistorie().getActueleRecord().getDatumAanvang()))
                {
                    final PersoonHisVolledig partnerHuwelijk = huwelijk.geefPartnerVan(eenPartner).getPersoon();
                    final PersoonHisVolledig partnerPartnerschap = hgp.geefPartnerVan(eenPartner).getPersoon();
                    if (partnerHuwelijk.getID().equals(partnerPartnerschap.getID())) {
                        omgezetPartnerschap = (GeregistreerdPartnerschapHisVolledig) hgp;
                    }
                }
            }
        }
        return omgezetPartnerschap;
    }

}
