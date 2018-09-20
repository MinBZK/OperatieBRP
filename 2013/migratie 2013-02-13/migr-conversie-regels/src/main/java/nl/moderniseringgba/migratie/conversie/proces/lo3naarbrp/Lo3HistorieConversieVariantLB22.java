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
import nl.moderniseringgba.migratie.conversie.model.migratie.MigratieGroep;

import org.springframework.stereotype.Component;

/**
 * LO3 historie -> BRP historie conversie variant 22.
 * 
 */
@Component
@Requirement(Requirements.CHP001_LB22)
public class Lo3HistorieConversieVariantLB22 extends Lo3HistorieConversieVariant {

    private static final Comparator<MigratieGroep<?>> GROEPEN_COMPARATOR = new VariantLB21GroepenComparator();
    private static final Comparator<BrpGroep<?>> NABEWERKING_COMPARATOR = new NabewerkingComparator();

    @Override
    public final <T extends BrpGroepInhoud> List<BrpGroep<T>> converteer(final List<MigratieGroep<T>> lo3Groepen) {
        // Sorteer de migratie groepen, eerst onjuist, dan juist; daarbinnen van oud naar nieuw.
        Collections.sort(lo3Groepen, GROEPEN_COMPARATOR);

        final List<BrpGroep<T>> brpGroepen = new ArrayList<BrpGroep<T>>();
        for (final MigratieGroep<T> lo3Groep : lo3Groepen) {
            brpGroepen.add(Lo3HistorieConversieVariantLB22.converteerLo3Groep(this, lo3Groep, brpGroepen));
        }

        return Lo3HistorieConversieVariantLB22.doeNabewerking(brpGroepen);
    }

    /**
     * Als er meerdere rijen zijn die niet vervallen zijn: Sorteer op datum/tijd registratie. Vul de datum/tijd verval
     * met datum/tijd registratie van de opvolger (actie verval wordt niet gevuld)
     * 
     * @param brpGroepen
     *            brp groepen
     * @param <T>
     *            groep inhoud type
     * @return De nabewerkte groepen
     */
    static <T extends BrpGroepInhoud> List<BrpGroep<T>> doeNabewerking(final List<BrpGroep<T>> brpGroepen) {
        // Nabewerking:
        // Als er meerdere rijen zijn die niet vervallen zijn: Sorteer op datum/tijd registratie. Vul de datum/tijd
        // Verval met datum/tijd registratie van de opvolger (actie verval wordt niet gevuld)
        Collections.sort(brpGroepen, NABEWERKING_COMPARATOR);
        final List<BrpGroep<T>> nabewerkteGroepen = new ArrayList<BrpGroep<T>>(brpGroepen.size());
        BrpDatumTijd opvolger = null;
        for (final BrpGroep<T> brpGroep : brpGroepen) {
            if (brpGroep.getHistorie().isVervallen()) {
                nabewerkteGroepen.add(0, brpGroep);
                continue;
            }

            final BrpHistorie.Builder builder = new BrpHistorie.Builder(brpGroep.getHistorie());
            builder.setDatumTijdVerval(opvolger);

            nabewerkteGroepen.add(0, new BrpGroep<T>(brpGroep.getInhoud(), builder.build(),
                    brpGroep.getActieInhoud(), brpGroep.getActieVerval(), brpGroep.getActieGeldigheid()));
            opvolger = brpGroep.getHistorie().getDatumTijdRegistratie();
        }

        return nabewerkteGroepen;
    }

    /**
     * Converteer Lo3 groep.
     * 
     * @param historieConversieVariant
     *            De conversie variant
     * @param lo3Groep
     *            lo3 groep
     * @param brpGroepen
     *            brp groepen
     * @param <T>
     *            brp groep inhoud type
     * @return nieuwe brp groep
     */
    static <T extends BrpGroepInhoud> BrpGroep<T> converteerLo3Groep(
            final Lo3HistorieConversieVariant historieConversieVariant,
            final MigratieGroep<T> lo3Groep,
            final List<BrpGroep<T>> brpGroepen) {
        final Lo3Historie lo3Historie = lo3Groep.getHistorie();
        final boolean isOnjuist = lo3Historie.getIndicatieOnjuist() != null;

        final BrpDatum aanvangGeldigheid = null;
        final BrpDatum eindeGeldigheid = null;
        final BrpDatumTijd datumTijdRegistratie =
                Lo3HistorieConversieVariant.bepaalDatumTijdRegistratie(aanvangGeldigheid, lo3Groep, brpGroepen);
        final BrpDatumTijd datumTijdVerval = isOnjuist ? datumTijdRegistratie : null;

        final BrpHistorie historie =
                new BrpHistorie(aanvangGeldigheid, eindeGeldigheid, datumTijdRegistratie, datumTijdVerval);

        final BrpActie actieInhoud =
                historieConversieVariant.maakActie(lo3Groep.getDocumentatie(), lo3Groep.getHistorie(),
                        lo3Groep.getLo3Herkomst());
        final BrpActie actieGeldigheid = null;
        final BrpActie actieVerval = isOnjuist ? actieInhoud : null;

        return new BrpGroep<T>(lo3Groep.getInhoud(), historie, actieInhoud, actieVerval, actieGeldigheid);
    }

    /**
     * Sorteer de groepen op basis van datumtijd registratie van nieuw naar oud.
     */
    private static final class NabewerkingComparator implements Comparator<BrpGroep<?>> {

        @Override
        public int compare(final BrpGroep<?> arg0, final BrpGroep<?> arg1) {
            return -arg0.getHistorie().getDatumTijdRegistratie()
                    .compareTo(arg1.getHistorie().getDatumTijdRegistratie());
        }
    }

    /**
     * Sorteer de migratie groepen; (juist en onjuist) op basis van 86.10 datum van opneming en daarbinnen op 85.10
     * ingangsdatum geldigheid.
     */
    private static final class VariantLB21GroepenComparator implements Comparator<MigratieGroep<?>> {

        @Override
        public int compare(final MigratieGroep<?> arg0, final MigratieGroep<?> arg1) {

            int result = arg0.getHistorie().getDatumVanOpneming().compareTo(arg1.getHistorie().getDatumVanOpneming());
            if (result == 0) {
                result =
                        arg0.getHistorie().getIngangsdatumGeldigheid()
                                .compareTo(arg1.getHistorie().getIngangsdatumGeldigheid());
            }

            return result;
        }
    }
}
