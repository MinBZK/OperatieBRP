/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.stappen.bijhouding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.inject.Inject;
import nl.bzk.brp.bijhouding.business.stappen.context.BijhoudingBerichtContext;
import nl.bzk.brp.bijhouding.business.stappen.resultaat.Resultaat;
import nl.bzk.brp.bijhouding.business.stappen.resultaat.ResultaatMelding;
import nl.bzk.brp.bijhouding.business.stappen.resultaat.WaardeMetMeldingen;
import nl.bzk.brp.business.stappen.BerichtContext;
import nl.bzk.brp.dataaccess.exceptie.OnbekendeReferentieExceptie;
import nl.bzk.brp.dataaccess.repository.ReferentieDataRepository;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.model.RootObject;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.AanduidingInhoudingVermissingReisdocumentCodeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.AangeverCodeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.AdellijkeTitelCodeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.GemeenteCodeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.LandGebiedCodeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamEnumeratiewaardeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NationaliteitcodeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Nee;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NeeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.PartijCodeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.PredicaatCodeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.RedenEindeRelatieCodeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.RedenVerkrijgingCodeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.RedenVerliesCodeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.RedenWijzigingVerblijfCodeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.ScheidingstekenAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.SoortNederlandsReisdocumentCodeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.VolgnummerAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.VoorvoegselAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortMelding;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.AanduidingInhoudingVermissingReisdocumentAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.AangeverAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.AdellijkeTitelAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.DatabaseObject;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.DatabaseObjectKern;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Gemeente;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.GemeenteAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.LandGebiedAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Naamgebruik;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.NaamgebruikAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.NationaliteitAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PartijAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PredicaatAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RedenEindeRelatieAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RedenVerkrijgingNLNationaliteitAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RedenVerliesNLNationaliteitAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RedenWijzigingVerblijfAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortDocument;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortDocumentAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortMigratie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortMigratieAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortNederlandsReisdocumentAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoonAttribuut;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.basis.BerichtIdentificeerbaar;
import nl.bzk.brp.model.bericht.ber.AdministratieveHandelingBronBericht;
import nl.bzk.brp.model.bericht.kern.ActieBericht;
import nl.bzk.brp.model.bericht.kern.ActieBronBericht;
import nl.bzk.brp.model.bericht.kern.AdministratieveHandelingBericht;
import nl.bzk.brp.model.bericht.kern.BetrokkenheidBericht;
import nl.bzk.brp.model.bericht.kern.DocumentBericht;
import nl.bzk.brp.model.bericht.kern.DocumentStandaardGroepBericht;
import nl.bzk.brp.model.bericht.kern.FamilierechtelijkeBetrekkingBericht;
import nl.bzk.brp.model.bericht.kern.HuwelijkGeregistreerdPartnerschapBericht;
import nl.bzk.brp.model.bericht.kern.KindBericht;
import nl.bzk.brp.model.bericht.kern.OuderBericht;
import nl.bzk.brp.model.bericht.kern.PersoonAdresBericht;
import nl.bzk.brp.model.bericht.kern.PersoonAdresStandaardGroepBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bericht.kern.PersoonGeboorteGroepBericht;
import nl.bzk.brp.model.bericht.kern.PersoonGeslachtsnaamcomponentBericht;
import nl.bzk.brp.model.bericht.kern.PersoonGeslachtsnaamcomponentStandaardGroepBericht;
import nl.bzk.brp.model.bericht.kern.PersoonMigratieGroepBericht;
import nl.bzk.brp.model.bericht.kern.PersoonNaamgebruikGroepBericht;
import nl.bzk.brp.model.bericht.kern.PersoonNationaliteitBericht;
import nl.bzk.brp.model.bericht.kern.PersoonNationaliteitStandaardGroepBericht;
import nl.bzk.brp.model.bericht.kern.PersoonOverlijdenGroepBericht;
import nl.bzk.brp.model.bericht.kern.PersoonReisdocumentBericht;
import nl.bzk.brp.model.bericht.kern.PersoonReisdocumentStandaardGroepBericht;
import nl.bzk.brp.model.bericht.kern.PersoonSamengesteldeNaamGroepBericht;
import nl.bzk.brp.model.bericht.kern.PersoonVerstrekkingsbeperkingBericht;
import nl.bzk.brp.model.bericht.kern.RelatieBericht;
import nl.bzk.brp.model.bericht.kern.RelatieStandaardGroepBericht;
import nl.bzk.brp.model.bijhouding.BijhoudingsBericht;
import nl.bzk.brp.webservice.business.service.ObjectSleutelService;
import nl.bzk.brp.webservice.business.service.OngeldigeObjectSleutelExceptie;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

/**
 * Stap verrijkt stamgegevens in het bericht zoals Land, Partij en Gemeente. Codes worden omgezet naar stamgegeven entities.
 */
@Component
public class BerichtVerrijkingsStap {
    private static final Logger LOGGER                                            = LoggerFactory.getLogger();
    private static final String PARTIJ_CODE                                       = "partijCode";
    private static final String REDEN_VERLIES_CODE                                = "redenVerliesCode";
    private static final String GEMEENTE_CODE                                     = "gemeenteCode";
    private static final String LAND_CODE                                         = "landCode";
    private static final String LAND_GEBIED_MIGRATIE_CODE                         = "landGebiedMigratieCode";
    private static final String NATIONALITEIT_NAAM                                = "nationaliteitNaam";
    private static final String GEMEENTE_GEBOORTE_CODE                            = "gemeenteGeboorteCode";
    private static final String LAND_GEBIED_GEBOORTE_CODE                         = "landGebiedGeboorteCode";
    private static final String WOONPLAATSNAAM                                    = "woonplaatsnaam";
    private static final String GEMEENTE_OVERLIJDEN_CODE                          = "gemeenteOverlijdenCode";
    private static final String LAND_OVERLIJDEN_CODE                              = "landOverlijdenCode";
    private static final String PREDIKAAT_CODE                                    = "predikaatCode";
    private static final String ADELLIJKE_TITEL_CODE                              = "adellijkeTitelCode";
    private static final String AANGEVER_ADRESHOUDING_CODE                        = "aangeverAdreshoudingCode";
    private static final String REDEN_WIJZIGING_CODE                              = "redenWijzigingCode";
    private static final String REDEN_VERKRIJGING_CODE                            = "redenVerkrijgingCode";
    private static final String REDEN_VERKRIJGING_NAAM                            = "redenVerkrijgingNaam";
    private static final String AANDUIDING_INHOUDING_VERMISSING_REISDOCUMENT_CODE = "aanduidingInhoudingVermissingReisdocumentCode";
    private static final String LAND_AANVANG_CODE                                 = "landAanvangCode";
    private static final String LAND_EINDE_CODE                                   = "landEindeCode";
    private static final String GEMEENTE_AANVANG_CODE                             = "gemeenteAanvangCode";
    private static final String GEMEENTE_EINDE_CODE                               = "gemeenteEindeCode";
    private static final String REDEN_EINDE_CODE                                  = "redenEindeCode";

    @Inject
    private ReferentieDataRepository referentieRepository;

    @Inject
    private ObjectSleutelService objectSleutelService;

    @Inject
    private MeldingFactory meldingFactory;

    /**
     * Voert de stap uit.
     *
     * @param bericht het bijhoudingsbericht
     * @param context de bijhoudingberichtcontext
     * @return het resultaat
     */
    public Resultaat voerStapUit(final BijhoudingsBericht bericht, final BijhoudingBerichtContext context) {
        final AdministratieveHandelingBericht admhBericht = bericht.getAdministratieveHandeling();
        admhBericht.setTijdstipRegistratie(new DatumTijdAttribuut(context.getTijdstipVerwerking()));

        final Set<ResultaatMelding> meldingen = new HashSet<>();

        meldingen.addAll(verrijkAdministratieveHandeling(admhBericht));

        bericht.getStuurgegevens().setZendendePartij(context.getPartij());

        for (final ActieBericht actie : admhBericht.getActies()) {
            // BOLIE: verrijk acties (part == admhBericht met de partij, deze is slechts eenmalig in het bericht,
            // maar moet herhaald worden naar acties.
            if (actie.getPartij() == null) {
                actie.setPartij(admhBericht.getPartij());
            }
            actie.setTijdstipRegistratie(admhBericht.getTijdstipRegistratie());
            meldingen.addAll(verrijkActieBronnen(context, actie));
            final RootObject rootObject = actie.getRootObject();
            if (rootObject instanceof PersoonBericht) {
                meldingen.addAll(verrijkActieGegevens((PersoonBericht) rootObject, admhBericht.getSoort().getWaarde(),
                    context));
            } else if (rootObject instanceof RelatieBericht) {
                meldingen.addAll(verrijkActieGegevens((RelatieBericht) rootObject, actie.getSoort().getWaarde(), admhBericht
                    .getSoort().getWaarde()));
            } else {
                throw new UnsupportedOperationException("RootObject wordt niet ondersteund door "
                    + "BerichtVerrijkingsStap: " + rootObject.getClass().getSimpleName());
            }
        }
        return Resultaat.builder().withMeldingen(meldingen).build();
    }

