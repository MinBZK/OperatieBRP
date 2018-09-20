/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.beheer.view;

import java.lang.reflect.Method;
import java.util.Collection;
import nl.bzk.brp.model.basis.ElementIdentificeerbaar;
import nl.bzk.brp.model.basis.ModelIdentificeerbaar;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.operationeel.kern.HisBetrokkenheidModel;
import nl.bzk.brp.model.operationeel.kern.HisOnderzoekAfgeleidAdministratiefModel;
import nl.bzk.brp.model.operationeel.kern.HisOnderzoekModel;
import nl.bzk.brp.model.operationeel.kern.HisOuderOuderlijkGezagModel;
import nl.bzk.brp.model.operationeel.kern.HisOuderOuderschapModel;
import nl.bzk.brp.model.operationeel.kern.HisPartijOnderzoekModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonAdresModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonAfgeleidAdministratiefModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonBijhoudingModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonDeelnameEUVerkiezingenModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonGeboorteModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonGeslachtsaanduidingModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonGeslachtsnaamcomponentModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonIdentificatienummersModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonIndicatieBehandeldAlsNederlanderModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonIndicatieBijzondereVerblijfsrechtelijkePositieModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonIndicatieDerdeHeeftGezagModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonIndicatieModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonIndicatieOnderCurateleModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonIndicatieSignaleringMetBetrekkingTotVerstrekkenReisdocumentModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonIndicatieStaatloosModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonIndicatieVastgesteldNietNederlanderModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonIndicatieVolledigeVerstrekkingsbeperkingModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonInschrijvingModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonMigratieModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonNaamgebruikModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonNationaliteitModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonNummerverwijzingModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonOnderzoekModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonOverlijdenModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonPersoonskaartModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonReisdocumentModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonSamengesteldeNaamModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonUitsluitingKiesrechtModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonVerblijfsrechtModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonVerificatieModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonVerstrekkingsbeperkingModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonVoornaamModel;
import nl.bzk.brp.model.operationeel.kern.HisRelatieModel;

/**
 * Actie utility methods.
 */
public final class Acties {

    private Acties() {
        // Niet instantieerbaar
    }

    /**
     * Reshuffeld (groepeert, sorteert, etc) actie naar een actie view.
     *
     * @param actie actie
     * @return actie view
     */
    public static ActieView asActieView(final Actie actie) {
        final ActieView resultaat = new ActieView(actie);

        verwerkPersoon(actie, resultaat);

        verwerkRelatie(actie, resultaat);

        verwerkOnderzoek(actie, resultaat);
        return resultaat;
    }

    private static void verwerkPersoon(final Actie actie, final ActieView resultaat) {
        verwerkPersoonGegevens(actie, resultaat);
        verwerkPersoonIndicatie(actie, resultaat);
        verwerkPersoonAdres(actie, resultaat);
        verwerkPersoonGeslachtsnaamComponent(actie, resultaat);
        verwerkPersoonNationaliteit(actie, resultaat);
        verwerkPersoonReisDocument(actie, resultaat);
        verwerkPersoonVerificatie(actie, resultaat);
        verwerkPersoonVerstrekkingsBeperking(actie, resultaat);
        verwerkPersoonVoornaam(actie, resultaat);
    }

    private static void verwerkPersoonGegevens(final Actie actie, final ActieView resultaat) {
        // PERSOON
        verwerkHistorie(actie.getHisPersoonAfgeleidAdministratief(), new PersoonViewBepaler<HisPersoonAfgeleidAdministratiefModel>(resultaat));
        verwerkHistorie(actie.getHisPersoonIdentificatienummer(), new PersoonViewBepaler<HisPersoonIdentificatienummersModel>(resultaat));
        verwerkHistorie(actie.getHisPersoonSamengesteldeNaam(), new PersoonViewBepaler<HisPersoonSamengesteldeNaamModel>(resultaat));
        verwerkHistorie(actie.getHisPersoonGeboorte(), new PersoonViewBepaler<HisPersoonGeboorteModel>(resultaat));
        verwerkHistorie(actie.getHisPersoonGeslachtsaanduiding(), new PersoonViewBepaler<HisPersoonGeslachtsaanduidingModel>(resultaat));
        verwerkHistorie(actie.getHisPersoonInschrijving(), new PersoonViewBepaler<HisPersoonInschrijvingModel>(resultaat));
        verwerkHistorie(actie.getHisPersoonNummerverwijzing(), new PersoonViewBepaler<HisPersoonNummerverwijzingModel>(resultaat));
        verwerkHistorie(actie.getHisPersoonBijhoudingModel(), new PersoonViewBepaler<HisPersoonBijhoudingModel>(resultaat));
        verwerkHistorie(actie.getHisPersoonOverlijdenModel(), new PersoonViewBepaler<HisPersoonOverlijdenModel>(resultaat));
        verwerkHistorie(actie.getHisPersoonNaamgebruikModel(), new PersoonViewBepaler<HisPersoonNaamgebruikModel>(resultaat));
        verwerkHistorie(actie.getHisPersoonMigratie(), new PersoonViewBepaler<HisPersoonMigratieModel>(resultaat));
        verwerkHistorie(actie.getHisPersoonVerblijfsrechtModel(), new PersoonViewBepaler<HisPersoonVerblijfsrechtModel>(resultaat));
        verwerkHistorie(actie.getHisPersoonUitsluitingKiesrecht(), new PersoonViewBepaler<HisPersoonUitsluitingKiesrechtModel>(resultaat));
        verwerkHistorie(actie.getHisPersoonDeelnameEUVerkiezingen(), new PersoonViewBepaler<HisPersoonDeelnameEUVerkiezingenModel>(resultaat));
        verwerkHistorie(actie.getHisPersoonPersoonskaart(), new PersoonViewBepaler<HisPersoonPersoonskaartModel>(resultaat));
    }

