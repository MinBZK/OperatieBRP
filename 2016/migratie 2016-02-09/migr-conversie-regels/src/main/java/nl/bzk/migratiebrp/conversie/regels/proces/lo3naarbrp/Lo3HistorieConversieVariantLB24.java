/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.lo3naarbrp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortActieCode;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGroepInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpRelatieInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Documentatie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Historie;
import nl.bzk.migratiebrp.conversie.model.lo3.element.AbstractLo3Element;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.conversie.model.tussen.TussenGroep;

import org.springframework.stereotype.Component;

/**
 * LO3 historie -> BRP historie conversie variant 24.
 * 
 */
@Component
@Requirement(Requirements.CHP001_LB24)
public class Lo3HistorieConversieVariantLB24 extends Lo3HistorieConversieVariant {

    private static final Comparator<BrpGroep<?>> RELATIE_NABEWERKING_COMPARATOR = new LB24BrpRelatieGroepenSorteerComparator();

    @Inject
    private Lo3HistorieConversieVariantLB22 lo3HistorieConversieVariantLB22;

    @Override
    protected final <T extends BrpGroepInhoud> BrpGroep<T> converteerLo3Groep(
        final TussenGroep<T> lo3Groep,
        final List<TussenGroep<T>> lo3Groepen,
        final List<BrpGroep<T>> brpGroepen,
        final Map<Long, BrpActie> actieCache)
    {
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

        if (brpGroepen.size() == 0) {
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
        final Map<Long, BrpActie> actieCache)
    {

        if (!brpGroepen.isEmpty()) {
            final BrpGroep<T> vervallenBrpGroep = bepaalTeVervallenGroep(lo3Groep, lo3Groepen, brpGroepen);
            if (vervallenBrpGroep != null) {
                verwerkLegeRij(lo3Groep, lo3Groepen, brpGroepen, vervallenBrpGroep, actieCache);
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
    private <T extends BrpGroepInhoud> BrpGroep<T> bepaalTeVervallenGroep(
        final TussenGroep<T> lo3Groep,
        final List<TussenGroep<T>> lo3Groepen,
        final List<BrpGroep<T>> brpGroepen)
    {
        BrpGroep<T> laatsteNietActueleBrpGroep = null;
        BrpGroep<T> laatsteNietActueleBrpGroepMetGelijkeGeldigheid = null;

        for (int index = brpGroepen.size() - 1; index >= 0; index--) {
            final BrpGroep<T> brpGroep = brpGroepen.get(index);
            if (brpGroep.getActieInhoud().getLo3Herkomst().getVoorkomen() == 0) {
                // de BRP groep is afkomstig uit een actueel LO3 voorkomen
                continue;
            }
            laatsteNietActueleBrpGroep = brpGroep;

            final TussenGroep<T> lo3GroepVoorVervallenBrpGroep = vindLo3GroepBijHerkomst(lo3Groepen, brpGroep.getActieInhoud().getLo3Herkomst());

            if (AbstractLo3Element.equalsWaarde(
                lo3Groep.getHistorie().getIngangsdatumGeldigheid(),
                lo3GroepVoorVervallenBrpGroep.getHistorie().getIngangsdatumGeldigheid()))
            {
                laatsteNietActueleBrpGroepMetGelijkeGeldigheid = brpGroep;
                break;
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
        final BrpGroep<T> vervallenGroep,
        final Map<Long, BrpActie> actieCache)
    {
        final Long laatsteActieInhoudId = vervallenGroep.getActieInhoud().getId();
        final Long laatsteActieVervalId = vervallenGroep.getActieVerval() == null ? null : vervallenGroep.getActieVerval().getId();

        if (laatsteActieInhoudId.equals(laatsteActieVervalId) || laatsteActieVervalId == null) {
            // 3c. Als Actie verval gelijk is aan Actie inhoud of actie verval is niet gevuld:
            // Overschrijf in de gevonden BRP-rij de volgende elementen
            final int vervallenGroepIndex = brpGroepen.indexOf(vervallenGroep);
            brpGroepen.remove(vervallenGroep);

            final BrpActie actieInhoud = vervallenGroep.getActieInhoud();
            final BrpActie actieGeldigheid = vervallenGroep.getActieGeldigheid();

            final Lo3Documentatie lo3Documentatie = lo3Groep.getDocumentatie();
            final Lo3Historie lo3Historie = lo3Groep.getHistorie();
            final Lo3Herkomst lo3Herkomst = lo3Groep.getLo3Herkomst();

            final BrpActie actieVerval;
            if (lo3Historie.isOnjuist() && lo3Groep.isOorsprongVoorkomenLeeg()) {
                actieVerval = maakActie(lo3Documentatie, lo3Historie, lo3Herkomst, actieCache, BrpSoortActieCode.CONVERSIE_GBA_LEEG_CATEGORIE_ONJUIST);
            } else {
                actieVerval = maakActie(lo3Documentatie, lo3Historie, lo3Herkomst, actieCache);
            }

            final BrpHistorie historie =
                    new BrpHistorie(
                        vervallenGroep.getHistorie().getDatumAanvangGeldigheid(),
                        vervallenGroep.getHistorie().getDatumEindeGeldigheid(),
                        vervallenGroep.getHistorie().getDatumTijdRegistratie(),
                        BrpDatumTijd.fromLo3Datum(lo3Groep.getHistorie().getDatumVanOpneming()),
                        new BrpCharacter('O'));

            final BrpGroep<T> aangepast = new BrpGroep<>(vervallenGroep.getInhoud(), historie, actieInhoud, actieVerval, actieGeldigheid);
            brpGroepen.add(vervallenGroepIndex, aangepast);
        } else if (!laatsteActieInhoudId.equals(laatsteActieVervalId)) {
            // 3d.

            final TussenGroep<T> vervallenLo3Groep = vindLo3GroepBijHerkomst(lo3Groepen, vervallenGroep.getActieVerval().getLo3Herkomst());

            if (!lo3Groep.getHistorie().isOnjuist() && vervallenLo3Groep.getHistorie().isOnjuist()) {
                final BrpHistorie historie =
                        new BrpHistorie(
                            vervallenGroep.getHistorie().getDatumAanvangGeldigheid(),
                            vervallenGroep.getHistorie().getDatumEindeGeldigheid(),
                            updateDatumTijdRegistratie(
                                vervallenGroep.getHistorie().getDatumAanvangGeldigheid(),
                                vervallenGroep.getHistorie().getDatumTijdRegistratie(),
                                brpGroepen),
                            BrpDatumTijd.fromLo3Datum(lo3Groep.getHistorie().getDatumVanOpneming()),
                            vervallenGroep.getHistorie().getNadereAanduidingVerval());

                final BrpActie actieInhoud = vervallenGroep.getActieInhoud();
                final BrpActie actieGeldigheid = vervallenGroep.getActieGeldigheid();
                final BrpActie actieVerval = maakActie(lo3Groep.getDocumentatie(), lo3Groep.getHistorie(), lo3Groep.getLo3Herkomst(), actieCache);

                final BrpGroep<T> afsluitendeRij = new BrpGroep<>(vervallenGroep.getInhoud(), historie, actieInhoud, actieVerval, actieGeldigheid);
                brpGroepen.add(afsluitendeRij);
            }
        }
    }

    /**
     * Als er meerdere rijen zijn die niet vervallen zijn: Sorteer op datum/tijd registratie. Vul de datum/tijd verval
     * met datum/tijd registratie van de opvolger (actie verval wordt niet gevuld).
     * 
     * Deze variant voor huwelijk maakt geen uitzondering voor de actuele groep (zie ORANJE-438 / ORANJE-1075).
     * 
     * @param brpGroepen
     *            brp groepen
     * @param <T>
     *            groep inhoud type
     * @return De nabewerkte groepen
     */
    private <T extends BrpGroepInhoud> List<BrpGroep<T>> doeNabewerkingVoorRelatie(final List<BrpGroep<T>> brpGroepen) {
        // Nabewerking:
        // Als er meerdere rijen zijn die niet vervallen zijn: Sorteer op datum/tijd registratie. Vul de datum/tijd
        // Verval met datum/tijd registratie van de opvolger en vul actie verval met de actie inhoud van de rij zelf.
        Collections.sort(brpGroepen, Lo3HistorieConversieVariantLB24.RELATIE_NABEWERKING_COMPARATOR);

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
