/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.mapper;

import nl.bzk.brp.domain.element.AttribuutElement;
import nl.bzk.brp.domain.element.GroepElement;
import nl.bzk.brp.domain.leveringmodel.MetaRecord;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIdentificatienummersInhoud;
import org.springframework.stereotype.Component;

/**
 * Mapt de identificatienummers.
 */
@Component
public abstract class AbstractIdentificatienummersMapper extends AbstractMapper<BrpIdentificatienummersInhoud> {
    private final AttribuutElement administratienummer;
    private final AttribuutElement burgerservicenummer;

    /**
     * Constructor.
     * @param identiteitGroep element voor identiteit groep
     * @param groep element voor te mappen groep
     * @param datumAanvangGeldigheid element voor datum aanvang geldigheid
     * @param datumEindeGeldigheid element voor datum einde geldigheid
     * @param tijdstipRegistratie element voor tijdstip registratie
     * @param tijdstipVerval element voor tijdstip verval
     * @param administratienummer element voor administratienummer
     * @param burgerservicenummer element voor burgerservicenumemr
     */
    //
    /*
     * squid:S00107 Methods should not have too many parameters
     *
     * Terecht, geaccepteerd voor deze klasse.
     */
    protected AbstractIdentificatienummersMapper(
            final GroepElement identiteitGroep,
            final GroepElement groep,
            final AttribuutElement datumAanvangGeldigheid,
            final AttribuutElement datumEindeGeldigheid,
            final AttribuutElement tijdstipRegistratie,
            final AttribuutElement tijdstipVerval,
            final AttribuutElement administratienummer,
            final AttribuutElement burgerservicenummer) {
        super(identiteitGroep, groep, datumAanvangGeldigheid, datumEindeGeldigheid, tijdstipRegistratie, tijdstipVerval);
        this.administratienummer = administratienummer;
        this.burgerservicenummer = burgerservicenummer;
    }

    @Override
    public final BrpIdentificatienummersInhoud mapInhoud(
            final MetaRecord identiteitRecord,
            final MetaRecord record,
            final OnderzoekMapper onderzoekMapper) {
        return new BrpIdentificatienummersInhoud(
                BrpMetaAttribuutMapper.mapBrpString(
                        record.getAttribuut(administratienummer),
                        onderzoekMapper.bepaalOnderzoek(record.getVoorkomensleutel(), administratienummer, true)),
                BrpMetaAttribuutMapper.mapBrpString(
                        record.getAttribuut(burgerservicenummer),
                        onderzoekMapper.bepaalOnderzoek(record.getVoorkomensleutel(), burgerservicenummer, true)));
    }
}
