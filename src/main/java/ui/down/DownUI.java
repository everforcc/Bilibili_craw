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
package ui.down;

import org.jb2011.lnf.beautyeye.ch3_button.BEButtonUI;
import ui.MainFrame;
import ui.utils.NPComponentUtils;
import ui.utils.NPHelper;
import ui.utils.PropertiesURL;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DownUI extends JPanel
{
	private static final int MIN_PROGRESS = 0;
	private static final int MAX_PROGRESS = 100;
	private static int currentProgress = MIN_PROGRESS;

	private JButton btnShow = new JButton("搜索");

	private JLabel videoName = new JLabel("videoName");
	private JLabel videoName_2 = new JLabel("videoName_2");

	JTextField jTextField = new JTextField(50);

	private GridBagLayout gridBag = new GridBagLayout();    // 布局管理器
	private GridBagConstraints gridBagConstraints = null;                    // 约束

	private static NPComponentUtils npComponentUtils = new NPComponentUtils();

	public DownUI()
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
		/*btnShow.setToolTipText(
				"<html>\n" +
					"<body>\n" +
						"弹出来的\n" +
						"<br>\n" +
						"HTML.\n" +
						"<img src=\"https://avatars.githubusercontent.com/u/48779058?v=4\">\n" +
					"</body>\n" +
				"</html>\n");*/


		System.out.println(PropertiesURL.getURL("imgs/np/cool_tool_tip_demo_bg.9.png"));
		// init btn pane
		JPanel btnPane = npComponentUtils.createPanel_root(
				NPHelper.createNinePatch(PropertiesURL.getURL("imgs/np/cool_tool_tip_demo_bg.9.png"), false)
				, new Insets(150,50,50,50));
		btnPane.setLayout(gridBag);

		JProgressBar progressBar = jProcessBar();

		JProgressBar progressBar_2 = jProcessBar();

		/* 设置布局 */

		gridBagConstraints = new GridBagConstraints();
		gridBag.addLayoutComponent(videoName, gridBagConstraints);

		gridBagConstraints = new GridBagConstraints();
		//
		gridBagConstraints.gridwidth = GridBagConstraints.REMAINDER;
		gridBag.addLayoutComponent(progressBar, gridBagConstraints);

		gridBagConstraints = new GridBagConstraints();
		gridBag.addLayoutComponent(videoName_2, gridBagConstraints);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridwidth = GridBagConstraints.REMAINDER;
		gridBag.addLayoutComponent(progressBar_2, gridBagConstraints);

		/* tianjia进去 */

		btnPane.add(videoName);
		btnPane.add(progressBar);

		btnPane.add(videoName_2);
		btnPane.add(progressBar_2);



		// 测试，删
		tProcess(progressBar);
		tProcess(progressBar_2);

		btnPane.remove(videoName_2);

		// init main ui
		this.add(btnPane, BorderLayout.CENTER);
		this.setBorder(BorderFactory.createEmptyBorder(100,100,100,100));
	}

	private JProgressBar jProcessBar(){
		// 创建一个进度条
		final JProgressBar progressBar = new JProgressBar();

		// 设置进度的 最小值 和 最大值
		progressBar.setMinimum(MIN_PROGRESS);
		progressBar.setMaximum(MAX_PROGRESS);

		// 设置当前进度值
		progressBar.setValue(currentProgress);

		// 绘制百分比文本（进度条中间显示的百分数）
		progressBar.setStringPainted(true);

		// 添加进度改变通知
		progressBar.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				System.out.println("当前进度值: " + progressBar.getValue() + "; " +
						"进度百分比: " + progressBar.getPercentComplete());
			}
		});
		return progressBar;
	}

	private void tProcess(JProgressBar progressBar){
		// 模拟延时操作进度, 每隔 0.5 秒更新进度
		new Timer(500, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				currentProgress++;
				if (currentProgress > MAX_PROGRESS) {
					currentProgress = MIN_PROGRESS;
				}
				progressBar.setValue(currentProgress);
			}
		}).start();
	}

	private void initListeners()
	{
		btnShow.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e)
			{
				JButton btn = (JButton) e.getSource();
				System.out.println(jTextField.getText());
				MainFrame.cutPane();
			}
		});
	}

}
