/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.poc.generiekmodel.hismapper;

import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.poc.generiekmodel.BrpObject;
import nl.bzk.brp.util.testpersoonbouwers.TestPersoonJohnnyJordaan;

/**
 */
public class Blob2Model {


    public static void main(String[] args) {


        final PersoonHisVolledigImpl testpersoon = TestPersoonJohnnyJordaan.maak();
        final BrpObject model = new Blob2Model().converteer(testpersoon);
        model.visit(new ModelViz());
    }

    /**
     *
     * @param persoonHisVolledig
     * @return
     */
    public BrpObject converteer(final PersoonHisVolledigImpl persoonHisVolledig) {
        return new ObjectPersoonMapper(persoonHisVolledig).mapObject();
    }


    /*

    private void vulBetrokkenheden(BrpObject persoon, PersoonHisVolledigImpl persoonHisVolledig) {


        final Set<BetrokkenheidHisVolledigImpl> betrokkenheden = persoonHisVolledig.getBetrokkenheden();
        for (BetrokkenheidHisVolledigImpl betrokkenheidHisVolledig : betrokkenheden) {
            vulBetrokkenheid(persoon, betrokkenheidHisVolledig);
        }
    }

    private void vulBetrokkenheid(BrpObject persoon, BetrokkenheidHisVolledigImpl hisBetr) {

        final BrpObject betrokkenheid = new BrpObject();
        betrokkenheid.parent = persoon;
        betrokkenheid.objectsleutel = hisBetr.getID();
        betrokkenheid.element = ElementObjectType.Betrokkenheid; //TODO bepaal specifiek type

        //TODO rol in identiteitgroep

        vulRelatie(betrokkenheid, hisBetr.getRelatie());
        persoon.voegObjectToe(betrokkenheid);

    }

    private void vulRelatie(BrpObject betrokkenheid, RelatieHisVolledigImpl hisRelatie) {

        final BrpObject relatie = new BrpObject();
        relatie.parent = betrokkenheid;
        relatie.objectsleutel = hisRelatie.getID();
        relatie.element = ElementObjectType.Relatie;

        betrokkenheid.voegObjectToe(relatie);
        //TODO bepaal elemetn adhv type hisRelatie


        {
            //relatie standaard groep
            final BrpGroep groep = new BrpGroep();
            relatie.groepen.add(groep);
            groep.parent = betrokkenheid;
            groep.element = ElementGroep.RelatieStandaard;
            groep.voorkomens = new HashSet<>();
            final FormeleHistorieSet<HisRelatieModel> persoonGeboorteHistorie = hisRelatie.getRelatieHistorie();
            for (HisRelatieModel model : persoonGeboorteHistorie) {

                final BrpGroepVoorkomen voorkomen = maakVoorkomen(groep, model);
                voorkomen.voorkomensleutel = model.getID();
                //attrs

                voegAttrbuutToe(voorkomen, ElementAttribuut.RelatieBuitenlandsePlaatsAanvang, model.getBuitenlandsePlaatsAanvang());

            }
        }

        for (BetrokkenheidHisVolledigImpl betrokkenheidHisVolledig : hisRelatie.getBetrokkenheden()) {
            if (betrokkenheidHisVolledig.getID() == betrokkenheid.objectsleutel) {
                //skip hoofdpersoon relatie
                continue;
            }

            vulBetrokkenheidVoorAnderePersonen(relatie, betrokkenheidHisVolledig);
        }

    }

    private void vulBetrokkenheidVoorAnderePersonen(BrpObject relatie,
                                                    BetrokkenheidHisVolledigImpl hisBetr) {

        final BrpObject betrokkenheid = new BrpObject();
        betrokkenheid.parent = relatie;
        betrokkenheid.objectsleutel = hisBetr.getID();
        betrokkenheid.element = ElementObjectType.Betrokkenheid;

        relatie.voegObjectToe(betrokkenheid);

    }

    private void vulPersoonGroepen(BrpObject persoon, PersoonHisVolledigImpl persoonHisVolledig) {


        //afgeleid administratief
        {
            final BrpGroep groep = new BrpGroep();
            persoon.groepen.add(groep);
            groep.parent = persoon;
            groep.element = ElementGroep.PersoonAfgeleidAdministratief;
            groep.voorkomens = new HashSet<>();
            for (HisPersoonAfgeleidAdministratiefModel model : persoonHisVolledig.getPersoonAfgeleidAdministratiefHistorie()) {

                final BrpGroepVoorkomen voorkomen = maakVoorkomen(groep, model);
                voorkomen.voorkomensleutel = model.getID();

                //attrs
                voegAttrbuutToe(voorkomen, ElementAttribuut.PersoonAfgeleidAdministratiefIndicatieOnverwerktBijhoudingsvoorstelNietIngezeteneAanwezig,
                        model.getIndicatieOnverwerktBijhoudingsvoorstelNietIngezeteneAanwezig());
                voegAttrbuutToe(voorkomen, ElementAttribuut.PersoonAfgeleidAdministratiefIndicatieVoorkomenTbvLeveringMutaties,
                        model.getIndicatieVoorkomenTbvLeveringMutaties());

            }
        }

        //indicaties
        persoonHisVolledig.getIndicatieStaatloos();



    }*/


}
