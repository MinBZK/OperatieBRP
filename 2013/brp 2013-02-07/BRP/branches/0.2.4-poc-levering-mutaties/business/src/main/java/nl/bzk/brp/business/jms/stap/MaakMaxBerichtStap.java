/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.jms.stap;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import nl.bzk.brp.business.jms.LevMutAdmHandBerichtContext;
import nl.bzk.brp.business.levering.AangeverAdreshoudingCode;
import nl.bzk.brp.business.levering.Administratienummer;
import nl.bzk.brp.business.levering.AdministratieveHandeling;
import nl.bzk.brp.business.levering.Adres;
import nl.bzk.brp.business.levering.AdresseerbaarObject;
import nl.bzk.brp.business.levering.Adressen;
import nl.bzk.brp.business.levering.AfgekorteNaamOpenbareRuimte;
import nl.bzk.brp.business.levering.AfgeleidAdministratief;
import nl.bzk.brp.business.levering.Applicatie;
import nl.bzk.brp.business.levering.Berichtcode;
import nl.bzk.brp.business.levering.Bijhouding;
import nl.bzk.brp.business.levering.Burgerservicenummer;
import nl.bzk.brp.business.levering.Functie;
import nl.bzk.brp.business.levering.GemeenteCode;
import nl.bzk.brp.business.levering.Huisletter;
import nl.bzk.brp.business.levering.Huisnummer;
import nl.bzk.brp.business.levering.Huisnummertoevoeging;
import nl.bzk.brp.business.levering.Identificatie;
import nl.bzk.brp.business.levering.IdentificatiecodeNummeraanduiding;
import nl.bzk.brp.business.levering.Identificatienummers;
import nl.bzk.brp.business.levering.LEVLeveringBijgehoudenPersoonLv;
import nl.bzk.brp.business.levering.Levering;
import nl.bzk.brp.business.levering.Leveringen;
import nl.bzk.brp.business.levering.NaamOpenbareRuimte;
import nl.bzk.brp.business.levering.Ontvanger;
import nl.bzk.brp.business.levering.Organisatie;
import nl.bzk.brp.business.levering.PartijCode;
import nl.bzk.brp.business.levering.Persoon;
import nl.bzk.brp.business.levering.Postcode;
import nl.bzk.brp.business.levering.RedenWijzigingCode;
import nl.bzk.brp.business.levering.Referentienummer;
import nl.bzk.brp.business.levering.SoortCode;
import nl.bzk.brp.business.levering.SoortNaam;
import nl.bzk.brp.business.levering.Stuurgegevens;
import nl.bzk.brp.business.levering.TijdstipLaatsteWijziging;
import nl.bzk.brp.business.levering.TijdstipOntlening;
import nl.bzk.brp.business.levering.TijdstipVerwerking;
import nl.bzk.brp.business.levering.WoonplaatsCode;
import nl.bzk.brp.business.levering.Zender;
import nl.bzk.brp.dataaccess.repository.PersoonAdresRepository;
import nl.bzk.brp.dataaccess.repository.PersoonRepository;
import nl.bzk.brp.model.objecttype.operationeel.ActieModel;
import nl.bzk.brp.model.objecttype.operationeel.PersoonAdresModel;
import nl.bzk.brp.model.objecttype.operationeel.PersoonModel;
import org.apache.commons.lang.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;


/**
 * Maak een maximaal bericht voor de notificatie aan de afnemers.
 */
public class MaakMaxBerichtStap extends AbstractBerichtVerwerkingsStap {

    private static final Logger LOGGER             = LoggerFactory.getLogger(MaakMaxBerichtStap.class);

    private static final String ZENDER_ORGANISATIE = "BPR";
    private static final String ZENDER_APPLICATIE  = "BRP";
    private static final String FUNCTIE            = "RegistratieInterGemeentelijkeVerhuizing";
    private static final String SOORT_CODE         = "05002";
    private static final String SOORT_NAAM         = "Registratie intergemeentelijke verhuizing";

    @Inject
    PersoonRepository           persoonRepository;

    @Inject
    PersoonAdresRepository      persoonAdresRepository;

