package com.starweaver.membrana;

import android.content.*;
import android.database.*;
import android.database.sqlite.*;
import android.util.*;
import com.google.gson.*;
import java.util.*; //FOR LOGGING

public class DataBaseHandler extends SQLiteOpenHelper 
{
Context context;
private ImplementInRobot implementInRobotListener;
private static TreeNode temporaryTreeNode;
private static final String strSeparator = "__,__";//NEED DELETE???
private static final int DATABASE_VERSION = 1;
private static final String DATABASE_NAME = "treeNodesDataBase002";
private static final String TABLE_NAME = "treeNodesDataTable002";
private static final String KEY_ID = "id";
//private static final String KEY_NAME = "name";
//private static final String KEY_PH_NO = "phone_number";
//--
private static final String KEY_IERARCHICAL_NAME = "ierarchicalName";
private static final String KEY_ENVIRONMENT_NAME = "environmentName";
private static final String KEY_ENVIRONMENT_PICTURE = "environmentPicture";
private static final String KEY_DEVICES_NAMES_ARRAY_STRING = "devicesNamesArrayString";
private static final String KEY_DEVICES_PICTURES_ARRAY_STRING = "devicesPicturesArrayString";
private static final String KEY_DEVICES_PATTERNS_ARRAY_STRING = "devicesPatternsArrayString";
private static final String KEY_CHILDREN_NAMES_ARRAY_STRING = "childrenNamesArrayString";
//ImplementInRobot will be = "this"
private static final String KEY_BUTTON_STATE_INT = "buttonStateInt";
private static final String KEY_MULTISENSOR_ARRAY_POSITION = "multisensorArrayPosition";
private static final String KEY_NODE_TYPE = "nodeType";
static String[] savedTreeNodeAttributesFromDataBaseReturnStringArray = new String[10];
private static ArrayList<String> temporaryDevicesNamesArraylist = new ArrayList<String>();
private static ArrayList<String> temporaryDevicesPicturesArraylist = new ArrayList<String>();
private static ArrayList<String> temporaryDevicesPatternsArraylist = new ArrayList<String>();
private static ArrayList<String> temporaryChildrenNamesArraylist = new ArrayList<String>();
private static JsonParser jsonParser;
private static JsonElement jsonElement;
private static JsonArray jsonArray;
//--
//private static final String KEY_JSONSTRING = "tree_nodes_array_list_json_string";

public DataBaseHandler(Context context, ImplementInRobot implementInRobotListener) 
{
super(context, DATABASE_NAME, null, DATABASE_VERSION);
this.context = context;
this.implementInRobotListener = implementInRobotListener;
}

public String getDataBasePathString()
{
return context.getDatabasePath(DATABASE_NAME).getPath();
}

public boolean dataBaseExists() 
{
SQLiteDatabase checkDB = null;
try 
{
checkDB = SQLiteDatabase.openDatabase(context.getDatabasePath(DATABASE_NAME).getPath(), null, SQLiteDatabase.OPEN_READONLY);
checkDB.close();
} 
catch (SQLiteException e) 
{
// database doesn't exist yet.
}
return checkDB != null;
}

@Override
public void onCreate(SQLiteDatabase db) 
{
/*String CREATED_TABLE = "CREATE TABLE " + TABLE_NAME + "("
+ KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
+ KEY_PH_NO + " TEXT" + ")";
db.execSQL(CREATED_TABLE);*/
//
String CREATED_TABLE = "CREATE TABLE " + TABLE_NAME + "("
+ KEY_ID + " INTEGER PRIMARY KEY," 
+ KEY_IERARCHICAL_NAME + " TEXT," 
+ KEY_ENVIRONMENT_NAME + " TEXT,"
+ KEY_ENVIRONMENT_PICTURE + " INT,"
+ KEY_DEVICES_NAMES_ARRAY_STRING + " TEXT,"
+ KEY_DEVICES_PICTURES_ARRAY_STRING + " TEXT,"
+ KEY_DEVICES_PATTERNS_ARRAY_STRING + " TEXT,"
+ KEY_CHILDREN_NAMES_ARRAY_STRING + " TEXT,"
+ KEY_BUTTON_STATE_INT + " INT," 
+ KEY_MULTISENSOR_ARRAY_POSITION  + " INT,"  
+ KEY_NODE_TYPE  + " INT" + ")";
db.execSQL(CREATED_TABLE);
//
/*String CREATED_TABLE = "CREATE TABLE " + TABLE_NAME + "("
 + KEY_ID + " INTEGER PRIMARY KEY,"
 + KEY_JSONSTRING + " TEXT" + ")";
 db.execSQL(CREATED_TABLE);*/
}

@Override
public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) 
{
db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
onCreate(db);
}