    /**
     * Verrijk administratieve handeling.
     *
     * @param administratieveHandeling administratieve handeling bericht
     * @brp.bedrijfsregel BRAL1002A
     */
    private List<ResultaatMelding> verrijkAdministratieveHandeling(final AdministratieveHandelingBericht administratieveHandeling)
    {
        final List<ResultaatMelding> meldingen = new ArrayList<>();
        final String code = administratieveHandeling.getPartijCode();
        if (!StringUtils.isBlank(code)) {
            if (StringUtils.isNumeric(code)) {
                try {
                    final Partij partij = referentieRepository
                        .vindPartijOpCode(new PartijCodeAttribuut(Integer.parseInt(code)));
                    administratieveHandeling.setPartij(new PartijAttribuut(partij));
                } catch (final OnbekendeReferentieExceptie ore) {
                    meldingen.add(meldingFactory.maakResultaatMelding(Regel.BRAL1002A, administratieveHandeling,
                        DatabaseObjectKern.ADMINISTRATIEVE_HANDELING__PARTIJ));
                }
            } else {
                meldingen.add(ResultaatMelding.builder()
                    .withSoort(SoortMelding.FOUT)
                    .withRegel(Regel.ALG0001)
                    .withReferentieID(administratieveHandeling.getCommunicatieID())
                    .withMeldingTekst(PARTIJ_CODE + " moet een numerieke waarde bevatten: " + code)
                    .build());
            }
        }
        return meldingen;
    }

    /**
     * Verrijk bronnen en onderliggende documenten op basis van de actie en bericht verwerking resultaat.
     *
     * @param berichtContext bericht context
     * @param actie          actie
     */
    private List<ResultaatMelding> verrijkActieBronnen(final BerichtContext berichtContext, final ActieBericht actie)
    {
        final List<ResultaatMelding> meldingen = new ArrayList<>();
        if (actie.getBronnen() != null) {
            for (final ActieBronBericht bron : actie.getBronnen()) {
                // Haal bron op uit de Administratieve Handeling op basis van referentieId
                final AdministratieveHandelingBronBericht bronAH =
                    (AdministratieveHandelingBronBericht) berichtContext.getIdentificeerbareObjecten()
                        .get(bron.getReferentieID()).get(0);

                // Als er een document aan de bron hangt, neem deze dan over.
                if (null != bronAH.getDocument()) {
                    final DocumentBericht documentUitAH = bronAH.getDocument();

                    // TODO: wat te doen bij een bestaand document?
                    if (StringUtils.isEmpty(documentUitAH.getObjectSleutel())) {
                        meldingen.addAll(verrijkDocument(documentUitAH));

                        // Vervang het document in de actie met degene uit de administratieve handeling.
                        bron.setDocument(documentUitAH);
                    }
                }
            }
        }
        return meldingen;
    }

    /**
     * Verrijk het document
     *
     * @param documentUitAH document behorende bij de bron waarvan de object sleutel leeg is
     */
    private List<ResultaatMelding> verrijkDocument(final DocumentBericht documentUitAH) {
        final List<ResultaatMelding> meldingen = new ArrayList<>();
        if (documentUitAH.getSoortNaam() != null) {
            final String naam = documentUitAH.getSoortNaam();
            try {
                final SoortDocument soortDocument = referentieRepository.vindSoortDocumentOpNaam(new NaamEnumeratiewaardeAttribuut(naam));
                documentUitAH.setSoort(new SoortDocumentAttribuut(soortDocument));
            } catch (final OnbekendeReferentieExceptie ore) {
                meldingen.add(ResultaatMelding.builder()
                    .withSoort(SoortMelding.FOUT)
                    .withRegel(Regel.BRAL1026)
                    .withMeldingTekst(String.format(Regel.BRAL1026.getOmschrijving(), naam))
                    .withReferentieID(documentUitAH.getCommunicatieID())
                    .withAttribuutNaam("soort")
                    .build());
            }

            zetPartijOpGroep(documentUitAH, meldingen);
        }
        return meldingen;
    }

    private void zetPartijOpGroep(final DocumentBericht documentUitAH, final List<ResultaatMelding> meldingen) {
        if (documentUitAH.getStandaard() != null) {
            final DocumentStandaardGroepBericht groep = documentUitAH.getStandaard();

            final String code = groep.getPartijCode();

            if (!StringUtils.isBlank(code)) {
                if (StringUtils.isNumeric(code)) {
                    final PartijCodeAttribuut partijCodeAttribuut = new PartijCodeAttribuut(Integer.parseInt(groep.getPartijCode()));
                    Partij waarde = null;
                    try {
                        waarde = referentieRepository.vindPartijOpCode(partijCodeAttribuut);
                    } catch (final OnbekendeReferentieExceptie ore) {
                        final ResultaatMelding melding = meldingFactory.maakResultaatMelding(Regel.BRAL1030, documentUitAH, null);
                        meldingen.add(melding);
                    }
                    groep.setPartij(new PartijAttribuut(waarde));
                } else {
                    meldingen.add(ResultaatMelding.builder()
                        .withSoort(SoortMelding.FOUT)
                        .withRegel(Regel.ALG0001)
                        .withMeldingTekst(PARTIJ_CODE + " moet een numerieke waarde bevatten: " + code)
                        .withReferentieID(groep.getCommunicatieID())
                        .withAttribuutNaam(PARTIJ_CODE)
                        .build());
                }
            }
        }
    }

    /**
     * Verrijk de stamgegevens van de opgegeven relatie.
     *
     * @param relatie                       De te verrijken relatie.
     * @param soort                         Soort actie die uitgevoerd wordt.
     * @param soortAdministratieveHandeling soort adm.hand.
     */
    private List<ResultaatMelding> verrijkActieGegevens(final RelatieBericht relatie, final SoortActie soort,
        final SoortAdministratieveHandeling soortAdministratieveHandeling)
    {
        final List<ResultaatMelding> meldingen = new ArrayList<>();
        // Verrijk gegevens relatie
        if (relatie instanceof HuwelijkGeregistreerdPartnerschapBericht) {
            if (relatie.getBetrokkenheden() != null) {
                for (final BetrokkenheidBericht betr : relatie.getBetrokkenheden()) {
                    meldingen.addAll(vulAanPersoon(betr.getPersoon(), soortAdministratieveHandeling));
                }
            }
            meldingen.addAll(verrijkHuwelijkGeregistreerdPartnerschapBericht((HuwelijkGeregistreerdPartnerschapBericht) relatie,
                soortAdministratieveHandeling));
        } else if (relatie instanceof FamilierechtelijkeBetrekkingBericht) {
            meldingen.addAll(verrijkFamilieRechtelijkeBetrekkingBericht((FamilierechtelijkeBetrekkingBericht) relatie, soort,
                soortAdministratieveHandeling));
        }
        return meldingen;
    }

    private List<ResultaatMelding> verrijkFamilieRechtelijkeBetrekkingBericht(final FamilierechtelijkeBetrekkingBericht relatie,
        final SoortActie soort,
        final SoortAdministratieveHandeling soortAdministratieveHandeling)
    {
        final List<ResultaatMelding> meldingen = new ArrayList<>();
        for (final BetrokkenheidBericht betr : relatie.getBetrokkenheden()) {
            if (betr instanceof KindBericht) {
                // Verrijk geboorte gegevens kind
                meldingen.addAll(verrijkGeboorteGegevensKind(soort, soortAdministratieveHandeling, (KindBericht) betr));
            } else if (betr instanceof OuderBericht) {
                final OuderBericht ouderBericht = (OuderBericht) betr;
                if (ouderBericht.getPersoon() != null) {
                    final PersoonBericht ouder = ouderBericht.getPersoon();
                    meldingen.addAll(vulAanPersoon(ouder, soortAdministratieveHandeling));
                }
            }
        }
        return meldingen;
    }

