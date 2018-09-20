/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.proces.brpnaarlo3;

import java.util.ArrayList;
import java.util.List;

import nl.moderniseringgba.migratie.conversie.model.brp.BrpActie;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpBetrokkenheid;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpGroep;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpGroepInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpHistorie;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpPersoonslijst;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpPersoonslijstBuilder;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpRelatie;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpStapel;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpDatum;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpSoortActieCode;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpGeboorteInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpInschrijvingInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpOuderInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpOverlijdenInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpRelatieInhoud;
import nl.moderniseringgba.migratie.logging.Logger;
import nl.moderniseringgba.migratie.logging.LoggerFactory;

import org.springframework.stereotype.Component;

/**
 * Conversie stap: bepaal materiele historie.
 */
@Component
public class BrpBepalenMaterieleHistorie {

    private static final Logger LOG = LoggerFactory.getLogger();

    /**
     * Converteer.
     * 
     * @param persoonslijst
     *            persoonslijst
     * @return geconverteerde persoonslijst
     */
    public final BrpPersoonslijst converteer(final BrpPersoonslijst persoonslijst) {

        LOG.debug("converteerLo3Persoonslijst(lo3Persoonslijst.anummer={})",
                persoonslijst.getActueelAdministratienummer());
        final BrpPersoonslijstBuilder builder = new BrpPersoonslijstBuilder(persoonslijst);

        // Geboorte
        LOG.debug("geboorte");
        builder.geboorteStapel(verwerkStapel(persoonslijst.getGeboorteStapel(), new GeboorteHistorieBepaler(),
                persoonslijst.getSamengesteldeNaamStapel(), null));

        // Inschrijving
        LOG.debug("inschrijving");
        builder.inschrijvingStapel(verwerkStapel(persoonslijst.getInschrijvingStapel(),
                new InschrijvingHistorieBepaler(), persoonslijst.getSamengesteldeNaamStapel(), null));

        // Overlijden
        LOG.debug("overlijden");
        builder.overlijdenStapel(verwerkStapel(persoonslijst.getOverlijdenStapel(), new OverlijdenHistorieBepaler(),
                null, null));

        // Geboorte in relaties
        // Sortering toevoegen aan actie zodat de gegevens van de gerelateerde voorgaan op de relatie/ouder gegevens
        // Relatie groepn in relaties
        builder.relaties(verwerkRelaties(persoonslijst.getRelaties()));

        LOG.debug("build persoonslijst");
        return builder.build();
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    private static List<BrpRelatie> verwerkRelaties(final List<BrpRelatie> stapels) {
        if (stapels == null) {
            return null;
        }

        final List<BrpRelatie> result = new ArrayList<BrpRelatie>();
        for (final BrpRelatie stapel : stapels) {
            result.add(verwerkRelatie(stapel));
        }

        return result;
    }

    private static BrpRelatie verwerkRelatie(final BrpRelatie stapel) {
        // De relatieinhoud groepen zouden materiele historie moeten krijgen.
        final BrpStapel<BrpRelatieInhoud> relatieStapel =
                verwerkStapel(stapel.getRelatieStapel(), new RelatieHistorieBepaler(), null, 1);

        // Bepaal de einddatum van de relatie (kan alleen bij huwelijken/partnerschappen
        BrpDatum einddatumRelatie = null;
        if (relatieStapel != null) {
            for (final BrpGroep<BrpRelatieInhoud> relatieGroep : relatieStapel) {
                final BrpHistorie relatieHistorie = relatieGroep.getHistorie();

                if (relatieHistorie.getDatumTijdVerval() == null && relatieHistorie.getDatumEindeGeldigheid() != null) {
                    einddatumRelatie = relatieHistorie.getDatumEindeGeldigheid();
                }
            }
        }

        final List<BrpBetrokkenheid> verwerkteBetrokkenheden = new ArrayList<BrpBetrokkenheid>();
        for (final BrpBetrokkenheid betrokkenheid : stapel.getBetrokkenheden()) {
            verwerkteBetrokkenheden.add(verwerkBetrokkenheid(betrokkenheid));
        }

        final List<BrpBetrokkenheid> beperkteBetrokkenheden;
        if (einddatumRelatie != null) {
            beperkteBetrokkenheden = new ArrayList<BrpBetrokkenheid>();
            // Einddatum bij betrokkenheden toevoegen om aan te geven dat we na deze datum geen uitspraak
            // doen over deze gegevens (dat is belangrijk voor de terugconversie).
            for (final BrpBetrokkenheid betrokkenheid : verwerkteBetrokkenheden) {
                beperkteBetrokkenheden.add(beperkBetrokkenheid(betrokkenheid, einddatumRelatie));
            }
        } else {
            beperkteBetrokkenheden = verwerkteBetrokkenheden;
        }

        return new BrpRelatie(stapel.getSoortRelatieCode(), stapel.getRolCode(), beperkteBetrokkenheden,
                relatieStapel);
    }

    private static BrpBetrokkenheid verwerkBetrokkenheid(final BrpBetrokkenheid betrokkenheid) {
        final BrpStapel<BrpGeboorteInhoud> geboorteStapel =
                verwerkStapel(betrokkenheid.getGeboorteStapel(), new GeboorteHistorieBepaler(),
                        betrokkenheid.getSamengesteldeNaamStapel(), null);

        final BrpStapel<BrpOuderInhoud> ouderStapel = verwerkStapel(betrokkenheid.getOuderStapel(), 1);

        return new BrpBetrokkenheid(betrokkenheid.getRol(), betrokkenheid.getIdentificatienummersStapel(),
                betrokkenheid.getGeslachtsaanduidingStapel(), geboorteStapel,
                betrokkenheid.getOuderlijkGezagStapel(), betrokkenheid.getSamengesteldeNaamStapel(), ouderStapel);
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    private static BrpBetrokkenheid
            beperkBetrokkenheid(final BrpBetrokkenheid betrokkenheid, final BrpDatum einddatum) {

        return new BrpBetrokkenheid(betrokkenheid.getRol(), beperkStapel(
                betrokkenheid.getIdentificatienummersStapel(), einddatum), beperkStapel(
                betrokkenheid.getGeslachtsaanduidingStapel(), einddatum), beperkStapel(
                betrokkenheid.getGeboorteStapel(), einddatum), beperkStapel(betrokkenheid.getOuderlijkGezagStapel(),
                einddatum), beperkStapel(betrokkenheid.getSamengesteldeNaamStapel(), einddatum), beperkStapel(
                betrokkenheid.getOuderStapel(), einddatum));
    }

    private static <T extends BrpGroepInhoud> BrpStapel<T> beperkStapel(
            final BrpStapel<T> stapel,
            final BrpDatum einddatum) {
        if (stapel == null) {
            return null;
        }

        final List<BrpGroep<T>> groepen = new ArrayList<BrpGroep<T>>();

        for (final BrpGroep<T> groep : stapel) {
            groepen.add(beperkGroep(groep, einddatum));
        }

        return new BrpStapel<T>(groepen);
    }

    private static <T extends BrpGroepInhoud> BrpGroep<T> beperkGroep(
            final BrpGroep<T> groep,
            final BrpDatum einddatum) {
        final BrpHistorie hist = groep.getHistorie();
        final BrpDatum eind = hist.getDatumEindeGeldigheid();

        final BrpGroep<T> result;
        if (eind == null) {
            final BrpHistorie historie =
                    new BrpHistorie(hist.getDatumAanvangGeldigheid(), einddatum, hist.getDatumTijdRegistratie(),
                            hist.getDatumTijdVerval());

            result =
                    new BrpGroep<T>(groep.getInhoud(), historie, groep.getActieInhoud(), groep.getActieVerval(), null);
        } else {
            if (eind.compareTo(eind) <= 0) {
                result = groep;
            } else {
                final BrpHistorie historie =
                        new BrpHistorie(hist.getDatumAanvangGeldigheid(), einddatum, hist.getDatumTijdRegistratie(),
                                hist.getDatumTijdVerval());

                result =
                        new BrpGroep<T>(groep.getInhoud(), historie, groep.getActieInhoud(), groep.getActieVerval(),
                                null);

            }

        }
        return result;

    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    private static <T extends BrpGroepInhoud> BrpStapel<T> verwerkStapel(
            final BrpStapel<T> stapel,
            final MaterieeleHistorieBepaler<T> bepaler,
            final BrpStapel<?> andereStapel,
            final Integer actieSortering) {
        if (stapel == null) {
            return null;
        }

        return new BrpStapel<T>(verwerkGroepen(stapel.getGroepen(), bepaler, andereStapel, actieSortering));
    }

    private static <T extends BrpGroepInhoud> List<BrpGroep<T>> verwerkGroepen(
            final List<BrpGroep<T>> groepen,
            final MaterieeleHistorieBepaler<T> bepaler,
            final BrpStapel<?> andereStapel,
            final Integer actieSortering) {
        final List<BrpGroep<T>> nieuweGroepen = new ArrayList<BrpGroep<T>>();

        for (final BrpGroep<T> oudeGroep : groepen) {
            final BrpActie actie = oudeGroep.getActieInhoud();

            final BrpHistorie nieuweHistorie;

            if (BrpSoortActieCode.CONVERSIE_GBA.equals(actie.getSoortActieCode()) && andereStapel != null) {
                // Zoek materiele historie in andere stapel
                nieuweHistorie = bepaalHistorie(andereStapel, actie);
            } else {
                final BrpHistorie oudeHistorie = oudeGroep.getHistorie();
                nieuweHistorie =
                        new BrpHistorie(bepaler.bepaalDatumAanvangGeldigheid(oudeGroep.getInhoud()),
                                oudeHistorie.getDatumEindeGeldigheid(), oudeHistorie.getDatumTijdRegistratie(),
                                oudeHistorie.getDatumTijdVerval());
            }

            final BrpActie actieInhoud = verwerkActieSortering(oudeGroep.getActieInhoud(), actieSortering);
            final BrpActie actieVerval = verwerkActieSortering(oudeGroep.getActieVerval(), actieSortering);
            final BrpActie actieGeldigheid = verwerkActieSortering(oudeGroep.getActieGeldigheid(), actieSortering);

            final BrpGroep<T> nieuweGroep =
                    new BrpGroep<T>(oudeGroep.getInhoud(), nieuweHistorie, actieInhoud, actieVerval, actieGeldigheid);
            nieuweGroepen.add(nieuweGroep);

        }

        return nieuweGroepen;

    }

    private static <T extends BrpGroepInhoud> BrpStapel<T> verwerkStapel(
            final BrpStapel<T> stapel,
            final int actieSortering) {
        if (stapel == null) {
            return null;
        }

        final List<BrpGroep<T>> nieuweGroepen = new ArrayList<BrpGroep<T>>();
        for (final BrpGroep<T> oudeGroep : stapel.getGroepen()) {

            final BrpActie actieInhoud = verwerkActieSortering(oudeGroep.getActieInhoud(), actieSortering);
            final BrpActie actieVerval = verwerkActieSortering(oudeGroep.getActieVerval(), actieSortering);
            final BrpActie actieGeldigheid = verwerkActieSortering(oudeGroep.getActieGeldigheid(), actieSortering);

            final BrpGroep<T> nieuweGroep =
                    new BrpGroep<T>(oudeGroep.getInhoud(), oudeGroep.getHistorie(), actieInhoud, actieVerval,
                            actieGeldigheid);
            nieuweGroepen.add(nieuweGroep);
        }
        return new BrpStapel<T>(nieuweGroepen);
    }

    private static BrpActie verwerkActieSortering(final BrpActie actie, final Integer sortering) {
        if (sortering == null || actie == null) {
            return actie;
        }

        return new BrpActie(actie.getId(), actie.getSoortActieCode(), actie.getPartijCode(), actie.getVerdragCode(),
                actie.getDatumTijdOntlening(), actie.getDatumTijdRegistratie(), actie.getDocumentStapels(),
                sortering, actie.getLo3Herkomst());

    }

    private static BrpHistorie bepaalHistorie(final BrpStapel<?> stapel, final BrpActie actie) {
        final Long actieId = actie.getId();
        for (final BrpGroep<?> groep : stapel) {
            if (actieId.equals(groep.getActieInhoud().getId())) {
                return groep.getHistorie();
            }
        }

        throw new IllegalStateException("Kon materiele historie van conversie actie niet vinden in andere stapel.");

    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    /**
     * Interface om materiele historie voor een groep te bepalen.
     * 
     * @param <T>
     *            groep inhoud type
     */
    private interface MaterieeleHistorieBepaler<T extends BrpGroepInhoud> {
        /**
         * Bepaal de datum aanvang geldigheid obv de inhoud.
         * 
         * @param inhoud
         *            inhoud
         * @return datum aanvang geldigheid
         */
        BrpDatum bepaalDatumAanvangGeldigheid(T inhoud);
    }

    /**
     * Bepaal de materiele historie voor geboorte.
     */
    private static final class GeboorteHistorieBepaler implements MaterieeleHistorieBepaler<BrpGeboorteInhoud> {
        @Override
        public BrpDatum bepaalDatumAanvangGeldigheid(final BrpGeboorteInhoud inhoud) {
            return inhoud.getGeboortedatum();
        }
    }

    /**
     * Bepaal de materiele historie voor inschrijving.
     */
    private static final class InschrijvingHistorieBepaler implements
            MaterieeleHistorieBepaler<BrpInschrijvingInhoud> {
        @Override
        public BrpDatum bepaalDatumAanvangGeldigheid(final BrpInschrijvingInhoud inhoud) {
            return inhoud.getDatumInschrijving();
        }
    }

    /**
     * Bepaal de materiele historie voor overlijden.
     */
    private static final class OverlijdenHistorieBepaler implements MaterieeleHistorieBepaler<BrpOverlijdenInhoud> {
        @Override
        public BrpDatum bepaalDatumAanvangGeldigheid(final BrpOverlijdenInhoud inhoud) {
            return inhoud.getDatum();
        }
    }

    /**
     * Bepaal de materiele historie voor relatie.
     */
    private static final class RelatieHistorieBepaler implements MaterieeleHistorieBepaler<BrpRelatieInhoud> {
        @Override
        public BrpDatum bepaalDatumAanvangGeldigheid(final BrpRelatieInhoud inhoud) {
            return inhoud.getDatumEinde() != null ? inhoud.getDatumEinde() : inhoud.getDatumAanvang();
        }
    }
}
