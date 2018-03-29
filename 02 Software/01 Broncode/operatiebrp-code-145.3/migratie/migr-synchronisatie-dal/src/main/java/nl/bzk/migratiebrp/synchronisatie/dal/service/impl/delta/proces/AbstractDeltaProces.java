/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.proces;

import java.util.List;
import java.util.Map;
import java.util.Set;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.AdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.BRPActie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Lo3Voorkomen;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Relatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.RootEntiteit;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Stapel;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.Sleutel;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.EntiteitSleutel;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.IstSleutel;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.DeltaBepalingContext;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.DeltaRootEntiteitMatch;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.DeltaVergelijkerResultaat;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.VergelijkerResultaat;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.Verschil;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.VerschilGroep;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.VerschilType;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.decorators.PersoonDecorator;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.transformeer.StapelDeltaWijzigingenMap;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.transformeer.Transformeerder;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.util.DeltaRootEntiteitModus;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.util.DeltaUtil;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.util.HistorieContext;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.vergelijker.DeltaRootEntiteitVergelijker;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.vergelijker.DeltaVergelijker;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.vergelijker.IstStapelVergelijker;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.verwerker.DeltaRootEntiteitVerschilVerwerker;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.verwerker.DeltaVerschilVerwerker;
import nl.bzk.migratiebrp.synchronisatie.logging.SynchronisatieLogging;

/**
 * Abstracte class voor de delta processen.
 */
abstract class AbstractDeltaProces implements DeltaProces {

    /**
     * Bepaalt de verschillen tussen een collectie van twee bij elkaar horende root entiteiten (bv {@link Persoon} of
     * {@link nl.bzk.algemeenbrp.dal.domein.brp.entity.Betrokkenheid}) en transformeert de gevonden verschillen tbv de
     * levering mutaties.Deze root entiteiten zijn verzameld in een {@link DeltaRootEntiteitMatch} object, waarbij ook
     * de gevonden verschillen worden opgeslagen.
     * @param context de context voor delta bepaling
     * @param matches set van root entiteiten die bij elkaar horen
     */
    final void bepaalEnTransformeerVerschillen(final DeltaBepalingContext context, final Set<DeltaRootEntiteitMatch> matches) {
        final BRPActie actieVervalTbvLeveringMuts = context.getActieVervalTbvLeveringMuts();
        for (final DeltaRootEntiteitMatch match : matches) {
            final VergelijkerResultaat vergelijkerResultaat;

            if (match.isDeltaRootEntiteitGewijzigd()) {
                vergelijkerResultaat = verwerkEntiteitGewijzigd(context, match);
            } else if (match.isDeltaRootEntiteitVerwijderd()) {
                // DeltaEntiteit is verwijderd. Zoek de bijbehorende historie en markeer deze als om te zetten naar
                // M-rij.
                vergelijkerResultaat = verwerkEntiteitVerwijderd(match);
            } else {
                vergelijkerResultaat = verwerkEntiteitNieuw(context, match);
            }

            if (!match.isIstStapel()) {
                final Map<BRPActie, Lo3Voorkomen> actieHerkomstMap = vergelijkerResultaat.getActieHerkomstMap();
                context.addActieHerkomstMapInhoud(actieHerkomstMap);
                context.addDeltaEntiteitPaarSetInhoud(vergelijkerResultaat.getDeltaEntiteitPaarSet());

                if (!vergelijkerResultaat.isLeeg()) {
                    SynchronisatieLogging.addMelding("Transformatie voor " + match);
                    transformeerEnBepaalTypeBijhouding(match, context, actieVervalTbvLeveringMuts, vergelijkerResultaat);
                    context.addAllActieConsolidatieData(vergelijkerResultaat.getActieConsolidatieData());
                }
            }

            match.setVergelijkerResultaat(vergelijkerResultaat);
        }
    }

    private VergelijkerResultaat verwerkEntiteitNieuw(final DeltaBepalingContext context, final DeltaRootEntiteitMatch match) {
        final VergelijkerResultaat vergelijkerResultaat = new DeltaVergelijkerResultaat();
        vergelijkerResultaat.voegToeOfVervangVerschil(maakVerschil(match));

        // Extra verschil maken als het verschil een IST-stapel is zonder relatie. Dit is dan een ouder
        // IST-stapel die aan een bestaand relatie gekoppeld moet worden.
        if (match.isIstStapel()) {
            voegBestaandeRelatieAanStapelVerschilToe(vergelijkerResultaat, match.getNieuweRootEntiteit(), context.getBestaandePersoon());
        }
        return vergelijkerResultaat;
    }

