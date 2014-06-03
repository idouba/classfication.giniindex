/**
 * 
 */
package com.idouba.datamining.classfication.decisiontree.process;

import java.util.ArrayList;
import java.util.Vector;

import com.idouba.datamining.classfication.decisiontree.model.AttributeImp;
import com.idouba.datamining.classfication.decisiontree.model.DesionTreeNode;
import com.idouba.datamining.classfication.decisiontree.model.Record;



/**
 * 
 * @author zhangchaomeng  2006/11/27
 * @version 1.0
 * @since 1.0
 */

public class Testing
{
	 Vector _rowDatas;
	 Vector _columNames;
	 
	 DesionTreeNode _rule;
	 ArrayList _testRecords;
	 
	 
	 
	 
	 
	 public Testing(DesionTreeNode _rule, ArrayList records)
	{
		this._rule = _rule;
		_testRecords = records;
	}
	 

	/**
	 * @return the testRecords
	 */
	public ArrayList getTestRecords()
	{
		return _testRecords;
	}

	/**
	 * @param testRecords the testRecords to set
	 */
	public void setTestRecords(ArrayList testRecords)
	{
		this._testRecords = testRecords;
	}

	/**
	 * @return the rule
	 */
	public DesionTreeNode getRule()
	{
		return _rule;
	}

	/**
	 * @param rule the rule to set
	 */
	public void setRule(DesionTreeNode rule)
	{
		this._rule = rule;
	}
	
	public void Test()
	{
		initColumNames();
		TestMining(_rule,_testRecords);
		
	}

	public int getConflictRecordCount()
	{
		return _rowDatas.size();
	}
	

	public  void TestMining(DesionTreeNode rule, ArrayList records)
	{
		_rowDatas = new Vector();
		
		for(int i = 0 ; i < records.size(); i ++)
		{
			Record record = (Record)records.get(i);
			//System.out.println(record+ "");
			StringBuffer decisionStr = new StringBuffer();
			if(!testRecord(rule,record,decisionStr))
			{
				Vector rowData = record2Vector(record);
				rowData.add(decisionStr.toString());				
				_rowDatas.add(rowData);
				//System.out.println("decisionStr: = " + decisionStr);
				
			}
		}
	}
	
	public  void initColumNames()
	{
		 _columNames = new Vector();
		 ArrayList attributeNames = Loading.getInstance().getAttributeNames();		 
		 String classAttributeName = Loading.getInstance().getClassNames();
		 attributeNames.remove(classAttributeName);
		 _columNames.addAll(attributeNames);
		 _columNames.add(classAttributeName);
		 _columNames.add("Rule Result");
	}
	
	public  Vector record2Vector(Record record)
	{ 
		Vector rowData = new Vector();
		for(int i = 0 ; i < _columNames.size() - 1; i++ )
		{   AttributeImp attribute = (AttributeImp)record.getAttribute((String)_columNames.get(i));
			rowData.add(attribute);
			
		}
		return rowData;
		
	}
	
	public static boolean testRecord(DesionTreeNode rule, Record record,StringBuffer ruledDecision)
	{
		boolean qualified;
		try
		{
			qualified = false;
			ArrayList attributeNames = Loading.getInstance()
					.getAttributeNames();
			String classAttributeName = Loading.getInstance()
					.getClassNames();
			attributeNames.remove(classAttributeName);
			DesionTreeNode testNode = rule;
			//DesionTreeNode lastLeaf  = testNode;
			while (!testNode.isLeaf())
			{
				AttributeImp spitPoint = testNode.getSplitAttribute();
				String attributeName = testNode.getAttributeName();
				AttributeImp attribute = record.getAttribute(attributeName);
				//lastLeaf = testNode;

				if (attribute.compareTo(spitPoint) < 0)
				{
					testNode = (DesionTreeNode)testNode.getChildAt(0);
				} else
				{
					testNode = (DesionTreeNode)testNode.getChildAt(1);
				}
			}
			AttributeImp resultAttr = testNode.getDecesionValue();
			qualified = resultAttr.equals(record
					.getAttribute(classAttributeName));
			if (!qualified)
			{
				ruledDecision.append(resultAttr.toString());
				//System.out.println("ruledDecision: = " + resultAttr);
			}
		} catch (Exception e)
		{
			System.out.println(record+ "");
			e.printStackTrace();
			return false;
		}		
		return qualified;
		
	}

	/**
	 * @return the columNames
	 */
	public Vector getColumNames()
	{
		return _columNames;
	}

	/**
	 * @return the rowDatas
	 */
	public Vector getRowDatas()
	{
		return _rowDatas;
	}

}
