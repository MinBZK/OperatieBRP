/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.filter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import nl.bzk.brp.model.FormeleHistorieSet;
import nl.bzk.brp.model.MaterieleHistorieSet;
import nl.bzk.brp.model.basis.FormeelHistorisch;
import nl.bzk.brp.model.basis.FormeelVerantwoordbaar;
import nl.bzk.brp.model.basis.MaterieelHistorisch;
import nl.bzk.brp.model.basis.MaterieelVerantwoordbaar;
import nl.bzk.brp.model.basis.VerantwoordingTbvLeveringMutaties;
import nl.bzk.brp.model.hisvolledig.kern.HuwelijkGeregistreerdPartnerschapHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.KindHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.OnderzoekHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.OuderHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PartnerHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PersoonAdresHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PersoonNationaliteitHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PersoonOnderzoekHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PersoonReisdocumentHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PersoonVerificatieHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.RelatieHisVolledig;
import nl.bzk.brp.model.logisch.ist.Stapel;
import nl.bzk.brp.model.logisch.ist.StapelVoorkomen;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;

/**
 * Rubrieken visitor.
 */
public final class RubriekenVisitor {

    private final Long administratieveHandelingId;
    private final List<Long> acties = new ArrayList<>();

    private Set<String> resultaat;

    /**
     * Constructor.
     *
     * @param administratieveHandeling
     *            administratieve handeling
     */
    public RubriekenVisitor(final AdministratieveHandelingModel administratieveHandeling) {
        administratieveHandelingId = administratieveHandeling.getID();
        for (final ActieModel actieModel : administratieveHandeling.getActies()) {
            acties.add(actieModel.getID());
        }
        resultaat = new HashSet<>();
    }

    /**
     * Geef de rubrieken.
     *
     * @return lijst met rubrieken (kan leeg zijn), null als de rubrieken niet bepaald konden worden
     */
    public List<String> getRubrieken() {
        if (resultaat == null) {
            return null;
        } else {
            return new ArrayList<>(resultaat);
        }
    }

