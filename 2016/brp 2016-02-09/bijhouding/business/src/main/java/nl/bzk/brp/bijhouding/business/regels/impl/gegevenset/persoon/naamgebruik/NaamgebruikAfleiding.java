/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.naamgebruik;

import java.util.Set;

import nl.bzk.brp.bijhouding.business.regels.AbstractAfleidingsregel;
import nl.bzk.brp.bijhouding.business.regels.AfleidingResultaat;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.GeslachtsnaamstamAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaNeeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.ScheidingstekenAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.VoornamenAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.VoorvoegselAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.AdellijkeTitel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.AdellijkeTitelAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Naamgebruik;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.NaamgebruikAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Predicaat;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PredicaatAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.hisvolledig.impl.kern.HuwelijkGeregistreerdPartnerschapHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PartnerHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.logisch.kern.PersoonSamengesteldeNaamGroep;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonNaamgebruikModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonSamengesteldeNaamModel;

/**
 * VR00009a: Afgeleide registratie Naamgebruik.
 */
public class NaamgebruikAfleiding extends AbstractAfleidingsregel<PersoonHisVolledig> {

    private static final Logger LOGGER = LoggerFactory.getLogger();
    private static final Naamgebruik DEFAULT_GEBRUIK_GESLACHTSNAAM = Naamgebruik.EIGEN;
    private static final String LEGE_STRING = "";

    /**
     * Forwarding constructor.
     *
     * @param persoon de persoon
     * @param actie   actie
     */
    public NaamgebruikAfleiding(final PersoonHisVolledig persoon, final ActieModel actie) {
        super(persoon, actie);
    }

    @Override
    public final Regel getRegel() {
        return Regel.VR00009a;
    }

    @Override
    public AfleidingResultaat leidAf() {
        final PersoonHisVolledig persoon = getModel();
        /**
         * Controle of de naamebruik groep afgeleid dient te worden. Deze afleiding dient plaats te vinden als
         * de naamgebruik groep van de persoon nog leeg is, of als de naamgebruik groep wel gevuld is en
         * niet overruled is door een actie in dezelfde administratieve handeling als de huidige actie.
         */
        if (isNaamgebruikNogLeeg(persoon) || !isNaamgebruikOverruledInBerichtActie(persoon)) {
            final HisPersoonSamengesteldeNaamModel samengesteldeNaam = persoon.getPersoonSamengesteldeNaamHistorie().getActueleRecord();

            final VoornamenAttribuut voornamen = samengesteldeNaam.getVoornamen();

            Predicaat predicaat = null;
            if (samengesteldeNaam.getPredicaat() != null) {
                predicaat = samengesteldeNaam.getPredicaat().getWaarde();
            }

            AdellijkeTitel adellijkeTitel = null;
            if (samengesteldeNaam.getAdellijkeTitel() != null) {
                adellijkeTitel = samengesteldeNaam.getAdellijkeTitel().getWaarde();
            }

            final PersoonHisVolledig partner = haalOpPartner(persoon);
            PersoonSamengesteldeNaamGroep samengesteldeNaamPartner = null;
            if (partner != null) {
                samengesteldeNaamPartner = partner.getPersoonSamengesteldeNaamHistorie().getActueleRecord();
            }

            final HisPersoonNaamgebruikModel naamgebruikHuidig = persoon.getPersoonNaamgebruikHistorie().getActueleRecord();
            Naamgebruik naamgebruik = null;
            if (naamgebruikHuidig != null && naamgebruikHuidig.getNaamgebruik() != null) {
                naamgebruik = naamgebruikHuidig.getNaamgebruik().getWaarde();
            }

            valideerGeenPartnerWelPartnerNaamgebruik(partner, naamgebruik, persoon.getID());

            leidAfNaamgebruik(naamgebruik, voornamen, predicaat, adellijkeTitel, samengesteldeNaam, samengesteldeNaamPartner);
        }

        return GEEN_VERDERE_AFLEIDINGEN;
    }

    /**
     * Controleert of naamgebruik groep van persoon nog leeg is.
     *
     * @param persoon the persoon
     * @return the boolean
     */
    private boolean isNaamgebruikNogLeeg(final PersoonHisVolledig persoon) {
        return persoon.getPersoonNaamgebruikHistorie().getActueleRecord() == null;
    }

