-- upgrade van BMR 8.1 naar BMR 9.0
-- De grootste wijzigingen zijn:
-- 	2 enums zijn bijgekomen (soortBerichte en SrtAdmHand
--  Vele nieuwe tabellen zijn bijgekomen ter ondersteuning van immigratie (schema IST)
--  Vele indexen zijn verdwenen (historische tabbel.actieInhoud, tabel.actieVerval, tabel.actieAanpGel).




-- dubbele constraint
ALTER TABLE Kern.SrtAdmHnd DROP CONSTRAINT IF EXISTS R9261;

-- indexen op historisch record naar actieID
DROP INDEX IF EXISTS "autaut"."his_authenticatiemiddel_actieinh";        -- CREATE INDEX his_authenticatiemiddel_actieinh ON autaut.his_authenticatiemiddel USING btree (actieinh)
DROP INDEX IF EXISTS "autaut"."his_authenticatiemiddel_actieinh_idx";        -- CREATE INDEX his_authenticatiemiddel_actieinh_idx ON autaut.his_authenticatiemiddel USING btree (actieinh)
DROP INDEX IF EXISTS "autaut"."his_authenticatiemiddel_actieverval";        -- CREATE INDEX his_authenticatiemiddel_actieverval ON autaut.his_authenticatiemiddel USING btree (actieverval)
DROP INDEX IF EXISTS "autaut"."his_authenticatiemiddel_actieverval_idx";        -- CREATE INDEX his_authenticatiemiddel_actieverval_idx ON autaut.his_authenticatiemiddel USING btree (actieverval)
DROP INDEX IF EXISTS "autaut"."his_autorisatiebesluit_actieinh";        -- CREATE INDEX his_autorisatiebesluit_actieinh ON autaut.his_autorisatiebesluit USING btree (actieinh)
DROP INDEX IF EXISTS "autaut"."his_autorisatiebesluit_actieinh_idx";        -- CREATE INDEX his_autorisatiebesluit_actieinh_idx ON autaut.his_autorisatiebesluit USING btree (actieinh)
DROP INDEX IF EXISTS "autaut"."his_autorisatiebesluit_actieverval";        -- CREATE INDEX his_autorisatiebesluit_actieverval ON autaut.his_autorisatiebesluit USING btree (actieverval)
DROP INDEX IF EXISTS "autaut"."his_autorisatiebesluit_actieverval_idx";        -- CREATE INDEX his_autorisatiebesluit_actieverval_idx ON autaut.his_autorisatiebesluit USING btree (actieverval)
DROP INDEX IF EXISTS "autaut"."his_autorisatiebesluitbijhau_actieinh";        -- CREATE INDEX his_autorisatiebesluitbijhau_actieinh ON autaut.his_autorisatiebesluitbijhau USING btree (actieinh)
DROP INDEX IF EXISTS "autaut"."his_autorisatiebesluitbijhau_actieinh_idx";        -- CREATE INDEX his_autorisatiebesluitbijhau_actieinh_idx ON autaut.his_autorisatiebesluitbijhau USING btree (actieinh)
DROP INDEX IF EXISTS "autaut"."his_autorisatiebesluitbijhau_actieverval";        -- CREATE INDEX his_autorisatiebesluitbijhau_actieverval ON autaut.his_autorisatiebesluitbijhau USING btree (actieverval)
DROP INDEX IF EXISTS "autaut"."his_autorisatiebesluitbijhau_actieverval_idx";        -- CREATE INDEX his_autorisatiebesluitbijhau_actieverval_idx ON autaut.his_autorisatiebesluitbijhau USING btree (actieverval)
DROP INDEX IF EXISTS "autaut"."his_bijhautorisatie_actieinh";        -- CREATE INDEX his_bijhautorisatie_actieinh ON autaut.his_bijhautorisatie USING btree (actieinh)
DROP INDEX IF EXISTS "autaut"."his_bijhautorisatie_actieinh_idx";        -- CREATE INDEX his_bijhautorisatie_actieinh_idx ON autaut.his_bijhautorisatie USING btree (actieinh)
DROP INDEX IF EXISTS "autaut"."his_bijhautorisatie_actieverval";        -- CREATE INDEX his_bijhautorisatie_actieverval ON autaut.his_bijhautorisatie USING btree (actieverval)
DROP INDEX IF EXISTS "autaut"."his_bijhautorisatie_actieverval_idx";        -- CREATE INDEX his_bijhautorisatie_actieverval_idx ON autaut.his_bijhautorisatie USING btree (actieverval)
DROP INDEX IF EXISTS "autaut"."his_bijhsituatie_actieinh";        -- CREATE INDEX his_bijhsituatie_actieinh ON autaut.his_bijhsituatie USING btree (actieinh)
DROP INDEX IF EXISTS "autaut"."his_bijhsituatie_actieinh_idx";        -- CREATE INDEX his_bijhsituatie_actieinh_idx ON autaut.his_bijhsituatie USING btree (actieinh)
DROP INDEX IF EXISTS "autaut"."his_bijhsituatie_actieverval";        -- CREATE INDEX his_bijhsituatie_actieverval ON autaut.his_bijhsituatie USING btree (actieverval)
DROP INDEX IF EXISTS "autaut"."his_bijhsituatie_actieverval_idx";        -- CREATE INDEX his_bijhsituatie_actieverval_idx ON autaut.his_bijhsituatie USING btree (actieverval)
DROP INDEX IF EXISTS "autaut"."his_doelbinding_actieaanpgel";        -- CREATE INDEX his_doelbinding_actieaanpgel ON autaut.his_doelbinding USING btree (actieaanpgel)
DROP INDEX IF EXISTS "autaut"."his_doelbinding_actieaanpgel_idx";        -- CREATE INDEX his_doelbinding_actieaanpgel_idx ON autaut.his_doelbinding USING btree (actieaanpgel)
DROP INDEX IF EXISTS "autaut"."his_doelbinding_actieinh";        -- CREATE INDEX his_doelbinding_actieinh ON autaut.his_doelbinding USING btree (actieinh)
DROP INDEX IF EXISTS "autaut"."his_doelbinding_actieinh_idx";        -- CREATE INDEX his_doelbinding_actieinh_idx ON autaut.his_doelbinding USING btree (actieinh)
DROP INDEX IF EXISTS "autaut"."his_doelbinding_actieverval";        -- CREATE INDEX his_doelbinding_actieverval ON autaut.his_doelbinding USING btree (actieverval)
DROP INDEX IF EXISTS "autaut"."his_doelbinding_actieverval_idx";        -- CREATE INDEX his_doelbinding_actieverval_idx ON autaut.his_doelbinding USING btree (actieverval)
DROP INDEX IF EXISTS "autaut"."his_uitgeslotene_actieinh";        -- CREATE INDEX his_uitgeslotene_actieinh ON autaut.his_uitgeslotene USING btree (actieinh)
DROP INDEX IF EXISTS "autaut"."his_uitgeslotene_actieinh_idx";        -- CREATE INDEX his_uitgeslotene_actieinh_idx ON autaut.his_uitgeslotene USING btree (actieinh)
DROP INDEX IF EXISTS "autaut"."his_uitgeslotene_actieverval";        -- CREATE INDEX his_uitgeslotene_actieverval ON autaut.his_uitgeslotene USING btree (actieverval)
DROP INDEX IF EXISTS "autaut"."his_uitgeslotene_actieverval_idx";        -- CREATE INDEX his_uitgeslotene_actieverval_idx ON autaut.his_uitgeslotene USING btree (actieverval)
DROP INDEX IF EXISTS "brm"."his_regelsituatie_actieinh";        -- CREATE INDEX his_regelsituatie_actieinh ON brm.his_regelsituatie USING btree (actieinh)
DROP INDEX IF EXISTS "brm"."his_regelsituatie_actieinh_idx";        -- CREATE INDEX his_regelsituatie_actieinh_idx ON brm.his_regelsituatie USING btree (actieinh)
DROP INDEX IF EXISTS "brm"."his_regelsituatie_actieverval";        -- CREATE INDEX his_regelsituatie_actieverval ON brm.his_regelsituatie USING btree (actieverval)
DROP INDEX IF EXISTS "brm"."his_regelsituatie_actieverval_idx";        -- CREATE INDEX his_regelsituatie_actieverval_idx ON brm.his_regelsituatie USING btree (actieverval)
DROP INDEX IF EXISTS "kern"."his_doc_actieinh";        -- CREATE INDEX his_doc_actieinh ON kern.his_doc USING btree (actieinh)
DROP INDEX IF EXISTS "kern"."his_doc_actieinh_idx";        -- CREATE INDEX his_doc_actieinh_idx ON kern.his_doc USING btree (actieinh)
DROP INDEX IF EXISTS "kern"."his_doc_actieverval";        -- CREATE INDEX his_doc_actieverval ON kern.his_doc USING btree (actieverval)
DROP INDEX IF EXISTS "kern"."his_doc_actieverval_idx";        -- CREATE INDEX his_doc_actieverval_idx ON kern.his_doc USING btree (actieverval)
DROP INDEX IF EXISTS "kern"."his_erkenningongeborenvrucht_actieinh";        -- CREATE INDEX his_erkenningongeborenvrucht_actieinh ON kern.his_erkenningongeborenvrucht USING btree (actieinh)
DROP INDEX IF EXISTS "kern"."his_erkenningongeborenvrucht_actieinh_idx";        -- CREATE INDEX his_erkenningongeborenvrucht_actieinh_idx ON kern.his_erkenningongeborenvrucht USING btree (actieinh)
DROP INDEX IF EXISTS "kern"."his_erkenningongeborenvrucht_actieverval";        -- CREATE INDEX his_erkenningongeborenvrucht_actieverval ON kern.his_erkenningongeborenvrucht USING btree (actieverval)
DROP INDEX IF EXISTS "kern"."his_erkenningongeborenvrucht_actieverval_idx";        -- CREATE INDEX his_erkenningongeborenvrucht_actieverval_idx ON kern.his_erkenningongeborenvrucht USING btree (actieverval)
DROP INDEX IF EXISTS "kern"."his_huwelijkgeregistreerdpar_actieinh";        -- CREATE INDEX his_huwelijkgeregistreerdpar_actieinh ON kern.his_huwelijkgeregistreerdpar USING btree (actieinh)
DROP INDEX IF EXISTS "kern"."his_huwelijkgeregistreerdpar_actieinh_idx";        -- CREATE INDEX his_huwelijkgeregistreerdpar_actieinh_idx ON kern.his_huwelijkgeregistreerdpar USING btree (actieinh)
DROP INDEX IF EXISTS "kern"."his_huwelijkgeregistreerdpar_actieverval";        -- CREATE INDEX his_huwelijkgeregistreerdpar_actieverval ON kern.his_huwelijkgeregistreerdpar USING btree (actieverval)
DROP INDEX IF EXISTS "kern"."his_huwelijkgeregistreerdpar_actieverval_idx";        -- CREATE INDEX his_huwelijkgeregistreerdpar_actieverval_idx ON kern.his_huwelijkgeregistreerdpar USING btree (actieverval)
DROP INDEX IF EXISTS "kern"."his_multirealiteitregel_actieinh";        -- CREATE INDEX his_multirealiteitregel_actieinh ON kern.his_multirealiteitregel USING btree (actieinh)
DROP INDEX IF EXISTS "kern"."his_multirealiteitregel_actieinh_idx";        -- CREATE INDEX his_multirealiteitregel_actieinh_idx ON kern.his_multirealiteitregel USING btree (actieinh)
DROP INDEX IF EXISTS "kern"."his_multirealiteitregel_actieverval";        -- CREATE INDEX his_multirealiteitregel_actieverval ON kern.his_multirealiteitregel USING btree (actieverval)
DROP INDEX IF EXISTS "kern"."his_multirealiteitregel_actieverval_idx";        -- CREATE INDEX his_multirealiteitregel_actieverval_idx ON kern.his_multirealiteitregel USING btree (actieverval)
DROP INDEX IF EXISTS "kern"."his_naamskeuzeongeborenvruch_actieinh";        -- CREATE INDEX his_naamskeuzeongeborenvruch_actieinh ON kern.his_naamskeuzeongeborenvruch USING btree (actieinh)
DROP INDEX IF EXISTS "kern"."his_naamskeuzeongeborenvruch_actieinh_idx";        -- CREATE INDEX his_naamskeuzeongeborenvruch_actieinh_idx ON kern.his_naamskeuzeongeborenvruch USING btree (actieinh)
DROP INDEX IF EXISTS "kern"."his_naamskeuzeongeborenvruch_actieverval";        -- CREATE INDEX his_naamskeuzeongeborenvruch_actieverval ON kern.his_naamskeuzeongeborenvruch USING btree (actieverval)
DROP INDEX IF EXISTS "kern"."his_naamskeuzeongeborenvruch_actieverval_idx";        -- CREATE INDEX his_naamskeuzeongeborenvruch_actieverval_idx ON kern.his_naamskeuzeongeborenvruch USING btree (actieverval)
DROP INDEX IF EXISTS "kern"."his_onderzoek_actieinh";        -- CREATE INDEX his_onderzoek_actieinh ON kern.his_onderzoek USING btree (actieinh)
DROP INDEX IF EXISTS "kern"."his_onderzoek_actieinh_idx";        -- CREATE INDEX his_onderzoek_actieinh_idx ON kern.his_onderzoek USING btree (actieinh)
DROP INDEX IF EXISTS "kern"."his_onderzoek_actieverval";        -- CREATE INDEX his_onderzoek_actieverval ON kern.his_onderzoek USING btree (actieverval)
DROP INDEX IF EXISTS "kern"."his_onderzoek_actieverval_idx";        -- CREATE INDEX his_onderzoek_actieverval_idx ON kern.his_onderzoek USING btree (actieverval)
DROP INDEX IF EXISTS "kern"."his_ouderouderlijkgezag_actieaanpgel";        -- CREATE INDEX his_ouderouderlijkgezag_actieaanpgel ON kern.his_ouderouderlijkgezag USING btree (actieaanpgel)
DROP INDEX IF EXISTS "kern"."his_ouderouderlijkgezag_actieaanpgel_idx";        -- CREATE INDEX his_ouderouderlijkgezag_actieaanpgel_idx ON kern.his_ouderouderlijkgezag USING btree (actieaanpgel)
DROP INDEX IF EXISTS "kern"."his_ouderouderlijkgezag_actieinh";        -- CREATE INDEX his_ouderouderlijkgezag_actieinh ON kern.his_ouderouderlijkgezag USING btree (actieinh)
DROP INDEX IF EXISTS "kern"."his_ouderouderlijkgezag_actieinh_idx";        -- CREATE INDEX his_ouderouderlijkgezag_actieinh_idx ON kern.his_ouderouderlijkgezag USING btree (actieinh)
DROP INDEX IF EXISTS "kern"."his_ouderouderlijkgezag_actieverval";        -- CREATE INDEX his_ouderouderlijkgezag_actieverval ON kern.his_ouderouderlijkgezag USING btree (actieverval)
DROP INDEX IF EXISTS "kern"."his_ouderouderlijkgezag_actieverval_idx";        -- CREATE INDEX his_ouderouderlijkgezag_actieverval_idx ON kern.his_ouderouderlijkgezag USING btree (actieverval)
DROP INDEX IF EXISTS "kern"."his_ouderouderschap_actieaanpgel_idx";        -- CREATE INDEX his_ouderouderschap_actieaanpgel_idx ON kern.his_ouderouderschap USING btree (actieaanpgel)
DROP INDEX IF EXISTS "kern"."his_ouderouderschap_actieinh_idx";        -- CREATE INDEX his_ouderouderschap_actieinh_idx ON kern.his_ouderouderschap USING btree (actieinh)
DROP INDEX IF EXISTS "kern"."his_ouderouderschap_actieverval_idx";        -- CREATE INDEX his_ouderouderschap_actieverval_idx ON kern.his_ouderouderschap USING btree (actieverval)
DROP INDEX IF EXISTS "kern"."his_partij_actieinh";        -- CREATE INDEX his_partij_actieinh ON kern.his_partij USING btree (actieinh)
DROP INDEX IF EXISTS "kern"."his_partij_actieinh_idx";        -- CREATE INDEX his_partij_actieinh_idx ON kern.his_partij USING btree (actieinh)
DROP INDEX IF EXISTS "kern"."his_partij_actieverval";        -- CREATE INDEX his_partij_actieverval ON kern.his_partij USING btree (actieverval)
DROP INDEX IF EXISTS "kern"."his_partij_actieverval_idx";        -- CREATE INDEX his_partij_actieverval_idx ON kern.his_partij USING btree (actieverval)
DROP INDEX IF EXISTS "kern"."his_persaanschr_actieinh";        -- CREATE INDEX his_persaanschr_actieinh ON kern.his_persaanschr USING btree (actieinh)
DROP INDEX IF EXISTS "kern"."his_persaanschr_actieinh_idx";        -- CREATE INDEX his_persaanschr_actieinh_idx ON kern.his_persaanschr USING btree (actieinh)
DROP INDEX IF EXISTS "kern"."his_persaanschr_actieverval";        -- CREATE INDEX his_persaanschr_actieverval ON kern.his_persaanschr USING btree (actieverval)
DROP INDEX IF EXISTS "kern"."his_persaanschr_actieverval_idx";        -- CREATE INDEX his_persaanschr_actieverval_idx ON kern.his_persaanschr USING btree (actieverval)
DROP INDEX IF EXISTS "kern"."his_persadres_actieaanpgel";        -- CREATE INDEX his_persadres_actieaanpgel ON kern.his_persadres USING btree (actieaanpgel)
DROP INDEX IF EXISTS "kern"."his_persadres_actieaanpgel_idx";        -- CREATE INDEX his_persadres_actieaanpgel_idx ON kern.his_persadres USING btree (actieaanpgel)
DROP INDEX IF EXISTS "kern"."his_persadres_actieinh";        -- CREATE INDEX his_persadres_actieinh ON kern.his_persadres USING btree (actieinh)
DROP INDEX IF EXISTS "kern"."his_persadres_actieinh_idx";        -- CREATE INDEX his_persadres_actieinh_idx ON kern.his_persadres USING btree (actieinh)
DROP INDEX IF EXISTS "kern"."his_persadres_actieverval";        -- CREATE INDEX his_persadres_actieverval ON kern.his_persadres USING btree (actieverval)
DROP INDEX IF EXISTS "kern"."his_persadres_actieverval_idx";        -- CREATE INDEX his_persadres_actieverval_idx ON kern.his_persadres USING btree (actieverval)
DROP INDEX IF EXISTS "kern"."his_persbijhaard_actieaanpgel_idx";        -- CREATE INDEX his_persbijhaard_actieaanpgel_idx ON kern.his_persbijhaard USING btree (actieaanpgel)
DROP INDEX IF EXISTS "kern"."his_persbijhaard_actieinh_idx";        -- CREATE INDEX his_persbijhaard_actieinh_idx ON kern.his_persbijhaard USING btree (actieinh)
DROP INDEX IF EXISTS "kern"."his_persbijhaard_actieverval_idx";        -- CREATE INDEX his_persbijhaard_actieverval_idx ON kern.his_persbijhaard USING btree (actieverval)
DROP INDEX IF EXISTS "kern"."his_persbijhgem_actieaanpgel";        -- CREATE INDEX his_persbijhgem_actieaanpgel ON kern.his_persbijhgem USING btree (actieaanpgel)
DROP INDEX IF EXISTS "kern"."his_persbijhgem_actieaanpgel_idx";        -- CREATE INDEX his_persbijhgem_actieaanpgel_idx ON kern.his_persbijhgem USING btree (actieaanpgel)
DROP INDEX IF EXISTS "kern"."his_persbijhgem_actieinh";        -- CREATE INDEX his_persbijhgem_actieinh ON kern.his_persbijhgem USING btree (actieinh)
DROP INDEX IF EXISTS "kern"."his_persbijhgem_actieinh_idx";        -- CREATE INDEX his_persbijhgem_actieinh_idx ON kern.his_persbijhgem USING btree (actieinh)
DROP INDEX IF EXISTS "kern"."his_persbijhgem_actieverval";        -- CREATE INDEX his_persbijhgem_actieverval ON kern.his_persbijhgem USING btree (actieverval)
DROP INDEX IF EXISTS "kern"."his_persbijhgem_actieverval_idx";        -- CREATE INDEX his_persbijhgem_actieverval_idx ON kern.his_persbijhgem USING btree (actieverval)
DROP INDEX IF EXISTS "kern"."his_persbvp_actieinh";        -- CREATE INDEX his_persbvp_actieinh ON kern.his_persbvp USING btree (actieinh)
DROP INDEX IF EXISTS "kern"."his_persbvp_actieinh_idx";        -- CREATE INDEX his_persbvp_actieinh_idx ON kern.his_persbvp USING btree (actieinh)
DROP INDEX IF EXISTS "kern"."his_persbvp_actieverval";        -- CREATE INDEX his_persbvp_actieverval ON kern.his_persbvp USING btree (actieverval)
DROP INDEX IF EXISTS "kern"."his_persbvp_actieverval_idx";        -- CREATE INDEX his_persbvp_actieverval_idx ON kern.his_persbvp USING btree (actieverval)
DROP INDEX IF EXISTS "kern"."his_perseuverkiezingen_actieinh";        -- CREATE INDEX his_perseuverkiezingen_actieinh ON kern.his_perseuverkiezingen USING btree (actieinh)
DROP INDEX IF EXISTS "kern"."his_perseuverkiezingen_actieinh_idx";        -- CREATE INDEX his_perseuverkiezingen_actieinh_idx ON kern.his_perseuverkiezingen USING btree (actieinh)
DROP INDEX IF EXISTS "kern"."his_perseuverkiezingen_actieverval";        -- CREATE INDEX his_perseuverkiezingen_actieverval ON kern.his_perseuverkiezingen USING btree (actieverval)
DROP INDEX IF EXISTS "kern"."his_perseuverkiezingen_actieverval_idx";        -- CREATE INDEX his_perseuverkiezingen_actieverval_idx ON kern.his_perseuverkiezingen USING btree (actieverval)
DROP INDEX IF EXISTS "kern"."his_persgeboorte_actieinh";        -- CREATE INDEX his_persgeboorte_actieinh ON kern.his_persgeboorte USING btree (actieinh)
DROP INDEX IF EXISTS "kern"."his_persgeboorte_actieinh_idx";        -- CREATE INDEX his_persgeboorte_actieinh_idx ON kern.his_persgeboorte USING btree (actieinh)
DROP INDEX IF EXISTS "kern"."his_persgeboorte_actieverval";        -- CREATE INDEX his_persgeboorte_actieverval ON kern.his_persgeboorte USING btree (actieverval)
DROP INDEX IF EXISTS "kern"."his_persgeboorte_actieverval_idx";        -- CREATE INDEX his_persgeboorte_actieverval_idx ON kern.his_persgeboorte USING btree (actieverval)
DROP INDEX IF EXISTS "kern"."his_persgeslachtsaand_actieaanpgel";        -- CREATE INDEX his_persgeslachtsaand_actieaanpgel ON kern.his_persgeslachtsaand USING btree (actieaanpgel)
DROP INDEX IF EXISTS "kern"."his_persgeslachtsaand_actieaanpgel_idx";        -- CREATE INDEX his_persgeslachtsaand_actieaanpgel_idx ON kern.his_persgeslachtsaand USING btree (actieaanpgel)
DROP INDEX IF EXISTS "kern"."his_persgeslachtsaand_actieinh";        -- CREATE INDEX his_persgeslachtsaand_actieinh ON kern.his_persgeslachtsaand USING btree (actieinh)
DROP INDEX IF EXISTS "kern"."his_persgeslachtsaand_actieinh_idx";        -- CREATE INDEX his_persgeslachtsaand_actieinh_idx ON kern.his_persgeslachtsaand USING btree (actieinh)
DROP INDEX IF EXISTS "kern"."his_persgeslachtsaand_actieverval";        -- CREATE INDEX his_persgeslachtsaand_actieverval ON kern.his_persgeslachtsaand USING btree (actieverval)
DROP INDEX IF EXISTS "kern"."his_persgeslachtsaand_actieverval_idx";        -- CREATE INDEX his_persgeslachtsaand_actieverval_idx ON kern.his_persgeslachtsaand USING btree (actieverval)
DROP INDEX IF EXISTS "kern"."his_persgeslnaamcomp_actieaanpgel";        -- CREATE INDEX his_persgeslnaamcomp_actieaanpgel ON kern.his_persgeslnaamcomp USING btree (actieaanpgel)
DROP INDEX IF EXISTS "kern"."his_persgeslnaamcomp_actieaanpgel_idx";        -- CREATE INDEX his_persgeslnaamcomp_actieaanpgel_idx ON kern.his_persgeslnaamcomp USING btree (actieaanpgel)
DROP INDEX IF EXISTS "kern"."his_persgeslnaamcomp_actieinh";        -- CREATE INDEX his_persgeslnaamcomp_actieinh ON kern.his_persgeslnaamcomp USING btree (actieinh)
DROP INDEX IF EXISTS "kern"."his_persgeslnaamcomp_actieinh_idx";        -- CREATE INDEX his_persgeslnaamcomp_actieinh_idx ON kern.his_persgeslnaamcomp USING btree (actieinh)
DROP INDEX IF EXISTS "kern"."his_persgeslnaamcomp_actieverval";        -- CREATE INDEX his_persgeslnaamcomp_actieverval ON kern.his_persgeslnaamcomp USING btree (actieverval)
DROP INDEX IF EXISTS "kern"."his_persgeslnaamcomp_actieverval_idx";        -- CREATE INDEX his_persgeslnaamcomp_actieverval_idx ON kern.his_persgeslnaamcomp USING btree (actieverval)
DROP INDEX IF EXISTS "kern"."his_persids_actieaanpgel";        -- CREATE INDEX his_persids_actieaanpgel ON kern.his_persids USING btree (actieaanpgel)
DROP INDEX IF EXISTS "kern"."his_persids_actieaanpgel_idx";        -- CREATE INDEX his_persids_actieaanpgel_idx ON kern.his_persids USING btree (actieaanpgel)
DROP INDEX IF EXISTS "kern"."his_persids_actieinh";        -- CREATE INDEX his_persids_actieinh ON kern.his_persids USING btree (actieinh)
DROP INDEX IF EXISTS "kern"."his_persids_actieinh_idx";        -- CREATE INDEX his_persids_actieinh_idx ON kern.his_persids USING btree (actieinh)
DROP INDEX IF EXISTS "kern"."his_persids_actieverval";        -- CREATE INDEX his_persids_actieverval ON kern.his_persids USING btree (actieverval)
DROP INDEX IF EXISTS "kern"."his_persids_actieverval_idx";        -- CREATE INDEX his_persids_actieverval_idx ON kern.his_persids USING btree (actieverval)
DROP INDEX IF EXISTS "kern"."his_persimmigratie_actieaanpgel";        -- CREATE INDEX his_persimmigratie_actieaanpgel ON kern.his_persimmigratie USING btree (actieaanpgel)
DROP INDEX IF EXISTS "kern"."his_persimmigratie_actieaanpgel_idx";        -- CREATE INDEX his_persimmigratie_actieaanpgel_idx ON kern.his_persimmigratie USING btree (actieaanpgel)
DROP INDEX IF EXISTS "kern"."his_persimmigratie_actieinh";        -- CREATE INDEX his_persimmigratie_actieinh ON kern.his_persimmigratie USING btree (actieinh)
DROP INDEX IF EXISTS "kern"."his_persimmigratie_actieinh_idx";        -- CREATE INDEX his_persimmigratie_actieinh_idx ON kern.his_persimmigratie USING btree (actieinh)
DROP INDEX IF EXISTS "kern"."his_persimmigratie_actieverval";        -- CREATE INDEX his_persimmigratie_actieverval ON kern.his_persimmigratie USING btree (actieverval)
DROP INDEX IF EXISTS "kern"."his_persimmigratie_actieverval_idx";        -- CREATE INDEX his_persimmigratie_actieverval_idx ON kern.his_persimmigratie USING btree (actieverval)
DROP INDEX IF EXISTS "kern"."his_persindicatie_actieaanpgel";        -- CREATE INDEX his_persindicatie_actieaanpgel ON kern.his_persindicatie USING btree (actieaanpgel)
DROP INDEX IF EXISTS "kern"."his_persindicatie_actieaanpgel_idx";        -- CREATE INDEX his_persindicatie_actieaanpgel_idx ON kern.his_persindicatie USING btree (actieaanpgel)
DROP INDEX IF EXISTS "kern"."his_persindicatie_actieinh";        -- CREATE INDEX his_persindicatie_actieinh ON kern.his_persindicatie USING btree (actieinh)
DROP INDEX IF EXISTS "kern"."his_persindicatie_actieinh_idx";        -- CREATE INDEX his_persindicatie_actieinh_idx ON kern.his_persindicatie USING btree (actieinh)
DROP INDEX IF EXISTS "kern"."his_persindicatie_actieverval";        -- CREATE INDEX his_persindicatie_actieverval ON kern.his_persindicatie USING btree (actieverval)
DROP INDEX IF EXISTS "kern"."his_persindicatie_actieverval_idx";        -- CREATE INDEX his_persindicatie_actieverval_idx ON kern.his_persindicatie USING btree (actieverval)
DROP INDEX IF EXISTS "kern"."his_persinschr_actieinh";        -- CREATE INDEX his_persinschr_actieinh ON kern.his_persinschr USING btree (actieinh)
DROP INDEX IF EXISTS "kern"."his_persinschr_actieinh_idx";        -- CREATE INDEX his_persinschr_actieinh_idx ON kern.his_persinschr USING btree (actieinh)
DROP INDEX IF EXISTS "kern"."his_persinschr_actieverval";        -- CREATE INDEX his_persinschr_actieverval ON kern.his_persinschr USING btree (actieverval)
DROP INDEX IF EXISTS "kern"."his_persinschr_actieverval_idx";        -- CREATE INDEX his_persinschr_actieverval_idx ON kern.his_persinschr USING btree (actieverval)
DROP INDEX IF EXISTS "kern"."his_persnation_actieaanpgel";        -- CREATE INDEX his_persnation_actieaanpgel ON kern.his_persnation USING btree (actieaanpgel)
DROP INDEX IF EXISTS "kern"."his_persnation_actieaanpgel_idx";        -- CREATE INDEX his_persnation_actieaanpgel_idx ON kern.his_persnation USING btree (actieaanpgel)
DROP INDEX IF EXISTS "kern"."his_persnation_actieinh";        -- CREATE INDEX his_persnation_actieinh ON kern.his_persnation USING btree (actieinh)
DROP INDEX IF EXISTS "kern"."his_persnation_actieinh_idx";        -- CREATE INDEX his_persnation_actieinh_idx ON kern.his_persnation USING btree (actieinh)
DROP INDEX IF EXISTS "kern"."his_persnation_actieverval";        -- CREATE INDEX his_persnation_actieverval ON kern.his_persnation USING btree (actieverval)
DROP INDEX IF EXISTS "kern"."his_persnation_actieverval_idx";        -- CREATE INDEX his_persnation_actieverval_idx ON kern.his_persnation USING btree (actieverval)
DROP INDEX IF EXISTS "kern"."his_persopschorting_actieaanpgel";        -- CREATE INDEX his_persopschorting_actieaanpgel ON kern.his_persopschorting USING btree (actieaanpgel)
DROP INDEX IF EXISTS "kern"."his_persopschorting_actieaanpgel_idx";        -- CREATE INDEX his_persopschorting_actieaanpgel_idx ON kern.his_persopschorting USING btree (actieaanpgel)
DROP INDEX IF EXISTS "kern"."his_persopschorting_actieinh";        -- CREATE INDEX his_persopschorting_actieinh ON kern.his_persopschorting USING btree (actieinh)
DROP INDEX IF EXISTS "kern"."his_persopschorting_actieinh_idx";        -- CREATE INDEX his_persopschorting_actieinh_idx ON kern.his_persopschorting USING btree (actieinh)
DROP INDEX IF EXISTS "kern"."his_persopschorting_actieverval";        -- CREATE INDEX his_persopschorting_actieverval ON kern.his_persopschorting USING btree (actieverval)
DROP INDEX IF EXISTS "kern"."his_persopschorting_actieverval_idx";        -- CREATE INDEX his_persopschorting_actieverval_idx ON kern.his_persopschorting USING btree (actieverval)
DROP INDEX IF EXISTS "kern"."his_persoverlijden_actieinh";        -- CREATE INDEX his_persoverlijden_actieinh ON kern.his_persoverlijden USING btree (actieinh)
DROP INDEX IF EXISTS "kern"."his_persoverlijden_actieinh_idx";        -- CREATE INDEX his_persoverlijden_actieinh_idx ON kern.his_persoverlijden USING btree (actieinh)
DROP INDEX IF EXISTS "kern"."his_persoverlijden_actieverval";        -- CREATE INDEX his_persoverlijden_actieverval ON kern.his_persoverlijden USING btree (actieverval)
DROP INDEX IF EXISTS "kern"."his_persoverlijden_actieverval_idx";        -- CREATE INDEX his_persoverlijden_actieverval_idx ON kern.his_persoverlijden USING btree (actieverval)
DROP INDEX IF EXISTS "kern"."his_perspk_actieinh";        -- CREATE INDEX his_perspk_actieinh ON kern.his_perspk USING btree (actieinh)
DROP INDEX IF EXISTS "kern"."his_perspk_actieinh_idx";        -- CREATE INDEX his_perspk_actieinh_idx ON kern.his_perspk USING btree (actieinh)
DROP INDEX IF EXISTS "kern"."his_perspk_actieverval";        -- CREATE INDEX his_perspk_actieverval ON kern.his_perspk USING btree (actieverval)
DROP INDEX IF EXISTS "kern"."his_perspk_actieverval_idx";        -- CREATE INDEX his_perspk_actieverval_idx ON kern.his_perspk USING btree (actieverval)
DROP INDEX IF EXISTS "kern"."his_persreisdoc_actieinh";        -- CREATE INDEX his_persreisdoc_actieinh ON kern.his_persreisdoc USING btree (actieinh)
DROP INDEX IF EXISTS "kern"."his_persreisdoc_actieinh_idx";        -- CREATE INDEX his_persreisdoc_actieinh_idx ON kern.his_persreisdoc USING btree (actieinh)
DROP INDEX IF EXISTS "kern"."his_persreisdoc_actieverval";        -- CREATE INDEX his_persreisdoc_actieverval ON kern.his_persreisdoc USING btree (actieverval)
DROP INDEX IF EXISTS "kern"."his_persreisdoc_actieverval_idx";        -- CREATE INDEX his_persreisdoc_actieverval_idx ON kern.his_persreisdoc USING btree (actieverval)
DROP INDEX IF EXISTS "kern"."his_perssamengesteldenaam_actieaanpgel";        -- CREATE INDEX his_perssamengesteldenaam_actieaanpgel ON kern.his_perssamengesteldenaam USING btree (actieaanpgel)
DROP INDEX IF EXISTS "kern"."his_perssamengesteldenaam_actieaanpgel_idx";        -- CREATE INDEX his_perssamengesteldenaam_actieaanpgel_idx ON kern.his_perssamengesteldenaam USING btree (actieaanpgel)
DROP INDEX IF EXISTS "kern"."his_perssamengesteldenaam_actieinh";        -- CREATE INDEX his_perssamengesteldenaam_actieinh ON kern.his_perssamengesteldenaam USING btree (actieinh)
DROP INDEX IF EXISTS "kern"."his_perssamengesteldenaam_actieinh_idx";        -- CREATE INDEX his_perssamengesteldenaam_actieinh_idx ON kern.his_perssamengesteldenaam USING btree (actieinh)
DROP INDEX IF EXISTS "kern"."his_perssamengesteldenaam_actieverval";        -- CREATE INDEX his_perssamengesteldenaam_actieverval ON kern.his_perssamengesteldenaam USING btree (actieverval)
DROP INDEX IF EXISTS "kern"."his_perssamengesteldenaam_actieverval_idx";        -- CREATE INDEX his_perssamengesteldenaam_actieverval_idx ON kern.his_perssamengesteldenaam USING btree (actieverval)
DROP INDEX IF EXISTS "kern"."his_persuitslnlkiesr_actieinh";        -- CREATE INDEX his_persuitslnlkiesr_actieinh ON kern.his_persuitslnlkiesr USING btree (actieinh)
DROP INDEX IF EXISTS "kern"."his_persuitslnlkiesr_actieinh_idx";        -- CREATE INDEX his_persuitslnlkiesr_actieinh_idx ON kern.his_persuitslnlkiesr USING btree (actieinh)
DROP INDEX IF EXISTS "kern"."his_persuitslnlkiesr_actieverval";        -- CREATE INDEX his_persuitslnlkiesr_actieverval ON kern.his_persuitslnlkiesr USING btree (actieverval)
DROP INDEX IF EXISTS "kern"."his_persuitslnlkiesr_actieverval_idx";        -- CREATE INDEX his_persuitslnlkiesr_actieverval_idx ON kern.his_persuitslnlkiesr USING btree (actieverval)
DROP INDEX IF EXISTS "kern"."his_persverblijfstitel_actieaanpgel";        -- CREATE INDEX his_persverblijfstitel_actieaanpgel ON kern.his_persverblijfstitel USING btree (actieaanpgel)
DROP INDEX IF EXISTS "kern"."his_persverblijfstitel_actieaanpgel_idx";        -- CREATE INDEX his_persverblijfstitel_actieaanpgel_idx ON kern.his_persverblijfstitel USING btree (actieaanpgel)
DROP INDEX IF EXISTS "kern"."his_persverblijfstitel_actieinh";        -- CREATE INDEX his_persverblijfstitel_actieinh ON kern.his_persverblijfstitel USING btree (actieinh)
DROP INDEX IF EXISTS "kern"."his_persverblijfstitel_actieinh_idx";        -- CREATE INDEX his_persverblijfstitel_actieinh_idx ON kern.his_persverblijfstitel USING btree (actieinh)
DROP INDEX IF EXISTS "kern"."his_persverblijfstitel_actieverval";        -- CREATE INDEX his_persverblijfstitel_actieverval ON kern.his_persverblijfstitel USING btree (actieverval)
DROP INDEX IF EXISTS "kern"."his_persverblijfstitel_actieverval_idx";        -- CREATE INDEX his_persverblijfstitel_actieverval_idx ON kern.his_persverblijfstitel USING btree (actieverval)
DROP INDEX IF EXISTS "kern"."his_persverificatie_actieinh";        -- CREATE INDEX his_persverificatie_actieinh ON kern.his_persverificatie USING btree (actieinh)
DROP INDEX IF EXISTS "kern"."his_persverificatie_actieinh_idx";        -- CREATE INDEX his_persverificatie_actieinh_idx ON kern.his_persverificatie USING btree (actieinh)
DROP INDEX IF EXISTS "kern"."his_persverificatie_actieverval";        -- CREATE INDEX his_persverificatie_actieverval ON kern.his_persverificatie USING btree (actieverval)
DROP INDEX IF EXISTS "kern"."his_persverificatie_actieverval_idx";        -- CREATE INDEX his_persverificatie_actieverval_idx ON kern.his_persverificatie USING btree (actieverval)
DROP INDEX IF EXISTS "kern"."his_persvoornaam_actieaanpgel";        -- CREATE INDEX his_persvoornaam_actieaanpgel ON kern.his_persvoornaam USING btree (actieaanpgel)
DROP INDEX IF EXISTS "kern"."his_persvoornaam_actieaanpgel_idx";        -- CREATE INDEX his_persvoornaam_actieaanpgel_idx ON kern.his_persvoornaam USING btree (actieaanpgel)
DROP INDEX IF EXISTS "kern"."his_persvoornaam_actieinh";        -- CREATE INDEX his_persvoornaam_actieinh ON kern.his_persvoornaam USING btree (actieinh)
DROP INDEX IF EXISTS "kern"."his_persvoornaam_actieinh_idx";        -- CREATE INDEX his_persvoornaam_actieinh_idx ON kern.his_persvoornaam USING btree (actieinh)
DROP INDEX IF EXISTS "kern"."his_persvoornaam_actieverval";        -- CREATE INDEX his_persvoornaam_actieverval ON kern.his_persvoornaam USING btree (actieverval)
DROP INDEX IF EXISTS "kern"."his_persvoornaam_actieverval_idx";        -- CREATE INDEX his_persvoornaam_actieverval_idx ON kern.his_persvoornaam USING btree (actieverval)
DROP INDEX IF EXISTS "kern"."his_verstrderde_actieinh_idx";        -- CREATE INDEX his_verstrderde_actieinh_idx ON kern.his_verstrderde USING btree (actieinh)
DROP INDEX IF EXISTS "kern"."his_verstrderde_actieverval_idx";        -- CREATE INDEX his_verstrderde_actieverval_idx ON kern.his_verstrderde USING btree (actieverval)
DROP INDEX IF EXISTS "lev"."his_abonnement_actieinh";        -- CREATE INDEX his_abonnement_actieinh ON lev.his_abonnement USING btree (actieinh)
DROP INDEX IF EXISTS "lev"."his_abonnement_actieinh_idx";        -- CREATE INDEX his_abonnement_actieinh_idx ON lev.his_abonnement USING btree (actieinh)
DROP INDEX IF EXISTS "lev"."his_abonnement_actieverval";        -- CREATE INDEX his_abonnement_actieverval ON lev.his_abonnement USING btree (actieverval)
DROP INDEX IF EXISTS "lev"."his_abonnement_actieverval_idx";        -- CREATE INDEX his_abonnement_actieverval_idx ON lev.his_abonnement USING btree (actieverval)
DROP INDEX IF EXISTS "lev"."his_abonnementsrtber_actieinh";        -- CREATE INDEX his_abonnementsrtber_actieinh ON lev.his_abonnementsrtber USING btree (actieinh)
DROP INDEX IF EXISTS "lev"."his_abonnementsrtber_actieinh_idx";        -- CREATE INDEX his_abonnementsrtber_actieinh_idx ON lev.his_abonnementsrtber USING btree (actieinh)
DROP INDEX IF EXISTS "lev"."his_abonnementsrtber_actieverval";        -- CREATE INDEX his_abonnementsrtber_actieverval ON lev.his_abonnementsrtber USING btree (actieverval)
DROP INDEX IF EXISTS "lev"."his_abonnementsrtber_actieverval_idx";        -- CREATE INDEX his_abonnementsrtber_actieverval_idx ON lev.his_abonnementsrtber USING btree (actieverval)


DROP SCHEMA IF EXISTS IST CASCADE;
CREATE SCHEMA IST;



CREATE SEQUENCE IST.seq_Stapel;
CREATE TABLE IST.Stapel (
   ID                            Integer                       NOT NULL  DEFAULT nextval('IST.seq_Stapel')  /* StapelID */,
   Pers                          Integer                       NOT NULL    /* PersID */,
   Categorie                     Varchar(2)  CHECK (Categorie <> '' )  NOT NULL    /* LO3Categorie */,
   Volgnr                        Integer                       NOT NULL    /* Volgnr */,
   CONSTRAINT R9991 PRIMARY KEY (ID),
   CONSTRAINT R9992 UNIQUE (Pers, Categorie, Volgnr)
);
ALTER SEQUENCE IST.seq_Stapel OWNED BY IST.Stapel.ID;
CREATE SEQUENCE IST.seq_StapelRelatie;
CREATE TABLE IST.StapelRelatie (
   ID                            Integer                       NOT NULL  DEFAULT nextval('IST.seq_StapelRelatie')  /* StapelRelatieID */,
   Stapel                        Integer                       NOT NULL    /* StapelID */,
   Relatie                       Integer                       NOT NULL    /* RelatieID */,
   CONSTRAINT R9993 PRIMARY KEY (ID),
   CONSTRAINT R9994 UNIQUE (Stapel, Relatie)
);
ALTER SEQUENCE IST.seq_StapelRelatie OWNED BY IST.StapelRelatie.ID;
CREATE SEQUENCE IST.seq_StapelVoorkomen;
CREATE TABLE IST.StapelVoorkomen (
   ID                            Integer                       NOT NULL  DEFAULT nextval('IST.seq_StapelVoorkomen')  /* StapelVoorkomenID */,
   Stapel                        Integer                       NOT NULL    /* StapelID */,
   Volgnr                        Integer                       NOT NULL    /* Volgnr */,
   SrtDoc                        Smallint                                  /* SrtDocID */,
   Partij                        Smallint                                  /* PartijID */,
   Rubr8220DatDoc                Integer                                   /* Dat */,
   DocOms                        Varchar(40)  CHECK (DocOms <> '' )              /* DocOms */,
   Rubr8310AandGegevensInOnderz  Integer                                   /* LO3Rubriek */,
   Rubr8320DatIngangOnderzoek    Integer                                   /* Dat */,
   Rubr8330DatEindeOnderzoek     Integer                                   /* Dat */,
   Rubr8410OnjuistStrijdigOpenb  Varchar(1)  CHECK (Rubr8410OnjuistStrijdigOpenb <> '' )              /* LO3CoderingOnjuist */,
   Rubr8510IngangsdatGel         Integer                                   /* Dat */,
   Rubr8610DatVanOpneming        Integer                                   /* Dat */,
   Rubr6210DatIngangFamilierech  Integer                                   /* Dat */,
   Aktenr                        Varchar(7)  CHECK (Aktenr <> '' )              /* Aktenr */,
   ANr                           Bigint                                    /* ANr */,
   BSN                           Integer                                   /* BSN */,
   Voornamen                     Varchar(200)  CHECK (Voornamen <> '' )              /* Voornamen */,
   Predikaat                     Smallint                                  /* PredikaatID */,
   AdellijkeTitel                Smallint                                  /* AdellijkeTitelID */,
   GeslachtBijAdellijkeTitelPre  Smallint                                  /* GeslachtsaandID */,
   Voorvoegsel                   Varchar(10)  CHECK (Voorvoegsel <> '' )              /* Voorvoegsel1 */,
   Scheidingsteken               Varchar(1)  CHECK (Scheidingsteken <> '' )              /* Scheidingsteken */,
   Geslnaam                      Varchar(200)  CHECK (Geslnaam <> '' )              /* Geslnaam */,
   DatGeboorte                   Integer                                   /* Dat */,
   GemGeboorte                   Smallint                                  /* GemID */,
   BLPlaatsGeboorte              Varchar(40)  CHECK (BLPlaatsGeboorte <> '' )              /* BLPlaats */,
   OmsLocGeboorte                Varchar(40)  CHECK (OmsLocGeboorte <> '' )              /* LocOms */,
   LandGeboorte                  Integer                                   /* LandID */,
   Geslachtsaand                 Smallint                                  /* GeslachtsaandID */,
   DatAanv                       Integer                                   /* Dat */,
   GemAanv                       Smallint                                  /* GemID */,
   BLPlaatsAanv                  Varchar(40)  CHECK (BLPlaatsAanv <> '' )              /* BLPlaats */,
   OmsLocAanv                    Varchar(40)  CHECK (OmsLocAanv <> '' )              /* LocOms */,
   LandAanv                      Integer                                   /* LandID */,
   RdnEinde                      Smallint                                  /* RdnBeeindRelatieID */,
   DatEinde                      Integer                                   /* Dat */,
   GemEinde                      Smallint                                  /* GemID */,
   BLPlaatsEinde                 Varchar(40)  CHECK (BLPlaatsEinde <> '' )              /* BLPlaats */,
   OmsLocEinde                   Varchar(40)  CHECK (OmsLocEinde <> '' )              /* LocOms */,
   LandEinde                     Integer                                   /* LandID */,
   SrtRelatie                    Smallint                                  /* SrtRelatieID */,
   IndOuder1HeeftGezag           Boolean CHECK (IndOuder1HeeftGezag IS NULL OR IndOuder1HeeftGezag IN ('T'))              /* Ja */,
   IndOuder2HeeftGezag           Boolean CHECK (IndOuder2HeeftGezag IS NULL OR IndOuder2HeeftGezag IN ('T'))              /* Ja */,
   IndDerdeHeeftGezag            Boolean CHECK (IndDerdeHeeftGezag IS NULL OR IndDerdeHeeftGezag IN ('T'))              /* Ja */,
   IndOnderCuratele              Boolean CHECK (IndOnderCuratele IS NULL OR IndOnderCuratele IN ('T'))              /* Ja */,
   CONSTRAINT R9995 PRIMARY KEY (ID),
   CONSTRAINT R9996 UNIQUE (Stapel, Volgnr)
);
ALTER SEQUENCE IST.seq_StapelVoorkomen OWNED BY IST.StapelVoorkomen.ID;


ALTER TABLE IST.Stapel ADD CONSTRAINT FK9919 FOREIGN KEY (Pers) REFERENCES Kern.Pers (ID);
ALTER TABLE IST.StapelRelatie ADD CONSTRAINT FK9929 FOREIGN KEY (Stapel) REFERENCES IST.Stapel (ID);
ALTER TABLE IST.StapelRelatie ADD CONSTRAINT FK9930 FOREIGN KEY (Relatie) REFERENCES Kern.Relatie (ID);
ALTER TABLE IST.StapelVoorkomen ADD CONSTRAINT FK9935 FOREIGN KEY (Stapel) REFERENCES IST.Stapel (ID);
ALTER TABLE IST.StapelVoorkomen ADD CONSTRAINT FK9975 FOREIGN KEY (SrtDoc) REFERENCES Kern.SrtDoc (ID);
ALTER TABLE IST.StapelVoorkomen ADD CONSTRAINT FK9976 FOREIGN KEY (Partij) REFERENCES Kern.Partij (ID);
ALTER TABLE IST.StapelVoorkomen ADD CONSTRAINT FK9951 FOREIGN KEY (Predikaat) REFERENCES Kern.Predikaat (ID);
ALTER TABLE IST.StapelVoorkomen ADD CONSTRAINT FK9952 FOREIGN KEY (AdellijkeTitel) REFERENCES Kern.AdellijkeTitel (ID);
ALTER TABLE IST.StapelVoorkomen ADD CONSTRAINT FK9953 FOREIGN KEY (GeslachtBijAdellijkeTitelPre) REFERENCES Kern.Geslachtsaand (ID);
ALTER TABLE IST.StapelVoorkomen ADD CONSTRAINT FK9958 FOREIGN KEY (GemGeboorte) REFERENCES Kern.Gem (ID);
ALTER TABLE IST.StapelVoorkomen ADD CONSTRAINT FK9961 FOREIGN KEY (LandGeboorte) REFERENCES Kern.Land (ID);
ALTER TABLE IST.StapelVoorkomen ADD CONSTRAINT FK9962 FOREIGN KEY (Geslachtsaand) REFERENCES Kern.Geslachtsaand (ID);
ALTER TABLE IST.StapelVoorkomen ADD CONSTRAINT FK9964 FOREIGN KEY (GemAanv) REFERENCES Kern.Gem (ID);
ALTER TABLE IST.StapelVoorkomen ADD CONSTRAINT FK9967 FOREIGN KEY (LandAanv) REFERENCES Kern.Land (ID);
ALTER TABLE IST.StapelVoorkomen ADD CONSTRAINT FK9970 FOREIGN KEY (RdnEinde) REFERENCES Kern.RdnBeeindRelatie (ID);
ALTER TABLE IST.StapelVoorkomen ADD CONSTRAINT FK9969 FOREIGN KEY (GemEinde) REFERENCES Kern.Gem (ID);
ALTER TABLE IST.StapelVoorkomen ADD CONSTRAINT FK9973 FOREIGN KEY (LandEinde) REFERENCES Kern.Land (ID);
ALTER TABLE IST.StapelVoorkomen ADD CONSTRAINT FK9974 FOREIGN KEY (SrtRelatie) REFERENCES Kern.SrtRelatie (ID);


CREATE INDEX ON IST.Stapel (Pers); -- Index door foreign key
CREATE INDEX ON IST.StapelRelatie (Stapel); -- Index door foreign key
CREATE INDEX ON IST.StapelRelatie (Relatie); -- Index door foreign key
CREATE INDEX ON IST.StapelVoorkomen (Stapel); -- Index door foreign key
CREATE INDEX ON Kern.Actie (AdmHnd); -- Index door foreign key
CREATE INDEX ON Kern.ActieBron (Actie); -- Index door foreign key
CREATE INDEX ON Kern.ActieBron (Doc); -- Index door foreign key
CREATE INDEX ON Kern.Betr (Relatie); -- Index door foreign key
CREATE INDEX ON Kern.Betr (Pers); -- Index door foreign key
CREATE INDEX ON Kern.Betr (Pers, Relatie); -- Index door expliciete index in logisch model
CREATE INDEX ON Kern.GegevenInOnderzoek (Onderzoek); -- Index door foreign key
