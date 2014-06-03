/**
 * 
 */
package com.idouba.datamining.classfication.decisiontree.model;

import java.util.Enumeration;
import java.util.Vector;

import javax.swing.tree.TreeNode;




/**
 * 
 * @author zhangchaomeng  2006/11/17
 * @version 1.0
 * @since 1.0
 */

public class DesionTreeNode implements TreeNode
{	

	private String _attributeName;
	private double _minGini;
	private AttributeImp _splitAttribute;
	private AttributeImp _decesionValue;
	private DesionTreeNode _leftChild;
	private DesionTreeNode _rightChild;
	private Vector _children = new Vector();
	private DesionTreeNode _parent;
	private String _decition ;
	/**
	 * @param parent the parent to set
	 */
	public void setParent(DesionTreeNode parent)
	{
		this._parent = parent;
	}

	/**
	 * @return the attributeName
	 */
	public String getAttributeName()
	{
		return _attributeName;
	}

	/**
	 * @param attributeName the attributeName to set
	 */
	public void setAttributeName(String attributeName)
	{
		this._attributeName = attributeName;
	}

	/**
	 * @return the minGini
	 */
	public double getMinGini()
	{
		return _minGini;
	}

	/**
	 * @param minGini the minGini to set
	 */
	public void setMinGini(double minGini)
	{
		this._minGini = minGini;
	}

	/**
	 * @return the splitAttribute
	 */
	public AttributeImp getSplitAttribute()
	{
		return _splitAttribute;
	}

	/**
	 * @param splitAttribute the splitAttribute to set
	 */
	public void setSplitAttribute(AttributeImp splitAttribute)
	{
		this._splitAttribute = splitAttribute;
	}

	/* (non-Javadoc)
	 * @see javax.swing.tree.TreeNode#children()
	 */
	public Enumeration children()
	{
		// TODO Auto-generated method stub
		Vector children = new Vector();
		children.elements();
		return null;
	}

	/* (non-Javadoc)
	 * @see javax.swing.tree.TreeNode#getAllowsChildren()
	 */
	public boolean getAllowsChildren()
	{
		// TODO Auto-generated method stub
		return true;
	}

	/* (non-Javadoc)
	 * @see javax.swing.tree.TreeNode#getChildAt(int)
	 */
	public TreeNode getChildAt(int childIndex)
	{
		// TODO Auto-generated method stub
		return (DesionTreeNode)_children.get(childIndex);
	}

	/* (non-Javadoc)
	 * @see javax.swing.tree.TreeNode#getChildCount()
	 */
	public int getChildCount()
	{
		// TODO Auto-generated method stub
		return _children.size();
	}

	/* (non-Javadoc)
	 * @see javax.swing.tree.TreeNode#getIndex(javax.swing.tree.TreeNode)
	 */
	public int getIndex(TreeNode node)
	{
		// TODO Auto-generated method stub
		return _children.indexOf(node);
	}

	/* (non-Javadoc)
	 * @see javax.swing.tree.TreeNode#getParent()
	 */
	public TreeNode getParent()
	{
		// TODO Auto-generated method stub
		return _parent;
	}

	/* (non-Javadoc)
	 * @see javax.swing.tree.TreeNode#isLeaf()
	 */
	public boolean isLeaf()
	{
		// TODO Auto-generated method stub
		return _children == null || _children.size() == 0;
	}

	/**
	 * @return the leftChild
	 */
	public DesionTreeNode getLeftChild()
	{
		return _leftChild;
	}
	
	public void addChild(DesionTreeNode child)
	{
		_children.add(child);
	}

	/**
	 * @param leftChild the leftChild to set
	 */
	public void setLeftChild(DesionTreeNode leftChild)
	{
		this._leftChild = leftChild;
		if(_leftChild != null)
		{
		_leftChild.setParent(this);
		_children.add(leftChild);
		}
	}

	/**
	 * @return the rightChild
	 */
	public DesionTreeNode getRightChild()
	{
		return _rightChild;
	}

	/**
	 * @param rightChild the rightChild to set
	 */
	public void setRightChild(DesionTreeNode rightChild)
	{
		this._rightChild = rightChild;
		if(_rightChild != null)
		{
		_rightChild.setParent(this);
		_children.add(rightChild);
		}
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		String toString ;
		DesionTreeNode parent = (DesionTreeNode)this.getParent();
		
		// TODO Auto-generated method stub
			if(!isLeaf())
			{
				toString =  this.getAttributeName()+ " ? "+this.getSplitAttribute() ;
			}
			else
			{
				toString = this.getDecition();
			}
			
			if(parent != null)
			{
				if(this.equals(parent.getChildAt(0)))
				{
				toString = toString+"("+parent.toString(0)+")" ;
				}
				else
				{
				toString = toString+"("+parent.toString(1)+")" ;
				}
			}
		
		return toString;		
	}
	
	public String toString(int index)
	{
		if(index == 0)
		{
		return this.getAttributeName()+ " < "+this.getSplitAttribute() ;
		}
		else
		{
			
			return this.getAttributeName()+ " > "+this.getSplitAttribute() ;
		}
	}

	/**
	 * @return the decition
	 */
	public String getDecition()
	{
		return this.getAttributeName()+ " : " + _decesionValue.toString();
	}

	/**
	 * @param decition the decition to set
	 */
	public void setDecition(String decition)
	{
		this._decition = decition;
	}

	/**
	 * @return the decesionValue
	 */
	public AttributeImp getDecesionValue()
	{
		return _decesionValue;
	}

	/**
	 * @param decesionValue the decesionValue to set
	 */
	public void setDecesionValue(AttributeImp decesionValue)
	{
		this._decesionValue = decesionValue;
	}
}
