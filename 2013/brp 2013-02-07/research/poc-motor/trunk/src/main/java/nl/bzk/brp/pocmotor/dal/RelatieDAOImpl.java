/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.pocmotor.dal;

import javax.inject.Inject;

import nl.bzk.brp.pocmotor.dal.operationeel.BetrokkenheidOGMRepository;
import nl.bzk.brp.pocmotor.dal.operationeel.PersoonOGMRepository;
import nl.bzk.brp.pocmotor.dal.operationeel.RelatieOGMRepository;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.Datum;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.DatumTijd;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.Ja;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.StatusHistorie;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.enums.SoortBetrokkenheid;
import nl.bzk.brp.pocmotor.model.logisch.usr.objecttype.Betrokkenheid;
import nl.bzk.brp.pocmotor.model.logisch.usr.objecttype.Persoon;
import nl.bzk.brp.pocmotor.model.logisch.usr.objecttype.Relatie;
import nl.bzk.brp.pocmotor.util.DatumEnTijdUtil;
import org.springframework.stereotype.Repository;

@Repository
public class RelatieDAOImpl implements RelatieDAO {

    @Inject
    private PersoonOGMRepository persoonOGMRepository;

    @Inject
    private RelatieOGMRepository relatieOGMRepository;

    @Inject
    private BetrokkenheidOGMRepository betrokkenheidOGMRepository;

    @Override
    public void persisteerFamilieRechtelijkeBetrekking(final Relatie geboorteRelatie) {

        final DatumTijd registratieTijd = DatumEnTijdUtil.nu();
        final Datum vandaag = DatumEnTijdUtil.vandaag();

        Persoon kind = null;
        Persoon ouder1 = null;
        Persoon ouder2 = null;
        Betrokkenheid kindBetr = null;
        Betrokkenheid ouder1Betr = null;
        Betrokkenheid ouder2Betr = null;
        for (Betrokkenheid betrokkenheid : geboorteRelatie.getBetrokkenheden()) {
            if (SoortBetrokkenheid.KIND == betrokkenheid.getIdentiteit().getRol()) {
                kindBetr = betrokkenheid;
                kind = betrokkenheid.getIdentiteit().getBetrokkene();
            } else {
                if (ouder1 == null) {
                    ouder1Betr = betrokkenheid;
                    ouder1 = betrokkenheid.getIdentiteit().getBetrokkene();
                } else {
                    ouder2Betr = betrokkenheid;
                    ouder2 = betrokkenheid.getIdentiteit().getBetrokkene();
                }
            }
        }
        
        //Haal kind en ouders op
        nl.bzk.brp.pocmotor.model.operationeel.usr.tabel.Persoon opKind =
                persoonOGMRepository.findByBurgerservicenummer(kind.getIdentificatienummers().getBurgerservicenummer());

        nl.bzk.brp.pocmotor.model.operationeel.usr.tabel.Persoon opOuder1 = null;
        if (ouder1 != null && ouder1.getIdentificatienummers() != null &&
                ouder1.getIdentificatienummers().getBurgerservicenummer() != null) {
            opOuder1 = persoonOGMRepository.findByBurgerservicenummer(ouder1.getIdentificatienummers().getBurgerservicenummer());
        }

        //Ouder2 is mogelijk niet aanwezig
        nl.bzk.brp.pocmotor.model.operationeel.usr.tabel.Persoon opOuder2 = null;
        if (ouder2 != null && ouder2.getIdentificatienummers() != null &&
            ouder2.getIdentificatienummers().getBurgerservicenummer() != null) {
            opOuder2 = persoonOGMRepository.findByBurgerservicenummer(ouder2.getIdentificatienummers().getBurgerservicenummer());
        }

        //Persisteer de nieuwe relatie
        nl.bzk.brp.pocmotor.model.operationeel.usr.tabel.Relatie opRelatie =
                new nl.bzk.brp.pocmotor.model.operationeel.usr.tabel.Relatie();
        opRelatie.setSoort(geboorteRelatie.getIdentiteit().getSoort());
        opRelatie.setDatumAanvang(vandaag);
        opRelatie.setRelatieStatusHis(StatusHistorie.ACTUEEL);
        opRelatie = relatieOGMRepository.save(opRelatie);
        relatieOGMRepository.persisteerRelatieHisEntiteit(opRelatie);

        //Persisteer betrokkenheden
        nl.bzk.brp.pocmotor.model.operationeel.usr.tabel.Betrokkenheid opKindBetr =
                new nl.bzk.brp.pocmotor.model.operationeel.usr.tabel.Betrokkenheid();
        opKindBetr.setBetrokkene(opKind);
        opKindBetr.setRelatie(opRelatie);
        opKindBetr.setRol(kindBetr.getIdentiteit().getRol());
        opKindBetr.setIndicatieOuder(new Ja(false));
        opKindBetr.setOuderlijkGezagStatusHis(StatusHistorie.ACTUEEL);
        opKindBetr.setOuderStatusHis(StatusHistorie.ACTUEEL);
        opKindBetr = betrokkenheidOGMRepository.save(opKindBetr);
        betrokkenheidOGMRepository.persisteerHisBetrokkenheidOuder(opKindBetr);

        if (opOuder1 != null) {
            nl.bzk.brp.pocmotor.model.operationeel.usr.tabel.Betrokkenheid opOuder1Betr =
                    new nl.bzk.brp.pocmotor.model.operationeel.usr.tabel.Betrokkenheid();
            opOuder1Betr.setBetrokkene(opOuder1);
            opOuder1Betr.setRelatie(opRelatie);
            opOuder1Betr.setRol(ouder1Betr.getIdentiteit().getRol());
            opOuder1Betr.setIndicatieOuder(new Ja(true));
            opOuder1Betr.setOuderlijkGezagStatusHis(StatusHistorie.ACTUEEL);
            opOuder1Betr.setOuderStatusHis(StatusHistorie.ACTUEEL);
            opOuder1Betr = betrokkenheidOGMRepository.save(opOuder1Betr);
            betrokkenheidOGMRepository.persisteerHisBetrokkenheidOuder(opOuder1Betr);
        }

        if (opOuder2 != null) {
            nl.bzk.brp.pocmotor.model.operationeel.usr.tabel.Betrokkenheid opOuder2Betr =
                    new nl.bzk.brp.pocmotor.model.operationeel.usr.tabel.Betrokkenheid();
            opOuder2Betr.setBetrokkene(opOuder2);
            opOuder2Betr.setRelatie(opRelatie);
            opOuder2Betr.setRol(ouder2Betr.getIdentiteit().getRol());
            opOuder2Betr.setIndicatieOuder(new Ja(true));
            opOuder2Betr.setOuderlijkGezagStatusHis(StatusHistorie.ACTUEEL);
            opOuder2Betr.setOuderStatusHis(StatusHistorie.ACTUEEL);
            opOuder2Betr = betrokkenheidOGMRepository.save(opOuder2Betr);
            betrokkenheidOGMRepository.persisteerHisBetrokkenheidOuder(opOuder2Betr);
        }
    }

