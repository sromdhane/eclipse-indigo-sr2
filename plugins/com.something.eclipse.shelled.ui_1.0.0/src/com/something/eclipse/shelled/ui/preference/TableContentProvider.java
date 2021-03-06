/* Copyright (c) 2004 Something Software Ltd. All rights reserved.*/ 
package com.something.eclipse.shelled.ui.preference;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;

/**
 * Content provider that maintains a generic list of objects which are shown in a table viewer.
 */
public class TableContentProvider implements IStructuredContentProvider
{
	protected List elements = new ArrayList();
	protected TableViewer tableViewer;
	private ViewerSorter sorter = null;

	public void add(Object o)
	{
		if(elements.contains(o)){ return; }
		elements.add(o);
		tableViewer.add(o);
		tableViewer.setSelection(new StructuredSelection(o),true);
	}

	public void dispose()
	{}

	public Object[] getElements(Object inputElement)
	{
		return elements.toArray(new Object[elements.size()]);
	}

	public void inputChanged(Viewer viewer, Object oldInput, Object newInput)
	{
		tableViewer = (TableViewer)viewer;
		elements.clear();
		if(newInput!=null)
		{
			tableViewer.setSorter(getSorter());
			List list;
			if(newInput instanceof List)
			{
				list = (List)newInput;
			}
			else
			{
				list = Arrays.asList((Object[])newInput);
			}
			elements.addAll(list);
		}
	}

	public void remove(Object o)
	{
		elements.remove(o);
		tableViewer.remove(o);
	}

	public void remove(IStructuredSelection selection)
	{
		Object[] array = selection.toArray();
		elements.removeAll(Arrays.asList(array));
		tableViewer.remove(array);
	}

	protected ViewerSorter getSorter()
	{
		if(sorter==null)
		{
			sorter = new ViewerSorter()
				{
					/**
					 * @see org.eclipse.jface.viewers.ViewerSorter#compare(org.eclipse.jface.viewers.Viewer, java.lang.Object, java.lang.Object)
					 */
					public int compare(Viewer viewer, Object e1, Object e2)
					{
						return e1.toString().compareToIgnoreCase(e2.toString());
					}
				};
		}
		return sorter;
	}
}