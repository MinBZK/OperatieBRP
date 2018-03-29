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
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatum;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatumTijd;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpNadereBijhoudingsaardCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpValidatie;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpBijhoudingInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGroepInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Historie;
import nl.bzk.migratiebrp.conversie.model.tussen.TussenGroep;
import nl.bzk.migratiebrp.conversie.regels.proces.lo3naarbrp.attributen.Lo3AttribuutConverteerder;
import org.springframework.stereotype.Component;

/**
 * LO3 historie -> BRP historie conversie variant 21.
 */
@Component
@Requirement(Requirements.CHP001_LB21)
public class Lo3HistorieConversieVariantLB21 extends AbstractLo3HistorieConversieVariant {

    /**
     * constructor.
     * @param converteerder Lo3AttribuutConverteerder
     */
    @Inject
    public Lo3HistorieConversieVariantLB21(final Lo3AttribuutConverteerder converteerder) {
        super(converteerder);
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

        final T inhoud = lo3Groep.getInhoud();
        final Lo3Historie lo3Historie = lo3Groep.getHistorie();
        final boolean isOnjuist = lo3Historie.isOnjuist();

        final BrpDatum aanvangGeldigheid = BrpDatum.fromLo3Datum(lo3Historie.getIngangsdatumGeldigheid());
        final BrpDatumTijd datumTijdRegistratie = bepaalDatumTijdRegistratie(aanvangGeldigheid, lo3Historie.getDatumVanOpneming(), brpGroepen);
        // bij een onjuiste rij wordt de einde geldigheid niet gevuld
        final BrpDatum eindeGeldigheid;
        final BrpDatumTijd datumTijdVerval;
        final BrpActie actieInhoud = maakActie(lo3Groep, actieCache);
        final BrpActie actieVerval;
        final BrpCharacter nadereAanduidingVerval = maakNadereAanduidingVerval(lo3Historie.getIndicatieOnjuist());
        final T teGebruikenInhoud;

        if (isOnjuist) {
            eindeGeldigheid = null;
            datumTijdVerval = datumTijdRegistratie;
            actieVerval = actieInhoud;
            teGebruikenInhoud = pasInhoudAanIndienBijhouding(inhoud);
        } else {
            final TussenGroep<T> volgendeRij = bepaalVolgendeJuisteGroep(lo3Groep, lo3Groepen);
            final TussenGroep<T> actueleRij = bepaalActueleGroep(lo3Groepen);

            final BrpDatum aanvangGeldigheidVolgendeJuistGroep = bepaalIngangsdatumGeldigheid(volgendeRij);
            final BrpDatum aanvangGeldigheidActueleGroep = bepaalIngangsdatumGeldigheid(actueleRij);

            final boolean volgendeRijEerderGeldig =
                    aanvangGeldigheidVolgendeJuistGroep != null && aanvangGeldigheidVolgendeJuistGroep.compareTo(aanvangGeldigheid) <= 0;
            final boolean actueleRijEerderGeldig =
                    aanvangGeldigheidActueleGroep != null && actueleRij != lo3Groep && aanvangGeldigheidActueleGroep.compareTo(aanvangGeldigheid) <= 0;

            if (volgendeRijEerderGeldig || actueleRijEerderGeldig) {
                eindeGeldigheid = null;
                datumTijdVerval = new BrpDatumTijd(datumTijdRegistratie.getJavaDate(), null);
                actieVerval = actieInhoud;
                teGebruikenInhoud = pasInhoudAanIndienBijhouding(inhoud);
            } else {
                if (aanvangGeldigheidVolgendeJuistGroep != null) {
                    eindeGeldigheid = new BrpDatum(aanvangGeldigheidVolgendeJuistGroep.getWaarde(), null);
                } else {
                    eindeGeldigheid = null;
                }
                teGebruikenInhoud = inhoud;
                datumTijdVerval = null;
                actieVerval = null;
            }
        }

        final BrpHistorie historie = new BrpHistorie(aanvangGeldigheid, eindeGeldigheid, datumTijdRegistratie, datumTijdVerval, nadereAanduidingVerval);

        return new BrpGroep<>(teGebruikenInhoud, historie, actieInhoud, actieVerval, null);
    }

    private <T extends BrpGroepInhoud> BrpDatum bepaalIngangsdatumGeldigheid(final TussenGroep<T> rij) {
        return rij == null ? null : BrpDatum.fromLo3Datum(rij.getHistorie().getIngangsdatumGeldigheid());
    }

    private <T extends BrpGroepInhoud> T pasInhoudAanIndienBijhouding(T inhoud) {
        final T teGebruikenInhoud;
        if (inhoud instanceof BrpBijhoudingInhoud) {
            final BrpBijhoudingInhoud bijhoudingInhoud = (BrpBijhoudingInhoud) inhoud;
            teGebruikenInhoud = (T) new BrpBijhoudingInhoud(bijhoudingInhoud.getBijhoudingspartijCode(), bijhoudingInhoud.getBijhoudingsaardCode(),
                    new BrpNadereBijhoudingsaardCode("?", bijhoudingInhoud.getNadereBijhoudingsaardCode().getOnderzoek()));
        } else {
            teGebruikenInhoud = inhoud;
        }
        return teGebruikenInhoud;
    }

    /**
     * Doe de nabewerking voor historie conversie variant 21 om actieGeldigheid aan te vullen.
     * @param inGroepen de BRP groepen
     * @param <T> groep inhoud type
     * @return de BRP groepen parameter
     */
    @Override
    protected final <T extends BrpGroepInhoud> List<BrpGroep<T>> doeNabewerking(final List<BrpGroep<T>> inGroepen) {
        final List<BrpGroep<T>> brpGroepen = new ArrayList<>();

        for (final BrpGroep<T> groep : inGroepen) {
            if (groep.getActieGeldigheid() == null
                    && BrpValidatie.isAttribuutGevuld(groep.getHistorie().getDatumEindeGeldigheid())) {
                final BrpGroep<T> opvolger = bepaalOpvolgendeNietVervallenRij(groep, inGroepen);
                brpGroepen.add(
                        new BrpGroep<>(groep.getInhoud(), groep.getHistorie(), groep.getActieInhoud(), groep.getActieVerval(), opvolger.getActieInhoud()));
            } else {
                brpGroepen.add(groep);
            }
        }

        return brpGroepen;
    }
}
