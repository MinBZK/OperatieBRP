/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.brpnaarlo3.attributen;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import nl.bzk.migratiebrp.conversie.model.brp.BrpActie;
import nl.bzk.migratiebrp.conversie.model.brp.BrpGroep;
import nl.bzk.migratiebrp.conversie.model.brp.BrpHistorie;
import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatum;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.Validatie;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpBijhoudingInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGroepInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpMigratieInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpNationaliteitInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Categorie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Documentatie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3CategorieInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Component;

/**
 * Categorie converteerder.
 *
 * Implementaties van deze klasse meoten de methode {@link #bepaalConverteerder} implementeren en daar de
 * {@link BrpGroepConverteerder} teruggeven voor de stapels waarmee de {@link #converteer} methode aangeroepen wordt.
 *
 * @param <L>
 *            LO3 inhoud type
 */
@Component
public abstract class AbstractBrpCategorieConverteerder<L extends Lo3CategorieInhoud> {

    private static final Comparator<BrpGroep<? extends BrpGroepInhoud>> GROEPEN_COMPARATOR = new Comparator<BrpGroep<? extends BrpGroepInhoud>>() {
        @Override
        public int compare(final BrpGroep<? extends BrpGroepInhoud> groep0, final BrpGroep<? extends BrpGroepInhoud> groep1) {
            final BrpHistorie his0 = groep0.getHistorie();
            final BrpHistorie his1 = groep1.getHistorie();

            int result = his0.getDatumAanvangGeldigheid().compareTo(his1.getDatumAanvangGeldigheid());

            if (result == 0) {
                result = his0.getDatumTijdRegistratie().compareTo(his1.getDatumTijdRegistratie());
            }
            return result;
        }
    };
    private static final BrpActieComparator BRP_ACTIE_COMPARATOR = new BrpActieComparator();

    private static final List<Class<? extends BrpGroepInhoud>> UITZONDERING_INHOUD_CLASSES = new ArrayList<>();

    static {
        UITZONDERING_INHOUD_CLASSES.add(BrpBijhoudingInhoud.class);
        UITZONDERING_INHOUD_CLASSES.add(BrpMigratieInhoud.class);
    }

    private static <T extends BrpGroepInhoud> List<BrpGroep<T>> bepaalGroepen(final BrpActie actie, final BrpStapel<T> stapel) {
        final List<BrpGroep<T>> groepen = new ArrayList<>();
        if (stapel == null) {
            return groepen;
        }

        final Long actieId = actie.getId();

        for (final BrpGroep<T> groep : stapel) {
            if (actie.isConversieActie()) {
                if (isActie(actieId, groep.getActieGeldigheid())
                    || isActie(actieId, groep.getActieVerval())
                       && (Validatie.isAttribuutGevuld(groep.getHistorie().getNadereAanduidingVerval()) || isEindeBijhoudingNationliteit(groep)))
                {
                    final BrpGroep<T> groepActieInhoud = zoekActieAlsInhoud(stapel, actieId);
                    if (groepActieInhoud == null || groep.equals(groepActieInhoud)) {
                        groepen.add(groep);
                    }
                } else if (isActie(actieId, groep.getActieInhoud())) {
                    groepen.add(groep);
                }
            } else {
                groepen.add(groep);
            }
        }

        // Sorteer de groepen
        Collections.sort(groepen, GROEPEN_COMPARATOR);

        return groepen;
    }

    private static <T extends BrpGroepInhoud> boolean isEindeBijhoudingNationliteit(final BrpGroep<T> groep) {
        return groep.getInhoud() instanceof BrpNationaliteitInhoud && ((BrpNationaliteitInhoud) groep.getInhoud()).isEindeBijhouding();
    }

