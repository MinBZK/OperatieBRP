#!/bin/bash

psql -h bdev-db02.modernodam.nl -U delta -f bronnen.sql bronnen
