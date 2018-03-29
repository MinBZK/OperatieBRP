/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.brpnaarlo3;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.function.Function;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.conversie.model.brp.BrpActie;
import nl.bzk.migratiebrp.conversie.model.brp.BrpBetrokkenheid;
import nl.bzk.migratiebrp.conversie.model.brp.BrpGroep;
import nl.bzk.migratiebrp.conversie.model.brp.BrpHistorie;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijst;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijstBuilder;
import nl.bzk.migratiebrp.conversie.model.brp.BrpRelatie;
import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatum;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpBuitenlandsPersoonsnummerInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGeboorteInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGroepInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpNaamgebruikInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpNationaliteitInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpOuderInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpOverlijdenInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpRelatieInhoud;
import org.springframework.stereotype.Component;

/**
 * Conversie stap: bepaal materiele historie.
 *
 * Deze stap volgt de beschrijvingen in Documentatie bidirectionele conversie.doc "Historieconversie per LO3 categorie /
 * BRP groep", paragraaf 7.3.3.1 Categorie 01 Persoon en paragraaf 7.3.3.5 Categorie 06 Overlijden.
 *
 * Voor Naamgebruik is zowel in het conversiedocument als in deze implementatie nog geen definitieve werkwijze
 * vastgelegd.
 */
@Component
public class BrpBepalenMaterieleHistorie {

    private static final Logger LOG = LoggerFactory.getLogger();

    private static List<BrpRelatie> verwerkRelaties(final List<BrpRelatie> stapels) {
        final List<BrpRelatie> result = new ArrayList<>();
        for (final BrpRelatie stapel : stapels) {
            result.add(verwerkRelatie(stapel));
        }

        return result;
    }

    private static BrpRelatie verwerkRelatie(final BrpRelatie stapel) {
        // De relatieinhoud groepen zouden materiele historie moeten krijgen.
        final BrpStapel<BrpRelatieInhoud> relatieStapel = verwerkStapel(stapel.getRelatieStapel(), new RelatieHistorieBepaler(), null, 1);

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

        // Vul de materiele historie van de geboortestapel van de betrokkenheden
        final List<BrpBetrokkenheid> verwerkteBetrokkenheden = new ArrayList<>();
        for (final BrpBetrokkenheid betrokkenheid : stapel.getBetrokkenheden()) {
            verwerkteBetrokkenheden.add(verwerkBetrokkenheid(betrokkenheid));
        }

        final List<BrpBetrokkenheid> beperkteBetrokkenheden;
        if (einddatumRelatie != null) {
            beperkteBetrokkenheden = new ArrayList<>();
            // Einddatum bij betrokkenheden toevoegen om aan te geven dat we na deze datum geen uitspraak
            // doen over deze gegevens (dat is belangrijk voor de terugconversie).
            for (final BrpBetrokkenheid betrokkenheid : verwerkteBetrokkenheden) {
                beperkteBetrokkenheden.add(beperkBetrokkenheid(betrokkenheid, einddatumRelatie));
            }
        } else {
            beperkteBetrokkenheden = verwerkteBetrokkenheden;
        }

        final BrpRelatie.Builder relatieBuilder = new BrpRelatie.Builder(stapel, null, new LinkedHashMap<Long, BrpActie>());
        relatieBuilder.betrokkenheden(beperkteBetrokkenheden);
        relatieBuilder.relatieStapel(relatieStapel);
        return relatieBuilder.build();
    }