    @Override
    public void voegVaderToeAanFamilieRechtelijkeBetrekking(final Persoon vader, final Persoon kind) {
        //Haal de vader op
        nl.bzk.brp.pocmotor.model.operationeel.usr.tabel.Persoon opVader =
                persoonOGMRepository.findByBurgerservicenummer(vader.getIdentificatienummers().getBurgerservicenummer());

        //Haal het kind op
        nl.bzk.brp.pocmotor.model.operationeel.usr.tabel.Persoon opKind =
                persoonOGMRepository.findByBurgerservicenummer(kind.getIdentificatienummers().getBurgerservicenummer());

        //Haal de relatie waar het om gaat op
        nl.bzk.brp.pocmotor.model.operationeel.usr.tabel.Betrokkenheid opKindBetrokkenHeid =
                betrokkenheidOGMRepository.findByRolAndBetrokkene(SoortBetrokkenheid.KIND, opKind);

        //Maak een betrokkenheid aan voor de vader
        nl.bzk.brp.pocmotor.model.operationeel.usr.tabel.Betrokkenheid opVaderBetr =
                new nl.bzk.brp.pocmotor.model.operationeel.usr.tabel.Betrokkenheid();
        opVaderBetr.setRelatie(opKindBetrokkenHeid.getRelatie());
        opVaderBetr.setRol(SoortBetrokkenheid.OUDER);
        opVaderBetr.setBetrokkene(opVader);
        opVaderBetr.setIndicatieOuder(new Ja(true));
        opVaderBetr.setOuderStatusHis(StatusHistorie.ACTUEEL);
        opVaderBetr.setOuderlijkGezagStatusHis(StatusHistorie.ACTUEEL);
        
        opVaderBetr = betrokkenheidOGMRepository.save(opVaderBetr);
        betrokkenheidOGMRepository.persisteerHisBetrokkenheidOuder(opVaderBetr);
    }
}