    /**
     * Verrijk de geboorte gegevens kind bericht
     *
     * @param soort                         Soort actie die uitgevoerd wordt.
     * @param soortAdministratieveHandeling soort adm.hand.
     * @param kindBericht                   De te verrijken relatie.
     */
    private List<ResultaatMelding> verrijkGeboorteGegevensKind(final SoortActie soort,
        final SoortAdministratieveHandeling soortAdministratieveHandeling, final KindBericht kindBericht)
    {
        final List<ResultaatMelding> meldingen = new ArrayList<>();
        if (kindBericht.getPersoon() != null) {
            final PersoonBericht kind = kindBericht.getPersoon();
            // GEEN tijdelijke hack, technische sleutel copieren naar identificatienrs.burgerservicenummer
            // bsn moet altijd worden meegegeven en niet als onderdeel van technische sleutel.
            if (null != kind.getGeboorte() && soort == SoortActie.REGISTRATIE_GEBOORTE
                && null != kind.getGeboorte().getDatumGeboorte()
                // TODO: voorlopig bij elk geboorte, MOET het kind in NL geboren zijn.
                // In andere gevallen is dit niet 'Registratie Geboorte' actie.
                // Want de (nieuwe) XSD laat het niet toe een land in te vullen en de database verplicht
                // het.
                && StringUtils.isBlank(kind.getGeboorte().getLandGebiedGeboorteCode()))
            {
                kind.getGeboorte().setLandGebiedGeboorteCode(LandGebiedCodeAttribuut.NL_LAND_CODE_STRING);
            }
            meldingen.addAll(vulAanPersoon(kind, soortAdministratieveHandeling));

            if (soort == SoortActie.REGISTRATIE_GEBOORTE) {
                kind.setSoort(new SoortPersoonAttribuut(SoortPersoon.INGESCHREVENE));
            }
        }
        return meldingen;
    }

    /**
     * Verrijk de HuwelijkGeregistreerdPartnerschapBericht
     *
     * @param relatie                       te verrijken relatie
     * @param soortAdministratieveHandeling soort adm.hand.
     */
    private List<ResultaatMelding> verrijkHuwelijkGeregistreerdPartnerschapBericht(final HuwelijkGeregistreerdPartnerschapBericht relatie,
        final SoortAdministratieveHandeling soortAdministratieveHandeling)
    {
        final List<ResultaatMelding> meldingen = new ArrayList<>();
        if (relatie.getStandaard() == null) {
            return meldingen;
        }
        meldingen.addAll(verrijkHGPGroep(soortAdministratieveHandeling, relatie.getStandaard()));

        // controleer of woonplaatsnaamAanvang en woonplaatsnaamEinde bestaande plaatsen zijn.
        if (relatie.getStandaard().getWoonplaatsnaamAanvang() != null) {
            meldingen.addAll(checkBestaandeWoonplaats(relatie.getStandaard().getWoonplaatsnaamAanvang(), relatie.getStandaard()));
        }
        if (relatie.getStandaard().getWoonplaatsnaamEinde() != null) {
            meldingen.addAll(checkBestaandeWoonplaats(relatie.getStandaard().getWoonplaatsnaamEinde(), relatie.getStandaard()));
        }
        return meldingen;
    }

    /**
     * Verrijk de huwelijk / geregistreerd partnerschap groep vanuit het bericht.
     *
     * @param soortAdministratieveHandeling soort adm.hand.
     * @param groep                         groep uit bericht die verrijkt wordt.
     */
    private List<ResultaatMelding> verrijkHGPGroep(final SoortAdministratieveHandeling soortAdministratieveHandeling,
        final RelatieStandaardGroepBericht groep)
    {
        final List<ResultaatMelding> meldingen = new ArrayList<>();
        if (soortAdministratieveHandeling == SoortAdministratieveHandeling.VOLTREKKING_HUWELIJK_IN_NEDERLAND
            || soortAdministratieveHandeling == SoortAdministratieveHandeling.
            AANGAAN_GEREGISTREERD_PARTNERSCHAP_IN_NEDERLAND)
        {
            groep.setLandGebiedAanvangCode(LandGebiedCodeAttribuut.NL_LAND_CODE_STRING);
        }
        if (soortAdministratieveHandeling == SoortAdministratieveHandeling.ONTBINDING_HUWELIJK_IN_NEDERLAND
            || soortAdministratieveHandeling == SoortAdministratieveHandeling.
            BEEINDIGING_GEREGISTREERD_PARTNERSCHAP_IN_NEDERLAND
            || soortAdministratieveHandeling == SoortAdministratieveHandeling.
            OMZETTING_GEREGISTREERD_PARTNERSCHAP_IN_HUWELIJK)
        {
            groep.setLandGebiedEindeCode(LandGebiedCodeAttribuut.NL_LAND_CODE_STRING);
        }

        meldingen.addAll(controleerLandWaardes(groep));
        meldingen.addAll(controleerGemeenteWaardes(groep));

        // Reden beeindiging relatie
        if (groep.getRedenEindeCode() != null) {
            final WaardeMetMeldingen<RedenEindeRelatieAttribuut> waardeMetMeldingen = vindRedenEindeRelatieOpCode(
                new RedenEindeRelatieCodeAttribuut(groep.getRedenEindeCode()), groep);
            groep.setRedenEinde(waardeMetMeldingen.getWaarde());
            meldingen.addAll(waardeMetMeldingen.getMeldingen());
        }

        return meldingen;
    }

    private List<ResultaatMelding> controleerGemeenteWaardes(final RelatieStandaardGroepBericht groep) {
        final List<ResultaatMelding> meldingen = new ArrayList<>();

        if (!StringUtils.isBlank(groep.getGemeenteAanvangCode())) {
            final String code = groep.getGemeenteAanvangCode();
            if (StringUtils.isNumeric(code)) {
                final WaardeMetMeldingen<GemeenteAttribuut> waardeMetMeldingen = vindGemeenteOpCode(code, groep);
                groep.setGemeenteAanvang(waardeMetMeldingen.getWaarde());
                meldingen.addAll(waardeMetMeldingen.getMeldingen());
            } else {
                meldingen.add(maakMeldingNumeriekeWaarde(groep, GEMEENTE_AANVANG_CODE, code));
            }
        }

        if (!StringUtils.isBlank(groep.getGemeenteEindeCode())) {
            final String code = groep.getGemeenteEindeCode();
            if (StringUtils.isNumeric(code)) {
                final WaardeMetMeldingen<GemeenteAttribuut> waardeMetMeldingen = vindGemeenteOpCode(code, groep);
                groep.setGemeenteEinde(waardeMetMeldingen.getWaarde());
                meldingen.addAll(waardeMetMeldingen.getMeldingen());
            } else {
                meldingen.add(maakMeldingNumeriekeWaarde(groep, GEMEENTE_EINDE_CODE, code));
            }
        }

        return meldingen;
    }

    private List<ResultaatMelding> controleerLandWaardes(final RelatieStandaardGroepBericht groep) {
        final List<ResultaatMelding> meldingen = new ArrayList<>();
        // LandAanvang

        if (!StringUtils.isBlank(groep.getLandGebiedAanvangCode())) {
            final String code = groep.getLandGebiedAanvangCode();
            if (StringUtils.isNumeric(code)) {
                final WaardeMetMeldingen<LandGebiedAttribuut> waardeMetMeldingen = vindLandGebiedOpCode(code, groep);
                groep.setLandGebiedAanvang(waardeMetMeldingen.getWaarde());
                meldingen.addAll(waardeMetMeldingen.getMeldingen());
            } else {
                meldingen.add(maakMeldingNumeriekeWaarde(groep, LAND_AANVANG_CODE, code));
            }
        }

        if (!StringUtils.isBlank(groep.getLandGebiedEindeCode())) {
            final String code = groep.getLandGebiedEindeCode();
            if (StringUtils.isNumeric(code)) {
                final WaardeMetMeldingen<LandGebiedAttribuut> waardeMetMeldingen = vindLandGebiedOpCode(code, groep);
                groep.setLandGebiedEinde(waardeMetMeldingen.getWaarde());
                meldingen.addAll(waardeMetMeldingen.getMeldingen());
            } else {
                meldingen.add(maakMeldingNumeriekeWaarde(groep, LAND_EINDE_CODE, code));
            }
        }

        return meldingen;
    }

    /**
     * Verrijk de stamgegevens van de opgegeven persoon.
     *
     * @param persoon                       De te verrijken persoon.
     * @param soortAdministratieveHandeling soort adm.hand.
     * @param context                       de bericht context.
     */
    private List<ResultaatMelding> verrijkActieGegevens(final PersoonBericht persoon,
        final SoortAdministratieveHandeling soortAdministratieveHandeling,
        final BijhoudingBerichtContext context)
    {
        final List<ResultaatMelding> meldingen = new ArrayList<>();
        verrijkPersoonMetDatabaseID(persoon, context);
        meldingen.addAll(vulAanPersoon(persoon, soortAdministratieveHandeling));
        return meldingen;
    }

