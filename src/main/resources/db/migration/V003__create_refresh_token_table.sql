CREATE TABLE tb_refresh_tokens
(
    id UUID DEFAULT gen_random_uuid(),
    user_id UUID NOT NULL,
    token TEXT,
    expires_at TIMESTAMP,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT pk_refresh_tokens PRIMARY KEY (id),

    CONSTRAINT fk_refresh_tokens_user FOREIGN KEY (user_id)
    REFERENCES tb_users (id) ON DELETE CASCADE
);