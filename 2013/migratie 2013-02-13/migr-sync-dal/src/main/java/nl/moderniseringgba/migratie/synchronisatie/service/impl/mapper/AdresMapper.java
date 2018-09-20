/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.synchronisatie.service.impl.mapper;

import java.math.BigDecimal;

import nl.moderniseringgba.migratie.conversie.model.brp.BrpStapel;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpAdresInhoud;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.FunctieAdres;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.HistorieStatus;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.PersoonAdres;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.PersoonAdresHistorie;
import nl.moderniseringgba.migratie.synchronisatie.repository.DynamischeStamtabelRepository;
import nl.moderniseringgba.migratie.synchronisatie.service.impl.mapper.strategie.AbstractHistorieMapperStrategie;
import nl.moderniseringgba.migratie.synchronisatie.service.impl.mapper.strategie.BRPActieFactory;
import nl.moderniseringgba.migratie.synchronisatie.service.impl.mapper.strategie.MapperUtil;

/**
 * Deze mapper mapped BrpAdresInhoud uit het migratie model op de corresponderen BRP entiteiten uit het operationele
 * datamodel van de BRP.
 * 
 */
public final class AdresMapper extends
        AbstractHistorieMapperStrategie<BrpAdresInhoud, PersoonAdresHistorie, PersoonAdres> {

    /**
     * Maakt een AdresMapper object.
     * 
     * @param dynamischeStamtabelRepository
     *            de repository die bevraging van de stamtabellen mogelijk maakt
     * @param brpActieFactory
     *            de factory die gebruikt wordt voor het mappen van BRP acties
     */
    public AdresMapper(
            final DynamischeStamtabelRepository dynamischeStamtabelRepository,
            final BRPActieFactory brpActieFactory) {
        super(dynamischeStamtabelRepository, brpActieFactory);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void voegHistorieToeAanEntiteit(final PersoonAdresHistorie historie, final PersoonAdres entiteit) {
        entiteit.addPersoonAdresHistorie(historie);
    }

    @Override
    protected void mapActueleGegevens(final BrpStapel<BrpAdresInhoud> brpStapel, final PersoonAdres adres) {
        adres.setPersoonAdresStatusHistorie(HistorieStatus.bepaalHistorieStatus(brpStapel));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void kopieerActueleGroepNaarEntiteit(final PersoonAdresHistorie historie, final PersoonAdres adres) {
        adres.setAangeverAdreshouding(historie.getAangeverAdreshouding());
        adres.setAdresseerbaarObject(historie.getAdresseerbaarObject());
        adres.setAfgekorteNaamOpenbareRuimte(historie.getAfgekorteNaamOpenbareRuimte());
        adres.setBuitenlandsAdresRegel1(historie.getBuitenlandsAdresRegel1());
        adres.setBuitenlandsAdresRegel2(historie.getBuitenlandsAdresRegel2());
        adres.setBuitenlandsAdresRegel3(historie.getBuitenlandsAdresRegel3());
        adres.setBuitenlandsAdresRegel4(historie.getBuitenlandsAdresRegel4());
        adres.setBuitenlandsAdresRegel5(historie.getBuitenlandsAdresRegel5());
        adres.setBuitenlandsAdresRegel6(historie.getBuitenlandsAdresRegel6());
        adres.setDatumAanvangAdreshouding(historie.getDatumAanvangAdreshouding());
        adres.setDatumVertrekUitNederland(historie.getDatumVertrekUitNederland());
        adres.setFunctieAdres(historie.getFunctieAdres());
        adres.setGemeentedeel(historie.getGemeentedeel());
        adres.setHuisletter(historie.getHuisletter());
        adres.setHuisnummer(historie.getHuisnummer());
        adres.setHuisnummertoevoeging(historie.getHuisnummertoevoeging());
        adres.setIdentificatiecodeNummeraanduiding(historie.getIdentificatiecodeNummeraanduiding());
        adres.setLand(historie.getLand());
        adres.setLocatieOmschrijving(historie.getLocatieOmschrijving());
        adres.setLocatietovAdres(historie.getLocatietovAdres());
        adres.setNaamOpenbareRuimte(historie.getNaamOpenbareRuimte());
        adres.setAfgekorteNaamOpenbareRuimte(historie.getAfgekorteNaamOpenbareRuimte());
        adres.setPartij(historie.getPartij());
        adres.setPlaats(historie.getPlaats());
        adres.setPostcode(historie.getPostcode());
        adres.setRedenWijzigingAdres(historie.getRedenWijzigingAdres());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected PersoonAdresHistorie mapHistorischeGroep(final BrpAdresInhoud groepInhoud) {
        final PersoonAdresHistorie result = new PersoonAdresHistorie();
        result.setAangeverAdreshouding(getStamtabelMapping().findAangeverAdreshouding(
                groepInhoud.getAangeverAdreshoudingCode()));
        result.setAdresseerbaarObject(groepInhoud.getAdresseerbaarObject());
        result.setAfgekorteNaamOpenbareRuimte(groepInhoud.getAfgekorteNaamOpenbareRuimte());
        result.setBuitenlandsAdresRegel1(groepInhoud.getBuitenlandsAdresRegel1());
        result.setBuitenlandsAdresRegel2(groepInhoud.getBuitenlandsAdresRegel2());
        result.setBuitenlandsAdresRegel3(groepInhoud.getBuitenlandsAdresRegel3());
        result.setBuitenlandsAdresRegel4(groepInhoud.getBuitenlandsAdresRegel4());
        result.setBuitenlandsAdresRegel5(groepInhoud.getBuitenlandsAdresRegel5());
        result.setBuitenlandsAdresRegel6(groepInhoud.getBuitenlandsAdresRegel6());
        result.setDatumAanvangAdreshouding(MapperUtil.mapBrpDatumToBigDecimal(groepInhoud
                .getDatumAanvangAdreshouding()));
        result.setDatumVertrekUitNederland(MapperUtil.mapBrpDatumToBigDecimal(groepInhoud
                .getDatumVertrekUitNederland()));
        if (groepInhoud.getFunctieAdresCode() != null) {
            result.setFunctieAdres(FunctieAdres.parseCode(groepInhoud.getFunctieAdresCode().name()));
        }
        result.setGemeentedeel(groepInhoud.getGemeentedeel());
        if (groepInhoud.getHuisletter() != null) {
            result.setHuisletter(String.valueOf(groepInhoud.getHuisletter()));
        }
        if (groepInhoud.getHuisnummer() != null) {
            result.setHuisnummer(new BigDecimal(groepInhoud.getHuisnummer()));
        }
        result.setHuisnummertoevoeging(groepInhoud.getHuisnummertoevoeging());
        result.setIdentificatiecodeNummeraanduiding(groepInhoud.getIdentificatiecodeNummeraanduiding());
        result.setLand(getStamtabelMapping().findLandByLandcode(groepInhoud.getLandCode()));
        result.setLocatieOmschrijving(groepInhoud.getLocatieOmschrijving());
        if (groepInhoud.getLocatieTovAdres() != null) {
            result.setLocatietovAdres(groepInhoud.getLocatieTovAdres().getCode());
        }
        result.setNaamOpenbareRuimte(groepInhoud.getNaamOpenbareRuimte());
        result.setAfgekorteNaamOpenbareRuimte(groepInhoud.getAfgekorteNaamOpenbareRuimte());
        result.setPartij(getStamtabelMapping().findPartijByGemeentecode(groepInhoud.getGemeenteCode()));
        result.setPlaats(getStamtabelMapping().findPlaatsByCode(groepInhoud.getPlaatsCode()));
        result.setPostcode(groepInhoud.getPostcode());
        result.setRedenWijzigingAdres(getStamtabelMapping().findRedenWijzigingAdres(
                groepInhoud.getRedenWijzigingAdresCode()));

        return result;
    }
}