    /**
     * Bepaal op basis van de object sleutel de database id van deze persoon. Dit doen we omdat we voor binnengemeentelijke verhuizing de gemeente van de
     * persoon ophalen op basis van de persoon id en deze verrijking in het adres van deze persoon. Zie vulAanAdresGroep().
     *
     * @param persoon de te verrijken persoon
     * @param context de bericht context die we nodig hebben voor de partij
     */
    private void verrijkPersoonMetDatabaseID(final PersoonBericht persoon, final BijhoudingBerichtContext context) {
        if (StringUtils.isNotBlank(persoon.getObjectSleutel())) {
            try {
                final Integer persoonId = objectSleutelService.bepaalPersoonId(
                    persoon.getObjectSleutel(), context.getPartij().getWaarde().getCode().getWaarde());
                persoon.setObjectSleutelDatabaseID(persoonId);
            } catch (final OngeldigeObjectSleutelExceptie exc) {
                // We doen hier niks met de excepties, want de BijhoudingRootObjectOphaalStap zal dit verder afhandelen.
                LOGGER.debug("Objectsleutel niet gevonden voor persoon {}. Foutmelding: {}", persoon.getObjectSleutel(), exc);
            }
        }
    }

    /**
     * Vul aan van een willekeurig 'persoon' bericht alle 'code' naar objecten van alle groepen die deze persoon heeft.
     *
     * @param persoon                       De te verrijken persoon.
     * @param soortAdministratieveHandeling soort adm.hand.
     */
    private List<ResultaatMelding> vulAanPersoon(final PersoonBericht persoon,
        final SoortAdministratieveHandeling soortAdministratieveHandeling)
    {
        final List<ResultaatMelding> meldingen = new ArrayList<>();
        meldingen.addAll(vulAanGeboorteGroep(persoon.getGeboorte()));
        meldingen.addAll(vulAanOverlijdenGroep(persoon.getOverlijden(), soortAdministratieveHandeling));
        meldingen.addAll(vulAanNamen(persoon));
        meldingen.addAll(vulAanAdressen(persoon, soortAdministratieveHandeling));
        meldingen.addAll(vulAanNationaliteiten(persoon));
        meldingen.addAll(vulAanReisdocumenten(persoon));
        meldingen.addAll(vulAanMigratieGroep(persoon.getMigratie(), soortAdministratieveHandeling));
        meldingen.addAll(vulAanVerstrekkingsbeperkingen(persoon));

        return meldingen;
    }

    private List<ResultaatMelding> vulAanVerstrekkingsbeperkingen(final PersoonBericht persoon) {
        final List<ResultaatMelding> meldingen = new ArrayList<>();
        if (persoon.getVerstrekkingsbeperkingen() != null) {
            for (final PersoonVerstrekkingsbeperkingBericht persoonVerstrekkingsbeperkingBericht : persoon.getVerstrekkingsbeperkingen()) {
                meldingen.addAll(vulAanVerstrekkingsbeperkingGroep(persoonVerstrekkingsbeperkingBericht));
            }
        }
        return meldingen;
    }

    private List<ResultaatMelding> vulAanReisdocumenten(final PersoonBericht persoon) {
        final List<ResultaatMelding> meldingen = new ArrayList<>();
        if (persoon.getReisdocumenten() != null) {
            for (final PersoonReisdocumentBericht persoonReisdocumentBericht : persoon.getReisdocumenten()) {
                meldingen.addAll(vulAanReisdocumentGroep(persoonReisdocumentBericht));
            }
        }
        return meldingen;
    }

    private List<ResultaatMelding> vulAanNationaliteiten(final PersoonBericht persoon) {
        final List<ResultaatMelding> meldingen = new ArrayList<>();
        if (persoon.getNationaliteiten() != null) {
            for (final PersoonNationaliteitBericht persoonNationaliteitBericht : persoon.getNationaliteiten()) {
                meldingen.addAll(vulAanNationaliteitGroep(persoonNationaliteitBericht));
            }
        }
        return meldingen;
    }

    private List<ResultaatMelding> vulAanAdressen(final PersoonBericht persoon,
        final SoortAdministratieveHandeling soortAdministratieveHandeling)
    {
        final List<ResultaatMelding> meldingen = new ArrayList<>();
        if (persoon.getAdressen() != null) {
            for (final PersoonAdresBericht persoonAdresBericht : persoon.getAdressen()) {
                if (persoonAdresBericht.getStandaard() != null) {
                    meldingen.addAll(vulAanAdresGroep(persoon, persoonAdresBericht.getStandaard(),
                        soortAdministratieveHandeling));
                }
            }
        }
        return meldingen;
    }

    private List<ResultaatMelding> vulAanNamen(final PersoonBericht persoon) {
        final List<ResultaatMelding> meldingen = new ArrayList<>();
        if (persoon.getNaamgebruik() != null) {
            meldingen.addAll(vulAanNaamgebruikGroep(persoon.getNaamgebruik()));
        }
        if (persoon.getSamengesteldeNaam() != null) {
            meldingen.addAll(vulAanSamenGesteldeNaamGroep(persoon.getSamengesteldeNaam()));
        }
        if (CollectionUtils.isNotEmpty(persoon.getGeslachtsnaamcomponenten())) {
            for (final PersoonGeslachtsnaamcomponentBericht geslachtsnaamcomponent : persoon.getGeslachtsnaamcomponenten()) {
                meldingen.addAll(vulAanGeslachtsnaamComponent(geslachtsnaamcomponent));
            }
        }
        return meldingen;
    }

    /**
     * Vul aan de codes die in de geboorte groep zitten.
     *
     * @param groep het geboorte groep, mag niet null zijn.
     */

    private List<ResultaatMelding> vulAanGeboorteGroep(final PersoonGeboorteGroepBericht groep)
    {
        final List<ResultaatMelding> meldingen = new ArrayList<>();
        if (groep == null) {
            return meldingen;
        }

        // Gemeente geboorte
        final WaardeMetMeldingen<GemeenteAttribuut> gemeenteWaardeMetMeldingen = getGemeenteAttribuutMetMelding(
            groep, groep.getGemeenteGeboorteCode(), GEMEENTE_GEBOORTE_CODE);
        groep.setGemeenteGeboorte(gemeenteWaardeMetMeldingen.getWaarde());
        meldingen.addAll(gemeenteWaardeMetMeldingen.getMeldingen());

        // Land/gebied geboorte
        final WaardeMetMeldingen<LandGebiedAttribuut> landWaardeMetMeldingen = getLandGebiedAttribuutMetMelding(
            groep, groep.getLandGebiedGeboorteCode(), LAND_GEBIED_GEBOORTE_CODE);
        groep.setLandGebiedGeboorte(landWaardeMetMeldingen.getWaarde());
        meldingen.addAll(landWaardeMetMeldingen.getMeldingen());


        // controleer of woonplaatsnaamGeboorte een bestaande plaats is
        final NaamEnumeratiewaardeAttribuut woonplaatsnaam = groep.getWoonplaatsnaamGeboorte();
        if (woonplaatsnaam != null) {
            final boolean woonplaatsBestaat = referentieRepository.isBestaandeWoonplaats(woonplaatsnaam);
            if (!woonplaatsBestaat) {
                meldingen.add(maakMeldingOnbekendeReferentie(groep, Regel.BRAL1020, WOONPLAATSNAAM, woonplaatsnaam.getWaarde()));
            }
        }

        return meldingen;
    }


    private ResultaatMelding maakMeldingOnbekendeReferentie(final BerichtIdentificeerbaar groep, final Regel regel, final String code,
        final Object waarde)
    {
        return ResultaatMelding.builder()
            .withRegel(regel)
            .withMeldingTekst(String.format(regel.getOmschrijving(), waarde))
            .withReferentieID(groep.getCommunicatieID())
            .withAttribuutNaam(code)
            .build();
    }

    private ResultaatMelding maakMeldingNumeriekeWaarde(final BerichtIdentificeerbaar groep, final String attribuutnaam, final String code) {
        return ResultaatMelding.builder()
            .withMeldingTekst(attribuutnaam
                + " moet een numerieke waarde bevatten: " + code)
            .withReferentieID(groep.getCommunicatieID())
            .withAttribuutNaam(attribuutnaam)
            .build();
    }

