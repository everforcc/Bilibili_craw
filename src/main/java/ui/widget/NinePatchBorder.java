/*
 * Copyright (C) 2015 Jack Jiang(cngeeker.com) The Swing9patch Project. 
 * All rights reserved.
 * Project URL:https://github.com/JackJiang2011/Swing9patch
 * Version 1.0
 * 
 * Jack Jiang PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 * NinePatchBorder.java at 2015-2-6 16:10:04, original version by Jack Jiang.
 * You can contact author with jb2011@163.com.
 */
package ui.widget;

import org.jb2011.ninepatch4j.NinePatch;

import javax.swing.border.AbstractBorder;
import java.awt.*;

/**
 * 一个利用NinePatch图实现边框的border实现类.
 * <p>
 * 本类可以很好地被重用于NinePatch图作为border实现的场景哦.
 * 
 * @author Jack Jiang(jb2011@163.com), 2012-09-04
 * @version 1.0
 */
public class NinePatchBorder extends AbstractBorder
{
	
	/** The insets. */
	private Insets insets = null;
	
	/** The np. */
	private NinePatch np = null;
	
	/**
	 * Instantiates a new nine patch border.
	 *
	 * @param insets the insets
	 * @param np the np
	 */
	public NinePatchBorder(Insets insets, NinePatch np)
	{
		this.insets = insets;
		this.np = np;
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.border.AbstractBorder#getBorderInsets(java.awt.Component)
	 */
	public Insets getBorderInsets(Component c)       
	{
		return insets;
	}

	/* (non-Javadoc)
	 * @see javax.swing.border.AbstractBorder#getBorderInsets(java.awt.Component, java.awt.Insets)
	 */
	public Insets getBorderInsets(Component c, Insets insets) 
	{
		return insets;
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.border.AbstractBorder#paintBorder(java.awt.Component, java.awt.Graphics, int, int, int, int)
	 */
	public void paintBorder(Component c, Graphics g, int x, int y, int width, int height)
	{
		this.np.draw((Graphics2D)g, x, y, width, height);
	}
}
