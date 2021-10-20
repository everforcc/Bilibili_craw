/*
 * Copyright (C) 2015 Jack Jiang(cngeeker.com) The Swing9patch Project. 
 * All rights reserved.
 * Project URL:https://github.com/JackJiang2011/Swing9patch
 * Version 1.0
 * 
 * Jack Jiang PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 * DragToMove.java at 2015-2-6 16:10:04, original version by Jack Jiang.
 * You can contact author with jb2011@163.com.
 */
package ui.utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 * Drag to move helper.
 * 
 * @author Jack Jiang(jb2011@163.com), 2012-11-07
 * @version 1.0
 */
public class DragToMove implements MouseListener,MouseMotionListener
{
	private Component 
	/** 鼠标拖动事件发生源 */
		srcCom
	/** 鼠标拖动后要作用的目的对象（即据鼠标拖动位置设置此目的组件位置） */
		,destCom;
	private int lastX=-1,lastY=-1;
	
	/**
	 * 默认设置移动的目的组件是其父窗口.
	 * @param srcCom
	 */
	public DragToMove(Component srcCom)
	{
		this(srcCom,null);
	}
	public DragToMove(Component srcCom,Component destCom)
	{
		this.srcCom=srcCom;
		this.destCom=destCom;
		init();
	}
	
	private void init()
	{
		srcCom.addMouseListener(this);
		srcCom.addMouseMotionListener(this);
	}
	
	private void reset()
	{
		this.lastX=-1;
		this.lastY=-1;
	}
	
	public static void apply(Component[] coms)
	{
		apply(coms,null);
	}
	/**
	 * @param coms
	 * @param destComs (目的destComs如不为空则必须要与coms一一对应）
	 */
	public static void apply(Component[] coms,Component[] destComs)
	{
		if(coms!=null)
		{
			boolean destIsParantWindow = (destComs==null);
			for(int i=0;i<coms.length;i++)
			{
				if(destIsParantWindow)
					new DragToMove(coms[i]);
				else
					new DragToMove(coms[i],destComs[i]);
			}
		}
	}
	
	//------------------------------------------- impl MouseListener
	public void mouseClicked(MouseEvent e)
	{
	}
	public void mouseEntered(MouseEvent e)
	{
		srcCom.setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
	}
	public void mouseExited(MouseEvent e)
	{
		srcCom.setCursor(Cursor.getDefaultCursor());
	}
	public void mousePressed(MouseEvent e)
	{
		this.lastX=e.getX();
		this.lastY=e.getY();
	}
	public void mouseReleased(MouseEvent e)
	{
		this.reset();
	}

	//------------------------------------------- impl MouseMotionListener
	public void mouseDragged(MouseEvent e)
	{
		int x=e.getX(),y=e.getY(),deltaX=x-this.lastX,deltaY=y-this.lastY;
		//目的组件未设置就默认认为是它的父窗口吧
		Component win=(destCom==null?SwingUtilities.windowForComponent(srcCom):destCom);
		if(win!=null)
//			win.setLocation((int)(win.getLocation().getX()+deltaX)
//					, (int)(win.getLocation().getY()+deltaY));
			setLocationImpl(win,deltaX,deltaY);
	}
	public void mouseMoved(MouseEvent e)
	{
	}
	
	protected void setLocationImpl(Component dest,int deltaX,int deltaY)
	{
		dest.setLocation((int)(dest.getLocation().getX()+deltaX)
				, (int)(dest.getLocation().getY()+deltaY));
	}
	
	public static void applyDragToMoveWindow(Component[] coms)
	{
		applyDragToMoveWindow(coms,null);
	}
	/**
	 * 给一组组件实现其上的拖动带动窗口的移动(目的destComs如不为空则必须要与coms一一对应）.
	 * @param coms
	 */
	public static void applyDragToMoveWindow(Component[] coms,Component[] destComs)
	{
		apply(coms,destComs);
	}
}
