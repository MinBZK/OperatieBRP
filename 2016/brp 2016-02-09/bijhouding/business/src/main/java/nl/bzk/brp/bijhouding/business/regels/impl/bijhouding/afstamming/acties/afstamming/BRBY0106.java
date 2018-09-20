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
import nl.bzk.brp.model.logisch.kern.Betrokkenheid;
import nl.bzk.brp.model.logisch.kern.Kind;
import nl.bzk.brp.model.logisch.kern.Ouder;
import nl.bzk.brp.model.logisch.kern.Persoon;
import nl.bzk.brp.model.logisch.kern.PersoonSamengesteldeNaamGroep;
import nl.bzk.brp.util.RelatieUtils;

import org.apache.commons.lang.ObjectUtils;


/**
 * Als door een Actie in een Bericht de Familierechtelijke betrekking van een ingezeten Kind met de Nederlandse
 * Nationaliteit wordt geregistreerd of
 * gewijzigd, dan moet na verwerking hiervan op datum aanvang geldigheid Actie gelden dat de geslachtsnaam van het Kind
 * overeenkomt met de geslachtsnaam
 * van minstens één Ouder.
 * <p/>
 * Opmerkingen: 1.De regel is niet van toepassing voor een NietIngeschrevene als Kind omdat daarvan de Nationaliteit
 * onbekend is in de BRP. 2.De regel is niet van toepassing als het Kind een namenreeks heeft omdat het attribuut
 * geslachtsnaam dan niet geïnterpreteerd mag worden als een geslachtsnaam. 3.De regel is niet van toepassing als het
 * Kind geen enkele Ouder heeft. 4.Geslachtsnaam van een Ouder en Kind komen overeen indien Namenreeks?, Voorvoegsel,
 * Scheidingsteken en Geslachtsnaam uit de groep Samengestelde naam zoals vastgelegd in de BRP of verkregen met het
 * Bericht bij beide exact gelijk zijn. Een Ouder met een namenreeks voldoet door deze wijze van vergelijking niet.
 * 5.Een registratie/wijziging in de Familierechtelijke betrekking betreft aanvang/einde Ouderschap en/of wijziging van
 * de geslachtsnaam van een niet-ingeschreven Ouder. Deze regel is dus niet van toepassing bij wijziging van de
 * geslachtsnaam van een ingezeten Ouder of Kind (zie ook BRBY0183: Persoon mag geen kinderen hebben als naam of
 * nationaliteit wijzigt).
 *
 * @brp.bedrijfsregel BRBY0106
 */
