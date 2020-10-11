package com.mycompany.myapp81;
//THIS CLASS NONEED????

import android.content.Context;
import com.google.gson.Gson;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

class JSONHelper 
{
private static final String FILE_NAME = "data.json";

static boolean exportToJSON(Context context, List<TreeNode> dataList) 
{
Gson gson = new Gson();
DataItems dataItems = new DataItems();
dataItems.setPhones(dataList);
String jsonString = gson.toJson(dataItems);
//from here data saving start
FileOutputStream fileOutputStream = null;
try 
{
fileOutputStream = context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
fileOutputStream.write(jsonString.getBytes());
return true;
} 
catch (Exception e) 
{
e.printStackTrace();
} 
finally 
{
if (fileOutputStream != null) 
{
try 
{
fileOutputStream.close();
} 
catch (IOException e) 
{
e.printStackTrace();
}
}
}
//data saving end
return false;
}

static List<TreeNode> importFromJSON(Context context) 
{
//from here data read start
InputStreamReader streamReader = null;
FileInputStream fileInputStream = null;
try
{
fileInputStream = context.openFileInput(FILE_NAME);
streamReader = new InputStreamReader(fileInputStream);
Gson gson = new Gson();
DataItems dataItems = gson.fromJson(streamReader, DataItems.class);
return  dataItems.getPhones();
}
catch (IOException ex)
{
ex.printStackTrace();
}
finally 
{
if (streamReader != null) 
{
try 
{
streamReader.close();
} 
catch (IOException e) 
{
e.printStackTrace();
}
}
if (fileInputStream != null) 
{
try 
{
fileInputStream.close();
} 
catch (IOException e) 
{
e.printStackTrace();
}
}
//data read end
}

return null;
}

private static class DataItems 
{
private List<TreeNode> treeNodes;

List<TreeNode> getPhones() 
{
return treeNodes;
}

void setPhones(List<TreeNode> treeNodes) 
{
this.treeNodes = treeNodes;
}
}

}