    /**
     * Bepaal de mutaties voor een persoon.
     *
     * @param persoon
     *            persoon
     */
    public void visit(final PersoonHisVolledig persoon) {
        visitHistorie(RubriekenMap.PERSOON, persoon.getPersoonAfgeleidAdministratiefHistorie());
        visitHistorie(RubriekenMap.PERSOON, persoon.getPersoonBijhoudingHistorie());
        visitHistorie(RubriekenMap.PERSOON, persoon.getPersoonDeelnameEUVerkiezingenHistorie());
        visitHistorie(RubriekenMap.PERSOON, persoon.getPersoonGeboorteHistorie());
        visitHistorie(RubriekenMap.PERSOON, persoon.getPersoonGeslachtsaanduidingHistorie());
        visitHistorie(RubriekenMap.PERSOON, persoon.getPersoonIdentificatienummersHistorie());
        visitHistorie(RubriekenMap.PERSOON, persoon.getPersoonInschrijvingHistorie());
        visitHistorie(RubriekenMap.PERSOON, persoon.getPersoonMigratieHistorie());
        visitHistorie(RubriekenMap.PERSOON, persoon.getPersoonNaamgebruikHistorie());
        visitHistorie(RubriekenMap.PERSOON, persoon.getPersoonNummerverwijzingHistorie());
        visitHistorie(RubriekenMap.PERSOON, persoon.getPersoonOverlijdenHistorie());
        visitHistorie(RubriekenMap.PERSOON, persoon.getPersoonPersoonskaartHistorie());
        visitHistorie(RubriekenMap.PERSOON, persoon.getPersoonSamengesteldeNaamHistorie());
        visitHistorie(RubriekenMap.PERSOON, persoon.getPersoonUitsluitingKiesrechtHistorie());
        visitHistorie(RubriekenMap.PERSOON, persoon.getPersoonVerblijfsrechtHistorie());

        if (persoon.getIndicatieBehandeldAlsNederlander() != null) {
            visitHistorie(RubriekenMap.PERSOON, persoon.getIndicatieBehandeldAlsNederlander().getPersoonIndicatieHistorie());
        }
        if (persoon.getIndicatieBijzondereVerblijfsrechtelijkePositie() != null) {
            visitHistorie(RubriekenMap.PERSOON, persoon.getIndicatieBijzondereVerblijfsrechtelijkePositie().getPersoonIndicatieHistorie());
        }
        if (persoon.getIndicatieDerdeHeeftGezag() != null) {
            visitHistorie(RubriekenMap.PERSOON, persoon.getIndicatieDerdeHeeftGezag().getPersoonIndicatieHistorie());
        }
        if (persoon.getIndicatieOnderCuratele() != null) {
            visitHistorie(RubriekenMap.PERSOON, persoon.getIndicatieOnderCuratele().getPersoonIndicatieHistorie());
        }
        if (persoon.getIndicatieSignaleringMetBetrekkingTotVerstrekkenReisdocument() != null) {
            visitHistorie(RubriekenMap.PERSOON, persoon.getIndicatieSignaleringMetBetrekkingTotVerstrekkenReisdocument().getPersoonIndicatieHistorie());
        }
        if (persoon.getIndicatieStaatloos() != null) {
            visitHistorie(RubriekenMap.PERSOON, persoon.getIndicatieStaatloos().getPersoonIndicatieHistorie());
        }
        if (persoon.getIndicatieVastgesteldNietNederlander() != null) {
            visitHistorie(RubriekenMap.PERSOON, persoon.getIndicatieVastgesteldNietNederlander().getPersoonIndicatieHistorie());
        }
        if (persoon.getIndicatieVolledigeVerstrekkingsbeperking() != null) {
            visitHistorie(RubriekenMap.PERSOON, persoon.getIndicatieVolledigeVerstrekkingsbeperking().getPersoonIndicatieHistorie());
        }

        for (final PersoonVerificatieHisVolledig verificatie : persoon.getVerificaties()) {
            visitHistorie(RubriekenMap.PERSOON, verificatie.getPersoonVerificatieHistorie());
        }

        final PersoonAdresHisVolledig adres = persoon.getAdressen().iterator().next();
        visitHistorie(RubriekenMap.PERSOON, adres.getPersoonAdresHistorie());

        for (final PersoonNationaliteitHisVolledig nationaliteit : persoon.getNationaliteiten()) {
            visitHistorie(RubriekenMap.PERSOON, nationaliteit.getPersoonNationaliteitHistorie());
        }
        for (final PersoonReisdocumentHisVolledig reisdocument : persoon.getReisdocumenten()) {
            visitHistorie(RubriekenMap.PERSOON, reisdocument.getPersoonReisdocumentHistorie());
        }

        // Verwerk ouders
        final KindHisVolledig mijnKindBetrokkenheid = persoon.getKindBetrokkenheid();
        if (mijnKindBetrokkenheid != null) {
            final RelatieHisVolledig mijnKindRelatie = mijnKindBetrokkenheid.getRelatie();
            for (final OuderHisVolledig gerelateerdeOuderBetrokkenheid : mijnKindRelatie.getOuderBetrokkenheden()) {
                verwerkOuder(mijnKindRelatie, gerelateerdeOuderBetrokkenheid);
            }
        }

        // Verwerk kinderen
        for (final OuderHisVolledig mijnOuderBetrokkenheid : persoon.getOuderBetrokkenheden()) {
            final RelatieHisVolledig mijnOuderRelatie = mijnOuderBetrokkenheid.getRelatie();
            final KindHisVolledig gerelateerdeKindBetrokkenheid = mijnOuderRelatie.getKindBetrokkenheid();

            verwerkKind(mijnOuderBetrokkenheid, mijnOuderRelatie, gerelateerdeKindBetrokkenheid);
        }

        // Verwerken huwelijken/gerelateerd partnerschappen
        for (final HuwelijkGeregistreerdPartnerschapHisVolledig mijnPartnerRelatie : persoon.getHuwelijkGeregistreerdPartnerschappen()) {
            final PartnerHisVolledig gerelateerdePersoon = mijnPartnerRelatie.geefPartnerVan(persoon);

            verwerkHuwelijk(mijnPartnerRelatie, gerelateerdePersoon.getPersoon());
        }

        // Onderzoeken
        for (final PersoonOnderzoekHisVolledig persoonOnderzoek : persoon.getOnderzoeken()) {
            final OnderzoekHisVolledig onderzoek = persoonOnderzoek.getOnderzoek();
            visitHistorie(RubriekenMap.PERSOON, onderzoek.getOnderzoekHistorie());
        }
    }

    private void verwerkOuder(final RelatieHisVolledig relatie, final OuderHisVolledig gerelateerdeBetrokkenheid) {
        visitHistorie(RubriekenMap.OUDER, relatie.getRelatieHistorie());
        visitHistorie(RubriekenMap.OUDER, gerelateerdeBetrokkenheid.getBetrokkenheidHistorie());
        visitHistorie(RubriekenMap.OUDER, gerelateerdeBetrokkenheid.getOuderOuderschapHistorie());
        visitHistorie(RubriekenMap.OUDER, gerelateerdeBetrokkenheid.getOuderOuderlijkGezagHistorie());
        if (gerelateerdeBetrokkenheid.getPersoon() != null) {
            visitHistorie(RubriekenMap.OUDER, gerelateerdeBetrokkenheid.getPersoon().getPersoonIdentificatienummersHistorie());
            visitHistorie(RubriekenMap.OUDER, gerelateerdeBetrokkenheid.getPersoon().getPersoonSamengesteldeNaamHistorie());
            visitHistorie(RubriekenMap.OUDER, gerelateerdeBetrokkenheid.getPersoon().getPersoonGeboorteHistorie());
            visitHistorie(RubriekenMap.OUDER, gerelateerdeBetrokkenheid.getPersoon().getPersoonGeslachtsaanduidingHistorie());
        }
    }