    @Override
    public StapResultaat voerVerwerkingsStapUitVoorBericht(final LevMutAdmHandBerichtContext context) {

        if (context.getPartijIds().size() > 0) {
            ActieModel admHandeling = context.getActieModel();
            List<LEVLeveringBijgehoudenPersoonLv> maxBerichten = new ArrayList<LEVLeveringBijgehoudenPersoonLv>();
            context.setMaxBerichten(maxBerichten);

            switch (admHandeling.getSoort()) {
                case VERHUIZING:
                    maxBerichten = maakMaxBerichtenVerhuizing(context);
                    break;
                default:
                    break;
            }
            context.setMaxBerichten(maxBerichten);
        }
        return StapResultaat.DOORGAAN_MET_VERWERKING;
    }

    private List<LEVLeveringBijgehoudenPersoonLv> maakMaxBerichtenVerhuizing(final LevMutAdmHandBerichtContext context)
    {
        List<LEVLeveringBijgehoudenPersoonLv> maxBerichten = context.getMaxBerichten();

        // Voor verhuizing is er altijd maar 1 betrokken bsn -> 1 maxbericht
        if (context.getBetrokkenBsns() != null) {
            for (String bsn : context.getBetrokkenBsns()) {
                LEVLeveringBijgehoudenPersoonLv maxBericht = creeerBerichtVerhuizing(bsn, context);
                maxBerichten.add(maxBericht);
            }
        }
        return maxBerichten;
    }

    private LEVLeveringBijgehoudenPersoonLv creeerBerichtVerhuizing(final String bsn,
            final LevMutAdmHandBerichtContext context)
    {
        LEVLeveringBijgehoudenPersoonLv maxBericht = new LEVLeveringBijgehoudenPersoonLv();

        Stuurgegevens stuurgegevens = creeerStuurgegevens();
        maxBericht.setStuurgegevens(stuurgegevens);

        AdministratieveHandeling administratieveHandeling = creeerAdministratieveHandeling(bsn, context);
        maxBericht.setAdministratieveHandeling(administratieveHandeling);

        return maxBericht;
    }

    private Stuurgegevens creeerStuurgegevens() {
        Stuurgegevens stuurgegevens = new Stuurgegevens();

        Berichtcode berichtcode = new Berichtcode();
        berichtcode.setBerichtcode(RandomStringUtils.randomAlphanumeric(15));
        stuurgegevens.setBerichtcode(berichtcode);

        Zender zender = new Zender();
        Organisatie organisatie = new Organisatie();
        organisatie.setOrganisatie(ZENDER_ORGANISATIE);
        zender.setOrganisatie(organisatie);
        Applicatie applicatie = new Applicatie();
        applicatie.setApplicatie(ZENDER_APPLICATIE);
        zender.setApplicatie(applicatie);
        stuurgegevens.setZender(zender);

        // Per afnemer verschillend
        // TODO: implementeren na afnemer bepalen
        Ontvanger ontvanger = new Ontvanger();
        ontvanger.setOrganisatie(organisatie);
        ontvanger.setApplicatie(applicatie);
        stuurgegevens.setOntvanger(ontvanger);

        Referentienummer referentienummer = new Referentienummer();
        referentienummer.setReferentienummer(RandomStringUtils.randomNumeric(15));
        stuurgegevens.setReferentienummer(referentienummer);

        Functie functie = new Functie();
        functie.setFunctie(FUNCTIE);
        stuurgegevens.setFunctie(functie);

        return stuurgegevens;
    }