    private static BrpBetrokkenheid verwerkBetrokkenheid(final BrpBetrokkenheid betrokkenheid) {
        final BrpStapel<BrpGeboorteInhoud> geboorteStapel =
                verwerkStapel(betrokkenheid.getGeboorteStapel(), new GeboorteHistorieBepaler(), betrokkenheid.getSamengesteldeNaamStapel(), null);

        final BrpStapel<BrpOuderInhoud> ouderStapel = verwerkStapel(betrokkenheid.getOuderStapel(), 1);

        return new BrpBetrokkenheid(
                betrokkenheid.getRol(),
                betrokkenheid.getIdentificatienummersStapel(),
                betrokkenheid.getGeslachtsaanduidingStapel(),
                geboorteStapel,
                betrokkenheid.getOuderlijkGezagStapel(),
                betrokkenheid.getSamengesteldeNaamStapel(),
                ouderStapel,
                betrokkenheid.getIdentiteitStapel());
    }

    private static BrpBetrokkenheid beperkBetrokkenheid(final BrpBetrokkenheid betrokkenheid, final BrpDatum einddatum) {

        return new BrpBetrokkenheid(
                betrokkenheid.getRol(),
                beperkStapel(betrokkenheid.getIdentificatienummersStapel(), einddatum),
                beperkStapel(betrokkenheid.getGeslachtsaanduidingStapel(), einddatum),
                beperkStapel(betrokkenheid.getGeboorteStapel(), einddatum),
                beperkStapel(betrokkenheid.getOuderlijkGezagStapel(), einddatum),
                beperkStapel(betrokkenheid.getSamengesteldeNaamStapel(), einddatum),
                beperkStapel(betrokkenheid.getOuderStapel(), einddatum),
                beperkStapel(betrokkenheid.getIdentiteitStapel(), einddatum));
    }

    private static <T extends BrpGroepInhoud> BrpStapel<T> beperkStapel(final BrpStapel<T> stapel, final BrpDatum einddatum) {
        if (stapel == null) {
            return null;
        }

        final List<BrpGroep<T>> groepen = new ArrayList<>();

        for (final BrpGroep<T> groep : stapel) {
            groepen.add(beperkGroep(groep, einddatum));
        }

        return new BrpStapel<>(groepen);
    }

    private static <T extends BrpGroepInhoud> BrpGroep<T> beperkGroep(final BrpGroep<T> groep, final BrpDatum einddatum) {
        final BrpHistorie hist = groep.getHistorie();
        final BrpDatum eind = hist.getDatumEindeGeldigheid();

        final BrpGroep<T> result;
        if (eind == null) {
            final BrpHistorie historie =
                    new BrpHistorie(
                            hist.getDatumAanvangGeldigheid(),
                            einddatum,
                            hist.getDatumTijdRegistratie(),
                            hist.getDatumTijdVerval(),
                            hist.getNadereAanduidingVerval());

            result = new BrpGroep<>(groep.getInhoud(), historie, groep.getActieInhoud(), groep.getActieVerval(), null);
        } else {
            result = groep;
        }
        return result;

    }

    private static List<BrpStapel<BrpBuitenlandsPersoonsnummerInhoud>> verwerkBuitenlandsPersoonsnummerStapels(
            final List<BrpStapel<BrpBuitenlandsPersoonsnummerInhoud>> buitenlandsPersoonsnummerStapels,
            final List<BrpStapel<BrpNationaliteitInhoud>> nationaliteitStapels) {
        final List<BrpStapel<BrpBuitenlandsPersoonsnummerInhoud>> result = new ArrayList<>();
        if (!buitenlandsPersoonsnummerStapels.isEmpty()) {
            for (final BrpStapel<BrpBuitenlandsPersoonsnummerInhoud> stapel : buitenlandsPersoonsnummerStapels) {
                final BrpStapel<BrpNationaliteitInhoud> nationaliteitStapel = bepaalBijbehorendeNationaliteitStapel(stapel, nationaliteitStapels);
                result.add(verwerkStapel(stapel, null, nationaliteitStapel, null));
            }
        }
        return result;
    }

