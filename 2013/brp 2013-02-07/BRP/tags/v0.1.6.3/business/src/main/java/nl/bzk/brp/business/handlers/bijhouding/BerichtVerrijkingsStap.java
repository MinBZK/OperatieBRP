/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.handlers.bijhouding;

import javax.inject.Inject;

import nl.bzk.brp.business.dto.BerichtContext;
import nl.bzk.brp.business.dto.BerichtResultaat;
import nl.bzk.brp.business.dto.bijhouding.AbstractBijhoudingsBericht;
import nl.bzk.brp.business.handlers.AbstractBerichtVerwerkingsStap;
import nl.bzk.brp.dataaccess.exceptie.OnbekendeReferentieExceptie;
import nl.bzk.brp.dataaccess.repository.ReferentieDataMdlRepository;
import nl.bzk.brp.model.RootObject;
import nl.bzk.brp.model.attribuuttype.LandCode;
import nl.bzk.brp.model.groep.bericht.PersoonAdresStandaardGroepBericht;
import nl.bzk.brp.model.groep.bericht.PersoonGeboorteGroepBericht;
import nl.bzk.brp.model.groep.bericht.PersoonGeslachtsnaamCompStandaardGroepBericht;
import nl.bzk.brp.model.groep.bericht.PersoonNationaliteitStandaardGroepBericht;
import nl.bzk.brp.model.groep.bericht.RelatieStandaardGroepBericht;
import nl.bzk.brp.model.objecttype.bericht.ActieBericht;
import nl.bzk.brp.model.objecttype.bericht.BetrokkenheidBericht;
import nl.bzk.brp.model.objecttype.bericht.PersoonAdresBericht;
import nl.bzk.brp.model.objecttype.bericht.PersoonBericht;
import nl.bzk.brp.model.objecttype.bericht.PersoonGeslachtsnaamComponentBericht;
import nl.bzk.brp.model.objecttype.bericht.PersoonNationaliteitBericht;
import nl.bzk.brp.model.objecttype.bericht.RelatieBericht;
import nl.bzk.brp.model.objecttype.logisch.Actie;
import nl.bzk.brp.model.objecttype.operationeel.statisch.SoortActie;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.model.validatie.MeldingCode;
import nl.bzk.brp.model.validatie.SoortMelding;
import nl.bzk.brp.util.AttribuutTypeUtil;
import org.apache.commons.collections.CollectionUtils;


/**
 * Stap verrijkt stamgegevens in het bericht zoals Land, Partij en Gemeente.
 * Codes worden omgezet naar stamgegeven entities.
 */
