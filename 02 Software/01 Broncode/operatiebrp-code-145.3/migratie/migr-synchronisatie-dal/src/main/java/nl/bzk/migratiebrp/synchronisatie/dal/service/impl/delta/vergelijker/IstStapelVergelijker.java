/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.vergelijker;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.Entiteit;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Relatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.RootEntiteit;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Stapel;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.Sleutel;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.IstSleutel;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.DeltaBepalingContext;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.DeltaVergelijkerResultaat;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.VergelijkerResultaat;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.Verschil;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.VerschilType;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.decorators.RelatieDecorator;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.decorators.StapelDecorator;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.decorators.StapelVoorkomenDecorator;
import nl.bzk.migratiebrp.synchronisatie.logging.SynchronisatieLogging;

/**
 * Vergelijker implementatie voor de IST-stapels.
 */
public final class IstStapelVergelijker implements DeltaVergelijker {

    /**
     * Vergelijkt 2 IST-stapels ({@link Stapel}) met elkaar.
     * @param context de context voor deltabepaling
     * @param bestaandeStapel de bestaande IST-Stapel
     * @param nieuweStapel de nieuwe IST-stapel
     * @return een {@link VergelijkerResultaat} met daarin de gevonden verschillen tussen de 2 IST-stapels
     */
    @Override
    public VergelijkerResultaat vergelijk(final DeltaBepalingContext context, final RootEntiteit bestaandeStapel, final RootEntiteit nieuweStapel) {
        final StapelDecorator bestaandeStapelDecorator = StapelDecorator.decorate((Stapel) bestaandeStapel);
        final StapelDecorator nieuweStapelDecorator = StapelDecorator.decorate((Stapel) nieuweStapel);
        final VergelijkerResultaat result = new DeltaVergelijkerResultaat();

        vergelijkStapelVolgnummering(result, bestaandeStapelDecorator, nieuweStapelDecorator);
        final boolean isActueel = vergelijkStapelVoorkomens(result, bestaandeStapelDecorator, nieuweStapelDecorator);
        vergelijkStapelRelaties(result, bestaandeStapelDecorator, nieuweStapelDecorator);

        if (!isActueel) {
            context.setBijhoudingOverig();
        }

        SynchronisatieLogging.addMelding(
                String.format(
                        "IST Stapel (%s) vergelijking: Voorlopige conclusie: Is bijhouding actueel: %b",
                        ((Stapel) bestaandeStapel).getCategorie(),
                        isActueel));
        return result;
    }

    @Override
    public VergelijkerResultaat vergelijkEntiteiten(final RootEntiteit eigenaar, final Entiteit bestaandeEntiteit, final Entiteit nieuweEntiteit) {
        return new DeltaVergelijkerResultaat();
    }

    private void vergelijkStapelVolgnummering(
            final VergelijkerResultaat result,
            final StapelDecorator bestaandeStapel,
            final StapelDecorator nieuweStapel) {
        // Controleren of de volgnummers gelijk zijn en anders deze als verschil opnemen
        final int oudStapelNummer = bestaandeStapel.getStapelNummer();
        final int nieuwStapelNummer = nieuweStapel.getStapelNummer();
        if (oudStapelNummer != nieuwStapelNummer) {
            result.voegToeOfVervangVerschil(
                    new Verschil(
                            new IstSleutel(bestaandeStapel.getStapel(), Stapel.VELD_VOLGNUMMER, true),
                            bestaandeStapel,
                            nieuweStapel,
                            VerschilType.ELEMENT_AANGEPAST,
                            null,
                            null));
        }
    }

    private void vergelijkStapelRelaties(final VergelijkerResultaat result, final StapelDecorator bestaandeStapel, final StapelDecorator nieuweStapel) {
        final List<RelatieDecorator> bestaandeRelaties = bestaandeStapel.getRelaties();
        final List<RelatieDecorator> nieuweRelaties = nieuweStapel.getRelaties();

        // Als beide relaties (bestaand/nieuw) maar 1 relatie bevat, dan kunnen dit dezelfde zijn. Ze worden als
        // hetzelfde beschouwd als de type relatie hetzelfde is.

        final Set<RelatieDecorator> matchendeBestaandeRelaties = new HashSet<>();
        if (bestaandeRelaties.size() == 1 && nieuweRelaties.size() == 1) {
            final RelatieDecorator bestaandeRelatie = bestaandeRelaties.get(0);
            final RelatieDecorator nieuweRelatie = nieuweRelaties.get(0);

            final Relatie relatie = bestaandeRelatie.getRelatie();
            if (relatie.getSoortRelatie().equals(nieuweRelatie.getRelatie().getSoortRelatie())) {
                matchendeBestaandeRelaties.add(nieuweRelatie);
            } else {
                final Sleutel istSleutel = maakIstSleutelVoorRelatie(bestaandeStapel, relatie);
                result.voegToeOfVervangVerschil(new Verschil(istSleutel, relatie, null, VerschilType.RIJ_VERWIJDERD, null, null));
            }
        } else {
            // Meerdere bestaande relaties.
            for (final RelatieDecorator bestaandeRelatie : bestaandeRelaties) {
                final RelatieDecorator matchendeRelatie = bestaandeRelatie.zoekMatchendeRelatie(nieuweRelaties);
                if (matchendeRelatie != null) {
                    matchendeBestaandeRelaties.add(matchendeRelatie);
                } else {
                    final Relatie relatie = bestaandeRelatie.getRelatie();
                    final Sleutel istSleutel = maakIstSleutelVoorRelatie(bestaandeStapel, relatie);
                    result.voegToeOfVervangVerschil(new Verschil(istSleutel, relatie, null, VerschilType.RIJ_VERWIJDERD, null, null));
                }
            }
        }

        // Andere relatie(s) vinden, deze zijn nieuw
        final Set<RelatieDecorator> nieuweOverigeRelaties = nieuweStapel.getOverigeRelaties(matchendeBestaandeRelaties, true);
        for (final RelatieDecorator nieuweOverigeRelatie : nieuweOverigeRelaties) {
            final Relatie relatie = nieuweOverigeRelatie.getRelatie();
            final Sleutel istSleutel = maakIstSleutelVoorRelatie(bestaandeStapel, relatie);

            nieuweOverigeRelatie.removeStapel(nieuweStapel);

            result.voegToeOfVervangVerschil(new Verschil(istSleutel, null, nieuweOverigeRelatie.getRelatie(), VerschilType.RIJ_TOEGEVOEGD, null, null));

        }
    }