/*public void addEnvironment(Environment environment) 
{
SQLiteDatabase db = this.getWritableDatabase();
ContentValues values = new ContentValues();
values.put(KEY_NAME, environment.getName());
values.put(KEY_PH_NO, environment.getPhoneNumber());
db.insert(TABLE_NAME, null, values);
db.close();
}*/
//=//
public void saveTreeNodeAttributesToDataBase(TreeNode treeNode)
{
SQLiteDatabase dataBase = this.getWritableDatabase();
ContentValues values = new ContentValues();
values.put(KEY_IERARCHICAL_NAME, treeNode.getIerarchicalName());
values.put(KEY_ENVIRONMENT_NAME, treeNode.getEnvironmentName());
values.put(KEY_ENVIRONMENT_PICTURE, treeNode.getEnvironmentPicture());
values.put(KEY_DEVICES_NAMES_ARRAY_STRING, treeNode.getEnvironmentDevicesArrayListString(0));
values.put(KEY_DEVICES_PICTURES_ARRAY_STRING, treeNode.getEnvironmentDevicesArrayListString(1));
values.put(KEY_DEVICES_PATTERNS_ARRAY_STRING, treeNode.getEnvironmentDevicesArrayListString(2));
values.put(KEY_CHILDREN_NAMES_ARRAY_STRING, treeNode.getEnvironmentDevicesArrayListString(3));
//JSON STRINGS mAY BE COMMENTED BECOUSE UPDAITING IT - CAUSE TO RECURSIVE
//MULTIPLE SAVING AND FINALLY - FREEZING PROGRAM

values.put(KEY_BUTTON_STATE_INT, treeNode.getButtonStateInt());
values.put(KEY_MULTISENSOR_ARRAY_POSITION, treeNode.getMultiSensorArrayPosition());
values.put(KEY_NODE_TYPE, treeNode.getNodeType());
dataBase.insert(TABLE_NAME, null, values);
dataBase.close();
}
//=//
/*
Gson gson = new Gson();
String jsonArray = gson.toJson(mylist);
*/
/*public void saveTreeNodesArrayListJSONtoDataBase(List<TreeNode> treeNodesArrayList)
{
Gson gson = new Gson();
SQLiteDatabase dataBase = this.getWritableDatabase();
ContentValues values = new ContentValues();
//here freezes
String s = gson.toJson(treeNodesArrayList);
//here freezes
values.put(KEY_JSONSTRING, s);
dataBase.insert(TABLE_NAME, null, values);
dataBase.close();
}*/

