/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.bedrijfsregels.impl.afstamming;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

import nl.bzk.brp.business.bedrijfsregels.ActieBedrijfsRegel;
import nl.bzk.brp.dataaccess.repository.PersoonRepository;
import nl.bzk.brp.dataaccess.repository.RelatieRepository;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Datum;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortMelding;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortBetrokkenheid;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortRelatie;
import nl.bzk.brp.model.basis.Identificeerbaar;
import nl.bzk.brp.model.logisch.kern.Actie;
import nl.bzk.brp.model.logisch.kern.Betrokkenheid;
import nl.bzk.brp.model.logisch.kern.FamilierechtelijkeBetrekking;
import nl.bzk.brp.model.logisch.kern.Persoon;
import nl.bzk.brp.model.logisch.kern.PersoonGeslachtsnaamcomponent;
import nl.bzk.brp.model.logisch.kern.Relatie;
import nl.bzk.brp.model.operationeel.kern.PersoonModel;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.model.validatie.MeldingCode;
import nl.bzk.brp.util.AttribuutTypeUtil;
import nl.bzk.brp.util.RelatieUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


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
public class BRBY0106 implements ActieBedrijfsRegel<FamilierechtelijkeBetrekking> {

    private static final Logger LOGGER = LoggerFactory.getLogger(BRBY0106.class);

    // TODO Deze bedrijfsregel houdt er nog geen rekening mee dat het kind wel of niet de NL nationaliteit heeft.
    // TODO bedrijfsregel geldt alleen als het kind de NL nationaliteit heeft.

    @Inject
    private PersoonRepository   persoonRepository;

    @Inject
    private RelatieRepository   relatieRepository;

    @Override
    public String getCode() {
        return "BRBY0106";
    }

    @Override
    public List<Melding> executeer(final FamilierechtelijkeBetrekking huidigeSituatie,
            final FamilierechtelijkeBetrekking nieuweSituatie, final Actie actie)
    {
        List<Melding> meldingen = new ArrayList<Melding>();
        // Haal beide ouders en kind uit bericht
        Persoon ouder1 = null;
        Persoon ouder2 = null;
        Persoon kind = RelatieUtils.haalKindUitRelatie(nieuweSituatie);

        PersoonModel pOuder1 = null;
        PersoonModel pOuder2 = null;

        for (Betrokkenheid betrokkenheid : nieuweSituatie.getBetrokkenheden()) {
            if (SoortBetrokkenheid.OUDER == betrokkenheid.getRol()) {
                if (ouder1 == null) {
                    ouder1 = betrokkenheid.getPersoon();
                    Melding melding = checkOuderHeeftIdentificerendeGroep(ouder1);
                    if (melding != null) {
                        meldingen.add(melding);
                    }
                } else {
                    ouder2 = betrokkenheid.getPersoon();
                    Melding melding = checkOuderHeeftIdentificerendeGroep(ouder2);
                    if (melding != null) {
                        meldingen.add(melding);
                    }
                }
            }
            if (SoortBetrokkenheid.KIND == betrokkenheid.getRol()) {
                kind = betrokkenheid.getPersoon();
                Melding melding = checkKindHeeftGeslacht(kind);
                if (melding != null) {
                    meldingen.add(melding);
                }
            }
            if (meldingen.size() > 0) {
                break;
            }
        }

        if (meldingen.size() == 0) {
            if (ouder1 == null) {
                meldingen.add(new Melding(SoortMelding.FOUT, MeldingCode.ALG0002,
                        "Er is geen ouder meegegeven in het bericht.", (Identificeerbaar) nieuweSituatie, "ouder"));
            }
            if (ouder2 != null) {
                // TODO: bolie, moet omgezet worden naar technische sleutel
                pOuder2 =
                    persoonRepository.findByBurgerservicenummer(ouder2.getIdentificatienummers()
                            .getBurgerservicenummer());
                Melding melding = checkOuderPersistentPersoonBestaat(ouder2, pOuder2);
                if (melding != null) {
                    meldingen.add(melding);
                }
            }
            if (ouder1 != null) {
                // TODO: bolie, moet omgezet worden naar technische sleutel
                pOuder1 =
                    persoonRepository.findByBurgerservicenummer(ouder1.getIdentificatienummers()
                            .getBurgerservicenummer());
                Melding melding = checkOuderPersistentPersoonBestaat(ouder1, pOuder1);
                if (melding != null) {
                    meldingen.add(melding);
                }
            }
        }

        if (meldingen.size() == 0) {
            // hehe, doe nu de daadwerkelijke test.
            Melding melding = controlleerKind(kind, pOuder1, pOuder2);
            if (melding != null) {
                meldingen.add(melding);
            }
        }
        return meldingen;
    }

