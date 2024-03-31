CREATE UNIQUE INDEX "udx.layer.id"
    ON layer (id);

CREATE UNIQUE INDEX "udx.layer_tag.id"
    ON layer_tag (id);

CREATE INDEX "idx.layer.status"
    ON layer (status);