    private static <T extends BrpGroepInhoud> BrpGroep<T> zoekActieAlsInhoud(final BrpStapel<T> stapel, final Long actieId) {
        BrpGroep<T> resultaat = null;
        for (final BrpGroep<T> groep : stapel) {
            if (isActie(actieId, groep.getActieInhoud())) {
                resultaat = groep;
            }
        }
        return resultaat;
    }

    private static boolean isActie(final Long actieId, final BrpActie brpActie) {
        return brpActie != null && actieId.equals(brpActie.getId());
    }

    private static List<BrpActie> bepaalActies(final BrpStapel<?>... stapels) {
        final List<BrpActie> acties = new ArrayList<>();

        if (stapels != null) {
            for (final BrpStapel<?> stapel : stapels) {
                if (stapel != null) {
                    for (final BrpGroep<?> groep : stapel) {
                        voegActieToe(acties, groep.getActieInhoud());
                        voegActieToe(acties, groep.getActieGeldigheid());
                        voegActieToe(acties, groep.getActieVerval());
                    }
                }
            }
        }

        Collections.sort(acties, BRP_ACTIE_COMPARATOR);

        return acties;
    }

    private static void voegActieToe(final List<BrpActie> acties, final BrpActie actie) {
        if (actie == null) {
            return;
        }

        for (final BrpActie check : acties) {
            if (check.getId().equals(actie.getId())) {
                return;
            }
        }

        acties.add(actie);
    }

    /**
     * Logger (zodat subclasses hun eigen logger krijgen).
     *
     * @return logger
     */
    protected abstract Logger getLogger();

    /**
     * Converteer.
     *
     * @param brpStapels
     *            brp stapels
     * @return lo3 stapel
     */
    @SafeVarargs
    public final Lo3Stapel<L> converteer(final BrpStapel<? extends BrpGroepInhoud>... brpStapels) {
        getLogger().debug("converteer(#brpStapels={}", brpStapels.length);
        final List<Lo3CategorieWrapper<L>> lo3CategorieWrappers = new ArrayList<>();

        final List<BrpActie> acties = bepaalActies(brpStapels);
        for (final BrpActie actie : acties) {
            getLogger().debug("te verwerken actie.id: {}", actie.getId());
            getLogger().debug("categorieen voor verwerking ({})", lo3CategorieWrappers.size());
            for (final BrpStapel<? extends BrpGroepInhoud> stapel : brpStapels) {
                verwerkStapel(actie, stapel, lo3CategorieWrappers);
            }

            getLogger().debug("categorieen na verwerking ({}):", lo3CategorieWrappers.size());
        }

        getLogger().debug("geconverteerde categorieen ({})", lo3CategorieWrappers.size());

        bepaalActueel(lo3CategorieWrappers, brpStapels);

        final List<Lo3Categorie<L>> lo3Categorieen = new ArrayList<>(lo3CategorieWrappers.size());
        for (final Lo3CategorieWrapper<L> lo3CategorieWrapper : lo3CategorieWrappers) {
            lo3Categorieen.add(lo3CategorieWrapper.getLo3Categorie());
        }

        return lo3CategorieWrappers.isEmpty() ? null : new Lo3Stapel<>(lo3Categorieen);
    }

    private <B extends BrpGroepInhoud> void verwerkStapel(
        final BrpActie actie,
        final BrpStapel<B> stapel,
        final List<Lo3CategorieWrapper<L>> categorieen)
    {
        getLogger().debug("verwerkStapel");
        final List<BrpGroep<B>> groepen = bepaalGroepen(actie, stapel);
        getLogger().debug("#groepen: {}", groepen.size());

        if (!groepen.isEmpty()) {
            getLogger().debug("bepaalConverteerder voor groep");
            final BrpGroepConverteerder<B, L> converteerder = bepaalConverteerder(groepen.get(0).getInhoud());
            getLogger().debug("converteerder: {}", converteerder);
            converteerder.converteer(actie, groepen, stapel, categorieen);
        }
    }

    /**
     * Bepaal de actuele rij in Lo3 en verplaats die naar het eind van de lijst categorien.
     */

