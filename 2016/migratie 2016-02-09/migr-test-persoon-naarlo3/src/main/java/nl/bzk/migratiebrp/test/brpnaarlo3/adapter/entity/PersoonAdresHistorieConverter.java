/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.brpnaarlo3.adapter.entity;

import java.sql.Timestamp;
import java.util.Set;
import javax.inject.Inject;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Aangever;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.BRPActie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.FunctieAdres;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Gemeente;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.LandOfGebied;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Persoon;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PersoonAdres;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PersoonAdresHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.RedenWijzigingVerblijf;
import nl.bzk.migratiebrp.test.brpnaarlo3.adapter.ConverterContext;
import nl.bzk.migratiebrp.test.brpnaarlo3.adapter.OnbekendeHeaderException;
import nl.bzk.migratiebrp.test.brpnaarlo3.adapter.property.AangeverAdreshoudingConverter;
import nl.bzk.migratiebrp.test.brpnaarlo3.adapter.property.GemeenteConverter;
import nl.bzk.migratiebrp.test.brpnaarlo3.adapter.property.LandOfGebiedConverter;
import nl.bzk.migratiebrp.test.brpnaarlo3.adapter.property.RedenWijzigingAdresConverter;
import org.springframework.stereotype.Component;

/**
 * Adres converter.
 */
@Component
public final class PersoonAdresHistorieConverter extends AbstractEntityHistorieConverter<PersoonAdresHistorie> {
    private static final String HEADER_TYPE = "kern.his_persadres";
    private static final String HEADER_REDEN_WIJZIGING = "rdnwijz";
    private static final String HEADER_AANGEVER_ADRESHOUDING = "aangadresh";
    private static final String HEADER_DATUM_AANVANG_ADRESHOUDING = "dataanvadresh";
    private static final String HEADER_IDENTIFICATIECODE_ADRESSERBAAR_OBJECT = "identcodeadresseerbaarobject";
    private static final String HEADER_IDENTIFICATIECODE_NUMMER_AANDUIDING = "identcodenraand";
    private static final String HEADER_GEMEENTE = "gem";
    private static final String HEADER_NAAM_OPENBARE_RUIMTE = "nor";
    private static final String HEADER_AFGEKORTE_NAAM_OPENBARE_RUIMTE = "afgekortenor";
    private static final String HEADER_GEMEENTE_DEEL = "gemdeel";
    private static final String HEADER_HUISNUMMER = "huisnr";
    private static final String HEADER_HUISLETTER = "huisletter";
    private static final String HEADER_HUISNUMMER_TOEVOEGING = "huisnrtoevoeging";
    private static final String HEADER_POSTCODE = "postcode";
    private static final String HEADER_WOONPLAATSNAAM = "wplnaam";
    private static final String HEADER_LOCATIE_TOV_ADRES = "loctenopzichtevanadres";
    private static final String HEADER_LOCATIE_OMSCHRIJVING = "locoms";
    private static final String HEADER_BUITENLANDSE_ADRES_REGEL_1 = "bladresregel1";
    private static final String HEADER_BUITENLANDSE_ADRES_REGEL_2 = "bladresregel2";
    private static final String HEADER_BUITENLANDSE_ADRES_REGEL_3 = "bladresregel3";
    private static final String HEADER_BUITENLANDSE_ADRES_REGEL_4 = "bladresregel4";
    private static final String HEADER_BUITENLANDSE_ADRES_REGEL_5 = "bladresregel5";
    private static final String HEADER_BUITENLANDSE_ADRES_REGEL_6 = "bladresregel6";
    private static final String HEADER_LAND_OF_GEBIED = "landgebied";
    private static final String HEADER_INDICATIE_PERSOON_AANGETROFFEN_OP_ADRES = "indpersaangetroffenopadr";

    @Inject
    private RedenWijzigingAdresConverter redenWijzigingAdresConverter;
    @Inject
    private AangeverAdreshoudingConverter aangeverAdreshoudingConverter;
    @Inject
    private GemeenteConverter gemeenteConverter;
    @Inject
    private LandOfGebiedConverter landOfGebiedConverter;

