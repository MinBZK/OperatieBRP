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
import nl.bzk.brp.model.attribuuttype.DatumTijd;
import nl.bzk.brp.model.attribuuttype.Gemeentecode;
import nl.bzk.brp.model.attribuuttype.Landcode;
import nl.bzk.brp.model.attribuuttype.Naam;
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
import nl.bzk.brp.model.groep.bericht.PersoonOverlijdenGroepBericht;
import nl.bzk.brp.model.groep.bericht.RelatieStandaardGroepBericht;
import nl.bzk.brp.model.objecttype.bericht.ActieBericht;
import nl.bzk.brp.model.objecttype.bericht.BetrokkenheidBericht;
import nl.bzk.brp.model.objecttype.bericht.BronBericht;
import nl.bzk.brp.model.objecttype.bericht.DocumentBericht;
import nl.bzk.brp.model.objecttype.bericht.DocumentStandaardGroepBericht;
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
import nl.bzk.brp.model.objecttype.operationeel.statisch.SoortDocument;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Soortmelding;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.model.validatie.MeldingCode;
import nl.bzk.brp.util.AttribuutTypeUtil;
import org.apache.commons.collections.CollectionUtils;


/**
 * Stap verrijkt stamgegevens in het bericht zoals Land, Partij en Gemeente.
 * Codes worden omgezet naar stamgegeven entities.
 */
