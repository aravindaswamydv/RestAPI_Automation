package com.qa.utils;

import java.io.*;
import java.util.ArrayList;

/* file1 - file2 = file3*/
public class CompareCSV {
public static void main(String args[]) throws FileNotFoundException, IOException
{
    String path="C:\\Users\\Ei01864\\git\\RestAPI_Automation\\RestAPI_Automation\\";
    String file1="MyUsers2.csv";
    String file2="MyUsers3.csv";
    String file3="MyUsers4.csv";
    ArrayList<String> al1=new ArrayList<String>();
    ArrayList<String> al2=new ArrayList<String>();
    //ArrayList al3=new ArrayList();

    BufferedReader CSVFile1 = new BufferedReader(new FileReader(path+file1));
    String dataRow1 = CSVFile1.readLine();
    while (dataRow1 != null)
    {
        String[] dataArray1 = dataRow1.split(",");
        for (String item1:dataArray1)
        { 
           al1.add(item1);
        }

        dataRow1 = CSVFile1.readLine(); // Read next line of data.
    }

     CSVFile1.close();

    BufferedReader CSVFile2 = new BufferedReader(new FileReader(path+file2));
    String dataRow2 = CSVFile2.readLine();
    while (dataRow2 != null)
    {
        String[] dataArray2 = dataRow2.split(",");
        for (String item2:dataArray2)
        { 
           al2.add(item2);

        }
        dataRow2 = CSVFile2.readLine(); // Read next line of data.
    }
     CSVFile2.close();

     for(String bs:al2)
     {
         al1.remove(bs);
     }

     int size=al1.size();
     System.out.println(size);

     try
        {
            FileWriter writer=new FileWriter(path+file3);
            while(size!=0)
            {
                size--;
                writer.append(""+al1.get(size));
                writer.append('\n');
            }
            writer.flush();
            writer.close();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
}}