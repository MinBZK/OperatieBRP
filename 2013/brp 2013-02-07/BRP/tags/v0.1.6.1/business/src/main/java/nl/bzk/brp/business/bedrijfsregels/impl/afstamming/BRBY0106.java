/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.bedrijfsregels.impl.afstamming;

import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import nl.bzk.brp.business.bedrijfsregels.BedrijfsRegel;
import nl.bzk.brp.dataaccess.repository.PersoonRepository;
import nl.bzk.brp.dataaccess.repository.RelatieRepository;
import nl.bzk.brp.model.gedeeld.SoortBetrokkenheid;
import nl.bzk.brp.model.gedeeld.SoortRelatie;
import nl.bzk.brp.model.logisch.Betrokkenheid;
import nl.bzk.brp.model.logisch.Persoon;
import nl.bzk.brp.model.logisch.PersoonGeslachtsnaamcomponent;
import nl.bzk.brp.model.logisch.Relatie;
import nl.bzk.brp.model.operationeel.kern.PersistentBetrokkenheid;
import nl.bzk.brp.model.operationeel.kern.PersistentPersoon;
import nl.bzk.brp.model.operationeel.kern.PersistentPersoonGeslachtsnaamcomponent;
import nl.bzk.brp.model.operationeel.kern.PersistentRelatie;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.model.validatie.MeldingCode;
import nl.bzk.brp.model.validatie.SoortMelding;
import org.apache.commons.lang.StringUtils;


/**
 * Implementatie van bedrijfsregel BRBY0106.
 * Als bij de inschrijving op grond van geboorteakte de nieuwgeborene de Nederlandse nationaliteit bezit,
 * dan komt de geslachtsnaam van het kind overeen met die van een van de ouders.
 * Als de twee ouders getrouwd zijn of een geregistreerd partnerschap hebben, en ze hebben al gemeenschappelijke
 * kinderen, dan is de geslachtsnaam van de nieuwgeborene gelijk aan de (gekozen) geslachtsnaam van de eerdere kinderen.
 *
 * @brp.bedrijfsregel BRBY0106
 */
public class BRBY0106 implements BedrijfsRegel<PersistentRelatie, Relatie> {
    // TODO Deze bedrijfsregel houdt er nog geen rekening mee dat het kind wel of niet de NL nationaliteit heeft.
    // TODO bedrijfsregel geldt alleen als het kind de NL nationaliteit heeft.

    @Inject
    private PersoonRepository persoonRepository;

    @Inject
    private RelatieRepository relatieRepository;

    @Override
    public String getCode() {
        return "BRBY0106";
    }


    @Override
    public Melding executeer(final PersistentRelatie huidigeSituatie, final Relatie nieuweSituatie,
            final Integer datumAanvangGeldigheid)
    {
        Melding melding = null;
        // Haal beide ouders en kind uit bericht
        Persoon ouder1 = null;
        Persoon ouder2 = null;
        Persoon kind = null;
        PersistentPersoon pOuder1 = null;
        PersistentPersoon pOuder2 = null;

        for (Betrokkenheid betrokkenheid : nieuweSituatie.getBetrokkenheden()) {
            if (SoortBetrokkenheid.OUDER == betrokkenheid.getSoortBetrokkenheid()) {
                if (ouder1 == null) {
                    ouder1 = betrokkenheid.getBetrokkene();
                    melding = checkOuderHeeftIdentificerendeGroep(ouder1);
                } else {
                    ouder2 = betrokkenheid.getBetrokkene();
                    melding = checkOuderHeeftIdentificerendeGroep(ouder2);
                }
            }
            if (SoortBetrokkenheid.KIND == betrokkenheid.getSoortBetrokkenheid()) {
                kind = betrokkenheid.getBetrokkene();
                melding = checkKindHeeftGeslacht(kind);
            }
            if (null != melding) {
                break;
            }
        }
        if ((melding == null) && (ouder1 == null)) {
            melding = new Melding(SoortMelding.FOUT_OVERRULEBAAR, MeldingCode.ALG0002,
                    "Er is geen ouder meegegeven in het bericht." , nieuweSituatie, "ouder");
        }
        if ((melding == null) && (ouder2 != null)) {
            pOuder2 = persoonRepository.findByBurgerservicenummer(ouder2.getIdentificatienummers()
                            .getBurgerservicenummer());
            melding = checkOuderPersistentPersoonBestaat(ouder2, pOuder2);
        }
        if ((melding == null) && (ouder1 != null)) {
            pOuder1 = persoonRepository.findByBurgerservicenummer(ouder1.getIdentificatienummers()
                            .getBurgerservicenummer());
            melding = checkOuderPersistentPersoonBestaat(ouder1, pOuder1);
        }

        if (null == melding) {
            // hehe, doe nu de daadwerkelijke test.
            melding = controlleerKind(kind, pOuder1, pOuder2);
        }
        return melding;
    }

