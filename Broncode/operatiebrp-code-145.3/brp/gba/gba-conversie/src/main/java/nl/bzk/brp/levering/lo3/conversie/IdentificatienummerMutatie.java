/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.conversie;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import nl.bzk.brp.domain.element.ElementConstants;
import nl.bzk.brp.domain.leveringmodel.Actie;
import nl.bzk.brp.domain.leveringmodel.AdministratieveHandeling;
import nl.bzk.brp.domain.leveringmodel.MetaAttribuut;
import nl.bzk.brp.domain.leveringmodel.MetaObject;
import nl.bzk.brp.domain.leveringmodel.MetaRecord;
import nl.bzk.brp.levering.lo3.mapper.PersoonIdentificatienummersMapper;
import nl.bzk.brp.levering.lo3.util.MetaModelUtil;

/**
 * Identificatienummer mutatie.
 */
public final class IdentificatienummerMutatie {

    private boolean isBsnWijziging;
    private boolean isAnummerWijziging;
    private MetaRecord nieuwRecords;
    private MetaRecord vervallenRecord;

    /**
     * Voer identificatienummer mutatie bepaling uit.
     * @param persoon persoon
     * @param administratieveHandeling administratieve handeling
     */
    public IdentificatienummerMutatie(final MetaObject persoon, final AdministratieveHandeling administratieveHandeling) {
        bepaalIdentificatienummerWijziging(persoon, administratieveHandeling);
    }

    private void bepaalIdentificatienummerWijziging(final MetaObject persoon, final AdministratieveHandeling handeling) {
        final Collection<MetaRecord> records = MetaModelUtil.getRecords(persoon, ElementConstants.PERSOON_IDENTIFICATIENUMMERS);

        final List<Long> acties = handeling.getActies().stream().map(Actie::getId).collect(Collectors.toList());

        nieuwRecords =
                records.stream()
                        .filter(
                                historie -> historie.getActieInhoud() != null
                                        && historie.getActieVerval() == null
                                        && historie.getActieAanpassingGeldigheid() == null
                                        && acties.contains(historie.getActieInhoud().getId()))
                        .findFirst()
                        .orElse(null);

        vervallenRecord = VervalBepaler.bepaalVerval(records, acties);

        bepaalIsAnummerWijziging();
        bepaalIsBsnWijziging();
    }

    private void bepaalIsAnummerWijziging() {
        if (nieuwRecords != null && vervallenRecord != null) {

            final MetaAttribuut nieuwAnummer = nieuwRecords.getAttribuut(PersoonIdentificatienummersMapper.ADMINISTRATIENUMMER_ELEMENT);
            final MetaAttribuut oudAnummer = vervallenRecord.getAttribuut(PersoonIdentificatienummersMapper.ADMINISTRATIENUMMER_ELEMENT);

            if (nieuwAnummer != null && oudAnummer != null && !Objects.equals(nieuwAnummer.getWaarde(), oudAnummer.getWaarde())) {
                isAnummerWijziging = true;
            }
        }
    }

    private void bepaalIsBsnWijziging() {
        if (nieuwRecords != null && vervallenRecord != null) {

            final MetaAttribuut nieuwBsn = nieuwRecords.getAttribuut(PersoonIdentificatienummersMapper.BURGERSERVICENUMMER_ELEMENT);
            final MetaAttribuut oudBsn = vervallenRecord.getAttribuut(PersoonIdentificatienummersMapper.BURGERSERVICENUMMER_ELEMENT);

            if (nieuwBsn != null && oudBsn != null && !Objects.equals(nieuwBsn.getWaarde(), oudBsn.getWaarde())) {
                isBsnWijziging = true;
            }
        }
    }

    // private

    /**
     * indicatie voor a nummer wijziging.
     * @return indicatie of het een a nummer wijziging betreft
     */
    public boolean isAnummerWijziging() {
        return isAnummerWijziging;
    }

    /**
     * indicatie of het een bsn wijziging betreft.
     * @return indicatie of het een bsn wijziging betreft.
     */
    public boolean isBsnWijziging() {
        return isBsnWijziging;
    }

    /**
     * Geeft het metarecord van het nieuwe identificatie nummer terug.
     * @return metarecord identificatienummers
     */
    public MetaRecord getNieuwIdentificatienummersRecord() {
        return nieuwRecords;
    }

    /**
     * Geeft het vervallen metarecord van de vervallen identificatie nummer terug
     * @return metarecord idenitifactienummers
     */
    public MetaRecord getVervallenIdentificatienummersRecord() {
        return vervallenRecord;
    }

}
