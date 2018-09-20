/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.momentview.kern;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import javax.annotation.Generated;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.ElementEnum;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortIndicatie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoonAttribuut;
import nl.bzk.brp.model.basis.ElementIdentificeerbaar;
import nl.bzk.brp.model.basis.ModelMoment;
import nl.bzk.brp.model.hisvolledig.autaut.PersoonAfnemerindicatieHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.BetrokkenheidHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.ErkennerHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.InstemmerHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.KindHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.NaamgeverHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.OuderHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PartnerHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PersoonAdresHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PersoonGeslachtsnaamcomponentHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PersoonIndicatieHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PersoonNationaliteitHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PersoonOnderzoekHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PersoonReisdocumentHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PersoonVerificatieHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PersoonVerstrekkingsbeperkingHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PersoonVoornaamHisVolledig;
import nl.bzk.brp.model.hisvolledig.momentview.autaut.PersoonAfnemerindicatieView;
import nl.bzk.brp.model.logisch.kern.PersoonBasis;
import nl.bzk.brp.model.operationeel.kern.HisPersoonAfgeleidAdministratiefModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonBijhoudingModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonDeelnameEUVerkiezingenModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonGeboorteModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonGeslachtsaanduidingModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonIdentificatienummersModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonInschrijvingModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonMigratieModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonNaamgebruikModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonNummerverwijzingModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonOverlijdenModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonPersoonskaartModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonSamengesteldeNaamModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonUitsluitingKiesrechtModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonVerblijfsrechtModel;

