/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.lo3naarbrp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.conversie.model.Requirement;
import nl.bzk.migratiebrp.conversie.model.Requirements;
import nl.bzk.migratiebrp.conversie.model.brp.BrpActie;
import nl.bzk.migratiebrp.conversie.model.brp.BrpGroep;
import nl.bzk.migratiebrp.conversie.model.brp.BrpHistorie;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpCharacter;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatum;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatumTijd;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortActieCode;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGroepInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpRelatieInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.element.AbstractLo3Element;
import nl.bzk.migratiebrp.conversie.model.tussen.TussenGroep;
import nl.bzk.migratiebrp.conversie.regels.proces.lo3naarbrp.attributen.Lo3AttribuutConverteerder;
import org.springframework.stereotype.Component;

/**
 * LO3 historie -> BRP historie conversie variant 24.
 */
@Component
@Requirement(Requirements.CHP001_LB24)
public class Lo3HistorieConversieVariantLB24 extends AbstractLo3HistorieConversieVariant {

    private static final Logger LOGGER = LoggerFactory.getLogger();
    private static final Comparator<BrpGroep<?>> RELATIE_NABEWERKING_COMPARATOR = new LB24BrpRelatieGroepenSorteerComparator();

    private final Lo3HistorieConversieVariantLB22 lo3HistorieConversieVariantLB22;


    /**
     * constructor.
     * @param converteerder Lo3AttribuutConverteerder
     * @param conversieVariantLB22 variant LB22
     */
    @Inject
    public Lo3HistorieConversieVariantLB24(final Lo3AttribuutConverteerder converteerder, final Lo3HistorieConversieVariantLB22 conversieVariantLB22) {
        super(converteerder);
        this.lo3HistorieConversieVariantLB22 = conversieVariantLB22;
    }

    @Override
    protected final <T extends BrpGroepInhoud> BrpGroep<T> converteerLo3Groep(
            final TussenGroep<T> lo3Groep,
            final List<TussenGroep<T>> lo3Groepen,
            final List<BrpGroep<T>> brpGroepen,
            final Map<Long, BrpActie> actieCache) {
        final boolean isLeeg = lo3Groep.isInhoudelijkLeeg();

        if (isLeeg) {
            // Stap 3
            return converteerLegeRij(lo3Groep, lo3Groepen, brpGroepen, actieCache);
        } else {
            // Stap 2
            return lo3HistorieConversieVariantLB22.converteerLo3Groep(lo3Groep, lo3Groepen, brpGroepen, actieCache);
        }
    }

    @Override
    protected final <T extends BrpGroepInhoud> List<BrpGroep<T>> doeNabewerking(final List<BrpGroep<T>> brpGroepen) {

        final List<BrpGroep<T>> result;

        if (brpGroepen.isEmpty()) {
            result = brpGroepen;
        } else if (brpGroepen.get(0).getInhoud() instanceof BrpRelatieInhoud) {
            result = doeNabewerkingVoorRelatie(brpGroepen);
        } else {
            result = lo3HistorieConversieVariantLB22.doeNabewerking(brpGroepen);
        }

        return result;
    }

    private <T extends BrpGroepInhoud> BrpGroep<T> converteerLegeRij(
            final TussenGroep<T> lo3Groep,
            final List<TussenGroep<T>> lo3Groepen,
            final List<BrpGroep<T>> brpGroepen,
            final Map<Long, BrpActie> actieCache) {

        if (!brpGroepen.isEmpty()) {
            final BrpGroep<T> laatstToegevoegdeGroep = zoekLaatstToegevoegdeGroep(lo3Groep, lo3Groepen, brpGroepen);
            if (laatstToegevoegdeGroep != null) {
                verwerkLegeRij(lo3Groep, lo3Groepen, brpGroepen, laatstToegevoegdeGroep, actieCache);
            }
        }
        return null;
    }