    private void bepaalActueel(final List<Lo3CategorieWrapper<L>> lo3CategorieWrappers, final BrpStapel<? extends BrpGroepInhoud>... brpStapels) {

        // Stap 1 alleen juiste LO3 voorkomens verzamelen
        final List<Lo3CategorieWrapper<L>> juisteLo3Voorkomens = bepaalActueelStap1(lo3CategorieWrappers);

        // Stap 2 alle BrpGroepen die bij de juiste lo3 voorkomens horen, verzamelen
        final List<BrpGroep<? extends BrpGroepInhoud>> mogelijkeActueleBrpGroepen = bepaalActueelStap2(juisteLo3Voorkomens, brpStapels);
        // Stap 3 Als er een BRP-rij is die niet vervallen en geen datum einde geldigheid heeft -> mogelijk actueel.
        Pair<BrpActie, BrpGroep<? extends BrpGroepInhoud>> actueleActieGroep = bepaalActueelStap3(mogelijkeActueleBrpGroepen);

        if (actueleActieGroep == null) {
            // Stap 4a Lijst maken van brp groepen die ts verval hebben, daarna door stap 5
            final Pair<BrpActie, BrpGroep<? extends BrpGroepInhoud>> stap4aActieGroep = bepaalActueelStap4a(mogelijkeActueleBrpGroepen);

            // Stap 4b Lijst maken van brp groepen die geen actie verval en geen ts verval hebben, daarna door stap 5
            final Pair<BrpActie, BrpGroep<? extends BrpGroepInhoud>> stap4bAcieGroep = bepaalActueelStap4b(mogelijkeActueleBrpGroepen);

            // Doe stap 6
            actueleActieGroep = bepaalActueelStap6(stap4aActieGroep, stap4bAcieGroep);
        }

        // Stap 7 actuele Lo3CategorieWrappers bepalen
        final Lo3CategorieWrapper<L> actueleLo3Categorie = bepaalActueelStap7(lo3CategorieWrappers, actueleActieGroep);

        // Verplaats eventueel gevonden actuele rij naar de laatste posistie
        if (actueleLo3Categorie != null) {
            lo3CategorieWrappers.remove(actueleLo3Categorie);
            lo3CategorieWrappers.add(actueleLo3Categorie);
        }
    }

    /**
     * Filtert alle wrappers die als "onjuist" zijn gemarkeerd uit de opgegeven lijst.
     *
     * @param lo3CategorieWrappers
     *            lijst van Lo3CategorieWrapper
     * @return een lijst met alleen "juiste" Lo3CategorieWrapper
     */
    private List<Lo3CategorieWrapper<L>> bepaalActueelStap1(final List<Lo3CategorieWrapper<L>> lo3CategorieWrappers) {
        final List<Lo3CategorieWrapper<L>> juisteLo3Voorkomens = new ArrayList<>();
        for (final Lo3CategorieWrapper<L> lo3CategorieWrapper : lo3CategorieWrappers) {
            if (!lo3CategorieWrapper.getLo3Categorie().getHistorie().isOnjuist()) {
                juisteLo3Voorkomens.add(lo3CategorieWrapper);
            }
        }
        return juisteLo3Voorkomens;
    }