    private Persoon persoon;
    private String adresseerbaarObject;
    private String afgekorteNaamOpenbareRuimte;
    private String buitenlandsAdresRegel1;
    private String buitenlandsAdresRegel2;
    private String buitenlandsAdresRegel3;
    private String buitenlandsAdresRegel4;
    private String buitenlandsAdresRegel5;
    private String buitenlandsAdresRegel6;
    private Integer datumAanvangAdreshouding;
    private Integer datumAanvangGeldigheid;
    private Integer datumEindeGeldigheid;
    private String gemeentedeel;
    private Character huisletter;
    private Integer huisnummer;
    private String huisnummertoevoeging;
    private String identificatiecodeNummeraanduiding;
    private String locatieOmschrijving;
    private String locatietovAdres;
    private String naamOpenbareRuimte;
    private String postcode;
    private Timestamp datumTijdRegistratie;
    private Timestamp datumTijdVerval;
    private Aangever aangeverAdreshouding;
    private BRPActie actieInhoud;
    private BRPActie actieVerval;
    private BRPActie actieAanpassingGeldigheid;
    private FunctieAdres soortAdres;
    private LandOfGebied landOfGebied;
    private Gemeente gemeente;
    private String woonplaatsnaam;
    private RedenWijzigingVerblijf redenWijzigingAdres;
    private Boolean indicatiePersoonAangetroffenOpAdres;

    /**
     * Default constructor.
     */
    protected PersoonAdresHistorieConverter() {
        super(HEADER_TYPE);
    }

    @Override
    protected void resetConverter() {
        persoon = null;
        landOfGebied = null;
        indicatiePersoonAangetroffenOpAdres = null;
        adresseerbaarObject = null;
        afgekorteNaamOpenbareRuimte = null;
        buitenlandsAdresRegel1 = null;
        buitenlandsAdresRegel2 = null;
        buitenlandsAdresRegel3 = null;
        buitenlandsAdresRegel4 = null;
        buitenlandsAdresRegel5 = null;
        buitenlandsAdresRegel6 = null;
        datumAanvangAdreshouding = null;
        datumAanvangGeldigheid = null;
        datumEindeGeldigheid = null;
        gemeentedeel = null;
        huisletter = null;
        huisnummer = null;
        huisnummertoevoeging = null;
        identificatiecodeNummeraanduiding = null;
        locatieOmschrijving = null;
        locatietovAdres = null;
        naamOpenbareRuimte = null;
        postcode = null;
        datumTijdRegistratie = null;
        datumTijdVerval = null;
        aangeverAdreshouding = null;
        actieInhoud = null;
        actieVerval = null;
        actieAanpassingGeldigheid = null;
        soortAdres = null;
        gemeente = null;
        woonplaatsnaam = null;
        redenWijzigingAdres = null;
    }

    @Override
    protected void maakHistorieEntity(final ConverterContext context) {
        final PersoonAdres adres = new PersoonAdres(persoon);
        final PersoonAdresHistorie persoonAdresHistorie = new PersoonAdresHistorie(adres, soortAdres, landOfGebied, redenWijzigingAdres);
        persoonAdresHistorie.setIdentificatiecodeAdresseerbaarObject(adresseerbaarObject);
        persoonAdresHistorie.setAfgekorteNaamOpenbareRuimte(afgekorteNaamOpenbareRuimte);
        persoonAdresHistorie.setBuitenlandsAdresRegel1(buitenlandsAdresRegel1);
        persoonAdresHistorie.setBuitenlandsAdresRegel2(buitenlandsAdresRegel2);
        persoonAdresHistorie.setBuitenlandsAdresRegel3(buitenlandsAdresRegel3);
        persoonAdresHistorie.setBuitenlandsAdresRegel4(buitenlandsAdresRegel4);
        persoonAdresHistorie.setBuitenlandsAdresRegel5(buitenlandsAdresRegel5);
        persoonAdresHistorie.setBuitenlandsAdresRegel6(buitenlandsAdresRegel6);
        persoonAdresHistorie.setDatumAanvangAdreshouding(datumAanvangAdreshouding);
        persoonAdresHistorie.setDatumAanvangGeldigheid(datumAanvangGeldigheid);
        persoonAdresHistorie.setDatumEindeGeldigheid(datumEindeGeldigheid);
        persoonAdresHistorie.setGemeentedeel(gemeentedeel);
        persoonAdresHistorie.setHuisletter(huisletter);
        persoonAdresHistorie.setHuisnummer(huisnummer);
        persoonAdresHistorie.setHuisnummertoevoeging(huisnummertoevoeging);
        persoonAdresHistorie.setIdentificatiecodeNummeraanduiding(identificatiecodeNummeraanduiding);
        persoonAdresHistorie.setLocatieOmschrijving(locatieOmschrijving);
        persoonAdresHistorie.setLocatietovAdres(locatietovAdres);
        persoonAdresHistorie.setNaamOpenbareRuimte(naamOpenbareRuimte);
        persoonAdresHistorie.setPostcode(postcode);
        persoonAdresHistorie.setDatumTijdRegistratie(datumTijdRegistratie);
        persoonAdresHistorie.setDatumTijdVerval(datumTijdVerval);
        persoonAdresHistorie.setAangeverAdreshouding(aangeverAdreshouding);
        persoonAdresHistorie.setActieInhoud(actieInhoud);
        persoonAdresHistorie.setActieVerval(actieVerval);
        persoonAdresHistorie.setActieAanpassingGeldigheid(actieAanpassingGeldigheid);
        persoonAdresHistorie.setGemeente(gemeente);
        persoonAdresHistorie.setWoonplaatsnaam(woonplaatsnaam);
        persoonAdresHistorie.setIndicatiePersoonAangetroffenOpAdres(indicatiePersoonAangetroffenOpAdres);

        adres.addPersoonAdresHistorie(persoonAdresHistorie);
        persoon.addPersoonAdres(adres);
    }