    /**
     * Vul aan de codes die in de geslachtnaamcomponent zitten.
     *
     * @param geslachtsnaamcomponent het geslachtsnaamcomponent.
     */
    private List<ResultaatMelding> vulAanGeslachtsnaamComponent(final PersoonGeslachtsnaamcomponentBericht geslachtsnaamcomponent)
    {
        final List<ResultaatMelding> meldingen = new ArrayList<>();
        final PersoonGeslachtsnaamcomponentStandaardGroepBericht groep = geslachtsnaamcomponent.getStandaard();
        // In de xsd is het volgnummer weggelaten, omdat we alleen maar '1' ondersteunen.
        // Maar in de database is het verplicht, dus bij deze een default van 1.
        if (geslachtsnaamcomponent.getVolgnummer() == null) {
            geslachtsnaamcomponent.setVolgnummer(new VolgnummerAttribuut(1));
        }
        // Predikaat
        if (groep.getPredicaatCode() != null) {
            final WaardeMetMeldingen<PredicaatAttribuut> predicaatMetMelding = vindPredicaatOpCode(groep.getPredicaatCode(), groep);
            groep.setPredicaat(predicaatMetMelding.getWaarde());
            meldingen.addAll(predicaatMetMelding.getMeldingen());
        }

        // Adellijke Titel geboorte
        if (groep.getAdellijkeTitelCode() != null) {
            final WaardeMetMeldingen<AdellijkeTitelAttribuut> adellijkeTitelMetMelding = vindAdellijkeTitelOpCode(
                groep.getAdellijkeTitelCode(), groep);
            groep.setAdellijkeTitel(adellijkeTitelMetMelding.getWaarde());
            meldingen.addAll(adellijkeTitelMetMelding.getMeldingen());
        }

        meldingen.addAll(controleerVoorkomenVoorvoegselScheidingsteken(groep.getVoorvoegsel(), groep.getScheidingsteken(), groep));

        return meldingen;
    }

    /**
     * Vul aan de codes die in de naam groep zitten. Letop, we kunnen naamgebruik, samengesteldenaam en geslachtsnaam component niet mergen omdat de groep
     * types anders zijn.
     *
     * @param groep het groep.
     */
    private List<ResultaatMelding> vulAanSamenGesteldeNaamGroep(final PersoonSamengesteldeNaamGroepBericht groep)
    {
        final List<ResultaatMelding> meldingen = new ArrayList<>();

        // Predikaat
        if (groep.getPredicaatCode() != null) {
            final WaardeMetMeldingen<PredicaatAttribuut> predicaatMetMelding = vindPredicaatOpCode(groep.getPredicaatCode(), groep);
            groep.setPredicaat(predicaatMetMelding.getWaarde());
            meldingen.addAll(predicaatMetMelding.getMeldingen());
        }

        // Adelijke Titel geboorte
        if (groep.getAdellijkeTitelCode() != null) {
            final WaardeMetMeldingen<AdellijkeTitelAttribuut> adellijkeTitelMetMelding = vindAdellijkeTitelOpCode(
                groep.getAdellijkeTitelCode(), groep);
            groep.setAdellijkeTitel(adellijkeTitelMetMelding.getWaarde());
            meldingen.addAll(adellijkeTitelMetMelding.getMeldingen());
        }

        meldingen.addAll(controleerVoorkomenVoorvoegselScheidingsteken(groep.getVoorvoegsel(), groep.getScheidingsteken(), groep));
        return meldingen;
    }

    /**
     * Vul aan de codes die in de naam groep zitten. Letop, we kunnen naamgebruik, samengesteldenaam en geslachtsnaam component niet mergen omdat de groep
     * types anders zijn.
     *
     * @param groep het groep.
     */
    private List<ResultaatMelding> vulAanNaamgebruikGroep(final PersoonNaamgebruikGroepBericht groep)
    {
        final List<ResultaatMelding> meldingen = new ArrayList<>();

        // Predikaat
        if (groep.getPredicaatNaamgebruikCode() != null) {
            final WaardeMetMeldingen<PredicaatAttribuut> predicaatMetMelding = vindPredicaatOpCode(groep.getPredicaatNaamgebruikCode(), groep);
            groep.setPredicaatNaamgebruik(predicaatMetMelding.getWaarde());
            meldingen.addAll(predicaatMetMelding.getMeldingen());
        }

        // Adelijke Titel geboorte
        if (groep.getAdellijkeTitelNaamgebruikCode() != null) {
            final WaardeMetMeldingen<AdellijkeTitelAttribuut> adellijkeTitelMetMelding = vindAdellijkeTitelOpCode(
                groep.getAdellijkeTitelNaamgebruikCode(), groep);
            groep.setAdellijkeTitelNaamgebruik(adellijkeTitelMetMelding.getWaarde());
            meldingen.addAll(adellijkeTitelMetMelding.getMeldingen());
        }

        // Als naamgebruik niet ingevuld is dan is het by default EIGEN.
        if (groep.getNaamgebruik() == null) {
            groep.setNaamgebruik(new NaamgebruikAttribuut(Naamgebruik.EIGEN));
        }

        meldingen.addAll(controleerVoorkomenVoorvoegselScheidingsteken(groep.getVoorvoegselNaamgebruik(),
            groep.getScheidingstekenNaamgebruik(), groep));

        return meldingen;
    }

    /**
     * Vul aan de codes die in de overlijden groep zitten.
     *
     * @param groep                         het overlijden groep, mag niet null zijn.
     * @param soortAdministratieveHandeling soort adm.hand.
     */
    private List<ResultaatMelding> vulAanOverlijdenGroep(final PersoonOverlijdenGroepBericht groep,
        final SoortAdministratieveHandeling soortAdministratieveHandeling)
    {
        final List<ResultaatMelding> meldingen = new ArrayList<>();
        if (groep == null) {
            return meldingen;
        }

        final WaardeMetMeldingen<GemeenteAttribuut> gemeenteWaardeMetMeldingen =
            getGemeenteAttribuutMetMelding(groep, groep.getGemeenteOverlijdenCode(), GEMEENTE_OVERLIJDEN_CODE);
        groep.setGemeenteOverlijden(gemeenteWaardeMetMeldingen.getWaarde());
        meldingen.addAll(gemeenteWaardeMetMeldingen.getMeldingen());

        if (groep.getWoonplaatsnaamOverlijden() != null) {
            checkBestaandeWoonplaats(groep.getWoonplaatsnaamOverlijden(), groep);
        }

        // ROMEO-605: Verrijking voor BRAL validatie
        if (soortAdministratieveHandeling == SoortAdministratieveHandeling.OVERLIJDEN_IN_NEDERLAND) {
            groep.setLandGebiedOverlijdenCode(LandGebiedCodeAttribuut.NL_LAND_CODE_STRING);
        }

        final WaardeMetMeldingen<LandGebiedAttribuut> landWaardeMetMeldingen = getLandGebiedAttribuutMetMelding(
            groep, groep.getLandGebiedOverlijdenCode(), LAND_OVERLIJDEN_CODE);
        groep.setLandGebiedOverlijden(landWaardeMetMeldingen.getWaarde());
        meldingen.addAll(landWaardeMetMeldingen.getMeldingen());

        return meldingen;
    }

    private WaardeMetMeldingen<GemeenteAttribuut> getGemeenteAttribuutMetMelding(
        final BerichtIdentificeerbaar groep, final String gemeenteCode, final String attribuutnaam)
    {
        GemeenteAttribuut gemeenteAttribuut = null;
        final Set<ResultaatMelding> deelmeldingen = new HashSet<>();
        if (!StringUtils.isBlank(gemeenteCode)) {
            if (StringUtils.isNumeric(gemeenteCode)) {
                final GemeenteCodeAttribuut gemeenteCodeAttribuut = new GemeenteCodeAttribuut(Short.parseShort(gemeenteCode));
                try {
                    final Gemeente gemeente = referentieRepository.vindGemeenteOpCode(gemeenteCodeAttribuut);
                    gemeenteAttribuut = new GemeenteAttribuut(gemeente);
                } catch (final OnbekendeReferentieExceptie ore) {
                    deelmeldingen.add(maakMeldingOnbekendeReferentie(groep, Regel.BRAL1002, GEMEENTE_CODE, gemeenteCodeAttribuut));
                }
            } else {
                deelmeldingen.add(maakMeldingNumeriekeWaarde(groep, attribuutnaam, gemeenteCode));
            }
        }
        return new WaardeMetMeldingen<>(gemeenteAttribuut, deelmeldingen);
    }

    private WaardeMetMeldingen<LandGebiedAttribuut> getLandGebiedAttribuutMetMelding(
        final BerichtIdentificeerbaar groep, final String landCode, final String attribuutnaam)
    {
        LandGebiedAttribuut landAttribuut = null;
        final Set<ResultaatMelding> deelmeldingen = new HashSet<>();
        if (!StringUtils.isBlank(landCode)) {
            if (StringUtils.isNumeric(landCode)) {
                final LandGebiedCodeAttribuut landCodeAttribuut = new LandGebiedCodeAttribuut(Short.parseShort(landCode));
                try {
                    landAttribuut = new LandGebiedAttribuut(referentieRepository.vindLandOpCode(landCodeAttribuut));
                } catch (final OnbekendeReferentieExceptie ore) {
                    deelmeldingen.add(maakMeldingOnbekendeReferentie(groep, Regel.BRAL1001, LAND_CODE, landCodeAttribuut));
                }
            } else {
                deelmeldingen.add(maakMeldingNumeriekeWaarde(groep, attribuutnaam, landCode));
            }
        }
        return new WaardeMetMeldingen<>(landAttribuut, deelmeldingen);
    }