    private List<BrpGroep<? extends BrpGroepInhoud>> bepaalActueelStap2(
        final List<Lo3CategorieWrapper<L>> juisteLo3Voorkomens,
        final BrpStapel<? extends BrpGroepInhoud>... brpStapels)
    {
        final List<BrpGroep<? extends BrpGroepInhoud>> mogelijkeActueleBrpGroepen = new ArrayList<>();
        for (final Lo3CategorieWrapper<L> juistLo3Voorkomen : juisteLo3Voorkomens) {
            // documentatie Id is gelijk aan actieId.
            final Long documentatieId = juistLo3Voorkomen.getLo3Categorie().getDocumentatie().getId();
            for (final BrpStapel<? extends BrpGroepInhoud> brpStapel : brpStapels) {
                if (brpStapel != null) {
                    for (final BrpGroep<? extends BrpGroepInhoud> brpGroep : brpStapel.getGroepen()) {
                        if (isActieBijBrpGroep(documentatieId, brpGroep)
                            && isActieMogelijkActueel(documentatieId, brpGroep)
                            && !mogelijkeActueleBrpGroepen.contains(brpGroep))
                        {
                            mogelijkeActueleBrpGroepen.add(brpGroep);
                        }
                    }
                }
            }
        }
        return mogelijkeActueleBrpGroepen;
    }

    private Pair<BrpActie, BrpGroep<? extends BrpGroepInhoud>> bepaalActueelStap3(final List<BrpGroep<? extends BrpGroepInhoud>> brpGroepen) {
        final List<BrpGroep<? extends BrpGroepInhoud>> resultaat = new ArrayList<>();
        for (final BrpGroep<? extends BrpGroepInhoud> brpGroep : brpGroepen) {
            if (brpGroep.getActieVerval() == null && brpGroep.getHistorie().getDatumEindeGeldigheid() == null) {
                resultaat.add(brpGroep);
            }
        }

        return bepaalActueelStap5(resultaat);
    }

    private Pair<BrpActie, BrpGroep<? extends BrpGroepInhoud>> bepaalActueelStap4a(final List<BrpGroep<? extends BrpGroepInhoud>> brpGroepen) {
        final List<BrpGroep<? extends BrpGroepInhoud>> vervallenGroepen = new ArrayList<>();
        for (final BrpGroep<? extends BrpGroepInhoud> brpGroep : brpGroepen) {
            // brpGroep.getActieVerval() != null
            if (Validatie.isAttribuutGevuld(brpGroep.getHistorie().getNadereAanduidingVerval()) && !vervallenGroepen.contains(brpGroep)) {
                vervallenGroepen.add(brpGroep);
            }

            if (brpGroep.getActieVerval() != null && !vervallenGroepen.contains(brpGroep)) {
                vervallenGroepen.add(brpGroep);
            }
        }

        return bepaalActueelStap5(vervallenGroepen);
    }

    private Pair<BrpActie, BrpGroep<? extends BrpGroepInhoud>> bepaalActueelStap4b(final List<BrpGroep<? extends BrpGroepInhoud>> brpGroepen) {
        final List<BrpGroep<? extends BrpGroepInhoud>> resultaat = new ArrayList<>();
        for (final BrpGroep<? extends BrpGroepInhoud> brpGroep : brpGroepen) {
            // brpGroep.getActieVerval() == null
            if (!Validatie.isAttribuutGevuld(brpGroep.getHistorie().getNadereAanduidingVerval()) && !resultaat.contains(brpGroep)) {
                resultaat.add(brpGroep);
            }
        }
        return bepaalActueelStap5(resultaat);
    }

    private Pair<BrpActie, BrpGroep<? extends BrpGroepInhoud>> bepaalActueelStap5(final List<BrpGroep<? extends BrpGroepInhoud>> actueleGroepen) {
        // Stap 5 groepen met de meest recente datum aanvang geldigheid verzamelen
        final List<BrpGroep<? extends BrpGroepInhoud>> groepenMetMeestRecenteDag = getGroepenMetMeestRecenteDatumAanvangGeldigheid(actueleGroepen);

        // Stap 5A acties verzamelen
        final List<Pair<BrpActie, BrpGroep<? extends BrpGroepInhoud>>> brpActies = getActies(groepenMetMeestRecenteDag);

        // Stap 5B actie met de meest recente datum/tijd registratie zoeken
        return getActiesMeestRecenteDatumTijdRegistratie(brpActies);
    }