    @Override
    public void convertInhoudelijk(final ConverterContext context, final String header, final String value) {
        switch (header) {
            case HEADER_TYPE:
                break;
            case HEADER_PERSOON:
                persoon = context.getPersoon(Integer.parseInt(value));
                break;
            case HEADER_SOORT:
                soortAdres = FunctieAdres.parseId(Short.valueOf(value));
                break;
            case HEADER_REDEN_WIJZIGING:
                redenWijzigingAdres = redenWijzigingAdresConverter.convert(value);
                break;
            case HEADER_AANGEVER_ADRESHOUDING:
                aangeverAdreshouding = aangeverAdreshoudingConverter.convert(value);
                break;
            case HEADER_DATUM_AANVANG_ADRESHOUDING:
                datumAanvangAdreshouding = Integer.valueOf(value);
                break;
            case HEADER_IDENTIFICATIECODE_ADRESSERBAAR_OBJECT:
                adresseerbaarObject = value;
                break;
            case HEADER_IDENTIFICATIECODE_NUMMER_AANDUIDING:
                identificatiecodeNummeraanduiding = value;
                break;
            case HEADER_GEMEENTE:
                gemeente = gemeenteConverter.convert(value);
                break;
            case HEADER_NAAM_OPENBARE_RUIMTE:
                naamOpenbareRuimte = value;
                break;
            case HEADER_AFGEKORTE_NAAM_OPENBARE_RUIMTE:
                afgekorteNaamOpenbareRuimte = value;
                break;
            case HEADER_GEMEENTE_DEEL:
                gemeentedeel = value;
                break;
            case HEADER_HUISNUMMER:
                huisnummer = Integer.valueOf(value);
                break;
            case HEADER_HUISLETTER:
                huisletter = value == null || value.isEmpty() ? null : value.charAt(0);
                break;
            case HEADER_HUISNUMMER_TOEVOEGING:
                huisnummertoevoeging = value;
                break;
            case HEADER_POSTCODE:
                postcode = value;
                break;
            case HEADER_WOONPLAATSNAAM:
                woonplaatsnaam = value;
                break;
            case HEADER_LOCATIE_TOV_ADRES:
                locatietovAdres = value;
                break;
            case HEADER_LOCATIE_OMSCHRIJVING:
                locatieOmschrijving = value;
                break;
            case HEADER_BUITENLANDSE_ADRES_REGEL_1:
                buitenlandsAdresRegel1 = value;
                break;
            case HEADER_BUITENLANDSE_ADRES_REGEL_2:
                buitenlandsAdresRegel2 = value;
                break;
            case HEADER_BUITENLANDSE_ADRES_REGEL_3:
                buitenlandsAdresRegel3 = value;
                break;
            case HEADER_BUITENLANDSE_ADRES_REGEL_4:
                buitenlandsAdresRegel4 = value;
                break;
            case HEADER_BUITENLANDSE_ADRES_REGEL_5:
                buitenlandsAdresRegel5 = value;
                break;
            case HEADER_BUITENLANDSE_ADRES_REGEL_6:
                buitenlandsAdresRegel6 = value;
                break;
            case HEADER_LAND_OF_GEBIED:
                landOfGebied = landOfGebiedConverter.convert(value);
                break;
            case HEADER_INDICATIE_PERSOON_AANGETROFFEN_OP_ADRES:
                indicatiePersoonAangetroffenOpAdres = Boolean.valueOf(value);
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

    @Override
    protected void vulActueelLaag() {
        for (final PersoonAdres adres : persoon.getPersoonAdresSet()) {
            final Set<PersoonAdresHistorie> historieSet = adres.getPersoonAdresHistorieSet();
            vulActueelVanuit(adres, getActueel(historieSet));
        }
    }
}
