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

import nl.moderniseringgba.migratie.BijzondereSituaties;
import nl.moderniseringgba.migratie.Requirement;
import nl.moderniseringgba.migratie.Requirements;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpActie;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpGroep;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpGroepInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpHistorie;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpDatum;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpRelatieInhoud;
import nl.moderniseringgba.migratie.conversie.model.migratie.MigratieGroep;
import nl.moderniseringgba.migratie.conversie.proces.preconditie.lo3.Foutmelding;

import org.springframework.stereotype.Component;

/**
 * LO3 historie -> BRP historie conversie variant 24.
 * 
 */
@Component
@Requirement(Requirements.CHP001_LB24)
public class Lo3HistorieConversieVariantLB24 extends Lo3HistorieConversieVariant {

    private static final Comparator<MigratieGroep<?>> GROEPEN_COMPARATOR =
            new MigratieGroep.MigratieGroepComparator();

    @Override
    public final <T extends BrpGroepInhoud> List<BrpGroep<T>> converteer(final List<MigratieGroep<T>> lo3Groepen) {
        // Sorteer de migratie groepen, eerst onjuist, dan juist; daarbinnen van oud naar nieuw.
        Collections.sort(lo3Groepen, GROEPEN_COMPARATOR);

        final List<BrpGroep<T>> brpGroepen = new ArrayList<BrpGroep<T>>();
        for (final MigratieGroep<T> lo3Groep : lo3Groepen) {
            final BrpGroep<T> brpGroep = converteerLo3Groep(lo3Groep, brpGroepen);
            if (brpGroep != null) {
                brpGroepen.add(brpGroep);
            }
        }

        return Lo3HistorieConversieVariantLB22.doeNabewerking(brpGroepen);
    }

    private <T extends BrpGroepInhoud> BrpGroep<T> converteerLo3Groep(
            final MigratieGroep<T> lo3Groep,
            final List<BrpGroep<T>> brpGroepen) {
        final boolean isLeeg = lo3Groep.isInhoudelijkLeeg();

        if (isLeeg) {
            // Stap 3
            return converteerLegeRij(lo3Groep, brpGroepen);
        } else {
            // Stap 2
            return Lo3HistorieConversieVariantLB22.converteerLo3Groep(this, lo3Groep, brpGroepen);
        }
    }

    private <T extends BrpGroepInhoud> BrpGroep<T> converteerLegeRij(
            final MigratieGroep<T> lo3Groep,
            final List<BrpGroep<T>> brpGroepen) {

        // 3a. Verkrijg de laatst toegevoegde BRP rij/groep
        verwerkLegeRij(lo3Groep, brpGroepen, brpGroepen.get(brpGroepen.size() - 1));
        if (lo3Groep.getInhoud() instanceof BrpRelatieInhoud) {
            // Bij de relatie kan het alleen zo zijn dat een lege rij een sluiting 'corrigreerd'. Echter als de sluiting
            // al is opgevolgd door een ontbinding dan zal de standaard verwerking de correctie koppelen aan deze
            // ontbinding en niet aan de sluiting. We koppelen hier de correctie aan beide rijen omdat het anders niet
            // zichtbaar is in de BRP data. We maken deze ontdubbeling weer ongedaan in BrpRelatiesOpschonen voor de
            // terug conversie.
            final BrpDatum ingangdatum =
                    converteerder.converteerDatum(lo3Groep.getHistorie().getIngangsdatumGeldigheid());
            verwerkLegeRij(lo3Groep, brpGroepen, bepaalRelevanteRelatieGroep(ingangdatum, brpGroepen));
        }

        return null;
    }

    private <T extends BrpGroepInhoud> void verwerkLegeRij(
            final MigratieGroep<T> lo3Groep,
            final List<BrpGroep<T>> brpGroepen,
            final BrpGroep<T> laatste) {
        final Long laatsteActieInhoudId = laatste.getActieInhoud().getId();
        final Long laatsteActieVervalId = laatste.getActieVerval() == null ? null : laatste.getActieVerval().getId();

        if (laatsteActieInhoudId.equals(laatsteActieVervalId)) {
            // 3b. b. Als Actie verval gelijk is aan Actie inhoud:
            // Overschrijf in de gevonden BRP-rij de volgende elementen
            final int laatsteIndex = brpGroepen.indexOf(laatste);
            brpGroepen.remove(laatste);

            final BrpHistorie historie =
                    new BrpHistorie(laatste.getHistorie().getDatumAanvangGeldigheid(), laatste.getHistorie()
                            .getDatumEindeGeldigheid(), laatste.getHistorie().getDatumTijdRegistratie(), lo3Groep
                            .getHistorie().getDatumVanOpneming().converteerNaarBrpDatumTijd());

            final BrpActie actieInhoud = laatste.getActieInhoud();
            final BrpActie actieGeldigheid = laatste.getActieGeldigheid();
            final BrpActie actieVerval =
                    maakActie(lo3Groep.getDocumentatie(), lo3Groep.getHistorie(), lo3Groep.getLo3Herkomst());

            final BrpGroep<T> aangepast =
                    new BrpGroep<T>(laatste.getInhoud(), historie, actieInhoud, actieVerval, actieGeldigheid);
            brpGroepen.add(laatsteIndex, aangepast);
        } else {
            // 3c. Als Actie verval niet gelijk is aan Actie inhoud:
            // Niks doen. (dit is een specifiek geval dat zich uitsluitend bij categorie 05 kan voordoen)
            Foutmelding.logBijzondereSituatieFout(lo3Groep.getLo3Herkomst(), BijzondereSituaties.BIJZ_CONV_LB020,
                    laatste);
        }
    }

    private <T extends BrpGroepInhoud> BrpGroep<T> bepaalRelevanteRelatieGroep(
            final BrpDatum datum,
            final List<BrpGroep<T>> brpGroepen) {
        // Zoek terug totdat er een sluiting is met die ingangsdatum of een ontbinding met die ingangsdatum
        for (int index = brpGroepen.size() - 1; index >= 0; index--) {
            final BrpGroep<T> brpGroep = brpGroepen.get(index);

            // Deze methode wordt alleen aangeroepen als het om relaties gaat. De controle wordt uitgevoerd
            // in de methode 'converteerLegeRij'.
            assert brpGroep.getInhoud() instanceof BrpRelatieInhoud;
            final BrpRelatieInhoud inhoud = (BrpRelatieInhoud) brpGroep.getInhoud();

            if (datum.equals(inhoud.getDatumEinde()) || datum.equals(inhoud.getDatumAanvang())) {
                return brpGroep;
            }
        }

        throw new IllegalStateException("Lege rij gevonden in relatie, maar geen rij om te corrigeren");
    }
}