    private Pair<BrpActie, BrpGroep<? extends BrpGroepInhoud>> bepaalActueelStap6(
        final Pair<BrpActie, BrpGroep<? extends BrpGroepInhoud>> stap4aActieGroep,
        final Pair<BrpActie, BrpGroep<? extends BrpGroepInhoud>> stap4bAcieGroep)
    {
        final Pair<BrpActie, BrpGroep<? extends BrpGroepInhoud>> resultaat;
        if (stap4aActieGroep == null && stap4bAcieGroep == null) {
            resultaat = null;
        } else if (stap4aActieGroep == null) {
            resultaat = stap4bAcieGroep;
        } else if (stap4bAcieGroep == null) {
            resultaat = stap4aActieGroep;
        } else {
            final BrpGroep<? extends BrpGroepInhoud> stap4aGroep = stap4aActieGroep.getRight();
            final BrpGroep<? extends BrpGroepInhoud> stap4bGroep = stap4bAcieGroep.getRight();

            final BrpDatum stap4aDatumAanvang = stap4aGroep.getHistorie().getDatumAanvangGeldigheid();
            final BrpDatum stap4bDatumAanvang = stap4bGroep.getHistorie().getDatumAanvangGeldigheid();
            final BrpDatum stap4bDatumEinde = stap4bGroep.getHistorie().getDatumEindeGeldigheid();

            if (stap4aDatumAanvang.compareTo(stap4bDatumAanvang) > 0 && stap4aDatumAanvang.compareTo(stap4bDatumEinde) > 0) {
                resultaat = stap4aActieGroep;
            } else {
                resultaat = stap4bAcieGroep;
            }
        }
        return resultaat;
    }

