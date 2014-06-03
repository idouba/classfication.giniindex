package com.idouba.datamining.classfication.decisiontree.process;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.TreeMap;
import java.util.TreeSet;

import com.idouba.datamining.classfication.decisiontree.model.AttributeImp;
import com.idouba.datamining.classfication.decisiontree.model.DesionTreeNode;
import com.idouba.datamining.classfication.decisiontree.model.Record;



//Just go ahead.
/**
 * A class used for data mining.
 * @author zhangchaomeng   2006/11/17
 * @version 1.0
 * @since 1.0
 */
/**
 * Gini(D) = 1 - (p1*p1+p2*p2+p3*p+...)
 * Ginisplit(D) = n1/n* gini(D1) + n2/n* gini(D2)
 */
public class Training
{
	private static ArrayList _records;
	private static ArrayList _attributeNames;
	private static String _classAttributeName;
	
	
	public Training(ArrayList records)
	{
		this._records = records;
		initData();
	}

	private void initData()
	{
		_attributeNames = Loading.getInstance().getAttributeNames();
		_classAttributeName = Loading.getInstance().getClassNames();
		_attributeNames.remove(_classAttributeName);
		
	}
	
	
	/**
	 * Decide which attribute should be selected as the decition attribute.
	 * General is the one has the lowest giniD among the attribute collection.
	 * @param records
	 * @param attributeNames
	 * @return
	 */
	public static DesionTreeNode getPriorAttribute(ArrayList records,ArrayList attributeNames)
	{
		if(records.size() == 0)
		{
			return null ;
		}
		TreeMap gini_attributeNode = new TreeMap();
		for(int i = 0 ; i < attributeNames.size(); i ++)
		{
			String attributeName = (String)attributeNames.get(i);
			TreeMap  gini_attribue = getSpitAttributeValue(records,attributeName);
			//test
			System.out.println("gini_attribue = " + gini_attribue + " size = " +gini_attribue.size() );
			
			Double minGini = (Double)gini_attribue.firstKey();
			AttributeImp splitAttribute = (AttributeImp)gini_attribue.get(minGini);
			
			DesionTreeNode treeNode = new DesionTreeNode();
			treeNode.setAttributeName(attributeName);
			treeNode.setMinGini(minGini.doubleValue());
			treeNode.setSplitAttribute(splitAttribute);
			
			gini_attributeNode.put(minGini, treeNode);
		}
		//the node with the lowest gini.
		if(gini_attributeNode.size() == 0)
		{
			return null;
		}
		else
		{
		return (DesionTreeNode)gini_attributeNode.get(gini_attributeNode.firstKey());
		}
	}
	
