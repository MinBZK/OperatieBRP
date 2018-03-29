/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.autorisatie;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonAfnemerindicatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonAfnemerindicatieHistorie;
import nl.bzk.algemeenbrp.dal.repositories.DynamischeStamtabelRepository;
import nl.bzk.migratiebrp.conversie.model.brp.groep.autorisatie.BrpAfnemersindicatieInhoud;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.AbstractHistorieZonderVerantwoordingMapperStrategie;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.MapperUtil;

/**
 * Mapper verantwoordelijk voor het mappen van het BRP model naar een PersoonAfnemerindicatie entiteit.
 */
public final class PersoonAfnemerindicatieMapper
        extends AbstractHistorieZonderVerantwoordingMapperStrategie<BrpAfnemersindicatieInhoud, PersoonAfnemerindicatieHistorie, PersoonAfnemerindicatie> {

    /**
     * Constructor.
     * @param dynamischeStamtabelRepository dynamische stamtabel repository om oa partijen te bevragen
     */
    public PersoonAfnemerindicatieMapper(final DynamischeStamtabelRepository dynamischeStamtabelRepository) {
        super(dynamischeStamtabelRepository, null);
    }

    @Override
    protected void voegHistorieToeAanEntiteit(final PersoonAfnemerindicatieHistorie historie, final PersoonAfnemerindicatie entiteit) {
        entiteit.getPersoonAfnemerindicatieHistorieSet().add(historie);
    }

    @Override
    protected void kopieerActueleGroepNaarEntiteit(final PersoonAfnemerindicatieHistorie historie, final PersoonAfnemerindicatie entiteit) {
        if (historie != null) {
            entiteit.setDatumAanvangMaterielePeriode(historie.getDatumAanvangMaterielePeriode());
            entiteit.setDatumEindeVolgen(historie.getDatumEindeVolgen());
        }
    }

    @Override
    protected PersoonAfnemerindicatieHistorie mapHistorischeGroep(final BrpAfnemersindicatieInhoud groepInhoud, final PersoonAfnemerindicatie entiteit) {

        final Integer datumAanvangMaterielePeriode = MapperUtil.mapBrpDatumToInteger(groepInhoud.getDatumAanvangMaterielePeriode());
        final Integer datumEindeVolgen = MapperUtil.mapBrpDatumToInteger(groepInhoud.getDatumEindeVolgen());

        final PersoonAfnemerindicatieHistorie result = new PersoonAfnemerindicatieHistorie(entiteit);
        result.setDatumAanvangMaterielePeriode(datumAanvangMaterielePeriode);
        result.setDatumEindeVolgen(datumEindeVolgen);

        return result;
    }
}
