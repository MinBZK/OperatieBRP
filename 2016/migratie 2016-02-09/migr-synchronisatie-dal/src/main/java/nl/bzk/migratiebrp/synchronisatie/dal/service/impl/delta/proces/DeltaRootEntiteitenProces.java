/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.proces;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.AdministratieveHandeling;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.DeltaRootEntiteit;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.EntiteitSleutel;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.HistorieUtil;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.NadereBijhoudingsaard;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Persoon;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PersoonAdres;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PersoonAdresHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.SoortAdministratieveHandeling;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.DeltaBepalingContext;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.DeltaRootEntiteitMatch;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.DeltaVergelijkerResultaat;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.VergelijkerResultaat;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.Verschil;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.VerschilType;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.vergelijker.IstStapelEnRelatieMatcher;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.verwerker.IstStapelVerschilVerwerker;
import nl.bzk.migratiebrp.synchronisatie.logging.SynchronisatieLogging;

/**
 * Matcht, vergelijk en verwerkt de gevonden verschillen tussen 2 {@link DeltaRootEntiteit} objecten van hetzelfde type
 * (bv {@link nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Persoon}.
 */
public final class DeltaRootEntiteitenProces extends AbstractDeltaProces {

    private static Set<DeltaRootEntiteitMatch> matchDeltaRootEntiteiten(final DeltaBepalingContext context) {
        final Set<DeltaRootEntiteitMatch> deltaRootEntiteitMatches = new IstStapelEnRelatieMatcher().matchIstGegevens(context);
        final Persoon nieuwePersoon = context.getNieuwePersoon();

        deltaRootEntiteitMatches.add(new DeltaRootEntiteitMatch(context.getBestaandePersoon(), nieuwePersoon, null, null));
        return deltaRootEntiteitMatches;
    }

    @Override
    public void bepaalVerschillen(final DeltaBepalingContext context) {
        final Set<DeltaRootEntiteitMatch> matches = matchDeltaRootEntiteiten(context);

        bepaalEnTransformeerVerschillen(context, matches);
        context.setDeltaRootEntiteitMatches(matches);

        if (context.isAnummerWijziging()) {
            context.setBijhoudingAnummerWijziging();
            if (!isSchoneAnummerWijziging(context)) {
                if (isPersoonslijstAfgevoerd(context)) {
                    context.setBijhoudingAfgevoerd();
                } else {
                    context.setBijhoudingOverig();
                }
            }
        } else {
            if (isInfrastructureleAdministratieveHandeling(context)) {
                context.setBijhoudingInfrastructureleWijziging();
            }

            if (isPersoonslijstAfgevoerd(context)) {
                context.setBijhoudingAfgevoerd();
            }
        }
        SynchronisatieLogging.addMelding(String.format("Voorlopige conclusie deltabepalen:  %s", context.getAdministratieveHandelingenAlsString()));
    }

    @Override
    public void verwerkVerschillen(final DeltaBepalingContext context) {
        final AdministratieveHandeling administratieveHandeling = context.getAdministratieveHandelingGekoppeldAanActies();

        /*
         * Wijzigingen op IST stapels worden samen verwerkt omdat gewijzigde stapels gerelateerd kunnen zijn (doordat
         * stapel volgnummer onderdeel is van sleutel en wijzigt bij nieuwe stapels).
         */
        final Collection<DeltaRootEntiteitMatch> teVerwerkenDeltaRootEntiteitMatch = context.getDeltaRootEntiteitMatches();
        final VergelijkerResultaat istVerschillen = filterIstVerschillen(teVerwerkenDeltaRootEntiteitMatch);
        new IstStapelVerschilVerwerker().verwerkWijzigingen(istVerschillen, context);

        for (final DeltaRootEntiteitMatch match : teVerwerkenDeltaRootEntiteitMatch) {
            final DeltaRootEntiteit deltaRootEntiteit;
            final VergelijkerResultaat vergelijkerResultaat = match.getVergelijkerResultaat();
            if (vergelijkerResultaat != null) {
                if (match.isDeltaRootEntiteitNieuw()) {
                    deltaRootEntiteit = match.getEigenaarEntiteit();
                } else {
                    // Er zijn wijzigingen op het DeltaRootEntiteit of deze is verwijderd
                    deltaRootEntiteit = match.getBestaandeDeltaRootEntiteit();
                }
                verwerkVerschillen(deltaRootEntiteit, administratieveHandeling, vergelijkerResultaat);
            }
        }
    }

    private boolean isSchoneAnummerWijziging(final DeltaBepalingContext context) {
        if (context.heeftAlleenPersoonsWijzigingen()) {
            final VergelijkerResultaat vergelijkerResultaat = context.getEigenPersoonMatch().getVergelijkerResultaat();
            if (vergelijkerResultaat.bevatAlleenAnummerWijzigingen()) {
                return true;
            }
        }
        return false;
    }

