package vnt.dip.rpg_to_do_kurs

import vnt.dip.rpg_to_do_kurs.db.AppDatabase

lateinit var MAIN : MainActivity
lateinit var DB: AppDatabase
var ADMIN_MODE = false

const val TASK_DB_TABLE_NAME = "task_table"
const val HERO_DB_TABLE_NAME = "hero_table"
const val DB_NAME = "task-hero-database"
const val BUNDLE_KEY_TASK_TO_DESC = "key_task_open_info"
const val BUNDLE_KEY_DESC_TO_EDIT = "key_task_edit"
const val PREFS_NAME = "hero_prefs"
const val CHARACTER_KEY = "hero"

