package com.idouba.datamining.classfication.decisiontree.model;

import java.util.HashMap;
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
 * Data class 
 * @author zhangchaomeng   2006/11/17
 * @version 1.0
 * @since 1.0
 */

	public class Record
	{
		/**
		 * Constructor.
		 *
		 */
		public Record()
		{
			_attributes = new HashMap();
		}
		
		//The properties of record
		private HashMap _attributes;
		
		/**
		 * Add each attribue for the record accroding to the value parsed from data source.
		 * @param attributeName
		 * @param attributeValue
		 */
		public void addAttribute(String attributeName,Attribute attributeValue)
		{
			_attributes.put(attributeName, attributeValue);
		}

		/**
		 * Get the count of attribute
		 * @return count of attribute
		 */
		public int getAttributesCount()
		{
			return _attributes.size();
		}
		
		public AttributeImp getAttribute(String attributeName)
		{
			return (AttributeImp)_attributes.get(attributeName);
		}

		/* (non-Javadoc)
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString()
		{
			// TODO Auto-generated method stub
			return _attributes.toString();
		}
		
		
	}

