/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.handlers.bijhouding;

import javax.inject.Inject;

import nl.bzk.brp.business.dto.BerichtContext;
import nl.bzk.brp.business.dto.BerichtVerwerkingsResultaat;
import nl.bzk.brp.business.dto.bijhouding.AbstractBijhoudingsBericht;
import nl.bzk.brp.business.handlers.AbstractBerichtVerwerkingsStap;
import nl.bzk.brp.constanten.BrpConstanten;
import nl.bzk.brp.dataaccess.exceptie.OnbekendeReferentieExceptie;
import nl.bzk.brp.dataaccess.repository.ReferentieDataRepository;
import nl.bzk.brp.model.RootObject;
import nl.bzk.brp.model.attribuuttype.AdellijkeTitelCode;
import nl.bzk.brp.model.attribuuttype.Gemeentecode;
import nl.bzk.brp.model.attribuuttype.Landcode;
import nl.bzk.brp.model.attribuuttype.Nationaliteitcode;
import nl.bzk.brp.model.attribuuttype.PlaatsCode;
import nl.bzk.brp.model.attribuuttype.PredikaatCode;
import nl.bzk.brp.model.attribuuttype.RedenVerkrijgingCode;
import nl.bzk.brp.model.attribuuttype.RedenVerliesNaam;
import nl.bzk.brp.model.attribuuttype.RedenWijzigingAdresCode;
import nl.bzk.brp.model.basis.Identificeerbaar;
import nl.bzk.brp.model.groep.bericht.PersoonAdresStandaardGroepBericht;
import nl.bzk.brp.model.groep.bericht.PersoonGeboorteGroepBericht;
import nl.bzk.brp.model.groep.bericht.PersoonGeslachtsnaamcomponentStandaardGroepBericht;
import nl.bzk.brp.model.groep.bericht.PersoonNationaliteitStandaardGroepBericht;
import nl.bzk.brp.model.groep.bericht.RelatieStandaardGroepBericht;
import nl.bzk.brp.model.objecttype.bericht.ActieBericht;
import nl.bzk.brp.model.objecttype.bericht.BetrokkenheidBericht;
import nl.bzk.brp.model.objecttype.bericht.PersoonAdresBericht;
import nl.bzk.brp.model.objecttype.bericht.PersoonBericht;
import nl.bzk.brp.model.objecttype.bericht.PersoonGeslachtsnaamcomponentBericht;
import nl.bzk.brp.model.objecttype.bericht.PersoonNationaliteitBericht;
import nl.bzk.brp.model.objecttype.bericht.RelatieBericht;
import nl.bzk.brp.model.objecttype.logisch.Actie;
import nl.bzk.brp.model.objecttype.operationeel.statisch.AdellijkeTitel;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Land;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Nationaliteit;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Partij;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Plaats;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Predikaat;
import nl.bzk.brp.model.objecttype.operationeel.statisch.RedenVerkrijgingNLNationaliteit;
import nl.bzk.brp.model.objecttype.operationeel.statisch.RedenVerliesNLNationaliteit;
import nl.bzk.brp.model.objecttype.operationeel.statisch.RedenWijzigingAdres;
import nl.bzk.brp.model.objecttype.operationeel.statisch.SoortActie;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Soortmelding;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.model.validatie.MeldingCode;
import nl.bzk.brp.util.AttribuutTypeUtil;
import org.apache.commons.collections.CollectionUtils;


/**
 * Stap verrijkt stamgegevens in het bericht zoals Land, Partij en Gemeente.
 * Codes worden omgezet naar stamgegeven entities.
 */