    private void verwerkKind(final OuderHisVolledig mijnBetrokkenheid, final RelatieHisVolledig relatie, final KindHisVolledig gerelateerdeBetrokkenheid) {
        visitHistorie(RubriekenMap.KIND, mijnBetrokkenheid.getBetrokkenheidHistorie());
        visitHistorie(RubriekenMap.KIND, relatie.getRelatieHistorie());
        visitHistorie(RubriekenMap.KIND, gerelateerdeBetrokkenheid.getPersoon().getPersoonIdentificatienummersHistorie());
        visitHistorie(RubriekenMap.KIND, gerelateerdeBetrokkenheid.getPersoon().getPersoonSamengesteldeNaamHistorie());
        visitHistorie(RubriekenMap.KIND, gerelateerdeBetrokkenheid.getPersoon().getPersoonGeboorteHistorie());
    }

    private void verwerkHuwelijk(final HuwelijkGeregistreerdPartnerschapHisVolledig relatie, final PersoonHisVolledig gerelateerdePersoon) {
        visitHistorie(RubriekenMap.HUWELIJK, relatie.getRelatieHistorie());
        visitHistorie(RubriekenMap.HUWELIJK, gerelateerdePersoon.getPersoonIdentificatienummersHistorie());
        visitHistorie(RubriekenMap.HUWELIJK, gerelateerdePersoon.getPersoonSamengesteldeNaamHistorie());
        visitHistorie(RubriekenMap.HUWELIJK, gerelateerdePersoon.getPersoonGeboorteHistorie());
        visitHistorie(RubriekenMap.HUWELIJK, gerelateerdePersoon.getPersoonGeslachtsaanduidingHistorie());
    }

    private <T extends FormeelHistorisch & FormeelVerantwoordbaar<? extends ActieModel>> void visitHistorie(
        final RubriekenMap rubriekenMap,
        final FormeleHistorieSet<T> historie)
    {
        for (final T entiteit : historie) {
            if (isFormeleEntiteitGeraakt(entiteit) && resultaat != null) {
                final List<String> rubrieken = rubriekenMap.getRubrieken(entiteit.getClass());
                if (rubrieken == null) {
                    resultaat = null;
                } else {
                    resultaat.addAll(rubrieken);
                }
                break;
            }
        }
    }

    private <T extends MaterieelHistorisch & MaterieelVerantwoordbaar<? extends ActieModel>> void visitHistorie(
        final RubriekenMap rubriekenMap,
        final MaterieleHistorieSet<T> historie)
    {
        for (final T entiteit : historie) {
            if (isMaterieleEntiteitGeraakt(entiteit) && resultaat != null) {
                final List<String> rubrieken = rubriekenMap.getRubrieken(entiteit.getClass());
                if (rubrieken == null) {
                    resultaat = null;
                } else {
                    resultaat.addAll(rubrieken);
                }
                break;
            }
        }
    }

    private boolean isMaterieleEntiteitGeraakt(final MaterieelVerantwoordbaar<? extends ActieModel> entiteit) {
        return isFormeleEntiteitGeraakt(entiteit) || isGeraakt(entiteit.getVerantwoordingAanpassingGeldigheid());
    }

    private boolean isFormeleEntiteitGeraakt(final FormeelVerantwoordbaar<? extends ActieModel> entiteit) {
        return isGeraakt(entiteit.getVerantwoordingInhoud())
               || isGeraakt(entiteit.getVerantwoordingVerval())
               || entiteit instanceof VerantwoordingTbvLeveringMutaties
                  && isGeraakt(((VerantwoordingTbvLeveringMutaties) entiteit).getVerantwoordingVervalTbvLeveringMutaties());
    }

    private boolean isGeraakt(final ActieModel actie) {
        return actie != null && acties.contains(actie.getID());
    }

    /**
     * Bepaal de mutaties obv de IST stapels.
     *
     * @param istStapels
     *            IST stapels
     */
    public void visit(final List<Stapel> istStapels) {
        if (istStapels != null) {
            for (final Stapel istStapel : istStapels) {
                verwerkIstStapel(istStapel);
            }
        }
    }

    private void verwerkIstStapel(final Stapel istStapel) {
        for (final StapelVoorkomen stapelVoorkomen : istStapel.getStapelVoorkomens()) {
            verwerkIstStapelVoorkomen(istStapel, stapelVoorkomen);
        }
    }

    private void verwerkIstStapelVoorkomen(final Stapel istStapel, final StapelVoorkomen stapelVoorkomen) {
        final AdministratieveHandelingModel ah = (AdministratieveHandelingModel) stapelVoorkomen.getStandaard().getAdministratieveHandeling();
        if (administratieveHandelingId.equals(ah.getID())) {
            if (resultaat != null) {
                resultaat.add(istStapel.getCategorie().getWaarde() + ".");
            }
        }
    }
}
