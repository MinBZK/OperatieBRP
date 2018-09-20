CREATE ROLE brpreview LOGIN PASSWORD 'brpreview' VALID UNTIL 'infinity';
GRANT ALL ON SCHEMA dashboard TO brpreview;
GRANT ALL ON TABLE dashboard.seq_berichten TO brpreview;
GRANT ALL ON TABLE dashboard.berichten TO brpreview;
