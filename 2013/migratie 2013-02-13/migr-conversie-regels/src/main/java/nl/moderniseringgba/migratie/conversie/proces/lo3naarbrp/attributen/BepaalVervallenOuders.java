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

import nl.moderniseringgba.migratie.conversie.model.brp.BrpBetrokkenheid;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpGroep;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpHistorie;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpRelatie;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpStapel;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpDatum;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpDatumTijd;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpSoortBetrokkenheidCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpSoortRelatieCode;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpOuderInhoud;

import org.springframework.stereotype.Component;

/**
 * Doordat 62.10 (datum familierechtelijk betrekkening) verplaatst wordt naar de materiele historie kan het voorkomen
 * dat er meerdere gelijke datum aanvang in een ouder stapel komen. Deze gaan we sorteren op datum tijd inhoud en dan
 * alleen de meeste recente niet laten vervallen.
 */
@Component
public class BepaalVervallenOuders {

    /**
     * Laat de 'niet-meeste-recente' record vervallen voor ouders (datum familierechtelijke betrekking).
     * 
     * @param relatieStapels
     *            relatiestapels
     * @return aangepaste relatiestapels
     */
    public final List<BrpRelatie> bepaal(final List<BrpRelatie> relatieStapels) {
        final List<BrpRelatie> relaties = new ArrayList<BrpRelatie>(relatieStapels.size());

        for (final BrpRelatie relatieStapel : relatieStapels) {
            if (BrpSoortRelatieCode.FAMILIERECHTELIJKE_BETREKKING == relatieStapel.getSoortRelatieCode()
                    || BrpSoortBetrokkenheidCode.KIND == relatieStapel.getRolCode()) {
                relaties.add(bepaalVervalOuders(relatieStapel));
            } else {
                relaties.add(relatieStapel);
            }
        }

        return relaties;
    }

    private static BrpRelatie bepaalVervalOuders(final BrpRelatie relatie) {
        return new BrpRelatie(relatie.getSoortRelatieCode(), relatie.getRolCode(),
                bepaalVervalOuders(relatie.getBetrokkenheden()), relatie.getRelatieStapel());
    }

    private static List<BrpBetrokkenheid> bepaalVervalOuders(final List<BrpBetrokkenheid> betrokkenheden) {
        final List<BrpBetrokkenheid> result = new ArrayList<BrpBetrokkenheid>();
        for (final BrpBetrokkenheid betrokkenheid : betrokkenheden) {
            result.add(bepaalVervalOuder(betrokkenheid));
        }
        return result;
    }

    private static BrpBetrokkenheid bepaalVervalOuder(final BrpBetrokkenheid betrokkenheid) {
        return new BrpBetrokkenheid(betrokkenheid.getRol(), betrokkenheid.getIdentificatienummersStapel(),
                betrokkenheid.getGeslachtsaanduidingStapel(), betrokkenheid.getGeboorteStapel(),
                betrokkenheid.getOuderlijkGezagStapel(), betrokkenheid.getSamengesteldeNaamStapel(),
                bepaalVervalOuder(betrokkenheid.getOuderStapel()));
    }

    private static BrpStapel<BrpOuderInhoud> bepaalVervalOuder(final BrpStapel<BrpOuderInhoud> ouderStapel) {
        if (ouderStapel == null) {
            return null;
        }

        final List<BrpGroep<BrpOuderInhoud>> groepen = ouderStapel.getGroepen();
        Collections.sort(groepen, new Comparator<BrpGroep<BrpOuderInhoud>>() {

            @Override
            public int compare(final BrpGroep<BrpOuderInhoud> arg0, final BrpGroep<BrpOuderInhoud> arg1) {
                int result =
                        -arg0.getHistorie().getDatumAanvangGeldigheid()
                                .compareTo(arg1.getHistorie().getDatumAanvangGeldigheid());

                if (result == 0) {
                    result =
                            -arg0.getHistorie().getDatumTijdRegistratie()
                                    .compareTo(arg1.getHistorie().getDatumTijdRegistratie());
                }
                return result;
            }
        });

        final List<BrpGroep<BrpOuderInhoud>> result = new ArrayList<BrpGroep<BrpOuderInhoud>>();

        BrpDatum laatsteAanvang = null;
        BrpDatumTijd laatsteRegistratie = null;

        for (int index = 0; index < groepen.size(); index++) {
            final BrpGroep<BrpOuderInhoud> groep = groepen.get(index);

            if (laatsteAanvang != null && laatsteAanvang.equals(groep.getHistorie().getDatumAanvangGeldigheid())) {
                result.add(laatVervallen(groep, laatsteRegistratie));
            } else {
                result.add(groep);
            }

            laatsteAanvang = groep.getHistorie().getDatumAanvangGeldigheid();
            laatsteRegistratie = groep.getHistorie().getDatumTijdRegistratie();
        }

        return new BrpStapel<BrpOuderInhoud>(result);

    }

    private static BrpGroep<BrpOuderInhoud> laatVervallen(
            final BrpGroep<BrpOuderInhoud> groep,
            final BrpDatumTijd laatsteRegistratie) {
        final BrpHistorie his = groep.getHistorie();
        final BrpHistorie historie =
                new BrpHistorie(his.getDatumAanvangGeldigheid(), his.getDatumEindeGeldigheid(),
                        his.getDatumTijdRegistratie(), laatsteRegistratie);

        return new BrpGroep<BrpOuderInhoud>(groep.getInhoud(), historie, groep.getActieInhoud(),
                groep.getActieVerval(), groep.getActieGeldigheid());
    }
}
