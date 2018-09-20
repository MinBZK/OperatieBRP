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
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaNeeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.bericht.kern.FamilierechtelijkeBetrekkingBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.hisvolledig.momentview.kern.FamilierechtelijkeBetrekkingView;
import nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonView;
import nl.bzk.brp.model.logisch.kern.Persoon;
import nl.bzk.brp.model.logisch.kern.PersoonSamengesteldeNaamGroep;
import nl.bzk.brp.util.RelatieUtils;
import org.apache.commons.lang.ObjectUtils;

/**
 * Als door een Actie in een Bericht de Familierechtelijke betrekking van een ingezeten Kind met de Nederlandse Nationaliteit wordt geregistreerd of
 * gewijzigd, dan moet na verwerking hiervan op datum aanvang geldigheid Actie gelden dat de geslachtsnaam van het Kind overeenkomt met de geslachtsnaam
 * van elk ander gezamenlijk ingezeten Kind van beide Ouders.
 *
 * @brp.bedrijfsregel BRBY0107
 */
@Named("BRBY0107")
public class BRBY0107 implements NaActieRegelMetMomentopname<FamilierechtelijkeBetrekkingView,
    FamilierechtelijkeBetrekkingBericht>
{

    @Override
    public List<BerichtEntiteit> voerRegelUit(
        final FamilierechtelijkeBetrekkingView familierechtelijkeBetrekking,
        final FamilierechtelijkeBetrekkingBericht familierechtelijkeBetrekkingBericht)
    {
        final List<BerichtEntiteit> objectenDieDeRegelOvertreden = new ArrayList<>();
        final Persoon kind = RelatieUtils.haalKindUitRelatie(familierechtelijkeBetrekking);
        final PersoonBericht kindBericht =
            (PersoonBericht) RelatieUtils.haalKindUitRelatie(familierechtelijkeBetrekkingBericht);
        if (checkPreconditiesVoorBedrijfsregel(kind)) {
            final List<Persoon> oudersVanKind = RelatieUtils.haalOudersUitRelatie(familierechtelijkeBetrekking);
            final List<Persoon> gezamenlijkeKinderen = bepaalGezamenlijkeKinderenVanOuders(oudersVanKind);

            for (final Persoon gezamenlijkKind : gezamenlijkeKinderen) {
                if (!hebbenDezelfdeGeslachtsnamen(gezamenlijkKind, kind)) {
                    objectenDieDeRegelOvertreden.add(kindBericht);
                    break;
                }
            }
        }
        return objectenDieDeRegelOvertreden;
    }

    /**
     * Controleer of de geslachtsnaam van persoon 1 overeenkomt met die van een persoon 2.
     *
     * @param persoon1 persoon 1
     * @param persoon2 persoon 2
     * @return true indien het geslachtsnaam overeenkomt, anders false.
     */
    private boolean hebbenDezelfdeGeslachtsnamen(final Persoon persoon1, final Persoon persoon2) {
        boolean resultaat = true;
        if (persoon1.getSamengesteldeNaam() != null && persoon2.getSamengesteldeNaam() != null) {
            final PersoonSamengesteldeNaamGroep naamKind = persoon1.getSamengesteldeNaam();
            final PersoonSamengesteldeNaamGroep naamOuder = persoon2.getSamengesteldeNaam();

            resultaat = ObjectUtils.equals(naamKind.getIndicatieNamenreeks(), naamOuder.getIndicatieNamenreeks());
            resultaat &= ObjectUtils.equals(naamKind.getVoorvoegsel(), naamOuder.getVoorvoegsel());
            resultaat &= ObjectUtils.equals(naamKind.getScheidingsteken(), naamOuder.getScheidingsteken());
            resultaat &= ObjectUtils.equals(naamKind.getGeslachtsnaamstam(), naamOuder.getGeslachtsnaamstam());
        }
        return resultaat;
    }

    /**
     * Bepaalt de kinderen die de ouders gezamenlijk hebben.
     *
     * @param oudersVanKind ouders van een kind
     * @return gezamenlijke kinderen van beide ouders
     */
    private List<Persoon> bepaalGezamenlijkeKinderenVanOuders(final List<Persoon> oudersVanKind) {
        final List<Persoon> gezamenlijkeKinderen = new ArrayList<>();
        if (oudersVanKind.size() == 2) {
            final Persoon ouder1 = oudersVanKind.get(0);
            final Persoon ouder2 = oudersVanKind.get(1);

            final List<Persoon> kinderenOuder1 = RelatieUtils.haalAlleKinderenUitPersoon(ouder1);
            final List<Persoon> kinderenOuder2 = RelatieUtils.haalAlleKinderenUitPersoon(ouder2);

            for (final Persoon kindOuder1 : kinderenOuder1) {
                final PersoonView kindOuder1View = (PersoonView) kindOuder1;
                for (final Persoon kindOuder2 : kinderenOuder2) {
                    final PersoonView kindOuder2View = (PersoonView) kindOuder2;
                    if (ObjectUtils.equals(kindOuder1View.getID(), kindOuder2View.getID())) {
                        gezamenlijkeKinderen.add(kindOuder1View);
                    }
                }
            }
        }
        return gezamenlijkeKinderen;
    }

    /**
     * Precondities voor regel executie. - Heeft de NL nationaliteit - Heeft geen namenreeks - Kind is ingeschrevene
     *
     * @param kind het kind waarop de regel van toepassing is
     * @return true indien aan de condities is voldaan anders false
     */
    private boolean checkPreconditiesVoorBedrijfsregel(final Persoon kind) {
        return kind.heeftNederlandseNationaliteit()
            && JaNeeAttribuut.NEE.equals(kind.getSamengesteldeNaam().getIndicatieNamenreeks())
            && SoortPersoon.INGESCHREVENE == kind.getSoort().getWaarde();
    }

    @Override
    public final Regel getRegel() {
        return Regel.BRBY0107;
    }
}
