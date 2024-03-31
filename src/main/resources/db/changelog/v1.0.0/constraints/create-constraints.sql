ALTER TABLE layer
    ADD CONSTRAINT "pk.layer.id" PRIMARY KEY (id);

--ALTER TABLE layer
--    ADD CONSTRAINT "fk.layer.created_by"
--    FOREIGN KEY (created_by) REFERENCES "user"(id);
--
--ALTER TABLE layer
--    ADD CONSTRAINT "fk.layer.edit_by"
--    FOREIGN KEY (edit_by) REFERENCES "user"(id);

ALTER TABLE layer_tag
    ADD CONSTRAINT "pk.layer_tag.id" PRIMARY KEY (id);

ALTER TABLE layer_tag
    ADD CONSTRAINT "fk.layer_tag.layer_id"
    FOREIGN KEY (layer_id) REFERENCES layer(id);