    private Sleutel maakIstSleutelVoorRelatie(final StapelDecorator bestaandeStapel, final Relatie relatie) {
        final Sleutel istSleutel = new IstSleutel(bestaandeStapel.getStapel(), Stapel.VELD_RELATIES, true);
        istSleutel.addSleuteldeel(Relatie.DATUM_AANVANG, relatie.getDatumAanvang());
        istSleutel.addSleuteldeel(Relatie.RELATIE_SOORT, relatie.getSoortRelatie());
        return istSleutel;
    }

    private boolean vergelijkStapelVoorkomens(
            final VergelijkerResultaat result,
            final StapelDecorator bestaandeStapel,
            final StapelDecorator nieuweStapel) {
        boolean isActueel = true;
        final Set<StapelVoorkomenDecorator> bestaandeVoorkomens = bestaandeStapel.getVoorkomens();
        final Set<StapelVoorkomenDecorator> nieuweVoorkomens = nieuweStapel.getVoorkomens();

        final int aantalBestaandeVoorkomens = bestaandeVoorkomens.size();
        final int aantalNieuweVoorkomens = nieuweVoorkomens.size();

        // Voorkomens
        if (aantalBestaandeVoorkomens == aantalNieuweVoorkomens) {
            // Exact aantal. Controleren dat alle voorkomen in oude gelijk zijn aan nieuw incl volgnr
            if (!bestaandeStapel.isVoorkomenSetMatch(nieuweVoorkomens, true)) {
                toevoegenVerwijderenElementen(result, bestaandeStapel, nieuweStapel);
                isActueel = false;
            }
        } else if (aantalNieuweVoorkomens - aantalBestaandeVoorkomens == 1) {
            final StapelVoorkomenDecorator actueelVoorkomen = nieuweStapel.getActueelVoorkomen();
            final Set<StapelVoorkomenDecorator> tijdelijkeNeuweVoorkomenSet = new HashSet<>(nieuweVoorkomens);
            tijdelijkeNeuweVoorkomenSet.remove(actueelVoorkomen);

            // 1 extra voorkomen. Controleer dat volgnr oude voorkomen gelijk is aan volgnr + 1 van nieuw voorkomen.
            if (!bestaandeStapel.isVoorkomenSetMatch(tijdelijkeNeuweVoorkomenSet, false)) {
                toevoegenVerwijderenElementen(result, bestaandeStapel, nieuweStapel);
                isActueel = false;
            } else {
                result.voegToeOfVervangVerschil(
                        new Verschil(
                                new IstSleutel(actueelVoorkomen.getVoorkomen(), Stapel.STAPEL_VOORKOMENS, false),
                                null,
                                actueelVoorkomen,
                                VerschilType.RIJ_TOEGEVOEGD,
                                null,
                                null));
            }
        } else {
            // Meer wijzigingen dan nu mogelijk voor sync. Alles verwijderen en nieuw toevoegen en dat wordt een resync.
            toevoegenVerwijderenElementen(result, bestaandeStapel, nieuweStapel);
            isActueel = false;
        }

        return isActueel;
    }

    private void toevoegenVerwijderenElementen(final VergelijkerResultaat result, final StapelDecorator oudeStapel, final StapelDecorator nieuweStapel) {
        result.voegToeOfVervangVerschil(
                new Verschil(new IstSleutel(oudeStapel.getStapel(), true), oudeStapel, null, VerschilType.ELEMENT_VERWIJDERD, null, null));
        // Sleutel is oude stapel omdat het anders mis gaat als de stapel ook verplaatst is.
        result.voegToeOfVervangVerschil(
                new Verschil(new IstSleutel(oudeStapel.getStapel(), false), null, nieuweStapel, VerschilType.ELEMENT_NIEUW, null, null));
    }
}