    private static void verwerkPersoonIndicatie(final Actie actie, final ActieView resultaat) {
        // PERSOON / INDICATIE
        verwerkHistorie(actie.getHisPersoonIndicatieDerdeHeeftGezagModel(), new IndicatieViewBepaler<HisPersoonIndicatieDerdeHeeftGezagModel>(resultaat));
        verwerkHistorie(actie.getHisPersoonIndicatieOnderCuratele(), new IndicatieViewBepaler<HisPersoonIndicatieOnderCurateleModel>(resultaat));
        verwerkHistorie(
                actie.getHisPersoonIndicatieBijzondereVerblijfsrechtelijkePositie(),
                new IndicatieViewBepaler<HisPersoonIndicatieBijzondereVerblijfsrechtelijkePositieModel>(resultaat));
        verwerkHistorie(actie.getHisPersoonIndicatieStaatloos(), new IndicatieViewBepaler<HisPersoonIndicatieStaatloosModel>(resultaat));
        verwerkHistorie(
                actie.getHisPersoonIndicatieVolledigeVerstrekkingsbeperking(),
                new IndicatieViewBepaler<HisPersoonIndicatieVolledigeVerstrekkingsbeperkingModel>(resultaat));
        verwerkHistorie(
                actie.getHisPersoonIndicatieVastgesteldNietNederlander(),
                new IndicatieViewBepaler<HisPersoonIndicatieVastgesteldNietNederlanderModel>(resultaat));
        verwerkHistorie(actie.getHisPersoonIndicatieBehandeldAlsNederlander(), new IndicatieViewBepaler<HisPersoonIndicatieBehandeldAlsNederlanderModel>(
                resultaat));
        verwerkHistorie(
                actie.getHisPersoonIndicatieSignaleringMetBetrekkingTotVerstrekkenReisdocument(),
                new IndicatieViewBepaler<HisPersoonIndicatieSignaleringMetBetrekkingTotVerstrekkenReisdocumentModel>(resultaat));
    }

    private static void verwerkPersoonAdres(final Actie actie, final ActieView resultaat) {
        // PERSOON / ADRES
        verwerkHistorie(actie.getHisAdresStandaard(), new ObjectViewBepaler<HisPersoonAdresModel>() {
            @Override
            public ObjectView<?> bepaalObjectView(final HisPersoonAdresModel hisRecord) {
                final PersoonView persoonView = resultaat.getView(hisRecord.getPersoonAdres().getPersoon());
                return persoonView.getView(hisRecord.getPersoonAdres());
            }
        });
    }

    private static void verwerkPersoonGeslachtsnaamComponent(final Actie actie, final ActieView resultaat) {
        // PERSOON / GESLACHTSNAAMCOMPONENT
        verwerkHistorie(actie.getHisGeslachtsnaamcomponentStandaard(), new ObjectViewBepaler<HisPersoonGeslachtsnaamcomponentModel>() {
            @Override
            public ObjectView<?> bepaalObjectView(final HisPersoonGeslachtsnaamcomponentModel hisRecord) {
                final PersoonView persoonView = resultaat.getView(hisRecord.getPersoonGeslachtsnaamcomponent().getPersoon());
                return persoonView.getView(hisRecord.getPersoonGeslachtsnaamcomponent());
            }
        });
    }

    private static void verwerkPersoonNationaliteit(final Actie actie, final ActieView resultaat) {
        // PERSOON / NATIONALITEIT
        verwerkHistorie(actie.getHisNationaliteitStandaard(), new ObjectViewBepaler<HisPersoonNationaliteitModel>() {
            @Override
            public ObjectView<?> bepaalObjectView(final HisPersoonNationaliteitModel hisRecord) {
                final PersoonView persoonView = resultaat.getView(hisRecord.getPersoonNationaliteit().getPersoon());
                return persoonView.getView(hisRecord.getPersoonNationaliteit());
            }
        });
    }