	/***
	 * Compute the gini of the given atribute according to each value of this attribute.
	 * @param records
	 * @param attributeName
	 * @return a map used to save the gini computed according to using this attribute as split point and the value of attribue 
	 */
	public static  TreeMap getSpitAttributeValue(ArrayList records,String attributeName)
	{
		//a map used to save the gini computed according to 
		//using this attribute as split point and the value of attribue 
		TreeMap gini_attribue = new TreeMap();
		//the class values
		TreeSet attributeSet = getUniqueAttributes(records,attributeName);
		//The count of records
		int recordCount = records.size();
		Iterator attribteIter = attributeSet.iterator();
		AttributeImp attribue;
		while(attribteIter.hasNext())
		{
			attribue = (AttributeImp)attribteIter.next();
			ArrayList leftArray = new ArrayList();
			ArrayList rightArray = new ArrayList();
			splitRecords(records,attributeName,attribue,leftArray,rightArray);
			double giniD_left = coumputGiniD(leftArray);
			double giniD_right = coumputGiniD(rightArray);	
			// Ginisplit(D) = n1/n* gini(D1) + n2/n* gini(D2)
			double leftRecordsCount = leftArray.size();
			double rightRecordCount = rightArray.size();
			double giniD = (leftRecordsCount/recordCount)*giniD_left + 
			            (rightRecordCount/recordCount)*giniD_right ;
			
            //a map used to save the gini computed according to 
			//using this attribute as split point and the value of attribue 
			gini_attribue.put(new Double(giniD), attribue);
		}
//		Double minGini = (Double)gini_attribue.firstKey();
//		splitAttribute = (AttributeImp)gini_attribue.get(minGini);
		return gini_attribue;
	}
	
	
	/**
	 * Gini(D) = 1 - (p1*p1+p2*p2+p3*p+...)
	 * return a max value for the empty list
	 * @param records
	 * @return
	 */
	public static double coumputGiniD(ArrayList records)
	{
		if(records != null && records.size()>0)
		{
		double giniD = 0;
		AttributeImp classAttribue;
		TreeSet classSet = getUniqueAttributes(records,_classAttributeName);
		Iterator classAttributeIter = classSet.iterator();
		double pj = 0;
		double sum_square_Pj = 0;
		double recordCount = records.size();
		while(classAttributeIter.hasNext())
		{
			classAttribue = (AttributeImp)classAttributeIter.next();;
			double classRecordCount = getClassRecordCount(records,classAttribue);
			pj = classRecordCount/recordCount;
			sum_square_Pj = sum_square_Pj + pj*pj;
		}
		 giniD = 1 - sum_square_Pj;	
		
		return giniD;
		}
		else
		{
			//return a max value for the empty list
			return Double.MAX_VALUE/2;
		}
	}
	
	
	/**
	 * Get the count of records with the required attribute.
	 * @param classAttributeValue
	 * @return the filtered Records
	 */
	public  static int  getClassRecordCount(ArrayList records ,AttributeImp classAttributeValue)
	{
		int count= 0;
		for(int i = 0 ; i < records.size(); i++)
		{
			Record record = (Record)records.get(i);
			if (record.getAttribute(_classAttributeName).equals(classAttributeValue))
			{
				count++;
			}
		}		
		return count;
	}
	
	
	/**
	 * Get the count of records with the required attribute.
	 * @param attributeName
	 * @param attribute
	 * @return the filtered Records
	 */
	public  static ArrayList getLabeledRecord(ArrayList records ,String attributeName,AttributeImp attribute)
	{
		ArrayList labeledRecord = new ArrayList();
		for(int i = 0 ; i < records.size(); i++)
		{
			Record record = (Record)records.get(i);
			if (record.getAttribute(attributeName).equals(attribute))
			{
				labeledRecord.add(record);
			}
		}		
		return labeledRecord;
	
	}
	
	
	
	/**
	 * Get the record count which attribute is in the left of split point
	 * @param records
	 * @param attributeName
	 * @param splitAttribute
	 * @return
	 */
	public static int getSplitedRecordCount(ArrayList records,String attributeName,AttributeImp splitAttribute)
	{
		int count = 0;
		for(int i = 0 ; i < records.size(); i++)
		{
			Record record = (Record)records.get(i);
			//the record count which attribute is in the left of split point
			if (record.getAttribute(attributeName).compareTo(splitAttribute)<0)
			{
				count++;
			}
		}		
		return count;
	}
	
	/**
	 * only one attribute remining.
	 * @param records
	 * @param attributeName
	 * @return
	 */
	public static DesionTreeNode buildEndBranch(ArrayList records,String attributeName)
	{
		//TODO the attribue value to certain class
		TreeMap  gini_attribue = getSpitAttributeValue(records,attributeName);
		Double minGini = (Double)gini_attribue.firstKey();
		AttributeImp splitAttribute = (AttributeImp)gini_attribue.get(minGini);		
		
		 DesionTreeNode node = new DesionTreeNode();
		 node.setAttributeName(attributeName);
		
		 node.setSplitAttribute(splitAttribute);
		 ArrayList leftArray = new ArrayList();
		 ArrayList rightArray = new ArrayList();
		 splitRecords(records,attributeName,splitAttribute,leftArray,rightArray);
		 
		 AttributeImp leftClass = getHightPosibilityClass(leftArray);
		 AttributeImp rightClass = getHightPosibilityClass(rightArray);
		 
		 //two class children.
		 if(leftClass != null && rightClass != null)
		 {
		 DesionTreeNode leftChild = new DesionTreeNode();
		 leftChild.setDecesionValue(leftClass);
		 leftChild.setAttributeName(_classAttributeName);
		 leftChild.setParent(node);
		 node.addChild(leftChild);		 
		 
		 if(!leftClass.equals(rightClass))
		 {
		 DesionTreeNode rightChild = new DesionTreeNode();
		 rightChild.setDecesionValue(rightClass);
		 rightChild.setAttributeName(_classAttributeName);
		 rightChild.setParent(node);
		 node.addChild(rightChild);
		 }
		 else
		 {
			 return leftChild;
		 }
		 }
		 //not two children
		 else
		 {
			 AttributeImp onlyChild = (leftClass != null)?leftClass:rightClass;
			 if(onlyChild !=null)
			 {
				 DesionTreeNode onlyChildNode = new DesionTreeNode();
				 onlyChildNode.setDecesionValue(rightClass);
				 onlyChildNode.setAttributeName(_classAttributeName);
				 //onlyChildNode.setParent(node);
				 node = onlyChildNode;
			 }
			 else
			 {
				 // two children are null ,then the parent node should be null;
				return null; 
			 }
		 }
			 
		 return node;
	}
	
