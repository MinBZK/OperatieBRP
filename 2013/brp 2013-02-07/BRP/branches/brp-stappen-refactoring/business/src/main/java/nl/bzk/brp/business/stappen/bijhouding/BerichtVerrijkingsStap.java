/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.stappen.bijhouding;

import javax.inject.Inject;

import nl.bzk.brp.business.dto.BerichtContext;
import nl.bzk.brp.business.dto.BerichtVerwerkingsResultaat;
import nl.bzk.brp.business.dto.bijhouding.AbstractBijhoudingsBericht;
import nl.bzk.brp.business.stappen.AbstractBerichtVerwerkingsStap;
import nl.bzk.brp.constanten.BrpConstanten;
import nl.bzk.brp.dataaccess.exceptie.OnbekendeReferentieExceptie;
import nl.bzk.brp.dataaccess.repository.ReferentieDataRepository;
import nl.bzk.brp.model.RootObject;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.AdellijkeTitelCode;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Burgerservicenummer;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijd;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.GemeenteCode;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Landcode;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamEnumeratiewaarde;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Nationaliteitcode;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.PredikaatCode;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.RedenVerkrijgingCode;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.RedenVerliesCode;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.RedenWijzigingAdresCode;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Woonplaatscode;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortMelding;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.AdellijkeTitel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.FunctieAdres;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Land;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Nationaliteit;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Plaats;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Predikaat;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RedenVerkrijgingNLNationaliteit;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RedenVerliesNLNationaliteit;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RedenWijzigingAdres;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortDocument;
import nl.bzk.brp.model.basis.Identificeerbaar;
import nl.bzk.brp.model.bericht.kern.ActieBericht;
import nl.bzk.brp.model.bericht.kern.ActieBronBericht;
import nl.bzk.brp.model.bericht.kern.AdministratieveHandelingBericht;
import nl.bzk.brp.model.bericht.kern.BetrokkenheidBericht;
import nl.bzk.brp.model.bericht.kern.DocumentBericht;
import nl.bzk.brp.model.bericht.kern.DocumentStandaardGroepBericht;
import nl.bzk.brp.model.bericht.kern.FamilierechtelijkeBetrekkingBericht;
import nl.bzk.brp.model.bericht.kern.HuwelijkGeregistreerdPartnerschapBericht;
import nl.bzk.brp.model.bericht.kern.HuwelijkGeregistreerdPartnerschapStandaardGroepBericht;
import nl.bzk.brp.model.bericht.kern.KindBericht;
import nl.bzk.brp.model.bericht.kern.OuderBericht;
import nl.bzk.brp.model.bericht.kern.PersoonAdresBericht;
import nl.bzk.brp.model.bericht.kern.PersoonAdresStandaardGroepBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bericht.kern.PersoonGeboorteGroepBericht;
import nl.bzk.brp.model.bericht.kern.PersoonGeslachtsnaamcomponentBericht;
import nl.bzk.brp.model.bericht.kern.PersoonGeslachtsnaamcomponentStandaardGroepBericht;
import nl.bzk.brp.model.bericht.kern.PersoonIdentificatienummersGroepBericht;
import nl.bzk.brp.model.bericht.kern.PersoonNationaliteitBericht;
import nl.bzk.brp.model.bericht.kern.PersoonNationaliteitStandaardGroepBericht;
import nl.bzk.brp.model.bericht.kern.PersoonOverlijdenGroepBericht;
import nl.bzk.brp.model.bericht.kern.RelatieBericht;
import nl.bzk.brp.model.logisch.kern.Actie;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.model.validatie.MeldingCode;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;


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
        // TODO: bolie: map alle identificerrbare groepen en bewaar deze in het berichtContext

        // TODO: bolie:

        AdministratieveHandelingBericht admhBericht = bericht.getAdministratieveHandeling();
        verrijkAdministratieveHandeling(admhBericht, resultaat);

        for (Actie actie : admhBericht.getActies()) {
            // BOLIE: verrijk acties (part == admhBerict met de partij, deze is slechts eenmalig in het bericht,
            // maar moet herhaald worden naar acties.
            if (actie.getPartij() == null) {
                ((ActieBericht) actie).setPartij(admhBericht.getPartij());
            }
            ((ActieBericht) actie).setTijdstipRegistratie(new DatumTijd(context.getTijdstipVerwerking()));
            verrijkBronnen((ActieBericht) actie, resultaat);
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
     *
     * @param admhBericht .
     * @param resultaat .
     */
    private void verrijkAdministratieveHandeling(final AdministratieveHandelingBericht admhBericht,
        final BerichtVerwerkingsResultaat resultaat)
    {
        if (StringUtils.isNotBlank(admhBericht.getPartijCode())) {
            admhBericht.setPartij(vindGemeenteOpCode(new GemeenteCode(admhBericht.getPartijCode()),
                admhBericht, resultaat));
        }
    }

    /**
     * .
     *
     * @param actie .
     * @param resultaat .
     */
    private void verrijkBronnen(final ActieBericht actie, final BerichtVerwerkingsResultaat resultaat) {
        if (actie.getBronnen() != null) {
            for (ActieBronBericht bron : actie.getBronnen()) {
                if (null != bron.getDocument()) {
                    DocumentBericht document = bron.getDocument();
                    if (StringUtils.isNotBlank(document.getSoortNaam())) {
                        document.setSoort(vindSoortDocumentOpNaam(document.getSoortNaam(),
                            document, resultaat));
                        if (null != bron.getDocument() && null != bron.getDocument().getStandaard()) {
                            DocumentStandaardGroepBericht groep =
                                bron.getDocument().getStandaard();

                            if (StringUtils.isNotBlank(groep.getPartijCode())) {
                                groep.setPartij(vindGemeenteOpCode(new GemeenteCode(groep.getPartijCode()),
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
     * aan de client.
     */
    private void verrijkActieGegevens(final RelatieBericht relatie, final SoortActie soort,
        final BerichtVerwerkingsResultaat resultaat)
    {
        // Verrijk gegevens relatie
        if (relatie instanceof HuwelijkGeregistreerdPartnerschapBericht) {
            // TODO tijdelijke hack, technische sleutel copieren naar identificatienrs.burgerservicenummer
            if (null != relatie.getBetrokkenheden()) {
                for (BetrokkenheidBericht betr : relatie.getBetrokkenheden()) {
                    verrijkPersoonMetBsnNummer(betr.getPersoon());
                }
            }
            HuwelijkGeregistreerdPartnerschapBericht partnerschap = (HuwelijkGeregistreerdPartnerschapBericht) relatie;
            if (null != partnerschap.getStandaard()) {
                verrijkPartnerschapGroep(partnerschap.getStandaard(), soort, resultaat);
            }
        } else if (relatie instanceof FamilierechtelijkeBetrekkingBericht) {
            FamilierechtelijkeBetrekkingBericht familie = (FamilierechtelijkeBetrekkingBericht) relatie;
            for (BetrokkenheidBericht betr : familie.getBetrokkenheden()) {
                if (betr instanceof KindBericht) {
                    // Verrijk geboorte gegevens kind
                    final KindBericht kindBericht = (KindBericht) betr;
                    if (kindBericht.getPersoon() != null) {
                        final PersoonBericht kind = kindBericht.getPersoon();
                        // GEEN tijdelijke hack, technische sleutel copieren naar identificatienrs.burgerservicenummer
                        // bsn moet altijd worden meegegeven en niet als onderdeel van technische sleutel.
                        if (kind.getGeboorte() != null) {
                            vulAanGeboorteGroep(kind.getGeboorte(), resultaat);
                        }

                        if (CollectionUtils.isNotEmpty(kind.getGeslachtsnaamcomponenten())) {
                            for (PersoonGeslachtsnaamcomponentBericht geslachtsnaamcomponent : kind
                                .getGeslachtsnaamcomponenten())
                            {
                                vulAanGeslachtsnaamComponent(geslachtsnaamcomponent, resultaat);
                            }
                        }
                    }
                } else if (betr instanceof OuderBericht) {
                    final OuderBericht ouderBericht = (OuderBericht) betr;
                    if (ouderBericht.getPersoon() != null) {
                        final PersoonBericht ouder = ouderBericht.getPersoon();
                        // TODO: bolie: tijdelijke hack, technische sleutel copieren naar identificatienrs.burgerservicenummer
                        verrijkPersoonMetBsnNummer(ouder);
                    }
                }
            }
        }
//        else if (relatie instanceof ErkenningOngeborenVruchtBericht) {
//        } else if (relatie instanceof NaamskeuzeOngeborenVruchtBericht) {
//        } else if (relatie instanceof OntkenningOuderschapOngeborenVruchtBericht) {
//        }

    }

    private void verrijkPartnerschapGroep(final HuwelijkGeregistreerdPartnerschapStandaardGroepBericht groep,
        final SoortActie soort, final BerichtVerwerkingsResultaat resultaat)
    {
        // Land aanvang
        if (StringUtils.isBlank(groep.getLandAanvangCode())) {
            groep.setLandAanvang(vindLandOpCode(BrpConstanten.NL_LAND_CODE, null, null));
        } else {
            groep.setLandAanvang(vindLandOpCode(new Landcode(groep.getLandAanvangCode()), groep, resultaat));
        }
        // LandEinde
        if (StringUtils.isNotBlank(groep.getLandEindeCode())) {
            groep.setLandEinde(vindLandOpCode(new Landcode(groep.getLandEindeCode()), groep, resultaat));
        }
        // Gemeente aanvang
        if (StringUtils.isNotBlank(groep.getGemeenteAanvangCode())) {
            groep.setGemeenteAanvang(
                vindGemeenteOpCode(new GemeenteCode(groep.getGemeenteAanvangCode()), groep, resultaat));
        }
        // Gemeente einde
        if (StringUtils.isNotBlank(groep.getGemeenteEindeCode())) {
            groep
                .setGemeenteEinde(vindGemeenteOpCode(new GemeenteCode(groep.getGemeenteEindeCode()), groep, resultaat));
        }
        // Woonplaats aanvang
        if (StringUtils.isNotBlank(groep.getWoonplaatsAanvangCode())) {
            groep.setWoonplaatsAanvang(
                vindWoonplaatsOpCode(new Woonplaatscode(groep.getWoonplaatsAanvangCode()), groep, resultaat));
        }
        // Woonplaats einde
        if (StringUtils.isNotBlank(groep.getWoonplaatsEindeCode())) {
            groep.setWoonplaatsEinde(
                vindWoonplaatsOpCode(new Woonplaatscode(groep.getWoonplaatsEindeCode()), groep, resultaat));
        }
    }

    /**
     * Verrijk de stamgegevens van de opgegeven persoon.
     *
     * @param persoon De te verrijken persoon.
     * @param soort Soort actie die uitgevoerd wordt.
     * @param resultaat Resultaat met lijst om eventuele meldingen die door de stap worden gemaakt terug te geven
     * aan de client.
     */
    private void verrijkActieGegevens(final PersoonBericht persoon, final SoortActie soort,
        final BerichtVerwerkingsResultaat resultaat)
    {
        // TODO tijdelijke hack, technische sleutel copieren naar identificatienrs.burgerservicenummer
        verrijkPersoonMetBsnNummer(persoon);
        if (CollectionUtils.isNotEmpty(persoon.getAdressen())) {
            for (PersoonAdresBericht persoonAdresBericht : persoon.getAdressen()) {
                if (persoonAdresBericht.getStandaard() != null) {
                    vulAanAdresGroep(persoonAdresBericht.getStandaard(), resultaat);
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
        if (StringUtils.isNotBlank(groep.getGemeenteGeboorteCode())) {
            groep.setGemeenteGeboorte(
                vindGemeenteOpCode(new GemeenteCode(groep.getGemeenteGeboorteCode()), groep, resultaat));
        }
        // Land geboorte
        if (StringUtils.isNotBlank(groep.getLandGeboorteCode())) {
            groep.setLandGeboorte(vindLandOpCode(new Landcode(groep.getLandGeboorteCode()), groep, resultaat));
        }
        // Woonplaats geboorte
        if (StringUtils.isNotBlank(groep.getWoonplaatsGeboorteCode())) {
            groep.setWoonplaatsGeboorte(
                vindWoonplaatsOpCode(new Woonplaatscode(groep.getWoonplaatsGeboorteCode()), groep, resultaat));
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
        final PersoonGeslachtsnaamcomponentStandaardGroepBericht groep = geslachtsnaamcomponent.getStandaard();
        // Predikaat
        if (StringUtils.isNotBlank(groep.getPredikaatCode())) {
            groep.setPredikaat(vindPredikaatOpCode(groep.getPredikaatCode(), groep, resultaat));
        }
        // Adelijke Titel geboorte
        if (StringUtils.isNotBlank(groep.getAdellijkeTitelCode())) {
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
        if (StringUtils.isNotBlank(groep.getGemeenteOverlijdenCode())) {
            groep.setGemeenteOverlijden(
                vindGemeenteOpCode(new GemeenteCode(groep.getGemeenteOverlijdenCode()), groep, resultaat));
        }
        if (StringUtils.isNotBlank(groep.getWoonplaatsOverlijdenCode())) {
            groep.setWoonplaatsOverlijden(
                vindWoonplaatsOpCode(new Woonplaatscode(groep.getWoonplaatsOverlijdenCode()), groep, resultaat));
        }
        if (StringUtils.isNotBlank(groep.getLandOverlijdenCode())) {
            groep.setLandOverlijden(vindLandOpCode(new Landcode(groep.getLandOverlijdenCode()), groep, resultaat));
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
        if (StringUtils.isNotBlank(nationaliteit.getNationaliteitCode())) {
            nationaliteit.setNationaliteit(vindNationaliteitOpCode(nationaliteit.getNationaliteitCode(), nationaliteit,
                resultaat));
        }
        final PersoonNationaliteitStandaardGroepBericht groep = nationaliteit.getStandaard();

        if (StringUtils.isNotBlank(groep.getRedenVerkrijgingCode())) {
            groep.setRedenVerkrijging(
                vindRedenVerkregenNlNationaliteitOpCode(groep.getRedenVerkrijgingCode(), groep, resultaat));
        }
        if (StringUtils.isNotBlank(groep.getRedenVerliesCode())) {
            groep.setRedenVerlies(
                vindRedenVerliesNLNationaliteitOpCode(groep.getRedenVerliesCode(), groep, resultaat));
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
        if (StringUtils.isNotBlank(groep.getRedenWijzigingCode())) {
            groep.setRedenWijziging(vindRedenWijzingAdresOpCode(groep.getRedenWijzigingCode(), groep, resultaat));
        }
        if (StringUtils.isNotBlank(groep.getWoonplaatsCode())) {
            groep.setWoonplaats(vindWoonplaatsOpCode(new Woonplaatscode(groep.getWoonplaatsCode()), groep, resultaat));
        }
        if (StringUtils.isNotBlank(groep.getGemeenteCode())) {
            groep.setGemeente(vindGemeenteOpCode(new GemeenteCode(groep.getGemeenteCode()), groep, resultaat));
        }
        if (StringUtils.isNotBlank(groep.getLandCode())) {
            groep.setLand(vindLandOpCode(new Landcode(groep.getLandCode()), groep, resultaat));
        } else {
            // TODO tijdelijke fix, als er geen land opgegeven is dan tijdelijk land op NL zetten
            groep.setLand(vindLandOpCode(BrpConstanten.NL_LAND_CODE, groep, resultaat));
        }

        if (groep.getSoort() != null) {
            if (groep.getSoort().getCode().equals("W")) {
                groep.setSoort(FunctieAdres.WOONADRES);
            } else if (groep.getSoort().getCode().equals("B")) {
                groep.setSoort(FunctieAdres.BRIEFADRES);
            } else {
                throw new UnsupportedOperationException("Onbekende 'enum' waarde voor soort Adres: "
                    + groep.getSoort().getCode());
            }
        }
    }

    /**
     * 'copieer' de attribuut technische sleutel naar de groep identificatienrs->burgerservicenummer.
     * Dit moet later weer weg, NADAT alle validatie regels niet meer met de bsn uit het bericht werken.
     *
     * @param persoonBericht .
     */
    private void verrijkPersoonMetBsnNummer(final PersoonBericht persoonBericht) {
        if (StringUtils.isNotBlank(persoonBericht.getTechnischeSleutel())) {
            // copieer naar de groep (for sake of downward compatible. moet later ook weggehaald worden).
            if (persoonBericht.getIdentificatienummers() == null) {
                persoonBericht.setIdentificatienummers(new PersoonIdentificatienummersGroepBericht());
            }
            persoonBericht.getIdentificatienummers().setBurgerservicenummer(
                new Burgerservicenummer(persoonBericht.getTechnischeSleutel()));
        }
    }

    /**
     * Haal RedenVerliesNLNationaliteit op en als het niet bestaat voeg melding toe aan de bericht resultaat.
     *
     * @param redenVerliesCode redenVerliesCode
     * @param groep het groep dat een zendendId bevat
     * @param resultaat Resultaat met lijst om eventuele meldingen die door de stap worden gemaakt terug te geven
     * aan de client.
     * @return RedenVerliesNLNationaliteit of null
     */
    private RedenVerliesNLNationaliteit vindRedenVerliesNLNationaliteitOpCode(final String redenVerliesCode,
        final Identificeerbaar groep, final BerichtVerwerkingsResultaat resultaat)
    {
        try {
            return referentieRepository.vindRedenVerliesNLNationaliteitOpCode(new RedenVerliesCode(redenVerliesCode));
        } catch (OnbekendeReferentieExceptie ore) {
            final Melding melding =
                new Melding(SoortMelding.FOUT, MeldingCode.BRAL1022, String.format(
                    MeldingCode.BRAL1022.getOmschrijving(), redenVerliesCode), groep,
                    "redenVerliesCode");
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
     * aan de client.
     * @return RedenVerkrijgingNLNationaliteit of null
     */
    private RedenVerkrijgingNLNationaliteit vindRedenVerkregenNlNationaliteitOpCode(
        final String redenVerkrijgingCode, final Identificeerbaar groep,
        final BerichtVerwerkingsResultaat resultaat)
    {
        try {
            return referentieRepository.vindRedenVerkregenNlNationaliteitOpCode(
                new RedenVerkrijgingCode(redenVerkrijgingCode));
        } catch (OnbekendeReferentieExceptie ore) {
            final Melding melding =
                new Melding(SoortMelding.FOUT, MeldingCode.BRAL1021, String.format(
                    MeldingCode.BRAL1021.getOmschrijving(), redenVerkrijgingCode), groep,
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
     * aan de client.
     * @return RedenWijzigingAdres of null
     */
    private Nationaliteit vindNationaliteitOpCode(final String nationaliteitcode,
        final Identificeerbaar groep, final BerichtVerwerkingsResultaat resultaat)
    {
        try {
            return referentieRepository.vindNationaliteitOpCode(new Nationaliteitcode(nationaliteitcode));
        } catch (OnbekendeReferentieExceptie ore) {
            final Melding melding =
                new Melding(SoortMelding.FOUT, MeldingCode.BRAL1017, String.format(
                    MeldingCode.BRAL1017.getOmschrijving(), nationaliteitcode), groep,
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
     * aan de client.
     * @return RedenWijzigingAdres of null
     */
    private RedenWijzigingAdres vindRedenWijzingAdresOpCode(final String redenWijzigingAdresCode,
        final Identificeerbaar groep, final BerichtVerwerkingsResultaat resultaat)
    {
        try {
            return referentieRepository
                .vindRedenWijzingAdresOpCode(new RedenWijzigingAdresCode(redenWijzigingAdresCode));
        } catch (OnbekendeReferentieExceptie ore) {
            final Melding melding =
                new Melding(SoortMelding.FOUT, MeldingCode.BRAL1007, String.format(
                    MeldingCode.BRAL1007.getOmschrijving(), redenWijzigingAdresCode), groep,
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
     * aan de client.
     * @return AdellijkeTitel of null
     */
    private AdellijkeTitel vindAdellijkeTitelOpCode(final String adellijkeTitelCode,
        final Identificeerbaar groep, final BerichtVerwerkingsResultaat resultaat)
    {
        try {
            return referentieRepository.vindAdellijkeTitelOpCode(new AdellijkeTitelCode(adellijkeTitelCode));
        } catch (OnbekendeReferentieExceptie ore) {
            final Melding melding =
                new Melding(SoortMelding.FOUT, MeldingCode.BRAL1015, String.format(
                    MeldingCode.BRAL1015.getOmschrijving(), adellijkeTitelCode), groep,
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
     * aan de client.
     * @return Predikaat of null
     */
    private Predikaat vindPredikaatOpCode(final String predikaatCode, final Identificeerbaar groep,
        final BerichtVerwerkingsResultaat resultaat)
    {
        try {
            return referentieRepository.vindPredikaatOpCode(new PredikaatCode(predikaatCode));
        } catch (OnbekendeReferentieExceptie ore) {
            final Melding melding =
                new Melding(SoortMelding.FOUT, MeldingCode.BRAL1018, String.format(
                    MeldingCode.BRAL1018.getOmschrijving(), predikaatCode), groep, "predikaatCode");
            resultaat.voegMeldingToe(melding);
            return null;
        }
    }

    /**
     * Haal land op en als het niet bestaat voeg melding toe aan de bericht resultaat.
     *
     * @param woonplaatscode woonplaatscode
     * @param groep het groep dat een zendendId bevat
     * @param resultaat Resultaat met lijst om eventuele meldingen die door de stap worden gemaakt terug te geven
     * aan de client.
     * @return Plaats of null
     */
    private Plaats vindWoonplaatsOpCode(final Woonplaatscode woonplaatscode, final Identificeerbaar groep,
        final BerichtVerwerkingsResultaat resultaat)
    {
        try {
            return referentieRepository.vindWoonplaatsOpCode(woonplaatscode);
        } catch (OnbekendeReferentieExceptie ore) {
            final Melding melding =
                new Melding(SoortMelding.FOUT, MeldingCode.BRAL1020, String.format(
                    MeldingCode.BRAL1020.getOmschrijving(), woonplaatscode), groep, "woonplaatsCode");
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
     * aan de client.
     * @return Land of null
     */
    private Land vindLandOpCode(final Landcode landcode, final Identificeerbaar groep,
        final BerichtVerwerkingsResultaat resultaat)
    {
        try {
            return referentieRepository.vindLandOpCode(landcode);
        } catch (OnbekendeReferentieExceptie ore) {
            final Melding melding =
                new Melding(SoortMelding.FOUT, MeldingCode.BRAL1001, String.format(
                    MeldingCode.BRAL1001.getOmschrijving(), landcode), groep, "landCode");
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
     * aan de client.
     * @return Partij of null wanneer de gemeente niet gevonden kan worden met de gemeenteCode
     */
    private Partij vindGemeenteOpCode(final GemeenteCode gemeenteCode, final Identificeerbaar groep,
        final BerichtVerwerkingsResultaat resultaat)
    {
        try {
            return referentieRepository.vindGemeenteOpCode(gemeenteCode);
        } catch (OnbekendeReferentieExceptie ore) {
            final Melding melding =
                new Melding(SoortMelding.FOUT, MeldingCode.BRAL1002, String.format(
                    MeldingCode.BRAL1002.getOmschrijving(), gemeenteCode), groep, "gemeenteCode");
            resultaat.voegMeldingToe(melding);
            return null;
        }
    }

    /**
     * Haal de soort document op en als het niet bestaat voeg een melding toe aan de bericht resultaat.
     *
     * @param naam naam soort document
     * @param groep het groep dat een zendendId bevat
     * @param resultaat Resultaat met lijst om eventuele meldingen die door de stap worden gemaakt terug te geven
     * aan de client.
     * @return SoortDocument of null wanneer het sooort document niet gevonden kan worden met de naam
     * @brp.bedrijfsregel BRAL1026
     */
    private SoortDocument vindSoortDocumentOpNaam(final String naam, final Identificeerbaar groep,
        final BerichtVerwerkingsResultaat resultaat)
    {
        try {
            return referentieRepository.vindSoortDocumentOpNaam(new NaamEnumeratiewaarde(naam));
        } catch (OnbekendeReferentieExceptie ore) {
            final Melding melding =
                new Melding(SoortMelding.FOUT, MeldingCode.BRAL1026, String.format(
                    MeldingCode.BRAL1026.getOmschrijving(), naam), groep, "soort");
            resultaat.voegMeldingToe(melding);
            return null;
        }
    }

}
