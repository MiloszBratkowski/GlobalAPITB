CREATE TABLE IF NOT EXISTS "%prefix%all_players%suffix%" (
	"id"	INTEGER NOT NULL UNIQUE,
	"player_uuid"	TEXT NOT NULL,
	"player_name"	TEXT NOT NULL,
	"first_join"	INTEGER NOT NULL DEFAULT 0,
	"last_join"	INTEGER NOT NULL DEFAULT 0,
	"join_count"	INTEGER NOT NULL DEFAULT 0,
	"join_time"	INTEGER NOT NULL DEFAULT 0,
	PRIMARY KEY("id" AUTOINCREMENT)
);