/*public Environment getEnvironment(int id) 
{
SQLiteDatabase db = this.getReadableDatabase();
Cursor cursor = db.query(TABLE_NAME, new String[] { KEY_ID, KEY_NAME, KEY_PH_NO }, KEY_ID + "=?", new String[] { String.valueOf(id) }, null, null, null, null);
if (cursor != null)
{()
cursor.moveToFirst();
}
Environment environment = new Environment(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2));
return environment;
}*/
//=//
public void getSavedTreeNodeAttributesFromDataBase(String numberSavedPosition)
{
SQLiteDatabase db = this.getReadableDatabase();
Cursor cursor = db.query(TABLE_NAME, new String[] { KEY_ID, KEY_IERARCHICAL_NAME, KEY_ENVIRONMENT_NAME, KEY_ENVIRONMENT_PICTURE, KEY_DEVICES_NAMES_ARRAY_STRING, KEY_DEVICES_PICTURES_ARRAY_STRING, KEY_CHILDREN_NAMES_ARRAY_STRING, KEY_DEVICES_PATTERNS_ARRAY_STRING, KEY_BUTTON_STATE_INT, KEY_MULTISENSOR_ARRAY_POSITION, KEY_NODE_TYPE }, KEY_ID + "=?", new String[] { numberSavedPosition }, null, null, null, null);
//<=+=>//Cursor cursor = db.query(TABLE_NAME, new String[] { KEY_ID, KEY_IERARCHICAL_NAME, KEY_ENVIRONMENT_NAME, KEY_ENVIRONMENT_PICTURE, KEY_DEVICES_NAMES_ARRAY_JSON_STRING, KEY_BUTTON_STATE_INT, KEY_MULTISENSOR_ARRAY_POSITION, KEY_NODE_TYPE }, KEY_ID + "=?", new String[] { numberSavedPosition }, null, null, null, null);
if (cursor != null && cursor.getCount()>0)
{
cursor.moveToFirst();
for (int i=0;i<10;i++)
{
savedTreeNodeAttributesFromDataBaseReturnStringArray[i] = cursor.getString(i+1);
}
}
else
{
//<=+=>//
cursor.moveToFirst();
for (int i=0;i<10;i++)
{
savedTreeNodeAttributesFromDataBaseReturnStringArray[i] = "er"+i+" count: "+getDataBaseItemsQuantity();
}
}
}
/*public ArrayList<TreeNode> restoreTreeNodesArrayListFromJSONDataBase(int id)
{
Gson gson = new Gson();
SQLiteDatabase db = this.getReadableDatabase();
Cursor cursor = db.query(TABLE_NAME, new String[] { KEY_ID, KEY_JSONSTRING }, KEY_ID + "=?", new String[] { String.valueOf(id) }, null, null, null, null);
if (cursor != null)
{
cursor.moveToFirst();
}

//Environment environment = new Environment(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2));

//cursor.getString(0) <= this json string need convert to arraylist
Type type = new TypeToken<ArrayList<TreeNode>>() {}.getType();
return gson.fromJson(cursor.getString(0), type);
}*/

public TreeNode formTreeNodeFromDataBaseRestoredData(String numberSavedPosition)
//public TreeNode formTreeNodeFromDataBaseRestoredData(String numberSavedPosition, TreeNode treeNode)
{
Log.i("DataBaseHadler", "0");
getSavedTreeNodeAttributesFromDataBase(numberSavedPosition);
temporaryTreeNode = new TreeNode("node_"+numberSavedPosition,  savedTreeNodeAttributesFromDataBaseReturnStringArray[1], Integer.valueOf(savedTreeNodeAttributesFromDataBaseReturnStringArray[2]).intValue(), implementInRobotListener, Integer.valueOf(savedTreeNodeAttributesFromDataBaseReturnStringArray[7]).intValue(), Integer.valueOf(savedTreeNodeAttributesFromDataBaseReturnStringArray[8]).intValue(), Integer.valueOf(savedTreeNodeAttributesFromDataBaseReturnStringArray[9]).intValue());
if (savedTreeNodeAttributesFromDataBaseReturnStringArray[5]!=null) //fOR A VARIANT, WE HERE VERIFIING IF STRING DEVICES PATTERNS AVAIABLE
{

/*Log.i("DataBaseHadler", "0");
jsonParser = new JsonParser();
jsonElement = jsonParser.parse(savedTreeNodeAttributesFromDataBaseReturnStringArray[3]);
jsonArray = jsonElement.getAsJsonArray();
if (jsonArray != null) 
{
for (int i=0;i<jsonArray.size();i++)
{
temporaryDevicesNamesArraylist.add(jsonArray.get(i).getAsString());
}
}*/
temporaryDevicesNamesArraylist = convertStringToArrayList(savedTreeNodeAttributesFromDataBaseReturnStringArray[3]);
 
/*Log.i("DataBaseHadler", "1");
jsonElement = jsonParser.parse(savedTreeNodeAttributesFromDataBaseReturnStringArray[4]);
jsonArray = jsonElement.getAsJsonArray();
if (jsonArray != null) 
{
for (int i=0;i<jsonArray.size();i++)
{
temporaryDevicesPicturesArraylist.add(jsonArray.get(i).getAsString());
}
}*/
temporaryDevicesPicturesArraylist = convertStringToArrayList(savedTreeNodeAttributesFromDataBaseReturnStringArray[4]);

/*Log.i("DataBaseHadler", "2");
jsonElement = jsonParser.parse(savedTreeNodeAttributesFromDataBaseReturnStringArray[5]);
jsonArray = jsonElement.getAsJsonArray();
if (jsonArray != null) 
{
for (int i=0;i<jsonArray.size();i++)
{
temporaryDevicesPatternsArraylist.add(jsonArray.get(i).getAsString());
}
}*/
temporaryDevicesPatternsArraylist = convertStringToArrayList(savedTreeNodeAttributesFromDataBaseReturnStringArray[6]);
temporaryChildrenNamesArraylist = convertStringToArrayList(savedTreeNodeAttributesFromDataBaseReturnStringArray[5]);
Log.i("DataBaseHadler", "3");
//need "<" not "<="
for (int i=0;i<temporaryDevicesPatternsArraylist.size();i++)
{
temporaryTreeNode.addDevice(Integer.valueOf(temporaryDevicesPatternsArraylist.get(i)).intValue(),temporaryDevicesNamesArraylist.get(i),Integer.valueOf(temporaryDevicesPicturesArraylist.get(i)).intValue());
}
temporaryTreeNode.setChildrenNamesArrayList(temporaryChildrenNamesArraylist);
Log.i("DataBaseHadler", "4");
}
return temporaryTreeNode;
}

