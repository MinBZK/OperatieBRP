/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.proces.lo3naarbrp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import nl.moderniseringgba.migratie.Requirement;
import nl.moderniseringgba.migratie.Requirements;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpActie;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpGroep;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpGroepInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpHistorie;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpDatum;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpDatumTijd;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Historie;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Datum;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3IndicatieOnjuist;
import nl.moderniseringgba.migratie.conversie.model.migratie.MigratieGroep;
import nl.moderniseringgba.migratie.logging.Logger;
import nl.moderniseringgba.migratie.logging.LoggerFactory;

import org.springframework.stereotype.Component;

/**
 * LO3 historie -> BRP historie conversie variant 21.
 * 
 */
@Component
@Requirement(Requirements.CHP001_LB21)
public class Lo3HistorieConversieVariantLB21 extends Lo3HistorieConversieVariant {

    private static final Comparator<MigratieGroep<?>> GROEPEN_COMPARATOR = new VariantLB21GroepenComparator();

    private static final Logger LOG = LoggerFactory.getLogger();

    @Override
    public final <T extends BrpGroepInhoud> List<BrpGroep<T>> converteer(final List<MigratieGroep<T>> lo3Groepen) {
        // Sorteer de migratie groepen, eerst onjuist, dan juist; daarbinnen van oud naar nieuw.
        Collections.sort(lo3Groepen, GROEPEN_COMPARATOR);

        LOG.debug("converteer(lo3Groepen<sorted>=" + lo3Groepen + " )");

        final List<BrpGroep<T>> brpGroepen = new ArrayList<BrpGroep<T>>();
        for (final MigratieGroep<T> lo3Groep : lo3Groepen) {
            brpGroepen.add(converteerLo3Groep(lo3Groep, lo3Groepen, brpGroepen));
        }

        return brpGroepen;
    }

    private <T extends BrpGroepInhoud> BrpGroep<T> converteerLo3Groep(
            final MigratieGroep<T> lo3Groep,
            final List<MigratieGroep<T>> lo3Groepen,
            final List<BrpGroep<T>> brpGroepen) {
        final Lo3Historie lo3Historie = lo3Groep.getHistorie();
        final boolean isOnjuist = lo3Historie.getIndicatieOnjuist() != null;

        final BrpDatum aanvangGeldigheid = lo3Historie.getIngangsdatumGeldigheid().converteerNaarBrpDatum();
        // bij een onjuiste rij wordt de einde geldigheid niet gevuld
        final BrpDatum eindeGeldigheid = isOnjuist ? null : bepaalEindeGeldigheidJuist(lo3Groep, lo3Groepen);
        final BrpDatumTijd datumTijdRegistratie = bepaalDatumTijdRegistratie(aanvangGeldigheid, lo3Groep, brpGroepen);
        final BrpDatumTijd datumTijdVerval = isOnjuist ? datumTijdRegistratie : null;

        final BrpHistorie historie =
                new BrpHistorie(aanvangGeldigheid, eindeGeldigheid, datumTijdRegistratie, datumTijdVerval);

        final BrpActie actieInhoud =
                maakActie(lo3Groep.getDocumentatie(), lo3Groep.getHistorie(), lo3Groep.getLo3Herkomst());
        final BrpActie actieGeldigheid = null;
        final BrpActie actieVerval = isOnjuist ? actieInhoud : null;

        return new BrpGroep<T>(lo3Groep.getInhoud(), historie, actieInhoud, actieVerval, actieGeldigheid);
    }

    /**
     * Als er een of meerdere niet-onjuiste LO3-rijen zijn met een grotere 85.10 ingangsdatum geldigheid: kleinste 85.10
     * ingansgdatum geldigheid gebruiken. Indien geen rijen: leeg.
     */
    private <T extends BrpGroepInhoud> BrpDatum bepaalEindeGeldigheidJuist(
            final MigratieGroep<T> huidigeLo3Groep,
            final List<MigratieGroep<T>> lo3Groepen) {

        final Lo3Datum huidigeIngangsdatumGeldigheid = huidigeLo3Groep.getHistorie().getIngangsdatumGeldigheid();

        Lo3Datum resultaat = null;

        for (final MigratieGroep<?> lo3Groep : lo3Groepen) {
            if (lo3Groep.getHistorie().getIndicatieOnjuist() != null) {
                continue;
            }

            final Lo3Datum lo3RijGeldigheid = lo3Groep.getHistorie().getIngangsdatumGeldigheid();

            if (lo3RijGeldigheid.compareTo(huidigeIngangsdatumGeldigheid) > 0) {
                if (resultaat == null || resultaat.compareTo(lo3RijGeldigheid) > 0) {
                    resultaat = lo3RijGeldigheid;
                }
            }
        }

        return resultaat == null ? null : resultaat.converteerNaarBrpDatum();
    }

    /**
     * Sorteer de migratie groepen; eerst onjuist, dan juist; daarbinnen van oud naar nieuw.
     */
    private static final class VariantLB21GroepenComparator implements Comparator<MigratieGroep<?>> {

        @Override
        public int compare(final MigratieGroep<?> arg0, final MigratieGroep<?> arg1) {
            final Lo3IndicatieOnjuist onjuist0 = arg0.getHistorie().getIndicatieOnjuist();
            final Lo3IndicatieOnjuist onjuist1 = arg1.getHistorie().getIndicatieOnjuist();

            final int result;
            if (onjuist0 != null && onjuist1 == null) {
                result = -1;
            } else if (onjuist0 == null && onjuist1 != null) {
                result = 1;
            } else {
                result = arg0.getHistorie().getDatumVanOpneming().compareTo(arg1.getHistorie().getDatumVanOpneming());
            }

            return result;
        }
    };

}
