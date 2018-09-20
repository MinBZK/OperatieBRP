/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.mapper;

import nl.bzk.brp.model.algemeen.stamgegeven.kern.ElementEnum;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.operationeel.kern.HisPersoonIdentificatienummersModel;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIdentificatienummersInhoud;
import org.springframework.stereotype.Component;

/**
 * Mapt de identificatienummers.
 */
@Component
public abstract class AbstractIdentificatieNummersMapper
        extends AbstractMaterieelMapper<PersoonHisVolledig, HisPersoonIdentificatienummersModel, BrpIdentificatienummersInhoud>
{
    private final ElementEnum administratienummer;
    private final ElementEnum burgerservicenummer;

    /**
     * Constructor.
     * 
     * @param datumAanvangGeldigheid
     *            element voor datum aanvang geldigheid
     * @param datumEindeGeldigheid
     *            element voor datum einde geldigheid
     * @param tijdstipRegistratie
     *            element voor tijdstip registratie
     * @param tijdstipVerval
     *            element voor tijdstip verval
     * @param administratienummer
     *            element voor administratienummer
     * @param burgerservicenummer
     *            element voor burgerservicenumemr
     */
    protected AbstractIdentificatieNummersMapper(
        final ElementEnum datumAanvangGeldigheid,
        final ElementEnum datumEindeGeldigheid,
        final ElementEnum tijdstipRegistratie,
        final ElementEnum tijdstipVerval,
        final ElementEnum administratienummer,
        final ElementEnum burgerservicenummer)
    {
        super(datumAanvangGeldigheid, datumEindeGeldigheid, tijdstipRegistratie, tijdstipVerval);
        this.administratienummer = administratienummer;
        this.burgerservicenummer = burgerservicenummer;
    }

    @Override
    protected final Iterable<HisPersoonIdentificatienummersModel> getHistorieIterable(final PersoonHisVolledig persoonHisVolledig) {
        return persoonHisVolledig.getPersoonIdentificatienummersHistorie();
    }

    @Override
    public final BrpIdentificatienummersInhoud mapInhoud(final HisPersoonIdentificatienummersModel historie, final OnderzoekMapper onderzoekMapper) {
        return new BrpIdentificatienummersInhoud(
            BrpMapperUtil.mapBrpLong(historie.getAdministratienummer(), onderzoekMapper.bepaalOnderzoek(historie.getID(), administratienummer, true)),
            BrpMapperUtil.mapBrpInteger(historie.getBurgerservicenummer(), onderzoekMapper.bepaalOnderzoek(historie.getID(), burgerservicenummer, true)));
    }
}
