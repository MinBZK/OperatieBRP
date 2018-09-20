/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.actie;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import nl.bzk.brp.business.actie.validatie.GeboorteActieValidator;
import nl.bzk.brp.business.dto.BerichtContext;
import nl.bzk.brp.dataaccess.exceptie.ObjectReedsBestaandExceptie;
import nl.bzk.brp.dataaccess.exceptie.OnbekendeReferentieExceptie;
import nl.bzk.brp.dataaccess.repository.PersoonRepository;
import nl.bzk.brp.dataaccess.repository.RelatieRepository;
import nl.bzk.brp.model.RootObject;
import nl.bzk.brp.model.gedeeld.SoortPersoon;
import nl.bzk.brp.model.logisch.BRPActie;
import nl.bzk.brp.model.logisch.Betrokkenheid;
import nl.bzk.brp.model.logisch.Persoon;
import nl.bzk.brp.model.logisch.Relatie;
import nl.bzk.brp.model.operationeel.kern.PersistentActie;
import nl.bzk.brp.model.operationeel.kern.PersistentPersoon;
import nl.bzk.brp.model.operationeel.kern.PersistentRelatie;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.model.validatie.MeldingCode;
import nl.bzk.brp.model.validatie.SoortMelding;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;


/** Actie uitvoerder voor verhuizing. */
@Component
public class InschrijvingGeboorteUitvoerder extends AbstractActieUitvoerder {

    @Inject
    private PersoonRepository      persoonRepository;

    @Inject
    private RelatieRepository      relatieRepository;

    @Inject
    private GeboorteActieValidator geboorteActieValidator;

    @Override
    List<Melding> valideerActieGegevens(final BRPActie actie) {
        return geboorteActieValidator.valideerActie(actie);
    }

    @Override
    List<Melding> verwerkActie(final BRPActie actie, final BerichtContext berichtContext) {
        return voerInschrijvingGeboorteDoor(actie, berichtContext);
    }

    @Override
    List<Melding> bereidActieVerwerkingVoor(final BRPActie actie, final BerichtContext berichtContext) {
        if (actie == null) {
            throw new IllegalArgumentException("Actie kan niet leeg/null zijn bij uitvoering van de actie.");
        }

        List<Melding> meldingen;
        Relatie nieuweFamilieRechtelijkeBetrekking = haalRelatieUitActie(actie);
        if (nieuweFamilieRechtelijkeBetrekking == null) {
            meldingen =
                Arrays.asList(new Melding(SoortMelding.FOUT_ONOVERRULEBAAR, MeldingCode.ALG0002,
                    MeldingCode.ALG0002.getOmschrijving() + ": Geen relatie gevonden"));
        } else {
            meldingen = vervangOudersInRelatieVoorOudersUitRepository(nieuweFamilieRechtelijkeBetrekking);

            if (meldingen == null) {
                Relatie relatie = (Relatie) actie.getRootObjecten().get(0);
                if (null != relatie && null != relatie.getKindBetrokkenheid()
                        && null != relatie.getKindBetrokkenheid().getBetrokkene())
                {
                    Persoon kind = relatie.getKindBetrokkenheid().getBetrokkene();
                    // Administratie spul: houdt bij de hoofdpersoon
                    berichtContext.voegHoofdPersoonToe(kind);

                    // houdt nu bij alle personen die gerakt zijn.
                    if (nieuweFamilieRechtelijkeBetrekking.getBetrokkenheden() != null) {
                        for (Betrokkenheid betr : nieuweFamilieRechtelijkeBetrekking.getBetrokkenheden()) {
                            Persoon persoon = betr.getBetrokkene();
                            String bsn = persoon.getIdentificatienummers().getBurgerservicenummer();
                            if (StringUtils.isNotBlank(bsn)
                                    && !bsn.equals(kind.getIdentificatienummers().getBurgerservicenummer()))
                            {
                                berichtContext.voegBijPersoonToe(betr.getBetrokkene());
                            }
                        }
                    }
                }
            }
        }

        return meldingen;
    }

    /**
     * Haalt de (eerste) relatie uit de actie en retourneert deze. Indien er geen Relatie als root object in de actie
     * is opgenomen dan zal <code>null</code> worden geretourneerd.
     *
     * @param actie de actie waaruit de relatie zal worden opgehaald.
     * @return de relatie die als root object in de actie is opgenomen of <code>null</code> indien die er niet is.
     */
    private Relatie haalRelatieUitActie(final BRPActie actie) {
        Relatie relatie = null;
        if (actie.getRootObjecten() != null) {
            for (RootObject rootObject : actie.getRootObjecten()) {
                if (rootObject instanceof Relatie) {
                    relatie = (Relatie) rootObject;
                }
            }
        }
        return relatie;
    }

