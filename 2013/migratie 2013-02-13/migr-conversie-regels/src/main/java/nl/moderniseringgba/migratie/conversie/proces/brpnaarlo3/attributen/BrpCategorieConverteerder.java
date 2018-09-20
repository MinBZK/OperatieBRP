/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.proces.brpnaarlo3.attributen;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import nl.moderniseringgba.migratie.conversie.model.brp.BrpActie;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpGroep;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpGroepInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpHistorie;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpStapel;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Categorie;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3CategorieInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Stapel;
import nl.moderniseringgba.migratie.logging.Logger;

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
public abstract class BrpCategorieConverteerder<L extends Lo3CategorieInhoud> {

    private static final Comparator<BrpGroep<? extends BrpGroepInhoud>> GROEPEN_COMPARATOR =
            new Comparator<BrpGroep<? extends BrpGroepInhoud>>() {
                @Override
                public int compare(
                        final BrpGroep<? extends BrpGroepInhoud> groep0,
                        final BrpGroep<? extends BrpGroepInhoud> groep1) {
                    final BrpHistorie his0 = groep0.getHistorie();
                    final BrpHistorie his1 = groep1.getHistorie();

                    int result = his0.getDatumAanvangGeldigheid().compareTo(his1.getDatumAanvangGeldigheid());

                    if (result == 0) {
                        result = his0.getDatumTijdRegistratie().compareTo(his1.getDatumTijdRegistratie());
                    }
                    return result;
                }
            };

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
    public final Lo3Stapel<L> converteer(final BrpStapel<? extends BrpGroepInhoud>... brpStapels) {
        getLogger().debug("converteer(#brpStapels={}", brpStapels.length);
        final List<Lo3Categorie<L>> lo3Categorieen = new ArrayList<Lo3Categorie<L>>();

        final List<BrpActie> acties = bepaalActies(brpStapels);
        for (final BrpActie actie : acties) {
            getLogger().debug("te verwerken actie.id: {}", actie.getId());
            getLogger().debug("categorieen voor verwerking ({})", lo3Categorieen.size());
            for (final BrpStapel<? extends BrpGroepInhoud> stapel : brpStapels) {
                verwerkStapel(actie, stapel, lo3Categorieen);
            }

            getLogger().debug("categorieen na verwerking ({}):", lo3Categorieen.size());
        }

        getLogger().debug("geconverteerde categorieen ({})", lo3Categorieen.size());
        return lo3Categorieen.isEmpty() ? null : new Lo3Stapel<L>(lo3Categorieen);
    }

    private <B extends BrpGroepInhoud> void verwerkStapel(
            final BrpActie actie,
            final BrpStapel<B> stapel,
            final List<Lo3Categorie<L>> categorieen) {
        getLogger().debug("verwerkStapel");
        final List<BrpGroep<B>> groepen = bepaalGroepen(actie, stapel);
        getLogger().debug("#groepen: {}", groepen.size());

        if (!groepen.isEmpty()) {
            getLogger().debug("bepaalConverteerder voor groep");
            final BrpGroepConverteerder<B, L> converteerder = bepaalConverteerder(groepen.get(0).getInhoud());
            getLogger().debug("converteerder: {}", converteerder);
            converteerder.converteer(actie, groepen, categorieen);
        }
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

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

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    private static <T extends BrpGroepInhoud> List<BrpGroep<T>> bepaalGroepen(
            final BrpActie actie,
            final BrpStapel<T> stapel) {
        final Long actieId = actie.getId();
        final List<BrpGroep<T>> groepen = new ArrayList<BrpGroep<T>>();

        if (stapel != null) {
            for (final BrpGroep<T> groep : stapel) {
                // CHECKSTYLE:OFF - Boolean complexity: is hier goed leesbaar.
                if (groep.getActieInhoud() != null && actieId.equals(groep.getActieInhoud().getId())
                        || groep.getActieVerval() != null && actieId.equals(groep.getActieVerval().getId())
                        || groep.getActieGeldigheid() != null && actieId.equals(groep.getActieGeldigheid().getId())) {
                    // CHECKSTYLE:ON
                    groepen.add(groep);
                }
            }
        }

        // Sorteer de groepen
        Collections.sort(groepen, GROEPEN_COMPARATOR);

        return groepen;
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    private static List<BrpActie> bepaalActies(final BrpStapel<?>... stapels) {
        final List<BrpActie> acties = new ArrayList<BrpActie>();

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

        Collections.sort(acties, new Comparator<BrpActie>() {
            @Override
            public int compare(final BrpActie actie1, final BrpActie actie2) {
                int result = actie1.getDatumTijdRegistratie().compareTo(actie2.getDatumTijdRegistratie());

                if (result == 0) {
                    final int sort1 = actie1.getSortering();
                    final int sort2 = actie2.getSortering();
                    result = sort1 < sort2 ? -1 : sort1 > sort2 ? 1 : 0;
                }

                // MIG-388: Records die wat zeggen over persoonsgegevens van een gerelateerde gaan voor
                // records die wat zeggen over ouderschap (anders krijgen we een onjuist lege rij, omhoog
                // bubbel etc).
                return result == 0 ? actie1.getId().compareTo(actie2.getId()) : result;
            }
        });

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

}