    /**
     * Controleert of naamgebruik groep overruled is door een naamgebruik actie in bericht.
     *
     * @param persoon de persoon
     * @return de boolean die aangeeft of naamgebruik overrruled is
     */
    private Boolean isNaamgebruikOverruledInBerichtActie(final PersoonHisVolledig persoon) {
        final HisPersoonNaamgebruikModel actueleNaamgebruik = persoon.getPersoonNaamgebruikHistorie().getActueleRecord();

        return JaNeeAttribuut.NEE.equals(actueleNaamgebruik.getIndicatieNaamgebruikAfgeleid());
    }

    /**
     * Valideert of er sprake is van naamgebruik waarbij een partner vereist is, maar ontbreekt.
     * In dit geval wordt er een foutmelding getoond.
     *
     * @param partner     de partner
     * @param naamgebruik de wijze van naamgebruik
     * @param persoonId   de persoon id
     */
    private void valideerGeenPartnerWelPartnerNaamgebruik(final PersoonHisVolledig partner,
                                                          final Naamgebruik naamgebruik,
                                                          final Integer persoonId)
    {
        if (partner == null && naamgebruik != null && (!naamgebruik.equals(Naamgebruik.EIGEN))) {
            LOGGER.error("Persoon id {} heeft geen partner maar wel naamgebruik met partner {}", persoonId, naamgebruik);
        }
    }

    /**
     * Vult velden op basis van naamgebruik. Op basis hiervan wordt voorvoegsel, scheidingsteken en
     * geslachtsnaam gevuld.
     *
     * @param naamgebruik              naamgebruik
     * @param voornamen                voornamen
     * @param predikaat                predikaat
     * @param adellijkeTitel           adellijkeTitel
     * @param samengesteldeNaam        samengesteldeNaam
     * @param samengesteldeNaamPartner samengesteldeNaam van partner
     */
    private void leidAfNaamgebruik(final Naamgebruik naamgebruik,
                                   final VoornamenAttribuut voornamen,
                                   final Predicaat predikaat,
                                   final AdellijkeTitel adellijkeTitel,
                                   final HisPersoonSamengesteldeNaamModel samengesteldeNaam,
                                   final PersoonSamengesteldeNaamGroep samengesteldeNaamPartner)
    {
        Naamgebruik gebruikGeslachtsnaam = naamgebruik;

        /**
         * Logisch gezien is deze check wellicht niet nodig, maar beide objecten kunnen in theorie leeg zijn. De
         * default zorgt ervoor dat er geen NullPointerExceptions optreden.
         */
        if (gebruikGeslachtsnaam == null || samengesteldeNaamPartner == null) {
            gebruikGeslachtsnaam = DEFAULT_GEBRUIK_GESLACHTSNAAM;
        }

        VoorvoegselAttribuut voorvoegsel = null;
        ScheidingstekenAttribuut scheidingsteken = null;
        GeslachtsnaamstamAttribuut geslachtsnaam = null;

        final String verbindingsTeken = "-";
        switch (gebruikGeslachtsnaam) {
            case PARTNER:
                voorvoegsel = samengesteldeNaamPartner.getVoorvoegsel();
                scheidingsteken = samengesteldeNaamPartner.getScheidingsteken();
                geslachtsnaam = samengesteldeNaamPartner.getGeslachtsnaamstam();
                break;
            case EIGEN:
                voorvoegsel = samengesteldeNaam.getVoorvoegsel();
                scheidingsteken = samengesteldeNaam.getScheidingsteken();
                geslachtsnaam = samengesteldeNaam.getGeslachtsnaamstam();
                break;
            case PARTNER_EIGEN:
                voorvoegsel = samengesteldeNaamPartner.getVoorvoegsel();
                scheidingsteken = samengesteldeNaamPartner.getScheidingsteken();
                geslachtsnaam = new GeslachtsnaamstamAttribuut(samengesteldeNaamPartner.getGeslachtsnaamstam()
                        + verbindingsTeken
                        + maakLeegVoorvoegselBijOntbreken(samengesteldeNaam.getVoorvoegsel())
                        + samengesteldeNaam.getScheidingsteken()
                        + samengesteldeNaam.getGeslachtsnaamstam());
                break;
            case EIGEN_PARTNER:
                voorvoegsel = samengesteldeNaam.getVoorvoegsel();
                scheidingsteken = samengesteldeNaam.getScheidingsteken();
                geslachtsnaam = new GeslachtsnaamstamAttribuut(samengesteldeNaam.getGeslachtsnaamstam()
                        + verbindingsTeken
                        + maakLeegVoorvoegselBijOntbreken(samengesteldeNaamPartner.getVoorvoegsel())
                        + samengesteldeNaamPartner.getScheidingsteken()
                        + samengesteldeNaamPartner.getGeslachtsnaamstam());
                break;
            default:
                break;
        }

        final NaamgebruikAttribuut naamgebruikAttribuut = new NaamgebruikAttribuut(gebruikGeslachtsnaam);
        PredicaatAttribuut predicaatAttribuut = null;
        AdellijkeTitelAttribuut adellijkeTitelAttribuut = null;

        if (predikaat != null) {
            predicaatAttribuut = new PredicaatAttribuut(predikaat);
        }
        if (adellijkeTitel != null) {
            adellijkeTitelAttribuut = new AdellijkeTitelAttribuut(adellijkeTitel);
        }

        final HisPersoonNaamgebruikModel hisPersoonNaamgebruikModel =
                new HisPersoonNaamgebruikModel(getModel(),
                        naamgebruikAttribuut,
                        JaNeeAttribuut.JA,
                        predicaatAttribuut,
                        voornamen,
                        adellijkeTitelAttribuut,
                        voorvoegsel,
                        scheidingsteken,
                        geslachtsnaam,
                        getActie());
        getModel().getPersoonNaamgebruikHistorie().voegToe(hisPersoonNaamgebruikModel);
    }

