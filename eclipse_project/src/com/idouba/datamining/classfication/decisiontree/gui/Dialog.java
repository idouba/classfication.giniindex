package com.idouba.datamining.classfication.decisiontree.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultTreeModel;

import com.idouba.datamining.classfication.decisiontree.model.DesionTreeNode;
import com.idouba.datamining.classfication.decisiontree.process.Loading;
import com.idouba.datamining.classfication.decisiontree.process.Training;
import com.idouba.datamining.classfication.decisiontree.process.Testing;




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
 * @author zhangchaomeng  2006/11/17
 * @version 1.0
 * @since 1.0
 */
public class Dialog extends JDialog
{
	private String _sourcePath;
	private String _targetPath;
	
	private JButton  _sourceButton;
	private JButton  _testButton ;
	private JButton _testfileButton;
	private JTextField _sourceField ;
	private JTextField _testField ;
	private JButton _miningButton;
	private JLabel _inforMiningLabel;
	private JLabel _inforTestLabel;
//	JPanel panel1;
//	JPanel panel2 ;
//	JPanel panel3; 
	JTree _desitionTree;
	JTable _testTable;
	DefaultTableModel _tableModel;
	DefaultTreeModel _treeModel;
	private int _miningRecordCount ;
	private int _testRecordCount ;
	private int _conflictCount;
	private DesionTreeNode _rule;
	
	
	/**
	 * @throws HeadlessException
	 */
	public Dialog() throws HeadlessException 
	{
		super();
		// TODO Auto-generated constructor stub
		JPanel mainPanel = jInit();
		this.getContentPane().add(mainPanel);
		initListener();
		//this.setDefaultCloseOperation(JDialog.EXIT_ON_CLOSE);
		this.setTitle("Gini Index Demo");
		this.pack();
//		Handle window closing correctly.
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
	
	private DefaultTreeModel buildDecisionTree()
	{
		ArrayList records = Loading.getInstance().loadDataFromText(_sourceField.getText().trim());
		_miningRecordCount = records.size();
		Training mining = new Training(records);
		_rule = Training.buildTreeNode(mining.getRecords(),mining.getAttributeNames());
		DefaultTreeModel treeModel= new DefaultTreeModel(_rule);
		return treeModel;
	}
	
	private DefaultTableModel buildTestTable()
	{
		ArrayList records = Loading.getInstance().loadDataFromText(_testField.getText().trim());
		_testRecordCount = records.size();
		Testing test = new Testing(_rule,records);
		test.Test();
		Vector columNames = test.getColumNames();
		Vector rowDatas = test.getRowDatas();
		_conflictCount = test.getConflictRecordCount();
//		Mining mining = new Mining(records);
//		DesionTreeNode node = Mining.buildTreeNode(mining.getRecords(),mining.getAttributeNames());
		DefaultTableModel treeModel= new DefaultTableModel(rowDatas,columNames);
		return treeModel;
	}
	

	private JPanel jInit()
	{
		JPanel panel = new JPanel();
		_sourceButton = new JButton("Import Training Data");
		_sourceField = new JTextField();
		_miningButton = new JButton("Start Mining");		
		
		_sourceField.setColumns(20);		
		 JPanel panel3 = new JPanel();
		_inforMiningLabel = new JLabel();
		_inforMiningLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		_inforMiningLabel.setAlignmentX((float)20.0);

		//panel3.setLayout(new GridLayout(3,1,0,0));
		panel3.add(_sourceField);
		panel3.add(_sourceButton);
		panel3.add(_miningButton);
		 Dimension minimumSize = new Dimension(400, 500);
		 _desitionTree = new JTree();
		 _treeModel =new DefaultTreeModel(null);
		 //_treeModel = buildTree();
		 _desitionTree.setModel(_treeModel);
		//JScrollPane scrollPanel = new JScrollPane(buildTree());	
		 JScrollPane scrollPanel = new JScrollPane(_desitionTree);	
		scrollPanel.setPreferredSize(new Dimension(400, 350));
		 scrollPanel.setMinimumSize(minimumSize);
		 panel.setLayout(new BorderLayout());
		panel.add(panel3,BorderLayout.NORTH);
		panel.add(scrollPanel,BorderLayout.CENTER);		
		panel.add(_inforMiningLabel,BorderLayout.SOUTH);
		
		
		JPanel testPanel = new JPanel();
		_testfileButton = new JButton("Import Test Data ");
		_testField = new JTextField();
		_testButton = new JButton("Start Testing");			
		_testField.setColumns(20);		
		
		_inforTestLabel = new JLabel();
		_inforTestLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		_inforTestLabel.setAlignmentX((float)20.0);
		
		 JPanel panelTest3 = new JPanel();
//		_inforLabel = new JLabel();
//		_inforLabel.setHorizontalAlignment(SwingConstants.RIGHT);
//		_inforLabel.setAlignmentX((float)20.0);

		//panel3.setLayout(new GridLayout(3,1,0,0));
		 panelTest3.add(_testField);
		 panelTest3.add(_testfileButton);		
		 panelTest3.add(_testButton);
		 Dimension minimumSizeTest = new Dimension(400, 100);
		 _testTable = new JTable();
		 _tableModel =new DefaultTableModel();
		 //_treeModel = buildTree();
		 _testTable.setModel(_tableModel);
		//JScrollPane scrollPanel = new JScrollPane(buildTree());	
		 JScrollPane scrollPanelTable = new JScrollPane(_testTable);	
		 scrollPanelTable.setPreferredSize(new Dimension(400, 100));
		 scrollPanelTable.setMinimumSize(minimumSize);
		 testPanel.setLayout(new BorderLayout());
		 testPanel.add(panelTest3,BorderLayout.NORTH);
		 testPanel.add(scrollPanelTable,BorderLayout.CENTER);		
		 testPanel.add(_inforTestLabel,BorderLayout.SOUTH);
		
		JPanel contentPanel = new JPanel();
		contentPanel.setLayout(new BorderLayout());
		contentPanel.add(panel,BorderLayout.CENTER);
		contentPanel.add(testPanel,BorderLayout.SOUTH);
		
		return contentPanel;
		
	}
	
	private void initListener()
	{
		_sourceButton.addMouseListener(new MouseAdapter()
				{
			    
			    public void mouseClicked(MouseEvent e) {
			    	JFileChooser fileChooser = new JFileChooser();
			    	//fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			    	int ret = fileChooser.showDialog(Dialog.this,null);
			    	if(ret == JFileChooser.APPROVE_OPTION)
			    	{
			    	File sourceFile = fileChooser.getSelectedFile();
			    	_sourcePath = sourceFile.getPath();
			    	_sourceField.setText(_sourcePath);
			    	}
			    }
			
				});
		
		_testfileButton.addMouseListener(new MouseAdapter()
		{
	    
	    public void mouseClicked(MouseEvent e) {
	    	JFileChooser fileChooser = new JFileChooser();
	    	//fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
	    	int ret = fileChooser.showDialog(Dialog.this,null);
	    	if(ret == JFileChooser.APPROVE_OPTION)
	    	{
	    	File sourceFile = fileChooser.getSelectedFile();
	    	_testField.setText(sourceFile.getPath());
	    	}
	    }
	
		});
		
		_miningButton.addMouseListener(new MouseAdapter()
				{
			 public void mouseClicked(MouseEvent e) {
				 try {
					 _treeModel =  buildDecisionTree();
					 _desitionTree.setModel(_treeModel);
					 _desitionTree.setExpandsSelectedPaths(true);
					 _desitionTree.setShowsRootHandles(true);
						for(int i = 0 ;i < _desitionTree.getRowCount(); i++)
						{
							_desitionTree.expandRow(i);
						}
					 _desitionTree.updateUI();
					 _inforMiningLabel.setText("Training data: " + _miningRecordCount + " records   ");

				} catch (Exception e1) {
					//FileDialog.this.setDefaultCloseOperation(JDialog.EXIT_ON_CLOSE);
					e1.printStackTrace();
				}	
			    }			
				});		
		_testButton.addMouseListener(new MouseAdapter()
		{
	 public void mouseClicked(MouseEvent e) {
		 try {
			 _tableModel =  buildTestTable();
			 _testTable.setModel(_tableModel);
			 _testTable.updateUI();
			 float rate = (_testRecordCount- _conflictCount)/(float)_testRecordCount * 100;
			 _inforTestLabel.setText("Correct rate: " +rate  +"% ,   Testing data: " + _testRecordCount + " records , " + "Conflict data : " + _conflictCount + " records   ");

		} catch (Exception e1) {
			//FileDialog.this.setDefaultCloseOperation(JDialog.EXIT_ON_CLOSE);
			e1.printStackTrace();
		}	
	    }			
		});
		
	}
		
	void resultInfor(long senconds)
	{
		JOptionPane.showMessageDialog(this,""+ senconds +"","",JOptionPane.INFORMATION_MESSAGE);
	}
	
	/***
	 * Provid the interface method for invoke.
	 *
	 */
	public void invokeTest()
	{
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	Dialog dialog = new Dialog();
        		dialog.setVisible(true);
            }
        });
	}
	
	
	/***
	 * Provid the interface method for invoke.
	 *
	 */
	public void invokeTestWithArgs(String argStr,Integer argIn)
	{
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	Dialog dialog = new Dialog();
        		dialog.setVisible(true);
            }
        });
	}
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
     
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	Dialog dialog = new Dialog();
            	dialog.pack();
        		dialog.setVisible(true);
            }
        });
		
	}
}