    private static BrpStapel<BrpNationaliteitInhoud> bepaalBijbehorendeNationaliteitStapel(
            final BrpStapel<BrpBuitenlandsPersoonsnummerInhoud> buitenlandsPersoonsnummerStapel,
            final List<BrpStapel<BrpNationaliteitInhoud>> nationaliteitStapels) {
        final Long actieInhoudId = buitenlandsPersoonsnummerStapel.getLaatsteElement().getActieInhoud().getId();
        for (final BrpStapel<BrpNationaliteitInhoud> nationaliteitStapel : nationaliteitStapels) {
            for (final BrpGroep<BrpNationaliteitInhoud> groep : nationaliteitStapel) {
                if (groep.getActieInhoud().getId().equals(actieInhoudId)) {
                    return nationaliteitStapel;
                }
            }
        }
        throw new IllegalStateException("Kon materiele historie voor buitenlands persoonsnummer niet vinden in nationaliteit stapels.");
    }

    private static <T extends BrpGroepInhoud> BrpStapel<T> verwerkStapel(
            final BrpStapel<T> stapel,
            final Function<T, BrpDatum> bepaler,
            final BrpStapel<?> andereStapel,
            final Integer actieSortering) {
        if (stapel == null) {
            return null;
        }

        return new BrpStapel<>(verwerkGroepen(stapel.getGroepen(), bepaler, andereStapel, actieSortering));
    }

    private static <T extends BrpGroepInhoud> BrpStapel<T> verwerkStapelGeconverteerdMetLb24(
            final BrpStapel<T> stapel,
            final Function<T, BrpDatum> bepaler,
            final BrpStapel<?> andereStapel) {
        if (stapel == null) {
            return null;
        }

        return new BrpStapel<>(verwerkGroepenGeconverteerdMetLb24(stapel.getGroepen(), bepaler, andereStapel));
    }

    private static <T extends BrpGroepInhoud> List<BrpGroep<T>> verwerkGroepenGeconverteerdMetLb24(
            final List<BrpGroep<T>> groepen,
            final Function<T, BrpDatum> bepaler,
            final BrpStapel<?> andereStapel) {
        final List<BrpGroep<T>> nieuweGroepen = new ArrayList<>();

        for (final BrpGroep<T> oudeGroep : groepen) {
            final BrpActie oudeActieInhoud = oudeGroep.getActieInhoud();
            final BrpActie oudeActieVerval = oudeGroep.getActieVerval();

            final BrpHistorie oudeHistorie = oudeGroep.getHistorie();
            final BrpDatum datumAanvangGeldigheid;
            final BrpDatum datumEindeGeldigheid;

            final BrpActie actieInhoud;
            final BrpActie actieVerval;
            final BrpActie actieGeldigheid;

            if (oudeActieVerval != null && !oudeActieVerval.getId().equals(oudeActieInhoud.getId())) {
                if (andereStapel != null) {
                    LOG.trace(String.format("Oude actie verval is gevuld en ongelijk aan oude actie inhoud. Actie verval: %s, actie inhoud %s",
                            oudeActieVerval.getId(), oudeActieInhoud.getId()));
                    datumAanvangGeldigheid = bepaalHistorie(andereStapel, oudeActieInhoud).getDatumAanvangGeldigheid();
                    datumEindeGeldigheid = bepaalHistorie(andereStapel, oudeActieVerval).getDatumAanvangGeldigheid();
                } else {
                    final BrpDatum bepaaldeDatum = bepaler.apply(oudeGroep.getInhoud());
                    datumAanvangGeldigheid = bepaaldeDatum;
                    datumEindeGeldigheid = bepaaldeDatum;
                }

                actieInhoud = verwerkActieSortering(oudeActieInhoud, null);
                actieVerval = verwerkActieSortering(oudeActieInhoud, null);
                actieGeldigheid = verwerkActieSortering(oudeActieVerval, null);
            } else {
                if (andereStapel != null) {
                    LOG.trace(String.format("Oude actie verval is of niet gevuld of gelijk aan oude actie inhoud. Actie verval: %s, actie inhoud %s",
                            oudeActieVerval == null ? "null" : oudeActieVerval.getId(), oudeActieInhoud.getId()));
                    final BrpHistorie brpHistorie = bepaalHistorie(andereStapel, oudeActieInhoud);
                    datumAanvangGeldigheid = brpHistorie.getDatumAanvangGeldigheid();
                    datumEindeGeldigheid = brpHistorie.getDatumEindeGeldigheid();
                } else {
                    datumAanvangGeldigheid = bepaler.apply(oudeGroep.getInhoud());
                    datumEindeGeldigheid = null;
                }
                actieInhoud = verwerkActieSortering(oudeActieInhoud, null);
                actieVerval = verwerkActieSortering(oudeActieVerval, null);
                actieGeldigheid = verwerkActieSortering(oudeGroep.getActieGeldigheid(), null);
            }

            final BrpHistorie nieuweHistorie =
                    new BrpHistorie(
                            datumAanvangGeldigheid,
                            datumEindeGeldigheid,
                            oudeHistorie.getDatumTijdRegistratie(),
                            oudeHistorie.getDatumTijdVerval(),
                            oudeHistorie.getNadereAanduidingVerval());

            final BrpGroep<T> nieuweGroep = new BrpGroep<>(oudeGroep.getInhoud(), nieuweHistorie, actieInhoud, actieVerval, actieGeldigheid);
            nieuweGroepen.add(nieuweGroep);
        }

        return nieuweGroepen;
    }