    /**
     * Vul aan de codes die in de nationaliteit standaard groep zitten.
     *
     * @param nationaliteit de nationaliteit.
     */
    private List<ResultaatMelding> vulAanNationaliteitGroep(final PersoonNationaliteitBericht nationaliteit)
    {
        final List<ResultaatMelding> meldingen = new ArrayList<>();
        if (nationaliteit.getNationaliteitCode() != null) {
            final WaardeMetMeldingen<NationaliteitAttribuut> waardeMetMeldingen = vindNationaliteitOpCode(
                nationaliteit.getNationaliteitCode(), nationaliteit);
            nationaliteit.setNationaliteit(waardeMetMeldingen.getWaarde());
            meldingen.addAll(waardeMetMeldingen.getMeldingen());
        }

        final PersoonNationaliteitStandaardGroepBericht groep = nationaliteit.getStandaard();
        if (groep != null) {
            String code = groep.getRedenVerkrijgingCode();
            if (!StringUtils.isBlank(code)) {
                if (StringUtils.isNumeric(code)) {
                    final WaardeMetMeldingen<RedenVerkrijgingNLNationaliteitAttribuut> waardeMetMeldingen =
                        vindRedenVerkregenNlNationaliteitOpCode(new RedenVerkrijgingCodeAttribuut(Short.parseShort(groep.getRedenVerkrijgingCode())),
                            groep);
                    groep.setRedenVerkrijging(waardeMetMeldingen.getWaarde());
                    meldingen.addAll(waardeMetMeldingen.getMeldingen());
                } else {
                    meldingen.add(maakMeldingNumeriekeWaarde(groep, REDEN_VERKRIJGING_CODE, code));
                }
            }

            code = groep.getRedenVerliesCode();
            if (!StringUtils.isBlank(code)) {
                if (StringUtils.isNumeric(code)) {
                    final WaardeMetMeldingen<RedenVerliesNLNationaliteitAttribuut> waardeMetMeldingen =
                        vindRedenVerliesNlNationaliteitOpCode(new RedenVerliesCodeAttribuut(Short.parseShort(groep.getRedenVerliesCode())),
                            groep);
                    groep.setRedenVerlies(waardeMetMeldingen.getWaarde());
                    meldingen.addAll(waardeMetMeldingen.getMeldingen());
                } else {
                    meldingen.add(maakMeldingNumeriekeWaarde(groep, REDEN_VERLIES_CODE, code));
                }
            }
        }
        return meldingen;
    }

    /**
     * Vul aan de codes die in de reisdocument standaard groep zitten.
     *
     * @param reisdocument het reisdocument.
     */
    private List<ResultaatMelding> vulAanReisdocumentGroep(final PersoonReisdocumentBericht reisdocument)
    {
        final List<ResultaatMelding> meldingen = new ArrayList<>();

        if (reisdocument.getSoortCode() != null) {
            final WaardeMetMeldingen<SoortNederlandsReisdocumentAttribuut> waardeMetMeldingen
                = vindSoortReisdocumentOpCode(reisdocument.getSoortCode(), reisdocument);
            reisdocument.setSoort(waardeMetMeldingen.getWaarde());
            meldingen.addAll(waardeMetMeldingen.getMeldingen());
        }

        final PersoonReisdocumentStandaardGroepBericht groep = reisdocument.getStandaard();
        if (groep != null && groep.getAanduidingInhoudingVermissingCode() != null) {

            final WaardeMetMeldingen<AanduidingInhoudingVermissingReisdocumentAttribuut>
                waardeMetMeldingen = vindAanduidingInhoudingVermissingReisdocument(
                new AanduidingInhoudingVermissingReisdocumentCodeAttribuut(groep.getAanduidingInhoudingVermissingCode()),
                groep);
            groep.setAanduidingInhoudingVermissing(waardeMetMeldingen.getWaarde());
            meldingen.addAll(waardeMetMeldingen.getMeldingen());
        }

        return meldingen;
    }

    /**
     * Vul aan de codes die in verstrekkingsbeperking zitten.
     *
     * @param persoonVerstrekkingsbeperkingBericht de verstekkingsbeperking.
     */
    private List<ResultaatMelding> vulAanVerstrekkingsbeperkingGroep(
        final PersoonVerstrekkingsbeperkingBericht persoonVerstrekkingsbeperkingBericht)
    {
        final List<ResultaatMelding> meldingen = new ArrayList<>();

        if (persoonVerstrekkingsbeperkingBericht.getPartijCode() != null) {
            final WaardeMetMeldingen<PartijAttribuut> waardeMetMeldingen = vindPartijOpCode(
                new PartijCodeAttribuut(Integer.parseInt(persoonVerstrekkingsbeperkingBericht.getPartijCode())),
                persoonVerstrekkingsbeperkingBericht, Regel.BRAL0220, null);
            persoonVerstrekkingsbeperkingBericht.setPartij(waardeMetMeldingen.getWaarde());
            meldingen.addAll(waardeMetMeldingen.getMeldingen());
        }

        if (persoonVerstrekkingsbeperkingBericht.getGemeenteVerordeningCode() != null) {
            final WaardeMetMeldingen<PartijAttribuut> waardeMetMeldingen = vindPartijOpCode(
                new PartijCodeAttribuut(Integer.parseInt(persoonVerstrekkingsbeperkingBericht.getGemeenteVerordeningCode())),
                persoonVerstrekkingsbeperkingBericht, Regel.BRAL0221, null);
            persoonVerstrekkingsbeperkingBericht.setGemeenteVerordening(waardeMetMeldingen.getWaarde());
            meldingen.addAll(waardeMetMeldingen.getMeldingen());
        }

        return meldingen;
    }

    /**
     * Vul aan de codes die in de adres standaard groep zitten.
     *
     * @param persoon                       de persoon
     * @param groep                         het adres standaard groep, mag niet null zijn.
     * @param soortAdministratieveHandeling soort adm.hand.
     */
    private List<ResultaatMelding> vulAanAdresGroep(final PersoonBericht persoon, final PersoonAdresStandaardGroepBericht groep,
        final SoortAdministratieveHandeling soortAdministratieveHandeling)
    {
        final List<ResultaatMelding> meldingen = new ArrayList<>();
        meldingen.addAll(zetAangeverAdreshoudingOpGroep(groep));
        meldingen.addAll(zetRedenVerwijzingOpGroep(groep));
        zetGemeenteCodeOpGroep(persoon, groep, soortAdministratieveHandeling);
        meldingen.addAll(zetGemeenteOpGroep(groep));
        if (groep.getWoonplaatsnaam() != null) {
            meldingen.addAll(checkBestaandeWoonplaats(groep.getWoonplaatsnaam(), groep));
        }
        zetLandGebiedCodeOpGroep(groep, soortAdministratieveHandeling);
        meldingen.addAll(zetLandGebiedOpGroep(groep));
        zetIndicatiePersoonAangetroffenOpAdresOpGroep(groep);
        return meldingen;
    }

    private void zetIndicatiePersoonAangetroffenOpAdresOpGroep(final PersoonAdresStandaardGroepBericht groep) {
        if (null != groep.getIndicatiePersoonAangetroffenOpAdres()) {
            groep.setIndicatiePersoonAangetroffenOpAdres(new NeeAttribuut(Nee.N));
        }
    }

    private List<ResultaatMelding> zetLandGebiedOpGroep(final PersoonAdresStandaardGroepBericht groep) {
        final List<ResultaatMelding> meldingen = new ArrayList<>();

        final String code = groep.getLandGebiedCode();

        if (!StringUtils.isBlank(code)) {
            if (StringUtils.isNumeric(code)) {
                final WaardeMetMeldingen<LandGebiedAttribuut> waardeMetMeldingen = vindLandGebiedOpCode(
                    groep.getLandGebiedCode(), groep);
                groep.setLandGebied(waardeMetMeldingen.getWaarde());
                meldingen.addAll(waardeMetMeldingen.getMeldingen());
            } else {
                meldingen.add(maakMeldingNumeriekeWaarde(groep, LAND_GEBIED_MIGRATIE_CODE, code));
            }
        }
        return meldingen;
    }

    private void zetLandGebiedCodeOpGroep(final PersoonAdresStandaardGroepBericht groep,
        final SoortAdministratieveHandeling soortAdministratieveHandeling)
    {
        // ROMEO-605: Verrijking voor BRAL validatie
        if (soortAdministratieveHandeling == SoortAdministratieveHandeling.VERHUIZING_BINNENGEMEENTELIJK
            || soortAdministratieveHandeling == SoortAdministratieveHandeling.VERHUIZING_INTERGEMEENTELIJK)
        {
            groep.setLandGebiedCode(LandGebiedCodeAttribuut.NL_LAND_CODE_STRING);
        }
    }

