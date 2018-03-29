Meta:
@status                 Klaar
@usecase                UCS-BY.0.VA

Narrative: R1377 Buitenlands adres regel migratie mag uit niet meer dan 35 karakters bestaan

Scenario:   Persoon.Buitenlands adres regel 2 migratie heeft 36 karakters
            LT: VZBL02C90T40

Given alle personen zijn verwijderd
Given enkel initiele vulling uit bestand /LO3PL-VA/LO3PL-VZBL/VZBL02C90T40.xls

When voer een bijhouding uit VZBL02C90T40.xml namens partij 'Gemeente BRP 1'

Then heeft het antwoordbericht verwerking Geslaagd

Then is het antwoordbericht gelijk aan /testcases/bijhouding_VA/VZBL/expected/VZBL02C90T40.xml voor expressie //brp:bhg_vbaRegistreerVerhuizing_R

!-- Controleer Persoon.Migratie
Then in kern heeft select sm.naam as soortMigratie,
                   pm.dataanvgel as dataanvgel,
                   rwv.naam as redenWijzigenMigratie,
                   aa.naam as Aangever,
                   lg.naam as Land,
                   bladresregel1migratie,
                   bladresregel2migratie,
                   bladresregel3migratie
                   from kern.his_persmigratie pm
                   left join kern.rdnwijzverblijf rwv on pm.rdnwijzmigratie=rwv.id
                   left join kern.aang aa on pm.aangmigratie=aa.id
                   left join kern.srtmigratie sm on pm.srtmigratie=sm.id
                   left join kern.landgebied lg on pm.landgebiedmigratie=lg.id
                   where landgebiedmigratie in (select id from kern.landgebied where naam='Aruba') de volgende gegevens:
| veld                      | waarde                               |
| bladresregel1migratie     | adres kort 1                         |
| bladresregel2migratie     | adw we w wewe w we w w ewewew wasdaa |
| bladresregel3migratie     | adres kort 3                         |