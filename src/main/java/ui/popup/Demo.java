/*
 * Copyright (C) 2015 Jack Jiang(cngeeker.com) The Swing9patch Project. 
 * All rights reserved.
 * Project URL:https://github.com/JackJiang2011/Swing9patch
 * Version 1.0
 * 
 * Jack Jiang PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 * Demo.java at 2015-2-6 16:10:04, original version by Jack Jiang.
 * You can contact author with jb2011@163.com.
 */
package ui.popup;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import org.jb2011.lnf.beautyeye.ch3_button.BEButtonUI;
import ui.Launch;
import ui.utils.NPComponentUtils;
import ui.utils.NPHelper;
import ui.utils.PropertiesURL;

public class Demo extends JPanel
{
	private JButton btnShow = new JButton("这是个按钮!");
	private static NPComponentUtils npComponentUtils = new NPComponentUtils();
	public Demo()
	{
		super(new BorderLayout());
		
		initGUI();
		initListeners();
	}
	
	private void initGUI()
	{
		// init sub coms
		btnShow.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.red));
		btnShow.setForeground(Color.white);
		btnShow.setToolTipText(
				"<html>\n" +
					"<body>\n" +
						"弹出来的\n" +
						"<br>\n" +
						"HTML.\n" +
						"<img src=\"https://avatars.githubusercontent.com/u/48779058?v=4\">\n" +
					"</body>\n" +
				"</html>\n");


		System.out.println(PropertiesURL.getURL("imgs/np/cool_tool_tip_demo_bg.9.png"));
		// init btn pane
		JPanel btnPane = npComponentUtils.createPanel_root(
				NPHelper.createNinePatch(PropertiesURL.getURL("imgs/np/cool_tool_tip_demo_bg.9.png"), false)
				, new Insets(150,50,50,50));
		btnPane.setLayout(new FlowLayout(FlowLayout.CENTER));
		btnPane.add(btnShow);
		
		// init main ui
		this.add(btnPane, BorderLayout.CENTER);
		this.setBorder(BorderFactory.createEmptyBorder(100,100,100,100));
	}
	
	private void initListeners()
	{
//		btnShow.addActionListener(new ActionListener(){
//			@Override
//			public void actionPerformed(ActionEvent e)
//			{
//				FloatableDialog fixtipDialog = FloatableDialog.createDialog(
//							new FixtipPane().setTiptext("Hay, rover here!")
//							, SwingUtilities.getWindowAncestor(Demo.this)
//							, btnShow);
//				fixtipDialog.display();
//			}
//		});
	}

}