public class BerichtVerrijkingsStap extends
        AbstractBerichtVerwerkingsStap<AbstractBijhoudingsBericht, BerichtResultaat>
{

    @Inject
    private ReferentieDataMdlRepository referentieRepository;

    @Override
    public boolean voerVerwerkingsStapUitVoorBericht(final AbstractBijhoudingsBericht bericht,
            final BerichtContext context, final BerichtResultaat resultaat)
    {
        for (Actie actie : bericht.getBrpActies()) {
            ((ActieBericht) actie).setPartij(context.getPartij());
            try {
                for (RootObject rootObject : actie.getRootObjecten()) {
                    if (rootObject instanceof PersoonBericht) {
                        verrijkActieGegevens((PersoonBericht) rootObject, actie.getSoort());
                    } else if (rootObject instanceof RelatieBericht) {
                        verrijkActieGegevens((RelatieBericht) rootObject, actie.getSoort());
                    } else {
                        throw new UnsupportedOperationException("RootOjbect wordt niet ondersteund door "
                            + "BerichtVerrijkingsStap: " + rootObject.getClass().getSimpleName());
                    }
                }
            } catch (OnbekendeReferentieExceptie ore) {
                final Melding melding =
                    new Melding(SoortMelding.FOUT_ONOVERRULEBAAR, MeldingCode.REF0001, "Waarde "
                        + ore.getReferentieWaarde() + " voor veld " + ore.getReferentieVeldNaam() + " is ongeldig");
                resultaat.voegMeldingToe(melding);
                return STOP_VERWERKING;
            }
        }
        return DOORGAAN_MET_VERWERKING;
    }

    /**
     * Verrijk de stamgegevens van de opgegeven relatie.
     *
     * @param relatie De te verrijken relatie.
     * @param soort Soort actie die uitgevoerd wordt.
     */
    private void verrijkActieGegevens(final RelatieBericht relatie, final SoortActie soort) {
        if (SoortActie.AANGIFTE_GEBOORTE == soort) {
            // Verrijk gegevens relatie
            if (relatie.getGegevens() != null) {
                final RelatieStandaardGroepBericht relatieStandaardGroep = relatie.getGegevens();
                // Land aanvang
                if (AttribuutTypeUtil.isNotBlank(relatieStandaardGroep.getLandAanvangCode())) {
                    relatieStandaardGroep.setLandAanvang(referentieRepository.vindLandOpCode(relatieStandaardGroep
                            .getLandAanvangCode()));
                }
                // LandEinde
                if (AttribuutTypeUtil.isNotBlank(relatieStandaardGroep.getLandEindeCode())) {
                    relatieStandaardGroep.setLandEinde(referentieRepository.vindLandOpCode(relatieStandaardGroep
                            .getLandEindeCode()));
                }
                // Gemeente aanvang
                if (AttribuutTypeUtil.isNotBlank(relatieStandaardGroep.getGemeenteAanvangCode())) {
                    relatieStandaardGroep.setGemeenteAanvang(referentieRepository
                            .vindGemeenteOpCode(relatieStandaardGroep.getGemeenteAanvangCode()));
                }
                // Gemeente einde
                if (AttribuutTypeUtil.isNotBlank(relatieStandaardGroep.getGemeenteEindeCode())) {
                    relatieStandaardGroep.setGemeenteEinde(referentieRepository
                            .vindGemeenteOpCode(relatieStandaardGroep.getGemeenteEindeCode()));
                }
                // Woonplaats aanvang
                if (AttribuutTypeUtil.isNotBlank(relatieStandaardGroep.getWoonPlaatsAanvangCode())) {
                    relatieStandaardGroep.setWoonPlaatsAanvang(referentieRepository
                            .vindWoonplaatsOpCode(relatieStandaardGroep.getWoonPlaatsAanvangCode()));
                }
                // Woonplaats einde
                if (AttribuutTypeUtil.isNotBlank(relatieStandaardGroep.getWoonPlaatsEindeCode())) {
                    relatieStandaardGroep.setWoonPlaatsEinde(referentieRepository
                            .vindWoonplaatsOpCode(relatieStandaardGroep.getWoonPlaatsEindeCode()));
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
                        geboorte.setGemeenteGeboorte(referentieRepository.vindGemeenteOpCode(geboorte
                                .getGemeenteGeboorteCode()));
                    }
                    // Land geboorte
                    if (AttribuutTypeUtil.isNotBlank(geboorte.getLandGeboorteCode())) {
                        geboorte.setLandGeboorte(referentieRepository.vindLandOpCode(geboorte.getLandGeboorteCode()));
                    }
                    // Woonplaats geboorte
                    if (AttribuutTypeUtil.isNotBlank(geboorte.getWoonplaatsGeboorteCode())) {
                        geboorte.setWoonplaatsGeboorte(referentieRepository.vindWoonplaatsOpCode(geboorte
                                .getWoonplaatsGeboorteCode()));
                    }
                }

                if (CollectionUtils.isNotEmpty(kind.getGeslachtsnaamcomponenten())) {
                    for (PersoonGeslachtsnaamComponentBericht geslachtsnaamComponent : kind
                            .getGeslachtsnaamcomponenten())
                    {
                        final PersoonGeslachtsnaamCompStandaardGroepBericht geslachtsnaamCompGegevens =
                            geslachtsnaamComponent.getGegevens();

                        // Predikaat
                        if (AttribuutTypeUtil.isNotBlank(geslachtsnaamCompGegevens.getPredikaatCode())) {
                            geslachtsnaamCompGegevens.setPredikaat(referentieRepository
                                    .vindPredikaatOpCode(geslachtsnaamCompGegevens.getPredikaatCode()));
                        }

                        // Adelijke Titel geboorte
                        if (AttribuutTypeUtil.isNotBlank(geslachtsnaamCompGegevens.getAdellijkeTitelCode())) {
                            geslachtsnaamCompGegevens.setAdellijkeTitel(referentieRepository
                                    .vindAdellijkeTitelOpCode(geslachtsnaamCompGegevens.getAdellijkeTitelCode()));
                        }
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
     */
    private void verrijkActieGegevens(final PersoonBericht persoon, final SoortActie soort) {
        if (SoortActie.VERHUIZING == soort) {
            for (PersoonAdresBericht persoonAdresBericht : persoon.getAdressen()) {
                if (persoonAdresBericht.getGegevens() != null) {
                    final PersoonAdresStandaardGroepBericht adresGegevens = persoonAdresBericht.getGegevens();
                    if (AttribuutTypeUtil.isNotBlank(adresGegevens.getRedenWijzigingAdresCode())) {
                        adresGegevens.setRedenwijziging(referentieRepository.vindRedenWijzingAdresOpCode(adresGegevens
                                .getRedenWijzigingAdresCode()));
                    }
                    if (AttribuutTypeUtil.isNotBlank(adresGegevens.getCode())) {
                        adresGegevens.setWoonplaats(referentieRepository.vindWoonplaatsOpCode(adresGegevens.getCode()));
                    }
                    if (AttribuutTypeUtil.isNotBlank(adresGegevens.getGemeenteCode())) {
                        adresGegevens.setGemeente(referentieRepository.vindGemeenteOpCode(adresGegevens
                                .getGemeenteCode()));
                    }
                    if (AttribuutTypeUtil.isNotBlank(adresGegevens.getLandCode())) {
                        adresGegevens.setLand(referentieRepository.vindLandOpCode(adresGegevens.getLandCode()));
                    } else {
                        // TODO tijdelijke fix, als er geen land opgegeven is dan tijdelijk land op NL zetten
                        adresGegevens.setLand(referentieRepository.vindLandOpCode(new LandCode("6030")));
                    }
                }
            }
        } else if (SoortActie.REGISTRATIE_NATIONALITEIT == soort) {
            for (PersoonNationaliteitBericht persoonNationaliteitBericht : persoon.getNationaliteiten()) {
                if (AttribuutTypeUtil.isNotBlank(persoonNationaliteitBericht.getNationaliteitCode())) {
                    persoonNationaliteitBericht.setNationaliteit(referentieRepository
                            .vindNationaliteitOpCode(persoonNationaliteitBericht.getNationaliteitCode()));
                }
                if (persoonNationaliteitBericht.getGegevens() != null) {
                    PersoonNationaliteitStandaardGroepBericht nationGegevens =
                        persoonNationaliteitBericht.getGegevens();
                    if (AttribuutTypeUtil.isNotBlank(nationGegevens.getRedenVerkregenNlNationaliteitNaam())) {
                        nationGegevens.setRedenVerkregenNlNationaliteit(referentieRepository
                                .vindRedenVerkregenNlNationaliteitOpNaam(nationGegevens
                                        .getRedenVerkregenNlNationaliteitNaam()));
                    }
                    if (AttribuutTypeUtil.isNotBlank(nationGegevens.getRedenVerliesNlNationaliteitNaam())) {
                        nationGegevens.setRedenVerliesNlNationaliteit(referentieRepository
                                .vindRedenVerliesNLNationaliteitOpNaam(nationGegevens
                                        .getRedenVerliesNlNationaliteitNaam()));
                    }
                }
            }
        }
    }
}