//THIS IS ONLY TEMPORARY METHOD!!!
public String formTreeNodeFromDataBaseRestoredDataString(String numberSavedPosition)
{
getSavedTreeNodeAttributesFromDataBase(numberSavedPosition);
/*
JsonParser parser = new JsonParser();
JsonElement tradeElement = parser.parse(savedTreeNodeAttributesFromDataBaseReturnStringArray[4]);
JsonArray trade = tradeElement.getAsJsonArray();

return ""+(trade.get(1).getAsInt()+1);//https://www.javadoc.io/doc/com.google.code.gson/gson/2.8.5/com/google/gson/JsonElement.html
*/
return
savedTreeNodeAttributesFromDataBaseReturnStringArray[0]+"/"+
savedTreeNodeAttributesFromDataBaseReturnStringArray[1]+"/"+
savedTreeNodeAttributesFromDataBaseReturnStringArray[2]+"/"+
savedTreeNodeAttributesFromDataBaseReturnStringArray[3]+"/"+//here is json start
savedTreeNodeAttributesFromDataBaseReturnStringArray[4]+"/"+
savedTreeNodeAttributesFromDataBaseReturnStringArray[5]+"/"+
savedTreeNodeAttributesFromDataBaseReturnStringArray[6]+"/"+
savedTreeNodeAttributesFromDataBaseReturnStringArray[7]+"/"+
savedTreeNodeAttributesFromDataBaseReturnStringArray[8]+"/"+
savedTreeNodeAttributesFromDataBaseReturnStringArray[9]+"/";
}

//NONEED NOW?????????????????????
public List<Environment> getAllEnvironments() 
{
List<Environment> environmentsList = new ArrayList<Environment>();
String selectQuery = "SELECT  * FROM " + TABLE_NAME;
SQLiteDatabase db = this.getWritableDatabase();
Cursor cursor = db.rawQuery(selectQuery, null);
if (cursor.moveToFirst()) 
{
do 
{
Environment environment = new Environment();
environment.setID(Integer.parseInt(cursor.getString(0)));//???????????????????::
environment.setName(cursor.getString(1));//???????????????????::
environment.setPhoneNumber(cursor.getString(2));//???????????????????::
environmentsList.add(environment);
} 
while (cursor.moveToNext());
}
return environmentsList;
}
//NONEED NOW??????????????????????

/*public int updateEnvironment(Environment environment, String string) 
{
SQLiteDatabase dataBase = this.getWritableDatabase();
ContentValues values = new ContentValues();
values.put(KEY_PH_NO, string);
return dataBase.update(TABLE_NAME, values, KEY_ID + " = ?", new String[] { String.valueOf(environment.getID())});
}*/
//=//

