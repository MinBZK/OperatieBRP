/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.bedrijfsregels.impl.aanschrijving;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import nl.bzk.brp.business.bedrijfsregels.ActieBedrijfsRegel;
import nl.bzk.brp.dataaccess.repository.PersoonRepository;
import nl.bzk.brp.dataaccess.repository.RelatieRepository;
import nl.bzk.brp.model.RootObject;
import nl.bzk.brp.model.attribuuttype.Datum;
import nl.bzk.brp.model.attribuuttype.Geslachtsnaamcomponent;
import nl.bzk.brp.model.attribuuttype.JaNee;
import nl.bzk.brp.model.groep.bericht.PersoonAanschrijvingGroepBericht;
import nl.bzk.brp.model.groep.logisch.PersoonSamengesteldeNaamGroep;
import nl.bzk.brp.model.objecttype.bericht.PersoonBericht;
import nl.bzk.brp.model.objecttype.logisch.Actie;
import nl.bzk.brp.model.objecttype.logisch.Persoon;
import nl.bzk.brp.model.objecttype.operationeel.PersoonModel;
import nl.bzk.brp.model.objecttype.operationeel.statisch.SoortMelding;
import nl.bzk.brp.model.objecttype.operationeel.statisch.WijzeGebruikGeslachtsnaam;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.model.validatie.MeldingCode;
import nl.bzk.brp.util.AttribuutTypeUtil;
import org.springframework.beans.factory.annotation.Qualifier;


/**
 * Afleidingsbedrijfsregel die de aanschrijving van een persoon afleidt.
 * Is de flag 'indAanschrijvingAlgorthmischAfgeleid' == Ja, dan worden de voorvoegsel, scheidingsteken en geslachtsnaam
 * afgeleid uit de WijzeGebruikGeslachtsnaam (Eigen, Partner, Partner_Eigen, Eigen_Partner).
 * De samengestelde naam van de hoofdpersoon en die van de partner worden opgehaald en hiermee de properties van de
 * aanschrijving van de hoofdpersoon ingevuld.
 * De voornaam wordt gehaald uit de voornaam van de samengestelde naam van de hoofd persoon.
 * De predikaat / adellijke titel worden alleen overgenomen van de predikaat / adellijke titel vd. samengestelde naam
 * vd. hoofd persoon als de flag 'indAanschrijvenMetAdellijkeTitel' aan staat (anders altijd leeg).
 * <p/>
 * De voorvoegsel en scheidingsteken worden van de eerste voorkeur overgenomen. De geslachtsnaam wordt overgenomen van
 * de voorkeur persoon, en in geval van combinatie, wordt dit als volgt opgebouwd: geslachtsnaam van de voorkeur
 * +<spatie> + "-" + <spatie> + (voorvoegsel + scheidingsteken + geslachtsnaam) vd. andere.
 * <p/>
 * Is de flag indAanschrijvingAlgorthmischAfgeleid == Nee, dan worden alle velden die in het bericht doorgegeven als
 * compleet beschouwd en wordt volledig overgenomen.
 * <p/>
 * <p/>
 * SRPUC04203
 */
@Qualifier("aanschrijvingGroepAfleiding")
public class AanschrijvingGroepAfleiding implements ActieBedrijfsRegel<RootObject> {

    @Inject
    private PersoonRepository persoonRepository;
    @Inject
    private RelatieRepository relatieRepository;

    @Override
    public String getCode() {
        // @TODO Bedrijfsregel code nog onbekend.
        return "AanschrijvingGroepAfleiding";
    }

    @Override
    public List<Melding> executeer(final RootObject huidigeSituatie, final RootObject nieuweSituatie, final Actie actie)
    {
        List<Melding> meldingen;
        if (nieuweSituatie instanceof Persoon) {
            meldingen =
                executeer((PersoonModel) huidigeSituatie, (PersoonBericht) nieuweSituatie,
                        actie.getDatumAanvangGeldigheid());
        } else {
            throw new IllegalArgumentException(
                    "Alleen rootobject van het type Persoon is hier toegestaan. Doorgegeven "
                        + nieuweSituatie.getClass().getName());
        }
        return meldingen;
    }