    /*
     * 3a. Zoek de BRP-rij die als laatste toegevoegd is, waarvan het LO3-voorkomen waaruit deze voortkomt niet de
     * actuele categorie is en een gelijke 85.10 Datum aanvang geldigheid heeft als dit LO3-voorkomen (note: een lege
     * rij kan nooit de actuele categorie laten vervallen). Als er geen BRP-rij gevonden is: Zoek de BRP-rij die als
     * laatste toegevoegd is, die niet voortkomt uit de actuele LO3-categorie.
     */
    private <T extends BrpGroepInhoud> BrpGroep<T> zoekLaatstToegevoegdeGroep(
            final TussenGroep<T> lo3Groep,
            final List<TussenGroep<T>> lo3Groepen,
            final List<BrpGroep<T>> brpGroepen) {
        BrpGroep<T> laatsteNietActueleBrpGroep = null;
        BrpGroep<T> laatsteNietActueleBrpGroepMetGelijkeGeldigheid = null;

        for (final BrpGroep<T> brpGroep : brpGroepen) {
            if (brpGroep.getActieInhoud().getLo3Herkomst().isLo3ActueelVoorkomen()) {
                // de BRP groep is afkomstig uit een actueel LO3 voorkomen
                continue;
            }
            laatsteNietActueleBrpGroep = brpGroep;

            final TussenGroep<T> lo3GroepVoorVervallenBrpGroep = vindTussenGroepBijHerkomst(lo3Groepen, brpGroep.getActieInhoud().getLo3Herkomst());

            if (lo3GroepVoorVervallenBrpGroep != null
                    && AbstractLo3Element.equalsWaarde(
                    lo3Groep.getHistorie().getIngangsdatumGeldigheid(),
                    lo3GroepVoorVervallenBrpGroep.getHistorie().getIngangsdatumGeldigheid())) {
                laatsteNietActueleBrpGroepMetGelijkeGeldigheid = brpGroep;
            }
        }
        if (laatsteNietActueleBrpGroepMetGelijkeGeldigheid != null) {
            return laatsteNietActueleBrpGroepMetGelijkeGeldigheid;
        } else {
            return laatsteNietActueleBrpGroep;
        }
    }

    private <T extends BrpGroepInhoud> void verwerkLegeRij(
            final TussenGroep<T> lo3Groep,
            final List<TussenGroep<T>> lo3Groepen,
            final List<BrpGroep<T>> brpGroepen,
            final BrpGroep<T> laatstToegevoegdeGroep,
            final Map<Long, BrpActie> actieCache) {
        final BrpActie actieInhoud = laatstToegevoegdeGroep.getActieInhoud();
        final Long laatsteActieInhoudId = actieInhoud.getId();
        final Long laatsteActieVervalId = laatstToegevoegdeGroep.getActieVerval() == null ? null : laatstToegevoegdeGroep.getActieVerval().getId();

        final boolean isOnjuist = lo3Groep.getHistorie().isOnjuist();
        final BrpHistorie laatstToegevoegdeGroepHistorie = laatstToegevoegdeGroep.getHistorie();

        if ((laatsteActieInhoudId.equals(laatsteActieVervalId) || laatsteActieVervalId == null) && !isOnjuist) {
            verwerkStap3C(lo3Groep, brpGroepen, laatstToegevoegdeGroep, actieCache, actieInhoud, laatstToegevoegdeGroepHistorie);
        } else if (laatsteActieVervalId == null) {
            verwerkStap3D(lo3Groep, brpGroepen, laatstToegevoegdeGroep, actieCache, actieInhoud, laatstToegevoegdeGroepHistorie);
        } else if (laatsteActieInhoudId.equals(laatsteActieVervalId)) {
            verwerkStap3E(lo3Groep, brpGroepen, laatstToegevoegdeGroep, actieCache, actieInhoud, laatstToegevoegdeGroepHistorie);
        } else {
            verwerkStap3F(lo3Groep, lo3Groepen, brpGroepen, laatstToegevoegdeGroep, actieCache, actieInhoud, laatstToegevoegdeGroepHistorie);
        }
    }

