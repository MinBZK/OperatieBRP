/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.bijhouding.afstamming.acties.afstamming;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Named;

import nl.bzk.brp.business.regels.VoorActieRegelMetMomentopname;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.bericht.kern.FamilierechtelijkeBetrekkingBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.hisvolledig.momentview.kern.FamilierechtelijkeBetrekkingView;
import nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonView;
import nl.bzk.brp.model.logisch.kern.Actie;
import nl.bzk.brp.model.logisch.kern.Persoon;
import nl.bzk.brp.model.logisch.kern.PersoonGeboorteGroep;
import nl.bzk.brp.util.RelatieUtils;

import org.apache.commons.collections.CollectionUtils;


/**
 * Implementatie van bedrijfsregel BRBY0187. De datum geboorte uit de groep Geboorte van nieuwKind moet minstens 306
 * dagen later zijn dan de datum geboorte
 * uit de groep Geboorte van andere kinderen die betrokken zijn in een relatie van de soort familierechtelijke
 * betrekking met dezelfde persoon als ouder
 * als de nieuwgeborene als de ouder uit wie het nieuwKind geboren is.
 * <p/>
 * Uitzondering hierop is als de datum geboorte uit de groep Geboorte van de nieuwgeborene precies gelijk is aan die van
 * andere kinderen (meerlingsituatie).
 * <p/>
 * Merk op: de 306 dgn (== 9 maanden) is normaal gesproken geldig alleen voor NL-moeders, maar is van toepassing hier
 * voor alle moeders.
 *
 * @brp.bedrijfsregel BRBY0187
 */
@Named("BRBY0187")
public class BRBY0187 implements
        VoorActieRegelMetMomentopname<FamilierechtelijkeBetrekkingView, FamilierechtelijkeBetrekkingBericht>
{

    private static final int NEGEN_MAANDEN_IN_DAGEN = 306;

    @Override
    public final Regel getRegel() {
        return Regel.BRBY0187;
    }

    @Override
    public List<BerichtEntiteit> voerRegelUit(final FamilierechtelijkeBetrekkingView huidigeSituatie,
            final FamilierechtelijkeBetrekkingBericht nieuweSituatie, final Actie actie,
            final Map<String, PersoonView> bestaandeBetrokkenen)
    {
        final List<BerichtEntiteit> regelovertredendeObjecten = new ArrayList<>();

        final PersoonBericht moeder = (PersoonBericht) RelatieUtils.haalMoederUitRelatie(nieuweSituatie);

        if (moeder != null) {
            final Persoon kind = RelatieUtils.haalKindUitRelatie(nieuweSituatie);
            if (kind.getGeboorte() != null) {
                final PersoonView moederView = bestaandeBetrokkenen.get(moeder.getIdentificerendeSleutel());
                if (moederView != null) {
                    regelovertredendeObjecten.addAll(voerRegelUit(kind, moederView));
                }
            }
        }

        return regelovertredendeObjecten;
    }

    private List<BerichtEntiteit> voerRegelUit(final Persoon kind, final PersoonView moederView) {
        final List<BerichtEntiteit> regelovertredendeObjecten = new ArrayList<>();
        if (heeftMoederAnderKindJongerDanNegenMaanden(kind, moederView)) {
            regelovertredendeObjecten.add((BerichtEntiteit) kind);
        }
        return regelovertredendeObjecten;
    }


    /**
     * Methode voert de controle uit. Heeft deze moeder meerdere kinderen waarvan de leeftijd jonger is dan '9 maanden'.
     *
     * @param nieuwKind Persoon
     * @param moederView Persoon
     * @return true als moeder andere kind heeft jonger dan 9 maanden
     */
    private boolean heeftMoederAnderKindJongerDanNegenMaanden(final Persoon nieuwKind, final PersoonView moederView) {
        boolean resultaat = false;

        final DatumAttribuut minimaleDatumGeboorte = new DatumAttribuut(nieuwKind.getGeboorte().getDatumGeboorte());
        minimaleDatumGeboorte.voegDagToe(-NEGEN_MAANDEN_IN_DAGEN);

        final DatumAttribuut maximaleDatumGeboorte = new DatumAttribuut(nieuwKind.getGeboorte().getDatumGeboorte());
        maximaleDatumGeboorte.voegDagToe(NEGEN_MAANDEN_IN_DAGEN);

        // we gaan ervan uit dat dit al eerder is gecheckt op niet null en geldig kalender.

        final List<Persoon> kinderen = RelatieUtils.haalAlleKinderenUitPersoon(moederView);

        if (CollectionUtils.isNotEmpty(kinderen)) {
            for (final Persoon kind : kinderen) {
                final PersoonGeboorteGroep kindGeboorte = kind.getGeboorte();
                if (null != kindGeboorte && null != kindGeboorte.getDatumGeboorte()) {
                    // is het oudere nieuwKind 306 dgn ouder dan het in te schrijven nieuwKind?
                    // op 306 dagen terug is nog goed, later is het fout.

                    // Uitzondering is dat als ze beide geboorte datum hebben, dan is het weer goed (meerlingen).
                    final DatumEvtDeelsOnbekendAttribuut geboorteDatumOuderKind = kindGeboorte.getDatumGeboorte();

                    if (!geboorteDatumOuderKind.op(nieuwKind.getGeboorte().getDatumGeboorte())
                        && geboorteDatumOuderKind.na(minimaleDatumGeboorte)
                        && geboorteDatumOuderKind.voor(maximaleDatumGeboorte))
                    {
                        resultaat = true;
                        // don't care van andere kinderen, een is al fout.
                        break;
                    }
                }
            }
        }

        return resultaat;
    }
}