    /**
     * Haal de huidige partner op uit de database.
     *
     * @param hoofdPersoon de hoofd persoon
     * @param peilDatum de peil datum
     * @return zijn partner of null als geen
     */
    private PersoonModel haalHuidigPartner(final PersoonModel hoofdPersoon, final Datum peilDatum) {
        List<Integer> ids = getRelatieRepository().haalopPartners(hoofdPersoon.getId(), peilDatum);
        if (ids.size() > 1) {
            throw new RuntimeException("Te veel partners gevonden voor persoon "
                          + hoofdPersoon.getId() + " nl: " + ids);
        } else if (ids.size() == 1) {
            return getPersoonRepository().haalPersoonMetAdres(ids.get(0));
        }
        // geen partner gevonden.
        return null;
    }

    /**
     * Copieer alle standaard gegevens van een naamcomponent voor aanschrijving.
     *
     * @param aanschrijving de te vullen aanschrijving
     * @param samegesteldeNaam de samengestelde naam component
     */
    private void copieerGegevens(final PersoonAanschrijvingGroepBericht aanschrijving,
            final PersoonSamengesteldeNaamGroep samegesteldeNaam)
    {
        aanschrijving.setAdellijkeTitel(null);
        aanschrijving.setPredikaat(null);
        aanschrijving.setScheidingsteken(null);
        aanschrijving.setVoorvoegsel(null);
        aanschrijving.setGeslachtsnaam(null);

        if (aanschrijving.getIndAanschrijvenMetAdellijkeTitel() == JaNee.Ja) {
            aanschrijving.setAdellijkeTitel(samegesteldeNaam.getAdellijkeTitel());
            aanschrijving.setPredikaat(samegesteldeNaam.getPredikaat());
        } else {
            aanschrijving.setAdellijkeTitel(null);
            aanschrijving.setPredikaat(null);
        }
        if (AttribuutTypeUtil.isNotBlank(samegesteldeNaam.getGeslachtsnaam())) {
            aanschrijving.setGeslachtsnaam(samegesteldeNaam.getGeslachtsnaam());
        }
        if (AttribuutTypeUtil.isNotEmpty(samegesteldeNaam.getScheidingsteken())) {
            aanschrijving.setScheidingsteken(samegesteldeNaam.getScheidingsteken());
        }
        if (AttribuutTypeUtil.isNotBlank(samegesteldeNaam.getVoorvoegsel())) {
            aanschrijving.setVoorvoegsel(samegesteldeNaam.getVoorvoegsel());
        }
    }

    /**
     * Bouw de 2e achternaam op als combinatie van velden uit een samengestelde naam componenet.
     *
     * @param samenNaam de samengestelde naam componenet
     * @return de samengestelde naam
     */
    private String bouwTweedeNaamOp(final PersoonSamengesteldeNaamGroep samenNaam) {
        StringBuilder naam = new StringBuilder();
        if (AttribuutTypeUtil.isNotBlank(samenNaam.getVoorvoegsel())) {
            naam.append(samenNaam.getVoorvoegsel().getWaarde());
        }
        if (AttribuutTypeUtil.isNotEmpty(samenNaam.getScheidingsteken())) {
            naam.append(samenNaam.getScheidingsteken().getWaarde());
        }
        if (AttribuutTypeUtil.isNotBlank(samenNaam.getGeslachtsnaam())) {
            naam.append(samenNaam.getGeslachtsnaam().getWaarde());
        }
        return naam.toString();
    }