/**
 * View klasse voor Persoon.
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.HisVolledigMomentViewModelGenerator")
public abstract class AbstractPersoonView implements ModelMoment, PersoonBasis, ElementIdentificeerbaar {

    private final PersoonHisVolledig persoon;
    private final DatumTijdAttribuut formeelPeilmoment;
    private final DatumAttribuut materieelPeilmoment;

    /**
     * Constructor die het HisVolledig object achter de view proxied.
     *
     * @param persoon hisVolledig instantie voor deze view.
     * @param formeelPeilmoment formeel peilmoment.
     * @param materieelPeilmoment materieel peilmoment.
     */
    public AbstractPersoonView(final PersoonHisVolledig persoon, final DatumTijdAttribuut formeelPeilmoment, final DatumAttribuut materieelPeilmoment) {
        this.persoon = persoon;
        this.formeelPeilmoment = formeelPeilmoment;
        this.materieelPeilmoment = materieelPeilmoment;

    }

    /**
     * Retourneert formeel peilmoment voor deze view.
     *
     * @return Formeel peilmoment voor deze view.
     */
    protected final DatumTijdAttribuut getFormeelPeilmoment() {
        return formeelPeilmoment;
    }

    /**
     * Retourneert materieel peilmoment voor deze view.
     *
     * @return Materieel peilmoment voor deze view.
     */
    protected final DatumAttribuut getMaterieelPeilmoment() {
        return materieelPeilmoment;
    }

    /**
     * Functie die aangeeft of er actuele gegevens zijn in deze view.
     *
     * @return true indien actuele gegevens aanwezig, anders false
     */
    public boolean heeftActueleGegevens() {
        return this.getAfgeleidAdministratief() != null
               || this.getIdentificatienummers() != null
               || this.getSamengesteldeNaam() != null
               || this.getGeboorte() != null
               || this.getGeslachtsaanduiding() != null
               || this.getInschrijving() != null
               || this.getNummerverwijzing() != null
               || this.getBijhouding() != null
               || this.getOverlijden() != null
               || this.getNaamgebruik() != null
               || this.getMigratie() != null
               || this.getVerblijfsrecht() != null
               || this.getUitsluitingKiesrecht() != null
               || this.getDeelnameEUVerkiezingen() != null
               || this.getPersoonskaart() != null;
    }

    /**
     * Retourneert ID van Persoon.
     *
     * @return ID.
     */
    public final Integer getID() {
        return persoon.getID();
    }

    /**
     * Retourneert Soort van Persoon.
     *
     * @return Soort.
     */
    public final SoortPersoonAttribuut getSoort() {
        return persoon.getSoort();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final HisPersoonAfgeleidAdministratiefModel getAfgeleidAdministratief() {
        return persoon.getPersoonAfgeleidAdministratiefHistorie().getHistorieRecord(getFormeelPeilmoment());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final HisPersoonIdentificatienummersModel getIdentificatienummers() {
        return persoon.getPersoonIdentificatienummersHistorie().getHistorieRecord(getMaterieelPeilmoment(), getFormeelPeilmoment());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final HisPersoonSamengesteldeNaamModel getSamengesteldeNaam() {
        return persoon.getPersoonSamengesteldeNaamHistorie().getHistorieRecord(getMaterieelPeilmoment(), getFormeelPeilmoment());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final HisPersoonGeboorteModel getGeboorte() {
        return persoon.getPersoonGeboorteHistorie().getHistorieRecord(getFormeelPeilmoment());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final HisPersoonGeslachtsaanduidingModel getGeslachtsaanduiding() {
        return persoon.getPersoonGeslachtsaanduidingHistorie().getHistorieRecord(getMaterieelPeilmoment(), getFormeelPeilmoment());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final HisPersoonInschrijvingModel getInschrijving() {
        return persoon.getPersoonInschrijvingHistorie().getHistorieRecord(getFormeelPeilmoment());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final HisPersoonNummerverwijzingModel getNummerverwijzing() {
        return persoon.getPersoonNummerverwijzingHistorie().getHistorieRecord(getMaterieelPeilmoment(), getFormeelPeilmoment());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final HisPersoonBijhoudingModel getBijhouding() {
        return persoon.getPersoonBijhoudingHistorie().getHistorieRecord(getMaterieelPeilmoment(), getFormeelPeilmoment());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final HisPersoonOverlijdenModel getOverlijden() {
        return persoon.getPersoonOverlijdenHistorie().getHistorieRecord(getFormeelPeilmoment());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final HisPersoonNaamgebruikModel getNaamgebruik() {
        return persoon.getPersoonNaamgebruikHistorie().getHistorieRecord(getFormeelPeilmoment());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final HisPersoonMigratieModel getMigratie() {
        return persoon.getPersoonMigratieHistorie().getHistorieRecord(getMaterieelPeilmoment(), getFormeelPeilmoment());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final HisPersoonVerblijfsrechtModel getVerblijfsrecht() {
        return persoon.getPersoonVerblijfsrechtHistorie().getHistorieRecord(getFormeelPeilmoment());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final HisPersoonUitsluitingKiesrechtModel getUitsluitingKiesrecht() {
        return persoon.getPersoonUitsluitingKiesrechtHistorie().getHistorieRecord(getFormeelPeilmoment());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final HisPersoonDeelnameEUVerkiezingenModel getDeelnameEUVerkiezingen() {
        return persoon.getPersoonDeelnameEUVerkiezingenHistorie().getHistorieRecord(getFormeelPeilmoment());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final HisPersoonPersoonskaartModel getPersoonskaart() {
        return persoon.getPersoonPersoonskaartHistorie().getHistorieRecord(getFormeelPeilmoment());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Collection<PersoonVoornaamView> getVoornamen() {
        final Set<PersoonVoornaamView> result = new HashSet<>();
        for (PersoonVoornaamHisVolledig persoonVoornaamHisVolledig : persoon.getVoornamen()) {
            final PersoonVoornaamView view = new PersoonVoornaamView(persoonVoornaamHisVolledig, getFormeelPeilmoment(), getMaterieelPeilmoment());
            if (view.heeftActueleGegevens()) {
                result.add(view);
            }
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Collection<PersoonGeslachtsnaamcomponentView> getGeslachtsnaamcomponenten() {
        final Set<PersoonGeslachtsnaamcomponentView> result = new HashSet<>();
        for (PersoonGeslachtsnaamcomponentHisVolledig persoonGeslachtsnaamcomponentHisVolledig : persoon.getGeslachtsnaamcomponenten()) {
            final PersoonGeslachtsnaamcomponentView view =
                    new PersoonGeslachtsnaamcomponentView(persoonGeslachtsnaamcomponentHisVolledig, getFormeelPeilmoment(), getMaterieelPeilmoment());
            if (view.heeftActueleGegevens()) {
                result.add(view);
            }
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Collection<PersoonVerificatieView> getVerificaties() {
        final Set<PersoonVerificatieView> result = new HashSet<>();
        for (PersoonVerificatieHisVolledig persoonVerificatieHisVolledig : persoon.getVerificaties()) {
            final PersoonVerificatieView view =
                    new PersoonVerificatieView(persoonVerificatieHisVolledig, getFormeelPeilmoment(), getMaterieelPeilmoment());
            if (view.heeftActueleGegevens()) {
                result.add(view);
            }
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Collection<PersoonNationaliteitView> getNationaliteiten() {
        final Set<PersoonNationaliteitView> result = new HashSet<>();
        for (PersoonNationaliteitHisVolledig persoonNationaliteitHisVolledig : persoon.getNationaliteiten()) {
            final PersoonNationaliteitView view =
                    new PersoonNationaliteitView(persoonNationaliteitHisVolledig, getFormeelPeilmoment(), getMaterieelPeilmoment());
            if (view.heeftActueleGegevens()) {
                result.add(view);
            }
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Collection<PersoonAdresView> getAdressen() {
        final Set<PersoonAdresView> result = new HashSet<>();
        for (PersoonAdresHisVolledig persoonAdresHisVolledig : persoon.getAdressen()) {
            final PersoonAdresView view = new PersoonAdresView(persoonAdresHisVolledig, getFormeelPeilmoment(), getMaterieelPeilmoment());
            if (view.heeftActueleGegevens()) {
                result.add(view);
            }
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Collection<PersoonIndicatieView> getIndicaties() {
        final Set<PersoonIndicatieView> result = new HashSet<>();
        for (PersoonIndicatieHisVolledig persoonIndicatieHisVolledig : persoon.getIndicaties()) {
            final PersoonIndicatieView view = new PersoonIndicatieView(persoonIndicatieHisVolledig, getFormeelPeilmoment(), getMaterieelPeilmoment());
            if (view.heeftActueleGegevens()) {
                result.add(view);
            }
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Collection<PersoonReisdocumentView> getReisdocumenten() {
        final Set<PersoonReisdocumentView> result = new HashSet<>();
        for (PersoonReisdocumentHisVolledig persoonReisdocumentHisVolledig : persoon.getReisdocumenten()) {
            final PersoonReisdocumentView view =
                    new PersoonReisdocumentView(persoonReisdocumentHisVolledig, getFormeelPeilmoment(), getMaterieelPeilmoment());
            if (view.heeftActueleGegevens()) {
                result.add(view);
            }
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Collection<BetrokkenheidView> getBetrokkenheden() {
        final Set<BetrokkenheidView> result = new HashSet<>();
        for (BetrokkenheidHisVolledig betrokkenheidHisVolledig : persoon.getBetrokkenheden()) {
            final BetrokkenheidView view;
            if (betrokkenheidHisVolledig instanceof ErkennerHisVolledig) {
                view = new ErkennerView((ErkennerHisVolledig) betrokkenheidHisVolledig, getFormeelPeilmoment(), getMaterieelPeilmoment());
            } else if (betrokkenheidHisVolledig instanceof InstemmerHisVolledig) {
                view = new InstemmerView((InstemmerHisVolledig) betrokkenheidHisVolledig, getFormeelPeilmoment(), getMaterieelPeilmoment());
            } else if (betrokkenheidHisVolledig instanceof KindHisVolledig) {
                view = new KindView((KindHisVolledig) betrokkenheidHisVolledig, getFormeelPeilmoment(), getMaterieelPeilmoment());
            } else if (betrokkenheidHisVolledig instanceof NaamgeverHisVolledig) {
                view = new NaamgeverView((NaamgeverHisVolledig) betrokkenheidHisVolledig, getFormeelPeilmoment(), getMaterieelPeilmoment());
            } else if (betrokkenheidHisVolledig instanceof OuderHisVolledig) {
                view = new OuderView((OuderHisVolledig) betrokkenheidHisVolledig, getFormeelPeilmoment(), getMaterieelPeilmoment());
            } else if (betrokkenheidHisVolledig instanceof PartnerHisVolledig) {
                view = new PartnerView((PartnerHisVolledig) betrokkenheidHisVolledig, getFormeelPeilmoment(), getMaterieelPeilmoment());
            } else {
                throw new IllegalArgumentException("Onbekend type Betrokkenheid.");
            }
            if (view.heeftActueleGegevens()) {
                result.add(view);
            }
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Collection<PersoonOnderzoekView> getOnderzoeken() {
        final Set<PersoonOnderzoekView> result = new HashSet<>();
        for (PersoonOnderzoekHisVolledig persoonOnderzoekHisVolledig : persoon.getOnderzoeken()) {
            final PersoonOnderzoekView view = new PersoonOnderzoekView(persoonOnderzoekHisVolledig, getFormeelPeilmoment(), getMaterieelPeilmoment());
            if (view.heeftActueleGegevens()) {
                result.add(view);
            }
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Collection<PersoonVerstrekkingsbeperkingView> getVerstrekkingsbeperkingen() {
        final Set<PersoonVerstrekkingsbeperkingView> result = new HashSet<>();
        for (PersoonVerstrekkingsbeperkingHisVolledig persoonVerstrekkingsbeperkingHisVolledig : persoon.getVerstrekkingsbeperkingen()) {
            final PersoonVerstrekkingsbeperkingView view =
                    new PersoonVerstrekkingsbeperkingView(persoonVerstrekkingsbeperkingHisVolledig, getFormeelPeilmoment(), getMaterieelPeilmoment());
            if (view.heeftActueleGegevens()) {
                result.add(view);
            }
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Collection<PersoonAfnemerindicatieView> getAfnemerindicaties() {
        final Set<PersoonAfnemerindicatieView> result = new HashSet<>();
        for (PersoonAfnemerindicatieHisVolledig persoonAfnemerindicatieHisVolledig : persoon.getAfnemerindicaties()) {
            final PersoonAfnemerindicatieView view =
                    new PersoonAfnemerindicatieView(persoonAfnemerindicatieHisVolledig, getFormeelPeilmoment(), getMaterieelPeilmoment());
            if (view.heeftActueleGegevens()) {
                result.add(view);
            }
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final PersoonIndicatieView getIndicatieDerdeHeeftGezag() {
        PersoonIndicatieView indicatieDerdeHeeftGezag = null;
        for (PersoonIndicatieView persoonIndicatie : this.getIndicaties()) {
            if (persoonIndicatie.getSoort().getWaarde() == SoortIndicatie.INDICATIE_DERDE_HEEFT_GEZAG) {
                indicatieDerdeHeeftGezag = persoonIndicatie;
            }
        }
        return indicatieDerdeHeeftGezag;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final PersoonIndicatieView getIndicatieOnderCuratele() {
        PersoonIndicatieView indicatieOnderCuratele = null;
        for (PersoonIndicatieView persoonIndicatie : this.getIndicaties()) {
            if (persoonIndicatie.getSoort().getWaarde() == SoortIndicatie.INDICATIE_ONDER_CURATELE) {
                indicatieOnderCuratele = persoonIndicatie;
            }
        }
        return indicatieOnderCuratele;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final PersoonIndicatieView getIndicatieVolledigeVerstrekkingsbeperking() {
        PersoonIndicatieView indicatieVolledigeVerstrekkingsbeperking = null;
        for (PersoonIndicatieView persoonIndicatie : this.getIndicaties()) {
            if (persoonIndicatie.getSoort().getWaarde() == SoortIndicatie.INDICATIE_VOLLEDIGE_VERSTREKKINGSBEPERKING) {
                indicatieVolledigeVerstrekkingsbeperking = persoonIndicatie;
            }
        }
        return indicatieVolledigeVerstrekkingsbeperking;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final PersoonIndicatieView getIndicatieVastgesteldNietNederlander() {
        PersoonIndicatieView indicatieVastgesteldNietNederlander = null;
        for (PersoonIndicatieView persoonIndicatie : this.getIndicaties()) {
            if (persoonIndicatie.getSoort().getWaarde() == SoortIndicatie.INDICATIE_VASTGESTELD_NIET_NEDERLANDER) {
                indicatieVastgesteldNietNederlander = persoonIndicatie;
            }
        }
        return indicatieVastgesteldNietNederlander;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final PersoonIndicatieView getIndicatieBehandeldAlsNederlander() {
        PersoonIndicatieView indicatieBehandeldAlsNederlander = null;
        for (PersoonIndicatieView persoonIndicatie : this.getIndicaties()) {
            if (persoonIndicatie.getSoort().getWaarde() == SoortIndicatie.INDICATIE_BEHANDELD_ALS_NEDERLANDER) {
                indicatieBehandeldAlsNederlander = persoonIndicatie;
            }
        }
        return indicatieBehandeldAlsNederlander;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final PersoonIndicatieView getIndicatieSignaleringMetBetrekkingTotVerstrekkenReisdocument() {
        PersoonIndicatieView indicatieSignaleringMetBetrekkingTotVerstrekkenReisdocument = null;
        for (PersoonIndicatieView persoonIndicatie : this.getIndicaties()) {
            if (persoonIndicatie.getSoort().getWaarde() == SoortIndicatie.INDICATIE_SIGNALERING_MET_BETREKKING_TOT_VERSTREKKEN_REISDOCUMENT) {
                indicatieSignaleringMetBetrekkingTotVerstrekkenReisdocument = persoonIndicatie;
            }
        }
        return indicatieSignaleringMetBetrekkingTotVerstrekkenReisdocument;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final PersoonIndicatieView getIndicatieStaatloos() {
        PersoonIndicatieView indicatieStaatloos = null;
        for (PersoonIndicatieView persoonIndicatie : this.getIndicaties()) {
            if (persoonIndicatie.getSoort().getWaarde() == SoortIndicatie.INDICATIE_STAATLOOS) {
                indicatieStaatloos = persoonIndicatie;
            }
        }
        return indicatieStaatloos;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final PersoonIndicatieView getIndicatieBijzondereVerblijfsrechtelijkePositie() {
        PersoonIndicatieView indicatieBijzondereVerblijfsrechtelijkePositie = null;
        for (PersoonIndicatieView persoonIndicatie : this.getIndicaties()) {
            if (persoonIndicatie.getSoort().getWaarde() == SoortIndicatie.INDICATIE_BIJZONDERE_VERBLIJFSRECHTELIJKE_POSITIE) {
                indicatieBijzondereVerblijfsrechtelijkePositie = persoonIndicatie;
            }
        }
        return indicatieBijzondereVerblijfsrechtelijkePositie;
    }

    /**
     * Retourneert het Element behorende bij dit objecttype.
     *
     * @return Element enum instantie behorende bij dit objecttype.
     */
    public final ElementEnum getElementIdentificatie() {
        return ElementEnum.PERSOON;
    }

}