    /**
     * Vervangt de ouders in de familierechtelijke betrekking relatie door hun representaties uit de repository. Dit
     * zodat de technische sleutels aanwezig zijn daar dit vereist is voor de juist opslag van de relatie.
     *
     * @param familieRechtelijkeBetrekking de relatie met de ouders opgenomen als betrokkenen.
     * @return een lijst van meldingen indien de ouders niet correct konden worden opgehaald.
     */
    private List<Melding> vervangOudersInRelatieVoorOudersUitRepository(final Relatie familieRechtelijkeBetrekking) {
        List<Melding> meldingen = new ArrayList<Melding>();
        if (familieRechtelijkeBetrekking.getBetrokkenheden() == null) {
            meldingen.add(new Melding(SoortMelding.FOUT_ONOVERRULEBAAR, MeldingCode.ALG0002,
                "Er zijn geen betrokkenheden (kind en/of ouder) opgenomen terwijl deze wel verwacht worden."));
        } else {
            List<String> bekendeOuders = new ArrayList<String>();

            for (Betrokkenheid betrokkenheid : familieRechtelijkeBetrekking.getBetrokkenheden()) {
                if (betrokkenheid.isOuder()) {
                    // ToDo: Indicatie ouder wordt hier gezet, maar mogelijk moet dat ergens anders!
                    betrokkenheid.setIndOuder(Boolean.TRUE);

                    if (betrokkenheid.getBetrokkene() == null) {
                        meldingen.add(new Melding(SoortMelding.FOUT_ONOVERRULEBAAR, MeldingCode.ALG0002,
                            "Er is geen ouder opgegeven"));
                    } else {
                        Persoon ouder = betrokkenheid.getBetrokkene();
                        if (ouder.getIdentificatienummers() == null
                            || ouder.getIdentificatienummers().getBurgerservicenummer() == null)
                        {
                            meldingen
                                    .add(new Melding(SoortMelding.FOUT_ONOVERRULEBAAR, MeldingCode.ALG0002,
                                            "Het is verplicht om een BSN op te geven bij een ouder."));
                        } else {
                            String bsn = ouder.getIdentificatienummers().getBurgerservicenummer();

                            // ToDo: Hier hoort ook het tijdstiplaatstewijziging gebruikt te worden, maar nu nog niet
                            ouder = persoonRepository.haalPersoonOpMetBurgerservicenummer(bsn);
                            if (ouder != null) {
                                // TODO: bolie: tijdelijke validatie (2x ouders, zelfde persoon =>FOUT_ONOVERRULEBAAR
                                if (bekendeOuders.contains(bsn)) {
                                    meldingen.add(new Melding(SoortMelding.FOUT_ONOVERRULEBAAR, MeldingCode.REF0001,
                                            "Het bericht bevat 2 ouders met hetzelfde BSN."));
                                } else {
                                    betrokkenheid.setBetrokkene(ouder);
                                    bekendeOuders.add(bsn);
                                }
                            } else {
                                meldingen.add(new Melding(SoortMelding.FOUT_ONOVERRULEBAAR, MeldingCode.REF0001, String
                                    .format("Ouder (bsn: %s) is niet bekend", bsn)));
                            }
                        }
                    }
                }
            }
        }

        if (meldingen.isEmpty()) {
            meldingen = null;
        }
        return meldingen;
    }

    /**
     * Voert de werkelijke inschrijving door geboorte door middels het opslaan van het kind en de relatie.
     *
     * moet worden ingeschreven.
     * @param actie de actie waarbinnen de inschrijving plaatsvindt.
     * @param berichtContext de context waarbinnen het bericht en dus de actie uitgevoerd dient te worden.
     * @return eventueel opgetreden meldingen/fouten, welke <code>null</code> kan zijn indien er geen
     *         meldingen/fouten zijn.
     */
    private List<Melding> voerInschrijvingGeboorteDoor(final BRPActie actie, final BerichtContext berichtContext)
    {
        List<Melding> meldingen;
        final Relatie nieuwFamilieRechtelijkeBetrekking = haalRelatieUitActie(actie);
        Persoon kind = nieuwFamilieRechtelijkeBetrekking.getKindBetrokkenheid().getBetrokkene();
        kind.getIdentiteit().setSoort(SoortPersoon.INGESCHREVENE);

        //Sla op actie
        final PersistentActie persistentActie = persisteerActie(actie, berichtContext);
        try {

            PersistentPersoon nieuwGeborene = persoonRepository.opslaanNieuwPersoon(kind,
                    actie.getDatumAanvangGeldigheid(), berichtContext.getTijdstipVerwerking());
            kind.setId(nieuwGeborene.getId());
            meldingen = null;

            //Opslaan historie:
            persoonRepository.werkHistorieBij(nieuwGeborene, kind, persistentActie, actie.getDatumAanvangGeldigheid());

        } catch (ObjectReedsBestaandExceptie e) {
            meldingen = Arrays.asList(new Melding(SoortMelding.FOUT_ONOVERRULEBAAR, MeldingCode.BRBER00121));
        } catch (OnbekendeReferentieExceptie e) {
            Melding melding =
                new Melding(SoortMelding.FOUT_ONOVERRULEBAAR, MeldingCode.REF0001, String.format(
                    "%s. Foutieve code/referentie '%s' voor veld '%s'.", MeldingCode.REF0001.getOmschrijving(),
                    e.getReferentieWaarde(), e.getReferentieVeldNaam()), kind, null);
            meldingen = Arrays.asList(melding);
        }

        if (meldingen == null) {
            PersistentRelatie persistentRelatie = relatieRepository
                    .opslaanNieuweRelatie(nieuwFamilieRechtelijkeBetrekking, actie.getDatumAanvangGeldigheid(),
                            berichtContext.getTijdstipVerwerking());

            //Opslaan historie
            // Opslaan in C-laag
            relatieRepository.werkHistorieBij(persistentRelatie, persistentActie, actie.getDatumAanvangGeldigheid());
        }
        return meldingen;
    }

}