    /**
     * Vul aan / leidt af de naam component velden afhankelijk van de indicatie naamgebruik.
     *
     * @param aanschrijving de aan te vullen aanschrijving.
     * @param eigen eigen naam component
     * @param partner partnaam component
     * @return de aangevulde aanschrijving.
     */
    private PersoonAanschrijvingGroepBericht leidAlgoritmischAf(final PersoonAanschrijvingGroepBericht aanschrijving,
            final PersoonSamengesteldeNaamGroep eigen, final PersoonSamengesteldeNaamGroep partner)
    {
        if (AttribuutTypeUtil.isNotBlank(eigen.getVoornamen())) {
            aanschrijving.setVoornamen(eigen.getVoornamen());
        }
        if (aanschrijving.getGebruikGeslachtsnaam() == WijzeGebruikGeslachtsnaam.EIGEN) {
            copieerGegevens(aanschrijving, eigen);
        } else if (aanschrijving.getGebruikGeslachtsnaam() == WijzeGebruikGeslachtsnaam.PARTNER) {
            copieerGegevens(aanschrijving, partner);
        } else if (aanschrijving.getGebruikGeslachtsnaam() == WijzeGebruikGeslachtsnaam.EIGEN_PARTNER) {
            copieerGegevens(aanschrijving, eigen);
            StringBuilder gecombineerdeNaam = new StringBuilder(eigen.getGeslachtsnaam().getWaarde());
            gecombineerdeNaam.append("-");
            gecombineerdeNaam.append(bouwTweedeNaamOp(partner));
            aanschrijving.setGeslachtsnaam(new Geslachtsnaamcomponent(gecombineerdeNaam.toString()));
        } else if (aanschrijving.getGebruikGeslachtsnaam() == WijzeGebruikGeslachtsnaam.PARTNER_EIGEN) {
            copieerGegevens(aanschrijving, partner);
            StringBuilder gecombineerdeNaam = new StringBuilder(partner.getGeslachtsnaam().getWaarde());
            gecombineerdeNaam.append("-");
            gecombineerdeNaam.append(bouwTweedeNaamOp(eigen));
            aanschrijving.setGeslachtsnaam(new Geslachtsnaamcomponent(gecombineerdeNaam.toString()));
        }
        return aanschrijving;
    }

    /**
     * Voert de werkelijke afleiding uit van de samen gestelde naam voor de opgegeven {@link Persoon} instantie.
     *
     * @param huidigPersoon de persoon uit de database.
     * @param berichtPersoon de persoon waarvoor de afleiding geldt.
     * @param peilDatum de peil datum
     * @return een eventuele foutmelding indien de afleiding niet kan worden uitgevoerd.
     */
    private List<Melding> executeer(final PersoonModel huidigPersoon, final PersoonBericht berichtPersoon,
            final Datum peilDatum)
    {
        List<Melding> foutMeldingen = new ArrayList<Melding>();
        if (berichtPersoon.getAanschrijving() != null
            && berichtPersoon.getAanschrijving().getIndAanschrijvingAlgorthmischAfgeleid() == JaNee.Ja)
        {
            PersoonSamengesteldeNaamGroep eigen = huidigPersoon.getSamengesteldeNaam();
            if (berichtPersoon.getAanschrijving().getGebruikGeslachtsnaam() == WijzeGebruikGeslachtsnaam.EIGEN) {
                leidAlgoritmischAf(berichtPersoon.getAanschrijving(), eigen, null);
            } else {
                PersoonModel partnerPersoon = haalHuidigPartner(huidigPersoon, peilDatum);
                if (partnerPersoon == null) {
                    foutMeldingen.add(new Melding(SoortMelding.FOUT, MeldingCode.ALG0002,
                            "Partner niet gevonden.", berichtPersoon.getIdentificatienummers(), "burgerservicenummer"));
                } else {
                    PersoonSamengesteldeNaamGroep partner = partnerPersoon.getSamengesteldeNaam();
                    leidAlgoritmischAf(berichtPersoon.getAanschrijving(), eigen, partner);
                }
            }

            // de rest van de data is al gevuld door het xml
        }
        return foutMeldingen;
    }

    public PersoonRepository getPersoonRepository() {
        return persoonRepository;
    }

    public void setPersoonRepository(final PersoonRepository persoonRepository) {
        this.persoonRepository = persoonRepository;
    }

    public RelatieRepository getRelatieRepository() {
        return relatieRepository;
    }

    public void setRelatieRepository(final RelatieRepository relatieRepository) {
        this.relatieRepository = relatieRepository;
    }

}