    private List<ResultaatMelding> zetGemeenteOpGroep(final PersoonAdresStandaardGroepBericht groep) {

        final List<ResultaatMelding> meldingen = new ArrayList<>();
        final String code = groep.getGemeenteCode();
        if (!StringUtils.isBlank(code)) {
            if (StringUtils.isNumeric(code)) {
                final WaardeMetMeldingen<GemeenteAttribuut> waardeMetMeldingen = vindGemeenteOpCode(
                    groep.getGemeenteCode(), groep);
                groep.setGemeente(waardeMetMeldingen.getWaarde());
                meldingen.addAll(waardeMetMeldingen.getMeldingen());
            } else {
                meldingen.add(maakMeldingNumeriekeWaarde(groep, GEMEENTE_CODE, code));
            }
        }
        return meldingen;

    }

    private void zetGemeenteCodeOpGroep(final PersoonBericht persoon, final PersoonAdresStandaardGroepBericht groep,
        final SoortAdministratieveHandeling soortAdministratieveHandeling)
    {
        // ROMEO-605: Verrijking voor BRAL validatie
        if (soortAdministratieveHandeling == SoortAdministratieveHandeling.VERHUIZING_BINNENGEMEENTELIJK
            && persoon.getObjectSleutelDatabaseID() != null)
        {
            // Haal de huidige gemeente op. Indien de sleutel database id niet is gevuld dan is er iets mis met
            // de object sleutel. Dit zal verderop in de BijhoudingRootObjectenOphaalStap worden afgehandeld met
            // een verwerking stoppende fout.
            final Gemeente huidigeGemeente = referentieRepository.findHuidigeGemeenteByPersoonID(
                persoon.getObjectSleutelDatabaseID());
            if (huidigeGemeente != null) {
                groep.setGemeenteCode(huidigeGemeente.getCode().toString());
            }
        }
    }

    private Set<ResultaatMelding> zetRedenVerwijzingOpGroep(final PersoonAdresStandaardGroepBericht groep) {
        if (groep.getRedenWijzigingCode() == null) {
            return Collections.emptySet();
        }
        final WaardeMetMeldingen<RedenWijzigingVerblijfAttribuut> waardeMetMeldingen = vindRedenWijzigingVerblijfOpCode(
            groep.getRedenWijzigingCode(), groep);
        groep.setRedenWijziging(waardeMetMeldingen.getWaarde());
        return waardeMetMeldingen.getMeldingen();
    }

    private Set<ResultaatMelding> zetAangeverAdreshoudingOpGroep(final PersoonAdresStandaardGroepBericht groep) {
        if (groep.getAangeverAdreshoudingCode() == null) {
            return Collections.emptySet();
        }
        final WaardeMetMeldingen<AangeverAttribuut> waardeMetMeldingen = vindAangeverAdreshoudingOpCode(
            groep.getAangeverAdreshoudingCode(), groep);
        groep.setAangeverAdreshouding(waardeMetMeldingen.getWaarde());
        return waardeMetMeldingen.getMeldingen();
    }

    /**
     * Vul aan de codes die in de migratie groep zitten.
     *
     * @param groep          het migratie groep, mag niet null zijn.
     * @param soortHandeling soort AH
     */
    private List<ResultaatMelding> vulAanMigratieGroep(final PersoonMigratieGroepBericht groep,
        final SoortAdministratieveHandeling soortHandeling)
    {
        final List<ResultaatMelding> meldingen = new ArrayList<>();
        if (groep == null) {
            return meldingen;
        }
        // De soort migratie wordt automatisch gezet nav de soort handeling waar we mee bezig zijn.
        if (soortHandeling == SoortAdministratieveHandeling.VERHUIZING_NAAR_BUITENLAND) {
            groep.setSoortMigratie(new SoortMigratieAttribuut(SoortMigratie.EMIGRATIE));
        } else if (soortHandeling == SoortAdministratieveHandeling.VESTIGING_NIET_INGESCHREVENE) {
            groep.setSoortMigratie(new SoortMigratieAttribuut(SoortMigratie.IMMIGRATIE));
        } else {
            throw new IllegalStateException("Onverwachte soort administratieve handeling bij verwerken migratie groep: " + soortHandeling);
        }

        if (groep.getRedenWijzigingMigratieCode() != null) {
            final WaardeMetMeldingen<RedenWijzigingVerblijfAttribuut> waardeMetMeldingen = vindRedenWijzigingVerblijfOpCode(
                groep.getRedenWijzigingMigratieCode(), groep);
            groep.setRedenWijzigingMigratie(waardeMetMeldingen.getWaarde());
            meldingen.addAll(waardeMetMeldingen.getMeldingen());
        }

        if (groep.getAangeverMigratieCode() != null) {
            final WaardeMetMeldingen<AangeverAttribuut> waardeMetMeldingen = vindAangeverAdreshoudingOpCode(
                groep.getAangeverMigratieCode(), groep);
            groep.setAangeverMigratie(waardeMetMeldingen.getWaarde());
            meldingen.addAll(waardeMetMeldingen.getMeldingen());
        }

        final String code = groep.getLandGebiedMigratieCode();

        if (!StringUtils.isBlank(code)) {
            if (StringUtils.isNumeric(code)) {
                final WaardeMetMeldingen<LandGebiedAttribuut> waardeMetMeldingen = vindLandGebiedMigratieOpCode(
                    groep.getLandGebiedMigratieCode(), groep);
                groep.setLandGebiedMigratie(waardeMetMeldingen.getWaarde());
                meldingen.addAll(waardeMetMeldingen.getMeldingen());
            } else {
                meldingen.add(maakMeldingNumeriekeWaarde(groep, LAND_GEBIED_MIGRATIE_CODE, code));
            }
        }

        return meldingen;
    }

    private WaardeMetMeldingen<RedenVerkrijgingNLNationaliteitAttribuut> vindRedenVerkregenNlNationaliteitOpCode(
        final RedenVerkrijgingCodeAttribuut redenVerkrijgingCode, final BerichtIdentificeerbaar groep)
    {
        try {
            return new WaardeMetMeldingen<>(
                new RedenVerkrijgingNLNationaliteitAttribuut(referentieRepository.vindRedenVerkregenNlNationaliteitOpCode(redenVerkrijgingCode)));
        } catch (final OnbekendeReferentieExceptie ore) {
            return new WaardeMetMeldingen<>(maakMelding(Regel.BRAL1021, REDEN_VERKRIJGING_NAAM, groep, redenVerkrijgingCode));
        }
    }

    private WaardeMetMeldingen<RedenVerliesNLNationaliteitAttribuut> vindRedenVerliesNlNationaliteitOpCode(
        final RedenVerliesCodeAttribuut redenVerkrijgingCode, final BerichtIdentificeerbaar groep)
    {
        try {
            return new WaardeMetMeldingen<>(
                new RedenVerliesNLNationaliteitAttribuut(referentieRepository.vindRedenVerliesNLNationaliteitOpCode(redenVerkrijgingCode)));
        } catch (final OnbekendeReferentieExceptie ore) {
            return new WaardeMetMeldingen<>(maakMelding(Regel.BRAL1022, REDEN_VERLIES_CODE, groep, redenVerkrijgingCode));
        }
    }

    private WaardeMetMeldingen<AanduidingInhoudingVermissingReisdocumentAttribuut> vindAanduidingInhoudingVermissingReisdocument(
        final AanduidingInhoudingVermissingReisdocumentCodeAttribuut aanduidingInhoudingVermissingReisdocumentCode,
        final BerichtIdentificeerbaar groep)
    {
        try {
            return new WaardeMetMeldingen<>(new AanduidingInhoudingVermissingReisdocumentAttribuut(referentieRepository
                .vindAanduidingInhoudingVermissingReisdocumentOpCode(aanduidingInhoudingVermissingReisdocumentCode)));
        } catch (final OnbekendeReferentieExceptie ore) {
            return new WaardeMetMeldingen<>(
                maakMelding(Regel.BRAL1023, AANDUIDING_INHOUDING_VERMISSING_REISDOCUMENT_CODE, groep, aanduidingInhoudingVermissingReisdocumentCode));
        }
    }

    private WaardeMetMeldingen<SoortNederlandsReisdocumentAttribuut> vindSoortReisdocumentOpCode(final String reisdocumentCode,
        final BerichtIdentificeerbaar groep)
    {
        try {
            return new WaardeMetMeldingen<>(new SoortNederlandsReisdocumentAttribuut(
                referentieRepository.vindSoortReisdocumentOpCode(new SoortNederlandsReisdocumentCodeAttribuut(reisdocumentCode))));
        } catch (final OnbekendeReferentieExceptie ore) {
            return new WaardeMetMeldingen<>(maakMelding(Regel.BRAL1025, NATIONALITEIT_NAAM, groep, reisdocumentCode));
        }
    }