    private Lo3CategorieWrapper<L> bepaalActueelStap7(
        final List<Lo3CategorieWrapper<L>> lo3CategorieWrappers,
        final Pair<BrpActie, BrpGroep<? extends BrpGroepInhoud>> actueleActieGroep)
    {
        Lo3CategorieWrapper<L> actueleLo3Categorie = null;
        if (actueleActieGroep != null) {
            for (final Lo3CategorieWrapper<L> lo3CategorieWrapper : lo3CategorieWrappers) {
                final Lo3Documentatie documentatie = lo3CategorieWrapper.getLo3Categorie().getDocumentatie();
                if (documentatie != null && Long.valueOf(documentatie.getId()).equals(actueleActieGroep.getLeft().getId())) {
                    actueleLo3Categorie = lo3CategorieWrapper;
                    break;
                }
            }
        }
        return actueleLo3Categorie;
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    private Pair<BrpActie, BrpGroep<? extends BrpGroepInhoud>> getActiesMeestRecenteDatumTijdRegistratie(
        final List<Pair<BrpActie, BrpGroep<? extends BrpGroepInhoud>>> brpActies)
    {
        // Stap 5B.a
        final List<Pair<BrpActie, BrpGroep<? extends BrpGroepInhoud>>> actueleActies = new ArrayList<>();
        for (final Pair<BrpActie, BrpGroep<? extends BrpGroepInhoud>> actie : brpActies) {
            if (actueleActies.isEmpty()) {
                actueleActies.add(actie);
            } else {
                final BrpActie a1 = actie.getLeft();
                final BrpActie a2 = actueleActies.get(0).getLeft();

                // Als de huidig geselecteerde actie (a1) gelijk is aan de mogelijke actuele actie (a2) dan verder met
                // de volgende actie
                if (a1.equals(a2)) {
                    continue;
                }

                final int compareResult = a1.getDatumTijdRegistratie().compareTo(a2.getDatumTijdRegistratie());
                if (compareResult == 0) {
                    actueleActies.add(actie);
                } else if (compareResult > 0) {
                    actueleActies.clear();
                    actueleActies.add(actie);
                }
            }
        }

        // Stap 5B.b
        Pair<BrpActie, BrpGroep<? extends BrpGroepInhoud>> actueleActie = null;
        if (actueleActies.size() == 1) {
            actueleActie = actueleActies.get(0);
        } else if (actueleActies.size() > 1) {
            // Sort op basis van conversievolgorde uit de lo3herkomst die aan actie zit.
            Collections.sort(actueleActies, new BrpActieConversieVolgordeComparator());
        }

        return actueleActie;
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    private List<Pair<BrpActie, BrpGroep<? extends BrpGroepInhoud>>> getActies(final List<BrpGroep<? extends BrpGroepInhoud>> groepenMetMeestRecenteDag) {
        final List<Pair<BrpActie, BrpGroep<? extends BrpGroepInhoud>>> brpActies = new ArrayList<>();
        for (final BrpGroep<? extends BrpGroepInhoud> brpGroep : groepenMetMeestRecenteDag) {
            final BrpActie actieVerval = brpGroep.getActieVerval();
            final BrpActie actieInhoud = brpGroep.getActieInhoud();
            final BrpActie actieGeldigheid = brpGroep.getActieGeldigheid();

            if (actieVerval != null && !actieVerval.equals(actieInhoud)) {
                brpActies.add(new ImmutablePair<BrpActie, BrpGroep<? extends BrpGroepInhoud>>(actieVerval, brpGroep));
            } else if ((actieVerval == null || actieVerval.equals(actieInhoud)) && actieGeldigheid != null) {
                brpActies.add(new ImmutablePair<BrpActie, BrpGroep<? extends BrpGroepInhoud>>(actieGeldigheid, brpGroep));
            } else if (actieVerval == null) {
                // hoeft hier niet te controleren op actieGeldigheid == null, want dit is altijd waar.
                brpActies.add(new ImmutablePair<BrpActie, BrpGroep<? extends BrpGroepInhoud>>(actieInhoud, brpGroep));
            }
        }
        return brpActies;
    }

    private List<BrpGroep<? extends BrpGroepInhoud>> getGroepenMetMeestRecenteDatumAanvangGeldigheid(
        final List<BrpGroep<? extends BrpGroepInhoud>> mogelijkeActueleBrpGroepen)
    {
        final List<BrpGroep<? extends BrpGroepInhoud>> groepenMetMeestRecenteDag = new ArrayList<>();
        BrpGroep<? extends BrpGroepInhoud> meestRecenteDag = null;
        for (final BrpGroep<? extends BrpGroepInhoud> brpGroep : mogelijkeActueleBrpGroepen) {
            if (meestRecenteDag == null) {
                meestRecenteDag = brpGroep;
                groepenMetMeestRecenteDag.add(brpGroep);
            } else {
                final int compareResult =
                        brpGroep.getHistorie().getDatumAanvangGeldigheid().compareTo(meestRecenteDag.getHistorie().getDatumAanvangGeldigheid());
                if (compareResult == 0) {
                    // Gelijk
                    groepenMetMeestRecenteDag.add(brpGroep);
                } else if (compareResult > 0) {
                    meestRecenteDag = brpGroep;
                    groepenMetMeestRecenteDag.clear();
                    groepenMetMeestRecenteDag.add(brpGroep);
                }
            }
        }
        return groepenMetMeestRecenteDag;
    }

    private boolean isActieBijBrpGroep(final Long actieId, final BrpGroep<? extends BrpGroepInhoud> brpGroep) {
        boolean resultaat = true;
        BrpActie actie = null;
        if (brpGroep != null) {
            if (actieId.equals(brpGroep.getActieInhoud().getId())) {
                actie = brpGroep.getActieInhoud();
            } else if (brpGroep.getActieVerval() != null && actieId.equals(brpGroep.getActieVerval().getId())) {
                actie = brpGroep.getActieVerval();
            } else if (brpGroep.getActieGeldigheid() != null && actieId.equals(brpGroep.getActieGeldigheid().getId())) {
                actie = brpGroep.getActieGeldigheid();
            }

            // Als actie van type is dat in de uitzonderingslijst voor komt, dan hoort de actie niet bij een groep
            if (actie == null || UITZONDERING_INHOUD_CLASSES.contains(brpGroep.getInhoud().getClass()) && actie.isConversieActie()) {
                resultaat = false;
            }
        }
        return resultaat;
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    private boolean isActieMogelijkActueel(final Long actieId, final BrpGroep<? extends BrpGroepInhoud> brpGroep) {
        return !(actieId.equals(brpGroep.getActieInhoud().getId()) && brpGroep.getHistorie().isVervallen() && brpGroep.getActieVerval() == null);
    }

    /**
     * Bepaal de BrpGroepConverteerder voor een bepaalde inhoud. Wordt 1 keer aangeroepen per stapel.
     *
     * @param <B>
     *            brp groep inhoud type
     * @param inhoud
     *            inhoud
     * @return brp groep converteerder
     */
    protected abstract <B extends BrpGroepInhoud> BrpGroepConverteerder<B, L> bepaalConverteerder(B inhoud);

    /**
     * Sorteer acties. Acties uit Lo3 eerst op volgorde zoals gebruikt tijdens conversie, gevolgd door brp acties op
     * datum-tijd registratie.
     */
    private static final class BrpActieComparator implements Comparator<BrpActie>, Serializable {
        private static final long serialVersionUID = 1L;

        @Override
        public int compare(final BrpActie actie1, final BrpActie actie2) {
            final Integer conversieSortering1 = bepaalConversieSortering(actie1);
            final Integer conversieSortering2 = bepaalConversieSortering(actie2);

            int result;

            if (conversieSortering1 != null && conversieSortering2 != null) {
                result = conversieSortering1.compareTo(conversieSortering2);
            } else if (conversieSortering1 == null && conversieSortering2 != null) {
                result = 1;
            } else if (conversieSortering1 != null) {
                result = -1;
            } else {
                result = actie1.getDatumTijdRegistratie().compareTo(actie2.getDatumTijdRegistratie());

                if (result == 0) {
                    final int sort1 = actie1.getSortering();
                    final int sort2 = actie2.getSortering();
                    result = sort1 < sort2 ? -1 : sort1 > sort2 ? 1 : 0;
                }

                if (result == 0) {
                    // MIG-388: Records die wat zeggen over persoonsgegevens van een gerelateerde gaan voor
                    // records die wat zeggen over ouderschap (anders krijgen we een onjuist lege rij, omhoog
                    // bubbel etc).
                    result = actie1.getId().compareTo(actie2.getId());
                }
            }

            return result;
        }

        private Integer bepaalConversieSortering(final BrpActie actie) {
            final Lo3Herkomst herkomst1 = actie.getLo3Herkomst();
            return herkomst1 != null ? herkomst1.getConversieSortering() : null;
        }
    }

    /**
     * Vergelijkt de BrpActies op basis van conversie sortering. Als er geen herkomst is, dan wordt er standaard -1
     * (kleiner) terug gegeven.
     */
    private static final class BrpActieConversieVolgordeComparator implements Comparator<Pair<BrpActie, BrpGroep<?>>>, Serializable {
        private static final long serialVersionUID = 1L;

        @Override
        public int compare(final Pair<BrpActie, BrpGroep<?>> o1, final Pair<BrpActie, BrpGroep<?>> o2) {
            final int standaardResultaat = -1;
            final Lo3Herkomst o1Lo3Herkomst = o1.getLeft().getLo3Herkomst();
            final Lo3Herkomst o2Lo3Herkomst = o2.getLeft().getLo3Herkomst();

            if (o1Lo3Herkomst == null || o2Lo3Herkomst == null) {
                return standaardResultaat;
            }
            return standaardResultaat * o1Lo3Herkomst.getConversieSortering().compareTo(o2Lo3Herkomst.getConversieSortering());
        }
    }
}