    private static void verwerkPersoonReisDocument(final Actie actie, final ActieView resultaat) {
        // PERSOON / REISDOCUMENT
        verwerkHistorie(actie.getHisReisdocumentStandaard(), new ObjectViewBepaler<HisPersoonReisdocumentModel>() {
            @Override
            public ObjectView<?> bepaalObjectView(final HisPersoonReisdocumentModel hisRecord) {
                final PersoonView persoonView = resultaat.getView(hisRecord.getPersoonReisdocument().getPersoon());
                return persoonView.getView(hisRecord.getPersoonReisdocument());
            }
        });
    }

    private static void verwerkPersoonVerificatie(final Actie actie, final ActieView resultaat) {
        // PERSOON / VERIFICATIE
        verwerkHistorie(actie.getHisVerificatieStandaard(), new ObjectViewBepaler<HisPersoonVerificatieModel>() {
            @Override
            public ObjectView<?> bepaalObjectView(final HisPersoonVerificatieModel hisRecord) {
                final PersoonView persoonView = resultaat.getView(hisRecord.getPersoonVerificatie().getGeverifieerde());
                return persoonView.getView(hisRecord.getPersoonVerificatie());
            }
        });
    }

    private static void verwerkPersoonVerstrekkingsBeperking(final Actie actie, final ActieView resultaat) {
        // PERSOON / VERSTREKKINGSBEPERKING
        verwerkHistorie(actie.getHisVerstrekkingsbeperkingStandaard(), new ObjectViewBepaler<HisPersoonVerstrekkingsbeperkingModel>() {
            @Override
            public ObjectView<?> bepaalObjectView(final HisPersoonVerstrekkingsbeperkingModel hisRecord) {
                final PersoonView persoonView = resultaat.getView(hisRecord.getPersoonVerstrekkingsbeperking().getPersoon());
                return persoonView.getView(hisRecord.getPersoonVerstrekkingsbeperking());
            }
        });
    }

    private static void verwerkPersoonVoornaam(final Actie actie, final ActieView resultaat) {
        // PERSOON / VOORNAAM
        verwerkHistorie(actie.getHisVoornaamStandaard(), new ObjectViewBepaler<HisPersoonVoornaamModel>() {
            @Override
            public ObjectView<?> bepaalObjectView(final HisPersoonVoornaamModel hisRecord) {
                final PersoonView persoonView = resultaat.getView(hisRecord.getPersoonVoornaam().getPersoon());
                return persoonView.getView(hisRecord.getPersoonVoornaam());
            }
        });
    }

    private static void verwerkRelatie(final Actie actie, final ActieView resultaat) {
        // RELATIE
        verwerkHistorie(actie.getHisRelatieStandaard(), new ObjectViewBepaler<HisRelatieModel>() {
            @Override
            public ObjectView<?> bepaalObjectView(final HisRelatieModel hisRecord) {
                return resultaat.getView(hisRecord.getRelatie());
            }
        });

        // RELATIE / BETROKKENHEID
        verwerkHistorie(actie.getHisBetrokkenheidStandaard(), new ObjectViewBepaler<HisBetrokkenheidModel>() {
            @Override
            public ObjectView<?> bepaalObjectView(final HisBetrokkenheidModel hisRecord) {
                final RelatieView relatieView = resultaat.getView(hisRecord.getBetrokkenheid().getRelatie());
                return relatieView.getView(hisRecord.getBetrokkenheid());
            }
        });
        verwerkHistorie(actie.getHisBetrokkenheidOuderschap(), new ObjectViewBepaler<HisOuderOuderschapModel>() {
            @Override
            public ObjectView<?> bepaalObjectView(final HisOuderOuderschapModel hisRecord) {
                final RelatieView relatieView = resultaat.getView(hisRecord.getBetrokkenheid().getRelatie());
                return relatieView.getView(hisRecord.getBetrokkenheid());
            }
        });
        verwerkHistorie(actie.getHisBetrokkenheidOuderlijkGezag(), new ObjectViewBepaler<HisOuderOuderlijkGezagModel>() {
            @Override
            public ObjectView<?> bepaalObjectView(final HisOuderOuderlijkGezagModel hisRecord) {
                final RelatieView relatieView = resultaat.getView(hisRecord.getBetrokkenheid().getRelatie());
                return relatieView.getView(hisRecord.getBetrokkenheid());
            }
        });
    }