    private static <T extends BrpGroepInhoud> List<BrpGroep<T>> verwerkGroepen(
            final List<BrpGroep<T>> groepen,
            final Function<T, BrpDatum> bepaler,
            final BrpStapel<?> andereStapel,
            final Integer actieSortering) {
        final List<BrpGroep<T>> nieuweGroepen = new ArrayList<>();

        for (final BrpGroep<T> oudeGroep : groepen) {
            final BrpActie actie = oudeGroep.getActieInhoud();

            final BrpHistorie nieuweHistorie;

            // N.B. In geval van Overlijden komen we altijd in de 'else' tak omdat er geen andereStapel is.
            // Hierdoor komt de logica verder overeen met de omschrijving in het conversiedocument.

            if (actie.isConversieActie() && andereStapel != null) {
                // Zoek materiele historie in andere stapel
                nieuweHistorie = bepaalHistorie(andereStapel, actie);
            } else {
                final BrpHistorie oudeHistorie = oudeGroep.getHistorie();
                nieuweHistorie =
                        new BrpHistorie(
                                bepaler.apply(oudeGroep.getInhoud()),
                                oudeHistorie.getDatumEindeGeldigheid(),
                                oudeHistorie.getDatumTijdRegistratie(),
                                oudeHistorie.getDatumTijdVerval(),
                                oudeHistorie.getNadereAanduidingVerval());
            }

            final BrpActie actieInhoud = verwerkActieSortering(oudeGroep.getActieInhoud(), actieSortering);
            final BrpActie actieVerval = verwerkActieSortering(oudeGroep.getActieVerval(), actieSortering);
            final BrpActie actieGeldigheid = verwerkActieSortering(oudeGroep.getActieGeldigheid(), actieSortering);

            final BrpGroep<T> nieuweGroep = new BrpGroep<>(oudeGroep.getInhoud(), nieuweHistorie, actieInhoud, actieVerval, actieGeldigheid);
            nieuweGroepen.add(nieuweGroep);

        }

        return nieuweGroepen;
    }