    private <T extends BrpGroepInhoud> void verwerkStap3F(final TussenGroep<T> lo3Groep, final List<TussenGroep<T>> lo3Groepen,
                                                          final List<BrpGroep<T>> brpGroepen, final BrpGroep<T> laatstToegevoegdeGroep,
                                                          final Map<Long, BrpActie> actieCache, final BrpActie actieInhoud,
                                                          final BrpHistorie laatstToegevoegdeGroepHistorie) {
        LOGGER.trace("3F, herkomt: " + lo3Groep.getLo3Herkomst());
        final BrpDatum aanvangGeldigheid = laatstToegevoegdeGroepHistorie.getDatumAanvangGeldigheid();
        final T inhoud = laatstToegevoegdeGroep.getInhoud();

        final TussenGroep<T> vervallenLo3Groep = vindTussenGroepBijHerkomst(lo3Groepen, laatstToegevoegdeGroep.getActieVerval().getLo3Herkomst());
        final TussenGroep<T> inhoudLo3Groep = vindTussenGroepBijHerkomst(lo3Groepen, laatstToegevoegdeGroep.getActieInhoud().getLo3Herkomst());

        if (!lo3Groep.getHistorie().isOnjuist() && vervallenLo3Groep != null && vervallenLo3Groep.getHistorie().isOnjuist()) {
            final BrpCharacter nav = inhoudLo3Groep.getHistorie().isOnjuist() ? new BrpCharacter('O') : null;
            final BrpHistorie historie =
                    new BrpHistorie(
                            aanvangGeldigheid,
                            laatstToegevoegdeGroepHistorie.getDatumEindeGeldigheid(),
                            updateDatumTijdRegistratie(aanvangGeldigheid, laatstToegevoegdeGroepHistorie.getDatumTijdRegistratie(), brpGroepen),
                            BrpDatumTijd.fromLo3Datum(lo3Groep.getHistorie().getDatumVanOpneming()),
                            nav);

            final BrpActie brpActie = maakActie(lo3Groep, actieCache);
            final BrpGroep<T> afsluitendeRij = new BrpGroep<>(inhoud, historie, actieInhoud, brpActie, null);
            brpGroepen.add(afsluitendeRij);
        }
    }

    private <T extends BrpGroepInhoud> void verwerkStap3E(final TussenGroep<T> lo3Groep, final List<BrpGroep<T>> brpGroepen,
                                                          final BrpGroep<T> laatstToegevoegdeGroep, final Map<Long, BrpActie> actieCache,
                                                          final BrpActie actieInhoud, final BrpHistorie laatstToegevoegdeGroepHistorie) {
        LOGGER.trace("3E, herkomt: " + lo3Groep.getLo3Herkomst());
        final T inhoud = laatstToegevoegdeGroep.getInhoud();
        final BrpDatum aanvangGeldigheid = laatstToegevoegdeGroepHistorie.getDatumAanvangGeldigheid();

        final int vervallenGroepIndex = brpGroepen.indexOf(laatstToegevoegdeGroep);
        brpGroepen.remove(laatstToegevoegdeGroep);

        final BrpHistorie historie =
                new BrpHistorie(
                        aanvangGeldigheid,
                        laatstToegevoegdeGroepHistorie.getDatumEindeGeldigheid(),
                        laatstToegevoegdeGroepHistorie.getDatumTijdRegistratie(),
                        BrpDatumTijd.fromLo3Datum(lo3Groep.getHistorie().getDatumVanOpneming()),
                        new BrpCharacter('O'));

        final BrpActie brpActie = maakActie(lo3Groep, actieCache, BrpSoortActieCode.CONVERSIE_GBA_LEEG_CATEGORIE_ONJUIST);
        final BrpGroep<T> aangepast = new BrpGroep<>(inhoud, historie, actieInhoud, brpActie, null);
        brpGroepen.add(vervallenGroepIndex, aangepast);
    }

    private <T extends BrpGroepInhoud> void verwerkStap3D(final TussenGroep<T> lo3Groep, final List<BrpGroep<T>> brpGroepen,
                                                          final BrpGroep<T> laatstToegevoegdeGroep, final Map<Long, BrpActie> actieCache,
                                                          final BrpActie actieInhoud, final BrpHistorie laatstToegevoegdeGroepHistorie) {
        LOGGER.trace("3D, herkomt: " + lo3Groep.getLo3Herkomst());
        final BrpDatum aanvangGeldigheid = laatstToegevoegdeGroepHistorie.getDatumAanvangGeldigheid();
        final T inhoud = laatstToegevoegdeGroep.getInhoud();

        final int vervallenGroepIndex = brpGroepen.indexOf(laatstToegevoegdeGroep);
        brpGroepen.remove(laatstToegevoegdeGroep);

        final BrpDatumTijd datumTijdRegistratie =
                AbstractLo3HistorieConversieVariant.updateDatumTijdRegistratie(
                        aanvangGeldigheid,
                        laatstToegevoegdeGroepHistorie.getDatumTijdRegistratie(),
                        brpGroepen);
        final BrpHistorie historie =
                new BrpHistorie(
                        aanvangGeldigheid,
                        laatstToegevoegdeGroepHistorie.getDatumEindeGeldigheid(),
                        datumTijdRegistratie,
                        BrpDatumTijd.fromLo3Datum(lo3Groep.getHistorie().getDatumVanOpneming()),
                        new BrpCharacter('O'));

        final BrpActie brpActie = maakActie(lo3Groep, actieCache, BrpSoortActieCode.CONVERSIE_GBA_LEEG_CATEGORIE_ONJUIST);
        final BrpGroep<T> kopie = new BrpGroep<>(inhoud, historie, actieInhoud, brpActie, null);
        brpGroepen.add(vervallenGroepIndex, kopie);
    }

