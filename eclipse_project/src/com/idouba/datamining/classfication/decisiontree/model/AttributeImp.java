package com.idouba.datamining.classfication.decisiontree.model;
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
 * 
 * @author zhangchaomeng   2006/11/17
 * @version 1.0
 * @since 1.0
 */
public class AttributeImp implements Attribute,Comparable
{
    /**Value of attribute*/
	private Object _attributeValue;
	/**The type of attribute, there are only two types used here.
	 * <ul>
	 * <li>ATTRIBUTE_TYPE_CATEGORICAL for the type of String</li>
	 * <li>ATTRIBUTE_TYPE_NMMBERICAL for the type of Double</li>
	 * </ul>
	 * */
	private int _attributeType;
	
	/**for the type of String*/
	public static int ATTRIBUTE_TYPE_CATEGORICAL = 0;
	/**for the type of Double*/
	public static int ATTRIBUTE_TYPE_NMMBERICAL = 1;
	public static int CONFICT_DATA_TYPE = -1;
	
	
	
	/**
	 * Constructor with empty args
	 *
	 */
	public AttributeImp()
	{
		_attributeValue = null;
		_attributeType = -1;
	}

	/**
	 * Constructor
	 * @param attributeStr the string used to build the attribute
	 */
	public AttributeImp(String attributeStr)
	{
		this();
		parseValue(attributeStr);
	}

	/**
	 * Constructor 
	 * @param value the value of attribute
	 * @param type  the type of attribute
	 */
	public AttributeImp(Object value, int type)
	{
		_attributeValue = value;
		_attributeType = type;
	}

	/**
	 * The compare method
	 * @param to
	 * @return
	 */
	public int compareTo(Object to)
	{
		if(this.getAttributeType() != ((AttributeImp)to).getAttributeType())
		{
			return CONFICT_DATA_TYPE;
		}
		else if(this.getAttributeType() == ATTRIBUTE_TYPE_NMMBERICAL)
		{
			return ((Double)getAttributeValue()).compareTo((Double)((AttributeImp)to).getAttributeValue());
		}
		else
		{
			return ((String)getAttributeValue()).compareTo((String)((AttributeImp)to).getAttributeValue());
		}
	}
	
	
    /**
     * Get the value and type through the string
     * @param attributeStr
     */
	public void parseValue(String attributeStr)
	{
		try
		{
			_attributeType = ATTRIBUTE_TYPE_NMMBERICAL;
			_attributeValue = Double.valueOf(attributeStr);
		} catch (NumberFormatException e)
		{
			_attributeType = ATTRIBUTE_TYPE_CATEGORICAL;
			_attributeValue = attributeStr;
		}
	}

	/**
	 * @return the _attributeType
	 */
	public int getAttributeType()
	{
		return _attributeType;
	}

	/**
	 * @param type the _attributeType to set
	 */
	public void setAttributeType(int type)
	{
		_attributeType = type;
	}

	/**
	 * @return the _attributeValue
	 */
	public Object getAttributeValue()
	{
		return _attributeValue;
	}

	/**
	 * @param value the _attributeValue to set
	 */
	public void setAttributeValue(Object value)
	{
		_attributeValue = value;
	}

	
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object to)
	{
		if(this.getAttributeType() != ((AttributeImp)to).getAttributeType())
		{
			return false;
		}
		else if(this.getAttributeType() == ATTRIBUTE_TYPE_NMMBERICAL)
		{
			return (((Double)getAttributeValue()).compareTo((Double)((AttributeImp)to).getAttributeValue()))== 0;
		}
		else
		{
			return (((String)getAttributeValue()).compareTo((String)((AttributeImp)to).getAttributeValue())) == 0;
		}
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return getAttributeValue().toString();
	}
	
	
	

}