	public static AttributeImp getHightPosibilityClass(ArrayList records)
	{	
		if(records.size() == 0)
		{
			return null;
		}
		 TreeSet uniqueClassValues =  getUniqueAttributes( records,getClassAttributeName() );
		 Iterator decisionIter = uniqueClassValues.iterator();
		 AttributeImp classAttributeValue ;
		 TreeMap count_ClassValue = new TreeMap();
		 while(decisionIter.hasNext())
		 {
			 classAttributeValue = (AttributeImp)decisionIter.next();
			 int count = getClassRecordCount( records ,classAttributeValue);
			 count_ClassValue.put(new Integer(count), classAttributeValue);			 
		 }
		 Integer count = (Integer)count_ClassValue.lastKey();
		 return (AttributeImp)count_ClassValue.get(count);
		
	}
	
	
	
	
	
	
	/**
	 * Build the root of the decision tree
	 * @param records
	 * @return
	 */
	public static DesionTreeNode buildTreeNode(ArrayList records,ArrayList attributeNames)
	{
		if(records == null || records.size() == 0)
		{
			return null;
		}
		DesionTreeNode node;
		//if there is single class label
		TreeSet uniqueClassValues =  getUniqueAttributes( records,getClassAttributeName() ); 
		if(uniqueClassValues.size() == 1)
		{
		    node = new DesionTreeNode();
		    node.setDecesionValue((AttributeImp)uniqueClassValues.first());
			node.setAttributeName(_classAttributeName);
			return node;
		}
		
		node =  getPriorAttribute(records,attributeNames);
		
		//end recursive TODO
		if(node == null)
		{
			return null;
		}
		
		if(attributeNames.size() == 1)
		{
			node = buildEndBranch(records,attributeNames.get(0).toString());
			return node;	 			 
		}
		
		String attributeName = node.getAttributeName();
		AttributeImp attribute = node.getSplitAttribute();
		
		//TODO can use the splited ,not need to use split again. 
		ArrayList leftArray = new ArrayList();
		ArrayList rightArray = new ArrayList();
		splitRecords(records,attributeName,attribute,leftArray,rightArray);
		
		//the next recursive willl not consider the current attribute
		ArrayList subAttributeNames = (ArrayList)attributeNames.clone();
		subAttributeNames.remove(attributeName);
		
		DesionTreeNode leftNode = buildTreeNode(leftArray,subAttributeNames);		
		DesionTreeNode rightNode = buildTreeNode(rightArray,subAttributeNames);
		
		 System.out.println("leftNode = " + leftNode);
		 System.out.println("rightNode = " + rightNode);
		 
		if(leftNode != null && rightNode != null)
		{
		if(leftNode.isLeaf() && rightNode.isLeaf() && leftNode.getDecesionValue().equals(rightNode.getDecesionValue()))
		{
			node = leftNode;
		}
		else
		{
		node.setLeftChild(leftNode);
		node.setRightChild(rightNode);
		}
		}
		 else if(leftNode != null)
		 {
			 node = leftNode;
			 System.out.println(" only leftNode");
		 }
		 else if(rightNode != null)
		 {
			 node = rightNode;
			 System.out.println(" only rightNode");
		 }
		 else
		 {
			 node = null;
		 }
		
		return node;
	}
	