    private VergelijkerResultaat verwerkEntiteitVerwijderd(final DeltaRootEntiteitMatch match) {
        final VergelijkerResultaat vergelijkerResultaat = new DeltaVergelijkerResultaat();

        if (!match.isIstStapel()) {
            final Set<Verschil> verschillen =
                    DeltaUtil.bepaalVerschillenVoorVerwijderdeALaagEntiteit(
                            match.getBestaandeRootEntiteit(),
                            null,
                            HistorieContext.bepaalNieuweHistorieContext(null, null, null));
            vergelijkerResultaat.voegVerschillenToe(verschillen);
        } else {
            vergelijkerResultaat.voegToeOfVervangVerschil(maakVerschil(match));
        }
        return vergelijkerResultaat;
    }

    private VergelijkerResultaat verwerkEntiteitGewijzigd(final DeltaBepalingContext context, final DeltaRootEntiteitMatch match) {
        final DeltaVergelijker vergelijker;
        if (match.isIstStapel()) {
            vergelijker = new IstStapelVergelijker();
        } else {
            vergelijker = new DeltaRootEntiteitVergelijker(DeltaRootEntiteitModus.bepaalModus(match.getBestaandeRootEntiteit()));
        }
        return vergelijker.vergelijk(context, match.getBestaandeRootEntiteit(), match.getNieuweRootEntiteit());
    }

    private void voegBestaandeRelatieAanStapelVerschilToe(
            final VergelijkerResultaat vergelijkerResultaat,
            final RootEntiteit nieuweRootEntiteit,
            final Persoon bestaandePersoon) {
        final Stapel stapel = (Stapel) nieuweRootEntiteit;
        if (stapel.getRelaties().isEmpty()) {
            final PersoonDecorator persoonDecorator = PersoonDecorator.decorate(bestaandePersoon);
            final Relatie bestaandeRelatie = persoonDecorator.getOuderRelatie();
            if (bestaandeRelatie == null) {
                throw new IllegalStateException("Er is geen bestaande relatie gevonden, terwijl deze bij het IST-stapel matchen wel is gevonden");
            }
            final Sleutel sleutel = new IstSleutel(stapel, Stapel.VELD_RELATIES, false);
            vergelijkerResultaat.voegToeOfVervangVerschil(new Verschil(sleutel, null, bestaandeRelatie, VerschilType.RIJ_TOEGEVOEGD, null, null));
        }
    }

    private Verschil maakVerschil(final DeltaRootEntiteitMatch match) {
        final VerschilType verschilType = match.isDeltaRootEntiteitNieuw() ? VerschilType.RIJ_TOEGEVOEGD : VerschilType.RIJ_VERWIJDERD;
        final Sleutel sleutel;
        if (match.isIstStapel()) {
            final Stapel stapel = (Stapel) (match.isDeltaRootEntiteitNieuw() ? match.getNieuweRootEntiteit() : match.getBestaandeRootEntiteit());
            sleutel = new IstSleutel(stapel, !match.isDeltaRootEntiteitNieuw());
        } else {
            sleutel = new EntiteitSleutel(match.getEigenaarEntiteit().getClass(), match.getEigenaarEntiteitVeldnaam());
        }
        return new Verschil(sleutel, match.getBestaandeRootEntiteit(), match.getNieuweRootEntiteit(), verschilType, null, null);
    }

    private void transformeerEnBepaalTypeBijhouding(
            final DeltaRootEntiteitMatch deltaRootEntiteitMatch,
            final DeltaBepalingContext context,
            final BRPActie actieVervalTbvLeveringMuts,
            final VergelijkerResultaat vergelijkerResultaat) {
        final Transformeerder transformeerder = Transformeerder.maakTransformeerder();
        final List<VerschilGroep> getransformeerdeVerschilGroepen =
                transformeerder.transformeer(vergelijkerResultaat.getVerschilGroepen(), actieVervalTbvLeveringMuts, context);

        final StapelDeltaWijzigingenMap stapelDeltaWijzigingenMap = transformeerder.getStapelDeltaWijzigingenMap();
        stapelDeltaWijzigingenMap.log(deltaRootEntiteitMatch.toString());

        if (!stapelDeltaWijzigingenMap.isBijhoudingActueel()) {
            context.setBijhoudingOverig();
        }
        vergelijkerResultaat.vervangVerschillen(getransformeerdeVerschilGroepen);
    }

    /**
     * Verwerkt de verschillen op de meegegeven delta root entiteit.
     * @param bestaandeRootEntiteit de delta root entiteit waarop de wijziging toegepast moeten worden
     * @param administratieveHandeling de administratieve handeling waaraan de wijzigingen gekoppeld moeten worden
     * @param verschillen een {@link VergelijkerResultaat} met daarin o.a. de getransformeerde gevonden verschillen.
     */
    protected final void verwerkVerschillen(
            final RootEntiteit bestaandeRootEntiteit,
            final AdministratieveHandeling administratieveHandeling,
            final VergelijkerResultaat verschillen) {
        final DeltaVerschilVerwerker verschilVerwerker = new DeltaRootEntiteitVerschilVerwerker(DeltaRootEntiteitModus.bepaalModus(bestaandeRootEntiteit));
        verschilVerwerker.verwerkWijzigingen(verschillen, bestaandeRootEntiteit, administratieveHandeling);
    }
}