    private <T extends BrpGroepInhoud> void verwerkStap3C(final TussenGroep<T> lo3Groep, final List<BrpGroep<T>> brpGroepen,
                                                          final BrpGroep<T> laatstToegevoegdeGroep, final Map<Long, BrpActie> actieCache,
                                                          final BrpActie actieInhoud, final BrpHistorie laatstToegevoegdeGroepHistorie) {
        LOGGER.trace("3C, herkomt: " + lo3Groep.getLo3Herkomst());
        final BrpDatum aanvangGeldigheid = laatstToegevoegdeGroepHistorie.getDatumAanvangGeldigheid();
        final T inhoud = laatstToegevoegdeGroep.getInhoud();

        final int vervallenGroepIndex = brpGroepen.indexOf(laatstToegevoegdeGroep);
        brpGroepen.remove(laatstToegevoegdeGroep);

        final BrpHistorie historie =
                new BrpHistorie(
                        aanvangGeldigheid,
                        laatstToegevoegdeGroepHistorie.getDatumEindeGeldigheid(),
                        laatstToegevoegdeGroepHistorie.getDatumTijdRegistratie(),
                        BrpDatumTijd.fromLo3Datum(lo3Groep.getHistorie().getDatumVanOpneming()),
                        laatstToegevoegdeGroepHistorie.getNadereAanduidingVerval());

        final BrpActie brpActie = maakActie(lo3Groep, actieCache);
        final BrpGroep<T> aangepast = new BrpGroep<>(inhoud, historie, actieInhoud, brpActie, null);
        brpGroepen.add(vervallenGroepIndex, aangepast);
    }

    /**
     * Als er meerdere rijen zijn die niet vervallen zijn: Sorteer op datum/tijd registratie. Vul de datum/tijd verval
     * met datum/tijd registratie van de opvolger (actie verval wordt niet gevuld).
     *
     * Deze variant voor huwelijk maakt geen uitzondering voor de actuele groep (zie ORANJE-438 / ORANJE-1075).
     * @param brpGroepen brp groepen
     * @param <T> groep inhoud type
     * @return De nabewerkte groepen
     */
    private <T extends BrpGroepInhoud> List<BrpGroep<T>> doeNabewerkingVoorRelatie(final List<BrpGroep<T>> brpGroepen) {
        // Nabewerking:
        // Als er meerdere rijen zijn die niet vervallen zijn: Sorteer op datum/tijd registratie. Vul de datum/tijd
        // Verval met datum/tijd registratie van de opvolger en vul actie verval met de actie inhoud van de rij zelf.
        brpGroepen.sort(Lo3HistorieConversieVariantLB24.RELATIE_NABEWERKING_COMPARATOR);

        final List<BrpGroep<T>> nabewerkteGroepen = new ArrayList<>(brpGroepen.size());
        BrpGroep<T> opvolger = null;

        for (final BrpGroep<T> brpGroep : brpGroepen) {
            if (brpGroep.getHistorie().isVervallen()) {
                nabewerkteGroepen.add(0, brpGroep);
                continue;
            }

            final BrpHistorie.Builder builder = new BrpHistorie.Builder(brpGroep.getHistorie());

            final BrpActie actieVerval;
            if (opvolger != null) {
                builder.setDatumTijdVerval(brpGroep.getHistorie().getDatumTijdRegistratie());
                actieVerval = brpGroep.getActieInhoud();
            } else {
                actieVerval = null;
            }

            nabewerkteGroepen.add(
                    0,
                    new BrpGroep<>(brpGroep.getInhoud(), builder.build(), brpGroep.getActieInhoud(), actieVerval, brpGroep.getActieGeldigheid()));
            opvolger = brpGroep;
        }

        return nabewerkteGroepen;
    }

    /**
     * Sorteer de groepen op basis van datumtijd registratie van nieuw naar oud.
     */
    private static final class LB24BrpRelatieGroepenSorteerComparator implements Comparator<BrpGroep<?>>, Serializable {
        private static final long serialVersionUID = 1L;

        @Override
        public int compare(final BrpGroep<?> arg0, final BrpGroep<?> arg1) {
            return arg1.getHistorie().getDatumTijdRegistratie().compareTo(arg0.getHistorie().getDatumTijdRegistratie());
        }
    }
}