public class BerichtVerrijkingsStap extends
        AbstractBerichtVerwerkingsStap<AbstractBijhoudingsBericht, BerichtVerwerkingsResultaat>
{

    @Inject
    private ReferentieDataRepository referentieRepository;

    @Override
    public boolean voerVerwerkingsStapUitVoorBericht(final AbstractBijhoudingsBericht bericht,
            final BerichtContext context, final BerichtVerwerkingsResultaat resultaat)
    {
        for (Actie actie : bericht.getBrpActies()) {
            verrijkBronnen((ActieBericht) actie, resultaat);
            ((ActieBericht) actie).setTijdstipRegistratie(new DatumTijd(context.getTijdstipVerwerking()));
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
     * .
     * @param actie .
     * @param resultaat .
     */
    private void verrijkBronnen(final ActieBericht actie, final BerichtVerwerkingsResultaat resultaat) {
        if (actie.getBronnen() != null) {
            for (BronBericht bron : actie.getBronnen()) {
                if (null != bron.getDocument()) {
                    DocumentBericht document = bron.getDocument();
                    if (AttribuutTypeUtil.isNotBlank(document.getDocumentsoortNaam())) {
                        document.setSoort(vindSoortDocumentOpNaam(document.getDocumentsoortNaam(),
                            document, resultaat));
                        if (null != bron.getDocument() && null != bron.getDocument().getStandaard()) {
                            DocumentStandaardGroepBericht groep =
                                bron.getDocument().getStandaard();

                            if (AttribuutTypeUtil.isNotBlank(groep.getPartijCode())) {
                                groep.setPartij(vindGemeenteOpCode(groep.getPartijCode(),
                                        groep, resultaat));
                            }
                        }
                    }
                }
            }
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
            verrijkRelatieGroep(relatieStandaardGroep, soort, resultaat);
        }

        // Verrijk geboorte gegevens kind
        final BetrokkenheidBericht kindBetrokkenheid = relatie.getKindBetrokkenheid();
        if (kindBetrokkenheid != null && kindBetrokkenheid.getBetrokkene() != null) {
            final PersoonBericht kind = kindBetrokkenheid.getBetrokkene();
            if (kind.getGeboorte() != null) {
                vulAanGeboorteGroep(kind.getGeboorte(), resultaat);
            }

            if (CollectionUtils.isNotEmpty(kind.getGeslachtsnaamcomponenten())) {
                for (PersoonGeslachtsnaamcomponentBericht geslachtsnaamcomponent : kind.getGeslachtsnaamcomponenten()) {
                    vulAanGeslachtsnaamComponent(geslachtsnaamcomponent, resultaat);
                }
            }
        }
    }

    /**
     * Vul aan de codes die in de standaard relatie groep zitten.
     *
     * @param groep de relatie groep, mag niet leeg zijn.
     * @param soort het soort (extra flag)
     * @param resultaat lijst met resultaten.
     */
    private void verrijkRelatieGroep(final RelatieStandaardGroepBericht groep, final SoortActie soort,
            final BerichtVerwerkingsResultaat resultaat)
    {
        // Land aanvang
        if (SoortActie.HUWELIJK == soort) {
            // SRPUC04201: landAanvang = Nederland
            groep.setLandAanvang(vindLandOpCode(BrpConstanten.NL_LAND_CODE, null, null));
        } else if (AttribuutTypeUtil.isNotBlank(groep.getLandAanvangCode())) {
            groep.setLandAanvang(vindLandOpCode(groep.getLandAanvangCode(), groep, resultaat));
        }
        // LandEinde
        if (AttribuutTypeUtil.isNotBlank(groep.getLandEindeCode())) {
            groep.setLandEinde(vindLandOpCode(groep.getLandEindeCode(), groep, resultaat));
        }
        // Gemeente aanvang
        if (AttribuutTypeUtil.isNotBlank(groep.getGemeenteAanvangCode())) {
            groep.setGemeenteAanvang(vindGemeenteOpCode(groep.getGemeenteAanvangCode(), groep, resultaat));
        }
        // Gemeente einde
        if (AttribuutTypeUtil.isNotBlank(groep.getGemeenteEindeCode())) {
            groep.setGemeenteEinde(vindGemeenteOpCode(groep.getGemeenteEindeCode(), groep, resultaat));
        }
        // Woonplaats aanvang
        if (AttribuutTypeUtil.isNotBlank(groep.getWoonPlaatsAanvangCode())) {
            groep.setWoonPlaatsAanvang(vindWoonplaatsOpCode(groep.getWoonPlaatsAanvangCode(), groep, resultaat));
        }
        // Woonplaats einde
        if (AttribuutTypeUtil.isNotBlank(groep.getWoonPlaatsEindeCode())) {
            groep.setWoonPlaatsEinde(vindWoonplaatsOpCode(groep.getWoonPlaatsEindeCode(), groep, resultaat));
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
        if (CollectionUtils.isNotEmpty(persoon.getAdressen())) {
            for (PersoonAdresBericht persoonAdresBericht : persoon.getAdressen()) {
                if (persoonAdresBericht.getGegevens() != null) {
                    vulAanAdresGroep(persoonAdresBericht.getGegevens(), resultaat);
                }
            }
        }
        if (CollectionUtils.isNotEmpty(persoon.getNationaliteiten())) {
            for (PersoonNationaliteitBericht persoonNationaliteitBericht : persoon.getNationaliteiten()) {
                vulAanNationaliteitGroep(persoonNationaliteitBericht, resultaat);
            }
        }

        if (persoon.getOverlijden() != null) {
            vulAanOverlijdenGroep(persoon.getOverlijden(), resultaat);
        }
    }

    /**
     * Vul aan de codes die in de geboorte groep zitten.
     *
     * @param groep het geboorte groep, mag niet null zijn.
     * @param resultaat lijst met resultaten.
     */
    private void vulAanGeboorteGroep(final PersoonGeboorteGroepBericht groep,
            final BerichtVerwerkingsResultaat resultaat)
    {
        // Gemeente geboorte
        if (AttribuutTypeUtil.isNotBlank(groep.getGemeenteGeboorteCode())) {
            groep.setGemeenteGeboorte(vindGemeenteOpCode(groep.getGemeenteGeboorteCode(), groep, resultaat));
        }
        // Land geboorte
        if (AttribuutTypeUtil.isNotBlank(groep.getLandGeboorteCode())) {
            groep.setLandGeboorte(vindLandOpCode(groep.getLandGeboorteCode(), groep, resultaat));
        }
        // Woonplaats geboorte
        if (AttribuutTypeUtil.isNotBlank(groep.getWoonplaatsGeboorteCode())) {
            groep.setWoonplaatsGeboorte(vindWoonplaatsOpCode(groep.getWoonplaatsGeboorteCode(), groep, resultaat));
        }
    }

    /**
     * Vul aan de codes die in de geslachtnaamcomponent zitten.
     *
     * @param geslachtsnaamcomponent het geslachtsnaamcomponent.
     * @param resultaat lijst met resultaten.
     */
    private void vulAanGeslachtsnaamComponent(final PersoonGeslachtsnaamcomponentBericht geslachtsnaamcomponent,
            final BerichtVerwerkingsResultaat resultaat)
    {
        final PersoonGeslachtsnaamcomponentStandaardGroepBericht groep = geslachtsnaamcomponent.getGegevens();
        // Predikaat
        if (AttribuutTypeUtil.isNotBlank(groep.getPredikaatCode())) {
            groep.setPredikaat(vindPredikaatOpCode(groep.getPredikaatCode(), groep, resultaat));
        }
        // Adelijke Titel geboorte
        if (AttribuutTypeUtil.isNotBlank(groep.getAdellijkeTitelCode())) {
            groep.setAdellijkeTitel(vindAdellijkeTitelOpCode(groep.getAdellijkeTitelCode(), geslachtsnaamcomponent,
                    resultaat));
        }
    }

    /**
     * Vul aan de codes die in de overlijden groep zitten.
     *
     * @param groep het overlijden groep, mag niet null zijn.
     * @param resultaat lijst met resultaten.
     */
    private void vulAanOverlijdenGroep(final PersoonOverlijdenGroepBericht groep,
            final BerichtVerwerkingsResultaat resultaat)
    {
        if (AttribuutTypeUtil.isNotBlank(groep.getGemeenteOverlijdenCode())) {
            groep.setOverlijdenGemeente(vindGemeenteOpCode(groep.getGemeenteOverlijdenCode(), groep, resultaat));
        }
        if (AttribuutTypeUtil.isNotBlank(groep.getWoonplaatsOverlijdenCode())) {
            groep.setWoonplaatsOverlijden(vindWoonplaatsOpCode(groep.getWoonplaatsOverlijdenCode(), groep, resultaat));
        }
        if (AttribuutTypeUtil.isNotBlank(groep.getLandOverlijdenCode())) {
            groep.setLandOverlijden(vindLandOpCode(groep.getLandOverlijdenCode(), groep, resultaat));
        }
    }

    /**
     * Vul aan de codes die in de nationaliteit standaard groep zitten.
     *
     * @param nationaliteit de nationaliteit.
     * @param resultaat lijst met resultaten.
     */
    private void vulAanNationaliteitGroep(final PersoonNationaliteitBericht nationaliteit,
            final BerichtVerwerkingsResultaat resultaat)
    {
        if (AttribuutTypeUtil.isNotBlank(nationaliteit.getNationaliteitcode())) {
            nationaliteit.setNationaliteit(vindNationaliteitOpCode(nationaliteit.getNationaliteitcode(), nationaliteit,
                    resultaat));
        }
        final PersoonNationaliteitStandaardGroepBericht groep = nationaliteit.getGegevens();

        if (AttribuutTypeUtil.isNotBlank(groep.getRedenVerkregenNlNationaliteitCode())) {
            groep.setRedenVerkregenNlNationaliteit(vindRedenVerkregenNlNationaliteitOpCode(
                    groep.getRedenVerkregenNlNationaliteitCode(), groep, resultaat));
        }
        if (AttribuutTypeUtil.isNotBlank(groep.getRedenVerliesNlNationaliteitNaam())) {
            groep.setRedenVerliesNlNationaliteit(vindRedenVerliesNLNationaliteitOpNaam(
                    groep.getRedenVerliesNlNationaliteitNaam(), groep, resultaat));
        }
    }

    /**
     * Vul aan de codes die in de adres standaard groep zitten.
     *
     * @param groep het adres standaard groep, mag niet null zijn.
     * @param resultaat lijst met resultaten.
     */
    private void vulAanAdresGroep(final PersoonAdresStandaardGroepBericht groep,
            final BerichtVerwerkingsResultaat resultaat)
    {
        if (AttribuutTypeUtil.isNotBlank(groep.getRedenWijzigingAdresCode())) {
            groep.setRedenwijziging(vindRedenWijzingAdresOpCode(groep.getRedenWijzigingAdresCode(), groep, resultaat));
        }
        if (AttribuutTypeUtil.isNotBlank(groep.getCode())) {
            groep.setWoonplaats(vindWoonplaatsOpCode(groep.getCode(), groep, resultaat));
        }
        if (AttribuutTypeUtil.isNotBlank(groep.getGemeentecode())) {
            groep.setGemeente(vindGemeenteOpCode(groep.getGemeentecode(), groep, resultaat));
        }
        if (AttribuutTypeUtil.isNotBlank(groep.getLandcode())) {
            groep.setLand(vindLandOpCode(groep.getLandcode(), groep, resultaat));
        } else {
            // TODO tijdelijke fix, als er geen land opgegeven is dan tijdelijk land op NL zetten
            groep.setLand(vindLandOpCode(BrpConstanten.NL_LAND_CODE, groep, resultaat));
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
    private Land vindLandOpCode(final Landcode landcode, final Identificeerbaar groep,
            final BerichtVerwerkingsResultaat resultaat)
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

    /**
     * Haal de soort document op en als het niet bestaat voeg een melding toe aan de bericht resultaat.
     * @brp.bedrijfsregel BRAL1026
     *
     * @param naam naam soort document
     * @param groep het groep dat een zendendId bevat
     * @param resultaat Resultaat met lijst om eventuele meldingen die door de stap worden gemaakt terug te geven
     *            aan de client.
     * @return SoortDocument of null wanneer het sooort document niet gevonden kan worden met de naam
     */
    private SoortDocument vindSoortDocumentOpNaam(final Naam naam, final Identificeerbaar groep,
            final BerichtVerwerkingsResultaat resultaat)
    {
        try {
            return referentieRepository.vindSoortDocumentOpNaam(naam);
        } catch (OnbekendeReferentieExceptie ore) {
            final Melding melding =
                new Melding(Soortmelding.FOUT, MeldingCode.BRAL1026, String.format(
                        MeldingCode.BRAL1026.getOmschrijving(), naam.getWaarde()), groep, "soort");
            resultaat.voegMeldingToe(melding);
            return null;
        }
    }

}