public class BerichtVerrijkingsStap
        extends AbstractBerichtVerwerkingsStap<AbstractBijhoudingsBericht, BerichtVerwerkingsResultaat>
{

    @Inject
    private ReferentieDataRepository referentieRepository;

    @Override
    public boolean voerVerwerkingsStapUitVoorBericht(final AbstractBijhoudingsBericht bericht,
                                                     final BerichtContext context,
                                                     final BerichtVerwerkingsResultaat resultaat)
    {
        for (Actie actie : bericht.getBrpActies()) {
            ((ActieBericht) actie).setPartij(context.getPartij());
            for (RootObject rootObject : actie.getRootObjecten()) {
                if (rootObject instanceof PersoonBericht) {
                    verrijkActieGegevens((PersoonBericht) rootObject, actie.getSoort(), resultaat);
                } else if (rootObject instanceof RelatieBericht) {
                    verrijkActieGegevens((RelatieBericht) rootObject, actie.getSoort(), resultaat);
                } else {
                    throw new UnsupportedOperationException("RootOjbect wordt niet ondersteund door "
                        + "BerichtVerrijkingsStap: " + rootObject.getClass().getSimpleName());
                }
            }
        }

        if (CollectionUtils.isNotEmpty(resultaat.getMeldingen())) {
            return STOP_VERWERKING;
        } else {
            return DOORGAAN_MET_VERWERKING;
        }
    }

    /**
     * Verrijk de stamgegevens van de opgegeven relatie.
     *
     * @param relatie De te verrijken relatie.
     * @param soort Soort actie die uitgevoerd wordt.
     * @param resultaat Resultaat met lijst om eventuele meldingen die door de stap worden gemaakt terug te geven
     *            aan de client.
     */
    private void verrijkActieGegevens(final RelatieBericht relatie, final SoortActie soort,
        final BerichtVerwerkingsResultaat resultaat)
    {
        // Verrijk gegevens relatie
        if (relatie.getGegevens() != null) {
            final RelatieStandaardGroepBericht relatieStandaardGroep = relatie.getGegevens();
            // Land aanvang
            if (SoortActie.HUWELIJK == soort) {
                // SRPUC04201: landAanvang = Nederland
                relatieStandaardGroep.setLandAanvang(vindLandOpCode(BrpConstanten.NL_LAND_CODE, null, null));
            } else if (AttribuutTypeUtil.isNotBlank(relatieStandaardGroep.getLandAanvangCode())) {
                relatieStandaardGroep.setLandAanvang(vindLandOpCode(relatieStandaardGroep.getLandAanvangCode(),
                        relatieStandaardGroep, resultaat));
            }
            // LandEinde
            if (AttribuutTypeUtil.isNotBlank(relatieStandaardGroep.getLandEindeCode())) {
                relatieStandaardGroep.setLandEinde(vindLandOpCode(relatieStandaardGroep.getLandEindeCode(),
                        relatieStandaardGroep, resultaat));
            }
            // Gemeente aanvang
            if (AttribuutTypeUtil.isNotBlank(relatieStandaardGroep.getGemeenteAanvangCode())) {
                relatieStandaardGroep.setGemeenteAanvang(vindGemeenteOpCode(
                        relatieStandaardGroep.getGemeenteAanvangCode(), relatieStandaardGroep, resultaat));
            }
            // Gemeente einde
            if (AttribuutTypeUtil.isNotBlank(relatieStandaardGroep.getGemeenteEindeCode())) {
                relatieStandaardGroep.setGemeenteEinde(vindGemeenteOpCode(relatieStandaardGroep.getGemeenteEindeCode(),
                        relatieStandaardGroep, resultaat));
            }
            // Woonplaats aanvang
            if (AttribuutTypeUtil.isNotBlank(relatieStandaardGroep.getWoonPlaatsAanvangCode())) {
                relatieStandaardGroep.setWoonPlaatsAanvang(vindWoonplaatsOpCode(
                        relatieStandaardGroep.getWoonPlaatsAanvangCode(), relatieStandaardGroep, resultaat));
            }
            // Woonplaats einde
            if (AttribuutTypeUtil.isNotBlank(relatieStandaardGroep.getWoonPlaatsEindeCode())) {
                relatieStandaardGroep.setWoonPlaatsEinde(vindWoonplaatsOpCode(
                        relatieStandaardGroep.getWoonPlaatsEindeCode(), relatieStandaardGroep, resultaat));
            }
        }

        // Verrijk geboorte gegevens kind
        final BetrokkenheidBericht kindBetrokkenheid = relatie.getKindBetrokkenheid();
        if (kindBetrokkenheid != null && kindBetrokkenheid.getBetrokkene() != null) {
            final PersoonBericht kind = kindBetrokkenheid.getBetrokkene();
            if (kind.getGeboorte() != null) {
                final PersoonGeboorteGroepBericht geboorte = kind.getGeboorte();
                // Gemeente geboorte
                if (AttribuutTypeUtil.isNotBlank(geboorte.getGemeenteGeboorteCode())) {
                    geboorte.setGemeenteGeboorte(vindGemeenteOpCode(geboorte.getGemeenteGeboorteCode(), geboorte,
                            resultaat));
                }
                // Land geboorte
                if (AttribuutTypeUtil.isNotBlank(geboorte.getLandGeboorteCode())) {
                    geboorte.setLandGeboorte(vindLandOpCode(geboorte.getLandGeboorteCode(), geboorte, resultaat));
                }
                // Woonplaats geboorte
                if (AttribuutTypeUtil.isNotBlank(geboorte.getWoonplaatsGeboorteCode())) {
                    geboorte.setWoonplaatsGeboorte(vindWoonplaatsOpCode(geboorte.getWoonplaatsGeboorteCode(), geboorte,
                            resultaat));
                }
            }

            if (CollectionUtils.isNotEmpty(kind.getGeslachtsnaamcomponenten())) {
                for (PersoonGeslachtsnaamcomponentBericht geslachtsnaamcomponent : kind.getGeslachtsnaamcomponenten()) {
                    final PersoonGeslachtsnaamcomponentStandaardGroepBericht geslachtsnaamcomponentGegevens =
                        geslachtsnaamcomponent.getGegevens();

                    // Predikaat
                    if (AttribuutTypeUtil.isNotBlank(geslachtsnaamcomponentGegevens.getPredikaatCode())) {
                        geslachtsnaamcomponentGegevens.setPredikaat(vindPredikaatOpCode(
                                geslachtsnaamcomponentGegevens.getPredikaatCode(), geslachtsnaamcomponentGegevens,
                                resultaat));
                    }

                    // Adelijke Titel geboorte
                    if (AttribuutTypeUtil.isNotBlank(geslachtsnaamcomponentGegevens.getAdellijkeTitelCode())) {
                        geslachtsnaamcomponentGegevens.setAdellijkeTitel(vindAdellijkeTitelOpCode(
                                geslachtsnaamcomponentGegevens.getAdellijkeTitelCode(), geslachtsnaamcomponent,
                                resultaat));
                    }
                }
            }
        }
    }

    /**
     * Verrijk de stamgegevens van de opgegeven persoon.
     *
     * @param persoon De te verrijken persoon.
     * @param soort Soort actie die uitgevoerd wordt.
     * @param resultaat Resultaat met lijst om eventuele meldingen die door de stap worden gemaakt terug te geven
     *            aan de client.
     */
    private void verrijkActieGegevens(final PersoonBericht persoon, final SoortActie soort,
        final BerichtVerwerkingsResultaat resultaat)
    {
        if (SoortActie.VERHUIZING == soort || SoortActie.CORRECTIE_ADRES_NL == soort) {
            for (PersoonAdresBericht persoonAdresBericht : persoon.getAdressen()) {
                if (persoonAdresBericht.getGegevens() != null) {
                    final PersoonAdresStandaardGroepBericht adresGegevens = persoonAdresBericht.getGegevens();
                    if (AttribuutTypeUtil.isNotBlank(adresGegevens.getRedenWijzigingAdresCode())) {
                        adresGegevens.setRedenwijziging(vindRedenWijzingAdresOpCode(
                                adresGegevens.getRedenWijzigingAdresCode(), adresGegevens, resultaat));
                    }
                    if (AttribuutTypeUtil.isNotBlank(adresGegevens.getCode())) {
                        adresGegevens.setWoonplaats(vindWoonplaatsOpCode(adresGegevens.getCode(), adresGegevens,
                                resultaat));
                    }
                    if (AttribuutTypeUtil.isNotBlank(adresGegevens.getGemeentecode())) {
                        adresGegevens.setGemeente(vindGemeenteOpCode(adresGegevens.getGemeentecode(), adresGegevens,
                                resultaat));
                    }
                    if (AttribuutTypeUtil.isNotBlank(adresGegevens.getLandcode())) {
                        adresGegevens.setLand(vindLandOpCode(adresGegevens.getLandcode(), adresGegevens, resultaat));
                    } else {
                        // TODO tijdelijke fix, als er geen land opgegeven is dan tijdelijk land op NL zetten
                        adresGegevens.setLand(vindLandOpCode(BrpConstanten.NL_LAND_CODE, adresGegevens, resultaat));
                    }
                }
            }
        } else if (SoortActie.REGISTRATIE_NATIONALITEIT == soort) {
            if (CollectionUtils.isNotEmpty(persoon.getNationaliteiten())) {
                for (PersoonNationaliteitBericht persoonNationaliteitBericht : persoon.getNationaliteiten()) {
                    if (AttribuutTypeUtil.isNotBlank(persoonNationaliteitBericht.getNationaliteitcode())) {
                        persoonNationaliteitBericht.setNationaliteit(vindNationaliteitOpCode(
                                persoonNationaliteitBericht.getNationaliteitcode(), persoonNationaliteitBericht,
                                resultaat));
                    }
                    if (persoonNationaliteitBericht.getGegevens() != null) {
                        PersoonNationaliteitStandaardGroepBericht nationGegevens =
                            persoonNationaliteitBericht.getGegevens();
                        if (AttribuutTypeUtil.isNotBlank(nationGegevens.getRedenVerkregenNlNationaliteitCode())) {
                            nationGegevens.setRedenVerkregenNlNationaliteit(vindRedenVerkregenNlNationaliteitOpCode(
                                    nationGegevens.getRedenVerkregenNlNationaliteitCode(), nationGegevens, resultaat));
                        }
                        if (AttribuutTypeUtil.isNotBlank(nationGegevens.getRedenVerliesNlNationaliteitNaam())) {
                            nationGegevens.setRedenVerliesNlNationaliteit(vindRedenVerliesNLNationaliteitOpNaam(
                                    nationGegevens.getRedenVerliesNlNationaliteitNaam(), nationGegevens, resultaat));
                        }
                    }
                }
            }
        }
    }

    /**
     * Haal RedenVerliesNLNationaliteit op en als het niet bestaat voeg melding toe aan de bericht resultaat.
     *
     * @param redenVerliesNaam redenVerliesNaam
     * @param groep het groep dat een zendendId bevat
     * @param resultaat Resultaat met lijst om eventuele meldingen die door de stap worden gemaakt terug te geven
     *            aan de client.
     * @return RedenVerliesNLNationaliteit of null
     */
    private RedenVerliesNLNationaliteit vindRedenVerliesNLNationaliteitOpNaam(final RedenVerliesNaam redenVerliesNaam,
        final Identificeerbaar groep, final BerichtVerwerkingsResultaat resultaat)
    {
        try {
            return referentieRepository.vindRedenVerliesNLNationaliteitOpNaam(redenVerliesNaam);
        } catch (OnbekendeReferentieExceptie ore) {
            final Melding melding =
                new Melding(Soortmelding.FOUT, MeldingCode.BRAL1022, String.format(
                        MeldingCode.BRAL1022.getOmschrijving(), redenVerliesNaam.getWaarde()), groep,
                        "redenVerliesNaam");
            resultaat.voegMeldingToe(melding);
            return null;
        }
    }

    /**
     * Haal RedenVerkrijgingNLNationaliteit op en als het niet bestaat voeg melding toe aan de bericht resultaat.
     *
     * @param redenVerkrijgingCode redenVerkrijgingCode
     * @param groep het groep dat een zendendId bevat
     * @param resultaat Resultaat met lijst om eventuele meldingen die door de stap worden gemaakt terug te geven
     *            aan de client.
     * @return RedenVerkrijgingNLNationaliteit of null
     */
    private RedenVerkrijgingNLNationaliteit vindRedenVerkregenNlNationaliteitOpCode(
            final RedenVerkrijgingCode redenVerkrijgingCode, final Identificeerbaar groep,
            final BerichtVerwerkingsResultaat resultaat)
    {
        try {
            return referentieRepository.vindRedenVerkregenNlNationaliteitOpCode(redenVerkrijgingCode);
        } catch (OnbekendeReferentieExceptie ore) {
            final Melding melding =
                new Melding(Soortmelding.FOUT, MeldingCode.BRAL1021, String.format(
                        MeldingCode.BRAL1021.getOmschrijving(), redenVerkrijgingCode.getWaarde()), groep,
                        "redenVerkrijgingNaam");
            resultaat.voegMeldingToe(melding);
            return null;
        }
    }

    /**
     * Haal Nationaliteit op en als het niet bestaat voeg melding toe aan de bericht resultaat.
     *
     * @param nationaliteitcode nationaliteitcode
     * @param groep het groep dat een zendendId bevat
     * @param resultaat Resultaat met lijst om eventuele meldingen die door de stap worden gemaakt terug te geven
     *            aan de client.
     * @return RedenWijzigingAdres of null
     */
    private Nationaliteit vindNationaliteitOpCode(final Nationaliteitcode nationaliteitcode,
            final Identificeerbaar groep, final BerichtVerwerkingsResultaat resultaat)
    {
        try {
            return referentieRepository.vindNationaliteitOpCode(nationaliteitcode);
        } catch (OnbekendeReferentieExceptie ore) {
            final Melding melding =
                new Melding(Soortmelding.FOUT, MeldingCode.BRAL1017, String.format(
                        MeldingCode.BRAL1017.getOmschrijving(), nationaliteitcode.getWaarde()), groep,
                        "nationaliteitNaam");
            resultaat.voegMeldingToe(melding);
            return null;
        }
    }

    /**
     * Haal RedenWijzigingAdres op en als het niet bestaat voeg melding toe aan de bericht resultaat.
     *
     * @param redenWijzigingAdresCode redenWijzigingAdresCode
     * @param groep het groep dat een zendendId bevat
     * @param resultaat Resultaat met lijst om eventuele meldingen die door de stap worden gemaakt terug te geven
     *            aan de client.
     * @return RedenWijzigingAdres of null
     */
    private RedenWijzigingAdres vindRedenWijzingAdresOpCode(final RedenWijzigingAdresCode redenWijzigingAdresCode,
            final Identificeerbaar groep, final BerichtVerwerkingsResultaat resultaat)
    {
        try {
            return referentieRepository.vindRedenWijzingAdresOpCode(redenWijzigingAdresCode);
        } catch (OnbekendeReferentieExceptie ore) {
            final Melding melding =
                new Melding(Soortmelding.FOUT, MeldingCode.BRAL1007, String.format(
                        MeldingCode.BRAL1007.getOmschrijving(), redenWijzigingAdresCode.getWaarde()), groep,
                        "redenWijzigingCode");
            resultaat.voegMeldingToe(melding);
            return null;
        }
    }

    /**
     * Haal AdellijkeTitel op en als het niet bestaat voeg melding toe aan de bericht resultaat.
     *
     * @param adellijkeTitelCode adellijkeTitelCode
     * @param groep het groep dat een zendendId bevat
     * @param resultaat Resultaat met lijst om eventuele meldingen die door de stap worden gemaakt terug te geven
     *            aan de client.
     * @return AdellijkeTitel of null
     */
    private AdellijkeTitel vindAdellijkeTitelOpCode(final AdellijkeTitelCode adellijkeTitelCode,
            final Identificeerbaar groep, final BerichtVerwerkingsResultaat resultaat)
    {
        try {
            return referentieRepository.vindAdellijkeTitelOpCode(adellijkeTitelCode);
        } catch (OnbekendeReferentieExceptie ore) {
            final Melding melding =
                new Melding(Soortmelding.FOUT, MeldingCode.BRAL1015, String.format(
                        MeldingCode.BRAL1015.getOmschrijving(), adellijkeTitelCode.getWaarde()), groep,
                        "adellijkeTitelCode");
            resultaat.voegMeldingToe(melding);
            return null;
        }
    }

    /**
     * Haal Predikaat op en als het niet bestaat voeg melding toe aan de bericht resultaat.
     *
     * @param predikaatCode predikaatCode
     * @param groep het groep dat een zendendId bevat
     * @param resultaat Resultaat met lijst om eventuele meldingen die door de stap worden gemaakt terug te geven
     *            aan de client.
     * @return Predikaat of null
     */
    private Predikaat vindPredikaatOpCode(final PredikaatCode predikaatCode, final Identificeerbaar groep,
            final BerichtVerwerkingsResultaat resultaat)
    {
        try {
            return referentieRepository.vindPredikaatOpCode(predikaatCode);
        } catch (OnbekendeReferentieExceptie ore) {
            final Melding melding =
                new Melding(Soortmelding.FOUT, MeldingCode.BRAL1018, String.format(
                        MeldingCode.BRAL1018.getOmschrijving(), predikaatCode.getWaarde()), groep, "predikaatCode");
            resultaat.voegMeldingToe(melding);
            return null;
        }
    }

    /**
     * Haal land op en als het niet bestaat voeg melding toe aan de bericht resultaat.
     *
     * @param plaatsCode plaatsCode
     * @param groep het groep dat een zendendId bevat
     * @param resultaat Resultaat met lijst om eventuele meldingen die door de stap worden gemaakt terug te geven
     *            aan de client.
     * @return Plaats of null
     */
    private Plaats vindWoonplaatsOpCode(final PlaatsCode plaatsCode, final Identificeerbaar groep,
            final BerichtVerwerkingsResultaat resultaat)
    {
        try {
            return referentieRepository.vindWoonplaatsOpCode(plaatsCode);
        } catch (OnbekendeReferentieExceptie ore) {
            final Melding melding =
                new Melding(Soortmelding.FOUT, MeldingCode.BRAL1020, String.format(
                        MeldingCode.BRAL1020.getOmschrijving(), plaatsCode.getWaarde()), groep, "woonplaatsCode");
            resultaat.voegMeldingToe(melding);
            return null;
        }
    }

    /**
     * Haal land op en als het niet bestaat voeg melding toe aan de bericht resultaat.
     *
     * @param landcode landcode
     * @param groep het groep dat een zendendId bevat
     * @param resultaat Resultaat met lijst om eventuele meldingen die door de stap worden gemaakt terug te geven
     *            aan de client.
     * @return Land of null
     */
    private Land vindLandOpCode(final Landcode landcode,
                                final Identificeerbaar groep, final BerichtVerwerkingsResultaat resultaat)
    {
        try {
            return referentieRepository.vindLandOpCode(landcode);
        } catch (OnbekendeReferentieExceptie ore) {
            final Melding melding =
                new Melding(Soortmelding.FOUT, MeldingCode.BRAL1001, String.format(
                        MeldingCode.BRAL1001.getOmschrijving(), landcode.getWaarde()), groep, "landCode");
            resultaat.voegMeldingToe(melding);
            return null;
        }
    }

    /**
     * Haal de gemeente code op en als het niet bestaat voeg een melding toe aan de bericht resultaat.
     *
     * @param gemeenteCode gemeentecode
     * @param groep het groep dat een zendendId bevat
     * @param resultaat Resultaat met lijst om eventuele meldingen die door de stap worden gemaakt terug te geven
     *            aan de client.
     * @return Partij of null wanneer de gemeente niet gevonden kan worden met de gemeenteCode
     */
    private Partij vindGemeenteOpCode(final Gemeentecode gemeenteCode, final Identificeerbaar groep,
            final BerichtVerwerkingsResultaat resultaat)
    {
        try {
            return referentieRepository.vindGemeenteOpCode(gemeenteCode);
        } catch (OnbekendeReferentieExceptie ore) {
            final Melding melding =
                new Melding(Soortmelding.FOUT, MeldingCode.BRAL1002, String.format(
                        MeldingCode.BRAL1002.getOmschrijving(), gemeenteCode.getWaarde()), groep, "gemeenteCode");
            resultaat.voegMeldingToe(melding);
            return null;
        }
    }
}
