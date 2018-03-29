/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.brpnaarlo3.adapter.entity;

import java.sql.Timestamp;
import java.util.Set;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.BRPActie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonGeslachtsnaamcomponent;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonGeslachtsnaamcomponentHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.AdellijkeTitel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Predicaat;
import nl.bzk.migratiebrp.test.brpnaarlo3.adapter.ConverterContext;
import nl.bzk.migratiebrp.test.brpnaarlo3.adapter.OnbekendeHeaderException;

import org.springframework.stereotype.Component;

/**
 * GeslachtsnaamComponent converter.
 */
@Component
public final class PersoonGeslachtsnaamcomponentHistorieConverter extends AbstractEntityHistorieConverter<PersoonGeslachtsnaamcomponentHistorie> {

    private static final String HEADER_TYPE = "kern.his_persgeslnaamcomp";
    private static final String HEADER_VOLG_NUMMER = "volgnr";
    private static final String HEADER_PREDICAAT = "predicaat";
    private static final String HEADER_ADELLIJKETITEL = "adellijketitel";
    private static final String HEADER_VOORVOEGSEL = "voorvoegsel";
    private static final String HEADER_SCHEIDINGSTEKEN = "scheidingsteken";
    private static final String HEADER_STAM = "stam";

    private Persoon persoon;
    private Integer volgnr;
    private Integer datumAanvangGeldigheid;
    private Integer datumEindeGeldigheid;
    private String stam;
    private Character scheidingsteken;
    private Timestamp datumTijdRegistratie;
    private Timestamp datumTijdVerval;
    private String voorvoegsel;
    private BRPActie actieAanpassingGeldigheid;
    private BRPActie actieVerval;
    private BRPActie actieInhoud;
    private AdellijkeTitel adellijkeTitel;
    private Predicaat predicaat;

    /**
     * Default constructor.
     */
    public PersoonGeslachtsnaamcomponentHistorieConverter() {
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
            case HEADER_VOLG_NUMMER:
                volgnr = Integer.valueOf(value);
                break;
            case HEADER_PREDICAAT:
                predicaat = Predicaat.parseId(Integer.valueOf(value));
                break;
            case HEADER_ADELLIJKETITEL:
                adellijkeTitel = AdellijkeTitel.parseId(Integer.valueOf(value));
                break;
            case HEADER_VOORVOEGSEL:
                voorvoegsel = value;
                break;
            case HEADER_SCHEIDINGSTEKEN:
                scheidingsteken = value == null || value.isEmpty() ? null : value.charAt(0);
                break;
            case HEADER_STAM:
                stam = value;
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

    private PersoonGeslachtsnaamcomponent getOrCreateGeslachtsnaamComponent(final Persoon eenPersoon, final Integer volgnummer) {
        for (final PersoonGeslachtsnaamcomponent component : eenPersoon.getPersoonGeslachtsnaamcomponentSet()) {
            if (component.getVolgnummer().equals(volgnummer)) {
                return component;
            }
        }

        final PersoonGeslachtsnaamcomponent component = new PersoonGeslachtsnaamcomponent(eenPersoon, volgnummer);
        eenPersoon.addPersoonGeslachtsnaamcomponent(component);

        return component;
    }

    @Override
    protected void maakHistorieEntity(final ConverterContext context) {
        final PersoonGeslachtsnaamcomponent component = getOrCreateGeslachtsnaamComponent(persoon, volgnr);
        final PersoonGeslachtsnaamcomponentHistorie historie = new PersoonGeslachtsnaamcomponentHistorie(component, stam);
        historie.setActieAanpassingGeldigheid(actieAanpassingGeldigheid);
        historie.setActieInhoud(actieInhoud);
        historie.setActieVerval(actieVerval);
        historie.setAdellijkeTitel(adellijkeTitel);
        historie.setDatumAanvangGeldigheid(datumAanvangGeldigheid);
        historie.setDatumEindeGeldigheid(datumEindeGeldigheid);
        historie.setDatumTijdRegistratie(datumTijdRegistratie);
        historie.setDatumTijdVerval(datumTijdVerval);
        historie.setPredicaat(predicaat);
        historie.setScheidingsteken(scheidingsteken);
        historie.setVoorvoegsel(voorvoegsel);

        component.addPersoonGeslachtsnaamcomponentHistorie(historie);
    }

    @Override
    protected void resetConverter() {
        persoon = null;
        volgnr = null;
        datumAanvangGeldigheid = null;
        datumEindeGeldigheid = null;
        stam = null;
        scheidingsteken = null;
        datumTijdRegistratie = null;
        datumTijdVerval = null;
        voorvoegsel = null;
        actieAanpassingGeldigheid = null;
        actieVerval = null;
        actieInhoud = null;
        adellijkeTitel = null;
        predicaat = null;
    }

    @Override
    protected void vulActueelLaag() {
        for (final PersoonGeslachtsnaamcomponent component : persoon.getPersoonGeslachtsnaamcomponentSet()) {
            final Set<PersoonGeslachtsnaamcomponentHistorie> historieSet = component.getPersoonGeslachtsnaamcomponentHistorieSet();
            vulActueelVanuit(component, getActueel(historieSet));
        }
    }
}
