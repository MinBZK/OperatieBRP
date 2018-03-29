/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.brp;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.algemeenbrp.util.xml.annotation.ElementList;
import nl.bzk.migratiebrp.conversie.model.Sortable;
import nl.bzk.migratiebrp.conversie.model.Stapel;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGroepInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIstGroepInhoud;

/**
 * Deze class representeert een BRP stapel. Dit is een lijst met BRP groepen die samen de actuele en historische
 * gegevens van deze groep representeren.
 * @param <I> brp groep inhoud
 */
public final class BrpStapel<I extends BrpGroepInhoud> extends Stapel<BrpGroep<I>> implements Sortable {

    /**
     * Maakt een BRP-groep stapel aan.
     * @param groepen de lijst met BRP groepen. Deze mag niet leeg zijn.
     * @throws IllegalArgumentException als groepen een lege lijst is
     * @throws NullPointerException als groepen null is
     */
    public BrpStapel(@ElementList(name = "groepen", inline = true, type = BrpGroep.class) final List<BrpGroep<I>> groepen) {
        super(groepen);
    }

    /**
     * Geef de waarde van groepen.
     * @return BRP groepen
     */
    @ElementList(name = "groepen", inline = true, type = BrpGroep.class)
    public List<BrpGroep<I>> getGroepen() {
        return super.getElementen();
    }

    /**
     * Valideer de inhoud van deze BRP stapel.
     */
    public void valideer() {
        for (final BrpGroep<I> brpGroep : getGroepen()) {
            brpGroep.getInhoud().valideer();
        }
    }

    /**
     * Controleer of de stapel een actuele groep bevat. Dit wil zeggen dat de groep niet is vervallen en geen einde
     * geldigheid heeft.
     * @return true als deze stapel een actuele groep bevat
     */
    public boolean bevatActueel() {
        return getActueel() != null;
    }

    /**
     * Haal de actuele groep op als de stapel een actuele groep bevat. Dit wil zeggen dat de groep niet is vervallen en
     * geen einde geldigheid heeft. Als de stapel geen actuele groep bevat wordt <code>null</code> teruggegeven.
     * @return de actuele groep als die in de stapel aanwezig is. Anders <code>null</code>
     */
    public BrpGroep<I> getActueel() {
        BrpGroep<I> gevondenGroep = null;
        for (final BrpGroep<I> brpGroep : getGroepen()) {
            final BrpHistorie hist = brpGroep.getHistorie();
            if (!hist.isVervallen() && hist.getDatumEindeGeldigheid() == null) {
                if (gevondenGroep != null) {
                    throw new IllegalStateException("Er zijn meerdere actuele groepen gevonden!");
                }
                gevondenGroep = brpGroep;
            }
        }
        return gevondenGroep;
    }

    /**
     * Haal de actuele groep op als de stapel een actuele groep bevat. Dit wil zeggen dat de groep niet is een actie
     * verval en geen datum einde geldigheid heeft. Als er meer groepen gevonden wordt, dan wordt de groep terug gegeven
     * die geen tijdstip verval heeft. Als en geen groepen worden gevonden, dan wordt <code>null</code> teruggegeven.
     * @return de actuele groep als die in de stapel aanwezig is. Anders <code>null</code>
     */
    BrpGroep<I> getActueelOpActieVerval() {
        BrpGroep<I> gevondenGroep = null;

        final List<BrpGroep<I>> gevondenGroepen = new ArrayList<>();

        for (final BrpGroep<I> brpGroep : getGroepen()) {
            final BrpHistorie hist = brpGroep.getHistorie();
            final BrpActie actieVerval = brpGroep.getActieVerval();
            if (hist.getDatumEindeGeldigheid() == null && actieVerval == null) {
                gevondenGroepen.add(brpGroep);
            }
        }

        if (gevondenGroepen.size() == 1) {
            gevondenGroep = gevondenGroepen.get(0);
        } else {
            for (final BrpGroep<I> groep : gevondenGroepen) {
                if (!groep.getHistorie().isVervallen()) {
                    gevondenGroep = groep;
                    break;
                }
            }
        }

        return gevondenGroep;
    }

    /**
     * Sorteert de {@link BrpStapel} op de volgende manier :
     * <UL><LI>datum/tijd Registratie - van nieuw naar oud</LI>
     * <LI>datum aanvang geldigheid - van oud naar nieuw</LI>
     * <LI>datum/tijd verval - van nieuw naar oud</LI></UL>
     * <LI>datum einde geldigheid - van nieuw naar oud</LI></UL>
     */
    @Override
    public void sorteer() {
        if (get(0).getInhoud() instanceof BrpIstGroepInhoud) {
            sorteer(new BrpIstComparator());
        } else {
            sorteer((o1, o2) -> {
                int result = o2.getHistorie().getDatumTijdRegistratie().compareTo(o1.getHistorie().getDatumTijdRegistratie());

                if (result == 0) {
                    result = -SortUtil.compareNulls(o1.getHistorie().getDatumAanvangGeldigheid(), o2.getHistorie().getDatumAanvangGeldigheid());
                }

                if (result == 0) {
                    result = SortUtil.compareNulls(o1.getHistorie().getDatumTijdVerval(), o2.getHistorie().getDatumTijdVerval());
                }

                if (result == 0) {
                    result = SortUtil.compareNulls(o1.getHistorie().getDatumEindeGeldigheid(), o2.getHistorie().getDatumEindeGeldigheid());
                }

                return result;
            });

            // Binnen de groepen moet ook de actiebronnen worden gesorteerd.
            for (final BrpGroep<I> groep : getGroepen()) {
                groep.sorteerActieBronnen();
            }
        }
    }
}
