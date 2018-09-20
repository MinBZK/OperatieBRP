/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.bedrijfsregels.impl.afstamming;

import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

import nl.bzk.brp.business.bedrijfsregels.BedrijfsRegel;
import nl.bzk.brp.dataaccess.repository.PersoonMdlRepository;
import nl.bzk.brp.dataaccess.repository.RelatieMdlRepository;
import nl.bzk.brp.model.attribuuttype.Datum;
import nl.bzk.brp.model.basis.Identificeerbaar;
import nl.bzk.brp.model.objecttype.logisch.Betrokkenheid;
import nl.bzk.brp.model.objecttype.logisch.Persoon;
import nl.bzk.brp.model.objecttype.logisch.PersoonGeslachtsnaamComponent;
import nl.bzk.brp.model.objecttype.logisch.Relatie;
import nl.bzk.brp.model.objecttype.operationeel.PersoonModel;
import nl.bzk.brp.model.objecttype.operationeel.statisch.SoortBetrokkenheid;
import nl.bzk.brp.model.objecttype.operationeel.statisch.SoortRelatie;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.model.validatie.MeldingCode;
import nl.bzk.brp.model.validatie.SoortMelding;
import nl.bzk.brp.util.AttribuutTypeUtil;
import org.apache.commons.lang.StringUtils;


/**
 * Implementatie van bedrijfsregel BRBY0106.
 * Als bij de inschrijving op grond van geboorteakte de nieuwgeborene de Nederlandse nationaliteit bezit,
 * dan komt de geslachtsnaam van het kind overeen met die van een van de ouders.
 * Als de twee ouders getrouwd zijn of een geregistreerd partnerschap hebben, en ze hebben al gemeenschappelijke
 * kinderen, dan is de geslachtsnaam van de nieuwgeborene gelijk aan de (gekozen) geslachtsnaam van de eerdere
 * kinderen.
 *
 * @brp.bedrijfsregel BRBY0106
 */
public class BRBY0106 implements BedrijfsRegel<Relatie> {
    // TODO Deze bedrijfsregel houdt er nog geen rekening mee dat het kind wel of niet de NL nationaliteit heeft.
    // TODO bedrijfsregel geldt alleen als het kind de NL nationaliteit heeft.

    @Inject
    private PersoonMdlRepository persoonRepository;

    @Inject
    private RelatieMdlRepository relatieRepository;

    @Override
    public String getCode() {
        return "BRBY0106";
    }


