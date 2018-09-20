/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.bericht.request;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Named;
import nl.bzk.brp.business.regels.VoorBerichtRegel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.basis.BerichtIdentificeerbaar;
import nl.bzk.brp.model.basis.BerichtRootObject;
import nl.bzk.brp.model.bericht.kern.ActieBericht;
import nl.bzk.brp.model.bericht.kern.BetrokkenheidBericht;
import nl.bzk.brp.model.bericht.kern.FamilierechtelijkeBetrekkingBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bericht.kern.RelatieBericht;
import nl.bzk.brp.model.bijhouding.BijhoudingsBericht;

/**
 * Een nevenactie mag alleen betrekking hebben op bij de hoofdactie betrokken ingezetenen.
 *
 * @brp.bedrijfsregel BRBY0152
 */
@Named("BRBY0152")
public class BRBY0152 implements VoorBerichtRegel<BijhoudingsBericht> {

    @Override
    public final Regel getRegel() {
        return Regel.BRBY0152;
    }

    @Override
    public final List<BerichtIdentificeerbaar> voerRegelUit(final BijhoudingsBericht bericht) {
        final List<BerichtIdentificeerbaar> objectenDieDeRegelOvertreden = new ArrayList<>();

        final ActieBericht hoofdActie = bericht.getAdministratieveHandeling().getHoofdActie();
        final BerichtRootObject rootObject = hoofdActie.getRootObject();

        for (final ActieBericht nevenActie : bericht.getAdministratieveHandeling().getNevenActies()) {
            if (nevenActie.getRootObject() instanceof PersoonBericht) {
                final PersoonBericht persoonUitNevenActie = (PersoonBericht) nevenActie.getRootObject();

                if (isRegelOvertreden(rootObject, persoonUitNevenActie)) {
                    objectenDieDeRegelOvertreden.add(persoonUitNevenActie);
                }
            }
        }

        return objectenDieDeRegelOvertreden;
    }

    /**
     * Controleert of de regel overtreden is, voor rootobjecten van het type persoonbericht en relatiebericht.
     *
     * @param rootObject het root object van de hoofdactie
     * @param persoonUitNevenActie persoon uit nevenactie
     * @return true als regel overtreden is
     */
    private boolean isRegelOvertreden(final BerichtRootObject rootObject, final PersoonBericht persoonUitNevenActie) {
        boolean isRegelOvertreden = false;
        if (rootObject instanceof PersoonBericht) {
            final PersoonBericht persoonUitHoofdActie = (PersoonBericht) rootObject;
            if (isRegelOvertredenVoorPersoon(persoonUitHoofdActie, persoonUitNevenActie)) {
                isRegelOvertreden = true;
            }
        } else if (rootObject instanceof RelatieBericht) {
            final RelatieBericht relatieUitHoofdActie = (RelatieBericht) rootObject;
            if (isRegelOvertredenVoorRelatie(relatieUitHoofdActie, persoonUitNevenActie)) {
                isRegelOvertreden = true;
            }
        }
        return isRegelOvertreden;
    }

    /**
     * Controleert of de regel overtreden is voor persoon.
     *
     * @param persoonUitHoofdActie persoon uit hoofd actie
     * @param persoonUitNevenActie persoon uit neven actie
     * @return true als regel overtreden is
     */
    private boolean isRegelOvertredenVoorPersoon(final PersoonBericht persoonUitHoofdActie,
                                                 final PersoonBericht persoonUitNevenActie)
    {
        return !verwijstNevenActieNaarPersoonUitHoofdActie(persoonUitHoofdActie, persoonUitNevenActie);
    }

    /**
     * Controleer of regel overtreden is.
     *
     * @param relatieUitHoofdActie the relatie uit hoofd actie
     * @param persoonUitNevenActie the persoon uit neven actie
     * @return true als regel overtreden is
     */
    private boolean isRegelOvertredenVoorRelatie(final RelatieBericht relatieUitHoofdActie,
                                                  final PersoonBericht persoonUitNevenActie)
    {
        final boolean isRegelOvertreden;

        if (relatieUitHoofdActie instanceof FamilierechtelijkeBetrekkingBericht) {
            isRegelOvertreden = controleerVoorFamilierechtelijkBetrekking(relatieUitHoofdActie, persoonUitNevenActie);
        } else {
            isRegelOvertreden = controleerVoorOverigeRelaties(relatieUitHoofdActie, persoonUitNevenActie);
        }

        return isRegelOvertreden;
    }