@Named("BRBY0106")
public class BRBY0106 implements
        NaActieRegelMetMomentopname<FamilierechtelijkeBetrekkingView, FamilierechtelijkeBetrekkingBericht>
{

    @Override
    public final Regel getRegel() {
        return Regel.BRBY0106;
    }

    @Override
    public List<BerichtEntiteit> voerRegelUit(final FamilierechtelijkeBetrekkingView familierechtelijkeBetrekking,
            final FamilierechtelijkeBetrekkingBericht familierechtelijkeBetrekkingBerichtEntiteit)
    {
        Persoon kind = null;
        Persoon ouder1 = null;
        Persoon ouder2 = null;

        for (final Betrokkenheid betrokkenheid : familierechtelijkeBetrekking.getBetrokkenheden()) {
            if (betrokkenheid instanceof Kind) {
                kind = betrokkenheid.getPersoon();
            } else if (betrokkenheid instanceof Ouder) {
                if (ouder1 == null) {
                    ouder1 = betrokkenheid.getPersoon();
                } else {
                    ouder2 = betrokkenheid.getPersoon();
                }
            } else {
                throw new IllegalArgumentException("Dit type betrokkenheid wordt niet verwacht in deze bedrijfsregel: "
                        + betrokkenheid.getClass());
            }
        }

        final List<BerichtEntiteit> objectenDieDeRegelOvertreden = new ArrayList<>();
        // 3. De regel is niet van toepassing als het Kind geen enkele Ouder heeft.
        if (erIsEenKindMetMinstensEenOuder(kind, ouder1)
            && preConditiesVoorBedrijfsregelZijnValide(kind, ouder1, ouder2)
            && !geslachtsnaamMatchTussenKindEnOuder(kind, ouder1, ouder2))
        {
            // @TODO Hoe komen we aan de communicatie id van het stukje in het bericht wat foutief is??
            // TODO: Overal getRegel gebruiken ipv regel code herhalen!
            objectenDieDeRegelOvertreden.add((PersoonBericht) RelatieUtils.haalKindUitRelatie(familierechtelijkeBetrekkingBerichtEntiteit));
        }
        return objectenDieDeRegelOvertreden;
    }

    private boolean erIsEenKindMetMinstensEenOuder(final Persoon kind, final Persoon ouder1) {
        return kind != null && ouder1 != null;
    }

    private boolean geslachtsnaamMatchTussenKindEnOuder(final Persoon kind, final Persoon ouder1, final Persoon ouder2)
    {
        boolean geslachtsNaamMatchMetEenOuder = controleerGeslachtsnaamTegenOuder(kind, ouder1);
        if (!geslachtsNaamMatchMetEenOuder && ouder2 != null) {
            geslachtsNaamMatchMetEenOuder = controleerGeslachtsnaamTegenOuder(kind, ouder2);
        }
        return geslachtsNaamMatchMetEenOuder;
    }

    /**
     * Controleer of de geslachtsnaam van een kind overeenkomt met die van een ouder.
     *
     * @param kind het kind
     * @param ouder de ouder
     * @return true indien het geslachtsnaam overeenkomt, anders false.
     */
    private boolean controleerGeslachtsnaamTegenOuder(final Persoon kind, final Persoon ouder) {
        boolean resultaat = true;
        if (kind.getSamengesteldeNaam() != null && ouder.getSamengesteldeNaam() != null) {
            final PersoonSamengesteldeNaamGroep naamKind = kind.getSamengesteldeNaam();
            final PersoonSamengesteldeNaamGroep naamOuder = ouder.getSamengesteldeNaam();

            resultaat = ObjectUtils.equals(naamKind.getIndicatieNamenreeks(), naamOuder.getIndicatieNamenreeks());
            resultaat &= ObjectUtils.equals(naamKind.getVoorvoegsel(), naamOuder.getVoorvoegsel());
            resultaat &= ObjectUtils.equals(naamKind.getScheidingsteken(), naamOuder.getScheidingsteken());
            resultaat &= ObjectUtils.equals(naamKind.getGeslachtsnaamstam(), naamOuder.getGeslachtsnaamstam());
        }
        return resultaat;
    }

    /**
     * Er gelden een aantal precondities voordat deze bedrijfsregel moet worden uitgevoerd. - Kind heeft NL
     * nationaliteit. - Kind is ingeschrevene. - Kind
     * heeft géén indicatie namenreeks. - Kind heeft minstens één ouder.
     *
     * @param kind het kind
     * @param ouder1 ouder 1
     * @param ouder2 ouder 2
     * @return true indien aan alle precondities is voldaan, anders false.
     */
    private boolean preConditiesVoorBedrijfsregelZijnValide(final Persoon kind, final Persoon ouder1,
            final Persoon ouder2)
    {
        // Het kind heeft de Nederlandse nationaliteit
        final boolean heeftNlNationaliteit = kind.heeftNederlandseNationaliteit();

        // Kind is ingeschrevene
        final boolean isIngeschrevene = SoortPersoon.INGESCHREVENE == kind.getSoort().getWaarde();

        // Kind is niet ingeschreven met NamenReeks = Ja
        final boolean indicatieNamenreeks =
                kind.getSamengesteldeNaam() != null
                && JaNeeAttribuut.JA.equals(kind.getSamengesteldeNaam().getIndicatieNamenreeks());

        // Kind moet met minimaal 1 ouder geregistreerd zijn.
        final boolean heeftGeenOuder = ouder1 == null && ouder2 == null;

        return heeftNlNationaliteit && isIngeschrevene && !indicatieNamenreeks && !heeftGeenOuder;
    }
}
