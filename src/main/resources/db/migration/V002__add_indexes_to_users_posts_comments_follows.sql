CREATE INDEX IF NOT EXISTS idx_users_username ON tb_users(username);
CREATE INDEX IF NOT EXISTS idx_users_email ON tb_users(email);
CREATE INDEX IF NOT EXISTS idx_posts_author on tb_posts(author_id);
CREATE INDEX IF NOT EXISTS idx_follows_following ON tb_follows(following_id);
CREATE INDEX IF NOT EXISTS idx_follows_follower ON tb_follows(follower_id);