/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.lo3naarbrp;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import nl.bzk.migratiebrp.conversie.model.Requirement;
import nl.bzk.migratiebrp.conversie.model.Requirements;
import nl.bzk.migratiebrp.conversie.model.brp.BrpActie;
import nl.bzk.migratiebrp.conversie.model.brp.BrpGroep;
import nl.bzk.migratiebrp.conversie.model.brp.BrpHistorie;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpCharacter;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatumTijd;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGroepInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Historie;
import nl.bzk.migratiebrp.conversie.model.tussen.TussenGroep;
import nl.bzk.migratiebrp.conversie.regels.proces.lo3naarbrp.attributen.Lo3AttribuutConverteerder;
import org.springframework.stereotype.Component;

/**
 * LO3 historie -> BRP historie conversie variant 22.
 */
@Component
@Requirement(Requirements.CHP001_LB22)
public class Lo3HistorieConversieVariantLB22 extends AbstractLo3HistorieConversieVariant {


    /**
     * constructor.
     * @param converteerder Lo3AttribuutConverteerder
     */
    @Inject
    public Lo3HistorieConversieVariantLB22(final Lo3AttribuutConverteerder converteerder) {
        super(converteerder);
    }

    /**
     * Bepaal of de huidige groep voortkomt uit de actuele Lo3 groep is met betrekking tot een lijst groepen door het
     * voorkomen uit de Lo3 herkomst te vergelijken. De gegeven groep is uit de actuele Lo3 groep als het Lo3 voorkomen
     * van die groep lager is of gelijk aan het Lo3 voorkomen van alle groepen in de lijst Brp groepen.
     */
    private static <T extends BrpGroepInhoud> boolean isUitLo3ActueleGroep(final BrpGroep<T> huidigeBrpGroep, final List<BrpGroep<T>> brpGroepen) {
        final int huidigeGroepLo3Voorkomen = huidigeBrpGroep.getActieInhoud().getLo3Herkomst().getVoorkomen();

        for (final BrpGroep<T> brpGroep : brpGroepen) {
            if (huidigeGroepLo3Voorkomen > brpGroep.getActieInhoud().getLo3Herkomst().getVoorkomen()
                    || brpGroep.getActieVerval() != null
                    && huidigeGroepLo3Voorkomen > brpGroep.getActieVerval().getLo3Herkomst().getVoorkomen()) {
                return false;
            }
        }

        return true;
    }

    @Override
    protected final <T extends BrpGroepInhoud> BrpGroep<T> converteerLo3Groep(
            final TussenGroep<T> lo3Groep,
            final List<TussenGroep<T>> lo3Groepen,
            final List<BrpGroep<T>> brpGroepen,
            final Map<Long, BrpActie> actieCache) {
        if (lo3Groep.isInhoudelijkLeeg()) {
            return null;
        }

        final Lo3Historie lo3Historie = lo3Groep.getHistorie();
        final boolean isOnjuist = lo3Historie.isOnjuist();

        final BrpDatumTijd datumTijdRegistratie = AbstractLo3HistorieConversieVariant.bepaalDatumTijdRegistratie(lo3Historie.getDatumVanOpneming(), brpGroepen);
        final BrpDatumTijd datumTijdVerval = isOnjuist ? datumTijdRegistratie : null;

        final BrpActie actieInhoud = maakActie(lo3Groep, actieCache);
        final BrpActie actieGeldigheid = null;
        final BrpActie actieVerval = isOnjuist ? actieInhoud : null;
        final BrpCharacter nadereAanduidingVerval = maakNadereAanduidingVerval(lo3Historie.getIndicatieOnjuist());

        final BrpHistorie historie = new BrpHistorie(datumTijdRegistratie, datumTijdVerval, nadereAanduidingVerval);

        return new BrpGroep<>(lo3Groep.getInhoud(), historie, actieInhoud, actieVerval, actieGeldigheid);
    }

    /**
     * Als er meerdere rijen zijn die niet vervallen zijn: Sorteer op datum/tijd registratie. Vul de datum/tijd verval
     * met datum/tijd registratie van de opvolger (actie verval wordt niet gevuld)
     * @param brpGroepen brp groepen
     * @param <T> groep inhoud type
     * @return De nabewerkte groepen
     */
    @Override
    protected final <T extends BrpGroepInhoud> List<BrpGroep<T>> doeNabewerking(final List<BrpGroep<T>> brpGroepen) {
        final List<BrpGroep<T>> nabewerkteGroepen = new ArrayList<>(brpGroepen.size());

        for (final BrpGroep<T> brpGroep : brpGroepen) {
            if (brpGroep.getHistorie().isVervallen() || Lo3HistorieConversieVariantLB22.isUitLo3ActueleGroep(brpGroep, brpGroepen)) {
                nabewerkteGroepen.add(brpGroep);
            } else {
                final BrpHistorie.Builder builder = new BrpHistorie.Builder(brpGroep.getHistorie());
                builder.setDatumTijdVerval(brpGroep.getHistorie().getDatumTijdRegistratie());
                nabewerkteGroepen.add(new BrpGroep<>(
                        brpGroep.getInhoud(),
                        builder.build(),
                        brpGroep.getActieInhoud(),
                        brpGroep.getActieInhoud(),
                        brpGroep.getActieGeldigheid()));
            }
        }

        return nabewerkteGroepen;
    }
}