    private WaardeMetMeldingen<NationaliteitAttribuut> vindNationaliteitOpCode(final String nationaliteitcode, final BerichtIdentificeerbaar groep)
    {
        try {
            return new WaardeMetMeldingen<>(
                new NationaliteitAttribuut(referentieRepository.vindNationaliteitOpCode(new NationaliteitcodeAttribuut(nationaliteitcode))));
        } catch (final OnbekendeReferentieExceptie ore) {
            return new WaardeMetMeldingen<>(maakMelding(Regel.BRAL1017, NATIONALITEIT_NAAM, groep, nationaliteitcode));
        }
    }

    private WaardeMetMeldingen<RedenWijzigingVerblijfAttribuut> vindRedenWijzigingVerblijfOpCode(
        final String redenWijzigingVerblijfCode, final BerichtIdentificeerbaar groep)
    {
        try {
            return new WaardeMetMeldingen<>(
                new RedenWijzigingVerblijfAttribuut(referentieRepository.vindRedenWijzingVerblijfOpCode(new RedenWijzigingVerblijfCodeAttribuut(
                    redenWijzigingVerblijfCode))));
        } catch (final OnbekendeReferentieExceptie ore) {
            return new WaardeMetMeldingen<>(maakMelding(Regel.BRAL1007, REDEN_WIJZIGING_CODE, groep, redenWijzigingVerblijfCode));
        }
    }

    private WaardeMetMeldingen<AangeverAttribuut> vindAangeverAdreshoudingOpCode(final String aangeverAdreshoudingCode,
        final BerichtIdentificeerbaar groep)
    {
        try {
            return new WaardeMetMeldingen<>(
                new AangeverAttribuut(referentieRepository.vindAangeverAdreshoudingOpCode(new AangeverCodeAttribuut(aangeverAdreshoudingCode))));
        } catch (final OnbekendeReferentieExceptie ore) {
            return new WaardeMetMeldingen<>(maakMelding(Regel.BRAL1008, AANGEVER_ADRESHOUDING_CODE, groep, aangeverAdreshoudingCode));
        }
    }

    private WaardeMetMeldingen<AdellijkeTitelAttribuut> vindAdellijkeTitelOpCode(final String adellijkeTitelCode, final BerichtIdentificeerbaar groep)
    {
        try {
            return new WaardeMetMeldingen<>(
                new AdellijkeTitelAttribuut(referentieRepository.vindAdellijkeTitelOpCode(new AdellijkeTitelCodeAttribuut(adellijkeTitelCode))));
        } catch (final OnbekendeReferentieExceptie ore) {
            return new WaardeMetMeldingen<>(maakMelding(Regel.BRAL1015, ADELLIJKE_TITEL_CODE, groep, adellijkeTitelCode));
        }
    }

    private WaardeMetMeldingen<PredicaatAttribuut> vindPredicaatOpCode(final String predicaatCode, final BerichtIdentificeerbaar groep)
    {
        try {
            return new WaardeMetMeldingen<>(new PredicaatAttribuut(referentieRepository.vindPredicaatOpCode(new PredicaatCodeAttribuut(predicaatCode))));
        } catch (final OnbekendeReferentieExceptie ore) {
            return new WaardeMetMeldingen<>(maakMelding(Regel.BRAL1018, PREDIKAAT_CODE, groep, predicaatCode));
        }
    }

    private WaardeMetMeldingen<LandGebiedAttribuut> vindLandGebiedMigratieOpCode(final String landgebiedcode, final BerichtIdentificeerbaar groep)
    {
        try {
            return new WaardeMetMeldingen<>(new LandGebiedAttribuut(
                referentieRepository.vindLandOpCode(new LandGebiedCodeAttribuut(Short.parseShort(landgebiedcode)))));
        } catch (final OnbekendeReferentieExceptie ore) {
            return new WaardeMetMeldingen<>(maakMelding(Regel.BRAL0181, LAND_GEBIED_MIGRATIE_CODE, groep, landgebiedcode));
        }
    }

    private WaardeMetMeldingen<LandGebiedAttribuut> vindLandGebiedOpCode(final String landgebiedcode, final BerichtIdentificeerbaar groep)
    {
        try {
            return new WaardeMetMeldingen<>(new LandGebiedAttribuut(
                referentieRepository.vindLandOpCode(new LandGebiedCodeAttribuut(Short.parseShort(landgebiedcode)))));
        } catch (final OnbekendeReferentieExceptie ore) {
            return new WaardeMetMeldingen<>(maakMelding(Regel.BRAL1001, LAND_CODE, groep, landgebiedcode));
        }
    }

    private WaardeMetMeldingen<GemeenteAttribuut> vindGemeenteOpCode(final String gemeenteCode, final BerichtIdentificeerbaar groep)
    {
        try {
            return new WaardeMetMeldingen<>(
                new GemeenteAttribuut(referentieRepository.vindGemeenteOpCode(new GemeenteCodeAttribuut(Short.parseShort(gemeenteCode)))));
        } catch (final OnbekendeReferentieExceptie ore) {
            return new WaardeMetMeldingen<>(maakMelding(Regel.BRAL1002, GEMEENTE_CODE, groep, gemeenteCode));
        }
    }

    private WaardeMetMeldingen<PartijAttribuut> vindPartijOpCode(final PartijCodeAttribuut partijCode, final BerichtEntiteit berichtEntiteit,
        final Regel regel, final DatabaseObject dbObject)
    {
        try {
            return new WaardeMetMeldingen<>(new PartijAttribuut(referentieRepository.vindPartijOpCode(partijCode)));
        } catch (final OnbekendeReferentieExceptie ore) {
            final ResultaatMelding resultaatMelding = meldingFactory.maakResultaatMelding(regel, berichtEntiteit, dbObject);
            return new WaardeMetMeldingen<>(resultaatMelding);
        }
    }


    private List<ResultaatMelding> checkBestaandeWoonplaats(final NaamEnumeratiewaardeAttribuut woonplaatsnaam,
        final BerichtIdentificeerbaar groep)
    {
        if (referentieRepository.isBestaandeWoonplaats(woonplaatsnaam)) {
            return Collections.emptyList();
        } else {
            return Collections.singletonList(maakMelding(Regel.BRAL1020, WOONPLAATSNAAM, groep, woonplaatsnaam.getWaarde()));
        }
    }

    private WaardeMetMeldingen<RedenEindeRelatieAttribuut> vindRedenEindeRelatieOpCode(
        final RedenEindeRelatieCodeAttribuut redenBeeindigingRelatieCode, final BerichtIdentificeerbaar groep)
    {
        try {
            return new WaardeMetMeldingen<>(new RedenEindeRelatieAttribuut(referentieRepository.vindRedenEindeRelatieOpCode(redenBeeindigingRelatieCode)));
        } catch (final OnbekendeReferentieExceptie ore) {
            return new WaardeMetMeldingen<>(maakMelding(Regel.BRAL1019, REDEN_EINDE_CODE, groep, redenBeeindigingRelatieCode));
        }
    }

    private ResultaatMelding maakMelding(final Regel regel, final String attribuutNaam, final BerichtIdentificeerbaar groep,
        final Object parameters)
    {
        return ResultaatMelding.builder()
                    .withRegel(regel)
                    .withMeldingTekst(String.format(regel.getOmschrijving(), parameters))
                    .withReferentieID(groep.getCommunicatieID())
                    .withAttribuutNaam(attribuutNaam).build();
    }

    /**
     * Controleer of de opgegeven combinatie van voorvoegsel en scheidingsteken bestaat in de stamtabel.
     *
     * @param voorvoegsel     het voorvoegsel
     * @param scheidingsteken het scheidingsteken
     * @param groep           de groep
     * @brp.bedrijfsregel BRAL1031
     */
    private List<ResultaatMelding> controleerVoorkomenVoorvoegselScheidingsteken(final VoorvoegselAttribuut voorvoegsel,
        final ScheidingstekenAttribuut scheidingsteken,
        final BerichtIdentificeerbaar groep)
    {
        if (voorvoegsel != null
            && scheidingsteken != null
            && !this.referentieRepository.bestaatVoorvoegselScheidingsteken(
            voorvoegsel.getWaarde(), scheidingsteken.getWaarde()))
        {
            return Collections.singletonList(ResultaatMelding.builder()
                .withRegel(Regel.BRAL1031)
                .withMeldingTekst(Regel.BRAL1031.getOmschrijving())
                .withReferentieID(groep.getCommunicatieID()).build());
        } else {
            return Collections.emptyList();
        }
    }
}
