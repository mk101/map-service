CREATE TABLE layer (
    id UUID NOT NULL,

    created_by UUID NOT NULL,
    created_at timestamp with time zone NOT NULL,

    edit_by UUID,
    edit_at timestamp with time zone,

    data jsonb NOT NULL,

    horizontal_area numrange NOT NULL,
    vertical_area   numrange NOT NULL,

    name varchar(256) NOT NULL,
    description varchar(512) NOT NULL,
    status varchar(32) NOT NULL
);

CREATE TABLE layer_tag (
    id UUID NOT NULL,
    layer_id UUID NOT NULL,
    "value" varchar(128) NOT NULL
)