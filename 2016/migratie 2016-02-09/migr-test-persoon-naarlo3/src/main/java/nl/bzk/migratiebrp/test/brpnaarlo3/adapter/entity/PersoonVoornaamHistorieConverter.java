/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.brpnaarlo3.adapter.entity;

import java.sql.Timestamp;
import java.util.Set;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.BRPActie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Persoon;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PersoonVoornaam;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PersoonVoornaamHistorie;
import nl.bzk.migratiebrp.test.brpnaarlo3.adapter.ConverterContext;
import nl.bzk.migratiebrp.test.brpnaarlo3.adapter.OnbekendeHeaderException;
import org.springframework.stereotype.Component;

/**
 * Voornaam converter.
 */
@Component
public final class PersoonVoornaamHistorieConverter extends AbstractEntityHistorieConverter<PersoonVoornaamHistorie> {

    private static final String HEADER_TYPE = "kern.his_persvoornaam";
    private static final String HEADER_VOLGNUMMER = "volgnr";
    private static final String HEADER_NAAM = "naam";

    private Integer datumAanvangGeldigheid;
    private Integer datumEindeGeldigheid;
    private String naam;
    private Timestamp datumTijdRegistratie;
    private Timestamp datumTijdVerval;
    private BRPActie actieAanpassingGeldigheid;
    private BRPActie actieVerval;
    private BRPActie actieInhoud;
    private Integer volgnummer;
    private Persoon persoon;

    /**
     * Default constructor.
     */
    public PersoonVoornaamHistorieConverter() {
        super(HEADER_TYPE);
    }

    @Override
    protected void convertInhoudelijk(final ConverterContext context, final String header, final String value) {
        switch (header) {
            case HEADER_TYPE:
                break;
            case HEADER_PERSOON:
                persoon = context.getPersoon(Integer.parseInt(value));
                break;
            case HEADER_VOLGNUMMER:
                volgnummer = Integer.valueOf(value);
                break;
            case HEADER_NAAM:
                naam = value;
                break;
            case HEADER_DATUM_AANVANG_GELDIGHEID:
                datumAanvangGeldigheid = Integer.valueOf(value);
                break;
            case HEADER_DATUM_EINDE_GELDIGHEID:
                datumEindeGeldigheid = Integer.valueOf(value);
                break;
            case HEADER_TIJDSTIP_REGISTRATIE:
                datumTijdRegistratie = maakTimestamp(value);
                break;
            case HEADER_TIJDSTIP_VERVAL:
                datumTijdVerval = maakTimestamp(value);
                break;
            case HEADER_ACTIE_INHOUD:
                actieInhoud = context.getActie(Integer.parseInt(value));
                break;
            case HEADER_ACTIE_VERVAL:
                actieVerval = context.getActie(Integer.parseInt(value));
                break;
            case HEADER_ACTIE_AANPASSING_GELDIGHEID:
                actieAanpassingGeldigheid = context.getActie(Integer.parseInt(value));
                break;
            default:
                throw new OnbekendeHeaderException(header, getName());
        }
    }

    /**
     * Geef de waarde van or create persoon voornaam.
     *
     * @return or create persoon voornaam
     */
    private PersoonVoornaam getOrCreatePersoonVoornaam() {
        for (final PersoonVoornaam component : persoon.getPersoonVoornaamSet()) {
            if (component.getVolgnummer() == volgnummer) {
                return component;
            }
        }

        final PersoonVoornaam component = new PersoonVoornaam(persoon, volgnummer);
        persoon.addPersoonVoornaam(component);

        return component;
    }

    @Override
    protected void maakHistorieEntity(final ConverterContext context) {
        final PersoonVoornaam persoonVoornaam = getOrCreatePersoonVoornaam();
        final PersoonVoornaamHistorie historie = new PersoonVoornaamHistorie(persoonVoornaam, naam);
        historie.setActieAanpassingGeldigheid(actieAanpassingGeldigheid);
        historie.setActieInhoud(actieInhoud);
        historie.setActieVerval(actieVerval);
        historie.setDatumAanvangGeldigheid(datumAanvangGeldigheid);
        historie.setDatumEindeGeldigheid(datumEindeGeldigheid);
        historie.setDatumTijdRegistratie(datumTijdRegistratie);
        historie.setDatumTijdVerval(datumTijdVerval);

        persoonVoornaam.addPersoonVoornaamHistorie(historie);
    }

    @Override
    protected void resetConverter() {
        datumAanvangGeldigheid = null;
        datumEindeGeldigheid = null;
        naam = null;
        datumTijdRegistratie = null;
        datumTijdVerval = null;
        actieAanpassingGeldigheid = null;
        actieVerval = null;
        actieInhoud = null;
        volgnummer = null;
        persoon = null;
    }

    @Override
    protected void vulActueelLaag() {
        for (final PersoonVoornaam persoonVoornaam : persoon.getPersoonVoornaamSet()) {
            final Set<PersoonVoornaamHistorie> historieSet = persoonVoornaam.getPersoonVoornaamHistorieSet();
            vulActueelVanuit(persoonVoornaam, getActueel(historieSet));
        }
    }
}