	public  static void splitRecords(ArrayList records,String attributeName,AttributeImp splitAttribute,ArrayList leftArray,ArrayList rightArray)
	{
		if(leftArray == null)
		{
			leftArray = new ArrayList();
		}
		if(rightArray == null)
		{
			rightArray = new ArrayList();
		}
		
		leftArray.clear();
		rightArray.clear();
		
		for(int i = 0 ; i < records.size(); i++)
		{
			Record record = (Record)records.get(i);
			//the record count which attribute is in the left of split point
			if (record.getAttribute(attributeName).compareTo(splitAttribute)<0)
			{
				leftArray.add(record);
			}
			else
			{
				rightArray.add(record);
			}
		}		
	}
	
	
	
	public static TreeSet getUniqueAttributes(ArrayList records,String attributeName) 
	{
		TreeSet attributeDomain = new TreeSet();
		for(int i = 0 ; i < records.size(); i++)
		{
			Record record = (Record)records.get(i);
			attributeDomain.add(record.getAttribute(attributeName));			
		}
		
		return attributeDomain;
	}	
	
	
	public static void main(String args[])
	{
	}
	
	
	
	
	
	
	/****************************/
	
	public static void printUniqueSet(Collection c)
	{
		Object array[] = c.toArray();
		for(int i = 0 ; i < array.length; i++)
		{
		   System.out.println(array[i]);
		}
		
	}
	
//	public static void testCount()
//	{
//		ArrayList records = LoadDataUtil.getInstance().loadDataFromText("test");
//		Mining mining = new Mining(records);
//		int  price5060 = mining.getLabeledRecord( records,"price",new AttributeImp("5060")).size();
//		int  price5090 = mining.getLabeledRecord( records,"price",new AttributeImp("5090")).size();
//		
//		int  brandIBM = mining.getLabeledRecord( records,"brand",new AttributeImp("IBM")).size();
//		int  brandSun = mining.getLabeledRecord( records,"brand",new AttributeImp("Sun")).size();
//		
//		int all = mining.getLabeledRecord(records,null,null).size();
//		
//		System.out.println("price5060 = " + price5060);
//		System.out.println("price5090 = " + price5090);
//		System.out.println("brandIBM = " + brandIBM);
//		System.out.println("brandSun = " + brandSun);
//		System.out.println("all = " + all);
//	}
	
//	public static void testGetSplitedRecordCount()
//	{
//		ArrayList records = LoadDataUtil.getInstance().loadDataFromText("test");
//		Mining mining = new Mining(records);
//		ArrayList labelFilteredList = mining.getLabeledRecord(records, "brand",new AttributeImp("IBM"));
//		int splitcount = getSplitedRecordCount(labelFilteredList, "price",new AttributeImp("5070"));
//		System.out.println("splitcount = " + splitcount);
//		
//		ArrayList all = mining.getLabeledRecord( records, null,null);
//		int splitcountall = getSplitedRecordCount(all, "price",new AttributeImp("5070"));
//		System.out.println("splitcountall = " + splitcountall);
//	}

	/**
	 * @return the _attributeNames
	 */
	public static ArrayList getAttributeNames()
	{
		return _attributeNames;
	}

	/**
	 * @param names the _attributeNames to set
	 */
	public static void setAttributeNames(ArrayList names)
	{
		_attributeNames = names;
	}

	/**
	 * @return the _classAttributeName
	 */
	public static String getClassAttributeName()
	{
		return _classAttributeName;
	}

	/**
	 * @param attributeName the _classAttributeName to set
	 */
	public static void setClassAttributeName(String attributeName)
	{
		_classAttributeName = attributeName;
	}

	/**
	 * @return the _records
	 */
	public static ArrayList getRecords()
	{
		return _records;
	}

	/**
	 * @param _records the _records to set
	 */
	public static void setRecords(ArrayList _records)
	{
		Training._records = _records;
	}
	

}