    private static <T extends BrpGroepInhoud> BrpStapel<T> verwerkStapel(final BrpStapel<T> stapel, final int actieSortering) {
        if (stapel == null) {
            return null;
        }

        final List<BrpGroep<T>> nieuweGroepen = new ArrayList<>();
        for (final BrpGroep<T> oudeGroep : stapel.getGroepen()) {

            final BrpActie actieInhoud = verwerkActieSortering(oudeGroep.getActieInhoud(), actieSortering);
            final BrpActie actieVerval = verwerkActieSortering(oudeGroep.getActieVerval(), actieSortering);
            final BrpActie actieGeldigheid = verwerkActieSortering(oudeGroep.getActieGeldigheid(), actieSortering);

            final BrpGroep<T> nieuweGroep = new BrpGroep<>(oudeGroep.getInhoud(), oudeGroep.getHistorie(), actieInhoud, actieVerval, actieGeldigheid);
            nieuweGroepen.add(nieuweGroep);
        }
        return new BrpStapel<>(nieuweGroepen);
    }

    private static BrpActie verwerkActieSortering(final BrpActie actie, final Integer sortering) {
        if (sortering == null || actie == null) {
            return actie;
        }

        return new BrpActie.Builder(actie).sortering(sortering).build();
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

    private List<BrpStapel<BrpNationaliteitInhoud>> verwerkNationaliteitStapels(final List<BrpStapel<BrpNationaliteitInhoud>> stapels) {
        final List<BrpStapel<BrpNationaliteitInhoud>> nieuweStapels = new ArrayList<>();
        for (final BrpStapel<BrpNationaliteitInhoud> stapel : stapels) {
            final List<BrpGroep<BrpNationaliteitInhoud>> nieuweGroepen = verwerkNationaliteitStapel(stapel);
            nieuweStapels.add(new BrpStapel<>(nieuweGroepen));
        }
        return nieuweStapels;
    }

    private List<BrpGroep<BrpNationaliteitInhoud>> verwerkNationaliteitStapel(final BrpStapel<BrpNationaliteitInhoud> stapel) {
        final List<BrpGroep<BrpNationaliteitInhoud>> nieuweGroepen = new ArrayList<>();

        for (final BrpGroep<BrpNationaliteitInhoud> groep : stapel.getGroepen()) {
            if (groep.getInhoud().isEindeBijhouding()) {
                final BrpActie actieVerval;
                final BrpHistorie oudeHistorie = groep.getHistorie();
                if (oudeHistorie.getNadereAanduidingVerval() != null) {
                    actieVerval = oudeHistorie.getNadereAanduidingVerval().isInhoudelijkGevuld() ? groep.getActieInhoud() : null;
                } else {
                    actieVerval = null;
                }

                // Nieuwe rij tbv de einde bijhouding
                final BrpHistorie
                        rijHistorie =
                        new BrpHistorie(oudeHistorie.getDatumAanvangGeldigheid(), groep.getInhoud().getMigratieDatum(), oudeHistorie.getDatumTijdRegistratie(),
                                oudeHistorie.getDatumTijdVerval(), oudeHistorie.getNadereAanduidingVerval());
                final BrpActie actieAanpassingGeldigheid = groep.getActieVerval();
                actieAanpassingGeldigheid.setWasActieVerval();
                nieuweGroepen.add(new BrpGroep<>(groep.getInhoud(), rijHistorie, groep.getActieInhoud(), actieVerval, actieAanpassingGeldigheid));

                // Bestaande rij aanpassen, verwijderen migrdatum en einde bijhouding
                final BrpNationaliteitInhoud.Builder builder = new BrpNationaliteitInhoud.Builder(groep.getInhoud());
                builder.migratieDatum(null);
                builder.eindeBijhouding(null);
                nieuweGroepen.add(new BrpGroep<>(builder.build(), oudeHistorie, groep.getActieInhoud(), actieVerval, groep.getActieGeldigheid()));
            } else {
                nieuweGroepen.add(groep);
            }
        }
        return nieuweGroepen;
    }

    /**
     * Converteer.
     * @param persoonslijst persoonslijst
     * @return geconverteerde persoonslijst
     */
    public final BrpPersoonslijst converteer(final BrpPersoonslijst persoonslijst) {

        LOG.debug("converteerLo3Persoonslijst(lo3Persoonslijst.anummer={})", persoonslijst.getActueelAdministratienummer());
        final BrpPersoonslijstBuilder builder = new BrpPersoonslijstBuilder(persoonslijst);

        // Cat 01 Geboortestapel
        LOG.debug("geboorte");
        builder.geboorteStapel(
                verwerkStapel(persoonslijst.getGeboorteStapel(), new GeboorteHistorieBepaler(), persoonslijst.getSamengesteldeNaamStapel(), null));

        // Cat 01 Naamgebruikstapel
        builder.naamgebruikStapel(
                verwerkStapel(persoonslijst.getNaamgebruikStapel(), new NaamgebruikHistorieBepaler(), persoonslijst.getSamengesteldeNaamStapel(), null));

        // Cat 04 Nationaliteit, einde bijhouding van materieel einde voorzien
        LOG.debug("nationaliteit");
        builder.nationaliteitStapels(verwerkNationaliteitStapels(persoonslijst.getNationaliteitStapels()));

        // Cat 04 Nationaliteit, buitenlands persoonsnummer van materieel einde voorzien
        LOG.debug("nationaliteit/buitenlands persoonsnummer");
        builder.buitenlandsPersoonsnummerStapels(
                verwerkBuitenlandsPersoonsnummerStapels(persoonslijst.getBuitenlandsPersoonsnummerStapels(), persoonslijst.getNationaliteitStapels()));

        // Cat06 Overlijden
        LOG.debug("overlijden");
        builder.overlijdenStapel(verwerkStapelGeconverteerdMetLb24(persoonslijst.getOverlijdenStapel(), new OverlijdenHistorieBepaler(), null));

        // Cat08 Adres/indicatie onverwerkt document aawnwezig
        builder.onverwerktDocumentAanwezigIndicatieStapel(
                verwerkStapelGeconverteerdMetLb24(
                        persoonslijst.getOnverwerktDocumentAanwezigIndicatieStapel(),
                        null,
                        persoonslijst.getAdresStapel()));

        // Cat02/03/05/09 Geboorte
        // Sortering toevoegen aan actie zodat de gegevens van de gerelateerde voorgaan op de relatie/ouder gegevens
        // Relatie groepen in relaties
        builder.relaties(verwerkRelaties(persoonslijst.getRelaties()));

        LOG.debug("build persoonslijst");
        return builder.build();
    }

    /**
     * Bepaal de materiele historie voor geboorte.
     *
     * Zie Historieconversie per LO3 categorie / BRP groep, Categorie 01 Persoon in Documentatie bidirectionele
     * conversie.
     */
    private static final class GeboorteHistorieBepaler implements Function<BrpGeboorteInhoud, BrpDatum> {
        @Override
        public BrpDatum apply(final BrpGeboorteInhoud inhoud) {
            return inhoud.getGeboortedatum();
        }
    }

    /**
     * Bepaal de materiele historie voor naamgebruik.
     */
    private static final class NaamgebruikHistorieBepaler implements Function<BrpNaamgebruikInhoud, BrpDatum> {
        @Override
        public BrpDatum apply(final BrpNaamgebruikInhoud inhoud) {
            return null;
        }
    }

    /**
     * Bepaal de materiele historie voor overlijden.
     */
    private static final class OverlijdenHistorieBepaler implements Function<BrpOverlijdenInhoud, BrpDatum> {
        @Override
        public BrpDatum apply(final BrpOverlijdenInhoud inhoud) {
            return new BrpDatum(inhoud.getDatum().getWaarde(), null);
        }
    }

    /**
     * Bepaal de materiele historie voor relatie.
     */
    private static final class RelatieHistorieBepaler implements Function<BrpRelatieInhoud, BrpDatum> {
        @Override
        public BrpDatum apply(final BrpRelatieInhoud inhoud) {
            return inhoud.getDatumEinde() != null ? inhoud.getDatumEinde() : inhoud.getDatumAanvang();
        }
    }
}
