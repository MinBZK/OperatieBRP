/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.vergelijker;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import nl.bzk.migratiebrp.bericht.model.BerichtSyntaxException;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatumTijd;
import nl.bzk.migratiebrp.conversie.model.exceptions.Lo3SyntaxException;
import nl.bzk.migratiebrp.conversie.model.exceptions.OngeldigePersoonslijstException;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.ActieBron;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.AdministratieveHandeling;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.BRPActie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.DeltaRootEntiteit;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Document;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.DocumentHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.EntiteitSleutel;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.FormeleHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Lo3Voorkomen;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Partij;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Persoon;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PersoonAdres;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PersoonAdresHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PersoonBijhoudingHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PersoonGeslachtsnaamcomponent;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PersoonGeslachtsnaamcomponentHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PersoonIndicatie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PersoonNaamgebruikHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PersoonNationaliteit;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PersoonNationaliteitHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PersoonReisdocument;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PersoonSamengesteldeNaamHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PersoonVoornaam;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.SoortActie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.SoortAdministratieveHandeling;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.SoortDocument;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.SoortPersoon;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.DeltaVergelijkerResultaat;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.TestPersonen;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.VergelijkerResultaat;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.Verschil;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.VerschilType;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.util.DeltaRootEntiteitModus;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.MapperUtil;
import nl.bzk.migratiebrp.synchronisatie.dal.util.DBUnit;
import nl.bzk.migratiebrp.util.excel.ExcelAdapterException;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.junit.Assert;
import org.junit.Test;

/**
 * Test class voor de {@link DeltaRootEntiteitVergelijker}.
 */
public class DeltaRootEntiteitVergelijkerTest extends AbstractVergelijkerTest {

    private static final String TESTDATA_FOLDER_NAME = "deltaTestdata/";

    /*
     * (non-Javadoc)
     * 
     * @see nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.AbstractVergelijkerTest#getTestDataFolderName()
     */
    @Override
    protected String getTestDataFolderName() {
        return TESTDATA_FOLDER_NAME;
    }

    private VergelijkerResultaat vergelijkPersonen(final String testnaam)
        throws BerichtSyntaxException, OngeldigePersoonslijstException, ExcelAdapterException, Lo3SyntaxException, IOException
    {
        final TestPersonen personen = getTestPersonen(testnaam);

        final DeltaRootEntiteit dbPersoon = personen.getDbPersoon();
        final DeltaRootEntiteit kluizenaar = personen.getKluizenaar();

        final DeltaRootEntiteitVergelijker deltaRootEntiteitVergelijker = new DeltaRootEntiteitVergelijker(DeltaRootEntiteitModus.PERSOON);
        final VergelijkerResultaat resultaat = deltaRootEntiteitVergelijker.vergelijk(null, dbPersoon, kluizenaar);
        final boolean isEuVerkiezingeDeelnameAanwezig = isEuVerkiezingeDeelnameAanwezig(dbPersoon, kluizenaar);

        final VergelijkerResultaat vergelijkerResultaat = new DeltaVergelijkerResultaat();
        vergelijkerResultaat.voegVerschillenToe(resultaat.getVerschillen());
        vergelijkerResultaat.voegActieHerkomstMapInhoudToe(resultaat.getActieHerkomstMap());
        testAlgemeneWijzigingen(vergelijkerResultaat, isEuVerkiezingeDeelnameAanwezig);
        return vergelijkerResultaat;
    }

    private boolean isEuVerkiezingeDeelnameAanwezig(final DeltaRootEntiteit dbPersoon, final DeltaRootEntiteit kluizenaar) {
        final boolean result;
        if (Persoon.class.isAssignableFrom(dbPersoon.getClass()) && Persoon.class.isAssignableFrom(kluizenaar.getClass())) {
            result =
                    !((Persoon) dbPersoon).getPersoonDeelnameEuVerkiezingenHistorieSet().isEmpty()
                     && !((Persoon) kluizenaar).getPersoonDeelnameEuVerkiezingenHistorieSet().isEmpty();
        } else {
            result = false;
        }
        return result;
    }

    /**
     * Test of de standaard gewijzigde items. LETOP Verwijderd de verschillen nadat deze gecontroleerd zijn.
     */
    private void testAlgemeneWijzigingen(final VergelijkerResultaat vergelijkerResultaat, final boolean isDeelnameEUAanwezig) {
        assertTrue(vergelijkerResultaat.verwijderVerschil(vergelijkerResultaat.zoekVerschil(new EntiteitSleutel(Persoon.class, "versienummer", null))));
        assertTrue(
            vergelijkerResultaat.verwijderVerschil(
                vergelijkerResultaat.zoekVerschil(new EntiteitSleutel(Persoon.class, "tijdstipLaatsteWijziging", null))));
        assertTrue(
            vergelijkerResultaat.verwijderVerschil(
                vergelijkerResultaat.zoekVerschil(new EntiteitSleutel(Persoon.class, "tijdstipLaatsteWijzigingGbaSystematiek", null))));
        final Verschil persoonDatumTijdStempelVerschil =
 vergelijkerResultaat.zoekVerschil(new EntiteitSleutel(Persoon.class, "datumtijdstempel", null));
        assertTrue(vergelijkerResultaat.verwijderVerschil(persoonDatumTijdStempelVerschil));

        final Timestamp oudeDatumtijdstempel = (Timestamp) persoonDatumTijdStempelVerschil.getOudeWaarde();
        final Timestamp nieuweDatumtijdstempel = (Timestamp) persoonDatumTijdStempelVerschil.getNieuweWaarde();

        controleerRijAangepastObvHistorieCat07(
            vergelijkerResultaat,
            PersoonIndicatie.class,
            "persoonIndicatieHistorieSet",
            oudeDatumtijdstempel,
            nieuweDatumtijdstempel,
            new EntiteitSleutel(Persoon.class, "persoonIndicatieSet", null));
        controleerRijVerwijderdToegevoegd(
            vergelijkerResultaat,
            Persoon.class,
            "persoonInschrijvingHistorieSet",
            oudeDatumtijdstempel,
            nieuweDatumtijdstempel,
            null);
        controleerRijAangepastObvHistorieCat07(
            vergelijkerResultaat,
            Persoon.class,
            "persoonPersoonskaartHistorieSet",
            oudeDatumtijdstempel,
            nieuweDatumtijdstempel,
            null);
        if (isDeelnameEUAanwezig) {
            controleerRijAangepastObvHistorieCat07(
                vergelijkerResultaat,
                Persoon.class,
                "persoonDeelnameEuVerkiezingenHistorieSet",
                oudeDatumtijdstempel,
                nieuweDatumtijdstempel,
                null);
        }
    }

    private void controleerRijAangepastObvHistorieCat07(
        final VergelijkerResultaat vergelijkerResultaat,
        final Class<?> entiteit,
        final String veldnaam,
        final Timestamp oudeDatumtijdstempel,
        final Timestamp nieuweDatumtijdstempel,
        final EntiteitSleutel eigenaarSleutel)
    {
        final EntiteitSleutel oudeSleutel = new EntiteitSleutel(entiteit, veldnaam, eigenaarSleutel);
        final EntiteitSleutel nieuweSleutel = new EntiteitSleutel(entiteit, veldnaam, eigenaarSleutel);

        final Verschil historieVerschil = zoekVerschilHistorieEntry(vergelijkerResultaat, oudeSleutel, oudeDatumtijdstempel, nieuweDatumtijdstempel);
        final Verschil actieVerschil = zoekVerschilActieEntry(vergelijkerResultaat, nieuweSleutel, oudeDatumtijdstempel, nieuweDatumtijdstempel);
        Assert.assertNotNull(historieVerschil);
        Assert.assertNotNull(actieVerschil);

        assertTrue(vergelijkerResultaat.verwijderVerschil(historieVerschil));
        assertTrue(vergelijkerResultaat.verwijderVerschil(actieVerschil));
    }

    private Verschil zoekVerschilHistorieEntry(
        final VergelijkerResultaat vergelijkerResultaat,
        final EntiteitSleutel sleutel,
        final Timestamp oudeDatumtijdstempel,
        final Timestamp nieuweDatumtijdstempel)
    {
        Verschil resultaat = null;
        for (final Verschil verschil : vergelijkerResultaat.getVerschillen()) {
            final EntiteitSleutel entrySleutel = (EntiteitSleutel) verschil.getSleutel();
            final boolean isJuisteSleutel =
                    entrySleutel.getEigenaarSleutel() != null
                                            && entrySleutel.getEigenaarSleutel().equalsIgnoreDelen(sleutel)
                                            && entrySleutel.getVeld().equals("datumTijdRegistratie");
            if (isJuisteSleutel && isJuisteDatumTijdRegistratieVerschil(verschil, oudeDatumtijdstempel, nieuweDatumtijdstempel)) {
                resultaat = verschil;
                break;
            }
        }
        return resultaat;
    }

    private boolean isJuisteDatumTijdRegistratieVerschil(
        final Verschil verschil,
        final Timestamp oudeDatumtijdstempel,
        final Timestamp nieuweDatumtijdstempel)
    {
        boolean result = false;
        if (verschil.getVerschilType().equals(VerschilType.ELEMENT_AANGEPAST)) {
            if (verschil.getOudeWaarde() instanceof BRPActie) {
                final BRPActie oudeWaarde = (BRPActie) verschil.getOudeWaarde();
                final BRPActie nieuweWaarde = (BRPActie) verschil.getNieuweWaarde();
                result =
                        isJuisteTijdstempel(oudeDatumtijdstempel, oudeWaarde.getDatumTijdRegistratie())
                         && isJuisteTijdstempel(nieuweDatumtijdstempel, nieuweWaarde.getDatumTijdRegistratie());
            } else {
                result =
                        isJuisteTijdstempel(oudeDatumtijdstempel, (Timestamp) verschil.getOudeWaarde())
                         && isJuisteTijdstempel(nieuweDatumtijdstempel, (Timestamp) verschil.getNieuweWaarde());
            }
        }
        return result;
    }