    /**
     * Check of een ouder een identificerende groep heeft, zo niet maak een melding van.
     *
     * @param ouder de ouder
     * @return de melding als iets fout is, null anders
     */
    private Melding checkOuderHeeftIdentificerendeGroep(final Persoon ouder) {
        if (ouder.getIdentificatienummers() == null) {
            return new Melding(SoortMelding.DEBLOKKEERBAAR, MeldingCode.ALG0002,
                    "Er is een ouder meegegeven zonder identificatienummers in het bericht.", (Identificeerbaar) ouder,
                    "identificatienummers");
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
            return new Melding(SoortMelding.FOUT, MeldingCode.ALG0002,
                    "Er zijn geen geslachtsnaamcomponenten voor het kind meegegeven in het bericht.",
                    (Identificeerbaar) kind, "geslachtsnaamcomponenten");
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
            // TODO: bolie, moet omgezet worden naar technische sleutel
            return new Melding(SoortMelding.FOUT, MeldingCode.REF0001, "Kan ouder niet vinden met BSN: "
                + ouder.getIdentificatienummers().getBurgerservicenummer().getWaarde(),
                    (Identificeerbaar) ouder.getIdentificatienummers(), "burgerservicenummer");
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
    private Melding controlleerKind(final Persoon kind, final PersoonModel pOuder1, final PersoonModel pOuder2) {
        Melding melding = null;
        PersoonGeslachtsnaamcomponent kindcomponent = kind.getGeslachtsnaamcomponenten().iterator().next();
        // Controleer geslachtsnaam nieuw geborene met die van ouders
        boolean geslachtsNaamIsVanEenVanDeOuders = false;
        geslachtsNaamIsVanEenVanDeOuders = isGeslachtsNaamGelijk(pOuder1.getGeslachtsnaamcomponenten(), kindcomponent);

        if (!geslachtsNaamIsVanEenVanDeOuders && pOuder2 != null) {
            geslachtsNaamIsVanEenVanDeOuders =
                isGeslachtsNaamGelijk(pOuder2.getGeslachtsnaamcomponenten(), kindcomponent);
        }

        if (!geslachtsNaamIsVanEenVanDeOuders) {
            LOGGER.error("geslachtsnaam kind is niet gelijk aan een vd. ouders");
            melding =
                new Melding(SoortMelding.DEBLOKKEERBAAR, MeldingCode.BRBY0106, (Identificeerbaar) kindcomponent, "naam");
        } else {
            if (kind.getGeboorte() != null && kind.getGeboorte().getDatumGeboorte() != null) {
                Datum peildatum = kind.getGeboorte().getDatumGeboorte();
                // Zijn de 2 ouders getrouwd of hebben zei een familierechtelijke betrekking:
                final List<? extends Relatie> huwelijkenEnGeregistreerdePartnerschappen =
                    relatieRepository.vindSoortRelatiesMetPersonenInRol(pOuder1, pOuder2, SoortBetrokkenheid.PARTNER,
                            peildatum, SoortRelatie.HUWELIJK, SoortRelatie.GEREGISTREERD_PARTNERSCHAP);

                if (!huwelijkenEnGeregistreerdePartnerschappen.isEmpty()) {
                    // Haal alle familie rechtelijke betrekkingen op waarin beide ouders zitten
                    final List<? extends Relatie> familieRechtelijkeBetrekkingenOuders =
                        relatieRepository.vindSoortRelatiesMetPersonenInRol(pOuder1, pOuder2, SoortBetrokkenheid.OUDER,
                                peildatum, SoortRelatie.FAMILIERECHTELIJKE_BETREKKING);

                    // Controleer geslachtsnaam van eerdere kinderen met die van nieuw geborene
                    for (Relatie familieRechtelijkeBetrekking : familieRechtelijkeBetrekkingenOuders) {
                        for (Betrokkenheid betrokkenheid : familieRechtelijkeBetrekking.getBetrokkenheden()) {
                            if (SoortBetrokkenheid.KIND == betrokkenheid.getRol()
                                && !isGeslachtsNaamGelijk(betrokkenheid.getPersoon().getGeslachtsnaamcomponenten(),
                                        kindcomponent))
                            {
                                LOGGER.error("geslachtsnaam kind is niet gelijk aan een vd. siblings met naam: "
                                    + betrokkenheid.getPersoon().getGeslachtsnaamcomponenten().iterator().next()
                                            .getStandaard().getNaam());
                                melding =
                                    new Melding(SoortMelding.DEBLOKKEERBAAR, MeldingCode.BRBY0106,
                                            (Identificeerbaar) kindcomponent, "naam");
                            }
                        }
                    }
                }
            } else {
                melding =
                    new Melding(SoortMelding.FOUT, MeldingCode.ALG0002, "Geboortedatum van kind is niet opgegeven.");
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
            final Collection<? extends PersoonGeslachtsnaamcomponent> persGeslachtsnaamcomponenten,
            final PersoonGeslachtsnaamcomponent persGeslachtsnaamcomponentKind)
    {
        final String inputNaamKind =
            AttribuutTypeUtil.defaultWaardeIfBlank(persGeslachtsnaamcomponentKind.getStandaard().getNaam(), null);
        final String inputScheidingstekenKind =
            AttribuutTypeUtil.defaultWaardeIfBlank(persGeslachtsnaamcomponentKind.getStandaard().getScheidingsteken(),
                    null);
        final String inputVoorvoegelKind =
            AttribuutTypeUtil
                    .defaultWaardeIfBlank(persGeslachtsnaamcomponentKind.getStandaard().getVoorvoegsel(), null);

        boolean isGelijk = false;
        for (PersoonGeslachtsnaamcomponent comp : persGeslachtsnaamcomponenten) {
            String inputNaamcomponent = AttribuutTypeUtil.defaultWaardeIfBlank(comp.getStandaard().getNaam(), null);
            String inputScheidingstekenComp =
                AttribuutTypeUtil.defaultWaardeIfBlank(comp.getStandaard().getScheidingsteken(), null);
            String inputVoorvoegelComp =
                AttribuutTypeUtil.defaultWaardeIfBlank(comp.getStandaard().getVoorvoegsel(), null);

            isGelijk =
                (StringUtils.equals(inputNaamKind, inputNaamcomponent)
                    && StringUtils.equals(inputScheidingstekenKind, inputScheidingstekenComp) && StringUtils.equals(
                        inputVoorvoegelKind, inputVoorvoegelComp));
        }
        return isGelijk;
    }

}