    @Override
    public Melding executeer(final Relatie huidigeSituatie, final Relatie nieuweSituatie,
        final Datum datumAanvangGeldigheid)
    {
        Melding melding = null;
        // Haal beide ouders en kind uit bericht
        Persoon ouder1 = null;
        Persoon ouder2 = null;
        Persoon kind = null;

        PersoonModel pOuder1 = null;
        PersoonModel pOuder2 = null;

        for (Betrokkenheid betrokkenheid : nieuweSituatie.getBetrokkenheden()) {
            if (SoortBetrokkenheid.OUDER == betrokkenheid.getRol()) {
                if (ouder1 == null) {
                    ouder1 = betrokkenheid.getBetrokkene();
                    melding = checkOuderHeeftIdentificerendeGroep(ouder1);
                } else {
                    ouder2 = betrokkenheid.getBetrokkene();
                    melding = checkOuderHeeftIdentificerendeGroep(ouder2);
                }
            }
            if (SoortBetrokkenheid.KIND == betrokkenheid.getRol()) {
                kind = betrokkenheid.getBetrokkene();
                melding = checkKindHeeftGeslacht(kind);
            }
            if (null != melding) {
                break;
            }
        }
        if ((melding == null) && (ouder1 == null)) {
            melding = new Melding(SoortMelding.FOUT_OVERRULEBAAR, MeldingCode.ALG0002,
                "Er is geen ouder meegegeven in het bericht.", (Identificeerbaar) nieuweSituatie, "ouder");
        }
        if ((melding == null) && (ouder2 != null)) {
            pOuder2 = persoonRepository.findByBurgerservicenummer(ouder2.getIdentificatieNummers()
                                                                        .getBurgerServiceNummer());
            melding = checkOuderPersistentPersoonBestaat(ouder2, pOuder2);
        }
        if ((melding == null) && (ouder1 != null)) {
            pOuder1 = persoonRepository.findByBurgerservicenummer(ouder1.getIdentificatieNummers()
                                                                        .getBurgerServiceNummer());
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
     *
     * @param ouder de ouder
     * @return de melding als iets fout is, null anders
     */
    private Melding checkOuderHeeftIdentificerendeGroep(final Persoon ouder) {
        if (ouder.getIdentificatieNummers() == null) {
            return new Melding(SoortMelding.FOUT_OVERRULEBAAR, MeldingCode.ALG0002,
                "Er is een ouder meegegeven zonder identificatienummers in het bericht."
                , (Identificeerbaar) ouder, "identificatienummers");
        }
        return null;
    }

    /**
     * Check of een kind een geslacht groep heeft, zo niet maak een melding van.
     *
     * @param kind het kind
     * @return de melding als iets fout is, null anders
     */
    private Melding checkKindHeeftGeslacht(final Persoon kind) {
        // Bolie: kan eigenlijk niet gebeuren. in de xsd is dit verplicht, zowel de groep and het geslacht.
        if (kind.getGeslachtsnaamcomponenten() == null || kind.getGeslachtsnaamcomponenten().isEmpty()) {
            return new Melding(SoortMelding.FOUT_ONOVERRULEBAAR, MeldingCode.ALG0002,
                "Er zijn geen geslachtsnaamcomponenten voor het kind meegegeven in het bericht."
                , (Identificeerbaar) kind, "geslachtsnaamcomponenten");
        }
        return null;
    }

    /**
     * Check of een persistent persoon bestaat in de database.
     *
     * @param ouder logische ouder
     * @param pOuder persistent ouder
     * @return de melding als iets fout is, null anders
     */
    private Melding checkOuderPersistentPersoonBestaat(final Persoon ouder, final PersoonModel pOuder) {
        if (pOuder == null) {
            return new Melding(SoortMelding.FOUT_ONOVERRULEBAAR, MeldingCode.REF0001,
                "Kan ouder niet vinden met BSN: " + ouder.getIdentificatieNummers().getBurgerServiceNummer().getWaarde(),
                (Identificeerbaar) ouder.getIdentificatieNummers(), "burgerservicenummer");
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
    private Melding controlleerKind(final Persoon kind, final PersoonModel pOuder1,
        final PersoonModel pOuder2)
    {
        Melding melding = null;
        PersoonGeslachtsnaamComponent kindComponent = kind.getGeslachtsnaamcomponenten().iterator().next();
        // Controleer geslachtsnaam nieuw geborene met die van ouders
        boolean geslachtsNaamIsVanEenVanDeOuders = false;
        geslachtsNaamIsVanEenVanDeOuders =
            isGeslachtsNaamGelijk(pOuder1.getGeslachtsnaamcomponenten(), kindComponent);

        if (!geslachtsNaamIsVanEenVanDeOuders && pOuder2 != null) {
            geslachtsNaamIsVanEenVanDeOuders =
                isGeslachtsNaamGelijk(pOuder2.getGeslachtsnaamcomponenten(), kindComponent);
        }

        if (!geslachtsNaamIsVanEenVanDeOuders) {
            melding = new Melding(SoortMelding.FOUT_OVERRULEBAAR, MeldingCode.BRBY0106,
                (Identificeerbaar) kindComponent, "naam");
        } else {
            if (kind.getGeboorte() != null && kind.getGeboorte().getDatumGeboorte() != null) {
                Datum peildatum = kind.getGeboorte().getDatumGeboorte();
                // Zijn de 2 ouders getrouwd of hebben zei een familierechtelijke betrekking:
                final List<? extends Relatie> huwelijkenEnGeregistreerdePartnerschappen =
                    relatieRepository.vindSoortRelatiesMetPersonenInRol(
                        pOuder1,
                        pOuder2,
                        SoortBetrokkenheid.PARTNER,
                        peildatum,
                        SoortRelatie.HUWELIJK,
                        SoortRelatie.GEREGISTREERD_PARTNERSCHAP);

                if (!huwelijkenEnGeregistreerdePartnerschappen.isEmpty()) {
                    // Haal alle familie rechtelijke betrekkingen op waarin beide ouders zitten
                    final List<? extends Relatie> familieRechtelijkeBetrekkingenOuders =
                        relatieRepository.vindSoortRelatiesMetPersonenInRol(
                            pOuder1,
                            pOuder2,
                            SoortBetrokkenheid.OUDER,
                            peildatum,
                            SoortRelatie.FAMILIERECHTELIJKE_BETREKKING);

                    // Controleer geslachtsnaam van eerdere kinderen met die van nieuw geborene
                    for (Relatie familieRechtelijkeBetrekking : familieRechtelijkeBetrekkingenOuders) {
                        for (Betrokkenheid betrokkenheid : familieRechtelijkeBetrekking.getBetrokkenheden()) {
                            if (SoortBetrokkenheid.KIND == betrokkenheid.getRol()
                                && !isGeslachtsNaamGelijk(
                                    betrokkenheid.getBetrokkene().getGeslachtsnaamcomponenten(), kindComponent))
                            {
                                melding = new Melding(SoortMelding.FOUT_OVERRULEBAAR, MeldingCode.BRBY0106,
                                    (Identificeerbaar) kindComponent, "naam");
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
        final Collection<? extends PersoonGeslachtsnaamComponent> persGeslachtsnaamcomponenten,
        final PersoonGeslachtsnaamComponent persGeslachtsnaamcomponentKind)
    {
        final String inputNaamKind = AttribuutTypeUtil.defaultWaardeIfBlank(
                persGeslachtsnaamcomponentKind.getGegevens().getNaam(), null);
        final String inputScheidingsTekenKind =
                AttribuutTypeUtil.defaultWaardeIfBlank(
                        persGeslachtsnaamcomponentKind.getGegevens().getScheidingsteken(), null);
        final String inputVoorvoegelKind =
                AttribuutTypeUtil.defaultWaardeIfBlank(
                        persGeslachtsnaamcomponentKind.getGegevens().getVoorvoegsel(), null);

        boolean isGelijk = false;
        for (PersoonGeslachtsnaamComponent comp : persGeslachtsnaamcomponenten) {
            String inputNaamComp = AttribuutTypeUtil.defaultWaardeIfBlank(comp.getGegevens().getNaam(), null);
            String inputScheidingsTekenComp = AttribuutTypeUtil.defaultWaardeIfBlank(
                comp.getGegevens().getScheidingsteken(), null);
            String inputVoorvoegelComp = AttribuutTypeUtil.defaultWaardeIfBlank(
                comp.getGegevens().getVoorvoegsel(), null);

            isGelijk = (StringUtils.equals(inputNaamKind, inputNaamComp)
                && StringUtils.equals(inputScheidingsTekenKind, inputScheidingsTekenComp)
                && StringUtils.equals(inputVoorvoegelKind, inputVoorvoegelComp));
        }
        return isGelijk;
    }

}