    private boolean isJuisteTijdstempel(final Timestamp tijdstempel1, final Timestamp tijdstempel2) {
        final Calendar cal1 = Calendar.getInstance();
        final Calendar cal2 = Calendar.getInstance();
        cal1.setTime(tijdstempel1);
        cal2.setTime(tijdstempel2);
        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)
               && cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH)
               && cal1.get(Calendar.DATE) == cal2.get(Calendar.DATE);
    }

    private Verschil zoekVerschilActieEntry(
        final VergelijkerResultaat vergelijkerResultaat,
        final EntiteitSleutel sleutel,
        final Timestamp oudeDatumtijdstempel,
        final Timestamp nieuweDatumtijdstempel)
    {
        Verschil resultaat = null;
        for (final Verschil verschil : vergelijkerResultaat.getVerschillen()) {
            final EntiteitSleutel entrySleutel = (EntiteitSleutel) verschil.getSleutel();
            final boolean isJuisteSleutel =
                    entrySleutel.getEigenaarSleutel() != null
                                            && entrySleutel.getEigenaarSleutel().equalsIgnoreDelen(sleutel)
                                            && entrySleutel.getVeld().equals("actieInhoud");
            if (isJuisteSleutel && isJuisteDatumTijdRegistratieVerschil(verschil, oudeDatumtijdstempel, nieuweDatumtijdstempel)) {
                resultaat = verschil;
                break;
            }
        }
        return resultaat;
    }

    private void controleerRijVerwijderdToegevoegd(
        final VergelijkerResultaat vergelijkerResultaat,
        final Class<?> entiteit,
        final String veldnaam,
        final Timestamp oudeDatumtijdstempel,
        final Timestamp nieuweDatumtijdstempel,
        final EntiteitSleutel eigenaarSleutel)
    {
        final EntiteitSleutel oudeSleutel = new EntiteitSleutel(entiteit, veldnaam, eigenaarSleutel);
        final EntiteitSleutel nieuweSleutel = new EntiteitSleutel(entiteit, veldnaam, eigenaarSleutel);

        final Verschil oudeSleutelVerschil = zoekVerschilEntry(vergelijkerResultaat, oudeSleutel, oudeDatumtijdstempel);
        final Verschil nieuweSleutelVerschil = zoekVerschilEntry(vergelijkerResultaat, nieuweSleutel, nieuweDatumtijdstempel);
        Assert.assertNotNull(oudeSleutelVerschil);
        Assert.assertNotNull(nieuweSleutelVerschil);

        final boolean oudeRijGevonden = VerschilType.RIJ_VERWIJDERD.equals(oudeSleutelVerschil.getVerschilType());
        final boolean nieuweRijGevonden = VerschilType.RIJ_TOEGEVOEGD.equals(nieuweSleutelVerschil.getVerschilType());

        assertTrue(oudeRijGevonden && nieuweRijGevonden);
        assertTrue(vergelijkerResultaat.verwijderVerschil(oudeSleutelVerschil));
        assertTrue(vergelijkerResultaat.verwijderVerschil(nieuweSleutelVerschil));
    }

    private Verschil zoekVerschilEntry(final VergelijkerResultaat vergelijkerResultaat, final EntiteitSleutel sleutel, final Timestamp datumtijdstempel) {
        final String tsregVeld = "tsreg";
        Verschil resultaat = null;
        for (final Verschil verschil : vergelijkerResultaat.getVerschillen()) {
            final EntiteitSleutel entrySleutel = (EntiteitSleutel) verschil.getSleutel();
            if (entrySleutel.equalsIgnoreDelen(sleutel)) {
                final Timestamp sleutelTsreg = (Timestamp) entrySleutel.getDelen().get(tsregVeld);
                final Calendar datumtijd = Calendar.getInstance();
                final Calendar tsRegDatumTijd = Calendar.getInstance();
                datumtijd.setTime(datumtijdstempel);
                tsRegDatumTijd.setTime(sleutelTsreg);
                if (tsRegDatumTijd.get(Calendar.YEAR) == datumtijd.get(Calendar.YEAR)
                    && tsRegDatumTijd.get(Calendar.MONTH) == datumtijd.get(Calendar.MONTH)
                    && tsRegDatumTijd.get(Calendar.DATE) == datumtijd.get(Calendar.DATE))
                {
                    resultaat = verschil;
                    break;
                }
            }
        }
        return resultaat;
    }

    /**
     * Dezelfde PL-en worden aangeboden.
     *
     * @throws IOException
     * @throws BerichtSyntaxException
     *             als het LO3 bericht niet opgebouwd kan worden
     * @throws OngeldigePersoonslijstException
     *             wordt gegooid als de PL niet geldig is als er een fout optreedt tijdens de delta-vergelijking
     * @throws ExcelAdapterException
     *             als er een fout optreedt tijdens het inlezen van een Excel-bestand
     * @throws Lo3SyntaxException
     *             als de LO3 PL een syntaxfout bevat
     */
    @DBUnit.InsertBefore({SQL_DATA_BRP_STAMGEGEVENS_KERN_XML,
                          SQL_DATA_BRP_STAMGEGEVENS_AUTAUT_XML,
                          SQL_DATA_BRP_STAMGEGEVENS_CONV_XML,
                          SQL_DATA_BRP_STAMGEGEVENS_VERCONV_XML })
    @Test
    public void testDELTAVERS01C10T10()
        throws IOException, BerichtSyntaxException, OngeldigePersoonslijstException, ExcelAdapterException, Lo3SyntaxException
    {
        final VergelijkerResultaat resultaat = vergelijkPersonen("DELTAVERS01C10T10");

        final Set<Verschil> verschillen = new HashSet<>(resultaat.getVerschillen());
        assertTrue(verschillen.isEmpty());

        final Map<BRPActie, Lo3Voorkomen> actieHerkomstMap = resultaat.getActieHerkomstMap();
        controleerHerkomstGelijk("01", 0, 0, actieHerkomstMap);
        controleerHerkomstGelijk("04", 0, 0, actieHerkomstMap);
        controleerHerkomstGelijk("06", 0, 0, actieHerkomstMap);
        controleerHerkomstGelijk("08", 0, 0, actieHerkomstMap);
        controleerHerkomstGelijk("10", 0, 0, actieHerkomstMap);
        controleerHerkomstGelijk("12", 0, 0, actieHerkomstMap);
        assertEquals(AANTAL_AANPASSINGEN_HERKOMST_NIET_JUIST, 6, actieHerkomstMap.size());
    }

    /**
     * <LL>
     * <LI>Cat 04, 06, 10, 12 en 13 worden toegevoegd</LI>
     * <LI>Een voornaam wordt toegevoegd (er was nog geen voornaam)</LI>
     * <LI>Een onderzoek op 61.10 wordt toegevoegd in cat 01</LI> </LL>
     *
     * @throws IOException
     * @throws BerichtSyntaxException
     * @throws OngeldigePersoonslijstException
     * @throws ExcelAdapterException
     * @throws Lo3SyntaxException
     */
    @DBUnit.InsertBefore({SQL_DATA_BRP_STAMGEGEVENS_KERN_XML,
                          SQL_DATA_BRP_STAMGEGEVENS_AUTAUT_XML,
                          SQL_DATA_BRP_STAMGEGEVENS_CONV_XML,
                          SQL_DATA_BRP_STAMGEGEVENS_VERCONV_XML })
    @Test
    public void testDELTAVERS01C20T10()
        throws IOException, BerichtSyntaxException, OngeldigePersoonslijstException, ExcelAdapterException, Lo3SyntaxException
    {
        final VergelijkerResultaat resultaat = vergelijkPersonen("DELTAVERS01C20T10");

        final Set<Verschil> verschillen = new HashSet<>(resultaat.getVerschillen());
        controleerCat04Toegevoegd(verschillen, false);
        controleerCat06Toegevoegd(verschillen);
        controleerCat10Toegevoegd(verschillen);
        controleerCat12Toegevoegd(verschillen);
        controleerCat13Toegevoegd(verschillen);
        controleerVoornaamToegevoegd(verschillen, false);

        assertTrue(MEER_VERSCHILLEN_DAN_VERWACHT_GEKREGEN, verschillen.isEmpty());

        final Map<BRPActie, Lo3Voorkomen> actieHerkomstMap = resultaat.getActieHerkomstMap();
        controleerHerkomstGelijk("01", 0, 0, actieHerkomstMap);
        controleerHerkomstGelijk("08", 0, 0, actieHerkomstMap);
        assertEquals(AANTAL_AANPASSINGEN_HERKOMST_NIET_JUIST, 2, actieHerkomstMap.size());
    }

    private void controleerCat04Toegevoegd(final Set<Verschil> verschillen, final boolean bestaandeLijst) {
        final VerschilType verschilType;
        if (bestaandeLijst) {
            verschilType = VerschilType.RIJ_TOEGEVOEGD;
        } else {
            verschilType = VerschilType.RIJ_TOEGEVOEGD;
        }
        final VerwachtSleutel nationaliteitRij = new VerwachtSleutel(Persoon.class, VELD_NATIONALITEIT);
        final VerwachtVerschil persoonNationaliteitRij = new VerwachtVerschil(nationaliteitRij, false, true, verschilType);
        controleerVerschillen(verschillen, persoonNationaliteitRij);
    }

    private void controleerCat06Toegevoegd(final Set<Verschil> verschillen) {
        final VerwachtSleutel persoonDatumOverlijdenSleutel = new VerwachtSleutel(Persoon.class, "datumOverlijden");
        final VerwachtSleutel persoonGemeenteOverlijdenSleutel = new VerwachtSleutel(Persoon.class, "gemeenteOverlijden");
        final VerwachtSleutel persoonLandOfGebiedOverlijdenSleutel = new VerwachtSleutel(Persoon.class, "landOfGebiedOverlijden");
        final VerwachtSleutel persoonOverlijdenHistoriePersoonOverlijdenHistorieSetSleutel =
                new VerwachtSleutel(Persoon.class, "persoonOverlijdenHistorieSet");

        final VerwachtVerschil persoonDatumOverlijden = new VerwachtVerschil(persoonDatumOverlijdenSleutel, false, true, VerschilType.ELEMENT_NIEUW);
        final VerwachtVerschil persoonGemeenteOverlijden = new VerwachtVerschil(persoonGemeenteOverlijdenSleutel, false, true, VerschilType.ELEMENT_NIEUW);
        final VerwachtVerschil persoonLandOfGebiedOverlijden =
                new VerwachtVerschil(persoonLandOfGebiedOverlijdenSleutel, false, true, VerschilType.ELEMENT_NIEUW);
        final VerwachtVerschil persoonOverlijdenHistorie =
                new VerwachtVerschil(persoonOverlijdenHistoriePersoonOverlijdenHistorieSetSleutel, false, true, VerschilType.RIJ_TOEGEVOEGD);
        controleerVerschillen(verschillen, persoonDatumOverlijden, persoonGemeenteOverlijden, persoonLandOfGebiedOverlijden, persoonOverlijdenHistorie);
    }

    private void controleerCat10Toegevoegd(final Set<Verschil> verschillen) {
        final VerwachtSleutel persoonDatumAanvangSleutel = new VerwachtSleutel(Persoon.class, "datumAanvangVerblijfsrecht");
        final VerwachtSleutel persoonDatumEindeSleutel = new VerwachtSleutel(Persoon.class, "datumVoorzienEindeVerblijfsrecht");
        final VerwachtSleutel persoonVerblijfsrechtSleutel = new VerwachtSleutel(Persoon.class, "verblijfsrecht");
        final VerwachtSleutel persoonVerblijfsrechtHistorieSleutel = new VerwachtSleutel(Persoon.class, "persoonVerblijfsrechtHistorieSet");

        final VerwachtVerschil persoonDatumAanvang = new VerwachtVerschil(persoonDatumAanvangSleutel, false, true, VerschilType.ELEMENT_NIEUW);
        final VerwachtVerschil persoonDatumEinde = new VerwachtVerschil(persoonDatumEindeSleutel, false, true, VerschilType.ELEMENT_NIEUW);
        final VerwachtVerschil persoonVerblijfsrecht = new VerwachtVerschil(persoonVerblijfsrechtSleutel, false, true, VerschilType.ELEMENT_NIEUW);
        final VerwachtVerschil persoonVerblijfsrechtHistorie =
                new VerwachtVerschil(persoonVerblijfsrechtHistorieSleutel, false, true, VerschilType.RIJ_TOEGEVOEGD);

        controleerVerschillen(verschillen, persoonDatumAanvang, persoonDatumEinde, persoonVerblijfsrecht, persoonVerblijfsrechtHistorie);
    }

    private void controleerCat12Toegevoegd(final Set<Verschil> verschillen) {
        final VerwachtSleutel persoonReisdocumentSleutel = new VerwachtSleutel(Persoon.class, "persoonReisdocumentSet");
        final VerwachtVerschil persoonReisdocument = new VerwachtVerschil(persoonReisdocumentSleutel, false, true, VerschilType.RIJ_TOEGEVOEGD);

        controleerVerschillen(verschillen, persoonReisdocument);
    }

    private void controleerCat13Toegevoegd(final Set<Verschil> verschillen) {
        final VerwachtSleutel persoonAanleidingAanpassingSleutel = new VerwachtSleutel(Persoon.class, "datumAanleidingAanpassingDeelnameEuVerkiezing");
        final VerwachtSleutel persoonIndicatieDeelnameSleutel = new VerwachtSleutel(Persoon.class, "indicatieDeelnameEuVerkiezingen");
        final VerwachtSleutel persoonDeelnameEUHistorieSleutel = new VerwachtSleutel(Persoon.class, "persoonDeelnameEuVerkiezingenHistorieSet");

        final VerwachtVerschil persoonAanleidingAanpassing =
                new VerwachtVerschil(persoonAanleidingAanpassingSleutel, false, true, VerschilType.ELEMENT_NIEUW);
        final VerwachtVerschil persoonIndicatieDeelname = new VerwachtVerschil(persoonIndicatieDeelnameSleutel, false, true, VerschilType.ELEMENT_NIEUW);
        final VerwachtVerschil persoonDeelnameEUHistorie =
                new VerwachtVerschil(persoonDeelnameEUHistorieSleutel, false, true, VerschilType.RIJ_TOEGEVOEGD);

        controleerVerschillen(verschillen, persoonAanleidingAanpassing, persoonIndicatieDeelname, persoonDeelnameEUHistorie);
    }

    private void controleerVoornaamToegevoegd(final Set<Verschil> verschillen, final boolean isOudeWaardeAanwezig) {
        final VerwachtSleutel persoonVoornaamSetSleutel = new VerwachtSleutel(Persoon.class, "persoonVoornaamSet");
        final VerschilType verwachteVerschilType;
        final VerschilType verwachteLijstVerschilType;

        if (isOudeWaardeAanwezig) {
            verwachteLijstVerschilType = VerschilType.RIJ_TOEGEVOEGD;
            verwachteVerschilType = VerschilType.ELEMENT_AANGEPAST;
        } else {
            verwachteLijstVerschilType = VerschilType.RIJ_TOEGEVOEGD;
            verwachteVerschilType = VerschilType.ELEMENT_NIEUW;
        }

        final VerwachtSleutel persoonVoornaamSleutel = new VerwachtSleutel(Persoon.class, VELD_VOORNAMEN);
        final VerwachtSleutel persoonVoornaamGebruikSleutel = new VerwachtSleutel(Persoon.class, VELD_VOORNAMEN_NAAMGEBRUIK);
        final VerwachtSleutel persoonSamengesteldeHistorieSleutel = new VerwachtSleutel(PersoonSamengesteldeNaamHistorie.class, VELD_VOORNAMEN);
        final VerwachtSleutel persoonNaamgebruikHistorieSleutel = new VerwachtSleutel(PersoonNaamgebruikHistorie.class, VELD_VOORNAMEN_NAAMGEBRUIK);

        final VerwachtVerschil persoonVoornaam = new VerwachtVerschil(persoonVoornaamSleutel, isOudeWaardeAanwezig, true, verwachteVerschilType);
        final VerwachtVerschil persoonVoornaamGebruik =
                new VerwachtVerschil(persoonVoornaamGebruikSleutel, isOudeWaardeAanwezig, true, verwachteVerschilType);
        final VerwachtVerschil persoonVoornaamSet = new VerwachtVerschil(persoonVoornaamSetSleutel, false, true, verwachteLijstVerschilType);
        final VerwachtVerschil persoonSamengesteldeHistorie =
                new VerwachtVerschil(persoonSamengesteldeHistorieSleutel, isOudeWaardeAanwezig, true, verwachteVerschilType);
        final VerwachtVerschil persoonNaamgebruikHistorie =
                new VerwachtVerschil(persoonNaamgebruikHistorieSleutel, isOudeWaardeAanwezig, true, verwachteVerschilType);

        controleerVerschillen(
            verschillen,
            persoonVoornaam,
            persoonVoornaamGebruik,
            persoonVoornaamSet,
            persoonSamengesteldeHistorie,
            persoonNaamgebruikHistorie);
    }

    private void controleerVerschillen(final Set<Verschil> verschillen, final VerwachtVerschil... verwachteVerschillen) {
        final int verwachtAantalVerschillen = verwachteVerschillen.length;

        int aantalGevondenVerschillen = 0;
        for (final VerwachtVerschil verwachtVerschil : verwachteVerschillen) {
            final VerwachtSleutel verwachtSleutel = verwachtVerschil.getVerwachtSleutel();
            Verschil gevondenVerschil = null;
            for (final Verschil verschil : verschillen) {
                final EntiteitSleutel sleutel = (EntiteitSleutel) verschil.getSleutel();
                if (verwachtSleutel.komtOvereenMetSleutel(sleutel)) {
                    if (VELD_ONDERZOEK.equals(sleutel.getVeld())) {
                        final String onderzoekVeld = verwachtSleutel.getOnderzoekVeld();
                        if (onderzoekVeld != null && !onderzoekVeld.equals(sleutel.getDelen().get("onderzoekVeld"))) {
                            continue;
                        }
                    }
                    assertTrue(
                        "Verschillen oude waarden",
                        verwachtVerschil.isOudVerwacht() ? verschil.getOudeWaarde() != null : verschil.getOudeWaarde() == null);
                    assertTrue(
                        "Verschillen nieuwe waarde",
                        verwachtVerschil.isNieuwVerwacht() ? verschil.getNieuweWaarde() != null : verschil.getNieuweWaarde() == null);
                    assertEquals("Verschillen type", verwachtVerschil.getVerwachtType(), verschil.getVerschilType());
                    aantalGevondenVerschillen++;
                    gevondenVerschil = verschil;
                    break;
                }
            }
            verschillen.remove(gevondenVerschil);
        }

        if (verwachtAantalVerschillen != aantalGevondenVerschillen) {
            Assert.fail(
                String.format(
                    "Aantal gevonden verschillen (%d) komt niet overeen met het aantal verwachte verschillen (%d)",
                    aantalGevondenVerschillen,
                    verwachtAantalVerschillen));
        }
    }

    /**
     * Een voornaam wordt toegevoegd aan een bestaande voornaam, Cat 04 wordt toegevoegd, terwijl er al een cat 04 was,
     * Toevoeging immigratie aan bestaande cat 08,
     *
     * @throws IOException
     * @throws BerichtSyntaxException
     * @throws OngeldigePersoonslijstException
     * @throws ExcelAdapterException
     * @throws Lo3SyntaxException
     */
    @DBUnit.InsertBefore({SQL_DATA_BRP_STAMGEGEVENS_KERN_XML,
                          SQL_DATA_BRP_STAMGEGEVENS_AUTAUT_XML,
                          SQL_DATA_BRP_STAMGEGEVENS_CONV_XML,
                          SQL_DATA_BRP_STAMGEGEVENS_VERCONV_XML })
    @Test
    public void testDELTAVERS01C20T20()
        throws IOException, BerichtSyntaxException, OngeldigePersoonslijstException, ExcelAdapterException, Lo3SyntaxException
    {
        final VergelijkerResultaat resultaat = vergelijkPersonen("DELTAVERS01C20T20");

        final Set<Verschil> verschillen = new HashSet<>(resultaat.getVerschillen());
        controleerVoornaamToegevoegd(verschillen, true);
        controleerCat04Toegevoegd(verschillen, true);
        controleerImmigratieToegevoegd(verschillen);
        assertTrue(MEER_VERSCHILLEN_DAN_VERWACHT_GEKREGEN, verschillen.isEmpty());

        final Map<BRPActie, Lo3Voorkomen> actieHerkomstMap = resultaat.getActieHerkomstMap();
        controleerHerkomstGelijk("01", 0, 0, actieHerkomstMap);
        controleerHerkomstGelijk("04", 0, 0, actieHerkomstMap);
        controleerHerkomstGelijk("06", 0, 0, actieHerkomstMap);
        controleerHerkomstGelijk("08", 0, 0, actieHerkomstMap);
        controleerHerkomstGelijk("10", 0, 0, actieHerkomstMap);
        controleerHerkomstGelijk("12", 0, 0, actieHerkomstMap);
        assertEquals(AANTAL_AANPASSINGEN_HERKOMST_NIET_JUIST, 6, actieHerkomstMap.size());
    }

    private void controleerImmigratieToegevoegd(final Set<Verschil> verschillen) {
        final VerwachtSleutel persoonSoortMigratieSleutel = new VerwachtSleutel(Persoon.class, "soortMigratieId");
        final VerwachtSleutel persoonRedenWijzigingMigratieSleutel = new VerwachtSleutel(Persoon.class, "redenWijzigingMigratie");
        final VerwachtSleutel persoonLandOfGebiedMigratieSleutel = new VerwachtSleutel(Persoon.class, "landOfGebiedMigratie");
        final VerwachtSleutel persoonMigratieHistorieSleutel = new VerwachtSleutel(Persoon.class, "persoonMigratieHistorieSet");

        final VerwachtVerschil persoonSoortMigratie = new VerwachtVerschil(persoonSoortMigratieSleutel, false, true, VerschilType.ELEMENT_NIEUW);
        final VerwachtVerschil persoonRedenWijzigingMigratie =
                new VerwachtVerschil(persoonRedenWijzigingMigratieSleutel, false, true, VerschilType.ELEMENT_NIEUW);
        final VerwachtVerschil persoonLandOfGebiedMigratie =
                new VerwachtVerschil(persoonLandOfGebiedMigratieSleutel, false, true, VerschilType.ELEMENT_NIEUW);
        final VerwachtVerschil persoonMigratieHistorie = new VerwachtVerschil(persoonMigratieHistorieSleutel, false, true, VerschilType.RIJ_TOEGEVOEGD);

        controleerVerschillen(verschillen, persoonSoortMigratie, persoonRedenWijzigingMigratie, persoonLandOfGebiedMigratie, persoonMigratieHistorie);
    }

    /**
     * Toevoegen behandeld als Nederlander aan lege cat 04, Toevoegen ind onjuist aan cat 54
     *
     * @throws IOException
     * @throws BerichtSyntaxException
     * @throws OngeldigePersoonslijstException
     * @throws ExcelAdapterException
     * @throws Lo3SyntaxException
     */
    @DBUnit.InsertBefore({SQL_DATA_BRP_STAMGEGEVENS_KERN_XML,
                          SQL_DATA_BRP_STAMGEGEVENS_AUTAUT_XML,
                          SQL_DATA_BRP_STAMGEGEVENS_CONV_XML,
                          SQL_DATA_BRP_STAMGEGEVENS_VERCONV_XML })
    @Test
    public void testDELTAVERS01C20T30()
        throws IOException, BerichtSyntaxException, OngeldigePersoonslijstException, ExcelAdapterException, Lo3SyntaxException
    {
        final VergelijkerResultaat resultaat = vergelijkPersonen("DELTAVERS01C20T30");

        final Set<Verschil> verschillen = new HashSet<>(resultaat.getVerschillen());
        final VerwachtSleutel behandelAlsNlIndicatieSleutel = new VerwachtSleutel(Persoon.class, "persoonIndicatieSet");
        final VerwachtSleutel indicatieOnjuistCat54Sleutel = new VerwachtSleutel(PersoonNationaliteitHistorie.class, "nadereAanduidingVerval");

        final VerwachtVerschil behandeldAlsNlIndicatie = new VerwachtVerschil(behandelAlsNlIndicatieSleutel, false, true, VerschilType.RIJ_TOEGEVOEGD);
        final VerwachtVerschil indicatieOnjuistCat54 = new VerwachtVerschil(indicatieOnjuistCat54Sleutel, false, true, VerschilType.ELEMENT_NIEUW);

        controleerVerschillen(verschillen, behandeldAlsNlIndicatie, indicatieOnjuistCat54);

        assertTrue(MEER_VERSCHILLEN_DAN_VERWACHT_GEKREGEN, verschillen.isEmpty());

        final Map<BRPActie, Lo3Voorkomen> actieHerkomstMap = resultaat.getActieHerkomstMap();
        controleerHerkomstGelijk("01", 0, 0, actieHerkomstMap);
        controleerHerkomstGelijk("04", 0, 0, actieHerkomstMap);
        controleerHerkomstGelijk("54", 0, 1, actieHerkomstMap);
        controleerHerkomstGelijk("06", 0, 0, actieHerkomstMap);
        controleerHerkomstGelijk("08", 0, 0, actieHerkomstMap);
        controleerHerkomstGelijk("10", 0, 0, actieHerkomstMap);
        controleerHerkomstGelijk("12", 0, 0, actieHerkomstMap);
        assertEquals(AANTAL_AANPASSINGEN_HERKOMST_NIET_JUIST, 7, actieHerkomstMap.size());
    }

    /**
     * Adellijke titel toegevoegd, Geslachtsnaam gewijzigd, Voorvoegsel geslachtsnaam verwijderd, Huisnummer toevoeging
     * toegevoegd, Huisnummer gewijzigd, Gemeentedeel verwijderd, Onderzoek in cat 04 gewijzigd
     *
     * @throws IOException
     * @throws BerichtSyntaxException
     * @throws OngeldigePersoonslijstException
     * @throws ExcelAdapterException
     * @throws Lo3SyntaxException
     */
    @DBUnit.InsertBefore({SQL_DATA_BRP_STAMGEGEVENS_KERN_XML,
                          SQL_DATA_BRP_STAMGEGEVENS_AUTAUT_XML,
                          SQL_DATA_BRP_STAMGEGEVENS_CONV_XML,
                          SQL_DATA_BRP_STAMGEGEVENS_VERCONV_XML })
    @Test
    public void testDELTAVERS01C30T10()
        throws IOException, BerichtSyntaxException, OngeldigePersoonslijstException, ExcelAdapterException, Lo3SyntaxException
    {
        final VergelijkerResultaat resultaat = vergelijkPersonen("DELTAVERS01C30T10");

        final Set<Verschil> verschillen = new HashSet<>(resultaat.getVerschillen());
        controleerAdellijkTitelToegevoegd(verschillen);
        controleerGeslachtsnaamGewijzigd(verschillen);
        controleerVoorvoegselVerwijderd(verschillen);
        controleerHuisnummerToevoegingToegevoegd(verschillen);
        controleerHuisnummerGewijzigd(verschillen);
        controleerGemeentedeelVerwijderd(verschillen);

        assertTrue(MEER_VERSCHILLEN_DAN_VERWACHT_GEKREGEN, verschillen.isEmpty());

        final Map<BRPActie, Lo3Voorkomen> actieHerkomstMap = resultaat.getActieHerkomstMap();
        controleerHerkomstGelijk("01", 0, 0, actieHerkomstMap);
        controleerHerkomstGelijk("04", 0, 0, actieHerkomstMap);
        controleerHerkomstGelijk("06", 0, 0, actieHerkomstMap);
        controleerHerkomstGelijk("08", 0, 0, actieHerkomstMap);
        controleerHerkomstGelijk("10", 0, 0, actieHerkomstMap);
        controleerHerkomstGelijk("12", 0, 0, actieHerkomstMap);
        assertEquals(AANTAL_AANPASSINGEN_HERKOMST_NIET_JUIST, 6, actieHerkomstMap.size());
    }

    private void controleerAdellijkTitelToegevoegd(final Set<Verschil> verschillen) {
        final String adellijkeTitelIdVeld = "adellijkeTitelId";
        final VerwachtSleutel persoonNaamgebruikSleutel = new VerwachtSleutel(Persoon.class, "adellijkeTitelNaamgebruikId");
        final VerwachtSleutel persoonAdellijkeTitelSleutel = new VerwachtSleutel(Persoon.class, adellijkeTitelIdVeld);
        final VerwachtSleutel persoonGeslachtsnaamHistorieSleutel = new VerwachtSleutel(PersoonGeslachtsnaamcomponentHistorie.class, adellijkeTitelIdVeld);
        final VerwachtSleutel persoonGeslachtsnaamSleutel = new VerwachtSleutel(PersoonGeslachtsnaamcomponent.class, adellijkeTitelIdVeld);
        final VerwachtSleutel persoonSamengesteldeNaamHistorieSleutel = new VerwachtSleutel(PersoonSamengesteldeNaamHistorie.class, adellijkeTitelIdVeld);
        final VerwachtSleutel persoonNaamgebruikHistorieSleutel = new VerwachtSleutel(PersoonNaamgebruikHistorie.class, adellijkeTitelIdVeld);

        final VerwachtVerschil persoonNaamgebruik = new VerwachtVerschil(persoonNaamgebruikSleutel, false, true, VerschilType.ELEMENT_NIEUW);
        final VerwachtVerschil persoonAdellijkeTitel = new VerwachtVerschil(persoonAdellijkeTitelSleutel, false, true, VerschilType.ELEMENT_NIEUW);
        final VerwachtVerschil persoonGeslachtsnaamHistorie =
                new VerwachtVerschil(persoonGeslachtsnaamHistorieSleutel, false, true, VerschilType.ELEMENT_NIEUW);
        final VerwachtVerschil persoonGeslachtsnaam = new VerwachtVerschil(persoonGeslachtsnaamSleutel, false, true, VerschilType.ELEMENT_NIEUW);
        final VerwachtVerschil persoonSamengesteldeNaamHistorie =
                new VerwachtVerschil(persoonSamengesteldeNaamHistorieSleutel, false, true, VerschilType.ELEMENT_NIEUW);
        final VerwachtVerschil persoonNaamgebruikHistorie =
                new VerwachtVerschil(persoonNaamgebruikHistorieSleutel, false, true, VerschilType.ELEMENT_NIEUW);

        controleerVerschillen(
            verschillen,
            persoonNaamgebruik,
            persoonAdellijkeTitel,
            persoonGeslachtsnaamHistorie,
            persoonGeslachtsnaam,
            persoonSamengesteldeNaamHistorie,
            persoonNaamgebruikHistorie);
    }

    private void controleerGeslachtsnaamGewijzigd(final Set<Verschil> verschillen) {
        final VerwachtSleutel persoonGeslachtsnaamStamSleutel = new VerwachtSleutel(Persoon.class, "geslachtsnaamstam");
        final VerwachtSleutel persoonNaamgebruikSleutel = new VerwachtSleutel(Persoon.class, "geslachtsnaamstamNaamgebruik");
        final VerwachtSleutel persoonGeslachtsnaamComponentSleutel = new VerwachtSleutel(PersoonGeslachtsnaamcomponent.class, "stam");
        final VerwachtSleutel persoonGeslachtsnaamComponentHistorieSleutel = new VerwachtSleutel(PersoonGeslachtsnaamcomponentHistorie.class, "stam");
        final VerwachtSleutel persoonSamengesteldeNaamHistorieSleutel = new VerwachtSleutel(PersoonSamengesteldeNaamHistorie.class, "geslachtsnaamstam");
        final VerwachtSleutel persoonNaamgebruikHistorieSleutel = new VerwachtSleutel(PersoonNaamgebruikHistorie.class, "geslachtsnaamstamNaamgebruik");

        final VerwachtVerschil persoonGeslachtsnaamStam =
                new VerwachtVerschil(persoonGeslachtsnaamStamSleutel, true, true, VerschilType.ELEMENT_AANGEPAST);
        final VerwachtVerschil persoonNaamgebruik = new VerwachtVerschil(persoonNaamgebruikSleutel, true, true, VerschilType.ELEMENT_AANGEPAST);
        final VerwachtVerschil persoonGeslachtsnaamComponent =
                new VerwachtVerschil(persoonGeslachtsnaamComponentSleutel, true, true, VerschilType.ELEMENT_AANGEPAST);
        final VerwachtVerschil persoonGeslachtsnaamComponentHistorie =
                new VerwachtVerschil(persoonGeslachtsnaamComponentHistorieSleutel, true, true, VerschilType.ELEMENT_AANGEPAST);
        final VerwachtVerschil persoonSamengesteldeNaamHistorie =
                new VerwachtVerschil(persoonSamengesteldeNaamHistorieSleutel, true, true, VerschilType.ELEMENT_AANGEPAST);
        final VerwachtVerschil persoonNaamgebruikHistorie =
                new VerwachtVerschil(persoonNaamgebruikHistorieSleutel, true, true, VerschilType.ELEMENT_AANGEPAST);

        controleerVerschillen(
            verschillen,
            persoonGeslachtsnaamStam,
            persoonNaamgebruik,
            persoonGeslachtsnaamComponent,
            persoonGeslachtsnaamComponentHistorie,
            persoonSamengesteldeNaamHistorie,
            persoonNaamgebruikHistorie);
    }

    private void controleerVoorvoegselVerwijderd(final Set<Verschil> verschillen) {
        final String scheidingstekenVeld = "scheidingsteken";
        final String voorvoegselVeld = "voorvoegsel";
        final VerwachtSleutel persoonScheidingstekenSleutel = new VerwachtSleutel(Persoon.class, scheidingstekenVeld);
        final VerwachtSleutel persoonScheidingstekenNaamgebruikSleutel = new VerwachtSleutel(Persoon.class, "scheidingstekenNaamgebruik");
        final VerwachtSleutel persoonVoorvoegselSleutel = new VerwachtSleutel(Persoon.class, voorvoegselVeld);
        final VerwachtSleutel persoonVoorvoegselNaamgebruikSleutel = new VerwachtSleutel(Persoon.class, "voorvoegselNaamgebruik");
        final VerwachtSleutel persoonScheidingstekenGeslachtsnaamComponentSleutel =
                new VerwachtSleutel(PersoonGeslachtsnaamcomponent.class, scheidingstekenVeld);
        final VerwachtSleutel persoonVoorvoegselGeslachtsnaamComponentSleutel = new VerwachtSleutel(PersoonGeslachtsnaamcomponent.class, voorvoegselVeld);
        final VerwachtSleutel persoonScheidingstekenGeslachtsnaamComponentHistorieSleutel =
                new VerwachtSleutel(PersoonGeslachtsnaamcomponentHistorie.class, scheidingstekenVeld);
        final VerwachtSleutel persoonVoorvoegselGeslachtsnaamComponentHistorieSleutel =
                new VerwachtSleutel(PersoonGeslachtsnaamcomponentHistorie.class, voorvoegselVeld);
        final VerwachtSleutel persoonScheidingstekenSamengesteldeNaamHistorieSleutel =
                new VerwachtSleutel(PersoonSamengesteldeNaamHistorie.class, scheidingstekenVeld);
        final VerwachtSleutel persoonVoorvoegselSamengesteldeNaamHistorieSleutel =
                new VerwachtSleutel(PersoonSamengesteldeNaamHistorie.class, voorvoegselVeld);
        final VerwachtSleutel persoonScheidingstekenNaamgebruikHistorieSleutel =
                new VerwachtSleutel(PersoonNaamgebruikHistorie.class, "scheidingstekenNaamgebruik");
        final VerwachtSleutel persoonVoorvoegselNaamgebruikHistorieSleutel =
                new VerwachtSleutel(PersoonNaamgebruikHistorie.class, "voorvoegselNaamgebruik");

        final VerwachtVerschil persoonScheidingsteken = new VerwachtVerschil(persoonScheidingstekenSleutel, true, false, VerschilType.ELEMENT_VERWIJDERD);
        final VerwachtVerschil persoonScheidingstekenNaamgebruik =
                new VerwachtVerschil(persoonScheidingstekenNaamgebruikSleutel, true, false, VerschilType.ELEMENT_VERWIJDERD);
        final VerwachtVerschil persoonVoorvoegsel = new VerwachtVerschil(persoonVoorvoegselSleutel, true, false, VerschilType.ELEMENT_VERWIJDERD);
        final VerwachtVerschil persoonVoorvoegselNaamgebruik =
                new VerwachtVerschil(persoonVoorvoegselNaamgebruikSleutel, true, false, VerschilType.ELEMENT_VERWIJDERD);
        final VerwachtVerschil persoonScheidingstekenGeslachtsnaamComponent =
                new VerwachtVerschil(persoonScheidingstekenGeslachtsnaamComponentSleutel, true, false, VerschilType.ELEMENT_VERWIJDERD);
        final VerwachtVerschil persoonVoorvoegselGeslachtsnaamComponent =
                new VerwachtVerschil(persoonVoorvoegselGeslachtsnaamComponentSleutel, true, false, VerschilType.ELEMENT_VERWIJDERD);
        final VerwachtVerschil persoonScheidingstekenGeslachtsnaamComponentHistorie =
                new VerwachtVerschil(persoonScheidingstekenGeslachtsnaamComponentHistorieSleutel, true, false, VerschilType.ELEMENT_VERWIJDERD);
        final VerwachtVerschil persoonVoorvoegselGeslachtsnaamComponentHistorie =
                new VerwachtVerschil(persoonVoorvoegselGeslachtsnaamComponentHistorieSleutel, true, false, VerschilType.ELEMENT_VERWIJDERD);
        final VerwachtVerschil persoonScheidingstekensSamengesteldeNaamHistorie =
                new VerwachtVerschil(persoonScheidingstekenSamengesteldeNaamHistorieSleutel, true, false, VerschilType.ELEMENT_VERWIJDERD);
        final VerwachtVerschil persoonVoorvoegselSamengesteldeNaamHistorie =
                new VerwachtVerschil(persoonVoorvoegselSamengesteldeNaamHistorieSleutel, true, false, VerschilType.ELEMENT_VERWIJDERD);
        final VerwachtVerschil persoonScheidingstekenNaamgebruikHistorie =
                new VerwachtVerschil(persoonScheidingstekenNaamgebruikHistorieSleutel, true, false, VerschilType.ELEMENT_VERWIJDERD);
        final VerwachtVerschil persoonVoorvoegselNaamgebruikHistorie =
                new VerwachtVerschil(persoonVoorvoegselNaamgebruikHistorieSleutel, true, false, VerschilType.ELEMENT_VERWIJDERD);

        controleerVerschillen(
            verschillen,
            persoonScheidingsteken,
            persoonScheidingstekenNaamgebruik,
            persoonVoorvoegsel,
            persoonVoorvoegselNaamgebruik,
            persoonScheidingstekenGeslachtsnaamComponent,
            persoonVoorvoegselGeslachtsnaamComponent,
            persoonScheidingstekenGeslachtsnaamComponentHistorie,
            persoonVoorvoegselGeslachtsnaamComponentHistorie,
            persoonScheidingstekensSamengesteldeNaamHistorie,
            persoonVoorvoegselSamengesteldeNaamHistorie,
            persoonScheidingstekenNaamgebruikHistorie,
            persoonVoorvoegselNaamgebruikHistorie);
    }

    private void controleerHuisnummerToevoegingToegevoegd(final Set<Verschil> verschillen) {
        final String huisnummerToevoegingVeld = "huisnummertoevoeging";
        final VerwachtSleutel persoonAdresSleutel = new VerwachtSleutel(PersoonAdres.class, huisnummerToevoegingVeld);
        final VerwachtSleutel persoonAdresHistorieSleutel = new VerwachtSleutel(PersoonAdresHistorie.class, huisnummerToevoegingVeld);

        final VerwachtVerschil persoonAdres = new VerwachtVerschil(persoonAdresSleutel, false, true, VerschilType.ELEMENT_NIEUW);
        final VerwachtVerschil persoonAdresHistorie = new VerwachtVerschil(persoonAdresHistorieSleutel, false, true, VerschilType.ELEMENT_NIEUW);

        controleerVerschillen(verschillen, persoonAdres, persoonAdresHistorie);
    }

    private void controleerHuisnummerGewijzigd(final Set<Verschil> verschillen) {
        final String huisnummerVeld = "huisnummer";
        final VerwachtSleutel persoonAdresSleutel = new VerwachtSleutel(PersoonAdres.class, huisnummerVeld);
        final VerwachtSleutel persoonAdresHistorieSleutel = new VerwachtSleutel(PersoonAdresHistorie.class, huisnummerVeld);

        final VerwachtVerschil persoonAdres = new VerwachtVerschil(persoonAdresSleutel, true, true, VerschilType.ELEMENT_AANGEPAST);
        final VerwachtVerschil persoonAdresHistorie = new VerwachtVerschil(persoonAdresHistorieSleutel, true, true, VerschilType.ELEMENT_AANGEPAST);

        controleerVerschillen(verschillen, persoonAdres, persoonAdresHistorie);
    }

    private void controleerGemeentedeelVerwijderd(final Set<Verschil> verschillen) {
        final String gemeentedeelVeld = "gemeentedeel";
        final VerwachtSleutel persoonAdresSleutel = new VerwachtSleutel(PersoonAdres.class, gemeentedeelVeld);
        final VerwachtSleutel persoonAdresHistorieSleutel = new VerwachtSleutel(PersoonAdresHistorie.class, gemeentedeelVeld);

        final VerwachtVerschil persoonAdres = new VerwachtVerschil(persoonAdresSleutel, true, false, VerschilType.ELEMENT_VERWIJDERD);
        final VerwachtVerschil persoonAdresHistorie = new VerwachtVerschil(persoonAdresHistorieSleutel, true, false, VerschilType.ELEMENT_VERWIJDERD);

        controleerVerschillen(verschillen, persoonAdres, persoonAdresHistorie);
    }

    /**
     * Verwijderen cat 04, 06, 10, 12 en 13, Verwijderen voornaam, Verwijderen onderzoek
     *
     * @throws IOException
     * @throws BerichtSyntaxException
     * @throws OngeldigePersoonslijstException
     * @throws ExcelAdapterException
     * @throws Lo3SyntaxException
     */
    @DBUnit.InsertBefore({SQL_DATA_BRP_STAMGEGEVENS_KERN_XML,
                          SQL_DATA_BRP_STAMGEGEVENS_AUTAUT_XML,
                          SQL_DATA_BRP_STAMGEGEVENS_CONV_XML,
                          SQL_DATA_BRP_STAMGEGEVENS_VERCONV_XML })
    @Test
    public void testDELTAVERS01C40T10()
        throws IOException, BerichtSyntaxException, OngeldigePersoonslijstException, ExcelAdapterException, Lo3SyntaxException
    {
        final VergelijkerResultaat resultaat = vergelijkPersonen("DELTAVERS01C40T10");

        final Set<Verschil> verschillen = new HashSet<>(resultaat.getVerschillen());

        controleerCat04Verwijderd(verschillen);
        controleerCat06Verwijderd(verschillen);
        controleerCat10Verwijderd(verschillen);
        controleerCat12Verwijderd(verschillen);
        controleerCat13Verwijderd(verschillen);
        controleerVoornaamVerwijderd(verschillen);

        assertTrue(MEER_VERSCHILLEN_DAN_VERWACHT_GEKREGEN, verschillen.isEmpty());

        final Map<BRPActie, Lo3Voorkomen> actieHerkomstMap = resultaat.getActieHerkomstMap();
        controleerHerkomstGelijk("01", 0, 0, actieHerkomstMap);
        controleerHerkomstGelijk("08", 0, 0, actieHerkomstMap);
        assertEquals(AANTAL_AANPASSINGEN_HERKOMST_NIET_JUIST, 2, actieHerkomstMap.size());
    }

    private void controleerCat04Verwijderd(final Set<Verschil> verschillen) {
        final VerwachtSleutel nationaliteitRij = new VerwachtSleutel(PersoonNationaliteit.class, VELD_NATIONALITEIT_HISTORIE);
        final VerwachtVerschil persoonNationaliteitRij = new VerwachtVerschil(nationaliteitRij, true, false, VerschilType.RIJ_VERWIJDERD);
        controleerVerschillen(verschillen, persoonNationaliteitRij);
    }

    private void controleerCat06Verwijderd(final Set<Verschil> verschillen) {
        final VerwachtSleutel persoonDatumOverlijdenSleutel = new VerwachtSleutel(Persoon.class, "datumOverlijden");
        final VerwachtSleutel persoonGemeenteOverlijdenSleutel = new VerwachtSleutel(Persoon.class, "gemeenteOverlijden");
        final VerwachtSleutel persoonLandOfGebiedOverlijdenSleutel = new VerwachtSleutel(Persoon.class, "landOfGebiedOverlijden");
        final VerwachtSleutel persoonOverlijdenHistoriePersoonOverlijdenHistorieSetSleutel =
                new VerwachtSleutel(Persoon.class, "persoonOverlijdenHistorieSet");

        final VerwachtVerschil persoonDatumOverlijden = new VerwachtVerschil(persoonDatumOverlijdenSleutel, true, false, VerschilType.ELEMENT_VERWIJDERD);
        final VerwachtVerschil persoonGemeenteOverlijden =
                new VerwachtVerschil(persoonGemeenteOverlijdenSleutel, true, false, VerschilType.ELEMENT_VERWIJDERD);
        final VerwachtVerschil persoonLandOfGebiedOverlijden =
                new VerwachtVerschil(persoonLandOfGebiedOverlijdenSleutel, true, false, VerschilType.ELEMENT_VERWIJDERD);
        final VerwachtVerschil persoonOverlijdenHistorie =
                new VerwachtVerschil(persoonOverlijdenHistoriePersoonOverlijdenHistorieSetSleutel, true, false, VerschilType.RIJ_VERWIJDERD);
        controleerVerschillen(verschillen, persoonDatumOverlijden, persoonGemeenteOverlijden, persoonLandOfGebiedOverlijden, persoonOverlijdenHistorie);
    }

    private void controleerCat10Verwijderd(final Set<Verschil> verschillen) {
        final VerwachtSleutel persoonDatumAanvangSleutel = new VerwachtSleutel(Persoon.class, "datumAanvangVerblijfsrecht");
        final VerwachtSleutel persoonDatumEindeSleutel = new VerwachtSleutel(Persoon.class, "datumVoorzienEindeVerblijfsrecht");
        final VerwachtSleutel persoonVerblijfsrechtSleutel = new VerwachtSleutel(Persoon.class, "verblijfsrecht");
        final VerwachtSleutel persoonVerblijfsrechtHistorieSleutel = new VerwachtSleutel(Persoon.class, "persoonVerblijfsrechtHistorieSet");

        final VerwachtVerschil persoonDatumAanvang = new VerwachtVerschil(persoonDatumAanvangSleutel, true, false, VerschilType.ELEMENT_VERWIJDERD);
        final VerwachtVerschil persoonDatumEinde = new VerwachtVerschil(persoonDatumEindeSleutel, true, false, VerschilType.ELEMENT_VERWIJDERD);
        final VerwachtVerschil persoonVerblijfsrecht = new VerwachtVerschil(persoonVerblijfsrechtSleutel, true, false, VerschilType.ELEMENT_VERWIJDERD);
        final VerwachtVerschil persoonVerblijfsrechtHistorie =
                new VerwachtVerschil(persoonVerblijfsrechtHistorieSleutel, true, false, VerschilType.RIJ_VERWIJDERD);

        controleerVerschillen(verschillen, persoonDatumAanvang, persoonDatumEinde, persoonVerblijfsrecht, persoonVerblijfsrechtHistorie);
    }

    private void controleerCat12Verwijderd(final Set<Verschil> verschillen) {
        final VerwachtSleutel persoonReisdocumentSleutel = new VerwachtSleutel(PersoonReisdocument.class, "persoonReisdocumentHistorieSet");
        final VerwachtVerschil persoonReisdocument = new VerwachtVerschil(persoonReisdocumentSleutel, true, false, VerschilType.RIJ_VERWIJDERD);

        controleerVerschillen(verschillen, persoonReisdocument);
    }

    private void controleerCat13Verwijderd(final Set<Verschil> verschillen) {
        final VerwachtSleutel persoonAanleidingAanpassingSleutel = new VerwachtSleutel(Persoon.class, "datumAanleidingAanpassingDeelnameEuVerkiezing");
        final VerwachtSleutel persoonIndicatieDeelnameSleutel = new VerwachtSleutel(Persoon.class, "indicatieDeelnameEuVerkiezingen");
        final VerwachtSleutel persoonDeelnameEUHistorieSleutel = new VerwachtSleutel(Persoon.class, "persoonDeelnameEuVerkiezingenHistorieSet");

        final VerwachtVerschil persoonAanleidingAanpassing =
                new VerwachtVerschil(persoonAanleidingAanpassingSleutel, true, false, VerschilType.ELEMENT_VERWIJDERD);
        final VerwachtVerschil persoonIndicatieDeelname =
                new VerwachtVerschil(persoonIndicatieDeelnameSleutel, true, false, VerschilType.ELEMENT_VERWIJDERD);
        final VerwachtVerschil persoonDeelnameEUHistorie =
                new VerwachtVerschil(persoonDeelnameEUHistorieSleutel, true, false, VerschilType.RIJ_VERWIJDERD);

        controleerVerschillen(verschillen, persoonAanleidingAanpassing, persoonIndicatieDeelname, persoonDeelnameEUHistorie);
    }

    private void controleerVoornaamVerwijderd(final Set<Verschil> verschillen) {
        final VerwachtSleutel persoonVoornaamSleutel = new VerwachtSleutel(Persoon.class, VELD_VOORNAMEN);
        final VerwachtSleutel persoonVoornaamGebruikSleutel = new VerwachtSleutel(Persoon.class, VELD_VOORNAMEN_NAAMGEBRUIK);
        final VerwachtSleutel persoonVoornaamSetSleutel = new VerwachtSleutel(PersoonVoornaam.class, "persoonVoornaamHistorieSet");
        final VerwachtSleutel persoonSamengesteldeHistorieSleutel = new VerwachtSleutel(PersoonSamengesteldeNaamHistorie.class, VELD_VOORNAMEN);
        final VerwachtSleutel persoonNaamgebruikHistorieSleutel = new VerwachtSleutel(PersoonNaamgebruikHistorie.class, VELD_VOORNAMEN_NAAMGEBRUIK);

        final VerwachtVerschil persoonVoornaam = new VerwachtVerschil(persoonVoornaamSleutel, true, false, VerschilType.ELEMENT_VERWIJDERD);
        final VerwachtVerschil persoonVoornaamGebruik = new VerwachtVerschil(persoonVoornaamGebruikSleutel, true, false, VerschilType.ELEMENT_VERWIJDERD);
        final VerwachtVerschil persoonVoornaamSet = new VerwachtVerschil(persoonVoornaamSetSleutel, true, false, VerschilType.RIJ_VERWIJDERD);
        final VerwachtVerschil persoonSamengesteldeHistorie =
                new VerwachtVerschil(persoonSamengesteldeHistorieSleutel, true, false, VerschilType.ELEMENT_VERWIJDERD);
        final VerwachtVerschil persoonNaamgebruikHistorie =
                new VerwachtVerschil(persoonNaamgebruikHistorieSleutel, true, false, VerschilType.ELEMENT_VERWIJDERD);

        controleerVerschillen(
            verschillen,
            persoonVoornaam,
            persoonVoornaamGebruik,
            persoonVoornaamSet,
            persoonSamengesteldeHistorie,
            persoonNaamgebruikHistorie);
    }

    /**
     * Verwijderen 2 categorieën 04, Verwijderen immigratie
     *
     * @throws IOException
     * @throws BerichtSyntaxException
     * @throws OngeldigePersoonslijstException
     * @throws ExcelAdapterException
     * @throws Lo3SyntaxException
     */
    @DBUnit.InsertBefore({SQL_DATA_BRP_STAMGEGEVENS_KERN_XML,
                          SQL_DATA_BRP_STAMGEGEVENS_AUTAUT_XML,
                          SQL_DATA_BRP_STAMGEGEVENS_CONV_XML,
                          SQL_DATA_BRP_STAMGEGEVENS_VERCONV_XML })
    @Test
    public void testDELTAVERS01C40T20()
        throws IOException, BerichtSyntaxException, OngeldigePersoonslijstException, ExcelAdapterException, Lo3SyntaxException
    {
        final VergelijkerResultaat resultaat = vergelijkPersonen("DELTAVERS01C40T20");

        final Set<Verschil> verschillen = new HashSet<>(resultaat.getVerschillen());
        controleer2Cat04Verwijderd(verschillen);
        controleerImmigratieVerwijderd(verschillen);
        assertTrue(MEER_VERSCHILLEN_DAN_VERWACHT_GEKREGEN, verschillen.isEmpty());

        final Map<BRPActie, Lo3Voorkomen> actieHerkomstMap = resultaat.getActieHerkomstMap();
        controleerHerkomstGelijk("01", 0, 0, actieHerkomstMap);
        controleerHerkomstGelijk("06", 0, 0, actieHerkomstMap);
        controleerHerkomstGelijk("08", 0, 0, actieHerkomstMap);
        controleerHerkomstGelijk("10", 0, 0, actieHerkomstMap);
        controleerHerkomstGelijk("12", 0, 0, actieHerkomstMap);
        assertEquals(AANTAL_AANPASSINGEN_HERKOMST_NIET_JUIST, 5, actieHerkomstMap.size());
    }

    /**
     * Beeindigen van 1 van de 2 categorieen 4 dmv nieuwe lege rij.
     *
     * @throws IOException
     * @throws BerichtSyntaxException
     * @throws OngeldigePersoonslijstException
     * @throws ExcelAdapterException
     * @throws Lo3SyntaxException
     */
    @DBUnit.InsertBefore({SQL_DATA_BRP_STAMGEGEVENS_KERN_XML,
                          SQL_DATA_BRP_STAMGEGEVENS_AUTAUT_XML,
                          SQL_DATA_BRP_STAMGEGEVENS_CONV_XML,
                          SQL_DATA_BRP_STAMGEGEVENS_VERCONV_XML })
    @Test
    public void testDELTAVERS01C50T10()
        throws IOException, BerichtSyntaxException, OngeldigePersoonslijstException, ExcelAdapterException, Lo3SyntaxException
    {
        final VerwachtSleutel nationaliteitDatumEindeGeldigheidSleutel = new VerwachtSleutel(PersoonNationaliteitHistorie.class, "datumEindeGeldigheid");
        final VerwachtVerschil nationaliteitDatumEindeGeldigheid =
                new VerwachtVerschil(nationaliteitDatumEindeGeldigheidSleutel, false, true, VerschilType.ELEMENT_NIEUW);
        final VerwachtSleutel nationaliteitActieAanpassingGeldigheidSleutel =
                new VerwachtSleutel(PersoonNationaliteitHistorie.class, "actieAanpassingGeldigheid");
        final VerwachtVerschil nationaliteitActieAanpasingEindeGeldigheid =
                new VerwachtVerschil(nationaliteitActieAanpassingGeldigheidSleutel, false, true, VerschilType.ELEMENT_NIEUW);

        final VergelijkerResultaat resultaat = vergelijkPersonen("DELTAVERS01C50T10");

        final Set<Verschil> verschillen = new HashSet<>(resultaat.getVerschillen());
        controleerVerschillen(verschillen, nationaliteitDatumEindeGeldigheid, nationaliteitActieAanpasingEindeGeldigheid);
        assertTrue(MEER_VERSCHILLEN_DAN_VERWACHT_GEKREGEN, verschillen.isEmpty());

        final Map<BRPActie, Lo3Voorkomen> actieHerkomstMap = resultaat.getActieHerkomstMap();
        controleerHerkomstGelijk("01", 0, 0, actieHerkomstMap);
        controleerHerkomstGelijk("04", 0, 0, actieHerkomstMap);
        controleerHerkomstAanpassing("04", 1, 0, actieHerkomstMap, "54", 1, 1);
        controleerHerkomstGelijk("06", 0, 0, actieHerkomstMap);
        controleerHerkomstGelijk("08", 0, 0, actieHerkomstMap);
        controleerHerkomstGelijk("10", 0, 0, actieHerkomstMap);
        controleerHerkomstGelijk("12", 0, 0, actieHerkomstMap);
        assertEquals(AANTAL_AANPASSINGEN_HERKOMST_NIET_JUIST, 7, actieHerkomstMap.size());
    }

    /**
     * Verwijder de beeindiging van 1 van de 2 categorieen 4 dmv verwijderen actuele lege rij.
     *
     * @throws IOException
     * @throws BerichtSyntaxException
     * @throws OngeldigePersoonslijstException
     * @throws ExcelAdapterException
     * @throws Lo3SyntaxException
     */
    @DBUnit.InsertBefore({SQL_DATA_BRP_STAMGEGEVENS_KERN_XML,
                          SQL_DATA_BRP_STAMGEGEVENS_AUTAUT_XML,
                          SQL_DATA_BRP_STAMGEGEVENS_CONV_XML,
                          SQL_DATA_BRP_STAMGEGEVENS_VERCONV_XML })
    @Test
    public void testDELTAVERS01C50T20()
        throws IOException, BerichtSyntaxException, OngeldigePersoonslijstException, ExcelAdapterException, Lo3SyntaxException
    {
        final VerwachtSleutel nationaliteitDatumEindeGeldigheidSleutel = new VerwachtSleutel(PersoonNationaliteitHistorie.class, "datumEindeGeldigheid");
        final VerwachtVerschil nationaliteitDatumEindeGeldigheid =
                new VerwachtVerschil(nationaliteitDatumEindeGeldigheidSleutel, true, false, VerschilType.ELEMENT_VERWIJDERD);
        final VerwachtSleutel nationaliteitActieAanpassingGeldigheidSleutel =
                new VerwachtSleutel(PersoonNationaliteitHistorie.class, "actieAanpassingGeldigheid");
        final VerwachtVerschil nationaliteitActieAanpasingEindeGeldigheid =
                new VerwachtVerschil(nationaliteitActieAanpassingGeldigheidSleutel, true, false, VerschilType.ELEMENT_VERWIJDERD);

        final VergelijkerResultaat resultaat = vergelijkPersonen("DELTAVERS01C50T20");

        final Set<Verschil> verschillen = new HashSet<>(resultaat.getVerschillen());
        controleerVerschillen(verschillen, nationaliteitDatumEindeGeldigheid, nationaliteitActieAanpasingEindeGeldigheid);
        assertTrue(MEER_VERSCHILLEN_DAN_VERWACHT_GEKREGEN, verschillen.isEmpty());

        final Map<BRPActie, Lo3Voorkomen> actieHerkomstMap = resultaat.getActieHerkomstMap();
        controleerHerkomstGelijk("01", 0, 0, actieHerkomstMap);
        controleerHerkomstGelijk("04", 0, 0, actieHerkomstMap);
        controleerHerkomstVerwijderd("04", 1, 0, actieHerkomstMap);
        controleerHerkomstAanpassing("54", 1, 1, actieHerkomstMap, "04", 1, 0);
        controleerHerkomstGelijk("06", 0, 0, actieHerkomstMap);
        controleerHerkomstGelijk("08", 0, 0, actieHerkomstMap);
        controleerHerkomstGelijk("10", 0, 0, actieHerkomstMap);
        controleerHerkomstGelijk("12", 0, 0, actieHerkomstMap);
        assertEquals(AANTAL_AANPASSINGEN_HERKOMST_NIET_JUIST, 8, actieHerkomstMap.size());
    }

    private void controleerHerkomstAanpassing(
        final String oudeCategorie,
        final int oudeStapel,
        final int oudVoorkomen,
        final Map<BRPActie, Lo3Voorkomen> actieHerkomstMap,
        final String nieuweCategorie,
        final int nieuweStapel,
        final int nieuwVoorkomen)
    {
        final boolean aanpassingGevonden =
                zoekHerkomstEntry(oudeCategorie, oudeStapel, oudVoorkomen, actieHerkomstMap, nieuweCategorie, nieuweStapel, nieuwVoorkomen);

        assertTrue(AANPASSING_HERKOMST_NIET_GEVONDEN, aanpassingGevonden);
    }

    private boolean zoekHerkomstEntry(
        final String oudeCategorie,
        final int oudeStapel,
        final int oudVoorkomen,
        final Map<BRPActie, Lo3Voorkomen> actieHerkomstMap,
        final String nieuweCategorie,
        final Integer nieuweStapel,
        final Integer nieuwVoorkomen)
    {
        boolean aanpassingGevonden = false;
        for (final Map.Entry<BRPActie, Lo3Voorkomen> actieHerkomstEntry : actieHerkomstMap.entrySet()) {
            final Lo3Voorkomen oudeHerkomst = actieHerkomstEntry.getKey().getLo3Voorkomen();
            final Lo3Voorkomen nieuweHerkomst = actieHerkomstEntry.getValue();

            final boolean matchOud = controleerHerkomst(oudeCategorie, oudeStapel, oudVoorkomen, oudeHerkomst);
            final boolean matchNieuw;
            if (nieuweHerkomst != null) {
                matchNieuw = controleerHerkomst(nieuweCategorie, nieuweStapel, nieuwVoorkomen, nieuweHerkomst);
            } else {
                matchNieuw = nieuweCategorie == null && nieuweStapel == null && nieuwVoorkomen == null;
            }

            if (matchOud && matchNieuw) {
                aanpassingGevonden = true;
                break;
            }
        }
        return aanpassingGevonden;
    }

    private boolean controleerHerkomst(final String categorie, final Integer stapel, final Integer voorkomen, final Lo3Voorkomen herkomst) {
        return Objects.equals(categorie, herkomst.getCategorie())
               && Objects.equals(stapel, herkomst.getStapelvolgnummer())
               && Objects.equals(voorkomen, herkomst.getVoorkomenvolgnummer());
    }

    private void controleerHerkomstGelijk(
        final String categorie,
        final int stapel,
        final int voorkomen,
        final Map<BRPActie, Lo3Voorkomen> actieHerkomstMap)
    {
        final boolean aanpassingGevonden = zoekHerkomstEntry(categorie, stapel, voorkomen, actieHerkomstMap, categorie, stapel, voorkomen);

        assertTrue(GELIJKE_HERKOMST_NIET_GEVONDEN, aanpassingGevonden);
    }

    private void controleerHerkomstVerwijderd(
        final String categorie,
        final int stapel,
        final int voorkomen,
        final Map<BRPActie, Lo3Voorkomen> actieHerkomstMap)
    {
        final boolean aanpassingGevonden = zoekHerkomstEntry(categorie, stapel, voorkomen, actieHerkomstMap, null, null, null);

        assertTrue(VERWIJDERDE_HERKOMST_NIET_GEVONDEN, aanpassingGevonden);
    }

    private void controleer2Cat04Verwijderd(final Set<Verschil> verschillen) {
        final VerwachtSleutel persoonNationaliteitSetSleutel = new VerwachtSleutel(PersoonNationaliteit.class, VELD_NATIONALITEIT_HISTORIE);
        final VerwachtVerschil persoonNationaliteit = new VerwachtVerschil(persoonNationaliteitSetSleutel, true, false, VerschilType.RIJ_VERWIJDERD);

        controleerVerschillen(verschillen, persoonNationaliteit, persoonNationaliteit);
    }

    private void controleerImmigratieVerwijderd(final Set<Verschil> verschillen) {
        final VerwachtSleutel persoonSoortMigratieSleutel = new VerwachtSleutel(Persoon.class, "soortMigratieId");
        final VerwachtSleutel persoonRedenWijzigingMigratieSleutel = new VerwachtSleutel(Persoon.class, "redenWijzigingMigratie");
        final VerwachtSleutel persoonLandOfGebiedMigratieSleutel = new VerwachtSleutel(Persoon.class, "landOfGebiedMigratie");
        final VerwachtSleutel persoonMigratieHistorieSleutel = new VerwachtSleutel(Persoon.class, "persoonMigratieHistorieSet");

        final VerwachtVerschil persoonSoortMigratie = new VerwachtVerschil(persoonSoortMigratieSleutel, true, false, VerschilType.ELEMENT_VERWIJDERD);
        final VerwachtVerschil persoonRedenWijzigingMigratie =
                new VerwachtVerschil(persoonRedenWijzigingMigratieSleutel, true, false, VerschilType.ELEMENT_VERWIJDERD);
        final VerwachtVerschil persoonLandOfGebiedMigratie =
                new VerwachtVerschil(persoonLandOfGebiedMigratieSleutel, true, false, VerschilType.ELEMENT_VERWIJDERD);
        final VerwachtVerschil persoonMigratieHistorie = new VerwachtVerschil(persoonMigratieHistorieSleutel, true, false, VerschilType.RIJ_VERWIJDERD);

        controleerVerschillen(verschillen, persoonSoortMigratie, persoonRedenWijzigingMigratie, persoonLandOfGebiedMigratie, persoonMigratieHistorie);

    }

    /**
     * Verwijderen 1 van 2 categorieën 04
     *
     * @throws IOException
     * @throws BerichtSyntaxException
     * @throws OngeldigePersoonslijstException
     * @throws ExcelAdapterException
     * @throws Lo3SyntaxException
     */
    @DBUnit.InsertBefore({SQL_DATA_BRP_STAMGEGEVENS_KERN_XML,
                          SQL_DATA_BRP_STAMGEGEVENS_AUTAUT_XML,
                          SQL_DATA_BRP_STAMGEGEVENS_CONV_XML,
                          SQL_DATA_BRP_STAMGEGEVENS_VERCONV_XML })
    @Test
    public void testDELTAVERS01C40T30()
        throws IOException, BerichtSyntaxException, OngeldigePersoonslijstException, ExcelAdapterException, Lo3SyntaxException
    {
        final VergelijkerResultaat resultaat = vergelijkPersonen("DELTAVERS01C40T30");

        final Set<Verschil> verschillen = new HashSet<>(resultaat.getVerschillen());

        final VerwachtSleutel persoonNationaliteitSetSleutel = new VerwachtSleutel(PersoonNationaliteit.class, VELD_NATIONALITEIT_HISTORIE);
        final VerwachtVerschil persoonNationaliteit = new VerwachtVerschil(persoonNationaliteitSetSleutel, true, false, VerschilType.RIJ_VERWIJDERD);

        controleerVerschillen(verschillen, persoonNationaliteit);
        assertTrue(MEER_VERSCHILLEN_DAN_VERWACHT_GEKREGEN, verschillen.isEmpty());

        final Map<BRPActie, Lo3Voorkomen> actieHerkomstMap = resultaat.getActieHerkomstMap();
        controleerHerkomstGelijk("01", 0, 0, actieHerkomstMap);
        controleerHerkomstAanpassing("04", 1, 0, actieHerkomstMap, "04", 0, 0);
        controleerHerkomstGelijk("06", 0, 0, actieHerkomstMap);
        controleerHerkomstGelijk("08", 0, 0, actieHerkomstMap);
        controleerHerkomstGelijk("10", 0, 0, actieHerkomstMap);
        controleerHerkomstGelijk("12", 0, 0, actieHerkomstMap);
        assertEquals(AANTAL_AANPASSINGEN_HERKOMST_NIET_JUIST, 6, actieHerkomstMap.size());
    }

    /**
     * Verwijderen Bijzonder Nederlanderschap
     *
     *
     * @throws IOException
     * @throws BerichtSyntaxException
     * @throws OngeldigePersoonslijstException
     * @throws ExcelAdapterException
     * @throws Lo3SyntaxException
     */
    @DBUnit.InsertBefore({SQL_DATA_BRP_STAMGEGEVENS_KERN_XML,
                          SQL_DATA_BRP_STAMGEGEVENS_AUTAUT_XML,
                          SQL_DATA_BRP_STAMGEGEVENS_CONV_XML,
                          SQL_DATA_BRP_STAMGEGEVENS_VERCONV_XML })
    @Test
    public void testDELTAVERS01C40T40()
        throws IOException, BerichtSyntaxException, OngeldigePersoonslijstException, ExcelAdapterException, Lo3SyntaxException
    {
        final VergelijkerResultaat resultaat = vergelijkPersonen("DELTAVERS01C40T40");

        final Set<Verschil> verschillen = new HashSet<>(resultaat.getVerschillen());
        final VerwachtSleutel behandelAlsNlIndicatieSleutel = new VerwachtSleutel(PersoonIndicatie.class, "persoonIndicatieHistorieSet");

        final VerwachtVerschil behandeldAlsNlIndicatie = new VerwachtVerschil(behandelAlsNlIndicatieSleutel, true, false, VerschilType.RIJ_VERWIJDERD);

        controleerVerschillen(verschillen, behandeldAlsNlIndicatie);

        assertTrue(MEER_VERSCHILLEN_DAN_VERWACHT_GEKREGEN, verschillen.isEmpty());

        final Map<BRPActie, Lo3Voorkomen> actieHerkomstMap = resultaat.getActieHerkomstMap();
        controleerHerkomstGelijk("01", 0, 0, actieHerkomstMap);
        controleerHerkomstGelijk("06", 0, 0, actieHerkomstMap);
        controleerHerkomstGelijk("08", 0, 0, actieHerkomstMap);
        controleerHerkomstGelijk("10", 0, 0, actieHerkomstMap);
        controleerHerkomstGelijk("12", 0, 0, actieHerkomstMap);
        assertEquals(AANTAL_AANPASSINGEN_HERKOMST_NIET_JUIST, 5, actieHerkomstMap.size());
    }

    /**
     * Correctie op adres. Veroorzaakte problemen (ORANJE-2871) doordat actie inhoud en actie verval niet dezelfde actie
     * waren na delta.
     *
     * @throws IOException
     * @throws BerichtSyntaxException
     * @throws OngeldigePersoonslijstException
     * @throws ExcelAdapterException
     * @throws Lo3SyntaxException
     */
    @DBUnit.InsertBefore({SQL_DATA_BRP_STAMGEGEVENS_KERN_XML,
                          SQL_DATA_BRP_STAMGEGEVENS_AUTAUT_XML,
                          SQL_DATA_BRP_STAMGEGEVENS_CONV_XML,
                          SQL_DATA_BRP_STAMGEGEVENS_VERCONV_XML })
    @Test
    public void testOranje2871RELA02C110T20()
        throws IOException, BerichtSyntaxException, OngeldigePersoonslijstException, ExcelAdapterException, Lo3SyntaxException
    {
        final VergelijkerResultaat resultaat = vergelijkPersonen("ORANJE-2871-RELA02C110T20");

        final Set<Verschil> verschillen = new HashSet<>(resultaat.getVerschillen());

        final VerwachtSleutel persoonAdresSleutel = new VerwachtSleutel(PersoonAdres.class, "huisnummer");
        final VerwachtVerschil persoonAdres = new VerwachtVerschil(persoonAdresSleutel, true, true, VerschilType.ELEMENT_AANGEPAST);

        final VerwachtSleutel persoonAdresHistorieDatumTijdVervalSleutel = new VerwachtSleutel(PersoonAdresHistorie.class, "datumTijdVerval");
        final VerwachtVerschil persoonAdresHistorieDatumTijdVerval =
                new VerwachtVerschil(persoonAdresHistorieDatumTijdVervalSleutel, false, true, VerschilType.ELEMENT_NIEUW);
        final VerwachtSleutel persoonAdresHistorieActieVervalSleutel = new VerwachtSleutel(PersoonAdresHistorie.class, "actieVerval");
        final VerwachtVerschil persoonAdresHistorieActieVerval =
                new VerwachtVerschil(persoonAdresHistorieActieVervalSleutel, false, true, VerschilType.ELEMENT_NIEUW);
        final VerwachtSleutel persoonAdresHistorieNadereAanduidingVervalSleutel =
                new VerwachtSleutel(PersoonAdresHistorie.class, "nadereAanduidingVerval");
        final VerwachtVerschil persoonAdresHistorieNadereAanduidingVerval =
                new VerwachtVerschil(persoonAdresHistorieNadereAanduidingVervalSleutel, false, true, VerschilType.ELEMENT_NIEUW);
        final VerwachtSleutel persoonAdresHistorieSetSleutel = new VerwachtSleutel(PersoonAdres.class, "persoonAdresHistorieSet");
        final VerwachtVerschil persoonAdresHistorieSet = new VerwachtVerschil(persoonAdresHistorieSetSleutel, false, true, VerschilType.RIJ_TOEGEVOEGD);

        final VerwachtSleutel persoonBijhoudingHistorieBijhoudingIdSleutel = new VerwachtSleutel(PersoonBijhoudingHistorie.class, "bijhoudingsaardId");
        final VerwachtVerschil persoonBijhoudingHistorieBijhoudingId =
                new VerwachtVerschil(persoonBijhoudingHistorieBijhoudingIdSleutel, true, true, VerschilType.ELEMENT_AANGEPAST);
        final VerwachtSleutel persoonBijhoudingHistorieNadereBijhoudingIdSleutel =
                new VerwachtSleutel(PersoonBijhoudingHistorie.class, "nadereBijhoudingsaardId");
        final VerwachtVerschil persoonBijhoudingHistorieNadereBijhoudingId =
                new VerwachtVerschil(persoonBijhoudingHistorieNadereBijhoudingIdSleutel, true, true, VerschilType.ELEMENT_AANGEPAST);
        final VerwachtSleutel persoonBijhoudingHistorieDatumTijdVervalSleutel = new VerwachtSleutel(PersoonBijhoudingHistorie.class, "datumTijdVerval");
        final VerwachtVerschil persoonBijhoudingHistorieDatumTijdVerval =
                new VerwachtVerschil(persoonBijhoudingHistorieDatumTijdVervalSleutel, false, true, VerschilType.ELEMENT_NIEUW);
        final VerwachtSleutel persoonBijhoudingHistorieActieVervalSleutel = new VerwachtSleutel(PersoonBijhoudingHistorie.class, "actieVerval");
        final VerwachtVerschil persoonBijhoudingHistorieActieVerval =
                new VerwachtVerschil(persoonBijhoudingHistorieActieVervalSleutel, false, true, VerschilType.ELEMENT_NIEUW);
        final VerwachtSleutel persoonBijhoudingHistorieNadereAanduidingVervalSleutel =
                new VerwachtSleutel(PersoonBijhoudingHistorie.class, "nadereAanduidingVerval");
        final VerwachtVerschil persoonBijhoudingHistorieNadereAanduidingVerval =
                new VerwachtVerschil(persoonBijhoudingHistorieNadereAanduidingVervalSleutel, false, true, VerschilType.ELEMENT_NIEUW);
        final VerwachtSleutel persoonBijhoudingHistorieSetSleutel = new VerwachtSleutel(Persoon.class, "persoonBijhoudingHistorieSet");
        final VerwachtVerschil persoonBijhoudingHistorieSet =
                new VerwachtVerschil(persoonBijhoudingHistorieSetSleutel, false, true, VerschilType.RIJ_TOEGEVOEGD);

        Verschil actieVervalToegevoegd = null;
        for (final Verschil verschil : verschillen) {
            if (persoonAdresHistorieActieVervalSleutel.komtOvereenMetSleutel((EntiteitSleutel) verschil.getSleutel())) {
                actieVervalToegevoegd = verschil;
            }
        }

        controleerVerschillen(
            verschillen,
            persoonAdres,
            persoonAdresHistorieDatumTijdVerval,
            persoonAdresHistorieActieVerval,
            persoonAdresHistorieNadereAanduidingVerval,
            persoonAdresHistorieSet,
            persoonBijhoudingHistorieBijhoudingId,
            persoonBijhoudingHistorieNadereBijhoudingId,
            persoonBijhoudingHistorieDatumTijdVerval,
            persoonBijhoudingHistorieActieVerval,
            persoonBijhoudingHistorieNadereAanduidingVerval,
            persoonBijhoudingHistorieSet);

        assertTrue(MEER_VERSCHILLEN_DAN_VERWACHT_GEKREGEN, verschillen.isEmpty());

        final Map<BRPActie, Lo3Voorkomen> actieHerkomstMap = resultaat.getActieHerkomstMap();
        controleerHerkomstGelijk("01", 0, 0, actieHerkomstMap);
        controleerHerkomstGelijk("04", 0, 0, actieHerkomstMap);
        controleerHerkomstAanpassing("08", 0, 0, actieHerkomstMap, "58", 0, 1);
        assertEquals(AANTAL_AANPASSINGEN_HERKOMST_NIET_JUIST, 3, actieHerkomstMap.size());

        // controleer of de actie verval die is toegevoegd, de hergebruikte actie-inhoud is van de oude PL
        // Doe dit door de herkomst op de actie te controleren, die moet 08-00-00 zijn.
        Assert.assertNotNull("actie verval moet toegevoegd zijn", actieVervalToegevoegd);
        final Lo3Voorkomen actieVervalHerkomst = ((BRPActie) actieVervalToegevoegd.getNieuweWaarde()).getLo3Voorkomen();
        assertEquals("Actie verval herkomst moet Cat 08 zijn", "08", actieVervalHerkomst.getCategorie());
        assertEquals("Actie verval herkomst moet Stapel 0 zijn", 0, actieVervalHerkomst.getStapelvolgnummer().intValue());
        assertEquals("Actie verval herkomst moet Voorkomen 0 zijn", 0, actieVervalHerkomst.getVoorkomenvolgnummer().intValue());
    }

    @Test
    public void testVerschilHistorieContext() {
        final Persoon bestaandePersoon = maakHistorieContextTestPersoon("Oud", "Voor", 20110101);
        final Persoon kluizenaar = maakHistorieContextTestPersoon("Nieuw", "Na", 20140430);

        final Set<Verschil> verschillen =
                new DeltaRootEntiteitVergelijker(DeltaRootEntiteitModus.PERSOON).vergelijk(null, bestaandePersoon, kluizenaar).getVerschillen();

        assertEquals(6, verschillen.size());

        final FormeleHistorie naamHistorieBestaand = bestaandePersoon.getPersoonSamengesteldeNaamHistorieSet().iterator().next();
        final FormeleHistorie naamHistorieKluizenaar = kluizenaar.getPersoonSamengesteldeNaamHistorieSet().iterator().next();

        int naamHistorieVerschillen = 0;

        for (final Verschil verschil : verschillen) {
            if (verschil.getBestaandeHistorieRij() == naamHistorieBestaand && verschil.getNieuweHistorieRij() == naamHistorieKluizenaar) {
                naamHistorieVerschillen += 1;
            }
        }

        assertEquals(4, naamHistorieVerschillen);
    }

    @Test
    public void testVerschilHistorieContextNieuweRij() {
        final Persoon bestaandePersoon = maakHistorieContextTestPersoon("Oud", "Voor", 20110101);
        final Persoon kluizenaar = maakHistorieContextTestPersoon("Oud", "Voor", 20110101);

        final PersoonSamengesteldeNaamHistorie nieuweNaamHistorie =
                maakHistorieContextNaamHistorie(
                    "Nieuw",
                    "Na",
                    20140430,
                    kluizenaar,
                    kluizenaar.getPersoonSamengesteldeNaamHistorieSet().iterator().next().getActieInhoud().getAdministratieveHandeling(),
                    100000);
        kluizenaar.addPersoonSamengesteldeNaamHistorie(nieuweNaamHistorie);

        final Set<Verschil> verschillen =
                new DeltaRootEntiteitVergelijker(DeltaRootEntiteitModus.PERSOON).vergelijk(null, bestaandePersoon, kluizenaar).getVerschillen();

        assertEquals(1, verschillen.size());

        final Verschil verschil = verschillen.iterator().next();

        assertTrue(verschil.getBestaandeHistorieRij() == null);
        assertSame(verschil.getNieuweHistorieRij(), nieuweNaamHistorie);
    }

    private Persoon maakHistorieContextTestPersoon(final String geslachtsnaamstam, final String voornamen, final int ontlening) {
        final Persoon testPersoon = new Persoon(SoortPersoon.INGESCHREVENE);
        testPersoon.setGeslachtsnaamstam(geslachtsnaamstam);
        testPersoon.setVoornamen(voornamen);

        final AdministratieveHandeling administratieveHandeling =
                new AdministratieveHandeling(new Partij("partij", 1), SoortAdministratieveHandeling.GBA_INITIELE_VULLING);
        administratieveHandeling.setDatumTijdRegistratie(MapperUtil.mapBrpDatumTijdToTimestamp(BrpDatumTijd.fromDatum(20150430, null)));

        final PersoonSamengesteldeNaamHistorie naamHistorie =
                maakHistorieContextNaamHistorie(geslachtsnaamstam, voornamen, ontlening, testPersoon, administratieveHandeling, 0);

        testPersoon.addPersoonSamengesteldeNaamHistorie(naamHistorie);

        return testPersoon;
    }

    private PersoonSamengesteldeNaamHistorie maakHistorieContextNaamHistorie(
        final String geslachtsnaamstam,
        final String voornamen,
        final int ontlening,
        final Persoon testPersoon,
        final AdministratieveHandeling administratieveHandeling,
        final int datumTijdRegistratieOffset)
    {
        final PersoonSamengesteldeNaamHistorie naamHistorie = new PersoonSamengesteldeNaamHistorie(testPersoon, geslachtsnaamstam, false, false);
        naamHistorie.setVoornamen(voornamen);

        final BRPActie naamActie =
                new BRPActie(
                    SoortActie.CONVERSIE_GBA,
                    administratieveHandeling,
                    administratieveHandeling.getPartij(),
                    new Timestamp(administratieveHandeling.getDatumTijdRegistratie().getTime() + datumTijdRegistratieOffset));
        naamActie.setDatumOntlening(ontlening);
        final ActieBron actieBron = new ActieBron(naamActie);
        naamActie.addActieBron(actieBron);

        final Document document = new Document(new SoortDocument("test", "test"));
        final DocumentHistorie docHistorie = new DocumentHistorie(document, administratieveHandeling.getPartij());
        docHistorie.setAktenummer(String.valueOf(ontlening));
        document.addDocumentHistorie(docHistorie);

        actieBron.setDocument(document);
        document.addActieBron(actieBron);

        naamHistorie.setActieInhoud(naamActie);
        naamHistorie.setDatumTijdRegistratie(naamActie.getDatumTijdRegistratie());
        return naamHistorie;
    }

    private class VerwachtVerschil {
        private final VerwachtSleutel verwachtSleutel;
        private final boolean verwachtOud;
        private final boolean verwachtNieuw;
        private final VerschilType verwachtType;

        private VerwachtVerschil(
            final VerwachtSleutel verwachtSleutel,
            final boolean verwachtOud,
            final boolean verwachtNieuw,
            final VerschilType verwachtType)
        {
            this.verwachtSleutel = verwachtSleutel;
            this.verwachtOud = verwachtOud;
            this.verwachtNieuw = verwachtNieuw;
            this.verwachtType = verwachtType;
        }

        /**
         * Geef de waarde van verwacht sleutel.
         *
         * @return verwacht sleutel
         */
        public VerwachtSleutel getVerwachtSleutel() {
            return verwachtSleutel;
        }

        /**
         * Geef de oud verwacht.
         *
         * @return oud verwacht
         */
        public boolean isOudVerwacht() {
            return verwachtOud;
        }

        /**
         * Geef de nieuw verwacht.
         *
         * @return nieuw verwacht
         */
        public boolean isNieuwVerwacht() {
            return verwachtNieuw;
        }

        /**
         * Geef de waarde van verwacht type.
         *
         * @return verwacht type
         */
        public VerschilType getVerwachtType() {
            return verwachtType;
        }
    }

    private class VerwachtSleutel {
        private final Class<?> entiteit;
        private final String veld;
        private final String onderzoekVeld;

        public VerwachtSleutel(final Class<?> entiteit, final String veld) {
            this(entiteit, veld, null);
        }

        public VerwachtSleutel(final Class<?> entiteit, final String veld, final String onderzoekVeld) {
            this.entiteit = entiteit;
            this.veld = veld;
            this.onderzoekVeld = onderzoekVeld;
        }

        /**
         * Geef de waarde van entiteit.
         *
         * @return entiteit
         */
        public Class<?> getEntiteit() {
            return entiteit;
        }

        /**
         * Geef de waarde van veld.
         *
         * @return veld
         */
        public String getVeld() {
            return veld;
        }

        /**
         * Geef de waarde van onderzoek veld.
         *
         * @return onderzoek veld
         */
        public String getOnderzoekVeld() {
            return onderzoekVeld;
        }

        @Override
        public boolean equals(final Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof VerwachtSleutel)) {
                return false;
            }
            final VerwachtSleutel castOther = (VerwachtSleutel) other;
            return new EqualsBuilder().append(entiteit, castOther.entiteit).append(veld, castOther.veld).isEquals();
        }

        @Override
        public int hashCode() {
            return new HashCodeBuilder().append(entiteit).append(veld).toHashCode();
        }

        @Override
        public String toString() {
            final ToStringBuilder sb = new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("Entiteit", entiteit).append("Veld", veld);
            return sb.toString();
        }

        private boolean komtOvereenMetSleutel(final EntiteitSleutel sleutel) {
            return sleutel.getEntiteit().equals(getEntiteit()) && sleutel.getVeld().equals(getVeld());
        }
    }
}
