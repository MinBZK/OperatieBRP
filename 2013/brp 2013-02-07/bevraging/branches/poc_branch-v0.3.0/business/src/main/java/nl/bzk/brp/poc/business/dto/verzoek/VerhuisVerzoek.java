/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.poc.business.dto.verzoek;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashSet;

import nl.bzk.brp.bevraging.business.dto.verzoek.BerichtVerzoek;
import nl.bzk.brp.bevraging.domein.ber.SoortBericht;
import nl.bzk.brp.poc.business.dto.antwoord.BijhoudingResultaat;
import nl.bzk.brp.poc.business.util.DatumUtility;
import nl.bzk.brp.poc.domein.BijhoudingContext;
import nl.bzk.brp.poc.domein.PocPersoon;
import nl.bzk.brp.poc.domein.PocPersoonAdres;

/**
 * Bericht specifieke implementatie van {@link BerichtVerzoek} en {@link BijhoudingVerzoek} welke specifiek is voor
 * een verhuisbericht.
 */
public class VerhuisVerzoek implements BerichtVerzoek<BijhoudingResultaat>, BijhoudingVerzoek<PocPersoon> {

    private final Bijhouding        verhuisBijhouding;
    private final BijhoudingContext bijhoudingContext;
    private       PocPersoon        nieuweRootEntity;

    /**
     * Standaard constructor die direct de in het bericht aanwezige content zet en de bijhoudingscontext bepaald.
     *
     * @param verhuisBijhouding de content van het bericht.
     */
    public VerhuisVerzoek(final Bijhouding verhuisBijhouding) {
        this.verhuisBijhouding = verhuisBijhouding;
        bijhoudingContext = new BijhoudingContext(
                DatumUtility.zetDatumOmNaarInteger(verhuisBijhouding.getVerhuizing().getDatumAanvangGeldigheid()),
                null);
    }

    /**
     * {@inheritDoc}
     * Voor dit bericht komt dit neer op de persoon, maar dan met het nieuwe adres als opgegeven adres van de persoon.
     */
    @Override
    public PocPersoon getNieuweSituatie() {
        if (nieuweRootEntity == null) {
            nieuweRootEntity = bouwRootEntityUitbericht();
        }
        return nieuweRootEntity;
    }

    /**
     * Bouwt de root entity voor dit bericht op, op basis van de in het bericht aanwezige data. Daar in het bericht
     * het adres en de persoon als twee losse entities zijn opgenomen, wordt in deze methode het adres aan de persoon
     * toegevoegd en wordt de persoon dan als root entity geretourneerd.
     *
     * @return de volledige rootentity.
     */
    private PocPersoon bouwRootEntityUitbericht() {
        PocPersoon resultaat = verhuisBijhouding.getVerhuizing().getVerhuizer();
        if (resultaat.getAdressen() == null) {
            resultaat.setAdressen(new HashSet<PocPersoonAdres>());
        }

        PocPersoonAdres adres = verhuisBijhouding.getVerhuizing().getNieuwAdres();
        adres.setPersoon(resultaat);
        adres.setDatumAanvangAdresHouding(bijhoudingContext.getDatumIngangGeldigheid());

        resultaat.getAdressen().add(adres);
        return resultaat;
    }

    @Override
    public BijhoudingContext getBijhoudingContext() {
        return bijhoudingContext;
    }


    // -------------------------------------------------------------------------------------------
    // Voor de POC niet relevante classes welke wel benodigd zijn om aan te sluiten bij bevraging.
    // -------------------------------------------------------------------------------------------

    @Override
    public SoortBericht getSoortBericht() {
        return SoortBericht.POC_VERHUIZING_VRAAG;
    }

    @Override
    public Calendar getBeschouwing() {
        return verhuisBijhouding.getTijdstipVerzonden();
    }

    @Override
    public Collection<Long> getReadBsnLocks() {
        return Arrays.asList(verhuisBijhouding.getVerhuizing().getVerhuizer().getBurgerservicenummer());
    }

    @Override
    public Collection<Long> getWriteBsnLocks() {
        return Arrays.asList(verhuisBijhouding.getVerhuizing().getVerhuizer().getBurgerservicenummer());
    }

    @Override
    public Class<BijhoudingResultaat> getAntwoordClass() {
        return BijhoudingResultaat.class;
    }

    public Long getPersoonBsn() {
        return verhuisBijhouding.getVerhuizing().getVerhuizer().getBurgerservicenummer();
    }

}
