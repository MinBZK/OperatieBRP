/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.proces.lo3naarbrp.attributen;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import nl.moderniseringgba.migratie.conversie.model.brp.BrpGroep;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpRelatie;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpStapel;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpSoortRelatieCode;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpRelatieInhoud;

import org.springframework.stereotype.Component;

/**
 * Bepaal huwelijks sluitingen bij de ontbindingen.
 */
@Component
public class BepaalSluitingen {

    private static final Comparator<BrpGroep<BrpRelatieInhoud>> GROEPEN_COMPARATOR = new GroepenComparator();

    /**
     * Bepaal huwelijks sluitingen bij de ontbindingen.
     * 
     * @param relatieStapels
     *            relatiestapels
     * @return aangepaste relatiestapels
     */
    public final List<BrpRelatie> bepaal(final List<BrpRelatie> relatieStapels) {
        final List<BrpRelatie> relaties = new ArrayList<BrpRelatie>(relatieStapels.size());

        for (final BrpRelatie relatieStapel : relatieStapels) {
            if (BrpSoortRelatieCode.HUWELIJK == relatieStapel.getSoortRelatieCode()
                    || BrpSoortRelatieCode.GEREGISTREERD_PARTNERSCHAP == relatieStapel.getSoortRelatieCode()) {
                relaties.add(BepaalSluitingen.bepaalSluitingen(relatieStapel));
            } else {
                relaties.add(relatieStapel);
            }
        }

        return relaties;
    }

    private static BrpRelatie bepaalSluitingen(final BrpRelatie relatie) {
        return new BrpRelatie(relatie.getSoortRelatieCode(), relatie.getRolCode(), relatie.getBetrokkenheden(),
                BepaalSluitingen.bepaalSluitingen(relatie.getRelatieStapel()));

    }

    private static BrpStapel<BrpRelatieInhoud> bepaalSluitingen(final BrpStapel<BrpRelatieInhoud> stapel) {
        final List<BrpGroep<BrpRelatieInhoud>> groepen = stapel.getGroepen();
        if (groepen.size() < 2) {
            return stapel;
        }

        final List<BrpGroep<BrpRelatieInhoud>> result = new ArrayList<BrpGroep<BrpRelatieInhoud>>(groepen.size());
        Collections.sort(groepen, GROEPEN_COMPARATOR);
        result.add(groepen.get(0));

        BrpGroep<BrpRelatieInhoud> geldigeOntbinding = null;
        for (int index = 1; index < groepen.size(); index++) {
            final BrpGroep<BrpRelatieInhoud> dezeGroep = groepen.get(index);
            if (BepaalSluitingen.isOntbinding(dezeGroep)) {
                final BrpGroep<BrpRelatieInhoud> vorigeGroep = result.get(result.size() - 1);
                result.add(BepaalSluitingen.kopieerSluitingNaarOntbinding(dezeGroep, vorigeGroep));
                if (dezeGroep.getActieVerval() == null) {
                    geldigeOntbinding = dezeGroep;
                }
            } else {
                if (!dezeGroep.getInhoud().isLeeg() && geldigeOntbinding != null) {
                    result.add(BepaalSluitingen.kopieerOntbindingNaarSluiting(dezeGroep, geldigeOntbinding));
                } else {
                    result.add(dezeGroep);
                }
            }
        }

        return new BrpStapel<BrpRelatieInhoud>(result);
    }

    private static boolean isOntbinding(final BrpGroep<BrpRelatieInhoud> dezeGroep) {
        return dezeGroep.getInhoud().getDatumEinde() != null;
    }

    private static BrpGroep<BrpRelatieInhoud> kopieerSluitingNaarOntbinding(
            final BrpGroep<BrpRelatieInhoud> ontbindingGroep,
            final BrpGroep<BrpRelatieInhoud> sluitingGroep) {
        return BepaalSluitingen
                .kopieerSluitingOntbindingMetDoelgroep(ontbindingGroep, sluitingGroep, ontbindingGroep);
    }

    private static BrpGroep<BrpRelatieInhoud> kopieerOntbindingNaarSluiting(
            final BrpGroep<BrpRelatieInhoud> sluitingGroep,
            final BrpGroep<BrpRelatieInhoud> ontbindingGroep) {
        return BepaalSluitingen.kopieerSluitingOntbindingMetDoelgroep(ontbindingGroep, sluitingGroep, sluitingGroep);

    }

    private static BrpGroep<BrpRelatieInhoud> kopieerSluitingOntbindingMetDoelgroep(
            final BrpGroep<BrpRelatieInhoud> ontbindingGroep,
            final BrpGroep<BrpRelatieInhoud> sluitingGroep,
            final BrpGroep<BrpRelatieInhoud> doelGroep) {
        final BrpRelatieInhoud ontbinding = ontbindingGroep.getInhoud();
        final BrpRelatieInhoud sluiting = sluitingGroep.getInhoud();

        final BrpRelatieInhoud inhoud =
                new BrpRelatieInhoud(sluiting.getDatumAanvang(), sluiting.getGemeenteCodeAanvang(),
                        sluiting.getPlaatsCodeAanvang(), sluiting.getBuitenlandsePlaatsAanvang(),
                        sluiting.getBuitenlandseRegioAanvang(), sluiting.getLandCodeAanvang(),
                        sluiting.getOmschrijvingLocatieAanvang(), ontbinding.getRedenEinde(),
                        ontbinding.getDatumEinde(), ontbinding.getGemeenteCodeEinde(),
                        ontbinding.getPlaatsCodeEinde(), ontbinding.getBuitenlandsePlaatsEinde(),
                        ontbinding.getBuitenlandseRegioEinde(), ontbinding.getLandCodeEinde(),
                        ontbinding.getOmschrijvingLocatieEinde());

        return new BrpGroep<BrpRelatieInhoud>(inhoud, doelGroep.getHistorie(), doelGroep.getActieInhoud(),
                doelGroep.getActieVerval(), doelGroep.getActieGeldigheid());
    }

    /**
     * Volgorde om te bepalen welke sluiting bij welke ontbindingen hoort (datum tijdstip registratie).
     */
    private static final class GroepenComparator implements Comparator<BrpGroep<BrpRelatieInhoud>> {

        @Override
        public int compare(final BrpGroep<BrpRelatieInhoud> arg0, final BrpGroep<BrpRelatieInhoud> arg1) {
            return arg0.getActieInhoud().getDatumTijdRegistratie()
                    .compareTo(arg1.getActieInhoud().getDatumTijdRegistratie());
        }

    }
}