    /**
     * Check of een ouder een identificerende groep heeft, zo niet maak een melding van.
     * @param ouder de ouder
     * @return de melding als iets fout is, null anders
     */
    private Melding checkOuderHeeftIdentificerendeGroep(final Persoon ouder) {
        if (ouder.getIdentificatienummers() == null) {
            return new Melding(SoortMelding.FOUT_OVERRULEBAAR, MeldingCode.ALG0002,
                    "Er is een ouder meegegeven zonder identificatienummers in het bericht."
                    , ouder, "identificatienummers");
        }
        return null;
    }

    /**
     * Check of een kind een geslacht groep heeft, zo niet maak een melding van.
     * @param kind het kind
     * @return de melding als iets fout is, null anders
     */
    private Melding checkKindHeeftGeslacht(final Persoon kind) {
        // Bolie: kan eigenlijk niet gebeuren. in de xsd is dit verplicht, zowel de groep and het geslacht.
        if (kind.getGeslachtsnaamcomponenten() == null || kind.getGeslachtsnaamcomponenten().isEmpty()) {
            return new Melding(SoortMelding.FOUT_ONOVERRULEBAAR, MeldingCode.ALG0002,
                    "Er zijn geen geslachtsnaamcomponenten voor het kind meegegeven in het bericht."
                    , kind, "geslachtsnaamcomponenten");
        }
        return null;
    }

    /**
     * Check of een persistent persoon bestaat in de database.
     * @param ouder logische ouder
     * @param pOuder persistent ouder
     * @return de melding als iets fout is, null anders
     */
    private Melding checkOuderPersistentPersoonBestaat(final Persoon ouder, final PersistentPersoon pOuder) {
        if (pOuder == null) {
            return new Melding(SoortMelding.FOUT_ONOVERRULEBAAR, MeldingCode.REF0001,
                        "Kan ouder niet vinden met BSN: " + ouder.getIdentificatienummers().getBurgerservicenummer(),
                    ouder.getIdentificatienummers(), "burgerservicenummer");
        }
        return null;
    }