//public int updateTreeNodeAttributesInDataBase(TreeNode treeNode)
public int updateTreeNodeAttributesInDataBase(String numberOfPositionInDataBase, TreeNode treeNode)
{
SQLiteDatabase dataBase = this.getWritableDatabase();
ContentValues values = new ContentValues();
values.put(KEY_ENVIRONMENT_NAME, treeNode.getEnvironmentName());
values.put(KEY_ENVIRONMENT_PICTURE, treeNode.getEnvironmentPicture());
values.put(KEY_DEVICES_NAMES_ARRAY_STRING, treeNode.getEnvironmentDevicesArrayListString(0));
values.put(KEY_DEVICES_PICTURES_ARRAY_STRING, treeNode.getEnvironmentDevicesArrayListString(1));
values.put(KEY_DEVICES_PATTERNS_ARRAY_STRING, treeNode.getEnvironmentDevicesArrayListString(2));
values.put(KEY_CHILDREN_NAMES_ARRAY_STRING, treeNode.getEnvironmentDevicesArrayListString(3));

//JSON STRINGS mAY BE COMMENTED BECOUSE UPDAITING IT - CAUSE TO RECURSIVE
//MULTIPLE SAVING AND FINALLY - FREEZING PROGRAM
//THIS IS THE VARIANT FOR THE FAR FUTURE: WE CAN CONVERT OUR
//ARRAYLIST INTO ARRAY, AND SAVE ARRAy TO STRING BY THIS
//METHODOLOGY - https://stackoverflow.com/questions/9053685/android-sqlite-saving-string-array
//values.put(KEY_DEVICES_NAMES_ARRAY_JSON_STRING, treeNode.getEnvironmentDevicesArrayListJsonString(0));
//values.put(KEY_DEVICES_PICTURES_ARRAY_JSON_STRING, treeNode.getEnvironmentDevicesArrayListJsonString(1));
//values.put(KEY_DEVICES_PATTERNS_ARRAY_JSON_STRING, treeNode.getEnvironmentDevicesArrayListJsonString(2));

values.put(KEY_BUTTON_STATE_INT, treeNode.getButtonStateInt());
values.put(KEY_MULTISENSOR_ARRAY_POSITION, treeNode.getMultiSensorArrayPosition());
values.put(KEY_NODE_TYPE, treeNode.getNodeType());
return dataBase.update(TABLE_NAME, values, KEY_ID + " = ?", new String[] { numberOfPositionInDataBase });
}

/*public void deleteEnvironment(Environment environment) 
{
SQLiteDatabase db = this.getWritableDatabase();
db.delete(TABLE_NAME, KEY_ID + " = ?", new String[] { String.valueOf(environment.getID()) });
db.close();
}*/
//=//
public void deleteTreeNodeAttributesInDataBase(String numberSavedPosition) 
{
SQLiteDatabase dataBase = this.getWritableDatabase();
dataBase.delete(TABLE_NAME, KEY_ID + " = ?", new String[] { numberSavedPosition });
dataBase.close();
}

public void deleteAll() 
{
SQLiteDatabase db = this.getWritableDatabase();
db.delete(TABLE_NAME, null, null);
db.close();
}

public void dropTable()
{
SQLiteDatabase db = this.getWritableDatabase();
db.execSQL("DROP TABLE IF EXISTS TABLE_NAME");
}

public int getDataBaseItemsQuantity() 
{
String countQuery = "SELECT  * FROM " + TABLE_NAME;
SQLiteDatabase db = this.getReadableDatabase();
Cursor cursor = db.rawQuery(countQuery, null);
int countInt = cursor.getCount();
cursor.close();
return countInt;
}

@Override//noneed??????
protected void finalize() throws Throwable 
{
this.close();
super.finalize();
}

public static String convertArrayToString(String[] array)
{
String str = "";
for (int i = 0;i<array.length; i++) 
{
str = str+array[i];
// Do not append comma at the end of last element
if(i<array.length-1)
{
str = str+strSeparator;
}
}
return str;
}

public static String[] convertStringToArray(String str)
{
String[] arr = str.split(strSeparator);
return arr;
}

public static String convertArrayListToString(ArrayList<String> arrayList)
{
return android.text.TextUtils.join(",", arrayList);
}

public static ArrayList<String> convertStringToArrayList(String inputString)
{
return new ArrayList<String>(Arrays.asList(inputString.split(",")));
}

}
//101_vl@ukr.net

