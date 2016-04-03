This demo app highlights a problem when reading compressed files from GCS from version 1.5.0 of the google-cloud-sdk

When using v 1.5.0 the ParDo only processes the header row of the csv.  When using v 1.4.0 every row is processed.
