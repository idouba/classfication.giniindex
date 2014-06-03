package com.idouba.datamining.classfication.decisiontree.process;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

import com.idouba.datamining.classfication.decisiontree.model.AttributeImp;
import com.idouba.datamining.classfication.decisiontree.model.Record;



/* 
#----------------------------------------------------------------------------------------------------------------
# Gini Index Demo 
#
# This software is proprietary to and embodies the confidential technology
# of The Fifth Element Technologies, Inc.. Possession, use or copying of this software
# and media is authorized only pursuant to a valid written license from 
# The Fifth Element Technologies, Inc. or an authorized sublicensor.
#-----   ----------   ----------------  -------------------------------------------------------------------------
*/

/**
* A util class used to load data from diverse data source.
* @author zhangchaomeng 2006/11/17
* @version 1.0
* @since 1.0
*/

public class Loading 
{
   private static Loading _instance;
   private static ArrayList _attributeNames;
   private static String _className;
   public static Loading getInstance()
   {
	   if(_instance == null)
	   {
		   return new Loading();
	   }
	   else
		   return _instance;
 
   }
   
   /**
    * Load the data from a semil-structured text.
    * TODO will be realize by great laolou.
    * @return
    */
   public ArrayList loadDataFromText(String from)
   {
	   //ArrayList attributeNames =  getAttributeNames();
	   ArrayList datalist = new ArrayList();
	   //parse the data from different data format
	
		   try
		{
			BufferedReader reader = new BufferedReader(new FileReader(from));
			String line = reader.readLine();
			line = line.trim();
			_attributeNames = new ArrayList();
			//Load attribute names, headers.
			String[] attrNames = line.split("\\s+");
			for(int i = 0 ; i < attrNames.length ; i ++ )
			{
				_attributeNames.add(attrNames[i]);
			}
			
			 //load class name
			 // the last element of attribute is class, if not specified.
			_className = (String)_attributeNames.get(_attributeNames.size()-1);
			
			//load records
			while((line = reader.readLine())!=null )
			{
				if(!line.trim().equals(""))
				{
			 Record record = new Record();
			 String[] attributes = line.trim().split("\\s+");
			 for(int i = 0 ; i < attributes.length; i++)
			 {
				 AttributeImp attribute = new AttributeImp(attributes[i]);
				 record.addAttribute((String)_attributeNames.get(i), attribute);
				 
			 }
			 datalist.add(record);
				}
			}
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	   
	   
	   //demoLoad(datalist);
	   return datalist;
   }
   
   public ArrayList loadTestDataFromText(String from)
   {
	   //ArrayList attributeNames =  getAttributeNames();
	   ArrayList datalist = new ArrayList();
	   //parse the data from different data format
	
		   try
		{
			BufferedReader reader = new BufferedReader(new FileReader(from));
			String line = reader.readLine();
					
			 //load records
			while((line = reader.readLine())!=null )
			{
				if(!line.trim().equals(""))
				{
			 Record record = new Record();
			 String[] attributes = line.split(" ");
			 for(int i = 0 ; i < attributes.length; i++)
			 {
				 AttributeImp attribute = new AttributeImp(attributes[i]);
				 record.addAttribute((String)_attributeNames.get(i), attribute);
				 
			 }
			 datalist.add(record);
			// System.out.println(record);
				}
			}
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	   
	   
	   //demoLoad(datalist);
	   return datalist;
   }
   
   /**
    * TODO 
    * Get the names of class 
    * @return
    */
   public String  getClassNames()
   {
	   // the last element of attribute is class, if not specified.
	   return _className;
   }
   
   /**
    * TODO 
    * Get the names of attribute 
    * @return
    */
   public ArrayList getAttributeNames()
   {
	   return _attributeNames;
   }
   
   /**
    * Load the data from DB.
    * Maybe need not to realize since it is hard to find DB data source
    * @return
    */
   public ArrayList loadDataFromDB()
   {
	   ArrayList  datalist = new ArrayList ();
	   //parse the data from different data format
	   return datalist;
   }
   
   
   /**
    * A demo data collection for zhangchaomeng's using
    * 
    *
    */
   private void demoLoad(ArrayList datalist)
   {	   
	   
	   //record 1
	   Record record1 = new Record();
	   record1.addAttribute("brand", new AttributeImp("IBM"));
	   record1.addAttribute("price", new AttributeImp("5000"));
	   record1.addAttribute("weight", new AttributeImp("5.1"));	
	   record1.addAttribute("class", new AttributeImp("medium"));
	   datalist.add(record1);
	   
//	 record 2
	   Record record2 = new Record();
	   record2.addAttribute("brand", new AttributeImp("Microfot"));
	   record2.addAttribute("price", new AttributeImp("5060"));
	   record2.addAttribute("weight", new AttributeImp("5.2"));	
	   record2.addAttribute("class", new AttributeImp("high"));
	   datalist.add(record2);
	   
//	 record3
	   Record record3 = new Record();
	   record3.addAttribute("brand", new AttributeImp("Sun"));
	   record3.addAttribute("price", new AttributeImp("5070"));
	   record3.addAttribute("weight", new AttributeImp("6.8"));	
	   record3.addAttribute("class", new AttributeImp("high"));
	   datalist.add(record3);
	   
//	 record4
	   Record record4 = new Record();
	   record4.addAttribute("brand", new AttributeImp("IBM"));
	   record4.addAttribute("price", new AttributeImp("5090"));
	   record4.addAttribute("weight", new AttributeImp("5.1"));	 
	   record4.addAttribute("class", new AttributeImp("low"));
	   datalist.add(record4);
	   
//		 record5
	   Record record5 = new Record();
	   record5.addAttribute("brand", new AttributeImp("IBM"));
	   record5.addAttribute("price", new AttributeImp("5090"));
	   record5.addAttribute("weight", new AttributeImp("5.1"));	 
	   record5.addAttribute("class", new AttributeImp("low"));
	   datalist.add(record5);
	   
	   ////another 5
	   
	   Record record11 = new Record();
	   record11.addAttribute("brand", new AttributeImp("IBM"));
	   record11.addAttribute("price", new AttributeImp("6000"));
	   record11.addAttribute("weight", new AttributeImp("5.1"));	
	   record11.addAttribute("class", new AttributeImp("medium"));
	   datalist.add(record11);
	   
//	 record 2
	   Record record12 = new Record();
	   record12.addAttribute("brand", new AttributeImp("Microfot"));
	   record12.addAttribute("price", new AttributeImp("6060"));
	   record12.addAttribute("weight", new AttributeImp("5.2"));	
	   record12.addAttribute("class", new AttributeImp("high"));
	   datalist.add(record12);
	   
//	 record3
	   Record record13 = new Record();
	   record13.addAttribute("brand", new AttributeImp("Sun"));
	   record13.addAttribute("price", new AttributeImp("6070"));
	   record13.addAttribute("weight", new AttributeImp("6.8"));	
	   record13.addAttribute("class", new AttributeImp("high"));
	   datalist.add(record13);
	   
//	 record4
	   Record record14 = new Record();
	   record14.addAttribute("brand", new AttributeImp("IBM"));
	   record14.addAttribute("price", new AttributeImp("6090"));
	   record14.addAttribute("weight", new AttributeImp("5.1"));	 
	   record14.addAttribute("class", new AttributeImp("low"));
	   datalist.add(record14);
	   
//		 record15
	   Record record15 = new Record();
	   record15.addAttribute("brand", new AttributeImp("IBM"));
	   record15.addAttribute("price", new AttributeImp("6090"));
	   record15.addAttribute("weight", new AttributeImp("5.1"));	 
	   record15.addAttribute("class", new AttributeImp("low"));
	   datalist.add(record15);
	   
	   
////another 5
	   
	   Record record21 = new Record();
	   record21.addAttribute("brand", new AttributeImp("IBM"));
	   record21.addAttribute("price", new AttributeImp("6000"));
	   record21.addAttribute("weight", new AttributeImp("6.7"));	
	   record21.addAttribute("class", new AttributeImp("medium"));
	   datalist.add(record21);
	   
//	 record 2
	   Record record22 = new Record();
	   record22.addAttribute("brand", new AttributeImp("Microfot"));
	   record22.addAttribute("price", new AttributeImp("6060"));
	   record22.addAttribute("weight", new AttributeImp("6.2"));	
	   record22.addAttribute("class", new AttributeImp("high"));
	   datalist.add(record22);
	   
//	 record3
	   Record record23 = new Record();
	   record23.addAttribute("brand", new AttributeImp("Sun"));
	   record23.addAttribute("price", new AttributeImp("6070"));
	   record23.addAttribute("weight", new AttributeImp("7.8"));	
	   record23.addAttribute("class", new AttributeImp("high"));
	   datalist.add(record23);
	   
//	 record4
	   Record record24 = new Record();
	   record24.addAttribute("brand", new AttributeImp("IBM"));
	   record24.addAttribute("price", new AttributeImp("6090"));
	   record24.addAttribute("weight", new AttributeImp("6.1"));	 
	   record24.addAttribute("class", new AttributeImp("low"));
	   datalist.add(record24);
	   
//		 record15
	   Record record25 = new Record();
	   record25.addAttribute("brand", new AttributeImp("IBM"));
	   record25.addAttribute("price", new AttributeImp("6090"));
	   record25.addAttribute("weight", new AttributeImp("6.1"));	 
	   record25.addAttribute("class", new AttributeImp("low"));
	   datalist.add(record25);
   }

}
