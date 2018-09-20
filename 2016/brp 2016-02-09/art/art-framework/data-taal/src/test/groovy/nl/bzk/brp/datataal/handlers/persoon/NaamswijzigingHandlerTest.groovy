package nl.bzk.brp.datataal.handlers.persoon

import nl.bzk.brp.datataal.AbstractDSLIntegratieTest
import nl.bzk.brp.datataal.execution.PersoonDSLExecutor
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl
import nl.bzk.brp.model.operationeel.kern.ActieModel
import nl.bzk.brp.model.operationeel.kern.HisPersoonAfgeleidAdministratiefModel
import org.junit.Test

class NaamswijzigingHandlerTest extends AbstractDSLIntegratieTest {

    @Test
    void integratie() {
        def dsl = '''
            persoon = Persoon.uitGebeurtenissen {
                geboorte(aanvang:19900909) {
                    namen {
                        voornamen 'Piet'
                        geslachtsnaam(
                            [stam: 'Jansen']
                        )
                    }
                }

                naamswijziging(aanvang: 20001010) {
                    geslachtsnaam stam:'Jansen' wordt stam:'Pietersen', voorvoegsel:'van'
                }
            }

            slaOp(persoon)
        '''

        // act
        def persoon = new PersoonDSLExecutor().execute(dsl).persoon as PersoonHisVolledigImpl

        // assert
        assert persoon.geslachtsnaamcomponenten[0].persoonGeslachtsnaamcomponentHistorie.actueleRecord.stam.waarde == 'Pietersen'
        assert persoon.geslachtsnaamcomponenten[0].persoonGeslachtsnaamcomponentHistorie.actueleRecord.voorvoegsel.waarde == 'van'
        assert persoon.geslachtsnaamcomponenten[0].persoonGeslachtsnaamcomponentHistorie.aantal == 3
        assert persoon.geslachtsnaamcomponenten.size() == 1

        assert persoon.persoonSamengesteldeNaamHistorie.actueleRecord.voornamen.waarde == 'Piet'
        assert persoon.persoonSamengesteldeNaamHistorie.actueleRecord.geslachtsnaamstam.waarde == 'Pietersen'
        assert persoon.persoonSamengesteldeNaamHistorie.aantal == 3
    }

    @Test
    void integratieMetPartij() {
        def dsl = '''
            persoon = Persoon.uitGebeurtenissen {
                geboorte(aanvang:19900909) {
                    namen {
                        voornamen 'Piet'
                        geslachtsnaam(
                            [stam: 'Jansen']
                        )
                    }
                }

                naamswijziging(aanvang: 20001010, partij: 34401) {
                    geslachtsnaam stam:'Jansen' wordt stam:'Pietersen', voorvoegsel:'van'
                }
            }

            slaOp(persoon)
        '''

        // act
        def persoon = new PersoonDSLExecutor().execute(dsl).persoon as PersoonHisVolledigImpl

        assert persoon.administratieveHandelingen.size() == 1
        assert persoon.administratieveHandelingen[0].partij.waarde.code.waarde == 34401

        // assert
        assert persoon.geslachtsnaamcomponenten.size() == 1
        assert persoon.geslachtsnaamcomponenten[0].persoonGeslachtsnaamcomponentHistorie.actueleRecord.stam.waarde == 'Pietersen'
        assert persoon.geslachtsnaamcomponenten[0].persoonGeslachtsnaamcomponentHistorie.actueleRecord.voorvoegsel.waarde == 'van'
        assert persoon.geslachtsnaamcomponenten[0].persoonGeslachtsnaamcomponentHistorie.aantal == 3

        assert persoon.persoonSamengesteldeNaamHistorie.actueleRecord.voornamen.waarde == 'Piet'
        assert persoon.persoonSamengesteldeNaamHistorie.actueleRecord.geslachtsnaamstam.waarde == 'Pietersen'
        assert persoon.persoonSamengesteldeNaamHistorie.aantal == 3
    }

    @Test
    void integratieMetPartijEnBronDocumenten() {
        def dsl = '''
            persoon = Persoon.uitGebeurtenissen {
                geboorte(aanvang:19900909) {
                    namen {
                        voornamen 'Piet'
                        geslachtsnaam(
                            [stam: 'Jansen']
                        )
                    }
                }

                naamswijziging(aanvang: 20001010, partij: 34401) {
                    geslachtsnaam stam:'Jansen' wordt stam:'Pietersen', voorvoegsel:'van'
                    document grond:'Blabla'
                }
            }

            slaOp(persoon)
        '''

        // act
        def persoon = new PersoonDSLExecutor().execute(dsl).persoon as PersoonHisVolledigImpl

        assert persoon.getPersoonAfgeleidAdministratiefHistorie().size() == 2

        HisPersoonAfgeleidAdministratiefModel administratiefModel =
            persoon.getPersoonAfgeleidAdministratiefHistorie().iterator().find {it -> it.verantwoordingInhoud.bronnen.size() == 1}

        assert administratiefModel.administratieveHandeling.partij.waarde.code.waarde == 34401
        assert administratiefModel.administratieveHandeling.acties.size() == 1
        assert administratiefModel.getVerantwoordingInhoud().bronnen.size() == 1
        assert administratiefModel.getVerantwoordingInhoud().bronnen[0].rechtsgrondomschrijving.waarde == 'Blabla'

        // assert
        assert persoon.geslachtsnaamcomponenten.size() == 1
        assert persoon.geslachtsnaamcomponenten[0].persoonGeslachtsnaamcomponentHistorie.actueleRecord.stam.waarde == 'Pietersen'
        assert persoon.geslachtsnaamcomponenten[0].persoonGeslachtsnaamcomponentHistorie.actueleRecord.voorvoegsel.waarde == 'van'
        assert persoon.geslachtsnaamcomponenten[0].persoonGeslachtsnaamcomponentHistorie.aantal == 3

        assert persoon.persoonSamengesteldeNaamHistorie.actueleRecord.voornamen.waarde == 'Piet'
        assert persoon.persoonSamengesteldeNaamHistorie.actueleRecord.geslachtsnaamstam.waarde == 'Pietersen'
        assert persoon.persoonSamengesteldeNaamHistorie.aantal == 3
    }




}
