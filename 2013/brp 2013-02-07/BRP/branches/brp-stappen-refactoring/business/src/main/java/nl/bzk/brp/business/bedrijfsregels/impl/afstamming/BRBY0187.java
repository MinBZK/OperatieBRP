/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.bedrijfsregels.impl.afstamming;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import nl.bzk.brp.business.bedrijfsregels.ActieBedrijfsRegel;
import nl.bzk.brp.dataaccess.repository.PersoonRepository;
import nl.bzk.brp.dataaccess.repository.RelatieRepository;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Datum;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortMelding;
import nl.bzk.brp.model.basis.Identificeerbaar;
import nl.bzk.brp.model.logisch.kern.Actie;
import nl.bzk.brp.model.logisch.kern.FamilierechtelijkeBetrekking;
import nl.bzk.brp.model.logisch.kern.Persoon;
import nl.bzk.brp.model.operationeel.kern.PersoonModel;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.model.validatie.MeldingCode;
import nl.bzk.brp.util.DatumUtil;
import nl.bzk.brp.util.RelatieUtils;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Implementatie van bedrijfsregel BRBY0187.
 * De datum geboorte uit de groep Geboorte van kind moet minstens 306 dagen later zijn dan de datum geboorte uit
 * de groep Geboorte van andere kinderen die betrokken zijn in een relatie van de soort familierechtelijke
 * betrekking met dezelfde persoon als ouder als de nieuwgeborene als de ouder uit wie het kind geboren is.
 * <p/>
 * Uitzondering hierop is als de datum geboorte uit de groep Geboorte van de nieuwgeborene precies gelijk is aan die van
 * andere kinderen (meerlingsituatie).
 * <p/>
 * Merk op: de 306 dgn (== 9 maanden) is normaal gesproken geldig alleen voor NL-moderes, maar is van toepassing hier
 * voor alle moeders.
 *
 * @brp.bedrijfsregel BRBY0187
 */
public class BRBY0187 implements ActieBedrijfsRegel<FamilierechtelijkeBetrekking> {

    private static final Logger LOGGER                 = LoggerFactory.getLogger(BRBY0187.class);
    private static final int    NEGEN_MAANDEN_IN_DAGEN = 306;

    @Inject
    private PersoonRepository   persoonRepository;

    @Inject
    private RelatieRepository   relatieRepository;

    @Override
    public String getCode() {
        return "BRBY0187";
    }

    @Override
    public List<Melding> executeer(final FamilierechtelijkeBetrekking huidigeSituatie,
            final FamilierechtelijkeBetrekking nieuweSituatie, final Actie actie)
    {
        List<Melding> meldingen = new ArrayList<Melding>();

        // we doen geen CHECK en FOUTEN meer als we een van de elementen missen.
        // Dit is al in een eerdere situatie opgevangen.
        // zoiets als minimaal 1, maximaal 2 ouders, EXACT 1 met indicatieAdresgevenOuder etc ...
        if (null != nieuweSituatie) {
            Persoon moeder = RelatieUtils.haalMoederUitRelatie(nieuweSituatie);
            Persoon kind = RelatieUtils.haalKindUitRelatie(nieuweSituatie);

            if (null == moeder) {
                LOGGER.error(String.format("%s kan niet uigevoerd worden omdat 'moeder' niet gevonden is voor kind.",
                        getCode()));
            } else {
                if (null == kind) {
                    LOGGER.error(String.format("%s kan niet uigevoerd worden omdat 'kind' niet gevonden is voor kind.",
                            getCode()));
                } else if (null == kind.getGeboorte()) {
                    LOGGER.error(String.format("%s kan niet uigevoerd worden omdat 'kind' geen geboorte datum heeft.",
                            getCode()));
                } else {
                    PersoonModel pMoeder = haalPersoonOp(moeder, meldingen);
                    if (meldingen.size() == 0 && pMoeder != null) {
                        Melding melding = controlleerKind(kind, pMoeder, moeder);
                        if (melding != null) {
                            meldingen.add(melding);
                        }
                    }
                }
            }
        }
        return meldingen;
    }

    /**
     * Methode voert de controlle uit.
     * Heeft deze moeder meerdere kinderen waarvan de leeftijd jonger is dan '9 maanden'.
     *
     * @param kind Persoon
     * @param pMoeder Persoon
     * @param moeder berpsson bericht, voor het verzendendId groep
     * @return melding of null
     */
    private Melding controlleerKind(final Persoon kind, final PersoonModel pMoeder, final Persoon moeder) {
        Melding melding = null;
        Datum datumGeboorteNegenMaandenGeleden =
            DatumUtil.voegToeDatum(kind.getGeboorte().getDatumGeboorte(), Calendar.DATE, -NEGEN_MAANDEN_IN_DAGEN);
        // we gaan vanuit dat dit al eerder is gechecked op niet null en geldig kalender.

        // haal ALLE kinderen van deze moeder.
        List<Integer> kinderenIds = relatieRepository.haalopKinderen(pMoeder.getID());
        if (CollectionUtils.isNotEmpty(kinderenIds)) {
            for (Integer kindId : kinderenIds) {
                PersoonModel oudereKind = persoonRepository.haalPersoonSimpel(kindId);
                if (null == oudereKind) {
                    // whaa? waarom kunnen we nu ineens niet vinden?
                    LOGGER.error("Kan ineens geen persoon vinden met id: " + kindId);
                } else {
                    if (null != oudereKind.getGeboorte() && null != oudereKind.getGeboorte().getDatumGeboorte()) {
                        // is het oudere kind 306 dgn ouder dan het in te schrijven kind?
                        // op 306 dagen terug is nog goed, later is het fout.

                        // Uitzondering is dat als ze beide geboorte datum hebben, dan is het weer goed (meerlingen).
                        Datum geboorteDatumOuderKind = oudereKind.getGeboorte().getDatumGeboorte();

                        if (!geboorteDatumOuderKind.op(kind.getGeboorte().getDatumGeboorte())
                            && geboorteDatumOuderKind.na(datumGeboorteNegenMaandenGeleden))
                        {
                            // don't care van andere kinderen, een is al fout.
                            return new Melding(SoortMelding.WAARSCHUWING, MeldingCode.BRBY0187,
                                    (Identificeerbaar) kind.getGeboorte(), "geboorteDatum");
                        }
                    }
                }
            }
        }

        return melding;
    }

    /**
     * .
     *
     * @param persoonBericht .
     * @param meldingen .
     * @return .
     */
    private PersoonModel haalPersoonOp(final Persoon persoonBericht, final List<Melding> meldingen) {
        PersoonModel persoonModel = null;
        persoonModel =
            persoonRepository.findByBurgerservicenummer(persoonBericht.getIdentificatienummers()
                    .getBurgerservicenummer());
        if (persoonModel == null) {
            meldingen.add(new Melding(SoortMelding.FOUT, MeldingCode.REF0001, "Kan ouder niet vinden met BSN: "
                + persoonBericht.getIdentificatienummers().getBurgerservicenummer().getWaarde(),
                    (Identificeerbaar) persoonBericht.getIdentificatienummers(), "burgerservicenummer"));
        }
        return persoonModel;
    }

}