    private boolean isPersoonslijstAfgevoerd(final DeltaBepalingContext context) {
        final VergelijkerResultaat vergelijkerResultaat = context.getEigenPersoonMatch().getVergelijkerResultaat();
        final Verschil verschil =
                vergelijkerResultaat != null ? vergelijkerResultaat.zoekVerschil(new EntiteitSleutel(Persoon.class, Persoon.NADERE_BIJHOUDINGSAARD))
                                            : null;
        return verschil != null
               && verschil.getVerschilType().equals(VerschilType.ELEMENT_AANGEPAST)
               && NadereBijhoudingsaard.FOUT.getId() == (Short) verschil.getNieuweWaarde();
    }

    /**
     * Controleert of we te maken hebben met een in Infrastructrurele wijziging en wijzig de administratieve context als
     * dit zo is. indien we te maken hebben met een Inhoudelijke wijziging welke aanduid dat het een INFRA structurele
     * wijziging betreft: 8.72.10 is T of W (LO), waarde van de nieuwe entiteit I of B En er zijn verder geen andere
     * wijzigingen anders dan de Persoon of PersoonAdres entiteiten met de velden:
     * "persoonBijhoudingHistorieSet","persoonAdresSet","persoonInschrijvingHistorieSet", dan setten we de
     * AdministratieveHandeling van de context op {@link SoortAdministratieveHandeling}.GBA_INFRASTRUCTURELE_WIJZIGING
     *
     * @param context
     *            DeltaBepalingContext
     */
    private boolean isInfrastructureleAdministratieveHandeling(final DeltaBepalingContext context) {
        if (context.isBijhoudingActueel() && context.heeftAlleenPersoonsWijzigingen()) {
            final DeltaRootEntiteitMatch match = context.getEigenPersoonMatch();

            final VergelijkerResultaat vergelijkerResultaat = match.getVergelijkerResultaat();

            if (isInhoudelijkEenInfraWijziging(match)
                && vergelijkerResultaat.bevatAlleenInfrastructureleWijzigingen()
                && isNieuwAdresGroepOpgenomen(vergelijkerResultaat))
            {
                return true;
            }
        }
        return false;
    }

    private boolean isNieuwAdresGroepOpgenomen(final VergelijkerResultaat vergelijkerResultaat) {
        final List<Verschil> adresVerschillen =
                vergelijkerResultaat.zoekVerschillen(new EntiteitSleutel(PersoonAdres.class, "persoonAdresHistorieSet", new EntiteitSleutel(
                    Persoon.class,
                    "persoonAdresSet")));

        boolean result = !adresVerschillen.isEmpty();
        final Iterator<Verschil> iterator = adresVerschillen.iterator();
        while (result && iterator.hasNext()) {
            result = VerschilType.RIJ_TOEGEVOEGD.equals(iterator.next().getVerschilType());
        }
        return result;
    }

    private boolean isInhoudelijkEenInfraWijziging(final DeltaRootEntiteitMatch match) {
        final Set<PersoonAdres> persoonAdresSet = ((Persoon) match.getNieuweDeltaRootEntiteit()).getPersoonAdresSet();
        if (!persoonAdresSet.isEmpty()) {
            // Er is altijd maar 1 adres vanuit GBA geconverteerd
            final PersoonAdres pAdres = persoonAdresSet.iterator().next();
            final PersoonAdresHistorie actueelAdres = HistorieUtil.getActueelHistorieVoorkomen(pAdres.getPersoonAdresHistorieSet());
            final char redenWijzigingCode = actueelAdres.getRedenWijziging().getCode();
            return 'I' == redenWijzigingCode || 'B' == redenWijzigingCode;
        }
        return false;
    }

    /**
     * Filtert de verschillen die betrekking hebben op de IST uit de meegegeven map. Er wordt gekeken of de entiteit
     * match een Stapel is.
     *
     * @param teVerwerkenDeltaRootEntiteitMatches
     *            map met daarin per {@link DeltaRootEntiteitMatch} een lijst van verschillen
     * @return een {@link VergelijkerResultaat} met daarin de verschillen die betrekking hebben op de IST-stapels.
     */

    private VergelijkerResultaat filterIstVerschillen(final Collection<DeltaRootEntiteitMatch> teVerwerkenDeltaRootEntiteitMatches) {
        final VergelijkerResultaat result = new DeltaVergelijkerResultaat();
        final Iterator<DeltaRootEntiteitMatch> matchesIterator = teVerwerkenDeltaRootEntiteitMatches.iterator();

        while (matchesIterator.hasNext()) {
            final DeltaRootEntiteitMatch match = matchesIterator.next();
            if (match.isIstStapel()) {
                for (final Verschil verschil : match.getVergelijkerResultaat().getVerschillen()) {
                    result.voegToeOfVervangVerschil(verschil);
                }
                matchesIterator.remove();
            }
        }
        return result;
    }
}
