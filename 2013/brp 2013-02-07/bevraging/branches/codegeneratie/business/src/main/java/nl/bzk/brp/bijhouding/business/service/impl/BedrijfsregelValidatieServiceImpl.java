/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.service.impl;

import javax.inject.Inject;

import nl.bzk.brp.bevraging.business.dto.antwoord.BerichtVerwerkingsFout;
import nl.bzk.brp.bevraging.business.dto.antwoord.BerichtVerwerkingsFoutZwaarte;
import nl.bzk.brp.bevraging.business.dto.verzoek.BerichtVerzoek;
import nl.bzk.brp.bijhouding.business.bedrijfsregel.AbstractValidatie;
import nl.bzk.brp.bijhouding.business.bedrijfsregel.ValidatieResultaat;
import nl.bzk.brp.bijhouding.business.service.BedrijfsregelValidatieService;
import nl.bzk.brp.bijhouding.business.service.exception.OnbekendeValidatieExceptie;
import nl.bzk.brp.bijhouding.business.service.exception.OngeldigValidatieResultaatExceptie;
import nl.bzk.brp.domein.ber.SoortBericht;
import nl.bzk.brp.domein.brm.Regel;
import nl.bzk.brp.domein.brm.Regeleffect;
import nl.bzk.brp.domein.brm.Regelimplementatie;
import nl.bzk.brp.domein.brm.Regelimplementatiesituatie;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;


/**
 * Implementatie van {@link BedrijfsregelValidatieService}.
 */
public class BedrijfsregelValidatieServiceImpl implements BedrijfsregelValidatieService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BedrijfsregelValidatieServiceImpl.class);

    @Inject
    private ApplicationContext  applicationContext;

    /**
     * {@inheritDoc}
     */
    @Override
    public AbstractValidatie<?> creeerValidatie(final Regel regel, final BerichtVerzoek<?> verzoek)
            throws OnbekendeValidatieExceptie
    {
        AbstractValidatie<?> validatie;
        try {
            validatie = applicationContext.getBean(regel.getCode(), AbstractValidatie.class);
        } catch (NoSuchBeanDefinitionException e) {
            LOGGER.error("Bedrijfsregel {} kan niet worden gevalideerd want er is geen Spring bean met die naam.",
                    regel.getCode());
            throw new OnbekendeValidatieExceptie();
        }
        validatie.setVerzoek(verzoek);
        return validatie;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BerichtVerwerkingsFout roepValidatieAan(final AbstractValidatie<?> validatie,
            final Regelimplementatiesituatie gedrag)
    {
        BerichtVerwerkingsFout fout;
        if (validatie == null) {
            Regelimplementatie implementatie = gedrag.getRegelimplementatie();
            fout = creeerFoutVoorOnbekendeValidatie(implementatie.getRegel(), implementatie.getSoortBericht());
        } else {
            ValidatieResultaat resultaat = validatie.voerUit();
            if (resultaat == ValidatieResultaat.CONFORM) {
                fout = null;
            } else if (resultaat == ValidatieResultaat.AFWIJKEND) {
                fout = new BerichtVerwerkingsFout(validatie.getFoutCode(), getZwaarte(gedrag.getEffect()));
            } else {
                throw new OngeldigValidatieResultaatExceptie(validatie, resultaat);
            }
            LOGGER.debug("gedragId({}):{}/{}={}", new Object[] { gedrag.getID(),
                validatie.getVerzoek().getSoortBericht().getNaam(),
                gedrag.getRegelimplementatie().getRegel().getCode(), resultaat });
        }
        return fout;
    }

    /**
     * @param regel de bedrijfsregel van de onbekende validatie
     * @param soortBericht de berichtsoort van de onbekende validatie
     * @return een {@link BerichtVerwerkingsFout} met zwaarte {@link BerichtVerwerkingsFoutZwaarte#SYSTEEM}
     */
    private BerichtVerwerkingsFout creeerFoutVoorOnbekendeValidatie(final Regel regel, final SoortBericht soortBericht)
    {
        String melding =
            String.format("Het is niet mogelijk om bedrijfsregel %s te valideren bij een %s bericht", regel.getCode(),
                    soortBericht.getNaam());
        return new BerichtVerwerkingsFout(null, BerichtVerwerkingsFoutZwaarte.SYSTEEM, melding);
    }

    /**
     * Vertaalt het gedrag effect naar een fout zwaarte.
     *
     * @param effect gedrag effect dat vertaald moet worden naar een fout zwaarte
     * @return de zwaarte van de fout
     */
    private BerichtVerwerkingsFoutZwaarte getZwaarte(final Regeleffect effect) {
        BerichtVerwerkingsFoutZwaarte resultaat;
        switch (effect) {
            case HARD_VERBIEDEN:
                resultaat = BerichtVerwerkingsFoutZwaarte.FOUT;
                break;
            case ZACHT_VERBIEDEN:
                // TODO friso: bouw een overrule mechanisme
                resultaat = BerichtVerwerkingsFoutZwaarte.FOUT;
                break;
            case WAARSCHUWEN:
                resultaat = BerichtVerwerkingsFoutZwaarte.WAARSCHUWING;
                break;
            case INFORMEREN:
                resultaat = BerichtVerwerkingsFoutZwaarte.INFO;
                break;
            case AFLEIDEN:
                // TODO friso: zoek uit wat afleiden hier betekent en implementeer dat
                resultaat = BerichtVerwerkingsFoutZwaarte.INFO;
                break;
            default:
                throw new RuntimeException("onbekende waarde RegelEffect [" + effect + "]");
        }
        return resultaat;
    }

}