    private AdministratieveHandeling creeerAdministratieveHandeling(final String bsn,
            final LevMutAdmHandBerichtContext context)
    {
        nl.bzk.brp.model.attribuuttype.Burgerservicenummer burgerServiceNummerPersoon =
            new nl.bzk.brp.model.attribuuttype.Burgerservicenummer(bsn);

        PersoonModel persoonModel = persoonRepository.findByBurgerservicenummer(burgerServiceNummerPersoon);

        try {
            PersoonAdresModel persoonAdresModel =
                persoonAdresRepository.vindHuidigWoonAdresVoorPersoon(burgerServiceNummerPersoon);

            if (persoonAdresModel != null) {
                AdministratieveHandeling administratieveHandeling = new AdministratieveHandeling();

                SoortCode soortCode = new SoortCode();
                soortCode.setSoortCode(SOORT_CODE);
                administratieveHandeling.setSoortCode(soortCode);

                SoortNaam soortNaam = new SoortNaam();
                soortNaam.setSoortNaam(SOORT_NAAM);
                administratieveHandeling.setSoortNaam(soortNaam);

                PartijCode partijCode = new PartijCode();
                partijCode.setPartijCode(context.getActieModel().getPartij().getGemeentecode().getWaarde());
                administratieveHandeling.setPartijCode(partijCode);

                TijdstipOntlening tijdstipOntlening = new TijdstipOntlening();
                tijdstipOntlening.setTijdstipOntlening(context.getActieModel().getTijdstipOntlening().getWaarde());
                administratieveHandeling.setTijdstipOntlening(tijdstipOntlening);

                if (context.getActieModel().getTijdstipVerwerkingMutatie() != null) {
                    TijdstipVerwerking tijdstipVerwerking = new TijdstipVerwerking();
                    tijdstipVerwerking.setTijdstipVerwerking(context.getActieModel().getTijdstipVerwerkingMutatie()
                            .getWaarde());
                    administratieveHandeling.setTijdstipVerwerking(tijdstipVerwerking);
                }

                Leveringen leveringen = new Leveringen();
                administratieveHandeling.setLeveringen(leveringen);

                Levering levering = new Levering();
                leveringen.setLevering(levering);

                Identificatie identificatie = new Identificatie();
                levering.setIdentificatie(identificatie);

                Persoon persoon = new Persoon();
                identificatie.setPersoon(persoon);

                Identificatienummers identificatienummers = new Identificatienummers();
                persoon.setIdentificatienummers(identificatienummers);

                Burgerservicenummer burgerservicenummer = new Burgerservicenummer();
                burgerservicenummer.setBurgerservicenummer(Integer.valueOf(bsn));
                identificatienummers.setBurgerservicenummer(burgerservicenummer);

                if (persoonModel.getIdentificatienummers() != null
                    && persoonModel.getIdentificatienummers().getAdministratienummer() != null
                    && persoonModel.getIdentificatienummers().getAdministratienummer().getWaarde() != null)
                {
                    Administratienummer administratienummer = new Administratienummer();
                    administratienummer.setAdministratienummer(persoonModel.getIdentificatienummers()
                            .getAdministratienummer().getWaarde());
                    identificatienummers.setAdministratienummer(administratienummer);
                }

                AfgeleidAdministratief afgeleidAdministratief = new AfgeleidAdministratief();
                persoon.setAfgeleidAdministratief(afgeleidAdministratief);

                TijdstipLaatsteWijziging tijdstipLaatsteWijziging = new TijdstipLaatsteWijziging();
                tijdstipLaatsteWijziging.setTijdstipLaatsteWijziging(persoonModel.getAfgeleidAdministratief()
                        .getTijdstipLaatsteWijziging().getWaarde());
                afgeleidAdministratief.setTijdstipLaatsteWijziging(tijdstipLaatsteWijziging);

                Bijhouding bijhouding = new Bijhouding();
                levering.setBijhouding(bijhouding);

                Persoon persoonBijhouding = new Persoon();
                bijhouding.setPersoon(persoonBijhouding);

                Adressen adressen = new Adressen();
                persoonBijhouding.setAdressen(adressen);

                Adres adres = new Adres();
                adressen.setAdres(adres);

                SoortCode soortCodeBijhouding = new SoortCode();
                soortCodeBijhouding.setSoortCode(persoonAdresModel.getGegevens().getSoort().getCode());
                adres.setSoortCode(soortCodeBijhouding);

                RedenWijzigingCode redenWijzigingCode = new RedenWijzigingCode();
                redenWijzigingCode.setRedenWijzigingCode(persoonAdresModel.getGegevens().getRedenWijziging()
                        .getRedenWijzigingAdresCode().getWaarde());
                adres.setRedenWijzigingCode(redenWijzigingCode);

                if (persoonAdresModel.getGegevens().getAangeverAdresHouding() != null) {
                    AangeverAdreshoudingCode aangeverAdreshoudingCode = new AangeverAdreshoudingCode();
                    aangeverAdreshoudingCode.setAangeverAdreshoudingCode(persoonAdresModel.getGegevens()
                            .getAangeverAdresHouding().getCode());
                    adres.setAangeverAdreshoudingCode(aangeverAdreshoudingCode);
                }

                // try {
                // DatumAanvangAdreshouding datumAanvangAdreshouding = new DatumAanvangAdreshouding();
                // datumAanvangAdreshouding.setDatumAanvangAdreshouding((Date) new SimpleDateFormat("yyyyMMdd").parse(""
                // + persoonAdresModel.getGegevens().getDatumAanvangAdreshouding().getWaarde()));
                // adres.setDatumAanvangAdreshouding(datumAanvangAdreshouding);
                // } catch (ParseException e) {
                // LOGGER.error("Fout opgetreden bij conversie van datum.");
                // }

                if (persoonAdresModel.getGegevens().getAdresseerbaarObject() != null) {
                    AdresseerbaarObject adresseerbaarObject = new AdresseerbaarObject();
                    adresseerbaarObject.setAdresseerbaarObject(Long.valueOf(persoonAdresModel.getGegevens()
                            .getAdresseerbaarObject().getWaarde()));
                    adres.setAdresseerbaarObject(adresseerbaarObject);
                }

                if (persoonAdresModel.getGegevens().getIdentificatiecodeNummeraanduiding() != null) {
                    IdentificatiecodeNummeraanduiding identificatiecodeNummeraanduiding =
                        new IdentificatiecodeNummeraanduiding();
                    identificatiecodeNummeraanduiding
                            .setIdentificatiecodeNummeraanduiding(Long.valueOf(persoonAdresModel.getGegevens()
                                    .getIdentificatiecodeNummeraanduiding().getWaarde()));
                    adres.setIdentificatiecodeNummeraanduiding(identificatiecodeNummeraanduiding);
                }

                if (persoonAdresModel.getGegevens().getGemeente() != null
                    && persoonAdresModel.getGegevens().getGemeente().getGemeentecode() != null)
                {
                    GemeenteCode gemeenteCode = new GemeenteCode();
                    gemeenteCode.setGemeenteCode(persoonAdresModel.getGegevens().getGemeente().getGemeentecode()
                            .getWaarde());
                    adres.setGemeenteCode(gemeenteCode);
                }

                if (persoonAdresModel.getGegevens().getNaamOpenbareRuimte() != null) {
                    NaamOpenbareRuimte naamOpenbareRuimte = new NaamOpenbareRuimte();
                    naamOpenbareRuimte.setNaamOpenbareRuimte(persoonAdresModel.getGegevens().getNaamOpenbareRuimte()
                            .getWaarde());
                    adres.setNaamOpenbareRuimte(naamOpenbareRuimte);
                }

                if (persoonAdresModel.getGegevens().getAfgekorteNaamOpenbareRuimte() != null) {
                    AfgekorteNaamOpenbareRuimte afgekorteNaamOpenbareRuimte = new AfgekorteNaamOpenbareRuimte();
                    afgekorteNaamOpenbareRuimte.setAfgekorteNaamOpenbareRuimte(persoonAdresModel.getGegevens()
                            .getAfgekorteNaamOpenbareRuimte().getWaarde());
                    adres.setAfgekorteNaamOpenbareRuimte(afgekorteNaamOpenbareRuimte);
                }

                if (persoonAdresModel.getGegevens().getHuisnummer() != null) {
                    Huisnummer huisnummer = new Huisnummer();
                    huisnummer.setHuisnummer(Short.valueOf(""
                        + persoonAdresModel.getGegevens().getHuisnummer().getWaarde()));
                    adres.setHuisnummer(huisnummer);
                }

                if (persoonAdresModel.getGegevens().getHuisletter() != null) {
                    Huisletter huisletter = new Huisletter();
                    huisletter.setHuisletter(persoonAdresModel.getGegevens().getHuisletter().getWaarde());
                    adres.setHuisletter(huisletter);
                }

                if (persoonAdresModel.getGegevens().getHuisnummertoevoeging() != null) {
                    Huisnummertoevoeging huisnummertoevoeging = new Huisnummertoevoeging();
                    huisnummertoevoeging.setHuisnummertoevoeging(persoonAdresModel.getGegevens()
                            .getHuisnummertoevoeging().getWaarde());
                    adres.setHuisnummertoevoeging(huisnummertoevoeging);
                }

                if (persoonAdresModel.getGegevens().getPostcode() != null) {
                    Postcode postcode = new Postcode();
                    postcode.setPostcode(persoonAdresModel.getGegevens().getPostcode().getWaarde());
                    adres.setPostcode(postcode);
                }

                if (persoonAdresModel.getGegevens().getWoonplaats() != null) {
                    WoonplaatsCode woonplaatsCode = new WoonplaatsCode();
                    woonplaatsCode.setWoonplaatsCode(persoonAdresModel.getGegevens().getWoonplaats().getCode()
                            .getWaarde());
                    adres.setWoonplaatsCode(woonplaatsCode);
                }
                return administratieveHandeling;
            }
        } catch (EmptyResultDataAccessException e) {
            LOGGER.error("Geen persoonsadres bekend voor deze persoon: " + bsn);
        }

        return null;
    }
}