    private static void verwerkOnderzoek(final Actie actie, final ActieView resultaat) {
        // ONDERZOEK
        verwerkHistorie(actie.getHisOnderzoekStandaard(), new ObjectViewBepaler<HisOnderzoekModel>() {
            @Override
            public ObjectView<?> bepaalObjectView(final HisOnderzoekModel hisRecord) {
                return resultaat.getView(hisRecord.getOnderzoek());
            }
        });
        verwerkHistorie(actie.getHisOnderzoekAfgeleidAdministratief(), new ObjectViewBepaler<HisOnderzoekAfgeleidAdministratiefModel>() {
            @Override
            public ObjectView<?> bepaalObjectView(final HisOnderzoekAfgeleidAdministratiefModel hisRecord) {
                return resultaat.getView(hisRecord.getOnderzoek());
            }
        });

        // ONDERZOEK / PARTIJ
        verwerkHistorie(actie.getHisOnderzoekPartijStandaard(), new ObjectViewBepaler<HisPartijOnderzoekModel>() {
            @Override
            public ObjectView<?> bepaalObjectView(final HisPartijOnderzoekModel hisRecord) {
                final OnderzoekView onderzoekView = resultaat.getView(hisRecord.getPartijOnderzoek().getOnderzoek());
                return onderzoekView.getView(hisRecord.getPartijOnderzoek());
            }
        });
        // ONDERZOEK / PERSOON
        verwerkHistorie(actie.getHisOnderzoekPersoonStandaard(), new ObjectViewBepaler<HisPersoonOnderzoekModel>() {
            @Override
            public ObjectView<?> bepaalObjectView(final HisPersoonOnderzoekModel hisRecord) {
                final OnderzoekView onderzoekView = resultaat.getView(hisRecord.getPersoonOnderzoek().getOnderzoek());
                return onderzoekView.getView(hisRecord.getPersoonOnderzoek());
            }
        });
    }

    private static <T extends ElementIdentificeerbaar & ModelIdentificeerbaar<?>> void verwerkHistorie(
        final Historie<T> historie,
        final ObjectViewBepaler<T> viewBepaler)
    {
        verwerkHistorieList(ActieType.INHOUD, historie.getInhoud(), viewBepaler);
        //verwerkHistorieList(ActieType.AANPASSING, historie.getGeldigheid(), viewBepaler);
        verwerkHistorieList(ActieType.VERVAL, historie.getVerval(), viewBepaler);
        verwerkHistorieList(ActieType.VERVAL_MUTS, historie.getVervalTbvMutatie(), viewBepaler);
    }

    private static <T extends ElementIdentificeerbaar & ModelIdentificeerbaar<?>> void verwerkHistorieList(
        final ActieType actieType,
        final Collection<T> recordList,
        final ObjectViewBepaler<T> viewBepaler)
    {
        for (final T inhoudRecord : recordList) {
            final ObjectView<?> objectView = viewBepaler.bepaalObjectView(inhoudRecord);
            final GegevensView<T> gegevensView = (GegevensView<T>) objectView.geefGegevensViewVoor(inhoudRecord.getElementIdentificatie());
            gegevensView.voegGegevenToe(actieType, inhoudRecord);
        }
    }

    @SuppressWarnings("unchecked")
    private static <T> T get(final Object target, final String methodName) {
        try {
            final Method getter = target.getClass().getMethod(methodName);
            return (T) getter.invoke(target);
        } catch (final ReflectiveOperationException e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * Object view bepaler
     *
     * @param <T> basis object type
     */
    private interface ObjectViewBepaler<T> {
        ObjectView<?> bepaalObjectView(T hisRecord);
    }

    /**
     * Persoon view bepaler.
     * @param <T> type class
     */
    private static class PersoonViewBepaler<T> implements ObjectViewBepaler<T> {

        private final ActieView actieView;

        /**
         * Constructor.
         *
         * @param actieView De actieview.
         */
        PersoonViewBepaler(final ActieView actieView) {
            this.actieView = actieView;
        }

        /**
         * Bepaalt de objectview.
         *
         * @param hisRecord Historische view.
         */
        @Override
        public ObjectView<?> bepaalObjectView(final Object hisRecord) {
            final PersoonHisVolledig persoon = get(hisRecord, "getPersoon");
            return actieView.getView(persoon);
        }
    }

    /**
     * Indicatieviewbepaler.
     * @param <T> de class
     */
    private static class IndicatieViewBepaler<T extends HisPersoonIndicatieModel & ElementIdentificeerbaar> implements ObjectViewBepaler<T> {
        private final ActieView actieView;

        /**
         * Constructor.
         *
         * @param actieView De actieview.
         */
        IndicatieViewBepaler(final ActieView actieView) {
            this.actieView = actieView;
        }

        /**
         * Bepaalt de objectview.
         *
         * @param hisRecord Historische view.
         */
        @Override
        public ObjectView<?> bepaalObjectView(final T hisRecord) {
            return actieView.getView(hisRecord.getPersoonIndicatie().getPersoon());
        }
    }
}