    /**
     * Methode voert de controlle uit.
     *
     * @param kind Persoon
     * @param pOuder1 Persoon
     * @param pOuder2 Persoon
     * @return melding of null
     */
    private Melding controlleerKind(final Persoon kind, final PersistentPersoon pOuder1,
        final PersistentPersoon pOuder2)
    {
        Melding melding = null;

        // Controleer geslachtsnaam nieuw geborene met die van ouders
        boolean geslachtsNaamIsVanEenVanDeOuders = false;
        geslachtsNaamIsVanEenVanDeOuders =
            isGeslachtsNaamGelijk(pOuder1.getPersoonGeslachtsnaamcomponenten(),
                    kind.getGeslachtsnaamcomponenten().get(0));

        if (!geslachtsNaamIsVanEenVanDeOuders && pOuder2 != null) {
            geslachtsNaamIsVanEenVanDeOuders =
                isGeslachtsNaamGelijk(pOuder2.getPersoonGeslachtsnaamcomponenten(), kind.getGeslachtsnaamcomponenten()
                        .get(0));
        }

        if (!geslachtsNaamIsVanEenVanDeOuders) {
            melding = new Melding(SoortMelding.FOUT_OVERRULEBAAR, MeldingCode.BRBY0106,
                    kind.getGeslachtsnaamcomponenten().get(0), "naam");
        } else {
            if (kind.getGeboorte() != null && kind.getGeboorte().getDatumGeboorte() != null) {
                Integer peildatum = kind.getGeboorte().getDatumGeboorte();
                // Zijn de 2 ouders getrouwd of hebben zei een familierechtelijke betrekking:
                final List<PersistentRelatie> huwelijkenEnGeregistreerdePartnerschappen =
                        relatieRepository.vindSoortRelatiesMetPersonenInRol(
                                pOuder1,
                                pOuder2,
                                SoortBetrokkenheid.PARTNER,
                                peildatum,
                                SoortRelatie.HUWELIJK,
                                SoortRelatie.GEREGISTREERD_PARTNERSCHAP);

                if (!huwelijkenEnGeregistreerdePartnerschappen.isEmpty()) {
                    // Haal alle familie rechtelijke betrekkingen op waarin beide ouders zitten
                    final List<PersistentRelatie> familieRechtelijkeBetrekkingenOuders =
                            relatieRepository.vindSoortRelatiesMetPersonenInRol(
                                    pOuder1,
                                    pOuder2,
                                    SoortBetrokkenheid.OUDER,
                                    peildatum,
                                    SoortRelatie.FAMILIERECHTELIJKE_BETREKKING);

                    // Controleer geslachtsnaam van eerdere kinderen met die van nieuw geborene
                    for (PersistentRelatie familieRechtelijkeBetrekking : familieRechtelijkeBetrekkingenOuders) {
                        for (PersistentBetrokkenheid betrokkenheid : familieRechtelijkeBetrekking.getBetrokkenheden()) {
                            if (SoortBetrokkenheid.KIND == betrokkenheid.getSoortBetrokkenheid()
                                && !isGeslachtsNaamGelijk(
                                    betrokkenheid.getBetrokkene().getPersoonGeslachtsnaamcomponenten(),
                                    kind.getGeslachtsnaamcomponenten().get(0)))
                            {
                                melding = new Melding(SoortMelding.FOUT_OVERRULEBAAR, MeldingCode.BRBY0106,
                                        kind.getGeslachtsnaamcomponenten().get(0),
                                        "naam");
                            }
                        }
                    }
                }
            } else {
                melding =
                    new Melding(SoortMelding.FOUT_ONOVERRULEBAAR, MeldingCode.ALG0002,
                            "Geboortedatum van kind is niet opgegeven.");
            }
        }

        return melding;
    }

    /**
     * Controleert of geslachtsnaam component van een kind is gelijk aan een geslachtsnaam component van een ouder.
     *
     * @param persGeslachtsnaamcomponenten Geslachtsnaam componenten van ouder.
     * @param persGeslachtsnaamcomponentKind Geslachtsnaam component van kind.
     * @return True indien geslachtsnaam componenten gelijk zijn.
     */
    private boolean isGeslachtsNaamGelijk(
            final Set<PersistentPersoonGeslachtsnaamcomponent> persGeslachtsnaamcomponenten,
            final PersoonGeslachtsnaamcomponent persGeslachtsnaamcomponentKind)
    {
        final String inputNaamKind = StringUtils.defaultIfBlank(persGeslachtsnaamcomponentKind.getNaam(), null);
        final String inputScheidingsTekenKind =
            StringUtils.defaultIfBlank(persGeslachtsnaamcomponentKind.getScheidingsTeken(), null);
        final String inputVoorvoegelKind =
            StringUtils.defaultIfBlank(persGeslachtsnaamcomponentKind.getVoorvoegsel(), null);

        boolean isGelijk = false;
        for (PersistentPersoonGeslachtsnaamcomponent comp : persGeslachtsnaamcomponenten) {
            String inputNaamComp = StringUtils.defaultIfBlank(comp.getNaam(), null);
            String inputScheidingsTekenComp = StringUtils.defaultIfBlank(comp.getScheidingsteken(), null);
            String inputVoorvoegelComp = StringUtils.defaultIfBlank(comp.getVoorvoegsel(), null);

            isGelijk = (StringUtils.equals(inputNaamKind, inputNaamComp)
                 && StringUtils.equals(inputScheidingsTekenKind, inputScheidingsTekenComp)
                 && StringUtils.equals(inputVoorvoegelKind, inputVoorvoegelComp));
        }
        return isGelijk;
    }

}