    /**
     * Als in de Hoofdactie een Familierechtelijke betrekking wordt bijgehouden, dan mag een Nevenactie alleen
     * betrekking hebben op de Persoon die in de Hoofdactie als Kind is aangeduid.
     *
     * @param relatieUitHoofdActie de relatie uit de hoofd actie
     * @param persoonUitNevenActie de persoon uit de neven actie
     * @return true als nevenactie niet naar hoofdpersoon verwijst
     */
    private boolean controleerVoorFamilierechtelijkBetrekking(final RelatieBericht relatieUitHoofdActie,
                                                           final PersoonBericht persoonUitNevenActie)
    {
        boolean isRegelOvertreden = false;

        final PersoonBericht kindPersoon =
                ((FamilierechtelijkeBetrekkingBericht) relatieUitHoofdActie).getKindBetrokkenheid()
                        .getPersoon();

        if (!verwijstNevenActieNaarPersoonUitHoofdActie(kindPersoon, persoonUitNevenActie)) {
            isRegelOvertreden = true;
        }

        return isRegelOvertreden;
    }

    /**
     * Controleer of persoon uit neven actie voorkomt in de hoofd actie.
     * @param relatieUitHoofdActie relatie uit de hoofd actie
     * @param persoonUitNevenActie persoon uit de neven actie
     * @return true als nevenactie niet naar hoofdpersoon verwijst
     */
    private boolean controleerVoorOverigeRelaties(final RelatieBericht relatieUitHoofdActie,
                                               final PersoonBericht persoonUitNevenActie)
    {
        boolean persoonGevonden = false;

        //Loop door de betrokkenheden uit de hoofd actie
        for (final BetrokkenheidBericht betrokkenheidBericht : relatieUitHoofdActie.getBetrokkenheden()) {
            if (verwijstNevenActieNaarPersoonUitHoofdActie(betrokkenheidBericht.getPersoon(),
                    persoonUitNevenActie))
            {
                persoonGevonden = true;
                break;
            }
        }

        return !persoonGevonden;
    }

    /**
     * Controleert of de neven actie betrekking heeft op het kind dat in de hoofdactie zit.
     *
     * @param persoonUitHoofdActie het persoon uit de hoofd actie
     * @param persoonUitNevenActie het persoon uit de neven actie
     * @return true als persoon uit nevenActie gelijk is aan persoon uit hoofd actie
     */
    private boolean verwijstNevenActieNaarPersoonUitHoofdActie(final PersoonBericht persoonUitHoofdActie,
                                                               final PersoonBericht persoonUitNevenActie)
    {
        final boolean resultaat;

        if (persoonUitNevenActie.getReferentieID() != null) {
            resultaat = isReferentieIdGelijkAanCommunicatieId(persoonUitNevenActie, persoonUitHoofdActie);
        } else {
            resultaat = isTechnischeIdGelijk(persoonUitNevenActie, persoonUitHoofdActie);
        }

        return resultaat;
    }

    /**
     * Controleert of referentieId uit persoonUitNevenActie verwijst naar communicatieId in de persoonUitHoofdActie.
     *
     * @param persoonUitNevenActie persoon uit neven actie
     * @param persoonUitHoofdActie persoon uit hoofd actie
     * @return true als referentieId uit persoonUitNevenActie verwijst naar communicatieId in de persoonUitHoofdActie.
     */
    private boolean isReferentieIdGelijkAanCommunicatieId(final PersoonBericht persoonUitNevenActie,
                                                          final PersoonBericht persoonUitHoofdActie)
    {
        return persoonUitNevenActie.getReferentieID().equals(persoonUitHoofdActie.getCommunicatieID());
    }

    /**
     * Controleert of de technischeId in de persoonUitNevenActie gelijk is aan persoonUitHoofdActie.
     *
     * @param persoonUitNevenActie persoon uit neven actie
     * @param persoonUitHoofdActie persoon uit hoofd actie.
     * @return true als technischeId in de persoonUitNevenActie gelijk is aan persoonUitHoofdActie.
     */
    private boolean isTechnischeIdGelijk(final PersoonBericht persoonUitNevenActie,
                                         final PersoonBericht persoonUitHoofdActie)
    {
        return persoonUitNevenActie.getObjectSleutel().equals(persoonUitHoofdActie.getObjectSleutel());
    }
}
