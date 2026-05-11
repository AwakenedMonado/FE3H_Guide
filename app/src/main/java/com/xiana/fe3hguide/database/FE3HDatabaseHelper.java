package com.xiana.fe3hguide.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.xiana.fe3hguide.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.regex.Pattern;


public class FE3HDatabaseHelper extends SQLiteOpenHelper {

    private final Context context;
    private static final String DB_NAME = "fe3h";       // name of the database
    private static final int DB_VERSION = 16;            // version of the database

    public FE3HDatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createCharactersTable(db);
        createAbilitiesTable(db);
        createCombatArtsTables(db);
        createSpellsTable(db);
        createMagicTable(db);
        createClassesTable(db);
        createCharacterGiftsTable(db);
        createCharacterMealsTable(db);
        createCharacterLostItemsTable(db);
        createFavouriteTeasTable(db);
        createTopicsTable(db);
        createFinalConversationsTable(db);
        createSupportsTable(db);
        createGambitsTable(db);
        createBattalionsTable(db);
        createFacultyTrainingTable(db);
        createLectureQuestionsTable(db);
        createWeaponsTable(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 2) {
            createGambitsTable(db);
        }
        if (oldVersion < 3) {
            db.execSQL("DROP TABLE IF EXISTS Battalions");
            createBattalionsTable(db);
        }
        if (oldVersion < 4) {
            db.execSQL("DROP TABLE IF EXISTS Battalions");
            createBattalionsTable(db);
        }
        if (oldVersion < 5) {
            db.execSQL("DROP TABLE IF EXISTS Characters");
            createCharactersTable(db);
        }
        if (oldVersion < 6) {
            db.execSQL("DROP TABLE IF EXISTS Classes");
            createClassesTable(db);
            db.execSQL("DROP TABLE IF EXISTS Abilities");
            createAbilitiesTable(db);
        }
        if (oldVersion < 7) {
            db.execSQL("DROP TABLE IF EXISTS Battalions");
            createBattalionsTable(db);
        }
        if (oldVersion < 8) {
            createFacultyTrainingTable(db);
        }
        if (oldVersion < 9) {
            db.execSQL("DROP TABLE IF EXISTS FacultyTraining");
            createFacultyTrainingTable(db);
        }
        if (oldVersion < 10) {
            createLectureQuestionsTable(db);
        }
        if (oldVersion < 11) {
            createWeaponsTable(db);
        }
        if (oldVersion < 12) {
            db.execSQL("UPDATE Supports SET bSupport = REPLACE(bSupport, 'Batlhus:', 'Balthus:') "
                    + "WHERE character1 = 5 AND character2 = 22");
        }
        if (oldVersion < 13) {
            db.execSQL("UPDATE LectureQuestions SET phase = 'post' "
                    + "WHERE characterName = 'Alois' AND phase = 'pre' "
                    + "AND question LIKE '%anniversary%'");
        }
        if (oldVersion < 14) {
            db.execSQL("UPDATE LectureQuestions SET phase = 'post' "
                    + "WHERE characterName = 'Leonie' AND phase = 'pre' "
                    + "AND question LIKE '%Blade Breaker%'");
        }
        if (oldVersion < 15) {
            db.execSQL("UPDATE LectureQuestions SET phase = 'post' "
                    + "WHERE characterName = 'Felix' AND phase = 'pre' "
                    + "AND question LIKE '%sword for a long time%'");
            db.execSQL("UPDATE LectureQuestions SET phase = 'post' "
                    + "WHERE characterName = 'Ashe' AND phase = 'pre' "
                    + "AND question LIKE '%brother and sister%'");
        }
        if (oldVersion < 16) {
            db.execSQL("UPDATE LectureQuestions SET phase = 'post' "
                    + "WHERE characterName = 'Caspar' AND phase = 'pre' "
                    + "AND question LIKE '%nickname%'");
        }
    }

    private void createCharactersTable(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE Characters ( "
                + "_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "name TEXT, "
                + "portrait TEXT, "
                + "pronouns TEXT, "
                + "faction TEXT, "
                + "age INTEGER, "
                + "birthday TEXT, "
                + "fodlanBirthday TEXT, "
                + "crest TEXT, "
                + "baseStatsHP TEXT, "
                + "baseStatsStr TEXT, "
                + "baseStatsMag TEXT, "
                + "baseStatsDex TEXT, "
                + "baseStatsSpd TEXT, "
                + "baseStatsLck TEXT, "
                + "baseStatsDef TEXT, "
                + "baseStatsRes TEXT, "
                + "baseStatsCha TEXT, "
                + "growthRatesHP TEXT, "
                + "growthRatesStr TEXT, "
                + "growthRatesMag TEXT, "
                + "growthRatesDex TEXT, "
                + "growthRatesSpd TEXT, "
                + "growthRatesLck TEXT, "
                + "growthRatesDef TEXT, "
                + "growthRatesRes TEXT, "
                + "growthRatesCha TEXT, "
                + "skillSword TEXT, "
                + "skillLance TEXT, "
                + "skillAxe TEXT, "
                + "skillBow TEXT, "
                + "skillBrawling TEXT, "
                + "skillReason TEXT, "
                + "skillFaith TEXT, "
                + "skillAuthority TEXT, "
                + "skillHeavyArmor TEXT, "
                + "skillRiding TEXT, "
                + "skillFlying TEXT, "
                + "recruitment TEXT);");

        insertCharacters(db);
    }

    private void insertCharacters(SQLiteDatabase db) {
        insertCharacter(db, "Alois", "alois", "he/him", "Knights of Seiros", 44, "December 1", "Ethereal 1_Imperial Year 1135", "None", 47, 24, 8, 12, 14, 11, 17, 8, 16, "45%", "45%", "20%", "35%", "40%", "30%", "40%", "20%", "40%", "E", "E", "A_UP", "E", "B_UP", "E_DOWN", "E", "C", "C_UP", "E", "E_DOWN", "Available from Chapter 11 to the end of the Academy Phase._Level requirement with no support: 15 (reduced in 3 with each support rank down to 3 at B+).");
        insertCharacter(db, "Anna", "anna", "she/her", "Knights of Seiros", 25, "June 9", "Garland Moon 9_Imperial Year 1155", "Major Crest of Ernest", 28, 8, 8, 9, 11, 14, 6, 8, 9, "35%", "35%", "35%", "45%", "55%", "45%", "30%", "40%", "50%", "D_UP", "E", "E+_UP", "E+_UP", "E", "E_DOWN", "E+_UP", "E_DOWN", "E", "E$Pass", "E", "Must have the DLC._Available after Chapter 3._No extra requirements.");
        insertCharacter(db, "Annette", "annette", "she/her", "Blue Lions", 16, "May 9", "Harpstring Moon 9_Imperial Year 1163", "Minor Crest of Dominic", 23, 6, 11, 7, 7, 6, 5, 4, 6, "25%", "30%", "50%", "50%", "35%", "35%", "20%", "30%", "35%", "E", "E", "E+_UP", "E_DOWN", "E", "D+_UP", "E", "E+_UP", "E_DOWN", "E", "E", "Skill requirement: Faith (B with no support, reduced to C+, C, D+ and D with each rank up to B+)._Stat requirement: Mag (10 with no support, reduced in 2 with each support rank down to 2 at B+).");
        insertCharacter(db, "Ashe", "ashe", "he/him", "Blue Lions", 16, "October 17", "Wyvern Moon 17_Imperial Year 1163", "None", 23, 8, 5, 8, 9, 6, 5, 6, 5, "35%", "35%", "25%", "55%", "50%", "40%", "20%", "35%", "25%", "E", "E$Shatter Slash", "E+_UP", "D_UP", "E", "E_DOWN", "E", "E", "E", "E", "E", "Skill requirement: Lance (C, C, D+, D and E+ with no rank, C, C+, B and B+ support, respectively)._Stat requirement: Cha (15 with no support, reduced in 3 with each support rank down to 3 at B+)");
        insertCharacter(db, "Balthus", "balthus", "he/him", "Ashen Wolves", 26, "July 9", "Blue Sea Moon 9_Imperial Year 1153", "Major Crest of Chevalier", 29, 12, 6, 5, 8, 3, 8, 5, 5, "50%", "50%", "30%", "25%", "30%", "20%", "45%", "30%", "30%", "E+_UP", "E_DOWN", "D+_UP", "E_DOWN", "D+_UP", "E_DOWN$Black Magic Crit +10", "E_UP", "E+", "E_UP", "E", "E_DOWN", "Can be recruited in the main campaign after completing Chapter 4 of Cindered Shadows.");
        insertCharacter(db, "Bernadetta", "bernadetta", "she/her", "Black Eagles", 17, "December 12", "Ethereal Moon 12_Imperial Year 1162", "Minor Crest of Indech", 25, 8, 5, 7, 7, 5, 4, 2, 6, "35%", "35%", "20%", "55%", "50%", "25%", "20%", "30%", "35%", "E_DOWN", "E+_UP", "E_DOWN", "D_UP", "E_DOWN", "E", "E", "E", "E_DOWN", "E$Pass", "E", "Skill requirement: Bow (C, C, D+, D and E+ with no rank, C, C+, B and B+ support, respectively)._Stat requirement: Str (20 with no support, reduced in 4 with each support rank down to 4 at B+)");
        insertCharacter(db, "Caspar", "caspar", "he/him", "Black Eagles", 16, "July 1", "Blue Sea Moon 1_Imperial Year 1163", "None", 26, 9, 3, 5, 6, 8, 6, 2, 4, "55%", "45%", "25%", "45%", "45%", "40%", "30%", "20%", "25%", "E", "E", "D_UP", "E_DOWN", "E+_UP", "E_DOWN", "E", "E_DOWN", "E", "E", "E", "Skill requirement: Brawl (C, C, D+, D and E+ with no rank, C, C+, B and B+ support, respectively)._Stat requirement: Str (10 with no support, reduced in 2 with each support rank down to 2 at B+)");
        insertCharacter(db, "Catherine", "catherine", "she/her", "Knights of Seiros", 27, "September 15", "Horsebow Moon 15_Imperial Year 1152", "Major Crest of Charon", 36, 17, 8, 12, 16, 10, 12, 8, 7, "50%", "50%", "25%", "40%", "55%", "30%", "30%", "20%", "25%", "A_UP", "E", "E", "E", "C+_UP", "E_DOWN", "E", "E", "E", "E", "E", "Available from Chapter 4 to the end of the Academy Phase._Not available in Crimson Flower._Automatically joins in Chapter 12 in Silver Snow._Level requirement with no support: 15 (reduced in 3 with each support rank down to 3 at B+)");
        insertCharacter(db, "Claude", "claude", "he/him", "Golden Deer", 17, "July 24", "Blue Sea Moon 24_Imperial Year 1162", "Minor Crest of Riegan", 26, 11, 5, 8, 8, 7, 6, 4, 8, "35%", "40%", "25%", "60%", "55%", "45%", "30%", "25%", "55%", "E+_UP", "E_DOWN", "E$Diamond Axe", "D_UP", "E", "E", "E_DOWN", "D_UP", "E", "E+_UP", "E+_UP", "Only available in Verdant Wind (Golden Deer route)");
        insertCharacter(db, "Constance", "constance", "she/her", "Ashen Wolves", 18, "March 20", "Lone Moon 20_Imperial Year 1161", "Major Crest of Noa", 23, 5, 12, 7, 7, 4, 3, 5, 7, "20%", "20%", "60%", "30%", "35%", "15%", "15%", "30%", "25%", "E+_UP", "E", "E_DOWN", "E", "E$Mystic Blow", "D+_UP", "E_UP", "D_UP", "E_DOWN", "E", "D_UP", "Can be recruited in the main campaign after completing Chapter 2 of Cindered Shadows.");
        insertCharacter(db, "Cyril", "cyril", "he/him", "Knights of Seiros", 14, "October 25", "Wyvern Moon 25_Imperial Year 1165", "None", 24, 7, 4, 6, 6, 6, 5, 2, 4, "35%", "20%", "15%", "40%", "40%", "30%", "10%", "10%", "15%", "E", "E_UP", "D+_UP", "D_UP", "E", "E_DOWN", "E_DOWN", "E", "E", "E_UP", "E_UP", "Available from Chapter 5 to the end of the Academy Phase._Not available in Crimson Flower._Automatically joins in Chapter 12 in Silver Snow._Level requirement with no support: 10 (reduced in 2 with each support rank down to 2 at B+");
        insertCharacter(db, "Dedue", "dedue", "he/him", "Blue Lions", 18, "August 31", "Verdant Rain Moon_Imperial Year 1161", "None", 30, 12, 2, 5, 7, 5, 8, 1, 4, "60%", "50%", "15%", "30%", "20%", "25%", "50%", "10%", "30%", "E", "E+_UP", "D+_UP", "E", "E+_UP", "E", "E_DOWN", "E", "D_UP", "E_DOWN", "E_DOWN", "Only available in Azure Moon (Blue Lions route)");
        insertCharacter(db, "Dimitri", "dimitri", "he/him", "Blue Lions", 17, "December 20", "Ethereal Moon 20_Imperial Year 1162", "Minor Crest of Blaiddyd", 28, 12, 4, 7, 7, 5, 7, 4, 9, "55%", "60%", "20%", "50%", "50%", "25%", "40%", "20%", "55%", "E+_UP", "D+_UP", "E_DOWN", "E", "E", "E_DOWN", "E", "D_UP", "E", "D$Seal Movement", "E", "Only available in Azure Moon (Blue Lions route)");
        insertCharacter(db, "Dorothea", "dorothea", "she/her", "Black Eagles", 18, "September 29", "Horsebow Moon 29_Imperial Year 1161", "None", 24, 5, 11, 6, 7, 6, 4, 7, 8, "40%", "20%", "40%", "45%", "40%", "35%", "15%", "35%", "40%", "E+_UP", "E", "E", "E", "E", "D_UP", "E_DOWN$White Magic Avo +20", "E", "E", "E_DOWN", "E_DOWN", "Skill requirement: Authority (B with no support, reduced to C+, C, D+ and D with each rank up to B+)._Stat requirement: Cha (25 with no support, reduced in 5 with each support rank down to 5 at B+).");
        insertCharacter(db, "Edelgard", "edelgard", "she/her", "Black Eagles", 17, "June 22", "Garland Moon 22_Imperial Year 1162", "Minor Crest of Seiros_Major Crest of Flames", 29, 13, 6, 5, 8, 5, 6, 4, 10, "40%", "55%", "45%", "45%", "40%", "30%", "35%", "35%", "60%", "E+_UP", "E", "D_UP", "E_DOWN", "E", "E$Black Magic Crit +10", "E_DOWN", "D_UP", "D_UP", "E", "E", "Only available in Crimson Flower (select the Black Eagles house and choose to side with Edelgard during Chapter 11)");
        insertCharacter(db, "Felix", "felix", "he/him", "Blue Lions", 17, "February 20", "Pegasus Moon 20_Imperial Year 1163", "Major Crest of Fraldarius", 26, 10, 5, 6, 9, 5, 5, 3, 5, "45%", "55%", "30%", "45%", "55%", "40%", "30%", "20%", "30%", "D_UP", "E", "E", "E+_UP", "E+_UP", "E_DOWN$Black Magic Crit +10", "E", "E_DOWN", "E", "E", "E", "Skill requirement: Sword (B+ with no support, reduced to B, C+ and D+ with each rank up to B)._Stat requirement: Spd (15 with no support, reduced in 3 with each support rank down to 3 at B+).");
        insertCharacter(db, "Ferdinand", "ferdinand", "he/him", "Black Eagles", 17, "April 30", "Great Tree Moon 30_Imperial Year 1162", "Minor Crest of Cichol", 28, 8, 5, 6, 8, 6, 6, 2, 7, "50%", "45%", "20%", "40%", "50%", "40%", "35%", "20%", "40%", "E+_UP", "D_UP", "E+_UP", "E", "E", "E", "E", "E", "E$Seal Speed", "D_UP", "E", "Skill requirement: Heavy Armor (C, C, D+, D and E+ with no rank, C, C+, B and B+ support, respectively)._Stat requirement: Dex (10 with no support, reduced in 2 with each support rank down to 2 at B+)");
        insertCharacter(db, "Flayn", "flayn", "she/her", "Knights of Seiros", 0, "July 12", "Blue Sea Moon 12", "Major Crest of Cethleann", 28, 8, 15, 11, 9, 8, 7, 16, 15, "25%", "25%", "55%", "45%", "35%", "15%", "25%", "50%", "45%", "E", "C+_UP", "E", "E", "E", "E$Seal Magic", "C+_UP", "E", "E_DOWN", "E_DOWN", "D", "Automatically joins in Chapter 7. Leaves if the player selects Crimson Flower in Chapter 11");
        insertCharacter(db, "Gilbert", "gilbert", "he/him", "Knights of Seiros", 56, "January 26", "Guardian Moon 26_Imperial Year 1124", "None", 56, 26, 9, 22, 8, 10, 19, 7, 20, "55%", "45%", "20%", "45%", "30%", "15%", "45%", "10%", "35%", "E", "B+_UP", "B+_UP", "E", "E", "E", "E", "D+", "B_UP", "C_UP", "E", "Only available in Azure Moon (Blue Lions route). He joins automatically on the third turn of Chapter 13");
        insertCharacter(db, "Hanneman", "hanneman", "he/him", "Knights of Seiros", 51, "February 8", "Pegasus Moon 8_Imperial Year 1129", "Minor Crest of Indech", 32, 10, 19, 12, 9, 9, 7, 15, 11, "40%", "30%", "55%", "45%", "20%", "35%", "25%", "40%", "35%", "E", "E", "E", "C+_UP", "E", "B_UP", "E", "D", "E_DOWN", "D_UP", "E_DOWN", "Available from Chapter 8 to the end of the Academy Phase._Level requirement with no support: 15 (reduced in 3 with each support rank down to 3 at B+).");
        insertCharacter(db, "Hapi", "hapi", "she/her", "Ashen Wolves", 20, "January 15", "Guardian Moon 15_Imperial Year 1159", "Major Crest of Timotheos", 27, 7, 12, 9, 7, 4, 4, 8, 5, "35%", "35%", "45%", "45%", "40%", "20%", "15%", "45%", "25%", "E", "D", "E$Exhaustive Strike", "E", "E_DOWN", "D+_UP", "E", "E_DOWN", "E_DOWN", "D_UP", "E_UP", "Can be recruited in the main campaign after completing Chapter 5 of Cindered Shadows.");
        insertCharacter(db, "Hilda", "hilda", "she/her", "Golden Deer", 18, "February 3", "Pegasus Moon 3_Imperial Year 1162", "Minor Crest of Goneril", 29, 10, 5, 5, 8, 6, 6, 3, 7, "50%", "45%", "25%", "30%", "50%", "35%", "35%", "20%", "50%", "E", "E+_UP", "D_UP", "E", "E", "E", "E_DOWN", "E_DOWN", "E$Seal Speed", "E", "E", "Skill requirement: Axe (C, C, D+, D and E+ with no rank, C, C+, B and B+ support, respectively)._Stat requirement: Cha (30 with no support, reduced in 6 with each support rank down to 6 at B+)._Not available in Crimson Flower._Can only be recruited in Chapter 12 in Silver Snow.");
        insertCharacter(db, "Hubert", "hubert", "he/him", "Black Eagles", 20, "April 17", "Great Tree Moon 17_Imperial Year 1160", "None", 22, 6, 12, 6, 7, 6, 4, 7, 6, "35%", "30%", "55%", "45%", "45%", "35%", "25%", "40%", "35%", "E", "E$Frozen Lance", "E_DOWN", "E+_UP", "E", "D_UP", "E_DOWN", "E+_UP", "E", "E", "E_DOWN", "Only available in Crimson Flower (select the Black Eagles house and choose to side with Edelgard during Chapter 11).");
        insertCharacter(db, "Ignatz", "ignatz", "he/him", "Golden Deer", 17, "March 14", "Lone Moon 14_Imperial Year 1163", "None", 25, 8, 5, 7, 8, 8, 4, 6, 4, "35%", "35%", "30%", "50%", "50%", "55%", "25%", "35%", "25%", "E+_UP", "E", "E", "D_UP", "E", "E$Seal Strength", "E", "E+_UP", "E", "E", "E_DOWN", "Skill requirement: Authority (B with no support, reduced to C+, C, D+ and D with each rank up to B+)._Stat requirement: Dex (10 with no support, reduced in 2 with each support rank down to 2 at B+).");
        insertCharacter(db, "Ingrid", "ingrid", "she/her", "Blue Lions", 17, "January 4", "Guardian Moon 4_Imperial Year 1163", "Minor Crest of Daphnel", 27, 8, 6, 6, 8, 6, 6, 8, 8, "40%", "35%", "35%", "40%", "60%", "45%", "30%", "40%", "45%", "E+_UP", "D_UP", "E", "E", "E", "E", "E", "E", "E", "D_UP", "D_UP", "Skill requirement: Flying (D, D, D, E+ and E+ with no rank, C, C+, B and B+ support, respectively)._Stat requirement: Dex (15 with no support, reduced in 3 with each support rank down to 3 at B+).");
        insertCharacter(db, "Jeritza", "jeritza", "he/him", "Knights of Seiros", 21, "March 4", "Lone Moon 4_Imperial Year 1158", "Minor Crest of Lamine", 48, 29, 18, 15, 25, 12, 23, 15, 10, "50%", "50%", "35%", "35%", "60%", "30%", "40%", "25%", "25%", "B+_UP", "A+_UP", "C+", "D", "C+_UP", "D", "E_DOWN", "B_DOWN", "E", "A_UP", "E$Darting Blow", "After version 1.1.0, he joins automatically in Chapter 13 of Crimson Flower._Not available in any other route.");
        insertCharacter(db, "Leonie", "leonie", "she/her", "Golden Deer", 19, "August 21", "Vendant Rain Moon 21_Imperial Year 1160", "None", 26, 9, 5, 8, 9, 6, 7, 2, 7, "40%", "40%", "20%", "55%", "60%", "40%", "40%", "15%", "40%", "E", "D+_UP", "E", "E+_UP", "E", "E", "E", "E", "E", "E+_UP", "E", "Skill requirement: Lance (C, C, D+, D and E+ with no rank, C, C+, B and B+ support, respectively)._Stat requirement: Str (15 with no support, reduced in 3 with each support rank down to 3 at B+).");
        insertCharacter(db, "Linhardt", "linhardt", "he/him", "Black Eagles", 16, "November 7", "Red Wolf Moon 7_Imperial Year 1163", "Minor Crest of Cethleann", 24, 5, 10, 6, 5, 7, 5, 9, 3, "30%", "30%", "45%", "40%", "40%", "45%", "30%", "45%", "20%", "E", "E", "E_DOWN", "E", "E_DOWN", "E+_UP", "D+_UP", "E", "E", "E", "E", "Skill requirement: Reason (C, C, D+, D and E+ with no rank, C, C+, B and B+ support, respectively)._Stat requirement: Mag (10 with no support, reduced in 2 with each support rank down to 2 at B+).");
        insertCharacter(db, "Lorenz", "lorenz", "he/him", "Golden Deer", 18, "June 13", "Garland Moon 13_Imperial Year 1161", "Minor Crest of Gloucester", 28, 8, 7, 6, 7, 5, 6, 6, 3, "55%", "40%", "40%", "45%", "40%", "25%", "30%", "40%", "35%", "E", "D_UP", "E", "E", "E_DOWN", "E+_UP", "E", "E", "E", "D_UP", "E", "Skill requirement: Reason (C, C, D+, D and E+ with no rank, C, C+, B and B+ support, respectively)._Stat requirement: Cha (20 with no support, reduced in 4 with each support rank down to 4 at B+).");
        insertCharacter(db, "Lysithea", "lysithea", "she/her", "Golden Deer", 15, "February 28", "Pegasus Moon 28_Imperial Year 1165", "Major Crest of Gloucester_Minor Crest of Charon", 22, 4, 11, 7, 7, 4, 3, 4, 5, "20%", "15%", "60%", "60%", "50%", "15%", "10%", "25%", "25%", "E_DOWN$Soulblade", "E_DOWN", "E_DOWN", "E", "E", "D_UP", "E+_UP", "E+_UP", "E_DOWN", "E", "E", "Skill requirement: Faith (B with no support, reduced to C+, C, D+ and D with each rank up to B+)._Stat requirement: Mag (15 with no support, reduced in 3 with each support rank down to 3 at B+).");
        insertCharacter(db, "Manuela", "manuela", "she/her", "Knights of Seiros", 0, "August 3", "Verdant Rain Moon 3", "None", 34, 14, 14, 12, 16, 12, 8, 12, 15, "50%", "35%", "35%", "40%", "60%", "35%", "30%", "25%", "50%", "C+_UP", "E", "E", "E", "E", "E_DOWN", "B_UP", "E", "E", "E", "D_UP", "Available from Chapter 8 to the end of the Academy Phase._Level requirement with no support: 15 (reduced in 3 with each support rank down to 3 at B+).");
        insertCharacter(db, "Marianne", "marianne", "she/her", "Golden Deer", 17, "November 23", "Red Wold Moon 23_Imperial Year 1162", "Minor Crest of The Beast", 23, 5, 11, 6, 7, 6, 4, 8, 7, "35%", "20%", "50%", "40%", "40%", "35%", "15%", "45%", "40%", "E+_UP", "E$Frozen Lance", "E", "E", "E_DOWN", "E", "D+_UP", "E", "E_DOWN", "E_UP", "E_UP", "Skill requirement: Riding (C, C, D+, D and E+ with no rank, C, C+, B and B+ support, respectively)._Stat requirement: Mag (10 with no support, reduced in 2 with each support rank down to 2 at B+).");
        insertCharacter(db, "Mercedes", "mercedes", "she/her", "Blue Lions", 22, "May 27", "Harpstring Moon 27_Imperial Year 1157", "Minor Crest of Lamine", 25, 6, 10, 6, 8, 5, 5, 9, 8, "30%", "25%", "50%", "45%", "40%", "30%", "25%", "45%", "40%", "E_DOWN", "E_DOWN", "E_DOWN", "E$Waning Shot", "E", "E+_UP", "D_UP", "E", "E_DOWN", "E", "E", "Skill requirement: Bow (C, C, D+, D and E+ with no rank, C, C+, B and B+ support, respectively)._Stat requirement: Mag (15 with no support, reduced in 3 with each support rank down to 3 at B+).");
        insertCharacter(db, "Petra", "petra", "she/her", "Black Eagles", 15, "September 7", "Horsebow Moon 7_Imperial Year 1164", "None", 25, 9, 3, 7, 10, 7, 5, 2, 6, "45%", "40%", "25%", "50%", "60%", "45%", "30%", "15%", "35%", "D+_UP", "E", "E+_UP", "E+_UP", "E", "E_DOWN", "E_DOWN", "E", "E", "E", "D_UP", "Skill requirement: Riding (C, C, D+, D and E+ with no rank, C, C+, B and B+ support, respectively)._Stat requirement: Dex (10 with no support, reduced in 2 with each support rank down to 2 at B+).");
        insertCharacter(db, "Raphael", "raphael", "he/him", "Golden Deer", 18, "May 18", "Harpstring Moon 18_Imperial Year 1162", "None", 30, 11, 3, 5, 6, 6, 7, 1, 4, "65%", "50%", "15%", "35%", "15%", "35%", "45%", "10%", "25%", "E", "E", "E+_UP", "E_DOWN", "D_UP", "E_DOWN", "E", "E", "D_UP", "E_DOWN", "E", "Skill requirement: Heavy Armor (C, C, D+, D and E+ with no rank, C, C+, B and B+ support, respectively)._Stat requirement: Str (20 with no support, reduced in 4 with each support rank down to 4 at B+).");
        insertCharacter(db, "Rhea", "rhea", "she/her", "Knights of Seiros", 0, "January 11", "Guardian Moon 11", "Major Crest of Seiros", 46, 17, 19, 13, 14, 13, 14, 19, 37, "45%", "35%", "45%", "40%", "40%", "40%", "30%", "40%", "70%", "A", "D", "E", "E", "B", "A", "A", "A", "E", "E", "E", "Not recruitable");
        insertCharacter(db, "Seteth", "seteth", "he/him", "Knights of Seiros", 0, "December 27", "Ethereal Moon 27", "Major Crest of Cichol", 47, 24, 15, 19, 16, 12, 19, 8, 20, "50%", "45%", "35%", "50%", "50%", "25%", "30%", "25%", "45%", "D+_UP", "B_UP", "B_UP", "E", "E", "E", "E", "B_UP", "E", "E_DOWN", "C_UP", "Automatically joins in Chapter 12. Cannot be recruited in Crimson Flower");
        insertCharacter(db, "Shamir", "shamir", "she/her", "Knights of Seiros", 25, "March 27", "Lone Moon 27_Imperial Year 1154", "None", 33, 17, 8, 16, 14, 14, 12, 8, 10, "35%", "40%", "20%", "55%", "40%", "55%", "20%", "15%", "30%", "E", "C+_UP", "E", "A_UP", "E", "E", "E_DOWN", "D", "E", "E", "E", "Available from Chapter 6 to the end of the Academy Phase._Level requirement with no support: 15 (reduced in 3 with each support rank down to 3 at B+)._Joins automatically in Chapter 12 in Silver Snow");
        insertCharacter(db, "Sylvain", "sylvain", "he/him", "Blue Lions", 19, "June 5", "Garland Moon 5_Imperial Year 1160", "Minor Crest of Gautier", 27, 9, 5, 5, 8, 6, 6, 2, 7, "55%", "45%", "30%", "35%", "50%", "35%", "40%", "25%", "40%", "E", "D_UP", "D_UP", "E_DOWN", "E", "E$Black Magic Avo +20", "E", "E", "E", "D_UP", "E", "Skill requirement: Reason (C, C, D+, D and E+ with no rank, C, C+, B and B+ support, respectively)._Stat requirement: Cha (25 with no support, reduced in 5 with each support rank down to 5 at B+).");
        insertCharacter(db, "Yuri", "yuri", "he/him", "Ashen Wolves", 20, "August 12", "Verdant Rain Moon 12_Imperial Year 1160", "Major Crest of Aubin", 25, 11, 7, 8, 10, 8, 6, 8, 9, "30%", "40%", "35%", "40%", "65%", "45%", "30%", "35%", "50%", "D+_UP", "E_DOWN", "E_DOWN", "E$Deadeye", "E", "E+_UP", "D_UP", "D+_UP", "E", "E_DOWN", "E_DOWN", "Can be recruited in the main campaign after completing Chapter 6 of Cindered Shadows");
        insertCharacter(db, "BylethM", "mbyleth", "Chosen by the player", null, 20, "Chosen by the player", "Chosen by the player_Imperial Year 1159", "Major Crest of Flames", 27, 13, 6, 9, 8, 8, 6, 6, 7, "45%", "45%", "35%", "45%", "45%", "45%", "35%", "30%", "45%", "D+_UP", "E", "E", "E", "E+_UP", "E", "E$White Magic Avo +20", "D_UP", "E", "E", "E", "Player character");
        insertCharacter(db, "BylethF", "fbyleth", "Chosen by the player", null, 20, "Chosen by the player", "Chosen by the player_Imperial Year 1159", "Major Crest of Flames", 27, 13, 6, 9, 8, 8, 6, 6, 7, "45%", "45%", "35%", "45%", "45%", "45%", "35%", "30%", "45%", "D+_UP", "E", "E", "E", "E+_UP", "E", "E$White Magic Avo +20", "D_UP", "E", "E", "E", "Player character");
    }

    private void insertCharacter(SQLiteDatabase db, String name, String portrait,
                                       String pronouns, String faction, int age,
                                       String birthday, String fodlanBirthday, String crest,
                                       int baseStatsHP, int baseStatsStr, int baseStatsMag,
                                       int baseStatsDex, int baseStatsSpd, int baseStatsLck,
                                       int baseStatsDef, int baseStatsRes, int baseStatsCha,
                                       String growthRHP, String growthRStr, String growthRMag,
                                       String growthRDex, String growthRSpd, String growthRLck,
                                       String growthRDef, String growthRRes, String growthRCha,
                                       String skillSword, String skillLance, String skillAxe,
                                       String skillBow, String skillBrawling, String skillReason,
                                       String skillFaith, String skillAuthority,
                                       String skillHeavyArmor, String skillRiding,
                                       String skillFlying, String recruitment) {
        ContentValues characterValues = new ContentValues();
        characterValues.put("name", name);
        characterValues.put("portrait", portrait);
        characterValues.put("pronouns", pronouns);
        characterValues.put("faction", faction);
        characterValues.put("age", age);
        characterValues.put("birthday", birthday);
        characterValues.put("fodlanBirthday", fodlanBirthday);
        characterValues.put("crest", crest);
        characterValues.put("baseStatsHP", baseStatsHP);
        characterValues.put("baseStatsStr", baseStatsStr);
        characterValues.put("baseStatsMag", baseStatsMag);
        characterValues.put("baseStatsDex", baseStatsDex);
        characterValues.put("baseStatsSpd", baseStatsSpd);
        characterValues.put("baseStatsLck", baseStatsLck);
        characterValues.put("baseStatsDef", baseStatsDef);
        characterValues.put("baseStatsRes", baseStatsRes);
        characterValues.put("baseStatsCha", baseStatsCha);
        characterValues.put("growthRatesHP", growthRHP);
        characterValues.put("growthRatesStr", growthRStr);
        characterValues.put("growthRatesMag", growthRMag);
        characterValues.put("growthRatesDex", growthRDex);
        characterValues.put("growthRatesSpd", growthRSpd);
        characterValues.put("growthRatesLck", growthRLck);
        characterValues.put("growthRatesDef", growthRDef);
        characterValues.put("growthRatesRes", growthRRes);
        characterValues.put("growthRatesCha", growthRCha);
        characterValues.put("skillSword", skillSword);
        characterValues.put("skillLance", skillLance);
        characterValues.put("skillAxe", skillAxe);
        characterValues.put("skillBow", skillBow);
        characterValues.put("skillBrawling", skillBrawling);
        characterValues.put("skillReason", skillReason);
        characterValues.put("skillFaith", skillFaith);
        characterValues.put("skillAuthority", skillAuthority);
        characterValues.put("skillHeavyArmor", skillHeavyArmor);
        characterValues.put("skillRiding", skillRiding);
        characterValues.put("skillFlying", skillFlying);
        characterValues.put("recruitment", recruitment);
        db.insert("Characters", null, characterValues);
    }

    private void createAbilitiesTable(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE Abilities ( " +
                "ability TEXT PRIMARY KEY, " +
                "icon TEXT, " +
                "effect TEXT, " +
                "origin TEXT, " +
                "type TEXT);");

        insertLearnedAbilities(db);
        insertPersonalAbilities(db);
        insertBuddingTalentAbilities(db);
        insertClassAbilities(db);
        insertMasteringAbilities(db);
        insertOtherAbilities(db);
        insertEnemiesAbilities(db);
    }

    private void insertPersonalAbilities(SQLiteDatabase db) {
        insertAbility(db, "Professor's Guidance", "professors_guidance",
                "Multiplies this unit's and adjacent allies' experience earned by 1.2.",
                "Byleth's personal ability", "Personal");
        insertAbility(db, "Professor's Guidance+", "professors_guidance_2",
                "Unit deals 2 extra damage during combat. Multiplies this unit's and adjacent allies' experience earned by 1.2.",
                "Byleth's personal ability from Chapter 10 battle onwards", "Personal");
        insertAbility(db, "Imperial Lineage", "imperial_lineage",
                "Multiplies experience earned by 1.2.",
                "Edelgard's personal ability", "Personal");
        insertAbility(db, "Imperial Lineage+", "imperial_lineage_2",
                "If unit takes no action except Wait, grants Res +4 for 1 turn. Multiplies experience earned by 1.2.",
                "Edelgard's personal ability after timeskip", "Personal");
        insertAbility(db, "Royal Lineage", "royal_lineage",
                "Multiplies experience earned by 1.2.",
                "Dimitri's personal ability", "Personal");
        insertAbility(db, "Royal Lineage+", "royal_lineage_2",
                "Grants Avo +20 while unit is at full HP. Multiplies experience earned by 1.2.",
                "Dimitri's personal ability after timeskip", "Personal");
        insertAbility(db, "Leicester Lineage", "leicester_lineage",
                "Multiplies experience earned by 1.2.",
                "Claude's personal ability", "Personal");
        insertAbility(db, "Leicester Lineage+", "leicester_lineage_2",
                "Allows unit to pass through spaces occupied by foes. Multiplies experience earned by 1.2.",
                "Claude's personal ability after timeskip", "Personal");
        insertAbility(db, "Officer Duty", "officer_duty",
                "Grants Mt +5 with gambits.",
                "Hubert's personal ability", "Personal");
        insertAbility(db, "Songstress", "songstress",
                "Adjacent allies recover up to 10% of max HP at the start of each turn.",
                "Dorothea's personal ability", "Personal");
        insertAbility(db, "Confidence", "confidence",
                "Grants Hit/Avo +15 when unit is at full HP.",
                "Ferdinand's personal ability", "Personal");
        insertAbility(db, "Persecution Complex", "persecution_complex",
                "Grants Atk +5 when unit is not at full HP.",
                "Bernadetta's personal ability", "Personal");
        insertAbility(db, "Born Fighter", "born_fighter",
                "Adjacent foes suffer Avo -10 during combat.",
                "Caspar's personal ability", "Personal");
        insertAbility(db, "Catnap", "catnap",
                "If unit takes no action except Wait, recovers up to 10% of max HP.",
                "Linhardt's personal ability", "Personal");
        insertAbility(db, "Hunter's Boon", "hunters_boon",
                "Grants Crit +20 when foe's HP is ≤ 50%.",
                "Petra's personal ability", "Personal");
        insertAbility(db, "Staunch Shield", "staunch_shield",
                "If unit takes no action except Wait, grants Def +4 for 1 turn.",
                "Dedue's personal ability", "Personal");
        insertAbility(db, "Live to Serve", "live_to_serve",
                "When healing an ally with white magic, unit recovers the same amount of HP.",
                "Mercedes's personal ability", "Personal");
        insertAbility(db, "Lone Wolf", "lone_wolf",
                "Unit deals 5 extra damage when no battalion is assigned or when battalion endurance is 0.",
                "Felix's personal ability", "Personal");
        insertAbility(db, "Lockpick", "lockpick",
                "Allows unit to open doors and chests without keys.",
                "Ashe's personal ability", "Personal");
        insertAbility(db, "Perseverance", "perseverance",
                "Use Rally to grant Str +4 to an ally.",
                "Annette's personal ability", "Personal");
        insertAbility(db, "Philanderer", "philanderer",
                "If a female ally is adjacent, unit deals 2 extra damage and takes 2 less damage during combat.",
                "Sylvain's personal ability", "Personal");
        insertAbility(db, "Lady Knight", "lady_knight",
                "Grants Mt +3 and Hit +5 with gambits.",
                "Ingrid's personal ability", "Personal");
        insertAbility(db, "Advocate", "advocate",
                "Adjacent male allies deal 3 extra damage during combat.",
                "Hilda's personal ability", "Personal");
        insertAbility(db, "Distinguished House", "distinguished_house",
                "Unit deals 2 extra damage while in formation with a battalion.",
                "Lorenz's personal ability", "Personal");
        insertAbility(db, "Goody Basket", "goody_basket",
                "Chance to recover up to 10% of max HP at the start of each turn. Trigger % = Lck stat.",
                "Raphael's personal ability", "Personal");
        insertAbility(db, "Mastermind", "mastermind",
                "Doubles skill experience earned in battle.",
                "Lysithea's personal ability\nIncrease sword skill level to E+ for Jeritza", "Personal Learned");
        insertAbility(db, "Watchful Eye", "watchful_eye",
                "Grants Hit +20.",
                "Ignatz's personal ability", "Personal");
        insertAbility(db, "Animal Friend", "animal_friend",
                "Unit recovers up to 20% of max HP at the start of each turn when adjacent to a cavalry or flying ally.",
                "Marianne's personal ability", "Personal");
        insertAbility(db, "Rivalry", "rivalry",
                "If a male ally is adjacent, unit deals 2 extra damage and takes 2 less damage during combat.",
                "Leonie's personal ability", "Personal");
        insertAbility(db, "Infirmary Master", "infirmary_master",
                "Adjacent allies gain Crit Avo +10 during combat.",
                "Manuela's personal ability", "Personal");
        insertAbility(db, "Crest Scholar", "crest_scholar",
                "Use Rally to grant Mag +4 to an ally.",
                "Hanneman's personal ability", "Personal");
        insertAbility(db, "Fighting Spirit", "fighting_spirit",
                "Unit takes 5 less damage when no battalion is assigned or when battalion endurance is 0.",
                "Catherine's personal ability", "Personal");
        insertAbility(db, "Compassion", "compassion",
                "Use Rally to grant Lck +8 to an ally.",
                "Alois's personal ability", "Personal");
        insertAbility(db, "Guardian", "guardian",
                "Adjacent female allies deal 3 extra damage during combat",
                "Seteth's personal ability", "Personal");
        insertAbility(db, "Lily's Poise", "lilys_poise",
                "Adjacent allies take 3 less damage during combat.",
                "Flayn's personal ability", "Personal");
        insertAbility(db, "Veteran Knight", "veteran_knight",
                "Unit takes 2 less damage while in formation with a battalion.",
                "Gilbert's personal ability", "Personal");
        insertAbility(db, "Survival Instinct", "survival_instinct",
                "If unit initiates combat and defeats foe, grants Str/Mag/Dex/Spd +4 for one turn.",
                "Shamir's personal ability", "Personal");
        insertAbility(db, "Aptitude", "aptitude",
                "Makes each stat 20% more likely to increase on level up.",
                "Cyril's personal ability", "Personal");
        insertAbility(db, "Murderous Intent", "murderous_intent",
                "If unit initiates combat, grants Hit +20 during combat.",
                "Jeritza's personal ability", "Personal");
        insertAbility(db, "Business Prosperity", "business_prosperity",
                "Grants Lck +5",
                "Anna's personal ability", "Personal");
        insertAbility(db, "Honorable Spirit", "honorable_spirit",
                "If unit is not near an ally, grants Atk +3 when in combat with a foe one space away.",
                "Yuri's personal ability", "Personal");
        insertAbility(db, "Circadian Beat", "circadian_beat",
                "Grants Str/Mag +3 when indoors and Def/Res +3 when outdoors.",
                "Constance's personal ability", "Personal");
        insertAbility(db, "King of Grappling", "king_of_grappling",
                "Grants Str/Def +6 when HP ≤ 50%.",
                "Balthus's personal ability", "Personal");
        insertAbility(db, "Monstrous Appeal", "monstrous_appeal",
                "Makes all attacks effective against monsters and makes it easier for monsters to target unit.",
                "Hapi's personal ability", "Personal");
        insertAbility(db, "Blade Breaker", "blade_breaker",
                "If unit damages foe, foe suffers Str/Def -6 for 1 turn after combat.",
                "Jeralt's personal ability", "Personal");
        insertAbility(db, "Sacred Power", "sacred_power",
                "Adjacent allies deal 3 extra damage and take 3 less damage during combat.",
                "Rhea's personal ability\nMaster the Enlightened One class", "Personal Master");
    }

    private void insertLearnedAbilities(SQLiteDatabase db) {
        insertAbility(db, "Sword Progress Lv. 1", "sword_prowess_lv_1",
                "Grants Hit +5, Avo +7 and Crit Avo +5 when using a sword.",
                "Increase sword skill level to E+", "Learned");
        insertAbility(db, "Sword Progress Lv. 2", "sword_prowess_lv_2",
                "Grants Hit +6, Avo +10, and Crit Avo +6 when using a sword.",
                "Increase sword skill level to D+", "Learned");
        insertAbility(db, "Sword Progress Lv. 3", "sword_prowess_lv_3",
                "Grants Hit +7, Avo +13, and Crit Avo +7 when using a sword.",
                "Increase sword skill level to C+", "Learned");
        insertAbility(db, "Axebreaker", "axebreaker",
                "Grants Hit/Avo +20 when using a sword against axe users.",
                "Increase sword skill level to B", "Learned");
        insertAbility(db, "Sword Progress Lv. 4", "sword_prowess_lv_4",
                "Grants Hit +8, Avo +16, and Crit Avo +8 when using a sword.",
                "Increase sword skill level to B+", "Learned");
        insertAbility(db, "Sword Progress Lv. 5", "sword_prowess_lv_5",
                "Grants Hit +10, Avo +20, and Crit Avo +10 when using a sword.",
                "Increase sword skill level to A+", "Learned");
        insertAbility(db, "Sword Crit +10", "sword_crit_10",
                "Grants Crit +10 when using a sword.",
                "Increase sword skill level to S\nClass ability for Swordmaster", "Learned Class");
        insertAbility(db, "Swordfaire", "swordfaire",
                "Grants Atk +5 when using a sword.",
                "Increase sword skill level to S+\nClass ability for Hero, Swordmaster, Assassin, Mortal Savant, Enlightened One", "Learned Class");
        insertAbility(db, "Lance Progress Lv. 1", "lance_prowess_lv_1",
                "Grants Hit +6, Avo +6, and Crit Avo +5 when using a lance.",
                "Increase lance skill level to E+", "Learned");
        insertAbility(db, "Lance Progress Lv. 2", "lance_prowess_lv_2",
                "Grants Hit +8, Avo +8, and Crit Avo +6 when using a lance.",
                "Increase lance skill level to D+", "Learned");
        insertAbility(db, "Lance Progress Lv. 3", "lance_prowess_lv_3",
                "Grants Hit +10, Avo +10, and Crit Avo +7 when using a lance.",
                "Increase lance skill level to C+", "Learned");
        insertAbility(db, "Swordbreaker", "swordbreaker",
                "Grants Hit/Avo +20 when using a lance against sword users.",
                "Increase lance skill level to B", "Learned");
        insertAbility(db, "Lance Progress Lv. 4", "lance_prowess_lv_4",
                "Grants Hit +12, Avo +12, and Crit Avo +8 when using a lance.",
                "Increase lance skill level to B+", "Learned");
        insertAbility(db, "Lance Progress Lv. 5", "lance_prowess_lv_5",
                "Grants Hit +15, Avo +15, and Crit Avo +10 when using a lance.",
                "Increase lance skill level to A+", "Learned");
        insertAbility(db, "Lance Crit +10", "lance_crit_10",
                "Grants Crit +10 when using a lance.",
                "Increase lance skill level to S", "Learned");
        insertAbility(db, "Lancefaire", "lancefaire",
                "Grants Atk +5 when using a lance.",
                "Increase lance skill level to S+\nClass ability for Paladin, High Lord, Falcon Knight, Great Knight and Great Lord", "Learned Class");
        insertAbility(db, "Axe Progress Lv. 1", "axe_prowess_lv_1",
                "Grants Hit +7, Avo +5, and Crit Avo +5 when using an axe.",
                "Increase axe skill level to E+", "Learned");
        insertAbility(db, "Axe Progress Lv. 2", "axe_prowess_lv_2",
                "Grants Hit +10, Avo +6, and Crit Avo +6 when using an axe.",
                "Increase axe skill level to D+", "Learned");
        insertAbility(db, "Axe Progress Lv. 3", "axe_prowess_lv_3",
                "Grants Hit +13, Avo +7, and Crit Avo +7 when using an axe.",
                "Increase axe skill level to C+", "Learned");
        insertAbility(db, "Lancebreaker", "lancebreaker",
                "Grants Hit/Avo +20 when using an axe against lance users.",
                "Increase axe skill level to B", "Learned");
        insertAbility(db, "Axe Progress Lv. 4", "axe_prowess_lv_4",
                "Grants Hit +16, Avo +8, and Crit Avo +8 when using an axe.",
                "Increase axe skill level to B+", "Learned");
        insertAbility(db, "Axe Progress Lv. 5", "axe_prowess_lv_5",
                "Grants Hit +20, Avo +10, and Crit Avo +10 when using an axe.",
                "Increase axe skill level to A+", "Learned");
        insertAbility(db, "Axe Crit +10", "axe_crit_10",
                "Grants Crit +10 when using an axe.",
                "Increase axe skill level to S\nClass ability for Warrior", "Learned Class");
        insertAbility(db, "Axefaire", "axefaire",
                "Grants Atk +5 when using an axe.",
                "Increase axe skill level to S+\nClass ability for Fortress Knight, Warrior, Wyvern Rider, Armored Lord, Wyvern Lord, Great Knight, War Master and Emperor", "Learned Class");
        insertAbility(db, "Bow Progress Lv. 1", "bow_prowess_lv_1",
                "Grants Hit +6, Avo +6, and Crit Avo +5 when using a bow.",
                "Increase bow skill level to E+", "Learned");
        insertAbility(db, "Bow Progress Lv. 2", "bow_prowess_lv_2",
                "Grants Hit +8, Avo +8, and Crit Avo +6 when using a bow.",
                "Increase bow skill level to D+", "Learned");
        insertAbility(db, "Bow Progress Lv. 3", "bow_prowess_lv_3",
                "Grants Hit +10, Avo +10, and Crit Avo +7 when using a bow.",
                "Increase bow skill level to C+", "Learned");
        insertAbility(db, "Close Counter", "close_counter",
                "Allows unit to counterattack adjacent foes.",
                "Increase bow skill level to C", "Learned");
        insertAbility(db, "Bow Progress Lv. 4", "bow_prowess_lv_4",
                "Grants Hit +12, Avo +12, and Crit Avo +8 when using a bow.",
                "Increase bow skill level to B+", "Learned");
        insertAbility(db, "Bow Progress Lv. 5", "bow_prowess_lv_5",
                "Grants Hit +15, Avo +15, and Crit Avo +10 when using a bow.",
                "Increase bow skill level to A+", "Learned");
        insertAbility(db, "Bow Crit +10", "bow_crit_10",
                "Grants Crit +10 when using a bow.",
                "Increase bow skill level to S", "Learned");
        insertAbility(db, "Bowfaire", "bowfaire",
                "Grants Atk +5 when using a bow.",
                "Increase bow skill level to S+\nClass ability for Wyvern Master, Barbarossa, Sniper and Bow Knight", "Learned Class");
        insertAbility(db, "Brawling Progress Lv. 1", "brawling_prowess_lv_1",
                "Grants Hit +5, Avo +7, and Crit Avo +5 when brawling.",
                "Increase brawling skill level to E+", "Learned");
        insertAbility(db, "Brawling Progress Lv. 2", "brawling_prowess_lv_2",
                "Grants Hit +6, Avo +10, and Crit Avo +6 when brawling.",
                "Increase brawling skill level to D+", "Learned");
        insertAbility(db, "Brawling Progress Lv. 3", "brawling_prowess_lv_3",
                "Grants Hit +7, Avo +13, and Crit Avo +7 when brawling.",
                "Increase brawling skill level to C+", "Learned");
        insertAbility(db, "Brawling Progress Lv. 4", "brawling_prowess_lv_4",
                "Grants Hit +8, Avo +16, and Crit Avo +8 when brawling.",
                "Increase brawling skill level to B+", "Learned");
        insertAbility(db, "Brawling Progress Lv. 5", "brawling_prowess_lv_5",
                "Grants Hit +10, Avo +20, and Crit Avo +10 when brawling.",
                "Increase brawling skill level to A+", "Learned");
        insertAbility(db, "Brawl Crit +10", "brawl_crit_10",
                "Grants Crit +10 when brawling.",
                "Increase brawling skill level to S", "Learned");
        insertAbility(db, "Fistfaire", "fistfaire",
                "Grants Atk +5 when brawling.",
                "Increase brawling skill level to S+\nClass ability for Grappler, War Master, War Monk and War Cleric", "Learned Class");
        insertAbility(db, "Reason Lv. 1", "reason_lv_1",
                "Grants Hit +7, Avo +5, and Crit Avo +5 when using black or dark magic.",
                "Increase reason skill level to E+", "Learned");
        insertAbility(db, "Reason Lv. 2", "reason_lv_2",
                "Grants Hit +10, Avo +6, and Crit Avo +6 when using black or dark magic.",
                "Increase reason skill level to D+", "Learned");
        insertAbility(db, "Reason Lv. 3", "reason_lv_3",
                "Grants Hit +13, Avo +7, and Crit Avo +7 when using black or dark magic.",
                "Increase reason skill level to C+", "Learned");
        insertAbility(db, "Reason Lv. 4", "reason_lv_4",
                "Grants Hit +16, Avo +8, and Crit Avo +8 when using black or dark magic.",
                "Increase reason skill level to B+", "Learned");
        insertAbility(db, "Reason Lv. 5", "reason_lv_5",
                "Grants Hit +20, Avo +10, and Crit Avo +10 when using black or dark magic.",
                "Increase reason skill level to A+", "Learned");
        insertAbility(db, "Black Magic Range +1", "black_magic_range_1",
                "Increases black magic range by 1.",
                "Increase reason skill level to S (except for Edelgard, Hubert, Lysithea and Hapi)\nClass ability for Valkyrie", "Learned Unique Class");
        insertAbility(db, "Dark Magic Range +1", "dark_magic_range_1",
                "Increases dark magic range by 1.",
                "Increase reason skill level to S for Edelgard, Hubert, Lysithea or Hapi\nClass ability for Valkyrie", "Learned Unique Class");
        insertAbility(db, "Black Tomefaire", "black_tomefaire",
                "Grants Atk +5 when using black magic.",
                "Increase reason skill level to S+ (except for Edelgard, Hubert, Lysithea and Hapi)\nClass ability for Warlock, Mortal Savant, Dark Knight and Dark Flier", "Learned Unique Class");
        insertAbility(db, "Dark Tomefaire", "dark_tomefaire",
                "Grants Atk +5 when using dark magic.",
                "Increase reason skill level to S+ for Edelgard, Hubert, Lysithea or Hapi\nClass ability for Dark Knight", "Learned Unique Class");
        insertAbility(db, "Faith Lv. 1", "faith_lv_1",
                "Grants Hit +5, Avo +7, and Crit Avo +5 when using white magic.",
                "Increase faith skill level to E+", "Learned");
        insertAbility(db, "Faith Lv. 2", "faith_lv_2",
                "Grants Hit +6, Avo +10, and Crit Avo +6 when using white magic.",
                "Increase faith skill level to D+", "Learned");
        insertAbility(db, "Faith Lv. 3", "faith_lv_3",
                "Grants Hit +7, Avo +13, and Crit Avo +7 when using white magic.",
                "Increase faith skill level to C+", "Learned");
        insertAbility(db, "Faith Lv. 4", "faith_lv_4",
                "Grants Hit +8, Avo +16, and Crit Avo +8 when using white magic.",
                "Increase faith skill level to B+", "Learned");
        insertAbility(db, "Faith Lv. 5", "faith_lv_5",
                "Grants Hit +10, Avo +20, and Crit Avo +10 when using white magic.",
                "Increase faith skill level to A+", "Learned");
        insertAbility(db, "White Magic Range +1", "white_magic_range_1",
                "Increases White Magic range by 1 for attacks that damage foes.",
                "Increase faith skill level to S", "Learned");
        insertAbility(db, "White Tomefaire", "white_tomefaire",
                "Grants Atk +5 when using white magic.",
                "Increase faith skill level to S+\nClass ability for Holy Knight", "Learned Class");
        insertAbility(db, "Authority Lv. 1", "authority_lv_1",
                "Grants Mt +2 with gambits.",
                "Increase authority skill level to E+", "Learned");
        insertAbility(db, "Authority Lv. 2", "authority_lv_1",
                "Grants Mt +4 with gambits.",
                "Increase authority skill level to D+", "Learned");
        insertAbility(db, "Authority Lv. 3", "authority_lv_1",
                "Grants Mt +6 with gambits.",
                "Increase authority skill level to C+", "Learned");
        insertAbility(db, "Authority Lv. 4", "authority_lv_1",
                "Grants Mt +8 with gambits.",
                "Increase authority skill level to B+", "Learned");
        insertAbility(db, "Authority Lv. 5", "authority_lv_1",
                "Grants Mt +10 with gambits.",
                "Increase authority skill level to A+", "Learned");
        insertAbility(db, "Rally Magic", "rally_magic",
                "Use Rally to grant Mag +4 to an ally.",
                "Increase authority skill level to D for Hubert, Ingrid or Constance", "Learned Unique");
        insertAbility(db, "Rally Speed", "rally_speed",
                "Use Rally to grant Spd +4 to an ally.",
                "Learned by Ignatz at authority skill level D, Annette at C+ and Hubert at S", "Learned Unique");
        insertAbility(db, "Rally Strength", "rally_strength",
                "Use Rally to grant Str +4 to an ally.",
                "Learned by Raphael at authority skill level C, Ignatz at S, Alois at D and Balthus at D", "Learned Unique");
        insertAbility(db, "Rally Defense", "rally_defense",
                "Use Rally to grant Def +4 to an ally.",
                "Learned by Seteth and Gilbert at authority skill level D", "Learned Unique");
        insertAbility(db, "Rally Resistance", "rally_resistance",
                "Use Rally to grant Res +4 to an ally.",
                "Learned by Constance at authority skill level C, Hubert at C+, Annette at D and Seteth at S", "Learned Unique");
        insertAbility(db, "Rally Movement", "rally_movement",
                "Use Rally to grant Mv +1 to an ally.",
                "Learned by Byleth and Annette at authority skill level S", "Learned Unique");
        insertAbility(db, "Rally Dexterity", "rally_dexterity",
                "Use Rally to grant Dex +8 to an ally.",
                "Learned by Ferdinand at authority skill level D, Ignatz at C+ and Hapi at D", "Learned Unique");
        insertAbility(db, "Rally Luck", "rally_luck",
                "Use Rally to grant Lck +8 to an ally.",
                "Learned by Flayn and Anna at authority skill level D", "Learned Unique");
        insertAbility(db, "Rally Charm", "rally_charm",
                "Use Rally to grant Cha +8 to an ally.",
                "Learned by Edelgard at authority skill level S, Dimitri at S, Claude at S, Dorothea at D and Manuela at D", "Learned Unique");
        insertAbility(db, "Battalion Vantage", "battalion_vantage",
                "When foe initiates combat, unit still attacks first if battalion endurance is ≤ 1/3.",
                "Increase authority skill level to C for Byleth, Edelgard, Felix, Sylvain, Lorenz, Catherine, Yuri and Anna\nIncrease authority skill level to A for Dimitri and Ignatz", "Learned Unique");
        insertAbility(db, "Battalion Wrath", "battalion_wrath",
                "If foe initiates combat while unit’s battalion endurance is ≤ 1/3, grants Crit +50.",
                "Increase authority skill level to C for Hubert, Bernadetta, Caspar, Petra, Dimitri, Dedue, Hilda, Raphael, Seteth, Alois, Gilbert, Hapi and Jeritza\nIncrease authority skill level to A for Annette and Claude", "Learned Unique");
        insertAbility(db, "Battalion Desperation", "battalion_desperation",
                "If unit initiates combat when battalion endurance is ≤ 1/3, unit’s follow-up attack (if possible) occurs before foe’s counterattack.",
                "Increase authority skill level to C for Dorothea, Ferdinand, Ashe, Ingrid, Claude, Lysithea, Ignatz, Leonie, Hanneman, Cyril and Shamir\nIncrease authority skill level to B for Balthus\nIncrease authority skill level to A for Byleth, Hubert and Seteth", "Learned Unique");
        insertAbility(db, "Battalion Renewal", "battalion_renewal",
                "Unit recovers up to 30% of max HP at the start of each turn while battalion endurance is ≤ 1/3.",
                "Increase authority skill level to C for Linhardt, Mercedes, Annette, Marianne, Manuela and Flayn\nIncrease authority skill level to A for Edelgard", "Learned Unique");
        insertAbility(db, "Model Leader", "model_leader",
                "Doubles experience earned for battalions.",
                "Increase authority skill level to C+ for Byleth, Edelgard, Dimitri, Claude, Yuri and Seteth", "Learned Unique");
        insertAbility(db, "Defensive Tactics", "defensive_tactics",
                "Battalion endurance takes half damage.",
                "Increase authority skill level to B", "Learned");
        insertAbility(db, "Offensive Tactics", "offensive_tactics",
                "Grants Mt +5 and Hit +20 with gambits.",
                "Increase authority skill level to S+", "Learned");
        insertAbility(db, "Weight -3", "weight_3",
                "Reduces total equipment weight by 3.",
                "Increase heavy armor skill level to C", "Learned");
        insertAbility(db, "Weight -5", "weight_5",
                "Reduces total equipment weight by 5.",
                "Increase heavy armor skill level to A+\nClass ability for Fortress Knight", "Learned Class");
        insertAbility(db, "Armored Effect Null", "armored_effect_null",
                "Nullifies any extra effectiveness against armored units.",
                "Increase heavy armor skill level to S+", "Learned");
        insertAbility(db, "Dexterity +4", "dexterity_4",
                "Increases Dex by 4.",
                "Increase riding skill level to C", "Learned");
        insertAbility(db, "Movement +1", "movement_1",
                "Increases Mv by 1.",
                "Increase riding skill level to A+", "Learned");
        insertAbility(db, "Cavalry Effect Null", "cavalry_effect_null",
                "Nullifies any extra effectiveness against cavalry units.",
                "Increase riding skill level to S+", "Learned");
        insertAbility(db, "Alert Stance", "alert_stance",
                "If unit takes no action except Wait, grants Avo +15 for 1 turn.",
                "Increase flying skill level to B", "Learned");
        insertAbility(db, "Alert Stance +", "alert_stance_2",
                "If unit takes no action except Wait, grants Avo +30 for 1 turn.",
                "Increase flying skill level to A+", "Learned");
        insertAbility(db, "Flying Effect Null", "flying_effect_null",
                "Nullifies any extra effectiveness against flying units.",
                "Increase flying skill level to S+", "Learned");
    }

    private void insertBuddingTalentAbilities(SQLiteDatabase db) {
        insertAbility(db, "Black Magic Crit +10", "black_magic_crit_10",
                "Grants Crit +10 when using black magic.",
                "Balthus, Felix and Edelgard's budding talent in reason", "Talent");
        insertAbility(db, "Black Magic Avo +20", "black_magic_avo_20",
                "Grants Avo +20 when using black magic.",
                "Sylvain's budding talent in reason", "Talent");
        insertAbility(db, "White Magic Avo +20", "white_magic_avo_20",
                "Grants Avo +20 when using white magic.",
                "Byleth and Dorothea's budding talent in faith", "Talent");
        insertAbility(db, "Darting Blow", "darting_blow",
                "If unit initiates combat, grants AS +6 during combat.",
                "Jeritza's budding talent in flying\nMaster the Pegasus Knight class", "Talent Master");
        insertAbility(db, "Seal Strength", "seal_strength",
                "If unit damages foe during combat, foe suffers Str -6 for 1 turn after combat.",
                "Ignatz's budding talent in reason", "Talent");
        insertAbility(db, "Seal Magic", "seal_magic",
                "If unit damages foe during combat, foe suffers Mag -6 for 1 turn after combat.",
                "Flayn's budding talent in reason", "Talent");
        insertAbility(db, "Seal Speed", "seal_speed",
                "If unit damages foe during combat, foe suffers Spd -6 for 1 turn after combat.",
                "Ferdinand and Hilda's budding talent in Heavy Armor", "Talent");
        insertAbility(db, "Seal Movement", "seal_movement",
                "If unit damages foe during combat, foe suffers Mv -1 for 1 turn after combat.",
                "Dimitri's budding talent in riding", "Talent");
        insertAbility(db, "Pass", "pass",
                "Allows unit to pass through spaces occupied by foes.",
                "Bernadetta and Anna's budding talent in riding", "Talent");
    }

    private void insertClassAbilities(SQLiteDatabase db) {
        insertAbility(db, "Avo +10", "avo_10",
                "Increases Avo by 10.",
                "Class ability for Pegasus Knight, Falcon Knight and Wyvern Lord", "Class");
        insertAbility(db, "Crit +20", "crit_20",
                "Increases Crit by 20.",
                "Class ability for War Master", "Class");
        insertAbility(db, "Fiendish Blow", "fiendish_blow",
                "If unit initiates combat, grants Mag +6 during combat",
                "Class ability for Dark Bishop\nMaster the Master Mage class", "Class Master");
        insertAbility(db, "Charm", "charm",
                "Adjacent allies deal 3 extra damage during combat.",
                "Class ability for Lord, Armored Lord, Emperor, Great Lord, High Lord, Wyvern Master and Barbarossa", "Class");
        insertAbility(db, "Heartseeker", "heartseeker",
                "Adjacent foes suffer Avo -20 during combat.",
                "Class ability for Dark Mage and Dark Bishop", "Class");
        insertAbility(db, "Fire", "fire",
                "Allows unit to cast Fire. If Fire is already available, then unit can cast it twice as often.",
                "Class ability for Mage", "Class");
        insertAbility(db, "Miasma Δ", "miasma",
                "Allows unit to cast Miasma Δ. If Miasma Δ is already available, then unit can cast it twice as often.",
                "Class ability for Dark Mage and Dark Bishop", "Class");
        insertAbility(db, "Heal", "heal",
                "Allows unit to cast Heal. If Heal is already available, then unit can cast it twice as often.",
                "Class ability for Priest, War Monk and War Cleric", "Class");
        insertAbility(db, "Terrain Resistance", "terrain_resistance",
                "Nullifies damage from terrain.",
                "Class ability for Paladin, Bishop, Holy Knight and Enlightened One", "Class");
        insertAbility(db, "Vantage", "vantage",
                "When foe initiates combat, unit still attacks first if HP is ≤ 50%.",
                "Class ability for Hero\nMaster the Mercenary class", "Class Master");
        insertAbility(db, "Dance", "dance",
                "Use Dance to allow an ally to move again.",
                "Class ability for Dancer", "Class");
        insertAbility(db, "Steal", "steal",
                "Allows unit to steal a non-weapon item from a foe with a lower Spd stat.",
                "Class ability for Thief\nMaster the Thief class", "Class Master");
        insertAbility(db, "Locktouch", "locktouch",
                "Allows unit to open doors and chests without keys.",
                "Class ability for Thief, Assassin and Trickster", "Class");
        insertAbility(db, "Stealth", "stealth",
                "Makes it more difficult for foes to target unit.",
                "Class ability for Assassin and Trickster", "Class");
        insertAbility(db, "Canto", "canto",
                "Allows unit to move again after completing certain actions, if there is movement remaining.",
                "Class ability for Cavalier, Paladin, Great Knight, Pegasus Knight, Falcon Knight, Wyvern Rider, Wyvern Lord, Bow Knight, Holy Knight, Dark Knight, Dark Flier, Valkyrie, Death Knight, Wyvern Master and Barbarossa", "Class");
        insertAbility(db, "Bowrange +1", "bowrange_1",
                "Increases bow range by 1.",
                "Class ability for Archer and Sniper", "Class");
        insertAbility(db, "Bowrange +2", "bowrange_2",
                "Increases bow range by 2.",
                "Class ability for Bow Knight", "Class");
        insertAbility(db, "Unarmed Combat", "unarmed_combat",
                "Allows unit to fight without a weapon.",
                "Class ability for Brawler, Grappler, War Monk and War Cleric\nMaster the Brawler class", "Class Master");
        insertAbility(db, "Black Magic Uses x2", "black_magic_uses_x2",
                "Doubles the number of uses for black magic.",
                "Class ability for Warlock and Gremory", "Class");
        insertAbility(db, "Dark Magic Uses x2", "dark_magic_uses_x2",
                "Doubles the number of uses for dark magic.",
                "Class ability for Gremory", "Class");
        insertAbility(db, "White Magic Uses x2", "white_magic_uses_x2",
                "Doubles the number of uses for white magic.",
                "Class ability for Bishop and Gremory", "Class");
        insertAbility(db, "White Magic Heal +5", "white_magic_heal_5",
                "Heal 5 extra HP when using white magic.",
                "Class ability for Priest", "Class");
        insertAbility(db, "White Magic Heal +10", "white_magic_heal_10",
                "Heal 10 extra HP when using white magic.",
                "Class ability for Bishop", "Class");
        insertAbility(db, "Lucky Seven", "lucky_seven",
                "Each turn, grants +5 to one of the following stats: Str, Mag, Spd, Def, Res, Hit, or Avo.",
                "Class ability for Trickster", "Class");
        insertAbility(db, "Transmute", "transmute",
                "If unit is hit with a magic attack during enemy phase, grants +3 to all stats until next player phase ends.",
                "Class ability for Dark Flier\nMaster the Dark Flier class", "Class Master");
    }

    private void insertMasteringAbilities(SQLiteDatabase db) {
        insertAbility(db, "Brawl Avo +20", "brawl_avo_20",
                "Grants Avo +20 when brawling.",
                "Master the War Monk or the War Cleric class", "Master");
        insertAbility(db, "HP +5", "hp_5",
                "Increases maximum HP by 5.",
                "Master the Noble or Commoner class", "Master");
        insertAbility(db, "Strength +2", "strength_2",
                "Increases Str by 2.",
                "Master the Fighter class", "Master");
        insertAbility(db, "Magic +2", "magic_2",
                "Increases Mag by 2.",
                "Master the Monk class", "Master");
        insertAbility(db, "Speed +2", "speed_2",
                "Increases Spd by 2.",
                "Master the Myrmidon class", "Master");
        insertAbility(db, "Pomp & Circumstance", "pomp__circumstance",
                "Grants Lck & Cha +4.",
                "Master the Armored Lord, the Wyvern Master or the High Lord class", "Master");
        insertAbility(db, "Defense +2", "defense_2",
                "Increases Def by 2.",
                "Master the Soldier class", "Master");
        insertAbility(db, "Resistance +2", "resistance_2",
                "Increases Res by 2.",
                "Master the Lord class", "Master");
        insertAbility(db, "Hit +20", "hit_20",
                "Increases Hit by 20.",
                "Master the Archer class", "Master");
        insertAbility(db, "Defiant Str", "defiant_str",
                "Grants Str +8 when HP is ≤ 25%.",
                "Master the Hero class", "Master");
        insertAbility(db, "Defiant Mag", "defiant_mag",
                "Grants Mag +8 when HP is ≤ 25%.",
                "Master the Gremory class", "Master");
        insertAbility(db, "Defiant Spd", "defiant_spd",
                "Grants Spd +8 when HP is ≤ 25%.",
                "Master the Bow Knight class", "Master");
        insertAbility(db, "Defiant Def", "defiant_def",
                "Grants Def +8 when HP is ≤ 25%.",
                "Master the Great Knight class", "Master");
        insertAbility(db, "Defiant Res", "defiant_res",
                "Grants Res +8 when HP is ≤ 25%.",
                "Master the Holy Knight class", "Master");
        insertAbility(db, "Defiant Avo", "defiant_avo",
                "Grants Avo +30 when HP is ≤ 25%.",
                "Master the Falcon Knight class", "Master");
        insertAbility(db, "Defiant Crit", "defiant_crit",
                "Grants Crit +50 when HP is ≤ 25%.",
                "Master the Wyvern Lord class", "Master");
        insertAbility(db, "Death Blow", "death_blow",
                "If unit initiates combat, grants Str +6 during combat.",
                "Master the Brigand class", "Master");
        insertAbility(db, "Armored Blow", "armored_blow",
                "If unit initiates combat, grants Def +6 during combat.",
                "Master the Armored Knight class", "Master");
        insertAbility(db, "Warding Blow", "warding_blow",
                "If unit initiates combat, grants Res +6 during combat.",
                "Master the Mortal Savant class", "Master");
        insertAbility(db, "Seal Defense", "seal_defense",
                "If unit damages foe during combat, foe suffers Def -6 for 1 turn after combat.",
                "Master the Wyvern Rider class", "Master");
        insertAbility(db, "Seal Resistance", "seal_resistance",
                "If unit damages foe during combat, foe suffers Res -6 for 1 turn after combat.",
                "Master the Dark Knight class", "Master");
        insertAbility(db, "Bowbreaker", "bowbreaker",
                "Grants Hit/Avo +20 when using magic against bow users.",
                "Master the Warlock class", "Master");
        insertAbility(db, "Tomebreaker", "tomebreaker",
                "Grants Hit/Avo +20 when brawling against magic users.",
                "Master the Grappler class", "Master");
        insertAbility(db, "Renewal", "renewal",
                "Unit recovers up to 20% of max HP at the start of each turn.",
                "Master the Bishop class", "Master");
        insertAbility(db, "Poison Strike", "poison_strike",
                "If unit initiates combat and lands a hit, targeted foe loses up to 20% of max HP after combat.",
                "Master the Dark Mage class", "Master");
        insertAbility(db, "Miracle", "miracle",
                "Chance to survive lethal damage with 1 HP, if HP is > 1. Trigger % = Lck stat.",
                "Master the Priest class", "Master");
        insertAbility(db, "Lifetaker", "lifetaker",
                "Unit recovers HP equal to 50% of damage dealt after defeating a foe.",
                "Master the Dark Bishop class", "Master");
        insertAbility(db, "Lethality", "lethality",
                "Chance to instantly kill a foe when dealing damage. Trigger % = 0.25×Dex.",
                "Master the Assassin class", "Master");
        insertAbility(db, "Pavise", "pavise",
                "Chance to reduce sword/lance/axe/brawling damage by half. Trigger % = Dex stat.",
                "Master the Fortress Knight class", "Master");
        insertAbility(db, "Aegis", "aegis",
                "Chance to reduce bow/magic damage by half. Trigger % = Dex stat.",
                "Master the Paladin class", "Master");
        insertAbility(db, "Special Dance", "special_dance",
                "When using the Dance ability, grant Dex/Spd/Lck +4 to target ally.",
                "Master the Dance class", "Master");
        insertAbility(db, "Desperation", "desperation",
                "If unit initiates combat with HP ≤ 50%, unit’s follow-up attack (if possible) occurs before foe’s counterattack.",
                "Master the Cavalier class", "Master");
        insertAbility(db, "Quick Riposte", "quick_riposte",
                "If foe initiates combat while unit’s HP is ≥ 50%, unit makes guaranteed follow-up attack.",
                "Master the War Master class", "Master");
        insertAbility(db, "Wrath", "wrath",
                "If foe initiates combat while unit’s HP is ≤ 50%, grants Crit +50.",
                "Master the Warrior class", "Master");
        insertAbility(db, "Counterattack", "counterattack",
                "Allows unit to counterattack regardless of distance to attacker.",
                "Master the Death Knight class\nRetribution gambit\nChalice of Beginnings", "Master");
        insertAbility(db, "Duelist's Blow", "duelists_blow",
                "If unit initiates combat, grants Avo +20 during combat.",
                "Master the Trickster class", "Master");
        insertAbility(db, "Uncanny Blow", "uncanny_blow",
                "If unit initiates combat, grant Hit +30 during combat.",
                "Master the Valkyrie class", "Master");
    }

    private void insertOtherAbilities(SQLiteDatabase db) {
        insertAbility(db, "Sword Avo +20", "sword_avo_20",
                "Grants Avo +20 when using a sword.",
                "Win the White Heron Cup", "Other");
        insertAbility(db, "Missing Number", "missing_number",
                "Missing Number. The icon is based on Wary Fighter.",
                "This ability does not exist", "Other");
    }

    private void insertEnemiesAbilities(SQLiteDatabase db) {
        insertAbility(db, "White Magic Crit +10", "white_magic_crit_10",
                "Grants Crit +10 when using white magic.",
                "Enemy only", "Enemy");
        insertAbility(db, "Dark Magic Crit +10", "dark_magic_crit_10",
                "Grants Crit +10 when using dark magic.",
                "Enemy only", "Enemy");
        insertAbility(db, "Lance Avo +20", "lance_avo_20",
                "Grants Avo +20 when using a lance.",
                "Enemy only", "Enemy");
        insertAbility(db, "Axe Avo +20", "axe_avo_20",
                "Grants Avo +20 when using an axe.",
                "Enemy only", "Enemy");
        insertAbility(db, "Bow Avo +20", "bow_avo_20",
                "Grants Avo +20 when using a bow.",
                "Enemy only", "Enemy");
        insertAbility(db, "Dark Magic Avo +20", "dark_magic_avo_20",
                "Grants Avo +20 when using dark magic.",
                "Enemy only", "Enemy");
        insertAbility(db, "Swordbreaker+", "swordbreaker_2",
                "Grants Hit/Avo +30 when using a lance against sword users.",
                "Enemy only", "Enemy");
        insertAbility(db, "Lancebreaker+", "lancebreaker_2",
                "Grants Hit/Avo +30 when using an axe against sword users.",
                "Enemy only", "Enemy");
        insertAbility(db, "Axebreaker+", "axebreaker_2",
                "Grants Hit/Avo +30 when using a sword against sword users.",
                "Enemy only", "Enemy");
        insertAbility(db, "Fistbreaker", "fistbreaker",
                "Grants Hit/Avo +30 when using a bow against brawlers.",
                "Enemy only", "Enemy");
        insertAbility(db, "Poison", "poison",
                "Chance to inflict poison on foe when dealing damage. Trigger % = Dex stat.",
                "Enemy only", "Enemy");
        insertAbility(db, "Paragon", "paragon",
                "Doubles experience earned.",
                "Enemy only", "Enemy");
        insertAbility(db, "Discipline", "discipline",
                "Doubles skill experience earned in battle.",
                "Enemy only", "Enemy");
        insertAbility(db, "Unsealable Magic", "unsealable_magic",
                "Prevents unit from being silenced.",
                "Enemy only", "Enemy");
        insertAbility(db, "Immune Status", "immune_status",
                "Nullifies status effects and debuffs.",
                "Enemy only", "Enemy");
        insertAbility(db, "General", "general",
                "Nullifies instant death effects and halves damage from enemy gambits.",
                "Enemy/NPC only", "Enemy");
        insertAbility(db, "Commander", "commander",
                "Nullifies instant death effects, status effects, and movement effects, and greatly reduces damage from enemy gambits.",
                "Enemy/NPC only", "Enemy");
        insertAbility(db, "Infinite Magic", "infinite_magic",
                "Removes the limitation on the number of times magic can be used.",
                "Enemy only", "Enemy");
        insertAbility(db, "Magic Bind", "magic_bind",
                "If unit lands a hit, targeted foe is unable to use magic for 1 turn.",
                "Enemy only", "Enemy");
        insertAbility(db, "Infantry Effect Null", "infantry_effect_null",
                "Cancels effectiveness against infantry units.",
                "Enemy only", "Enemy");
        insertAbility(db, "Dragon Effect Null", "dragon_effect_null",
                "Cancels effectiveness against dragons.",
                "Enemy only", "Enemy");
        insertAbility(db, "Monster Effect Null", "monster_effect_null",
                "Nullifies any extra effectiveness against monsters.",
                "Enemy only", "Enemy");
        insertAbility(db, "Effect Null", "effect_null",
                "Cancels all types of effectiveness.",
                "Enemy only", "Enemy");
        insertAbility(db, "Noncombatant", "noncombatant",
                "Unit cannot be targeted by foes.",
                "Enemy only", "Enemy");
        insertAbility(db, "Cursed Power", "cursed_power",
                "Unit recovers HP on swamp terrain.",
                "Enemy only", "Enemy");
        insertAbility(db, "Anchor", "anchor",
                "Prevents unit from being moved.",
                "Enemy/NPC only", "Enemy");
        insertAbility(db, "Twin Crests", "twin_crests",
                "Allows unit to take two actions in one turn.",
                "Enemy only", "Enemy");
        insertAbility(db, "Ancient Dragon Wrath", "ancient_dragon_wrath",
                "Calculates damage using the lower of foe’s Prt or Rsl.",
                "Enemy only", "Enemy");
        insertAbility(db, "Surging Light", "surging_light",
                "Performs Staggering Blow immediately.",
                "Enemy only", "Enemy");
        insertAbility(db, "Ancient Dragonskin", "ancient_dragonskin",
                "Halves all damage taken, negates some abilities and combat arts, and prevents unit from being moved.",
                "Enemy only", "Enemy");
        insertAbility(db, "Keen Intuition", "keen_intuition",
                "Grants Avo +30 during combat with a foe 2 or more spaces away.",
                "Enemy only", "Enemy");
        insertAbility(db, "Black Magic Uses Up+", "white_magic_uses_up",
                "Quadruples the number of uses for black magic.",
                "Enemy only", "Enemy");
        insertAbility(db, "Dark Magic Uses x4", "dark_magic_uses_x4",
                "Quadruples the number of uses for dark magic.",
                "Enemy only", "Enemy");
        insertAbility(db, "White Magic Uses Up+", "white_magic_uses_up",
                "Quadruples the number of uses for white magic.",
                "Enemy only", "Enemy");
        insertAbility(db, "Mighty King of Legend", "mighty_king_of_legend",
                "Negates 1 attack per turn and strengthens stats while the 10 Elites are present.",
                "Enemy (Nemesis) only", "Enemy");
        insertAbility(db, "10 Elites", "elites",
                "Grants power to the Mighty King of Legend.",
                "Enemy only", "Enemy");
        insertAbility(db, "Agarthan Technology", "agarthan_technology",
                "Adjacent foes deal 3 less damage during combat.",
                "Enemy only", "Enemy");
        insertAbility(db, "Umbral Leech", "umbral_leech",
                "Unit absorbs HP from phantoms.",
                "Umbral Beast only", "Enemy");
        insertAbility(db, "Manifest Phantom", "manifest_phantom",
                "Unit births phantoms.",
                "Umbral Beast only", "Enemy");
        insertAbility(db, "Enhanced Fortitude", "enhanced_fortitude",
                "Halves all damage taken, negates some abilities and combat arts, and prevents unit from being moved.",
                "Umbral Beast only", "Enemy");
        insertAbility(db, "Barrier", "barrier",
                "Reduces all damage dealt to the user by 50%. Grants access to the user's Barrier Abilities. Active if an intact Barrier tile is targeted.",
                "Enemy (monster) only", "Enemy");
        insertAbility(db, "Vital Defense", "vital_defense",
                "Negates enemy critical chance.",
                "Enemy (monster) only", "Enemy");
    }

    private void insertAbility(SQLiteDatabase db, String ability, String icon, String effect,
                               String origin, String type) {
        ContentValues abilityValues = new ContentValues();
        abilityValues.put("ability", ability);
        abilityValues.put("icon", icon);
        abilityValues.put("effect", effect);
        abilityValues.put("origin", origin);
        abilityValues.put("type", type);
        db.insert("Abilities", null, abilityValues);
    }

    private void createCombatArtsTables(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE CombatArtsAllWeaponProficient ( "
                + "art TEXT PRIMARY KEY, "
                + "effect TEXT, "
                + "weapon TEXT, "
                + "skillLevel TEXT, "
                + "dur TEXT, "
                + "mt TEXT, "
                + "hit TEXT, "
                + "avo TEXT, "
                + "crit TEXT, "
                + "range TEXT);");

        db.execSQL("CREATE TABLE CombatArtsCharactersWeaponProficient ( "
                + "art TEXT PRIMARY KEY, "
                + "effect TEXT, "
                + "weapon TEXT, "
                + "skillLevel TEXT, "
                + "dur TEXT, "
                + "mt TEXT, "
                + "hit TEXT, "
                + "avo TEXT, "
                + "crit TEXT, "
                + "range TEXT);");

        db.execSQL("CREATE TABLE CharacterHasCombatArtWeaponProficiency ( "
                + "art TEXT, "
                + "character TEXT, "
                + "specificSkillLevel TEXT, "
                + "PRIMARY KEY(art, character), "
                + "CONSTRAINT fkArtHasCombatArtProf FOREIGN KEY (art) "
                + "REFERENCES CombatArtsCharactersWeaponProficient(art) "
                + "ON DELETE NO ACTION ON UPDATE CASCADE, "
                + "CONSTRAINT fkCharacHasCombatArtProf FOREIGN KEY (character) "
                + "REFERENCES Characters(name) "
                + "ON DELETE NO ACTION ON UPDATE CASCADE);");

        db.execSQL("CREATE TABLE CombatArtsWeaponExclusive ( "
                + "art TEXT PRIMARY KEY, "
                + "effect TEXT, "
                + "weapon TEXT, "
                + "crest TEXT, "
                + "dur TEXT, "
                + "mt TEXT, "
                + "hit TEXT, "
                + "avo TEXT, "
                + "crit TEXT, "
                + "range TEXT);");

        db.execSQL("CREATE TABLE CombatArtsClassMastery ( "
                + "art TEXT PRIMARY KEY, "
                + "effect TEXT, "
                + "weapon TEXT, "
                + "class TEXT, "
                + "dur TEXT, "
                + "mt TEXT, "
                + "hit TEXT, "
                + "avo TEXT, "
                + "crit TEXT, "
                + "range TEXT);");

        db.execSQL("CREATE TABLE CombatArtsBuddingTalents ( "
                + "art TEXT, "
                + "effect TEXT, "
                + "weapon TEXT, "
                + "character TEXT, "
                + "dur TEXT, "
                + "mt TEXT, "
                + "hit TEXT, "
                + "avo TEXT, "
                + "crit TEXT, "
                + "range TEXT, "
                + "PRIMARY KEY (art, character), "
                + "CONSTRAINT fkCombatArtsBuddingTalents FOREIGN KEY (character) "
                + "REFERENCES Characters(name) "
                + "ON DELETE NO ACTION ON UPDATE CASCADE);");

        db.execSQL("CREATE TABLE CombatArtsOther ( "
                + "art TEXT PRIMARY KEY, "
                + "effect TEXT, "
                + "weapon TEXT, "
                + "origin TEXT, "
                + "dur INTEGER, "
                + "mt INTEGER, "
                + "hit INTEGER, "
                + "avo INTEGER, "
                + "crit INTEGER, "
                + "range TEXT);");

        insertDataCombatArtsWeaponProficiency(db);
        insertDataCharacterHasCombatArtWeaponProficiency(db);
        insertDataCombatArtsWeaponExclusive(db);
        insertDataCombatArtsClassMastery(db);
        insertDataCombatArtsBuddingTalents(db);
        insertDataCombatArtsOther(db);
    }

    private void insertDataCombatArtsWeaponProficiency(SQLiteDatabase db) {
        String line = null;
        InputStream is = context.getResources().openRawResource(R.raw.combat_arts_weapon_prof_char);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        try {
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("_");
                insertCAWeaponProfChar(db, parts[0], parts[1], parts[2], parts[3], parts[4],
                        parts[5], parts[6], parts[7], parts[8], parts[9]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        InputStream is2 = context.getResources().openRawResource(R.raw.combat_arts_weapon_prof_all);
        BufferedReader reader2 = new BufferedReader(new InputStreamReader(is2));
        try {
            while ((line = reader2.readLine()) != null) {
                String[] parts = line.split("_");
                insertCAWeaponProfAll(db, parts[0], parts[1], parts[2], parts[3], parts[4],
                        parts[5], parts[6], parts[7], parts[8], parts[9]);
            }
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader2.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void insertCAWeaponProfAll(SQLiteDatabase db, String art, String effect, String weapon,
                                       String skillLevel, String dur, String mt, String hit,
                                       String avo, String crit, String range) {
        ContentValues combatArtValues = new ContentValues();
        combatArtValues.put("art", art);
        combatArtValues.put("effect", effect);
        combatArtValues.put("weapon", weapon);
        combatArtValues.put("skillLevel", skillLevel);
        combatArtValues.put("dur", dur);
        combatArtValues.put("mt", mt);
        combatArtValues.put("hit", hit);
        combatArtValues.put("avo", avo);
        combatArtValues.put("crit", crit);
        combatArtValues.put("range", range);
        db.insert("CombatArtsAllWeaponProficient", null,
                combatArtValues);
    }

    private void insertCAWeaponProfChar(SQLiteDatabase db, String art, String effect, String weapon,
                                        String skillLevel, String dur, String mt, String hit,
                                        String avo, String crit, String range) {
        ContentValues combatArtValues = new ContentValues();
        combatArtValues.put("art", art);
        combatArtValues.put("effect", effect);
        combatArtValues.put("weapon", weapon);
        combatArtValues.put("skillLevel", skillLevel);
        combatArtValues.put("dur", dur);
        combatArtValues.put("mt", mt);
        combatArtValues.put("hit", hit);
        combatArtValues.put("avo", avo);
        combatArtValues.put("crit", crit);
        combatArtValues.put("range", range);
        db.insert("CombatArtsCharactersWeaponProficient", null,
                combatArtValues);
    }

    private void insertDataCharacterHasCombatArtWeaponProficiency(SQLiteDatabase db) {
        String line = null;
        InputStream is = context.getResources().
                openRawResource(R.raw.char_has_combat_art_weapon_prof);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        try {
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("_");
                insertCharacterHasCombatArtWeaponProficiency(db, parts[0], parts[1], parts[2]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void insertCharacterHasCombatArtWeaponProficiency(SQLiteDatabase db, String art,
                                                              String characterName,
                                                              String skillLevel) {
        ContentValues combatArtValues = new ContentValues();
        combatArtValues.put("art", art);
        combatArtValues.put("character", characterName);
        combatArtValues.put("specificSkillLevel", skillLevel);
        db.insert("CharacterHasCombatArtWeaponProficiency", null,
                combatArtValues);
    }

    private void insertDataCombatArtsWeaponExclusive(SQLiteDatabase db) {
        String line = null;
        InputStream is = context.getResources().
                openRawResource(R.raw.combat_arts_weapon_exclusive);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        try {
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("_");
                insertCombatArtWeaponExclusive(db, parts[0], parts[1], parts[2], parts[3],
                        parts[4], parts[5], parts[6], parts[7], parts[8], parts[9]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void insertCombatArtWeaponExclusive(SQLiteDatabase db, String art, String effect,
                                                String weapon, String crest, String dur,
                                                String mt, String hit, String avo, String crit,
                                                String range) {
        ContentValues combatArtValues = new ContentValues();
        combatArtValues.put("art", art);
        combatArtValues.put("effect", effect);
        combatArtValues.put("weapon", weapon);
        combatArtValues.put("crest", crest);
        combatArtValues.put("dur", dur);
        combatArtValues.put("mt", mt);
        combatArtValues.put("hit", hit);
        combatArtValues.put("avo", avo);
        combatArtValues.put("crit", crit);
        combatArtValues.put("range", range);
        db.insert("CombatArtsWeaponExclusive", null,
                combatArtValues);
    }

    private void insertDataCombatArtsClassMastery(SQLiteDatabase db) {
        String line = null;
        InputStream is = context.getResources().
                openRawResource(R.raw.combat_arts_class_mastery);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        try {
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("_");
                insertCombatArtClassMastery(db, parts[0], parts[1], parts[2], parts[3],
                        parts[4], parts[5], parts[6], parts[7], parts[8], parts[9]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void insertCombatArtClassMastery(SQLiteDatabase db, String art, String effect,
                                             String weapon, String gameClass, String dur,
                                             String mt, String hit, String avo, String crit,
                                             String range) {
        ContentValues combatArtValues = new ContentValues();
        combatArtValues.put("art", art);
        combatArtValues.put("effect", effect);
        combatArtValues.put("weapon", weapon);
        combatArtValues.put("class", gameClass);
        combatArtValues.put("dur", dur);
        combatArtValues.put("mt", mt);
        combatArtValues.put("hit", hit);
        combatArtValues.put("avo", avo);
        combatArtValues.put("crit", crit);
        combatArtValues.put("range", range);
        db.insert("CombatArtsClassMastery", null,
                combatArtValues);
    }

    private void insertDataCombatArtsBuddingTalents(SQLiteDatabase db) {
        String line = null;
        InputStream is = context.getResources().
                openRawResource(R.raw.combat_arts_budding_talents);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        try {
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("_");
                insertCombatArtBuddingTalent(db, parts[0], parts[1], parts[2], parts[3],
                        parts[4], parts[5], parts[6], parts[7], parts[8], parts[9]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void insertCombatArtBuddingTalent(SQLiteDatabase db, String art, String effect,
                                              String weapon, String character, String dur,
                                              String mt, String hit, String avo, String crit,
                                              String range) {
        ContentValues combatArtValues = new ContentValues();
        combatArtValues.put("art", art);
        combatArtValues.put("effect", effect);
        combatArtValues.put("weapon", weapon);
        combatArtValues.put("character", character);
        combatArtValues.put("dur", dur);
        combatArtValues.put("mt", mt);
        combatArtValues.put("hit", hit);
        combatArtValues.put("avo", avo);
        combatArtValues.put("crit", crit);
        combatArtValues.put("range", range);
        db.insert("CombatArtsBuddingTalents", null,
                combatArtValues);
    }

    private void insertDataCombatArtsOther(SQLiteDatabase db) {
        String line = null;
        InputStream is = context.getResources().
                openRawResource(R.raw.combat_arts_other);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        try {
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("_");
                insertCombatArtOther(db, parts[0], parts[1], parts[2], parts[3],
                        parts[4], parts[5], parts[6], parts[7], parts[8], parts[9]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void insertCombatArtOther(SQLiteDatabase db, String art, String effect,
                                      String talent, String character, String dur,
                                      String mt, String hit, String avo, String crit,
                                      String range) {
        ContentValues combatArtValues = new ContentValues();
        combatArtValues.put("art", art);
        combatArtValues.put("effect", effect);
        combatArtValues.put("weapon", talent);
        combatArtValues.put("origin", character);
        combatArtValues.put("dur", dur);
        combatArtValues.put("mt", mt);
        combatArtValues.put("hit", hit);
        combatArtValues.put("avo", avo);
        combatArtValues.put("crit", crit);
        combatArtValues.put("range", range);
        db.insert("CombatArtsOther", null,
                combatArtValues);
    }

    private void createSpellsTable(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE Spells ( "
                + "spell TEXT PRIMARY KEY, "
                + "magicType TEXT, "
                + "description TEXT, "
                + "rank TEXT, "
                + "uses TEXT, "
                + "mt TEXT, "
                + "hit TEXT, "
                + "range TEXT, "
                + "crit TEXT, "
                + "weight TEXT);");

        String line = null;
        InputStream is = context.getResources().openRawResource(R.raw.spells);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        try {
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(Pattern.quote("_"));
                insertSpell(db, parts[0], parts[1], parts[2], parts[3], parts[4], parts[5],
                        parts[6], parts[7], parts[8], parts[9]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void insertSpell(SQLiteDatabase db, String spell, String magicType, String desc,
                             String rank, String uses, String mt, String hit, String range,
                             String crit, String weight) {
        ContentValues spellValues = new ContentValues();
        spellValues.put("spell", spell);
        spellValues.put("magicType", magicType);
        spellValues.put("description", desc);
        spellValues.put("rank", rank);
        spellValues.put("uses", uses);
        spellValues.put("mt", mt);
        spellValues.put("hit", hit);
        spellValues.put("range", range);
        spellValues.put("crit", crit);
        spellValues.put("weight", weight);
        db.insert("Spells", null, spellValues);
    }

    private void createMagicTable(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE Magic ( "
                + "_id INTEGER, "
                + "skillLevel TEXT, "
                + "reason TEXT, "
                + "faith TEXT, "
                + "PRIMARY KEY (_id, skillLevel),"
                + "CONSTRAINT fkMagicChar FOREIGN KEY (_id) REFERENCES Characters(_id) "
                + "ON DELETE NO ACTION ON UPDATE CASCADE, "
                + "CONSTRAINT fkMagicReason FOREIGN KEY (reason) REFERENCES Spells(spell) "
                + "ON DELETE NO ACTION ON UPDATE CASCADE, "
                + "CONSTRAINT fkMagicFaith FOREIGN KEY (faith) REFERENCES Spells(spell) "
                + "ON DELETE NO ACTION ON UPDATE CASCADE);");

        String line = null;
        InputStream is = context.getResources().openRawResource(R.raw.magic);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        try {
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(Pattern.quote("$"));
                insertMagic(db, parts[0], parts[1], parts[2], parts[3]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void insertMagic(SQLiteDatabase db, String id, String skillLevel, String reason,
                             String faith) {
        ContentValues magicValues = new ContentValues();
        magicValues.put("_id", Integer.valueOf(id));
        magicValues.put("skillLevel", skillLevel);
        magicValues.put("reason", reason);
        magicValues.put("faith", faith);
        db.insert("Magic", null, magicValues);
    }

    private void createClassesTable(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE Classes ( "
                + "name TEXT PRIMARY KEY, "
                + "classLevel TEXT, "
                + "proficiencies TEXT, "
                + "ability1 TEXT, "
                + "ability2 TEXT, "
                + "ability3 TEXT, "
                + "mastery_ability TEXT, "
                + "mastery_combat_art TEXT, "
                + "canUse TEXT, "
                + "restrictions TEXT, "
                + "certification_requirement TEXT, "
                + "seal TEXT, "
                + "experience INTEGER, "
                + "icon TEXT, "
                + "growthRatesHP TEXT, "
                + "growthRatesStr TEXT, "
                + "growthRatesMag TEXT, "
                + "growthRatesDex TEXT, "
                + "growthRatesSpd TEXT, "
                + "growthRatesLck TEXT, "
                + "growthRatesDef TEXT, "
                + "growthRatesRes TEXT, "
                + "growthRatesCha TEXT, "
                + "CONSTRAINT fkClassesAb1 FOREIGN KEY (ability1) REFERENCES Abilities(ability) "
                + "ON DELETE NO ACTION ON UPDATE CASCADE,"
                + "CONSTRAINT fkClassesAb2 FOREIGN KEY (ability2) REFERENCES Abilities(ability) "
                + "ON DELETE NO ACTION ON UPDATE CASCADE,"
                + "CONSTRAINT fkClassesAb3 FOREIGN KEY (ability3) REFERENCES Abilities(ability) "
                + "ON DELETE NO ACTION ON UPDATE CASCADE,"
                + "CONSTRAINT fkClassesMAb1 FOREIGN KEY (mastery_ability) " +
                " REFERENCES Abilities(ability) "
                + "ON DELETE NO ACTION ON UPDATE CASCADE,"
                + "CONSTRAINT fkClassesMAb2 FOREIGN KEY (mastery_combat_art) " +
                " REFERENCES CombatArtsClassMastery(art) "
                + "ON DELETE NO ACTION ON UPDATE CASCADE);");

        String line = null;
        InputStream is = context.getResources().openRawResource(R.raw.classes);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        try {
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("_");
                insertClass(db, parts[0], parts[1], parts[2], parts[3], parts[4], parts[5],
                        parts[6], parts[7], parts[8], parts[9], parts[10], parts[11],
                        parts[12], getClassIcon(parts[0]), parts[13], parts[14],
                        parts[15], parts[16], parts[17], parts[18], parts[19], parts[20],
                        parts[21]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /** Given the name of a certain class, returns the id of its icon.
     * This method is only used to insert the classes data in the database. Later on, the icons
     * are looked up using queries.
     *
     * @param className
     * @return Icon of the class
     */
    public static String getClassIcon(String className){
        switch (className){
            case "Commoner": return "class_commoner";
            case "Noble": return "class_noble";
            case "Dancer": return "class_dancer";
            case "Enlightened One": return "class_enlightened";
            case "Armored Lord": return "class_armored_lord";
            case "High Lord": return "class_high_lord";
            case "Wyvern Master": return "class_wyvern_master";
            case "Death Knight": return "class_death_knight";
            case "Emperor": return "class_emperor";
            case "Great Lord": return "class_great_lord";
            case "Barbarossa": return "class_barbarossa";
            case "Myrmidon": return "class_myrmidon";
            case "Soldier": return "class_soldier";
            case "Fighter": return "class_fighter";
            case "Monk": return "class_monk";
            case "Lord": return "class_lord";
            case "Mercenary": return "class_mercenary";
            case "Thief": return "class_thief";
            case "Cavalier": return "class_cavalier";
            case "Pegasus Knight": return "class_pegasus";
            case "Brigand": return "class_brigand";
            case "Armored Knight": return "class_armored_knight";
            case "Archer": return "class_archer";
            case "Brawler": return "class_brawler";
            case "Mage": return "class_mage";
            case "Dark Mage": return "class_dark_mage";
            case "Priest": return "class_priest";
            case "Swordmaster": return "class_swordmaster";
            case "Hero": return "class_hero";
            case "Assassin": return "class_assassin";
            case "Paladin": return "class_paladin";
            case "Warrior": return "class_warrior";
            case "Fortress Knight": return "class_fortress_knight";
            case "Wyvern Rider": return "class_wyvern_rider";
            case "Sniper": return "class_sniper";
            case "Grappler": return "class_grappler";
            case "Warlock": return "class_warlock";
            case "Dark Bishop": return "class_dark_bishop";
            case "Bishop": return "class_bishop";
            case "Trickster": return "class_trickster";
            case "War Monk":
            case "War Cleric": return "class_war_monk";
            case "Dark Flier": return "class_dark_flier";
            case "Valkyrie": return "class_valkyrie";
            case "Falcon Knight": return "class_falcon_knight";
            case "Wyvern Lord": return "class_wyvern_lord";
            case "Mortal Savant": return "class_mortal_savant";
            case "Great Knight": return "class_great_knight";
            case "Bow Knight": return "class_bow_knight";
            case "Dark Knight": return "class_dark_knight";
            case "Holy Knight": return "class_holy_knight";
            case "War Master": return "class_war_master";
            case "Gremory": return "class_gremory";
            default: return "missing_number";
        }
    }

    private void insertClass(SQLiteDatabase db, String name, String classLevel,
                             String proficiencies, String ability1, String ability2,
                             String ability3, String masteryAbility, String masteryCArt,
                             String canUse, String restrictions,
                             String certReq, String seal, String experience, String icon,
                             String growthRHP, String growthRStr, String growthRMag,
                             String growthRDex, String growthRSpd, String growthRLck,
                             String growthRDef, String growthRRes, String growthRCha) {
        ContentValues classesValues = new ContentValues();
        classesValues.put("name", name);
        classesValues.put("classLevel", classLevel);
        classesValues.put("proficiencies", proficiencies);
        classesValues.put("ability1", ability1);
        classesValues.put("ability2", ability2);
        classesValues.put("ability3", ability3);
        classesValues.put("mastery_ability", masteryAbility);
        classesValues.put("mastery_combat_art", masteryCArt);
        classesValues.put("canUse", canUse);
        classesValues.put("restrictions", restrictions);
        classesValues.put("certification_requirement", certReq);
        classesValues.put("seal", seal);
        classesValues.put("experience", Integer.valueOf(experience));
        classesValues.put("icon", icon);
        classesValues.put("growthRatesHP", growthRHP);
        classesValues.put("growthRatesStr", growthRStr);
        classesValues.put("growthRatesMag", growthRMag);
        classesValues.put("growthRatesDex", growthRDex);
        classesValues.put("growthRatesSpd", growthRSpd);
        classesValues.put("growthRatesLck", growthRLck);
        classesValues.put("growthRatesDef", growthRDef);
        classesValues.put("growthRatesRes", growthRRes);
        classesValues.put("growthRatesCha", growthRCha);
        db.insert("Classes", null, classesValues);
    }

    private void createCharacterGiftsTable(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE CharacterGifts ( "
                + "_id INTEGER, "
                + "gift TEXT, "
                + "liked INTEGER, "
                + "rank INTEGER, "
                + "PRIMARY KEY (_id, gift),"
                + "CONSTRAINT fkCharacterGifts FOREIGN KEY (_id) REFERENCES Characters(_id) "
                + "ON DELETE NO ACTION ON UPDATE CASCADE);");

        String line = null;
        InputStream is = context.getResources().openRawResource(R.raw.gifts);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        try {
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("_");
                insertCharacterGift(db, parts[0], parts[1], parts[2], parts[3]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void insertCharacterGift(SQLiteDatabase db, String id, String gift, String liked,
                                     String rank) {
        ContentValues giftsValues = new ContentValues();
        giftsValues.put("_id", Integer.valueOf(id));
        giftsValues.put("gift", gift);
        giftsValues.put("liked", liked.equals("y") ? 1 : 0);
        giftsValues.put("rank", Integer.valueOf(rank));
        db.insert("CharacterGifts", null, giftsValues);
    }

    private void createCharacterMealsTable(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE CharacterMeals ( "
                + "_id INTEGER, "
                + "meal TEXT, "
                + "liked INTEGER, "
                + "PRIMARY KEY (_id, meal),"
                + "CONSTRAINT fkCharacterMeals FOREIGN KEY (_id) REFERENCES Characters(_id) "
                + "ON DELETE NO ACTION ON UPDATE CASCADE);");

        String line = null;
        InputStream is = context.getResources().openRawResource(R.raw.meals);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        try {
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("_");
                insertCharacterMeal(db, parts[0], parts[1], parts[2]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void insertCharacterMeal(SQLiteDatabase db, String id, String meal, String liked) {
        ContentValues mealValues = new ContentValues();
        mealValues.put("_id", Integer.valueOf(id));
        mealValues.put("meal", meal);
        mealValues.put("liked", liked.equals("y") ? 1 : 0);
        db.insert("CharacterMeals", null, mealValues);
    }

    private void createCharacterLostItemsTable(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE CharacterLostItems ( "
                + "_id INTEGER, "
                + "item TEXT, "
                + "PRIMARY KEY (_id, item),"
                + "CONSTRAINT fkCharacterLostItems FOREIGN KEY (_id) REFERENCES Characters(_id) "
                + "ON DELETE NO ACTION ON UPDATE CASCADE);");

        String line = null;
        InputStream is = context.getResources().openRawResource(R.raw.lost_items);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        try {
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("_");
                insertCharacterLostItem(db, parts[0], parts[1]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void insertCharacterLostItem(SQLiteDatabase db, String id, String item) {
        ContentValues lostItemValues = new ContentValues();
        lostItemValues.put("_id", Integer.valueOf(id));
        lostItemValues.put("item", item);
        db.insert("CharacterLostItems", null, lostItemValues);
    }

    private void createFavouriteTeasTable(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE FavouriteTeas ( "
                + "_id INTEGER, "
                + "tea TEXT, "
                + "PRIMARY KEY (_id, tea),"
                + "CONSTRAINT fkFavouriteTeas FOREIGN KEY (_id) REFERENCES Characters(_id) "
                + "ON DELETE NO ACTION ON UPDATE CASCADE);");

        String line = null;
        InputStream is = context.getResources().openRawResource(R.raw.favourite_teas);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        try {
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(Pattern.quote("_"));
                insertFavouriteTea(db, parts[0], parts[1]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void insertFavouriteTea(SQLiteDatabase db, String id, String tea) {
        ContentValues favouriteTeaValues = new ContentValues();
        favouriteTeaValues.put("_id", Integer.valueOf(id));
        favouriteTeaValues.put("tea", tea);
        db.insert("FavouriteTeas", null, favouriteTeaValues);
    }

    private void createTopicsTable(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE Topics ( "
                + "_id INTEGER, "
                + "topic TEXT, "
                + "PRIMARY KEY (_id, topic),"
                + "CONSTRAINT fkTopics FOREIGN KEY (_id) REFERENCES Characters(_id) "
                + "ON DELETE NO ACTION ON UPDATE CASCADE);");

        String line = null;
        InputStream is = context.getResources().openRawResource(R.raw.topics);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        try {
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(Pattern.quote("_"));
                insertTopic(db, parts[0], parts[1]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void insertTopic(SQLiteDatabase db, String id, String topic) {
        ContentValues topicValues = new ContentValues();
        topicValues.put("_id", Integer.valueOf(id));
        topicValues.put("topic", topic);
        db.insert("Topics", null, topicValues);
    }

    private void createFinalConversationsTable(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE FinalConversations ( "
                + "_id INTEGER, "
                + "conversation TEXT, "
                + "option1 TEXT, "
                + "option2 TEXT, "
                + "option3 TEXT, "
                + "PRIMARY KEY (_id, conversation),"
                + "CONSTRAINT fkFinalConversations FOREIGN KEY (_id) REFERENCES Characters(_id) "
                + "ON DELETE NO ACTION ON UPDATE CASCADE);");

        String line = null;
        InputStream is = context.getResources().openRawResource(R.raw.final_conversations);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        try {
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(Pattern.quote("_"));
                insertFinalConversation(db, parts[0], parts[1], parts[2], parts[3], parts[4]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void insertFinalConversation(SQLiteDatabase db, String id, String conversation,
                                         String option1, String option2, String option3) {
        ContentValues conversationValues = new ContentValues();
        conversationValues.put("_id", Integer.valueOf(id));
        conversationValues.put("conversation", conversation);
        conversationValues.put("option1", option1);
        conversationValues.put("option2", option2);
        conversationValues.put("option3", option3);
        db.insert("FinalConversations", null, conversationValues);
    }

    private void createSupportsTable(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE Supports ( "
                + "character1 INTEGER, "
                + "character2 INTEGER, "
                + "cSupport TEXT, "
                + "bSupport TEXT, "
                + "aSupport TEXT, "
                + "interSupport TEXT, "
                + "interRank TEXT, "
                + "sSupport TEXT, "
                + "PRIMARY KEY (character1, character2),"
                + "CONSTRAINT fkSupports1 FOREIGN KEY (character1) REFERENCES Characters(_id) "
                + "ON DELETE NO ACTION ON UPDATE CASCADE, "
                + "CONSTRAINT fkSupports2 FOREIGN KEY (character2) REFERENCES Characters(_id) "
                + "ON DELETE NO ACTION ON UPDATE CASCADE);");

        insertAloisSupports(db);
        insertAnnetteSupports(db);
        insertAsheSupports(db);
        insertBalthusSupports(db);
        insertBernadettaSupports(db);
        insertCasparSupports(db);
        insertCatherineSupports(db);
        insertClaudeSupports(db);
        insertConstanceSupports(db);
        insertCyrilSupports(db);
        insertDedueSupports(db);
        insertDimitriSupports(db);
        insertDorotheaSupports(db);
        insertEdelgardSupports(db);
        insertFelixSupports(db);
        insertFerdinandSupports(db);
        insertFlaynSupports(db);
        insertGilbertSupports(db);
        insertHannemanSupports(db);
        insertHapiSupports(db);
        insertHildaSupports(db);
        insertHubertSupports(db);
        insertIgnatzSupports(db);
        insertIngridSupports(db);
        insertJeritzaSupports(db);
        insertLeonieSupports(db);
        insertLinhardtSupports(db);
        insertLorenzSupports(db);
        insertLysitheaSupports(db);
        insertManuelaSupports(db);
        insertMarianneSupports(db);
        insertMercedesSupports(db);
        insertPetraSupports(db);
        insertRaphaelSupports(db);
        insertRheaSupports(db);
        insertSetethSupports(db);
        insertShamirSupports(db);
        insertSylvainSupports(db);
        insertYuriSupports(db);
    }

    private void insertSupport(SQLiteDatabase db, String character1, String character2,
                               String cSupport, String bSupport, String aSupport, String interSup,
                               String interRank, String sSupport) {
        ContentValues supportValues = new ContentValues();
        supportValues.put("character1", Integer.valueOf(character1));
        supportValues.put("character2", Integer.valueOf(character2));
        supportValues.put("cSupport", cSupport);
        supportValues.put("bSupport", bSupport);
        supportValues.put("aSupport", aSupport);
        supportValues.put("interSupport", interSup);
        supportValues.put("interRank", interRank);
        supportValues.put("sSupport", sSupport);
        db.insert("Supports", null, supportValues);
    }

    private void insertAloisSupports(SQLiteDatabase db) {
        String line = null;
        InputStream is = context.getResources().openRawResource(R.raw.alois_supports);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        try {
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(Pattern.quote("_"));
                insertSupport(db, parts[0], parts[1], parts[2], parts[3], parts[4],
                        parts[5], parts[6], parts[7]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void insertAnnetteSupports(SQLiteDatabase db) {
        String line = null;
        InputStream is = context.getResources().openRawResource(R.raw.annette_supports);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        try {
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(Pattern.quote("_"));
                insertSupport(db, parts[0], parts[1], parts[2], parts[3], parts[4],
                        parts[5], parts[6], parts[7]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void insertAsheSupports(SQLiteDatabase db) {
        String line = null;
        InputStream is = context.getResources().openRawResource(R.raw.ashe_supports);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        try {
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(Pattern.quote("_"));
                insertSupport(db, parts[0], parts[1], parts[2], parts[3], parts[4],
                        parts[5], parts[6], parts[7]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void insertBalthusSupports(SQLiteDatabase db) {
        String line = null;
        InputStream is = context.getResources().openRawResource(R.raw.balthus_supports);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        try {
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(Pattern.quote("_"));
                insertSupport(db, parts[0], parts[1], parts[2], parts[3], parts[4],
                        parts[5], parts[6], parts[7]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void insertBernadettaSupports(SQLiteDatabase db) {
        String line = null;
        InputStream is = context.getResources().openRawResource(R.raw.bernadetta_supports);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        try {
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(Pattern.quote("_"));
                insertSupport(db, parts[0], parts[1], parts[2], parts[3], parts[4],
                        parts[5], parts[6], parts[7]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void insertCasparSupports(SQLiteDatabase db) {
        String line = null;
        InputStream is = context.getResources().openRawResource(R.raw.caspar_supports);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        try {
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(Pattern.quote("_"));
                insertSupport(db, parts[0], parts[1], parts[2], parts[3], parts[4],
                        parts[5], parts[6], parts[7]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void insertCatherineSupports(SQLiteDatabase db) {
        String line = null;
        InputStream is = context.getResources().openRawResource(R.raw.catherine_supports);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        try {
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(Pattern.quote("_"));
                insertSupport(db, parts[0], parts[1], parts[2], parts[3], parts[4],
                        parts[5], parts[6], parts[7]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void insertClaudeSupports(SQLiteDatabase db) {
        String line = null;
        InputStream is = context.getResources().openRawResource(R.raw.claude_supports);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        try {
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(Pattern.quote("_"));
                insertSupport(db, parts[0], parts[1], parts[2], parts[3], parts[4],
                        parts[5], parts[6], parts[7]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void insertConstanceSupports(SQLiteDatabase db) {
        String line = null;
        InputStream is = context.getResources().openRawResource(R.raw.constance_supports);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        try {
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(Pattern.quote("_"));
                insertSupport(db, parts[0], parts[1], parts[2], parts[3], parts[4],
                        parts[5], parts[6], parts[7]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void insertCyrilSupports(SQLiteDatabase db) {
        String line = null;
        InputStream is = context.getResources().openRawResource(R.raw.cyril_supports);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        try {
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(Pattern.quote("_"));
                insertSupport(db, parts[0], parts[1], parts[2], parts[3], parts[4],
                        parts[5], parts[6], parts[7]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void insertDedueSupports(SQLiteDatabase db) {
        String line = null;
        InputStream is = context.getResources().openRawResource(R.raw.dedue_supports);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        try {
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(Pattern.quote("_"));
                insertSupport(db, parts[0], parts[1], parts[2], parts[3], parts[4],
                        parts[5], parts[6], parts[7]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void insertDimitriSupports(SQLiteDatabase db) {
        String line = null;
        InputStream is = context.getResources().openRawResource(R.raw.dimitri_supports);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        try {
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(Pattern.quote("_"));
                insertSupport(db, parts[0], parts[1], parts[2], parts[3], parts[4],
                        parts[5], parts[6], parts[7]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void insertDorotheaSupports(SQLiteDatabase db) {
        String line = null;
        InputStream is = context.getResources().openRawResource(R.raw.dorothea_supports);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        try {
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(Pattern.quote("_"));
                insertSupport(db, parts[0], parts[1], parts[2], parts[3], parts[4],
                        parts[5], parts[6], parts[7]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void insertEdelgardSupports(SQLiteDatabase db) {
        String line = null;
        InputStream is = context.getResources().openRawResource(R.raw.edelgard_supports);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        try {
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(Pattern.quote("_"));
                insertSupport(db, parts[0], parts[1], parts[2], parts[3], parts[4],
                        parts[5], parts[6], parts[7]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void insertFelixSupports(SQLiteDatabase db) {
        String line = null;
        InputStream is = context.getResources().openRawResource(R.raw.felix_supports);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        try {
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(Pattern.quote("_"));
                insertSupport(db, parts[0], parts[1], parts[2], parts[3], parts[4],
                        parts[5], parts[6], parts[7]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void insertFerdinandSupports(SQLiteDatabase db) {
        String line = null;
        InputStream is = context.getResources().openRawResource(R.raw.ferdinand_supports);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        try {
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(Pattern.quote("_"));
                insertSupport(db, parts[0], parts[1], parts[2], parts[3], parts[4],
                        parts[5], parts[6], parts[7]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void insertFlaynSupports(SQLiteDatabase db) {
        String line = null;
        InputStream is = context.getResources().openRawResource(R.raw.flayn_supports);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        try {
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(Pattern.quote("_"));
                insertSupport(db, parts[0], parts[1], parts[2], parts[3], parts[4],
                        parts[5], parts[6], parts[7]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void insertGilbertSupports(SQLiteDatabase db) {
        String line = null;
        InputStream is = context.getResources().openRawResource(R.raw.gilbert_supports);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        try {
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(Pattern.quote("_"));
                insertSupport(db, parts[0], parts[1], parts[2], parts[3], parts[4],
                        parts[5], parts[6], parts[7]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void insertHannemanSupports(SQLiteDatabase db) {
        String line = null;
        InputStream is = context.getResources().openRawResource(R.raw.hanneman_supports);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        try {
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(Pattern.quote("_"));
                insertSupport(db, parts[0], parts[1], parts[2], parts[3], parts[4],
                        parts[5], parts[6], parts[7]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void insertHapiSupports(SQLiteDatabase db) {
        String line = null;
        InputStream is = context.getResources().openRawResource(R.raw.hapi_supports);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        try {
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(Pattern.quote("_"));
                insertSupport(db, parts[0], parts[1], parts[2], parts[3], parts[4],
                        parts[5], parts[6], parts[7]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void insertHildaSupports(SQLiteDatabase db) {
        String line = null;
        InputStream is = context.getResources().openRawResource(R.raw.hilda_supports);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        try {
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(Pattern.quote("_"));
                insertSupport(db, parts[0], parts[1], parts[2], parts[3], parts[4],
                        parts[5], parts[6], parts[7]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void insertHubertSupports(SQLiteDatabase db) {
        String line = null;
        InputStream is = context.getResources().openRawResource(R.raw.hubert_supports);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        try {
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(Pattern.quote("_"));
                insertSupport(db, parts[0], parts[1], parts[2], parts[3], parts[4],
                        parts[5], parts[6], parts[7]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void insertIgnatzSupports(SQLiteDatabase db) {
        String line = null;
        InputStream is = context.getResources().openRawResource(R.raw.ignatz_supports);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        try {
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(Pattern.quote("_"));
                insertSupport(db, parts[0], parts[1], parts[2], parts[3], parts[4],
                        parts[5], parts[6], parts[7]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void insertIngridSupports(SQLiteDatabase db) {
        String line = null;
        InputStream is = context.getResources().openRawResource(R.raw.ingrid_supports);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        try {
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(Pattern.quote("_"));
                insertSupport(db, parts[0], parts[1], parts[2], parts[3], parts[4],
                        parts[5], parts[6], parts[7]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void insertJeritzaSupports(SQLiteDatabase db) {
        String line = null;
        InputStream is = context.getResources().openRawResource(R.raw.jeritza_supports);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        try {
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(Pattern.quote("_"));
                insertSupport(db, parts[0], parts[1], parts[2], parts[3], parts[4],
                        parts[5], parts[6], parts[7]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void insertLeonieSupports(SQLiteDatabase db) {
        String line = null;
        InputStream is = context.getResources().openRawResource(R.raw.leonie_supports);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        try {
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(Pattern.quote("_"));
                insertSupport(db, parts[0], parts[1], parts[2], parts[3], parts[4],
                        parts[5], parts[6], parts[7]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void insertLinhardtSupports(SQLiteDatabase db) {
        String line = null;
        InputStream is = context.getResources().openRawResource(R.raw.linhardt_supports);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        try {
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(Pattern.quote("_"));
                insertSupport(db, parts[0], parts[1], parts[2], parts[3], parts[4],
                        parts[5], parts[6], parts[7]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void insertLorenzSupports(SQLiteDatabase db) {
        String line = null;
        InputStream is = context.getResources().openRawResource(R.raw.lorenz_supports);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        try {
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(Pattern.quote("_"));
                insertSupport(db, parts[0], parts[1], parts[2], parts[3], parts[4],
                        parts[5], parts[6], parts[7]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void insertLysitheaSupports(SQLiteDatabase db) {
        String line = null;
        InputStream is = context.getResources().openRawResource(R.raw.lysithea_supports);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        try {
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(Pattern.quote("_"));
                insertSupport(db, parts[0], parts[1], parts[2], parts[3], parts[4],
                        parts[5], parts[6], parts[7]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void insertManuelaSupports(SQLiteDatabase db) {
        String line = null;
        InputStream is = context.getResources().openRawResource(R.raw.manuela_supports);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        try {
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(Pattern.quote("_"));
                insertSupport(db, parts[0], parts[1], parts[2], parts[3], parts[4],
                        parts[5], parts[6], parts[7]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void insertMarianneSupports(SQLiteDatabase db) {
        String line = null;
        InputStream is = context.getResources().openRawResource(R.raw.marianne_supports);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        try {
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(Pattern.quote("_"));
                insertSupport(db, parts[0], parts[1], parts[2], parts[3], parts[4],
                        parts[5], parts[6], parts[7]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void insertMercedesSupports(SQLiteDatabase db) {
        String line = null;
        InputStream is = context.getResources().openRawResource(R.raw.mercedes_supports);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        try {
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(Pattern.quote("_"));
                insertSupport(db, parts[0], parts[1], parts[2], parts[3], parts[4],
                        parts[5], parts[6], parts[7]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void insertPetraSupports(SQLiteDatabase db) {
        String line = null;
        InputStream is = context.getResources().openRawResource(R.raw.petra_supports);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        try {
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(Pattern.quote("_"));
                insertSupport(db, parts[0], parts[1], parts[2], parts[3], parts[4],
                        parts[5], parts[6], parts[7]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void insertRaphaelSupports(SQLiteDatabase db) {
        String line = null;
        InputStream is = context.getResources().openRawResource(R.raw.raphael_supports);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        try {
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(Pattern.quote("_"));
                insertSupport(db, parts[0], parts[1], parts[2], parts[3], parts[4],
                        parts[5], parts[6], parts[7]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void insertRheaSupports(SQLiteDatabase db) {
        String line = null;
        InputStream is = context.getResources().openRawResource(R.raw.rhea_supports);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        try {
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(Pattern.quote("_"));
                insertSupport(db, parts[0], parts[1], parts[2], parts[3], parts[4],
                        parts[5], parts[6], parts[7]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void insertSetethSupports(SQLiteDatabase db) {
        String line = null;
        InputStream is = context.getResources().openRawResource(R.raw.seteth_supports);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        try {
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(Pattern.quote("_"));
                insertSupport(db, parts[0], parts[1], parts[2], parts[3], parts[4],
                        parts[5], parts[6], parts[7]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void insertShamirSupports(SQLiteDatabase db) {
        String line = null;
        InputStream is = context.getResources().openRawResource(R.raw.shamir_supports);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        try {
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(Pattern.quote("_"));
                insertSupport(db, parts[0], parts[1], parts[2], parts[3], parts[4],
                        parts[5], parts[6], parts[7]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void insertSylvainSupports(SQLiteDatabase db) {
        String line = null;
        InputStream is = context.getResources().openRawResource(R.raw.sylvain_supports);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        try {
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(Pattern.quote("_"));
                insertSupport(db, parts[0], parts[1], parts[2], parts[3], parts[4],
                        parts[5], parts[6], parts[7]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void insertYuriSupports(SQLiteDatabase db) {
        String line = null;
        InputStream is = context.getResources().openRawResource(R.raw.yuri_supports);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        try {
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(Pattern.quote("_"));
                insertSupport(db, parts[0], parts[1], parts[2], parts[3], parts[4],
                        parts[5], parts[6], parts[7]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void createGambitsTable(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE Gambits ( "
                + "name TEXT PRIMARY KEY, "
                + "type TEXT, "
                + "mt TEXT, "
                + "hit TEXT, "
                + "range TEXT, "
                + "description TEXT, "
                + "formation TEXT);");

        String line = null;
        InputStream is = context.getResources().openRawResource(R.raw.gambits);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        try {
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] parts = line.split("_");
                // parts[6] + "_" + parts[7] = formation image name (e.g. "formation_cross")
                String formation = parts[6] + "_" + parts[7];
                ContentValues values = new ContentValues();
                values.put("name", parts[0]);
                values.put("type", parts[1]);
                values.put("mt", parts[2]);
                values.put("hit", parts[3]);
                values.put("range", parts[4]);
                values.put("description", parts[5]);
                values.put("formation", formation);
                db.insert("Gambits", null, values);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void createBattalionsTable(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE Battalions ( "
                + "name TEXT PRIMARY KEY, "
                + "authorityLevel TEXT, "
                + "endurance TEXT, "
                + "str TEXT, "
                + "mag TEXT, "
                + "hit TEXT, "
                + "crit TEXT, "
                + "avo TEXT, "
                + "prt TEXT, "
                + "rsl TEXT, "
                + "cha TEXT, "
                + "gambit TEXT, "
                + "movementType TEXT);");

        String line = null;
        InputStream is = context.getResources().openRawResource(R.raw.battalions);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        try {
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] parts = line.split("_");
                ContentValues values = new ContentValues();
                values.put("name", parts[0]);
                values.put("authorityLevel", parts[1]);
                values.put("endurance", parts[2]);
                values.put("str", parts[3]);
                values.put("mag", parts[4]);
                values.put("hit", parts[5]);
                values.put("crit", parts[6]);
                values.put("avo", parts[7]);
                values.put("prt", parts[8]);
                values.put("rsl", parts[9]);
                values.put("cha", parts[10]);
                values.put("gambit", parts[11]);
                values.put("movementType", parts[12]);
                db.insert("Battalions", null, values);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void createFacultyTrainingTable(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE FacultyTraining ( "
                + "name TEXT PRIMARY KEY, "
                + "skills TEXT, "
                + "part1Routes TEXT, "
                + "part2Routes TEXT);");

        String line = null;
        InputStream is = context.getResources().openRawResource(R.raw.faculty_training);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        try {
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] parts = line.split("_", 4);
                ContentValues values = new ContentValues();
                values.put("name", parts[0]);
                values.put("skills", parts[1]);
                values.put("part1Routes", parts[2]);
                values.put("part2Routes", parts[3]);
                db.insert("FacultyTraining", null, values);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void createLectureQuestionsTable(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE LectureQuestions ( "
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "characterName TEXT, "
                + "question TEXT, "
                + "bestAnswer TEXT, "
                + "phase TEXT);");

        String line = null;
        InputStream is = context.getResources().openRawResource(R.raw.lecture_questions);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        try {
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] parts = line.split("~", 4);
                if (parts.length < 4) continue;
                ContentValues values = new ContentValues();
                values.put("characterName", parts[0]);
                values.put("question", parts[1]);
                values.put("bestAnswer", parts[2]);
                values.put("phase", parts[3]);
                db.insert("LectureQuestions", null, values);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void createWeaponsTable(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE Weapons ( "
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "name TEXT, "
                + "type TEXT, "
                + "lvl TEXT, "
                + "mt INTEGER, "
                + "hit INTEGER, "
                + "crit INTEGER, "
                + "rng TEXT, "
                + "wt INTEGER, "
                + "uses INTEGER, "
                + "effect TEXT);");

        String line = null;
        InputStream is = context.getResources().openRawResource(R.raw.weapons);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        try {
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] parts = line.split("\\|", -1);
                if (parts.length < 10) continue;
                ContentValues values = new ContentValues();
                values.put("name", parts[0]);
                values.put("type", parts[1]);
                values.put("lvl", parts[2]);
                values.put("mt", Integer.parseInt(parts[3].trim()));
                values.put("hit", Integer.parseInt(parts[4].trim()));
                values.put("crit", Integer.parseInt(parts[5].trim()));
                values.put("rng", parts[6]);
                values.put("wt", Integer.parseInt(parts[7].trim()));
                values.put("uses", Integer.parseInt(parts[8].trim()));
                values.put("effect", parts[9]);
                db.insert("Weapons", null, values);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
