CREATE EXTENSION IF NOT EXISTS pgcrypto;

CREATE TYPE user_status  AS ENUM ('ACTIVE', 'INACTIVE', 'BANNED', 'PENDING_VERIFICATION');

CREATE TYPE user_profile_visibility AS ENUM ('PUBLIC', 'PRIVATE');

CREATE TABLE tb_users
(
	id UUID DEFAULT gen_random_uuid(),
	name VARCHAR(25) NOT NULL,
	lastname VARCHAR(25),
	username VARCHAR(15) UNIQUE NOT NULL,
	email VARCHAR(254) UNIQUE NOT NULL,
	password VARCHAR,
	bio TEXT,
	avatar_url TEXT,
	verified BOOLEAN DEFAULT false,
	active BOOLEAN DEFAULT true,
	status user_status,
	profile_visibility user_profile_visibility default 'PUBLIC',
	followers_count BIGINT DEFAULT 0,
	following_count BIGINT DEFAULT 0,
	created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	deleted_at TIMESTAMP,
	CONSTRAINT pk_user PRIMARY KEY (id)
);

CREATE TABLE tb_roles
(
	id UUID DEFAULT gen_random_uuid(),
	name VARCHAR UNIQUE CHECK (name like 'ROLE_%') ,
	description TEXT,
	CONSTRAINT pk_roles PRIMARY KEY (id)
);

CREATE TABLE tb_user_roles
(
	user_id UUID,
	role_id UUID,
	CONSTRAINT pk_user_roles_user_role PRIMARY KEY (user_id, role_id),

	CONSTRAINT fk_user_roles_user_id FOREIGN KEY (user_id)
	REFERENCES tb_users (id) ON DELETE CASCADE,

	CONSTRAINT fk_user_roles_role_id FOREIGN KEY (role_id)
	REFERENCES tb_roles (id)
);

CREATE TABLE tb_posts
(
	id UUID DEFAULT gen_random_uuid(),
	author_id UUID NOT NULL,
	content TEXT ,
	post_type VARCHAR(15),
	views_count BIGINT DEFAULT 0,
	likes_count BIGINT DEFAULT 0,
	comments_count INTEGER DEFAULT 0,
	status VARCHAR(15) DEFAULT 'ACTIVE',
	visibility VARCHAR(15) DEFAULT 'PUBLIC',
	created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	updated_at TIMESTAMP,
	deleted_at TIMESTAMP,

	CONSTRAINT pk_posts PRIMARY KEY (id),

    CONSTRAINT chk_posts_post_type
    CHECK (post_type IN ('TEXT', 'IMAGE', 'VIDEO', 'MIXED')),

    CONSTRAINT chk_posts_post_status
    CHECK (status IN ('ACTIVE', 'FLAGGED', 'DELETED', 'ARCHIVED')),

    CONSTRAINT chk_posts_post_visibility
    CHECK (visibility IN ('PUBLIC', 'PRIVATE', 'FOLLOWERS_ONLY')),

	CONSTRAINT fk_posts_author FOREIGN KEY (author_id)
	REFERENCES tb_users (id) ON DELETE CASCADE
);

CREATE TABLE tb_post_likes
(
	id UUID DEFAULT gen_random_uuid(),
	user_id UUID NOT NULL,
	post_id UUID NOT NULL,
	liked_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

	CONSTRAINT uq_posts_likes_user_post UNIQUE (user_id, post_id),

	CONSTRAINT pk_tb_posts_likes PRIMARY KEY (id),

	CONSTRAINT fk_tb_posts_likes_user FOREIGN KEY (user_id)
	REFERENCES tb_users (id) ON DELETE CASCADE,

	CONSTRAINT fk_tb_posts_likes_post FOREIGN KEY (post_id)
	REFERENCES tb_posts (id) ON DELETE CASCADE
);

CREATE TABLE tb_follows
(
	id UUID DEFAULT gen_random_uuid(),
	following_id UUID,
	follower_id UUID,
	followed_at TIMESTAMP,

	CONSTRAINT uq_follows_following_follower UNIQUE (following_id, follower_id),

	CONSTRAINT chk_follower_and_following_are_different CHECK (follower_id <> following_id),

	CONSTRAINT pk_follow PRIMARY KEY (id),

	CONSTRAINT fk_follows_following FOREIGN KEY (following_id)
	REFERENCES tb_users (id) ON DELETE CASCADE,

	CONSTRAINT fk_follows_follower FOREIGN KEY (follower_id)
	REFERENCES tb_users (id) ON DELETE CASCADE
);