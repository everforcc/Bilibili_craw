/*
 * Copyright (C) 2015 Jack Jiang(cngeeker.com) The Swing9patch Project. 
 * All rights reserved.
 * Project URL:https://github.com/JackJiang2011/Swing9patch
 * Version 1.0
 * 
 * Jack Jiang PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 * MainFrame.java at 2015-2-6 16:10:04, original version by Jack Jiang.
 * You can contact author with jb2011@163.com.
 */
package ui;

import ui.down.DownUI;
import ui.setting.SettingUI;
import ui.utils.Clipboard;
import ui.video.VideoUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;

public class MainFrame extends JFrame
{
	private static JTabbedPane tbs = new JTabbedPane();
	private JPanel mainPane = new JPanel(new BorderLayout());

	private static ui.popup.Demo demo = new ui.popup.Demo();
	private static VideoUI videoUI = new VideoUI();
	private static DownUI downUI = new DownUI();
	private static SettingUI settingUI = new SettingUI();

	public MainFrame(String path) throws HeadlessException 
	{
		//super ("Swing9patch Project - 欢迎加入Swing交流群：259448663");
		super ("这是一个标题");

		initGUI();
		
		setSize(900, 680);
	}
	
	private void initGUI()
	{
		this.mainPane.add(createMainTabs(), BorderLayout.CENTER);
		
		this.getContentPane().add(mainPane);
		this.setJMenuBar(createMenuBar());
	}
	
	private JComponent createMainTabs()
	{

		
		tbs.addTab("示例",demo); //Cool tooltip
		tbs.addTab("视频",videoUI);
		tbs.addTab("下载",downUI);
		tbs.addTab("设置",settingUI);
		// 下面的切换有点问题，
//		tbs.add(new org.jb2011.swing9patch.photoframe.Demo(), "相册");//Photo frame
//		tbs.add(new JPanel(), "Cool border demo");
//		tbs.add(new JPanel(), "仿手机短信内容查看");
//		tbs.add(new JPanel(), "Cool 名片");
//		tbs.add(new org.jb2011.swing9patch.toast.Demo(), "Toast");
		
		tbs.setToolTipTextAt(0, "简单说一下");
		tbs.setToolTipTextAt(1, "短视频");
		tbs.setToolTipTextAt(2, "下载啊");
//		tbs.setToolTipTextAt(2, "Photo frame");
//		tbs.setToolTipTextAt(3, "Cool border demo");
//		tbs.setToolTipTextAt(4, "仿手机短信内容查看");
//		tbs.setToolTipTextAt(5, "Cool 名片");
//		tbs.setToolTipTextAt(3, "Toast");
		
		return tbs;
	}

	public static void cutPane(){
		tbs.setSelectedIndex(0);
	}
	
	private JMenuBar createMenuBar() 
	{
		//------------------------------------ MenuDemo1
//		JMenu fileMenu = new JMenu("MenuDemo1");
//		JMenuItem openMenuItem = new JMenuItem("Menu item 1");
//		JMenuItem saveMenuItem = new JMenuItem("Menu item 2");
//		JMenuItem exitMenuItem = new JMenuItem("Menu item 3");
//		fileMenu.add(openMenuItem);
//		saveMenuItem.setEnabled(false);
//		fileMenu.add(saveMenuItem);
//		fileMenu.addSeparator();
//		fileMenu.add(exitMenuItem);
		
		//------------------------------------ MenuDemo2
//		JMenu fileMenu2 = new JMenu("MenuDemo2");
//		fileMenu2.add(new JMenuItem("Menu item 1"));
//		fileMenu2.add(new JMenuItem("Menu item 2"));
//		fileMenu2.addSeparator();
//		fileMenu2.add(new JMenuItem("Menu item 3"));
//		fileMenu2.add(new JMenuItem("Menu item 4"));
//		fileMenu2.addSeparator();
//		fileMenu2.add(new JMenuItem("Menu item 5"));
//		fileMenu2.add(new JMenuItem("Menu item 6"));
//		fileMenu2.add(new JMenuItem("Menu item 7"));
//		fileMenu2.add(new JMenuItem("Menu item 8"));
		
		//------------------------------------ About
		JMenu aboutMenu = new JMenu("关于");
		JMenuItem referenceMenuItem = new JMenuItem("参考项目");
		referenceMenuItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				JOptionPane.showMessageDialog(rootPane, "Swing9patch" +
						"\n - 本工程是一组利用BeautyEye工程相同基础技术实现的Swing组件或UI效果." +
						"\n - 工程内的UI组件或效果都可方便地抽取出来用于您的项目." +
						"\n - 本工程是BeautyEye的衍生工程，开发于2012年底，作是Jack Jiang(jb2011@163.com)." +
						"\n");
			}
		});
		JMenuItem thisProjectMenuItem = new JMenuItem("本项目地址");
		thisProjectMenuItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				ImageIcon everforccIcon = new ImageIcon("everforcc.jpg");
				/*JOptionPane.showMessageDialog(rootPane, "github" +
						"\n - https://github.com/everforcc/Bilibili_craw");*/
				// 选项按钮
				Object[] options = new Object[]{"访问", "复制"};
				String everforccGithub = "https://github.com/everforcc/Bilibili_craw";

				// 显示选项对话框, 返回选择的选项索引, 点击关闭按钮返回-1
				int optionSelected = JOptionPane.showOptionDialog(
						rootPane,
						everforccGithub,
						"项目地址",
						JOptionPane.YES_NO_CANCEL_OPTION,
						JOptionPane.ERROR_MESSAGE,
						everforccIcon,
						options,    // 如果传null, 则按钮为 optionType 类型所表示的按钮（也就是确认对话框）
						options[0]
				);

				if (optionSelected >= 0) {
					System.out.println("点击的按钮: " + options[optionSelected]);
					if(options[0].equals(options[optionSelected])){
						if (Desktop.isDesktopSupported()) {
							// 获取当前平台桌面实例
							Desktop desktop = Desktop.getDesktop();

							// 使用默认浏览器打开链接
							try {
								desktop.browse(URI.create(everforccGithub));
							} catch (IOException ex) {
								ex.printStackTrace();
							}

						} else {
							System.out.println("当前平台不支持 Desktop");
						}
					}else if(options[1].equals(options[optionSelected])){
						Clipboard.setClipboardString(everforccGithub);
					}
				}
			}
		});
		aboutMenu.add(referenceMenuItem);
		aboutMenu.add(thisProjectMenuItem);

		JMenuBar menuBar = new JMenuBar();
//		menuBar.add(fileMenu);
//		menuBar.add(fileMenu2);
		menuBar.add(aboutMenu);
		
		return menuBar;
	}
	

}