    /**
     * Maakt een leeg voorvoegsel wanneer voorvoegsel ontbreekt voor invulling afgeleide geslachtsnaam.
     *
     * @param voorvoegsel voorvoegsel
     * @return voorvoegsel
     */
    private VoorvoegselAttribuut maakLeegVoorvoegselBijOntbreken(final VoorvoegselAttribuut voorvoegsel) {
        final VoorvoegselAttribuut correctVoorvoegsel;
        if (voorvoegsel != null) {
            correctVoorvoegsel = voorvoegsel;
        } else {
            correctVoorvoegsel = new VoorvoegselAttribuut(LEGE_STRING);
        }
        return correctVoorvoegsel;
    }

    /**
     * Haalt meest recente partner op.
     *
     * @param persoon persoon van wie partner wordt opgehaald
     * @return partner
     */
    PersoonHisVolledig haalOpPartner(final PersoonHisVolledig persoon) {

        final PersoonHisVolledigImpl persoonHisVolledig = (PersoonHisVolledigImpl) persoon;

        PersoonHisVolledig meestRecentePartner = null;
        DatumAttribuut datumMeestRecenteHuwelijk = null;

        final Set<PartnerHisVolledigImpl> partnerBetrokkenheden = persoonHisVolledig.getPartnerBetrokkenheden();

        for (final PartnerHisVolledigImpl partnerHisVolledig : partnerBetrokkenheden) {
            final HuwelijkGeregistreerdPartnerschapHisVolledigImpl huwelijk =
                    (HuwelijkGeregistreerdPartnerschapHisVolledigImpl) partnerHisVolledig.getRelatie();

            if (huwelijk.getRelatieHistorie().getActueleRecord() != null) {
                final DatumEvtDeelsOnbekendAttribuut datumAanvangHuwelijk = huwelijk.getRelatieHistorie().getActueleRecord().getDatumAanvang();

                /**
                 * Neemt persoon van meest recente huwelijk als partner.
                 */
                if (datumMeestRecenteHuwelijk == null || datumMeestRecenteHuwelijk.voorDatumSoepel(datumAanvangHuwelijk)) {
                    datumMeestRecenteHuwelijk = new DatumAttribuut(datumAanvangHuwelijk);
                    meestRecentePartner = huwelijk.geefPartnerVan(persoonHisVolledig).getPersoon();
                }
            }
        }

        return meestRecentePartner;
    }
}
