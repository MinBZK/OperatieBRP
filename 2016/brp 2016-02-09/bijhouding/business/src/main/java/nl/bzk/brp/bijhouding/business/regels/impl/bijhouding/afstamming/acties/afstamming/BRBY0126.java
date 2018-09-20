/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.bijhouding.afstamming.acties.afstamming;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;

import nl.bzk.brp.business.regels.NaActieRegelMetMomentopname;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.bericht.kern.FamilierechtelijkeBetrekkingBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.hisvolledig.momentview.kern.FamilierechtelijkeBetrekkingView;
import nl.bzk.brp.model.logisch.kern.Persoon;
import nl.bzk.brp.model.logisch.kern.PersoonGeboorteGroep;
import nl.bzk.brp.model.logisch.kern.PersoonSamengesteldeNaamGroep;
import nl.bzk.brp.util.RelatieUtils;

import org.apache.commons.lang.builder.EqualsBuilder;


/**
 * Bij de registratie van een Ouderschap mag de betrokken Ouder op DatumAanvangGeldigheid daarvan niet reeds Ouder zijn
 * van een Kind met personalia die
 * overkomen met de personalia van het nieuwe Kind. Er is sprake van overeenkomende personalia indien de volgende
 * gegevens exact gelijk zijn:
 * <p/>
 * - Voornamen, Geslachtsnaam, Voorvoegsel en Scheidingsteken uit de groep Samengestelde naam;
 * <p/>
 * - DatumGeboorte uit de groep Geboorte.
 * <p/>
 * Opmerking: Door de vergelijking te baseren op SamengesteldeNaam is de regel zowel van toepassing op Ingeschreven als
 * NietIngeschreven Personen.
 * <p/>
 * Regel kan alleen worden geimplementeerd als na actie regel want de samengestelde naam van het kind moet eerst worden
 * afgeleid voordat je deze kan controleren met die van eerdere kinderen. De afleiding gebeurt na het verwerken van de
 * actie.
 *
 * @brp.bedrijfsregel BRBY0126
 */
@Named("BRBY0126")
public class BRBY0126 implements NaActieRegelMetMomentopname<FamilierechtelijkeBetrekkingView, FamilierechtelijkeBetrekkingBericht>
{

    @Override
    public final Regel getRegel() {
        return Regel.BRBY0126;
    }

    @Override
    public final List<BerichtEntiteit> voerRegelUit(final FamilierechtelijkeBetrekkingView aangemaakteRelatie,
            final FamilierechtelijkeBetrekkingBericht relatieBericht)
    {
        final List<BerichtEntiteit> objectenDieDeRegelOvertreden = new ArrayList<>();
        final PersoonBericht kindBericht = (PersoonBericht) RelatieUtils.haalKindUitRelatie(relatieBericht);
        final Persoon nieuwKind = RelatieUtils.haalKindUitRelatie(aangemaakteRelatie);

        if (kindBericht != null) {
            for (final Persoon ouder : RelatieUtils.haalOudersUitRelatie(aangemaakteRelatie)) {
                if (isKindAlEerderGeregistreerd(ouder, nieuwKind)) {
                    objectenDieDeRegelOvertreden.add(kindBericht);
                    break;
                }
            }
        }

        return objectenDieDeRegelOvertreden;
    }

    /**
     * Controleert of kind al eerder geregistreerd is met dezelfde personalia.
     *
     * @param ouder de ouder view
     * @param nieuwKind het kind bericht
     * @return the boolean
     */
    private boolean isKindAlEerderGeregistreerd(final Persoon ouder, final Persoon nieuwKind) {
        int isKindAlEerderGeregistreerd = 0;

        for (final Persoon bestaandKind : RelatieUtils.haalAlleKinderenUitPersoon(ouder)) {
            if (isDatumGeboorteIdentiek(bestaandKind.getGeboorte(), nieuwKind.getGeboorte())
                && isSamengesteldeNaamIdentiek(bestaandKind.getSamengesteldeNaam(), nieuwKind.getSamengesteldeNaam()))
            {
                isKindAlEerderGeregistreerd++;
            }
        }

        // Er is natuurlijk al één kind met dezelfde personalia, dat is het kind wat we aan het inschrijven zijn.
        // Dus als er meer dan 1 kind is die matcht met de personalia van het in te schrijven kind dan is er sprake van
        // dubbele inschrijving.
        return isKindAlEerderGeregistreerd > 1;
    }

    /**
     * Controleert of datum geboorte van tweede geboorte groepen identiek is.
     *
     * @param geboorteGroep1 eerste geboorte groep
     * @param geboorteGroep2 tweede geboorte groep
     * @return true indien gelijk, anders false
     */
    private boolean isDatumGeboorteIdentiek(final PersoonGeboorteGroep geboorteGroep1,
            final PersoonGeboorteGroep geboorteGroep2)
    {
        return geboorteGroep1.getDatumGeboorte().equals(geboorteGroep2.getDatumGeboorte());
    }

    /**
     * Controleert of twee samengestelde naam groepen identiek zijn.
     *
     * @param samengesteldeNaamGroep1 de eerste samengestelde naam
     * @param samengesteldeNaamGroep2 de tweede samengestelde naam
     * @return true indien gelijk, anders false
     */
    private boolean isSamengesteldeNaamIdentiek(final PersoonSamengesteldeNaamGroep samengesteldeNaamGroep1,
            final PersoonSamengesteldeNaamGroep samengesteldeNaamGroep2)
    {
        return new EqualsBuilder()
                .append(samengesteldeNaamGroep1.getVoornamen(), samengesteldeNaamGroep2.getVoornamen())
                .append(samengesteldeNaamGroep1.getVoorvoegsel(), samengesteldeNaamGroep2.getVoorvoegsel())
                .append(samengesteldeNaamGroep1.getScheidingsteken(), samengesteldeNaamGroep2.getScheidingsteken())
                .append(samengesteldeNaamGroep1.getGeslachtsnaamstam(), samengesteldeNaamGroep2.getGeslachtsnaamstam())
                .isEquals();
    }
}
