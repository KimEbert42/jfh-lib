/*
 * Copyright (C) 2012 by Kim Ebert
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 * 
 */

package com.reflectivedevelopment.jfh.ged.objects;

import java.util.ArrayList;

import com.reflectivedevelopment.jfh.ged.objects.fam.GedObjectFam;
import com.reflectivedevelopment.jfh.ged.objects.head.GedObjectHead;
import com.reflectivedevelopment.jfh.ged.objects.indi.GedObjectIndi;

public class GedObject {

	public static GedObject createGedObject(GedObject root, GedObject parent, int depth, String name, String value)
	{	
		if (root instanceof GedObjectHead)
		{
			return GedObjectHead.createGedObject(root, parent, depth, name, value);
		} 
		if (root instanceof GedObjectIndi)
		{
			return GedObjectIndi.createGedObject(root, parent, depth, name, value);
		}
		if (root instanceof GedObjectFam)
		{
			return GedObjectFam.createGedObject(root, parent, depth, name, value);
		}
		
		//check to see if head
		if (name.equals("HEAD") && parent == null && depth == 0)
		{
			return new GedObjectHead(parent, depth, name, value);	
		}
		
	
		if (name.startsWith("@") && name.endsWith("@") && value != null)
		{
			if (value.equals("INDI"))
			{
				return new GedObjectIndi(parent, depth, name, value);
			} else if (value.equals("FAM"))
			{
				return new GedObjectFam(parent, depth, name, value);
			}
		}
		
		if (name.equals("TRLR"))
			return new GedObjectTrlr(parent, depth, name, value);
		
		return new GedObjectUnknown(parent, depth, name, value);
	}
	
	protected int mDepth = 0;
	protected String mName = null;
	protected String mValue = null;
	protected ArrayList<GedObject> mChildren = new ArrayList<GedObject>();
	protected GedObject mParent = null;
	
	public GedObject(GedObject parent, int depth, String name, String value)
	{
		mDepth = depth;
		mName = name;
		mValue = value;
		mParent = parent;
	}
	
	public int getDepth()
	{
		return mDepth;
	}
	
	public String getName()
	{
		return mName;
	}
	
	public String getValue()
	{
		return mValue;
	}
	
	public GedObject getParent()
	{
		return mParent;
	}
	
	public void setParent(GedObject parent)
	{
		mParent = parent;
	}
	
	public void addChild(GedObject child)
	{
		mChildren.add(child);
		child.setParent(this);
	}
	
	public GedObject[] getChildren()
	{
		return (GedObject[]) mChildren.toArray();
	}
	
	public String toString()
	{
		String result = "";
		for (int i = 0; i < mDepth; i ++)
			result += " ";
		result = result + mDepth + ":" + mName + ":" + mValue;
		for (int i = 0; i < mChildren.size(); i ++)
			result = result + "\n" + mChildren.get(i).toString();
		return result; 
	}
}
